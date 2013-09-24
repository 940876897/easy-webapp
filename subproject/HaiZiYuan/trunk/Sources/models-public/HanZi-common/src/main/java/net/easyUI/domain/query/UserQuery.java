/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.domain.query;

import java.util.Date;
import java.util.List;

import net.easyUI.domain.common.User;
import net.easyUI.dto.common.query.DwzBaseQuery;

public class UserQuery extends DwzBaseQuery<User> {
	private static final long serialVersionUID = 3148176768559230877L;

	// ========= ID start =========
	private Long id; // 等于
	private List<Long> id_in;// IN 子句 ,属于
	// ========= ID end =========

	// ========= 登录名 start =========
	private String loginName; // 等于
	private List<String> loginName_in;// IN 子句 ,属于
	private String loginName_bw;// 右模糊查询，左边像XXX,开始于
	private String loginName_cn;// like,包含
	// ========= 登录名 end =========

	// ========= 登录密码 start =========
	private String password; // 等于
	// ========= 登录密码 end =========

	// ========= 用户昵称 start =========
	private String nicename; // 等于
	private List<String> nicename_in;// IN 子句 ,属于
	private String nicename_bw;// 右模糊查询，左边像XXX,开始于
	private String nicename_cn;// like,包含
	// ========= 用户昵称 end =========

	// ========= 用户邮箱 start =========
	private String email; // 等于
	private List<String> email_in;// IN 子句 ,属于
	private String email_bw;// 右模糊查询，左边像XXX,开始于
	private String email_cn;// like,包含
	// ========= 用户邮箱 end =========

	// ========= 网站URL start =========
	private String website; // 等于
	private List<String> website_in;// IN 子句 ,属于
	private String website_bw;// 右模糊查询，左边像XXX,开始于
	private String website_cn;// like,包含
	// ========= 网站URL end =========

	// ========= 注册时间 start =========
	private Date gmtRegistered; // 等于
	private Date gmtRegistered_ne;// 不等于
	private Date gmtRegistered_lt;// 小于
	private Date gmtRegistered_le;// 小于等于
	private Date gmtRegistered_gt;// 大于
	private Date gmtRegistered_ge;// 大于等于
	// ========= 注册时间 end =========

	// ========= 账号状态 start =========
	private Integer status; // 等于
	private List<Integer> status_in;// IN 子句 ,属于
	private List<Integer> status_ni;// not in 子句,不属于
	// ========= 账号状态 end =========

	// ========= 显示名称 start =========
	private String displayName; // 等于
	private List<String> displayName_in;// IN 子句 ,属于
	private String displayName_bw;// 右模糊查询，左边像XXX,开始于
	private String displayName_cn;// like,包含

	// ========= 显示名称 end =========

