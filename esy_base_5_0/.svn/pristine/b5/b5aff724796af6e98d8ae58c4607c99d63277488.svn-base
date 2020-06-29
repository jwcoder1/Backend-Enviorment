package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 
 * Entity for 群组信息
 *
 */
@Entity
@EntityInfo("群组信息")
@Table(name = "base_mgroup", indexes = { @Index(name = "mgroup_eid", columnList = "mg_eid"),
		@Index(name = "mgroup_gid", columnList = "mg_gid") })
@DynamicUpdate
public class Group extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("企业路径")
	@Column(name = "mg_eid", length = 255, nullable = false)
	private String eid = "";

	@FieldInfo("编号")
	@Column(name = "mg_gid", length = 32, nullable = false)
	private String gid = "";

	@FieldInfo("自定义id")
	@Column(name = "mg_myid", length = 32)
	private String myid;

	@FieldInfo("名称")
	@Column(name = "mg_name", length = 64, nullable = false)
	private String name = "";

	@FieldInfo("描述")
	@Column(name = "mg_describe", length = 128)
	private String describe = "";

	/**
	 *
	 * 构造函数
	 *
	 */
	public Group() {
		super();
	}

	public String getMyid() {
		return myid;
	}

	public void setMyid(String myid) {
		this.myid = myid;
	}

	/**
	 *
	 * 构造函数
	 *
	 * @param eid
	 *            企业路径
	 * 
	 * @param gid
	 *            编号
	 * 
	 * @param name
	 *            名称
	 * 
	 * @param type
	 *            类型
	 * 
	 * @param describe
	 *            描述
	 * 
	 */
	public Group(String eid, String gid, String name, String describe) {
		super();
		this.eid = eid;
		this.gid = gid;
		this.name = name;
		this.describe = describe;
	}

	/**
	 * @return eid 企业路径
	 */
	public String getEid() {
		return eid;
	}

	/**
	 * @param eid
	 *            企业路径
	 */
	public void setEid(String eid) {
		this.eid = eid;
	}

	/**
	 * @return gid 编号
	 */
	public String getGid() {
		return gid;
	}

	/**
	 * @param gid
	 *            编号
	 */
	public void setGid(String gid) {
		this.gid = gid;
	}

	/**
	 * @return name 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return describe 描述
	 */
	public String getDescribe() {
		return describe;
	}

	/**
	 * @param describe
	 *            描述
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
