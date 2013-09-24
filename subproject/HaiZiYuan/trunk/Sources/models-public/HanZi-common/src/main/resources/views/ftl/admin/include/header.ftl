    
                <div id="header">
                    <div class="headerNav">
                        <a class="logo" href="#" title="后台管理平台">LOGO图片</a>
                        
                        <ul class="nav">
                            <li id="switchEnvBox"><a href="javascript:">（<span>管理员</span>）切换角色</a>
                                <ul>
                                    <li class="selected"><a href="${appServer!""}/admin/leftSidebar.htm">默认功能</a></li>
                                    <li><a href="${appServer!""}/admin/leftSidebar.htm">管理员</a></li>
                                    <li><a href="${appServer!""}/admin/leftSidebar.htm">文章审核员</a></li>
                                </ul>
                            </li>
                            <li><a href="${appServer!""}/loginAjaxPage.htm" target="dialog" width="500" mask="true" >切换用户</a></li>
                            <li><a href="${appServer!""}/admin/updatePwdAjax.htm" target="dialog" width="500" >修改密码</a></li>
                            <li><a href="#" target="_blank">反馈</a></li>
                            <li><a href="${appServer!""}/logout.htm">退出</a></li>
                            <li><a href="${appServer!""}/admin/help.htm" target="navTab" rel="dwz_tab_help">后台帮助</a></li>
                        </ul>
                        
                        <ul class="themeList" id="themeList">
                            <li theme="default"><div class="selected">蓝色</div></li>
                            <li theme="green"><div>绿色</div></li>
                            <!--<li theme="red"><div>红色</div></li>-->
                            <li theme="purple"><div>紫色</div></li>
                            <li theme="silver"><div>银色</div></li>
                            <li theme="azure"><div>天蓝</div></li>
                        </ul>
                    </div>
                    
                    <!-- 中间导航条。 
                    <div id="navMenu">
                        <ul>
                            <li class="selected"><a href="${appServer!""}/admin/leftSidebar.htm"><span>默认功能</span></a></li>
                            <li><a href="${appServer!""}/admin/leftSidebar.htm"><span>管理员功能</span></a></li>
                            <li><a href="${appServer!""}/admin/leftSidebar.htm"><span>文章审核员功能</span></a></li>
                        </ul>
                    </div>
                    -->
                    
                </div>