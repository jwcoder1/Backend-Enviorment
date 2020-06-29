package org.esy.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IRoleMemberDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.RoleMember;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleMemberDaoImpl implements IRoleMemberDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public RoleMember save(RoleMember o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			this.em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
	}

	public RoleMember getByUid(String uid) {
		return this.em.find(RoleMember.class, uid);
	}

	public boolean delete(RoleMember o) {
		String hql = "delete Menu where uid='" + o.getUid() + "'";
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

			String hql = "from RoleMember a where 1=1";

			String whereStr = "";

			Condition roleId = conditions.get("roleId"); // 角色编号
			if (roleId != null) {
				if (roleId.getConditions().get("eq") != null) {
					whereStr += " and a.roleId = '" + StringEscapeUtils.escapeSql(roleId.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition accountId = conditions.get("pid"); // 用户账号
			if (accountId != null) {
				if (accountId.getConditions().get("eq") != null) {
					whereStr += " and a.accountId = '"
							+ StringEscapeUtils.escapeSql(accountId.getConditions().get("eq")) + "'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.roleId, a.accountId";

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
				q.setMaxResults(9999);
			}
			qr.setItems(q.getResultList());
			qr.setHeaders(QueryUtils.getClassFieldInfo(RoleMember.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public RoleMember getById(String roleId, String accountId) {
		RoleMember o = null;
		if (!"".equals(roleId) && !"".equals(accountId)) {
			String hql = "from RoleMember a where a.roleId = '" + StringEscapeUtils.escapeSql(roleId) + "'";
			hql += " and a.accountId = '" + StringEscapeUtils.escapeSql(accountId) + "'";
			Query q = em.createQuery(hql);
			try {
				o = (RoleMember) q.getSingleResult();
			} catch (Exception e) {

			}
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getDetail(String roleId) {
		String hql = "select new org.esy.base.entity.Account(a.aid, a.name)"
				+ "from Account a, RoleMember rm where a.aid = rm.accountId and rm.roleId = :roleId";
		Query q = em.createQuery(hql, Account.class).setParameter("roleId", roleId);
		return q.getResultList();
	}

	@Override
	public boolean delDetail(String rid) {
		String hql = "delete RoleMember where roleId = :roleId";
		Query q = em.createQuery(hql).setParameter("roleId", rid);
		q.executeUpdate();
		return true;
	}

}
