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
import net.easyUI.domain.common.Role;
import net.easyUI.domain.common.User;
import net.easyUI.domain.common.WebConfig;
import net.easyUI.domain.query.UserQuery;
import net.easyUI.domain.query.WebConfigQuery;
import net.easyUI.dto.common.ConstantDTO;
import net.easyUI.dto.common.UserAgent;
import net.easyUI.dto.common.enums.EnumEnableStatus;
import net.easyUI.dto.common.enums.EnumWebCfgCacheType;
import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.service.common.UserService;
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
@RequestMapping("/admin/user")
public class UserAction extends BaseAction {

	@Autowired
	private UserService userService;
	@Autowired
	private WebConfigService webConfigService;

	private void putEnums(ModelMap model) {
		if (model == null)
			return;
		model.put("EnumEnableStatusList", EnumEnableStatus.values());
		model.put("EnumEnableStatusMap", EnumEnableStatus.toStrMap());
	}

	/**
	 * 删除一个角色.
	 */
	@Transactional
	@PageType("JsonPage")
	@RequestMapping(value = "/userRoleDelJson/{userId}/{roleKey}", method = RequestMethod.POST)
	@ResponseBody
	public DwzJson userRoleDelJson(@PathVariable("userId") Long userId,
			@ModelAttribute Dwz dwz, @PathVariable("roleKey") String roleKey,
			@RequestParam(required = false, defaultValue = "") String dwzId,
			UserAgent userAgent, ModelMap model) {
		DwzJson dwzJson;
		if (StringUtils.isEmpty(roleKey))
			return new DwzJson();
		User user = userService.queryOne(new ServiceRequest(userId, userAgent))
				.getDataObj();
		if (user == null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					"ServiceError.not_find", null, this.getThisLocale()));
			return dwzJson;
		}
		String RoleKeys = "";
		for (Role role : user.getRoles()) {
			if (roleKey.equals(role.getRoleKey()))
				continue;
			if (StringUtils.isBlank(RoleKeys))
				RoleKeys = role.getRoleKey().trim();
			else
				RoleKeys = RoleKeys.trim() + "," + role.getRoleKey().trim();
		}

		// 修改.
		WebConfigQuery webConfigQuery = new WebConfigQuery();
		webConfigQuery.setCfgGroup(EnumWebCfgGroup.USERMETA.getCode());
		webConfigQuery.setCfgName(user.getLoginName() + "_"
				+ ConstantDTO.userMeta_Key_ROLEKEY);
		WebConfig webConfig = new WebConfig();
		webConfig.setCacheType(EnumWebCfgCacheType.NONE.getCode());
		webConfig.setCfgGroup(EnumWebCfgGroup.USERMETA.getCode());
		webConfig.setCfgValue(StringUtils.isBlank(RoleKeys) ? "guest"
				: RoleKeys);
		ServiceRequest serviceRequest = new ServiceRequest(webConfig, user);
		serviceRequest.getRequestMap().put("query", webConfigQuery);
		ServiceResult<Integer> result = webConfigService
				.updateByQuery(serviceRequest);

		// dwzJson = new DwzJson("200", this.messageSource.getMessage(
		// "operation.success", null, this.getThisLocale()), dwzId,
		// EnumDwzJsonCallbackType.FORWARD.getCode());
		dwzJson = new DwzJson(result, dwz);
		return dwzJson;
	}

	/**
	 * 删除一个用户扩展属性（角色）
	 */
	@PageType("JsonPage")
	@RequestMapping(value = "/userMetaDelJson/{metaId}")
	@ResponseBody
	public DwzJson userMetaDelJson(@PathVariable("metaId") Long metaId,
			@ModelAttribute Dwz dwz, UserAgent userAgent, ModelMap model) {
		DwzJson dwzJson;
		if (metaId == null || metaId < 0) {
			dwzJson = new DwzJson("300", this.getMessageSource().getMessage(
					"delete.error.parameter.ids.null", null,
					this.getThisLocale()));
			return dwzJson;
		}
		WebConfigQuery query = new WebConfigQuery();
		query.setId(metaId);
		ServiceResult<Integer> result = webConfigService
				.remove(new ServiceRequest(query, userAgent));
		if (result.getErrorNO() != null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					result.getErrorInfo(), result.getMsgArgs(),
					this.getThisLocale()));
		} else {
			if (result.getDataObj() > 0) {
				// dwzJson = new DwzJson("200", this.messageSource.getMessage(
				// "operation.success", result.getMsgArgs(),
				// this.getThisLocale()), dwzId,
				// EnumDwzJsonCallbackType.FORWARD.getCode());
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
	 * 进入添加用户角色关联页面
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/userRoleAddAjax/{id}")
	public String userRoleAddAjax(@PathVariable("id") Long id,
			@ModelAttribute Dwz dwz, UserAgent userAgent, ModelMap model) {
		DwzJson dwzJson;
		User user = userService.queryOne(new ServiceRequest(id, userAgent))
				.getDataObj();
		if (user == null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					"ServiceError.not_find", null, this.getThisLocale()));
			model.put("msgJson", dwzJson);
			return "errorPage/msg" + EnumPageType.AJAXPAGE.getCode();
		}
		// 获取当前用户还没有的有效角色Role（状态为非0的。）
		ServiceRequest srq = new ServiceRequest(user, userAgent);
		ServiceResult<List<Role>> result = userService.queryUserFreeRole(srq);

		if (result.getErrorNO() != null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					result.getErrorInfo(), result.getMsgArgs(),
					this.getThisLocale()));
			model.put("msgJson", dwzJson);
			return "errorPage/msg" + EnumPageType.AJAXPAGE.getCode();
		} else {
			List<Role> rList = result.getDataObj();
			model.put("roles", rList);
			model.put("user", user);
			model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		}
		putEnums(model);
		return "admin/user/userRoleAddAjax";
	}

	/**
	 * 保存添加和修改,
	 */
	@PageType("JsonPage")
	@RequestMapping(value = "/userRoleAddJson/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public DwzJson userRoleAddJson(@PathVariable("userId") Long userId,
			@RequestParam("roleKey_in") List<String> roleKey_in,
			@ModelAttribute Dwz dwz, UserAgent userAgent, ModelMap model) {
		DwzJson dwzJson;
		User user = userService.queryOne(new ServiceRequest(userId))
				.getDataObj();
		if (user == null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					"ServiceError.not_find", null, this.getThisLocale()));
			return dwzJson;
		}
		if (CollectionUtils.isEmpty(roleKey_in))
			return new DwzJson();
		String roleKeysOld = user.getMetaMap().get(
				ConstantDTO.userMeta_Key_ROLEKEY);
		String roleKeys = null;
		for (String roleKey : roleKey_in) {
			if (StringUtils.isBlank(roleKeys))
				roleKeys = roleKey;
			else
				roleKeys = roleKeys.trim() + "," + roleKey.trim();
		}
		if (StringUtils.isBlank(roleKeys))
			return new DwzJson();
		if (StringUtils.isBlank(roleKeysOld)) {
			// 添加
			WebConfig webConfig = new WebConfig();
			webConfig.setCacheType(EnumWebCfgCacheType.NONE.getCode());
			webConfig.setCfgGroup(EnumWebCfgGroup.USERMETA.getCode());
			webConfig.setCfgName(user.getLoginName() + "_"
					+ ConstantDTO.userMeta_Key_ROLEKEY);
			webConfig.setCfgValue(roleKeys);
			webConfig.setRank(99F);
			webConfigService.save(new ServiceRequest(webConfig, user));
		} else {
			// 修改.
			roleKeys = roleKeysOld + "," + roleKeys;
			WebConfigQuery webConfigQuery = new WebConfigQuery();
			webConfigQuery.setCfgGroup(EnumWebCfgGroup.USERMETA.getCode());
			webConfigQuery.setCfgName(user.getLoginName() + "_"
					+ ConstantDTO.userMeta_Key_ROLEKEY);
			WebConfig webConfig = new WebConfig();
			webConfig.setCacheType(EnumWebCfgCacheType.NONE.getCode());
			webConfig.setCfgGroup(EnumWebCfgGroup.USERMETA.getCode());
			webConfig.setCfgValue(roleKeys);
			ServiceRequest serviceRequest = new ServiceRequest(webConfig, user);
			serviceRequest.getRequestMap().put("query", webConfigQuery);
			webConfigService.updateByQuery(serviceRequest);
		}

		dwzJson = new DwzJson(dwz);
		return dwzJson;
	}

	/**
	 * 查询 数据
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/indexAjax")
	public String userIndexAjax(@ModelAttribute("query") UserQuery query,
			@ModelAttribute Dwz dwz, UserAgent userAgent, ModelMap model) {
		ServiceResult<DwzPage<User>> result = userService
				.pageQuery(new ServiceRequest(query, userAgent));
		model.addAttribute("page", result.getDataObj());
		model.addAttribute("srs", result);
		model.addAttribute("query", query);
		model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		putEnums(model);
		if (dwz != null && "lookup".equals(dwz.getDwzId()))
			return "/admin/user/lookupAjax";
		return "admin/user/indexAjax";
	}

	/**
	 * 进入添加页面
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/addAjax")
	public String userAddAjax(@ModelAttribute Dwz dwz, ModelMap model) {
		User user = new User();
		model.put("user", user);
		model.put("operType", "add");
		model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		putEnums(model);
		return "admin/user/objAjax";
	}

	/**
	 * 保存添加和修改,对象的id不为空时,为修改,否则为添加
	 */
	@PageType("JsonPage")
	@RequestMapping(value = "/save/json", method = RequestMethod.POST)
	public @ResponseBody
	DwzJson userSave(@ModelAttribute("user") User user,
			@ModelAttribute Dwz dwz, UserAgent userAgent, ModelMap model) {
		DwzJson dwzJson;
		if (user == null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					"operation.failed", null, this.getThisLocale()));
			return dwzJson;
		}
		ServiceRequest sr = new ServiceRequest(user, userAgent);
		sr.setResDto(user);
		if (user.getId() != null && user.getId() >= 0) {
			// 保存修改
			ServiceResult<Integer> result = userService.update(sr);
			if (result.getErrorNO() != null) {
				dwzJson = new DwzJson("300", this.messageSource.getMessage(
						result.getErrorInfo(), result.getMsgArgs(),
						this.getThisLocale()));
			} else {
				dwzJson = new DwzJson(dwz);
			}
		} else {
			// 保存新增
			ServiceResult<User> result = userService.save(sr);
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
	public String userView(@PathVariable("id") Long id,
			@ModelAttribute Dwz dwz, UserAgent userAgent, ModelMap model) {
		User user = userService.queryOne(new ServiceRequest(id, userAgent))
				.getDataObj();
		if (user == null) {
			DwzJson dwzJson = new DwzJson();
			dwzJson.setStatusCode("300");
			dwzJson.setMessage("要查看的数据不存在.");
			model.put("msgJson", dwzJson);
			return "errorPage/msg" + EnumPageType.AJAXPAGE.getCode();
		}
		model.put("user", user);
		model.put("operType", "view");
		putEnums(model);
		model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		return "admin/user/objAjax";
	}

	/**
	 * 进入单条记录的修改页面
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/editAjax/{id}")
	public String userEdit(@PathVariable("id") Long id,
			@ModelAttribute Dwz dwz, UserAgent userAgent, ModelMap model) {
		User user = userService.queryOne(new ServiceRequest(id, userAgent))
				.getDataObj();
		if (user == null) {
			DwzJson dwzJson = new DwzJson();
			dwzJson.setStatusCode("300");
			dwzJson.setMessage("要修改的数据不存在.");
			model.put("msgJson", dwzJson);
			return "errorPage/msg" + EnumPageType.AJAXPAGE.getCode();
		}
		model.put("user", user);
		model.put("operType", "edit");
		putEnums(model);
		model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		return "admin/user/objAjax";
	}

	/**
	 * 删除一个数据
	 */
	@PageType("JsonPage")
	@RequestMapping(value = "/delJson/{id}")
	public @ResponseBody
	DwzJson userDel(@PathVariable("id") Long id, UserAgent userAgent,
			@ModelAttribute Dwz dwz, ModelMap model) {
		DwzJson dwzJson;
		if (id == null || id < 0) {
			dwzJson = new DwzJson("300", this.getMessageSource().getMessage(
					"delete.error.parameter.ids.null", null,
					this.getThisLocale()));
			return dwzJson;
		}
		UserQuery query = new UserQuery();
		query.setId(id);
		ServiceResult<Integer> result = userService.remove(new ServiceRequest(
				query, userAgent));
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
	@PageType("JsonPage")
	@RequestMapping(value = "/delJson", method = RequestMethod.POST)
	public @ResponseBody
	DwzJson userDelBatch(@ModelAttribute("query") UserQuery query,
			@ModelAttribute Dwz dwz, UserAgent userAgent, ModelMap model) {
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

		ServiceResult<Integer> result = userService.remove(new ServiceRequest(
				query, userAgent));
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
