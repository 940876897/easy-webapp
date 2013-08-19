package net.easyUI.common.dto;

/**
 * 用户统一接口.TODO 等完善
 * 
 * @author busfly
 * 
 */
public interface UserDto {

	public Long getId();

	public void setId(Long id);

	public String getLoginName();

	public void setLoginName(String loginName);

	public String getDisplayName();

	public void setDisplayName(String displayName);
}
