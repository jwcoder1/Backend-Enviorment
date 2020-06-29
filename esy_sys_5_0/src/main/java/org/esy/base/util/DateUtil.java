package org.esy.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {
	// 按照给定的格式化字符串格式化日期
	public static String formatDate(Date date, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}

	public static Date addhours(Date date, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hour);// 24小时制
		date = cal.getTime();
		return date;
	}

	// 按照给定的格式化字符串解析日期
	public static Date parseDate(String dateStr, String formatStr) throws ParseException {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		date = sdf.parse(dateStr);
		return date;
	}

	// 从字符串中分析日期
	public static Date parseDate(String dateStr) throws ParseException {
		Date date = null;
		String[] dateArray = dateStr.split("\\D+"); // +防止多个非数字字符在一起时导致解析错误
		int dateLen = dateArray.length;
		int dateStrLen = dateStr.length();
		if (dateLen > 0) {
			if (dateLen == 1 && dateStrLen > 4) {
				if (dateStrLen == "yyyyMMddHHmmss".length()) {
					// 如果字符串长度为14位并且不包含其他非数字字符，则按照（yyyyMMddHHmmss）格式解析
					date = parseDate(dateStr, "yyyyMMddHHmmss");
				} else if (dateStrLen == "yyyyMMddHHmm".length()) {
					date = parseDate(dateStr, "yyyyMMddHHmm");
				} else if (dateStrLen == "yyyyMMddHH".length()) {
					date = parseDate(dateStr, "yyyyMMddHH");
				} else if (dateStrLen == "yyyyMMdd".length()) {
					date = parseDate(dateStr, "yyyyMMdd");
				} else if (dateStrLen == "yyyyMM".length()) {
					date = parseDate(dateStr, "yyyyMM");
				}
			} else {
				String fDateStr = dateArray[0];
				for (int i = 1; i < dateLen; i++) {
					// 左补齐是防止十位数省略的情况
					fDateStr += leftPad(dateArray[i], "0", 2);
				}

				if (dateStr.trim().matches("^\\d{1,2}:\\d{1,2}(:\\d{1,2})?$")) {
					// 补充年月日3个字段
					dateLen += 3;
					fDateStr = formatDate(new Date(), "yyyyMMdd") + fDateStr;
				}

				date = parseDate(fDateStr, "yyyyMMddHHmmss".substring(0, (dateLen - 1) * 2 + 4));
			}
		}

		return date;
	}

	// 左补齐
	public static String leftPad(String str, String pad, int len) {
		String newStr = (str == null ? "" : str);
		while (newStr.length() < len) {
			newStr = pad + newStr;
		}
		if (newStr.length() > len) {
			newStr = newStr.substring(newStr.length() - len);
		}
		return newStr;
	}

	/**
	 * sanan项目专用 增加16个小时
	 * 
	 * @param date
	 */
	public static Date addDay(Date date) {
		if (YESUtil.isNotEmpty(date)) {
			return DateUtils.addHours(date, 16);
		} else {
			return date;
		}
	}

	/**
	 * 日期相加减
	 * 
	 * @param time
	 *            时间字符串 yyyy-MM-dd HH:mm:ss
	 * @param num
	 *            加的数，-num就是减去
	 * @return 减去相应的数量的年的日期
	 * @throws ParseException
	 */
	public static Date yearAddNum(Date time, Integer num) {
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");
		// Date date = format.parse(time);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.YEAR, num);
		Date newTime = calendar.getTime();
		return newTime;
	}

	/**
	 * 
	 * @param time
	 *            时间
	 * @param num
	 *            加的数，-num就是减去
	 * @return 减去相应的数量的月份的日期
	 * @throws ParseException
	 *             Date
	 */
	public static Date monthAddNum(Date time, Integer num) {
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");
		// Date date = format.parse(time);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.MONTH, num);
		Date newTime = calendar.getTime();
		return newTime;
	}

	/**
	 * 
	 * @param time
	 *            时间
	 * @param num
	 *            加的数，-num就是减去
	 * @return 减去相应的数量的天的日期
	 * @throws ParseException
	 *             Date
	 */
	public static Date dayAddNum(Date time, Integer num) {
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");
		// Date date = format.parse(time);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.DAY_OF_MONTH, num);
		Date newTime = calendar.getTime();
		return newTime;
	}

	/**
	 * 获取本月第一天时间
	 */
	public static Date getMonthStartDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取本月最后一天
	 * 
	 */
	public static Date getMonthEndDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 获取本周的开始时间
	 */
	public static Date getBeginWeekDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		// 周日是1 ，周一是 2 ，周二是 3
		// 所以，当周的第一天 = 当前日期 - 距离周一过了几天（周一过了0天，周二过了1天， 周日过了6天）
		// 2 - 周一的（dayofweek：2 ）= 0
		// 2 - 周二的（dayofweek：3 ）= -1
		// .
		// .
		// 2 - 周日的（dayofweek：1） = 1（这个是不符合的需要我们修改）===》2 - 周日的（dayofweek：1 ==》8 ）
		// = -6
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return cal.getTime();
	}

	/**
	 * 本周的结束时间 开始时间 + 6天
	 */
	public static Date getEndWeekDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 8 - dayofweek);// 2 - dayofweek + 6
		return cal.getTime();
	}

	public static void main(String[] args) throws ParseException {
		// String[] dateStrArray = new String[] { "2014-03-12 12:05:34",
		// "2014-03-12 12:05", "2014-03-12 12", "2014-03-12", "2014-03", "2014",
		// "20140312120534", "2014/03/12 12:05:34", "2014/3/12 12:5:34",
		// "2014年3月12日 12时5分34秒",
		// "201403121205", "2014031212", "20140312", "201403", "12:05:34",
		// "12:05", };
		// for (int i = 0; i < dateStrArray.length; i++) {
		// Date date = parseDate(dateStrArray[i]);
		// System.out.println(dateStrArray[i] + " ==> " + formatDate(date,
		// "yyyy-MM-dd HH:mm:ss"));
		// }
	}
}
