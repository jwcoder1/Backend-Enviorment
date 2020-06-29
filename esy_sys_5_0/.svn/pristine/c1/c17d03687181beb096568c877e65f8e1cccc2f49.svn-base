package org.esy.base.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.common.BaseUtil;
import org.esy.base.util.BeanMapUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
public class BaseProperties extends Base {

	private static final long serialVersionUID = 1L;

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

	public BaseProperties() {
		super();
	}

	public BaseProperties(String uid) {
		super();
	}

	/**
	 * 更新实体信息
	 */
	@Override
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
	@Override
	public void createEntity() {

		if (BaseUtil.isNotEmpty(BaseUtil.getUser())) {
			if (BaseUtil.isEmpty(getEid())) {
				setEid(BaseUtil.getUser().getEid());
			}
			this.setCreatePid(BaseUtil.toString(BeanMapUtils.beanToMap(BaseUtil.getUser()).get("aid")));
			this.setLastEditpid(BaseUtil.toString(BeanMapUtils.beanToMap(BaseUtil.getUser()).get("aid")));
		}

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
