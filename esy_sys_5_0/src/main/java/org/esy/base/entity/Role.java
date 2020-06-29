package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.Base;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityInfo("角色信息")
@Table(name = "base_mrole", indexes = { @Index(name = "mrole_created", columnList = "created"),
		@Index(name = "mrole_updated", columnList = "updated"), @Index(name = "mr_id", columnList = "mr_id") })
public class Role extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("编号")
	@FilterInfo(ListValue = "eq", LovValue = "match")
	@Column(name = "mr_id", length = 50, nullable = false)
	private String rid;

	@FieldInfo("名称")
	@FilterInfo(ListValue = "eq", LovValue = "match")
	@Column(name = "mr_name", length = 100, nullable = false)
	private String name;

	@FieldInfo("描述")
	@FilterInfo(ListValue = "eq")
	@Column(name = "mr_describe", length = 255)
	private String describe;

	@FieldInfo("类型") // 角色10 部门 20 岗位30 职位40
	@FilterInfo(ListValue = "eq")
	@Column(name = "mr_type", length = 255)
	private String type;

	// 虚拟栏位
	@Transient
	@JsonProperty("pid")
	private String pid;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * 
	 */
	public Role() {
		super();
	}

	/**
	 * @param rid
	 * @param name
	 * @param describe
	 */
	public Role(String rid, String name, String describe) {
		super();
		this.rid = rid;
		this.name = name;
		this.describe = describe;
	}

	/**
	 * @return the rid
	 */
	public String getRid() {
		return rid;
	}

	/**
	 * @param rid
	 *            the rid to set
	 */
	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the describe
	 */
	public String getDescribe() {
		return describe;
	}

	/**
	 * @param describe
	 *            the describe to set
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
