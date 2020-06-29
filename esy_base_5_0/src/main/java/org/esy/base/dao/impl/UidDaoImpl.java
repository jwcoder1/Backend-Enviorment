package org.esy.base.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IUidDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Uid;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

@Repository
public class UidDaoImpl implements IUidDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Uid getById(String uid) {
		return em.find(Uid.class, uid);
	}

	@Override
	public Uid save(Uid uid) {
		Uid u = this.getById(uid.getUid());
		if (BaseUtil.isEmpty(u)) {
			em.persist(uid);
		} else {
			uid = em.merge(uid);
		}
		return uid;
	}

	@Override
	public Uid getByUid(String uid, String staffNo, String tempStaffNo, String birthday, String name, String identifyNo,
			String status) {
		String h = "select u from Uid u where 1=1 ";
		Map<String, Object> map = new HashMap<String, Object>();

		if (BaseUtil.isNotEmpty(uid)) {
			h += " and  u.uid=:uid";
			map.put("uid", uid);
		}

		if (BaseUtil.isNotEmpty(name)) {
			h += " and  u.name=:name";
			map.put("name", name);
		}

		if (BaseUtil.isNotEmpty(tempStaffNo)) {
			h += " and  u.tempStaffNo=:tempStaffNo";
			map.put("tempStaffNo", tempStaffNo);
		}

		if (BaseUtil.isNotEmpty(staffNo)) {
			h += " and  u.staffNo=:staffNo";
			map.put("staffNo", staffNo);
		}

		if (BaseUtil.isNotEmpty(birthday)) {
			h += " and  u.birthday=:birthday";
			map.put("birthday", birthday);
		}

		if (BaseUtil.isNotEmpty(identifyNo)) {
			h += " and  u.identifyNo=:identifyNo";
			map.put("identifyNo", identifyNo);
		}

		if (BaseUtil.isNotEmpty(status)) {
			h += " and  u.status=:status";
			map.put("status", status);
		}

		Query query = em.createQuery(h);
		Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<String, Object> entry = entries.next();
			query.setParameter(entry.getKey(), entry.getValue());
		}

		// sql
		@SuppressWarnings("unchecked")
		List<Uid> us = query.getResultList();
		return BaseUtil.isEmpty(us) ? null : us.get(0);
	}

	@Override
	public Uid getByStaffNo(String staffNo, String uid) {
		String h = "select u from Uid  u where u.staffNo=:staffNo and u.uid<>:uid";
		@SuppressWarnings("unchecked")
		List<Uid> us = em.createQuery(h).setParameter("staffNo", staffNo).setParameter("uid", uid).getResultList();
		return BaseUtil.isEmpty(us) ? null : us.get(0);
	}

	@Override
	public boolean isByStaffNo(String staffNo) {
		String h = "select u from  Uid u where u.staffNo=:staffNo";
		Query query = em.createQuery(h).setParameter("staffNo", staffNo);
		@SuppressWarnings("unchecked")
		List<Uid> us = query.getResultList();
		return YESUtil.isNotEmpty(us) ? true : false;
	}

	@Override
	public boolean isByTempStaffNo(String tempStaffNo) {
		String h = "select u from  Uid u where u.tempstaffno=:tempstaffno";
		Query query = em.createQuery(h).setParameter("tempStaffNo", tempStaffNo);
		@SuppressWarnings("unchecked")
		List<Uid> us = query.getResultList();
		return YESUtil.isNotEmpty(us) ? true : false;
	}

	@Override
	public boolean isByUid(String uid) {
		String h = "select u from  Uid u where u.uid=:uid";
		Query query = em.createQuery(h).setParameter("uid", uid);
		@SuppressWarnings("unchecked")
		List<Uid> us = query.getResultList();
		return YESUtil.isNotEmpty(us) ? true : false;

	}

	@Override
	public boolean delete(Uid uid) {
		String hql = "delete Uid where uid='" + uid.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Uid getByStaffNoAndUidForEffected(String staffNo, String uid, String status) {
		String h = "select o from Uid o  where o.uid<>:uid and o.staffNo=:staffNo and o.status=:status ";
		Query query = em.createQuery(h);
		query.setParameter("staffNo", staffNo);
		query.setParameter("uid", uid);
		query.setParameter("status", status);
		List<Uid> us = query.getResultList();
		return YESUtil.isEmpty(us) ? null : us.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Uid getBytempStaffNoAndUidForEffected(String tempstaffNo, String uid, String status) {
		String h = "select o from Uid o  where o.uid<>:uid and o.tempStaffNo=:tempStaffNo and o.status=:status ";
		Query query = em.createQuery(h);
		query.setParameter("tempStaffNo", tempstaffNo);
		query.setParameter("uid", uid);
		query.setParameter("status", status);
		List<Uid> us = query.getResultList();
		return YESUtil.isEmpty(us) ? null : us.get(0);
	}

	public QueryResult query(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);

		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));
			String hql = "  from Uid u  ,  Person p   ";
			String whereStr = " where  p.pid=u.pid  and COALESCE(u.pid,'')<>''  ";

			Condition uid = conditions.get("uid"); // uid
			if (uid != null) {
				if (YESUtil.isNotEmpty(uid.getConditions().get("eq"))) {
					whereStr += " and u.uid = '" + StringEscapeUtils.escapeSql(uid.getConditions().get("eq")) + "'";
				}
				if (YESUtil.isNotEmpty(uid.getConditions().get("match"))) {
					whereStr += " and u.uid like '%" + StringEscapeUtils.escapeSql(uid.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition name = conditions.get("name"); // 姓名
			if (name != null) {
				if (YESUtil.isNotEmpty(name.getConditions().get("match"))) {
					whereStr += " and u.name like '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition status = conditions.get("status"); // 状态
			if (status != null) {
				if (YESUtil.isNotEmpty(status.getConditions().get("eq"))) {
					whereStr += " and u.status = '" + StringEscapeUtils.escapeSql(status.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition birthday = conditions.get("birthday"); // 生日
			if (birthday != null) {
				if (YESUtil.isNotEmpty(birthday.getConditions().get("eq"))) {
					whereStr += " and u.birthday = '" + StringEscapeUtils.escapeSql(birthday.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition staffNo = conditions.get("staffNo"); // 正式员工号
			if (staffNo != null) {
				if (YESUtil.isNotEmpty(staffNo.getConditions().get("match"))) {
					whereStr += " and u.staffNo like '%"
							+ StringEscapeUtils.escapeSql(staffNo.getConditions().get("match")) + "%'";
				}
			}

			Condition tempstaffNo = conditions.get("tempstaffNo"); // 临时员工号
			if (tempstaffNo != null) {
				if (YESUtil.isNotEmpty(tempstaffNo.getConditions().get("match"))) {
					whereStr += " and u.tempstaffNo like '%"
							+ StringEscapeUtils.escapeSql(tempstaffNo.getConditions().get("match")) + "%'";
				}
			}

			Condition eid = conditions.get("eid"); // 企业eid
			if (eid != null) {
				if (YESUtil.isNotEmpty(eid.getConditions().get("eq"))) {
					// whereStr += " and EXISTS (select 1 from Person p where
					// p.pid=u.pid and p.eid = " +
					// YESUtil.getQuotedstr(eid.getConditions().get("eq")) + " )
					// ";
					whereStr += " and p.eid =" + YESUtil.getQuotedstr(eid.getConditions().get("eq"));
				}
			} else {
				// whereStr += " and EXISTS (select 1 from Person p where
				// p.pid=u.pid and p.eid like " +
				// YESUtil.getRightLikeStr(YESUtil.getUser().getEid()) + " ) ";
				whereStr += "  and p.eid like " + YESUtil.getRightLikeStr(BaseUtil.getUser().getEid());
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by u.uid";
			Query q = em.createQuery("select count(u.uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			if (!"".equals(orderStr)) {
				hql += orderStr;
			}

			q = em.createQuery("select u " + hql);

			if (start > 0) {
				q.setFirstResult(start);
			}
			if (count > 0) {
				q.setMaxResults(count);
			} else {
				q.setMaxResults(20);
			}
			qr.setItems(q.getResultList());
			// qr.setHeaders(QueryUtils.getClassFieldInfo(Menu.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Account getByUid(String uid) {
		String hql = " select a from Account a , Uid u  where u.pid=a.matrixNo and u.uid=:uid  and a.type=:type ";
		List<Account> as = (List<Account>) em.createQuery(hql).setParameter("uid", uid).setParameter("type", "user")
				.getResultList();
		return YESUtil.isEmpty(as) ? null : as.get(0);
	}

}
