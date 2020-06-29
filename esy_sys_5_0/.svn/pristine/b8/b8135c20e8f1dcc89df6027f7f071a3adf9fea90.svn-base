package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.Base;

@Entity
@EntityInfo("角色成员信息")
@Table(name = "base_mrolemember", indexes = { @Index(name = "mrolemember_created", columnList = "created"),
		@Index(name = "mrolemember_updated", columnList = "updated"), @Index(name = "mrm_rid", columnList = "mrm_rid"),
		@Index(name = "mrm_aid", columnList = "mrm_aid") })
public class RoleMember extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("角色编号")
	@FilterInfo(ListValue = "eq")
	@Column(name = "mrm_rid", length = 50, nullable = false)
	private String roleId;

	@FieldInfo("账号编号")
	@FilterInfo(ListValue = "eq")
	@Column(name = "mrm_aid", length = 50, nullable = false)
	private String accountId;

	/**
	 * 
	 */
	public RoleMember() {
		super();
	}

	/**
	 * @param roleId
	 * @param accountId
	 */
	public RoleMember(String roleId, String accountId) {
		super();
		this.roleId = roleId;
		this.accountId = accountId;
	}

	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
