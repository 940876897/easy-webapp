package net.easyUI.common.service;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.support.TransactionTemplate;

public class BasePOJOService {
	protected Log logger = LogFactory.getLog(this.getClass());

	protected Locale thisLocale = Locale.CHINA;
	@Autowired
	protected MessageSource messageSource;
	/**
	 * 事务模板
	 */
	@Autowired
	protected TransactionTemplate transactionTemplate;

	//xiejh修改，此处事物采用注入的方式，写手动事物的时候会用到
//	public TransactionTemplate getTransactionTemplate() {
//		return transactionTemplate;
//	}
//
//	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
//		this.transactionTemplate = transactionTemplate;
//	}
    public void afterPropertiesSet() throws Exception {
    }

	public Locale getThisLocale() {
		return thisLocale;
	}

	public void setThisLocale(Locale thisLocale) {
		this.thisLocale = thisLocale;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}
}
