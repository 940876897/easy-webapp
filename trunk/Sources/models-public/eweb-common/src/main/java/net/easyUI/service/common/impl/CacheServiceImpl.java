/**
 * 
 */
package net.easyUI.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.easyUI.domain.common.Permission;
import net.easyUI.domain.common.Role;
import net.easyUI.domain.common.WebConfig;
import net.easyUI.domain.query.PermissionQuery;
import net.easyUI.domain.query.RoleQuery;
import net.easyUI.domain.query.WebConfigQuery;
import net.easyUI.dto.common.CacheDTO;
import net.easyUI.dto.common.ConstantDTO;
import net.easyUI.dto.common.enums.EnumWebCfgCacheType;
import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.manager.common.PermissionManager;
import net.easyUI.manager.common.RoleManager;
import net.easyUI.manager.common.WebConfigManager;
import net.easyUI.service.common.CacheService;
import net.easyUI.common.dto.ServiceResult;
import net.easyUI.common.dto.enums.EnumServiceError;
import net.easyUI.common.service.BasePOJOService;
import net.easyUI.common.util.DateUtil;
import net.easyUI.common.util.cache.CompactCache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author busfly
 * 
 */
@SuppressWarnings({ "rawtypes", "restriction" })
@Service("cacheService")
public class CacheServiceImpl extends BasePOJOService implements CacheService {

	@Autowired
	private RoleManager roleManager;
	@Autowired
	private PermissionManager permissionManager;
	@Autowired
	private WebConfigManager webConfigManager;
	@Autowired
	private CompactCache velocityViewCache;

	// init-method
	@PostConstruct
	public ServiceResult<Map<String, String>> reloadAllCache() {
		ServiceResult<Map<String, String>> sr = new ServiceResult<Map<String, String>>();
		Map<String, String> runStatus = new HashMap<String, String>();
		int scount = 0;
		int fcount = 0;
		// velocityViewCache
		velocityViewCache.clean();
		// initWebConfig
		ServiceResult<String> srWebConfig = reloadWebConfig();
		if (srWebConfig.getErrorNO() == null
				|| srWebConfig.getErrorNO().equals(
						EnumServiceError.SUCCESS.getCode()))
			scount++;
		else
			fcount++;
		runStatus.put("initWebConfig", srWebConfig.getDataObj());

		// initRolePermission
		ServiceResult<String> srRolePermission = reloadRolePermission();
		if (srRolePermission.getErrorNO() == null
				|| srRolePermission.getErrorNO().equals(
						EnumServiceError.SUCCESS.getCode()))
			scount++;
		else
			fcount++;
		runStatus.put("initRolePermission", srRolePermission.getDataObj());

		if (scount > 0 && fcount == 0) {
			sr.setErrorNO(EnumServiceError.SUCCESS.getCode());
			sr.setErrorInfo(EnumServiceError.SUCCESS.msgId());
		} else if (scount > 0 && fcount > 0) {
			sr.setErrorNO(EnumServiceError.SOMESUCCESS.getCode());
			sr.setErrorInfo(EnumServiceError.SOMESUCCESS.msgId());
		} else if (scount == 0 && fcount > 0) {
			sr.setErrorNO(EnumServiceError.FAILED.getCode());
			sr.setErrorInfo(EnumServiceError.FAILED.msgId());
		} else {
			sr.setErrorNO(EnumServiceError.NODATA.getCode());
			sr.setErrorInfo(EnumServiceError.NODATA.msgId());
		}
		sr.setDataObj(runStatus);
		return sr;

	}

