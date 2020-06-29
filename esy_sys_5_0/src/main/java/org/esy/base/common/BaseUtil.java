package org.esy.base.common;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.esy.base.core.BASE64DecodedMultipartFile;
import org.esy.base.core.Base;
import org.esy.base.entity.Account;
import org.esy.base.entity.dto.MenuDto;
import org.esy.base.security.service.ShiroRealm.ShiroUser;
import org.esy.base.util.JsonUtil;
import org.esy.base.util.YESUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import sun.misc.BASE64Decoder;

public class BaseUtil {
	/**
	 * basic 方式验证
	 */
	public static final String AUTHORITY = "";

	public static Resource getResource(String path) {
		return ((ResourcePatternResolver) new PathMatchingResourcePatternResolver()).getResource(path);
	}

	public static Resource[] getResources(String path) throws IOException {
		return ((ResourcePatternResolver) new PathMatchingResourcePatternResolver()).getResources(path);
	}

	/**
	 * 字符串返回
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if (obj == null || obj.equals("null") || obj.toString().isEmpty())
			return "";
		else
			return obj.toString().trim();
	}

	/**
	 * 
	 * @Title: Hql like String
	 * @Description:
	 * @author duiqing.huang
	 * @param obj
	 * @return
	 */
	public static String toLikeString(Object obj) {
		return "%" + toString(obj) + "%";
	}

	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object pObj) {

		if (pObj == null)
			return true;
		if (pObj.equals(""))
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			return ((String) pObj).length() < 1;
		} else if (pObj instanceof Long) {
			return (Long) pObj == 0.0;
		} else if (pObj instanceof Double) {
			return (Double) pObj == 0.0;
		} else if (pObj instanceof Collection) {
			return ((Collection) pObj).size() < 1;
		} else if (pObj instanceof Map) {
			return ((Map) pObj).size() < 1;
		} else if (pObj.getClass().isArray()) {
			return Array.getLength(pObj) < 1;
		}
		return false;
	}

	public static boolean isNotEmpty(Object pObj) {
		return !isEmpty(pObj);
	}

	public static boolean isNumeric(String str) {
		if (str == null)
			return true;
		return str.matches("^[0-9]*$");
	}

	public static boolean isDouble(String str) {
		if (str == null)
			return true;
		return str.matches("^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)$");
	}

	public static boolean isDouble(Object obj) {
		if (obj == null)
			return true;
		if (obj instanceof Double) {
			return true;
		}
		return isDouble(obj.toString());
	}

	public static boolean isNumeric(Object obj) {
		if (obj == null)
			return true;
		return isNumeric(obj.toString());
	}

	public static Integer objtoint(Object obj) {
		return objtoint(obj, 0);
	}

	public static Integer objtoint(Object obj, Integer iter) {
		if (isNumeric(obj)) {
			return isEmpty(obj) ? iter : Integer.parseInt(obj.toString());
		} else {
			return iter;
		}
	}

	public static Long objtolong(Object obj) {
		return objtolong(obj, 0l);
	}

	public static Long objtolong(Object obj, Long iter) {
		if (isNumeric(obj)) {
			return isEmpty(obj) ? iter : Long.parseLong(obj.toString());
		} else {
			return iter;
		}
	}

	public static Double objToDouble(Object obj) {
		return objToDouble(obj, (double) 0l);
	}

	public static Double objToDoubleHasNull(Object obj) {
		if (isDouble(obj)) {
			return isEmpty(obj) ? null : Double.parseDouble(obj.toString());
		} else {
			return null;
		}
	}

	public static Double objToDouble(Object obj, Double num) {
		if (isDouble(obj)) {
			return isEmpty(obj) ? num : Double.parseDouble(obj.toString());
		} else {
			return num;
		}
	}

	public static Boolean objToBoolean(Object obj) {
		return objToBoolean(obj, false);
	}

	public static Boolean objToBoolean(Object obj, Boolean bool) {
		String value = toString(obj).trim();
		if (value.isEmpty()) {
			return isEmpty(bool) ? false : bool;
		}
		if ("是".equals(value)) {
			return true;
		}
		if ("否".equals(value)) {
			return false;
		}
		return Boolean.parseBoolean(value);
	}

	/**
	 * 判断字符串是否是整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (str == null)
			return true;
		return str.matches("^[-\\+]?\\d+$");
	}

	/**
	 * 判断字符串是否是浮点数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isFloat(String str) {
		if (str == null)
			return true;
		return str.matches("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$") || isInteger(str);
	}

	/**
	 * 字符串转为日期 注意格式是 yyyy-MM-dd
	 * 
	 * @param str
	 * @return Date
	 */
	public static Date strToDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串转为日期 注意格式是 yyyy-MM-dd
	 * 
	 * @param str
	 * @return Date
	 */
	public static Date strToDate(String str, String dateformat) {
		if (dateformat == null) {
			return strToDate(str);
		}

		SimpleDateFormat formatter = new SimpleDateFormat(dateformat);
		Date date = null;
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 日期转为字符串 注意格式是 yyyy-MM-dd
	 * 
	 * @param str
	 * @return str
	 */
	public static String dateToStr(Date date) {
		if (BaseUtil.isEmpty(date))
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	/**
	 * 日期转为字符串 注意格式是 yyyy-MM-dd
	 * 
	 * @param str
	 * @return str
	 */
	public static String dateToStr(Date date, String format) {
		if (BaseUtil.isEmpty(date))
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	/**
	 * 
	 * @Description obj转为json，不会抛出异常
	 * @param
	 * @return
	 */
	public static String objToJson(Object o) {
		String json = "";
		try {
			json = JsonUtil.beanToJson(o);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		return json;
	}

	// 四舍五入，并且保存n位小数
	public static Double getNumber(Double d, Integer n) {
		BigDecimal b = new BigDecimal(d);
		double f = b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f;
	}

	/**
	 * double 加法 v1+v2
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double doubleAdd(Double v1, Double v2) {
		if (v1 == null) {
			v1 = 0d;
		}
		if (v2 == null) {
			v2 = 0d;
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		Double f = b1.add(b2).doubleValue();
		return getNumber(f, 8);
	}

	/**
	 * double 减法 v1-v2
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double doubleSub(Double v1, Double v2) {
		if (v1 == null) {
			v1 = 0d;
		}
		if (v2 == null) {
			v2 = 0d;
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		Double f = b1.subtract(b2).doubleValue();
		return getNumber(f, 8);
	}

	/**
	 * double 乘法 v1*v2
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double doubleMul(Double v1, Double v2) {
		if (isEmpty(v1) || isEmpty(v2)) {
			return 0d;
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		Double f = b1.multiply(b2).doubleValue();
		return getNumber(f, 8);
	}

	/**
	 * double 除法 v1/v2 默认保留小数点后六位
	 * 
	 * @param v1
	 * @param v2
	 * @return if v2==0D return 0D
	 */
	public static Double doubleDivde(Double v1, Double v2) {
		return doubleDivde(v1, v2, 8);
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
	public static Double doubleDivde(Double v1, Double v2, int scale) {
		if (scale < 0) {
			scale = 0;
		}
		if (v2.compareTo(0D) == 0) {
			return 0D;
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
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
	public static Double doubleRound(Double v1, int scale) {
		if (scale < 0) {
			scale = 0;
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal("1");
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 根据出生日期获取人的年龄
	 * 
	 * @param strBirthDate(yyyy-mm-dd
	 *            or yyyy/mm/dd)
	 * @return
	 */
	public static String getAgeByBirthDate(Date dateBirthDate) {
		if (dateBirthDate == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strBirthDate = dateFormat.format(dateBirthDate);

		// 读取当前日期
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		// 计算年龄
		int age = year - Integer.parseInt(strBirthDate.substring(0, 4)) - 1;
		if (Integer.parseInt(strBirthDate.substring(5, 7)) < month) {
			age++;
		} else if (Integer.parseInt(strBirthDate.substring(5, 7)) == month
				&& Integer.parseInt(strBirthDate.substring(8, 10)) <= day) {
			age++;
		}
		return String.valueOf(age);
	}

	/**
	 * 根据出生日期获取人的年龄
	 * 
	 * @param strBirthDate(yyyy-mm-dd
	 *            or yyyy/mm/dd)
	 * @return
	 */
	public static String getAgeByBirthDate(String strBirthDate) {

		if ("".equals(strBirthDate) || strBirthDate == null) {
			return "";
		}
		// 读取当前日期
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DATE);
		// 计算年龄
		int age = year - Integer.parseInt(strBirthDate.substring(0, 4)) - 1;
		if (Integer.parseInt(strBirthDate.substring(5, 7)) < month) {
			age++;
		} else if (Integer.parseInt(strBirthDate.substring(5, 7)) == month
				&& Integer.parseInt(strBirthDate.substring(8, 10)) <= day) {
			age++;
		}
		return String.valueOf(age);
	}

	public static <T extends Base> T nulltoempty(T obj) {
		List<Field> fieldList = new ArrayList<>();
		Class tempClass = obj.getClass();
		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
		}

		for (Field field : fieldList) {
			String fieldtype = field.getType().getSimpleName();
			field.setAccessible(true);
			if ("Double".equals(fieldtype) || "long".equals(fieldtype)) {
				try {
					if (isEmpty(field.get(obj))) {
						field.set(obj, 0.0);
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return obj;

	}

	public static MultipartFile base64ToMultipart(String base64) {
		try {
			String[] baseStrs = base64.split(",");

			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b = new byte[0];
			b = decoder.decodeBuffer(baseStrs[1]);

			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			return new BASE64DecodedMultipartFile(b, baseStrs[0]);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static MultipartFile base64ToMultipart(String base64, String filename) {
		try {
			String[] baseStrs = base64.split(",");

			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b = new byte[0];
			b = decoder.decodeBuffer(baseStrs[1]);

			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			return new BASE64DecodedMultipartFile(b, baseStrs[0], filename);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 中文首字母转为拼音
	 * 
	 * @param chinese
	 * @return
	 */
	public static String ToPinyin(String chinese) {
		String pinyinStr = "";
		char[] newChar = chinese.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < newChar.length; i++) {
			if (newChar[i] > 128) {
				try {
					pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat)[0].substring(0, 1);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinStr += newChar[i];
			}
		}
		return pinyinStr;
	}

	/**
	 * 获取当前登录用户对象(Account)
	 */
	public static Account getUser() {
		Object obj = YESUtil.getSession("user");
		return obj == null ? null : (Account) obj;
	}

	/**
	 * @Description 获取指定权限列表
	 * @param
	 * @return
	 */
	public static List<MenuDto> getMumList(String parent) {
		List<MenuDto> menuDtoList = getUser().getMenus();
		if (YESUtil.isEmpty(parent)) {
			return menuDtoList;
		}
		for (MenuDto menuDto : menuDtoList) {
			if (YESUtil.isEmpty(YESUtil.toString(menuDto.getParent()).trim()))
				menuDto.setParent("");
		}
		List<MenuDto> retList = new ArrayList<MenuDto>();
		for (MenuDto menuDto : menuDtoList) {
			if (menuDto.getParent().equals(parent)) {
				retList.add(menuDto);
				List<MenuDto> childList = getMumList(menuDto.getUid());
				for (MenuDto menuchild : childList) {
					retList.add(menuchild);
				}
			}
		}
		return retList;
	}

	/**
	 * 获取当前登录用户信息（UserId） huiqiang.yan 2015-09-01
	 * 
	 * @return
	 */
	public static String getUserId() {
		// return "admin123";
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.getPrincipal() == null) {
			return "";
		}
		ShiroUser user = (ShiroUser) currentUser.getPrincipal();
		return user.getUserLoginID();
	}

}
