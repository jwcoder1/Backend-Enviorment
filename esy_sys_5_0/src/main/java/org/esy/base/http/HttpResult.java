/*
 * Copyright (c) , JianFa Ltd. and/or its affiliates, and/or Yes-soft Ltd. All rights reserved.
 * Use is subject to license terms.
 */
package org.esy.base.http;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 * <pre>
 * ClssName: HttpResult 
 * Description: 返回报文封装类 
 * </pre>
 *
 * @author chenxin
 * @since 1.7
 * @version $ Id:HttpResult.java 1.0 2016年1月29日上午10:41:04 $
 */
public class HttpResult extends ResponseEntity<HttpBody> implements Serializable {

	private static final long serialVersionUID = 1944922690089691664L;

	/**
	 * Description: 返回成功状态码
	 */
	private static final HttpStatus FS_STATUSCODE_OK = HttpStatus.OK;

	/**
	 * Description: 报文构造函数,不返回数据体,那么返回error状态为0-成功.
	 */
	public HttpResult() {
		super(new HttpBody(), FS_STATUSCODE_OK);
	}

	/**
	 * Description: 报文构造函数
	 * @param body 报文数据
	 */
	public HttpResult(Object body) {
		super(new HttpBody(body), FS_STATUSCODE_OK);
	}

	/**
	 * Description: 报文构造函数
	 * @param body 报文数据
	 * @param cls 转换目标类型(返回报文数据对象)
	 */
	public HttpResult(Object body, Class<?> cls) {
		super(new HttpBody(body, cls), FS_STATUSCODE_OK);
	}

	/**
	 * Description: 报文构造函数(异常码为0)
	 * @param message 异常信息
	 * @param httpStatus 浏览器状态(为空时默认返回200)
	 */
	public HttpResult(String message, HttpStatus httpStatus) {
		super(new HttpBody("0", message), httpStatus == null ? FS_STATUSCODE_OK : httpStatus);
	}

	/**
	 * Description: 报文构造函数
	 * @param code 异常编码
	 * @param message 异常信息
	 */
	public HttpResult(String code, String message) {
		super(new HttpBody(code, message), FS_STATUSCODE_OK);
	}

	/**
	 * Description: 报文构造函数
	 * @param code 异常编码
	 * @param message 异常信息
	 * @param httpStatus 浏览器状态
	 */
	public HttpResult(String code, String message, HttpStatus httpStatus) {
		super(new HttpBody(code, message), httpStatus);
	}

	/**Description: 简单描述下方法功能和调用注意事项
	 * @param httpBody 报文数据体对象
	 * @param headers 返回报文头
	 */
	public HttpResult(HttpBody httpBody, MultiValueMap<String, String> headers) {
		super(httpBody, headers, FS_STATUSCODE_OK);
	}

}
