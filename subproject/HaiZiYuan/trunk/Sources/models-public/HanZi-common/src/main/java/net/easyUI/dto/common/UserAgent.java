package net.easyUI.dto.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.easyUI.domain.common.Permission;
import net.easyUI.domain.common.Role;
import net.easyUI.common.dto.UserDto;
import net.easyUI.common.web.cookyjar.SelfDependence;
import net.easyUI.common.web.cookyjar.util.SelfUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 后台用户的cookie持久化对象
 * 
 * 
 */
public class UserAgent implements Serializable, SelfDependence, UserDto {
	protected Log logger = LogFactory.getLog(this.getClass());

	private static final long serialVersionUID = -3921778874150895446L;

	private Long id;
	private String loginName;
	private String displayName;
	private Long loginDateMilliSeconds = 0L;
	private List<String> roleKeys = new ArrayList<String>(); // 角色Id
	private List<Role> roleList = new ArrayList<Role>(); // 角色列表

	public UserAgent() {
		super();
	}

	public UserAgent(UserDto user) {
		super();
		this.id = user.getId();
		this.loginName = user.getLoginName();
		this.displayName = user.getDisplayName();
		this.loginDateMilliSeconds = new Date().getTime();
	}

	/**
	 * 检查是否拥有这个权限Key。
	 * 下面判断是否有权限的请注意与UserAuthorityHandlerInterceptor.java中pass（大概154行）方法保持一致
	 * 
	 * @param permissionKey
	 * @return
	 */
	public boolean havePermission(String permissionKey) {
		// TODO 暂时全部返回有权限!
		if (logger.isDebugEnabled()) {
			logger.debug("开发模式,暂时全部有权限");
			return true;// 有权限
		}

		// 用户角色集
		List<Permission> userPers = getUserPermissions(this.roleKeys);
		// 登录后可访问权限例外 , 已经在前面过滤掉了登录权限
		if (permissionKey.startsWith(ConstantDTO.permissionKey_LOGIN_PRE)) {
			return true;// 有权限
		}

		if (CollectionUtils.isEmpty(userPers)) {
			return false;
		}
		/* 一个URL可以分给几个权限,匹配值最高的权限为有效权限 */
		for (Permission userPer : userPers) {
			// 如果是超级管理员的权限,那就直接有权限
			if (ConstantDTO.permissionKey_SUPPERADMIN_PRE.equals(userPer
					.getPermissionKey())) {
				return true;// 有权限
			}
			if (userPer.getPermissionKey().equals(permissionKey)) {
				return true;// 有权限
			}
		}
		return false;
	}

	/**
	 * 检查是否拥有这个URL的权限
	 * 
	 * @param requestURI
	 *            URL必须以Web根目录开始(/)
	 * @return
	 */
	public boolean haveUrlPermission(String requestURI) {
		if (StringUtils.isBlank(requestURI))
			return false;
		// 先找出这个URL最终生效的权限Key
		Permission canPermission = null;
		Collection<Permission> permissionList = CacheDTO.permissionList;
		// 没有配置任何URI权限
		if (CollectionUtils.isEmpty(permissionList)) {
			return true;
		}
		// 查寻此URI配置的权限
		for (Permission permission : permissionList) {
			if (requestURI.startsWith(permission.getUri())) {
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
		}
		// 如果没有找到一个有效控制权限,表示不需要权限
		if (canPermission == null)
			return true;
		return havePermission(canPermission.getPermissionKey());
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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String lieDown() {
		StringBuilder sb = new StringBuilder();
		for (String role : this.getRoleKeys()) {
			sb.append(role + "-");
		}
		return SelfUtil.format(this.id + "", this.loginName, this.displayName,
				this.loginDateMilliSeconds.toString(), sb.toString());
	}

	public SelfDependence riseUp(String value) {
		String[] values = SelfUtil.recover(value);
		try {
			this.id = Long.parseLong(values[0]);
		} catch (Exception e) {
		}
		this.loginName = values[1];
		this.displayName = values[2];
		this.loginDateMilliSeconds = Long.parseLong(values[3]);
		String roleStrs = values[4];
		if (StringUtils.isNotBlank(roleStrs)
				&& MapUtils.isNotEmpty(CacheDTO.roleMap)) {
			for (String roleKeyStr : Arrays.asList(roleStrs.split("-"))) {
				if (StringUtils.isNotBlank(roleKeyStr)) {
					Role roleTmp = CacheDTO.roleMap.get(roleKeyStr.trim());
					if (roleTmp != null) {
						this.roleList.add(roleTmp);
						this.roleKeys.add(roleKeyStr.trim());
					}
				}
			}
		}
		return this;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<String> getRoleKeys() {
		return roleKeys;
	}

	public void setRoleKeys(List<String> roleKeys) {
		this.roleKeys = roleKeys;
	}

	public Date getLoginDate() {
		return new Date(loginDateMilliSeconds);
	}

	public Long getLoginDateMilliSeconds() {
		return loginDateMilliSeconds;
	}

	public void setLoginDateMilliSeconds(Long loginDateMilliSeconds) {
		this.loginDateMilliSeconds = loginDateMilliSeconds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
