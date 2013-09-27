<#include "/include/dev.ftl">
<#import "/admin/imports/macro.ftl" as m>
<#import "/spring.ftl" as s />
<#--  <@spring.bind>和<@spring.bindEscaped>支持将信息中的HTML码进行转码 -->
    
    <#-- 对象的添加add,修改edit,查看view表单页面(operType) -->
<div class="pageContent">
    <form method="post"  
<#if (operType!"")=="edit"|| (operType!"")=="add">
        action="${appServer!""}/admin/permission/save/json.json?navTabId=${dwz.navTabId}&dwzId=${dwz.dwzId!""}" 
        onsubmit="return validateCallback(this, ${dwz.targetType}AjaxDone);"
<#else>
        action="" onsubmit="return false;"
</#if>
        class="pageForm required-validate">
  
        <div class="pageFormContent" layoutH="56">
        <@s.bind "permission.id" />
             <input type="hidden" name="${s.status.expression!""}" value="${s.status.value!""}" />

        <@s.bind "permission.permissionKey" />
            <dl> 
                <dt><@s.message "Permission.permissionKey" />：</dt> 
                <dd> 
                    <input type="text"  name="${s.status.expression!""}" value="${s.status.value!""}"  class="required  " maxlength="16" size="30" title="supper以及以supper开头的Key,是超级权限,   login以及以login开头的Key,是登录要求的权限,   guest以及以guest开头的Key,是表示这些URL游客就可以访问的"/>
                    <a class="btnInfo" href="javascript:;" title="填写说明提示" onclick="alertMsg.info('supper以及以supper开头的Key,是超级权限,<br>login以及以login开头的Key,是登录要求的权限,<br>guest以及以guest开头的Key,是表示这些URL游客就可以访问的')"><span>提示</span></a>
                    <font color="#FF0000">${s.status.errorMessage!""}</font>
                </dd> 
            </dl> 
                
    
        <@s.bind "permission.permissionName" />
            <dl> 
                <dt><@s.message "Permission.permissionName" />：</dt> 
                <dd> 
                    <input type="text"  name="${s.status.expression!""}" value="${s.status.value!""}"  class=" " maxlength="64" size="30"/>
                </dd> 
            </dl> 
                
    
            <@s.bind "permission.uri" />
            <dl class="nowrap"> 
                <dt><@s.message "Permission.uri" />：</dt> 
                <dd> 
                    <input type="text"  name="${s.status.expression!""}" value="${s.status.value!""}"  class=" " maxlength="128" size="30"/>
                </dd> 
            </dl> 
            
                        <!-- 下面开始修改更多属性集：   -->
            <#--
            <dl> 
                <dt>状态：</dt> 
                <dd> 
                <@s.bind "permission.permissionMetas[0].cfgName" />
                    <input type="hidden"  name="${s.status.expression!""}" value="$!{constantUtils.permissionMeta_Key_ENABLED}"/>
                <@s.bind "permission.permissionMetas[0].cfgValue" />
                #set ($tmpEnabled = $!{permission.metaMap.get("$!{constantUtils.permissionMeta_Key_ENABLED}")})
                    <label class="autoWidth" ><input type="radio"  name="${s.status.expression!""}"  #if("$!{tmpEnabled}"!="0") checked #end value="1"/>启用</label>
                    <label class="autoWidth" ><input type="radio"  name="${s.status.expression!""}"  #if("$!{tmpEnabled}"=="0") checked #end value="0"/>禁用</label>
                </dd> 
            </dl> 
    	-->
    	
            <@s.bind "permission.memo" />
            <dl class="nowrap"> 
                <dt><@s.message "Permission.memo" />：</dt> 
                <dd> 
                    <textarea  name="${s.status.expression!""}" class="fullWidth " maxlength="65535" rows="12">${s.status.value!""?html} </textarea>
                </dd> 
            </dl> 
        
        </div>
        
        <div class="formBar">
            <ul>
            <#if (operType!"")=="edit"|| (operType!"")=="add">
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit"><@s.message "function.save" /> </button></div></div></li>
			</#if>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close"><@s.message "function.close" /> </button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>


