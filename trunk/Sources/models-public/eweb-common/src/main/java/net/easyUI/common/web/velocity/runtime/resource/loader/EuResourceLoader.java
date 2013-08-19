package net.easyUI.common.web.velocity.runtime.resource.loader;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.easyUI.common.util.ApplicationContextUtil;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.io.UnicodeInputStream;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

public class EuResourceLoader extends ResourceLoader {

	private static Log log = LogFactory.getLog(EuResourceLoader.class);
	private static Map<String, Object> cacheMap = new HashMap<String, Object>();
	private static String WEB_INF_path = "";
	private static String FILE_ENCODING = "utf-8";
	static {
		try {
			WEB_INF_path = EuResourceLoader.class.getClassLoader()
					.getResource("").toURI().getPath();
			WEB_INF_path = WEB_INF_path.substring(0,
					WEB_INF_path.lastIndexOf("classes"));
		} catch (URISyntaxException e) {
			log.info(e.getMessage());
			WEB_INF_path = "";
		}
	}

	/**
	 * 得到一个资源输入流。
	 * 
	 * <pre>
	 *  1: 如果name是以".cleanVMcache"结尾,则清空缓存.
	 *  2: 如果name是以".vmStr"结尾的,直接将字符串当作模板内容返回.
	 *  3: 先从数据库里取对应模板HTML,Key为:/views/screen/***   (/views/layout/***)  
	 *  	(需要在Spring中配置dataBaseResourceStreamManager和applicationContextUtil对象,如没有配置,则不从数据库中读取模板.)
	 *  4: 如果没有, 再找WEB-INF/views/screen/*** 目录下的VM,   (WEB-INF/views/layout/***)
	 *  5: 如果还是找不到,就找classPath*:vews/screen/*** 下的VM.  (classPath*:vews/layout/***)
	 * 
	 * </pre>
	 * 
	 * @author hs-yuancong 2012-11-01
	 * @param templateName
	 * @return The input stream for the requested resource.
	 * @throws ResourceNotFoundException
	 */
	@Override
	public InputStream getResourceStream(String templateName)
			throws ResourceNotFoundException {
		InputStream result = null;
		if (StringUtils.isNotEmpty(templateName)) {
			templateName = org.apache.velocity.util.StringUtils.normalizePath(
					templateName.trim()).substring(1);
		}
		if (StringUtils.isEmpty(templateName)) {
			if (log.isErrorEnabled()) {
				log.error("EuResourceLoader 失败:  模板名称不能为空.");
			}
			throw new ResourceNotFoundException(
					"EuResourceLoader 失败:  模板名称不能为空.");
		}
		// TODO 三种取模板的方式, 每种里,最好还能自由向上取兼容模板.
		// 检查模板名,如果是以".cleanVMcache"结尾,则清空缓存.并返回"cleanVMcache Success"
		if (templateName.endsWith(".cleanVMcache.vm")) {
			cacheMap.clear();
			log.info("以.cleanVMcache结尾,清空缓存,完成.");
			return getStringResourceStream("#set($layout = '')  {\"statusCode\":\"200\",\"message\":\"VM模板缓存清除成功\",\"navTabId\":\"\",\"callbackType\":\"\",\"forwardUrl\":\"\"}");
		}

		// 0: 判断模板类型,模板如果是以".vmStr"结尾的,直接将字符串当作模板内容返回.
		if (templateName.endsWith(".vmStr.vm")) {
			if (log.isDebugEnabled())
				log.debug("以.vmStr结尾,直接将字符串当作模板内容返回.");
			return getStringResourceStream(templateName.substring(0,
					templateName.lastIndexOf(".vmStr.vm")));
		}

		// TODO 1:从数据库中读取
		if (result == null) {
			if (log.isDebugEnabled()) {
				log.debug(" EuResourceLoader 开始尝试从数据库中读取模板... " + templateName);
			}
			result = getDataBaseResourceStream(templateName);
			if (result == null && log.isDebugEnabled()) {
				String msg = "EuResourceLoader : 从数据库中读取模板失败." + templateName;
				log.debug(msg);
			}
		}

		// 2:从WebRoot/WEB-INF下模板目录中读取
		if (result == null) {
			if (log.isDebugEnabled()) {
				log.debug(" EuResourceLoader 开始尝试从WebRoot/WEB-INF下模板目录中读取模板... "
						+ templateName);
			}
			result = getFileResourceStream(templateName);
			if (result == null && log.isDebugEnabled()) {
				String msg = "EuResourceLoader Error: 从WebRoot/WEB-INF下模板目录中读取模板失败."
						+ templateName;
				log.debug(msg);
			}
		}

		// 3:还是失败,再从ClassPath目录读取
		if (result == null) {
			if (log.isDebugEnabled()) {
				log.debug(" EuResourceLoader 开始尝试从ClassPath和Jar包里读取模板... "
						+ templateName);
			}
			result = getClassPathResourceStream(templateName);
			if (result == null && log.isDebugEnabled()) {
				String msg = "EuResourceLoader : 从ClassPath和Jar包里读取模板失败."
						+ templateName;
				log.debug(msg);
			}
		}
		// 最终返回模板
		if (result == null) {
			String msg = "EuResourceLoader: 以各种方式读取模板失败." + templateName;
			log.error(msg, null);
			throw new ResourceNotFoundException(msg);
		}
		return result;

	}

