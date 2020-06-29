package org.esy.base.dao.impl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.esy.base.dao.ISysvarDao;
import org.esy.base.dao.core.PageQuery;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:ardui@163.com">ardui</a>
 * @date Wed Nov 22 22:54:39 CST 2017
 *
 * @version v2.0
 */
@Repository
public class SysvarDaoImpl implements ISysvarDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Resource
	private PageQuery pageQuery;

	/* (non-Javadoc)
	 * @see org.esy.base.dao.ISysvarDao#getgroptype()
	 */
	@Override
	public Object getgroptype() {
		String hql = "select new map('' as pid,d.var_type as id,d.var_typedesc as name) from Sysvar d  group by var_type";
		Object ret = em.createQuery(hql).getResultList();
		return ret;
	}

}
