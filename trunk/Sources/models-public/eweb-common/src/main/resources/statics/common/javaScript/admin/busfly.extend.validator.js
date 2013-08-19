
/**
 * @author ZhangHuihua@msn.com
 */
(function($) {
	// jQuery validate
	$.extend($.validator.messages, {
				remote : "已被使用"
			});
})(jQuery);

/**
 * @requires jquery.validate.js
 * @author zHao
 */
(function($) {
	if ($.validator) {
		// Capital Letters only please
		$.validator.addMethod("capitalonly", function(value, element) {
					return this.optional(element) || /^[A-Z]+$/.test(value);
				}, "只能输入大写字母");

		// $.validator.addClassRules({
		// capitalonly: { capitalonly: true }
		// });
		// $.validator.setDefaults({errorElement:"span"});
		// $.validator.autoCreateRanges = true;
		//		
		// $.extend($.validator.messages, {
		// capitalonly: "必须是大写字母"
		// });

		$.validator.addMethod("charNum", function(value, element) {
					var charNum = /^([a-zA-Z0-9]+)$/;
					return this.optional(element) || (charNum.test(value));
				}, "只能输入数字和字母(字符A-Z, a-z, 0-9)");

	}
})(jQuery);
