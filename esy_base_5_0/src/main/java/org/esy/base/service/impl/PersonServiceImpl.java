package org.esy.base.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.IWebClientNotifier;
import org.esy.base.core.QueryResult;
import org.esy.base.core.WebClientXmlNotifier;
import org.esy.base.dao.IAccountDao;
import org.esy.base.dao.IDicDetailDao;
import org.esy.base.dao.IPersonDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Identity;
import org.esy.base.entity.Person;
import org.esy.base.entity.Uid;
import org.esy.base.entity.dto.PersonIdentityDto;
import org.esy.base.entity.pojo.IdentityXmlPojo;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.entity.pojo.PersonXmlPojo;
import org.esy.base.security.util.BCrypt;
import org.esy.base.service.IIdentityService;
import org.esy.base.service.IOrganizationService;
import org.esy.base.service.IPersonService;
import org.esy.base.service.ISerialService;
import org.esy.base.service.IUidService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.BizUtils;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Service implement for 人员信息
 *
 */
@Repository
public class PersonServiceImpl implements IPersonService {

	@Autowired
	IPersonDao personDao;

	@Autowired
	private ISerialService serialService;

	@Autowired
	private IUidService uidService;

	@Autowired
	private IIdentityService identityService;

	@Autowired
	private IOrganizationService organizationService;

	@Autowired
	private IAccountDao accountDao;

	@Autowired
	private IDicDetailDao dicDetailDao;

	@Transactional
	@Override
	public Person save(Person o) {
		return personDao.save(o);
	}

	@Override
	public boolean checkForEmployeeNo(String pid, String employeeNo, String eid) {
		return personDao.findByEmployeeNo(pid, employeeNo, eid);
	}

