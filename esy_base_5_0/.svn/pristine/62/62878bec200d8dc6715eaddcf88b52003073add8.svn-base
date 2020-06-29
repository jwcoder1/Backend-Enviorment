package org.esy.base.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IAppAuthorityDao;
import org.esy.base.entity.AppAuthority;
import org.esy.base.entity.AppGroupMember;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

/**
 * 
 * Dao implement for 群组成员
 *
 */
@Repository
public class AppAuthorityDaoImpl implements IAppAuthorityDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public boolean deleteByAid(String aid) {
		String hql = "delete AppAuthority where aid='" + aid + "'";
		Query q = em.createQuery(hql);
		q.executeUpdate();
		return true;
	}

	@Override
	public int deleteByValuesAndType(String[] values, String type) {
		String hql = "delete AppAuthority where value in :values and type='" + type + "'";
		Query q = em.createQuery(hql);
		q.setParameter("values", Arrays.asList(values));
		return q.executeUpdate();
	}

	@Override
	public AppAuthority save(AppAuthority o) {
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
	public AppAuthority getByUid(String uid) {
		return this.em.find(AppAuthority.class, uid);
	}

	@Override
	public boolean delete(AppAuthority o) {
		String hql = "delete AppAuthority where uid='" + o.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int updateShowByValueAndType(String type, String value, String newShow) {
		String hql = "update AppAuthority g set g.show='" + newShow + "'" + " where g.value='" + value + "' and g.type='" + type + "'";
		Query q = em.createQuery(hql);
		return q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppAuthority> getAppAuthorityByValuesAndTypeAndAid(String[] values, String type, String aid) {
		String hql = "from AppAuthority gm where gm.value in :values" + " and gm.type='" + type + "' and gm.aid='" + aid + "'";
		Query q = em.createQuery(hql);
		q.setParameter("values", Arrays.asList(values));
		return (List<AppAuthority>) q.getResultList();
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);

		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));
			String hql = "from AppAuthority a where 1=1";
			String whereStr = "";

			Condition eid = conditions.get("eid"); // 企业路径
			if (eid != null) {
				if (eid.getConditions().get("eq") != null) {
					whereStr += " and a.eid = '" + StringEscapeUtils.escapeSql(eid.getConditions().get("eq")) + "'";
				}
				if (eid.getConditions().get("match") != null) {
					whereStr += " and a.eid like ''" + StringEscapeUtils.escapeSql(eid.getConditions().get("match")) + "%'";
				}
			}

			if (parm.containsKey("eid")) {
				String veid = YESUtil.toString(parm.get("eid"));
				whereStr += " and a.eid = '" + veid + "'";
			}

			Condition search = conditions.get("search"); // 多搜索
			if (search != null) {
				if (search.getConditions().get("match") != null) {
					whereStr += " and (a.show like '%" + StringEscapeUtils.escapeSql(search.getConditions().get("match")) + "%'" + " or a.value like '%" + StringEscapeUtils.escapeSql(search.getConditions().get("match")) + "%')";
				}
			}

			Condition type = conditions.get("type"); // 群组类型
			if (type != null) {
				if (type.getConditions().get("eq") != null) {
					whereStr += " and a.type = '" + StringEscapeUtils.escapeSql(type.getConditions().get("eq")) + "'";
				}
			}

			Condition aid = conditions.get("aid"); // 群组类型
			if (aid != null) {
				if (aid.getConditions().get("eq") != null) {
					whereStr += " and a.aid = '" + StringEscapeUtils.escapeSql(aid.getConditions().get("eq")) + "'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.type  ,  a.created asc";

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(AppAuthority.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppAuthority> listByAid(String aid) {
		String h = " from AppAuthority a where a.aid=:aid ";
		return em.createQuery(h).setParameter("aid", aid).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppAuthority> findByPerson(String sql) {
		String hql = "select distinct a  from  AppAuthority a where " + sql;
		return em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppGroupMember> findApplicationByPerson(String sql) {
		String hql = "	select new AppGroupMember( am.uid,am.eid, am.agid, am.appid, b.name , am.seq) from  AppGroupMember am , Application b  where  am.appid=b.aid and b.aid in  (select  a.aid  from  AppAuthority a  where " + sql
				+ " group by a.aid ) ";
		return em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppGroupMember> findByAppAuthority(Map<String, Object> parm) {
		String sql = "";
		String type = YESUtil.toString(parm.get("type"));
		sql += " a.type=" + YESUtil.getQuotedstr(type);

		String value = YESUtil.toString(parm.get("value"));
		sql += " and  a.value=" + YESUtil.getQuotedstr(value);

		String type2 = YESUtil.toString(parm.get("type2"));
		if (YESUtil.isNotEmpty(type2)) {
			sql += " and  a.type2=" + YESUtil.getQuotedstr(type2);
		}

		String value2 = YESUtil.toString(parm.get("value2"));
		if (YESUtil.isNotEmpty(value2)) {
			sql += " and  a.value2=" + YESUtil.getQuotedstr(value2);
		}

		String hql = "	select new AppGroupMember( am.uid,am.eid, am.agid, am.appid, b.name , am.seq) from  AppGroupMember am , Application b  where  am.appid=b.aid and b.aid in  (select  a.aid  from  AppAuthority a  where " + sql
				+ " group by a.aid ) ";
		return em.createQuery(hql).getResultList();
	}

}
