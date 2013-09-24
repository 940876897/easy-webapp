/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.service.common.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.easyUI.domain.common.Role;
import net.easyUI.domain.common.User;
import net.easyUI.domain.common.WebConfig;
import net.easyUI.domain.query.RoleQuery;
import net.easyUI.domain.query.UserQuery;
import net.easyUI.domain.query.WebConfigQuery;
import net.easyUI.dto.common.ConstantDTO;
import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.dto.common.query.DwzPage;
import net.easyUI.manager.common.RoleManager;
import net.easyUI.manager.common.UserManager;
import net.easyUI.manager.common.WebConfigManager;
import net.easyUI.service.common.UserService;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;
import net.easyUI.common.dto.enums.EnumServiceError;
import net.easyUI.common.service.BasePOJOService;
import net.easyUI.common.util.digest.MessageDigest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends BasePOJOService implements UserService {

	@Autowired
	private UserManager userManager;
	@Autowired
	private WebConfigManager webConfigManager;
	@Autowired
	private MessageDigest messageDigest;
	@Autowired
	private RoleManager roleManager;

	protected static Log logger = LogFactory.getLog(UserServiceImpl.class);

	public ServiceResult<DwzPage<User>> pageQuery(ServiceRequest serseq) {
		ServiceResult<DwzPage<User>> serres = new ServiceResult<DwzPage<User>>();
		// TODO 预处理
		try {
			DwzPage<User> page = userManager.pageQueryFullFK((UserQuery) serseq
					.getResDto());
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

	public ServiceResult<User> save(ServiceRequest serseq) {
		// TODO 预处理
		User user = null;
		ServiceResult<User> sr = new ServiceResult<User>();
		try {
			user = (User) serseq.getResDto();
			if (StringUtils.isBlank(user.getPassword()))
				user.setPassword("123456");
			// TODO 将密码加密
			String pwd = messageDigest.digest(user.getPassword());
			user.setPassword(pwd);

			user = userManager.save(user);
		} catch (Exception e) {
			sr.setErrorNO(300);
			sr.setErrorInfo("service.exception");
			String[] msgs = { e.getMessage() };
			sr.setMsgArgs(msgs);
			logger.error("", e);
		}
		if (user != null && user.getId() != null)
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
			User user = (User) serseq.getResDto();
			// 如果密码不为空，则修改密码。
			if (StringUtils.isNotBlank(user.getPassword())) {
				// TODO 将密码加密
				String pwd = messageDigest.digest(user.getPassword());
				user.setPassword(pwd);
			}
			count = userManager.update((User) serseq.getResDto());
		} catch (Exception e) {
			sr.setErrorNO(300);
			sr.setErrorInfo("service.exception");
			String[] msgs = { e.getMessage() };
			sr.setMsgArgs(msgs);
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

	public ServiceResult<User> queryOne(ServiceRequest serseq) {
		// TODO 预处理
		ServiceResult<User> sr = new ServiceResult<User>();
		User user = userManager.queryOneFullFK((Long) serseq.getResDto());
		if (user != null) {
			// 查询所有的子属性集，以供一次性填写设置
			WebConfigQuery webConfigQuery = new WebConfigQuery();
			webConfigQuery.setCfgGroup(EnumWebCfgGroup.USERMETA.getCode());
			webConfigQuery.setCfgName_bw(user.getLoginName() + "_");
			user.setUserMetas(webConfigManager.listQuery(webConfigQuery));
		}
		// 查询这个用户下的角色集。
		if (user != null && CollectionUtils.isNotEmpty(user.getUserMetas())) {
			RoleQuery rQuery = new RoleQuery();
			List<String> roleKey_in = new ArrayList<String>();
			for (WebConfig userMeta : user.getUserMetas()) {
				if (userMeta.getCfgName().endsWith(
						"_" + ConstantDTO.userMeta_Key_ROLEKEY)) {
					// 拆分角色权限关联集.如果角色有多个权限,则值记录多个权限的KEY,之间用英文逗号分隔.
					String[] roleKeyArray = userMeta.getCfgValue().split(",");
					if (ArrayUtils.isEmpty(roleKeyArray))
						continue;
					roleKey_in = Arrays.asList(roleKeyArray);
					break;
				}
			}
			if (CollectionUtils.isNotEmpty(roleKey_in)) {
				rQuery.setRoleKey_in(roleKey_in);
				rQuery.setEnabled_ne("0");
				List<Role> roles = roleManager.listQuery(rQuery);
				user.setRoles(roles);
			}
		}
		sr.setDataObj(user);
		return sr;
	}

	public ServiceResult<Integer> remove(ServiceRequest serseq) {
		Integer count = 0;
		// TODO 预处理
		UserQuery query = (UserQuery) serseq.getResDto();
		if (!query.isWhereNull())
			count = userManager.remove(query);
		return new ServiceResult<Integer>(count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.easyUI.service.admin.UserService#queryUserFreeRole(net.
	 * easyUI.willingOX.common.dto.ServiceRequest)
	 */

	public ServiceResult<List<Role>> queryUserFreeRole(
			ServiceRequest serviceRequest) {
		User user = (User) serviceRequest.getResDto();
		RoleQuery rQuery = new RoleQuery();
		List<Long> id_ni = new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(user.getRoles())) {
			for (Role role : user.getRoles()) {
				id_ni.add(role.getId());
			}
			rQuery.setId_ni(id_ni);
		}
		rQuery.setEnabled_ne("0");
		List<Role> rList = roleManager.listQuery(rQuery);
		return new ServiceResult<List<Role>>(rList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.easyUI.service.admin.UserService#updatePwd(net.easyUI.willingOX
	 * .common.dto.ServiceRequest)
	 */

	public ServiceResult<Integer> updatePwd(ServiceRequest serseq) {
		// TODO 取原数据对象

		// TODO 预处理
		// TODO 分类变化修改
		// 保存修改
		Integer count = 0;
		ServiceResult<Integer> sr = new ServiceResult<Integer>();
		User user = (User) serseq.getResDto();

		User oldUser = null;
		if (user.getId() != null)
			oldUser = userManager.queryOne(user.getId());
		else if (StringUtils.isNotBlank(user.getLoginName())) {
			oldUser = userManager.getByUkLoginName(user.getLoginName());
		}
		if (oldUser == null) {
			sr.setErrorNO(300);
			sr.setErrorInfo("User.loginName.error");
			return sr;
		}

		// 查询比较原密码是否正确,如果原密码不空，就不比较了。
		if (StringUtils.isNotBlank(user.getNicename())) {
			String oldPwd = messageDigest.digest(user.getNicename());
			if (!oldUser.getPassword().equals(oldPwd)) {
				sr.setErrorNO(300);
				sr.setErrorInfo("User.password.error");
				return sr;
			}
		}
		try {
			// 如果密码不为空，则修改密码。
			if (StringUtils.isNotBlank(user.getPassword())) {
				String newPwd = messageDigest.digest(user.getPassword());
				oldUser.setPassword(newPwd);
			}
			count = userManager.update(oldUser);
		} catch (Exception e) {
			sr.setErrorNO(300);
			sr.setErrorInfo("service.exception");
			String[] msgs = { e.getMessage() };
			sr.setMsgArgs(msgs);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.easyUI.service.admin.UserService#listQuery(net.easyUI.willingOX
	 * .common.dto.ServiceRequest)
	 */

	public ServiceResult<List<User>> listQuery(ServiceRequest request) {
		return new ServiceResult<List<User>>(
				userManager.listQuery((UserQuery) request.getResDto()));
	}
}
