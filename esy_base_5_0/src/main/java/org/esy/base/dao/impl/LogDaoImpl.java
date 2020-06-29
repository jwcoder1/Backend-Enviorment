package org.esy.base.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.ILogDao;
import org.esy.base.entity.Log;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class LogDaoImpl implements ILogDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Log save(Log o) {
		if (o.checkNew()) {
			o.setUid(YESUtil.getUuid());
			this.em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
	}

	public Log getByUid(String uid) {
		return this.em.find(Log.class, uid);
	}

	public boolean delete(Log o) {
		String hql = "delete Log where uid='" + o.getUid() + "'";
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

			String hql = "from Log a where 1=1";

			String whereStr = "";

			Condition moduleId = conditions.get("moduleId"); // 模块编号
			if (moduleId != null) {
				if (moduleId.getConditions().get("eq") != null) {
					whereStr += " and a.moduleId = '" + StringEscapeUtils.escapeSql(moduleId.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition eventId = conditions.get("eventId"); // 事件编号
			if (eventId != null) {
				if (eventId.getConditions().get("eq") != null) {
					whereStr += " and a.eventId = '" + StringEscapeUtils.escapeSql(eventId.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition user = conditions.get("user"); // 记录用户
			if (user != null) {
				if (user.getConditions().get("eq") != null) {
					whereStr += " and a.user = '" + StringEscapeUtils.escapeSql(user.getConditions().get("eq")) + "'";
				}
			}

			Condition localIp = conditions.get("localIp"); // 本地IP
			if (localIp != null) {
				if (localIp.getConditions().get("match") != null) {
					whereStr += " and a.localIp like '%"
							+ StringEscapeUtils.escapeSql(localIp.getConditions().get("match")) + "%'";
				}
			}
			Condition remoteIp = conditions.get("remoteIp"); // 远程IP
			if (remoteIp != null) {
				if (remoteIp.getConditions().get("match") != null) {
					whereStr += " and a.remoteIp like '%"
							+ StringEscapeUtils.escapeSql(remoteIp.getConditions().get("match")) + "%'";
				}
			}

			Condition info = conditions.get("info"); // 记录信息
			if (info != null) {
				if (info.getConditions().get("match") != null) {
					whereStr += " and a.info = '%" + StringEscapeUtils.escapeSql(info.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition time = conditions.get("time"); // 记录时间
			if (time != null) {
				if (time.getConditions().get("gte") != null) {
					whereStr += " and a.time >= '" + StringEscapeUtils.escapeSql(time.getConditions().get("gte")) + "'";
				}
				if (time.getConditions().get("lte") != null) {
					whereStr += " and a.time <= '" + StringEscapeUtils.escapeSql(time.getConditions().get("lte")) + "'";
				}
			}
			Condition updated = conditions.get("updated"); // 操作时间
			if (updated != null) {
				if (updated.getConditions().get("gte") != null) {
					whereStr += " and a.updated >= :gte";
				}
				if (updated.getConditions().get("lte") != null) {
					whereStr += " and a.updated <= :lte";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.time desc";
			if (orderStr.equals("localIp")) {
				orderStr = " order by a.localIp";
			}
			if (orderStr.equals("remoteIp")) {
				orderStr = " order by a.remoteIp";
			}
			if (orderStr.equals("user")) {
				orderStr = " order by a.user";
			}

			Query q = em.createQuery("select count(uid) " + hql);
			if (updated != null) {
				if (updated.getConditions().get("gte") != null) {
					Date gte = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((updated.getConditions().get("gte")));
					java.sql.Date date = new java.sql.Date(gte.getTime());
					q.setParameter("gte", date, TemporalType.DATE);
				}
				if (updated.getConditions().get("lte") != null) {
					Date lte = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((updated.getConditions().get("lte")));
					java.sql.Date date = new java.sql.Date(lte.getTime());
					q.setParameter("lte", date, TemporalType.DATE);
				}
			}
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			if (!"".equals(orderStr)) {
				hql += orderStr;
			}

			q = em.createQuery(hql);
			if (updated != null) {
				if (updated.getConditions().get("gte") != null) {
					Date gte = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((updated.getConditions().get("gte")));
					java.sql.Date date = new java.sql.Date(gte.getTime());
					q.setParameter("gte", date, TemporalType.DATE);
				}
				if (updated.getConditions().get("lte") != null) {
					Date lte = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse((updated.getConditions().get("lte")));
					java.sql.Date date = new java.sql.Date(lte.getTime());
					q.setParameter("lte", date, TemporalType.DATE);
				}
			}
			if (start > 0) {
				q.setFirstResult(start);
			}
			if (count > 0) {
				q.setMaxResults(count);
			} else {
				q.setMaxResults(20);
			}
			qr.setItems(q.getResultList());
			qr.setHeaders(QueryUtils.getClassFieldInfo(Log.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

}
