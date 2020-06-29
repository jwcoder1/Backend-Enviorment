/**
 * 
 */
package org.esy.base.entity.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */

@Entity
@EntityInfo("部门视图")
@Table(name = "Identityv")
@Subselect("select a.*,b.ma_name as aname,c.mr_name as postname,d.mr_name as positname,e.mr_name as oname from base_midentity a LEFT JOIN base_maccount b on b.ma_aid=a.mi_pid LEFT JOIN base_mrole c on c.mr_id=a.mi_postid left join base_mrole d on d.mr_id=a.mi_positionid LEFT JOIN base_mrole e on e.mr_id= a.mi_oid")
@Synchronize("base_midentity")
public class Identityv extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("企业路径")
	@Column(name = "mi_eid", length = 255) // , nullable = false
	private String eid = "eq";

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

	@FieldInfo("人员姓名")
	@Column(name = "aname")
	private String aname;

	@FieldInfo("岗位")
	@Column(name = "postname")
	private String postname;

	@FieldInfo("职位 ")
	@Column(name = "positname")
	private String positname;

	@FieldInfo("部门 ")
	@Column(name = "oname")
	private String oname;

	public Identityv() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getPostname() {
		return postname;
	}

	public void setPostname(String postname) {
		this.postname = postname;
	}

	public String getPositname() {
		return positname;
	}

	public void setPositname(String positname) {
		this.positname = positname;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

}
