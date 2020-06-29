package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.Base;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityInfo("系统菜单")
@Table(name = "base_mmenu", indexes = { @Index(name = "rlog_created", columnList = "created"),
		@Index(name = "rlog_updated", columnList = "updated"), @Index(name = "mm_id", columnList = "mm_id"),
		@Index(name = "mm_pid", columnList = "mm_pid") })
public class Menu extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo(value = "编号")
	@FilterInfo(ListValue = "eq,matchl", FieldsValue = "mid,mida")
	@Column(name = "mm_id", length = 32, nullable = false)
	private String mid;

	@FieldInfo(value = "名称")
	@FilterInfo(ListValue = "match")
	@Column(name = "mm_name", length = 100, nullable = false)
	private String name;

	@FieldInfo(value = "已启用")
	@Column(name = "mm_enable")
	private boolean enable;

	@FieldInfo(value = "类别")
	@FilterInfo(ListValue = "eq")
	@Column(name = "mm_type", length = 50, nullable = false)
	private String type;

	@FieldInfo(value = "顺序号")
	@Column(name = "mm_order", nullable = false)
	private int order;

	@FieldInfo(value = "图标")
	@Column(name = "mm_icon", length = 100)
	private String icon;

	@FieldInfo(value = "提示")
	@Column(name = "mm_tip", length = 100)
	private String tip;

	@FieldInfo(value = "上级编号")
	@FilterInfo(ListValue = "eq,matchl", FieldsValue = "pid,pida")
	@Column(name = "mm_pid", length = 100)
	private String pid;

	@FieldInfo(value = "URL 地址")
	@Column(name = "mm_url", length = 255)
	private String url;

	@FieldInfo(value = "备注")
	@Column(name = "mm_memo", length = 255)
	private String memo;

	@FieldInfo(value = "首页链接")
	@Column(name = "mm_home")
	private boolean home;

	@FieldInfo(value = "新窗口开启")
	@Column(name = "mm_blank")
	private boolean blank;

	@FieldInfo(value = "自动展开")
	@Column(name = "mm_expanded")
	private boolean expanded;

	@FieldInfo(value = "颜色")
	@Column(name = "mm_color", length = 50)
	private String color;

	@FieldInfo(value = "标签")
	@Column(name = "mm_tag", length = 50)
	private String tag;

	@Transient
	@JsonProperty("isdel")
	private Boolean isdel = false;

	@Transient
	@JsonProperty("active")
	private Boolean active = false;

	@Transient
	@JsonProperty("hasnextlevel")
	private Boolean hasnextlevel = false;

	@Transient
	@JsonProperty("collapsed")
	private Boolean collapsed = false;

	@Transient
	@JsonProperty("pida")
	private String pida;

	@Transient
	@JsonProperty("mida")
	private String mida;

	public Boolean getHasnextlevel() {
		return hasnextlevel;
	}

	public void setHasnextlevel(Boolean hasnextlevel) {
		this.hasnextlevel = hasnextlevel;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * 
	 */
	public Menu() {
		super();
	}

	/**
	 * @param mid
	 * @param name
	 * @param enable
	 * @param type
	 * @param order
	 * @param icon
	 * @param tip
	 * @param pid
	 * @param url
	 * @param memo
	 * @param home
	 * @param blank
	 * @param expanded
	 * @param color
	 * @param tag
	 */
	public Menu(String mid, String name, boolean enable, String type, int order, String icon, String tip, String pid,
			String url, String memo, boolean home, boolean blank, boolean expanded, String color, String tag) {
		super();
		this.mid = mid;
		this.name = name;
		this.enable = enable;
		this.type = type;
		this.order = order;
		this.icon = icon;
		this.tip = tip;
		this.pid = pid;
		this.url = url;
		this.memo = memo;
		this.home = home;
		this.blank = blank;
		this.expanded = expanded;
		this.color = color;
		this.tag = tag;
	}

	public Menu(String pid, String mid) {
		this.pid = pid;
		this.mid = mid;
	}

	public Menu(String pid) {
		this.setFilterfields("pid");
		this.pid = pid;
	}

	/**
	 * @return the mid
	 */
	public final String getMid() {
		return mid;
	}

	/**
	 * @param mid the mid to set
	 */
	public final void setMid(String mid) {
		this.mid = mid;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the enable
	 */
	public final boolean isEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public final void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * @return the type
	 */
	public final String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the order
	 */
	public final int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public final void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the icon
	 */
	public final String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public final void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the tip
	 */
	public final String getTip() {
		return tip;
	}

	/**
	 * @param tip the tip to set
	 */
	public final void setTip(String tip) {
		this.tip = tip;
	}

	/**
	 * @return the pid
	 */
	public final String getPid() {
		return pid;
	}

	/**
	 * @param pid the pid to set
	 */
	public final void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public final void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the memo
	 */
	public final String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public final void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the home
	 */
	public final boolean isHome() {
		return home;
	}

	/**
	 * @param home the home to set
	 */
	public final void setHome(boolean home) {
		this.home = home;
	}

	/**
	 * @return the blank
	 */
	public final boolean isBlank() {
		return blank;
	}

	/**
	 * @param blank the blank to set
	 */
	public final void setBlank(boolean blank) {
		this.blank = blank;
	}

	/**
	 * @return the expanded
	 */
	public final boolean isExpanded() {
		return expanded;
	}

	/**
	 * @param expanded the expanded to set
	 */
	public final void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	/**
	 * @return the color
	 */
	public final String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public final void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the tag
	 */
	public final String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public final void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the isdel
	 */
	public final Boolean getIsdel() {
		return isdel;
	}

	/**
	 * @param isdel the isdel to set
	 */
	public final void setIsdel(Boolean isdel) {
		this.isdel = isdel;
	}

	public Boolean getCollapsed() {
		return collapsed;
	}

	public void setCollapsed(Boolean collapsed) {
		this.collapsed = collapsed;
	}

	public String getPida() {
		return pida;
	}

	public void setPida(String pida) {
		this.pida = pida;
	}

	public String getMida() {
		return mida;
	}

	public void setMida(String mida) {
		this.mida = mida;
	}

}
