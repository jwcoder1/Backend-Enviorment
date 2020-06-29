package org.esy.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.esy.base.common.BaseUtil;
import org.esy.base.dao.IAreaDao;
import org.esy.base.entity.Area;
import org.esy.base.service.IAreaService;
import org.esy.base.util.YESUtil;
import org.esy.base.util.YesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AreaServiceImpl implements IAreaService {

	@Autowired
	private IAreaDao areaDao;

	@Override
	public List<Area> listByPid(String pid, String addnext) {
		List<Area> ret = areaDao.listByPid(pid);
		List<Area> newList = new ArrayList<Area>();
		List<Area> nextarr = null;
		if ("Y".equals(addnext)) {
			for (Area area : ret) {
				nextarr = areaDao.listByPid(area.getId());
				if (nextarr.size() > 0) {
					for (Area o : nextarr) {
						newList.add(o);
					}
				} else {
					newList.add(area);
				}
			}
		} else {
			newList = ret;
		}
		return newList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.service.IAreaService#save(org.esy.base.entity.Area)
	 */
	@Override
	@Transactional
	public Area save(Area o) {
		if (BaseUtil.isEmpty(o.getId())) {
			throw new YesException(HttpStatus.NOT_FOUND, "请输入地区编号!!!");
		} else {
			Area old = areaDao.getById(o.getId());
			if (BaseUtil.isNotEmpty(old)) {
				o = areaDao.save(o);
			} else {
				o = areaDao.add(o);
			}

		}
		return o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.service.IAreaService#getById(java.lang.String)
	 */
	@Override
	public Area getById(String id) {
		Area o = null;
		if (BaseUtil.isNotEmpty(id)) {
			o = areaDao.getById(id);
		}

		return o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.service.IAreaService#query(org.esy.base.entity.Area)
	 */
	@Override
	public List<Area> query(Area area) {

		return areaDao.query(area);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.service.IAreaService#deletes(java.lang.String)
	 */
	@Override
	@Transactional
	public void deletes(String ids) {
		String[] idsArr = ids.split(",");
		for (String id : idsArr) {

			Area old = areaDao.getById(id);
			if (BaseUtil.isEmpty(old)) {
				throw new YesException(HttpStatus.INTERNAL_SERVER_ERROR, "记录不存，不可删除!!!");
			}

			areaDao.delete(old);
		}

	}

}
