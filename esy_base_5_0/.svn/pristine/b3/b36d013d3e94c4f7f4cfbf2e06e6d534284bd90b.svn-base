package org.esy.base.service.impl;

import java.util.List;
import java.util.Map;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IPositionDao;
import org.esy.base.entity.Position;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.service.IIdentityService;
import org.esy.base.service.IPositionService;
import org.esy.base.service.ISerialService;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Service implement for 职务信息
 *
 */
@Repository
public class PositionServiceImpl implements IPositionService {

	@Autowired
	IPositionDao positionDao;

	@SuppressWarnings("unused")
	@Autowired
	private ISerialService serialService;

	@Autowired
	private IIdentityService identityService;

	@Transactional
	@Override
	public Position save(Position o) {
		if (o.checkNew()) {
			Integer seq = positionDao.getMaxSeq(o.getEid(), o.getOid());
			o.setSeq(++seq);
			o.setShowid(o.getPid());
		}
		o.setPy(YESUtil.ChineseToPinyinHeader(o.getName(), true)); // 拼音简码
		return positionDao.save(o);
	}

	@Override
	public Position getByUid(String uid) {
		return positionDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(Position o) {
		return positionDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		QueryResult qr = positionDao.query(parm);
		return qr;
	}

	@Override
	public List<Position> listByOid(String oid) {
		return positionDao.listByOid(oid);
	}

	@Override
	public MsgResult checkForSave(Position p) {
		MsgResult result = new MsgResult(false, "职务名称重复");

		// 同级不可有重名
		String uid = p.getUid();
		String name = p.getName();
		String eid = BaseUtil.toString(p.getEid());
		String pid = BaseUtil.toString(p.getPid());

		// 检查名称是否重复
		long lb = positionDao.countByNameAndOid(null, name, null, uid, eid);
		if (lb > 0)
			return result;

		// 检查编号是否重复
		lb = positionDao.countByNameAndOid(null, null, pid, uid, eid);
		if (lb > 0) {
			result.setMsg("职务编号重复");
			return result;
		}

		result.setSuccess(true);
		result.setMsg("");
		return result;
	}

	@Override
	public MsgResult checkDeleteByUid(String uid) {
		MsgResult result = new MsgResult(false, "检查参数错误");
		// 1.节点下有职务，且职务被人使用 1.节点下所有职务，2.身份信息用到这个职务
		Position p = positionDao.getByUid(uid);
		if (BaseUtil.isEmpty(p))
			return result;
		String positionId = p.getPid();

		// 有身份
		if (identityService.hadInConditions(null, positionId, null)) {
			result.setMsg("本职务下有人员使用了身份，请先删除人员身份!");
			return result;
		}

		result.setSuccess(true);
		return result;
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

		int begin = positionDao.getSeqFromObject(classname, b); // 开始seq
		int end = positionDao.getSeqFromObject(classname, e); // 结束结束seq

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

	@Override
	public List<Position> listByOids(List<String> oids, String rootpid, Boolean enable) {
		return positionDao.listByOids(oids, rootpid, enable);
	}
}
