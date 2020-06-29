package org.esy.base.dao;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.RoleMenu;

public interface IRoleMenuDao extends IBaseDao<RoleMenu> {

	public RoleMenu getById(String roleId, String menuId);

	public boolean delDetail(String rid);

}
