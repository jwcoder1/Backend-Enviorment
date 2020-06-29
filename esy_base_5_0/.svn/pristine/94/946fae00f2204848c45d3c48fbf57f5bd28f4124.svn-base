package org.esy.base.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IInterfaceLogDao;
import org.esy.base.entity.InterfaceLog;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

@Repository
public class InterfaceLogDaoImpl implements IInterfaceLogDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public InterfaceLog save(InterfaceLog o) {
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
	public InterfaceLog getByUid(String uid) {
		return em.find(InterfaceLog.class, uid);
	}

	@Override
	public boolean delete(InterfaceLog o) {
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
			String hql = "  from InterfaceLog a , Application app, InterfaceInfo i  where a.aid=app.aid and a.iid=i.iid  ";
			String whereStr = "";

			Condition logDate = conditions.get("logDate"); // 发生时间
			if (logDate != null) {
				if (logDate.getConditions().get("gte") != null) {
					whereStr += " and a.logDate >= '" + StringEscapeUtils.escapeSql(logDate.getConditions().get("gte"))
							+ "'";
				}
				if (logDate.getConditions().get("lte") != null) {
					whereStr += " and a.logDate <= '" + StringEscapeUtils.escapeSql(logDate.getConditions().get("lte"))
							+ "'";
				}
			}

			Condition ip = conditions.get("ip"); // ip
			if (ip != null) {
				if (ip.getConditions().get("eq") != null) {
					whereStr += " and a.ip = '" + StringEscapeUtils.escapeSql(ip.getConditions().get("eq")) + "'";
				}

				if (ip.getConditions().get("match") != null) {
					whereStr += " and a.ip like  '%" + StringEscapeUtils.escapeSql(ip.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition type = conditions.get("type"); // 类型
			if (type != null) {
				if (type.getConditions().get("eq") != null) {
					whereStr += " and a.type = '" + StringEscapeUtils.escapeSql(type.getConditions().get("eq")) + "'";
				}

				if (type.getConditions().get("match") != null) {
					whereStr += " and a.type like  '%" + StringEscapeUtils.escapeSql(type.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition iid = conditions.get("iid"); // 接口编号
			if (iid != null) {
				if (iid.getConditions().get("eq") != null) {
					whereStr += " and a.iid = '" + StringEscapeUtils.escapeSql(iid.getConditions().get("eq")) + "'";
				}

				if (iid.getConditions().get("match") != null) {
					whereStr += " and a.iid like  '%" + StringEscapeUtils.escapeSql(iid.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition aid = conditions.get("aid"); // 应用编号
			if (aid != null) {
				if (aid.getConditions().get("eq") != null) {
					whereStr += " and a.aid = '" + StringEscapeUtils.escapeSql(aid.getConditions().get("eq")) + "'";
				}

				if (aid.getConditions().get("match") != null) {
					whereStr += " and a.aid like  '%" + StringEscapeUtils.escapeSql(aid.getConditions().get("match"))
							+ "%'";
				}
			}

			// iname
			Condition iname = conditions.get("iname"); // 接口编号+名称
			if (iname != null) {
				if (iname.getConditions().get("match") != null) {
					whereStr += " and (a.iid like " + YESUtil.getLikeStr(iname.getConditions().get("match"))
							+ " or i.name like " + YESUtil.getLikeStr(iname.getConditions().get("match")) + " ) ";
				}
			}

			// aname
			Condition aname = conditions.get("aname"); // 应用编号+名称
			if (aname != null) {
				if (aname.getConditions().get("match") != null) {
					whereStr += " and (a.aid like " + YESUtil.getLikeStr(aname.getConditions().get("match"))
							+ " or app.name like " + YESUtil.getLikeStr(aname.getConditions().get("match")) + " ) ";
				}
			}

			Condition send = conditions.get("send"); // 发送内容
			if (send != null) {
				if (send.getConditions().get("eq") != null) {
					whereStr += " and a.send = '" + StringEscapeUtils.escapeSql(send.getConditions().get("eq")) + "'";
				}

				if (send.getConditions().get("match") != null) {
					whereStr += " and a.send like  '%" + StringEscapeUtils.escapeSql(send.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition resc = conditions.get("resc"); // 接收内容
			if (resc != null) {
				if (resc.getConditions().get("eq") != null) {
					whereStr += " and a.resc = '" + StringEscapeUtils.escapeSql(resc.getConditions().get("eq")) + "'";
				}

				if (resc.getConditions().get("match") != null) {
					whereStr += " and a.resc like  '%" + StringEscapeUtils.escapeSql(resc.getConditions().get("match"))
							+ "%'";
				}
			}

			// eid权限
			whereStr += " and app.eid like '" + BaseUtil.getUser().getEid() + "%' ";

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.logDate desc ";

			Query q = em.createQuery("select count(a.uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			if (!"".equals(orderStr)) {
				hql += orderStr;
			}

			hql = "select new InterfaceLog(a.uid, a.logDate, a.ip, a.type, a.iid, a.aid, a.url, a.send, a.recv, app.name, i.name) "
					+ hql;
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
