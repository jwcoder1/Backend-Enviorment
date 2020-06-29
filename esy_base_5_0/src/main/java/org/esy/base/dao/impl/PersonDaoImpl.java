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
import org.esy.base.dao.IOrganizationDao;
import org.esy.base.dao.IPersonDao;
import org.esy.base.entity.Person;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * Dao implement for 人员信息
 *
 */
@Repository
public class PersonDaoImpl implements IPersonDao {

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
	public Person save(Person o) {
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
	public Person getByUid(String uid) {
		return this.em.find(Person.class, uid);
	}

	@Override
	public boolean delete(Person o) {
		String hql = "delete Person where uid='" + o.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public QueryResult query2(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);
		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));
			String hql = "  from base_mperson a " + "  left join  base_midentity ident on a.mp_pid=ident.mi_pid   "
					+ "   left join  base_mposition po on ident.mi_positionid=po.mpo_pid   "
					+ "   left join  base_uid u on  u.mu_pid= a.mp_pid   ";

			String whereStr = " where 1=1  ";
			String eid = "";

			if (parm.containsKey("path") && parm.containsKey("eid")) {
				String path = parm.get("path").toString();
				hql += " left join base_morganizationrelation r on r.mor_oid=ident.mi_oid  ";
				whereStr += " and r.mor_path  like  " + YESUtil.getRightLikeStr(path);
			}

			if (parm.containsKey("eid")) { // 身份里面有一个即可
				eid = YESUtil.toString(parm.get("eid"));
				whereStr += " and  ident.mi_eid=" + YESUtil.getQuotedstr(eid);
			}

			Condition pid = conditions.get("pid"); // 人员编号
			if (pid != null) {
				if (pid.getConditions().get("match") != null) {
					whereStr += " and a.mp_pid like '%" + StringEscapeUtils.escapeSql(pid.getConditions().get("match"))
							+ "%'";
				}
				if (pid.getConditions().get("eq") != null) {
					whereStr += " and a.mp_pid = '" + StringEscapeUtils.escapeSql(pid.getConditions().get("eq")) + "'";
				}
			}

