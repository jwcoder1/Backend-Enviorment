package org.esy.base.entity.jsonObject;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("body")  
public class UidResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//@JsonInclude(Include.NON_NULL)
	@JsonProperty("uid")
	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public UidResult(String uid) {
		super();
		this.uid = uid;
	}

	public UidResult() {
	}
	
}
