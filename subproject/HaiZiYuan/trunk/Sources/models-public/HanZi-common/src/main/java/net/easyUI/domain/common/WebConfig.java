/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.domain.common;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.easyUI.dto.common.enums.EnumWebCfgCacheType;
import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.common.dto.BaseDTO;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class WebConfig extends BaseDTO {
	private static final long serialVersionUID = 5454155825314635342L;

	// alias
	public static final String TABLE_ALIAS = "WebConfig";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_CFG_NAME = "名称";
	public static final String ALIAS_CFG_TITLE = "标题";
	public static final String ALIAS_CFG_VALUE = "值";
	public static final String ALIAS_RANK = "排序权重";
	public static final String ALIAS_CFG_GROUP = "分组";
	public static final String ALIAS_MEMO = "备注";
	public static final String ALIAS_CACHE_TYPE = "缓存方式";
	public static final String ALIAS_META1 = "扩展字段1";
	public static final String ALIAS_META2 = "扩展字段2";
	public static final String ALIAS_META3 = "扩展字段3";

	// TODO dwzText请修改WebConfig
	public String getDwzText() {
		return this.getCfgTitle();
	}

	// 可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	// columns START

	private String cacheType;
	private Date gmtModify;
	private Date gmtCreate;
	private Long id;
	private String cfgName;
	private String cfgTitle;
	private String cfgValue;

	private Float rank;
	private String cfgGroup;
	private String memo;
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

	public WebConfig() {
	}

	public WebConfig(Long id) {
		this.id = id;
	}

	// ////////////////////////////////////////////////////

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map toMap() {
		Map map = new HashMap();
		map.put("id", this.getId());
		map.put("cfgName", this.getCfgName());
		map.put("cfgTitle", this.getCfgTitle());
		map.put("cfgValue", this.getCfgValue());
		map.put("rank", this.getRank());
		map.put("cfgGroup", this.getCfgGroup());
		map.put("memo", this.getMemo());
		map.put("cacheType", this.getCacheType());
		map.put("gmtModify", this.getGmtModify());
		map.put("gmtCreate", this.getGmtCreate());
		map.put("permission", this.getPermission());
		map.put("inputType", this.getInputType());
		map.put("operator", this.getOperator());
		map.put("meta32", this.getMeta32());
		map.put("meta64", this.getMeta64());
		map.put("meta128", this.getMeta128());
		map.put("dataStatus", this.getDataStatus());
		map.put("dataUnit", this.getDataUnit());
		return map;
	}

	/**
	 * @param webConfigName
	 *            要查找的网站配置name,并将Value值组装成List对象
	 * @return 返回缓存中,网站配置Map中,Key为 webConfigName 的配置对象的Value值
	 */
	@SuppressWarnings("rawtypes")
	public Collection getCfgValueJsonList() {
		if (cfgValue == null)
			return null;
		ObjectMapper mapper = new ObjectMapper();
		Collection list = null;
		try {
			list = mapper.readValue(cfgValue, Collection.class);
		} catch (JsonParseException e) {
			logger.error("JsonParseException ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException ", e);
		} catch (IOException e) {
			logger.error("IOException ", e);
		}
		return list;
	}

	/**
	 * @param webConfigName
	 *            要查找的网站配置name,并将Value值组装成Map对象
	 * @return 返回缓存中,网站配置Map中,Key为 webConfigName 的配置对象的Value值
	 */
	@SuppressWarnings("rawtypes")
	public Map getCfgValueJsonMap() {
		if (cfgValue == null)
			return null;
		ObjectMapper mapper = new ObjectMapper();
		Map map = null;
		try {
			map = mapper.readValue(cfgValue, Map.class);
		} catch (JsonParseException e) {
			logger.error("JsonParseException ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException ", e);
		} catch (IOException e) {
			logger.error("IOException ", e);
		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	public void setCfgValueJsonMap(Map map) {
		if (map == null) {
			this.cfgValue = "";
			return;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			this.cfgValue = mapper.writeValueAsString(map);
		} catch (JsonParseException e) {
			logger.error("JsonParseException ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException ", e);
		} catch (IOException e) {
			logger.error("IOException ", e);
		}
	}

	/**
	 * @param webConfigName
	 *            要查找的网站配置name,并将Value值组装成Object对象,一般来说,会根据具体JsonString格式,
	 *            自动组装成对应的类型,如Map,List
	 * @return 返回缓存中,网站配置Map中,Key为 webConfigName 的配置对象的Value值
	 */
	public Object getCfgValueJson() {
		if (cfgValue == null)
			return null;
		ObjectMapper mapper = new ObjectMapper();
		Object object = null;
		try {
			object = mapper.readValue(cfgValue, Object.class);
		} catch (JsonParseException e) {
			logger.error("JsonParseException ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException ", e);
		} catch (IOException e) {
			logger.error("IOException ", e);
		}
		return object;
	}

	// ////////////////////////////////////////////////////////

	public void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return this.id;
	}

	public void setCfgName(String value) {
		this.cfgName = value;
	}

	public String getCfgName() {
		return this.cfgName;
	}

	public void setCfgTitle(String value) {
		this.cfgTitle = value;
	}

	public String getCfgTitle() {
		if (StringUtils.isBlank(this.cfgTitle))
			this.setCfgTitle(this.getCfgName());
		return this.cfgTitle;
	}

	public void setCfgValue(String value) {
		this.cfgValue = value;
	}

	public String getCfgValue() {
		return this.cfgValue;
	}

	public void setRank(Float value) {
		this.rank = value;
	}

	public Float getRank() {
		if (this.rank == null)
			this.setRank(99F);
		return this.rank;
	}

	public void setCfgGroup(String value) {
		this.cfgGroup = value;
	}

	public String getCfgGroup() {
		if (StringUtils.isBlank(this.cfgGroup))
			this.setCfgGroup(EnumWebCfgGroup.WEB_SETTING.getCode());
		return this.cfgGroup;
	}

	public void setMemo(String value) {
		this.memo = value;
	}

	public String getMemo() {
		return this.memo;
	}

	public String getCacheType() {
		if (StringUtils.isBlank(this.cacheType))
			this.setCacheType(EnumWebCfgCacheType.NONE.getCode());
		return cacheType;
	}

	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
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
