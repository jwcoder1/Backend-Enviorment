package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * Entity for 职务信息
 *
 */
@Entity
@EntityInfo("职务信息")
@Table(name = "base_mposition", indexes = { @Index(name = "mpo_eid", columnList = "mpo_eid"), @Index(name = "mpo_pid", columnList = "mpo_pid"), @Index(name = "mpo_oid", columnList = "mpo_oid"),
		@Index(name = "mpo_showid", columnList = "mpo_showid"), @Index(name = "mpo_py", columnList = "mpo_py") })
@DynamicUpdate
public class Position extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("企业路径")
	@Column(name = "mpo_eid", length = 255, nullable = false)
	private String eid = "";

	@FieldInfo("组织编号")
	@Column(name = "mpo_oid", length = 32, nullable = false)
	private String oid = "";

	@FieldInfo("职位编号")
	@Column(name = "mpo_pid", length = 32, nullable = false)
	private String pid = "";

	@FieldInfo("显示编号")
	@Column(name = "mpo_showid", length = 32, nullable = false)
	private String showid = "";

	@FieldInfo("名称")
	@Column(name = "mpo_name", length = 64, nullable = false)
	private String name = "";

	@FieldInfo("简拼")
	@Column(name = "mpo_py", length = 64)
	private String py;

	@FieldInfo("排序")
	@Column(name = "mpo_seq")
	private Integer seq;

	@FieldInfo("状态")
	@Column(name = "mpo_enable", nullable = false)
	private Boolean enable = true;

	// 组织
	@Transient
	@JsonProperty("orgName")
	private String orgName;

	@FieldInfo("所属根节点")
	@Column(name = "mpo_rootpid", length = 32, nullable = false)
	private String rootpid = "";

	// 保存数据迁移过来，非中烟的编号
	@FieldInfo("对照编号")
	@Column(name = "mpo_tmpid", length = 32)
	private String tmpId = "";

	// 保存数据迁移过来，非中烟的名称
	@FieldInfo("对照名称")
	@Column(name = "mpo_tmpname", length = 64)
	private String tmpName = "";

	public String getShowid() {
		return showid;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Position(String uid, String eid, String oid, String pid, String showid, String name, String py, Integer seq, Boolean enable, String orgName, String rootpid) {
		super();
		this.setUid(uid);
		this.eid = eid;
		this.oid = oid;
		this.pid = pid;
		this.showid = showid;
		this.name = name;
		this.py = py;
		this.seq = seq;
		this.enable = enable;
		this.orgName = orgName;
		this.rootpid = rootpid;
	}

	/**
	 *
	 * 构造函数
	 *
	 */
	public Position() {
		super();
	}

	/**
	 *
	 * 构造函数
	 *
	 * @param oid
	 *            组织编号
	 * 
	 * @param eid
	 *            企业路径
	 * 
	 * @param pid
	 *            编号
	 * 
	 * @param name
	 *            名称
	 * 
	 * @param seq
	 *            排序
	 * 
	 * @param enable
	 *            状态
	 * 
	 */
	public Position(String oid, String pid, String name, Integer seq, Boolean enable) {
		super();
		this.oid = oid;
		this.pid = pid;
		this.name = name;
		this.seq = seq;
		this.enable = enable;
	}

	/**
	 * @return oid 组织编号
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @param oid
	 *            组织编号
	 */
	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * @return pid 编号
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            编号
	 */
	public void setPid(String pid) {
		this.pid = pid;
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
	 * @return seq 排序
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            排序
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * @return enable 状态
	 */
	public Boolean getEnable() {
		return enable;
	}

	/**
	 * @param enable
	 *            状态
	 */
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public String getRootpid() {
		return rootpid;
	}

	public void setRootpid(String rootpid) {
		this.rootpid = rootpid;
	}

	public String getTmpId() {
		return tmpId;
	}

	public void setTmpId(String tmpId) {
		this.tmpId = tmpId;
	}

	public String getTmpName() {
		return tmpName;
	}

	public void setTmpName(String tmpName) {
		this.tmpName = tmpName;
	}

}