	private ServiceResult<String> reloadRolePermission() {
		ServiceResult<String> sr = new ServiceResult<String>();
		CacheDTO.permissionList.clear();
		CacheDTO.rolePermissionMap.clear();
		CacheDTO.roleMap.clear();
		// 1.有效的权限设置列表(Enabled=1或者Enabled没有设置)
		PermissionQuery permissionQuery = new PermissionQuery();
		permissionQuery
				.setEnabled_ne(ConstantDTO.permissionMeta_Value_NOT_ENABLED);
		List<Permission> permissionEnabledList = permissionManager
				.listQuery(permissionQuery);
		CacheDTO.permissionList.addAll(permissionEnabledList);
		// 2. 查询角色权限关联RoleMeta
		WebConfigQuery rolePermQuery = new WebConfigQuery();
		rolePermQuery.setCfgGroup(EnumWebCfgGroup.ROLEMETA.getCode());
		// 查全部角色与权限的关联,所以,CfgName使用右模糊.角色权限关联,如果角色有多个权限,则值记录多个权限的KEY,之间用英文逗号分隔.
		rolePermQuery.setCfgName_ew("_"
				+ ConstantDTO.roleMeta_Key_PERMISSIONKEY);
		List<WebConfig> rolePermList = webConfigManager
				.listQuery(rolePermQuery);
		// 3. 查询有效的角色列表(Enabled=1或者Enabled没有设置)
		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setEnabled_ne(ConstantDTO.roleMeta_Value_NOT_ENABLED);
		List<Role> roleEnableList = roleManager.listQuery(roleQuery);
		// 4. 将有效的角色放入缓存中CacheDTO.rolePermissionMap
		if (CollectionUtils.isNotEmpty(roleEnableList)) {
			// 将权限与角色集成,
			for (Role roleTmp : roleEnableList) {
				List<Permission> rolePermissionList = new ArrayList<Permission>();

				if (CollectionUtils.isNotEmpty(rolePermList))
					for (WebConfig rolePerm : rolePermList) {
						if (StringUtils.isNotBlank(rolePerm.getCfgValue())
								&& rolePerm
										.getCfgName()
										.equals(roleTmp.getRoleKey()
												+ "_"
												+ ConstantDTO.roleMeta_Key_PERMISSIONKEY)) {
							// 拆分角色权限关联集.如果角色有多个权限,则值记录多个权限的KEY,之间用英文逗号分隔.
							String[] permKeyArray = rolePerm.getCfgValue()
									.split(",");
							if (ArrayUtils.isEmpty(permKeyArray))
								continue;

							for (String permKey : permKeyArray) {
								for (Permission permissionTmp : permissionEnabledList) {
									if (permissionTmp.getPermissionKey()
											.equals(permKey.trim())) {
										rolePermissionList.add(permissionTmp);
									}
								}
							}
						}

					}
				CacheDTO.roleMap.put(roleTmp.getRoleKey(), roleTmp);
				CacheDTO.rolePermissionMap.put(roleTmp.getRoleKey(),
						rolePermissionList);
			}
		}

		// 成功
		sr.setErrorNO(EnumServiceError.SUCCESS.getCode());
		sr.setErrorInfo(EnumServiceError.SUCCESS.msgId());
		sr.setDataObj("Success->" + DateUtil.getTimeNow());

		return sr;
	}

	/**
	 * 只加载缓存类型为ram方式的配置.
	 * 
	 * @return
	 * @see net.easyUI.service.CacheService#reloadWebConfig()
	 */

	private ServiceResult<String> reloadWebConfig() {
		ServiceResult<String> sr = new ServiceResult<String>();
		WebConfigQuery query = new WebConfigQuery();
		query.setCacheType(EnumWebCfgCacheType.RAM.getCode());
		List<WebConfig> webConfigList = webConfigManager.listQuery(query);
		CacheDTO.clearCaches();
		for (WebConfig webConfig : webConfigList) {
			CacheDTO.webConfigMap.put(
					webConfig.getCfgGroup() + "_" + webConfig.getCfgName(),
					webConfig);
		}
		// 成功
		sr.setErrorNO(EnumServiceError.SUCCESS.getCode());
		sr.setErrorInfo(EnumServiceError.SUCCESS.msgId());
		sr.setDataObj("Success->" + DateUtil.getTimeNow());
		return sr;
	}

	public ServiceResult<String> reFlashWebConfig() {
		// TODO 缓存在本机上,不需要此方法,但是要注意集群部署时同步缓存需要开发功能来处理
		return new ServiceResult<String>();
	}

}
