package net.easyUI.web.action.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.easyUI.access.PageType;
import net.easyUI.domain.common.User;
import net.easyUI.dto.common.UserAgent;
import net.easyUI.service.common.CommonService;
import net.easyUI.service.common.UserService;
import net.easyUI.common.dto.DwzJson;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;
import net.easyUI.common.dto.enums.EnumDwzJsonCallbackType;
import net.easyUI.common.dto.enums.EnumDwzJsonStatusType;
import net.easyUI.common.dto.enums.EnumPageType;
import net.easyUI.common.dto.enums.EnumServiceError;
import net.easyUI.common.util.crypto.impl.Md5;
import net.easyUI.common.web.action.BaseAction;
import net.easyUI.common.web.cookyjar.Cookyjar;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginAction extends BaseAction {
	@Autowired
	private CommonService commonService;
	@Autowired
	private UserService userService;
	@Value("${cookie.lifeTime.imgCheckCode}")
	private Long imgCheckCodeLiveTime;

	/**
	 * 注入配置文件中的值,登录是否需要验证
	 */
	@Value("${login.imgCheckCode}")
	private boolean loginImgCheckCode;

	/**
	 * 修改密码处理(Ajax片段HTML代码)
	 */
	@RequestMapping(value = "/updatePwdJson")
	@PageType("JsonPage")
	@ResponseBody
	public DwzJson updatePwdJson(
			@RequestParam(value = "oldPassword", required = true) String oldPassword,
			@RequestParam(value = "newPassword", required = true) String newPassword,
			@RequestParam(value = "imgCheckCode", required = false) String imgCheckCode,
			Cookyjar cookyjar, UserAgent userAgent, ModelMap model) {

		User user = new User();
		user.setId(userAgent.getId());
		user.setNicename(oldPassword);// 原密码
		user.setPassword(newPassword);// 新密码暂时保存到这个字段。

		ServiceRequest sr = new ServiceRequest(userAgent);
		sr.setResDto(user);
		DwzJson dwzJson = new DwzJson();
		// 保存修改
		ServiceResult<Integer> result = userService.updatePwd(sr);
		if (result.getErrorNO() != null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					result.getErrorInfo(), result.getMsgArgs(),
					this.getThisLocale()));
		} else {
			dwzJson = new DwzJson("200", this.messageSource.getMessage(
					"operation.success", result.getMsgArgs(),
					this.getThisLocale()), "", "closeCurrent");
		}
		return dwzJson;
	}

	/**
	 * 登出,登出后,跳转到登录页面
	 */
	@RequestMapping(value = "/logout")
	@PageType("Page")
	public String userLogout(Cookyjar cookyjar, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		cookyjar.clean();
		return this.redirectLogin();
	}

	/**
	 * 登出,登出后,返回一个JSON.
	 */
	@RequestMapping(value = "/logoutJson")
	@PageType("JsonPage")
	public @ResponseBody
	DwzJson userLogoutJson(Cookyjar cookyjar) throws Exception {
		cookyjar.clean();
		DwzJson dwzJson = new DwzJson(EnumDwzJsonStatusType.OK.getCode(),
				EnumDwzJsonStatusType.OK.getDesc(), "",
				EnumDwzJsonCallbackType.FORWARD.getCode(), appServerBroker.get(
						"/loginPage.xhtml").toString());
		return dwzJson;
	}

	/**
	 * 打开登录页面(Ajax片段HTML代码)
	 * 
	 * @param actionType
	 *            登录Action方式?ajaxPage,page,defaultPage,jsonPage(默认)
	 */
	@RequestMapping(value = "/loginAjaxPage")
	@PageType("AjaxPage")
	public String loginAjaxPage(
			@RequestParam(value = "t", required = false) String actionType,
			@RequestParam(value = "returnurl", required = false) String returnurl,
			ModelMap model) {
		model.put("returnurl", returnurl);
		model.put("loginImgCheckCode", loginImgCheckCode);
		// if ("ajaxPage".equalsIgnoreCase(actionType))
		// model.put("action", appServerBroker.get("loginAjax.xhtml"));
		// else if ("page".equalsIgnoreCase(actionType))
		// model.put("action", appServerBroker.get("login.xhtml"));
		// else if ("defaultPage".equalsIgnoreCase(actionType))
		// model.put("action", appServerBroker.get("loginDefault.xhtml"));
		// else
		// model.put("action", appServerBroker.get("loginJson.xhtml"));
		return "errorPage/loginAjaxPage";
	}

	/**
	 * 登录处理(Ajax片段HTML代码)
	 * 
	 * @return 返回(Ajax片段HTML代码), 成功时,显示一个用户信息Widget片段,失败时返回Ajax登录片段
	 */
	@RequestMapping(value = "/loginAjax")
	@PageType("AjaxPage")
	public String loginAjax(
			@ModelAttribute("user") User user,
			@RequestParam(value = "imgCheckCode", required = false) String imgCheckCode,
			@RequestParam(value = "returnurl", required = false) String returnurl,
			Cookyjar cookyjar, ModelMap model) {

		ServiceResult<UserAgent> loginSR = checkLogin(user, imgCheckCode,
				cookyjar, loginImgCheckCode);
		if (loginSR.error()) {
			model.put("msg", this.messageSource.getMessage(
					loginSR.getErrorInfo(), null, this.getThisLocale()));
			model.put("user", user);
			model.put("returnurl", returnurl);
			return "errorPage/loginAjaxPage";
		} else {
			return "contain/loginOK";
		}
	}

	/**
	 * 打开登录页面(单独的完整登录页)
	 */
	@RequestMapping(value = "/loginPage")
	@PageType("Page")
	public String loginPage(
			@RequestParam(value = "returnurl", required = false) String returnurl,
			ModelMap model) {
		model.put("returnurl", returnurl);
		model.put("loginImgCheckCode", loginImgCheckCode);
		return "errorPage/loginPage";
	}

	/**
	 * 页面登录处理,登录后跳转,有来源页面的,跳转到来源页面,没有的,前台用户跳转到个人空间,后台用户跳转到后台管理
	 * 
	 * @param user
	 * @param imgCheckCode
	 * @param cookyjar
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	@PageType("Page")
	public String login(
			@ModelAttribute("user") User user,
			@RequestParam(value = "imgCheckCode", required = false) String imgCheckCode,
			@RequestParam(value = "returnurl", required = false) String returnurl,
			Cookyjar cookyjar, ModelMap model) {
		if (user == null
				|| (user.getLoginName() == null && user.getDisplayName() == null)) {
			return redirectLogin();
		}
		ServiceResult<UserAgent> loginSR = checkLogin(user, imgCheckCode,
				cookyjar, loginImgCheckCode);
		if (loginSR.error()) {
			model.put("msg", this.messageSource.getMessage(
					loginSR.getErrorInfo(), loginSR.getMsgArgs(),
					this.getThisLocale()));
			model.put("user", user);
			model.put("returnurl", returnurl);
			return "errorPage/loginPage";
		} else {
			if (StringUtils.isNotBlank(returnurl)) {
				try {
					// 将URL解码,再跳转
					String url = URLDecoder.decode(returnurl, "UTF-8");
					return this.redirectWebUrl(url);
				} catch (UnsupportedEncodingException e) {
				}
			}
			return redirect("/index.xhtml");
		}
	}

	/**
	 * 打开登录页面(默认通吃所有请求方式的登录)
	 */
	@RequestMapping(value = "/loginDefaultPage")
	@PageType("DefaultPage")
	public String loginDefaultPage(
			@RequestParam(value = "returnurl", required = false) String returnurl,
			ModelMap model) {
		model.put("returnurl", returnurl);
		model.put("loginImgCheckCode", loginImgCheckCode);
		return "errorPage/loginDefaultPage";
	}

	/**
	 * 登录处理
	 * 
	 * @param user
	 * @param imgCheckCode
	 * @param cookyjar
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loginDefault")
	@PageType("DefaultPage")
	public String loginDefault(
			@ModelAttribute("user") User user,
			@RequestParam(value = "imgCheckCode", required = false) String imgCheckCode,
			@RequestParam(value = "returnurl", required = false) String returnurl,
			Cookyjar cookyjar, ModelMap model) {
		if (user == null
				|| (user.getLoginName() == null && user.getDisplayName() == null)) {
			return redirectLogin();
		}
		ServiceResult<UserAgent> loginSR = checkLogin(user, imgCheckCode,
				cookyjar, loginImgCheckCode);
		if (loginSR.error()) {
			model.put("msg", this.messageSource.getMessage(
					loginSR.getErrorInfo(), loginSR.getMsgArgs(),
					this.getThisLocale()));
			model.put("user", user);
			model.put("returnurl", returnurl);
			return "errorPage/loginPage";
		} else {
			if (StringUtils.isNotBlank(returnurl)) {
				try {
					// 将URL解码,再跳转
					String url = URLDecoder.decode(returnurl, "UTF-8");
					return this.redirectWebUrl(url);
				} catch (UnsupportedEncodingException e) {
				}
			}
			return redirect("/index.xhtml");
		}
	}

	/**
	 * 打开登录页面(json代码)
	 * 
	 * @return 页面上为Json字符串,里面含有信息包括:账号字段名,密码字段名,验证码字段名,验证码图片URL,以及其它扩展内容
	 */
	@RequestMapping(value = "/loginJsonPage")
	@PageType("JsonPage")
	public String loginJsonPage(ModelMap model) {
		model.put("rand", Math.random());
		model.put("loginImgCheckCode", loginImgCheckCode);
		return "errorPage/loginJsonPage";
	}

	/**
	 * json登录处理(json代码)
	 * 
	 * @return JSON
	 */
	@RequestMapping(value = "/loginJson")
	@PageType("JsonPage")
	@ResponseBody
	public DwzJson loginJson(
			@ModelAttribute("user") User user,
			@RequestParam(value = "imgCheckCode", required = false) String imgCheckCode,
			@RequestParam(value = "returnurl", required = false) String returnurl,
			@RequestParam(required = false, defaultValue = "") String dwzId,
			Cookyjar cookyjar, ModelMap model) {

		ServiceResult<UserAgent> loginSR = checkLogin(user, imgCheckCode,
				cookyjar, loginImgCheckCode);
		DwzJson dwzJson = new DwzJson();
		if (loginSR.error()) {
			dwzJson = new DwzJson(EnumDwzJsonStatusType.ERROR.getCode(),
					this.messageSource.getMessage(loginSR.getErrorInfo(),
							loginSR.getMsgArgs(), this.getThisLocale()));
		} else {
			dwzJson = new DwzJson(EnumDwzJsonStatusType.OK.getCode(),
					this.messageSource.getMessage("operation.success",
							loginSR.getMsgArgs(), this.getThisLocale()), dwzId,
					EnumDwzJsonCallbackType.CLOSECURRENT.getCode(), returnurl);
		}
		return dwzJson;
	}

	/**
	 * 检查验证码(不区分大小写)是否正确,并删除缓存中的验证码.
	 * 
	 * @return 正确返回True,错误返回False
	 */
	private boolean checkImgCheckCode(String imgCheckCode, Cookyjar cookyjar) {
		String str = null;
		Long milliseconds = new Date().getTime();
		str = Md5.md5Str(imgCheckCode.toLowerCase() + milliseconds
				/ imgCheckCodeLiveTime);
		boolean t = (str != null && cookyjar != null && str.trim()
				.equalsIgnoreCase(cookyjar.get("imgCheckCode")));
		// 删除缓存中的验证码
		cookyjar.remove("imgCheckCode");
		return t;
	}

	/**
	 * 核验登录是否成功,user->userMeta(userId,key[roleKey],value[roleKey])->role->
	 * roleMeta(enabled||permissionKey)->Permission->PermissionMeta(enabled)
	 * 
	 * @param user
	 *            用户登录信息
	 * @param imgCheckCode
	 *            本次验证码
	 * @param cookyjar
	 *            缓存Cookie,正确的验证码就保存在这里.
	 * @param loginImgCheckCode
	 *            是否需要验证他的验证码
	 * @return
	 */
	private ServiceResult<UserAgent> checkLogin(User user, String imgCheckCode,
			Cookyjar cookyjar, boolean loginImgCheckCode) {
		ServiceResult<UserAgent> sr = new ServiceResult<UserAgent>();
		// 验证校验码
		if (loginImgCheckCode) {
			if (StringUtils.isBlank(imgCheckCode)) {
				sr.setErrorNO(EnumServiceError.FAILED.getCode());
				sr.setErrorInfo("input.required");
				sr.setMsgArgs(this.messageSource.getMessage("imgCheckCode",
						null, this.getThisLocale()));
				return sr;
			} else if (cookyjar == null
					|| !checkImgCheckCode(imgCheckCode, cookyjar)) {
				sr.setErrorNO(EnumServiceError.FAILED.getCode());
				sr.setErrorInfo("imgCheckCode.failed");
				return sr;
			}
		}

		ServiceRequest serReq = new ServiceRequest(user);
		// TODO 也可以在这里传入IP等其它信息,到Service中更新登录IP等信息.
		sr = commonService.checkLogin(serReq);
		if (sr.correct()) {
			// TODO 登录成功,将用户数据放入Cookie中.
			cookyjar.set(sr.getDataObj());
		}
		return sr;
	}
}
