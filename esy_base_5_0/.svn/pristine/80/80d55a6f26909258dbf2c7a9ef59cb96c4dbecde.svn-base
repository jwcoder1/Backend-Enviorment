package org.esy.base.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.esy.base.common.BaseUtil;
import org.esy.base.dao.IAreaDao;
import org.esy.base.entity.Area;
import org.springframework.stereotype.Repository;

@Repository
public class AreaDaoImpl implements IAreaDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> listByPid(String pid) {
		String h = "from Area a where a.pid=:pid";
		return em.createQuery(h).setParameter("pid", pid).getResultList();
	}

	/* (non-Javadoc)
	 * @see org.esy.base.dao.IAreaDao#getById(java.lang.String)
	 */
	@Override
	public Area getById(String id) {
		return em.find(Area.class, id);
	}

	/* (non-Javadoc)
	 * @see org.esy.base.dao.IAreaDao#save(org.esy.base.entity.Area)
	 */
	@Override
	public Area save(Area o) {
		o = em.merge(o);
		return o;
	}

	/* (non-Javadoc)
	 * @see org.esy.base.dao.IAreaDao#add(org.esy.base.entity.Area)
	 */
	@Override
	public Area add(Area o) {
		em.persist(o);
		return o;
	}

	/* (non-Javadoc)
	 * @see org.esy.base.dao.IAreaDao#query(org.esy.base.entity.Area)
	 */
	@Override
	public List<Area> query(Area area) {
		String hql = "from Area where 1=1 ";
		if (BaseUtil.isNotEmpty(area.getId())) {
			hql += " and id='" + area.getId() + "'";
		}

		if (BaseUtil.isNotEmpty(area.getPid())) {
			hql += " and pid='" + area.getPid() + "' ";
		}

		if (BaseUtil.isNotEmpty(area.getName())) {
			hql += " and name='" + area.getName() + "' ";
		}
		hql += " order by pid asc ,id asc";
		return em.createQuery(hql, Area.class).getResultList();
	}

	/* (non-Javadoc)
	 * @see org.esy.base.dao.IAreaDao#delete(org.esy.base.entity.Area)
	 */
	@Override
	public boolean delete(Area o) {
		try {
			em.remove(em.merge(o));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
