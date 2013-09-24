<#include "/include/dev.ftl">
<#import "/spring.ftl" as s />
<#--  <@spring.bind>和<@spring.bindEscaped>支持将信息中的HTML码进行转码 -->

<#macro dwzMiniLayout title="个人中心" app="dwzMini">
<!doctype html>
<html>
<!-- 本站全部采用HTML5的规范,如果显示有问题,这个是正常的,请升级浏览器 ${app}   -->
    <head>
        <meta charset="utf-8"/>
        <!--
        <meta http-equiv="X-UA-Compatible" content="IE=7" />
        -->
        <title>
			 ${title}  后台管理系统
		</title>
		<#include "/admin/include/common-js-css.ftl">
        
	</head>
	
	<body scroll="no">
		<#nested>
	</body>

</html>


</#macro>