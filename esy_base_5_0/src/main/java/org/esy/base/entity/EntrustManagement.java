package org.esy.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityInfo("委托办理 ")
@Table(name = "base_mentrustmanagement", indexes = { @Index(name = "mtm_entrustpersonid", columnList = "mtm_entrustpersonid"), @Index(name = "mtm_toentrustpersonid", columnList = "mtm_toentrustpersonid"),
		@Index(name = "mtm_entrustperson", columnList = "mtm_entrustperson"), @Index(name = "mtm_toentrustperson", columnList = "mtm_toentrustperson") })
@DynamicUpdate
public class EntrustManagement extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("委办人id")
	@Column(name = "mtm_entrustpersonid", length = 32, nullable = false)
	private String entrustPersonId;

	@FieldInfo("被委办人id")
	@Column(name = "mtm_toentrustpersonid", length = 32, nullable = false)
	private String toEntrustPersonId;

	@FieldInfo("委办人")
	@Column(name = "mtm_entrustperson", length = 64, nullable = false)
	private String entrustPerson;

	@FieldInfo("被委办人")
	@Column(name = "mtm_toentrustperson", length = 64, nullable = false)
	private String toEntrustPerson;

	@JsonProperty
	@Transient
	private String dateRange;

	@FieldInfo("开始日期")
	@Column(name = "mtm_startdate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date startDate;

	@FieldInfo("结束日期")
	@Column(name = "mtm__todate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date toDate;

	@FieldInfo("应用id")
	@Column(name = "mtm_aid", length = 32)
	private String aid = "";

	@FieldInfo("应用名称")
	@Column(name = "mtm_appname", length = 64)
	private String appName = "";

	@FieldInfo("功能模块")
	@Column(name = "mtm_module", length = 200)
	private String module = "";

	@FieldInfo("已启用")
	@Column(name = "ma_enable", nullable = false)
	private boolean enable = false;

	@FieldInfo("路径")
	@Column(name = "mp_eid", length = 255)
	private String eid;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public String getEntrustPerson() {
		return entrustPerson;
	}

	public void setEntrustPerson(String entrustPerson) {
		this.entrustPerson = entrustPerson;
	}

	public String getToEntrustPerson() {
		return toEntrustPerson;
	}

	public void setToEntrustPerson(String toEntrustPerson) {
		this.toEntrustPerson = toEntrustPerson;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public EntrustManagement(String entrustPerson, String toEntrustPerson, Date startDate, Date toDate, String appName, String module, boolean enable) {
		super();
		this.entrustPerson = entrustPerson;
		this.toEntrustPerson = toEntrustPerson;
		this.startDate = startDate;
		this.toDate = toDate;
		this.appName = appName;
		this.module = module;
		this.enable = enable;
	}

	public String getEntrustPersonId() {
		return entrustPersonId;
	}

	public void setEntrustPersonId(String entrustPersonId) {
		this.entrustPersonId = entrustPersonId;
	}

	public String getToEntrustPersonId() {
		return toEntrustPersonId;
	}

	public void setToEntrustPersonId(String toEntrustPersonId) {
		this.toEntrustPersonId = toEntrustPersonId;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public EntrustManagement(String entrustPersonId, String toEntrustPersonId, String entrustPerson, String toEntrustPerson, Date startDate, Date toDate, String aid, String appName, String module, boolean enable) {
		super();
		this.entrustPersonId = entrustPersonId;
		this.toEntrustPersonId = toEntrustPersonId;
		this.entrustPerson = entrustPerson;
		this.toEntrustPerson = toEntrustPerson;
		this.startDate = startDate;
		this.toDate = toDate;
		this.aid = aid;
		this.appName = appName;
		this.module = module;
		this.enable = enable;
	}

	public EntrustManagement() {
		super();
	}

}
