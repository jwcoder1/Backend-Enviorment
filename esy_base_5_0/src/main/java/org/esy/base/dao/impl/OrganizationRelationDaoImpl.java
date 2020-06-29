package org.esy.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.esy.base.core.QueryResult;
import org.esy.base.dao.IOrganizationRelationDao;
import org.esy.base.entity.OrganizationRelation;
import org.esy.base.entity.dto.IdentOrg;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationRelationDaoImpl implements IOrganizationRelationDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public OrganizationRelation save(OrganizationRelation o) {
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
	public OrganizationRelation getByUid(String uid) {
		return this.em.find(OrganizationRelation.class, uid);
	}

	@Override
	public boolean delete(OrganizationRelation o) {
		String hql = "delete OrganizationRelation where uid='" + o.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean deleteByOid(String oid) {
		String hql = "delete OrganizationRelation where oid=" + YESUtil.getQuotedstr(oid);
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
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationRelation> listByEid(String eid) {
		String h = " from OrganizationRelation e  where e.eid=:eid order by e.type, e.seq ";
		return em.createQuery(h).setParameter("eid", eid).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationRelation> listByOidAndType(String oid, String type) {
		String h = " from OrganizationRelation e  where e.oid=:oid and e.type=:type  order by e.type, e.seq ";
		return em.createQuery(h).setParameter("oid", oid).setParameter("type", type).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getPathFromOidAndType(String oid, String type) {
		String h = " select e.path from OrganizationRelation e  where e.oid=:oid and e.type=:type  order by e.type, e.seq ";
		List<String> ls = em.createQuery(h).setParameter("oid", oid).setParameter("type", type).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getPathFromOid(String oid, String eid) {
		String h = " select e.path from OrganizationRelation e  where e.oid=:oid and e.eid =:eid  order by e.type, e.seq ";
		List<String> ls = em.createQuery(h).setParameter("oid", oid).setParameter("eid", eid).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@Override
	public Integer getMaxSeqFromPidAndType(String pid, String type) {
		String h = " select  COALESCE(max(e.seq),0)  from OrganizationRelation e  where e.pid=:pid   ";
		if (YESUtil.isNotEmpty(type)) {
			h += "and e.type=" + YESUtil.getQuotedstr(type);
		}
		return (Integer) em.createQuery(h).setParameter("pid", pid).getSingleResult();
	}

	@Override
	public Integer getMaxSeqFromPidAndType(String pid, String type, String ppath) {
		String h = " select  COALESCE(max(e.seq),0)  from OrganizationRelation e  where e.pid=:pid   and e.path  like "
				+ YESUtil.getRightLikeStr(ppath);
		if (YESUtil.isNotEmpty(type)) {
			h += "and e.type=" + YESUtil.getQuotedstr(type);
		}

		return (Integer) em.createQuery(h).setParameter("pid", pid).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isExsitsOid(String path, String pid, String oid) {
		String h = "select 1 from  OrganizationRelation o where o.path like :path and o.pid=:pid and o.oid=:oid";
		List<Integer> ls = em.createQuery(h).setParameter("pid", pid).setParameter("path", path + ".%")
				.setParameter("oid", oid).getResultList();
		return YESUtil.isNotEmpty(ls);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrganizationRelation> listByOid(String oid, String eid) {
		String h = " from  OrganizationRelation o where o.oid=:oid and o.eid=:eid ";
		return em.createQuery(h).setParameter("oid", oid).setParameter("eid", eid).getResultList();
	}

	@Override
	public boolean deleteByOidPathEid(String oid, String path, String eid) {
		String h = " delete OrganizationRelation o where o.oid=:oid and o.path=:path and o.eid=:eid ";
		int i = em.createQuery(h).setParameter("oid", oid).setParameter("eid", eid).setParameter("path", path)
				.executeUpdate();
		return i > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getSeq(String eid, String pid, String path, String type) {
		String h = " select o.seq  from  OrganizationRelation o where o.pid=:pid and o.eid=:eid and o.type=:type and o.path=:path  ";
		List<Integer> ls = em.createQuery(h).setParameter("pid", pid).setParameter("eid", eid)
				.setParameter("path", path).setParameter("type", type).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@Override
	public boolean deleteByUid(String uid) {
		String h = " delete OrganizationRelation o where o.uid=:uid";
		int i = em.createQuery(h).setParameter("uid", uid).executeUpdate();
		return i > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listUidByPathEid(List<String> paths, String eid) {
		String h = "select o.uid  from  OrganizationRelation o where o.eid=:eid  ";
		String tmp = "";
		for (String path : paths) {
			h += " or  o.path like " + YESUtil.getRightLikeStr(path);
		}
		h += "(" + tmp + ")";
		return em.createQuery(h).setParameter("eid", eid).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IdentOrg> listOrgsByOids(List<String> oids) {
		String h = "select new org.esy.base.entity.dto.IdentOrg(b.eid,en.cname, b.type, root.name, b.oid, o.name,b.path )   from  OrganizationRelation b, Organization o  , Organization root , Enterprise en "
				+ "   where  b.oid=o.oid  and b.type=root.oid  and b.eid=en.eid  and b.oid in (:oid)  group by b.eid,en.cname, b.type, root.name, b.oid, o.name,b.path";
		return em.createQuery(h).setParameter("oid", oids).getResultList();
	}

}
