package net.easyUI.dto.common.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

public enum EnumWebCfgInputType implements EnumBase<String> {
	TEXT("text", "文本框"), // 文本框
	TEXTAREA("textarea", "大文本框"), // 大文本框
	MAP("map", "键值对输入框"), // 键值对输入框
	DATE("date", "日期输入框"), // 日期输入框
	DATETIME("datetime", "日期时间输入框"), // 日期时间输入框
	MULTIPART("multipart", "文件上传框"), // 文件上传框

	;
	private final String code;
	private final String desc;

	EnumWebCfgInputType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static EnumWebCfgInputType getByCode(String code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	public static EnumWebCfgInputType getRequiredByCode(String code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, EnumWebCfgInputType> objMap() {
		return EnumBaseUtils.toObjMap(values());
	}
}
