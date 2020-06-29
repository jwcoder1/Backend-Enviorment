package org.esy.base.service.impl;

import javax.transaction.Transactional;

import org.esy.base.dao.IRoleMenuDao;
import org.esy.base.entity.RoleMenu;
import org.esy.base.service.IRoleMenuService;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleMenuServiceImpl implements IRoleMenuService {

	@Autowired
	IRoleMenuDao roleMenuDao;

	@Override
	@Transactional
	public boolean save(RoleMenu rm) {
		roleMenuDao.delDetail(rm.getRoleId());
		if (YESUtil.isNotEmpty(rm.getMenuId())) {
			String[] mids = rm.getMenuId().split(",");
			for (String mid : mids) {
				RoleMenu r = new RoleMenu();
				r.setMenuId(mid);
				r.setEnable(true);
				r.setRoleId(rm.getRoleId());
				roleMenuDao.save(r);
			}
		}
		return true;
	}

}