	// ======== Id 开始 ========
	// 等于
	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
		this.filterMap.put("id", value);
	}

	// <!-- IN 子句 -->
	public List<Long> getId_in() {
		return this.id_in;
	}

	public void setId_in(List<Long> value) {
		this.id_in = value;
		this.filterMap.put("id_in", value);
	}

	// ======== Id 结束 ========

	// ======== LoginName 开始 ========
	// 等于
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String value) {
		this.loginName = value;
		this.filterMap.put("loginName", value);
	}

	// <!-- IN 子句 -->
	public List<String> getLoginName_in() {
		return this.loginName_in;
	}

	public void setLoginName_in(List<String> value) {
		this.loginName_in = value;
		this.filterMap.put("loginName_in", value);
	}

	// <!-- 右模糊查询，左边像XXX -->
	public String getLoginName_bw() {
		return this.loginName_bw;
	}

	public void setLoginName_bw(String value) {
		this.loginName_bw = value;
		this.filterMap.put("loginName_bw", value);
	}

	// <!-- 模糊查询 like -->
	public String getLoginName_cn() {
		return this.loginName_cn;
	}

	public void setLoginName_cn(String value) {
		this.loginName_cn = value;
		this.filterMap.put("loginName_cn", value);
	}

	// ======== LoginName 结束 ========

	// ======== Password 开始 ========
	// 等于
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String value) {
		this.password = value;
		this.filterMap.put("password", value);
	}

	// ======== Password 结束 ========

	// ======== Nicename 开始 ========
	// 等于
	public String getNicename() {
		return this.nicename;
	}

	public void setNicename(String value) {
		this.nicename = value;
		this.filterMap.put("nicename", value);
	}

	// <!-- IN 子句 -->
	public List<String> getNicename_in() {
		return this.nicename_in;
	}

	public void setNicename_in(List<String> value) {
		this.nicename_in = value;
		this.filterMap.put("nicename_in", value);
	}

	// <!-- 右模糊查询，左边像XXX -->
	public String getNicename_bw() {
		return this.nicename_bw;
	}

	public void setNicename_bw(String value) {
		this.nicename_bw = value;
		this.filterMap.put("nicename_bw", value);
	}

	// <!-- 模糊查询 like -->
	public String getNicename_cn() {
		return this.nicename_cn;
	}

	public void setNicename_cn(String value) {
		this.nicename_cn = value;
		this.filterMap.put("nicename_cn", value);
	}

	// ======== Nicename 结束 ========

	// ======== Email 开始 ========
	// 等于
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String value) {
		this.email = value;
		this.filterMap.put("email", value);
	}

	// <!-- IN 子句 -->
	public List<String> getEmail_in() {
		return this.email_in;
	}

	public void setEmail_in(List<String> value) {
		this.email_in = value;
		this.filterMap.put("email_in", value);
	}

	// <!-- 右模糊查询，左边像XXX -->
	public String getEmail_bw() {
		return this.email_bw;
	}

	public void setEmail_bw(String value) {
		this.email_bw = value;
		this.filterMap.put("email_bw", value);
	}

	// <!-- 模糊查询 like -->
	public String getEmail_cn() {
		return this.email_cn;
	}

	public void setEmail_cn(String value) {
		this.email_cn = value;
		this.filterMap.put("email_cn", value);
	}

	// ======== Email 结束 ========

	// ======== Website 开始 ========
	// 等于
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String value) {
		this.website = value;
		this.filterMap.put("website", value);
	}

	// <!-- IN 子句 -->
	public List<String> getWebsite_in() {
		return this.website_in;
	}

	public void setWebsite_in(List<String> value) {
		this.website_in = value;
		this.filterMap.put("website_in", value);
	}

	// <!-- 右模糊查询，左边像XXX -->
	public String getWebsite_bw() {
		return this.website_bw;
	}

	public void setWebsite_bw(String value) {
		this.website_bw = value;
		this.filterMap.put("website_bw", value);
	}

	// <!-- 模糊查询 like -->
	public String getWebsite_cn() {
		return this.website_cn;
	}

	public void setWebsite_cn(String value) {
		this.website_cn = value;
		this.filterMap.put("website_cn", value);
	}

	// ======== Website 结束 ========

	// ======== GmtRegistered 开始 ========
	// 等于
	public Date getGmtRegistered() {
		return this.gmtRegistered;
	}

	public void setGmtRegistered(Date value) {
		this.gmtRegistered = value;
		this.filterMap.put("gmtRegistered", value);
	}

	// <!-- 不等于 -->
	public Date getGmtRegistered_ne() {
		return this.gmtRegistered_ne;
	}

	public void setGmtRegistered_ne(Date value) {
		this.gmtRegistered_ne = value;
		this.filterMap.put("gmtRegistered_ne", value);
	}

	// <!-- 小于 -->
	public Date getGmtRegistered_lt() {
		return this.gmtRegistered_lt;
	}

	public void setGmtRegistered_lt(Date value) {
		this.gmtRegistered_lt = value;
		this.filterMap.put("gmtRegistered_lt", value);
	}

	// <!-- 小于等于 -->
	public Date getGmtRegistered_le() {
		return this.gmtRegistered_le;
	}

	public void setGmtRegistered_le(Date value) {
		this.gmtRegistered_le = value;
		this.filterMap.put("gmtRegistered_le", value);
	}

	// <!-- 大于 -->
	public Date getGmtRegistered_gt() {
		return this.gmtRegistered_gt;
	}

	public void setGmtRegistered_gt(Date value) {
		this.gmtRegistered_gt = value;
		this.filterMap.put("gmtRegistered_gt", value);
	}

	// <!-- 大于等于 -->
	public Date getGmtRegistered_ge() {
		return this.gmtRegistered_ge;
	}

	public void setGmtRegistered_ge(Date value) {
		this.gmtRegistered_ge = value;
		this.filterMap.put("gmtRegistered_ge", value);
	}

	// ======== GmtRegistered 结束 ========

	// ======== Status 开始 ========
	// 等于
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer value) {
		this.status = value;
		this.filterMap.put("status", value);
	}

	// <!-- IN 子句 -->
	public List<Integer> getStatus_in() {
		return this.status_in;
	}

	public void setStatus_in(List<Integer> value) {
		this.status_in = value;
		this.filterMap.put("status_in", value);
	}

	// <!-- not in 子句 -->
	public List<Integer> getStatus_ni() {
		return this.status_ni;
	}

	public void setStatus_ni(List<Integer> value) {
		this.status_ni = value;
		this.filterMap.put("status_ni", value);
	}

	// ======== Status 结束 ========

	// ======== DisplayName 开始 ========
	// 等于
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String value) {
		this.displayName = value;
		this.filterMap.put("displayName", value);
	}

	// <!-- IN 子句 -->
	public List<String> getDisplayName_in() {
		return this.displayName_in;
	}

	public void setDisplayName_in(List<String> value) {
		this.displayName_in = value;
		this.filterMap.put("displayName_in", value);
	}

	// <!-- 右模糊查询，左边像XXX -->
	public String getDisplayName_bw() {
		return this.displayName_bw;
	}

	public void setDisplayName_bw(String value) {
		this.displayName_bw = value;
		this.filterMap.put("displayName_bw", value);
	}

	// <!-- 模糊查询 like -->
	public String getDisplayName_cn() {
		return this.displayName_cn;
	}

	public void setDisplayName_cn(String value) {
		this.displayName_cn = value;
		this.filterMap.put("displayName_cn", value);
	}

	// ======== DisplayName 结束 ========

}
