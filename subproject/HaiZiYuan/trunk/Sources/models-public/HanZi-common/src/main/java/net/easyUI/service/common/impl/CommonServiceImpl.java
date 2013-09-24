package net.easyUI.service.common.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.easyUI.domain.common.User;
import net.easyUI.domain.common.WebConfig;
import net.easyUI.domain.query.UserQuery;
import net.easyUI.domain.query.WebConfigQuery;
import net.easyUI.dto.common.CacheDTO;
import net.easyUI.dto.common.ConstantDTO;
import net.easyUI.dto.common.UserAgent;
import net.easyUI.dto.common.enums.EnumEnableStatus;
import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.manager.common.RoleManager;
import net.easyUI.manager.common.UploadManager;
import net.easyUI.manager.common.UserManager;
import net.easyUI.manager.common.WebConfigManager;
import net.easyUI.service.common.CommonService;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;
import net.easyUI.common.dto.enums.EnumServiceError;
import net.easyUI.common.service.BasePOJOService;
import net.easyUI.common.util.digest.MessageDigest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("commonService")
public class CommonServiceImpl extends BasePOJOService implements CommonService {

	@Autowired
	private UserManager userManager;
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private WebConfigManager webConfigManager;
	@Autowired
	private UploadManager uploadManager;
	@Autowired
	private MessageDigest messageDigest;

