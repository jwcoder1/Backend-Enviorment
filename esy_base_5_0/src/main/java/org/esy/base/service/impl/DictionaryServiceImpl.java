package org.esy.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.core.Response;
import org.esy.base.dao.IDicDetailDao;
import org.esy.base.dao.IDictionaryDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Dictionary;
import org.esy.base.service.IDictionaryService;
import org.esy.base.service.ILogService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DictionaryServiceImpl implements IDictionaryService {

	@Autowired
	IDictionaryDao dictionaryDao;

	@Autowired
	IDicDetailDao dicDetailDao;

	@Autowired
	ILogService logService;

	@Override
	@Transactional
	public Dictionary save(Dictionary o, HttpServletRequest request) {
		if (o.checkNew()) {
			logService.save("base", "DictionaryCreate", o.getName() + " : 创建成功 ", request.getRemoteAddr());
		} else {
			logService.save("base", "DictionaryModify", o.getName() + " : 修改成功", request.getRemoteAddr());
		}
		return dictionaryDao.save(o);
	}

	@Override
	public boolean delete(Dictionary o, HttpServletRequest request) {
		boolean result = dictionaryDao.delete(o);
		if (result) {
			logService.save("base", "DictionaryDelete", o.getName() + " : 删除成功", request.getRemoteAddr());
		}
		return result;
	}

	@Override
	public Dictionary getByUid(String uid) {
		return dictionaryDao.getByUid(uid);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return dictionaryDao.query(parm);
	}

	@Override
	public List<Object> getDicDetailByName(String name) {
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("name$match", name);
		QueryResult qs = dictionaryDao.query(parm);
		if (qs.getCount() == 0) {
			return null;
		}
		Dictionary dic = (Dictionary) qs.getItems().get(0);

		parm.clear();
		parm.put("id$eq", dic.getId());
		qs = dicDetailDao.query(parm);
		if (qs.getCount() == 0) {
			return null;
		}
		return null;
	}

	@Override
	public ResponseEntity<Response> checkSearch(Map<String, Object> parm) {
		Response resp;
		Account user = BaseUtil.getUser();
		String eid = user.getEid();
		String type = user.getType();

		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);
		Condition ceid = conditions.get("eid");
		String peid = "";
		if (ceid.getConditions().get("match") != null) {
			peid = StringEscapeUtils.escapeSql(ceid.getConditions().get("match"));
		} else if (ceid.getConditions().get("eq") != null) {
			peid = StringEscapeUtils.escapeSql(ceid.getConditions().get("eq"));
		}

		if (!"admin".equalsIgnoreCase(type)) {
			if (BaseUtil.isEmpty(peid)) { // 非平台管理员，eid不可为空
				resp = new Response();
				resp.setError(HttpStatus.INTERNAL_SERVER_ERROR.value());
				resp.setMessage("非平台管理员，eid不可为空");
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if (peid.indexOf(eid) == -1) { // eid不是自己企业 或子企业的eid
				resp = new Response();
				resp.setError(HttpStatus.INTERNAL_SERVER_ERROR.value());
				resp.setMessage("查询的eid不是自己企业的eid或子企业eid");
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		// 正确返回
		resp = new Response();
		resp.setError(HttpStatus.OK.value());
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@Override
	public Dictionary getByID(String id) {
		return dictionaryDao.getByID(id);
	}
}
