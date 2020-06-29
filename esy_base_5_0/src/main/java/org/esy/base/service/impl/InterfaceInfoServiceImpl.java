package org.esy.base.service.impl;

import java.util.Map;

import org.esy.base.core.QueryResult;
import org.esy.base.dao.IInterfaceInfoDao;
import org.esy.base.entity.InterfaceInfo;
import org.esy.base.service.IInterfaceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InterfaceInfoServiceImpl implements IInterfaceInfoService {

	@Autowired
	private IInterfaceInfoDao interfaceInfoDao;

	@Override
	@Transactional
	public InterfaceInfo save(InterfaceInfo o) {
		return interfaceInfoDao.save(o);
	}

	@Override
	public InterfaceInfo getByUid(String uid) {
		return interfaceInfoDao.getByUid(uid);
	}

	@Override
	@Transactional
	public boolean delete(InterfaceInfo o) {
		return interfaceInfoDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return interfaceInfoDao.query(parm);
	}

}
