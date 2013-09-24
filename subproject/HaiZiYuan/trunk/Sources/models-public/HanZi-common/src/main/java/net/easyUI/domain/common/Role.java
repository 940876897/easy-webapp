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

public class Role extends BaseDTO {
	private static final long serialVersionUID = 5454155825314635342L;

	// alias
	public static final String TABLE_ALIAS = "角色";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ROLE_KEY = "角色Key";
	public static final String ALIAS_ROLE_NAME = "角色名称";
	public static final String ALIAS_MEMO = "备注说明";

    private List<WebConfig> roleMetas = new ArrayList<WebConfig>(0);
    private Map<String, String> metaMap = new HashMap<String, String>();

    private List<Permission> permissions = new ArrayList<Permission>(0);
    
	// TODO dwzText请修改Role
	public String getDwzText() {
		return this.getRoleName();
	}

	// 可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	// columns START

	private Long id;
	private String roleKey;
	private String roleName;
	private String memo;

	// columns END

	public Role() {
	}

	public Role(Long id) {
		this.id = id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return this.id;
	}

	public void setRoleKey(String value) {
		this.roleKey = value;
	}

	public String getRoleKey() {
		return this.roleKey;
	}

	public void setRoleName(String value) {
		this.roleName = value;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setMemo(String value) {
		this.memo = value;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setRoleMetas(List<WebConfig> roleMetas) {
        this.roleMetas = new ArrayList<WebConfig>();
		metaMap.clear();

        if (CollectionUtils.isNotEmpty(roleMetas)) {
            for (WebConfig roleMeta : roleMetas) {
                roleMeta.setCfgGroup(EnumWebCfgGroup.ROLEMETA.getCode());
                if (StringUtils.isBlank(roleMeta.getCfgName()))
                    continue;
                roleMeta.setCfgTitle(roleMeta.getCfgName());
                this.roleMetas.add(roleMeta);
                String metaKey = roleMeta.getCfgName().substring(
                    roleMeta.getCfgName().lastIndexOf("_")+1);
                metaMap.put(metaKey, roleMeta.getCfgValue());
            }
        }

	}

	public List<WebConfig> getRoleMetas() {
		return roleMetas;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Map<String, String> getMetaMap() {
		return metaMap;
	}

	public void setMetaMap(Map<String, String> metaMap) {
		this.metaMap = metaMap;
	}

}