	@Override
	@Transactional
	public Person save(Person o, Person old, List<Identity> identitys, HttpServletRequest request) {
		// webservice用的personpojo
		PersonXmlPojo pxp = new PersonXmlPojo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 保存人员
		boolean isNew = false;
		if (o.checkNew()) {
			isNew = true;
			String tempNo = serialService.getSerialString("base", "Person", "", 8, 99999999l);
			o.setPid(tempNo);
			if (YESUtil.isNotEmpty(BaseUtil.getUser())) {
				o.setEid(YESUtil.toString(BaseUtil.getUser().getEid()));
			}
		} else {
			pxp.setUid(o.getUid());
		}
		o.setPinyin(YESUtil.ChineseToPinyin(o.getCname())); // 姓名拼音
		o.setPy(YESUtil.ChineseToPinyinHeader(o.getCname(), true));
		o = personDao.save(o);

		// webservice用的personpojo
		pxp.setEid(o.getEid());
		pxp.setEmployeeNo(o.getEmployeeNo());
		pxp.setPid(o.getPid());
		pxp.setCname(o.getCname());
		pxp.setPy(o.getPy());
		pxp.setPinyin(o.getPinyin());
		pxp.setBirthday(YESUtil.isNotEmpty(o.getBirthday()) ? sdf.format(o.getBirthday()) : null);
		pxp.setOfficePhone(o.getOfficePhone());
		pxp.setMobilePhone(o.getMobilePhone());
		pxp.setSex(o.getSex());
		pxp.setMail(o.getMail());
		pxp.setEnable(YESUtil.toString(o.getEnable()));
		pxp.setSeq(YESUtil.toString(o.getSeq()));
		pxp.setType(o.getType());
		pxp.setMemo(o.getMemo());

		// 删除身份
		if (!isNew)
			identityService.deleteByPid(o.getPid()); // 保存身份
		for (Identity identity : identitys) {
			if (YESUtil.isEmpty(identity)) {
				continue;
			}
			identity.setPid(o.getPid());
			/* identity.setUid(YESUtil.getUuid()); */
			if (YESUtil.isEmpty(identity.getIsMain()))
				identity.setIsMain(false);
			if (YESUtil.isEmpty(identity.getPositionId()))
				identity.setPositionId("");
			if (YESUtil.isEmpty(identity.getPostId()))
				identity.setPostId("");
			if (YESUtil.isEmpty(identity.getEid()))
				identity.setEid(BaseUtil.getUser().getEid());
			identityService.save(identity);
		}

		// webservice用的identitypojo
		try {
			for (Identity i : identitys) {

				IdentityXmlPojo itx = new IdentityXmlPojo();
				itx.setEid(i.getEid());
				itx.setPid(i.getPid());
				itx.setOid(i.getOid());
				itx.setPositionId(i.getPositionId());
				itx.setPostId(i.getPostId());
				itx.setEnable(YESUtil.toString(i.getEnable()));
				itx.setType(i.getType());
				itx.setIsMain(YESUtil.toString(i.getIsMain()));
				itx.setSeq(YESUtil.toString(i.getSeq()));
				itx.setStartDate(sdf.format(i.getStartDate()));
				if (YESUtil.isNotEmpty(i.getToDate())) {
					itx.setToDate(sdf.format(i.getToDate()));
				}
				pxp.getIdentity().add(itx);

			}

			WebClientXmlNotifier wcxn = new WebClientXmlNotifier();
			IWebClientNotifier cn = (IWebClientNotifier) BizUtils.getBean("cxfWebClient");
			wcxn.addListener(cn, pxp, YESUtil.isEmpty(pxp.getUid()) ? "add" : "update");
			wcxn.notifyX();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 保存UID
		if (isNew) {
			Uid uid = new Uid();
			uid.setName(o.getCname());
			if (YESUtil.isEmpty(o.getBirthday())) {
				uid.setBirthday("");
			} else {
				uid.setBirthday(YESUtil.getDateString(o.getBirthday()));
			}

			uid.setPid(o.getPid());
			String id = uidService.getNewUid("", "");
			uid.setUid(id);
			if ("1".equals(o.getType())) { // 正式职工
				uid.setStaffNo(o.getEmployeeNo());
			} else { // 临时职工
				uid.setTempStaffNo(o.getEmployeeNo());
			}
			uid.setStatus(uidService.getStatus(uid));
			uidService.save(uid);
		}

		// 新增帐号 wwc 2016-9-14
		String isToCreated = YESUtil.toString(dicDetailDao.getValueByDdidandEid("CREATEACCOUNT", "")).trim();
		if (isNew && "1".equals(isToCreated)) {
			Account account = new Account();
			account.setAid(getNewAid());
			account.setName(o.getCname());
			account.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt()));
			account.setMatrixNo(o.getPid());
			account.setType("user");
			account.setEid(BaseUtil.getUser().getEid());
			account.setEnable(true);
			accountDao.save(account);
		}
		return o;
	}

	/**
	 * 取得新的 aid
	 * 
	 * @return
	 */
	private String getNewAid() {
		return serialService.getSerialString("base", "account", "", 8, 99999999l);
	}

	@Override
	public Person getByUid(String uid) {
		return personDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(Person o) {
		identityService.deleteByPid(o.getPid());
		boolean flag = personDao.delete(o);
		if (flag) {
			// webservice用的personpojo
			PersonXmlPojo pxp = new PersonXmlPojo();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			pxp.setUid(o.getUid());
			pxp.setEid(o.getEid());
			pxp.setEmployeeNo(o.getEmployeeNo());
			pxp.setPid(o.getPid());
			pxp.setCname(o.getCname());
			pxp.setPy(o.getPy());
			pxp.setPinyin(o.getPinyin());
			pxp.setBirthday(YESUtil.isNotEmpty(o.getBirthday()) ? sdf.format(o.getBirthday()) : null);
			pxp.setOfficePhone(o.getOfficePhone());
			pxp.setMobilePhone(o.getMobilePhone());
			pxp.setSex(o.getSex());
			pxp.setMail(o.getMail());
			pxp.setEnable(YESUtil.toString(o.getEnable()));
			pxp.setSeq(YESUtil.toString(o.getSeq()));
			pxp.setType(o.getType());
			pxp.setMemo(o.getMemo());
			try {
				WebClientXmlNotifier wcxn = new WebClientXmlNotifier();
				IWebClientNotifier cn = (IWebClientNotifier) BizUtils.getBean("cxfWebClient");
				wcxn.addListener(cn, pxp, "delete");
				wcxn.notifyX();
			} catch (Exception e) {

			}
		}
		return flag;

	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		if (YESUtil.isEmpty(parm.get("eid"))) {
			Account a = BaseUtil.getUser();
			parm.put("eid", YESUtil.toString(a.getEid()));
		}

		QueryResult qr = personDao.query2(parm);
		if (BaseUtil.isEmpty(qr))
			return qr;

		@SuppressWarnings("unchecked")
		List<Person> ps = ((List<Person>) qr.getItems());
		if (BaseUtil.isEmpty(ps))
			return qr;

		for (Person p : ps) {
			List<String> postNames = identityService.listPostNameByPid(p.getPid()); // 岗位
			List<Map<String, String>> positionIdNames = identityService.listPositionIdNameByPid(p.getPid());// 职位
			List<String> orgNames = identityService.listOrgNameByPid(p.getPid());
			// List<String> positionNames =
			// identityService.listPositionNameByPid(p.getPid()); // 职位
			String s1 = "", buff1 = "", s2 = "", s3 = "", buff2 = "", buff3 = "", s4 = "";
			if (YESUtil.isNotEmpty(postNames)) {
				for (String postName : postNames) {
					s1 += buff1 + postName;
					buff1 = ",";
				}
			}
			if (YESUtil.isNotEmpty(positionIdNames)) {
				for (Map<String, String> positionName : positionIdNames) {
					s2 += buff2 + positionName.get("name");
					s3 += buff2 + positionName.get("pid");
					buff2 = ",";
				}
			}
			if (YESUtil.isNotEmpty(orgNames)) {
				for (String orgName : orgNames) {
					s4 += buff3 + orgName;
					buff3 = ",";
				}
			}
			p.setOrgName(s4);
			p.setPositionName(s2);
			p.setPositionId(s3);
			p.setPostName(s1);
		}
		qr.setItems(ps);
		return qr;
	}

	@Override
	public MsgResult checkForSave(Person p) {
		MsgResult mr = new MsgResult(false, "");
		if (BaseUtil.isEmpty(p)) {
			mr.setMsg("人员不能为空");
			return mr;
		}

		if ("1".equals(p.getType())) {
			if (BaseUtil.isEmpty(p.getEmployeeNo())) {
				mr.setMsg("职工号不能为空");
				return mr;
			}
		}

		if (BaseUtil.isEmpty(p.getCname())) {
			mr.setMsg("姓名不能为空");
			return mr;
		}

		if (BaseUtil.isEmpty(p.getEid())) {
			mr.setMsg("eid不能为空");
			return mr;
		}

		// 职工号不能重复
		// 备注：中烟人员平台不用判断eid， 其他平台要判断eid
		// if (personDao.hadInEmployeeNo(p.getEmployeeNo(), p.getUid())) {
		// mr.setMsg("无法保存,已存在相同职工号!");
		// return mr;
		// }
		if (!this.checkForEmployeeNo(p.getPid(), p.getEmployeeNo(), p.getEid())) {
			mr.setMsg("无法保存,已存在相同职工号!");
			return mr;
		}

		mr.setSuccess(true);
		return mr;
	}

	@Override
	public Person getByPidAndEid(String pid, String eid) {
		return null;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public QueryResult findpeople(Map<String, Object> parm) {
		QueryResult qr = personDao.query(parm);
		if (YESUtil.isEmpty(qr))
			return null;
		List<Person> lps = (List<Person>) qr.getItems();
		if (YESUtil.isEmpty(lps))
			return null;

		List<PersonIdentityDto> pds = new ArrayList<PersonIdentityDto>();
		return null;
	}

	@Override
	public Map<String, Object> search(Map<String, Object> parm) {
		if (YESUtil.isEmpty(parm.get("eid"))) {
			parm.put("eid", BaseUtil.getUser().getEid());
		}
		return personDao.search(parm);
	}

	@Override
	public Map<String, Object> getOrgCount(Map<String, Object> parm) {
		if (YESUtil.isEmpty(parm.get("eid"))) {
			Account a = BaseUtil.getUser();
			parm.put("eid", YESUtil.toString(a.getEid()));
		}

		if (parm.containsKey("path") && parm.containsKey("eid")) {
			String path = parm.get("path").toString();
			String eid = parm.get("eid").toString();
			List<String> oids = organizationService.listChildNodesOidByPath(path, eid);
			String searchOids = "", buff = "";
			for (String oid : oids) {
				searchOids += buff + "'" + oid + "'";
				buff = ",";
			}
			parm.put("searchOids", searchOids);
		}

		return personDao.getOrgCount(parm);
	}

	@Override
	public QueryResult listByPersonOfOrganization(Person p) {
		List<String> oids = organizationService.listOidsByPerson(p);
		if (YESUtil.isEmpty(oids))
			return new QueryResult();
		return personDao.listByOidsAndEid(oids, p.getEid());
	}

	@Override
	public Person getByPid(String pid) {
		if (YESUtil.isEmpty(pid))
			return null;
		return personDao.getByPid(pid);
	}

	@Override
	@Transactional
	public boolean fixPersonPy() {
		List<String> uids = personDao.listAllUid();
		int i = 0;
		for (String uid : uids) {
			i++;
			System.out.println("begin:" + i + " :" + uid);
			Person p = personDao.getByUid(uid);
			String name = p.getCname().trim();
			if (YESUtil.isNotEmpty(name)) {
				p.setPy(YESUtil.ChineseToPinyinHeader(name, true));
				p.setPinyin(YESUtil.ChineseToPinyin(name));
				personDao.save(p);
			}
			System.out.println("over" + i + " :" + uid);
		}
		return false;
	}

}
