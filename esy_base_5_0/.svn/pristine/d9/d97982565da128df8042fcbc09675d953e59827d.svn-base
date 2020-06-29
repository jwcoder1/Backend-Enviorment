package org.esy.base.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IIdentityDao;
import org.esy.base.entity.Identity;
import org.esy.base.entity.pojo.CustomPojo;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

/**
 * 
 * Dao implement for 身份信息
 *
 */
@Repository
public class IdentityDaoImpl implements IIdentityDao {

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
	public List<Identity> getByPid(String pid) {
		Query q = em.createQuery("from Identity a  where a.pid='" + pid + "' order by a.seq ");
		return (List<Identity>) q.getResultList();
	}

	@Override
	public Identity save(Identity o) {
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
	public Identity getByUid(String uid) {
		return this.em.find(Identity.class, uid);
	}

	@Override
	public boolean delete(Identity o) {
		String hql = "delete Identity where uid='" + o.getUid() + "'";
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
			@SuppressWarnings("unused")
			String order = YESUtil.toString(parm.get("order"));

			String hql = "from Identity a where 1=1";

			String whereStr = "";

			Condition oid = conditions.get("oid"); // 组织编号
			if (oid != null) {
				if (oid.getConditions().get("eq") != null) {
					whereStr += " and a.oid = '" + StringEscapeUtils.escapeSql(oid.getConditions().get("eq")) + "'";
				}
			}

			Condition eid = conditions.get("eid"); // 企业路径
			if (eid != null) {
				if (eid.getConditions().get("match") != null) {
					whereStr += " and a.eid like '%" + StringEscapeUtils.escapeSql(eid.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition pid = conditions.get("pid"); // 人员编号
			if (pid != null) {
				if (pid.getConditions().get("eq") != null) {
					whereStr += " and a.pid = '" + StringEscapeUtils.escapeSql(pid.getConditions().get("eq")) + "'";
				}
			}

			Condition positionId = conditions.get("positionId"); // 职务编号
			if (positionId != null) {
				if (positionId.getConditions().get("match") != null) {
					whereStr += " and a.positionId like '%"
							+ StringEscapeUtils.escapeSql(positionId.getConditions().get("match")) + "%'";
				}
			}

			Condition postId = conditions.get("postId"); // 岗位编号
			if (postId != null) {
				if (postId.getConditions().get("eq") != null) {
					whereStr += " and a.postId = '" + StringEscapeUtils.escapeSql(postId.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition enable = conditions.get("enable"); // 状态
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.enable = " + StringEscapeUtils.escapeSql(enable.getConditions().get("eq"));
				}
			}

			Condition type = conditions.get("type"); // 类型
			if (type != null) {
				if (type.getConditions().get("eq") != null) {
					whereStr += " and a.type = '" + StringEscapeUtils.escapeSql(type.getConditions().get("eq")) + "'";
				}
			}

			Condition startDate = conditions.get("startDate"); // 开始日期
			if (startDate != null) {
				if (startDate.getConditions().get("gte") != null) {
					whereStr += " and a.startDate >= '"
							+ StringEscapeUtils.escapeSql(startDate.getConditions().get("gte")) + "'";
				}
				if (startDate.getConditions().get("lte") != null) {
					whereStr += " and a.startDate <= '"
							+ StringEscapeUtils.escapeSql(startDate.getConditions().get("lte")) + "'";
				}
			}

			Condition toDate = conditions.get("toDate"); // 结束日期
			if (toDate != null) {
				if (toDate.getConditions().get("gte") != null) {
					whereStr += " and a.toDate >= '" + StringEscapeUtils.escapeSql(toDate.getConditions().get("gte"))
							+ "'";
				}
				if (toDate.getConditions().get("lte") != null) {
					whereStr += " and a.toDate <= '" + StringEscapeUtils.escapeSql(toDate.getConditions().get("lte"))
							+ "'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = "";
			orderStr = " order by a.seq asc ";

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Identity.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public boolean exsitsInOids(List<String> oids) {
		String h = "select count(uid) from Identity where oid in (:oid) ";
		Long a = (Long) em.createQuery(h).setParameter("oid", oids).getSingleResult();
		return a > 0;
	}

	@Override
	public boolean hadInConditions(Object... obj) {

		String oid = "";
		String positionId = "";
		String postId = "";
		String eid = "";

		if (obj.length > 0 && YESUtil.isNotEmpty(obj[0])) {
			oid = YESUtil.toString(obj[0]);
		}
		if (obj.length > 1 && YESUtil.isNotEmpty(obj[1])) {
			positionId = YESUtil.toString(obj[1]);
		}
		if (obj.length > 2 && YESUtil.isNotEmpty(obj[2])) {
			postId = YESUtil.toString(obj[2]);
		}
		if (obj.length > 3 && YESUtil.isNotEmpty(obj[3])) {
			eid = YESUtil.toString(obj[3]);
		}

		if (BaseUtil.isEmpty(oid) && BaseUtil.isEmpty(positionId) && BaseUtil.isEmpty(postId))
			return false;
		String h = "select count(uid) from Identity where   1=1 ";
		if (!BaseUtil.isEmpty(oid))
			h += " and oid=:oid";
		if (!BaseUtil.isEmpty(positionId))
			h += " and positionId=:positionId";
		if (!BaseUtil.isEmpty(postId))
			h += " and postId=:postId";
		if (!BaseUtil.isEmpty(eid))
			h += " and eid=:eid";
		Query qry = em.createQuery(h);

		//
		if (!BaseUtil.isEmpty(oid))
			qry.setParameter("oid", oid);
		if (!BaseUtil.isEmpty(positionId))
			qry.setParameter("positionId", positionId);
		if (!BaseUtil.isEmpty(postId))
			qry.setParameter("postId", postId);
		if (!BaseUtil.isEmpty(postId))
			qry.setParameter("eid", eid);

		long l = (long) qry.getSingleResult();
		return l > 0;
	}

	@Override
	public boolean deleteByPid(String pid) {
		String h = "delete Identity o where o.pid=:pid ";
		Query q = em.createQuery(h).setParameter("pid", pid);
		int i = q.executeUpdate();
		return i > -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listPostNameByPid(String pid) {
		String h = " select  new org.esy.base.entity.pojo.CustomPojo( p.name,o.type , o.seq)   from Identity o ,Post p  where o.pid=:pid and  o.postId=p.pid and  o.pid is not null  group by p.name,o.type , o.seq  order by o.type , o.seq  ";
		List<CustomPojo> cs = em.createQuery(h).setParameter("pid", pid).getResultList();
		List<String> hs = new ArrayList<String>();
		if (YESUtil.isNotEmpty(cs)) {
			for (CustomPojo c : cs) {
				hs.add(YESUtil.toString(c.getStr1()));
			}
		}
		return hs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listPositionNameByPid(String pid) {
		String h = " select  new org.esy.base.entity.pojo.CustomPojo( ps.name,o.type , o.seq)    from Identity o ,Position ps  where o.pid=:pid and  ps.pid=o.positionId and o.positionId is not null group by  ps.name,o.type , o.seq order by o.type , o.seq ";
		List<CustomPojo> cs = em.createQuery(h).setParameter("pid", pid).getResultList();
		List<String> hs = new ArrayList<String>();
		if (YESUtil.isNotEmpty(cs)) {
			for (CustomPojo c : cs) {
				hs.add(YESUtil.toString(c.getStr1()));
			}
		}
		return hs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, String>> listPositionIdNameByPid(String pid) {
		String h = " select  new org.esy.base.entity.pojo.CustomPojo( ps.name, ps.pid , o.seq)    from Identity o ,Position ps  where o.pid=:pid and  ps.pid=o.positionId and o.positionId is not null group by  ps.name, ps.pid, o.type, o.seq order by o.type , o.seq ";
		List<CustomPojo> cs = em.createQuery(h).setParameter("pid", pid).getResultList();
		List<Map<String, String>> hs = new ArrayList<Map<String, String>>();
		if (YESUtil.isNotEmpty(cs)) {
			for (CustomPojo c : cs) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("pid", c.getStr2());
				map.put("name", c.getStr1());
				hs.add(map);
			}
		}
		// String h = " select distinct new map(ps.pid as pid,ps.name as name )
		// from Identity o ,Position ps where o.pid=:pid and ps.pid=o.positionId
		// and o.positionId is not null order by o.type , o.seq ";
		// return em.createQuery(h).setParameter("pid", pid).getResultList();
		return hs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listOrgNameByPid(String pid) {
		String h = " select    new org.esy.base.entity.pojo.CustomPojo( og.name, o.type , o.seq  ) from Identity o ,Organization og  where o.pid=:pid and  og.oid=o.oid  and o.oid is not null  group by og.name, o.type , o.seq order by o.type , o.seq ";
		List<CustomPojo> cs = em.createQuery(h).setParameter("pid", pid).getResultList();
		List<String> hs = new ArrayList<String>();
		if (YESUtil.isNotEmpty(cs)) {
			for (CustomPojo c : cs) {
				hs.add(YESUtil.toString(c.getStr1()));
			}
		}
		return hs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Identity> getByPidAndEnable(String pid) {
		Query q = em.createQuery("from Identity a  where a.pid=:pid  and a.enable=:enable ");
		return (List<Identity>) q.setParameter("pid", pid).setParameter("enable", true).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listOidByPid(String pid) {
		Query q = em.createQuery("select a.oid  from Identity a  where a.pid=:pid group by a.oid ");
		return (List<String>) q.setParameter("pid", pid).getResultList();
	}

	@Override
	public boolean exsitsInEid(String eid) {
		Query q = em.createQuery("select count(1)  from Identity a  where a.eid=:eid ");
		Long l = (Long) q.setParameter("eid", eid).getSingleResult();
		return l > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Identity> listtest() {
		String h = " select a  from Identity a  where  a.uid='5b4022b6f317482784b54264817f3b9e' and  COALESCE(date_format(a.toDate, '%YY-%MM-%DD'),'3001-01-01')> '2016-06-28'  ";
		return em.createQuery(h).getResultList();
	}

	@Override
	public boolean deleteByPidAndEid(String pid, String eid) {
		String h = "delete Identity o where o.pid=:pid  and eid=:eid ";
		Query q = em.createQuery(h).setParameter("pid", pid).setParameter("eid", eid);
		int i = q.executeUpdate();
		return i > -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Identity getByPidAndOid(String pid, String oid) {
		String hql = "from Identity i where i.pid=:pid and i.oid=:oid";
		Query q = em.createQuery(hql).setParameter("pid", pid).setParameter("oid", oid);
		List<Identity> list = q.getResultList();
		return YESUtil.isNotEmpty(list) ? list.get(0) : null;
	}

	@Override
	public boolean moveMainBypid(String pid) {
		String hql = "update Identity i set i.enable=:enable, i.isMain=:isMain where i.pid=:pid";
		Query q = em.createQuery(hql);
		q.setParameter("enable", false);
		q.setParameter("isMain", false);
		q.setParameter("pid", pid);

		return q.executeUpdate() > 0;
	}

}
