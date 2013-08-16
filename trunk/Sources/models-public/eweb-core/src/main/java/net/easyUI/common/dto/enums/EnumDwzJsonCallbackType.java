package net.easyUI.common.dto.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

/**
 * forward在当前面板中跳转到forwardUrl,closeCurrent关闭当前面板或者dialog
 */
public enum EnumDwzJsonCallbackType implements EnumBase<String> {
	/** 不指定,默认. */
	DEFAULT("", "默认"),
	/** forward在当前面板中跳转到forwardUrl */
	FORWARD("forward", "在当前面板中跳转到forwardUrl"),
	/** 关闭当前面板或者dialog */
	CLOSECURRENT("closeCurrent", "关闭当前面板或者dialog");

	private final String code;
	private final String desc;

	EnumDwzJsonCallbackType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static EnumDwzJsonCallbackType getByCode(String code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	public EnumDwzJsonCallbackType getRequiredByCode(String code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}
}
