package org.esy.base.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IRoleMenuDao;
import org.esy.base.entity.RoleMenu;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleMenuDaoImpl implements IRoleMenuDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public RoleMenu save(RoleMenu o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			this.em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
	}

	public RoleMenu getByUid(String uid) {
		return this.em.find(RoleMenu.class, uid);
	}

	public boolean delete(RoleMenu o) {
		String hql = "delete RoleMenu where uid='" + o.getUid() + "'";
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

			String hql = "from RoleMenu a where 1=1";

			String whereStr = "";

			Condition menuId = conditions.get("menuId"); // 菜单编号
			if (menuId != null) {
				if (menuId.getConditions().get("eq") != null) {
					whereStr += " and a.menuId = '" + StringEscapeUtils.escapeSql(menuId.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition roleId = conditions.get("roleId"); // 角色编号
			if (roleId != null) {
				if (roleId.getConditions().get("eq") != null) {
					whereStr += " and a.roleId = '" + StringEscapeUtils.escapeSql(roleId.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition enable = conditions.get("enable"); // 是否生效
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.enable = " + StringEscapeUtils.escapeSql(enable.getConditions().get("eq"));
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = "";

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(RoleMenu.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public RoleMenu getById(String roleId, String menuId) {
		RoleMenu o = null;
		if (!"".equals(roleId) && !"".equals(menuId)) {
			String hql = "from RoleMenu a where a.roleId = '" + StringEscapeUtils.escapeSql(roleId) + "'";
			hql += " and a.menuId = '" + StringEscapeUtils.escapeSql(menuId) + "'";
			Query q = em.createQuery(hql);
			try {
				o = (RoleMenu) q.getSingleResult();
			} catch (Exception e) {

			}
		}
		return o;
	}

	@Override
	public boolean delDetail(String rid) {
		String hql = "delete RoleMenu where roleId = :roleId";
		Query q = em.createQuery(hql).setParameter("roleId", rid);
		q.executeUpdate();
		return true;
	}

}
