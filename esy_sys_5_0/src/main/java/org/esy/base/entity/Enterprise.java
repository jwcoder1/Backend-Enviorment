package org.esy.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * Entity for 企业信息
 *
 */
@Entity
@EntityInfo("企业信息")
@Table(name = "base_menterprise", indexes = { @Index(name = "menterprise_eid", columnList = "me_eid"),
		@Index(name = "menterprise_no", columnList = "me_no") })
@DynamicUpdate
public class Enterprise extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("路径")
	@FilterInfo(ListValue = "eq", LovValue = "match")
	@Column(name = "me_eid", length = 255, nullable = false)
	private String eid = "";

	@FieldInfo("别名")
	@Column(name = "me_alias", length = 64)
	private String alias = "";

	@FieldInfo("父节点编号")
	@Column(name = "me_pid", length = 32)
	private String pid;

	@FieldInfo("编号")
	@Column(name = "me_no", length = 32, nullable = false)
	private String no = "";

	@FieldInfo("集团路径")
	@Column(name = "me_group", length = 255)
	private String group = "";

	@FieldInfo("企业分类")
	@Column(name = "me_classify", length = 50)
	private String classify;

	@FieldInfo("中文全称")
	@FilterInfo(ListValue = "eq", LovValue = "match")
	@Column(name = "me_cname", length = 255, nullable = false)
	private String cname = "";

	@FieldInfo("英文全称")
	@Column(name = "me_ename", length = 255)
	private String ename = "";

	@FieldInfo("中文简称")
	@FilterInfo(ListValue = "match", LovValue = "match")
	@Column(name = "me_csname", length = 64, nullable = false)
	private String csName = "";

	@FieldInfo("英文简称")
	@Column(name = "me_esName", length = 64)
	private String esName = "";

	@FieldInfo("组织机构代码证号码")
	@Column(name = "me_orgCode", length = 64)
	private String orgCode = "";

	@FieldInfo("营业执照号码")
	@Column(name = "me_regCode", length = 64)
	private String regCode = "";

	@FieldInfo("企业税号")
	@Column(name = "me_taxCode", length = 64)
	private String taxCode = "";

	@FieldInfo("注册地（省）ID")
	@Column(name = "me_regProvince", length = 8)
	private String regProvince;

	@FieldInfo("注册地（省）")
	@Column(name = "me_regProvinceShow", length = 20)
	private String regProvinceShow;

	@FieldInfo("注册地（市）Id")
	@Column(name = "me_regCity", length = 8)
	private String regCity;

	@FieldInfo("注册地（市）")
	@Column(name = "me_regCityShow", length = 20)
	private String regCityShow;

	@FieldInfo("注册地（区）Id")
	@Column(name = "me_regDistrict", length = 8)
	private String regDistrict;

	@FieldInfo("注册地（区）")
	@Column(name = "me_regDistrictShow", length = 20)
	private String regDistrictShow;

	@FieldInfo("注册地址")
	@Column(name = "me_regAddr", length = 255)
	private String regAddr = "";

	@FieldInfo("联系电话")
	@Column(name = "me_regTel", length = 128)
	private String regTel = "";

	@FieldInfo("传真")
	@Column(name = "me_regFax", length = 128)
	private String regFax = "";

	@FieldInfo("法定代表人")
	@Column(name = "me_legalPerson", length = 64)
	private String legalPerson = "";

	@FieldInfo("联系人")
	@Column(name = "me_contact", length = 64)
	private String contact = "";

	@FieldInfo("管理员")
	@FilterInfo(ListValue = "eq")
	@Column(name = "me_admin", length = 32)
	private String admin = "";

	@FieldInfo("网址")
	@Column(name = "me_website", length = 255)
	private String website = "";

	@FieldInfo("相关附件")
	@Column(name = "me_attachment", length = 32)
	private String attachment = "";

	@FieldInfo("级别")
	@Column(name = "me_level", nullable = false)
	private Integer level = 0;

	@FieldInfo("注册资金（万元）")
	@Column(name = "me_regCapital")
	private Float regCapital = 0.0f;

	@FieldInfo("状态")
	@Column(name = "me_enable")
	private Boolean enable = false;

	@FieldInfo("注册日期")
	@Column(name = "me_regDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date regDate;

	@FieldInfo("备注")
	@Column(name = "me_memo", length = 255)
	private String memo = "";

	@FieldInfo("客户代码")
	@Column(name = "me_customerCode", length = 50)
	private String customerCode = "";

	@FieldInfo("进口邮箱")
	@Column(name = "me_importEmail", length = 32)
	private String importEmail = "";

	@FieldInfo("出口邮箱")
	@Column(name = "me_exportEmail", length = 32)
	private String exportEmail = "";

	@FieldInfo("进口短信")
	@Column(name = "me_importMessage", length = 32)
	private String importMessage = "";

	@FieldInfo("出口短信")
	@Column(name = "me_exportMessage", length = 32)
	private String exportMessage = "";

	@FieldInfo(value = "定时邮件推送")
	@Column(name = "autoSendEmail")
	private Boolean autoSendEmail = false;

	@FieldInfo("开户行")
	@Column(name = "me_bank", length = 100)
	private String bank = "";

	@FieldInfo("银行帐号")
	@Column(name = "me_bankcode", length = 100)
	private String bankcode = "";

	@FieldInfo("银行地址")
	@Column(name = "me_bankaddr", length = 100)
	private String bankaddr = "";

	@FieldInfo("开户名")
	@Column(name = "me_bankusername", length = 100)
	private String bankusername = "";

	@FieldInfo("经度")
	@Column(name = "me_longitude")
	private Integer longitude = 0;

	@FieldInfo("纬度")
	@Column(name = "me_latitude")
	private Integer latitude = 0;

	@FieldInfo("类别")
	@Column(name = "me_type", length = 32)
	private String type = "";

	@FieldInfo("会员类别")
	@FilterInfo(ListValue = "eq")
	@Column(name = "memtype", length = 32)
	private String memtype;

	@FieldInfo("状态")
	@FilterInfo(ListValue = "eq")
	@Column(name = "me_status", length = 32)
	private String status = "";

	@FieldInfo("保证金")
	@Column(name = "bailamt")
	private Double bailamt;

	@FieldInfo("流转金")
	@Column(name = "flowamt")
	private Double flowamt;

	/**
	 *
	 * 构造函数
	 *
	 */
	public Enterprise() {
	}

	public String getRegProvinceShow() {
		return regProvinceShow;
	}

	public void setRegProvinceShow(String regProvinceShow) {
		this.regProvinceShow = regProvinceShow;
	}

	public String getRegCityShow() {
		return regCityShow;
	}

	public void setRegCityShow(String regCityShow) {
		this.regCityShow = regCityShow;
	}

	public String getRegDistrictShow() {
		return regDistrictShow;
	}

	public void setRegDistrictShow(String regDistrictShow) {
		this.regDistrictShow = regDistrictShow;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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
	 * @return alias 别名
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            别名
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return no 编号
	 */
	public String getNo() {
		return no;
	}

	/**
	 * @param no
	 *            编号
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * @return group 集团路径
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            集团路径
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return cname 中文全称
	 */
	public String getCname() {
		return cname;
	}

	/**
	 * @param cname
	 *            中文全称
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * @return ename 英文全称
	 */
	public String getEname() {
		return ename;
	}

	/**
	 * @param ename
	 *            英文全称
	 */
	public void setEname(String ename) {
		this.ename = ename;
	}

	/**
	 * @return csName 中文简称
	 */
	public String getCsName() {
		return csName;
	}

	/**
	 * @param csName
	 *            中文简称
	 */
	public void setCsName(String csName) {
		this.csName = csName;
	}

	/**
	 * @return esName 英文简称
	 */
	public String getEsName() {
		return esName;
	}

	/**
	 * @param esName
	 *            英文简称
	 */
	public void setEsName(String esName) {
		this.esName = esName;
	}

	/**
	 * @return orgCode 组织机构代码证号码
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 *            组织机构代码证号码
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return regCode 营业执照号码
	 */
	public String getRegCode() {
		return regCode;
	}

	/**
	 * @param regCode
	 *            营业执照号码
	 */
	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	/**
	 * @return taxCode 企业税号
	 */
	public String getTaxCode() {
		return taxCode;
	}

	/**
	 * @param taxCode
	 *            企业税号
	 */
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	/**
	 * @return regProvince 注册地（省）
	 */
	public String getRegProvince() {
		return regProvince;
	}

	/**
	 * @param regProvince
	 *            注册地（省）
	 */
	public void setRegProvince(String regProvince) {
		this.regProvince = regProvince;
	}

	/**
	 * @return regCity 注册地（市）
	 */
	public String getRegCity() {
		return regCity;
	}

	/**
	 * @param regCity
	 *            注册地（市）
	 */
	public void setRegCity(String regCity) {
		this.regCity = regCity;
	}

	/**
	 * @return regDistrict 注册地（区）
	 */
	public String getRegDistrict() {
		return regDistrict;
	}

	/**
	 * @param regDistrict
	 *            注册地（区）
	 */
	public void setRegDistrict(String regDistrict) {
		this.regDistrict = regDistrict;
	}

	/**
	 * @return regAddr 注册地址
	 */
	public String getRegAddr() {
		return regAddr;
	}

	/**
	 * @param regAddr
	 *            注册地址
	 */
	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	/**
	 * @return regTel 联系电话
	 */
	public String getRegTel() {
		return regTel;
	}

	/**
	 * @param regTel
	 *            联系电话
	 */
	public void setRegTel(String regTel) {
		this.regTel = regTel;
	}

	/**
	 * @return legalPerson 法定代表人
	 */
	public String getLegalPerson() {
		return legalPerson;
	}

	/**
	 * @param legalPerson
	 *            法定代表人
	 */
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	/**
	 * @return contact 联系人
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            联系人
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return admin 管理员
	 */
	public String getAdmin() {
		return admin;
	}

	/**
	 * @param admin
	 *            管理员
	 */
	public void setAdmin(String admin) {
		this.admin = admin;
	}

	/**
	 * @return website 网址
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website
	 *            网址
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return attachment 相关附件
	 */
	public String getAttachment() {
		return attachment;
	}

	/**
	 * @param attachment
	 *            相关附件
	 */
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	/**
	 * @return level 级别
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            级别
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * @return regCapital 注册资金（万元）
	 */
	public Float getRegCapital() {
		return regCapital;
	}

	/**
	 * @param regCapital
	 *            注册资金（万元）
	 */
	public void setRegCapital(Float regCapital) {
		this.regCapital = regCapital;
	}

	/**
	 * @return regDate 注册日期
	 */
	public Date getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate
	 *            注册日期
	 */
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return importEmail 进口邮箱
	 */
	public String getImportEmail() {
		return importEmail;
	}

	/**
	 * @param importEmail
	 *            进口邮箱
	 */
	public void setImportEmail(String importEmail) {
		this.importEmail = importEmail;
	}

	/**
	 * @return exportEmail 出口邮箱
	 */
	public String getExportEmail() {
		return exportEmail;
	}

	/**
	 * @param exportEmail
	 *            出口邮箱
	 */
	public void setExportEmail(String exportEmail) {
		this.exportEmail = exportEmail;
	}

	/**
	 * @return importMessage 进口短信
	 */
	public String getImportMessage() {
		return importMessage;
	}

	/**
	 * @param importMessage
	 *            进口短信
	 */
	public void setImportMessage(String importMessage) {
		this.importMessage = importMessage;
	}

	/**
	 * @return exportMessage 出口短信
	 */
	public String getExportMessage() {
		return exportMessage;
	}

	/**
	 * @param exportMessage
	 *            出口短信
	 */
	public void setExportMessage(String exportMessage) {
		this.exportMessage = exportMessage;
	}

	public Boolean getAutoSendEmail() {
		return autoSendEmail;
	}

	public void setAutoSendEmail(Boolean autoSendEmail) {
		this.autoSendEmail = autoSendEmail;
	}

	public Enterprise(String eid, String alias, String pid, String no, String group, String classify, String cname,
			String ename, String csName, String esName, String orgCode, String regCode, String taxCode,
			String regProvince, String regProvinceShow, String regCity, String regCityShow, String regDistrict,
			String regDistrictShow, String regAddr, String regTel, String legalPerson, String contact, String admin,
			String website, String attachment, Integer level, Float regCapital, Boolean enable, Date regDate,
			String memo, String customerCode, String importEmail, String exportEmail, String importMessage,
			String exportMessage, Boolean autoSendEmail) {
		super();
		this.eid = eid;
		this.alias = alias;
		this.pid = pid;
		this.no = no;
		this.group = group;
		this.classify = classify;
		this.cname = cname;
		this.ename = ename;
		this.csName = csName;
		this.esName = esName;
		this.orgCode = orgCode;
		this.regCode = regCode;
		this.taxCode = taxCode;
		this.regProvince = regProvince;
		this.regProvinceShow = regProvinceShow;
		this.regCity = regCity;
		this.regCityShow = regCityShow;
		this.regDistrict = regDistrict;
		this.regDistrictShow = regDistrictShow;
		this.regAddr = regAddr;
		this.regTel = regTel;
		this.legalPerson = legalPerson;
		this.contact = contact;
		this.admin = admin;
		this.website = website;
		this.attachment = attachment;
		this.level = level;
		this.regCapital = regCapital;
		this.enable = enable;
		this.regDate = regDate;
		this.memo = memo;
		this.customerCode = customerCode;
		this.importEmail = importEmail;
		this.exportEmail = exportEmail;
		this.importMessage = importMessage;
		this.exportMessage = exportMessage;
		this.autoSendEmail = autoSendEmail;
	}

	/**
	 * 旭盈专用,不要删
	 */
	public Enterprise(String uid, Date created, Date updated, String eid, String alias, String no, String group,
			String cname, String ename, String csName, String esName, String orgCode, String regCode, String taxCode,
			String regProvince, String regCity, String regDistrict, String regAddr, String regTel, String legalPerson,
			String contact, String admin, String website, String attachment, Integer level, Float regCapital,
			Boolean enable, Date regDate, String memo, String customerCode) {
		super(uid, created, updated);
		this.eid = eid;
		this.alias = alias;
		this.no = no;
		this.group = group;
		this.cname = cname;
		this.ename = ename;
		this.csName = csName;
		this.esName = esName;
		this.orgCode = orgCode;
		this.regCode = regCode;
		this.taxCode = taxCode;
		this.regProvince = regProvince;
		this.regCity = regCity;
		this.regDistrict = regDistrict;
		this.regAddr = regAddr;
		this.regTel = regTel;
		this.legalPerson = legalPerson;
		this.contact = contact;
		this.admin = admin;
		this.website = website;
		this.attachment = attachment;
		this.level = level;
		this.regCapital = regCapital;
		this.enable = enable;
		this.regDate = regDate;
		this.memo = memo;
		this.customerCode = customerCode;
	}

	public Enterprise(String uid, String no, String cname, String admin, String importEmail, String exportEmail,
			String importMessage, String exportMessage, Boolean autoSendEmail) {
		super(uid);
		this.no = no;
		this.cname = cname;
		this.admin = admin;
		this.importEmail = importEmail;
		this.exportEmail = exportEmail;
		this.importMessage = importMessage;
		this.exportMessage = exportMessage;
		this.autoSendEmail = autoSendEmail;
	}

	public Enterprise(String eid) {
		this.eid = eid;
	}

	public String getRegFax() {
		return regFax;
	}

	public void setRegFax(String regFax) {
		this.regFax = regFax;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public String getBankaddr() {
		return bankaddr;
	}

	public void setBankaddr(String bankaddr) {
		this.bankaddr = bankaddr;
	}

	public Integer getLongitude() {
		return longitude;
	}

	public void setLongitude(Integer longitude) {
		this.longitude = longitude;
	}

	public Integer getLatitude() {
		return latitude;
	}

	public void setLatitude(Integer latitude) {
		this.latitude = latitude;
	}

	public String getBankusername() {
		return bankusername;
	}

	public void setBankusername(String bankusername) {
		this.bankusername = bankusername;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMemtype() {
		return memtype;
	}

	public void setMemtype(String memtype) {
		this.memtype = memtype;
	}

	public Double getBailamt() {
		return bailamt;
	}

	public void setBailamt(Double bailamt) {
		this.bailamt = bailamt;
	}

	public Double getFlowamt() {
		return flowamt;
	}

	public void setFlowamt(Double flowamt) {
		this.flowamt = flowamt;
	}

}
