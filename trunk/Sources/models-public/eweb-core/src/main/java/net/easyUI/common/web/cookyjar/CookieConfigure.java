package net.easyUI.common.web.cookyjar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

import net.easyUI.common.util.crypto.Crypto;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CookieConfigure {
	private String name;

	private String clientName;

	private String path;

	private static final Integer DefaultLifeTime = -1;

	private Integer lifeTime;

	// 如果为null,则为不加密
	private Crypto crypto;

	// 字符编码，缺省为utf8
	private String encoding = "UTF-8";

	// 在值前面增加多少随机数字，如果 <= 0 ,则表示不增加
	private Integer randomChar = 0;

	private String domain;

	// domain是否设置,如果domain == "localhost" 则等同于未设置
	private boolean isDomainSet = false;

	private Class<? extends SelfDependence> selfDependenceClass;

	/**
	 * servlet3.0新增加的 httpOnly,注意，此配置不能用缺省配置来设置，只能每个CookieConfigure分别设置
	 */
	private boolean httpOnly = false;

	// 将客户端的cookie值翻译过来
	public String getRealValue(String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		String back = value;
		if (this.crypto != null) {
			back = this.crypto.dectypt(back, Crypto.Encoding.Base32, encoding);
		}
		if (back == null) {
			return null;
		}
		if (this.randomChar > 0) {
			if (back.length() < this.randomChar) {
				return null;
			}
			back = back.substring(this.randomChar);
		}
		return back;

	}

	// 将真实值翻译成客户端cookie存储值
	public String getClientValue(String value) {
		if (StringUtils.isBlank(value)) {
			return "";
		}
		String back = value;
		if (back == null) {
			return "";
		}
		if (this.randomChar > 0) {
			back = RandomStringUtils.randomAlphanumeric(this.randomChar) + back;
		}
		if (this.crypto != null) {
			back = this.crypto.encrypt(back, Crypto.Encoding.Base32, encoding);
		}
		return back;
	}

	public Cookie getCookie(String value, String contextPath) {
		return this.getCookie(value, contextPath, this.lifeTime);
	}

	public Cookie getCookie(String value, String contextPath, Integer expiry) {
		Cookie c = new Cookie(getClientName(), getClientValue(value));
		if (this.isDomainSet) {
			c.setDomain(domain);
		}
		c.setPath(contextPath + getPath());
		c.setMaxAge(expiry != null ? expiry : DefaultLifeTime);
		// TODO 以下代码需要Servlet 3.0+才能支持
		// if(this.httpOnly){
		// c.setHttpOnly(true);
		// }
		return c;
	}

	// 得到删除一个cookie的cookie
	public Cookie getDeleteCookie(String contextPath) {
		return this.getCookie("", contextPath, 0);// 将过期时间设置为0即为删除一个cookie
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
		if (!this.path.startsWith("/")) {
			this.path = "/" + this.path;
		}
	}

	/**
	 * @return the clinetName
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * @param clinetName
	 *            the clinetName to set
	 */
	public void setClientName(String clinetName) {
		this.clientName = clinetName;
	}

	public Crypto getCrypto() {
		return crypto;
	}

	public void setCrypto(Crypto crypto) {
		this.crypto = crypto;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return the lifeTime
	 */
	public Integer getLifeTime() {
		return lifeTime;
	}

	/**
	 * @param lifeTime
	 *            the lifeTime to set
	 */
	public void setLifeTime(Integer lifeTime) {
		this.lifeTime = lifeTime;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public Integer getRandomChar() {
		return randomChar;
	}

	public void setRandomChar(Integer randomChar) {
		this.randomChar = randomChar;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
		domainSet();
	}

	private static final Pattern Ipv4Pattern = Pattern
			.compile("^\\.?((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d|\\d)$");

	private void domainSet() {
		if (domain.equals("localhost") || domain.equals(".localhost")) {
			// localhost
			this.isDomainSet = false;
			return;
		}
		// 一般配置ip都用的ipv4,以后等ipv6大规模应用了再配上ipv6地址吧
		Matcher m = Ipv4Pattern.matcher(domain);
		if (m.find()) {
			this.isDomainSet = false;
			return;
		}
		this.isDomainSet = true;
	}

	public Class<? extends SelfDependence> getSelfDependenceClass() {
		return selfDependenceClass;
	}

	public void setSelfDependenceClass(
			Class<? extends SelfDependence> selfDependenceClass) {
		this.selfDependenceClass = selfDependenceClass;
	}

	public boolean isHttpOnly() {
		return httpOnly;
	}

	public void setHttpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
	}

}
