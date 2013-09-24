<#include "/include/dev.ftl">
<#import "/spring.ftl" as s >

<#import "/admin/layout/dwzAdmin.ftl" as layout>
<#import "/admin/imports/macro.ftl" as m>
<@layout.dwzAdminLayout title="后台首页 ">


                        <div class="accountInfo">
                            <div class="alertInfo">
                                <h2><a href="#" target="_blank">使用手册</a></h2>
                                <a href="#" target="_blank">学习</a>
                            </div>
                            <div class="right">
                                <p><a href="#" target="_blank" style="line-height:19px">使用手册</a></p>
                                <p><a href="#" target="_blank" style="line-height:19px">学习</a></p>
                            </div>
                            <p><span>功能</span></p>
                            <p>功能说明:<a href="#" target="_blank">**********</a></p>
                        </div>
                        
                        <div class="pageFormContent" layoutH="80" >
                            
                            <h2>更新缓存:</h2>
                            <div >
                                    系统缓存,此功能块主要提供手动更新系统级缓存,但是要考虑到集群服务器,将来应该提供分布式缓存,或者更新通知全部服务器,以及自动定时更新缓存等手段来同步更新所有缓存.
                            </div>
                            <div class="unit">
                                <p>
                                <a class="button " href="${appServer!""}/cacheManager/reloadWebConfigCache.json" target="ajaxTodo" title="当修改了字典(包括VM模板,权限,角色等)必须通过此操作来更新缓存"><span>更新字典缓存</span></a> 
                                </p>
                                <!--
                                <p>
                                <a class="button " href="${appServer!""}/cacheManager/reloadCache.cleanVMcache.json" target="ajaxTodo" title="当修改了VM模板时,需要先[更新字典缓存],再使用此功能来更新模板缓存,否则将无法生效."><span>更新VM模板缓存</span></a>
                                </p>
                                -->
                            </div>
                            <div class="divider"></div>
                            
                            <h2>常用操作:</h2>
                            
                            <div class="unit"><a href="#" target="_blank">常用操作3</a></div>
                            <div class="unit"><a href="#" target="_blank">常用操作4</a></div>
                            <div class="unit"><a href="#" target="_blank">常用操作5</a></div>
    
                            <div class="divider"></div>
                            <h2>常见问题及解决:</h2>
                            <pre style="margin:5px;line-height:1.4em">
                                Error loading XML document: dwz.frag.xml
                                直接用IE打开index.html弹出一个对话框：Error loading XML document: dwz.frag.xml
                                原因：没有加载成功dwz.frag.xml。IE ajax laod本地文件有限制, 是ie安全级别的问题, 不是框架的问题。
                                解决方法：用firefox打开或部署到apache下。
                            </pre>
    
                            <div class="divider"></div>
                            <h2>有偿服务请联系:</h2>
                            <pre style="margin:5px;line-height:1.4em;">
                                定制化开发，公司培训，技术支持
                                合作电话：******
                                邮箱：******
                            </pre>
                        </div>

</@layout.dwzAdminLayout>