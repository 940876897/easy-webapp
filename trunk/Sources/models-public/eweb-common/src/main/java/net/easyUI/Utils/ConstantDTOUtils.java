package net.easyUI.Utils;

import net.easyUI.dto.common.ConstantDTO;


public class ConstantDTOUtils {
	/** permission表中表示游客能访问的资源Key常量前缀, 如guest_01,guest_02 */
	public static String getPermissionKey_GUEST_PRE() {
		return ConstantDTO.permissionKey_GUEST_PRE;
	}

	/** permission表中表示登录后可操作的权限Key常量前缀(任何人都可以访问的页面,只需要登录) */
	public static String getPermissionKey_LOGIN_PRE() {
		return ConstantDTO.permissionKey_LOGIN_PRE;
	}

	/** permission表中表示超级权限KEY常量前缀 */
	public static String getPermissionKey_SUPPERADMIN_PRE() {
		return ConstantDTO.permissionKey_SUPPERADMIN_PRE;
	}

	/** permissionMeta表中表示生效状态的Key的常量-启用 */
	public static String getPermissionMeta_Key_ENABLED() {
		return ConstantDTO.permissionMeta_Key_ENABLED;
	}
	/** permissionMeta表中表示生效状态的Key的常量-禁用 */
	public static String getPermissionMeta_Value_NOT_ENABLED() {
		return ConstantDTO.permissionMeta_Value_NOT_ENABLED;
	}

	/** roleMeta表中表示与权限关联的Key的常量 */
	public static String getRoleMeta_Key_PERMISSIONKEY() {
		return ConstantDTO.roleMeta_Key_PERMISSIONKEY;
	}

	/** roleMeta表中表示生效状态的Key的常量--启用 */
	public static String getRoleMeta_Key_ENABLED() {
		return ConstantDTO.roleMeta_Key_ENABLED;
	}
	/** roleMeta表中表示生效状态的Key的常量--禁用 */
	public static String getRoleMeta_Value_NOT_ENABLED() {
		return ConstantDTO.roleMeta_Value_NOT_ENABLED;
	}

	/** userMeta表中表示与角色关联的Key的常量 */
	public static String getUserMeta_Key_ROLEKEY() {
		return ConstantDTO.userMeta_Key_ROLEKEY;
	}
}
