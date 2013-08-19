package net.easyUI.access;

import javax.servlet.http.HttpServletRequest;

import net.easyUI.dto.common.UserAgent;
import net.easyUI.common.web.cookyjar.Cookyjar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.tools.view.ViewContext;

public class UserAccessVMTool {
	protected Log logger = LogFactory.getLog(this.getClass());

	private UserAgent agent;

	public void init(Object obj) {
		if (!(obj instanceof ViewContext)) {
			throw new IllegalArgumentException(
					"Tool can only be initialized with a ViewContext");
		}
		ViewContext viewContext = (ViewContext) obj;
		HttpServletRequest request = viewContext.getRequest();
		Cookyjar cookyjar = (Cookyjar) request
				.getAttribute(Cookyjar.CookyjarInRequest);
		if (cookyjar == null) {
			logger.error("用户没有登录,Cookyjar not find in HttpServletRequest.");
			// throw new IllegalStateException(
			// "Cookyjar not find in HttpServletRequest");
		}
		agent = (UserAgent) cookyjar.getObject(UserAgent.class);
		if (agent == null) {
			logger.error("用户没有登录,UserAgent not find in Cookyjar");
			// throw new
			// IllegalStateException("UserAgent not find in Cookyjar");
		}
	}

	/**
	 * Velocity工具,检查是否拥有这个权限Key
	 * 
	 * @param permissionKey
	 * @return
	 */
	public boolean has(String permissionKey) {
		// TODO 暂时全部返回有权限!
		if (logger.isDebugEnabled()) {
			logger.debug("开发模式,暂时全部有权限");
			return true;// 有权限
		}
		if (this.agent == null || StringUtils.isBlank(permissionKey)) {
			logger.error("用户没有登录,或者权限Key为空,返回无权限");
			// throw new IllegalArgumentException("unknow function name:" +
			// permissionKey);
			return false;
		}
		return this.agent.havePermission(permissionKey);
	}

	/**
	 * Velocity工具,检查是否拥有这个URL的权限
	 * 
	 * @param url
	 *            URL必须以Web根目录开始(/)
	 * @return
	 */
	public boolean hasUrl(String url) {
		// TODO 暂时全部返回有权限!
		if (logger.isDebugEnabled()) {
			logger.debug("开发模式,暂时全部有权限");
			return true;// 有权限
		}
		if (this.agent == null || StringUtils.isBlank(url)) {
			logger.error("用户没有登录,或者权限Key为空,返回无权限");
			// throw new IllegalArgumentException("unknow function name:" +
			// permissionKey);
			return false;
		}
		return this.agent.haveUrlPermission(url);
	}

	/**
	 * 获取登录用户对象
	 * 
	 * @return UserAgent 登录用户对象
	 */
	public UserAgent getAgent() {
		return agent;
	}

}
