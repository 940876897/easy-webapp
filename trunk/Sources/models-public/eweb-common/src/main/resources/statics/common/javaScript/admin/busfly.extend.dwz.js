
(function($) {
//	$.fn.extend({
//		/** 重载了DWZ的这个方法,将根据targetType类型,扩展支持对话框dialogAjaxDone或navTabAjaxDone*/
//		selectedTodo : function() {
//			function _getIds(selectedIds, targetType) {
//				var ids = "";
//				var $box = targetType == "dialog"
//						? $.pdialog.getCurrent()
//						: navTab.getCurrentPanel();
//				$box.find("input:checked").filter("[name='" + selectedIds
//						+ "']").each(function(i) {
//							var val = $(this).val();
//							ids += i == 0 ? val : "," + val;
//						});
//				return ids;
//			}
//			return this.each(function() {
//				var $this = $(this);
//				var selectedIds = $this.attr("rel") || "ids";
//				var postType = $this.attr("postType") || "map";
//				// TODO 修改点:将成功回调函数改了一下,根据targetType,默认为navTab,支持dialog
//				var successCallBack = navTabAjaxDone;
//				if ($this.attr("targetType") == "dialog")
//					successCallBack = dialogAjaxDone;
//
//				$this.click(function() {
//							var ids = _getIds(selectedIds, $this
//											.attr("targetType"));
//							if (!ids) {
//								alertMsg.error($this.attr("warn")
//										|| DWZ.msg("alertSelectMsg"));
//								return false;
//							}
//							function _doPost() {
//								$.ajax({
//											type : 'POST',
//											url : $this.attr('href'),
//											dataType : 'json',
//											cache : false,
//											data : function() {
//												if (postType == 'map') {
//													return $.map(
//															ids.split(','),
//															function(val, i) {
//																return {
//																	name : selectedIds,
//																	value : val
//																};
//															})
//												} else {
//													var _data = {};
//													_data[selectedIds] = ids;
//													return _data;
//												}
//											}(),
//											success : successCallBack,
//											error : DWZ.ajaxError
//										});
//							}
//							var title = $this.attr("title");
//							if (title) {
//								alertMsg.confirm(title, {
//											okCall : _doPost
//										});
//							} else {
//								_doPost();
//							}
//							return false;
//						});
//
//			});
//		}
//	});
})(jQuery);
