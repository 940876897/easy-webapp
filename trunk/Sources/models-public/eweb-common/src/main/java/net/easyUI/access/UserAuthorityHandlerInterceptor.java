package net.easyUI.access;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.easyUI.domain.common.Permission;
import net.easyUI.dto.common.CacheDTO;
import net.easyUI.dto.common.ConstantDTO;
import net.easyUI.dto.common.UserAgent;
import net.easyUI.common.web.adapter.AnnotationMethodHandlerInterceptorAdapter;
import net.easyUI.common.web.cookyjar.Cookyjar;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 管理端权限拦截控制器
 * 
 */
public class UserAuthorityHandlerInterceptor extends
		AnnotationMethodHandlerInterceptorAdapter {

	public static final String Request_Attribute_ErrorPageType = "R_A_ErrorPageType";
	public static final String Request_Attribute_ErrorType = "R_A_ErrorType";
	// 默认登录Cookie有效时间为8小时
	private Long loginLiveMilliSeconds = 8 * 60 * 60 * 1000L;

	@Override
	public void preInvoke(Method handlerMethod, Object handler,
			ServletWebRequest webRequest) {
		// 初始化URI对应的错误页面类型,
		// 读取Action方法体里的注解@ErrorPageType
		PageType errorPageType = AnnotationUtils.getAnnotation(handlerMethod,
				PageType.class);
		String pageType = "Page";
		if (errorPageType != null)
			pageType = errorPageType.value();
		// 将网页类型放入到Request里,以便出错时,能正确解析到对应的出错模板中.
		webRequest.getRequest().setAttribute(Request_Attribute_ErrorPageType,
				pageType);
		// Cookie
		Cookyjar cookyjar = (Cookyjar) webRequest.getAttribute(
				Cookyjar.CookyjarInRequest, RequestAttributes.SCOPE_REQUEST);
		UserAgent user;
		if (cookyjar == null)
			user = null;
		else {
			user = (UserAgent) cookyjar.getObject(UserAgent.class);
			// 判断登录时间是否过期,
			if (user != null
					&& new Date().getTime() - user.getLoginDateMilliSeconds() > loginLiveMilliSeconds) {
				user = null;
				cookyjar.clean();
			}
		}
		// 没有权限,或者没有登录 区分对待Ajax请求时,响应返回Ajax类型错误信息
		String errorType = pass(user, handlerMethod, handler, webRequest);
		if (errorType != null) {
			webRequest.getRequest().setAttribute(Request_Attribute_ErrorType,
					errorType);
			throw new UserAccessDeniedException(errorType);
			// 到异常控制类中去处理
		}
	}

	/**
	 * 如果没有权限 ,就返回这个没通过的权限,如果有权限,就返回null. <BR>
	 * TODO 建议给权限,URL缓存加一个限制,当缓存数量超过一定数量时,就清空一次,或者给一个命中计数器,当缓存数量超过一定数量时,按照一定算法,
	 * 清除部分缓存.
	 * 
	 * @return 如果没有权限 ,就返回这个没通过的权限,如果有权限,就返回null
	 */
	private String pass(UserAgent user, Method handlerMethod, Object handler,
			ServletWebRequest webRequest) {
		// 访问的URI 去掉ContextPath，再与配置文档中的配置比较
		String requestURI = webRequest.getRequest().getServletPath() + "?"
				+ webRequest.getRequest().getQueryString();

		if (StringUtils.isBlank(requestURI)) {
			return null;
		}

		Permission canPermission = null;
		Collection<Permission> permissionList = CacheDTO.permissionList;
		// 没有配置任何URI权限,表示不需要权限
		if (CollectionUtils.isEmpty(permissionList)) {
			return null;
		}
		// 查寻此URI配置的权限
		for (Permission permission : permissionList) {
			if (requestURI.startsWith(permission.getUri())) {
				// uriPermissions.add(permission);
				// 比较,权限URI最长的,表示这个权限是更优先的权限
				if (canPermission == null) {
					canPermission = permission;
				} else {
					if (canPermission.getUri().length() < permission.getUri()
							.length()) {
						canPermission = permission;
					}
				}
			}
			// 没有配置这个URI权限,没有配置URI需要的权限,默认就是不需要权限控制.
			// 如果这个资源Key是以ConstantDTO.permissionKey_GUEST_PRE开头的,表示这个资源可以给游客访问
			if (canPermission == null
					|| canPermission.getPermissionKey().startsWith(
							ConstantDTO.permissionKey_GUEST_PRE)) {
				return null;
			}
		}

		// 如果不需要权限的,已经在上面的代码中返回了,剩下的都是要权限的,必须要登录了.
		if (user == null) {
			return "login";
		}

		// 如果没有角色数据,而uriPermissions是有配置的,说明没有生效的角色,其不可能有权限
		if (CacheDTO.rolePermissionMap == null
				|| CacheDTO.rolePermissionMap.isEmpty()) {
			return "permission";
		}
		// 用户角色集
		// 登录后可访问权限例外 , 已经在前面过滤掉了登录权限
		if (canPermission.getPermissionKey().startsWith(
				ConstantDTO.permissionKey_LOGIN_PRE)) {
			return null;// 有权限
		}
		// 此用户没有角色, 应该不会发生, 应该都有默认角色.
		List<Permission> userPers = getUserPermissions(user.getRoleKeys());
		if (CollectionUtils.isEmpty(userPers)) {
			return "permission";
		}
		// TODO 下面判断是否有权限的请注意与UserAgent.java中havePermission（）方法保持一致
		/* 一个URL可以分给几个权限,匹配值最高的权限为有效权限 */
		for (Permission userPer : userPers) {
			// 如果是超级管理员的权限,那就直接有权限
			if (ConstantDTO.permissionKey_SUPPERADMIN_PRE.equals(userPer
					.getPermissionKey())) {
				return null;// 有权限
			}
			if (userPer.getPermissionKey().equals(
					canPermission.getPermissionKey())) {
				return null;// 有权限
			}
		}
		// 默认返回无权限.
		return "permission";
	}

	private List<Permission> getUserPermissions(List<String> userRoleKeys) {
		if (CollectionUtils.isEmpty(userRoleKeys)) {
			return null;
		}
		List<Permission> perList = new ArrayList<Permission>();
		for (String rolesName : userRoleKeys) {

			Collection<Permission> pTmps = CacheDTO.rolePermissionMap
					.get(rolesName);
			if (CollectionUtils.isEmpty(pTmps)) {
				continue;
			}
			// 不重复的的收入进来
			for (Permission permissionT : pTmps) {
				boolean b = true;
				for (Permission permission : perList) {
					if (permission.getPermissionKey().equals(
							permissionT.getPermissionKey())) {
						b = false;
						break;
					}
				}
				if (b)
					perList.add(permissionT);
			}
		}
		return perList;
	}

	public Long getLoginLiveMilliSeconds() {
		return loginLiveMilliSeconds;
	}

	public void setLoginLiveMilliSeconds(Long loginLiveMilliSeconds) {
		if (loginLiveMilliSeconds > 0)
			this.loginLiveMilliSeconds = loginLiveMilliSeconds;
	}
}
