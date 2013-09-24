package net.easyUI.dto.common.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

public enum EnumYesNoShor implements EnumBase<Integer> {
	YES(1, "是"), NO(0, "否");

	private final Integer code;
	private final String desc;

	EnumYesNoShor(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static EnumYesNoShor getByCode(Integer code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	public static EnumYesNoShor getRequiredByCode(Integer code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<Integer, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<Integer, EnumYesNoShor> objMap() {
		return EnumBaseUtils.toObjMap(values());
	}
}
