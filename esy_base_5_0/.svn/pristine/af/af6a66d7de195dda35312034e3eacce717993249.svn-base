package org.esy.base.service.impl;

import java.util.List;
import java.util.Map;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IPositionDao;
import org.esy.base.dao.IPostDao;
import org.esy.base.entity.Post;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.service.IIdentityService;
import org.esy.base.service.IPostService;
import org.esy.base.service.ISerialService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Service implement for 岗位信息
 *
 */
@Repository
public class PostServiceImpl implements IPostService {

	@Autowired
	IPostDao postDao;

	@SuppressWarnings("unused")
	@Autowired
	private ISerialService serialService;

	@Autowired
	IPositionDao positionDao;

	@Autowired
	private IIdentityService identityService;

	@Transactional
	@Override
	public Post save(Post o) {
		if (o.checkNew()) {
			Integer seq = postDao.getMaxSeq(o.getEid(), o.getOid());
			o.setSeq(++seq);
			// String no = serialService.getSerialString("base", "Post", "", 8,
			// 99999999l);
			o.setShowid(o.getPid());
		}
		o.setPy(YESUtil.ChineseToPinyinHeader(o.getName(), true)); // 拼音简码
		return postDao.save(o);
	}

	@Override
	public Post getByUid(String uid) {
		return postDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(Post o) {
		return postDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return postDao.query(parm);
	}

	@Override
	public MsgResult checkDeleteByUid(String uid) {
		MsgResult result = new MsgResult(false, "检查参数错误");
		// 1.节点下有职务，且职务被人使用 1.节点下所有职务，2.身份信息用到这个职务
		Post p = postDao.getByUid(uid);
		if (BaseUtil.isEmpty(p))
			return result;
		String postId = p.getPid();

		// 有身份
		if (identityService.hadInConditions(null, null, postId, BaseUtil.getUser().getEid())) {
			result.setMsg("本岗位下有人员使用了身份，请先删除人员身份!");
			return result;
		}

		result.setSuccess(true);
		return result;

	}

	@Override
	public MsgResult checkForSave(Post p) {
		MsgResult result = new MsgResult(false, "岗位名称重复");

		// 同级不可有重名
		String uid = p.getUid();
		String name = p.getName();
		String pid = p.getPid();
		String eid = BaseUtil.toString(p.getEid());

		// 检查名称是否重复
		long lb = postDao.countByNameAndOid(null, name, null, uid, eid);
		if (lb > 0)
			return result;

		// 检查编号是否重复
		lb = postDao.countByNameAndOid(null, null, pid, uid, eid);
		if (lb > 0) {
			result.setMsg("岗位编号重复");
			return result;
		}

		result.setSuccess(true);
		result.setMsg("");
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

	@Override
	public List<Post> listByOids(List<String> oids, String rootpid, Boolean enable) {
		return postDao.listByOids(oids, rootpid, enable);
	}

}