	/**
	 * 初始化资源加载器 a resources class.
	 * 
	 * @param configuration
	 */
	@Override
	public void init(ExtendedProperties arg0) {
		if (log.isDebugEnabled()) {
			log.debug("初始化资源加载器 : initialization complete.");
		}
	}

	/**
	 * 检查资源是否被修改。
	 * 
	 * @param resource
	 * @return True if the resource has been modified.
	 */
	@Override
	public boolean isSourceModified(Resource arg0) {
		if (log.isDebugEnabled()) {
			log.debug("检查模板资源是否被修改.");
		}
		if (MapUtils.isEmpty(cacheMap))
			return true;
		return false;
	}

	/**
	 * 得到资源流的最后修改时间
	 * 
	 * @param resource
	 * @return Time in millis when the resource has been modified.
	 */
	@Override
	public long getLastModified(Resource arg0) {
		if (log.isDebugEnabled()) {
			log.debug("获取模板资源流的最后修改时间.");
		}
		if (MapUtils.isEmpty(cacheMap))
			return 1;
		return 0;
	}

	/**
	 * 从ClassPath及Jar中读取模板
	 * 
	 * @param templateName
	 */
	private InputStream getClassPathResourceStream(String templateName) {
		InputStream result = null;
		// 检查缓存,是否已有相应模板,如果有,并且没有过期,直接返回缓存中的模板.
		if (cacheMap.containsKey("ClassPath>>>" + templateName)) {
			try {
				result = ((URL) cacheMap.get("ClassPath>>>" + templateName))
						.openStream();
			} catch (IOException e) {
				cacheMap.remove("ClassPath>>>" + templateName);
				result = null;
				if (log.isDebugEnabled()) {
					log.debug("EuResourceLoader : 从ClassPath目录的 缓存中 读取模板失败."
							+ templateName);
				}
			}
		}
		if (result != null) {
			if (log.isDebugEnabled()) {
				log.debug("EuResourceLoader : 从ClassPath目录的 缓存中 读取模板成功."
						+ templateName);
			}
			return result;
		}
		// 3:还是失败,再从ClassPath目录读取
		ClassLoader cl = this.getClass().getClassLoader();
		URL url = cl.getResource(templateName);
		if (url != null) {
			try {
				result = url.openStream();
				cacheMap.put("ClassPath>>>" + templateName, url);
			} catch (IOException e) {
				if (log.isDebugEnabled()) {
					log.debug(
							"EuResourceLoader Exception: 从ClassPath目录读取模板发生异常.",
							e);
				}
			}
		}

		if (result == null) {
			String msg = "EuResourceLoader Error: 从ClassPath目录读取模板失败."
					+ templateName;
			if (log.isDebugEnabled()) {
				log.debug(msg);
			}
		}
		return result;
	}

	/**
	 * 直接返回字符串做模板
	 * 
	 * @param templateDataStr
	 *            模板内容字符串
	 * @throws ResourceNotFoundException
	 */
	private InputStream getStringResourceStream(String templateDataStr) {
		InputStream result = null;
		try {
			result = new ByteArrayInputStream(
					templateDataStr.getBytes(FILE_ENCODING));
		} catch (Exception e) {
			log.error("", e);
			result = null;
		}
		if (log.isDebugEnabled() && result == null) {
			String msg = "EuResourceLoader Error: 直接返回字符串做模板失败."
					+ templateDataStr;
			log.debug(msg);
		}
		return result;
	}

