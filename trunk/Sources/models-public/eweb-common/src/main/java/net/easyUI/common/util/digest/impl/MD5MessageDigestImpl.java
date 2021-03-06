package net.easyUI.common.util.digest.impl;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5信息摘要实现类
 * 
 */
public final class MD5MessageDigestImpl extends AbstractMessageDigestImpl {

	/**
	 * MD5信息摘要算法实现, 采用Apache Commons的codec包的MD5实现
	 * 
	 * @param bytes
	 * @return byte[]
	 * @see com.hundsun.wudadao.common.util.digest.impl.AbstractMessageDigestImpl#digestInternal(byte[])
	 */
	@Override
	protected byte[] digestInternal(byte[] bytes) {
		if (bytes == null) {
			throw new IllegalArgumentException("paramter bytes can't be null");
		}
		return DigestUtils.md5(bytes);
	}

}
