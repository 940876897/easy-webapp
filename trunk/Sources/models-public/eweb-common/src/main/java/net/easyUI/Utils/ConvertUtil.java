package net.easyUI.Utils;

import java.math.BigDecimal;

import net.easyUI.common.util.Money;


/**
 * vm页面与java端数据转换工具类
 * @author guowei
 * @version $Id ConvertUtil.java v1.0 2012-5-9 上午12:26:20 guowei Exp $
 */
public class ConvertUtil {
    
    /**
     * 分为单位的金钱转换为元为单位的字符串形式
     * @param cent
     * @return
     */
    public static String ConvertMoneyLongToString(Long cent){
        if (cent == null) {
            return "0.00";
        }
        Money costPriceM = new Money();
        costPriceM.setCent(cent);
        return costPriceM.toString();
    }
    
    /**
     * 折扣/转化率字符串转化为整形
     * 转化率*100
     * @param amountStr 若有小数点，小数点后不超过两位
     * @return
     */
    public static Integer convertRateStrToInteger(String rateStr) {
        Integer amount = null;
        try {
            if (rateStr.indexOf(".") == -1) {
                amount = Integer.valueOf(rateStr + "00");
            } else {
                int point = rateStr.lastIndexOf(".");
                String intPart = rateStr.substring(0, point);
                String deciPart = rateStr.substring(point + 1, rateStr
                        .length());
                String temp = deciPart + "00";
                deciPart = temp.substring(0, 2);
                String newAmountStr = intPart + deciPart;
                amount = Integer.valueOf(newAmountStr);
            }
        } catch (Exception e) {
            return null;
        }
        return amount;
    }
    /**
     * 折扣/转化率化为字符串显示
     * 转化率/100
     * @param amountStr
     * @return
     */
    public static String convertRateIntegerToStr(Integer rate) {
        String result ="";
        try {
            BigDecimal divisor = new BigDecimal(100);
            BigDecimal resultBigDecimal = new BigDecimal(rate.intValue())
                    .divide(divisor, 2, BigDecimal.ROUND_HALF_DOWN);
            result = resultBigDecimal.toString();
        } catch (Exception e) {
            return result;
        }
        return result;
    }
    
}
