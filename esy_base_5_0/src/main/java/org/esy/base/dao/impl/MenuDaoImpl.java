package org.esy.base.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IMenuDao;
import org.esy.base.entity.Menu;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MenuDaoImpl implements IMenuDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Menu save(Menu o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			this.em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
	}

	public Menu getByUid(String uid) {
		return this.em.find(Menu.class, uid);
	}

	public boolean delete(Menu o) {
		String hql = "delete Menu where uid='" + o.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	public QueryResult query(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);

		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));

			String hql = "from Menu a where 1=1";

			String whereStr = "";

			Condition mid = conditions.get("mid"); // 编号
			if (mid != null) {
				if (mid.getConditions().get("eq") != null) {
					whereStr += " and a.mid = '" + StringEscapeUtils.escapeSql(mid.getConditions().get("eq")) + "'";
				}
			}

			Condition pid = conditions.get("pid"); // 上级编号
			if (pid != null) {
				if (pid.getConditions().get("eq") != null) {
					whereStr += " and a.pid = '" + StringEscapeUtils.escapeSql(pid.getConditions().get("eq")) + "'";
				}
			}

			Condition name = conditions.get("name"); // 名称
			if (name != null) {
				if (name.getConditions().get("match") != null) {
					whereStr += " and a.name like '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match")) + "%'";
				}
			}

			Condition type = conditions.get("type"); // 类型
			if (type != null) {
				if (type.getConditions().get("eq") != null) {
					whereStr += " and a.type = '" + StringEscapeUtils.escapeSql(type.getConditions().get("eq")) + "'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.pid, a.mid";

			Query q = em.createQuery("select count(uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			if (!"".equals(orderStr)) {
				hql += orderStr;
			}

			q = em.createQuery(hql);
			if (start > 0) {
				q.setFirstResult(start);
			}
			if (count > 0) {
				q.setMaxResults(count);
			} else {
				q.setMaxResults(20);
			}
			qr.setItems(q.getResultList());
			qr.setHeaders(QueryUtils.getClassFieldInfo(Menu.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public Menu getById(String id) {
		Menu o = null;
		if (!"".equals(id)) {
			String hql = "from Menu a where a.mid = '" + StringEscapeUtils.escapeSql(id) + "'";
			Query q = em.createQuery(hql);
			try {
				o = (Menu) q.getSingleResult();
			} catch (Exception e) {

			}
		}
		return o;
	}

}
