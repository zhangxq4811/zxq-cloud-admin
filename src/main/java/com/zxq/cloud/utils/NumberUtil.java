package com.zxq.cloud.utils;

import java.math.BigDecimal;

/**
 * @author zxq
 */
public class NumberUtil {

	/**
	 * 两数相除
	 * @param d1 除数
	 * @param d2 被除数
	 * @param scale 保留位数
	 * @return
	 */
	public static BigDecimal divide(String d1,String d2,int scale) {
		BigDecimal bigDecimal1 = new BigDecimal(d1);
		BigDecimal bigDecimal2 = new BigDecimal(d2);
		BigDecimal divide = bigDecimal1.divide(bigDecimal2);
		return divide.setScale(scale,BigDecimal.ROUND_HALF_DOWN);
	}
	
	/**
	 * 默认保留2位
	 * @param d1 除数
	 * @param d2 被除数
	 * @return
	 */
	public static BigDecimal divide(String d1,String d2) {
		return divide(d1,d2,2);
	}
}
