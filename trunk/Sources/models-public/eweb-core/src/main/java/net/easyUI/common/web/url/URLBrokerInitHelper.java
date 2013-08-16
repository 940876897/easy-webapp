package net.easyUI.common.web.url;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class URLBrokerInitHelper {

	protected final Log logger = LogFactory.getLog(this.getClass());

	protected URLBroker[] brokers;

	public URLBroker[] getBrokers() {
		return brokers;
	}

	public void setBrokers(URLBroker[] brokers) {
		this.brokers = brokers;
	}

	public void setBroker(URLBroker broker) {
		this.brokers = new URLBroker[] { broker };
	}

}