	public ServiceResult<String> uploadFile(ServiceRequest serseq) {
		ServiceResult<String> sr = new ServiceResult<String>();
		MultipartFile multipartFile = (MultipartFile) serseq.getResDto();
		InputStream inputStream;
		try {
			inputStream = multipartFile.getInputStream();
		} catch (IOException e) {
			logger.error("", e);
			sr.setErrorNO(EnumServiceError.EXCEPTION.getCode());
			sr.setErrorInfo(EnumServiceError.EXCEPTION.getDesc());
			return sr;
		}
		String filename = multipartFile.getName();
		String fileExt = "";
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');
			if ((i > -1) && (i < (filename.length() - 1))) {
				fileExt = filename.substring(i + 1);
			}
		}
		String fileUrl = uploadManager.uploadFile(inputStream, fileExt);
		sr.setDataObj(fileUrl);
		return sr;
	}

	@SuppressWarnings("unchecked")
	public ServiceResult<List<String>> uploadFileList(ServiceRequest serseq) {
		ServiceResult<List<String>> sr = new ServiceResult<List<String>>();
		Collection<MultipartFile> multipartFileList = (Collection<MultipartFile>) serseq
				.getResDto();
		List<String> fileUrls = new ArrayList<String>();
		if (CollectionUtils.isEmpty(multipartFileList)) {
			sr.setErrorNO(EnumServiceError.NOT_FIND.getCode());
			sr.setErrorInfo(EnumServiceError.NOT_FIND.getDesc());
			sr.setDataObj(fileUrls);
			return sr;
		}
		for (MultipartFile multipartFile : multipartFileList) {
			InputStream inputStream;
			try {
				inputStream = multipartFile.getInputStream();
			} catch (IOException e) {
				logger.error("", e);
				fileUrls.add("");
				continue;
			}
			String filename = multipartFile.getOriginalFilename();
			if (logger.isDebugEnabled()) {
				logger.debug("正要上传的文件名为:" + filename);
				logger.debug("ContentType为:" + multipartFile.getContentType());
			}
			String fileExt = "";
			if ((filename != null) && (filename.length() > 0)) {
				int i = filename.lastIndexOf('.');
				if ((i > -1) && (i < (filename.length() - 1))) {
					fileExt = filename.substring(i + 1);
				}
			}
			fileUrls.add(uploadManager.uploadFile(inputStream, fileExt));
		}
		sr.setDataObj(fileUrls);
		return sr;
	}

	public ServiceResult<UserAgent> checkLogin(ServiceRequest serseq) {
		ServiceResult<UserAgent> sr = new ServiceResult<UserAgent>();
		User user = null;
		user = (User) serseq.getUserDto();
		if (user == null) {
			try {
				user = (User) serseq.getResDto();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		// 检查参数
		if (user == null) {
			sr.setErrorNO(EnumServiceError.FAILED.getCode());
			sr.setErrorInfo("input.required");
			sr.setMsgArgs("登录账号和密码");
			return sr;
		}
		String loginName = user.getLoginName();
		String displayName = user.getDisplayName();
		String password = user.getPassword();
		if ((StringUtils.isBlank(loginName)) || StringUtils.isBlank(password)) {
			sr.setErrorNO(EnumServiceError.FAILED.getCode());
			sr.setErrorInfo("input.required");
			sr.setMsgArgs("登录账号和密码");
			return sr;
		}
		// 检查数据库,账号密码是否正确

		// TODO 将密码加密
		String passwordMI = messageDigest.digest(password);

		User loginUser = null;
		// 使用登录账号登录
		if (StringUtils.isNotBlank(loginName)) {
			loginUser = userManager.getByUkLoginName(loginName);
		} else {
			// 使用员工号（显示名称displayName）登录
			// 通过员工号，找出当前状态为有效并且员工号为displayName的记录，如果有多条，就取第一条。
			UserQuery query = new UserQuery();
			query.setDisplayName(displayName);
			query.setStatus(EnumEnableStatus.ENABLED.getCode());
			query.setOrderField("id");
			query.setOrderDirection("desc");
			List<User> userLs = userManager.listQuery(query);
			if (CollectionUtils.isEmpty(userLs)) {
				sr.setErrorNO(EnumServiceError.FAILED.getCode());
				sr.setErrorInfo("User.loginName_password.error");
				return sr;
			}
			loginUser = userLs.get(0);
		}

		// 用户不存在
		if (loginUser == null) {
			sr.setErrorNO(EnumServiceError.FAILED.getCode());
			sr.setErrorInfo("User.loginName_password.error");
			return sr;
		}
		// 用户状态为正常
		if (EnumEnableStatus.ENABLED.getCode().equals(loginUser.getStatus()) == false) {
			sr.setErrorNO(EnumServiceError.FAILED.getCode());
			sr.setErrorInfo("User.notEnable");
			return sr;
		}
		// 密码不正确
		if (!passwordMI.equals(loginUser.getPassword())) {
			sr.setErrorNO(EnumServiceError.FAILED.getCode());
			sr.setErrorInfo("User.password.error");
			return sr;
		}
		// TODO 账号密码正确,可在此添加其它功能,如:记录登录次数,登录IP,登录时间等
		if (loginUser != null) {
			// 查询所有的子属性集，以供一次性填写设置
			WebConfigQuery webConfigQuery = new WebConfigQuery();
			webConfigQuery.setCfgGroup(EnumWebCfgGroup.USERMETA.getCode());
			webConfigQuery.setCfgName_bw(loginUser.getLoginName() + "_");
			loginUser.setUserMetas(webConfigManager.listQuery(webConfigQuery));
		}
		UserAgent userAgent = new UserAgent(loginUser);

		// 查询这个用户下的角色集。
		if (loginUser != null
				&& CollectionUtils.isNotEmpty(loginUser.getUserMetas())) {
			for (WebConfig userMeta : loginUser.getUserMetas()) {
				if (userMeta.getCfgName().endsWith(
						"_" + ConstantDTO.userMeta_Key_ROLEKEY)) {
					// 拆分角色权限关联集.如果角色有多个权限,则值记录多个权限的KEY,之间用英文逗号分隔.
					String[] roleKeyArray = userMeta.getCfgValue().split(",");
					if (ArrayUtils.isEmpty(roleKeyArray))
						continue;
					for (String roleKey : roleKeyArray) {
						userAgent.getRoleKeys().add(roleKey);
						if (CacheDTO.roleMap.get(roleKey) != null) {
							userAgent.getRoleList().add(
									CacheDTO.roleMap.get(roleKey));
						}
					}
					break;
				}
			}
		}
		sr.setDataObj(userAgent);
		return sr;
	}

}
