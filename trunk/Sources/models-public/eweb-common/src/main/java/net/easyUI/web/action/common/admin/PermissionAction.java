/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.web.action.common.admin;

import java.util.List;

import net.easyUI.access.PageType;
import net.easyUI.domain.common.Permission;
import net.easyUI.domain.query.PermissionQuery;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.service.common.PermissionService;
import net.easyUI.common.dto.DwzJson;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;
import net.easyUI.common.dto.enums.EnumPageType;
import net.easyUI.common.web.action.BaseAction;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/permission")
public class PermissionAction extends BaseAction {

	@Autowired
	private PermissionService permissionService;

	/**
	 * 演示如何直接在action中注入配置文件中设置的值
	 */
	@SuppressWarnings("unused")
	@Value("${system.devMode}")
	private boolean devMode;

	/**
	 * 查询 数据
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/indexAjax")
	public String permissionIndexAjax(
			@ModelAttribute("query") PermissionQuery query, ModelMap model) {
		ServiceResult<DwzPage<Permission>> result = permissionService
				.pageQuery(new ServiceRequest(query));
		model.addAttribute("page", result.getDataObj());
		model.addAttribute("srs", result);
		model.addAttribute("query", query);
		return "admin/permission/indexAjax";
	}

	/**
	 * 进入添加页面
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/addAjax")
	public String permissionAddAjax(ModelMap model) {
		Permission permission = new Permission();
		model.put("permission", permission);
		model.put("operType", "add");
		return "admin/permission/objAjax";
	}

	/**
	 * 保存添加和修改,对象的id不为空时,为修改,否则为添加
	 */
	@PageType("JsonPage")
	@RequestMapping(value = "/save/json", method = RequestMethod.POST)
	public @ResponseBody
	DwzJson permissionSave(@ModelAttribute("permission") Permission permission,
			ModelMap model) {
		DwzJson dwzJson;
		if (permission == null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					"operation.failed", null, this.getThisLocale()));
			return dwzJson;
		}
		if (permission.getId() != null && permission.getId() >= 0) {
			// 保存修改
			ServiceResult<Integer> result = permissionService
					.update(new ServiceRequest(permission));
			if (result.getErrorNO() != null) {
				dwzJson = new DwzJson("300", this.messageSource.getMessage(
						result.getErrorInfo(), result.getMsgArgs(),
						this.getThisLocale()));
			} else {
				dwzJson = new DwzJson("200", this.messageSource.getMessage(
						"operation.success", result.getMsgArgs(),
						this.getThisLocale()), "dwz_tab_permission",
						"closeCurrent");
			}
		} else {
			// 保存新增
			ServiceResult<Permission> result = permissionService
					.save(new ServiceRequest(permission));
			if (result.getErrorNO() != null) {
				dwzJson = new DwzJson("300", this.messageSource.getMessage(
						result.getErrorInfo(), result.getMsgArgs(),
						this.getThisLocale()));
			} else {
				dwzJson = new DwzJson("200", this.messageSource.getMessage(
						"operation.success", result.getMsgArgs(),
						this.getThisLocale()), "dwz_tab_permission",
						"closeCurrent");
			}

		}
		return dwzJson;
	}

	/**
	 * 查看单条记录详情
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/viewAjax/{id}")
	public String permissionView(@PathVariable("id") Long id, ModelMap model) {
		Permission permission = permissionService.queryOne(
				new ServiceRequest(id)).getDataObj();
		if (permission == null) {
			DwzJson dwzJson = new DwzJson();
			dwzJson.setStatusCode("300");
			dwzJson.setMessage("要查看的数据不存在.");
			model.put("msgJson", dwzJson);
			return "errorPage/msg" + EnumPageType.AJAXPAGE.getCode();
		}
		model.put("permission", permission);
		model.put("operType", "view");
		return "admin/permission/objAjax";
	}

	/**
	 * 进入单条记录的修改页面
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/editAjax/{id}")
	public String permissionEdit(@PathVariable("id") Long id, ModelMap model) {
		Permission permission = permissionService.queryOne(
				new ServiceRequest(id)).getDataObj();
		if (permission == null) {
			DwzJson dwzJson = new DwzJson();
			dwzJson.setStatusCode("300");
			dwzJson.setMessage("要修改的数据不存在.");
			model.put("msgJson", dwzJson);
			return "errorPage/msg" + EnumPageType.AJAXPAGE.getCode();
		}
		model.put("permission", permission);
		model.put("operType", "edit");
		return "admin/permission/objAjax";
	}

	/**
	 * 删除一个数据
	 */
	@PageType("JsonPage")
	@RequestMapping(value = "/delJson/{id}")
	public @ResponseBody
	DwzJson permissionDel(@PathVariable("id") Long id, ModelMap model) {
		DwzJson dwzJson;
		if (id == null || id < 0) {
			dwzJson = new DwzJson("300", this.getMessageSource().getMessage(
					"delete.error.parameter.ids.null", null,
					this.getThisLocale()));
			return dwzJson;
		}
		PermissionQuery query = new PermissionQuery();
		query.setId(id);
		ServiceResult<Integer> result = permissionService
				.remove(new ServiceRequest(query));
		if (result.getErrorNO() != null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					result.getErrorInfo(), result.getMsgArgs(),
					this.getThisLocale()));
		} else {
			if (result.getDataObj() > 0) {
				dwzJson = new DwzJson("200", this.messageSource.getMessage(
						"operation.success", result.getMsgArgs(),
						this.getThisLocale()), "dwz_tab_permission");
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
	@PageType("JsonPage")
	@RequestMapping(value = "/delJson")
	public @ResponseBody
	DwzJson permissionDelBatch(@ModelAttribute("query") PermissionQuery query,
			ModelMap model) {
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

		ServiceResult<Integer> result = permissionService
				.remove(new ServiceRequest(query));
		if (result.getErrorNO() != null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					result.getErrorInfo(), result.getMsgArgs(),
					this.getThisLocale()));
		} else {
			if (result.getDataObj() > 0) {
				dwzJson = new DwzJson("200", this.messageSource.getMessage(
						"operation.success", result.getMsgArgs(),
						this.getThisLocale()), "dwz_tab_permission");
			} else {
				dwzJson = new DwzJson("300", this.getMessageSource()
						.getMessage("delete.error", result.getMsgArgs(),
								this.getThisLocale()));
			}
		}

		return dwzJson;
	}

}
