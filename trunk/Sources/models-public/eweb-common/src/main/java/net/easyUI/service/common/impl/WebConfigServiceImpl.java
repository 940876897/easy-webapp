/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.service.common.impl;

import net.easyUI.domain.common.WebConfig;
import net.easyUI.domain.query.WebConfigQuery;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.manager.common.WebConfigManager;
import net.easyUI.service.common.WebConfigService;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;
import net.easyUI.common.dto.enums.EnumServiceError;
import net.easyUI.common.service.BasePOJOService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("webConfigService")
public class WebConfigServiceImpl extends BasePOJOService implements
		WebConfigService {

	@Autowired
	private WebConfigManager webConfigManager;
	protected static Log logger = LogFactory.getLog(WebConfigServiceImpl.class);

	public ServiceResult<DwzPage<WebConfig>> pageQuery(ServiceRequest serseq) {
		ServiceResult<DwzPage<WebConfig>> serres = new ServiceResult<DwzPage<WebConfig>>();
		// TODO 预处理
		try {
			DwzPage<WebConfig> page = webConfigManager
					.pageQueryFullFK((WebConfigQuery) serseq.getResDto());
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

	public ServiceResult<WebConfig> save(ServiceRequest serseq) {
		// TODO 预处理
		WebConfig webConfig = null;
		ServiceResult<WebConfig> sr = new ServiceResult<WebConfig>();
		try {
			webConfig = webConfigManager.save((WebConfig) serseq.getResDto());
		} catch (Exception e) {
			sr.setErrorNO(300);
			sr.setErrorInfo("service.exception");
			sr.setMsgArgs(e.getMessage());
			logger.error("", e);
		}
		if (webConfig != null && webConfig.getId() != null)
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
		try {
			count = webConfigManager.update((WebConfig) serseq.getResDto());
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

	public ServiceResult<Integer> updateByQuery(ServiceRequest serseq) {
		// TODO 取原数据对象

		// TODO 预处理
		// TODO 分类变化修改
		// 保存修改
		Integer count = 0;
		ServiceResult<Integer> sr = new ServiceResult<Integer>();
		try {
			count = webConfigManager.updateByQuery((WebConfig) serseq
					.getResDto(),
					(WebConfigQuery) serseq.getRequestMap().get("query"));
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

	public ServiceResult<WebConfig> queryOne(ServiceRequest serseq) {
		// TODO 预处理
		return new ServiceResult<WebConfig>(
				webConfigManager.queryOneFullFK((Long) serseq.getResDto()));
	}

	public ServiceResult<Integer> remove(ServiceRequest serseq) {
		Integer count = 0;
		// TODO 预处理
		WebConfigQuery query = (WebConfigQuery) serseq.getResDto();
		if (!query.isWhereNull())
			count = webConfigManager.remove(query);
		return new ServiceResult<Integer>(count);
	}

}
