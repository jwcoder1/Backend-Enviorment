package org.esy.base.common;

import java.text.MessageFormat;

import org.esy.base.common.BaseUtil;
import org.esy.base.exception.ValidRuntimeException;

/**
 * 
 * 错误信息收集类
 *
 * @author cx
 * @date 2016年5月10日 下午4:59:23 
 * @version v1.0
 */
public class ErrorMsg {

	private final static String FS_NL = "\n";

	private static ThreadLocal<StringBuilder> S_ERROR = new ThreadLocal<StringBuilder>();

	/**
	 * 
	 * 添加错误信息
	 * @author cx 2016年5月10日 下午5:06:24
	 * @param msg
	 */
	public static void add(String msg) {
		getSb().append(msg + FS_NL);
	}

	/**
	 * 
	 * 验证是否存在错误信息，如有错误信息就抛出异常
	 * @author 冰刀 2016年5月10日 下午5:24:30
	 */
	public static void vaildErrorMsg() {
		String message = getMsg();
		throwException(message);
	}

	/**
	 * 
	 * 抛出异常
	 * @author cx 2016年5月10日 下午5:36:45
	 * @param msg
	 */
	public static void throwException(String msg, Object... objects) {
		if (BaseUtil.isNotEmpty(msg)) {
			if (objects.length > 0) {
				msg = MessageFormat.format(msg, objects);
			}
			throw new ValidRuntimeException(msg);
		}
	}

	/**
	 * 
	 * 抛出异常
	 * @author cx 2016年5月10日 下午5:36:45
	 * @param msg
	 */
	public static void throwException(String msg, Exception ex) {
		if (BaseUtil.isNotEmpty(msg)) {
			throw new ValidRuntimeException(msg, ex);
		}
	}

	/**
	 * 
	 * 取回msg
	 * @author cx 2016年5月10日 下午5:09:59
	 * @return
	 */
	public static String getMsg() {
		String msg = getSb().toString();
		S_ERROR.set(null);
		return msg;
	}

	private static StringBuilder getSb() {
		if (S_ERROR.get() == null) {
			S_ERROR.set(new StringBuilder());
		}
		return S_ERROR.get();
	}
}
