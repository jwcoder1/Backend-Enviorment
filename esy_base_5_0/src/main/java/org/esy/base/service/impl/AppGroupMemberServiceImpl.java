package org.esy.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IAppGroupMemberDao;
import org.esy.base.dao.IApplicationDao;
import org.esy.base.dao.IPositionDao;
import org.esy.base.entity.AppGroupMember;
import org.esy.base.entity.Application;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.service.IAppGroupMemberService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Service implement for 群组成员
 *
 */
@Repository
public class AppGroupMemberServiceImpl implements IAppGroupMemberService {

	@Autowired
	IAppGroupMemberDao appGroupMemberDao;

	@Autowired
	IPositionDao positionDao;

	@Autowired
	private IApplicationDao applicationDao;

	@Override
	public boolean deleteByAgid(String agid) {
		return appGroupMemberDao.deleteByAgid(agid);
	}

	@Override
	public boolean deleteByAid(String aid) {
		return appGroupMemberDao.deleteByAid(aid);
	}

	@Override
	public List<AppGroupMember> getAppGroupMemberByAppids(List<String> appids) {
		return appGroupMemberDao.getAppGroupMemberByAppids(appids);
	}

	@Override
	public int updateShowByValue(String value, String newShow) {
		return appGroupMemberDao.updateShowByValue(value, newShow);
	}

	@Override
	public int deleteByValuesAndType(String values, String type) {
		return appGroupMemberDao.deleteByValuesAndType(values.split(","), type);
	}

	@Transactional
	@Override
	public AppGroupMember save(AppGroupMember o) {
		String[] values = o.getAppid().split(",");
		String[] shows = o.getShow().split(",");
		Map<String, AppGroupMember> temp = new HashMap<String, AppGroupMember>();
		for (int i = 0; i < values.length; i++) {
			String val = values[i];
			AppGroupMember gm = new AppGroupMember();
			gm.setAgid(o.getAgid());
			gm.setAppid(val);
			gm.setShow(shows[i]);
			temp.put(val, gm);
		}
		List<AppGroupMember> gms = appGroupMemberDao.getAppGroupMemberByAppidsAndAgid(values, o.getAgid());
		for (AppGroupMember gm : gms) {
			temp.remove(gm.getAppid());
		}
		Set<String> keys = temp.keySet();
		for (String key : keys) {
			appGroupMemberDao.save(temp.get(key));
		}
		return o;
	}

	@Override
	public AppGroupMember getByUid(String uid) {
		return appGroupMemberDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(AppGroupMember o) {
		return appGroupMemberDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		if (YESUtil.isEmpty(parm.get("eid"))) {
			parm.put("eid", BaseUtil.getUser().getEid());
		}
		return appGroupMemberDao.query(parm);
	}

	@Transactional
	@Override
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

		Application appBein = applicationDao.getByUid(b);
		Application appEnd = applicationDao.getByUid(e);

		String agid = YESUtil.toString(parm.get("agid"));
		String eid = YESUtil.toString(parm.get("eid"));
		int begin = appGroupMemberDao.getSeqByAppidAndEidAndAgid(appBein.getAid(), agid, eid);
		int end = appGroupMemberDao.getSeqByAppidAndEidAndAgid(appEnd.getAid(), agid, eid);

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
