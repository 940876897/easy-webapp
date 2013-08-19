package net.easyUI.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import net.easyUI.domain.common.WebConfig;
import net.easyUI.dto.common.CacheDTO;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

public class WebCacheUtils {
    /**
     * @return 返回缓存中,网站配置Map
     */
    public static Map<String, WebConfig> webConfig() {
        return CacheDTO.webConfigMap;
    }

    /**
     * 通过Cfg的Name查找缓存中的数据
     * @param webConfig_Group_Name 要查找的网站配置name, 由 group+"_"+name 组成.
     * @return 返回缓存中,网站配置Map中,Key为 webConfigName 的配置对象
     */
    public static WebConfig webConfig(String webConfig_Group_Name) {
        if (StringUtils.isBlank(webConfig_Group_Name) || MapUtils.isEmpty(CacheDTO.webConfigMap))
            return null;
        return CacheDTO.webConfigMap.get(webConfig_Group_Name);
    }

    /**
     * 通过Cfg的Name查找缓存中的数据的Value,以字符串形式返回.
     * @param webConfig_Group_Name 要查找的网站配置name, 由 group+"_"+name 组成.
     * @return 返回缓存中,网站配置Map中,Key为 webConfigName 的配置对象的Value值
     */
    public static String webConfigValue(String webConfig_Group_Name) {
        if (StringUtils.isBlank(webConfig_Group_Name) || MapUtils.isEmpty(CacheDTO.webConfigMap))
            return null;
        WebConfig cfg = CacheDTO.webConfigMap.get(webConfig_Group_Name);
        if (cfg == null)
            return null;
        return cfg.getCfgValue();
    }

    /**通过Cfg的Name查找缓存中的数据的Value,并将Value解析成Json格式的List返回.
     * @param webConfig_Group_Name 要查找的网站配置name, 由 group+"_"+name 组成.
     */
    @SuppressWarnings("rawtypes")
    public static Collection webConfigValueJsonList(String webConfig_Group_Name) {
        WebConfig webCfg = webConfig(webConfig_Group_Name);
        if (webCfg == null)
            return null;
        return webCfg.getCfgValueJsonList();
    }

    /**通过Cfg的Name查找缓存中的数据的Value,并将Value解析成Json格式的Map返回.
     * @param webConfig_Group_Name 要查找的网站配置name, 由 group+"_"+name 组成.
     */
    @SuppressWarnings("rawtypes")
    public static Map webConfigValueJsonMap(String webConfig_Group_Name) {
        WebConfig webCfg = webConfig(webConfig_Group_Name);
        if (webCfg == null)
            return null;
        return webCfg.getCfgValueJsonMap();
    }

    /**通过Cfg的Name查找缓存中的数据的Value,并将Value解析成Json格式的List或Map的Object返回.使用者需要预先知道其类型,再强制转换.不建议使用.
     * @param webConfig_Group_Name 要查找的网站配置name, 由 group+"_"+name 组成.
     */
    public static Object webConfigValueJson(String webConfig_Group_Name) {
        WebConfig webCfg = webConfig(webConfig_Group_Name);
        if (webCfg == null)
            return null;
        return webCfg.getCfgValueJson();
    }

    /**
     * 通过分组查询字典(缓存中的配置)列表，与使用ID的方式效率一样,不是很好,不太建议使用.
     * 
     * @param cfgGroup
     *            参见EnumWebCfgGroup.java
     * @return
     */
    public static Collection<WebConfig> webConfigByGroup(String cfgGroup) {
        if (StringUtils.isBlank(cfgGroup) || MapUtils.isEmpty(CacheDTO.webConfigMap))
            return null;
        Collection<WebConfig> cfgLs = new ArrayList<WebConfig>();
        for (WebConfig webConfig : CacheDTO.webConfigMap.values()) {
            if (cfgGroup.equals(webConfig.getCfgGroup())) {
                cfgLs.add(webConfig);
            }
        }
        return cfgLs;
    }

    /**
     * 通过Cfg的ID查找缓存中的数据,不建议采用,因为此方法效率不高,建议采用webConfig(webConfigName)方法
     * 
     * @param cfgId
     *            字典表ID
     * @return
     */
    public static WebConfig webConfigById(Long cfgId) {
        if (cfgId == null || MapUtils.isEmpty(CacheDTO.webConfigMap))
            return null;
        Collection<WebConfig> cfgCollection = CacheDTO.webConfigMap.values();
        for (WebConfig webConfig : cfgCollection) {
            if (cfgId.equals(webConfig.getId())) {
                return webConfig;
            }
        }
        return null;
    }

    /**
     * 通过字典表ID查询字典列表，
     * 
     * @param cfgId
     *            字典表ID
     * @return
     */
    public static String webConfigValueById(Long cfgId) {
        if (cfgId == null || MapUtils.isEmpty(CacheDTO.webConfigMap))
            return null;
        for (WebConfig webConfig : CacheDTO.webConfigMap.values()) {
            if (cfgId.equals(webConfig.getId())) {
                return webConfig.getCfgValue();
            }
        }
        return null;
    }
}
