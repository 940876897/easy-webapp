
package net.easyUI.dto.common.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

public enum EnumSexCalled implements EnumBase<String>{
	SIR(new String("m"),"先生"),
	LADY(new String("f"),"女士")
	;
	
	private final String code;
	private final String desc;
	EnumSexCalled(String code,String desc) {
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
	*根据code得到Enum,找不到则返回null
	*/
	public static EnumSexCalled getByCode(String code) {
		return EnumBaseUtils.getByCode(code, values());
	}
	
	/**
	*根据code得到Enum,找不到则抛异常
	*/
	public static EnumSexCalled getRequiredByCode(String code) throws IllegalArgumentException {
		return EnumBaseUtils.getRequiredByCode(code, values());
	}
	
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toMap() {
	    return EnumBaseUtils.toMap(values());
	}	

    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, EnumSexCalled>  objMap() {
        return EnumBaseUtils.toObjMap(values());
    }
}
