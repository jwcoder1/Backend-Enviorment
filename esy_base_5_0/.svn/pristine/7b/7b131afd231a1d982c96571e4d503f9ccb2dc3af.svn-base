package org.esy.base.service.impl;

import org.esy.base.dao.IImportDao;
import org.esy.base.service.IImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ImportServiceImpl implements IImportService {

	@Value("${init.data}")
	private Boolean init;

	@Value("${init.squence}")
	private String squence;

	@Autowired
	private IImportDao iImportDao;

	@Override
	@Transactional
	public void saveJson(String classname, String json) throws Exception {
		iImportDao.saveJson(classname, json);
	}

	@Override
	@Transactional
	public void executeHql(String hql) {
		iImportDao.executeHql(hql);
	}

}
