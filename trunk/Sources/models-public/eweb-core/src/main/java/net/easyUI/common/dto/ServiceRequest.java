package net.easyUI.common.dto;

import java.util.HashMap;
import java.util.Map;

public class ServiceRequest extends BaseDTO {

	private static final long serialVersionUID = 2875808895911088460L;
	/**
	 * 通用的参数Map,各子类可以直接扩展Request属性字段,以方便理解和使用.一般建议至少两个对象, 一个是用户数据对象userDto,
	 * 另一个是操作数据对象resDto
	 */
	protected Map<String, Object> requestMap = new HashMap<String, Object>();

	public ServiceRequest() {

	}

	public ServiceRequest(UserDto userDto) {
		this(null, userDto);
	}

	public ServiceRequest(Object resDto) {
		this(resDto, null);
	}

	public ServiceRequest(Object resDto, UserDto userDto) {
		this.setResDto(resDto);
		this.setUserDto(userDto);
	}

	/**
	 * 获取一个参数对象的方法
	 * 
	 * @return
	 */
	public Object get(String name) {
		return this.requestMap.get(name);
	}

	/**
	 * 设置(添加/修改)一个参数对象的方法
	 * 
	 * @param resDto
	 */
	public void put(String name, Object resDto) {
		this.requestMap.put(name, resDto);
	}

	/**
	 * 默认的获取另一个参数对象的方法
	 * 
	 * @return
	 */
	public Object getResDto() {
		return this.requestMap.get("resDto");
	}

	/**
	 * 默认的设置另一个参数对象的方法
	 * 
	 * @param resDto
	 */
	public void setResDto(Object resDto) {
		this.requestMap.put("resDto", resDto);
	}

	/**
	 * 登录用户/操作用户,等与用户相关的条件
	 * 
	 * @return
	 */
	public UserDto getUserDto() {
		try {
			return (UserDto) this.requestMap.get("userDto");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 登录用户/操作用户,等与用户相关的条件
	 * 
	 * @return
	 */
	public void setUserDto(UserDto userDto) {
		this.requestMap.put("userDto", userDto);
	}

	/**
	 * 通用的参数Map,各子类可以直接扩展Request属性字段,以方便理解和使用.一般建议至少两个对象, 一个是用户数据对象userDto,
	 * 另一个是操作数据对象resDto
	 */
	public void setRequestMap(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	/**
	 * 通用的参数Map,各子类可以直接扩展Request属性字段,以方便理解和使用.一般建议至少两个对象, 一个是用户数据对象userDto,
	 * 另一个是操作数据对象resDto
	 */
	public Map<String, Object> getRequestMap() {
		return requestMap;
	}
}
