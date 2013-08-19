package net.easyUI.dto.common.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

/**
 * 系统配置表数据的缓存方式,如果是RAM,表示启动服务时,就加载到内存中,之后就从内存中取数据.
 * 
 * @author busfly
 * 
 */
public enum EnumWebCfgCacheType implements EnumBase<String> {
    RAM("ram", "缓存到内存")// 
    , NONE("none", "不缓存") // 
    ;
    private final String code;
    private final String desc;

    EnumWebCfgCacheType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static EnumWebCfgCacheType getByCode(String code) {
        return EnumBaseUtils.getByCode(code, values());
    }

    public static EnumWebCfgCacheType getRequiredByCode(String code)
                                                                    throws IllegalArgumentException {
        return EnumBaseUtils.getRequiredByCode(code, values());
    }

    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> toMap() {
        return EnumBaseUtils.toMap(values());
    }

    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, EnumWebCfgCacheType> objMap() {
        return EnumBaseUtils.toObjMap(values());
    }
}
