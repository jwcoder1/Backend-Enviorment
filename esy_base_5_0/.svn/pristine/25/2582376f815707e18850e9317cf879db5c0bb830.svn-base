package org.esy.base.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@EntityInfo("应用分组")
@Table(name = "base_mappgroup", indexes = { @Index(name = "mappgroup_eid", columnList = "ma_eid"),
		@Index(name = "mappgroup_name", columnList = "ma_name") })
@DynamicUpdate
public class AppGroup extends Base {

	private static final long serialVersionUID = 4053191782584644159L;

	@FieldInfo("企业路径")
	@Column(name = "ma_eid", length = 255)
	private String eid;

	@FieldInfo("应用分组编号")
	@Column(name = "ma_agid", length = 10, nullable = false)
	private String agid;

	@FieldInfo("父group的agid")
	@Column(name = "ma_pid", length = 10)
	private String pid;

	@FieldInfo("名称")
	@Column(name = "ma_name", length = 32, nullable = false)
	private String name;

	@FieldInfo("企业名称")
	@Column(name = "ma_company", length = 32)
	private String company;

	@FieldInfo("排序号")
	@Column(name = "ma_seq")
	private Integer seq;

	@Transient
	List<AppGroup> childrens;

	@Transient
	List<Application> applications;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public List<AppGroup> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<AppGroup> childrens) {
		this.childrens = childrens;
	}

	public AppGroup() {
		super();
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAgid() {
		return agid;
	}

	public void setAgid(String agid) {
		this.agid = agid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
