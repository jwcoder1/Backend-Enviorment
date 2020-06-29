package org.esy.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.ICustomDao;
import org.esy.base.dao.IDicDetailDao;
import org.esy.base.dao.IDictionaryDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.DicDetail;
import org.esy.base.entity.Dictionary;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.service.IDicDetailService;
import org.esy.base.service.ILogService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DicDetailServiceImpl implements IDicDetailService {

	@Autowired
	IDicDetailDao dicDetailDao;

	@Autowired
	ILogService logService;

	@Autowired
	IDictionaryDao dictionaryDao;

	@Autowired
	private ICustomDao customDao;

	@Override
	@Transactional
	public DicDetail save(DicDetail o, HttpServletRequest request) {
		if (o.checkNew()) {
			if (YESUtil.isEmpty(o.getSeq())) {
				Integer seq = dicDetailDao.getMaxSeqByDidAndEid(o.getDid(), o.getEid());
				seq++;
				o.setSeq(seq);
				if (YESUtil.isEmpty(o.getValue())) {
					o.setValue(YESUtil.toString(o.getValue()));
				}
			}
			logService.save("base", "DicDetailCreate", o.getModel() + " : 创建成功 ", request.getRemoteAddr());
		} else {
			logService.save("base", "DicDetailModify", o.getModel() + " : 修改成功", request.getRemoteAddr());
		}
		if (YESUtil.isEmpty(o.getModel()))
			o.setModel("");
		DicDetail dd = dicDetailDao.save(o);
		// 企业分类，要同步到群组，和应用分组
		if ("ENTCLASSIFY".equals(dd.getDid())) {
			customDao.updateGroup(dd.getEid(), dd.getId(), dd.getValue());
		}
		return dd;
	}

	@Override
	@Transactional
	public boolean delete(DicDetail o, HttpServletRequest request) {
		boolean result = dicDetailDao.delete(o);
		if (result) {
			logService.save("base", "DicDetailDelete", o.getModel() + " : 删除成功", request.getRemoteAddr());
		}
		return result;
	}

	@Override
	public DicDetail getByUid(String uid) {
		return dicDetailDao.getByUid(uid);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return dicDetailDao.query(parm);
	}

	@Override
	public String getValueByDdidandEid(String did, String eid) {
		return dicDetailDao.getValueByDdidandEid(did, eid);
	}

	@Override
	public List<DicDetail> listValueByDdidandEid(String did, String eid) {
		return dicDetailDao.listValueByDdidandEid(did, eid);
	}

	@Override
	public MsgResult checkForSave(DicDetail o) {
		MsgResult mr = new MsgResult(false, "");
		// did不可为空
		if (YESUtil.isEmpty(o.getDid())) {
			mr.setMsg("Did不可为空");
			return mr;
		}
		// id不可为空
		if (YESUtil.isEmpty(o.getId())) {
			mr.setMsg("编号不可为空");
			return mr;
		}
		// value不检测

		// 1-只读 2-修改 3-完全控制(是否有动的权限)
		Dictionary d = dictionaryDao.getByID(o.getDid());
		if ("1".equals(d.getStatus())) {
			mr.setMsg("没有可操作权限");
			return mr;
		}

		if (o.checkNew()) {
			if ("1".equals(d.getStatus())) {
				mr.setMsg("没有可操作权限");
				return mr;
			}
		}
		// did下编号不可重复
		if (!dicDetailDao.checkForSave(o.getDid(), o.getEid(), o.getId(), null, o.getUid())) {
			mr.setMsg("编号不可重复");
			return mr;
		}

		mr.setSuccess(true);
		return mr;
	}

	@Override
	public QueryResult listByCondition(Map<String, Object> parm) {
		String type = "";
		if (parm.containsKey("type")) {
			type = YESUtil.toString(parm.get("type"));
		}
		String eid = "";
		if (parm.containsKey("eid")) {
			eid = YESUtil.toString(parm.get("eid"));
		}

		// 要加企业路径
		if ("ENT".equals(type) && YESUtil.isEmpty(eid)) {
			Account user = BaseUtil.getUser();
			eid = user.getEid();
			parm.put("eid", eid);
		}
		return dicDetailDao.listByCondition(parm);
	}

	@Override
	public String getValueByDid(String did, String id, String eid) {
		return dicDetailDao.getValueByDid(did, id, eid);
	}

	@Override
	public String getIdByDid(String did, String eid) {
		return dicDetailDao.getIdByDid(did, eid);
	}

	@Override
	public List<String> listIdByDid(String did, String eid) {
		return dicDetailDao.listIdByDid(did, eid);
	}

	@Override
	public String getValueByDid(String did, String eid) {
		return dicDetailDao.getValueByDid(did, eid);
	}

	@Override
	public List<String> listValueByDid(String did, String eid) {
		return dicDetailDao.listValueByDid(did, eid);
	}

	/**
	 * 通过did,id获取字典表数据
	 * 
	 * @author huiqiang.yan 2016-7-20 9:00:00 am
	 * @param did
	 *            主类编号
	 * @param id
	 *            编号
	 * @return
	 */
	@Override
	public List<DicDetail> getByCondition(Map<String, Object> parm) {
		String did = YESUtil.toString(parm.get("did"));
		String id = YESUtil.toString(parm.get("id"));
		return dicDetailDao.getByCondition(did, id);
	}

	/**
	 * 通过did,id删除字典数据
	 * 
	 * @author huiqiang.yan 2016-7-20 9:30:00 am
	 * @param did
	 *            主类编号
	 * @param id
	 *            编号
	 * @return
	 */
	@Override
	@Transactional
	public boolean deleteByCondition(Map<String, Object> parm) {
		String did = YESUtil.toString(parm.get("did"));
		String id = YESUtil.toString(parm.get("id"));
		return dicDetailDao.deleteByCondition(did, id);
	}

}
