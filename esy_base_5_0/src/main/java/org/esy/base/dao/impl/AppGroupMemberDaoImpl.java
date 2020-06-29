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
import org.esy.base.dao.IAppGroupMemberDao;
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
public class AppGroupMemberDaoImpl implements IAppGroupMemberDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public AppGroupMember save(AppGroupMember o) {
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
	public AppGroupMember getByUid(String uid) {
		return this.em.find(AppGroupMember.class, uid);
	}

	@Override
	public boolean delete(AppGroupMember o) {
		String hql = "delete AppGroupMember where uid='" + o.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int deleteByValuesAndType(String[] values, String type) {
		String hql = "delete AppGroupMember where appid in :values and type='" + type + "'";
		Query q = em.createQuery(hql);
		q.setParameter("values", Arrays.asList(values));
		return q.executeUpdate();
	}

	@Override
	public boolean deleteByAgid(String agid) {
		String hql = "delete AppGroupMember where agid='" + agid + "'";
		Query q = em.createQuery(hql);
		q.executeUpdate();
		return true;
	}

	@Override
	public boolean deleteByAid(String aid) {
		String hql = "delete AppGroupMember where appid='" + aid + "'";
		Query q = em.createQuery(hql);
		q.executeUpdate();
		return true;
	}

	@Override
	public int updateShowByValue(String value, String newShow) {
		String hql = "update AppGroupMember g set g.show='" + newShow + "'" + " where g.appid='" + value + "'";
		Query q = em.createQuery(hql);
		return q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppGroupMember> getAppGroupMemberByAppidsAndAgid(String[] appids, String agid) {
		String hql = "from AppGroupMember gm where gm.agid='" + agid + "' and gm.appid in :appids";
		Query q = em.createQuery(hql);
		q.setParameter("appids", Arrays.asList(appids));
		return (List<AppGroupMember>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppGroupMember> getAppGroupMemberByAppids(List<String> appids) {
		String hql = "from AppGroupMember gm where gm.appid in :appids";
		Query q = em.createQuery(hql);
		q.setParameter("appids", appids);
		return (List<AppGroupMember>) q.getResultList();
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);

		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));

			String hql = "  from AppGroupMember a , Application b  where  a.appid=b.aid ";

			String whereStr = "";

			if (parm.containsKey("eid")) {
				whereStr += " and a.eid = '" + parm.get("eid").toString() + "'";
			}

			Condition agid = conditions.get("agid"); // 群组编号
			if (agid != null) {
				if (agid.getConditions().get("eq") != null) {
					whereStr += " and a.agid = '" + StringEscapeUtils.escapeSql(agid.getConditions().get("eq")) + "'";
				}
			}

			Condition show = conditions.get("show"); // 群组类型
			if (show != null) {
				if (show.getConditions().get("match") != null) {
					whereStr += " and a.show like '%" + StringEscapeUtils.escapeSql(show.getConditions().get("match")) + "%'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			Query q = em.createQuery("select count(a.uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			String orderStr = " order by a.updated desc";

			if (!"".equals(orderStr)) {
				hql += orderStr;
			}
			hql="select new AppGroupMember( a.uid,a.eid, a.agid, a.appid, b.name , a.seq)  "+hql;
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

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public boolean deleteByAppidAndEid(String appid, String eid) {
		String h = " delete AppGroupMember a where a.eid=:eid and a.appid=:appid";
		try {
			@SuppressWarnings("unused")
			long l = (long) em.createQuery(h).setParameter("appid", appid).setParameter("eid", eid).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppGroupMember> listByAppidAndEid(String appid, String eid) {
		String h = " from AppGroupMember a where a.eid=:eid and a.appid=:appid order by a.seq";
		return em.createQuery(h).setParameter("appid", appid).setParameter("eid", eid).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listAgidByAppidAndEid(String appid, String eid) {
		String h = "select a.agid from AppGroupMember a where a.eid=:eid and a.appid=:appid order by a.seq";
		return em.createQuery(h).setParameter("appid", appid).setParameter("eid", eid).getResultList();
	}

	@Override
	public boolean deleteByAppidAndEidAndAgid(String appid, String agid, String eid) {
		String h = " delete AppGroupMember a where a.eid=:eid and a.appid=:appid and a.agid=:agid";
		try {
			@SuppressWarnings("unused")
			long l = (long) em.createQuery(h).setParameter("appid", appid).setParameter("eid", eid).setParameter("agid", agid).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Integer getMaxSeq(String agid, String eid) {
		String h = "select coalesce(max(a.seq),0) from AppGroupMember a where a.eid=:eid and a.agid=:agid ";
		return YESUtil.objtoint(em.createQuery(h).setParameter("eid", eid).setParameter("agid", agid).getSingleResult(), 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getSeqByAppidAndEidAndAgid(String appid, String agid, String eid) {
		String h = " select a.seq from  AppGroupMember a where a.eid=:eid and a.appid=:appid and a.agid=:agid";
		List<Integer> is = em.createQuery(h).setParameter("appid", appid).setParameter("eid", eid).setParameter("agid", agid).getResultList();
		return YESUtil.isEmpty(is) ? null : YESUtil.objtoint(is.get(0), 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AppGroupMember getByAppidAndEidAndAgid(String appid, String agid, String eid) {
		String h = " select a from  AppGroupMember a where a.eid=:eid and a.appid=:appid and a.agid=:agid";
		List<AppGroupMember> is = em.createQuery(h).setParameter("appid", appid).setParameter("eid", eid).setParameter("agid", agid).getResultList();
		return YESUtil.isEmpty(is) ? null : is.get(0);
	}

}
