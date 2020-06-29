package org.esy.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IAccountDao;
import org.esy.base.dao.IAppAuthorityDao;
import org.esy.base.dao.IAuthorityDao;
import org.esy.base.dao.IEnterpriseDao;
import org.esy.base.dao.IPersonDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Authority;
import org.esy.base.entity.AuthorityMenu;
import org.esy.base.entity.Enterprise;
import org.esy.base.entity.Identity;
import org.esy.base.entity.Menu;
import org.esy.base.entity.OrganizationRelation;
import org.esy.base.entity.Person;
import org.esy.base.entity.dto.MenuDto;
import org.esy.base.enums.MemberType;
import org.esy.base.service.IAuthorityService;
import org.esy.base.service.IIdentityService;
import org.esy.base.service.IOrganizationRelationService;
import org.esy.base.service.ISerialService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Service implement for 权限主表
 *
 */
@Repository
public class AuthorityServiceImpl implements IAuthorityService {

	@Autowired
	IAuthorityDao authorityDao;

	@Autowired
	private ISerialService serialService;

	@Autowired
	IAppAuthorityDao appAuthorityDao;

	@Autowired
	private IEnterpriseDao enterpriseDao;

	@Autowired
	private IIdentityService identityService;

	@Autowired
	private IPersonDao personDao;

	@Autowired
	private IAccountDao accountDao;

	@Autowired
	private IOrganizationRelationService organizationRelationService;

	@Transactional
	@Override
	public boolean save(AuthorityMenu am) {
		authorityDao.delDetail(am.getAid());
		if (YESUtil.isNotEmpty(am.getMid())) {
			String[] mids = am.getMid().split(",");
			for (String mid : mids) {
				AuthorityMenu temp = new AuthorityMenu();
				temp.setAid(am.getAid());
				temp.setMid(mid);
				authorityDao.save(temp);
			}
		}
		return true;
	}

	@Override
	public List<AuthorityMenu> getDetail(String aid) {
		return authorityDao.getDetail(aid);
	}

	@Override
	public int updateShowByValueAndType(String type, String value, String newShow) {
		return authorityDao.updateShowByValueAndType(type, value, newShow);
	}

	private String getStr(String str) {
		if (YESUtil.isEmpty(str))
			return " ";
		return str;
	}

	@Transactional
	@Override
	public Authority save(Authority o) {
		if ((MemberType.Position.toString().equals(o.getType()) && YESUtil.isNotEmpty(o.getValue2()))
				|| (MemberType.Post.toString().equals(o.getType()) && YESUtil.isNotEmpty(o.getValue2()))) {
			o.setType2(MemberType.Organization.toString());
		}

		String[] values = o.getValue().split(",");
		String[] shows = o.getShow().split(",");
		String[] eids = o.getEid().split(",");
		String[] showids = o.getShowid().split(",");

		Map<String, Authority> temp = new HashMap<String, Authority>();
		for (int i = 0; i < values.length; i++) {
			String val = values[i];
			Authority at = new Authority();
			at.setType(o.getType());
			at.setValue(val);
			at.setShow(shows[i]);
			at.setShowid(showids[i]);
			at.setEid(eids[i]);
			at.setType2(o.getType2());
			at.setValue2(o.getValue2());
			at.setShow2(o.getShow2());
			at.setShowid2(this.getStr(o.getShowid2())); // wwc 2016-11-2

			temp.put(val, at);
		}
		List<Authority> gms = authorityDao.getAuthorityByValuesAndType(values, o.getType());
		for (Authority gm : gms) {
			temp.remove(gm.getValue());
		}
		Set<String> keys = temp.keySet();
		for (String key : keys) {
			Authority t = temp.get(key);
			t.setAid(serialService.getSerialString("base", "Authority", "", 8, 99999999l));
			authorityDao.save(t);
		}
		return o;
	}

