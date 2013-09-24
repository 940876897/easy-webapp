package net.easyUI.dto.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.easyUI.domain.common.Permission;
import net.easyUI.domain.common.Role;
import net.easyUI.domain.common.WebConfig;

/**
 * 系统缓存
 * 
 * @author busfly
 * 
 */
public class CacheDTO {

	/** 有效的权限配置集合 */
	public static Collection<Permission> permissionList = new ArrayList<Permission>();
	/** 有效的角色对应的权限集合 */
	public static Map<String, Collection<Permission>> rolePermissionMap = new HashMap<String, Collection<Permission>>();
	/** 有效的角色缓存,Key为角色Key,Value为角色对象 */
	public static Map<String, Role> roleMap = new HashMap<String, Role>();
	/** 需要权限的URI缓存Map, Key为URI,Value为可操作的权限集 */
	// public static Map<String, List<Permission>> needControlcaches = new
	// ConcurrentHashMap<String, List<Permission>>();
	/** 需要权限的URI缓存Map, Key为URI,Value为可操作的权限集中最终生效的那一个权限(不包含登录权限和超级权限) */
	// public static Map<String, Permission> canControlcaches = new
	// ConcurrentHashMap<String, Permission>();
	/** 不需要权限的URI缓存Map , Key为URI,Value表示不需要控制 */
	// public static Map<String, Integer> noControlCaches = new
	// ConcurrentHashMap<String, Integer>();// 没有配置AdminAccess的method
	/** URI对应的页面类型,主要提供给系统错误和异常时,跳转页面的类型时使用 */
	// public static Map<String, EnumPageType> pageTypeMap = new HashMap<String,
	// EnumPageType>();
	
	/** 网站配置WebConfig缓存 其中Key为:webConfig.getCfgGroup() + "_" + webConfig.getCfgName(),值为WebConfig对象*/
	public static Map<String, WebConfig> webConfigMap = new HashMap<String, WebConfig>();
	
	/** 数据库中保存的VM模板缓存,Key为VM的路径文件名,Value为模板内容. **/
	public static Map<String, String> dbVMs = new HashMap<String, String>();

	// 从缓存中获取国际化信息设置. {msgKey1_zh_CN:value,msgKey2_zh_CN:value, ... }
	public static Map<String, String> i18nMsg = new HashMap<String, String>();

	public static void clearCaches() {
		CacheDTO.webConfigMap.clear();
		CacheDTO.permissionList.clear();
		CacheDTO.rolePermissionMap.clear();
		CacheDTO.roleMap.clear();
		CacheDTO.i18nMsg.clear();
		CacheDTO.dbVMs.clear();
	}
}
