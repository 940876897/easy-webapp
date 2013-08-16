package net.easyUI.common.util.crypto.impl;

/**
 * PasswordHash 转JAVA版By巴士飞扬.
 * Portable PHP password hashing framework. JAVA
 * @since 2.5
 * @version 0.2 / genuine.
 * @link http://www.busfly.net
 * @author busfly 2010-7-21 11:12:16 For Java
 */
public class PasswordHash {

	public final static String itoa64 = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	int iteration_count_log2; //WordPress里用的,好像这里没用
	boolean portable_hashes;//WordPress里用的,好像这里没用
	String random_state;//WordPress里用的,好像这里没用

	public static void main(String[] args) {
		boolean e = PasswordHash.CheckPassword("d",
				"$P$B3zMNjHI15X9gblIzEMkiSwDqv19oF0");
		System.out.println("eq=" + e);
		boolean e2 = PasswordHash.CheckPassword("nana@busfly",
		"$P$BuHOF2tOEwnn3ZB3pM6X1KFVNhRCZS0");
		System.out.println("eq2=" + e2);
	}

	/**
	 * 初始化
	 * 
	 * @param iteration_count_log2 WordPress里使用的是8,好像这里没用
	 * @param portable_hashes WebSphere里使用的是true,好像这里没用
	 */
	public PasswordHash(int iteration_count_log2, boolean portable_hashes) {
		if (iteration_count_log2 < 4 || iteration_count_log2 > 31)
			iteration_count_log2 = 8;
		this.iteration_count_log2 = iteration_count_log2;
		this.portable_hashes = portable_hashes;
	}

	/**
	 * 验证明文密码与密文密码是否相同
	 * 
	 * @param password
	 *            明文密码
	 * @param stored_hash
	 *            密文密码
	 * @return
	 */
	public static boolean CheckPassword(String password, String stored_hash) {
		String hash = crypt_private(password, stored_hash);
		return hash.equals(stored_hash);
	}

	/**
	 * 对密码加密
	 * 
	 * @param password
	 *            明文密码
	 * @param setting
	 *            密钥，至少12位长，验证密码时，可以用原密文密码做密钥，或者原密文密码的前12位。
	 * @return
	 */
	public static String crypt_private(String password, String setting) {
		String output = "*0";
		if (setting.substring(0, 2).equals(output))
			output = "*1";

		if (!setting.substring(0, 3).equals("$P$"))
			return output;

		int count_log2 = itoa64.indexOf(setting.charAt(3));
		if (count_log2 < 7 || count_log2 > 30)
			return output;

		int count = 1 << count_log2;

		String salt = setting.substring(4, 4 + 8);
		if (salt.length() != 8)
			return output;

		byte[] hash = Md5.md5Bytes(salt + password);
		do {
			hash = Md5.md5Bytes(Md5.mergArray(hash, password));
		} while (--count > 0);

		output = setting.substring(0, 12);
		output += encode64Bytes(hash, 16);

		return output;
	}

	public static String encode64Bytes(byte[] input, int count) {
		String output = "";
		int i = 0;
		do {
			int value = Integer
					.valueOf(Integer.toString(0xFF & input[i++], 10));
			output += itoa64.charAt(value & 0x3f);
			if (i < count)
				value |= Integer.valueOf(Integer.toString(0xFF & input[i], 10)) << 8;
			output += itoa64.charAt((value >> 6) & 0x3f);
			if (i++ >= count)
				break;
			if (i < count)
				value |= Integer.valueOf(Integer.toString(0xFF & input[i], 10)) << 16;
			output += itoa64.charAt((value >> 12) & 0x3f);
			if (i++ >= count)
				break;
			output += itoa64.charAt((value >> 18) & 0x3f);
		} while (i < count);

		return output;
	}

}
