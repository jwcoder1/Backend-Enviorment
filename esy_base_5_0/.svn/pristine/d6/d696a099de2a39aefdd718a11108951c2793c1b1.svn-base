package org.esy.base.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IAuthorityDao;
import org.esy.base.entity.Authority;
import org.esy.base.entity.AuthorityMenu;
import org.esy.base.entity.Menu;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;

/**
 * 
 * Dao implement for 群组成员
 *
 */
@Repository
public class AuthorityDaoImpl implements IAuthorityDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public boolean save(AuthorityMenu am) {
		if (am.checkNew()) {
			am.setUid(UuidUtils.getUUID());
			this.em.persist(am);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Authority save(Authority o) {
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
	public Authority getByUid(String uid) {
		return this.em.find(Authority.class, uid);
	}

	@Override
	public boolean delete(Authority o) {
		String hql = "delete Authority where uid='" + o.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delDetail(String aid) {
		String hql = "delete AuthorityMenu where aid='" + aid + "'";
		Query q = em.createQuery(hql);
		q.executeUpdate();
		return true;
	}

	@Override
	public int updateShowByValueAndType(String type, String value, String newShow) {
		String hql = "update Authority g set g.show='" + newShow + "'" + " where g.value='" + value + "' and g.type='"
				+ type + "'";
		Query q = em.createQuery(hql);
		return q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Authority> getAuthorityByValuesAndType(String[] values, String type) {
		String hql = "from Authority gm where gm.value in :values" + " and gm.type='" + type + "'";
		Query q = em.createQuery(hql);
		q.setParameter("values", Arrays.asList(values));
		return (List<Authority>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthorityMenu> getDetail(String aid) {
		Query q = em.createQuery("from AuthorityMenu am where am.aid='" + aid + "'");
		return (List<AuthorityMenu>) q.getResultList();
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);
		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));
			String hql = "from Authority a where 1=1 ";
			String whereStr = "";
			if (parm.containsKey("eid")) {
				whereStr += " and a.eid like '" + parm.get("eid").toString() + "%'";
			}

			Condition eid = conditions.get("eid");
			if (eid != null) {
				if (eid.getConditions().get("eq") != null) {
					whereStr += "  and  a.eid=" + YESUtil.getQuotedstr(eid.getConditions().get("eq"));
				}
			}

			Condition search = conditions.get("search"); // 多搜索
			if (search != null) {
				if (search.getConditions().get("match") != null) {
					whereStr += " and (a.show like '%"
							+ StringEscapeUtils.escapeSql(search.getConditions().get("match")) + "%'"
							+ " or a.value like '%" + StringEscapeUtils.escapeSql(search.getConditions().get("match"))
							+ "%')";
				}
			}

			Condition type = conditions.get("type"); // 群组类型
			if (type != null) {
				if (type.getConditions().get("eq") != null) {
					whereStr += " and a.type = '" + StringEscapeUtils.escapeSql(type.getConditions().get("eq")) + "'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.type, a.created ";

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Authority.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Authority> listByPeron(String sql) {
		String hql = "select distinct a from Authority a where " + sql;
		return em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> listMenuByPeron(String sql) {
		String hql = " select new org.esy.base.entity.Menu(m.mid, m.name, m.enable, m.type, m.order, m.icon, m.tip, m.pid, m.url, m.memo, m.home, m.blank, m.expanded, m.color, m.tag)  from Menu m where  m.mid  in  (select am.mid from AuthorityMenu am where am.aid in ( select  a.aid  from Authority a where "
				+ sql + " group by a.aid) group by am.mid) and m.enable=true order by m.order";
		return em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> listMenuByPeronForLogin(String sql) {
		String hql = " select new org.esy.base.entity.Menu(m.mid, m.name, m.enable, m.type, m.order, m.icon, m.tip, m.pid, m.url, m.memo, m.home, m.blank, m.expanded, m.color, m.tag)  from Menu m where  m.mid  in  (select am.mid from AuthorityMenu am where am.aid in ( select  a.aid  from Authority a where "
				+ sql + " group by a.aid) group by am.mid) and m.enable=true order by m.order";
		return em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> listMenuByAid(String aid) {
		String hql = " select new org.esy.base.entity.Menu(m.mid, m.name, m.enable, m.type, m.order, m.icon, m.tip, m.pid, m.url, m.memo, m.home, m.blank, m.expanded, m.color, m.tag)  from Menu m where  m.mid  in  (select am.mid from AuthorityMenu am  "
				+ " where am.aid =" + YESUtil.getQuotedstr(aid)
				+ " group by am.mid) and m.enable=true order by m.order";
		return em.createQuery(hql).getResultList();
	}

	@Override
	public Boolean checkMenuValue(String aid, String menuValue) {
		String hql = " select count(m.uid) from AuthorityMenu m where  m.mid=:mid and   m.aid in   ( select  a.aid from Authority a  where a.type=:type and a.value=:value ) ";
		Long l = (Long) em.createQuery(hql).setParameter("mid", menuValue).setParameter("type", "A")
				.setParameter("value", aid).getSingleResult();
		return l > 0;
	}

}
