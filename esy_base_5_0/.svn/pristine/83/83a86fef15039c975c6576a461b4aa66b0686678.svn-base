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
@EntityInfo("权限表")
@Table(name = "base_mauthority", indexes = { @Index(name = "mauthority_eid", columnList = "ma_eid"), @Index(name = "mauthority_value", columnList = "mg_value"), @Index(name = "mauthority_value2", columnList = "mg_value2"),
		@Index(name = "mauthority_showid2", columnList = "mg_show2"), @Index(name = "mg_showid", columnList = "mg_showid") })
@DynamicUpdate
public class Authority extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("企业路径")
	@Column(name = "ma_eid", length = 255)
	private String eid;

	@FieldInfo("编号")
	@Column(name = "mg_aid", length = 32, nullable = false)
	private String aid;

	@FieldInfo("成员类型   A:account E:enterprise")
	@Column(name = "mg_type", length = 2, nullable = false)
	private String type;

	@FieldInfo("成员值")
	@Column(name = "mg_value", length = 32, nullable = false)
	private String value;

	@FieldInfo("显示的名称")
	@Column(name = "mg_show", length = 32, nullable = false)
	private String show;

	@FieldInfo("显示的id")
	@Column(name = "mg_showid", length = 64, nullable = false)
	private String showid;

	@FieldInfo("成员类型   A:account E:enterprise")
	@Column(name = "mg_type2", length = 2)
	private String type2;

	@FieldInfo("成员类型")
	@Column(name = "mg_value2", length = 64)
	private String value2;

	@FieldInfo("显示的名称")
	@Column(name = "mg_show2", length = 64)
	private String show2;

	@FieldInfo("显示的id2")
	@Column(name = "mg_showid2", length = 64)
	private String showid2;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getType() {
		return type;
	}

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

	/**
	 *
	 * 构造函数
	 *
	 */
	public Authority() {
		super();
	}

	/**
	 * 旭盈专用
	 * 
	 * @param aid
	 * @param type
	 * @param value
	 * @param show
	 */
	public Authority(String aid, String type, String value, String show) {
		super();
		this.aid = aid;
		this.type = type;
		this.value = value;
		this.show = show;
	}

	public Authority(String eid, String aid, String type, String value, String show, String showid, String type2, String value2, String show2, String showid2) {
		super();
		this.eid = eid;
		this.aid = aid;
		this.type = type;
		this.value = value;
		this.show = show;
		this.showid = showid;
		this.type2 = type2;
		this.value2 = value2;
		this.show2 = show2;
		this.showid2 = showid2;
	}

}
