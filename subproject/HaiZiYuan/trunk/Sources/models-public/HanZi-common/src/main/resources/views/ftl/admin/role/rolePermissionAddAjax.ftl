<#include "/include/dev.ftl">
<#import "/admin/imports/macro.ftl" as m>
<#import "/spring.ftl" as s />

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href='${appServer!""}/admin/role/rolePermissionAddJson/${role.id}.json?navTabId=${dwz.navTabId!""}' 
                target="selectedTodo" rel="permissionKey_in" targetType="${dwz.targetType!""}" 
                title="<@s.message "function.add" /><@s.message "Permission" />">
                	<span><@s.message "function.addBatch" /></span>
                </a>
            </li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="56" >
        <thead>
            <tr >
               <th width="40"><input type="checkbox" group="permissionKey_in" class="checkboxCtrl"></th>
               <th width="120"  ><@s.message "Permission.permissionKey" /></th>
               <th width="160"><@s.message "Permission.permissionName" /></th>
               <th><@s.message "Permission.uri" /></th>
            </tr>
        </thead>
        <tbody>
	       <#list permissions as rs>
           <tr target="roleKey_in" rel="${rs.permissionKey}"> 
                <td><input name="permissionKey_in" value="${rs.permissionKey}" type="checkbox"></td>
                <td>
                   	${rs.permissionKey}
                </td>
                <td>
                   	${rs.permissionName}
                </td>
                <td>
                   	${rs.uri}
                </td>
            </tr>
	       	</#list>
        </tbody>
    </table>
</div>

