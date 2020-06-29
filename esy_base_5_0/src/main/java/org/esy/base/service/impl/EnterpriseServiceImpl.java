package org.esy.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.ICustomDao;
import org.esy.base.dao.IEnterpriseDao;
import org.esy.base.dao.YSDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Enterprise;
import org.esy.base.entity.Person;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.enums.MemberType;
import org.esy.base.service.IAccountService;
import org.esy.base.service.IAppAuthorityService;
import org.esy.base.service.IAttachmentService;
import org.esy.base.service.IAuthorityService;
import org.esy.base.service.IEnterpriseService;
import org.esy.base.service.IGroupMemberService;
import org.esy.base.service.IPersonService;
import org.esy.base.service.ISerialService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Service implement for 企业信息
 *
 */
@Repository
public class EnterpriseServiceImpl implements IEnterpriseService {

	@Autowired
	IEnterpriseDao enterpriseDao;

	@Autowired
	private IAttachmentService attachmentService;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IGroupMemberService groupMemberService;

	@Autowired
	private IAuthorityService authorityService;

	@Autowired
	private IAppAuthorityService appAuthorityService;

	@Autowired
	private ISerialService serialService;

	@Autowired
	private IPersonService personService;

	@Autowired
	private ICustomDao customDao;

	@Autowired
	private YSDao dao;

	@Override
	@Transactional
	public Enterprise save(Enterprise o) {
		return enterpriseDao.save(o);
	}

	@Transactional
	@Override
	public Enterprise save(Enterprise o, Enterprise old, HttpServletRequest request) {
		if (o.checkNew()) {
			String tempNo = serialService.getSerialString("base", "Enterprise", "", 8, 99999999l);
			o.setNo(tempNo);
			String peid = o.getEid();// 如果是新建的时候,这个eid 存的值实际上是parent的eid
			if (YESUtil.isNotEmpty(peid)) {
				o.setEid(peid + "." + tempNo);
			} else {
				return null;
			}
		}
		if (YESUtil.isEmpty(o.getAdmin())) {
			Account manager = new Account();
			manager.setEid(o.getEid());
			manager.setType("enterprise");
			manager.setMatrixNo(o.getEid());
			manager.setName(o.getCname());
			manager.setEnable(true);
			manager.setPassword("123456");
			manager = accountService.save(manager, null, request);
			o.setAdmin(manager.getAid());
		}
		// 处理level
		if (YESUtil.isNotEmpty(o.getPid())) {
			Enterprise pent = enterpriseDao.getByNo(o.getPid());
			if (YESUtil.isNotEmpty(pent)) {
				int level = pent.getLevel();
				o.setLevel(++level);
			}
		}
		o = enterpriseDao.save(o);
		Map<String, Object> upMap = new HashMap<String, Object>();
		upMap.put("entityName", "Enterprise");
		upMap.put("fieldName", "attachment");
		upMap.put("attachmentId", o.getAttachment());
		upMap.put("entityUid", o.getUid());
		attachmentService.updateBinds(upMap);
		if (YESUtil.isNotEmpty(old)) {
			if (!old.getCname().equals(o.getCname())) {
				appAuthorityService.updateShowByValueAndType(MemberType.Enterprise.toString(), o.getNo(), o.getCname());
				groupMemberService.updateShowByValueAndType(MemberType.Enterprise.toString(), o.getNo(), o.getCname());
				authorityService.updateShowByValueAndType(MemberType.Enterprise.toString(), o.getNo(), o.getCname());
			}
		}
		return o;
	}

	@Override
	public Enterprise getById(String eid) {
		return enterpriseDao.getById(eid);
	}

	@Override
	public Enterprise getByUid(String uid) {
		return enterpriseDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(Enterprise o) {
		appAuthorityService.deleteByValuesAndType(o.getNo(), MemberType.Enterprise.toString());
		groupMemberService.deleteByValuesAndType(o.getNo(), MemberType.Enterprise.toString());
		authorityService.deleteByValuesAndType(o.getNo(), MemberType.Enterprise.toString());
		return enterpriseDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		Account a = BaseUtil.getUser();
		parm.put("eid", YESUtil.toString(a.getEid()));
		return enterpriseDao.query(parm);
	}

	@Override
	public List<Enterprise> listByEid(String eid) {
		return enterpriseDao.listByEid(eid);
	}

	@Override
	public QueryResult listByPidAndClassify(Map<String, Object> parm) {
		if (!parm.containsKey("pid")) {
			Account user = BaseUtil.getUser();
			Enterprise en = this.getById(user.getEid());
			parm.put("pid", en.getNo());
		}
		return enterpriseDao.listByPidAndClassify(parm);
	}

	@Override
	public List<String> listEidsByParentEids(List<String> eids) {
		return enterpriseDao.listEidsByParentEids(eids);
	}

	@Override
	public List<Enterprise> listByPid(String pid) {
		if (YESUtil.isEmpty(pid))
			return null;
		Person p = personService.getByPid(pid);
		String eid = p.getEid();
		String[] eids = eid.split("\\.");
		List<String> nos = new ArrayList<String>();
		for (String no : eids) {
			if (YESUtil.isNotEmpty(no)) {
				nos.add(no);
			}
		}
		if (YESUtil.isEmpty(nos))
			return null;

		return enterpriseDao.listByNos(nos);
	}

	@Override
	public MsgResult checkForDelele(Enterprise enterprise) {
		MsgResult mr = new MsgResult(false, "");
		if (YESUtil.isEmpty(enterprise)) {
			mr.setMsg("该企业查无数据，可能已经被删除!");
			return mr;
		}

		// 上级组织可删除下级
		String eid = enterprise.getEid();
		String usereid = BaseUtil.getUser().getEid();
		boolean isRight = usereid.length() <= eid.length() && eid.indexOf(usereid) > -1;
		if (!isRight) {
			mr.setMsg("无删除权限!");
			return mr;
		}

		// 有子企业
		if (customDao.exsitsInTable("Enterprise", "pid", enterprise.getNo())) {
			mr.setMsg("请先删除子企业!");
			return mr;
		}

		// 1.人员先删除
		if (customDao.exsitsInTable("Identity", "eid", eid)) {
			mr.setMsg("请先删除企业内人员!");
			return mr;
		}

		// 2.组织 ,职位，岗位删除
		if (customDao.exsitsInTable("OrganizationRelation", "eid", eid)) {
			mr.setMsg("请先删除企业内组织!");
			return mr;
		}

		if (customDao.exsitsInTable("Position", "eid", eid)) {
			mr.setMsg("请先删除企业内职位!");
			return mr;
		}

		if (customDao.exsitsInTable("Post", "eid", eid)) {
			mr.setMsg("请先删除企业内岗位!");
			return mr;
		}

		// 3.群组，权限，应用要删除 TODO
		if (customDao.exsitsInTable("Group", "eid", eid)) {
			mr.setMsg("请先删除企业内的群组!");
			return mr;
		}

		if (customDao.exsitsInTable("GroupMember", "eid", eid)) {
			mr.setMsg("请先删除企业内的群组成员!");
			return mr;
		}

		if (customDao.exsitsInTable("Application", "eid", eid)) {
			mr.setMsg("请先删除企业内的应用!");
			return mr;
		}

		// 权限 TODO
		mr.setSuccess(true);
		return mr;
	}

	@Override
	public Page<Enterprise> query(Enterprise enterprise, Pageable pageable) {
		return dao.query(Enterprise.class, enterprise, pageable);
	}
}
