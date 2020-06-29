package org.esy.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IMQueueCfgDao;
import org.esy.base.entity.MQueueCfg;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.http.util.IPAddress;

@Repository
@Transactional
public class MQueueCfgDaoImpl implements IMQueueCfgDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public MQueueCfg save(MQueueCfg o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			this.em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
	}

	public MQueueCfg getByUid(String uid) {
		return this.em.find(MQueueCfg.class, uid);
	}

	public boolean delete(MQueueCfg o) {
		String hql = "delete MQueueCfg where uid='" + o.getUid() + "'";
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
			String hql = "from MQueueCfg a where 1=1";
			String whereStr = "";
			String ipadd = YESUtil.toString(parm.get("ipAddress"));
			if(YESUtil.isNotEmpty(ipadd)){
				whereStr += " and a.ipAddress like '%"+StringEscapeUtils.escapeSql(ipadd)+"%'";
			}
			Condition moduleId = conditions.get("moduleId"); //模块编号
			if (moduleId != null) {
				if (moduleId.getConditions().get("eq") != null) {
					whereStr += " and a.moduleId = '" + StringEscapeUtils.escapeSql(moduleId.getConditions().get("eq"))
							+ "'";
				}
			}
			Condition qid = conditions.get("qid"); //编号
			if (qid != null) {
				if (qid.getConditions().get("eq") != null) {
					whereStr += " and a.qid = '" + StringEscapeUtils.escapeSql(qid.getConditions().get("eq")) + "'";
				}
			}
			Condition type = conditions.get("type"); //类型
			if (type != null) {
				if (type.getConditions().get("eq") != null) {
					whereStr += " and a.type = '" + StringEscapeUtils.escapeSql(type.getConditions().get("eq")) + "'";
				}
			}
			Condition enable = conditions.get("enable"); //是否启动
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.enable = '" + StringEscapeUtils.escapeSql(enable.getConditions().get("eq"))
							+ "'";
				}
			}
			Condition name = conditions.get("name"); //名称
			if (name != null) {
				if (name.getConditions().get("match") != null) {
					whereStr += " and a.name like '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition category = conditions.get("category"); //分类
			if (category != null) {
				if (category.getConditions().get("match") != null) {
					whereStr += " and a.category = '%"
							+ StringEscapeUtils.escapeSql(category.getConditions().get("match")) + "%'";
				}
			}
			Condition lastScheduler = conditions.get("lastScheduler"); //调度时间
			if (lastScheduler != null) {
				if (lastScheduler.getConditions().get("gte") != null) {
					whereStr += " and a.lastScheduler >= '"
							+ StringEscapeUtils.escapeSql(lastScheduler.getConditions().get("gte")) + "'";
				}
				if (lastScheduler.getConditions().get("lte") != null) {
					whereStr += " and a.lastScheduler <= '"
							+ StringEscapeUtils.escapeSql(lastScheduler.getConditions().get("lte")) + "'";
				}
			}
			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.qid";

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(MQueueCfg.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public MQueueCfg getById(String id) {
		MQueueCfg o = null;
		if (!"".equals(id)) {
			String hql = "from MQueueCfg a where a.qid = '" + StringEscapeUtils.escapeSql(id) + "'";
			Query q = em.createQuery(hql);
			try {
				o = (MQueueCfg) q.getSingleResult();
			} catch (Exception e) {

			}
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MQueueCfg> getAtiveCfg() {

		String hql = "from MQueueCfg a where a.enable = true order by a.moduleId, a.qid";
		Query q = em.createQuery(hql);
		return (List<MQueueCfg>) q.getResultList();

	}

}
