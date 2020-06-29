package org.esy.base.service.impl;

import org.esy.base.dao.YSDao;
import org.esy.base.dao.core.PageResult;
import org.esy.base.entity.Role;
import org.esy.base.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private YSDao dao;

	/**
	 * 查询实体
	 * 
	 * @param Map<String,
	 *            Object> parm
	 * @return QueryResult @ version v2.0
	 */
	@Override
	public PageResult<Role> query(Role role, Pageable pageable) {
		return (PageResult<Role>) dao.query(Role.class, role, pageable);
	}

}