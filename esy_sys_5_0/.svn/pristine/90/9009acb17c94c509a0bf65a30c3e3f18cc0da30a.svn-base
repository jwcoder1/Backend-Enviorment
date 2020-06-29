package org.esy.base.core;

import java.io.Serializable;

/**
 * 
 * Base 3.6 消息传递体，用于页面 Json 数据数据交换
 * 
 * @author Victor 2014-10-28
 * 			v3.6 修改日期：20141217  duiqing.huang 添加序列化
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = -6187215620628364529L;

	private boolean success = true;

	private String id;

	private String message;

	private String type;

	private String field;

	private String url;

	private String action;

	private Object data;

	private Object other;

	public Message() {
	}

	public Message(String message) {
		this.message = message;
	}

	public Message(String message, boolean success) {
		this.message = message;
		this.success = success;
	}

	/**
	 * 
	 * @return 处理状态
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * 设置处理状态
	 * 
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * 
	 * @return 消息编号
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置消息编号
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return 消息信息
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置消息信息
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 
	 * @return 消息类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置消息类型
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return 定位字段
	 */
	public String getField() {
		return field;
	}

	/**
	 * 设定定位字段
	 * 
	 * @param field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * 
	 * @return URL 地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置 URL 地址
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @return 操作命令
	 */
	public String getAction() {
		return action;
	}

	/**
	 * 设置操作命令
	 * 
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * 
	 * @return 附加数据
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 设置附加数据
	 * 
	 * @param data
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @return 其他数据
	 */
	public Object getOther() {
		return other;
	}

	/**
	 * 设置其他数据
	 * @param other
	 */
	public void setOther(Object other) {
		this.other = other;
	}

}
