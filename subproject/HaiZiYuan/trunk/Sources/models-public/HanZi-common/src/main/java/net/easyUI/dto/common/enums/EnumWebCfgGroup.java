package net.easyUI.dto.common.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

/**
 * 系统配置、字典、分组的枚举。
 * 
 * @author busfly
 * 
 */
public enum EnumWebCfgGroup implements EnumBase<String> {
	WEB_SETTING("webSetting", "网站设置"), // 网站设置
	PERMISSIONMETA("permissionMeta", "权限属性"), // 权限属性
	ROLEMETA("roleMeta", "角色属性"), // 权限属性
	USERMETA("userMeta", "用户属性"), // 用户属性
	I18N("i18n", "多国语言"), // 多国语言
	DBVMS("dbVMs", "VM模板"), // VM模板
	;

	private final String code;
	private final String desc;

	EnumWebCfgGroup(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static EnumWebCfgGroup getByCode(String code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	public static EnumWebCfgGroup getRequiredByCode(String code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, EnumWebCfgGroup> objMap() {
		return EnumBaseUtils.toObjMap(values());
	}
}
