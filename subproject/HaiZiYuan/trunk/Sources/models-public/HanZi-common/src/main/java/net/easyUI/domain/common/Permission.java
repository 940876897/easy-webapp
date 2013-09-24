/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.domain.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.common.dto.BaseDTO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class Permission extends BaseDTO {
	private static final long serialVersionUID = 5454155825314635342L;

	private List<WebConfig> permissionMetas = new ArrayList<WebConfig>();
	private Map<String, String> metaMap = new HashMap<String, String>();

	// alias
	public static final String TABLE_ALIAS = "权限";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_PERMISSION_KEY = "权限Key";
	public static final String ALIAS_PERMISSION_NAME = "权限名称";
	public static final String ALIAS_URI = "资源URI";
	public static final String ALIAS_MEMO = "备注说明";

	// TODO dwzText请修改Permission
	public String getDwzText() {
		return this.getPermissionName();
	}

	// 可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	// columns START

	private Long id;
	private String permissionKey;
	private String permissionName;
	private String uri;
	private String memo;

	// columns END

	public Permission() {
	}

	public Permission(Long id) {
		this.id = id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return this.id;
	}

	public Long getPermissionId() {
		return this.id;
	}

	public void setPermissionKey(String value) {
		this.permissionKey = value;
	}

	public String getPermissionKey() {
		return this.permissionKey;
	}

	public void setPermissionName(String value) {
		this.permissionName = value;
	}

	public String getPermissionName() {
		return this.permissionName;
	}

	public void setUri(String value) {
		this.uri = value;
	}

	public String getUri() {
		return this.uri;
	}

	public void setMemo(String value) {
		this.memo = value;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setPermissionMetas(List<WebConfig> permissionMetas) {
		this.permissionMetas = new ArrayList<WebConfig>();
		metaMap.clear();
		if (CollectionUtils.isNotEmpty(permissionMetas)) {
			for (WebConfig permissionMeta : permissionMetas) {
				permissionMeta.setCfgGroup(EnumWebCfgGroup.PERMISSIONMETA
						.getCode());
				if (StringUtils.isBlank(permissionMeta.getCfgName()))
					continue;
				permissionMeta.setCfgTitle(permissionMeta.getCfgName());
				this.permissionMetas.add(permissionMeta);
				String metaKey = permissionMeta.getCfgName().substring(
						permissionMeta.getCfgName().lastIndexOf("_") + 1);
				metaMap.put(metaKey, permissionMeta.getCfgValue());
			}
		}
	}

	public List<WebConfig> getPermissionMetas() {
		return permissionMetas;
	}

	public void setMetaMap(Map<String, String> metaMap) {
		this.metaMap = metaMap;
	}

	public Map<String, String> getMetaMap() {
		return metaMap;
	}

}
