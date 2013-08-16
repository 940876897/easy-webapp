package net.easyUI.common.util.crypto.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
/**
 * 将字符串s转成byte[]数组，并拼接到byte[] a数组后面，返回拼接后的byte[]数组
 */
	public static byte[] mergArray(byte[] a, String s) {
		byte[] b = null;
		try {
			b = s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return mergArray(a, b);
	}

	/**
	 * 将byte[] b数组，拼接到byte[] a数组后面，返回拼接后的byte[]数组
	 */
	public static byte[] mergArray(byte[] a, byte[] b) {
		byte[] c = null;
		c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	/**
	 * 使用MD5加密字符串，并返回32位密文字符串
	 */
	public static String md5Str(String str) {
		byte[] byteArray = null;
		try {
			byteArray = md5Bytes(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	/**
	 * 使用MD5加密byte[] 数组，并返回32位密文字符串
	 */
	public static String md5Str(byte[] bytes) {
		byte[] byteArray = md5Bytes(bytes);
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	/**
	 * 使用MD5加密byte[] 数组，并返回16位二进制密文byte[]数组
	 */
	public static byte[] md5Bytes(byte[] bytes) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(bytes);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		}

		return messageDigest.digest();
	}

	/**
	 * 使用MD5加密字符串，并返回16位二进制密文byte[]数组
	 */
	public static byte[] md5Bytes(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return messageDigest.digest();
	}
}
