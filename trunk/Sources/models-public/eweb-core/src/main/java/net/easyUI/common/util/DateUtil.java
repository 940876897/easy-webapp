package net.easyUI.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time
 *         pattern. Minutes should be mm not MM (MM is month).
 */
public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);
	public static final String Hour_Minutes_PATTERN = "HH:mm";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * Checkstyle rule: utility classes should not have public constructor
	 */
	public DateUtil() {
	}

	/**
	 * Timestamp和String之间转换的函数："yyyy-MM-dd"
	 */
	public static String getTimestampToString(Timestamp obj) {
		return getTimestampToString(DATE_FORMAT, obj);
	}

	/**
	 * 自定义 转换模式将Timestamp 输出
	 */
	public static String getTimestampToString(String formatPattern,
			Timestamp obj) {
		SimpleDateFormat df = new SimpleDateFormat(formatPattern);
		String str = df.format(obj);
		return str;
	}

	/**
	 * String转化为Timestamp:
	 */
	public static Timestamp strToTimestamp(String str) {
		Timestamp ts = Timestamp.valueOf(str);
		return ts;
	}

	public static Date strToDate(String strDate, String pattern) {
		Date dateTemp = null;
		SimpleDateFormat formater2 = new SimpleDateFormat(strDate);
		try {
			dateTemp = formater2.parse(pattern);
		} catch (Exception e) {
			log.error("exception in convert string to date!");
		}
		return dateTemp;
	}

	/**
	 * This method returns the current date time in the format: yyyy-MM-dd HH:mm:ss
	 * 
	 * @return the current date/time in the format: yyyy-MM-dd HH:mm:ss
	 */
	public static String getTimeNow() {
		return dateTimeToStr(DATE_TIME_FORMAT, new Date());
	}

	/**
	 * This method returns the current date in the format: yyyy-MM-dd
	 * 
	 * @return the current date
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(strToDate(todayAsString));

		return cal;
	}

	public static Calendar getCurrentDay() throws ParseException {
		Calendar cal = Calendar.getInstance();
		return cal;

	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static String dateTimeToStr(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the
	 * System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date "yyyy-MM-dd"
	 */
	public static String dateToStr(Date aDate) {
		return dateTimeToStr(DATE_FORMAT, aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format yyyy-MM-dd)
	 * @return a date object
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Date strToDate(String strDate) throws ParseException {
		return strToDate(DATE_FORMAT, strDate);
	}

	/**
	 * 取得从startDate开始的前(正)/后(负)day天
	 * 
	 * @param startDate
	 * @param day
	 * @return
	 */
	public static Date getRelativeDate(Date startDate, int day) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(startDate);
			calendar.add(Calendar.DAY_OF_MONTH, day);
			return calendar.getTime();
		} catch (Exception e) {
			log.error(e);
			return startDate;
		}
	}

	/**
	 * 根据日期获取星期几
	 * 
	 * @param date
	 *            java.util.Date对象,不能为null
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 统计两个时间差，返回的是天数(即24小时算一天，少于24小时就为0，用这个的时候最好把小时、分钟等去掉)
	 * 
	 * @param begin
	 *            开始时间
	 * @param end
	 * @return
	 */
	public static int countDays(String beginStr, String endStr, String Foramt) {
		Date end = strToDate(endStr, Foramt);
		Date begin = strToDate(beginStr, Foramt);
		long times = end.getTime() - begin.getTime();
		return (int) (times / 60 / 60 / 1000 / 24);
	}
}
