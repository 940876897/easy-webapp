package net.easyUI.common.web.velocity.runtime.resource.loader;

/**
 * 此接口主要提供给VM模板加载器从数据库读取模板时使用, 如果需要从数据库中读取模板, 请提供此接口的实现,
 * 请在EuResourceLoader的Beans配置中注入此对象.
 * 
 * @author hs-yuancong 2012-11-03
 * 
 */
public interface DataBaseResourceStreamManager {
	/**
	 * 通过模板名称,读取对应模板字符串.
	 * 
	 * @param templateName
	 *            模板路径名称 , 如: views/layout/default.vm , views/screen/index.vm ,
	 *            views/screen/contain/header.vm
	 * @return
	 */
	String getTemplateDataStr(String templateName);

}
