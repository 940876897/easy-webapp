<#include "/include/dev.ftl">
<#import "/spring.ftl" as s />
<#--  <@spring.bind>和<@spring.bindEscaped>支持将信息中的HTML码进行转码 -->

<#macro dwzAdminLayout title="个人中心" app="dwzAdmin">
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
        
        <!-- 需要导航条时打开以下样式 -->
        <style type="text/css">
		 /*   
		 	#header{height:85px}
		    #leftside, #container, #splitBar, #splitBarProxy{top:90px}
        */
		</style>
		
	</head>
	
	<body scroll="no">
	    <div id="layout">
	        <!-- 头部 -->
	        <#include "/admin/include/header.ftl">
	        <!-- 左侧 -->
	        <div id="leftside">
                <div id="sidebar_s">
                    <div class="collapse">
                        <div class="toggleCollapse"><div></div></div>
                    </div>
                </div>
                <div id="sidebar">
                    <div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
                    
	        		<#include "/admin/include/leftSidebar.ftl">
	        		
                </div>
            </div>
			
                
	        <!-- 中间区 -->
            <div id="container">
                <div id="navTab" class="tabsPage">
                    <div class="tabsPageHeader">
                        <div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
                            <ul class="navTab-tab">
                                <li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
                            </ul>
                        </div>
                        <div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
                        <div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
                        <div class="tabsMore">more</div>
                    </div>
                    <ul class="tabsMoreList">
                        <li><a href="javascript:;">我的主页</a></li>
                    </ul>
                    <div class="navTab-panel tabsPageContent layoutBox">
                        <div class="page unitBox">
                        <!--  工作区  -->
	        <#nested>
                        </div>
                    </div>
                </div>
            </div>
            
	        <!-- layout区结束-->
	    </div>
	    
	    <!-- 底部区 -->
	        		<#include "/admin/include/footer.ftl">
	
	</body>

</html>


</#macro>