package net.easyUI.access;

/**
 * 后台没有权限时抛出的异常. TODO 如何区分对待Ajax请求时,响应返回Ajax类型错误信息
 * 
 * @author busfly
 * 
 */
public class UserAccessDeniedException extends RuntimeException {

	private static final long serialVersionUID = -4757581999998896852L;

	public UserAccessDeniedException() {
		super();
	}

	public UserAccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserAccessDeniedException(String message) {
		super(message);
	}

	public UserAccessDeniedException(Throwable cause) {
		super(cause);
	}

}
