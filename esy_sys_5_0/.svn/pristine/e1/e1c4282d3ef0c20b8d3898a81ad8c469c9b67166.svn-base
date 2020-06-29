package org.esy.base.util;

import java.math.BigDecimal;

import org.esy.base.common.BaseUtil;

/**
 * 
 *
 */

public class DoubleUtil {

	private Double initval = 0.0;

	private static class create {
		private static final DoubleUtil doubleUtil = new DoubleUtil();
	}

	/**
	 * Description:单例模式
	 * 
	 * @author cx 2018年8月1日上午2:05:51
	 * @return
	 */
	public static DoubleUtil init(Double val) {
		DoubleUtil ret = create.doubleUtil;
		ret.initval = BaseUtil.isEmpty(val) ? 0 : val;
		return ret;
	}

	public DoubleUtil(Double val) {
		super();
		val = BaseUtil.isEmpty(val) ? 0 : val;
		initval = val;
	}

	public DoubleUtil() {
		super();
	}

	/**
	 * double 加法 v1+v2
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public DoubleUtil add(Double val) {
		val = BaseUtil.isEmpty(val) ? 0 : val;

		BigDecimal b1 = new BigDecimal(val.toString());
		BigDecimal b2 = new BigDecimal(initval.toString());
		initval = b1.add(b2).doubleValue();
		return this;
	}

	/**
	 * double 减法 v1-v2
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public DoubleUtil sub(Double val) {
		val = BaseUtil.isEmpty(val) ? 0 : val;

		BigDecimal b1 = new BigDecimal(initval.toString());
		BigDecimal b2 = new BigDecimal(val.toString());
		initval = b1.subtract(b2).doubleValue();
		return this;
	}

	/**
	 * double 乘法 v1*v2
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public DoubleUtil mul(Double val) {
		val = BaseUtil.isEmpty(val) ? 0 : val;
		BigDecimal b1 = new BigDecimal(val.toString());
		BigDecimal b2 = new BigDecimal(initval.toString());
		initval = b1.multiply(b2).doubleValue();
		return this;
	}

	/**
	 * double 除法 v1/v2 默认保留小数点后六位
	 * 
	 * @param v1
	 * @param v2
	 * @return if v2==0D return 0D
	 */
	public DoubleUtil div(Double val) {
		val = BaseUtil.isEmpty(val) ? 0 : val;
		return div(val, 6);
	}

	/**
	 * double 除法 v1/v2
	 * 
	 * @param v1
	 * @param v2
	 * @param scale
	 *            除不尽时候精确的小数点位数
	 * @return
	 */
	public DoubleUtil div(Double val, int scale) {
		if (scale < 0) {
			scale = 0;
		}
		val = BaseUtil.isEmpty(val) ? 0 : val;
		if (val.compareTo(0D) == 0) {
			initval = 0D;
		} else {
			BigDecimal b1 = new BigDecimal(initval.toString());
			BigDecimal b2 = new BigDecimal(val.toString());
			initval = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return this;
	}

	/**
	 * double值绝对值
	 * 
	 * 
	 * @return
	 */
	public DoubleUtil getabs() {
		initval = Math.abs(initval);
		return this;
	}

	/**
	 * double 小数位精确
	 * 
	 * @param v1
	 *            需要四舍五入的double数字
	 * @param scale
	 *            保留小数点后几位
	 * @return
	 */
	public DoubleUtil round(int scale) {
		if (scale < 0) {
			scale = 0;
		}
		BigDecimal b1 = new BigDecimal(initval.toString());
		BigDecimal b2 = new BigDecimal("1");
		initval = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
		return this;

	}

	/**
	 * double 向下取整
	 * 
	 * @param v1
	 *            整要取整的的double数字
	 * @param scale
	 *            保留小数点后几位
	 * @return
	 */
	public DoubleUtil floor() {
		initval = Math.floor(initval);
		return this;
	}

	public Double getval() {
		Double ret = initval;
		initval = 0.0;
		return ret;
	}

	/**
	 * @return the initval
	 */
	public Double getInitval() {
		return initval;
	}

	/**
	 * @param initval
	 *            the initval to set
	 */
	public void setInitval(Double initval) {
		this.initval = initval;
	}

}
