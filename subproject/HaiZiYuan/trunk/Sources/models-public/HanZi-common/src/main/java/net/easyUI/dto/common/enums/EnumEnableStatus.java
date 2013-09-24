package net.easyUI.dto.common.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

public enum EnumEnableStatus implements EnumBase<Integer> {
	ENABLED(new Integer("1"), "有效")// 有效
	, WAIT(new Integer("0"), "待查")// 待查
	, DISABLED(new Integer("-1"), "禁用")// 禁用
	;

	private final Integer code;
	private final String desc;

	EnumEnableStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static EnumEnableStatus getByCode(Integer code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	public static EnumEnableStatus getRequiredByCode(Integer code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<Integer, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toStrMap() {
		return EnumBaseUtils.toStrMap(values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, EnumEnableStatus> objMap() {
		return EnumBaseUtils.toObjMap(values());
	}
}
