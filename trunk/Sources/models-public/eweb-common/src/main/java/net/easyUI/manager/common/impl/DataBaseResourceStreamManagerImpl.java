package net.easyUI.manager.common.impl;

import java.util.Collection;

import net.easyUI.Utils.WebCacheUtils;
import net.easyUI.domain.common.WebConfig;
import net.easyUI.dto.common.CacheDTO;
import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.common.web.velocity.runtime.resource.loader.DataBaseResourceStreamManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 主要提供给VM模板加载器从数据库读取模板时使用, 如果需要从数据库中读取模板, 请提供此接口的实现, 目前从数据库的缓存中读取,以提高读取速度.
 * 
 * @author yuancong
 *  用于EuResourceLoader从数据库中读取VM模板 
 */
@Service("dataBaseResourceStreamManager")
public class DataBaseResourceStreamManagerImpl implements
		DataBaseResourceStreamManager {
	private static Log log = LogFactory
			.getLog(DataBaseResourceStreamManagerImpl.class);

	public String getTemplateDataStr(String templateName) {
		String templateStr = null;
		try {
			// 如果说VM缓存为空,则将字典缓存中的VM放到专用的缓存中.
			if (MapUtils.isEmpty(CacheDTO.dbVMs)) {
				Collection<WebConfig> vmCfgList = WebCacheUtils
						.webConfigByGroup(EnumWebCfgGroup.DBVMS.getCode());
				if (CollectionUtils.isNotEmpty(vmCfgList)) {
					for (WebConfig webConfig : vmCfgList) {
						CacheDTO.dbVMs.put(webConfig.getCfgName(),
								webConfig.getCfgValue());
						// 不重复保留缓存数据,以免浪费内存.
						CacheDTO.webConfigMap.remove(webConfig.getCfgGroup()
								+ "_" + webConfig.getCfgName());
					}
				}
			}
			templateStr = CacheDTO.dbVMs.get(templateName);
		} catch (Exception ex) {
			log.error("读取数据库中的VM模板发生异常." + templateName, ex);
		}
		return templateStr;
	}

}
