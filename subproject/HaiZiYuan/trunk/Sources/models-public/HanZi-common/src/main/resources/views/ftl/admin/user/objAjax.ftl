<#include "/include/dev.ftl">
<#import "/admin/imports/macro.ftl" as m>
<#import "/spring.ftl" as s />
<#--  <@spring.bind>和<@spring.bindEscaped>支持将信息中的HTML码进行转码 -->

    <#-- 对象的添加add,修改edit,查看view表单页面(operType) -->
<div class="pageContent">
    <form method="post" 
<#if (operType!"")=="edit"|| (operType!"")=="add">
        action="${appServer!""}/admin/user/save/json.json" 
        onsubmit="return validateCallback(this, navTabAjaxDone);"
<#else>
        action="" onsubmit="return false;"
</#if>
        class="pageForm required-validate">
        
        <div class="pageFormContent" layoutH="56">
        	<@s.bind "user.id" />
             <input type="hidden" name="${s.status.expression!""}" value="${s.status.value!""}"/>
        
        	<@s.bind "user.displayName" />
	        <dl> 
	            <dt><@s.message "User.displayName" />：</dt> 
	            <dd> 
	                <input  type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class="required" maxlength="64" size="30"  <#if (operType!"")!="add">  readonly="readonly" </#if> />
	                <span class = "info"></span>
	            </dd> 
	        </dl> 
	        
        	<@s.bind "user.loginName" />
	        <dl> 
	            <dt> <@s.message "User.loginName" />：</dt> 
	            <dd> 
	                <input  type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class="required" maxlength="48" size="30"  <#if (operType!"")!="add">  readonly="readonly" </#if>/>
	                <span class = "info"></span>
	            </dd> 
	        </dl> 
	            
	
        	<@s.bind "user.password" />
	        <dl> 
	            <dt> <@s.message "User.password" />：</dt> 
	            <dd> 
	                <input  type="text" name="${s.status.expression!""}" title="如不需要修改，请保持空白，默认密码123456" 
	                <#if s.status.value?exists>value=""<#else>value="123456" </#if>
		                class="textInput" maxlength="64" size="30" <#if (operType!"")!="edit" &&  (operType!"")!="add"> readonly="readonly" </#if>/>
	                <span class = "info"></span>
	            </dd> 
	        </dl> 
	            
        	<@s.bind "user.nicename" />
	        <dl> 
	            <dt> <@s.message "User.nicename" />：</dt> 
	            <dd> 
	                <input  type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class="required" maxlength="48" size="30" <#if (operType!"")!="edit" &&  (operType!"")!="add"> readonly="readonly" </#if>/>
	                <span class = "info"></span>
	            </dd> 
	        </dl> 
	
        	<@s.bind "user.email" />
	        <dl> 
	            <dt> <@s.message "User.email" />：</dt> 
	            <dd> 
	                <input  type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class=" validate-email" maxlength="128" size="30" <#if (operType!"")!="edit" &&  (operType!"")!="add"> readonly="readonly" </#if>/>
	                <span class = "info"></span>
	            </dd> 
	        </dl> 
	            
	
        	<@s.bind "user.website" />
	        <dl> 
	            <dt> <@s.message "User.website" />：</dt> 
	            <dd> 
	                <input  type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class="" maxlength="128" size="30" <#if (operType!"")!="edit" &&  (operType!"")!="add"> readonly="readonly" </#if>/>
	                <span class = "info"></span>
	            </dd> 
	        </dl> 
	
        	<@s.bind "user.gmtRegistered" />
	        <dl> 
	            <dt> <@s.message "User.gmtRegistered" />：</dt>
	            <dd>
	               <input  type="text" name="${s.status.expression!""}"  yearstart="-20" yearend="20"  format="yyyy-MM-dd" value="${user.gmtRegisteredString}" class=" date textInput" size="30"  readonly="readonly"/>
	                <span class = "info"></span>
	            </dd> 
	        </dl> 
	            
	
        	<@s.bind "user.status" />
	        <dl> 
	            <dt> <@s.message "User.status" />：</dt>
	            <dd> <!-- EnumEnableStatus -->
	            <#if (operType!"")!="edit" &&  (operType!"")!="add"> <#assign readonly="readonly"> <#else> <#assign readonly=""> </#if>
                	<@m.enumSelectEl  EnumEnableStatusList   s.status.expression!""  s.status.value!""  ""  readonly  "required"/>
	                <span class = "info"></span>
	            </dd> 
	        </dl>
        
                 
            <!--  如果是修改和查看，则显示这个<@s.message "User" />下的<@s.message "Role" />列表，以修改修改链接。dialog mask="true" -->
            <#if (operType!"")!="add">  
            <dl class="nowrap"> 
                <dt><@s.message "Role" />列表：
                <#if (operType!"")=="edit">  
                    <a href='${appServer!""}/admin/user/userRoleAddAjax/${(user.id)!""}.htm?dwzId=${dwzId!""}&targetType=dialog' 
                    	target="dialog"  class="btnAdd"  rel="dwz_tab_userRole_add_${user.id!""}" style="float:right;"
                        title="<@s.message "function.add" />[${user.loginName!""}]<@s.message "Role" />">
                        <@s.message "function.add" />
                    </a>
               	</#if>
                </dt> 
                <dd> 
                    <div class="panel collapse fullWidth" minH="170" defH="220">
                        <h1>此<@s.message "User" />拥有的<@s.message "Role" />列表： </h1>
                        <div>
                           <table class="table" width="100%" nowrapTD="false">
                                <thead>
                                    <tr >
                                       <th width="120" > <@s.message "Role.roleKey" /> </th>
                                       <th  > <@s.message "Role.roleName" /> </th>
                                       <th width="120">操作</th>
                                    </tr>
                                </thead>
                                <tbody>
	      						  <#list user.roles as rs>
                                   <tr >  
        								<td>
                                            ${rs.roleKey!""}
                                        </td>
           								<td>
                                            ${rs.roleName!""}
                                        </td>
                                        <td >
                                        <#if (operType!"")=="edit">  
                                        <#assign tmpUrl=appServer!""+"/admin/user/userRoleDelJson/"+user.id!""+"/"+rs.roleKey!""+".htm?dwzId="+dwzId!"" />
                                             <a href="${tmpUrl!""}" target="ajaxTodo" title="你确定要<@s.message "function.del" />这个<@s.message "Role" />[${rs.roleName!""}吗?" class="btnDel"><@s.message "function.del" /></a>
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
				<#else>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close"><@s.message "function.close" /></button></div></div></li>
				</#if>
            </ul>
        </div>
    </form>
</div>
