package org.esy.base.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IEntrustManagementDao;
import org.esy.base.entity.EntrustManagement;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

@Repository
public class EntrustManagementDaoImpl implements IEntrustManagementDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public EntrustManagement save(EntrustManagement o) {
		if (o.checkNew()) {
			o.setUid(YESUtil.getUuid());
			this.em.persist(o);
		} else {
			o = this.em.merge(o);
		}
		return o;
	}

	@Override
	public EntrustManagement getByUid(String uid) {
		return this.em.find(EntrustManagement.class, uid);
	}

	@Override
	public boolean delete(EntrustManagement o) {
		String hql = "delete EntrustManagement where uid='" + o.getUid() + "'";
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
			String hql = "from EntrustManagement a where 1=1";
			String whereStr = "";

			if (parm.containsKey("eid")) {
				whereStr += " and a.eid = '" + parm.get("eid").toString() + "'";
			} else {
				whereStr += " and a.eid = '" + BaseUtil.getUser().getEid() + "'";
			}

			Condition entrustPersonId = conditions.get("entrustPersonId"); // 委办人id
			if (entrustPersonId != null) {
				if (entrustPersonId.getConditions().get("eq") != null) {
					whereStr += " and a.entrustPersonId = '"
							+ StringEscapeUtils.escapeSql(entrustPersonId.getConditions().get("eq")) + "'";
				}

				if (entrustPersonId.getConditions().get("match") != null) {
					whereStr += " and a.entrustPersonId like  '%"
							+ StringEscapeUtils.escapeSql(entrustPersonId.getConditions().get("match")) + "%'";
				}
			}

			Condition toEntrustPersonId = conditions.get("toEntrustPersonId"); // 被委办人id
			if (toEntrustPersonId != null) {
				if (toEntrustPersonId.getConditions().get("eq") != null) {
					whereStr += " and a.toEntrustPersonId = '"
							+ StringEscapeUtils.escapeSql(toEntrustPersonId.getConditions().get("eq")) + "'";
				}

				if (toEntrustPersonId.getConditions().get("match") != null) {
					whereStr += " and a.toEntrustPersonId like  '%"
							+ StringEscapeUtils.escapeSql(toEntrustPersonId.getConditions().get("match")) + "%'";
				}
			}

			Condition entrustPerson = conditions.get("entrustPerson"); // 委办人
			if (entrustPerson != null) {
				if (entrustPerson.getConditions().get("eq") != null) {
					whereStr += " and a.entrustPerson = '"
							+ StringEscapeUtils.escapeSql(entrustPerson.getConditions().get("eq")) + "'";
				}

				if (entrustPerson.getConditions().get("match") != null) {
					whereStr += " and a.entrustPerson like  '%"
							+ StringEscapeUtils.escapeSql(entrustPerson.getConditions().get("match")) + "%'";
				}
			}

			Condition toEntrustPerson = conditions.get("toEntrustPerson"); // 被委办人
			if (toEntrustPerson != null) {
				if (toEntrustPerson.getConditions().get("eq") != null) {
					whereStr += " and a.toEntrustPerson = '"
							+ StringEscapeUtils.escapeSql(toEntrustPerson.getConditions().get("eq")) + "'";
				}

				if (toEntrustPerson.getConditions().get("match") != null) {
					whereStr += " and a.toEntrustPerson like  '%"
							+ StringEscapeUtils.escapeSql(toEntrustPerson.getConditions().get("match")) + "%'";
				}
			}

			Condition startDate = conditions.get("startDate"); // 开始日期
			if (startDate != null) {
				if (startDate.getConditions().get("gte") != null) {
					whereStr += " and a.startDate >= '"
							+ StringEscapeUtils.escapeSql(startDate.getConditions().get("gte")) + " 00:00:00'";
				}
				if (startDate.getConditions().get("lte") != null) {
					whereStr += " and a.startDate <= '"
							+ StringEscapeUtils.escapeSql(startDate.getConditions().get("lte")) + " 23:59:59'";
				}
			}

			Condition toDate = conditions.get("toDate"); // 结束日期
			if (toDate != null) {
				if (toDate.getConditions().get("gte") != null) {
					whereStr += " and a.toDate >= '" + StringEscapeUtils.escapeSql(toDate.getConditions().get("gte"))
							+ " 00:00:00'";
				}
				if (toDate.getConditions().get("lte") != null) {
					whereStr += " and a.toDate <= '" + StringEscapeUtils.escapeSql(toDate.getConditions().get("lte"))
							+ " 23:59:59'";
				}
			}

			// boolean flagStartDate = false, flagToDate = false;
			// if (parm.containsKey("startDate")) {
			// flagStartDate = true;
			// YESUtil.getDateByString(")
			// }

			Condition aid = conditions.get("aid"); // 应用id
			if (aid != null) {
				if (aid.getConditions().get("eq") != null) {
					whereStr += " and a.aid = '" + StringEscapeUtils.escapeSql(aid.getConditions().get("eq")) + "'";
				}

				if (aid.getConditions().get("match") != null) {
					whereStr += " and a.aid like  '%" + StringEscapeUtils.escapeSql(aid.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition appName = conditions.get("appName"); // 应用名称
			if (appName != null) {
				if (appName.getConditions().get("eq") != null) {
					whereStr += " and a.appName = '" + StringEscapeUtils.escapeSql(appName.getConditions().get("eq"))
							+ "'";
				}

				if (appName.getConditions().get("match") != null) {
					whereStr += " and a.appName like  '%"
							+ StringEscapeUtils.escapeSql(appName.getConditions().get("match")) + "%'";
				}
			}

			Condition module = conditions.get("module"); // 功能模块
			if (module != null) {
				if (module.getConditions().get("eq") != null) {
					whereStr += " and a.module = '" + StringEscapeUtils.escapeSql(module.getConditions().get("eq"))
							+ "'";
				}

				if (module.getConditions().get("match") != null) {
					whereStr += " and a.module like  '%"
							+ StringEscapeUtils.escapeSql(module.getConditions().get("match")) + "%'";
				}
			}

			Condition enable = conditions.get("enable"); // 启用
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.enable =true";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.created desc ";

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
			// qr.setHeaders(QueryUtils.getClassFieldInfo(Organization.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

}
