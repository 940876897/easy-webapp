package net.easyUI.common.dto;

/***
 * 此类主要用于DWZ前台将下一个操作后控制页面处理的参数，其从URL中传入到Action中，再重新传回到页面Json或Page中，以供页面处理用。
 * 请在每个DWZ页面的Action方法入参中，添加  @ModelAttribute Dwz dwz,  以获取其值，
 * 在Action返回前，model.addAttribute("dwz", dwz == null ? new Dwz() : dwz);
 * 如返回JSON，则 dwzJson = new DwzJson(result, dwz); 或dwzJson = new DwzJson( dwz);
 * @author busfly
 * 
 */
public class Dwz {

	/**
	 * DWZ中打开面板的ID，主要用于UI端标记操作来源Tab或Dialog，以便后续操作完成时，通知或刷新源页面。如果是lookup，
	 * 表示打开的是弹出选择页面。
	 */
	protected String dwzId = "";
	/**
	 * 操作成功后,要重新加载的Tab或者dialog的 ID号。
	 * [把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的. 否则Tab重新载入当前navTab页面
	 */
	private String navTabId = "";

	/**
	 * EnumDwzJsonCallbackType . “forward”在当前面板中跳转到【forwardUrl】，
	 * “closeCurrent”关闭当前面板Tab或者dialog
	 */
	private String callbackType = "closeCurrent";

	/** callbackType=“forward”时在当前面板中跳转到forwardUrl */
	private String forwardUrl = "";

	/** targetType: "navTab" /“dialog” */
	private String targetType = "navTab";

	public String getDwzId() {
		int index = dwzId.indexOf(",");
		if (index > 0)
			dwzId = dwzId.substring(0, index);
		return dwzId;
	}

	public void setDwzId(String dwzId) {
		this.dwzId = dwzId;
	}

	public String getNavTabId() {
		int index = navTabId.indexOf(",");
		if (index > 0)
			navTabId = navTabId.substring(0, index);
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String getForwardUrl() {
		int index = forwardUrl.indexOf(",");
		if (index > 0)
			forwardUrl = forwardUrl.substring(0, index);
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getCallbackType() {
		int index = callbackType.indexOf(",");
		if (index > 0)
			callbackType = callbackType.substring(0, index);
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getTargetType() {
		int index = targetType.indexOf(",");
		if (index > 0)
			targetType = targetType.substring(0, index);
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

}
