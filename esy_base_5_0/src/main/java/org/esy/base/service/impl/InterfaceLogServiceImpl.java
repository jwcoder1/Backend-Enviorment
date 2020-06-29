package org.esy.base.service.impl;

import java.util.Date;
import java.util.Map;

import org.esy.base.core.QueryResult;
import org.esy.base.dao.IInterfaceLogDao;
import org.esy.base.entity.InterfaceLog;
import org.esy.base.service.IInterfaceLogService;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InterfaceLogServiceImpl implements IInterfaceLogService {

	@Autowired
	private IInterfaceLogDao interfaceLogDao;

	@Override
	@Transactional
	public InterfaceLog save(InterfaceLog o) {
		return interfaceLogDao.save(o);
	}

	@Override
	public InterfaceLog getByUid(String uid) {
		return interfaceLogDao.getByUid(uid);
	}

	@Override
	@Transactional
	public boolean delete(InterfaceLog o) {
		return interfaceLogDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return interfaceLogDao.query(parm);
	}

	@Override
	public InterfaceLog StartLog(String iid, String aid, String url, String send, String recv, String type, String ip) {
		InterfaceLog log = new InterfaceLog(iid, aid, url, send, recv);
		log.setUid(YESUtil.getUuid());
		log.setLogDate(new Date());
		log.setType(type);
		log.setIp(ip);
		return log;
	}

	@Override
	@Transactional
	public InterfaceLog saveLog(InterfaceLog log) {
		return interfaceLogDao.save(log);
	}

}