	/**
	 * 从数据库里根据模板路径获取字符串做模板,
	 * 需要在Spring中配置dataBaseResourceStreamManager对象,如没有配置,则不从数据库中读取模板.
	 * 
	 * @param templateName
	 *            模板(含路径)名称
	 * @throws ResourceNotFoundException
	 */
	private InputStream getDataBaseResourceStream(String templateName) {
		InputStream result = null;
		String templateDataStr = "";
		DataBaseResourceStreamManager dataBaseResourceStreamManager = (DataBaseResourceStreamManager) ApplicationContextUtil
				.getBean("dataBaseResourceStreamManager");
		// 1: 从数据库里查询模板内容
		if (dataBaseResourceStreamManager == null) {
			log.info("EuResourceLoader Error: 数据库Manager对象dataBaseResourceStreamManager没有注入,不能从数据库中读取模板.");
			return result;
		}
		// 检查缓存,是否已有相应模板,如果有,并且没有过期,直接返回缓存中的模板.
		if (cacheMap.containsKey("DataBase>>>" + templateName)) {
			templateDataStr = (String) cacheMap.get("DataBase>>>"
					+ templateName);
		} else {
			if (log.isDebugEnabled()) {
				log.debug("EuResourceLoader : 从DataBase的 缓存中 读取模板失败, 将尝试从数据库中读取并放入缓存."
						+ templateName);
			}
			templateDataStr = dataBaseResourceStreamManager
					.getTemplateDataStr(templateName);
			if (templateDataStr != null)
				cacheMap.put("DataBase>>>" + templateName, templateDataStr);
		}
		// 2: 将数据库里的模板字符串转成InputStream流并返回.
		if (StringUtils.isNotEmpty(templateDataStr)) {
			try {
				result = new ByteArrayInputStream(
						templateDataStr.getBytes(FILE_ENCODING));
			} catch (Exception e) {
				log.error("", e);
				result = null;
			}
		}
		if (log.isDebugEnabled() && result == null) {
			String msg = "EuResourceLoader Error: 从数据库里根据模板路径获取字符串做模板失败."
					+ templateName;
			log.debug(msg);
		}
		return result;
	}

	/**
	 * FileResourceLoader,从WebRoot空间里读取模板.
	 * 
	 * @param templateName
	 * @throws ResourceNotFoundException
	 */

	private InputStream getFileResourceStream(String templateName) {
		InputStream inputStream = null;
		try {
			inputStream = findTemplate(templateName);
		} catch (IOException ioe) {
			log.debug(ioe.getMessage());
		}
		if (log.isDebugEnabled() && inputStream == null) {
			String msg = "EuResourceLoader getFileResourceStream : 读取WEB-INF下模板失败."
					+ templateName;
			log.debug(msg);
		}
		return inputStream;
	}

	private InputStream findTemplate(String templateName) throws IOException {
		File file;
		// 找一下缓存里是否有对应的缓存文件,
		if (cacheMap.containsKey("WEB-INF>>>" + templateName)) {
			file = (File) cacheMap.get("WEB-INF>>>" + templateName);
			if (log.isDebugEnabled()) {
				log.debug("EuResourceLoader getFileResourceStream  在缓存中找到对应模板文件."
						+ templateName);
			}
		} else {
			if ("".equals(WEB_INF_path)) {
				file = new File(templateName);
			} else {
				file = new File(WEB_INF_path, templateName);
			}
			// 放入缓存.
			cacheMap.put("WEB-INF>>>" + templateName, file);
		}
		if (!file.canRead()) {
			cacheMap.remove("WEB-INF>>>" + templateName);
			throw new IOException(templateName + " can not read!");
		}

		FileInputStream fis = null;
		UnicodeInputStream uis = null;
		fis = new FileInputStream(file.getAbsolutePath());
		try {
			uis = new UnicodeInputStream(fis, true);
			if (log.isDebugEnabled())
				log.debug("File Encoding for " + file + " is: "
						+ uis.getEncodingFromStream());
			return new BufferedInputStream(uis);
		} catch (IOException e) {
			closeQuiet(uis);
			closeQuiet(fis);
			return new BufferedInputStream(fis);
		}
	}

	private void closeQuiet(InputStream is) {
		if (is != null)
			try {
				is.close();
			} catch (IOException ioe) {
			}
	}
}

// class StringInputStream extends InputStream {
//
// private char[] chars;
// private int cursor;
// private int mark;
//
// private StringInputStream() {
// super();
// cursor = 0;
// mark = 0;
// }
//
// public StringInputStream(CharSequence s) {
// this();
// if (null != s)
// chars = s.toString().toCharArray();
// else
// chars = new char[0];
// }
//
// @Override
// public synchronized void mark(int readlimit) {
// this.mark = readlimit;
// }
//
// @Override
// public boolean markSupported() {
// return true;
// }
//
// @Override
// public int read() throws IOException {
// if (cursor >= chars.length)
// return -1;
// return chars[cursor++]; //转换为int,很有问题
// }
//
// @Override
// public int available() throws IOException {
// return chars.length;
// }
//
// @Override
// public synchronized void reset() throws IOException {
// cursor = mark;
// }
//
// @Override
// public long skip(long n) throws IOException {
// int len = chars.length;
// if (len > cursor + n) {
// cursor += n;
// return n;
// }
// int d = len - 1 - cursor;
// cursor = len;
// return d;
// }
//
// }
