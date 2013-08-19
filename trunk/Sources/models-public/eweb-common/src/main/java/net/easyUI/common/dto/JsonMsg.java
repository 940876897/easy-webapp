package net.easyUI.common.dto;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JsonMsg extends BaseDTO {
	private static final long serialVersionUID = 8110183649754865707L;
	@SuppressWarnings("unused")
    private Log logger = LogFactory.getLog(this.getClass());
	
	private boolean result = false;
	private String msg = "";

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
