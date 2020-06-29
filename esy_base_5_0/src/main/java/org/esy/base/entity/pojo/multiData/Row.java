package org.esy.base.entity.pojo.multiData;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Row {

	private String action;

	private String index;

	private List<Table> tables;

	private Boolean sys_use_mark;

	private String sys_id_key;

	/*
	 * Uid属性
	 */
	private String uid;

	private String staffNo;

	private String birthday;

	private String name;

	private String identifyNo;

	private String status;

	/*
	 * Person属性
	 */
	private String eid;

	private String pid;

	private String cname;

	private String sex;

	private String employeeNo;

	private String officePhone;

	private String mobilePhone;

	private String mail;

	private String seq;

	private String enable;

	private String memo;

	private String py;

	private String pingyin;

	/*
	 * Identity属性
	 */

	private String oid;

	private String positionId;

	private String postId;

	private String type;

	private String isMain;

	private String startDate;

	private String toDate;

	// org属性

	private String abbreviated;

	private String isGroup;

	private String path;

	private String level;

	public String getAction() {
		return action;
	}

	@XmlAttribute(name = "ACTION", required = false)
	public void setAction(String action) {
		this.action = action;
	}

	public String getIndex() {
		return index;
	}

	@XmlAttribute(name = "Index", required = false)
	public void setIndex(String index) {
		this.index = index;
	}

	public List<Table> getTables() {
		return tables;
	}

	@XmlElement(name = "TABLE", required = false)
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public String getUid() {
		return uid;
	}

	@XmlAttribute(name = "uid", required = false)
	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getStaffNo() {
		return staffNo;
	}

	@XmlAttribute(name = "staffNo", required = false)
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getBirthday() {
		return birthday;
	}

	@XmlAttribute(name = "birthday", required = false)
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute(name = "name", required = false)
	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifyNo() {
		return identifyNo;
	}

	@XmlAttribute(name = "identifyNo", required = false)
	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}

	public String getStatus() {
		return status;
	}

	@XmlAttribute(name = "status", required = false)
	public void setStatus(String status) {
		this.status = status;
	}

	public String getEid() {
		return eid;
	}

	@XmlAttribute(name = "eid", required = false)
	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getPid() {
		return pid;
	}

	@XmlAttribute(name = "pid", required = false)
	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCname() {
		return cname;
	}

	@XmlAttribute(name = "cname", required = false)
	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getSex() {
		return sex;
	}

	@XmlAttribute(name = "sex", required = false)
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	@XmlAttribute(name = "employeeNo", required = false)
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	@XmlAttribute(name = "officePhone", required = false)
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	@XmlAttribute(name = "mobilePhone", required = false)
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMail() {
		return mail;
	}

	@XmlAttribute(name = "mail", required = false)
	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSeq() {
		return seq;
	}

	@XmlAttribute(name = "seq", required = false)
	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getEnable() {
		return enable;
	}

	@XmlAttribute(name = "enable", required = false)
	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getMemo() {
		return memo;
	}

	@XmlAttribute(name = "memo", required = false)
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOid() {
		return oid;
	}

	@XmlAttribute(name = "oid", required = false)
	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPositionId() {
		return positionId;
	}

	@XmlAttribute(name = "positionId", required = false)
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPostId() {
		return postId;
	}

	@XmlAttribute(name = "postId", required = false)
	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getType() {
		return type;
	}

	@XmlAttribute(name = "type", required = false)
	public void setType(String type) {
		this.type = type;
	}

	public String getIsMain() {
		return isMain;
	}

	@XmlAttribute(name = "isMain", required = false)
	public void setIsMain(String isMain) {
		this.isMain = isMain;
	}

	public String getStartDate() {
		return startDate;
	}

	@XmlAttribute(name = "startDate", required = false)
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getToDate() {
		return toDate;
	}

	@XmlAttribute(name = "toDate", required = false)
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Boolean getSys_use_mark() {
		return sys_use_mark;
	}

	@XmlAttribute(name = "sys_use_mark", required = false)
	public void setSys_use_mark(Boolean sys_use_mark) {
		this.sys_use_mark = sys_use_mark;
	}

	public String getSys_id_key() {
		return sys_id_key;
	}

	@XmlAttribute(name = "sys_id_key", required = false)
	public void setSys_id_key(String sys_id_key) {
		this.sys_id_key = sys_id_key;
	}

	public String getAbbreviated() {
		return abbreviated;
	}

	@XmlAttribute(name = "abbreviated", required = false)
	public void setAbbreviated(String abbreviated) {
		this.abbreviated = abbreviated;
	}

	public String getIsGroup() {
		return isGroup;
	}

	@XmlAttribute(name = "isGroup", required = false)
	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}

	public String getPath() {
		return path;
	}

	@XmlAttribute(name = "path", required = false)
	public void setPath(String path) {
		this.path = path;
	}

	public String getLevel() {
		return level;
	}

	@XmlAttribute(name = "level", required = false)
	public void setLevel(String level) {
		this.level = level;
	}

	public String getPy() {
		return py;
	}

	@XmlAttribute(name = "py", required = false)
	public void setPy(String py) {
		this.py = py;
	}

	public String getPingyin() {
		return pingyin;
	}

	@XmlAttribute(name = "pingyin", required = false)
	public void setPingyin(String pingyin) {
		this.pingyin = pingyin;
	}

	public Row() {

	}
}
