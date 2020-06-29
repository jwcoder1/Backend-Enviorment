package org.esy.base.entity.dto;

import java.io.Serializable;

import org.esy.base.entity.Menu;

public class MenuDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String uid;

	private String name;

	private String tip;

	private String color;

	private String tag;

	private String icon;

	private int order;

	private String url;

	private boolean state;

	private String parent;

	private String type;

	private boolean home;

	private boolean blank;

	private boolean expanded;

	public MenuDto() {
		super();
	}

	/**
	 * @param uid
	 * @param name
	 * @param tip
	 * @param color
	 * @param tag
	 * @param icon
	 * @param order
	 * @param url
	 * @param state
	 * @param parent
	 * @param type
	 * @param home
	 * @param blank
	 * @param expanded
	 */
	public MenuDto(String uid, String name, String tip, String color, String tag, String icon, int order, String url,
			boolean state, String parent, String type, boolean home, boolean blank, boolean expanded) {
		super();
		this.uid = uid;
		this.name = name;
		this.tip = tip;
		this.color = color;
		this.tag = tag;
		this.icon = icon;
		this.order = order;
		this.url = url;
		this.state = state;
		this.parent = parent;
		this.type = type;
		this.home = home;
		this.blank = blank;
		this.expanded = expanded;
	}

	/**
	 * @param menu
	 */
	public MenuDto(Menu menu) {
		super();
		this.uid = menu.getMid();
		this.name = menu.getName();
		this.tip = menu.getTip();
		this.color = menu.getColor();
		this.tag = menu.getTag();
		this.icon = menu.getIcon();
		this.order = menu.getOrder();
		this.url = menu.getUrl();
		this.state = menu.isEnable();
		this.parent = menu.getPid();
		this.type = menu.getType();
		this.home = menu.isHome();
		this.blank = menu.isBlank();
		this.expanded = menu.isExpanded();
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the tip
	 */
	public String getTip() {
		return tip;
	}

	/**
	 * @param tip the tip to set
	 */
	public void setTip(String tip) {
		this.tip = tip;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the state
	 */
	public boolean isState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(boolean state) {
		this.state = state;
	}

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the home
	 */
	public boolean isHome() {
		return home;
	}

	/**
	 * @param home the home to set
	 */
	public void setHome(boolean home) {
		this.home = home;
	}

	/**
	 * @return the blank
	 */
	public boolean isBlank() {
		return blank;
	}

	/**
	 * @param blank the blank to set
	 */
	public void setBlank(boolean blank) {
		this.blank = blank;
	}

	/**
	 * @return the expanded
	 */
	public boolean isExpanded() {
		return expanded;
	}

	/**
	 * @param expanded the expanded to set
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

}
