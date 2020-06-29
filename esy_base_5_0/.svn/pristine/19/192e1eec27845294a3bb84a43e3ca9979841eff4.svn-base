package org.esy.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IInterfaceInfoDao;
import org.esy.base.entity.InterfaceInfo;
import org.esy.base.entity.Organization;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

@Repository
public class InterfaceInfoDaoImpl implements IInterfaceInfoDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public InterfaceInfo save(InterfaceInfo o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
	}

	@Override
	public InterfaceInfo getByUid(String uid) {
		return em.find(InterfaceInfo.class, uid);
	}

	@Override
	public boolean delete(InterfaceInfo o) {
		String hql = "delete InterfaceInfo where uid='" + o.getUid() + "'";
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
			String hql = "from InterfaceInfo a where 1=1";
			String whereStr = "";

			if (parm.containsKey("eid")) {
				whereStr += " and a.eid = '" + parm.get("eid").toString() + "'";
			}

			Condition iid = conditions.get("iid"); // 接口编号
			if (iid != null) {
				if (iid.getConditions().get("eq") != null) {
					whereStr += " and a.iid = '" + StringEscapeUtils.escapeSql(iid.getConditions().get("eq")) + "'";
				}

				if (iid.getConditions().get("match") != null) {
					whereStr += " and a.iid like  '%" + StringEscapeUtils.escapeSql(iid.getConditions().get("match")) + "%'";
				}
			}

			Condition name = conditions.get("name"); // 接口名称
			if (name != null) {
				if (name.getConditions().get("eq") != null) {
					whereStr += " and a.name = '" + StringEscapeUtils.escapeSql(name.getConditions().get("eq")) + "'";
				}

				if (name.getConditions().get("match") != null) {
					whereStr += " and a.name like  '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match")) + "%'";
				}
			}

			Condition opening = conditions.get("opening"); // 公共接口
			if (opening != null) {
				if (opening.getConditions().get("eq") != null) {
					whereStr += " and a.opening = '" + StringEscapeUtils.escapeSql(opening.getConditions().get("eq")) + "'";
				}

				if (opening.getConditions().get("match") != null) {
					whereStr += " and a.opening like  '%" + StringEscapeUtils.escapeSql(opening.getConditions().get("match")) + "%'";
				}
			}

			Condition type = conditions.get("type"); // 类型
			if (type != null) {
				if (type.getConditions().get("eq") != null) {
					whereStr += " and a.type = '" + StringEscapeUtils.escapeSql(type.getConditions().get("eq")) + "'";
				}

				if (type.getConditions().get("match") != null) {
					whereStr += " and a.type like  '%" + StringEscapeUtils.escapeSql(type.getConditions().get("match")) + "%'";
				}
			}

			Condition memo = conditions.get("memo"); // 接口描述
			if (memo != null) {
				if (memo.getConditions().get("eq") != null) {
					whereStr += " and a.memo = '" + StringEscapeUtils.escapeSql(memo.getConditions().get("eq")) + "'";
				}

				if (memo.getConditions().get("match") != null) {
					whereStr += " and a.memo like  '%" + StringEscapeUtils.escapeSql(memo.getConditions().get("match")) + "%'";
				}
			}

			Condition enable = conditions.get("enable"); // 可用状态
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.enable = '" + StringEscapeUtils.escapeSql(enable.getConditions().get("eq")) + "'";
				}

				if (enable.getConditions().get("match") != null) {
					whereStr += " and a.enable like  '%" + StringEscapeUtils.escapeSql(enable.getConditions().get("match")) + "%'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.iid";

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Organization.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getNameByIid(String iid) {
		String h = " select a.name from InterfaceInfo a where a.iid=:iid";
		List<String> ls = em.createQuery(h).setParameter("iid", iid).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

}
