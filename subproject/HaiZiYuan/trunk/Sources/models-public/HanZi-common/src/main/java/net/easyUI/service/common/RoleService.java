/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.service.common;

import java.util.List;

import net.easyUI.domain.common.Permission;
import net.easyUI.domain.common.Role;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;

public interface RoleService {

	public ServiceResult<DwzPage<Role>> pageQuery(ServiceRequest serseq);

	public ServiceResult<Role> save(ServiceRequest serseq);

	public ServiceResult<Role> queryOne(ServiceRequest serseq);

	public ServiceResult<Integer> update(ServiceRequest serseq);

	public ServiceResult<Integer> remove(ServiceRequest serseq);

	/**
	 * 获取当前角色还没有的有效权限Permission（状态为非0的。）
	 * @param serviceRequest
	 * @return
	 */
	public ServiceResult<List<Permission>> queryRoleFreePermission(
			ServiceRequest serviceRequest);

}
