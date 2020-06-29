package org.esy.base.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.esy.base.annotation.FieldInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
public class BaseView implements Serializable {

	private static final long serialVersionUID = 1L;

	@FieldInfo("条件关系")
	@Transient
	@JsonProperty("conditionRelatiion")
	private String conditionRelatiion;

	@FieldInfo("全文搜索")
	@Transient
	@JsonProperty("lovsearchstr")
	private String lovsearchstr;

	@FieldInfo("视图状态")
	@Transient
	@JsonProperty("formstatus")
	private String formstatus;//"add,edit,view"

	@FieldInfo("排外条件")
	@Transient
	@JsonProperty("notinfield")
	private String notinfield;

	@Transient
	@JsonProperty("isdel")
	private Boolean isdel = false;

	public BaseView() {
		super();
	}

	public String getConditionRelatiion() {
		return conditionRelatiion;
	}

	public void setConditionRelatiion(String conditionRelatiion) {
		this.conditionRelatiion = conditionRelatiion;
	}

	public String getLovsearchstr() {
		return lovsearchstr;
	}

	public void setLovsearchstr(String lovsearchstr) {
		this.lovsearchstr = lovsearchstr;
	}

	/**
	 * @return the formstatus
	 */
	public final String getFormstatus() {
		return formstatus;
	}

	/**
	 * @param formstatus the formstatus to set
	 */
	public final void setFormstatus(String formstatus) {
		this.formstatus = formstatus;
	}

	/**
	 * @return the notinfield
	 */
	public final String getNotinfield() {
		return notinfield;
	}

	/**
	 * @param notinfield the notinfield to set
	 */
	public final void setNotinfield(String notinfield) {
		this.notinfield = notinfield;
	}

	/**
	 * @return the isdel
	 */
	public Boolean getIsdel() {
		return isdel;
	}

	/**
	 * @param isdel the isdel to set
	 */
	public void setIsdel(Boolean isdel) {
		this.isdel = isdel;
	}

}
