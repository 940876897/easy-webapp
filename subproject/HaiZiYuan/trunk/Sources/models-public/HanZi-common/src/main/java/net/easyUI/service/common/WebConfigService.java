/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.service.common;

import net.easyUI.domain.common.WebConfig;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;

public interface WebConfigService {

	public ServiceResult<DwzPage<WebConfig>> pageQuery(ServiceRequest serseq);

	public ServiceResult<WebConfig> save(ServiceRequest serseq);

	public ServiceResult<WebConfig> queryOne(ServiceRequest serseq);

	public ServiceResult<Integer> update(ServiceRequest serseq);
	public ServiceResult<Integer> updateByQuery(ServiceRequest serseq);

	public ServiceResult<Integer> remove(ServiceRequest serseq);

}
