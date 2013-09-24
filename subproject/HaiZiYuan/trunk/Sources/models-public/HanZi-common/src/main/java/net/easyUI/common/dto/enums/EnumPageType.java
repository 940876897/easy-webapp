package net.easyUI.common.dto.enums;

import java.util.LinkedHashMap;

import net.easyUI.common.domain.enums.EnumBase;
import net.easyUI.common.domain.enums.EnumBaseUtils;

/**
 * 访问的页面类型,同样也用于当访问页面出错时跳转的错误页面类型, 跳转的错误页面就是由EnumErrorType+EnumPageType组成
 * 
 * <pre>
 *      普通页面 (Page)
 *      AJAX页面(AjaxPage)
 *      Json页面(JsonPage)
 *      最大兼容页面 (DefaultPage)
 * </pre>
 */
public enum EnumPageType implements EnumBase<String> {
    REPONSE("Reponse", "Reponse输出"), //
    PAGE("Page", "普通页面"), //
    AJAXPAGE("AjaxPage", "AJAX页面"), //
    JSONPAGE("JsonPage", "Json页面"), //
    DEFAULTPAGE("DefaultPage", "default最大兼容页面");

    private final String code;
    private final String desc;

    EnumPageType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static EnumPageType getByCode(String code) {
        return EnumBaseUtils.getByCode(code, values());
    }

    public static EnumPageType getRequiredByCode(String code) throws IllegalArgumentException {
        return EnumBaseUtils.getRequiredByCode(code, values());
    }

    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> toMap() {
        return EnumBaseUtils.toMap(values());
    }
}
