<#include "/include/dev.ftl">
<#import "/admin/imports/macro.ftl" as m>
<#import "/spring.ftl" as s />

<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href='${appServer!""}/admin/user/userRoleAddJson/${user.id}.json?navTabId=${dwz.navTabId!""}' 
                target="selectedTodo" rel="roleKey_in" targetType="${dwz.targetType!""}" 
                title="<@s.message "function.add" /><@s.message "Role" />">
                	<span><@s.message "function.addBatch" /></span>
                </a>
            </li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="56" >
        <thead>
            <tr >
               <th width="40"><input type="checkbox" group="roleKey_in" class="checkboxCtrl"></th>
               <th width="120"  ><@s.message "Role.roleKey" /></th>
               <th ><@s.message "Role.roleName" /></th>
            </tr>
        </thead>
        <tbody>
	       <#list roles as rs>
           <tr target="roleKey_in" rel="${rs.roleKey}"> 
                <td><input name="roleKey_in" value="${rs.roleKey}" type="checkbox"></td>
                <td>
                   	${rs.roleKey}
                </td>
                <td>
                   	${rs.roleName}
                </td>
            </tr>
	       	</#list>
        </tbody>
    </table>
</div>

