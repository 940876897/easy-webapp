<#include "/include/dev.ftl">
<#import "/admin/imports/macro.ftl" as m>
<#import "/spring.ftl" as s />

<form id="pagerForm" action="${appServer!""}/admin/user/indexAjax.htm" method="post">
		<#include "/admin/include/dwzFormBaseQuery.ftl">
    <!--【可选】其它查询条件，业务有关，有什么查询条件就加什么参数-->
	
            
    
    <input type="hidden" name="loginName_cn" value="${query.loginName_cn!""}"/>
    <input type="hidden" name="nicename_cn" value="${query.nicename_cn!""}"/>
    <input type="hidden" name="email_cn" value="${query.email_cn!""}"/>
    <input type="hidden" name="status" value="${query.status!""}"/>
    <input type="hidden" name="displayName_cn" value="${query.displayName_cn!""}"/>
            
</form> 


<div class="pageHeader">
    <form onsubmit="return navTabSearch(this,'${dwz.dwzId!""}');" action="${appServer!""}/admin/user/indexAjax.htm" method="post">
		<#include "/admin/include/dwzFormBaseQuery.ftl">
    <!--【可选】其它查询条件，业务有关，有什么查询条件就加什么参数-->
    
    <div class="searchBar">
        <ul class="searchContent">
            <li>
                <label><@s.message "User.status" />：</label>
                    <!-- 枚举EnableStatusEnum: -->
                	<@m.enumSelectEl  EnumEnableStatusList "status" query.status!""  "--全部状态--"  "" ""/>
            </li>
            <li>
                <label><@s.message "User.loginName" />：</label>
                    <input type="text" name="loginName_cn" value="${query.loginName_cn!""}" size="25"/>
            </li>
            <li>
                <label><@s.message "User.displayName" />：</label>
                    <input type="text" name="displayName_cn" value="${query.displayName_cn!""}" size="25"/>
            </li>
            <li>
                <label><@s.message "User.nicename" />：</label>
                    <input type="text" name="nicename_cn" value="${query.nicename_cn!""}" size="25"/>
            </li>
            <li>
                <label><@s.message "User.email" />：</label>
                    <input type="text" name="email_cn" value="${query.email_cn!""}" size="25"/>
            </li>
        </ul>
        <div class="subBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit"><@s.message "queryType.query" /></button></div></div></li>
                <!--
                <li><a class="button" href="${appServer!""}/admin/user/fullSearchForm.htm" target="dialog"  width="400" height="300" rel="dlg_f_s_form" title="<@s.message "queryType.queryAdv" />"><span><@s.message "queryType.queryAdv" /></span></a></li>
                -->
            </ul>
        </div>
    </div>
    </form>
</div>



<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a href="${appServer!""}/admin/user/addAjax.htm?navTabId=${dwz.dwzId!""}&dwzId=${dwz.dwzId!""}_add" target="navTab" class="add" title="<@s.message "function.add" /><@s.message "User" />" rel="${dwz.dwzId!""}_add"><span><@s.message "function.add" /></span></a></li>
            <li><a href="${appServer!""}/admin/user/editAjax/{id}.htm?navTabId=${dwz.dwzId!""}&dwzId=${dwz.dwzId!""}_edit" target="navTab" class="edit" title="<@s.message "function.edit" /><@s.message "User" />" rel="${dwz.dwzId!""}_edit" ><span><@s.message "function.edit" /></a></li>
            <li class="line">line</li>
<!--
            <li><a href="${appServer!""}/admin/user/delJson.json?navTabId=${dwz.dwzId!""}&callbackType=forward" target="selectedTodo" class="delete" title="确实要<@s.message "function.delBatch" />这些<@s.message "User" />吗?"><span><@s.message "function.delBatch" /></span></a></li>
