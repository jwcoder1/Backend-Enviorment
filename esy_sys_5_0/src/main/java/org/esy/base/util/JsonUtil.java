package org.esy.base.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @Title: JsonUtil.java
 * @Description:
 * @author: duiqing.huang
 * @date: 2014-7-21 下午8:49:12
 * @version: V1.0
 * 
 */
public class JsonUtil {

	public static String beanToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

	public static <T> T jsonToBean(String json, Class<T> cls)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		T vo = mapper.readValue(json, cls);
		return vo;
	}

	/**
	 * @Description map转为json-wwc20160120
	 * @param map
	 * @return
	 */
	public static String mapToJson(Map<String, Object> parm) {
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(parm);
		} catch (Exception e) {
			json = "";
			e.printStackTrace();
		}
		return json;
	}

}
