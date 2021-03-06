package net.easyUI.web.resolver;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.easyUI.access.UserAccessDeniedException;
import net.easyUI.access.UserAuthorityHandlerInterceptor;
import net.easyUI.dto.common.UserAgent;
import net.easyUI.common.dto.enums.EnumErrorType;
import net.easyUI.common.dto.enums.EnumPageType;
import net.easyUI.common.web.cookyjar.Cookyjar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionResolver implements HandlerExceptionResolver {
	private static final Log logger = LogFactory
			.getLog(ExceptionResolver.class);

	private String webEncoding = "UTF-8";
	private String errorPagePath = "/common/errorPage/";
	private String adminLoginReturnParameterName = "backto";

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		logger.error("你的操作发生错误(可能是没有权限导致):", ex);
		// 没有登录,没有权限
		if (ex instanceof UserAccessDeniedException) {
			return resolveAccessDeniedException(request,
					(UserAccessDeniedException) ex);
		}
		String ept = (String)request.getAttribute(UserAuthorityHandlerInterceptor.Request_Attribute_ErrorPageType);
		if (ept == null)
			ept = EnumPageType.PAGE.getCode();

		return new ModelAndView(this.errorPagePath
				+ EnumErrorType.ERROR.getCode() + ept);
	}

	/**
	 * 区分对待Ajax请求时,响应返回Ajax类型错误信息
	 * 
	 * @param request
	 * @param ex
	 * @return
	 */
	private ModelAndView resolveAccessDeniedException(
			HttpServletRequest request, UserAccessDeniedException ex) {
		Cookyjar cookyjar = (Cookyjar) request
				.getAttribute(Cookyjar.CookyjarInRequest);
		UserAgent agent = (UserAgent) cookyjar.getObject(UserAgent.class);
		// 区分对待Ajax请求时,响应返回Ajax类型错误信息 如何获取已注解的@ErrorPageType
		String ept = (String)request.getAttribute(UserAuthorityHandlerInterceptor.Request_Attribute_ErrorPageType);
		if (ept == null)
			ept = EnumPageType.PAGE.getCode();

		// 没登录，定向到登录界面 ex.getMessage().equals("login")
		if (ex.getMessage().equals("login") || agent == null
				|| ex.getMessage().equals("")) {
			// 如果页面类型是普通页面,就跳转到完整的登录页面,并且登录后返回到登录前的页面
			if (ept.equals(EnumPageType.PAGE)) {
				String returnUrl = getReturnUrl(request);
				return new ModelAndView("redirect:/"
						+ EnumErrorType.LOGIN.getCode() + ept + ".xhtml",
						adminLoginReturnParameterName, returnUrl);
			} else {
				String returnUrl = getReturnUrl(request);
				ModelMap model = new ModelMap();
				model.put(adminLoginReturnParameterName, returnUrl);
				model.put("dwzId", request.getParameter("dwzId"));
				return new ModelAndView(this.errorPagePath
						+ EnumErrorType.LOGIN.getCode() + ept, model);
			}
		}

		return new ModelAndView(this.errorPagePath + ex.getMessage() + ept);
	}

	/**
	 * 获取要返回的之前网页URL
	 * 
	 * @param request
	 * @return
	 */
	private String getReturnUrl(HttpServletRequest request) {
		StringBuffer sb = request.getRequestURL();
		appendRequestParameters(sb, request);
		try {
			return URLEncoder.encode(sb.toString(), this.webEncoding);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 组装请求URI参数
	 * 
	 * @param sb
	 * @param request
	 */
	@SuppressWarnings("rawtypes")
	private void appendRequestParameters(StringBuffer sb,
			HttpServletRequest request) {
		Enumeration en = request.getParameterNames();
		if (!en.hasMoreElements()) {
			return;
		}
		sb.append('?');
		while (en.hasMoreElements()) {
			String name = (String) en.nextElement();
			String[] values = request.getParameterValues(name);
			if (values == null || values.length == 0) {
				continue;
			}
			for (String v : values) {
				try {
					v = URLEncoder.encode(v, this.webEncoding);
				} catch (UnsupportedEncodingException ignore) {
				}
				sb.append(name).append('=').append(v).append('&');
			}
		}
		sb.deleteCharAt(sb.length() - 1);
	}

	public void setErrorPagePath(String errorPagePath) {
		this.errorPagePath = errorPagePath;
	}

	public void setWebEncoding(String webEncoding) {
		this.webEncoding = webEncoding;
	}

	public void setAdminLoginReturnParameterName(
			String adminLoginReturnParameterName) {
		this.adminLoginReturnParameterName = adminLoginReturnParameterName;
	}
}