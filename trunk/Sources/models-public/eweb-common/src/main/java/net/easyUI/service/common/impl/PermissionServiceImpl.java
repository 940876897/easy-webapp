/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import net.easyUI.domain.common.Permission;
import net.easyUI.domain.common.WebConfig;
import net.easyUI.domain.query.PermissionQuery;
import net.easyUI.domain.query.WebConfigQuery;
import net.easyUI.dto.common.enums.EnumWebCfgCacheType;
import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.manager.common.PermissionManager;
import net.easyUI.manager.common.WebConfigManager;
import net.easyUI.service.common.PermissionService;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;
import net.easyUI.common.dto.enums.EnumServiceError;
import net.easyUI.common.service.BasePOJOService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionServiceImpl extends BasePOJOService implements
		PermissionService {

	@Autowired
	private WebConfigManager webConfigManager;
	@Autowired
	private PermissionManager permissionManager;
	protected static Log logger = LogFactory
			.getLog(PermissionServiceImpl.class);

	public ServiceResult<List<Permission>> queryAll(ServiceRequest serseq) {
		ServiceResult<List<Permission>> serres = new ServiceResult<List<Permission>>();
		// TODO 预处理
		try {
			List<Permission> list = permissionManager
					.listQuery((PermissionQuery) serseq.getResDto());
			if (CollectionUtils.isEmpty(list)) {
				serres.setErrorNO(EnumServiceError.NOT_FIND.getCode());
				serres.setErrorInfo(EnumServiceError.NOT_FIND.msgId());
			} else {
				serres.setDataObj(list);
			}
		} catch (Exception e) {
			serres.setErrorNO(EnumServiceError.EXCEPTION.getCode());
			serres.setErrorInfo(EnumServiceError.EXCEPTION.msgId());
			logger.error("", e);
		}
		return serres;
	}

	public ServiceResult<DwzPage<Permission>> pageQuery(ServiceRequest serseq) {
		ServiceResult<DwzPage<Permission>> serres = new ServiceResult<DwzPage<Permission>>();
		// TODO 预处理
		try {
			DwzPage<Permission> page = permissionManager
					.pageQuery((PermissionQuery) serseq.getResDto());
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

	public ServiceResult<Permission> save(ServiceRequest serseq) {
		// TODO 预处理
		Permission permission = null;
		ServiceResult<Permission> sr = new ServiceResult<Permission>();
		try {
			// 添加权限表
			permission = permissionManager
					.save((Permission) serseq.getResDto());
			// 添加/保存 权限扩展属性表
			List<WebConfig> pmList = permission.getPermissionMetas();
			WebConfigQuery webCfgRmQuery = new WebConfigQuery();
			List<String> pmKeyList = new ArrayList<String>();
			for (WebConfig pm : pmList) {
				pm.setCfgGroup(EnumWebCfgGroup.PERMISSIONMETA.getCode());
				pm.setCacheType(EnumWebCfgCacheType.RAM.getCode());
				pm.setCfgName(permission.getPermissionKey() + "_"
						+ pm.getCfgName());
				pmKeyList.add(pm.getCfgName());
			}
			webCfgRmQuery.setCfgName_in(pmKeyList);
			webConfigManager.remove(webCfgRmQuery);
			webConfigManager.saveBatch(pmList);
		} catch (Exception e) {
			sr.setErrorNO(300);
			sr.setErrorInfo("service.exception");
			sr.setMsgArgs(e.getMessage());
			logger.error("", e);
			return sr;
		}
		if (permission != null && permission.getId() != null)
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
		Permission permission = (Permission) serseq.getResDto();
		try {
			// 修改权限表
			count = permissionManager.update(permission);
			// 修改权限扩展表
			// 添加/保存 权限扩展属性表
			List<WebConfig> pmList = permission.getPermissionMetas();
			WebConfigQuery webCfgRmQuery = new WebConfigQuery();
			List<String> pmKeyList = new ArrayList<String>();
			for (WebConfig pm : pmList) {
				pm.setCfgGroup(EnumWebCfgGroup.PERMISSIONMETA.getCode());
				pm.setCacheType(EnumWebCfgCacheType.RAM.getCode());
				pm.setCfgName(permission.getPermissionKey() + "_"
						+ pm.getCfgName());
				pmKeyList.add(pm.getCfgName());
			}
			webCfgRmQuery.setCfgName_in(pmKeyList);
			webConfigManager.remove(webCfgRmQuery);
			webConfigManager.saveBatch(pmList);

		} catch (Exception e) {
			sr.setErrorNO(300);
			sr.setErrorInfo("service.exception");
			sr.setMsgArgs(e.getMessage());
			logger.error("", e);
			return sr;
		}
		if (count != null && count > 0)
			sr.setErrorNO(null);
		else {
			sr.setErrorNO(300);
			sr.setErrorInfo("update.error");
		}
		return sr;
	}

	public ServiceResult<Permission> queryOne(ServiceRequest serseq) {
		// TODO 预处理

		Permission obj = permissionManager.queryOneFullFK((Long) serseq
				.getResDto());
		if (obj != null) {
			// 查询所有的子属性集，以供一次性填写设置
			WebConfigQuery webConfigQuery = new WebConfigQuery();
			webConfigQuery
					.setCfgGroup(EnumWebCfgGroup.PERMISSIONMETA.getCode());
			webConfigQuery.setCfgName_bw(obj.getPermissionKey() + "_");
			obj.setPermissionMetas(webConfigManager.listQuery(webConfigQuery));
		}
		return new ServiceResult<Permission>(obj);
	}

	public ServiceResult<Integer> remove(ServiceRequest serseq) {
		Integer count = 0;
		// TODO 预处理
		PermissionQuery query = (PermissionQuery) serseq.getResDto();
		List<Permission> rmPermissionList = permissionManager.listQuery(query);
		if (CollectionUtils.isEmpty(rmPermissionList))
			return new ServiceResult<Integer>(0);

		// TODO 先删除扩展属性，注意，可能没在同一个事务中，可能会出问题。
		for (Permission pm : rmPermissionList) {
			if (StringUtils.isBlank(pm.getPermissionKey()))
				continue;
			WebConfigQuery cfgQuery = new WebConfigQuery();
			cfgQuery.setCfgGroup(EnumWebCfgGroup.PERMISSIONMETA.getCode());
			cfgQuery.setCfgName_bw(pm.getPermissionKey() + "_");
			webConfigManager.remove(cfgQuery);
		}
		count = permissionManager.remove(query);
		return new ServiceResult<Integer>(count);
	}

}