	@Override
	public Authority getByUid(String uid) {
		return authorityDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(Authority o) {
		boolean rs = authorityDao.delDetail(o.getAid());
		if (rs) {
			rs = authorityDao.delete(o);
		}
		return rs;
	}

	@Override
	public boolean deleteByValuesAndType(String values, String type) {
		List<Authority> ls = authorityDao.getAuthorityByValuesAndType(values.split(","), type);
		for (Authority en : ls) {
			if (delete(en) == false) {
				return false;
			}
		}
		return true;
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		Account a = BaseUtil.getUser();
		if (!"admin".equals(a.getType())) {
			parm.put("eid", YESUtil.toString(a.getEid()));
		}
		return authorityDao.query(parm);
	}

	@Override
	public QueryResult findByPerson(String pid, String eid) {
		QueryResult qr = new QueryResult();
		String sql = this.getPersonSql(pid, eid);
		if (YESUtil.isEmpty(sql))
			return qr;
		List<Authority> aus = authorityDao.listByPeron(sql);
		qr.setItems(aus);
		// qr.setCount(aus.size());
		return qr;
	}

	@Override
	public QueryResult findAuthorityMenuByPerson(String pid, String eid) {
		QueryResult qr = new QueryResult();
		String sql = this.getPersonSql(pid, eid);
		if (YESUtil.isEmpty(sql))
			return qr;
		List<MenuDto> ms = new ArrayList<MenuDto>();
		List<Menu> menus = authorityDao.listMenuByPeron(sql);
		for (Menu m : menus) {
			ms.add(new MenuDto(m));
		}

		qr.setItems(ms);
		return qr;
	}

	@Override
	public String getPersonSql(String pid, String eid) {
		Person p = personDao.getByPid(pid);
		if (YESUtil.isEmpty(p)) {
			return null;
		}
		// 人员对应的人员
		String sql = " ( a.value=" + YESUtil.getQuotedstr(pid) + " and a.type="
				+ YESUtil.getQuotedstr(YESUtil.toString(MemberType.Person)) + " )";

		// 人员对应的账号
		Account account = accountDao.getByPerson(p);
		if ((YESUtil.isNotEmpty(account)) && (YESUtil.isNotEmpty(account.getMatrixNo()))) {
			sql += " or (a.value=" + YESUtil.getQuotedstr(account.getAid()) + " and a.type="
					+ YESUtil.getQuotedstr(YESUtil.toString(MemberType.Account)) + " )";
		}

		// 人员对应的企业，企业类型 --> 递推出企业
		String[] eids = eid.split("\\.");
		if (eids.length > 1) {
			for (int i = 0; i < eids.length; i++) {
				String veid = eids[i];
				if (veid.equals("0")) // 根节点不处理
					continue;
				Enterprise enterprise = enterpriseDao.getByNo(veid);
				if (YESUtil.isNotEmpty(enterprise)) {
					sql += " or (a.value=" + YESUtil.getQuotedstr(enterprise.getNo()) + " and a.type="
							+ YESUtil.getQuotedstr(YESUtil.toString(MemberType.Enterprise)) + " )";
					sql += " or (a.value=" + YESUtil.getQuotedstr(enterprise.getClassify()) + " and a.type="
							+ YESUtil.getQuotedstr(YESUtil.toString(MemberType.EnterpriseType)) + " )";
				}
			}
		}

		// 人员对应的组织
		Map<String, String> oideids = new HashMap<String, String>();
		List<Identity> is = identityService.getByPidAndEnable(pid);
		if (YESUtil.isNotEmpty(is)) {
			for (Identity identity : is) {
				if (!oideids.containsKey(identity.getOid())) {
					oideids.put(identity.getOid(), identity.getEid());
				}
			}
		}
		List<String> uids = new ArrayList<String>();

		Iterator<Entry<String, String>> iter = oideids.entrySet().iterator();
		while (iter.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) iter.next();
			String toid = (String) entry.getKey();
			String teid = (String) entry.getValue();
			List<OrganizationRelation> ors = organizationRelationService.listByOid(toid, teid);
			for (OrganizationRelation or : ors) {
				if (!uids.contains(or.getUid())) {
					uids.add(or.getUid());
				}
			}
		}
		if (uids.size() > 0) {
			String tmp = "", buff = "";
			for (String u : uids) {
				tmp += buff + "'" + u + "'";
				buff = ",";
			}

			sql += " or (a.value in (" + tmp + ")" + " and a.type="
					+ YESUtil.getQuotedstr(YESUtil.toString(MemberType.Organization)) + ")";
		}

		// 人员对应的岗位，职位
		for (Identity identity : is) {
			if (YESUtil.isNotEmpty(identity.getPositionId())) { // 职位
				sql += " or (a.value=" + YESUtil.getQuotedstr(identity.getPositionId()) + " and a.type="
						+ YESUtil.getQuotedstr(YESUtil.toString(MemberType.Position)) + " and COALESCE(a.showid2,'')="
						+ YESUtil.getQuotedstr(YESUtil.toString(identity.getOid())) + " )";
			} else if (YESUtil.isNotEmpty(identity.getPostId())) { // 岗位
				sql += " or (a.value=" + YESUtil.getQuotedstr(identity.getPostId()) + " and a.type="
						+ YESUtil.getQuotedstr(YESUtil.toString(MemberType.Post)) + " and COALESCE(a.showid2,'')="
						+ YESUtil.getQuotedstr(YESUtil.toString(identity.getOid())) + " )";
			}
		}
		return sql;
	}

	@Override
	public List<Menu> listMenuByPeronForLogin(String sql) {
		return authorityDao.listMenuByPeronForLogin(sql);
	}

	@Override
	public QueryResult listMenuByAid(String aid) {
		QueryResult qr = new QueryResult();
		List<MenuDto> ms = new ArrayList<MenuDto>();
		List<Menu> menus = authorityDao.listMenuByAid(aid);
		for (Menu m : menus) {
			ms.add(new MenuDto(m));
		}
		qr.setItems(ms);
		return qr;
	}

	// 旭盈专用
	@Transactional
	@Override
	public void saveEntity(Authority b) {
		authorityDao.save(b);
	}

}
