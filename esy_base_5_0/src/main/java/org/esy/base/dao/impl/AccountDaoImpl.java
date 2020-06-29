package org.esy.base.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IAccountDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Enterprise;
import org.esy.base.entity.Menu;
import org.esy.base.entity.Person;
import org.esy.base.enums.MemberType;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements IAccountDao {

	@PersistenceContext
	private EntityManager em;

	public Account save(Account o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			/*
			 * o.setPassword(BCrypt.hashpw(o.getPassword(), BCrypt.gensalt()));
			 */
			this.em.persist(o);
		} else {
			/*
			 * if(YESUtil.isNotEmpty(o.getPassword())){
			 * o.setPassword(BCrypt.hashpw(o.getPassword(), BCrypt.gensalt()));
			 * }
			 */
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
	}

	public Account getByUid(String uid) {
		return this.em.find(Account.class, uid);
	}

	public boolean delete(Account o) {
		String hql = "delete Account where uid='" + o.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	public QueryResult query(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);
		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));
			String order = YESUtil.toString(parm.get("order"));
			String hql = "from Account a where 1=1", whereStr = "";

			String mode = YESUtil.toString(parm.get("mode"));
			if (mode.equals("notadmin")) {
				whereStr += " and a.type != 'admin'";
			}

			if (parm.containsKey("eid")) {
				String eid = parm.get("eid").toString();
				// whereStr += " and a.eid like '" + eid + "%'" + " and
				// (not(a.matrixNo='" + eid+ "' and a.type='enterprise'))";
				whereStr += " and a.eid like '" + eid + "%' ";
			}

			Condition eid = conditions.get("eid"); // 账号
			if (eid != null) {
				if (eid.getConditions().get("eq") != null) {
					whereStr += " and a.eid = '" + StringEscapeUtils.escapeSql(eid.getConditions().get("eq")) + "'";
				}
				if (eid.getConditions().get("match") != null) {
					whereStr += " and a.eid like '%" + StringEscapeUtils.escapeSql(eid.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition aid = conditions.get("aid"); // 账号
			if (aid != null) {
				if (aid.getConditions().get("eq") != null) {
					whereStr += " and a.aid = '" + StringEscapeUtils.escapeSql(aid.getConditions().get("eq")) + "'";
				}
				if (aid.getConditions().get("match") != null) {
					whereStr += " and a.aid like '%" + StringEscapeUtils.escapeSql(aid.getConditions().get("match"))
							+ "%'";
				}
				if (YESUtil.isNotEmpty(aid.getConditions().get("notin"))) {
					whereStr += " and a.aid not in ('" + aid.getConditions().get("notin").replaceAll(",", "','") + "')";
				}
			}
			Condition type = conditions.get("type"); // 类型
			if (type != null) {
				if (type.getConditions().get("eq") != null) {
					whereStr += " and a.type = '" + StringEscapeUtils.escapeSql(type.getConditions().get("eq")) + "'";
				}
			}
			Condition matrixNo = conditions.get("matrixNo"); // 关联编号
			if (matrixNo != null) {
				if (matrixNo.getConditions().get("eq") != null) {
					whereStr += " and a.matrixNo = '" + StringEscapeUtils.escapeSql(matrixNo.getConditions().get("eq"))
							+ "'";
				}
			}
			Condition name = conditions.get("name"); // 名称
			if (name != null) {
				if (name.getConditions().get("match") != null) {
					whereStr += " and a.name like '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match"))
							+ "%'";
				}
			}
			Condition mobile = conditions.get("mobile"); // 电话
			if (mobile != null) {
				if (mobile.getConditions().get("match") != null) {
					whereStr += " and a.mobile like '%"
							+ StringEscapeUtils.escapeSql(mobile.getConditions().get("match")) + "%'";
				}
			}
			Condition mail = conditions.get("mail"); // 邮件
			if (mail != null) {
				if (mail.getConditions().get("match") != null) {
					whereStr += " and a.mail like '%" + StringEscapeUtils.escapeSql(mail.getConditions().get("match"))
							+ "%'";
				}
			}
			Condition enable = conditions.get("enable"); // 状态
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.enable = " + StringEscapeUtils.escapeSql(enable.getConditions().get("eq"));
				}
			}
			Condition lastLogin = conditions.get("lastLogin"); // 最后登入
			if (lastLogin != null) {
				if (lastLogin.getConditions().get("gte") != null) {
					whereStr += " and a.lastLogin >= '"
							+ StringEscapeUtils.escapeSql(lastLogin.getConditions().get("gte")) + "'";
				}
				if (lastLogin.getConditions().get("lte") != null) {
					whereStr += " and a.lastLogin <= '"
							+ StringEscapeUtils.escapeSql(lastLogin.getConditions().get("lte")) + "'";
				}
			}
			Condition created = conditions.get("created"); // 创建时间起
			if (created != null) {
				if (created.getConditions().get("gte") != null) {
					whereStr += " and a.created >= '" + StringEscapeUtils.escapeSql(created.getConditions().get("gte"))
							+ "'";
				}
				if (created.getConditions().get("lte") != null) {
					whereStr += " and a.created <= '" + StringEscapeUtils.escapeSql(created.getConditions().get("lte"))
							+ "'";
				}
			}
			if (!"".equals(whereStr)) {
				hql += whereStr;
			}
			String orderStr = " order by a.aid";
			if (!"".equals(order)) {
				if (order.equals("lastLogin")) { // 最后登入时间
					orderStr += " order by a.lastLogin desc";
				}
				if (order.equals("created")) { // 创建时间
					orderStr += " order by a.created desc";
				}
				if (order.equals("updated")) { // 创建时间
					orderStr += " order by a.updated desc";
				}
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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Account.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<String> getAllPerdition(String accountId) {

		Account account = getById(accountId);

		List<List<? extends Object>> records;
		String hql = "select new list(m.mid)";
		if (account.getType().equals("admin")) {
			hql += " from Menu m";
		} else {
			hql += "from Menu m, RoleMember rm, RoleMenu ru";
			hql += "where rm.roleId=ru.roleId and rm.accountId='" + account.getAid() + "'";
		}
		records = this.em.createQuery(hql).getResultList();
		HashSet<String> sets = new HashSet<String>();
		for (List<? extends Object> record : records) {
			sets.add(record.get(0).toString());
		}

		return sets;
	}

	@Override
	public Account getById(String id) {
		Account o = null;
		if (!"".equals(id)) {
			String hql = "from Account a where a.aid = '" + StringEscapeUtils.escapeSql(id) + "'";
			Query q = em.createQuery(hql);
			try {
				o = (Account) q.getSingleResult();
			} catch (NoResultException e) {

			}
		}
		return o;
	}

	@Override
	public Account getEnterPriseManger(String eid) {
		Account o = null;
		if (!"".equals(eid)) {
			String hql = "from Account a where a.matrixNo = '" + StringEscapeUtils.escapeSql(eid) + "'"
					+ " and type = 'enterprise'";
			Query q = em.createQuery(hql);
			try {
				o = (Account) q.getSingleResult();
			} catch (NoResultException e) {
			}
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getAllByAccount(String accountId, String pid) {

		String hql = "select new org.esy.base.entity.Menu(m.mid, m.name, m.enable, m.type, m.order, m.icon, m.tip, m.pid, m.url, m.memo, m.home, m.blank, m.expanded, m.color, m.tag)";
		hql += " from Menu m";
		if (!"".equals(pid)) {
			hql += " where m.pid like '" + StringEscapeUtils.escapeSql(pid) + "_%'";
		}

		return this.em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getMenuByAccount(String accountId, String pid) {

		Account account = getById(accountId);

		String hql = "select new org.esy.base.entity.Menu(m.mid, m.name, m.enable, m.type, m.order, m.icon, m.tip, m.pid, m.url, m.memo, m.home, m.blank, m.expanded, m.color, m.tag)";
		if (account.getType().equals("admin")) {
			hql += " from Menu m";
			hql += " where m.pid ='" + StringEscapeUtils.escapeSql(pid) + "'";
			hql += " and m.type='menu'";
		} else {
			hql += " from Menu m,RoleMember rm, RoleMenu ru";
			hql += " where rm.roleId=ru.roleId";
			hql += " and rm.accountId='" + account.getAid() + "'";
			hql += " and m.pid='" + StringEscapeUtils.escapeSql(pid) + "'";
			hql += " and m.type='menu'";
		}

		return this.em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	private Enterprise getClassifyByEid(String eid) {
		String hql = "select e  from Enterprise e where e.eid=:eid";
		List<Enterprise> ls = em.createQuery(hql).setParameter("eid", eid).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getSubAllByAccount(Account account) {
		String hql = "select DISTINCT new org.esy.base.entity.Menu(m.mid, m.name, m.enable, m.type, m.order, m.icon, m.tip, m.pid, m.url, m.memo, m.home, m.blank, m.expanded, m.color, m.tag)";
		if (account.getType().equals("admin")) {
			hql += " from Menu m where m.enable=true order by m.order";
		} else {
			hql += " from Menu m, RoleMember rm, RoleMenu ru "
					+ "where m.mid = ru.menuId and ru.roleId = rm.roleId and m.enable=true and rm.accountId =:accountId order by m.order";
		}
		Query q = em.createQuery(hql);
		if (!account.getType().equals("admin")) {
			q.setParameter("accountId", account.getAid());
		}
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> getSubMenuByAccount(String accountId, String pid) {

		Account account = getById(accountId);

		String hql = "select new org.esy.base.entity.Menu(m.mid, m.name, m.enable, m.type, m.order, m.icon, m.tip, m.pid, m.url, m.memo, m.home, m.blank, m.expanded, m.color, m.tag)";
		if (account.getType().equals("admin")) {
			hql += " from Menu m";
			hql += " where m.type='menu'";
			if (!"".equals(pid)) {
				hql += " and m.pid like '" + StringEscapeUtils.escapeSql(pid) + "_%'";
			}
		} else {
			hql += " from Menu m,RoleMember rm, RoleMenu ru";
			hql += " where rm.roleId=ru.roleId";
			hql += " and rm.accountId='" + account.getAid() + "'";
			hql += " and m.type='menu'";
			if (!"".equals(pid)) {
				hql += " and m.pid like '" + StringEscapeUtils.escapeSql(pid) + "_%'";
			}
		}

		return this.em.createQuery(hql).getResultList();
	}

	/**
	 * 从人员找到人员对应的账号 ->可用的账号
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Account getByPerson(Person p) {
		String hql = " from  Account a where a.matrixNo=:matrixNo and a.enable=:enable  and  a.type=:type";
		List<Account> ls = em.createQuery(hql).setParameter("matrixNo", p.getPid()).setParameter("enable", true)
				.setParameter("type", "user").getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.dao.IAccountDao#getByEidAndMobile(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Account getByEidAndMobile(String eid, String mobile) {
		// TODO Auto-generated method stub
		Account o = null;
		if (!"".equals(eid)) {
			String hql = "from Account a where a.eid = '" + StringEscapeUtils.escapeSql(eid) + "'" + " and mobile = '"
					+ StringEscapeUtils.escapeSql(mobile) + "'";
			Query q = em.createQuery(hql);
			q.setMaxResults(1);
			try {
				o = (Account) q.getSingleResult();
			} catch (NoResultException e) {
			}
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getDoctor(Map<String, Object> parm) {
		String hql = "from Account a where a.type = 'doctor'";
		Query q = em.createQuery(hql);
		return (List<Account>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getByMobile(String mobile) {
		List<Account> a = null;
		if (YESUtil.isNotEmpty(mobile)) {
			String hql = "from Account a where a.mobile = '" + StringEscapeUtils.escapeSql(mobile) + "'";
			Query q = em.createQuery(hql);
			a = (List<Account>) q.getResultList();
		}
		return YESUtil.isNotEmpty(a) ? a : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getAllCountByType(String type) {
		String hql = "from Account a where 1=1";
		if (YESUtil.isNotEmpty(type)) {
			hql += " and a.type = '" + type + "'";
		}
		if (type.equals("user")) {
			hql += " or a.type = 'enterprise";
		}
		return (List<Account>) this.em.createQuery(hql).getResultList();
	}

	@Override
	public Account getByAlias(String alias, String aid) {
		Account o = null;
		String hql = "from Account a where a.aid!='" + aid + "' and a.alias='" + alias + "'";
		Query q = em.createQuery(hql);
		try {
			o = (Account) q.getSingleResult();
		} catch (NoResultException e) {
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> listByEid(String eid) {
		String hql = " from Account a where a.eid=:eid and a.enable=:enable  order by a.aid ";
		Query q = em.createQuery(hql).setParameter("eid", eid).setParameter("enable", true);
		return q.getResultList();
	}

	@Override
	public Account getByMno(String eid) {
		Account o = null;
		if (!"".equals(eid)) {
			String hql = "from Account a where a.matrixNo = '" + StringEscapeUtils.escapeSql(eid) + "'";
			Query q = em.createQuery(hql);
			try {
				o = (Account) q.getSingleResult();
			} catch (NoResultException e) {
			}
		}
		return o;
	}

}
