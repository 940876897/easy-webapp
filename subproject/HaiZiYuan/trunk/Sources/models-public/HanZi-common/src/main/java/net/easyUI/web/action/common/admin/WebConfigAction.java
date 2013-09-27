/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.web.action.common.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easyUI.Utils.WebCacheUtils;
import net.easyUI.access.PageType;
import net.easyUI.common.dto.Dwz;
import net.easyUI.common.dto.DwzJson;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;
import net.easyUI.common.dto.enums.EnumPageType;
import net.easyUI.common.web.action.BaseAction;
import net.easyUI.domain.common.WebConfig;
import net.easyUI.domain.query.WebConfigQuery;
import net.easyUI.dto.common.UserAgent;
import net.easyUI.dto.common.enums.EnumWebCfgCacheType;
import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.dto.common.enums.EnumWebCfgInputType;
import net.easyUI.dto.common.enums.EnumWebCfgPermission;
import net.easyUI.dto.common.enums.EnumWebCfgStatus;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.service.common.WebConfigService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/webConfig")
public class WebConfigAction extends BaseAction {

	@Autowired
	private WebConfigService webConfigService;

	private void putEnums(ModelMap model) {
		if (model == null)
			return;
		model.put("EnumWebCfgCacheTypeList", EnumWebCfgCacheType.values());
		model.put("EnumWebCfgCacheTypeMap", EnumWebCfgCacheType.toMap());

		model.put("EnumWebCfgGroupList", EnumWebCfgGroup.values());
		model.put("EnumWebCfgGroupMap", EnumWebCfgGroup.toMap());
		// 字典输入框类型
		model.put("EnumWebCfgInputTypeList", EnumWebCfgInputType.values());
		model.put("EnumWebCfgInputTypeMap", EnumWebCfgInputType.toMap());
		// 字典数据管理权限
		model.put("EnumWebCfgPermissionList", EnumWebCfgPermission.values());
		model.put("EnumWebCfgPermissionMap", EnumWebCfgPermission.toMap());
		// 字典数据状态
		model.put("EnumWebCfgStatusList", EnumWebCfgStatus.values());
		model.put("EnumWebCfgStatusMap", EnumWebCfgStatus.toMap());
	}

