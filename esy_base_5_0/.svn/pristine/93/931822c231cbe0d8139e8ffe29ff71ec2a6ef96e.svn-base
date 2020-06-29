package org.esy.base.service.impl;

import javax.transaction.Transactional;

import org.esy.base.dao.IRoleMemberDao;
import org.esy.base.dao.YSDao;
import org.esy.base.dao.core.PageResult;
import org.esy.base.entity.RoleMember;
import org.esy.base.entity.view.RoleMemberv;
import org.esy.base.service.IRoleMemberService;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleMemberServiceImpl implements IRoleMemberService {

	@Autowired
	IRoleMemberDao roleMemberDao;

	@Autowired
	private YSDao dao;

	@Override
	@Transactional
	public boolean save(RoleMember rm) {
		roleMemberDao.delDetail(rm.getRoleId());
		if (YESUtil.isNotEmpty(rm.getAccountId())) {
			String[] aids = rm.getAccountId().split(",");
			for (String aid : aids) {
				RoleMember r = new RoleMember();
				r.setAccountId(aid);
				r.setRoleId(rm.getRoleId());
				roleMemberDao.save(r);
			}
		}
		return true;
	}

	@Override
	public PageResult<RoleMemberv> query(RoleMemberv roleMember, Pageable pageable) {
		return (PageResult<RoleMemberv>) dao.query(RoleMemberv.class, roleMember, pageable);
	}

}
