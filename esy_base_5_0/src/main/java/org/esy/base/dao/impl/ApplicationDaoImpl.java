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
import org.esy.base.dao.IApplicationDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Application;
import org.esy.base.enums.MemberType;
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
public class ApplicationDaoImpl implements IApplicationDao {
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Application> getApplicationsByAccount(Account account) {
		String hql = "select app from Application app,AppAuthority a where app.aid = a.aid" + " and (" + " (a.value='" + account.getAid() + "' and a.type='" + MemberType.Account + "')" + " or (a.value='" + account.getEid()
				+ "' and a.type='" + MemberType.Enterprise + "')"// TODO
																	// 成员,身份等尚未处理
				+ " or (a.value in (select gm.gid from GroupMember gm where (" + "(gm.value='" + account.getAid() + "' and gm.type='" + MemberType.Account + "')" + " or (gm.value='" + account.getEid() + "' and gm.type='"
				+ MemberType.Enterprise + "')" + ")" + ") and a.type='" + MemberType.Group + "')" + ")";
		Query q = em.createQuery(hql);
		return (List<Application>) q.getResultList();
	}

	@Override
	public Application save(Application o) {
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
	public Application getByUid(String uid) {
		return this.em.find(Application.class, uid);
	}

	@Override
	public boolean delete(Application o) {
		String hql = "delete Application where uid='" + o.getUid() + "'";
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
			String order = YESUtil.toString(parm.get("order"));

			String hql = " from Application a ";
			String whereStr = "";

			// agid
			Condition agid = conditions.get("agid");
			boolean flag = false;
			if (agid != null) {
				if (agid.getConditions().get("eq") != null) {
					flag = true;
					hql += " , AppGroupMember b where a.aid=b.appid  and a.eid=b.eid ";
					whereStr += " and b.agid = '" + StringEscapeUtils.escapeSql(agid.getConditions().get("eq")) + "'";
				}
			}

			if (!flag) {
				hql += " where 1=1 ";
			}

			if (parm.containsKey("eid")) {
				whereStr += " and a.eid = '" + parm.get("eid").toString() + "'";
			}

			Condition aid = conditions.get("aid"); // 路径
			if (aid != null) {
				if (aid.getConditions().get("match") != null) {
					whereStr += " and a.aid like '%" + StringEscapeUtils.escapeSql(aid.getConditions().get("match")) + "%'";
				}
			}

			Condition name = conditions.get("name"); // 别名
			if (name != null) {
				if (name.getConditions().get("match") != null) {
					whereStr += " and a.name like '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match")) + "%'";
				}
			}

			Condition domain = conditions.get("domain"); // 别名
			if (domain != null) {
				if (domain.getConditions().get("match") != null) {
					whereStr += " and a.domain like '%" + StringEscapeUtils.escapeSql(domain.getConditions().get("match")) + "%'";
				}
			}

			Condition enable = conditions.get("enable"); // 状态
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.enable = " + StringEscapeUtils.escapeSql(enable.getConditions().get("eq"));
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = "";
			if (!flag) {
				orderStr = " order by a.updated desc";
			} else {
				orderStr = " order by b.seq asc ";
			}

			if (order.equals("name")) {
				orderStr = " order by a.name asc";
			}

			Query q = em.createQuery("select count(a.uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			if (!"".equals(orderStr)) {
				hql += orderStr;
			}

			hql = "select a " + hql;
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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Application.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Application getByIDandPW(String id, String password) {
		String h = "select a from Application a where a.aid=:aid and coalesce(a.password,'-1009')=:password and a.enable=:enable";
		List<Application> as = em.createQuery(h).setParameter("aid", id).setParameter("password", password).setParameter("enable", true).getResultList();
		return BaseUtil.isEmpty(as) ? null : as.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getEidByAid(String aid) {
		String h = "select a.eid from   Application a where a.aid=:aid  and a.enable=:enable";
		List<String> ls = em.createQuery(h).setParameter("aid", aid).setParameter("enable", true).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getNameByAid(String aid) {
		String h = "select a.name from   Application a where a.aid=:aid ";
		List<String> ls = em.createQuery(h).setParameter("aid", aid).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

}
