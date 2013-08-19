package net.easyUI.dto.common.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

/**
 * 字典数据状态
 * 
 */
public enum EnumWebCfgStatus implements EnumBase<String>{
	VALID("valid", "有效"), //有效
	INVALID("invalid", "无效"),//无效
	;
	private final String code;
	private final String desc;

	EnumWebCfgStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static EnumWebCfgStatus getByCode(String code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	public static EnumWebCfgStatus getRequiredByCode(String code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, EnumWebCfgStatus> objMap() {
		return EnumBaseUtils.toObjMap(values());
	}
}
