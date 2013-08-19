

// 清理浏览器内存,只对IE起效,FF不需要
if ($.browser.msie) {
	window.setInterval("CollectGarbage();", 10000);
}

function changeCheckcode(imgId, imgType) {
	if (!imgId)
		imgId = imgId || "imgCheckCode";
	if (!imgType)
		imgType = imgType || 1;
	var url = appServer + "imgCheckCode" + imgType + ".xhtml?t="
			+ (new Date()).getTime();
	$("#" + imgId).attr('src', url);
}
