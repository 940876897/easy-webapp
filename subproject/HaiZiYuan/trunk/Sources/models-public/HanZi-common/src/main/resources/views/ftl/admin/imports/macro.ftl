

<#-- 截取字符串前N个字，Freemarker自带substring，但是，如果要截取的长度大于字符串的长度会报错。
$strs  原字符串。
$count 要截取几个字。默认2个字符
$suffix 截断后，添加的后缀。
 	使用方法：1：<#import "/admin/imports/macro.ftl" as m>。2：<@m.subStr strs count suffix/>
 -->
<#macro subStr strs count suffix> 
	<#if strs?exists>
		<#if strs?length lt (count!2) > 
			${strs}
	  	<#else>
			${strs[0..count]}${suffix!""}
	  	</#if>
	</#if>
</#macro>

<#-- 
	生成DWZ页面上的分页条组件，
 	$pageObj 分页对象,(类为：net.easyUI.dto.common.query.DwzPage)
 	$pageType : 窗口dialog，导航Tab：navTab.
 	使用方法：1：<#import "/admin/imports/macro.ftl" as m>。2：<@m.dwzPageBar page "navTab"/>
-->
<#macro dwzPageBar pageObj pageType> 
    <#if (pageType!"")="dialog">
        <#assign pageType="navTab" />
    </#if>
    <div class="panelBar">
        <div class="pages">
            <span>每页</span>
            <select class="combox" onchange="${pageType}PageBreak({numPerPage:this.value})" name="numPerPage" param="numPerPage">
                <option value="1" <#if (page.numPerPage!20)==1> selected="selected" </#if>>1</option>
                <option value="10" <#if (page.numPerPage!20)==10> selected="selected" </#if>>10</option>
                <option value="20" <#if (page.numPerPage!20)==20> selected="selected" </#if>>20</option>
                <option value="50" <#if (page.numPerPage!20)==50> selected="selected" </#if>>50</option>
                <option value="100" <#if (page.numPerPage!20)==100> selected="selected" </#if>>100</option>
                <option value="200" <#if (page.numPerPage!20)==200> selected="selected" </#if>>200</option>
            </select>
            <span>条，共${page.totalCount!"0"}条</span>
        </div>
        <div class="pagination" targetType="${pageType}" totalCount="${page.totalCount!"0"}" 
        	numPerPage="${page.numPerPage!""}" pageNumShown="${page.pageNumShown!""}" currentPage="${page.currentPage!""}">
        </div>
    </div>
</#macro>   




<#-- 
##将枚举组装成下拉选择框
## $enumObj	枚举对象,
## $elName 		为下拉框的Name,默认为:postType.
## $curValue 	为当前值,则默认选中这项,且,如果页面中有类目组件,自动关联类目可选数据.
## $selectAllText 	请选择的文本,如果需要这个选项,请传入要显示的文字,默认为不显示这个值选项.
## $readOnly 	表示是否是只读,如果是只读的话,下拉中就只显示当前值.非空白表示只读,null或空白表示非只读
## $class 			下拉框样式名
 	使用方法：1：<#import "/admin/imports/macro.ftl" as m>。2：<@m.enumSelectEl  enumObj elName curValue selectAllText readOnly class/>
-->
<#macro enumSelectEl enumObj elName curValue selectAllText readOnly class> 
        
    <select name="${elName!"postType"}" class="combox  ${class!""}">
    	<#if (curValue!"")?string!="" && (readOnly!"")?string!="">
	        <#assign ro="1" />
        <#else>
	        <#assign ro="0" />
	    </#if>
	    
    	<#if ro!="1" && (selectAllText!"")?string!="">
        	<option value="" <#if (curValue!"")?string=="" > selected="selected" </#if> >${selectAllText!""}</option>
	    </#if>

       <#list enumObj as enObj>
       		<#if ro!="1">
        <option value="${enObj.code}" <#if (curValue!"")?string==enObj.code?string> selected="selected" </#if>>${enObj.desc}</option>
        	<#elseif (curValue!"")?string==enObj.code?string>
        <option value="${enObj.code}" selected="selected">${enObj.desc}</option>
            </#if>
        </#list>
    </select>
</#macro>         


<#-- 
## 将系统缓存中的$cfg_group_name (group+"_"+name)类型,组装成Select控件,
## $cfg_group_name 要查找的网站配置name, 由webconfig表里 group+"_"+name 组成.
## $elName为下拉框的Name,默认为:postType.
## $curValue为当前值,则默认选中这项,且,如果页面中有类目组件,自动关联类目可选数据.
## $selectAllText请选择的文本,如果需要这个选项,请传入要显示的文字,默认为不显示这个值选项.
## $readOnly表示是否是只读,如果是只读的话,下拉中就只显示当前值.非空白表示只读,null或空白表示非只读
 	使用方法：1：<#import "/admin/imports/macro.ftl" as m>。2：<@m.enumSelectEl  cfg_group_name elName curValue selectAllText readOnly class/>
-->
<#macro webConfigSelectEl cfg_group_name elName curValue selectAllText readOnly class > 
	<select name="${elName!"postType"}" class="combox ${class!""}">
    	<#if (curValue!"")!="" && (readOnly!"")!="">
	        <#assign ro="1" />
        <#else>
	        <#assign ro="0" />
	    </#if>
	    
    	<#if ro!="1" && (selectAllText!"")!="">
        	<option value="" <#if (curValue!"")=="" > selected="selected" </#if> >${selectAllText!""}</option>
	    </#if>

        <#assign cfg_group=_WebCacheUtils.webConfigValueJson(cfg_group_name!"postType") />
       	<#list codeDesc as cfg_group>
       		<#if ro!="1">
        <option value="${codeDesc.code}" <#if (curValue!"")?string==codeDesc.code?string> selected="selected" </#if>>${codeDesc.desc}</option>
        	<#elseif (curValue!"")?string==codeDesc.code?string>
        <option value="${codeDesc.code}" selected="selected">${codeDesc.desc}</option>
            </#if>
		</#list>
	</select>
</#macro>