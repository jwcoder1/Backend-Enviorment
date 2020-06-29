package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@EntityInfo("应用权限")
@Table(name = "base_mappauthority", indexes = { @Index(name = "mappauthority_aid", columnList = "ma_aid"), @Index(name = "mappauthority_value", columnList = "ma_value"),
		@Index(name = "mappauthority_value2", columnList = "ma_value2"), @Index(name = "ma_showid", columnList = "ma_showid"), @Index(name = "ma_showid2", columnList = "ma_showid2") })
@DynamicUpdate
public class AppAuthority extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("企业路径")
	@Column(name = "ma_eid", length = 255)
	private String eid;

	@FieldInfo("应用编号")
	@Column(name = "ma_aid", length = 10, nullable = false)
	private String aid;

	@FieldInfo("成员类型  详细见上面 'TYPE_' 开头的变量")
	@Column(name = "ma_type", length = 2, nullable = false)
	private String type;

	@FieldInfo("成员值")
	@Column(name = "ma_value", length = 32, nullable = false)
	private String value;

	@FieldInfo("显示的名称")
	@Column(name = "ma_show", length = 255, nullable = false)
	private String show;

	@FieldInfo("显示的id")
	@Column(name = "ma_showid", length = 64, nullable = false)
	private String showid;

	@FieldInfo("成员类型   A:account E:enterprise")
	@Column(name = "ma_type2", length = 2)
	private String type2;

	@FieldInfo("成员类型")
	@Column(name = "ma_value2", length = 64)
	private String value2;

	@FieldInfo("显示的名称")
	@Column(name = "ma_show2", length = 64)
	private String show2;

	@FieldInfo("显示的id")
	@Column(name = "ma_showid2", length = 64)
	private String showid2;

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

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
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

	public AppAuthority() {
		super();
	}

	public AppAuthority(String eid, String aid, String type, String value, String show, String showid, String type2, String value2, String show2, String showid2) {
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
