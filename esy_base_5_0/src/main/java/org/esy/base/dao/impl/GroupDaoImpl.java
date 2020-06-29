package org.esy.base.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IGroupDao;
import org.esy.base.entity.Group;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

/**
 * 
 * Dao implement for 群组信息
 *
 */
@Repository
public class GroupDaoImpl implements IGroupDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Group save(Group o) {
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
	public Group getByUid(String uid) {
		return this.em.find(Group.class, uid);
	}

	@Override
	public boolean delete(Group o) {
		String hql = "delete Group where uid='" + o.getUid() + "'";
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

			String hql = "from Group a where 1=1";
			String whereStr = "";

			if (parm.containsKey("eid")) {
				whereStr += " and a.eid = '" + parm.get("eid").toString() + "'";
			}

			Condition eid = conditions.get("eid"); // 企业路径
			if (eid != null) {
				if (eid.getConditions().get("match") != null) {
					whereStr += " and a.eid like '%" + StringEscapeUtils.escapeSql(eid.getConditions().get("match")) + "%'";
				}
			}

			Condition gid = conditions.get("gid"); // 编号
			if (gid != null) {
				if (gid.getConditions().get("eq") != null) {
					whereStr += " and a.gid = '" + StringEscapeUtils.escapeSql(gid.getConditions().get("eq")) + "'";
				}
				if (gid.getConditions().get("match") != null) {
					whereStr += " and (a.gid like '%" + StringEscapeUtils.escapeSql(gid.getConditions().get("match")) + "%'" + " or a.myid like '%" + StringEscapeUtils.escapeSql(gid.getConditions().get("match")) + "%')";
				}
			}

			Condition name = conditions.get("name"); // 名称
			if (name != null) {
				if (name.getConditions().get("match") != null) {
					whereStr += " and a.name like '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match")) + "%'";
				}
			}

			Condition type = conditions.get("type"); // 类型
			if (type != null) {
				if (type.getConditions().get("eq") != null) {
					whereStr += " and a.type = '" + StringEscapeUtils.escapeSql(type.getConditions().get("eq")) + "'";
				}
			}

			Condition describe = conditions.get("describe"); // 描述
			if (describe != null) {
				if (describe.getConditions().get("match") != null) {
					whereStr += " and a.describe like '%" + StringEscapeUtils.escapeSql(describe.getConditions().get("match")) + "%'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			Query q = em.createQuery("select count(uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);
			
			String orderStr = "";
			orderStr = " order by a.created desc";

			if (order.equals("gid")) {
				orderStr = " order by a.gid asc";
			}

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Group.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}
}