			Condition puid = conditions.get("puid"); // UID
			if (puid != null) {
				if (puid.getConditions().get("match") != null) {
					whereStr += " and u.mu_uid like '%" + StringEscapeUtils.escapeSql(puid.getConditions().get("match"))
							+ "%'";
				}
				if (puid.getConditions().get("eq") != null) {
					whereStr += " and u.mu_.uid = '" + StringEscapeUtils.escapeSql(puid.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition showid = conditions.get("showid"); // 人员编号
			if (showid != null) {
				if (showid.getConditions().get("match") != null) {
					whereStr += " and a.mp_showid like '%"
							+ StringEscapeUtils.escapeSql(showid.getConditions().get("match")) + "%'";
				}

				if (showid.getConditions().get("eq") != null) {
					whereStr += " and a.mp_showid = '" + StringEscapeUtils.escapeSql(showid.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition cname = conditions.get("cname"); // 中文名
			if (cname != null) {
				if (cname.getConditions().get("match") != null) {
					String tmp = YESUtil.getLikeStr(cname.getConditions().get("match"));
					whereStr += " and ( a.mp_employeeNo like " + tmp + "  or  a.mp_cname like " + tmp
							+ " or a.mp_pinyin like " + tmp + " or a.mp_py like  " + tmp + " )";
				}
			}

			Condition ename = conditions.get("ename"); // 英文名
			if (ename != null) {
				if (ename.getConditions().get("match") != null) {
					whereStr += " and a.mp_ename like '%"
							+ StringEscapeUtils.escapeSql(ename.getConditions().get("match")) + "%'";
				}
			}

			Condition sex = conditions.get("sex"); // 性别
			if (sex != null) {
				if (sex.getConditions().get("eq") != null) {
					whereStr += " and a.mp_sex = '" + StringEscapeUtils.escapeSql(sex.getConditions().get("eq")) + "'";
				}
			}

			Condition phone = conditions.get("phone"); // 办公电话
			if (phone != null) {
				if (phone.getConditions().get("match") != null) {
					whereStr += " and (a.mp_officePhone like '%"
							+ StringEscapeUtils.escapeSql(phone.getConditions().get("match")) + "%'"
							+ "	or a.mp_mobilePhone like '%"
							+ StringEscapeUtils.escapeSql(phone.getConditions().get("match")) + "%')";
				}
			}

			Condition mail = conditions.get("mail"); // 电子邮件
			if (mail != null) {
				if (mail.getConditions().get("match") != null) {
					whereStr += " and a.mp_mail like '%"
							+ StringEscapeUtils.escapeSql(mail.getConditions().get("match")) + "%'";
				}
			}

			Condition enable = conditions.get("enable"); // 状态
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.mp_enable = " + StringEscapeUtils.escapeSql(enable.getConditions().get("eq"));
				}
			}

			String positionId = "";
			if (parm.containsKey("position")) {
				positionId = YESUtil.toString(parm.get("position"));
			}
			if (YESUtil.isNotEmpty(positionId)) {
				whereStr += "  and  ident.mi_positionId=" + YESUtil.getQuotedstr(positionId);
			}

			String postId = "";
			if (parm.containsKey("post")) {
				postId = YESUtil.toString(parm.get("post"));
			}

			if (YESUtil.isNotEmpty(postId)) {
				whereStr += "  and ident.mi_postId=" + YESUtil.getQuotedstr(postId);
			}

			// oid
			if (parm.containsKey("oid")) {
				whereStr += "  and ident.mi_oid=" + YESUtil.toString(parm.get("oid"));
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = "  group by a.uuid ,a.created ,a.UPDATED ,a.mp_eid ,a.mp_pid ,a.mp_cname ,a.mp_py ,a.mp_pinyin ,a.mp_ename ,a.mp_shortName ,a.mp_sex ,a.mp_employeeNo ,a.mp_birthday ,a.mp_officePhone ,";
			orderStr += "a.mp_mobilePhone ,a.mp_mail ,a.mp_seq ,a.mp_enable ,a.mp_memo ,a.mp_type,a.worker_tech_grd_cd ,a.major_cd ,a.mp_sysid ,U .mu_uid";
			if (parm.containsKey("path") && parm.containsKey("eid")) {
				orderStr += " ,r.mor_seq";
			}
			orderStr += " order by ";
			if (parm.containsKey("orderstr")) {
				orderStr += parm.get("orderstr") + ",";
			}
			if (parm.containsKey("path") && parm.containsKey("eid")) {
				orderStr += "r.mor_seq,";
			}
			orderStr += "positionSeq , a.mp_seq , a.mp_pinyin asc ";

			Query q = em.createNativeQuery("select count(distinct a.uuid) " + hql);
			// System.out.println("===1===");
			// System.out.println("select count(distinct a.uuid) " + hql);
			Long records = YESUtil.objtolong(q.getSingleResult());
			qr.setCount(records);
			if (!"".equals(orderStr))
				hql += orderStr;

			hql = "select  a.uuid as uid2 ,a.created as created, a.updated as updated, a.mp_eid as  eid, a.mp_pid  as  pid, a.mp_cname as cname , a.mp_py as  py, a.mp_pinyin as  pinyin,  a.mp_ename as ename ,a.mp_shortName  as  shortName, a.mp_sex as sex,  a.mp_employeeNo as  employeeNo, a.mp_birthday as birthday , a.mp_officePhone as  officePhone, "
					+ "a.mp_mobilePhone as   mobilePhone, a.mp_mail as  mail, a.mp_seq  as  seq, a.mp_enable as enable, a.mp_memo as  memo,  a.mp_type as  type, ''  as positionName, '' as postName, min(COALESCE(po.mpo_seq,9999)) as positionSeq ,a.worker_tech_grd_cd as workerTechGrdCd  , a.major_cd as majorCd  ,a.mp_sysid as sysid , u.mu_uid as  puid "
					+ hql;

			// System.out.println("===2===");
			// System.out.println(hql);

			q = em.createNativeQuery(hql, "Person");
			if (start > 0) {
				q.setFirstResult(start);
			}
			if (count > 0) {
				q.setMaxResults(count);
			} else {
				q.setMaxResults(20);
			}
			List results = q.getResultList();
			List<Person> ps = new ArrayList<Person>();
			for (Object result : results) {
				Object[] ob = (Object[]) result;
				Person p = (Person) ob[0];
				p.setPuid(YESUtil.toString(ob[1]));
				ps.add(p);
			}
			qr.setItems(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return null;
	}

	@Override
	public Map<String, Object> search(Map<String, Object> parm) {
		Map<String, Object> map = new HashMap<String, Object>();
		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);

		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));
			String hql = "from Person a where a.enable=true ";
			String whereStr = "";
			if (parm.containsKey("eid")) {
				whereStr += " and a.eid = '" + parm.get("eid").toString() + "'";
			}

			Condition eid = conditions.get("eid"); // 路径
			if (eid != null) {
				if (eid.getConditions().get("eq") != null) {
					whereStr += " and a.eid = '" + StringEscapeUtils.escapeSql(eid.getConditions().get("eq")) + "'";
				}

				if (eid.getConditions().get("match") != null) {
					whereStr += " and a.eid like '%" + StringEscapeUtils.escapeSql(eid.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition pid = conditions.get("pid"); // 人员编号
			if (pid != null) {
				if (pid.getConditions().get("match") != null) {
					whereStr += " and a.pid like '%" + StringEscapeUtils.escapeSql(pid.getConditions().get("match"))
							+ "%'";
				}
				if (pid.getConditions().get("eq") != null) {
					whereStr += " and a.pid = '" + StringEscapeUtils.escapeSql(pid.getConditions().get("eq")) + "'";
				}
			}

			Condition showid = conditions.get("showid"); // 人员编号
			if (showid != null) {
				if (showid.getConditions().get("match") != null) {
					whereStr += " and a.showid like '%"
							+ StringEscapeUtils.escapeSql(showid.getConditions().get("match")) + "%'";
				}

				if (showid.getConditions().get("eq") != null) {
					whereStr += " and a.showid = '" + StringEscapeUtils.escapeSql(showid.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition ename = conditions.get("ename"); // 英文名
			if (ename != null) {
				if (ename.getConditions().get("match") != null) {
					whereStr += " and a.ename like '%" + StringEscapeUtils.escapeSql(ename.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition sex = conditions.get("sex"); // 性别
			if (sex != null) {
				if (sex.getConditions().get("eq") != null) {
					whereStr += " and a.sex = '" + StringEscapeUtils.escapeSql(sex.getConditions().get("eq")) + "'";
				}
			}

			Condition phone = conditions.get("phone"); // 办公电话
			if (phone != null) {
				if (phone.getConditions().get("match") != null) {
					whereStr += " and (a.officePhone like '%"
							+ StringEscapeUtils.escapeSql(phone.getConditions().get("match")) + "%'"
							+ "	or a.mobilePhone like '%"
							+ StringEscapeUtils.escapeSql(phone.getConditions().get("match")) + "%')";
				}
			}

			Condition mail = conditions.get("mail"); // 电子邮件
			if (mail != null) {
				if (mail.getConditions().get("match") != null) {
					whereStr += " and a.mail like '%" + StringEscapeUtils.escapeSql(mail.getConditions().get("match"))
							+ "%'";
				}
			}

			Condition enable = conditions.get("enable"); // 状态
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.enable = " + StringEscapeUtils.escapeSql(enable.getConditions().get("eq"));
				}
			}

			String postionType = YESUtil.toString(parm.get("showType"));// 是否显示兼职0不显示,1显示
			Condition coid = conditions.get("oid"); // 组织
			if (coid != null) {
				if (coid.getConditions().get("eq") != null) {
					String oid = StringEscapeUtils.escapeSql(coid.getConditions().get("eq"));
					if (BaseUtil.isNotEmpty(oid)) {
						String path = organizationDao.getPathFromOid(oid);
						if (BaseUtil.isEmpty(path))
							path = "-1";
						if ("0".equals(postionType))
							whereStr += " and EXISTS (select 1 from  Identity i  where i.pid=a.pid  and coalesce(i.isMain,false)=true  and EXISTS (select 1 from Organization o where o.path like '"
									+ path + "%'  and o.oid=i.oid ) ) ";
						else
							whereStr += " and EXISTS (select 1 from  Identity i where i.pid=a.pid  and EXISTS (select 1 from Organization o where o.path like '"
									+ path + "%'  and o.oid=i.oid )  ) ";
					}
				}
			}

			String allhql = hql + whereStr;

			Condition cname = conditions.get("cname"); // 中文名
			if (cname != null) {
				if (cname.getConditions().get("match") != null) {
					whereStr += " and ( a.cname like '%"
							+ StringEscapeUtils.escapeSql(cname.getConditions().get("match")) + "%'"
							+ " or a.pinyin like '%" + StringEscapeUtils.escapeSql(cname.getConditions().get("match"))
							+ "%'" + " or a.py like  '%"
							+ StringEscapeUtils.escapeSql(cname.getConditions().get("match")) + "%' )";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.pid desc";

			Query q = em.createQuery("select count(a.uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			// 全组织人员
			q = em.createQuery("select count(a.uid) " + allhql);
			Long allrecords = (Long) q.getSingleResult();
			map.put("allcount", allrecords);

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Person.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		map.put("count", qr.getCount());
		map.put("items", qr.getItems());
		map.put("headers", qr.getHeaders());
		return map;
	}

	@Override
	public Map<String, Object> getOrgCount(Map<String, Object> parm) {
		Map<String, Object> map = new HashMap<String, Object>();

		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);
		try {
			String hql = "  from base_mperson a "
					+ "  left join  base_midentity ident on a.mp_pid=ident.mi_pid and ident.mi_isMain=true "
					+ "   left join  base_mposition po on ident.mi_positionid=po.mpo_pid and ident.mi_isMain=true "
					+ "   " + "    left join  base_uid u on ( u.mu_pid= a.mp_pid )    where 1=1";
			String whereStr = "";
			String eid = "";
			if (parm.containsKey("eid")) { // 改成：身份里面有一个即可
				eid = YESUtil.toString(parm.get("eid"));
				whereStr += "  and EXISTS ( select 1 from base_midentity ident2 where ident2.mi_pid=a.mp_pid  and  ident2.mi_eid='"
						+ eid + "' )";
			}

			Condition pid = conditions.get("pid"); // 人员编号
			if (pid != null) {
				if (pid.getConditions().get("match") != null) {
					whereStr += " and a.mp_pid like '%" + StringEscapeUtils.escapeSql(pid.getConditions().get("match"))
							+ "%'";
				}
				if (pid.getConditions().get("eq") != null) {
					whereStr += " and a.mp_pid = '" + StringEscapeUtils.escapeSql(pid.getConditions().get("eq")) + "'";
				}
			}

			Condition puid = conditions.get("puid"); // UID
			if (puid != null) {
				if (puid.getConditions().get("match") != null) {
					whereStr += " and u.mu_uid like '%" + StringEscapeUtils.escapeSql(puid.getConditions().get("match"))
							+ "%'";
				}
				if (puid.getConditions().get("eq") != null) {
					whereStr += " and u.mu_.uid = '" + StringEscapeUtils.escapeSql(puid.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition showid = conditions.get("showid"); // 人员编号
			if (showid != null) {
				if (showid.getConditions().get("match") != null) {
					whereStr += " and a.mp_showid like '%"
							+ StringEscapeUtils.escapeSql(showid.getConditions().get("match")) + "%'";
				}

				if (showid.getConditions().get("eq") != null) {
					whereStr += " and a.mp_showid = '" + StringEscapeUtils.escapeSql(showid.getConditions().get("eq"))
							+ "'";
				}
			}

			Condition cname = conditions.get("cname"); // 中文名
			if (cname != null) {
				if (cname.getConditions().get("match") != null) {
					String tmp = YESUtil.getLikeStr(cname.getConditions().get("match"));
					whereStr += " and ( a.mp_employeeNo like " + tmp + "  or  a.mp_cname like " + tmp
							+ " or a.mp_pinyin like " + tmp + " or a.mp_py like  " + tmp + " )";
				}
			}

			Condition ename = conditions.get("ename"); // 英文名
			if (ename != null) {
				if (ename.getConditions().get("match") != null) {
					whereStr += " and a.mp_ename like '%"
							+ StringEscapeUtils.escapeSql(ename.getConditions().get("match")) + "%'";
				}
			}

			Condition sex = conditions.get("sex"); // 性别
			if (sex != null) {
				if (sex.getConditions().get("eq") != null) {
					whereStr += " and a.mp_sex = '" + StringEscapeUtils.escapeSql(sex.getConditions().get("eq")) + "'";
				}
			}

			Condition phone = conditions.get("phone"); // 办公电话
			if (phone != null) {
				if (phone.getConditions().get("match") != null) {
					whereStr += " and (a.mp_officePhone like '%"
							+ StringEscapeUtils.escapeSql(phone.getConditions().get("match")) + "%'"
							+ "	or a.mobilePhone like '%"
							+ StringEscapeUtils.escapeSql(phone.getConditions().get("match")) + "%')";
				}
			}

			Condition mail = conditions.get("mail"); // 电子邮件
			if (mail != null) {
				if (mail.getConditions().get("match") != null) {
					whereStr += " and a.mp_mail like '%"
							+ StringEscapeUtils.escapeSql(mail.getConditions().get("match")) + "%'";
				}
			}

			Condition enable = conditions.get("enable"); // 状态
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.mp_enable = " + StringEscapeUtils.escapeSql(enable.getConditions().get("eq"));
				}
			}

			if (parm.containsKey("path") && parm.containsKey("eid")) {
				String oids = parm.get("searchOids").toString();
				whereStr += " and EXISTS (select 1 from  base_midentity i  where i.mi_pid=a.mp_pid  and i.mi_eid='"
						+ eid + "'  and  i.mi_oid in  (" + oids + ")   ) ";
			}

			String positionId = "";
			if (parm.containsKey("position")) {
				positionId = YESUtil.toString(parm.get("position"));
			}
			if (YESUtil.isNotEmpty(positionId)) {
				whereStr += " and EXISTS (select 1 from base_midentity i  where i.mi_pid=a.mp_pid and i.mi_positionId="
						+ YESUtil.getQuotedstr(positionId) + " )";
			}

			String postId = "";
			if (parm.containsKey("post")) {
				postId = YESUtil.toString(parm.get("post"));
			}

			if (YESUtil.isNotEmpty(postId)) {
				whereStr += " and EXISTS (select 1 from base_midentity i  where i.mi_pid=a.mp_pid and i.mi_postId="
						+ YESUtil.getQuotedstr(postId) + " )";
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " group by a.uuid , a.mp_eid , a.mp_pid , a.mp_cname , a.mp_py , a.mp_pinyin , a.mp_shortName ,a.mp_sex , a.mp_seq , a.mp_employeeNo , a.mp_birthday , a.mp_officePhone , a.mp_mobilePhone , a.mp_mail , a.mp_seq , a.mp_enable ,a.mp_memo , a.mp_type ,u.mu_uid order by positionSeq , a.mp_seq , a.mp_pinyin asc ";

			Query q = em.createNativeQuery("select count(distinct a.uuid) " + hql);
			Long records = YESUtil.objtolong(q.getSingleResult());
			qr.setCount(records);
			map.put("allcount", records);
			if (!"".equals(orderStr))
				hql += orderStr;

			qr.setItems(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	@Override
	public boolean hadInEmployeeNo(String employeeNo, String uid) {
		String h = " select count(uid) from Person where employeeNo=:employeeNo ";
		if (BaseUtil.isNotEmpty(uid)) {
			h += " and uid<>:uid";
		}
		Query qry = em.createQuery(h).setParameter("employeeNo", employeeNo);
		if (BaseUtil.isNotEmpty(uid)) {
			qry.setParameter("uid", uid);
		}

		long l = (long) qry.getSingleResult();
		return l > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Person getByPidAndEid(String pid, String eid) {
		String h = " select p  from Person p where p.pid=:pid and p.eid like :eid ";
		List<Person> lp = em.createQuery(h).setParameter("pid", pid).setParameter("eid", eid + "%").getResultList();
		return YESUtil.isEmpty(lp) ? null : lp.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult listByOidsAndEid(List<String> oids, String eid) {
		QueryResult qr = new QueryResult();
		String hql = "   from Person p , Identity i where p.pid=i.pid and i.eid=p.eid and p.eid=:eid and i.oid in (:oid) and p.enable=true";
		String orderStr = " order by p.pid desc";

		try {
			Query q = em.createQuery("select count(distinct p.uid)  " + hql).setParameter("eid", eid)
					.setParameter("oid", oids);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			if (!"".equals(orderStr)) {
				hql += orderStr;
			}
			q = em.createQuery("select distinct p   " + hql).setParameter("eid", eid).setParameter("oid", oids);
			List<Person> ps = q.getResultList();
			qr.setItems(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Person getByPid(String pid) {
		String h = " select p  from Person p where p.pid=:pid  ";
		List<Person> lp = em.createQuery(h).setParameter("pid", pid).getResultList();
		return YESUtil.isEmpty(lp) ? null : lp.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listAllUid() {
		String h = " select p.uid from Person p";
		return em.createQuery(h).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Person getByStaffno(String staffno, String eid) {
		String h = " from Person p where p.employeeNo=:employeeNo and p.enable=:enable and  p.eid like :eid";
		List<Person> ps = em.createQuery(h).setParameter("employeeNo", staffno).setParameter("enable", true)
				.setParameter("eid", eid + "%").getResultList();
		return YESUtil.isEmpty(ps) ? null : ps.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean findByEmployeeNo(String pid, String employeeNo, String eid) {
		String h = "select 1 from Person p where p.employeeNo=:employeeNo and p.eid=:eid ";
		if (YESUtil.isNotEmpty(pid)) {
			h += " and p.pid<>'" + pid + "' ";
		}
		List<Integer> ls = em.createQuery(h).setParameter("employeeNo", employeeNo).setParameter("eid", eid)
				.getResultList();
		return YESUtil.isEmpty(ls);
	}

}
