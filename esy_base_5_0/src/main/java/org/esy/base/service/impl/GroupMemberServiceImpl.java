package org.esy.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IAccountDao;
import org.esy.base.dao.ICustomDao;
import org.esy.base.dao.IEnterpriseDao;
import org.esy.base.dao.IGroupMemberDao;
import org.esy.base.dao.IOrganizationDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.GroupMember;
import org.esy.base.entity.Organization;
import org.esy.base.entity.OrganizationRelation;
import org.esy.base.entity.Person;
import org.esy.base.enums.MemberType;
import org.esy.base.service.IGroupMemberService;
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
 * Service implement for 群组成员
 *
 */
@Repository
public class GroupMemberServiceImpl implements IGroupMemberService {

	@Autowired
	IGroupMemberDao groupMemberDao;

	@Autowired
	private ICustomDao customDao;

	@Autowired
	private IEnterpriseDao enterpriseDao;

	@Autowired
	private IIdentityService identityService;

	@Autowired
	private IAccountDao accountDao;

	// @Autowired
	// private IEnterpriseService enterpriseService;

	@Autowired
	private IOrganizationService organizationService;

	@Autowired
	private IOrganizationRelationService organizationRelationService;

	@Autowired
	private IOrganizationDao organizationDao;

	@Override
	public boolean deleteByGid(String gid) {
		return groupMemberDao.deleteByGid(gid);
	}

	@Override
	public int updateShowByValueAndType(String type, String value, String newShow) {
		return groupMemberDao.updateShowByValueAndType(type, value, newShow);
	}

	@Override
	public int deleteByValuesAndType(String values, String type) {
		return groupMemberDao.deleteByValuesAndType(values.split(","), type);
	}

	@Transactional
	@Override
	public GroupMember save(GroupMember o) {
		// 整理gm逻辑
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

		Map<String, GroupMember> temp = new HashMap<String, GroupMember>();
		for (int i = 0; i < values.length; i++) {
			String val = values[i];
			GroupMember gm = new GroupMember();
			gm.setGid(o.getGid());
			gm.setType(o.getType());
			gm.setValue(val);
			gm.setShow(shows[i]);
			gm.setShowid(showids[i]);
			gm.setEid(o.getEid());
			gm.setType2(o.getType2());
			gm.setValue2(o.getValue2());
			gm.setShow2(o.getShow2());
			gm.setShowid2(o.getShowid2());
			temp.put(val, gm);
		}
		List<GroupMember> gms = groupMemberDao.getGroupMemberByValuesAndTypeAndGid(values, o.getGid(), o.getType());
		for (GroupMember gm : gms) {
			temp.remove(gm.getValue());
		}
		Set<String> keys = temp.keySet();
		for (String key : keys) {
			groupMemberDao.save(temp.get(key));
		}
		return o;
	}

