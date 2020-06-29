package org.esy.base.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.common.BaseUtil;
import org.esy.base.util.BeanMapUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
public class ZxBaseProperties implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@FieldInfo("主键")
	@FilterInfo(ListValue = "eq", alias = "a")
	@Column(name = "uid", length = 32)
	private String uid;

	@FieldInfo(value = "创建时间")
	@FilterInfo(ListValue = "gte,lte", FieldsValue = "createda,createdb", alias = "a")
	@Column(name = "created", nullable = false, updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date created = new Date();

	@FieldInfo(value = "更新时间")
	@FilterInfo(ListValue = "gte,lte", FieldsValue = "updated,updatedb", alias = "a")
	@Column(name = "updated", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updated;

	@FieldInfo(value = "时间戳")
	@Column(name = "updstamp", columnDefinition = "timestamp", nullable = true, updatable = false, insertable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updstamp = new Date();

	@FieldInfo("条件类别")
	@Transient
	@JsonProperty("conditionRelatiion")
	private String conditionRelatiion;

	@FieldInfo("全文搜索")
	@Transient
	@JsonProperty("lovsearchstr")
	private String lovsearchstr;

	@FieldInfo("排外条件")
	@Transient
	@JsonProperty("notinfield")
	private String notinfield;

	@FieldInfo("表单状态")
	@Transient
	@JsonProperty("formstatus")
	private String formstatus;

	@FieldInfo(value = "创建时间")
	@Transient
	@JsonProperty("createda")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createda;

	@FieldInfo(value = "更新时间")
	@Transient
	@JsonProperty("updateda")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateda;

	@FieldInfo(value = "创建时间")
	@Transient
	@JsonProperty("createdb")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdb;

	@FieldInfo(value = "更新时间")
	@Transient
	@JsonProperty("updatedb")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updatedb;

	@FieldInfo(value = "包含空值与null", remark = "指定栏位不管是有值还是没值多参与查询")
	@Transient
	@JsonProperty("filterfields")
	private String filterfields;

	@FieldInfo(value = "前端传送的token值", remark = "匹配前后端查询结果")
	@Transient
	@JsonProperty("token")
	private String token;

	@FieldInfo("企业路径")
	@FilterInfo(ListValue = "eq", alias = "a")
	@Column(name = "eid", length = 255)
	private String eid;

	@FieldInfo("创建人员")
	@FilterInfo(ListValue = "eq", alias = "a")
	@Column(name = "createPid", length = 32)
	private String createPid;

	@FieldInfo("最后修改人员")
	@FilterInfo(ListValue = "eq", alias = "a")
	@Column(name = "lastEditpid", length = 32)
	private String lastEditpid;

	@FieldInfo("创建人部门Id")
	@FilterInfo(ListValue = "eq", alias = "a")
	@Column(name = "createOrgId", length = 32)
	private String createOrgId;

	@FieldInfo("最后修改部门Id")
	@FilterInfo(ListValue = "eq", alias = "a")
	@Column(name = "lastEditOrgId", length = 32)
	private String lastEditOrgId;

	@Transient
	@JsonProperty("isdel")
	private Boolean isdel = false;

	public ZxBaseProperties() {
		super();
	}

	public ZxBaseProperties(String uid) {
		super();
		this.uid = uid;
	}

	/**
	 * 更新实体信息
	 */
	public void updateEntity() {

		setUpdated(new Date());
		if (BaseUtil.isNotEmpty(BaseUtil.getUser())) {
			if (BaseUtil.isEmpty(getEid())) {
				setEid(BaseUtil.getUser().getEid());
			}
			this.setLastEditpid(BaseUtil.toString(BeanMapUtils.beanToMap(BaseUtil.getUser()).get("aid")));
		}

	}

	/**
	 * 创建实体信息
	 */
	public void createEntity() {

		if (BaseUtil.isNotEmpty(BaseUtil.getUser())) {
			if (BaseUtil.isEmpty(getEid())) {
				setEid(BaseUtil.getUser().getEid());
			}
			this.setCreatePid(BaseUtil.toString(BeanMapUtils.beanToMap(BaseUtil.getUser()).get("aid")));
			this.setLastEditpid(BaseUtil.toString(BeanMapUtils.beanToMap(BaseUtil.getUser()).get("aid")));
		}

	}

	/**
	 * @param uid
	 * @param created
	 * @param updated
	 */
	public ZxBaseProperties(String uid, Date created, Date updated) {
		super();
		this.uid = uid;
		this.created = created;
		this.updated = updated;
	}

	/**
	 * @return 是否为新实体
	 */
	public boolean checkNew() {
		return (uid == null || uid.equals(""));
	}

	@PreUpdate
	public void onPreUpdate() {
		this.updated = new Date();
	}

	@PrePersist
	public void onPrePersist() {
		if (this.created == null) {
			this.created = new Date();
		}
		this.updated = new Date();
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * @param updated
	 *            the updated to set
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * @return the conditionRelatiion
	 */
	public final String getConditionRelatiion() {
		return conditionRelatiion;
	}

	/**
	 * @param conditionRelatiion
	 *            the conditionRelatiion to set
	 */
	public final void setConditionRelatiion(String conditionRelatiion) {
		this.conditionRelatiion = conditionRelatiion;
	}

	/**
	 * @return the lovsearchstr
	 */
	public final String getLovsearchstr() {
		return lovsearchstr;
	}

	/**
	 * @param lovsearchstr
	 *            the lovsearchstr to set
	 */
	public final void setLovsearchstr(String lovsearchstr) {
		this.lovsearchstr = lovsearchstr;
	}

	/**
	 * @return the notinfield
	 */
	public final String getNotinfield() {
		return notinfield;
	}

	/**
	 * @param notinfield
	 *            the notinfield to set
	 */
	public final void setNotinfield(String notinfield) {
		this.notinfield = notinfield;
	}

	/**
	 * @return the formstatus
	 */
	public final String getFormstatus() {
		return formstatus;
	}

	/**
	 * @param formstatus
	 *            the formstatus to set
	 */
	public final void setFormstatus(String formstatus) {
		this.formstatus = formstatus;
	}

	/**
	 * @return the createdb
	 */
	public Date getCreatedb() {
		return createdb;
	}

	/**
	 * @param createdb
	 *            the createdb to set
	 */
	public void setCreatedb(Date createdb) {
		this.createdb = createdb;
	}

	/**
	 * @return the updatedb
	 */
	public Date getUpdatedb() {
		return updatedb;
	}

	/**
	 * @param updatedb
	 *            the updatedb to set
	 */
	public void setUpdatedb(Date updatedb) {
		this.updatedb = updatedb;
	}

	/**
	 * @return the createda
	 */
	public Date getCreateda() {
		return createda;
	}

	/**
	 * @param createda
	 *            the createda to set
	 */
	public void setCreateda(Date createda) {
		this.createda = createda;
	}

	/**
	 * @return the updateda
	 */
	public Date getUpdateda() {
		return updateda;
	}

	/**
	 * @param updateda
	 *            the updateda to set
	 */
	public void setUpdateda(Date updateda) {
		this.updateda = updateda;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the updstamp
	 */
	public Date getUpdstamp() {
		return updstamp;
	}

	/**
	 * @param updstamp
	 *            the updstamp to set
	 */
	public void setUpdstamp(Date updstamp) {
		this.updstamp = updstamp;
	}

	public String getFilterfields() {
		return filterfields;
	}

	public void setFilterfields(String filterfields) {
		this.filterfields = filterfields;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getCreatePid() {
		return createPid;
	}

	public void setCreatePid(String createPid) {
		this.createPid = createPid;
	}

	public String getLastEditpid() {
		return lastEditpid;
	}

	public void setLastEditpid(String lastEditpid) {
		this.lastEditpid = lastEditpid;
	}

	/**
	 * @return the isdel
	 */
	public final Boolean getIsdel() {
		return isdel;
	}

	/**
	 * @param isdel
	 *            the isdel to set
	 */
	public final void setIsdel(Boolean isdel) {
		this.isdel = isdel;
	}

	public String getCreateOrgId() {
		return createOrgId;
	}

	public void setCreateOrgId(String createOrgId) {
		this.createOrgId = createOrgId;
	}

	public String getLastEditOrgId() {
		return lastEditOrgId;
	}

	public void setLastEditOrgId(String lastEditOrgId) {
		this.lastEditOrgId = lastEditOrgId;
	}

}
