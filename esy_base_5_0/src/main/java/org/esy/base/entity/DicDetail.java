package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@EntityInfo("数据字典子表")
@Table(name = "base_mdicdetail", indexes = { @Index(name = "mdd_id", columnList = "mdd_id"),
		@Index(name = "mdd_did", columnList = "mdd_did") })
@DynamicUpdate
public class DicDetail extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("模块名")
	@Column(name = "mdd_model", length = 32)
	@FilterInfo(ListValue = "eq")
	private String model;

	@FieldInfo("编号")
	@FilterInfo(ListValue = "eq", LovValue = "match")
	@Column(name = "mdd_id", length = 32, nullable = false)
	private String id;

	@FieldInfo("主类编号")
	@FilterInfo(ListValue = "eq")
	@Column(name = "mdd_did", length = 32)
	private String did;

	@FieldInfo("序号")
	@Column(name = "mdd_seq", length = 32)
	private Integer seq;

	@FieldInfo("值")
	@FilterInfo(ListValue = "match", LovValue = "match")
	@Column(name = "mdd_value", length = 32, nullable = false)
	private String value;

	@FieldInfo("描述")
	@FilterInfo(ListValue = "eq", LovValue = "match")
	@Column(name = "mdd_text", length = 200)
	private String text;

	@FieldInfo("企业路径")
	@FilterInfo(ListValue = "eq")
	@Column(name = "mdd_eid", length = 255)
	private String eid = "";

	/**
	 * 
	 */
	public DicDetail() {
		super();
	}

	/**
	 * @param model
	 * @param id
	 * @param seq
	 * @param value
	 * @param text
	 */
	public DicDetail(String model, String id, Integer seq, String value, String text) {
		super();
		this.model = model;
		this.id = id;
		this.seq = seq;
		this.value = value;
		this.text = text;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

}
