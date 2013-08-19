package net.easyUI.dto.common;

/**
 * 常量类
 * 
 * @author busfly www.easyui.net
 * 
 */
public class ConstantDTO {
	/** 数据库中保存的比率与百分比之间的关系，dbRate = Percent乘以100 */
	public static int dbRatePercent=100;
	/** 数据库中保存的比率与实际数值之间的关系，dbRate = Real乘以10000 */
	public static int dbRateReal=10000;
	
	/** permission表中表示游客能访问的资源Key常量前缀, 如guest_01,guest_02 */
	public static String permissionKey_GUEST_PRE = "guest";
	/** permission表中表示登录后可操作的权限Key常量前缀(任何人都可以访问的页面,只需要登录) */
	public static String permissionKey_LOGIN_PRE = "login";
	/** permission表中表示超级权限KEY常量前缀 */
	public static String permissionKey_SUPPERADMIN_PRE = "supper";

	/** permissionMeta表中表示生效状态的Key的常量 */
	public static String permissionMeta_Key_ENABLED = "enabled";
	public static String permissionMeta_Value_NOT_ENABLED = "0";

	/** roleMeta表中表示与权限关联的Key的常量 */
	public static String roleMeta_Key_PERMISSIONKEY = "permissionKey";
	/** roleMeta表中表示生效状态的Key的常量 */
	public static String roleMeta_Key_ENABLED = "enabled";
	public static String roleMeta_Value_NOT_ENABLED = "0";
	/**超级角色KEY*/
	public static String role_SUPPER = "supper";

	/** userMeta表中表示与角色关联的Key的常量 */
	public static String userMeta_Key_ROLEKEY = "roleKey";


	public String getPermissionKey_GUEST_PRE() {
		return permissionKey_GUEST_PRE;
	}

	public void setPermissionKey_GUEST_PRE(String permissionKey_GUEST_PRE) {
		ConstantDTO.permissionKey_GUEST_PRE = permissionKey_GUEST_PRE;
	}

	public String getPermissionKey_LOGIN_PRE() {
		return permissionKey_LOGIN_PRE;
	}

	public void setPermissionKey_LOGIN_PRE(String permissionKey_LOGIN_PRE) {
		ConstantDTO.permissionKey_LOGIN_PRE = permissionKey_LOGIN_PRE;
	}

	public String getPermissionKey_SUPPERADMIN_PRE() {
		return permissionKey_SUPPERADMIN_PRE;
	}

	public void setPermissionKey_SUPPERADMIN_PRE(
			String permissionKey_SUPPERADMIN_PRE) {
		ConstantDTO.permissionKey_SUPPERADMIN_PRE = permissionKey_SUPPERADMIN_PRE;
	}

	public String getPermissionMeta_Key_ENABLED() {
		return permissionMeta_Key_ENABLED;
	}

	public void setPermissionMeta_Key_ENABLED(String permissionMeta_Key_ENABLED) {
		ConstantDTO.permissionMeta_Key_ENABLED = permissionMeta_Key_ENABLED;
	}

	public String getPermissionMeta_Value_NOT_ENABLED() {
		return permissionMeta_Value_NOT_ENABLED;
	}

	public void setPermissionMeta_Value_NOT_ENABLED(
			String permissionMeta_Value_NOT_ENABLED) {
		ConstantDTO.permissionMeta_Value_NOT_ENABLED = permissionMeta_Value_NOT_ENABLED;
	}

	public String getRoleMeta_Key_PERMISSIONKEY() {
		return roleMeta_Key_PERMISSIONKEY;
	}

	public void setRoleMeta_Key_PERMISSIONKEY(String roleMeta_Key_PERMISSIONKEY) {
		ConstantDTO.roleMeta_Key_PERMISSIONKEY = roleMeta_Key_PERMISSIONKEY;
	}

	public String getRoleMeta_Key_ENABLED() {
		return roleMeta_Key_ENABLED;
	}

	public void setRoleMeta_Key_ENABLED(String roleMeta_Key_ENABLED) {
		ConstantDTO.roleMeta_Key_ENABLED = roleMeta_Key_ENABLED;
	}

	public String getRoleMeta_Value_NOT_ENABLED() {
		return roleMeta_Value_NOT_ENABLED;
	}

	public void setRoleMeta_Value_NOT_ENABLED(String roleMeta_Value_NOT_ENABLED) {
		ConstantDTO.roleMeta_Value_NOT_ENABLED = roleMeta_Value_NOT_ENABLED;
	}

	public String getUserMeta_Key_ROLEKEY() {
		return userMeta_Key_ROLEKEY;
	}

	public void setUserMeta_Key_ROLEKEY(String userMeta_Key_ROLEKEY) {
		ConstantDTO.userMeta_Key_ROLEKEY = userMeta_Key_ROLEKEY;
	}

    public static String getRole_SUPPER() {
        return role_SUPPER;
    }

    public static void setRole_SUPPER(String role_SUPPER) {
        ConstantDTO.role_SUPPER = role_SUPPER;
    }

}
