/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.service.common;

import java.util.List;

import net.easyUI.domain.common.Role;
import net.easyUI.domain.common.User;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;

public interface UserService {

    public ServiceResult<DwzPage<User>> pageQuery(ServiceRequest serseq);

    public ServiceResult<User> save(ServiceRequest serseq);

    public ServiceResult<User> queryOne(ServiceRequest serseq);

    public ServiceResult<Integer> update(ServiceRequest serseq);

    public ServiceResult<Integer> remove(ServiceRequest serseq);

    /**
     * 获取当前用户还没有的有效角色Role（状态为非0的。）
     */
    public ServiceResult<List<Role>> queryUserFreeRole(ServiceRequest serviceRequest);

    /**
     * 修改密码，通过用户ID或登录账号，修改密码，新密码放在密码字段上，原密码放在nickName上。
     */
    public ServiceResult<Integer> updatePwd(ServiceRequest sr);

    /**
     * @param request
     * @return
     */
    public ServiceResult<List<User>> listQuery(ServiceRequest request);

}
