/*
 * Copyright (c) , JianFa Ltd. and/or its affiliates, and/or Yes-soft Ltd. All rights reserved.
 * Use is subject to license terms.
 */
package org.esy.base.http;

import java.io.Serializable;

/**
 * <pre>
 * ClssName: HttpError 
 * Description: (返回报文错误信息) 
 * </pre>
 *
 * @author chenxin
 * @since 1.7
 * @version $ Id:HttpError.java 1.0 2016年1月29日上午10:31:38 $
 */
public class HttpError implements Serializable {

	private static final long serialVersionUID = -5212971094674607897L;

	/**
	 * Description: 错误码 默认0-没错误
	 */
	private String code = "0";

	/**
	 * Description: 错误信息
	 */
	private String message;

	/**
	 * Description: 报文错误信息构造函数
	 */
	public HttpError() {
		super();
	}

	/**
	 * Description: 报文错误信息构造函数
	 * @param code 错误码
	 */
	public HttpError(String code) {
		super();
		this.code = code;
	}

	/**Description: 报文错误信息构造函数
	 * @param code 错误码
	 * @param message 错误信息
	 */
	public HttpError(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
