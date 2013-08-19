
package net.easyUI.manager.common;

import java.io.InputStream;

public interface UploadManager {
	/**
	 * 文件上传
	 * @param input 文件流
	 * @param fileExt 文件后缀名
	 * @return 返回保存的文件URL(根据配置文件upload.root.licence.url.path基础URL)
	 */
    public String uploadFile(InputStream input, String fileExt);
    /**
     * 文件上传
     * @param fileBytes 文件流字节数组
     * @param fileExt 文件后缀名
     * @return 返回保存的文件URL(根据配置文件upload.root.licence.url.path基础URL)
     */
    public String uploadFile(byte[] fileBytes, String fileExt);
}
