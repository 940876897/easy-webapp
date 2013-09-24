package net.easyUI.common.web.action;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.easyUI.common.web.propertyEditorSupport.CustomDateTimeEditor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class BaseAction {

	public Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 演示如何直接在action中注入配置文件中设置的值
	 */
	@Value("${system.devMode}")
	protected boolean devMode=true;

	protected Locale thisLocale = Locale.CHINA;
	@Autowired
	protected MessageSource messageSource;

	/**
	 * 初始化绑定
	 * 
	 * @param binder
	 */
	@InitBinder
	protected final void initBinderInternal(WebDataBinder binder) {

		registerDefaultCustomDateEditor(binder);
		registerDefaultCustomNumberEditor(binder);
		initBinder(binder);
	}

	private void registerDefaultCustomNumberEditor(WebDataBinder binder) {

		// 注册双精度数字格式化类型: #0.00
		NumberFormat numberFormat = new DecimalFormat("#0.00");
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(
				Double.class, numberFormat, true));
	}

	protected void registerDefaultCustomDateEditor(WebDataBinder binder) {

		// 注册默认的日期格式化类型: yyyy-MM-dd yyyy-MM-dd HH:mm:ss
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateTimeEditor(
				dateFormat, timeFormat, true));
		// binder.registerCustomEditor(Date.class, new CustomDateEditor(
		// dateFormat, true));
	}

	/**
	 * 提供子类初始化表单, 子类如果要调用请重写该方法
	 * 
	 * @param binder
	 */
	protected void initBinder(WebDataBinder binder) {
	}

	protected String redirectWebUrl(String webUrl) {
		return "redirect:" + webUrl;
	}

	protected String redirect(String url) {
		return "redirect:" + url;
	}

	protected String redirectLogin() {
		return redirect("/loginPage.htm");
	}

	protected String redirectAdminIndex() {
		return redirect("/admin/index.htm");
	}

	protected String redirectUserIndex() {
		return redirect("/user/index.htm");
	}

	protected String redirectIndex() {
		return redirect("/index.htm");
	}

	protected String success(ModelMap model) {
		return "success";
	}

	protected String error(ModelMap model) {
		return "error";
	}

	protected String success(Model model, String code, Object... args) {
		// protected String success(Model model, String code, String... args) {
		String message = messageSource.getMessage(code, args, thisLocale);
		model.addAttribute("message", message);
		return "success";
	}

	protected String success(ModelMap model, String code, Object... args) {
		// protected String success(ModelMap model, String code, String... args)
		// {
		String message = messageSource.getMessage(code, args, thisLocale);
		model.addAttribute("message", message);
		return "success";
	}

	protected String error(Model model, String code, Object... args) {
		// protected String error(Model model, String code, String... args) {
		String message = messageSource.getMessage(code, args, thisLocale);
		model.addAttribute("message", message);
		return "error";
	}

	protected String error(ModelMap model, String code, Object... args) {
		// protected String error(ModelMap model, String code, String... args) {
		String message = messageSource.getMessage(code, args, thisLocale);
		model.addAttribute("message", message);
		return "error";
	}

	public Locale getThisLocale() {
		return thisLocale;
	}

	public void setThisLocale(Locale thisLocale) {
		this.thisLocale = thisLocale;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

}
