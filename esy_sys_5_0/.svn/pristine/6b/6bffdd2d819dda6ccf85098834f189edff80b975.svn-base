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
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * Entity for 身份信息
 *
 */
@Entity
@EntityInfo("身份信息")
@Table(name = "base_midentity", indexes = { @Index(name = "midentity_eid", columnList = "mi_eid"),
		@Index(name = "midentity_oid", columnList = "mi_oid"), @Index(name = "midentity_pid", columnList = "mi_pid"),
		@Index(name = "midentity_positionid", columnList = "mi_positionid"),
		@Index(name = "mi_postid", columnList = "mi_postid") })
@SqlResultSetMapping(name = "Identity", entities = { @EntityResult(entityClass = Identity.class, fields = {
		@FieldResult(name = "uid", column = "uid"), @FieldResult(name = "created", column = "created"),
		@FieldResult(name = "updated", column = "updated"), @FieldResult(name = "eid", column = "eid"),
		@FieldResult(name = "pid", column = "pid"), @FieldResult(name = "oid", column = "oid"),
		@FieldResult(name = "positionId", column = "positionId"), @FieldResult(name = "postId", column = "postId"),
		@FieldResult(name = "enable", column = "enable"), @FieldResult(name = "type", column = "type"),
		@FieldResult(name = "isMain", column = "isMain"), @FieldResult(name = "seq", column = "seq"),
		@FieldResult(name = "startDate", column = "startDate"), @FieldResult(name = "toDate", column = "toDate"),
		@FieldResult(name = "enterpriseName", column = "enterpriseName"),
		@FieldResult(name = "oName", column = "oName"), @FieldResult(name = "positionName", column = "positionName"),
		@FieldResult(name = "postName", column = "postName") }) }, columns = { @ColumnResult(name = "enterpriseName"),
				@ColumnResult(name = "oName"), @ColumnResult(name = "positionName"), @ColumnResult(name = "postName") })
@DynamicUpdate
public class Identity extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("企业路径")
	@Column(name = "mi_eid", length = 255) // , nullable = false
	private String eid = "";

	@FieldInfo("人员编号")
	@FilterInfo(ListValue = "eq")
	@Column(name = "mi_pid", length = 32) // , nullable = false
	private String pid;

	@FieldInfo("组织编号")
	@FilterInfo(ListValue = "eq")
	@Column(name = "mi_oid", length = 32) // , nullable = false
	private String oid;

	@FieldInfo("职务编号")
	@FilterInfo(ListValue = "eq")
	@Column(name = "mi_positionid", length = 32)
	private String positionId;

	@FieldInfo("岗位编号")
	@FilterInfo(ListValue = "eq")
	@Column(name = "mi_postid", length = 32)
	private String postId;

	@FieldInfo("状态")
	@Column(name = "mi_enable") // , nullable = false
	private Boolean enable = false;

	@FieldInfo("类型  数据字段主表id:IDENTITYTYPE")
	@Column(name = "mi_type", length = 50) // , nullable = false
	private String type;

	@FieldInfo("是否主职")
	@Column(name = "mi_isMain") // , nullable = false
	private Boolean isMain = false;

	@FieldInfo("排序")
	@Column(name = "mi_seq")
	private Integer seq;

	@FieldInfo("开始日期")
	@Column(name = "mi_startdate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date startDate;

	@FieldInfo("结束日期")
	@Column(name = "mi_todate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date toDate = null;

	@FieldInfo("对应的组织路径")
	@Column(name = "mi_path", length = 255) // , nullable = false
	private String path = "";

	@Transient
	@JsonProperty("pName")
	private String pName;

	@Transient
	@JsonProperty("oName")
	private String oName;

	// 企业名称
	@Transient
	@JsonProperty("enterpriseName")
	private String enterpriseName;

	@Transient
	@JsonProperty("postName")
	private String postName;

	@Transient
	@JsonProperty("positionName")
	private String positionName;

	/**
	 *
	 * 构造函数
	 *
	 */
	public Identity() {
		super();
	}

	/**
	 *
	 * 构造函数
	 *
	 * @param oid
	 *            组织编号
	 * 
	 * @param pid
	 *            人员编号
	 * 
	 * @param positionId
	 *            职务编号
	 * 
	 * @param postId
	 *            岗位编号
	 * 
	 * @param enable
	 *            状态
	 * 
	 * @param type
	 *            类型
	 * 
	 * @param seq
	 *            排序
	 * 
	 * @param startDate
	 *            开始日期
	 * 
	 * @param toDate
	 *            结束日期
	 * 
	 */
	public Identity(String oid, String pid, String positionId, String postId, Boolean enable, String type, Integer seq,
			Date startDate, Date toDate) {
		super();
		this.oid = oid;
		this.pid = pid;
		this.positionId = positionId;
		this.postId = postId;
		this.enable = enable;
		this.type = type;
		this.seq = seq;
		this.startDate = startDate;
		this.toDate = toDate;
	}

	public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}

	/**
	 * @return oid 组织编号
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @param oid
	 *            组织编号
	 */
	public void setOid(String oid) {
		this.oid = oid;
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
	 * @return positionId 职务编号
	 */
	public String getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId
	 *            职务编号
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	/**
	 * @return postId 岗位编号
	 */
	public String getPostId() {
		return postId;
	}

	/**
	 * @param postId
	 *            岗位编号
	 */
	public void setPostId(String postId) {
		this.postId = postId;
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

	/**
	 * @return type 类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return seq 排序
	 */
	public Integer getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            排序
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * @return startDate 开始日期
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            开始日期
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return toDate 结束日期
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate
	 *            结束日期
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getoName() {
		return oName;
	}

	public void setoName(String oName) {
		this.oName = oName;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Identity(String uid, Date created, Date updated, String eid, String pid, String oid, String positionId,
			String postId, Boolean enable, String type, Boolean isMain, Integer seq, Date startDate, Date toDate,
			String enterpriseName, String oName, String positionName, String postName) {
		super();
		this.setUid(uid);
		this.setCreated(created);
		this.setUpdated(updated);
		this.eid = eid;
		this.enterpriseName = enterpriseName;
		this.pid = pid;
		this.oid = oid;
		this.oName = oName;
		this.positionId = positionId;
		this.positionName = positionName;
		this.postId = postId;
		this.postName = postName;
		this.enable = enable;
		this.type = type;
		this.isMain = isMain;
		this.seq = seq;
		this.startDate = startDate;
		this.toDate = toDate;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

}
