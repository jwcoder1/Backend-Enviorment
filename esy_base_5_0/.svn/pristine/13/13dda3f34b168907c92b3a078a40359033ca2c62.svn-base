package org.esy.base.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IOrganizationDao;
import org.esy.base.dao.IPositionDao;
import org.esy.base.entity.Position;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * Dao implement for 职务信息
 *
 */
@Repository
public class PositionDaoImpl implements IPositionDao {

	private EntityManager em;

	@Autowired
	private IOrganizationDao organizationDao;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Position save(Position o) {
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
	public Position getByUid(String uid) {
		return this.em.find(Position.class, uid);
	}

	@Override
	public boolean delete(Position o) {
		String hql = "delete Position where uid='" + o.getUid() + "'";
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

			String hql = "from Position a  , Organization o where a.oid=o.oid";
			String whereStr = "";

			String veid = null;
			if (parm.containsKey("eid")) {
				veid = YESUtil.toString(parm.get("eid"));
				whereStr += " and a.eid= '" + veid + "'";
			}

			Condition oid = conditions.get("oid"); // 组织编号
			if ((oid != null) && YESUtil.isNotEmpty(veid)) {
				if (oid.getConditions().get("eq") != null) {
					String coid = YESUtil.toString(oid.getConditions().get("eq"));
					coid = organizationDao.getPath(veid, coid);
					if (YESUtil.isNotEmpty(coid)) {
						if (coid.indexOf(".") < 0) {
							whereStr += " and a.oid = '" + StringEscapeUtils.escapeSql(oid.getConditions().get("eq")) + "'";
						} else {
							String[] arr = coid.split("\\u002E");
							String hstr = "";
							String buff = "";
							for (String ar : arr) {
								hstr += buff + "'" + ar + "'";
								buff = ",";
							}
							hstr = "(" + hstr + ")";
							whereStr += " and a.oid in   " + hstr;
						}
					}
				}
			}

			Condition pid = conditions.get("pid"); // 编号
			if (pid != null) {
				if (pid.getConditions().get("eq") != null) {
					whereStr += " and a.pid = '" + StringEscapeUtils.escapeSql(pid.getConditions().get("eq")) + "'";
				}
			}

			Condition name = conditions.get("name"); // 名称
			if (name != null) {
				if (name.getConditions().get("match") != null) {
					whereStr += " and a.name like '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match")) + "%'";
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
			orderStr = " order by a.seq";

			if (order.equals("oid")) {
				orderStr = " order by a.oid asc";
			}

			if (order.equals("pid")) {
				orderStr = " order by a.pid asc";
			}

			Query q = em.createQuery("select count(a.uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			if (!"".equals(orderStr)) {
				hql += orderStr;
			}

			hql = "select new Position(a.uid, a.eid, a.oid, a.pid, a.showid, a.name, a.py, a.seq, a.enable, o.name)" + hql;
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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Position.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return qr;
	}

	@Override
	public boolean deleteByOids(List<String> oids) {
		if (BaseUtil.isEmpty(oids))
			return false;
		String h = "delete Position p where p.oid in (:oid)";
		try {
			em.createQuery(h).setParameter("oid", oids).executeUpdate();
		} catch (Exception e) {
			//
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Position> listByOid(String oid) {
		String h = "select p from  Position p where  p.oid =:oid order by seq ";
		return em.createQuery(h).setParameter("oid", oid).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Position> listByOids(List<String> oid, String rootpid, Boolean enable) {
		String h = "select new Position(a.uid, a.eid, a.oid, a.pid, a.showid, a.name, a.py, a.seq, a.enable, o.name,a.rootpid)   from Position a  , Organization o where a.oid=o.oid and  a.oid in (:oid) and a.rootpid=:rootpid ";
		if (YESUtil.isNotEmpty(enable)) {
			if (enable)
				h += " and  a.enable=true ";
			else
				h += " and  a.enable=false ";
		}
		h += "order by a.seq ";
		return em.createQuery(h).setParameter("oid", oid).setParameter("rootpid", rootpid).getResultList();
	}

	@Override
	public long countByNameAndOid(String oid, String name, String pid, String uid, String eid) {
		String h = "select count(p.uid) from  Position p where    p.eid=:eid";
		if (BaseUtil.isNotEmpty(oid))
			h += " and  p.oid=:oid";
		if (BaseUtil.isNotEmpty(uid))
			h += " and uid<>:uid";
		if (BaseUtil.isNotEmpty(name))
			h += " and p.name=:name ";
		if (BaseUtil.isNotEmpty(pid))
			h += " and p.pid=:pid ";

		Query query = em.createQuery(h);
		query.setParameter("eid", eid);
		if (BaseUtil.isNotEmpty(oid))
			query.setParameter("oid", oid);
		if (BaseUtil.isNotEmpty(uid))
			query.setParameter("uid", uid);
		if (BaseUtil.isNotEmpty(name))
			query.setParameter("name", name);
		if (BaseUtil.isNotEmpty(pid))
			query.setParameter("pid", pid);

		return (long) query.getSingleResult();
	}

	@Override
	public boolean changeLocation(Map<String, Object> parm, String classname, int begin, int end) {
		// 从小到大
		String h = "";
		String key = "";
		String value = "";
		if (begin < end) {
			h = " update " + classname + " a  set a.seq=(a.seq-1) where   a.seq>" + begin + "  and a.seq<=" + end;
		}

		// 从大到小
		if (begin > end) {
			h = " update " + classname + " a  set a.seq=(a.seq+1) where  a.seq>=" + end + "  and a.seq<" + begin;
		}

		Iterator<Map.Entry<String, Object>> entries = parm.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, Object> entry = entries.next();
			key = entry.getKey();
			value = YESUtil.toString(entry.getValue());
			h += " and a." + key + "='" + value + "' ";
		}

		long l = 0;
		try {
			l = em.createQuery(h).executeUpdate();
		} catch (Exception e) {
			l = 0;
			e.printStackTrace();
		}

		return l >= 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getByEidAndSeq(Map<String, Object> parm, String classname, int seq) {
		String h = " select a.uid from " + classname + " a where a.seq=" + seq;
		String key = "";
		String value = "";

		Iterator<Map.Entry<String, Object>> entries = parm.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry<String, Object> entry = entries.next();
			key = entry.getKey();
			value = YESUtil.toString(entry.getValue());
			h += " and a." + key + "='" + value + "' ";
		}

		List<String> ls = em.createQuery(h).getResultList();
		return YESUtil.isNotEmpty(ls) ? ls.get(0) : null;
	}

	@Override
	public boolean updateSeq(String classname, String uid, int seq) {
		String h = " update " + classname + " a  set a.seq=" + seq + " where  a.uid='" + uid + "'";
		int l = 0;
		try {
			l = em.createQuery(h).executeUpdate();
		} catch (Exception e) {
			l = 0;
			e.printStackTrace();
		}
		return l >= 0;
	}

	@Override
	public Integer getMaxSeq(String eid, String oid) {
		String h = "select max(p.seq) from  Position p where  p.eid=:eid ";
		Integer l = (Integer) em.createQuery(h).setParameter("eid", eid).getSingleResult();
		return YESUtil.objtoint(l, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getSeqFromObject(String className, String uid) {
		String h = "select a.seq from  " + className + " a where  a.uid=:uid";
		List<Integer> is = em.createQuery(h).setParameter("uid", uid).getResultList();
		return YESUtil.isEmpty(is) ? null : YESUtil.objtoint(is.get(0), 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Position getByPid(String pid, String eid) {
		String h = " from  Position p where  p.pid=:pid and p.eid=:eid ";
		List<Position> ls = em.createQuery(h).setParameter("pid", pid).setParameter("eid", eid).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

}
