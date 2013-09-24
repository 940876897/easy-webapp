package net.easyUI.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware {
	public static ApplicationContext context;// 声明一个静态变量保存

	public void setApplicationContext(ApplicationContext contex)
			throws BeansException {
		ApplicationContextUtil.context = contex;
	}

	public static ApplicationContext getContext() {
		return ApplicationContextUtil.context;
	}

	public static Object getBean(String beanNameId) {
		if (ApplicationContextUtil.context == null
				|| ApplicationContextUtil.context.containsBean(beanNameId) == false)
			return null;
		return ApplicationContextUtil.context.getBean(beanNameId);
	}
}
