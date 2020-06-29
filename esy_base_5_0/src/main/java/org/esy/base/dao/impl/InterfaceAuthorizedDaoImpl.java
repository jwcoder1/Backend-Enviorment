package org.esy.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IInterfaceAuthorizedDao;
import org.esy.base.entity.InterfaceAuthorized;
import org.esy.base.entity.Organization;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

@Repository
public class InterfaceAuthorizedDaoImpl implements IInterfaceAuthorizedDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public InterfaceAuthorized save(InterfaceAuthorized o) {
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
	public InterfaceAuthorized getByUid(String uid) {
		return em.find(InterfaceAuthorized.class, uid);
	}

	@Override
	public boolean delete(InterfaceAuthorized o) {
		String hql = "delete InterfaceAuthorized where uid='" + o.getUid() + "'";
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
			String hql = " from InterfaceAuthorized a , InterfaceInfo b  where a.iid=b.iid ";
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
			hql = "select new InterfaceAuthorized(a.uid,a.iid, a.aid, a.password, a.ip, a.key, a.enable, b.url, b.name)   "
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

	@SuppressWarnings("unchecked")
	@Override
	public InterfaceAuthorized getByIdPassword(String iid, String aid, String password) {
		String h = " select o from  InterfaceAuthorized o where o.iid=:iid and o.aid=:aid and o.key=:key and o.enable=:enable ";
		List<InterfaceAuthorized> ias = em.createQuery(h).setParameter("iid", iid).setParameter("aid", aid)
				.setParameter("key", password).setParameter("enable", true).getResultList();
		return YESUtil.isEmpty(ias) ? null : ias.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getEidFromAid(String aid) {
		String h = "select  a.eid from  InterfaceAuthorized o  , Application a  where a.aid=o.aid and a.enable=:enable and o.aid=:aid ";
		List<String> aids = em.createQuery(h).setParameter("aid", aid).setParameter("enable", true).getResultList();
		return BaseUtil.isEmpty(aids) ? null : aids.get(0);
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

			String hql = " from Application a  where a.aid in (select  ia.aid from InterfaceAuthorized  ia where ia.iid="
					+ YESUtil.getQuotedstr(iid) + " group by  ia.aid  ) and a.enable=true and a.eid="
					+ YESUtil.getQuotedstr(eid) + strSearch;
			String orderStr = " order by a.aid";

			Query q = em.createQuery("select count( a.uid) " + hql);
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
