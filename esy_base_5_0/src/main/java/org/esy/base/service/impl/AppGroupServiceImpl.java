package org.esy.base.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IAppGroupDao;
import org.esy.base.dao.IPositionDao;
import org.esy.base.entity.AppGroup;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.service.IAppGroupMemberService;
import org.esy.base.service.IAppGroupService;
import org.esy.base.service.ISerialService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Service implement for 企业信息
 *
 */
@Repository
public class AppGroupServiceImpl implements IAppGroupService {

	@Autowired
	IAppGroupDao appGroupDao;

	@Autowired
	IPositionDao positionDao;

	@Autowired
	private IAppGroupMemberService appGroupMemberService;

	@Autowired
	private ISerialService serialService;

	@Override
	@Transactional
	public AppGroup save(AppGroup o) {
		return appGroupDao.save(o);
	}

	@Transactional
	@Override
	public AppGroup save(AppGroup o, AppGroup old, HttpServletRequest request) {
		if (o.checkNew()) {
			String tempNo = serialService.getSerialString("base", "AppGroup", "", 8, 99999999l);
			o.setAgid(tempNo);
			if (YESUtil.isEmpty(o.getEid()))
				o.setEid(YESUtil.toString(BaseUtil.getUser().getEid()));
			Integer seq = appGroupDao.getMaxSeq(o.getEid(), o.getPid());
			o.setSeq(++seq);
		} else {
			o.setSeq(old.getSeq());
		}

		if (o.getPid() == null) {
			o.setPid("");
		}
		o = appGroupDao.save(o);
		// if(YESUtil.isNotEmpty(old)){
		// if(!old.getCname().equals(o.getCname())){
		// groupMemberService.updateShowByValueAndType(GroupMember.TYPE_ENTERPRISE,
		// o.getEid(), o.getCname());
		// authorityService.updateShowByValueAndType(Authority.TYPE_ENTERPRISE,
		// o.getEid(), o.getCname());
		// }
		// }
		return o;
	}

	@Override
	public AppGroup getByUid(String uid) {
		return appGroupDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(AppGroup o) {
		appGroupMemberService.deleteByAgid(o.getAgid());
		return appGroupDao.delete(o);
	}

	@Override
	public Long getChildrenCount(String agid) {
		return appGroupDao.getChildrenCount(agid);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		if (YESUtil.isEmpty(parm.get("eid"))) {
			parm.put("eid", BaseUtil.getUser().getEid());
		}
		return appGroupDao.query(parm);
	}

	@Override
	@Transactional
	public MsgResult changeLocation(Map<String, Object> parm, String classname) {
		MsgResult mr = new MsgResult(false, "");
		if (!parm.containsKey("begin")) {
			mr.setMsg("没有起始位置");
			return mr;
		}
		if (!parm.containsKey("end")) {
			mr.setMsg("没有结束位置");
			return mr;
		}

		String b = YESUtil.toString(parm.get("begin"));
		String e = YESUtil.toString(parm.get("end"));

		int begin = positionDao.getSeqFromObject(classname, b);
		int end = positionDao.getSeqFromObject(classname, e);

		if (begin == end) {
			mr.setMsg("交换位置排序号一致");
			return mr;
		}
		parm.remove("begin");
		parm.remove("end");

		// 获得uid
		String uid = positionDao.getByEidAndSeq(parm, classname, begin);
		// 区间改变
		if (!positionDao.changeLocation(parm, classname, begin, end)) {
			mr.setMsg("交换数据错误！");
			return mr;
		}
		// 改变uid
		if (!positionDao.updateSeq(classname, uid, end)) {
			mr.setMsg("更新本节点数据错误！");
			return mr;
		}
		mr.setSuccess(true);
		return mr;
	}
}
