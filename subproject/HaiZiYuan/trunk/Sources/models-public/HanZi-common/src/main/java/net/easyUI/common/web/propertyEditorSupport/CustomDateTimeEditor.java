package net.easyUI.common.web.propertyEditorSupport;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

/**
 * 自定义的日期时间格式化器yyyy-MM-dd,yyyy-MM-dd HH:mm:ss,yyyy-MM-dd HH:mm:ss.S
 * 
 * @author busfly
 * 
 */
public class CustomDateTimeEditor extends PropertyEditorSupport {
	private DateFormat dateFormat_short;
	private DateFormat dateFormat_long;
	/**
	 * 短类型日期长度
	 */
	private int shortDateLength = 10;// 如果Text的长度小于等于这个值时,使用dateFormat_short,大于这个值时,使用dateFormat_long
	private boolean allowEmpty;// 如果为false的话,当值为空时,抛出异常,为true时,设置为null

	/**
	 * 可以定义两个DateFormat,如果第一个DateFormat解析出错,就使用第二个,这样就可以兼容二种日期输入形式
	 * yyyy-MM-dd,yyyy-MM-dd HH:mm:ss,yyyy-MM-dd HH:mm:ss.S
	 * 
	 */
	public CustomDateTimeEditor() {
		// 注册默认的日期格式化类型: yyyy-MM-dd yyyy-MM-dd HH:mm:ss
		this.dateFormat_short = new SimpleDateFormat("yyyy-MM-dd");
		this.dateFormat_long = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.allowEmpty = true;
	}

	/**
	 * 可以定义两个DateFormat,如果第一个DateFormat解析出错,就使用第二个,这样就可以兼容二种日期输入形式
	 * yyyy-MM-dd,yyyy-MM-dd HH:mm:ss,yyyy-MM-dd HH:mm:ss.S
	 * 
	 * @param dateFormat_short
	 *            yyyy-MM-dd HH:mm:ss
	 * @param dateFormat_long
	 *            yyyy-MM-dd
	 */
	public CustomDateTimeEditor(DateFormat dateFormat_short,
			DateFormat dateFormat_long) {
		this.dateFormat_short = dateFormat_short;
		this.dateFormat_long = dateFormat_long;
		this.allowEmpty = true;
	}

	/**
	 * 可以定义两个DateFormat,如果第一个DateFormat解析出错,就使用第二个,这样就可以兼容二种日期输入形式
	 * yyyy-MM-dd,yyyy-MM-dd HH:mm:ss,yyyy-MM-dd HH:mm:ss.S
	 * 
	 * @param dateFormat_short
	 *            yyyy-MM-dd HH:mm:ss
	 * @param dateFormat_long
	 *            yyyy-MM-dd
	 * @param allowEmpty
	 *            如果为false的话,当值为空时,抛出异常,为true时,设置为null
	 */
	public CustomDateTimeEditor(DateFormat dateFormat_short,
			DateFormat dateFormat_long, boolean allowEmpty) {
		this.dateFormat_short = dateFormat_short;
		this.dateFormat_long = dateFormat_long;
		this.allowEmpty = allowEmpty;
	}

	/**
	 * 可以定义两个DateFormat,如果第一个DateFormat解析出错,就使用第二个,这样就可以兼容二种日期输入形式
	 * yyyy-MM-dd,yyyy-MM-dd HH:mm:ss,yyyy-MM-dd HH:mm:ss.S
	 * 
	 * @param dateFormat_short
	 *            yyyy-MM-dd HH:mm:ss
	 * @param dateFormat_long
	 *            yyyy-MM-dd
	 * @param dateFormat_short
	 * @param dateFormat_long
	 * @param allowEmpty
	 *            如果为false的话,当值为空时,抛出异常,为true时,设置为null
	 * @param shortDateLength
	 *            如果此值>=0时,当值的长度不等于它时,抛出异常
	 */
	public CustomDateTimeEditor(DateFormat dateFormat_short,
			DateFormat dateFormat_long, boolean allowEmpty, int shortDateLength) {
		this.dateFormat_short = dateFormat_short;
		this.dateFormat_long = dateFormat_long;
		this.allowEmpty = allowEmpty;
		this.shortDateLength = shortDateLength;
	}

	/**
	 * 设入值时格式化
	 */
	public void setAsText(String text) throws IllegalArgumentException {

		text = text.trim();
		if (allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
			return;
		}
		try {
			if (text.length() <= shortDateLength) {
				setValue(new java.sql.Date(dateFormat_short.parse(text)
						.getTime()));
			} else {
				setValue(new java.sql.Timestamp(dateFormat_long.parse(text)
						.getTime()));
			}
		} catch (ParseException ex) {
			IllegalArgumentException iae = new IllegalArgumentException(
					"Could not parse date: " + ex.getMessage());
			iae.initCause(ex);
			throw iae;
		}

	}

	/**
	 * 读出值时格式化
	 */
	public String getAsText() {
		Date value = (Date) getValue();
		return (value != null ? dateFormat_long.format(value) : "");
	}

}
