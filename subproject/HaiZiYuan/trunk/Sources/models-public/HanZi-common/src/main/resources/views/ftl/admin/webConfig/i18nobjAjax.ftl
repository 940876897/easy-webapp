<#include "/include/dev.ftl">
<#import "/admin/imports/macro.ftl" as m>
<#import "/spring.ftl" as s />
<#--  <@spring.bind>和<@spring.bindEscaped>支持将信息中的HTML码进行转码 -->

    <#-- 对象的添加add,修改edit,查看view表单页面(operType) -->
<div class="pageContent">
    <form method="post" 
<#if (operType!"")=="edit"|| (operType!"")=="add">
        action="${appServer!""}/admin/webConfig/save/json.json?navTabId=${dwz.navTabId}&dwzId=${dwz.dwzId!""}" 
        onsubmit="return validateCallback(this, ${dwz.targetType}AjaxDone);"
<#else>
        action="" onsubmit="return false;"
</#if>
        class="pageForm required-validate">
        
        <div class="pageFormContent" layoutH="56">
        <@s.bind "webConfig.id" />
        <input type="hidden" name="${s.status.expression!""}" value="${s.status.value!""}" />         
		
		<input type="hidden" name="dwzId" value="${dwzId!""}" />

        <@s.bind "webConfig.cfgName" />
        <dl> 
            <dt>i18n文件名：</dt> 
            <dd> 
                <input type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class="required" maxlength="128" size="30"/>
            </dd> 
        </dl> 
        
        <@s.bind "webConfig.cfgTitle" />
        <dl> 
            <dt>i18n名称：</dt> 
            <dd> 
                <input type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class="required" maxlength="32" size="30"/>
            </dd> 
        </dl> 
        
        <@s.bind "webConfig.cacheType" />
            <input type="hidden" name="${s.status.expression!""}" value="ram" class=" " maxlength="32" size="30"/>
            <#--
        <dl> 
            <dt><@s.message "WebConfig.cacheType" />：</dt> 
            <dd> <!-- EnumWebCfgCacheTypeList --   >
                	<@m.enumSelectEl  EnumWebCfgCacheTypeList   s.status.expression!""  s.status.value!""  ""  ""  s.status.expression!""/>
            </dd> 
        </dl> 
		-->

        <@s.bind "webConfig.cfgGroup" />
            <input type="hidden" name="${s.status.expression!""}" value="i18n" class=" " maxlength="32" size="30"/>
            <#--
        <dl> 
            <dt><@s.message "WebConfig.cfgGroup" />：</dt> 
            <dd> <!-- EnumWebCfgGroup -- >
                	<@m.enumSelectEl  EnumWebCfgGroupList   s.status.expression!""  s.status.value!""  ""  "1"   "required"/>
            </dd> 
        </dl> 
		-->

        <@s.bind "webConfig.rank" />
        <dl> 
            <dt><@s.message "WebConfig.rank" />：</dt> 
            <dd> 
                <input type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class="validate-number  " maxlength="5" size="30"/>
            </dd> 
        </dl> 
        
        <@s.bind "webConfig.dataStatus" />
        <dl> 
            <dt><@s.message "WebConfig.dataStatus" />：</dt> 
            <dd> <!-- EnumWebCfgStatusList -->
                	<@m.enumSelectEl  EnumWebCfgStatusList   s.status.expression!""  s.status.value!""  ""  ""   "required"/>
            </dd> 
        </dl> 
        
        <@s.bind "webConfig.operator" />
            <input type="hidden" name="${s.status.expression!""}" value="${s.status.value!""}" class=" " maxlength="32" size="30"/>
            <#--
        <dl> 
            <dt><@s.message "WebConfig.operator" />：</dt> 
            <dd> 
                <input type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class=" " maxlength="32" size="30"/>
            </dd> 
        </dl> 
         -->
         
        <@s.bind "webConfig.inputType" />
            <input type="hidden" name="${s.status.expression!""}" value="${s.status.value!""}" class=" " maxlength="32" size="30"/>
            <#--
        <dl> 
            <dt><@s.message "WebConfig.inputType" />：</dt> 
            <dd> <!-- EnumWebCfgInputType --     >
                	<@m.enumSelectEl  EnumWebCfgInputTypeList   s.status.expression!""  s.status.value!""  ""  ""   "required"/>
            </dd> 
        </dl> 
         -->
         
        <@s.bind "webConfig.permission" />
        <dl> 
            <dt><@s.message "WebConfig.permission" />：</dt> 
            <dd>  <!-- EnumWebCfgPermission -->
                	<@m.enumSelectEl  EnumWebCfgPermissionList   s.status.expression!""  s.status.value!""  ""  ""   "required"/>
            </dd> 
        </dl> 
        
        <@s.bind "webConfig.dataUnit" />
            <input type="hidden" name="${s.status.expression!""}" value="${s.status.value!""}" class=" " maxlength="32" size="30"/>
            <#--
        <dl> 
            <dt><@s.message "WebConfig.dataUnit" />：</dt> 
            <dd> 
                <input type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class=" " maxlength="32" size="30"/>
            </dd> 
        </dl> 
         -->
         
        <@s.bind "webConfig.meta32" />
        <dl> 
            <dt>所属模块：</dt> 
            <dd> 
                <input type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class=" " maxlength="32" size="30"/>
            </dd> 
        </dl> 
        
        <#-- 
        <@s.bind "webConfig.meta64" />
        <dl> 
            <dt><@s.message "WebConfig.meta64" />：</dt> 
            <dd> 
                <input type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class=" " maxlength="64" size="30"/>
            </dd> 
        </dl> 
        <@s.bind "webConfig.meta128" />
        <dl> 
            <dt><@s.message "WebConfig.meta128" />：</dt> 
            <dd> 
                <input type="text" name="${s.status.expression!""}" value="${s.status.value!""}" class=" " maxlength="128" size="30"/>
            </dd> 
        </dl> 
     -->
        
        <@s.bind "webConfig.cfgValue" />
<#assign editor_tmp_id=_UUID.generate() />
            <dl class="nowrap"> 
                <dt><@s.message "WebConfig.cfgValue" />：
                	<br/><a class="button" href="javascript:euFn.codeEdit.showEditor('${editor_tmp_id!""}','json');"><span>code编辑</span></a>
                </dt> 
                <dd> 
                    <textarea name="${s.status.expression!""}" id="${editor_tmp_id!""}" class="fullWidth" maxlength="65535" cols="100" rows="8">${(s.status.value!"")?html}</textarea>
                </dd> 
            </dl> 
            <script>euFn.codeEdit.showEditor('${editor_tmp_id!""}','json');</script>
            
        <@s.bind "webConfig.memo" />
            <dl class="nowrap"> 
                <dt><@s.message "WebConfig.memo" />：</dt> 
                <dd> 
                    <textarea name="${s.status.expression!""}" class="" maxlength="512" cols="100" rows="6">${(s.status.value!"")?html}</textarea>
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


