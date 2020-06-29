package org.esy.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IEnterpriseDao;
import org.esy.base.entity.Enterprise;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

/**
 * 
 * Dao implement for 企业信息
 *
 */
@Repository
public class EnterpriseDaoImpl implements IEnterpriseDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Enterprise save(Enterprise o) {
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
	public Enterprise getByUid(String uid) {
		return this.em.find(Enterprise.class, uid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enterprise getById(String eid) {
		Enterprise o = null;
		if (!"".equals(eid)) {
			String hql = "from Enterprise a where a.eid = '" + StringEscapeUtils.escapeSql(eid) + "'";
			Query q = em.createQuery(hql);
			try {
				List<Enterprise> ls = q.getResultList();
				if (YESUtil.isNotEmpty(ls))
					o = ls.get(0);
			} catch (NoResultException e) {
				e.printStackTrace();
			}
		}
		return o;
	}

	@Override
	public boolean delete(Enterprise o) {
		;
		String hql = "delete Enterprise where uid='" + o.getUid() + "'";
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

		// TODO
		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));
			String order = YESUtil.toString(parm.get("order"));

			String hql = "from Enterprise a where 1=1";

			String whereStr = "";

			if (parm.containsKey("eid")) {
				whereStr += " and a.eid like '" + parm.get("eid").toString() + ".%'";
			}

			Condition alias = conditions.get("alias"); // 别名
			if (alias != null) {
				if (alias.getConditions().get("eq") != null) {
					whereStr += " and a.mnemonic = '" + StringEscapeUtils.escapeSql(alias.getConditions().get("eq")) + "'";
				}
				if (alias.getConditions().get("match") != null) {
					whereStr += " and a.mnemonic like '%" + StringEscapeUtils.escapeSql(alias.getConditions().get("match")) + "%'";
				}
			}

			Condition no = conditions.get("no"); // 编号
			if (no != null) {
				if (no.getConditions().get("eq") != null) {
					whereStr += " and a.no = '" + StringEscapeUtils.escapeSql(no.getConditions().get("eq")) + "'";
				}
				if (no.getConditions().get("match") != null) {
					whereStr += " and a.no like '%" + StringEscapeUtils.escapeSql(no.getConditions().get("match")) + "%'";
				}
			}

			Condition group = conditions.get("group"); // 集团路径
			if (group != null) {
				if (group.getConditions().get("eq") != null) {
					whereStr += " and a.group = '" + StringEscapeUtils.escapeSql(group.getConditions().get("eq")) + "'";
				}
				if (group.getConditions().get("match") != null) {
					whereStr += " and a.group like '%" + StringEscapeUtils.escapeSql(group.getConditions().get("match")) + "%'";
				}
			}

			Condition cname = conditions.get("cname"); // 中文全称
			if (cname != null) {
				if (cname.getConditions().get("match") != null) {
					whereStr += " and a.cname like '%" + StringEscapeUtils.escapeSql(cname.getConditions().get("match")) + "%'";
				}
			}

			Condition ename = conditions.get("ename"); // 英文全称
			if (ename != null) {
				if (ename.getConditions().get("match") != null) {
					whereStr += " and a.ename like '%" + StringEscapeUtils.escapeSql(ename.getConditions().get("match")) + "%'";
				}
			}

			Condition csName = conditions.get("csName"); // 中文简称
			if (csName != null) {
				if (csName.getConditions().get("match") != null) {
					whereStr += " and a.csName like '%" + StringEscapeUtils.escapeSql(csName.getConditions().get("match")) + "%'";
				}
			}

			Condition esName = conditions.get("esName"); // 英文简称
			if (esName != null) {
				if (esName.getConditions().get("match") != null) {
					whereStr += " and a.esName like '%" + StringEscapeUtils.escapeSql(esName.getConditions().get("match")) + "%'";
				}
			}

			Condition orgCode = conditions.get("orgCode"); // 组织机构代码证号码
			if (orgCode != null) {
				if (orgCode.getConditions().get("match") != null) {
					whereStr += " and a.orgCode like '%" + StringEscapeUtils.escapeSql(orgCode.getConditions().get("match")) + "%'";
				}
			}

			Condition regCode = conditions.get("regCode"); // 营业执照号码
			if (regCode != null) {
				if (regCode.getConditions().get("match") != null) {
					whereStr += " and a.regCode like '%" + StringEscapeUtils.escapeSql(regCode.getConditions().get("match")) + "%'";
				}
			}

			Condition taxCode = conditions.get("taxCode"); // 企业税号
			if (taxCode != null) {
				if (taxCode.getConditions().get("match") != null) {
					whereStr += " and a.taxCode like '%" + StringEscapeUtils.escapeSql(taxCode.getConditions().get("match")) + "%'";
				}
			}

			Condition regProvince = conditions.get("regProvince"); // 注册地（省）
			if (regProvince != null) {
				if (regProvince.getConditions().get("eq") != null) {
					whereStr += " and a.regProvince = '" + StringEscapeUtils.escapeSql(regProvince.getConditions().get("eq")) + "'";
				}
			}

			Condition regCity = conditions.get("regCity"); // 注册地（市）
			if (regCity != null) {
				if (regCity.getConditions().get("eq") != null) {
					whereStr += " and a.regCity = '" + StringEscapeUtils.escapeSql(regCity.getConditions().get("eq")) + "'";
				}
			}

			Condition regDistrict = conditions.get("regDistrict"); // 注册地（区）
			if (regDistrict != null) {
				if (regDistrict.getConditions().get("eq") != null) {
					whereStr += " and a.regDistrict = '" + StringEscapeUtils.escapeSql(regDistrict.getConditions().get("eq")) + "'";
				}
			}

			Condition regAddr = conditions.get("regAddr"); // 注册地址
			if (regAddr != null) {
				if (regAddr.getConditions().get("match") != null) {
					whereStr += " and a.regAddr like '%" + StringEscapeUtils.escapeSql(regAddr.getConditions().get("match")) + "%'";
				}
			}

			Condition regTel = conditions.get("regTel"); // 联系电话
			if (regTel != null) {
				if (regTel.getConditions().get("match") != null) {
					whereStr += " and a.regTel like '%" + StringEscapeUtils.escapeSql(regTel.getConditions().get("match")) + "%'";
				}
			}

			Condition legalPerson = conditions.get("legalPerson"); // 法定代表人
			if (legalPerson != null) {
				if (legalPerson.getConditions().get("eq") != null) {
					whereStr += " and a.legalPerson = '" + StringEscapeUtils.escapeSql(legalPerson.getConditions().get("eq")) + "'";
				}
			}

			Condition contact = conditions.get("contact"); // 联系人
			if (contact != null) {
				if (contact.getConditions().get("eq") != null) {
					whereStr += " and a.contact = '" + StringEscapeUtils.escapeSql(contact.getConditions().get("eq")) + "'";
				}
			}

			Condition admin = conditions.get("admin"); // 管理员
			if (admin != null) {
				if (admin.getConditions().get("eq") != null) {
					whereStr += " and a.admin = '" + StringEscapeUtils.escapeSql(admin.getConditions().get("eq")) + "'";
				}
			}

			Condition website = conditions.get("website"); // 网址
			if (website != null) {
				if (website.getConditions().get("match") != null) {
					whereStr += " and a.website like '%" + StringEscapeUtils.escapeSql(website.getConditions().get("match")) + "%'";
				}
			}

			Condition level = conditions.get("level"); // 级别
			if (level != null) {
				if (level.getConditions().get("eq") != null) {
					whereStr += " and a.level = " + StringEscapeUtils.escapeSql(level.getConditions().get("eq"));
				}
			}

			Condition regCapital = conditions.get("regCapital"); // 注册资金（万元）
			if (regCapital != null) {
				if (regCapital.getConditions().get("gte") != null) {
					whereStr += " and a.regCapital >= " + StringEscapeUtils.escapeSql(regCapital.getConditions().get("gte"));
				}
				if (regCapital.getConditions().get("lte") != null) {
					whereStr += " and a.regCapital <= " + StringEscapeUtils.escapeSql(regCapital.getConditions().get("lte"));
				}
			}

			Condition enable = conditions.get("enable"); // 状态
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.enable = " + StringEscapeUtils.escapeSql(enable.getConditions().get("eq"));
				}
			}

			Condition regDate = conditions.get("regDate"); // 注册日期
			if (regDate != null) {
				if (regDate.getConditions().get("gte") != null) {
					whereStr += " and a.regDate >= '" + StringEscapeUtils.escapeSql(regDate.getConditions().get("gte")) + "'";
				}
				if (regDate.getConditions().get("lte") != null) {
					whereStr += " and a.regDate <= '" + StringEscapeUtils.escapeSql(regDate.getConditions().get("lte")) + "'";
				}
			}

			// pid
			Condition pid = conditions.get("pid");
			if (pid != null) {
				if (pid.getConditions().get("eq") != null) {
					whereStr += " and a.pid = " + StringEscapeUtils.escapeSql(level.getConditions().get("eq"));
				}
			}

			// classify
			Condition classify = conditions.get("classify");
			if (classify != null) {
				if (classify.getConditions().get("eq") != null) {
					whereStr += " and a.level = " + StringEscapeUtils.escapeSql(level.getConditions().get("eq"));
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = "";
			orderStr = " order by a.updated desc";

			if (order.equals("eid")) {
				orderStr = " order by a.eid asc";
			}

			if (order.equals("mnemonic")) {
				orderStr = " order by a.mnemonic asc";
			}

			if (order.equals("group")) {
				orderStr = " order by a.group asc";
			}

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Enterprise.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public Enterprise getByCname(String cname) {
		Enterprise o = null;
		if (!"".equals(cname)) {
			String hql = "from Enterprise a where a.cname = '" + StringEscapeUtils.escapeSql(cname) + "'";
			Query q = em.createQuery(hql);
			try {
				o = (Enterprise) q.getSingleResult();
			} catch (NoResultException e) {

			}
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Enterprise> listByEid(String eid) {
		String h = " from Enterprise e where e.eid like :eid and enable=true order by level, no";
		return em.createQuery(h).setParameter("eid", eid + "%").getResultList();
	}

	@Override
	public QueryResult listByPidAndClassify(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);

		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));

			String hql = "from Enterprise a where 1=1";
			String whereStr = "";

			Condition alias = conditions.get("alias"); // 别名
			if (alias != null) {
				if (alias.getConditions().get("eq") != null) {
					whereStr += " and a.mnemonic = '" + StringEscapeUtils.escapeSql(alias.getConditions().get("eq")) + "'";
				}
				if (alias.getConditions().get("match") != null) {
					whereStr += " and a.mnemonic like '%" + StringEscapeUtils.escapeSql(alias.getConditions().get("match")) + "%'";
				}
			}

			Condition no = conditions.get("no"); // 编号
			if (no != null) {
				if (no.getConditions().get("eq") != null) {
					whereStr += " and a.no = '" + StringEscapeUtils.escapeSql(no.getConditions().get("eq")) + "'";
				}
				if (no.getConditions().get("match") != null) {
					whereStr += " and a.no like '%" + StringEscapeUtils.escapeSql(no.getConditions().get("match")) + "%'";
				}
			}

			Condition group = conditions.get("group"); // 集团路径
			if (group != null) {
				if (group.getConditions().get("eq") != null) {
					whereStr += " and a.group = '" + StringEscapeUtils.escapeSql(group.getConditions().get("eq")) + "'";
				}
				if (group.getConditions().get("match") != null) {
					whereStr += " and a.group like '%" + StringEscapeUtils.escapeSql(group.getConditions().get("match")) + "%'";
				}
			}

			Condition cname = conditions.get("cname"); // 中文全称
			if (cname != null) {
				if (cname.getConditions().get("match") != null) {
					whereStr += " and a.cname like '%" + StringEscapeUtils.escapeSql(cname.getConditions().get("match")) + "%'";
				}
			}

			Condition ename = conditions.get("ename"); // 英文全称
			if (ename != null) {
				if (ename.getConditions().get("match") != null) {
					whereStr += " and a.ename like '%" + StringEscapeUtils.escapeSql(ename.getConditions().get("match")) + "%'";
				}
			}

			Condition csName = conditions.get("csName"); // 中文简称
			if (csName != null) {
				if (csName.getConditions().get("match") != null) {
					whereStr += " and a.csName like '%" + StringEscapeUtils.escapeSql(csName.getConditions().get("match")) + "%'";
				}
			}

			Condition esName = conditions.get("esName"); // 英文简称
			if (esName != null) {
				if (esName.getConditions().get("match") != null) {
					whereStr += " and a.esName like '%" + StringEscapeUtils.escapeSql(esName.getConditions().get("match")) + "%'";
				}
			}

			Condition orgCode = conditions.get("orgCode"); // 组织机构代码证号码
			if (orgCode != null) {
				if (orgCode.getConditions().get("match") != null) {
					whereStr += " and a.orgCode like '%" + StringEscapeUtils.escapeSql(orgCode.getConditions().get("match")) + "%'";
				}
			}

			Condition regCode = conditions.get("regCode"); // 营业执照号码
			if (regCode != null) {
				if (regCode.getConditions().get("match") != null) {
					whereStr += " and a.regCode like '%" + StringEscapeUtils.escapeSql(regCode.getConditions().get("match")) + "%'";
				}
			}

			Condition taxCode = conditions.get("taxCode"); // 企业税号
			if (taxCode != null) {
				if (taxCode.getConditions().get("match") != null) {
					whereStr += " and a.taxCode like '%" + StringEscapeUtils.escapeSql(taxCode.getConditions().get("match")) + "%'";
				}
			}

			Condition regProvince = conditions.get("regProvince"); // 注册地（省）
			if (regProvince != null) {
				if (regProvince.getConditions().get("eq") != null) {
					whereStr += " and a.regProvince = '" + StringEscapeUtils.escapeSql(regProvince.getConditions().get("eq")) + "'";
				}
			}

			Condition regCity = conditions.get("regCity"); // 注册地（市）
			if (regCity != null) {
				if (regCity.getConditions().get("eq") != null) {
					whereStr += " and a.regCity = '" + StringEscapeUtils.escapeSql(regCity.getConditions().get("eq")) + "'";
				}
			}

			Condition regDistrict = conditions.get("regDistrict"); // 注册地（区）
			if (regDistrict != null) {
				if (regDistrict.getConditions().get("eq") != null) {
					whereStr += " and a.regDistrict = '" + StringEscapeUtils.escapeSql(regDistrict.getConditions().get("eq")) + "'";
				}
			}

			Condition regAddr = conditions.get("regAddr"); // 注册地址
			if (regAddr != null) {
				if (regAddr.getConditions().get("match") != null) {
					whereStr += " and a.regAddr like '%" + StringEscapeUtils.escapeSql(regAddr.getConditions().get("match")) + "%'";
				}
			}

			Condition regTel = conditions.get("regTel"); // 联系电话
			if (regTel != null) {
				if (regTel.getConditions().get("match") != null) {
					whereStr += " and a.regTel like '%" + StringEscapeUtils.escapeSql(regTel.getConditions().get("match")) + "%'";
				}
			}

			Condition legalPerson = conditions.get("legalPerson"); // 法定代表人
			if (legalPerson != null) {
				if (legalPerson.getConditions().get("eq") != null) {
					whereStr += " and a.legalPerson = '" + StringEscapeUtils.escapeSql(legalPerson.getConditions().get("eq")) + "'";
				}
			}

			Condition contact = conditions.get("contact"); // 联系人
			if (contact != null) {
				if (contact.getConditions().get("eq") != null) {
					whereStr += " and a.contact = '" + StringEscapeUtils.escapeSql(contact.getConditions().get("eq")) + "'";
				}
			}

			Condition admin = conditions.get("admin"); // 管理员
			if (admin != null) {
				if (admin.getConditions().get("eq") != null) {
					whereStr += " and a.admin = '" + StringEscapeUtils.escapeSql(admin.getConditions().get("eq")) + "'";
				}
			}

			Condition website = conditions.get("website"); // 网址
			if (website != null) {
				if (website.getConditions().get("match") != null) {
					whereStr += " and a.website like '%" + StringEscapeUtils.escapeSql(website.getConditions().get("match")) + "%'";
				}
			}

			Condition level = conditions.get("level"); // 级别
			if (level != null) {
				if (level.getConditions().get("eq") != null) {
					whereStr += " and a.level = " + StringEscapeUtils.escapeSql(level.getConditions().get("eq"));
				}
			}

			Condition regCapital = conditions.get("regCapital"); // 注册资金（万元）
			if (regCapital != null) {
				if (regCapital.getConditions().get("gte") != null) {
					whereStr += " and a.regCapital >= " + StringEscapeUtils.escapeSql(regCapital.getConditions().get("gte"));
				}
				if (regCapital.getConditions().get("lte") != null) {
					whereStr += " and a.regCapital <= " + StringEscapeUtils.escapeSql(regCapital.getConditions().get("lte"));
				}
			}

			Condition enable = conditions.get("enable"); // 状态
			if (enable != null) {
				if (enable.getConditions().get("eq") != null) {
					whereStr += " and a.enable = " + StringEscapeUtils.escapeSql(enable.getConditions().get("eq"));
				}
			}

			Condition regDate = conditions.get("regDate"); // 注册日期
			if (regDate != null) {
				if (regDate.getConditions().get("gte") != null) {
					whereStr += " and a.regDate >= '" + StringEscapeUtils.escapeSql(regDate.getConditions().get("gte")) + "'";
				}
				if (regDate.getConditions().get("lte") != null) {
					whereStr += " and a.regDate <= '" + StringEscapeUtils.escapeSql(regDate.getConditions().get("lte")) + "'";
				}
			}
			String pid = null;
			String classify = null;

			Condition eid = conditions.get("eid"); // eid
			if (eid != null) {
				if (eid.getConditions().get("eq") != null) {
					whereStr += " and a.eid = " + YESUtil.getQuotedstr(eid.getConditions().get("eq"));
				} else if (eid.getConditions().get("match") != null) {
					whereStr += " and a.eid like  " + YESUtil.getRightLikeStr(eid.getConditions().get("match"));
				}
			}

			// 查询包含本身的eid
			Boolean included = false;
			if (parm.containsKey("includeeid")) {
				included = YESUtil.objToBoolean(parm.get("includeeid"));
			}

			if (parm.containsKey("pid") && (!included)) {
				pid = YESUtil.toString(parm.get("pid"));
				String[] pidarr = pid.split("\\.");
				pid = pidarr[pidarr.length - 1];
				whereStr += " and a.pid='" + pid + "'";
			}

			// classify
			if (parm.containsKey("classify")) {
				classify = YESUtil.toString(parm.get("classify"));
				whereStr += " and a.classify='" + classify + "'";
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			Query q = em.createQuery("select count(uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);

			String orderStr = "";
			orderStr = " order by a.level,a.no ";

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
			qr.setHeaders(QueryUtils.getClassFieldInfo(Enterprise.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listEidByClassify(List<String> classifys) {
		String h = " select distinct e.eid from Enterprise e where e.enable=:enable and e.classify in (:classify)";
		return em.createQuery(h).setParameter("enable", true).setParameter("classify", classifys).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getEidByNo(String no) {
		String h = " select  e.eid from Enterprise e where e.enable=:enable and e.no=:no";
		List<String> ls = em.createQuery(h).setParameter("enable", true).setParameter("no", no).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enterprise getByNo(String no) {
		String h = " from Enterprise e where  e.no=:no";
		List<Enterprise> ls = em.createQuery(h).setParameter("no", no).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listEidsByParentEids(List<String> eids) {
		String hql = "", buff = "";
		for (String eid : eids) {
			hql += buff + " e.eid=" + YESUtil.getQuotedstr(eid);
			buff = " or ";
			hql += buff + " e.eid like " + YESUtil.getRightLikeStr(eid + ".");
		}
		String h = " select e.eid from Enterprise e where (" + hql + ") and enable=:enable and e.eid<>'0'   group by e.eid ";
		return em.createQuery(h).setParameter("enable", true).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Enterprise> listByNos(List<String> nos) {
		String h = " from Enterprise e where  e.no in (:no) and e.eid<>'0'  and enable=true  ";
		return em.createQuery(h).setParameter("no", nos).getResultList();
	}

	@Override
	public boolean existsChildEnterprise(String pid) {
		Query q = em.createQuery("select count(1)  from Enterprise  a  where a.pid=:pid ");
		Long l = (Long) q.setParameter("pid", pid).getSingleResult();
		return l > 0;
	}

}
