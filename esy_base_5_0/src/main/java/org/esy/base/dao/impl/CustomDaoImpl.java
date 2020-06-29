package org.esy.base.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.esy.base.dao.ICustomDao;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDaoImpl implements ICustomDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public String getNameById(String entityName, String name, String id, String idvalue) {
		String h = String.format("select %s  from %s where %s = '%s'  ", name, entityName, id, idvalue);
		List<String> ls = em.createQuery(h).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@Override
	public boolean updateGroup(String eid, String id, String value) {
		boolean flag = false;
		try {
			// 应用
			// 群组
			String h = "update GroupMember gm set gm.show=:show  where gm.eid=:eid and gm.type='ET' and gm.value=:id ";
			em.createQuery(h).setParameter("eid", eid).setParameter("id", id).setParameter("show", value).executeUpdate();

			h = "update GroupMember gm set gm.show2=:show  where gm.eid=:eid and gm.type2='ET' and gm.value2=:id ";
			em.createQuery(h).setParameter("eid", eid).setParameter("id", id).setParameter("show", value).executeUpdate();

			h = "update AppAuthority aa set aa.show=:show  where aa.eid=:eid and aa.type='ET' and aa.value=:id ";
			em.createQuery(h).setParameter("eid", eid).setParameter("id", id).setParameter("show", value).executeUpdate();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public boolean exsitsInTable(String className, String field, String value) {
		Query q = em.createQuery("select count(1)  from " + className + "  a  where a." + field + "=:value ");
		Long l = (Long) q.setParameter("value", value).getSingleResult();
		return l > 0;
	}

}
