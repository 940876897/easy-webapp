/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.domain.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.common.dto.BaseDTO;
import net.easyUI.common.dto.UserDto;
import net.easyUI.common.util.DateUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class User extends BaseDTO implements UserDto {
	private static final long serialVersionUID = 5454155825314635342L;

	// alias
	public static final String TABLE_ALIAS = "用户";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_LOGIN_NAME = "登录名";
	public static final String ALIAS_PASSWORD = "登录密码";
	public static final String ALIAS_NICENAME = "用户昵称";
	public static final String ALIAS_EMAIL = "用户邮箱";
	public static final String ALIAS_WEBSITE = "网站URL";
	public static final String ALIAS_GMT_REGISTERED = "注册时间";
	public static final String ALIAS_STATUS = "账号状态";
	public static final String ALIAS_DISPLAY_NAME = "显示名称";

    private List<WebConfig> userMetas = new ArrayList<WebConfig>(0);
    private Map<String, String> metaMap = new HashMap<String, String>();
    
    private List<Role> roles = new ArrayList<Role>(0);
    
    
	// TODO dwzText请修改User
	public String getDwzText() {
		return this.getLoginName();
	}

	// 可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	// columns START

	private Long id;
	private String loginName;
	private String password;
	private String nicename;//用来保存员工表ID
	private String email;
	private String website;
	private Date gmtRegistered;
	private Integer status;
	private String displayName; //用来保存员工号


	// columns END

	public User() {
	}

	public User(UserDto user) {
		super();
		this.id = user.getId();
		this.loginName = user.getLoginName();
		this.displayName = user.getDisplayName();
	}

	public User(Long id) {
		this.id = id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return this.id;
	}

	public void setLoginName(String value) {
		this.loginName = value;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public String getPassword() {
		return this.password;
	}

	public void setNicename(String value) {
		this.nicename = value;
	}

	public String getNicename() {
		return this.nicename;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public String getEmail() {
		return this.email;
	}

	public void setWebsite(String value) {
		this.website = value;
	}

	public String getWebsite() {
		return this.website;
	}

	/**
	 * "yyyy-MM-dd"
	 */
	public String getGmtRegisteredString() {
		return DateUtil.dateToStr(getGmtRegistered());
	}

	/**
	 * "yyyy-MM-dd"
	 */
	public void setGmtRegisteredString(String value) {
		setGmtRegistered(DateUtil.strToDate(DateUtil.DATE_FORMAT, value));
	}

	/**
	 * "yyyy-MM-dd HH:mm:ss"
	 */
	public String getGmtRegisteredTimeString() {
		return DateUtil.dateTimeToStr(DateUtil.DATE_TIME_FORMAT,
				getGmtRegistered());
	}

	/**
	 * "yyyy-MM-dd HH:mm:ss"
	 */
	public void setGmtRegisteredTimeString(String value) {
		setGmtRegistered(DateUtil.strToDate(DateUtil.DATE_TIME_FORMAT, value));
	}

	public void setGmtRegistered(Date value) {
		this.gmtRegistered = value;
	}

	public Date getGmtRegistered() {
		return this.gmtRegistered;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setDisplayName(String value) {
		this.displayName = value;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public void setUserMetas(List<WebConfig> userMetas) {
        this.userMetas = new ArrayList<WebConfig>();
        metaMap.clear();
        if (CollectionUtils.isNotEmpty(userMetas)) {
            for (WebConfig userMeta : userMetas) {
                userMeta.setCfgGroup(EnumWebCfgGroup.USERMETA.getCode());
                if (StringUtils.isBlank(userMeta.getCfgName()))
                    continue;
                userMeta.setCfgTitle(userMeta.getCfgName());
                this.userMetas.add(userMeta);
                String metaKey = userMeta.getCfgName().substring(
                    userMeta.getCfgName().lastIndexOf("_")+1);
                metaMap.put(metaKey, userMeta.getCfgValue());
            }
        }
	}

	public List<WebConfig> getUserMetas() {
		return userMetas;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

    public Map<String, String> getMetaMap() {
        return metaMap;
    }

    public void setMetaMap(Map<String, String> metaMap) {
        this.metaMap = metaMap;
    }

}
