/*
 * Powered By [busfly]
 * Web Site: http://www.busfly.net
 * Google Code: http://code.google.com/p/willing-ox/
 * Since 2010 - 2011
 */

package net.easyUI.service.common;

import java.util.List;

import net.easyUI.dto.common.UserAgent;
import net.easyUI.common.dto.ServiceRequest;
import net.easyUI.common.dto.ServiceResult;

public interface CommonService {

	public ServiceResult<UserAgent> checkLogin(ServiceRequest serseq);

	/**
	 * 上传文件
	 * 
	 * @param serseq
	 *            setResDto(MultipartFile)
	 * @return 
	 *         ServiceResult.getDataObj()为上传文件的URL(根据配置upload.root.licence.url.path
	 *         ,一般为相对根目录)
	 */
	public ServiceResult<String> uploadFile(ServiceRequest serseq);

	/**
	 * 上传多个文件
	 * 
	 * @param serseq
	 *            setResDto(Collection<MultipartFile>)
	 * @return 
	 *         ServiceResult.getDataObj()为上传文件的URL的List(根据配置upload.root.licence.url.path
	 *         ,一般为相对根目录),如果某一个文件没成功,则其在List中对应位置值为空白.
	 */
	public ServiceResult<List<String>> uploadFileList(ServiceRequest serseq);

}
