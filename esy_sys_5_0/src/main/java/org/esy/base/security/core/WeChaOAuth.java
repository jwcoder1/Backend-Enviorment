package org.esy.base.security.core;

import org.esy.base.util.YESUtil;

/**
 * 
 * 微信登入集成
 *
 * @author huangdq
 * @date 2016年4月15日 下午5:12:07 
 * @version v1.0
 */
public class WeChaOAuth {

	/**
	 * 
	 * 是否是单点请求
	 * @author cx 2016年4月15日 下午5:31:14
	 * @return
	 */
	public static boolean isWeChaRequest() {

		return YESUtil.isNotEmpty(YESUtil.getSession("weixinid"));
	}

}
