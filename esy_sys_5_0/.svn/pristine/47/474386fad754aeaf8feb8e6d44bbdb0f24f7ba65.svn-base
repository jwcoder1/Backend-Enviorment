package org.esy.base.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.Base;
import org.esy.base.entity.dto.MenuDto;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityInfo("用户账号")
@Table(name = "base_maccount", indexes = { @Index(name = "maccount_aid", columnList = "ma_aid"),
		@Index(name = "maccount_eid", columnList = "ma_eid"), @Index(name = "maccount_mno", columnList = "ma_mno") })
@DynamicUpdate
public class Account extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("企业路径")
	@FilterInfo(ListValue = "eq")
	@Column(name = "ma_eid", length = 255)
	private String eid;

	@FieldInfo("账号")
	@FilterInfo(ListValue = "eq,in", LovValue = "match", FieldsValue = "aid,inaid")
	@Column(name = "ma_aid", length = 32, nullable = false)
	private String aid;

	@FieldInfo("密码")
	@Column(name = "ma_password", length = 64)
	private String password;

	@FieldInfo("名称")
	@FilterInfo(ListValue = "eq", LovValue = "match")
	@Column(name = "ma_name", length = 32, nullable = false)
	private String name;

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

	@FieldInfo("已启用")
	@Column(name = "ma_enable", nullable = false)
	private Boolean enable = false;

	@FieldInfo("关联编号")
	@Column(name = "ma_mno", length = 64)
	private String matrixNo;

	@FieldInfo("账号组")
	@Column(name = "ma_group", length = 64)
	private String group;

	@FieldInfo("手机号码")
	@Column(name = "ma_mobile", length = 128)
	private String mobile;

	@FieldInfo("电子邮箱")
	@Column(name = "ma_mail", length = 128)
	private String mail;

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

	@Transient
	@FieldInfo("有代号列表(豆号分开)")
	@JsonProperty("inaid")
	private String inaid;

	@Transient
	@FieldInfo("状态中文显示")
	@JsonProperty("enablename")
	private String enablename;

	@Transient
	private List<MenuDto> menus;

	public Account() {
		super();
	}

	public String getEnablename() {
		return enablename;
	}

	public void setEnablename(String enablename) {
		this.enablename = enablename;
	}

	public List<MenuDto> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuDto> menus) {
		this.menus = menus;
	}

	/**
	 * @param eid
	 * @param aid
	 * @param password
	 * @param name
	 * @param alias
	 * @param type
	 * @param enable
	 * @param matrixNo
	 * @param group
	 * @param mobile
	 * @param mail
	 * @param lastLogin
	 * @param picture
	 */
	public Account(String eid, String aid, String password, String name, String alias, String type, boolean enable,
			String matrixNo, String group, String mobile, String mail, Date lastLogin, String picture) {
		super();
		this.eid = eid;
		this.aid = aid;
		this.password = password;
		this.name = name;
		this.alias = alias;
		this.type = type;
		this.enable = enable;
		this.matrixNo = matrixNo;
		this.group = group;
		this.mobile = mobile;
		this.mail = mail;
		this.lastLogin = lastLogin;
		this.picture = picture;
	}

	/**
	 * @return the eid
	 */
	public String getEid() {
		return eid;
	}

	/**
	 * @param eid
	 *            the eid to set
	 */
	public void setEid(String eid) {
		this.eid = eid;
	}

	/**
	 * @return the aid
	 */
	public String getAid() {
		return aid;
	}

	/**
	 * @param aid
	 *            the aid to set
	 */
	public void setAid(String aid) {
		this.aid = aid;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/*	*//**
			 * @return the enable
			 */
	/*
	 * public boolean isEnable() { return enable; }
	 */

	/**
	 * @param enable
	 *            the enable to set
	 */
	/*
	 * public void setEnable(boolean enable) { this.enable = enable; }
	 */

	/**
	 * @return the matrixNo
	 */
	public String getMatrixNo() {
		return matrixNo;
	}

	/**
	 * @param matrixNo
	 *            the matrixNo to set
	 */
	public void setMatrixNo(String matrixNo) {
		this.matrixNo = matrixNo;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin
	 *            the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * @param picture
	 *            the picture to set
	 */
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

	public boolean isEnable() {
		return enable;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	/**
	 * @return the aid
	 */
	// public Long getUser() {
	// long usercode = 0l;
	// return usercode;
	// }

	public Account(String aid, String name) {
		super();
		this.aid = aid;
		this.name = name;
	}

	public Account(String aid) {
		this.aid = aid;
	}

	public String getInaid() {
		return inaid;
	}

	public void setInaid(String inaid) {
		this.inaid = inaid;
	}

}
