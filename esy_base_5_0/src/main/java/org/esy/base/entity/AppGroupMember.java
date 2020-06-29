package org.esy.base.entity;

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
@EntityInfo("应用分组关联表")
@Table(name = "base_mappgroupmember", indexes = { @Index(name = "mgm_agid", columnList = "mgm_agid"), @Index(name = "mgm_appid", columnList = "mgm_appid") })
@DynamicUpdate
public class AppGroupMember extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("企业路径")
	@Column(name = "mgm_eid", length = 255)
	private String eid;

	@FieldInfo("关联 - AppGroup_agid")
	@Column(name = "mgm_agid", length = 32, nullable = false)
	private String agid;

	@FieldInfo("关联 - application表的aid")
	@Column(name = "mgm_appid", length = 32, nullable = false)
	private String appid;

	@FieldInfo("用于应用显示的名称")
	@Column(name = "mgm_show", length = 32, nullable = false)
	private String show;

	@FieldInfo("排序号")
	@Column(name = "mgm_seq")
	private Integer seq;

	@Transient
	private Application application;

	public String getEid() {
		return eid;
	}

	public AppGroupMember(String uid, String eid, String agid, String appid, String show, Integer seq) {
		this.setUid(uid);
		this.eid = eid;
		this.agid = agid;
		this.appid = appid;
		this.show = show;
		this.seq = seq;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public AppGroupMember() {
		super();
	}

	public String getAgid() {
		return agid;
	}

	public void setAgid(String agid) {
		this.agid = agid;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
