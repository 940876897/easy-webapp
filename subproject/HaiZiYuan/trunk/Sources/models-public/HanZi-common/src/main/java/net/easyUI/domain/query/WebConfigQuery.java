/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.domain.query;

import java.util.List;

import net.easyUI.domain.common.WebConfig;
import net.easyUI.dto.common.query.DwzBaseQuery;

public class WebConfigQuery extends DwzBaseQuery<WebConfig> {
	private static final long serialVersionUID = 3148176768559230877L;

	private String cacheType;

	// ========= id start =========
	private Long id; // 等于
	private List<Long> id_in;// IN 子句 ,属于
	// ========= id end =========

	// ========= 名称 start =========
	private String cfgName; // 等于
	private List<String> cfgName_in;//
	private String cfgName_cn;// like,包含
	private String cfgName_ew; // 右边像
	private String cfgName_bw; // 左边像
	// ========= 名称 end =========

	// ========= 标题 start =========
	private String cfgTitle; // 等于
	private String cfgTitle_cn;// like,包含
	// ========= 标题 end =========

	// ========= 值 start =========
	private String cfgValue; // 等于
	private String cfgValue_cn;// 包含字符
	// ========= 值 end =========

	// ========= 分组 start =========
	private String cfgGroup; // 等于

	// ========= 分组 end =========
	// ////////////////////////////////////////////////////
	/** 扩展字段1 varchar(32) */
	private String meta32;
	/** 扩展字段2 varchar(64) */
	private String meta64;
	/** 扩展字段3 varchar(128) */
	private String meta128;
	/** 数据单位 */
	private String dataUnit;
	/** 数据状态：valid有效、invalid无效 */
	private String dataStatus;
	/** 操作者 */
	private String operator;
	/** 数据输入类型 */
	private String inputType;
	/** 数据管理权限 */
	private String permission;

	// columns END
	
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

	// ======== CfgName 开始 ========
	// 等于
	public String getCfgName() {
		return this.cfgName;
	}

	public void setCfgName(String value) {
		this.cfgName = value;
		this.filterMap.put("cfgName", value);
	}

	// <!-- 模糊查询 like -->
	public String getCfgName_cn() {
		return this.cfgName_cn;
	}

	public void setCfgName_cn(String value) {
		this.cfgName_cn = value;
		this.filterMap.put("cfgName_cn", value);
	}

	// ======== CfgName 结束 ========

	// ======== CfgTitle 开始 ========
	// 等于
	public String getCfgTitle() {
		return this.cfgTitle;
	}

	public void setCfgTitle(String value) {
		this.cfgTitle = value;
		this.filterMap.put("cfgTitle", value);
	}

	// <!-- 模糊查询 like -->
	public String getCfgTitle_cn() {
		return this.cfgTitle_cn;
	}

	public void setCfgTitle_cn(String value) {
		this.cfgTitle_cn = value;
		this.filterMap.put("cfgTitle_cn", value);
	}

	// ======== CfgTitle 结束 ========

	// ======== CfgValue 开始 ========
	// 等于
	public String getCfgValue() {
		return this.cfgValue;
	}

	public void setCfgValue(String value) {
		this.cfgValue = value;
		this.filterMap.put("cfgValue", value);
	}

	// <!-- 左模糊查询，右边不像XXX -->
	public String getCfgValue_cn() {
		return this.cfgValue_cn;
	}

	public void setCfgValue_cn(String value) {
		this.cfgValue_cn = value;
		this.filterMap.put("cfgValue_en", value);
	}

	// ======== CfgValue 结束 ========

	// ======== CfgGroup 开始 ========
	// 等于
	public String getCfgGroup() {
		return this.cfgGroup;
	}

	public void setCfgGroup(String value) {
		this.cfgGroup = value;
		this.filterMap.put("cfgGroup", value);
	}

	public String getCacheType() {
		return cacheType;
	}

	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
		this.filterMap.put("cacheType", cacheType);
	}

	public void setCfgName_ew(String cfgName_ew) {
		this.cfgName_ew = cfgName_ew;
		this.filterMap.put("cfgName_ew", cfgName_ew);
	}

	public String getCfgName_ew() {
		return this.cfgName_ew;
	}

	public List<String> getCfgName_in() {
		return cfgName_in;
	}

	public void setCfgName_in(List<String> cfgName_in) {
		this.filterMap.put("cfgName_in", cfgName_in);
		this.cfgName_in = cfgName_in;
	}

	public String getCfgName_bw() {
		return cfgName_bw;
	}

	public void setCfgName_bw(String cfgName_bw) {
		this.filterMap.put("cfgName_bw", cfgName_bw);
		this.cfgName_bw = cfgName_bw;
	}

	public String getDataUnit() {
		return dataUnit;
	}

	public void setDataUnit(String dataUnit) {
		this.dataUnit = dataUnit;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getMeta32() {
		return meta32;
	}

	public void setMeta32(String meta32) {
		this.meta32 = meta32;
	}

	public String getMeta64() {
		return meta64;
	}

	public void setMeta64(String meta64) {
		this.meta64 = meta64;
	}

	public String getMeta128() {
		return meta128;
	}

	public void setMeta128(String meta128) {
		this.meta128 = meta128;
	}

}
