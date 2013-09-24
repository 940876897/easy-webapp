$(document)
		.ready(
				function() {
					// 需要动态增加一次Post文档查看次数
					var postAddViewsIds = "";
					$("span.postAddViews").each(function() {
						if (postAddViewsIds == "")
							postAddViewsIds = $(this).text().trim();
						else
							postAddViewsIds += "," + $(this).text().trim();
						$(this).hide();
						$(this).text("");
					});
					// 需要动态显示各Post文档查看次数
					var postViewsIds = "";
					$("span.postViews").each(function() {
						if (postViewsIds == "")
							postViewsIds = $(this).text().trim();
						else
							postViewsIds += "," + $(this).text().trim();
						$(this).hide();
						$(this).text("");
					});
					// 需要动态显示各Post文档评论数量
					var postCommentsIds = "";
					$("span.postComments").each(function() {
						if (postCommentsIds == "")
							postCommentsIds = $(this).text().trim();
						else
							postCommentsIds += "," + $(this).text().trim();
						$(this).hide();
						$(this).text("");
					});
					// 需要动态加载的HTML代码区
					var includeHtmlsIds = "";
					$("div.includeHtmls").each(function() {
						if (includeHtmlsIds == "")
							includeHtmlsIds = $(this).text().trim();
						else
							includeHtmlsIds += "," + $(this).text().trim();
						$(this).hide();
						$(this).text("");
					});
					
//					$.getScript("http://www.busfly.net/blog/zb_system/function/c_html_js.asp?act=batch"
//									+ unescape("%26")
//									+ "view="
//									+ escape(strBatchView)
//									+ unescape("%26")
//									+ "inculde="
//									+ escape(strBatchInculde)
//									+ unescape("%26")
//									+ "count="
//									+ escape(strBatchCount));

				});

// 清理浏览器内存,只对IE起效,FF不需要
if ($.browser.msie) {
	window.setInterval("CollectGarbage();", 10000);
}

// 刷新校验码
function changeCheckcode(imgId, imgType) {
	if (!imgId)
		imgId = imgId || "imgCheckCode";
	if (!imgType)
		imgType = imgType || 1;
	var url = appServer + "imgCheckCode" + imgType + ".htm?t="
			+ (new Date()).getTime();
	$("#" + imgId).attr('src', url);
}

// 前台分页,选择每页多少条时,刷新提交表单
function pageBreak(numPerPage) {
	jQuery("#frontPagerForm_numPerPage").val(numPerPage);
	jQuery("#frontPagerForm").submit();
}
// 前台分页,点击第几页时,刷新提交表单.
function goPage(pageNum) {
	jQuery("#frontPagerForm_pageNum").val(pageNum);
	jQuery("#frontPagerForm").submit();
}