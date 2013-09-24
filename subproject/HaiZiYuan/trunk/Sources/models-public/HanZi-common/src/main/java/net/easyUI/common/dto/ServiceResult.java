package net.easyUI.common.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * [远程]服务返回对象基类 子类必须有无参构造函数
 * 
 */
public class ServiceResult<T> extends BaseDTO {
	/**
	 * 扩展的多返回数据对象，如果需要返回多个结果时，将对象放入此Map中带回。
	 */
	protected Map<String, Object> resultDataMap = new HashMap<String, Object>();

	private static final long serialVersionUID = -3006110260323581844L;

	private Integer errorNO;// 错误代码,缺省为null,无错误

	private String errorInfo;// 错误原因,国际化配置名

	private Object[] msgArgs = new Object[] {};// 错误原因中参数值,国际化配置名中的参数值

	private String serviceIp;// 执行service的服务器地址
	private T dataObj; // 执行的数据结果

	public ServiceResult() {
		this(null, null, null, null);
	}

	/**
	 * 以数据结果对象来初始化
	 * 
	 * @param dataObj
	 */
	public ServiceResult(T dataObj) {
		this(dataObj, null, null, null);
	}

	/**
	 * 以错误号来初始化
	 * 
	 * @param errorNO
	 */
	public ServiceResult(int errorNO) {
		this(errorNO, null, null);
	}

	/**
	 * 以错误国际化信息和国际化信息参数值来初始化
	 * 
	 * @param errorInfo
	 * @param msgArgs
	 */
	public ServiceResult(String errorInfo, Object[] msgArgs) {
		this(null, 999, errorInfo, msgArgs);
	}

	/**
	 * 以错误号,错误国际化信息和国际化信息参数值来初始化
	 * 
	 * @param errorNO
	 * @param errorInfo
	 * @param msgArgs
	 */
	public ServiceResult(int errorNO, String errorInfo, Object[] msgArgs) {
		this(null, errorNO, errorInfo, msgArgs);
	}

	/**
	 * 以数据结果对象,错误号,错误国际化信息和国际化信息参数值来初始化
	 * 
	 * @param dataObj
	 * @param errorNO
	 * @param errorInfo
	 * @param msgArgs
	 */
	public ServiceResult(T dataObj, Integer errorNO, String errorInfo,
			Object[] msgArgs) {
		this(dataObj, errorNO, errorInfo, msgArgs, null);
	}

	/**
	 * 以数据结果对象,错误号,错误国际化信息和国际化信息参数值,服务器IP地址,来初始化
	 * 
	 * @param dataObj
	 * @param errorNO
	 * @param errorInfo
	 * @param msgArgs
	 * @param serviceIp
	 */
	public ServiceResult(T dataObj, Integer errorNO, String errorInfo,
			Object[] msgArgs, String serviceIp) {
		this.dataObj = dataObj;
		this.errorNO = errorNO;
		this.errorInfo = errorInfo;
		this.serviceIp = serviceIp;
	}

	/**
	 * 是否有错误
	 * 
	 * @return
	 */
	public boolean error() {
		return !correct();
	}

	/**
	 * 是否正确
	 * 
	 * @return
	 */
	public boolean correct() {
		return this.errorNO == null;
	}

	public Integer getErrorNO() {
		return errorNO;
	}

	public void setErrorNO(Integer errorNO) {
		this.errorNO = errorNO;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getServiceIp() {
		return serviceIp;
	}

	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}

	public T getDataObj() {
		return dataObj;
	}

	public void setDataObj(T dataObj) {
		this.dataObj = dataObj;
		this.putData("defaultData", dataObj);
	}

	public void setMsgArgs(Object... msgArgs) {
		this.msgArgs = msgArgs;
	}

	public Object[] getMsgArgs() {
		return msgArgs;
	}

	/**
	 * 获取一个返回数据对象
	 */
	public Object getData(String dataName) {
		return this.resultDataMap.get(dataName);
	}

	/**
	 * 设置(添加/修改)返回数据对象
	 */
	public void putData(String dataName, Object dataObj) {
		this.resultDataMap.put(dataName, dataObj);
	}

}
