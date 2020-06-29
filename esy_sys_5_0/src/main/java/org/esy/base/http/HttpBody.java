/*
 * Copyright (c) , JianFa Ltd. and/or its affiliates, and/or Yes-soft Ltd. All rights reserved.
 * Use is subject to license terms.
 */
package org.esy.base.http;

import java.io.Serializable;
import java.util.Collection;

import org.esy.base.dao.core.PageResult;
import org.esy.base.util.BeanMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

/**
 * <pre>
 * ClssName: HttpBody 
 * Description: 返回报文数据体 
 * </pre>
 *
 * @author chenxin
 * @since 1.7
 * @version $ Id:HttpBody.java 1.0 2016年1月29日上午10:37:11 $
 */
public class HttpBody implements Serializable {

	private static final long serialVersionUID = 4533920081499779691L;

	/**
	 * Description: 返回错识信息
	 */
	private HttpError error;

	/**
	 * Description: 报文数据体
	 */
	private Object body;

	public HttpBody() {
		error = new HttpError();
	}

	/**
	 * Description: 报文数据体构造函数 
	 * @param body 数据体
	 */
	public HttpBody(Object body) {
		this.body = body;
	}

	/**
	 * Description:报文数据体构造函数 
	 * @param body 数据体
	 * @param cls 转换目标类型
	 */
	public HttpBody(Object body, Class<?> cls) {
		convertBody(body, cls);
	}

	/**
	 * Description:报文数据体构造函数 
	 * @param code 异常编码
	 * @param message 异常信息
	 */
	public HttpBody(String code, String message) {
		this.error = new HttpError(code, message);
	}

	public void setError(HttpError error) {
		this.error = error;
	}

	public HttpError getError() {
		return error;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	/**
	 * Description: 报文数据转换
	 * @param body 报文数据
	 * @param cls 需要转换类型
	 */
	private <T> void convertBody(Object body, final Class<T> cls) {
		if (body == null) { //为空不做处理
			return;
		}
		if (body instanceof Page) { //如果是Page分页类型，那么需要实现Converter
			PageResult<?> pageResult = (PageResult<?>) body;
			this.body = pageResult.map(new Converter<Object, T>() {
				@Override
				public T convert(Object source) {
					return BeanMapper.map(source, cls);
				}
			});
		} else if (body instanceof Collection) {//集合类型
			this.body = BeanMapper.mapList((Collection<?>) body, cls);
		} else {
			this.body = BeanMapper.map(body, cls);
		}

	}

}
