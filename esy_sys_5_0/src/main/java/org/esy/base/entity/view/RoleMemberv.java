package org.esy.base.entity.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity
@EntityInfo("帐号管理")
@Table(name = "RoleMemberv")
@Subselect("select a.*,b.ma_name from base_mrolemember a left join base_maccount b on b.ma_aid=a.mrm_aid ")
@Synchronize("base_mrolemember")
public class RoleMemberv extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("角色编号")
	@FilterInfo(ListValue = "eq")
	@Column(name = "mrm_rid", length = 50, nullable = false)
	private String roleId;

	@FieldInfo("账号编号")
	@FilterInfo(ListValue = "eq")
	@Column(name = "mrm_aid", length = 50, nullable = false)
	private String accountId;

	@FieldInfo("名称")
	@FilterInfo(ListValue = "match", LovValue = "match")
	@Column(name = "ma_name", length = 32, nullable = false)
	private String name;

	public RoleMemberv() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
