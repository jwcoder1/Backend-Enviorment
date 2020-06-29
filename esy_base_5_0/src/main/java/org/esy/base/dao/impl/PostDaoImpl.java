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
import org.esy.base.dao.IOrganizationDao;
import org.esy.base.dao.IPostDao;
import org.esy.base.entity.Post;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * Dao implement for 岗位信息
 *
 */
@Repository
public class PostDaoImpl implements IPostDao {

	@Autowired
	private IOrganizationDao organizationDao;

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Post save(Post o) {
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
	public Post getByUid(String uid) {
		return this.em.find(Post.class, uid);
	}

	@Override
	public boolean delete(Post o) {
		String hql = "delete Post where uid='" + o.getUid() + "'";
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
			// String order = YESUtil.toString(parm.get("order"));

			String hql = " from Post a , Organization o where a.oid=o.oid ";

			String whereStr = "";

			String veid = null;
			if (parm.containsKey("eid")) {
				veid = YESUtil.toString(parm.get("eid"));
				whereStr += " and a.eid = '" + veid + "'";
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

			Condition eid = conditions.get("eid"); // 企业路径
			if (eid != null) {
				if (eid.getConditions().get("match") != null) {
					whereStr += " and a.eid like '%" + StringEscapeUtils.escapeSql(eid.getConditions().get("match")) + "%'";
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
			orderStr = " order by a.seq asc";

			Query q = em.createQuery("select count(a.uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			if (!"".equals(orderStr)) {
				hql += orderStr;
			}

			hql = "select new Post(a.uid, a.eid, a.oid, a.pid, a.showid, a.name, a.py, a.seq, a.enable, o.name)  " + hql;
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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Post.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public long countByNameAndOid(String oid, String name, String pid, String uid, String eid) {
		String h = "select count(p.uid) from  Post p where  p.eid=:eid";

		if (BaseUtil.isNotEmpty(oid)) {
			h += " and p.oid=:oid ";
		}
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
	public Integer getMaxSeq(String eid, String oid) {
		String h = "select max(p.seq) from  Post p where  p.eid=:eid ";
		Integer l = (Integer) em.createQuery(h).setParameter("eid", eid).getSingleResult();
		return YESUtil.objtoint(l, 0);
	}

	// TODO
	@SuppressWarnings("unchecked")
	@Override
	public List<Post> listByOids(List<String> oids, String rootpid, Boolean enable) {
		String h = "select new Post(a.uid, a.eid, a.oid, a.pid, a.showid, a.name, a.py, a.seq, a.enable, o.name,a.rootpid)   from Post a , Organization o where a.oid=o.oid  and  a.oid in (:oid)  and a.rootpid=:rootpid ";
		if (YESUtil.isNotEmpty(enable)) {
			if (enable)
				h += " and  a.enable=true ";
			else
				h += " and  a.enable=false ";
		}
		h += " order by a.seq ";
		return em.createQuery(h).setParameter("oid", oids).setParameter("rootpid", rootpid).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Post getByPid(String pid, String eid) {
		String h = " from Post p where p.pid=:pid and p.eid=:eid ";
		List<Post> ls = em.createQuery(h).setParameter("pid", pid).setParameter("eid", eid).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}
}
