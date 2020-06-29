package org.esy.base.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IMQueueDao;
import org.esy.base.entity.MQueue;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MQueueDaoImpl implements IMQueueDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public MQueue save(MQueue o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			this.em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
	}

	public MQueue getByUid(String uid) {
		return this.em.find(MQueue.class, uid);
	}

	public boolean delete(MQueue o) {
		String hql = "delete MQueue where uid='" + o.getUid() + "'";
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
			String order = YESUtil.toString(parm.get("order"));

			String hql = "from MQueue a where 1=1";

			String whereStr = "";

			Condition qid = conditions.get("qid"); //编号
			if (qid != null) {
				if (qid.getConditions().get("eq") != null) {
					whereStr += " and a.qid = '" + StringEscapeUtils.escapeSql(qid.getConditions().get("eq")) + "'";
				}
			}

			Condition seq = conditions.get("seq"); //序号
			if (seq != null) {
				if (seq.getConditions().get("eq") != null) {
					whereStr += " and a.seq = " + StringEscapeUtils.escapeSql(seq.getConditions().get("eq")) + "";
				}
			}
			Condition data = conditions.get("data"); //消息内容
			if (data != null) {
				if (data.getConditions().get("match") != null) {
					whereStr += " and a.data like '%" + StringEscapeUtils.escapeSql(data.getConditions().get("match"))
							+ "%'";
				}
			}
			Condition errorCount = conditions.get("errorCount"); //失败次数
			if (errorCount != null) {
				if (errorCount.getConditions().get("eq") != null) {
					whereStr += " and a.errorCount = "
							+ StringEscapeUtils.escapeSql(errorCount.getConditions().get("eq")) + "";
				}
			}
			Condition lastScheduler = conditions.get("lastScheduler"); //最后调度
			if (lastScheduler != null) {
				if (lastScheduler.getConditions().get("gte") != null) {
					whereStr += " and a.lastScheduler >= '"
							+ StringEscapeUtils.escapeSql(lastScheduler.getConditions().get("gte")) + "'";
				}
			}
			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.seq desc";

			if ("updated".equals(order)) {
				orderStr = " order by a.updated desc";
			}
			if ("created".equals(order)) {
				orderStr = " order by a.created desc";
			}

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(MQueue.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MQueue> getMessages(String qid) {
		String hql = "from MQueue a where a.qid = '" + StringEscapeUtils.escapeSql(qid) + "'";
		hql += " order by a.seq";
		Query q = em.createQuery(hql);
		return (List<MQueue>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MQueue> getMessages(String qid, int count, int skip) {
		if (count == 0) {
			return null;
		}
		String hql = "from MQueue a where a.qid = '" + StringEscapeUtils.escapeSql(qid) + "'";
		if (count > 0) {
			hql += " and a.state = '10'";
			hql += " order by a.seq";
			Query q = em.createQuery(hql);
			q.setMaxResults(count);
			q.setFirstResult(skip);
			List<MQueue> msgs = (List<MQueue>) q.getResultList();
			for (MQueue msg : msgs) {
				msg.setState("20");
				save(msg);
			}
			return msgs;
		} else {
			hql += " and a.state = '20'";
			hql += " order by a.seq desc";
			Query q = em.createQuery(hql);
			q.setMaxResults(count * -1);
			q.setFirstResult(skip);
			return (List<MQueue>) q.getResultList();
		}
	}

	@Override
	public int remove(String qid, Date beginDate) {
		String hql = "delete MQueue where qid='" + qid + "'";
		hql += " and created < '" + YESUtil.getDatetimeString(beginDate) + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		return updateCount;
	}

	@Override
	public int remove(String qid, long seq) {
		String hql = "delete MQueue where qid='" + qid + "'";
		hql += " and seq <= " + seq;
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		return updateCount;
	}

}
