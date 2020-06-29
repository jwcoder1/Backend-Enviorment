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
import org.esy.base.dao.IDictionaryDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Dictionary;
import org.esy.base.entity.Role;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DictionaryDaoImpl implements IDictionaryDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public Dictionary save(Dictionary o) {
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
	public Dictionary getByUid(String uid) {
		return this.em.find(Dictionary.class, uid);
	}

	@Override
	@Transactional
	public boolean delete(Dictionary o) {
		String hql = "delete Dictionary where uid='" + o.getUid() + "'";
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

			String hql = "from Dictionary a where 1=1";
			String whereStr = "";

			Account user = BaseUtil.getUser();
			String utype = user.getType();
			if (!"admin".equalsIgnoreCase(utype)) {// 平台管理员
				whereStr += " and  a.type = 'ENT'  ";
			}

			Condition model = conditions.get("model"); // 模块名
			if (model != null) {
				if (model.getConditions().get("match") != null) {
					whereStr += " and a.model like '%" + StringEscapeUtils.escapeSql(model.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition id = conditions.get("id"); // 编号
			if (id != null) {
				if (id.getConditions().get("eq") != null) {
					whereStr += " and a.id = '" + StringEscapeUtils.escapeSql(id.getConditions().get("eq")) + "'";
				}
			}

			Condition name = conditions.get("name"); // 名称
			if (name != null) {
				if (name.getConditions().get("match") != null) {
					whereStr += " and a.name like '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition type = conditions.get("type"); // type
			if (type != null) {
				if (type.getConditions().get("eq") != null) {
					whereStr += " and a.type = '" + StringEscapeUtils.escapeSql(type.getConditions().get("eq")) + "'";
				}
			}

			Condition status = conditions.get("status"); // status
			if (status != null) {
				if (status.getConditions().get("eq") != null) {
					whereStr += " and a.status = '" + StringEscapeUtils.escapeSql(status.getConditions().get("eq"))
							+ "'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.seq";

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Role.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Dictionary getByID(String id) {
		String h = " from Dictionary o where o.id=:id";
		List<Dictionary> ds = em.createQuery(h).setParameter("id", id).getResultList();
		return YESUtil.isEmpty(ds) ? null : ds.get(0);
	}

}
