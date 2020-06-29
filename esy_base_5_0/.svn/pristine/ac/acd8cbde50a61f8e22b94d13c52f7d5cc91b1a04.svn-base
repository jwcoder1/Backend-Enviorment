package org.esy.base.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.esy.base.dao.IImportDao;
import org.springframework.stereotype.Repository;

import net.sf.json.JSONObject;

/**
 * 菜单DAO实现类
 * @author Sun YongDa 2014-10-28
 *
 */
@Repository
public class ImportDaoImpl implements IImportDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("static-access")
	public void saveJson(String classname, String json) throws Exception {
		JSONObject obj = JSONObject.fromObject(json);
		Object bean = obj.toBean(obj, Class.forName(classname));
		em.merge(bean);
	}

	public void executeHql(String hql) {
		em.createQuery(hql).executeUpdate();
	}

}
