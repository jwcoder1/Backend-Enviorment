package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@EntityInfo("数据字典主表")
@Table(name = "base_mdictionary", indexes = { @Index(name = "mdictionary_created", columnList = "created"), @Index(name = "mdictionary_updated", columnList = "updated"), @Index(name = "md_model", columnList = "md_model"),
		@Index(name = "mdictionary_id", columnList = "md_id") })
@DynamicUpdate
public class Dictionary extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("模块名")
	@Column(name = "md_model", length = 100)
	private String model;

	@FieldInfo("编号")
	@Column(name = "md_id", length = 50, nullable = false)
	private String id;

	@FieldInfo("名称")
	@Column(name = "md_name", length = 100, nullable = false)
	private String name;

	// SYS平台级， ENT企业级
	@FieldInfo("类型")
	@Column(name = "md_type", length = 10)
	private String type;

	// 1-只读 2-修改 3-完全控制
	@FieldInfo("权限状态")
	@Column(name = "md_status", length = 10)
	private String status;

	@FieldInfo("序号")
	@Column(name = "md_seq", length = 100)
	private String seq;

	/**
	 * 
	 */
	public Dictionary() {
		super();
	}

	/**
	 * @param model
	 * @param id
	 * @param name
	 * @param type
	 */
	public Dictionary(String model, String id, String name, String type) {
		super();
		this.model = model;
		this.id = id;
		this.name = name;
		this.type = type;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

}
