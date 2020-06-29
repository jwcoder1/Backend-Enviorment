package org.esy.base.entity.pojo;

import java.io.Serializable;

public class MsgResult implements Serializable {
 
	private static final long serialVersionUID = 1L;

	private Boolean success=true;
	
	private String msg="";
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

	public MsgResult() {
	}

	public MsgResult(Boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}

}
