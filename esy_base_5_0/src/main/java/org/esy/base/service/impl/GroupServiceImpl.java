package org.esy.base.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IGroupDao;
import org.esy.base.entity.Group;
import org.esy.base.enums.MemberType;
import org.esy.base.service.IAppAuthorityService;
import org.esy.base.service.IAuthorityService;
import org.esy.base.service.IGroupMemberService;
import org.esy.base.service.IGroupService;
import org.esy.base.service.ISerialService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Service implement for 群组信息
 *
 */
@Repository
public class GroupServiceImpl implements IGroupService {

	@Autowired
	IGroupDao groupDao;

	@Autowired
	private ISerialService serialService;

	@Autowired
	private IGroupMemberService groupMemberService;

	@Autowired
	private IAuthorityService authorityService;

	@Autowired
	private IAppAuthorityService appAuthorityService;

	@Transactional
	@Override
	public Group save(Group o) {
		return groupDao.save(o);
	}

	@Transactional
	@Override
	public Group save(Group o, Group old, HttpServletRequest request) {
		if (o.checkNew()) {
			String tempNo = serialService.getSerialString("base", "Group", "", 8, 99999999l);
			o.setGid(tempNo);
			if (YESUtil.isEmpty(o.getEid()))
				o.setEid(YESUtil.toString(BaseUtil.getUser().getEid()));
		}
		o = groupDao.save(o);
		if (YESUtil.isNotEmpty(old)) {
			if (!old.getName().equals(o.getName())) {
				appAuthorityService.updateShowByValueAndType(MemberType.Group.toString(), o.getGid(), o.getName());
				authorityService.updateShowByValueAndType(MemberType.Group.toString(), o.getGid(), o.getName());
			}
		}
		return o;
	}

	@Override
	public Group getByUid(String uid) {
		return groupDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(Group o) {
		appAuthorityService.deleteByValuesAndType(o.getGid(), MemberType.Group.toString());
		authorityService.deleteByValuesAndType(o.getGid(), MemberType.Group.toString());
		groupMemberService.deleteByGid(o.getGid());
		return groupDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		// Account a = YESUtil.getUser();
		// if (!"admin".equals(a.getType())) {
		// parm.put("eid", YESUtil.toString(a.getEid()));
		// }
		String eid = BaseUtil.getUser().getEid();
		if (!parm.containsKey("eid")) {
			parm.put("eid", eid);
		}
		if (YESUtil.isEmpty(YESUtil.toString(parm.get("eid")))) {
			parm.put("eid", eid);
		}
		return groupDao.query(parm);
	}
}
