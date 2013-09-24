/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.service.common;

import java.util.List;

import net.easyUI.domain.common.Permission;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;

public interface PermissionService {

	public ServiceResult<List<Permission>> queryAll(ServiceRequest serseq);
	
	public ServiceResult<DwzPage<Permission>> pageQuery(ServiceRequest serseq);

	public ServiceResult<Permission> save(ServiceRequest serseq);

	public ServiceResult<Permission> queryOne(ServiceRequest serseq);

	public ServiceResult<Integer> update(ServiceRequest serseq);

	public ServiceResult<Integer> remove(ServiceRequest serseq);

}
