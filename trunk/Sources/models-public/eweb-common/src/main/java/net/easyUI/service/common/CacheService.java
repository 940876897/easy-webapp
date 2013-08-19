package net.easyUI.service.common;

import java.util.Map;

import net.easyUI.common.dto.ServiceResult;

/**
 * 系统启动时初始工作类
 * 
 * @author busfly http://www.busfly.net
 * 
 */
public interface CacheService {
	/**
	 * BeansInitService 初始时,执行此方法,目的是为了在这个方法中将系统需要的初始化数据进行加载
	 * 
	 * @return ServiceResult<Map<String,String>> Key为方法名, Value暂定为处理情况信息,
	 *         如:执行了哪些方法, 哪些成功, 哪些失败, 执行完成时间(yyyy-MM-dd HH:mm:ss.S)等.
	 */
	public ServiceResult<Map<String, String>> reloadAllCache();

	/**
	 * 初始化角色,权限数据到系统缓存中,如果角色和权限相关的数据发生变化,需要重新加载到缓存. <BR>
	 * user-> userMeta(userId,key[roleKey],value[roleKey]) ->role
	 * ->roleMeta(enabled||permissionKey) -> Permission
	 * ->PermissionMeta(enabled)
	 * 
	 * 一般加载完成后,需要reFlashRolePermission()将中央缓存同步到本服务器缓存
	 * 
	 * @return ServiceResult<String> String暂定为执行完成时间(yyyy-MM-dd HH:mm:ss.S)
	 */
//	public ServiceResult<String> reloadRolePermission();

	/**
	 * 刷新同步角色,权限数据系统缓存
	 */
//	public ServiceResult<String> reFlashRolePermission();

	/**
	 * 重新加载系统配置到中央系统缓存中,如果系统配置相关的数据发生变化,需要重新加载到缓存.
	 * 一般加载完成后,需要reFlashWebConfig()将中央缓存同步到本服务器缓存
	 */
//	public ServiceResult<String> reloadWebConfig();

	/**
	 * 刷新同步系统配置缓存
	 * 
	 * @return
	 */
	public ServiceResult<String> reFlashWebConfig();
}
