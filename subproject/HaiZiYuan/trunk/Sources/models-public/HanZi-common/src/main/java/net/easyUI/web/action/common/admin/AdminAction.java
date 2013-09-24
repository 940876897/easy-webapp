package net.easyUI.web.action.common.admin;

import net.easyUI.access.PageType;
import net.easyUI.dto.common.UserAgent;
import net.easyUI.common.web.action.BaseAction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统后台功能操作
 */
@Controller
@RequestMapping("/admin")
public class AdminAction extends BaseAction {

	/**
	 * 表示登录用户就可访问
	 */
	@RequestMapping(value = "/index")
	@PageType("Page")
	public String index(UserAgent userAgent, ModelMap model) {
		// throw new UserAccessDeniedException();
		return "admin/index";
	}

	@RequestMapping(value = "/header")
	@PageType("AjaxPage")
	public String header(UserAgent userAgent, ModelMap model) {
		return "admin/header";
	}

	@RequestMapping(value = "/leftSidebar")
	@PageType("AjaxPage")
	public String leftMenu(UserAgent userAgent, ModelMap model) {
		return "admin/leftSidebar";
	}

	@RequestMapping(value = "/footer")
	@PageType("AjaxPage")
	public String footer(UserAgent userAgent, ModelMap model) {
		return "admin/footer";
	}

	@RequestMapping(value = "/dev-help")
	@PageType("AjaxPage")
	public String devHelpAjax(UserAgent userAgent, ModelMap model) {
		// model.put("pageType", EnumPageType.values());
		// model.put("enumSex", EnumSex.values());
		// model.put("enumSexValueMap", EnumSex.toMap());
		return "admin/dev-help";
	}

}
