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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * Entity for 组织信息
 *
 */
@Entity
@EntityInfo("组织信息")
@Table(name = "base_morganization", indexes = { @Index(name = "mo_eid", columnList = "mo_eid"),
		@Index(name = "mo_oid", columnList = "mo_oid"), @Index(name = "mo_showid", columnList = "mo_showid"),
		@Index(name = "mo_py", columnList = "mo_py") })
@DynamicUpdate
public class Organization extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("企业路径")
	@Column(name = "mo_eid", length = 255, nullable = false)
	private String eid = "";

	@FieldInfo("自动编号")
	@Column(name = "mo_oid", length = 32, nullable = false)
	private String oid;

	@FieldInfo("显示编号")
	@Column(name = "mo_showid", length = 32, nullable = false)
	private String showid = "";

	@FieldInfo("名称")
	@Column(name = "mo_name", length = 64, nullable = false)
	private String name;

	@FieldInfo("简称")
	@Column(name = "mo_abbreviated", length = 64)
	private String abbreviated;

	@FieldInfo("分组组织")
	@Column(name = "mo_isgroup")
	private Boolean isGroup = false;

	@FieldInfo("简拼")
	@Column(name = "mo_py", length = 64)
	private String py;

	@FieldInfo("状态")
	@Column(name = "mo_enable", nullable = false)
	private Boolean enable = false;

	@FieldInfo("备注")
	@Column(name = "mpo_memo", length = 255)
	private String memo;

	// 用来对比用的pid,中烟webservice数据迁移时带过来的
	@FieldInfo("临时pid")
	@Column(name = "mo_temppid", length = 32)
	private String temppid;

	// 用来对比用的id，数据迁移时带过来的
	@FieldInfo("临时id")
	@Column(name = "mo_tmpid", length = 32)
	private String tmpId = "";

	// 上级编号
	@Transient
	@JsonProperty("pid")
	private String pid = "";

	// 类别
	@Transient
	@JsonProperty("type")
	private String type;

	// 排序
	@Transient
	@JsonProperty("seq")
	private Integer seq;

	// 组织路径
	@Transient
	@JsonProperty("path")
	private String path;

	// 关联relation的uid
	@Transient
	@JsonProperty("buid")
	private String buid;

	// 父组织的uid
	@Transient
	@JsonProperty("puid")
	private String puid;

	// 父组织的path
	@Transient
	@JsonProperty("ppath")
	private String ppath;

	@Transient
	@JsonProperty("level")
	private Integer level;

	@Transient
	private String sysid = "";

	public Organization() {
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getShowid() {
		return showid;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviated() {
		return abbreviated;
	}

	public void setAbbreviated(String abbreviated) {
		this.abbreviated = abbreviated;
	}

	public Boolean getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(Boolean isGroup) {
		this.isGroup = isGroup;
	}

	public String getPy() {
		return py;
	}

	public void setPy(String py) {
		this.py = py;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTemppid() {
		return temppid;
	}

	public void setTemppid(String temppid) {
		this.temppid = temppid;
	}

	public Organization(String eid, String oid, String showid, String name, String abbreviated, Boolean isGroup,
			String py, Boolean enable, String memo, String temppid, String tmpId, String pid, String type, Integer seq,
			String path, String buid, String puid, String ppath, Integer level, String sysid) {
		super();
		this.eid = eid;
		this.oid = oid;
		this.showid = showid;
		this.name = name;
		this.abbreviated = abbreviated;
		this.isGroup = isGroup;
		this.py = py;
		this.enable = enable;
		this.memo = memo;
		this.temppid = temppid;
		this.tmpId = tmpId;
		this.pid = pid;
		this.type = type;
		this.seq = seq;
		this.path = path;
		this.buid = buid;
		this.puid = puid;
		this.ppath = ppath;
		this.level = level;
		this.sysid = sysid;
	}

	public Organization(String eid, String oid, String showid, String name, String abbreviated, Boolean isGroup,
			String py, Boolean enable, String memo) {
		super();
		this.eid = eid;
		this.oid = oid;
		this.showid = showid;
		this.name = name;
		this.abbreviated = abbreviated;
		this.isGroup = isGroup;
		this.py = py;
		this.enable = enable;
		this.memo = memo;
	}

	public Organization(String uid, String eid, String oid, String showid, String name, String abbreviated,
			Boolean isGroup, String py, Boolean enable, String memo, String pid, String type, Integer seq,
			String path) {
		super();
		this.setUid(uid);
		this.eid = eid;
		this.oid = oid;
		this.showid = showid;
		this.name = name;
		this.abbreviated = abbreviated;
		this.isGroup = isGroup;
		this.py = py;
		this.enable = enable;
		this.memo = memo;
		this.pid = pid;
		this.type = type;
		this.seq = seq;
		this.path = path;
	}

	public Organization(String uid, String eid, String oid, String showid, String name, String abbreviated,
			Boolean isGroup, String py, Boolean enable, String memo, String pid, String type, Integer seq, String path,
			String buid, Integer level) {
		super();
		this.setUid(uid);
		this.eid = eid;
		this.oid = oid;
		this.showid = showid;
		this.name = name;
		this.abbreviated = abbreviated;
		this.isGroup = isGroup;
		this.py = py;
		this.enable = enable;
		this.memo = memo;
		this.pid = pid;
		this.type = type;
		this.seq = seq;
		this.path = path;
		this.buid = buid;
		this.level = level;
	}

	public String getBuid() {
		return buid;
	}

	public void setBuid(String buid) {
		this.buid = buid;
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public String getPpath() {
		return ppath;
	}

	public void setPpath(String ppath) {
		this.ppath = ppath;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getTmpId() {
		return tmpId;
	}

	public void setTmpId(String tmpId) {
		this.tmpId = tmpId;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

}
