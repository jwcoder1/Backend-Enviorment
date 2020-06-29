package org.esy.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IDicDetailDao;
import org.esy.base.entity.DicDetail;
import org.esy.base.entity.Role;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

@Repository
public class DicDetailDaoImpl implements IDicDetailDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Integer getMaxSeqByModelAndId(String model, String id) {
		Integer i = 0;
		String hql = "select MAX(a.seq) from DicDetail a where a.model = '" + StringEscapeUtils.escapeSql(model) + "'"
				+ " and a.id = '" + StringEscapeUtils.escapeSql(id) + "'";
		Query q = em.createQuery(hql);
		try {
			i = YESUtil.objtoint(q.getSingleResult());
		} catch (NoResultException e) {
		}
		return i;
	}

	@Override
	public DicDetail save(DicDetail o) {
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
	public DicDetail getByUid(String uid) {
		return this.em.find(DicDetail.class, uid);
	}

	@Override
	public boolean delete(DicDetail o) {
		String hql = "delete DicDetail where uid='" + o.getUid() + "'";
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
			String hql = "from DicDetail a where 1=1";
			String whereStr = "";

			Condition model = conditions.get("model"); // 模块名
			if (model != null) {
				if (model.getConditions().get("match") != null) {
					whereStr += " and a.model like '%" + StringEscapeUtils.escapeSql(model.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition name = conditions.get("name"); // 模块名
			if (name != null) {
				if (name.getConditions().get("match") != null) {
					whereStr += " and a.name like '%" + StringEscapeUtils.escapeSql(name.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition value = conditions.get("value"); // 模块名
			if (value != null) {
				if (value.getConditions().get("match") != null) {
					whereStr += " and a.value like '%" + StringEscapeUtils.escapeSql(value.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition id = conditions.get("id"); // 编号
			if (id != null) {
				if (id.getConditions().get("match") != null) {
					whereStr += " and a.id like '%" + StringEscapeUtils.escapeSql(id.getConditions().get("match"))
							+ "%'";
				}
				if (id.getConditions().get("eq") != null) {
					whereStr += " and a.id = '" + StringEscapeUtils.escapeSql(id.getConditions().get("eq")) + "'";
				}
			}

			Condition seq = conditions.get("seq"); // 排序
			if (seq != null) {
				if (seq.getConditions().get("eq") != null) {
					whereStr += " and a.seq = '" + StringEscapeUtils.escapeSql(seq.getConditions().get("eq")) + "'";
				}
			}

			Condition text = conditions.get("text"); // 描述
			if (text != null) {
				if (text.getConditions().get("match") != null) {
					whereStr += " and a.text like '%" + StringEscapeUtils.escapeSql(text.getConditions().get("match"))
							+ "%'";
				}
			}

			if (parm.containsKey("eid")) {
				String eid = YESUtil.toString(parm.get("eid"));
				whereStr += " and a.eid = '" + eid + "'";
			}
			/*
			 * Condition eid = conditions.get("eid"); // eid if (eid != null) {
			 * if (eid.getConditions().get("eq") != null) { whereStr +=
			 * " and a.eid = '" +
			 * StringEscapeUtils.escapeSql(eid.getConditions().get("eq")) + "'";
			 * }
			 * 
			 * if (eid.getConditions().get("match") != null) { whereStr +=
			 * " and a.eid like  '" +
			 * StringEscapeUtils.escapeSql(eid.getConditions().get("match")) +
			 * "%'"; } }
			 */

			Condition did = conditions.get("did"); // did 主类编号
			if (did != null) {
				if (did.getConditions().get("eq") != null) {
					whereStr += " and a.did = '" + StringEscapeUtils.escapeSql(did.getConditions().get("eq")) + "'";
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
	public DicDetail findById(String id) {
		String h = " from DicDetail o where o.id=:id";
		List<DicDetail> ds = em.createQuery(h).setParameter("id", id).getResultList();
		return (BaseUtil.isEmpty(ds)) ? null : ds.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getValueByDdidandEid(String did, String eid) {
		String h = " select o.value from DicDetail o where o.did=:did  and o.eid=:eid  order by o.seq";
		List<String> ds = em.createQuery(h).setParameter("did", did).setParameter("eid", eid).getResultList();
		return BaseUtil.isEmpty(ds) ? null : ds.get(0);
	}

	@Override
	public Integer getMaxSeqByDidAndEid(String did, String eid) {
		String h = " select coalesce(max(o.seq),0) from DicDetail o where o.did=:did   ";
		if (YESUtil.isNotEmpty(eid)) {
			h += " and o.eid=:eid";
		}
		Query query = em.createQuery(h).setParameter("did", did);
		if (YESUtil.isNotEmpty(eid)) {
			query.setParameter("eid", eid);
		}
		return BaseUtil.objtoint(query.getSingleResult(), 0);
	}

	@Override
	public boolean checkForSave(String did, String eid, String id, String name, String uid) {
		String h = " select count(o.uid) from DicDetail o where o.did=:did and id=:id  ";
		if (YESUtil.isNotEmpty(eid)) {
			h += " and o.eid=:eid";
		}
		if (YESUtil.isNotEmpty(uid)) {
			h += " and o.uid<>:uid";
		}

		if (YESUtil.isNotEmpty(name)) {
			h += " and o.name=:name";
		}

		Query query = em.createQuery(h).setParameter("did", did).setParameter("id", id);
		if (YESUtil.isNotEmpty(eid)) {
			query.setParameter("eid", eid);
		}
		if (YESUtil.isNotEmpty(uid)) {
			query.setParameter("uid", uid);
		}
		if (YESUtil.isNotEmpty(name)) {
			query.setParameter("name", name);
		}

		Integer i = BaseUtil.objtoint(query.getSingleResult(), 0);
		return i < 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DicDetail> listValueByDdidandEid(String did, String eid) {
		String h = " select o from DicDetail o where o.did=:did  and o.eid=:eid  order by o.seq";
		List<DicDetail> ds = em.createQuery(h).setParameter("did", did).setParameter("eid", eid).getResultList();
		return ds;
	}

	@Override
	public QueryResult listByCondition(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));
			String did = null;
			String eid = null;

			String hql = "from DicDetail a, Dictionary b  where a.did=b.id ";
			String whereStr = "";

			// type
			if (parm.containsKey("type")) {
				String type = YESUtil.toString(parm.get("type"));
				whereStr += " and b.type='" + type + "'";
			}

			// type
			if (parm.containsKey("eid")) {
				eid = YESUtil.toString(parm.get("eid"));
				whereStr += " and a.eid = '" + eid + "'";
			}

			// did
			if (parm.containsKey("id")) {
				did = YESUtil.toString(parm.get("id"));
				whereStr += " and a.did='" + did + "'";
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			Query q = em.createQuery("select count(a.uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			String orderStr = "";
			orderStr = " order by a.seq ";

			if (!"".equals(orderStr)) {
				hql += orderStr;
			}

			hql = "select a  " + hql;
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
			qr.setHeaders(QueryUtils.getClassFieldInfo(DicDetail.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getValueByDid(String did, String id, String eid) {
		String h = " select  o.value  from DicDetail o where o.did=:did and o.id=:id and o.eid=:eid order by seq ";
		List<String> ls = em.createQuery(h).setParameter("did", did).setParameter("id", id).setParameter("eid", eid)
				.getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getIdByDid(String did, String eid) {
		String h = " select  o.id  from DicDetail o where o.did=:did  and o.eid=:eid order by seq ";
		List<String> ls = em.createQuery(h).setParameter("did", did).setParameter("eid", eid).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listIdByDid(String did, String eid) {
		String h = " select  o.id  from DicDetail o where o.did=:did  and o.eid=:eid order by seq ";
		return em.createQuery(h).setParameter("did", did).setParameter("eid", eid).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getValueByDid(String did, String eid) {
		String h = " select  o.value  from DicDetail o where o.did=:did  and COALESCE(o.eid)=:eid order by seq ";
		List<String> ls = em.createQuery(h).setParameter("did", did).setParameter("eid", eid).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listValueByDid(String did, String eid) {
		String h = " select  o.id  from DicDetail o where o.did=:did  and o.eid=:eid order by seq ";
		return em.createQuery(h).setParameter("did", did).setParameter("eid", eid).getResultList();
	}

	@Override
	public List<DicDetail> getByCondition(String did, String id) {
		String hql = "from DicDetail o where o.did=:did  and o.id=:id order by o.value asc";
		List<DicDetail> list = em.createQuery(hql, DicDetail.class).setParameter("did", did).setParameter("id", id)
				.getResultList();
		return YESUtil.isNotEmpty(list) ? list : null;
	}

	@Override
	public boolean deleteByCondition(String did, String id) {
		String hql = "delete DicDetail where did=:did and id=:id";
		Query q = em.createQuery(hql).setParameter("did", did).setParameter("id", id);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

}
