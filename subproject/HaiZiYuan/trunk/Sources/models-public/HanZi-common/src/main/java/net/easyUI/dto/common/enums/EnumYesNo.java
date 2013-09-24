package net.easyUI.dto.common.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

public enum EnumYesNo implements EnumBase<String> {
	YES(new String("yes"), "是"), NO(new String("no"), "否");

	private final String code;
	private final String desc;

	EnumYesNo(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static EnumYesNo getByCode(String code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	public static EnumYesNo getRequiredByCode(String code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, EnumYesNo> objMap() {
		return EnumBaseUtils.toObjMap(values());
	}
}
