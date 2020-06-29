package org.esy.base.dao.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IGroupMemberDao;
import org.esy.base.entity.GroupMember;
import org.esy.base.util.BASEUtil;
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
public class GroupMemberDaoImpl implements IGroupMemberDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public GroupMember save(GroupMember o) {
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
	public GroupMember getByUid(String uid) {
		return this.em.find(GroupMember.class, uid);
	}

	@Override
	public boolean delete(GroupMember o) {
		String hql = "delete GroupMember where uid='" + o.getUid() + "'";
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
		String hql = "delete GroupMember where value in :values and type='" + type + "'";
		Query q = em.createQuery(hql);
		q.setParameter("values", Arrays.asList(values));
		return q.executeUpdate();
	}

	@Override
	public boolean deleteByGid(String gid) {
		String hql = "delete GroupMember where gid='" + gid + "'";
		Query q = em.createQuery(hql);
		q.executeUpdate();
		return true;
	}

	@Override
	public int updateShowByValueAndType(String type, String value, String newShow) {
		String hql = "update GroupMember g set g.show='" + newShow + "'" + " where g.value='" + value + "' and g.type='"
				+ type + "'";
		Query q = em.createQuery(hql);
		return q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupMember> getGroupMemberByValuesAndTypeAndGid(String[] values, String gid, String type) {
		String hql = "from GroupMember gm where gm.gid='" + gid + "' and gm.value in :values" + " and gm.type='" + type
				+ "'";
		Query q = em.createQuery(hql);
		q.setParameter("values", Arrays.asList(values));
		return (List<GroupMember>) q.getResultList();
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);
		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));

			String hql = "from GroupMember a where 1=1";

			String whereStr = "";

			Condition gid = conditions.get("gid"); // 群组编号
			if (gid != null) {
				if (gid.getConditions().get("eq") != null) {
					whereStr += " and a.gid = '" + StringEscapeUtils.escapeSql(gid.getConditions().get("eq")) + "'";
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

			String orderStr = " order by a.type asc";

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(GroupMember.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupMember> searchByGname(String gname, String eid) {
		gname = gname.replace("&amp;", "&");
		String hql = "from GroupMember gm,Group g where g.name = '" + StringEscapeUtils.escapeSql(gname)
				+ "' and g.gid =gm.gid and gm.type='A'";
		String reshql = "select new map(gm.uid as uid,gm.gid as gid,gm.type as type,gm.value as value,gm.show as show)";
		return em.createQuery(reshql + hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupMember> getMembersByUserId() {
		String hql = "select new org.esy.base.entity.GroupMember(g.name,gm.type,gm.value,gm.show,gm.showid, gm.showid2)"
				+ "from GroupMember gm,Group g where g.gid=gm.gid and gm.type='A' and gm.value='" + BaseUtil.getUserId()
				+ "'";
		return em.createQuery(hql).getResultList();
	}

	@Override
	public QueryResult listGroupPeople(Map<String, Object> parm, String hql) {
		QueryResult qr = new QueryResult();
		int count = YESUtil.objtoint(parm.get("count"));
		int start = YESUtil.objtoint(parm.get("start"));
		String peopleSearch = YESUtil.toString(parm.get("peopleSearch"));

		String h = " from Person p , Identity i , Position  po , OrganizationRelation r   where  p.pid=i.pid and i.positionId=po.pid  and  i.oid=r.oid  and  p.eid=r.eid "
				+ " and po.enable=true and  i.enable=true and  p.enable=true "
				+ " and (i.startDate is null or i.startDate < CURRENT_DATE()) "
				+ " and (i.toDate is null or i.toDate > CURRENT_DATE()) ";
		// " and (CURDATE()> COALESCE(i.startDate,CAST('1000-01-01 00:00:00' AS
		// DATE))) and (CURDATE()< COALESCE(i.toDate,CAST('3000-01-01 00:00:00'
		// AS DATE)))";
		if (YESUtil.isNotEmpty(peopleSearch)) {
			String tmp = YESUtil.getLikeStr(peopleSearch);
			h += String.format(
					" and (p.employeeNo like %s   or  p.py like %s  or p.pinyin like %s or p.cname like %s  )", tmp,
					tmp, tmp, tmp);
		}

		h += hql;
		String hcount = "select count(distinct p.uid)  " + h;
		Long l = (Long) em.createQuery(hcount).getSingleResult();
		qr.setCount(l);

		h = "select new Person( p.uid,p.eid, p.pid, p.cname, p.py, p.pinyin, p.ename, p.shortName, p.sex, p.employeeNo, p.birthday, p.officePhone, p.mobilePhone, p.mail, p.seq, p.enable, p.memo, p.type, '', '', po.seq, min(COALESCE(r.level, 99) ) , min(COALESCE(r.seq, 99)) ) "
				+ h;
		h += " group by p.uid " + " order by min(r.level) , min(r.seq) ,  po.seq , p.seq , p.pinyin ";
		Query q = em.createQuery(h);
		if (start > 0) {
			q.setFirstResult(start);
		}
		if (count > 0) {
			q.setMaxResults(count);
		} else {
			q.setMaxResults(20);
		}
		qr.setItems(q.getResultList());
		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupMember> listByGid(String gid) {
		String h = " from GroupMember g where g.gid=:gid";
		return em.createQuery(h).setParameter("gid", gid).getResultList();
	}
}
