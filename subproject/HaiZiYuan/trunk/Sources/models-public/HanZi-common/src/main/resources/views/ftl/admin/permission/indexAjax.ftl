<#include "/include/dev.ftl">
<#import "/admin/imports/macro.ftl" as m>
<#import "/spring.ftl" as s />

<form id="pagerForm" action="${appServer!""}/admin/permission/indexAjax.htm" method="post">
		<#include "/admin/include/dwzFormBaseQuery.ftl">
    <!--【可选】其它查询条件，业务有关，有什么查询条件就加什么参数-->
	
    <input type="hidden" name="permissionKey_cn" value="${query.permissionKey_cn!""}"/>
    <input type="hidden" name="permissionName_cn" value="${query.permissionName_cn!""}"/>
    <input type="hidden" name="uri_cn" value="${query.uri_cn!""}"/>
            
    
            
</form> 


<div class="pageHeader">
    <form onsubmit="return navTabSearch(this,'dwz_tab_permission');" action="${appServer!""}/admin/permission/indexAjax.htm" method="post">
		<#include "/admin/include/dwzFormBaseQuery.ftl">
    
    <div class="searchBar">
        <ul class="searchContent">
            <li>
                <label><@s.message "Permission.permissionKey" />：</label>
                    <input type="text" name="permissionKey_cn" value="${query.permissionKey_cn!""}" size="25"/>
            </li>
            <li>
                <label><@s.message "Permission.permissionName" />：</label>
                    <input type="text" name="permissionName_cn" value="${query.permissionName_cn!""}" size="25"/>
            </li>
            <li>
                <label><@s.message "Permission.uri" />：</label>
                    <input type="text" name="uri_cn" value="${query.uri_cn!""}" size="25"/>
            </li>
        </ul>
        <div class="subBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">模糊查询</button></div></div></li>
            </ul>
        </div>
    </div>
    </form>
</div>



<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="${appServer!""}/admin/permission/addAjax.htm" target="navTab" title="新增权限" rel="dwz_tab_permission_add"><span>新增</span></a></li>
            <li><a href="${appServer!""}/admin/permission/editAjax/{id}.htm" target="navTab" class="edit" title="修改权限" rel="dwz_tab_permission_edit" ><span>修改</span></a></li>
            <li class="line">line</li>
<!--
            <li><a href="${appServer!""}/admin/permission/delJson.htm" target="selectedTodo" class="delete" title="确实要批量删除这些权限吗?"><span>批量删除</span></a></li>
-->
            <li><a href="${appServer!""}/admin/permission/delJson/{id}.json" target="ajaxTodo" class="delete" title="你确定要删除选择这条权限吗?"><span>删除</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138" nowrapTD="false">
        <thead>
        	<tr >
               <th width="30"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
               <th width="160" orderField="permissionKey" <#if (query.orderField!"")=="permissionKey">class="${query.orderDirection!""}"</#if> ><@s.message "Permission.permissionKey" /></th>
               <th width="160" orderField="permissionName" <#if (query.orderField!"")=="permissionName">class="${query.orderDirection!""}"</#if> ><@s.message "Permission.permissionName" /></th>
               <th  orderField="uri" <#if (query.orderField!"")=="uri">class="${query.orderDirection!""}"</#if> ><@s.message "Permission.uri" /></th>
               
               <th width="60">操作</th>
            </tr>
        </thead>
        <tbody>
	       <#list page.rows as rs>
	       <tr target="id" rel="${rs.id!""}"> <!-- target="id" rel="${rs.id!""}" 这两个是用在AjaxURL中的占位({target})数据(rel)来源 -->
                <td><input name="ids" value="${rs.id!""}" type="checkbox"></td>
	            <!--
                <td> ${rs.id!""} </td>
                -->
                <td>
                ${(rs.permissionKey!"")?html}
                </td>
                <td>
                    ${(rs.permissionName!"")?html}
                </td>
                <td>
                    ${(rs.uri!"")?html}
                </td>
        
	            <td >
	            	<a href="${appServer!""}/admin/permission/delJson/${rs.id?url}.json" target="ajaxTodo" title="你确定要删除这个权限[${rs.id!""}]吗?" class="btnDel" >删除</a>
	                <a href="${appServer!""}/admin/permission/editAjax/${rs.id?url}.htm" target="navTab" title="修改权限[${rs.id!""}]" class="btnEdit"  rel="dwz_tab_permission_edit_${rs.id!""}" >修改</a>
               <!--如果有1对多,可加上查看多方列表按钮
                    <a href="${appServer!""}/admin/permissionMeta/indexAjax.htm?permissionId=${rs.id?url}" target="navTab" 
                        rel="dwz_tab_permissionMeta" title="查看扩展属性[${rs.id!""}]" class="btnInfo">
                        扩展属性</a>
                -->
                </td>
	        </tr>
	       	</#list>
        </tbody>
    </table>
    <@m.dwzPageBar page "navTab"/>  
</div>

