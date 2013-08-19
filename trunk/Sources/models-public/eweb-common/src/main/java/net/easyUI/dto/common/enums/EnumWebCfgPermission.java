package net.easyUI.dto.common.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

/**
 * 字典数据管理权限
 * 
 */
public enum EnumWebCfgPermission implements EnumBase<String> {
	READ("R", "可见只读"), // 可见只读
	WRITE("W", "可见可写"), // 可见可写
	DELETE("D", "可见可删"), // 可见可删
	HIDE("H", "不可见"), // 不可见
	;
	private final String code;
	private final String desc;

	EnumWebCfgPermission(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static EnumWebCfgPermission getByCode(String code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	public static EnumWebCfgPermission getRequiredByCode(String code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, EnumWebCfgPermission> objMap() {
		return EnumBaseUtils.toObjMap(values());
	}
}