-->
            <li><a href="${appServer!""}/admin/user/delJson/{id}.json?navTabId=${dwz.dwzId!""}&callbackType=forward" target="ajaxTodo" class="delete" title="你确定要<@s.message "function.del" />选择这条<@s.message "User" />吗?"><span><@s.message "function.del" /></span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138" nowrapTD="false">
        <thead>
        	<tr >
               <th width="30"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
               <th width="80" orderField="displayName" <#if (query.orderField!"")=="displayName">class="${query.orderDirection!""}"</#if>  ><@s.message "User.displayName" /></th>
               <th width="100" orderField="loginName" <#if (query.orderField!"")=="loginName">class="${query.orderDirection!""}"</#if>  ><@s.message "User.loginName" /></th>
               <th width="100" orderField="nicename" <#if (query.orderField!"")=="nicename">class="${query.orderDirection!""}"</#if>  ><@s.message "User.nicename" /></th>
               <!-- 
               <th width="120" orderField="email" <#if (query.orderField!"")=="email">class="${query.orderDirection!""}"</#if>  ><@s.message "User.email" /> </th>
               -->
               <!-- 
               <th orderField="website" <#if (query.orderField!"")=="website">class="${query.orderDirection!""}"</#if>  ><@s.message "User.website" /> </th>
               -->
               <th width="140" orderField="gmtRegistered" <#if (query.orderField!"")=="gmtRegistered">class="${query.orderDirection!""}"</#if>  ><@s.message "User.gmtRegistered" /></th>
               <th width="80" orderField="status" <#if (query.orderField!"")=="status">class="${query.orderDirection!""}"</#if>  > <@s.message "User.status" /> </th>
               <th width="100" >操作</th>
            </tr>
        </thead>
        <tbody>
	       <#list page.rows as rs>
	       <tr target="id" rel="${rs.id!""}"> <!-- target="id" rel="${rs.id!""}" 这两个是用在AjaxURL中的占位({target})数据(rel)来源-->
                <td><input name="ids" value="${rs.id!""}" type="checkbox"></td>
	            <td>
                    ${rs.displayName!""}
                </td>
                <td>
                    ${rs.loginName!""}
                </td>
                <td>
                    ${rs.nicename!""}
                </td>
                <!--
                <td>
                    ${rs.email!""}
                </td>
                -->
                <!--
                <td>
                    ${rs.website!""}
                </td>
                -->
                <td>
                    ${(rs.gmtRegistered!"")?date}
                </td>
                <td>
                    <!-- 枚举EnumEnableStatus:  -->
                	${EnumEnableStatusMap[rs.status?string]}
                </td>
        
	            <td >
                    <a href='${appServer!""}/admin/user/viewAjax/${rs.id}.htm?navTabId=${dwz.dwzId!""}&dwzId=${dwz.dwzId!""}_view_${rs.id!""}' target="navTab" title="<@s.message "function.view" /><@s.message "User" />[${rs.id!""}]" class="btnInfo"  rel="${dwz.dwzId!""}_view_${rs.id!""}" ><@s.message "function.view" /></a>
                    <a href='${appServer!""}/admin/user/editAjax/${rs.id}.htm?navTabId=${dwz.dwzId!""}&dwzId=${dwz.dwzId!""}_edit_${rs.id!""}' target="navTab" title="<@s.message "function.edit" /><@s.message "User" />[${rs.id!""}]" class="btnEdit"  rel="${dwz.dwzId!""}_edit_${rs.id!""}" ><@s.message "function.edit" /></a>
                    <a href='${appServer!""}/admin/user/delJson/${rs.id}.json?navTabId=${dwz.dwzId!""}&callbackType=forward' target="ajaxTodo" title="你确定要<@s.message "function.del" />这个<@s.message "User" />[${rs.id!""}]吗?" class="btnDel"><@s.message "function.del" /></a>
                </td>
	        </tr>
	       	</#list>
        </tbody>
    </table>
    <@m.dwzPageBar page "navTab"/>  
</div>

