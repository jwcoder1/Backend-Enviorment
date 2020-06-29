package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 
 * Entity for 群组成员
 *
 */
@Entity
@EntityInfo("群组成员")
@Table(name = "base_mgroupmember", indexes = { @Index(name = "mgm_gid", columnList = "mgm_gid"), @Index(name = "mgm_value", columnList = "mgm_value"), @Index(name = "mgm_value2", columnList = "mgm_value2"),
		@Index(name = "mgm_showid", columnList = "mgm_showid"), @Index(name = "mgm_showid2", columnList = "mgm_showid2") })
@DynamicUpdate
public class GroupMember extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("企业路径")
	@Column(name = "mgm_eid", length = 255, nullable = false)
	private String eid = "";

	@FieldInfo("群组编号")
	@Column(name = "mgm_gid", length = 32, nullable = false)
	private String gid = "";

	@FieldInfo("成员类型   A:account E:enterprise")
	@Column(name = "mgm_type", length = 2, nullable = false)
	private String type = "";

	@FieldInfo("成员类型")
	@Column(name = "mgm_value", length = 64, nullable = false)
	private String value = "";

	@FieldInfo("显示的id")
	@Column(name = "mgm_showid", length = 64, nullable = false)
	private String showid = "";

	@FieldInfo("显示的名称")
	@Column(name = "mgm_show", length = 64, nullable = false)
	private String show = "";

	@FieldInfo("成员类型   A:account E:enterprise")
	@Column(name = "mgm_type2", length = 2)
	private String type2 = "";

	@FieldInfo("成员类型")
	@Column(name = "mgm_value2", length = 64)
	private String value2 = "";

	@FieldInfo("显示的名称")
	@Column(name = "mgm_show2", length = 64)
	private String show2 = "";

	@FieldInfo("显示的id2")
	@Column(name = "mgm_showid2", length = 64)
	private String showid2 = "";

	/*
	 * @FieldInfo("成员组织路径")
	 * 
	 * @Column(name = "mgm_opath", length = 255, nullable = false) private
	 * String opath = "";
	 * 
	 * @FieldInfo("成员人员编号")
	 * 
	 * @Column(name = "mgm_pid", length = 32, nullable = false) private String
	 * pid = "";
	 * 
	 * @FieldInfo("成员职位编号")
	 * 
	 * @Column(name = "mgm_positionid", length = 64, nullable = false) private
	 * String positionId = "";
	 * 
	 * @FieldInfo("成员岗位编号")
	 * 
	 * @Column(name = "mgm_postid", length = 32, nullable = false) private
	 * String postId = "";
	 * 
	 * @FieldInfo("成员账号类型")
	 * 
	 * @Column(name = "mgm_accountType", length = 32, nullable = false) private
	 * String accountType = "";
	 * 
	 * @FieldInfo("成员账号")
	 * 
	 * @Column(name = "mgm_account", length = 32, nullable = false) private
	 * String account = "";
	 * 
	 * @FieldInfo("成员群组编号")
	 * 
	 * @Column(name = "mgm_groupid", length = 32, nullable = false) private
	 * String groupId = "";
	 */

	/**
	 *
	 * 构造函数
	 *
	 */
	public GroupMember() {
		super();
	}

	/**
	 *
	 * 构造函数
	 *
	 * @param eid
	 *            企业路径
	 * @param gid
	 *            群组编号
	 * @param type
	 *            成员类型
	 * @param value
	 *            值
	 */
	public GroupMember(String gid, String type, String value, String show, String showid, String showid2) {
		super();
		this.gid = gid;
		this.type = type;
		this.value = value;
		this.show = show;
		this.showid = showid;
		this.showid2 = showid2;
	}

	/**
	 * @return gid 群组编号
	 */
	public String getGid() {
		return gid;
	}

	/**
	 * @param gid
	 *            群组编号
	 */
	public void setGid(String gid) {
		this.gid = gid;
	}

	/**
	 * @return type 成员类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            成员类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getShow2() {
		return show2;
	}

	public void setShow2(String show2) {
		this.show2 = show2;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getShowid() {
		return showid;
	}

	public void setShowid(String showid) {
		this.showid = showid;
	}

	public String getShowid2() {
		return showid2;
	}

	public void setShowid2(String showid2) {
		this.showid2 = showid2;
	}

}
