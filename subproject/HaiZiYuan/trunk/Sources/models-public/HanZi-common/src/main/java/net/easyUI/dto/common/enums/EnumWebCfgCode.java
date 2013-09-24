package net.easyUI.dto.common.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

/**
 * 系统配置、字典、code的枚举。主要是开发中使用，Code不会发生改变的，可以入到这个枚举中，以方便使用。
 * 
 * @author busfly
 * 
 */
public enum EnumWebCfgCode implements EnumBase<String> {
	WEBSITE("webSite", "网站域名")// 网站域名
	, WEBSUBTITLE("webSubTitle", "网站小标题") // 网站小标题
	, WEBTITLE("webTitle", "网站名称")// 网站名称
	, COMPANY_NAME("CompanyName", "公司名称")// 公司名称
	, COMPANY_ADDRESS("CompanyAddress", "公司地址")// 公司地址
	, COMPANY_TEL("CompanyTel", "公司联系电话")// 公司联系电话
	, POST_TYPE("postType", "Post频道配置")// Post频道配置
	, CATEGORY("category", "分类")// 分类
	;
	private final String code;
	private final String desc;

	EnumWebCfgCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static EnumWebCfgCode getByCode(String code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	public static EnumWebCfgCode getRequiredByCode(String code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, EnumWebCfgCode> objMap() {
		return EnumBaseUtils.toObjMap(values());
	}
}
