package org.esy.base.dao.impl;

import java.util.ArrayList;
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
import org.esy.base.entity.Organization;
import org.esy.base.entity.OrganizationRelation;
import org.esy.base.entity.Person;
import org.esy.base.entity.dto.NameValue;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

/**
 * 
 * Dao implement for 组织信息
 *
 */
@Repository
public class OrganizationDaoImpl implements IOrganizationDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Organization save(Organization o) {
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
	public Organization getByUid(String uid) {
		return this.em.find(Organization.class, uid);
	}

	@Override
	public boolean delete(Organization o) {
		String hql = "delete Organization where uid='" + o.getUid() + "'";
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
			String hql = "   from  OrganizationRelation b,Organization a  where a.oid=b.oid ";
			String whereStr = "";

			if (parm.containsKey("eid")) {
				whereStr += " and a.eid = '" + parm.get("eid").toString() + "'";
			}

			Condition oid = conditions.get("oid"); // 编号
			if (oid != null) {
				if (oid.getConditions().get("eq") != null) {
					whereStr += " and a.oid = '" + StringEscapeUtils.escapeSql(oid.getConditions().get("eq")) + "'";
				}
			}

			Condition name = conditions.get("name"); // 名称
			if (name != null) {
				if (name.getConditions().get("match") != null) {
					whereStr += " and a.name like '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition eid = conditions.get("eid"); // 企业路径
			if (eid != null) {
				if (eid.getConditions().get("match") != null) {
					whereStr += " and a.eid like '%" + StringEscapeUtils.escapeSql(eid.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition pid = conditions.get("pid"); // 上级编号
			if (pid != null) {
				if (pid.getConditions().get("eq") != null) {
					whereStr += " and b.pid = '" + StringEscapeUtils.escapeSql(pid.getConditions().get("eq")) + "'";
				}
			}

			Condition path = conditions.get("path"); // 组织路径
			if (path != null) {
				if (path.getConditions().get("match") != null) {
					whereStr += " and b.path like '%" + StringEscapeUtils.escapeSql(path.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition type = conditions.get("type"); // 类别
			if (type != null) {
				if (YESUtil.isNotEmpty(type.getConditions().get("eq"))) {
					whereStr += " and b.type = '" + StringEscapeUtils.escapeSql(type.getConditions().get("eq")) + "'";
				}
			} else {
				if (parm.containsKey("type") && (YESUtil.isNotEmpty(parm.get("type")))) {
					whereStr += " and b.type = " + YESUtil.getQuotedstr(YESUtil.toString(parm.get("type")));
				}
			}

			Condition enable = conditions.get("enable"); // 状态
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.enable = " + StringEscapeUtils.escapeSql(enable.getConditions().get("eq"));
				}
			}

			Condition temppid = conditions.get("temppid"); // 临时pid,中烟webservice数据迁移pid
			if (temppid != null) {
				if (temppid.getConditions().get("eq") != null) {
					whereStr += " and a.temppid = " + StringEscapeUtils.escapeSql(temppid.getConditions().get("eq"));
				}
			}

			Condition tmpId = conditions.get("tmpId"); // 临时id,中烟webservice数据迁移pid
			if (tmpId != null) {
				if (tmpId.getConditions().get("eq") != null) {
					whereStr += " and a.tmpId = " + StringEscapeUtils.escapeSql(tmpId.getConditions().get("eq"));
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by b.level,b.seq,b.oid ";
			Query q = em.createQuery("select count(b.uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			if (!"".equals(orderStr)) {
				hql += orderStr;
			}

			q = em.createQuery(
					"select  new Organization(a.uid, a.eid, a.oid, a.showid, a.name, a.abbreviated, a.isGroup, a.py, a.enable, a.memo, b.pid, b.type, b.seq, b.path, b.uid,b.level)  "
							+ hql);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> listByEid(String eid) {
		String h = "select  o  from Organization o where eid=:eid order by pid , seq  ";
		return em.createQuery(h).setParameter("eid", eid).getResultList();
	}

	@Override
	public boolean deleteTreeByUid(List<String> uids) {
		String h = "delete Organization o where  o.uid in (:uids)  ";
		boolean flag = false;
		try {
			em.createQuery(h).setParameter("uids", uids).executeUpdate();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<String> listTreeByUid(String uid) {
		List<String> uids = new ArrayList<String>();
		List<String> ouids = this.listUidsByPid(uid);
		if (BaseUtil.isEmpty(ouids))
			return null;
		uids.addAll(ouids);

		for (String ouid : ouids) {
			List<String> tuids = listTreeByUid(ouid);
			if (BaseUtil.isNotEmpty(tuids)) {
				uids.addAll(tuids);
			}
		}
		return uids;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> listByPid(String uid) {
		String h = "select  o  from Organization o where pid=:pid  order by pid , seq";
		return em.createQuery(h).setParameter("pid", uid).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listUidsByPid(String uid) {
		String h = "select  o.uid  from Organization o where pid=:pid   order by pid , seq";
		return em.createQuery(h).setParameter("pid", uid).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Organization getByOid(String oid) {
		String h = "select  o  from Organization o where oid=:oid";
		List<Organization> os = em.createQuery(h).setParameter("oid", oid).getResultList();
		return BaseUtil.isNotEmpty(os) ? os.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> listByPath(String path) {
		String h = "select  o from Organization o where  path like :path order by pid , seq";
		return em.createQuery(h).setParameter("pid", BaseUtil.toLikeString(path)).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listOidsByPath(String path) {
		String h = "select  oid from Organization o where  path like :path";
		return em.createQuery(h).setParameter("path", path + "%").getResultList();
	}

	@Override
	public boolean deleteByOids(List<String> oids) {
		String h = "delete Organization o where o.oid in (:oid)";
		try {
			em.createQuery(h).setParameter("oid", oids).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean hadChild(String path, String eid, String oid) {
		String h = "select count(o.uid)  from OrganizationRelation  o where  o.path like :path  and o.eid=:eid  ";
		long i = (long) em.createQuery(h).setParameter("path", path + "%").setParameter("eid", eid).getSingleResult();
		return (i > 0);
	}

	@Override
	public boolean hadPosition(String oid) {
		String h = "select count(o.uid)  from Position o where  o.oid=:oid";
		long i = (long) em.createQuery(h).setParameter("oid", oid).getSingleResult();
		return (i > 0);
	}

	@Override
	public boolean hadPost(String oid) {
		String h = "select count(o.uid)  from Post o where  o.oid=:oid";
		long i = (long) em.createQuery(h).setParameter("oid", oid).getSingleResult();
		return (i > 0);
	}

	@Override
	public boolean hadIdentity(String oid) {
		String h = "select count(o.uid)  from Identity o where  o.oid=:oid";
		long i = (long) em.createQuery(h).setParameter("oid", oid).getSingleResult();
		return (i > 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getPathFromOid(String oid) {
		String h = "select  o.path from Organization o where  o.oid=:oid";
		List<String> ss = em.createQuery(h).setParameter("oid", oid).getResultList();
		return BaseUtil.isEmpty(ss) ? null : ss.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> getRootByEid(String eid) {
		String h = "select o from Organization o where  1=1";
		if (BaseUtil.isNotEmpty(eid)) {
			h += " and o.eid like :eid";
		}
		Query qry = em.createQuery(h);
		if (BaseUtil.isNotEmpty(eid)) {
			qry.setParameter("eid", eid + "%");
		}
		return qry.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getEidFromOid(String oid) {
		String h = "select o.eid from Organization where o.oid=:oid";
		List<String> ls = em.createQuery(h).setParameter("oid", oid).getResultList();
		return BaseUtil.isNotEmpty(ls) ? ls.get(0) : null;
	}

	@Override
	public Boolean checkOrgForSave(String eid, String oid, String name, String uid) {
		String h = "select count(o.uid) from Organization o  where o.eid=:eid";
		if (BaseUtil.isNotEmpty(oid))
			h += " and o.showid=:oid";
		if (BaseUtil.isNotEmpty(name))
			h += " and o.name=:name";
		if (BaseUtil.isNotEmpty(uid))
			h += " and o.uid<>:uid";

		//
		Query query = em.createQuery(h);
		query.setParameter("eid", eid);
		if (BaseUtil.isNotEmpty(oid))
			query.setParameter("oid", oid);
		if (BaseUtil.isNotEmpty(name))
			query.setParameter("name", name);
		if (BaseUtil.isNotEmpty(uid))
			query.setParameter("uid", uid);

		long l = (long) query.getSingleResult();
		return l > 0;
	}

	@Override
	public Integer getMaxSeq(String eid, String pid) {
		String h = "select max(o.seq) from Organization o where o.pid=:pid and o.eid=:eid ";
		return YESUtil.objtoint(em.createQuery(h).setParameter("eid", eid).setParameter("pid", pid).getSingleResult(),
				0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getPath(String eid, String oid) {
		String h = "select o.path from Organization o where o.oid=:oid and o.eid=:eid ";
		List<String> ls = em.createQuery(h).setParameter("eid", eid).setParameter("oid", oid).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listPathByPerson(Person p) {
		String h = "select oid from Identity i where i.pid=:pid and i.eid=:eid and i.enable=:enable";
		h = "select o.path from Organization o where o.oid in (" + h
				+ ") and o.eid=:eid2 and o.enable=:enable2 group by o.path ";
		List<String> ls = em.createQuery(h).setParameter("eid", p.getEid()).setParameter("pid", p.getPid())
				.setParameter("enable", true).setParameter("eid2", p.getEid()).setParameter("enable2", true)
				.getResultList();
		return YESUtil.isEmpty(ls) ? null : ls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listOidsByPaths(List<String> paths, String eid) {
		String buff = "", h = "";
		for (String path : paths) {
			h += buff + " o.path like '" + path + "%'";
			buff = " or ";
		}
		h = "select o.oid from Organization o where (" + h + ") and o.eid=:eid and o.enable=:enable ";
		List<String> ls = em.createQuery(h).setParameter("eid", eid).setParameter("enable", true).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listChildNodesOidByPath(String path, String eid) {
		String h = "select o.oid from Organization o,  OrganizationRelation b  where  o.oid=b.oid and   (b.path="
				+ YESUtil.getQuotedstr(path) + "   or   b.path  like " + YESUtil.getRightLikeStr(path + ".")
				+ ")  and o.enable=true  and b.eid=" + YESUtil.getQuotedstr(eid) + "  and o.eid="
				+ YESUtil.getQuotedstr(eid) + " group by  o.oid";
		return em.createQuery(h).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NameValue> listRootNames(String eid) {
		String h = "select new org.esy.base.entity.dto.NameValue(b.oid, a.name,b.seq)   from  OrganizationRelation b,Organization a  where a.oid=b.oid and a.eid="
				+ YESUtil.getQuotedstr(eid)
				+ "  and  (COALESCE(b.pid,'')=''   or  b.pid is null) group by b.oid, a.name,b.seq  order by b.seq  ";
		return em.createQuery(h).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationRelation> listParentNodes(String eid, String oid) {
		String h = "select new OrganizationRelation(b.uid,  b.eid, b.oid, b.pid, a.name, b.type, c.name, b.seq, b.path)   from  OrganizationRelation b,Organization a  , Organization c   where a.oid=b.pid and a.eid="
				+ YESUtil.getQuotedstr(eid) + "   and b.oid=" + YESUtil.getQuotedstr(oid)
				+ "  and  b.type=c.oid  group by  b.uid,  b.eid, b.oid, b.pid, a.name, b.type, c.name, b.seq, b.path order by b.seq  ";
		return em.createQuery(h).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrganizationRelation getRootName(String eid, String oid) {
		String h = "select new  OrganizationRelation(b.uid,  b.eid, b.oid, b.pid, '', b.type, a.name, b.seq, b.path)   from  OrganizationRelation b,Organization a  where a.oid=b.oid  and b.oid=:oid  and a.eid="
				+ YESUtil.getQuotedstr(eid)
				+ "  and  COALESCE(b.pid,'')=''  group by b.uid,  b.eid, b.oid, b.pid, b.type, a.name, b.seq, b.path order by b.seq  ";
		List<OrganizationRelation> ls = em.createQuery(h).setParameter("oid", oid).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@Override
	public boolean exsitsInEid(String eid) {
		Query q = em.createQuery("select count(1)  from OrganizationRelation  a  where a.eid=:eid ");
		Long l = (Long) q.setParameter("eid", eid).getSingleResult();
		return l > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Organization getByTmpid(String tmpid, String eid) {
		String hql = " from Organization o where o.tmpId=:tmpId and o.eid=:eid";
		List<Organization> os = em.createQuery(hql).setParameter("tmpId", tmpid).setParameter("eid", eid)
				.getResultList();
		return YESUtil.isEmpty(os) ? null : os.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrganizationRelation getSingleRelationByOid(String oid, String eid) {
		String hql = " from OrganizationRelation  o where o.oid=:oid and o.eid=:eid  ";
		List<OrganizationRelation> os = em.createQuery(hql).setParameter("oid", oid).setParameter("eid", eid)
				.getResultList();
		return YESUtil.isEmpty(os) ? null : os.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Organization getByOidAndEid(String oid, String eid) {
		String hql = " from Organization o where o.oid=:oid and o.eid=:eid";
		List<Organization> os = em.createQuery(hql).setParameter("oid", oid).setParameter("eid", eid).getResultList();
		return YESUtil.isEmpty(os) ? null : os.get(0);
	}

}
