/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.service.common.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.easyUI.domain.common.Permission;
import net.easyUI.domain.common.Role;
import net.easyUI.domain.common.WebConfig;
import net.easyUI.domain.query.PermissionQuery;
import net.easyUI.domain.query.RoleQuery;
import net.easyUI.domain.query.WebConfigQuery;
import net.easyUI.dto.common.ConstantDTO;
import net.easyUI.dto.common.enums.EnumWebCfgCacheType;
import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.manager.common.PermissionManager;
import net.easyUI.manager.common.RoleManager;
import net.easyUI.manager.common.WebConfigManager;
import net.easyUI.service.common.RoleService;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;
import net.easyUI.common.dto.enums.EnumServiceError;
import net.easyUI.common.service.BasePOJOService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl extends BasePOJOService implements RoleService {

	@Autowired
	private WebConfigManager webConfigManager;
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private PermissionManager permissionManager;
	protected static Log logger = LogFactory.getLog(RoleServiceImpl.class);

	public ServiceResult<DwzPage<Role>> pageQuery(ServiceRequest serseq) {
		ServiceResult<DwzPage<Role>> serres = new ServiceResult<DwzPage<Role>>();
		// TODO 预处理
		try {
			DwzPage<Role> page = roleManager.pageQuery((RoleQuery) serseq
					.getResDto());
			if (page == null) {
				serres.setErrorNO(EnumServiceError.NOT_FIND.getCode());
				serres.setErrorInfo(EnumServiceError.NOT_FIND.msgId());
			} else {
				serres.setDataObj(page);
			}
		} catch (Exception e) {
			serres.setErrorNO(EnumServiceError.EXCEPTION.getCode());
			serres.setErrorInfo(EnumServiceError.EXCEPTION.msgId());
			logger.error("", e);
		}
		return serres;
	}

	public ServiceResult<Role> save(ServiceRequest serseq) {
		// TODO 预处理
		Role role = null;
		ServiceResult<Role> sr = new ServiceResult<Role>();
		try {
			role = roleManager.save((Role) serseq.getResDto());

			// 添加/保存 扩展属性表
			List<WebConfig> roleMetaList = role.getRoleMetas();
			WebConfigQuery webCfgRmQuery = new WebConfigQuery();
			List<String> rmKeyList = new ArrayList<String>();
			for (WebConfig roleMeta : roleMetaList) {
				roleMeta.setCfgGroup(EnumWebCfgGroup.ROLEMETA.getCode());
				roleMeta.setCacheType(EnumWebCfgCacheType.RAM.getCode());
				roleMeta.setCfgName(role.getRoleKey() + "_"
						+ roleMeta.getCfgName());
				rmKeyList.add(roleMeta.getCfgName());
			}
			webCfgRmQuery.setCfgName_in(rmKeyList);
			webConfigManager.remove(webCfgRmQuery);
			webConfigManager.saveBatch(roleMetaList);

		} catch (Exception e) {
			sr.setErrorNO(300);
			sr.setErrorInfo("service.exception");
			sr.setMsgArgs(e.getMessage());
			logger.error("", e);
		}
		if (role != null && role.getId() != null)
			sr.setErrorNO(null);
		else {
			sr.setErrorNO(300);
			sr.setErrorInfo("add.error");
		}
		return sr;
	}

	public ServiceResult<Integer> update(ServiceRequest serseq) {
		// TODO 取原数据对象

		// TODO 预处理
		// TODO 分类变化修改
		// 保存修改
		Integer count = 0;
		ServiceResult<Integer> sr = new ServiceResult<Integer>();
		Role role = (Role) serseq.getResDto();
		try {
			count = roleManager.update((Role) serseq.getResDto());

			// 添加/保存 扩展属性表
			List<WebConfig> roleMetaList = role.getRoleMetas();
			WebConfigQuery webCfgRmQuery = new WebConfigQuery();
			List<String> rmKeyList = new ArrayList<String>();
			for (WebConfig roleMeta : roleMetaList) {
				roleMeta.setCfgGroup(EnumWebCfgGroup.ROLEMETA.getCode());
				roleMeta.setCacheType(EnumWebCfgCacheType.RAM.getCode());
				roleMeta.setCfgName(role.getRoleKey() + "_"
						+ roleMeta.getCfgName());
				rmKeyList.add(roleMeta.getCfgName());
			}
			webCfgRmQuery.setCfgName_in(rmKeyList);
			webConfigManager.remove(webCfgRmQuery);
			webConfigManager.saveBatch(roleMetaList);

		} catch (Exception e) {
			sr.setErrorNO(300);
			sr.setErrorInfo("service.exception");
			sr.setMsgArgs(e.getMessage());
			logger.error("", e);
		}
		if (count != null && count > 0)
			sr.setErrorNO(null);
		else {
			sr.setErrorNO(300);
			sr.setErrorInfo("update.error");
		}
		return sr;
	}

	public ServiceResult<Role> queryOne(ServiceRequest serseq) {
		// TODO 预处理
		Role role = roleManager.queryOneFullFK((Long) serseq.getResDto());
		if (role != null) {
			// 查询所有的子属性集，以供一次性填写设置
			WebConfigQuery webConfigQuery = new WebConfigQuery();
			webConfigQuery.setCfgGroup(EnumWebCfgGroup.ROLEMETA.getCode());
			webConfigQuery.setCfgName_bw(role.getRoleKey() + "_");
			role.setRoleMetas(webConfigManager.listQuery(webConfigQuery));
		}

		// 查询这个角色下的权限集。
		if (role != null && CollectionUtils.isNotEmpty(role.getRoleMetas())) {
			PermissionQuery pQuery = new PermissionQuery();
			List<String> permissionKey_in = new ArrayList<String>();
			for (WebConfig roleMeta : role.getRoleMetas()) {
				if (roleMeta.getCfgName().endsWith(
						"_" + ConstantDTO.roleMeta_Key_PERMISSIONKEY)) {
					// 拆分角色权限关联集.如果角色有多个权限,则值记录多个权限的KEY,之间用英文逗号分隔.
					String[] permKeyArray = roleMeta.getCfgValue().split(",");
					if (ArrayUtils.isEmpty(permKeyArray))
						continue;
					permissionKey_in = Arrays.asList(permKeyArray);
					break;
				}
			}
			if (CollectionUtils.isNotEmpty(permissionKey_in)) {
				pQuery.setPermissionKey_in(permissionKey_in);
				pQuery.setEnabled_ne("0");
				List<Permission> permissions = permissionManager
						.listQuery(pQuery);
				role.setPermissions(permissions);
			}
		}
		return new ServiceResult<Role>(role);
	}

	public ServiceResult<Integer> remove(ServiceRequest serseq) {
		Integer count = 0;
		// TODO 预处理
		RoleQuery query = (RoleQuery) serseq.getResDto();
		if (!query.isWhereNull())
			count = roleManager.remove(query);
		return new ServiceResult<Integer>(count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.easyUI.service.admin.RoleService#queryRoleFreePermission
	 * (net.easyUI.common.dto.ServiceRequest)
	 */

	public ServiceResult<List<Permission>> queryRoleFreePermission(
			ServiceRequest serviceRequest) {
		Role role = (Role) serviceRequest.getResDto();
		// TODO Auto-generated method stub
		PermissionQuery pQuery = new PermissionQuery();
		List<Long> id_ni = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(role.getPermissions())) {
			for (Permission permission : role.getPermissions()) {
				id_ni.add(permission.getId());
			}
			pQuery.setId_ni(id_ni);
		}
		pQuery.setEnabled_ne("0");
		List<Permission> pList = permissionManager.listQuery(pQuery);
		return new ServiceResult<List<Permission>>(pList);
	}

}
