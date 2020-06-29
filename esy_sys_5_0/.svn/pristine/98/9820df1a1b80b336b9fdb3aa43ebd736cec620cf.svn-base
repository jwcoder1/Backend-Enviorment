package org.esy.base.entity.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.Base;
import org.esy.base.core.BaseProperties;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityInfo("帐号管理")
@Table(name = "Accountv")
@Subselect("SELECT m.*,\r\n" + 
		"CASE  m.ma_enable\r\n" + 
		"    WHEN TRUE THEN '已启用'\r\n" + 
		"ELSE \"未启用\"\r\n" + 
		"END AS enablename,\r\n" + 
		"CASE  m.ma_type\r\n" + 
		"    WHEN 'admin' THEN '平台管理员'\r\n" + 
		"    WHEN 'enterprise' THEN '企业管理员' ELSE '员工'\r\n" + 
		"END AS typename FROM base_maccount m  ")
@Synchronize("base_maccount")
public class Accountv extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("账号")
	@FilterInfo(ListValue = "eq", LovValue = "match")
	@Column(name = "ma_aid", length = 32, nullable = false)
	private String aid;

	@FieldInfo("名称")
	@FilterInfo(ListValue = "eq", LovValue = "match")
	@Column(name = "ma_name", length = 32, nullable = false)
	private String name;

	@FieldInfo("已启用")
	@Column(name = "ma_enable", nullable = false)
	private Boolean enable = false;

	@FieldInfo("手机号码")
	@FilterInfo(ListValue = "eq")
	@Column(name = "ma_mobile", length = 128)
	private String mobile;

	@FieldInfo("电子邮箱")
	@FilterInfo(ListValue = "eq")
	@Column(name = "ma_mail", length = 128)
	private String mail;

	@FieldInfo("企业路径")
	@FilterInfo(ListValue = "eq")
	@Column(name = "ma_eid", length = 255)
	private String eid;

	@FieldInfo("密码")
	@Column(name = "ma_password", length = 64)
	private String password;

	@FieldInfo("名")
	@Column(name = "ma_first_name", length = 16)
	private String firstName;

	@FieldInfo("姓")
	@Column(name = "ma_last_name", length = 16)
	private String lastName;

	@FieldInfo("别名")
	@Column(name = "ma_alias", length = 32)
	private String alias;

	// admin 平台管理员 enterprise企业管理员 user员工
	@FieldInfo("类型")
	@Column(name = "ma_type", length = 32, nullable = false)
	private String type = "";

	// admin 平台管理员 enterprise企业管理员 user员工
	@FieldInfo("状态中文显示")
	@Column(name = "enablename")
	private String enablename = "";

	// 类型中文显示
	@FieldInfo("类型中文显示")
	@Column(name = "typename")
	private String typename = "";
	
	 

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	@FieldInfo("关联编号")
	@Column(name = "ma_mno", length = 64)
	private String matrixNo;

	@FieldInfo("账号组")
	@Column(name = "ma_group", length = 64)
	private String group;

	@FieldInfo("最后登入时间")
	@Column(name = "ma_lastlogin")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastLogin;

	@FieldInfo("头像")
	@Column(name = "ma_picture", length = 32)
	private String picture;

	@FieldInfo("提醒更改密码")
	@Column(name = "ma_remind")
	private Boolean remind = false;

	@FieldInfo("姓名")
	@Column(name = "ma_payName")
	private String payName;

	@FieldInfo("身份证号")
	@Column(name = "ma_payID")
	private String payID;

	@FieldInfo("支付密码")
	@Column(name = "ma_payPassword")
	private String payPassword;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEnablename() {
		return enablename;
	}

	public void setEnablename(String enablename) {
		this.enablename = enablename;
	}

	public String getMatrixNo() {
		return matrixNo;
	}

	public void setMatrixNo(String matrixNo) {
		this.matrixNo = matrixNo;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Boolean getRemind() {
		return remind;
	}

	public void setRemind(Boolean remind) {
		this.remind = remind;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getPayID() {
		return payID;
	}

	public void setPayID(String payID) {
		this.payID = payID;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	@FieldInfo(value = "包含空值与null", remark = "指定栏位不管是有值还是没值多参与查询")
	@Transient
	@JsonProperty("filterfields")
	private String filterfields;

	@Transient
	@JsonProperty("isdel")
	private Boolean isdel = false;

	public Accountv() {
		super();
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getFilterfields() {
		return filterfields;
	}

	public void setFilterfields(String filterfields) {
		this.filterfields = filterfields;
	}

	public Boolean getIsdel() {
		return isdel;
	}

	public void setIsdel(Boolean isdel) {
		this.isdel = isdel;
	}

}
