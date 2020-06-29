package org.esy.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.ILogEventDao;
import org.esy.base.entity.LogEvent;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class LogEventDaoImpl implements ILogEventDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public LogEvent save(LogEvent o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			this.em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
	}

	public LogEvent getByUid(String uid) {
		return this.em.find(LogEvent.class, uid);
	}

	public boolean delete(LogEvent o) {
		String hql = "delete LogEvent where uid='" + o.getUid() + "'";
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

			String hql = "from LogEvent a where 1=1";

			String whereStr = "";

			Condition moduleId = conditions.get("moduleId"); //模组编号
			if (moduleId != null) {
				if (moduleId.getConditions().get("eq") != null) {
					whereStr += " and a.moduleId = '" + StringEscapeUtils.escapeSql(moduleId.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition eventId = conditions.get("eventId"); //事件编号
			if (eventId != null) {
				if (eventId.getConditions().get("eq") != null) {
					whereStr += " and a.eventId = '" + StringEscapeUtils.escapeSql(eventId.getConditions().get("eq"))
							+ "'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.moduleId, a.eventId";

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(LogEvent.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public LogEvent getById(String moduleId, String eventId) {
		LogEvent o = null;
		if (!"".equals(moduleId) && !"".equals(eventId)) {
			String hql = "from LogEvent a where a.moduleId = '" + StringEscapeUtils.escapeSql(moduleId) + "'";
			hql += " and a.eventId = '" + StringEscapeUtils.escapeSql(eventId) + "'";
			Query q = em.createQuery(hql);
			try {
				o = (LogEvent) q.getSingleResult();
			} catch (Exception e) {

			}
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LogEvent> getByMid(String moduleId) {
		List<LogEvent> list = null;
		if (!"".equals(moduleId)) {
			String hql = "from LogEvent a where a.moduleId = '" + StringEscapeUtils.escapeSql(moduleId) + "'";
			Query q = em.createQuery(hql);
			try {
				list = (List<LogEvent>) q.getResultList();
			} catch (Exception e) {

			}
		}
		return list;
	}

}
