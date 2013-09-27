<#include "/include/dev.ftl">
<#import "/admin/imports/macro.ftl" as m>
<#import "/spring.ftl" as s />

<form id="pagerForm" action="${appServer!""}/admin/role/indexAjax.htm" method="post">
		<#include "/admin/include/dwzFormBaseQuery.ftl">
    <!--【可选】其它查询条件，业务有关，有什么查询条件就加什么参数-->
	
                
    <input type="hidden" name="roleKey_cn" value="${query.roleKey_cn!""}"/>
                
    <input type="hidden" name="roleName_cn" value="${query.roleName_cn!""}"/>
            
    
</form> 


<div class="pageHeader">
    <form onsubmit="return navTabSearch(this,'${dwz.dwzId!""}');" action="${appServer!""}/admin/role/indexAjax.htm" method="post">
		<#include "/admin/include/dwzFormBaseQuery.ftl">
    <!--【可选】其它查询条件，业务有关，有什么查询条件就加什么参数-->
    
    <div class="searchBar">
        <ul class="searchContent">
            <li>
                <label><@s.message "Role.roleKey" />：</label>
                    <input type="text" name="roleKey_cn" value="${query.roleKey_cn!""}" size="25"/>
            </li>
            <li>
                <label><@s.message "Role.roleName" />：</label>
                    <input type="text" name="roleName_cn" value="${query.roleName_cn!""}" size="25"/>
            </li>
        </ul>
        <div class="subBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit"><@s.message "queryType.query" /></button></div></div></li>
            </ul>
        </div>
    </div>
    </form>
</div>



<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="${appServer!""}/admin/role/addAjax.htm?navTabId=${dwz.dwzId!""}&dwzId=${dwz.dwzId!""}_add" target="navTab" title="<@s.message "function.add" /><@s.message "Role" />" rel="${dwz.dwzId!""}_add"><span><@s.message "function.add" /></span></a></li>
            <li><a href="${appServer!""}/admin/role/editAjax/{id}.htm?navTabId=${dwz.dwzId!""}&dwzId=${dwz.dwzId!""}_edit" target="navTab" class="edit" title="<@s.message "function.edit" /><@s.message "Role" />" rel="${dwz.dwzId!""}_edit" ><span><@s.message "function.edit" /></span></a></li>
            <li class="line">line</li>
<!--
            <li><a href="${appServer!""}/admin/role/delJson.json?navTabId=${dwz.dwzId!""}&callbackType=forward" target="selectedTodo" class="delete" title="确实要<@s.message "function.delBatch" />这些<@s.message "Role" />吗?"><span><@s.message "function.delBatch" /></span></a></li>
-->
            <li><a href="${appServer!""}/admin/role/delJson/{id}.json?navTabId=${dwz.dwzId!""}&callbackType=forward" target="ajaxTodo" class="delete" title="你确定要<@s.message "function.del" />选择这条<@s.message "Role" />吗?"><span><@s.message "function.del" /></span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138" nowrapTD="false">
        <thead>
        	<tr >
               <th width="30"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
               
               <th width="160" orderField="roleKey"  <#if (query.orderField!"")=="roleKey">class="${query.orderDirection!""}"</#if> ><@s.message "Role.roleKey" /></th>
               <th  orderField="roleName"  <#if (query.orderField!"")=="roleName">class="${query.orderDirection!""}"</#if> ><@s.message "Role.roleName" /></th>
               
               <th width="120" >操作</th>
            </tr>
        </thead>
        <tbody>
	       <#list page.rows as rs>
	       <tr target="id" rel="${rs.id}">
                <td><input name="ids" value="${rs.id}" type="checkbox"></td>
                <td> ${rs.roleKey} </td>
                <td> ${rs.roleName} </td>
        
	            <td >
                    <a href='${appServer!""}/admin/role/viewAjax/${rs.id}.htm?navTabId=${dwz.dwzId!""}&dwzId=${dwz.dwzId!""}_view_${rs.id!""}' target="navTab" title="<@s.message "function.view" /><@s.message "Role" />[${rs.id}]" class="btnInfo"  rel="${dwz.dwzId!""}_view_${rs.id}" ><@s.message "function.view" /> </a>
                    <a href='${appServer!""}/admin/role/editAjax/${rs.id}.htm?navTabId=${dwz.dwzId!""}&dwzId=${dwz.dwzId!""}_edit_${rs.id!""}' target="navTab" title="<@s.message "function.edit" /><@s.message "Role" />[${rs.id}]" class="btnEdit"  rel="${dwz.dwzId!""}_edit_${rs.id}" ><@s.message "function.edit" /> </a>
                    <a href='${appServer!""}/admin/role/delJson/${rs.id}.json?navTabId=${dwz.dwzId!""}&callbackType=forward' target="ajaxTodo" title="你确定要<@s.message "function.del" />这个<@s.message "Role" />[${rs.id}]吗?" class="btnDel"><@s.message "function.del" /> </a>
                <!--  如果有1对多,可加上查看多方列表按钮  -->
                <!--
                    <a href='${appServer!""}/admin/roleMeta/indexAjax.htm?roleId=${rs.id}&dwzId=${dwz.dwzId!""}Meta' target="navTab" 
                        rel="${dwz.dwzId!""}Meta" title="<@s.message "RoleMeta" /><@s.message "function.view" />[${rs.id}]" class="btnInfo">
                        <@s.message "RoleMeta" /></a>
                -->
                </td>
	        </tr>
	        </#list>
        </tbody>
    </table>
    <@m.dwzPageBar page "navTab"/>  
</div>

