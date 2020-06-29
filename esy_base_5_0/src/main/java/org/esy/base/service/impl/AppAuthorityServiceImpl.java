package org.esy.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IAppAuthorityDao;
import org.esy.base.dao.ICustomDao;
import org.esy.base.dao.IEnterpriseDao;
import org.esy.base.dao.IGroupMemberDao;
import org.esy.base.dao.IOrganizationDao;
import org.esy.base.entity.AppAuthority;
import org.esy.base.entity.AppGroupMember;
import org.esy.base.entity.Organization;
import org.esy.base.entity.OrganizationRelation;
import org.esy.base.entity.Person;
import org.esy.base.enums.MemberType;
import org.esy.base.service.IAppAuthorityService;
import org.esy.base.service.IAuthorityService;
import org.esy.base.service.IIdentityService;
import org.esy.base.service.IOrganizationRelationService;
import org.esy.base.service.IOrganizationService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Service implement for 权限主表
 *
 */
@Repository
public class AppAuthorityServiceImpl implements IAppAuthorityService {

	@Autowired
	IAppAuthorityDao appAuthorityDao;

	@Autowired
	private IGroupMemberDao groupMemberDao;

	@Autowired
	private IEnterpriseDao enterpriseDao;

	@Autowired
	private ICustomDao customDao;

	@Autowired
	private IIdentityService identityService;

	@Autowired
	private IAuthorityService authorityService;

	@Autowired
	private IOrganizationService organizationService;

	@Autowired
	private IOrganizationRelationService organizationRelationService;

	@Autowired
	private IOrganizationDao organizationDao;

	@Override
	public int updateShowByValueAndType(String type, String value, String newShow) {
		return appAuthorityDao.updateShowByValueAndType(type, value, newShow);
	}

	@Transactional
	@Override
	public AppAuthority save(AppAuthority o) {
		if (YESUtil.isEmpty(o.getEid())) {
			o.setEid(BaseUtil.getUser().getEid());
		}

		// 职务 = 职务+组织
		// 岗位= 岗位+组织
		if ((MemberType.Position.toString().equals(o.getType()) && YESUtil.isNotEmpty(o.getValue2()))
				|| (MemberType.Post.toString().equals(o.getType()) && YESUtil.isNotEmpty(o.getValue2()))) {
			o.setType2(MemberType.Organization.toString());
		}
		String[] values = o.getValue().split(",");
		String[] shows = o.getShow().split(",");
		String[] showids = o.getShowid().split(",");
		Map<String, AppAuthority> temp = new HashMap<String, AppAuthority>();
		for (int i = 0; i < values.length; i++) {
			String val = values[i];
			AppAuthority at = new AppAuthority();
			if (YESUtil.isEmpty(o.getEid())) {
				at.setEid(BaseUtil.getUser().getEid());
			} else {
				at.setEid(o.getEid());
			}
			at.setType(o.getType());
			at.setValue(val);
			at.setShow(shows[i]);
			at.setShowid(showids[i]);
			at.setAid(o.getAid());
			at.setType2(o.getType2());
			at.setValue2(o.getValue2());
			at.setShow2(o.getShow2());
			at.setShowid2(o.getShowid2());
			temp.put(val, at);
		}

		List<AppAuthority> gms = appAuthorityDao.getAppAuthorityByValuesAndTypeAndAid(values, o.getType(), o.getAid());
		for (AppAuthority gm : gms) {
			temp.remove(gm.getValue());
		}
		Set<String> keys = temp.keySet();
		for (String key : keys) {
			AppAuthority t = temp.get(key);
			appAuthorityDao.save(t);
		}
		return o;
	}

