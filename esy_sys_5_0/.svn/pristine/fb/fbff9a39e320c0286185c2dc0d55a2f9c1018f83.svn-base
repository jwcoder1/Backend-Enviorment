package org.esy.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Index;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * Entity for 人员信息
 *
 */
@Entity
@EntityInfo("人员信息")
@Table(name = "base_mperson", indexes = { @Index(name = "mp_eid", columnList = "mp_eid"),
		@Index(name = "mp_pid", columnList = "mp_pid") })
@SqlResultSetMapping(name = "Person", entities = { @EntityResult(entityClass = Person.class, fields = {
		@FieldResult(name = "uid", column = "uid2"), @FieldResult(name = "created", column = "created"),
		@FieldResult(name = "updated", column = "updated"), @FieldResult(name = "eid", column = "eid"),
		@FieldResult(name = "pid", column = "pid"), @FieldResult(name = "cname", column = "cname"),
		@FieldResult(name = "py", column = "py"), @FieldResult(name = "pinyin", column = "pinyin"),
		@FieldResult(name = "ename", column = "ename"), @FieldResult(name = "shortName", column = "shortName"),
		@FieldResult(name = "sex", column = "sex"), @FieldResult(name = "employeeNo", column = "employeeNo"),
		@FieldResult(name = "birthday", column = "birthday"),
		@FieldResult(name = "officePhone", column = "officePhone"),
		@FieldResult(name = "mobilePhone", column = "mobilePhone"), @FieldResult(name = "mail", column = "mail"),
		@FieldResult(name = "seq", column = "seq"), @FieldResult(name = "enable", column = "enable"),
		@FieldResult(name = "memo", column = "memo"), @FieldResult(name = "type", column = "type"),
		@FieldResult(name = "positionName", column = "positionName"),
		@FieldResult(name = "postName", column = "postName"),
		@FieldResult(name = "positionSeq", column = "positionSeq"),
		@FieldResult(name = "workerTechGrdCd", column = "workerTechGrdCd"),
		@FieldResult(name = "majorCd", column = "majorCd"), @FieldResult(name = "sysid", column = "sysid"),
		@FieldResult(name = "puid", column = "puid") }) }, columns = { @ColumnResult(name = "puid") })

