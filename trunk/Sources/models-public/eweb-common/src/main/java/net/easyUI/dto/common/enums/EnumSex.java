package net.easyUI.dto.common.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

public enum EnumSex implements EnumBase<String> {
	M(new String("m"), "男"), F(new String("f"), "女");

	private final String code;
	private final String desc;

	EnumSex(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	/**
	 * 根据code得到Enum,找不到则返回null
	 */
	public static EnumSex getByCode(String code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	/**
	 * 根据code得到Enum,找不到则抛异常
	 */
	public static EnumSex getRequiredByCode(String code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, EnumSex> objMap() {
		return EnumBaseUtils.toObjMap(values());
	}
}