	@Override
	public AppAuthority getByUid(String uid) {
		return appAuthorityDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(AppAuthority o) {
		return appAuthorityDao.delete(o);
	}

	@Override
	public int deleteByValuesAndType(String values, String type) {
		return appAuthorityDao.deleteByValuesAndType(values.split(","), type);
	}

	@Override
	public boolean deleteByAid(String aid) {
		return appAuthorityDao.deleteByAid(aid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult query(Map<String, Object> parm) {
		QueryResult qr = appAuthorityDao.query(parm);
		List<AppAuthority> items = (List<AppAuthority>) qr.getItems();
		if (YESUtil.isEmpty(items))
			return qr;
		for (AppAuthority au : items) {
			String type = au.getType();
			if (type.equals(MemberType.Account.toString())) {
				au.setShow(YESUtil.toString(customDao.getNameById("Account", "name", "aid", au.getValue())));
			} else if (type.equals(MemberType.Enterprise.toString())) {
				au.setShow(YESUtil.toString(customDao.getNameById("Enterprise", "cname", "no", au.getValue())));
			} else if (type.equals(MemberType.Organization.toString())) {
				// OrganizationRelation or =
				// organizationRelationService.getByUid(au.getValue());
				// String path = or.getPath();
				// String[] ids = path.split("\\.");
				// String name = "", buff = "";
				// for (String id : ids) {
				// Organization o = organizationDao.getByOid(id);
				// if (YESUtil.isNotEmpty(o)) {
				// name += buff + o.getName();
				// buff = "/";
				// }
				// }
				// au.setShow(name);
				au.setShow(YESUtil.toString(customDao.getNameById("Organization", "name", "oid", au.getShowid())));
			} else if (type.equals(MemberType.Position.toString())) {
				au.setShow(YESUtil.toString(customDao.getNameById("Position", "name", "pid", au.getValue())));
			} else if (type.equals(MemberType.Post.toString())) {
				au.setShow(YESUtil.toString(customDao.getNameById("Post", "name", "pid", au.getValue())));
			} else if (type.equals(MemberType.Person.toString())) {
				au.setShow(YESUtil.toString(customDao.getNameById("Person", "cname", "pid", au.getValue())));
			} else if (type.equals(MemberType.Group.toString())) {
				au.setShow(YESUtil.toString(customDao.getNameById("Group", "name", "gid", au.getValue())));
			}

			type = YESUtil.toString(au.getType2());
			if (YESUtil.isNotEmpty(type)) {
				if (type.equals(MemberType.Account.toString())) {
					au.setShow2(YESUtil.toString(customDao.getNameById("Account", "name", "aid", au.getValue2())));
				} else if (type.equals(MemberType.Enterprise.toString())) {
					au.setShow2(YESUtil.toString(customDao.getNameById("Enterprise", "cname", "no", au.getValue2())));
				} else if (type.equals(MemberType.Organization.toString())) {
					au.setShow2(
							YESUtil.toString(customDao.getNameById("Organization", "name", "oid", au.getShowid2())));
				} else if (type.equals(MemberType.Position.toString())) {
					au.setShow2(YESUtil.toString(customDao.getNameById("Position", "name", "pid", au.getValue2())));
				} else if (type.equals(MemberType.Post.toString())) {
					au.setShow2(YESUtil.toString(customDao.getNameById("Post", "name", "pid", au.getValue2())));
				} else if (type.equals(MemberType.Person.toString())) {
					au.setShow2(YESUtil.toString(customDao.getNameById("Person", "cname", "pid", au.getValue2())));
				} else if (type.equals(MemberType.Group.toString())) {
					au.setShow2(YESUtil.toString(customDao.getNameById("Group", "name", "gid", au.getValue2())));
				}
			}
		}

		qr.setItems(items);
		return qr;
	}

	private String getOidByParentOid(String uid) {
		return organizationService.listChildrenOidsByRelationUid(uid);
	}

	@Override
	public QueryResult listGroupPeople(Map<String, Object> parm) {
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);
		QueryResult qr = new QueryResult();
		String hql = "";
		String hqlBuff = "";
		Condition vaid = conditions.get("aid"); // 应用编号
		String aid = "";
		if (YESUtil.isNotEmpty(vaid)) {
			aid = vaid.getConditions().get("eq");
		}
		if (YESUtil.isEmpty(aid)) {
			return qr;
		}

		List<AppAuthority> apps = appAuthorityDao.listByAid(aid);
		if (YESUtil.isEmpty(apps)) {
			return qr;
		}

		List<String> classifys = new ArrayList<String>(); // 企业类型
		List<String> eids = new ArrayList<String>(); // 企业eid
		List<String> pids = new ArrayList<String>(); // 人员ID

		for (AppAuthority gm : apps) {
			if (MemberType.Account.toString().equals(gm.getType())) {// 账号
				hql += hqlBuff + "  ( p.pid='" + gm.getValue() + "') ";
				hqlBuff = " or ";
			} else if (MemberType.EnterpriseType.toString().equals(gm.getType()))
				classifys.add(gm.getValue());
			else if (MemberType.Enterprise.toString().equals(gm.getType()))
				eids.add(gm.getValue());
			else if (MemberType.Person.toString().equals(gm.getType()))
				pids.add(gm.getValue());
			else if (YESUtil.isNotEmpty(gm.getValue2())) {
				if (MemberType.Position.toString().equals(gm.getType())) { // 组织+职位
					String includeOids = this.getOidByParentOid(gm.getValue2());
					if (YESUtil.isNotEmpty(includeOids)) {
						hql += hqlBuff + "( i.oid in (" + includeOids + ")  and i.positionId="
								+ YESUtil.getQuotedstr(gm.getValue()) + "  )  ";
						hqlBuff = " or ";
					}
				} else if (MemberType.Post.toString().equals(gm.getType())) { // 组织+岗位
					String includeOids = this.getOidByParentOid(gm.getValue2());
					if (YESUtil.isNotEmpty(includeOids)) {
						hql += hqlBuff + "( i.oid in (" + includeOids + ")  and i.postId="
								+ YESUtil.getQuotedstr(gm.getValue()) + "  )  ";
						hqlBuff = " or ";
					}
				}
			} else if (MemberType.Organization.toString().equals(gm.getType())) {// 组织
				OrganizationRelation relation = organizationRelationService.getByUid(gm.getValue());
				if (YESUtil.isNotEmpty(relation)) {
					String path = relation.getPath();
					hql += hqlBuff + "   r.path like " + YESUtil.getRightLikeStr(path);
					hqlBuff = " or ";
				}
			}
		}

		// 企业
		List<String> tmpeids = new ArrayList<String>();
		if (YESUtil.isNotEmpty(eids)) {
			for (String eid : eids) {
				String veid = enterpriseDao.getEidByNo(eid);
				if (YESUtil.isNotEmpty(veid))
					tmpeids.add(veid);
			}
			eids.clear();
			eids.addAll(tmpeids);
		}
		if (YESUtil.isNotEmpty(classifys)) {
			List<String> eeids = enterpriseDao.listEidByClassify(classifys);
			eids.removeAll(eeids);
			eids.addAll(eeids);
		}
		if (YESUtil.isNotEmpty(eids)) {
			String buff = "", tempSql = "";
			List<String> realEids = enterpriseDao.listEidsByParentEids(eids);
			for (String eid : realEids) {
				tempSql += buff + "'" + eid + "'";
				buff = ",";
			}
			hql += hqlBuff + "  p.eid in (" + tempSql + ")";
			hqlBuff = " or ";
		}

		// 人员
		if (YESUtil.isNotEmpty(pids)) {
			String buff = "", tempSql = "";
			for (String pid : pids) {
				tempSql += buff + "'" + pid + "'";
				buff = ",";
			}
			hql += hqlBuff + "  p.pid in (" + tempSql + ")";
			hqlBuff = " or ";
		}

		if (YESUtil.isEmpty(hql))
			return qr;

		hql = " and (" + hql + ")";
		// 获取sql
		qr = groupMemberDao.listGroupPeople(parm, hql);
		// 查询组织
		// 查询职位
		@SuppressWarnings("unchecked")
		List<Person> ps = ((List<Person>) qr.getItems());
		if (BaseUtil.isEmpty(ps))
			return qr;

		for (Person p : ps) {
			String pid = p.getPid();
			List<String> positionNames = identityService.listPositionNameByPid(pid);
			List<String> orgNames = identityService.listOrgNameByPid(pid);

			String s1 = "", buff1 = "", s2 = "", buff2 = "";
			if (YESUtil.isNotEmpty(orgNames)) {
				for (String org : orgNames) {
					s1 += buff1 + org;
					buff1 = ",";
				}
			}
			if (YESUtil.isNotEmpty(positionNames)) {
				for (String positionName : positionNames) {
					s2 += buff2 + positionName;
					buff2 = ",";
				}
			}
			p.setPositionName(s2);
			p.setOrgName(s1);
		}
		qr.setItems(ps);
		return qr;
	}

	@Override
	public QueryResult findByPerson(String pid, String eid) {
		QueryResult qr = new QueryResult();
		String sql = authorityService.getPersonSql(pid, eid);
		if (YESUtil.isEmpty(sql))
			return qr;

		List<AppAuthority> as = appAuthorityDao.findByPerson(sql);
		qr.setItems(as);
		return qr;
	}

	@Override
	public QueryResult findApplicationByPerson(String pid, String eid) {
		QueryResult qr = new QueryResult();

		String sql = authorityService.getPersonSql(pid, eid);
		if (YESUtil.isEmpty(sql))
			return qr;

		List<AppGroupMember> apps = appAuthorityDao.findApplicationByPerson(sql);
		qr.setItems(apps);
		return qr;
	}

	@Override
	public QueryResult findByAppAuthority(Map<String, Object> parm) {
		QueryResult qr = new QueryResult();
		List<AppGroupMember> apps = appAuthorityDao.findByAppAuthority(parm);
		qr.setItems(apps);
		return qr;
	}
}
