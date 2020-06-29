package org.esy.base.security.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.util.PropertiesUtils;
import org.esy.base.util.YESUtil;

/**
 * 
 * 中烟门户单点集成
 *
 * @author cx
 * @date 2016年4月15日 下午5:12:07 
 * @version v1.0
 */
public class SSOAuth {

	public static final String SF_SSO_TOKEN_KEY = "iv-user";

	private static String username = "";

	private static List<String> ips = new ArrayList<String>();

	/**
	 * 
	 * 获取单点Uid
	 * @author cx 2016年4月15日 下午5:12:05
	 * @param token
	 */
	public static String getSSOUid() {
		return isSSORequest() ? username : null;
	}

	/**
	 * 
	 * 是否是单点请求
	 * @author cx 2016年4月15日 下午5:31:14
	 * @return
	 */
	public static boolean isSSORequest() {
		HttpServletRequest request = YESUtil.request();
		//获取request header中的用户UID字段
		username = request.getHeader(SF_SSO_TOKEN_KEY);
		//获取request中的WebSEALip地址
		String websealip = request.getRemoteAddr();
		System.out.println("==========SSO=========");
		System.out.println(username);
		System.out.println(websealip);
		return YESUtil.isNotEmpty(username) && getIps().contains(websealip);
	}

	private static List<String> getIps() {
		if (ips.isEmpty()) {
			try {
				PropertiesUtils propertiesUtils = new PropertiesUtils();
				String websealip = propertiesUtils.getProperty("websealip");
				if (YESUtil.isNotEmpty(websealip)) {
					ips = YESUtil.StrtoList(websealip);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ips;
	}
}
