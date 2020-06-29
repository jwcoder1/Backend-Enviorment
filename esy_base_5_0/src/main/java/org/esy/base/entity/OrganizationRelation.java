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

@Entity
@EntityInfo("组织关系")
@Table(name = "base_morganizationrelation", indexes = { @Index(name = "mor_eid", columnList = "mor_eid"), @Index(name = "mor_path", columnList = "mor_path"), @Index(name = "mor_oid", columnList = "mor_oid"),
		@Index(name = "mor_pid", columnList = "mor_pid") })

@DynamicUpdate
public class OrganizationRelation extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("企业路径")
	@Column(name = "mor_eid", length = 255, nullable = false)
	private String eid = "";

	@FieldInfo("编号")
	@Column(name = "mor_oid", length = 32, nullable = false)
	private String oid;

	@FieldInfo("上级编号")
	@Column(name = "mor_pid", length = 32)
	private String pid = "";

	// 上级名称
	@Transient
	@JsonProperty("pname")
	private String pname = "";

	@FieldInfo("类别")
	@Column(name = "mor_type", length = 64, nullable = false)
	private String type;

	// 根组织名称
	@Transient
	@JsonProperty("rootname")
	private String rootname = "";

	@FieldInfo("排序")
	@Column(name = "mor_seq")
	private Integer seq = 0;

	@FieldInfo("组织路径")
	@Column(name = "mor_path", length = 255, nullable = false)
	private String path;

	@FieldInfo("层级")
	@Column(name = "mor_level")
	private Integer level = 0;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public OrganizationRelation(String eid, String oid, String pid, String type, Integer seq, String path) {
		super();
		this.eid = eid;
		this.oid = oid;
		this.pid = pid;
		this.type = type;
		this.seq = seq;
		this.path = path;
	}

	public OrganizationRelation(String eid, String oid, String pid, String pname, String type, String rootname, Integer seq, String path, Integer level) {
		super();
		this.eid = eid;
		this.oid = oid;
		this.pid = pid;
		this.pname = pname;
		this.type = type;
		this.rootname = rootname;
		this.seq = seq;
		this.path = path;
		this.level = level;
	}

	public OrganizationRelation(String uid, String eid, String oid, String pid, String pname, String type, String rootname, Integer seq, String path) {
		super();
		this.setUid(uid);
		this.eid = eid;
		this.oid = oid;
		this.pid = pid;
		this.pname = pname;
		this.type = type;
		this.rootname = rootname;
		this.seq = seq;
		this.path = path;
	}

	public OrganizationRelation() {

	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getRootname() {
		return rootname;
	}

	public void setRootname(String rootname) {
		this.rootname = rootname;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
