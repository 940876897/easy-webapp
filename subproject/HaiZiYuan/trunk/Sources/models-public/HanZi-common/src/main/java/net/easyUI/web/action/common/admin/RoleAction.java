/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.web.action.common.admin;

import java.util.List;

import net.easyUI.access.PageType;
import net.easyUI.common.dto.Dwz;
import net.easyUI.common.dto.DwzJson;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;
import net.easyUI.common.dto.enums.EnumPageType;
import net.easyUI.common.web.action.BaseAction;
import net.easyUI.domain.common.Permission;
import net.easyUI.domain.common.Role;
import net.easyUI.domain.common.WebConfig;
import net.easyUI.domain.query.RoleQuery;
import net.easyUI.domain.query.WebConfigQuery;
import net.easyUI.dto.common.ConstantDTO;
import net.easyUI.dto.common.UserAgent;
import net.easyUI.dto.common.enums.EnumWebCfgCacheType;
import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.service.common.RoleService;
import net.easyUI.service.common.WebConfigService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/role")
public class RoleAction extends BaseAction {

	@Autowired
	private RoleService roleService;
	@Autowired
	private WebConfigService webConfigService;

	/**
	 * 查询 数据
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/indexAjax")
	public String roleIndexAjax(@ModelAttribute("query") RoleQuery query,
			UserAgent user, @ModelAttribute Dwz dwz, ModelMap model) {
		ServiceResult<DwzPage<Role>> result = roleService
				.pageQuery(new ServiceRequest(query));
		model.addAttribute("page", result.getDataObj());
		model.addAttribute("srs", result);
		model.addAttribute("query", query);
		model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		return "admin/role/indexAjax";
	}

	/**
	 * 进入添加页面
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/addAjax")
	public String roleAddAjax(UserAgent user, @ModelAttribute Dwz dwz,
			ModelMap model) {
		Role role = new Role();
		model.put("role", role);
		model.put("operType", "add");
		model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		return "admin/role/objAjax";
	}

	/**
	 * 删除一个角色扩展属性
	 */
	@Transactional
	@PageType("JsonPage")
	@RequestMapping(value = "/roleMetaDelJson/{id}")
	@ResponseBody
	public DwzJson roleMetaDel(@PathVariable("id") Long id,
			@ModelAttribute Dwz dwz, UserAgent user, ModelMap model) {
		DwzJson dwzJson;
		if (id == null || id < 0) {
			dwzJson = new DwzJson("300", this.getMessageSource().getMessage(
					"delete.error.parameter.ids.null", null,
					this.getThisLocale()));
			return dwzJson;
		}
		WebConfigQuery query = new WebConfigQuery();
		query.setId(id);
		ServiceResult<Integer> result = webConfigService
				.remove(new ServiceRequest(query, user));
		if (result.getErrorNO() != null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					result.getErrorInfo(), result.getMsgArgs(),
					this.getThisLocale()));
		} else {
			if (result.getDataObj() > 0) {
				dwzJson = new DwzJson(result, dwz);
			} else {
				dwzJson = new DwzJson("300", this.getMessageSource()
						.getMessage("delete.error", result.getMsgArgs(),
								this.getThisLocale()));
			}
		}

		return dwzJson;
	}

	/**
	 * 进入添加角色权限关联页面
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/rolePermissionAddAjax/{id}")
	public String rolePermissionAddAjax(@PathVariable("id") Long id,
			@ModelAttribute Dwz dwz, UserAgent user, ModelMap model) {
		DwzJson dwzJson;
		Role role = roleService.queryOne(new ServiceRequest(id)).getDataObj();
		if (role == null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					"ServiceError.not_find", null, this.getThisLocale()));
			model.put("msgJson", dwzJson);
			return "errorPage/msg" + EnumPageType.AJAXPAGE.getCode();
		}
		// 获取当前角色还没有的有效权限Permission（状态为非0的。）
		ServiceResult<List<Permission>> result = roleService
				.queryRoleFreePermission(new ServiceRequest(role));

		if (result.getErrorNO() != null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					result.getErrorInfo(), result.getMsgArgs(),
					this.getThisLocale()));
			model.put("msgJson", dwzJson);
			return "errorPage/msg" + EnumPageType.AJAXPAGE.getCode();
		} else {
			List<Permission> pList = result.getDataObj();
			model.put("permissions", pList);
			model.put("role", role);
			model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		}

		return "admin/role/rolePermissionAddAjax";
	}

	/**
	 * 保存添加和修改,
	 */
	@Transactional
	@PageType("JsonPage")
	@RequestMapping(value = "/rolePermissionAddJson/{roleId}", method = RequestMethod.POST)
	public @ResponseBody
	DwzJson rolePermissionAddJson(@PathVariable("roleId") Long roleId,
			@RequestParam("permissionKey_in") List<String> permissionKey_in,
			@ModelAttribute Dwz dwz, UserAgent user, ModelMap model) {
		DwzJson dwzJson;
		Role role = roleService.queryOne(new ServiceRequest(roleId))
				.getDataObj();
		if (role == null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					"ServiceError.not_find", null, this.getThisLocale()));
			return dwzJson;
		}
		if (CollectionUtils.isEmpty(permissionKey_in))
			return new DwzJson();
		String permissionKeysOld = role.getMetaMap().get(
				ConstantDTO.roleMeta_Key_PERMISSIONKEY);
		String permissionKeys = null;
		for (String permissionKey : permissionKey_in) {
			if (StringUtils.isBlank(permissionKeys))
				permissionKeys = permissionKey;
			else
				permissionKeys = permissionKeys.trim() + ","
						+ permissionKey.trim();
		}
		if (StringUtils.isBlank(permissionKeys))
			return new DwzJson();
		if (StringUtils.isBlank(permissionKeysOld)) {
			// 添加
			WebConfig webConfig = new WebConfig();
			webConfig.setCacheType(EnumWebCfgCacheType.NONE.getCode());
			webConfig.setCfgGroup(EnumWebCfgGroup.ROLEMETA.getCode());
			webConfig.setCfgName(role.getRoleKey() + "_"
					+ ConstantDTO.roleMeta_Key_PERMISSIONKEY);
			webConfig.setCfgValue(permissionKeys);
			webConfig.setRank(99F);
			webConfigService.save(new ServiceRequest(webConfig, user));
		} else {
			// 修改.
			permissionKeys = permissionKeysOld + "," + permissionKeys;
			WebConfigQuery webConfigQuery = new WebConfigQuery();
			webConfigQuery.setCfgGroup(EnumWebCfgGroup.ROLEMETA.getCode());
			webConfigQuery.setCfgName(role.getRoleKey() + "_"
					+ ConstantDTO.roleMeta_Key_PERMISSIONKEY);
			WebConfig webConfig = new WebConfig();
			webConfig.setCacheType(EnumWebCfgCacheType.NONE.getCode());
			webConfig.setCfgGroup(EnumWebCfgGroup.ROLEMETA.getCode());
			webConfig.setCfgValue(permissionKeys);
			ServiceRequest serviceRequest = new ServiceRequest(webConfig, user);
			serviceRequest.getRequestMap().put("query", webConfigQuery);
			webConfigService.updateByQuery(serviceRequest);
		}

		dwzJson = new DwzJson(dwz);
		return dwzJson;
	}

	/**
	 * 删除一个权限.
	 */
	@Transactional
	@PageType("JsonPage")
	@RequestMapping(value = "/rolePermissionDelJson/{roleId}/{permissionKey}", method = RequestMethod.POST)
	@ResponseBody
	public DwzJson rolePermissionDelJson(@PathVariable("roleId") Long roleId,
			@PathVariable("permissionKey") String permissionKey,
			@ModelAttribute Dwz dwz, UserAgent userAgent, ModelMap model) {
		DwzJson dwzJson;
		if (StringUtils.isEmpty(permissionKey))
			return new DwzJson();
		Role role = roleService.queryOne(new ServiceRequest(roleId, userAgent))
				.getDataObj();
		if (role == null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					"ServiceError.not_find", null, this.getThisLocale()));
			return dwzJson;
		}
		String permissionKeys = "";
		for (Permission permission : role.getPermissions()) {
			if (permissionKey.equals(permission.getPermissionKey()))
				continue;
			if (StringUtils.isBlank(permissionKeys))
				permissionKeys = permission.getPermissionKey().trim();
			else
				permissionKeys = permissionKeys.trim() + ","
						+ permission.getPermissionKey().trim();
		}

		// 修改.
		WebConfigQuery webConfigQuery = new WebConfigQuery();
		webConfigQuery.setCfgGroup(EnumWebCfgGroup.ROLEMETA.getCode());
		webConfigQuery.setCfgName(role.getRoleKey() + "_"
				+ ConstantDTO.roleMeta_Key_PERMISSIONKEY);
		WebConfig webConfig = new WebConfig();
		webConfig.setCacheType(EnumWebCfgCacheType.NONE.getCode());
		webConfig.setCfgGroup(EnumWebCfgGroup.ROLEMETA.getCode());
		webConfig.setCfgValue(permissionKeys);
		ServiceRequest serviceRequest = new ServiceRequest(webConfig, userAgent);
		serviceRequest.getRequestMap().put("query", webConfigQuery);
		webConfigService.updateByQuery(serviceRequest);

		dwzJson = new DwzJson(dwz);
		return dwzJson;
	}

	/**
	 * 保存添加和修改,对象的id不为空时,为修改,否则为添加
	 */
	@PageType("JsonPage")
	@RequestMapping(value = "/save/json", method = RequestMethod.POST)
	@ResponseBody
	public DwzJson roleSave(@ModelAttribute("role") Role role, UserAgent user,
			@ModelAttribute Dwz dwz, ModelMap model) {
		DwzJson dwzJson;
		if (role == null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					"operation.failed", null, this.getThisLocale()));
			return dwzJson;
		}
		if (role.getId() != null && role.getId() >= 0) {
			// 保存修改
			ServiceResult<Integer> result = roleService
					.update(new ServiceRequest(role));
			if (result.getErrorNO() != null) {
				dwzJson = new DwzJson("300", this.messageSource.getMessage(
						result.getErrorInfo(), result.getMsgArgs(),
						this.getThisLocale()));
			} else {
				dwzJson = new DwzJson(result, dwz);
			}
		} else {
			// 保存新增
			ServiceResult<Role> result = roleService.save(new ServiceRequest(
					role));
			if (result.getErrorNO() != null) {
				dwzJson = new DwzJson("300", this.messageSource.getMessage(
						result.getErrorInfo(), result.getMsgArgs(),
						this.getThisLocale()));
			} else {
				dwzJson = new DwzJson(result, dwz);
			}

		}
		return dwzJson;
	}

	/**
	 * 查看单条记录详情
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/viewAjax/{id}")
	public String roleView(@PathVariable("id") Long id,
			@ModelAttribute Dwz dwz, UserAgent user, ModelMap model) {
		Role role = roleService.queryOne(new ServiceRequest(id)).getDataObj();
		if (role == null) {
			DwzJson dwzJson = new DwzJson();
			dwzJson.setStatusCode("300");
			dwzJson.setMessage("要查看的数据不存在.");
			model.put("msgJson", dwzJson);
			return "errorPage/msg" + EnumPageType.AJAXPAGE.getCode();
		}
		model.put("role", role);
		model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		model.put("operType", "view");
		return "admin/role/objAjax";
	}

	/**
	 * 进入单条记录的修改页面
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/editAjax/{id}")
	public String roleEdit(@PathVariable("id") Long id,
			@ModelAttribute Dwz dwz, UserAgent user, ModelMap model) {
		Role role = roleService.queryOne(new ServiceRequest(id)).getDataObj();
		if (role == null) {
			DwzJson dwzJson = new DwzJson();
			dwzJson.setStatusCode("300");
			dwzJson.setMessage("要修改的数据不存在.");
			model.put("msgJson", dwzJson);
			return "errorPage/msg" + EnumPageType.AJAXPAGE.getCode();
		}
		model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		model.put("role", role);
		model.put("operType", "edit");
		return "admin/role/objAjax";
	}

	/**
	 * 删除一个数据
	 */
	@Transactional
	@PageType("JsonPage")
	@RequestMapping(value = "/delJson/{id}")
	@ResponseBody
	public DwzJson roleDel(@PathVariable("id") Long id, UserAgent user,
			@ModelAttribute Dwz dwz, ModelMap model) {
		DwzJson dwzJson;
		if (id == null || id < 0) {
			dwzJson = new DwzJson("300", this.getMessageSource().getMessage(
					"delete.error.parameter.ids.null", null,
					this.getThisLocale()));
			return dwzJson;
		}
		RoleQuery query = new RoleQuery();
		query.setId(id);
		ServiceResult<Integer> result = roleService.remove(new ServiceRequest(
				query));
		if (result.getErrorNO() != null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					result.getErrorInfo(), result.getMsgArgs(),
					this.getThisLocale()));
		} else {
			if (result.getDataObj() > 0) {
				dwzJson = new DwzJson(result, dwz);
			} else {
				dwzJson = new DwzJson("300", this.getMessageSource()
						.getMessage("delete.error", result.getMsgArgs(),
								this.getThisLocale()));
			}
		}

		return dwzJson;
	}

	/**
	 * 根据查询条件,批量删除
	 */
	@Transactional
	@PageType("JsonPage")
	@RequestMapping(value = "/delJson", method = RequestMethod.POST)
	public @ResponseBody
	DwzJson roleDelBatch(@ModelAttribute("query") RoleQuery query,
			@ModelAttribute Dwz dwz, UserAgent user, ModelMap model) {
		DwzJson dwzJson;
		// 将query.ids的条件合并到query.id_in中
		if (query != null && StringUtils.isNotBlank(query.getIds())) {
			List<Long> id_in = query.getIdsList();
			if (query.getId_in() == null) {
				query.setId_in(id_in);
			} else {
				query.getId_in().addAll(id_in);
			}
		}
		// 如果没有条件,就拒绝删除操作,以免所有数据被删掉.
		if (query == null
				|| (query.getId_in() == null || query.getId_in().size() <= 0)) {
			dwzJson = new DwzJson("300", this.getMessageSource().getMessage(
					"delete.error.parameter.ids.null", null,
					this.getThisLocale()));
			return dwzJson;
		}

		ServiceResult<Integer> result = roleService.remove(new ServiceRequest(
				query));
		if (result.getErrorNO() != null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					result.getErrorInfo(), result.getMsgArgs(),
					this.getThisLocale()));
		} else {
			if (result.getDataObj() > 0) {
				dwzJson = new DwzJson(result, dwz);
			} else {
				dwzJson = new DwzJson("300", this.getMessageSource()
						.getMessage("delete.error", result.getMsgArgs(),
								this.getThisLocale()));
			}
		}

		return dwzJson;
	}

}
