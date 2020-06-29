package org.esy.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;

@Entity
@EntityInfo("UID仓库")
@Table(name = "base_uid", indexes = { @Index(name = "mu_uid", columnList = "mu_uid"), @Index(name = "mu_name", columnList = "mu_name"), @Index(name = "mu_tempstaffno", columnList = "mu_tempstaffno"),
		@Index(name = "mu_staffno", columnList = "mu_staffno"), @Index(name = "mu_pid", columnList = "mu_pid") })
public class Uid implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@FieldInfo("UID")
	@Column(name = "mu_uid", length = 20, nullable = false)
	private String uid;

	@FieldInfo("职工号")
	@Column(name = "mu_staffno", length = 30)
	private String staffNo = "";

	@FieldInfo("临时职工号")
	@Column(name = "mu_tempstaffno", length = 30)
	private String tempStaffNo = "";

	@FieldInfo("生日")
	@Column(name = "mu_birthday", length = 12)
	private String birthday = "";

	@FieldInfo("姓名")
	@Column(name = "mu_name", length = 30, nullable = false)
	private String name;

	@FieldInfo("身份证")
	@Column(name = "mu_identifyNo", length = 30)
	private String identifyNo = "";

	@FieldInfo("状态")
	@Column(name = "mu_status", length = 10, nullable = false) // 临时TEMP,生效EFFECTED
	private String status = "";

	@FieldInfo("关联人员编号")
	@Column(name = "mu_pid", length = 32)
	private String pid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTempStaffNo() {
		return tempStaffNo;
	}

	public void setTempStaffNo(String tempStaffNo) {
		this.tempStaffNo = tempStaffNo;
	}

	public String getIdentifyNo() {
		return identifyNo;
	}

	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}

	public Uid() {
	}

	public Uid(String uid, String staffNo, String tempStaffNo, String birthday, String name, String identifyNo, String status) {
		super();
		this.uid = uid;
		this.staffNo = staffNo;
		this.tempStaffNo = tempStaffNo;
		this.birthday = birthday;
		this.name = name;
		this.identifyNo = identifyNo;
		this.status = status;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
