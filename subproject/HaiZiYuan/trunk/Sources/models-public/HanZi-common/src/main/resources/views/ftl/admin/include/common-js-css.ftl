        <!--[if lte IE 8]>
        <script src="${imageServer!""}/statics/jsLibs/html5.js"></script>
        <![endif]--> 
        <!--if lte IE 6]>
        <script src="${imageServer!""}/statics/jsLibs/letskillie6.zh_CN.pack.js"></script>
        <!--endif]-->
        
        
		<!-- 加速JS加载速度 -->
		<!--[if lte IE 9]>
			<script src="${imageServer!""}/statics/jsLibs/speedup.js"></script>
		<![endif]-->
			<script src="${imageServer!""}/statics/jsLibs/jquery.min.js"></script>
			<script src="${imageServer!""}/statics/jsLibs/jquery.cookie.js"></script>
			<script src="${imageServer!""}/statics/jsLibs/jquery.validate.js"></script>
			<script src="${imageServer!""}/statics/jsLibs/jquery.bgiframe.js"></script>
	  
       	 	<!-- 在线代码开发编辑器 -->
			<script src="${imageServer!""}/statics/jsLibs/ace-min/ace.js"></script>
			
	       <!-- dwz样式 -->
			<link href="${imageServer!""}/statics/jsLibs/dwz/themes/default/style.css" rel="stylesheet" />
			<link href="${imageServer!""}/statics/jsLibs/dwz/themes/css/core.css" rel="stylesheet" />
	        <link href="${imageServer!""}/statics/jsLibs/dwz/themes/css/print.css" rel="stylesheet" media="print"/>
	        <!--[if IE]>
	        <link href="${imageServer!""}/statics/jsLibs/dwz/themes/css/ieHack.css" rel="stylesheet" />
	        <![endif]-->
        
			<link href="${imageServer!""}/statics/jsLibs/uploadify/css/uploadify.css" rel="stylesheet" />
        	<link href="${imageServer!""}/statics/css-common-admin/default.css" rel="stylesheet" />
        
		


		
			<script src="${imageServer!""}/statics/jsLibs/xheditor/xheditor.min.js"></script>
			<script src="${imageServer!""}/statics/jsLibs/xheditor/xheditor_lang/zh-cn.js"></script>
			<script src="${imageServer!""}/statics/jsLibs/uploadify/scripts/jquery.uploadify.min.js"></script>
			
			<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
			<script type="text/javascript" src="${imageServer!""}/statics/jsLibs/dwz/chart/raphael.js"></script>
			<script type="text/javascript" src="${imageServer!""}/statics/jsLibs/dwz/chart/g.raphael.js"></script>
			<script type="text/javascript" src="${imageServer!""}/statics/jsLibs/dwz/chart/g.bar.js"></script>
			<script type="text/javascript" src="${imageServer!""}/statics/jsLibs/dwz/chart/g.line.js"></script>
			<script type="text/javascript" src="${imageServer!""}/statics/jsLibs/dwz/chart/g.pie.js"></script>
			<script type="text/javascript" src="${imageServer!""}/statics/jsLibs/dwz/chart/g.dot.js"></script>

	        <!-- DWZ -->
	        <script src="${imageServer!""}/statics/jsLibs/dwz/dwz.min.js"></script>
	        <script src="${imageServer!""}/statics/jsLibs/dwz/dwz.regional.zh.js"></script>
		
        <!-- 基础JS -->
       	 	<script>
				var sys = {};
				var appServer = {
					toString:function(){
				        return '${appServer!""}/';
				    },
				    get:function(urlStr){
				        return '${appServer!""}/' + urlStr ;
				    }
				};
				
				var sslAppServer = {
					toString:function(){
				        return '${sslAppServer!""}/';
				    },
				    get:function(urlStr){
				        return '${sslAppServer!""}/' + urlStr ;
				    }
				};
				
				var imageServer = {
				    toString:function(){
				        return '${imageServer!""}/';
				    },
				    get:function(urlStr){
				        return '${imageServer!""}/' + urlStr;
				    }
				}; 
				
				var uploadServer = {
				    toString:function(){
				        return '${uploadServer!""}/';
				    },
				    get:function(urlStr){
				        return '${uploadServer!""}/' + urlStr;
				    }
				}; 
				/**
				 *使用方法:String.format("变量{0},占位{1}", str1,str2)
				 */
				String.format = function(src){
				    if (arguments.length == 0) return null;
				    var args = Array.prototype.slice.call(arguments, 1);
				    return src.replace(/\{(\d+)\}/g, function(m, i){
				        return args[i];
				    });
				};
       	 	</script>
       <!-- JS代码文件        -->
	    	<script src="${imageServer!""}/statics/js-common-admin/busfly.extend.javascript.js"></script>

	    	<script src="${imageServer!""}/statics/js-common-admin/busfly.extend.validator.js"></script>
		    <!-- DWZ 修改-->
		    <script src="${imageServer!""}/statics/js-common-admin/busfly.extend.dwz.js"></script>
		    <script src="${imageServer!""}/statics/js-common-admin/busfly.extend.dwz.database.js"></script>
	        <script src="${imageServer!""}/statics/js-common-admin/dwz.busfly.js"></script>
		
			<!-- EasyUI 自定义方法类库: -->
	    	<script src="${imageServer!""}/statics/js-common/euFn.js"></script>
	    	
	    	<script src="${imageServer!""}/statics/js-common-admin/admin.js"></script>
