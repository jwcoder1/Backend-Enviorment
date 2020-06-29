package org.esy.base.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IRoleDao;
import org.esy.base.entity.Role;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleDaoImpl implements IRoleDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Role save(Role o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			this.em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
	}

	public Role getByUid(String uid) {
		return this.em.find(Role.class, uid);
	}

	public boolean delete(Role o) {
		String hql = "delete Role where uid='" + o.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public QueryResult query(Map<String, Object> parm) {

		QueryResult qr = new QueryResult();

		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);

		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));

			String hql = "from Role a where 1=1";

			String whereStr = "";

			Condition rid = conditions.get("rid"); // 编号
			if (rid != null) {
				if (rid.getConditions().get("eq") != null) {
					whereStr += " and a.rid = '" + StringEscapeUtils.escapeSql(rid.getConditions().get("eq")) + "'";
				}
			}

			Condition name = conditions.get("name"); // 名称
			if (name != null) {
				if (name.getConditions().get("match") != null) {
					whereStr += " and a.name like '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition describe = conditions.get("describe"); // 描述
			if (describe != null) {
				if (describe.getConditions().get("match") != null) {
					whereStr += " and a.describe like '%"
							+ StringEscapeUtils.escapeSql(describe.getConditions().get("match")) + "%'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by created";

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Role.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public Role getById(String id) {
		Role o = null;
		if (!"".equals(id)) {
			String hql = "from Role a where a.rid = '" + StringEscapeUtils.escapeSql(id) + "'";
			Query q = em.createQuery(hql);
			try {
				o = (Role) q.getSingleResult();
			} catch (Exception e) {

			}
		}
		return o;
	}

}
