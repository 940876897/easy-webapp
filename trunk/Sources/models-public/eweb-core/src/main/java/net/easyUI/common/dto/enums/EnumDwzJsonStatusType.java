package net.easyUI.common.dto.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

/**
 * 
 * <pre>
 * 	form提交后返回json数据结构
 * 	statusCode[200]=DWZ.statusCode.ok表示操作成功, 做页面跳转等操作. 
 * 	statusCode[300]=DWZ.statusCode.error表示操作失败, 提示错误原因. 
 * 	statusCode[301]=DWZ.statusCode.timeout表示session超时，下次点击时跳转到DWZ.loginUrl
 * 	{"statusCode":"200", "message":"操作成功", "navTabId":"navNewsLi", "forwardUrl":"", "callbackType":"closeCurrent"}
 * 	{"statusCode":"300", "message":"操作失败"}
 * 	{"statusCode":"301", "message":"会话超时"}
 * </pre>
 */
public enum EnumDwzJsonStatusType implements EnumBase<String> {
	/** 表示操作成功, 做页面跳转等操作. */
	OK("200", "操作成功"),
	/** 表示操作失败, 提示错误原因 */
	ERROR("300", "操作失败"),
	/** 表示session超时，下次点击时跳转到DWZ.loginUrl */
	TIMEOUT("301", "登录已过期");

	private final String code;
	private final String desc;

	EnumDwzJsonStatusType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static EnumDwzJsonStatusType getByCode(String code) {
		return EnumBaseUtils.getByCode(code, values());
	}

	public EnumDwzJsonStatusType getRequiredByCode(String code)
			throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toMap() {
		return EnumBaseUtils.toMap(values());
	}
}
