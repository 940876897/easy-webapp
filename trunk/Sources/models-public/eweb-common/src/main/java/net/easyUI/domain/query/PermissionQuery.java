/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.domain.query;

import java.util.List;

import net.easyUI.domain.common.Permission;
import net.easyUI.dto.common.query.DwzBaseQuery;

import org.apache.commons.lang.StringUtils;

public class PermissionQuery extends DwzBaseQuery<Permission> {
	private static final long serialVersionUID = 3148176768559230877L;

	// ========= 自定义条件 start =========
	// ========= 是否启用，数据在权限扩展表中 =========
	private String enabled; // 1/0
	private String enabled_ne; // 1/0 不等于
	// ========= 自定义条件 start =========

	// ========= id start =========
	private Long id; // 等于
	private List<Long> id_in; // in子句，ID在这个列表中
	private List<Long> id_ni; // not in 子句，ID不在这个列表中。
	// ========= id end =========

	// ========= 权限Key start =========
	private String permissionKey; // 等于
	private List<String> permissionKey_in;
	private String permissionKey_bw; // 右模糊查询，左边像XXX,开始于
	private String permissionKey_cn; // like,包含
	// ========= 权限Key end =========

	// ========= 权限名称 start =========
	private String permissionName; // 等于
	private String permissionName_bw; // 右模糊查询，左边像XXX,开始于
	private String permissionName_cn; // like,包含
	// ========= 权限名称 end =========

	// ========= 资源URI start =========
	private String uri; // 等于
	private String uri_bw; // 右模糊查询，左边像XXX,开始于
	private String uri_cn; // like,包含

	// ========= 资源URI end =========

	// ======== Id 开始 ========
	// 等于
	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
		this.filterMap.put("id", value);
	}

	// ======== Id 结束 ========

	// ======== PermissionKey 开始 ========
	// 等于
	public String getPermissionKey() {
		return this.permissionKey;
	}

	public void setPermissionKey(String value) {
		this.permissionKey = value;
		this.filterMap.put("permissionKey", value);
	}

	// <!-- 右模糊查询，左边像XXX -->
	public String getPermissionKey_bw() {
		return this.permissionKey_bw;
	}

	public void setPermissionKey_bw(String value) {
		this.permissionKey_bw = value;
		this.filterMap.put("permissionKey_bw", value);
	}

	// <!-- 模糊查询 like -->
	public String getPermissionKey_cn() {
		return this.permissionKey_cn;
	}

	public void setPermissionKey_cn(String value) {
		this.permissionKey_cn = value;
		this.filterMap.put("permissionKey_cn", value);
	}

	// ======== PermissionKey 结束 ========

	// ======== PermissionName 开始 ========
	// 等于
	public String getPermissionName() {
		return this.permissionName;
	}

	public void setPermissionName(String value) {
		this.permissionName = value;
		this.filterMap.put("permissionName", value);
	}

	// <!-- 右模糊查询，左边像XXX -->
	public String getPermissionName_bw() {
		return this.permissionName_bw;
	}

	public void setPermissionName_bw(String value) {
		this.permissionName_bw = value;
		this.filterMap.put("permissionName_bw", value);
	}

	// <!-- 模糊查询 like -->
	public String getPermissionName_cn() {
		return this.permissionName_cn;
	}

	public void setPermissionName_cn(String value) {
		this.permissionName_cn = value;
		this.filterMap.put("permissionName_cn", value);
	}

	// ======== PermissionName 结束 ========

	// ======== Uri 开始 ========
	// 等于
	public String getUri() {
		return this.uri;
	}

	public void setUri(String value) {
		this.uri = value;
		this.filterMap.put("uri", value);
	}

	// <!-- 右模糊查询，左边像XXX -->
	public String getUri_bw() {
		return this.uri_bw;
	}

	public void setUri_bw(String value) {
		this.uri_bw = value;
		this.filterMap.put("uri_bw", value);
	}

	// <!-- 模糊查询 like -->
	public String getUri_cn() {
		return this.uri_cn;
	}

	public void setUri_cn(String value) {
		this.uri_cn = value;
		this.filterMap.put("uri_cn", value);
	}

	// ======== Uri 结束 ========

	public List<Long> getId_in() {
		return id_in;
	}

	public void setId_in(List<Long> id_in) {
		this.id_in = id_in;
	}

	public List<String> getPermissionKey_in() {
		return permissionKey_in;
	}

	public void setPermissionKey_in(List<String> permissionKey_in) {
		this.permissionKey_in = permissionKey_in;
	}

	public String getEnabled() {
		if (!"0".equals(this.enabled) && StringUtils.isNotBlank(enabled))
			this.enabled = "1";
		return enabled;
	}

	public void setEnabled(String enabled) {
		if (!"0".equals(enabled))
			this.enabled = "1";
		else
			this.enabled = enabled;
	}

	public List<Long> getId_ni() {
		return id_ni;
	}

	public void setId_ni(List<Long> id_ni) {
		this.id_ni = id_ni;
	}

	public String getEnabled_ne() {
		return enabled_ne;
	}

	public void setEnabled_ne(String enabled_ne) {
		this.enabled_ne = enabled_ne;
	}

}
