package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;

@Entity
@EntityInfo("菜单角色授权表")
@Table(name = "base_mrolemenu", indexes = { @Index(name = "mrolemenu_created", columnList = "created"),
		@Index(name = "mrolemenu_updated", columnList = "updated"), @Index(name = "mmr_mid", columnList = "mmr_mid"),
		@Index(name = "mmr_rid", columnList = "mmr_rid") })
public class RoleMenu extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo(value = "菜单编号")
	@Column(name = "mmr_mid", length = 50, nullable = false)
	private String menuId;

	@FieldInfo(value = "角色编号")
	@Column(name = "mmr_rid", length = 50, nullable = false)
	private String roleId;

	@FieldInfo(value = "状态")
	@Column(name = "mmr_enable", nullable = false)
	private boolean enable;

	/**
	 * 
	 */
	public RoleMenu() {
		super();
	}

	/**
	 * @param menuId
	 * @param roleId
	 * @param enable
	 */
	public RoleMenu(String menuId, String roleId, boolean enable) {
		super();
		this.menuId = menuId;
		this.roleId = roleId;
		this.enable = enable;
	}

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the enable
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
