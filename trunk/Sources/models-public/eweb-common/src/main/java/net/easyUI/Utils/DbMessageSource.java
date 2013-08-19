package net.easyUI.Utils;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import net.easyUI.domain.common.WebConfig;
import net.easyUI.dto.common.CacheDTO;
import net.easyUI.dto.common.enums.EnumWebCfgGroup;
import net.easyUI.common.util.ApplicationContextUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

//i18n国际化支持 #i18nMsg  从缓存中查看这个MsgKey,返回对应的国际语言,如果没有最对应的,就使用默认的.  
@Component("messageSource")
public class DbMessageSource implements MessageSource {
	protected Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 仅供VM中调用.
	 * 
	 * @param msgKey
	 * @return
	 */
	public String vmMsg(HttpServletRequest request, String msgKey,
			Object[] args, String defaultMessage, Locale locale) {
		// 如果Locale为Null,则使用Cookies和浏览器,或服务器的Locale.
		if (locale == null) {
			// TODO 如果Cookies中有Locale设置,则使用Cookies中的Locale.

			// 如果Cookies中也没有Locale设置,则使用浏览器的Locale.
			if (locale == null) {
				AcceptHeaderLocaleResolver localeResolver = (AcceptHeaderLocaleResolver) ApplicationContextUtil
						.getBean("localeResolver");
				locale = localeResolver.resolveLocale(request);
			}
			// 如果浏览器的Locale也是Null,则使用服务器的Locale.
			if (locale == null)
				locale = Locale.getDefault();
		}
		return getMessage(msgKey, args, null, locale);
	}

	@SuppressWarnings("unchecked")
	public String getMessage(String msgKey, Object[] args,
			String defaultMessage, Locale locale) {
		if (StringUtils.isBlank(msgKey))
			return "";
		// 从缓存中获取国际化信息设置. {msgKey1_zh_CN:value,msgKey2_zh_CN:value, ... }
		if (MapUtils.isEmpty(CacheDTO.i18nMsg)) {
			Collection<WebConfig> msgCfgList = WebCacheUtils
					.webConfigByGroup(EnumWebCfgGroup.I18N.getCode());
			if (CollectionUtils.isNotEmpty(msgCfgList)) {
				for (WebConfig webConfig : msgCfgList) {
					CacheDTO.i18nMsg.putAll(webConfig.getCfgValueJsonMap());
					// 不重复保留缓存数据,以免浪费内存.
					CacheDTO.webConfigMap.remove(webConfig.getCfgGroup() + "_" + webConfig.getCfgName());
				}
			}
		}

		String tmpMsgKey = msgKey;
		// 先取KEY_语言_国家
		tmpMsgKey = msgKey + "_" + locale.getLanguage() + "_"
				+ locale.getCountry();
		// 没有找到,再找KEY_语言
		if (!CacheDTO.i18nMsg.containsKey(tmpMsgKey)) {
			tmpMsgKey = msgKey + "_" + locale.getLanguage();
		}
		// 没有找到,再取默认值
		if (!CacheDTO.i18nMsg.containsKey(tmpMsgKey)) {
			tmpMsgKey = msgKey;
		}
		String msgValue = "";
		// 没找到此KEY时,直接返回KEY的最后一节字符串.
		if (!CacheDTO.i18nMsg.containsKey(tmpMsgKey)) {
			// TODO 如果是Debug模式,是否应该抛出异常,以便开发时发现及处理.
			logger.error("dbMsg中没有找到这个KEY = "+msgKey);
			msgValue = StringUtils.isBlank(defaultMessage) ? (msgKey
					.substring(msgKey.lastIndexOf(".") + 1)) : defaultMessage;
		} else {
			msgValue = CacheDTO.i18nMsg.get(tmpMsgKey);
			// 将参数args组装到Msg里.
			if (args != null && args.length > 0) {
				msgValue = MessageFormat.format(msgValue, args);
			}
		}
		return msgValue;
	}

	public String getMessage(String msgKey, Object[] args, Locale locale)
			throws NoSuchMessageException {
		return getMessage(msgKey, args, null, locale);
	}

	public final String getMessage(MessageSourceResolvable resolvable,
			Locale locale) {
		String[] codes = resolvable.getCodes();
		if (codes == null) {
			codes = new String[0];
		}
		for (String code : codes) {
			String msg = getMessage(code, resolvable.getArguments(),
					"_notFindMsg_tmp", locale);
			if (!"_notFindMsg_tmp".equals(msg)) {
				return msg;
			}
		}
		String defaultMessage = resolvable.getDefaultMessage();
		return defaultMessage;
	}

}
