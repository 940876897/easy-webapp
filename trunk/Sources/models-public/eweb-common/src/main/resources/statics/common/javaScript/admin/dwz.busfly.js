$(function() {
			DWZ.init(imageServer + "/statics/common/javaScript/admin/dwz.frag.xml", {
						loginUrl : appServer + "loginAjaxPage.xhtml", // 弹出登录对话框
						loginTitle : "登录", // 弹出登录对话框
						// loginUrl : appServer+"loginPage.xhtml", // 跳到登录页面
						debug : false, // 调试模式 【true|false】
						callback : function() {
							initEnv();
							$("#themeList").theme({
								// themeBase : "themes"
								themeBase : imageServer
										+ "/statics/common/jsLibs/dwz/themes"
							});
						}
					});
		});

/**
 * AjaxJson登录成功
 * 
 * @param {}
 *            json
 */
function loginAjaxDone(json) {
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok) {
		try {
			$.pdialog.closeCurrent();
		} catch (e) {
		}

		// 刷新Tab页面
		if (json.navTabId)
			navTab.reloadFlag(json.navTabId);

	} else {
		changeCheckcode();
	}
}

