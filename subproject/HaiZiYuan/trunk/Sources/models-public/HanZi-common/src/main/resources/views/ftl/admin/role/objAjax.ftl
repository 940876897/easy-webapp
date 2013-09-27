<#include "/include/dev.ftl">
<#import "/admin/imports/macro.ftl" as m>
<#import "/spring.ftl" as s />
<#--  <@spring.bind>和<@spring.bindEscaped>支持将信息中的HTML码进行转码 -->
    
    <#-- 对象的添加add,修改edit,查看view表单页面(operType) -->
<div class="pageContent">
    <form method="post" 
<#if (operType!"")=="edit"|| (operType!"")=="add">
        action="${appServer!""}/admin/role/save/json.json?navTabId=${dwz.navTabId}&dwzId=${dwz.dwzId!""}" 
        onsubmit="return validateCallback(this, ${dwz.targetType}AjaxDone);"
<#else>
        action="" onsubmit="return false;"
</#if>
        class="pageForm required-validate">
        
        <div class="pageFormContent" layoutH="56">
        	<@s.bind "role.id" />
             <input type="hidden" name="${s.status.expression!""}" value="${s.status.value!""}" />
            
    
        	<@s.bind "role.roleKey" />
            <dl> 
                <dt><@s.message "Role.roleKey" />：</dt> 
                <dd> 
                    <input type="text"  name="${s.status.expression!""}" value="${s.status.value!""}" class="required" maxlength="16" size="30"/>
                </dd> 
            </dl> 
                
    
        	<@s.bind "role.roleName" />
            <dl> 
                <dt><@s.message "Role.roleName" />：</dt> 
                <dd> 
                    <input type="text"  name="${s.status.expression!""}" value="${s.status.value!""}" class=" " maxlength="64" size="30"/>
                </dd> 
            </dl> 
                                   <!--  下面开始修改更多属性集： -->  
                                   <#--  以下代码还有问题，无法解决，从子对象Map中取数据时，总是异常
            <dl> 
                <dt><@s.message "RoleMeta.metaKey.enabled" />：</dt> 
                <dd> 
        	<@s.bind "role.roleMetas[0].cfgName" />
                    <input type="hidden" name="${s.status.expression!""}" value="${_ConstantDTO.roleMeta_Key_ENABLED}"/>
        	<@s.bind "role.roleMetas[0].cfgValue" />
        		<#assign tmpEnabled = role.metaMap["enabled"] /> 
                    <label class="autoWidth" ><input type="radio" name="${s.status.expression!""}" <#if (tmpEnabled!"") !="0"> checked </#if> value="1"/>启用</label>
                    <label class="autoWidth" ><input type="radio" name="${s.status.expression!""}" <#if (tmpEnabled!"")=="0"> checked </#if> value="0"/>禁用</label>
                </dd> 
            </dl> 
            -->
    
        	<@s.bind "role.memo" />
            <dl class="nowrap"> 
                <dt><@s.message "Role.roleKey" />：</dt> 
                <dd> 
                    <textarea name="${s.status.expression!""}" class="fullWidth"  maxlength="65535"  rows="8">${s.status.value!""}</textarea>
                </dd> 
            </dl> 
            
            
           <!--  如果是修改和查看，则显示这个角色下的权限列表，以修改修改链接。dialog mask="true" -->
        <#if (operType!"")!="add">  
            <dl class="nowrap"> 
                <dt><@s.message "Permission" />列表：
                <#if (operType!"")=="edit">  
                    <a href='${appServer!""}/admin/role/rolePermissionAddAjax/${(role.id)!""}.htm?navTabId=${dwz.dwzId!""}&dwzId=${dwz.dwzId!""}_addPermission&targetType=dialog' 
                    	target="dialog"  class="btnAdd"  rel="${dwzId!""}_addPermission" style="float:right;"
                        title="<@s.message "function.add" />[${role.roleName!""}]<@s.message "Permission" />">
                        <@s.message "function.add" />
                    </a>
               	</#if>
                </dt> 
                <dd> 
                    <div class="panel collapse fullWidth" minH="170" defH="220">
                        <h1>此<@s.message "Role" />拥有的<@s.message "Permission" />列表： </h1>
                            <div>
                               <table class="table" width="100%" nowrapTD="false">
                                    <thead>
                                        <tr >
	                                       <th width="120" > <@s.message "Permission.permissionKey" /> </th>
	                                       <th width="160" > <@s.message "Permission.permissionName" /> </th>
                                           <th > <@s.message "Permission.uri" /> </th>
                                           <th width="40">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
	      						  <#list role.permissions as rs>
                                       <tr >  
                							<td>${rs.permissionKey!""} </td>
                							<td>${rs.permissionName!""} </td>
                							<td>${rs.uri!""} </td>
                                            
                                            <td >
		                                        <#if (operType!"")=="edit"> 
		                                        <#assign tmpUrl=(appServer!"")+("/admin/role/rolePermissionDelJson/")+(role.id!"")+"/"+(rs.permissionKey!"")+(".json?navTabId=")+(dwz.dwzId!"")+"&callbackType=forward"/>
		                                             <a href="${tmpUrl}" target="ajaxTodo" title="你确定要<@s.message "function.del" />这个<@s.message "Permission" />[${rs.permissionName!""}]吗?" class="btnDel"><@s.message "function.del" /></a>
		                                        </#if>
                                            </td>
                                       
                                        </tr>
	       						</#list>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                </dd> 
            </dl> 
   </#if>
        </div>
        
        
        
        <div class="formBar">
            <ul>
	            <#if (operType!"")=="edit"|| (operType!"")=="add">
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit"><@s.message "function.save" /></button></div></div></li>
				</#if>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close"><@s.message "function.close" /></button></div></div></li>
            </ul>
        </div>
    </form>
</div>


