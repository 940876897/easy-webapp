/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.domain.query;

import java.util.List;

import net.easyUI.domain.common.Role;
import net.easyUI.dto.common.query.DwzBaseQuery;

public class RoleQuery extends DwzBaseQuery<Role> {
	private static final long serialVersionUID = 3148176768559230877L;

	// ========= 扩展条件 start =========
	// ========= 是否启用，数据在角色扩展表中 =========
	private String enabled; // 1/0
	private String enabled_ne; // 1/0 不等于
	// ========= 扩展条件 start =========

	// ========= id start =========
	private Long id; // 等于
	private List<Long> id_in;// IN 子句 ,属于
	private List<Long> id_ni;// Not IN 子句 ,属于
	// ========= id end =========

	// ========= 角色Key start =========
	private String roleKey; // 等于
	private List<String> roleKey_in;// IN 子句 ,属于
	private String roleKey_bw;// 右模糊查询，左边像XXX,开始于
	private String roleKey_cn;// like,包含
	// ========= 角色Key end =========

	// ========= 角色名称 start =========
	private String roleName; // 等于
	private List<String> roleName_in;// IN 子句 ,属于
	private String roleName_bw;// 右模糊查询，左边像XXX,开始于
	private String roleName_cn;// like,包含

	// ========= 角色名称 end =========

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

	// ======== RoleKey 开始 ========
	// 等于
	public String getRoleKey() {
		return this.roleKey;
	}

	public void setRoleKey(String value) {
		this.roleKey = value;
		this.filterMap.put("roleKey", value);
	}

	// <!-- IN 子句 -->
	public List<String> getRoleKey_in() {
		return this.roleKey_in;
	}

	public void setRoleKey_in(List<String> value) {
		this.roleKey_in = value;
		this.filterMap.put("roleKey_in", value);
	}

	// <!-- 右模糊查询，左边像XXX -->
	public String getRoleKey_bw() {
		return this.roleKey_bw;
	}

	public void setRoleKey_bw(String value) {
		this.roleKey_bw = value;
		this.filterMap.put("roleKey_bw", value);
	}

	// <!-- 模糊查询 like -->
	public String getRoleKey_cn() {
		return this.roleKey_cn;
	}

	public void setRoleKey_cn(String value) {
		this.roleKey_cn = value;
		this.filterMap.put("roleKey_cn", value);
	}

	// ======== RoleKey 结束 ========

	// ======== RoleName 开始 ========
	// 等于
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String value) {
		this.roleName = value;
		this.filterMap.put("roleName", value);
	}

	// <!-- IN 子句 -->
	public List<String> getRoleName_in() {
		return this.roleName_in;
	}

	public void setRoleName_in(List<String> value) {
		this.roleName_in = value;
		this.filterMap.put("roleName_in", value);
	}

	// <!-- 右模糊查询，左边像XXX -->
	public String getRoleName_bw() {
		return this.roleName_bw;
	}

	public void setRoleName_bw(String value) {
		this.roleName_bw = value;
		this.filterMap.put("roleName_bw", value);
	}

	// <!-- 模糊查询 like -->
	public String getRoleName_cn() {
		return this.roleName_cn;
	}

	public void setRoleName_cn(String value) {
		this.roleName_cn = value;
		this.filterMap.put("roleName_cn", value);
	}

	public List<Long> getId_ni() {
		return id_ni;
	}

	public void setId_ni(List<Long> id_ni) {
		this.id_ni = id_ni;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getEnabled_ne() {
		return enabled_ne;
	}

	public void setEnabled_ne(String enabled_ne) {
		this.enabled_ne = enabled_ne;
	}

}
