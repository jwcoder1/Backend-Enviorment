package org.esy.base.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IInterfaceTransmitDao;
import org.esy.base.entity.InterfaceTransmit;
import org.esy.base.entity.Organization;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

@Repository
public class InterfaceTransmitDaoImpl implements IInterfaceTransmitDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public InterfaceTransmit save(InterfaceTransmit o) {
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
	public InterfaceTransmit getByUid(String uid) {
		return em.find(InterfaceTransmit.class, uid);
	}

	@Override
	public boolean delete(InterfaceTransmit o) {
		String hql = "delete InterfaceTransmit where uid='" + o.getUid() + "'";
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
			String hql = "from InterfaceTransmit a , InterfaceInfo b  where  a.iid=b.iid ";
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

			Condition enable = conditions.get("enable"); // 可用状态
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.enable = '" + StringEscapeUtils.escapeSql(enable.getConditions().get("eq"))
							+ "'";
				}

				if (enable.getConditions().get("match") != null) {
					whereStr += " and a.enable like  '%"
							+ StringEscapeUtils.escapeSql(enable.getConditions().get("match")) + "%'";
				}
			}

			Condition fromaids = conditions.get("fromaids"); // 可订阅来源
			if (fromaids != null) {
				if (fromaids.getConditions().get("eq") != null) {
					whereStr += " and a.fromaids = '" + StringEscapeUtils.escapeSql(fromaids.getConditions().get("eq"))
							+ "'";
				}

				if (fromaids.getConditions().get("match") != null) {
					whereStr += " and a.fromaids like  '%"
							+ StringEscapeUtils.escapeSql(fromaids.getConditions().get("match")) + "%'";
				}
			}

			Condition method = conditions.get("method"); // 方法
			if (method != null) {
				if (method.getConditions().get("eq") != null) {
					whereStr += " and a.method = '" + StringEscapeUtils.escapeSql(method.getConditions().get("eq"))
							+ "'";
				}

				if (method.getConditions().get("match") != null) {
					whereStr += " and a.method like  '%"
							+ StringEscapeUtils.escapeSql(method.getConditions().get("match")) + "%'";
				}
			}

			Condition url = conditions.get("url"); // 转发地址
			if (url != null) {
				if (url.getConditions().get("eq") != null) {
					whereStr += " and a.url = '" + StringEscapeUtils.escapeSql(url.getConditions().get("eq")) + "'";
				}

				if (url.getConditions().get("match") != null) {
					whereStr += " and a.url like  '%" + StringEscapeUtils.escapeSql(url.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition account = conditions.get("account"); // 转发帐号
			if (account != null) {
				if (account.getConditions().get("eq") != null) {
					whereStr += " and a.account = '" + StringEscapeUtils.escapeSql(account.getConditions().get("eq"))
							+ "'";
				}

				if (account.getConditions().get("match") != null) {
					whereStr += " and a.account like  '%"
							+ StringEscapeUtils.escapeSql(account.getConditions().get("match")) + "%'";
				}
			}

			Condition memo = conditions.get("memo"); // 描述
			if (memo != null) {
				if (memo.getConditions().get("eq") != null) {
					whereStr += " and a.memo = '" + StringEscapeUtils.escapeSql(memo.getConditions().get("eq")) + "'";
				}

				if (memo.getConditions().get("match") != null) {
					whereStr += " and a.memo like  '%" + StringEscapeUtils.escapeSql(memo.getConditions().get("match"))
							+ "%'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.iid";

			Query q = em.createQuery("select count(a.uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			if (!"".equals(orderStr)) {
				hql += orderStr;
			}

			hql = "select new InterfaceTransmit(a.uid,a.iid, a.aid, a.ip, a.key, a.fromaids, a.enable, a.method, a.url, a.account, a.password, a.memo, b.name)  "
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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Organization.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;

	}

	@Override
	public QueryResult listApplications(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);

		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));

			String iid = "";
			Condition viid = conditions.get("iid"); // 接口编号
			if (YESUtil.isNotEmpty(viid)) {
				iid = YESUtil.toString(viid.getConditions().get("eq"));
			}

			String eid = "";
			Condition veid = conditions.get("eid"); // eid
			if (YESUtil.isNotEmpty(veid)) {
				eid = YESUtil.toString(viid.getConditions().get("eq"));
			} else {
				eid = BaseUtil.getUser().getEid();// 取本地eid
			}

			String strSearch = "";
			if (parm.containsKey("search")) {
				strSearch = YESUtil.toString(parm.get("search"));
				if (YESUtil.isNotEmpty(strSearch)) {
					strSearch = YESUtil.getLikeStr(strSearch);
					strSearch = " and (a.name like " + strSearch + "  or  a.linkman like " + strSearch
							+ " or a.phone like " + strSearch + " or a.manager like " + strSearch + "  ) ";
				}
			}

			String hql = " from Application a  where a.aid in (select ia.aid from InterfaceTransmit  ia where ia.iid="
					+ YESUtil.getQuotedstr(iid) + " group by  ia.aid) and a.enable=true and a.eid="
					+ YESUtil.getQuotedstr(eid) + strSearch;
			String orderStr = " order by a.aid";

			Query q = em.createQuery("select count(a.uid) " + hql);
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
