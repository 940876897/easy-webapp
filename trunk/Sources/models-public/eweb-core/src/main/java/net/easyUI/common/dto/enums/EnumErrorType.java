package net.easyUI.common.dto.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;


/**
 * 访问网页时发生错误的类型,跳转的错误页面就是由EnumErrorType+EnumPageType组成
 * 
 * <pre>
 *      登录页面(login),
 *      无权限页面(permission),
 *      错误异常页面(error)
 * </pre>
 */
public enum EnumErrorType implements EnumBase<String> {
	LOGIN("login", "登录页面"), PERMISSION("permission", "无权限页面"), ERROR("error",
			"错误异常页面");

	private final String code;
	private final String desc;

	EnumErrorType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static EnumErrorType getByCode(String code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	public static EnumErrorType getRequiredByCode(String code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}
}
