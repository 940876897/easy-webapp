package net.easyUI.common.dto;

import net.easyUI.common.dto.enums.EnumDwzJsonStatusType;

import org.apache.commons.logging.Log;

/**
 * <pre>
 * form提交后返回json数据结构
 * statusCode[200]=DWZ.statusCode.ok表示操作成功, 做页面跳转等操作. 
 * statusCode[300]=DWZ.statusCode.error表示操作失败, 提示错误原因. 
 * statusCode[301]=DWZ.statusCode.timeout表示session超时，下次点击时跳转到DWZ.loginUrl
 * {"statusCode":"200", "message":"操作成功", "navTabId":"navNewsLi", "forwardUrl":"", "callbackType":"closeCurrent"}
 * {"statusCode":"300", "message":"操作失败"}
 * {"statusCode":"301", "message":"会话超时"}
 * </pre>
 * 
 * <pre>
 * Ajax表单提交后服务器端需要返回以下json代码：
 * {
 *       "statusCode":"200", 
 *       "message":"操作成功", 
 *       "navTabId":"", 
 *       "callbackType":"closeCurrent",
 *       "forwardUrl":""
 * }
 * </pre>
 */
public class DwzJson extends BaseDTO {
	private static final long serialVersionUID = 8110183649754865707L;
	private Object dataObj; // 返回的数据对象

	private String statusCode = EnumDwzJsonStatusType.OK.getCode();// EnumDwzJsonStatusType
																	// 操作成功200,操作失败300,会话超时301
	private String message = EnumDwzJsonStatusType.OK.getDesc();// 操作成功200,操作失败300,会话超时301
	/**
	 * 操作成功后,要重新加载的Tab或者dialog的 ID号,
	 * [把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的. 否则Tab重新载入当前navTab页面
	 */
	private String navTabId = "";
	/**
	 * EnumDwzJsonCallbackType
	 * forward在当前面板中跳转到forwardUrl,closeCurrent关闭当前面板或者dialog
	 */
	private String callbackType = "";
	/** callbackType=forward时在当前面板中跳转到forwardUrl */
	private String forwardUrl = "";

	public DwzJson() {

	}

	public DwzJson(String statusCode) {
		this(statusCode, "");
	}

	public DwzJson(String statusCode, String message) {
		this(statusCode, message, "");
	}

	public DwzJson(String statusCode, String message, String navTabId) {
		this(statusCode, message, navTabId, "");
	}

	public DwzJson(String statusCode, String message, String navTabId,
			String callbackType) {
		this(statusCode, message, navTabId, callbackType, "");
	}

	public DwzJson(String statusCode, String message, String navTabId,
			String callbackType, String forwardUrl) {
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
		this.callbackType = callbackType;
		this.forwardUrl = forwardUrl;
	}

	/**
	 * 通过Dwz参数，可设置navTabId，callbackType，forwardUrl这几个参数
	 * 
	 * @param query
	 */
	public DwzJson(Dwz dwz) {
		super();
		this.setDwz(dwz);
	}

	/**
	 * 通过Dwz参数，可设置navTabId，callbackType，forwardUrl这几个参数
	 * 
	 * @param query
	 */
	public void setDwz(Dwz dwz) {
		this.navTabId = dwz.getNavTabId();
		this.callbackType = dwz.getCallbackType();
		this.forwardUrl = dwz.getForwardUrl();
	}

	/**
	 * 通用操作结果，设置到dwzJson对象中，自动分析成功失败，失败信息，结果数据
	 * 
	 * @param query
	 */
	public DwzJson(ServiceResult result) {
		super();
		this.setResult(result);
	}

	/**
	 * 通用操作结果，设置到dwzJson对象中，自动分析成功失败，失败信息，结果数据 。
	 * 通过Dwz参数，可设置navTabId，callbackType，forwardUrl这几个参数
	 * 
	 * @param query
	 */
	public DwzJson(ServiceResult result, Dwz dwz) {
		super();
		this.setResult(result);
		this.setDwz(dwz);
	}

	/**
	 * 通用操作结果，设置到dwzJson对象中，自动分析成功失败，失败信息，结果数据
	 * 
	 * @param result
	 */
	public void setResult(ServiceResult result) {
		if (result.getErrorNO() != null) {
			this.setStatusCode(EnumDwzJsonStatusType.ERROR.getCode());
			this.setMessage(result.getErrorInfo());
		} else {
			this.setStatusCode(EnumDwzJsonStatusType.OK.getCode());
			this.setDataObj(result.getDataObj());
		}
	}

	/**
	 * 通用操作结果，设置到dwzJson对象中，自动分析成功失败，失败信息，结果数据，以及操作成功后UI端处理方式
	 * 
	 * @param result
	 */
	public void setResult(ServiceResult result, Dwz dwz) {
		this.setResult(result);
		this.setDwz(dwz);
	}

	/**
	 * EnumDwzJsonStatusType操作成功200,操作失败300,会话超时301
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * EnumDwzJsonStatusType操作成功200,操作失败300,会话超时301
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * 操作成功200,操作失败300,会话超时301
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 操作成功200,操作失败300,会话超时301
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 操作成功后,要重新加载的Tab或者dialog的 ID号,
	 * [把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的. 否则Tab重新载入当前navTab页面
	 */
	public String getNavTabId() {
		return navTabId;
	}

	/**
	 * 操作成功后,要重新加载的Tab或者dialog的 ID号,
	 * [把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的. 否则Tab重新载入当前navTab页面
	 */
	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	/** forward在当前面板中跳转到forwardUrl,closeCurrent关闭当前面板或者dialog */
	public String getCallbackType() {
		return callbackType;
	}

	/** forward在当前面板中跳转到forwardUrl,closeCurrent关闭当前面板或者dialog */
	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	/** callbackType=forward时在当前面板中跳转到forwardUrl */
	public String getForwardUrl() {
		return forwardUrl;
	}

	/** callbackType=forward时在当前面板中跳转到forwardUrl */
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public Log getLogger() {
		return null;
	}

	public Object getDataObj() {
		return dataObj;
	}

	public void setDataObj(Object dataObj) {
		this.dataObj = dataObj;
	}

}
