package org.esy.base.dao;

import java.util.List;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.RoleMember;

public interface IRoleMemberDao extends IBaseDao<RoleMember> {

	public RoleMember getById(String roleId, String accountId);

	public List<Account> getDetail(String roleId);

	public boolean delDetail(String rid);

}
