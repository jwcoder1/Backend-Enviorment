package org.esy.base.service.impl;

import java.util.Map;

import org.esy.base.core.QueryResult;
import org.esy.base.dao.IInterfaceTransmitDao;
import org.esy.base.entity.InterfaceTransmit;
import org.esy.base.service.IInterfaceTransmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InterfaceTransmitServiceImpl implements IInterfaceTransmitService {

	@Autowired
	private IInterfaceTransmitDao InterfaceTransmitDao;

	@Override
	@Transactional
	public InterfaceTransmit save(InterfaceTransmit o) {
		return InterfaceTransmitDao.save(o);
	}

	@Override
	public InterfaceTransmit getByUid(String uid) {
		return InterfaceTransmitDao.getByUid(uid);
	}

	@Override
	@Transactional
	public boolean delete(InterfaceTransmit o) {
		return InterfaceTransmitDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return InterfaceTransmitDao.query(parm);
	}

	@Override
	public QueryResult listApplications(Map<String, Object> parm) {
		return InterfaceTransmitDao.listApplications(parm);
	}

}
