package org.esy.base.core;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.esy.base.annotation.FieldInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
public class BaseAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldInfo("排外条件")
	@Transient
	@JsonProperty("notinfield")
	private String notinfield;

	public BaseAccount() {
		super();
	}

}
