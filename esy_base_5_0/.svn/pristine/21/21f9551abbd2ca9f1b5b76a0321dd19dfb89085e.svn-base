package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.BaseView;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityInfo("用户账号")
@Table(name = "base_area", indexes = { @Index(name = "ae_pid", columnList = "ae_pid") })
@DynamicUpdate
public class Area extends BaseView {

	@Id
	@FieldInfo("编号")
	@FilterInfo(ListValue = "eq", LovValue = "match")
	@Column(name = "ae_id", length = 8)
	private String id;

	@FieldInfo("名称")
	@FilterInfo(ListValue = "match", LovValue = "match")
	@Column(name = "ae_name", length = 20)
	private String name;

	@FieldInfo("pid")
	@FilterInfo(ListValue = "eq")
	@Column(name = "ae_pid", length = 8)
	private String pid;

	@Transient
	@JsonProperty("isdel")
	private Boolean isdel = false;

	@Transient
	@JsonProperty("addnext")
	private String addnext = "N";

	public Area() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return the isdel
	 */
	public final Boolean getIsdel() {
		return isdel;
	}

	/**
	 * @param isdel the isdel to set
	 */
	public final void setIsdel(Boolean isdel) {
		this.isdel = isdel;
	}

	/**
	 * @return the addnext
	 */
	public final String getAddnext() {
		return addnext;
	}

	/**
	 * @param addnext the addnext to set
	 */
	public final void setAddnext(String addnext) {
		this.addnext = addnext;
	}

}
