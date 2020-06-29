package org.esy.base.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/***
 * URL Encode / Decode 处理
 * @author Victor
 * 2013.10.26
 *
 */
public class UrlUtil {

	public static String decode(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "Broken VM does not support UTF-8";
		}
	}

	public static String encode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "Broken VM does not support UTF-8";
		}
	}
}