	/**
	 * dwz lookup 方式查询该分组下的配置列表，返回Json列表。
	 * 
	 * @param cfgGroup
	 *            字典分组
	 * @param outCfg
	 *            输出数据配置。{输出字段名1:数据字段名1,输出字段名2:数据字段名2,输出字段名3:数据字段名3}
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/lookupWebCfgsJson")
	@PageType("JsonPage")
	@ResponseBody
	public Collection lookupWebCfgs(
			@RequestParam(value = "cfgGroup", required = true) String cfgGroup,
			@RequestParam(value = "outCfg", required = false) String outCfg,
			@ModelAttribute Dwz dwz,
			UserAgent userAgent, ModelMap model) {
		Collection<WebConfig> webConfigs = WebCacheUtils
				.webConfigByGroup(cfgGroup);
		// 将webConfigs转成Json
		if (CollectionUtils.isEmpty(webConfigs))
			return null;
		if (StringUtils.isBlank(outCfg))
			return webConfigs;
		ObjectMapper mapper = new ObjectMapper();
		Collection<Map> mapLs = new ArrayList<Map>();
		// 开始解析输出配置
		Map outCfgJson = null;
		try {
			outCfgJson = mapper.readValue(outCfg, Map.class);
			if (outCfgJson.isEmpty())
				return webConfigs;
		} catch (Exception e) {
			logger.error("", e);
		}
		// 开始根据输出配置，组装数据。
		// {输出字段名1:数据字段名1,输出字段名2:数据字段名2,输出字段名3:数据字段名3}
		for (WebConfig webCfg : webConfigs) {
			Map objMap = webCfg.toMap();
			Map dataMap = new HashMap();
			for (Object o : outCfgJson.keySet()) {
				dataMap.put(o, objMap.get(outCfgJson.get(o)));
			}
			mapLs.add(dataMap);
		}
		return mapLs;
	}

	/**
	 * 查询 数据
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/indexAjax")
	public String webConfigIndexAjax(
			@ModelAttribute("query") WebConfigQuery query,
			@RequestParam(value = "vmPre", required = false, defaultValue = "") String vmPre,
			@ModelAttribute Dwz dwz,
			UserAgent userAgent, ModelMap model) {
		ServiceResult<DwzPage<WebConfig>> result = webConfigService
				.pageQuery(new ServiceRequest(query));
		model.addAttribute("page", result.getDataObj());
		model.addAttribute("srs", result);
		model.addAttribute("query", query);
		model.addAttribute("cfgGroup", query.getCfgGroup());
		putEnums(model);
		model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		// 返回页面的VM前缀
		if (vmPre == null)
			vmPre = "";
		return "admin/webConfig/" + vmPre + "indexAjax";
	}

	/**
	 * 进入添加页面
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/addAjax")
	public String webConfigAddAjax(
//			@RequestParam(value = "cfgGroup", required = false) String cfgGroup,
			@ModelAttribute("webConfig") WebConfig webConfig,
			@RequestParam(value = "vmPre", required = false, defaultValue = "") String vmPre,
			@ModelAttribute Dwz dwz,
			UserAgent userAgent, ModelMap model) {
		webConfig.setId(null);
		model.put("webConfig", webConfig);
		model.put("operType", "add");
		model.put("cfgGroup", webConfig.getCfgGroup());
		model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		putEnums(model);
		// 返回页面的VM前缀
		if (vmPre == null)
			vmPre = "";
		return "admin/webConfig/" + vmPre + "objAjax";
	}

	/**
	 * 保存添加和修改,对象的id不为空时,为修改,否则为添加
	 */
	@PageType("JsonPage")
	@RequestMapping(value = "/save/json", method = RequestMethod.POST)
	public @ResponseBody
	DwzJson webConfigSave(
			@ModelAttribute("webConfig") WebConfig webConfig,
			@RequestParam(value = "vmPre", required = false, defaultValue = "") String vmPre,
			@ModelAttribute Dwz dwz,
			UserAgent userAgent, ModelMap model) {
		DwzJson dwzJson;
		if (webConfig == null) {
			dwzJson = new DwzJson("300", this.messageSource.getMessage(
					"operation.failed", null, this.getThisLocale()));
			return dwzJson;
		}
		if (webConfig.getId() != null && webConfig.getId() >= 0) {
			// TODO 增加插件点(根据vmPre),使得个性化的功能能在这里进行.
			// 保存修改
			ServiceResult<Integer> result = webConfigService
					.update(new ServiceRequest(webConfig));
			if (result.getErrorNO() != null) {
				dwzJson = new DwzJson("300", this.messageSource.getMessage(
						result.getErrorInfo(), result.getMsgArgs(),
						this.getThisLocale()));
			} else {
				dwzJson = new DwzJson(result, dwz);
			}
		} else {
			// 保存新增
			// TODO 增加插件点(根据vmPre),使得个性化的功能能在这里进行.
			ServiceResult<WebConfig> result = webConfigService
					.save(new ServiceRequest(webConfig));
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
	public String webConfigView(
			@PathVariable("id") Long id,
			@RequestParam(value = "vmPre", required = false, defaultValue = "") String vmPre,
			@ModelAttribute Dwz dwz,
			UserAgent userAgent, ModelMap model) {
		WebConfig webConfig = webConfigService.queryOne(new ServiceRequest(id))
				.getDataObj();
		if (webConfig == null) {
			DwzJson dwzJson = new DwzJson();
			dwzJson.setStatusCode("300");
			dwzJson.setMessage("要查看的数据不存在.");
			model.put("msgJson", dwzJson);
			return "errorPage/msg" + EnumPageType.AJAXPAGE.getCode();
		}
		model.put("webConfig", webConfig);
		model.put("operType", "view");
		putEnums(model);
		model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		// 返回页面的VM前缀
		if (vmPre == null)
			vmPre = "";
		return "admin/webConfig/" + vmPre + "objAjax";
	}

	/**
	 * 进入单条记录的修改页面
	 */
	@PageType("AjaxPage")
	@RequestMapping(value = "/editAjax/{id}")
	public String webConfigEdit(
			@PathVariable("id") Long id,
			@ModelAttribute Dwz dwz,
			@RequestParam(value = "vmPre", required = false, defaultValue = "") String vmPre,
			UserAgent userAgent, ModelMap model) {
		WebConfig webConfig = webConfigService.queryOne(new ServiceRequest(id))
				.getDataObj();
		if (webConfig == null) {
			DwzJson dwzJson = new DwzJson();
			dwzJson.setStatusCode("300");
			dwzJson.setMessage("要修改的数据不存在.");
			model.put("msgJson", dwzJson);
			return "errorPage/msg" + EnumPageType.AJAXPAGE.getCode();
		}
		model.put("webConfig", webConfig);
		model.put("cfgGroup", webConfig.getCfgGroup());
		model.put("operType", "edit");
		model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
		putEnums(model);
		// 返回页面的VM前缀
		if (vmPre == null)
			vmPre = "";
		return "admin/webConfig/" + vmPre + "objAjax";
	}

	/**
	 * 删除一个数据
	 */
	@PageType("JsonPage")
	@RequestMapping(value = "/delJson/{id}")
	public @ResponseBody
	DwzJson webConfigDel(
			@PathVariable("id") Long id,
			UserAgent userAgent,
			@RequestParam(value = "vmPre", required = false, defaultValue = "") String vmPre,
			@ModelAttribute Dwz dwz,
			ModelMap model) {
		DwzJson dwzJson;
		if (id == null || id < 0) {
			dwzJson = new DwzJson("300", this.getMessageSource().getMessage(
					"delete.error.parameter.ids.null", null,
					this.getThisLocale()));
			return dwzJson;
		}
		WebConfigQuery query = new WebConfigQuery();
		query.setId(id);
		// TODO 增加插件点(根据vmPre),使得个性化的功能能在这里进行.
		ServiceResult<Integer> result = webConfigService
				.remove(new ServiceRequest(query));
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
	DwzJson webConfigDelBatch(
			@ModelAttribute("query") WebConfigQuery query,
			@RequestParam(value = "vmPre", required = false, defaultValue = "") String vmPre,
			@ModelAttribute Dwz dwz,
			UserAgent userAgent, ModelMap model) {
		DwzJson dwzJson;
		// TODO 增加插件点(根据vmPre),使得个性化的功能能在这里进行.
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

		ServiceResult<Integer> result = webConfigService
				.remove(new ServiceRequest(query));
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
