package net.easyUI.Utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.regex.Pattern;

import net.easyUI.dto.common.ConstantDTO;
import net.easyUI.common.util.Money;

/**
 * 一个公共的util工具类，系统中公用的工具方法可以放到此类中
 * 
 * @author xiejh
 * 
 */
public class CommonUtil {

	/**
	 * 判断一个字符串是否为整数
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isNumeric(String param) {
		if (param == null || param == "") {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(param).matches();
	}

	/**
	 * 转化金额格式，以元为单位0.00元
	 * 
	 * @param money
	 * @return
	 */
	public static String getStringMoney(Long money) {
		if (money == null) {
			return "0.00";
		}
		Money costPriceM = new Money();
		costPriceM.setCent(money);
		return costPriceM.toString();
	}

	/**
	 * 将String型的金钱转化成以分为单位的Long型的金钱（比如以元为单位的32.12转为3212, 以元为单位的3233转为323333）
	 * 
	 * @author xiejh (added time:2012-4-27 上午11:05:20)
	 * @email xiejh@hundsun.com
	 * @param money
	 *            String型的金钱
	 * @return
	 */
	public static Long getLongMoney(String money) {
		if (money == null) {
			return 0L;
		}
		Money costPriceM = new Money(money);
		return costPriceM.getCent();
	}

	/**
	 * 将金钱从分转换成元
	 * 
	 * @return 如果传入的价钱为Null，返回0
	 */
	public static Double fen2Yuan(Long fen) {
		if (fen == null)
			return 0D;
		Money money = new Money();
		money.setCent(fen);

		return money.getAmount().doubleValue();
	}

	public static Double fen2Yuan(Object fen) {
		if (fen == null)
			return 0D;
		try {
			Long fenLong = Long.valueOf(fen + "");
			Money money = new Money();
			money.setCent(fenLong);
			return money.getAmount().doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0D;
		}
	}

	/**
	 * 将金钱从元转换成分
	 * 
	 * @return 如果传入的价钱为Null，返回0
	 */
	public static Long yuan2Fen(Double yuan) {
		if (yuan == null)
			return 0L;
		else
			return new Money(yuan).getCent();
	}

	/**
	 * 金钱折扣计算 异常情况返回null
	 * 
	 * @param amount要折扣的总额
	 *            ，单位：分
	 * @param discountRate折扣率
	 *            ，单位：0.01 如95折传入参数为95
	 * @return
	 */
	public static Long moneyDiscount(Long amount, Long discountRate) {
		if (null == amount || null == discountRate) {
			return null;
		}
		try {
			Money money = new Money();
			money.setCent(amount);
			money.multiplyBy(discountRate);
			money.divideBy(100L);
			return money.getCent();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 计算比例，返回置为百分之* 的*部分 如，95%返回95
	 * 
	 * @param dividend
	 *            分子
	 * @param divisor
	 *            分母
	 * @return
	 */
	public static Long calculateRate(Long dividend, Long divisor) {
		if (null == dividend || null == divisor || divisor.longValue() == 0) {
			return 100L;
		}
		try {
			BigDecimal dividendDeci = new BigDecimal(dividend.longValue());
			BigDecimal divisorDeci = new BigDecimal(divisor.longValue());
			return Long.valueOf(dividendDeci
					.divide(divisorDeci, 2, BigDecimal.ROUND_HALF_UP)
					.multiply(new BigDecimal(100)).longValue());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 百分比转数据库比率值
	 * 
	 * <pre>
	 *     比率字段与金额相似处理，数据库中保存的是乘以10000的数值，也就是说，
	 *     如果数据库里保存的是10000，实际上就是1，对应的百分比就是100% 。
	 *     比率字段扩展出两组方法，分别对应实际小数（Real）和实际百分比（Percent）。
	 *     例如：Promotion.java中discountRate字段：
	 *     在页面上使用时，需要百分比的就用百分比（如：discountRatePercent）
	 *     需要实际值时，就用实际值：（如：discountRateReal）
	 * </pre>
	 */
	public static Integer percent2DbRate(Float valuePercent) {
		if (valuePercent == null)
			return 0;
		return new Float(valuePercent * ConstantDTO.dbRatePercent).intValue();
	}

	/**
	 * 数据库比率值转 百分比
	 * 
	 * <pre>
	 *     比率字段与金额相似处理，数据库中保存的是乘以10000的数值，也就是说，
	 *     如果数据库里保存的是10000，实际上就是1，对应的百分比就是100% 。
	 *     比率字段扩展出两组方法，分别对应实际小数（Real）和实际百分比（Percent）。
	 *     例如：Promotion.java中discountRate字段：
	 *     在页面上使用时，需要百分比的就用百分比（如：discountRatePercent）
	 *     需要实际值时，就用实际值：（如：discountRateReal）
	 * </pre>
	 */
	public static Float dbRate2Percent(Integer valueDbRate) {
		if (valueDbRate == null)
			return 0F;
		return new Float(valueDbRate) / new Float(ConstantDTO.dbRatePercent);
	}

	/**
	 * 百分比转数据库比率值
	 * 
	 * <pre>
	 *     比率字段与金额相似处理，数据库中保存的是乘以10000的数值，也就是说，
	 *     如果数据库里保存的是10000，实际上就是1，对应的百分比就是100% 。
	 *     比率字段扩展出两组方法，分别对应实际小数（Real）和实际百分比（Percent）。
	 *     例如：Promotion.java中discountRate字段：
	 *     在页面上使用时，需要百分比的就用百分比（如：discountRatePercent）
	 *     需要实际值时，就用实际值：（如：discountRateReal）
	 * </pre>
	 */
	public static Integer real2DbRate(Float valueReal) {
		if (valueReal == null)
			return 0;
		return new Float(valueReal * ConstantDTO.dbRateReal).intValue();
	}

	/**
	 * 数据库比率值转 实际值
	 * 
	 * <pre>
	 *     比率字段与金额相似处理，数据库中保存的是乘以10000的数值，也就是说，
	 *     如果数据库里保存的是10000，实际上就是1，对应的百分比就是100% 。
	 *     比率字段扩展出两组方法，分别对应实际小数（Real）和实际百分比（Percent）。
	 *     例如：Promotion.java中discountRate字段：
	 *     在页面上使用时，需要百分比的就用百分比（如：discountRatePercent）
	 *     需要实际值时，就用实际值：（如：discountRateReal）
	 * </pre>
	 */
	public static Float dbRate2Real(Integer valueDbRate) {
		if (valueDbRate == null)
			return 0F;
		return new Float(valueDbRate) / new Float(ConstantDTO.dbRateReal);
	}
	
	/**
	 * 格式化小数位数，输入输出参数均以字符串格式，固定小数位数（默认为两位）。
	 * 初次引入是用于收银交班表中各项目的格式化。
	 * 
	 * @author zHao
	 */
	public static String formatNumber(String originalValue) {
		return formatNumber(originalValue, 2);
	}
	public static String formatNumber(String originalValue, int n) {
		NumberFormat f = NumberFormat.getInstance();
		f.setMaximumFractionDigits(n);
		f.setMinimumFractionDigits(n);
		
		String formatValue = "";
		try {
			double d = Double.parseDouble(originalValue);
			formatValue = f.format(d);
		} catch (Exception e) {
			formatValue = originalValue;
		}
		return formatValue;
	}
}
