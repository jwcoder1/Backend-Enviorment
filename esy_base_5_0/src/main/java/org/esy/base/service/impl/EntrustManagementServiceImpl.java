package org.esy.base.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IEntrustManagementDao;
import org.esy.base.entity.EntrustManagement;
import org.esy.base.service.IEntrustManagementService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntrustManagementServiceImpl implements IEntrustManagementService {

	@Autowired
	private IEntrustManagementDao entrustManagementDao;

	@Override
	@Transactional
	public EntrustManagement save(EntrustManagement o) {
		String dateRange = o.getDateRange();
		if (YESUtil.isNotEmpty(dateRange)) {
			String[] dates = dateRange.split("~");
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			try {
				o.setStartDate(sim.parse(dates[0]));
				o.setToDate(sim.parse(dates[1]));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (YESUtil.isEmpty(o.getEid()))
			o.setEid(BaseUtil.getUser().getEid());
		return entrustManagementDao.save(o);
	}

	@Override
	public EntrustManagement getByUid(String uid) {
		return entrustManagementDao.getByUid(uid);
	}

	@Override
	@Transactional
	public boolean delete(EntrustManagement o) {
		return entrustManagementDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return entrustManagementDao.query(parm);
	}

}
