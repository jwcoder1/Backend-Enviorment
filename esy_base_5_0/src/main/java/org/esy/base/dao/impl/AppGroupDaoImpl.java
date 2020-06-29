package org.esy.base.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IAppGroupDao;
import org.esy.base.entity.AppGroup;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

/**
 * 
 * Dao implement for 企业信息
 *
 */
@Repository
public class AppGroupDaoImpl implements IAppGroupDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public AppGroup save(AppGroup o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			this.em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
	}

	@Override
	public Long getChildrenCount(String agid) {
		String hql = "select count(uid) from AppGroup a where a.pid = '" + agid + "'";
		Query q = em.createQuery(hql);
		return (Long) q.getSingleResult();
	}

	@Override
	public AppGroup getByUid(String uid) {
		return this.em.find(AppGroup.class, uid);
	}

	@Override
	public boolean delete(AppGroup o) {
		String hql = "delete AppGroup where uid='" + o.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);

		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));

			String hql = "from AppGroup a where 1=1";

			String whereStr = "";

			if (parm.containsKey("eid")) {
				whereStr += " and a.eid = '" + parm.get("eid").toString() + "'";
			}

			Condition agid = conditions.get("agid"); // 路径
			if (agid != null) {
				if (agid.getConditions().get("match") != null) {
					whereStr += " and a.agid like '%" + StringEscapeUtils.escapeSql(agid.getConditions().get("match")) + "%'";
				}
			}

			Condition name = conditions.get("name"); // 别名
			if (name != null) {
				if (name.getConditions().get("match") != null) {
					whereStr += " and a.name like '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match")) + "%'";
				}
			}

			Condition pid = conditions.get("pid"); // 别名
			if (pid != null) {
				if (pid.getConditions().get("match") != null) {
					whereStr += " and a.pid like '%" + StringEscapeUtils.escapeSql(pid.getConditions().get("match")) + "%'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = "";
			orderStr = " order by a.seq asc";

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(AppGroup.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public Integer getMaxSeq(String eid, String pid) {
		String h = " select max(a.seq) from AppGroup a where a.eid=:eid";
		if (YESUtil.isNotEmpty(pid)) {
			h += " and pid=:pid";
		}
		Query query = em.createQuery(h).setParameter("eid", eid);
		if (YESUtil.isNotEmpty(pid)) {
			query.setParameter("pid", pid);
		}
		return YESUtil.objtoint(query.getSingleResult(), 0);
	}

}