@DynamicUpdate
public class Person extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("路径")
	@Column(name = "mp_eid", length = 255, nullable = false)
	private String eid;

	@FieldInfo("人员编号")
	@Column(name = "mp_pid", length = 32, nullable = false)
	private String pid;

	@FieldInfo("中文名")
	@Column(name = "mp_cname", length = 64, nullable = false)
	private String cname;

	@FieldInfo("拼音简码")
	@Column(name = "mp_py", length = 64)
	private String py;

	@FieldInfo("拼音码")
	@Column(name = "mp_pinyin", length = 350)
	private String pinyin;

	@FieldInfo("英文名")
	@Column(name = "mp_ename", length = 64)
	private String ename;

	@FieldInfo("简称")
	@Column(name = "mp_shortName", length = 32)
	private String shortName;

	@FieldInfo("性别")
	@Column(name = "mp_sex", length = 1)
	private String sex;

	@FieldInfo("职工号")
	@Column(name = "mp_employeeNo", length = 32)
	private String employeeNo;

	@FieldInfo("生日")
	@Column(name = "mp_birthday")
	// @DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;

	@FieldInfo("办公电话")
	@Column(name = "mp_officePhone", length = 64)
	private String officePhone;

	@FieldInfo("移动电话")
	@Column(name = "mp_mobilePhone", length = 64)
	private String mobilePhone;

	@FieldInfo("电子邮件")
	@Column(name = "mp_mail", length = 128)
	private String mail;

	@FieldInfo("排序")
	@Column(name = "mp_seq")
	private Integer seq;

	@FieldInfo("状态")
	@Column(name = "mp_enable", nullable = false)
	private Boolean enable = false;

	@FieldInfo("备注")
	@Column(name = "mp_memo")
	private String memo;

	@FieldInfo("类型")
	@Column(name = "mp_type", length = 1)
	private String type; // 0 - 临时人员 ，1 - 正式职工

	// 所有的
	@Transient
	@JsonProperty("positionId")
	private String positionId;

	// 所有的
	@Transient
	@JsonProperty("positionName")
	private String positionName;

	// 个人的所有岗位
	@Transient
	@JsonProperty("postName")
	private String postName;

	// 组织
	@Transient
	@JsonProperty("orgName")
	private String orgName;

	// 主职的排序号
	@Transient
	@JsonProperty("positionSeq")
	private Integer positionSeq;

	// 人员对应的uid-->来源UID表
	@Transient
	@JsonProperty("puid")
	private String puid;

	// 人员主职所属的组织seq
	@Transient
	@JsonProperty("orgSeq")
	private Integer orgSeq;

	// 人员主职所属的组织层级
	@Transient
	@JsonProperty("orgLevel")
	private Integer orgLevel;

	@FieldInfo("技术职称")
	@Column(name = "worker_tech_grd_cd", length = 64)
	private String workerTechGrdCd;

	@FieldInfo("研究领域")
	@Column(name = "major_cd", length = 64)
	private String majorCd;

	@FieldInfo("备份ID")
	@Column(name = "mp_sysid", length = 32)
	private String sysid;

	/**
	 *
	 * 构造函数
	 *
	 */
	public Person() {
		super();
	}

	/**
	 *
	 * 构造函数
	 *
	 * @param eid
	 *            路径
	 * 
	 * @param pid
	 *            人员编号
	 * 
	 * @param cname
	 *            中文名
	 * 
	 * @param ename
	 *            英文名
	 * 
	 * @param sex
	 *            性别
	 * 
	 * @param officePhone
	 *            办公电话
	 * 
	 * @param mobilePhone
	 *            移动电话
	 * 
	 * @param mail
	 *            电子邮件
	 * 
	 * @param enable
	 *            状态
	 * 
	 */

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	/**
	 * @return eid 路径
	 */
	public String getEid() {
		return eid;
	}

	/**
	 * @param eid
	 *            路径
	 */
	public void setEid(String eid) {
		this.eid = eid;
	}

	/**
	 * @return pid 人员编号
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            人员编号
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return cname 中文名
	 */
	public String getCname() {
		return cname;
	}

	/**
	 * @param cname
	 *            中文名
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * @return ename 英文名
	 */
	public String getEname() {
		return ename;
	}

	/**
	 * @param ename
	 *            英文名
	 */
	public void setEname(String ename) {
		this.ename = ename;
	}

	/**
	 * @return sex 性别
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            性别
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return officePhone 办公电话
	 */
	public String getOfficePhone() {
		return officePhone;
	}

	/**
	 * @param officePhone
	 *            办公电话
	 */
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	/**
	 * @return mobilePhone 移动电话
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * @param mobilePhone
	 *            移动电话
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * @return mail 电子邮件
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 *            电子邮件
	 */
	public void setMail(String mail) {
		this.mail = mail;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getPositionSeq() {
		return positionSeq;
	}

	public void setPositionSeq(Integer positionSeq) {
		this.positionSeq = positionSeq;
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public Person(String uid, String eid, String pid, String cname, String py, String pinyin, String ename,
			String shortName, String sex, String employeeNo, Date birthday, String officePhone, String mobilePhone,
			String mail, Integer seq, Boolean enable, String memo, String type, String positionName, String postName,
			Integer positionSeq, Integer orgLevel, Integer orgSeq) {
		super();
		this.setUid(uid);
		this.eid = eid;
		this.pid = pid;
		this.cname = cname;
		this.py = py;
		this.pinyin = pinyin;
		this.ename = ename;
		this.shortName = shortName;
		this.sex = sex;
		this.employeeNo = employeeNo;
		this.birthday = birthday;
		this.officePhone = officePhone;
		this.mobilePhone = mobilePhone;
		this.mail = mail;
		this.seq = seq;
		this.enable = enable;
		this.memo = memo;
		this.type = type;
		this.positionName = positionName;
		this.postName = postName;
		this.positionSeq = positionSeq;
		this.orgLevel = orgLevel;
		this.orgSeq = orgSeq;
	}

	public Person(String eid, String pid, String cname, String py, String pinyin, String ename, String shortName,
			String sex, String employeeNo, Date birthday, String officePhone, String mobilePhone, String mail,
			Integer seq, Boolean enable, String memo, String type, String positionName, String postName, String orgName,
			Integer positionSeq, String puid, Integer orgSeq, Integer orgLevel, String workerTechGrdCd,
			String majorCd) {
		super();
		this.eid = eid;
		this.pid = pid;
		this.cname = cname;
		this.py = py;
		this.pinyin = pinyin;
		this.ename = ename;
		this.shortName = shortName;
		this.sex = sex;
		this.employeeNo = employeeNo;
		this.birthday = birthday;
		this.officePhone = officePhone;
		this.mobilePhone = mobilePhone;
		this.mail = mail;
		this.seq = seq;
		this.enable = enable;
		this.memo = memo;
		this.type = type;
		this.positionName = positionName;
		this.postName = postName;
		this.orgName = orgName;
		this.positionSeq = positionSeq;
		this.puid = puid;
		this.orgSeq = orgSeq;
		this.orgLevel = orgLevel;
		this.workerTechGrdCd = workerTechGrdCd;
		this.majorCd = majorCd;
	}

	public Integer getOrgSeq() {
		return orgSeq;
	}

	public void setOrgSeq(Integer orgSeq) {
		this.orgSeq = orgSeq;
	}

	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getWorkerTechGrdCd() {
		return workerTechGrdCd;
	}

	public void setWorkerTechGrdCd(String workerTechGrdCd) {
		this.workerTechGrdCd = workerTechGrdCd;
	}

	public String getMajorCd() {
		return majorCd;
	}

	public void setMajorCd(String majorCd) {
		this.majorCd = majorCd;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	/**
	 * @return the positionId
	 */
	public String getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

}