	@Override
	public GroupMember getByUid(String uid) {
		return groupMemberDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(GroupMember o) {
		return groupMemberDao.delete(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult query(Map<String, Object> parm) {
		QueryResult qr = groupMemberDao.query(parm);
		List<GroupMember> items = (List<GroupMember>) qr.getItems();
		if (YESUtil.isEmpty(items))
			return qr;

		for (GroupMember gm : items) {
			String type = gm.getType();
			if (type.equals(MemberType.Account.toString())) {
				gm.setShow(YESUtil.toString(customDao.getNameById("Account", "name", "aid", gm.getValue())));
			} else if (type.equals(MemberType.Enterprise.toString())) {
				gm.setShow(YESUtil.toString(customDao.getNameById("Enterprise", "cname", "no", gm.getValue())));
			} else if (type.equals(MemberType.Organization.toString())) {
				// gm.setShow(YESUtil.toString(customDao.getNameById("Organization",
				// "name", "oid", gm.getShowid())));
				OrganizationRelation or = organizationRelationService.getByUid(gm.getValue());
				String path = or.getPath();
				String[] ids = path.split("\\.");
				String name = "", buff = "";
				for (String id : ids) {
					Organization o = organizationDao.getByOid(id);
					if (YESUtil.isNotEmpty(o)) {
						name += buff + o.getName();
						buff = "/";
					}
				}
				gm.setShow(name);
			} else if (type.equals(MemberType.Position.toString())) {
				gm.setShow(YESUtil.toString(customDao.getNameById("Position", "name", "pid", gm.getValue())));
			} else if (type.equals(MemberType.Post.toString())) {
				gm.setShow(YESUtil.toString(customDao.getNameById("Post", "name", "pid", gm.getValue())));
			} else if (type.equals(MemberType.Person.toString())) {
				gm.setShow(YESUtil.toString(customDao.getNameById("Person", "cname", "pid", gm.getValue())));
			} else if (type.equals(MemberType.Group.toString())) {
				gm.setShow(YESUtil.toString(customDao.getNameById("Group", "name", "gid", gm.getValue())));
			}

			type = YESUtil.toString(gm.getType2());
			if (YESUtil.isNotEmpty(type)) {
				if (type.equals(MemberType.Account.toString())) {
					gm.setShow2(YESUtil.toString(customDao.getNameById("Account", "name", "aid", gm.getValue2())));
				} else if (type.equals(MemberType.Enterprise.toString())) {
					gm.setShow2(YESUtil.toString(customDao.getNameById("Enterprise", "cname", "no", gm.getValue2())));
				} else if (type.equals(MemberType.Organization.toString())) {
					gm.setShow2(
							YESUtil.toString(customDao.getNameById("Organization", "name", "oid", gm.getShowid2())));
				} else if (type.equals(MemberType.Position.toString())) {
					gm.setShow2(YESUtil.toString(customDao.getNameById("Position", "name", "pid", gm.getValue2())));
				} else if (type.equals(MemberType.Post.toString())) {
					gm.setShow2(YESUtil.toString(customDao.getNameById("Post", "name", "pid", gm.getValue2())));
				} else if (type.equals(MemberType.Person.toString())) {
					gm.setShow2(YESUtil.toString(customDao.getNameById("Person", "cname", "pid", gm.getValue2())));
				} else if (type.equals(MemberType.Group.toString())) {
					gm.setShow2(YESUtil.toString(customDao.getNameById("Group", "name", "gid", gm.getValue2())));
				}
			}
		}
		qr.setItems(items);
		return qr;
	}

	@Override
	public List<GroupMember> searchByGname(String gname, String eid) {

		return groupMemberDao.searchByGname(gname, eid);
	}

	@Override
	public List<GroupMember> getMembersByUserId() {
		return groupMemberDao.getMembersByUserId();
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
		Condition vgid = conditions.get("gid"); // 群组编号
		String gid = "";
		if (YESUtil.isNotEmpty(vgid)) {
			gid = vgid.getConditions().get("eq");
		}

		if (YESUtil.isEmpty(gid)) {
			return qr;
		}

		List<GroupMember> gms = groupMemberDao.listByGid(gid);
		if (YESUtil.isEmpty(gms)) {
			return qr;
		}

		List<String> classifys = new ArrayList<String>(); // 企业类型
		List<String> eids = new ArrayList<String>(); // 企业eid
		List<String> pids = new ArrayList<String>(); // 人员ID
		for (GroupMember gm : gms) {
			if (MemberType.EnterpriseType.toString().equals(gm.getType()))
				classifys.add(gm.getValue());
			else if (MemberType.Enterprise.toString().equals(gm.getType())) {
				eids.add(gm.getValue());
			} else if (MemberType.Person.toString().equals(gm.getType()))
				pids.add(gm.getValue());
			else if (MemberType.Account.toString().equals(gm.getType())) {
				Account account = accountDao.getById(gm.getValue());
				if (YESUtil.isNotEmpty(account) && (account.isEnable()) && (YESUtil.isNotEmpty(account.getMatrixNo())))
					pids.add(account.getMatrixNo());
			} else if (YESUtil.isNotEmpty(gm.getValue2())) {
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

				// String includeOids = this.getOidByParentOid(gm.getValue());
				// if (YESUtil.isNotEmpty(includeOids)) {
				// hql += hqlBuff + " exists (select 1 from Identity ident where
				// p.pid=ident.pid and ident.oid in (" +
				// this.getOidByParentOid(gm.getValue()) + ") )";
				// hqlBuff = " or ";
				// }
			}
		}

		// 企业分类 +企业
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
}
