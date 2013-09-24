	                <div class="accordion" fillSpace="sidebar">
	                    
	                    <div class="accordionHeader">
	                        <h2><span>Folder</span>系统设置</h2>
	                    </div>
	                    <div class="accordionContent">
	                        <ul class="tree treeFolder">
                             <li><a>权限管理</a>
                                    <ul>
                                        <li><a href="${appServer!""}/admin/role/indexAjax.htm?dwzId=dwz_tab_role" target="navTab" rel="dwz_tab_role">角色</a></li>
                                        <li><a href="${appServer!""}/admin/user/indexAjax.htm?dwzId=dwz_tab_user" target="navTab" rel="dwz_tab_user">操作员</a></li>
                                        <li><a href="${appServer!""}/admin/permission/indexAjax.htm?dwzId=dwz_tab_permission" target="navTab" rel="dwz_tab_permission">权限设置</a></li>
                                    </ul>
                             </li>
                             
	                         <li><a>缓存与字典配置管理</a>
	                         	<ul>
                         			<li><a href="${appServer!""}/admin/webConfig/indexAjax.htm?dwzId=dwz_tab_webConfig_VM&vmPre=VM&cfgGroup=dbVMs" target="navTab" rel="dwz_tab_webConfig_VM">VM模板管理</a></li>
                         			<li><a href="${appServer!""}/admin/webConfig/indexAjax.htm?dwzId=dwz_tab_webConfig_i18n&vmPre=i18n&cfgGroup=i18n" target="navTab" rel="dwz_tab_webConfig_i18n">i18n管理</a></li>
                         			<li><a href="${appServer!""}/admin/webConfig/indexAjax.htm?dwzId=dwz_tab_webConfig" target="navTab" rel="dwz_tab_webConfig">字典设置</a></li>
                         			<li><a href="${appServer!""}/cacheManager/indexAjax.htm?dwzId=dwz_tab_cacheManager" target="navTab" rel="dwz_tab_cacheManager">缓存管理</a></li>
	                         	</ul>
	                         </li>
	                        </ul>
	                    </div>
	                    
	                    <div class="accordionHeader">
	                        <h2><span>Folder</span>业务系统</h2>
	                    </div>
	                    <div class="accordionContent">
	                        <ul class="tree treeFolder">
                             <li><a>文档管理</a>
                                <ul>
										<li><a href="${appServer!""}/admin/post/indexAjax.htm?dwzId=dwz_tab_post" target="navTab" rel="dwz_tab_post">#dbMsg("Post")</a></li>
                                </ul>
                             </li>
                             
	                         <li><a>评论管理</a>
	                         	<ul>
	                         		
	                         	</ul>
	                         </li>

	                        </ul>
	                    </div>
	                </div>