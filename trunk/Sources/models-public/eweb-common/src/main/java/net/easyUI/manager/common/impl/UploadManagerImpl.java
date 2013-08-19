package net.easyUI.manager.common.impl;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import net.easyUI.manager.common.UploadManager;
import net.easyUI.common.service.BasePOJOService;
import net.easyUI.common.util.DateUtil;
import net.easyUI.common.util.ShortUUIDGenerator;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.texen.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("uploadManager")
public class UploadManagerImpl extends BasePOJOService implements UploadManager {
	@Value("${upload.root}")
	private String uploadRoot;
	@Value("${upload.root.licence.url.path}")
	private String uploadUrl;
	@Value("${upload.fileDefaultExt}")
	private String fileDefaultExt;

	public String uploadFile(InputStream input, String fileExt) {
		StringBuffer imgPath = new StringBuffer();
		StringBuffer contextPath = new StringBuffer();
		fileName(fileExt, imgPath, contextPath);
		if (writeFile(input, imgPath.toString())) {
			return contextPath.toString();
		} else {
			return null;
		}
	}

	public String uploadFile(byte[] fileBytes, String fileExt) {
		StringBuffer imgPath = new StringBuffer();
		StringBuffer contextPath = new StringBuffer();
		fileName(fileExt, imgPath, contextPath);
		if (writeFile(fileBytes, imgPath.toString())) {
			return contextPath.toString();
		} else {
			return null;
		}
	}

	/**
	 * 组装文件保存路径名以及访问URL名
	 * 
	 * @param fileExt
	 *            文件后缀名
	 * @param imgPath
	 *            文件保存路径名
	 * @param contextPath
	 *            文件访问URL名
	 */
	private void fileName(String fileExt, StringBuffer imgPath,
			StringBuffer contextPath) {
		if (StringUtils.isBlank(fileExt))
			fileExt = fileDefaultExt;
		String pathPart = DateUtil.dateTimeToStr("/yyyy/MM/dd/", new Date());
		String fileName = ShortUUIDGenerator.randomUUID() + "." + fileExt;
		imgPath.append(uploadRoot);
		contextPath.append(uploadUrl);
		imgPath.append(pathPart);
		contextPath.append(pathPart);
		FileUtil.mkdir(imgPath.toString());
		imgPath.append(fileName);
		contextPath.append(fileName);
	}

	/**
	 * 文件写入
	 * 
	 * @param input
	 * @param filePath
	 * @return
	 */
	private boolean writeFile(InputStream input, String filePath) {
		try {
			if (null == input) {
				logger.error("Parameter (InputStream in) is null!");
				return false;
			}
			FileOutputStream output = new FileOutputStream(filePath);
			byte[] buffer = new byte[1024];
			while ((input.read(buffer)) > 0) {
				// data.write(buffer, 0, num);
				output.write(buffer);
			}
			output.close();
			return true;
		} catch (Exception e) {
			logger.error("upload file error", e);
			return false;
		}
	}

	private boolean writeFile(byte[] fileBytes, String filePath) {
		try {
			if (null == fileBytes || fileBytes.length <= 0) {
				logger.error("Parameter (fileBytes in) is null!");
				return false;
			}
			OutputStream out = new BufferedOutputStream(new FileOutputStream(
					filePath, true));
			out.write(fileBytes);
			out.close();
			return true;
		} catch (Exception e) {
			logger.error("upload file error", e);
			return false;
		}
	}

}
