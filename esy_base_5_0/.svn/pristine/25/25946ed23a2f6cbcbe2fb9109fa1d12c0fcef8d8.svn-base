package org.esy.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IAppGroupMemberDao;
import org.esy.base.dao.IApplicationDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.AppGroup;
import org.esy.base.entity.AppGroupMember;
import org.esy.base.entity.Application;
import org.esy.base.entity.dto.AppGroupDto;
import org.esy.base.service.IAccountService;
import org.esy.base.service.IAppAuthorityService;
import org.esy.base.service.IAppGroupMemberService;
import org.esy.base.service.IAppGroupService;
import org.esy.base.service.IApplicationService;
import org.esy.base.service.ISerialService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Service implement for 企业信息
 *
 */
@Repository
public class ApplicationServiceImpl implements IApplicationService {

	@Autowired
	private IApplicationDao applicationDao;

	@Autowired
	private IAppAuthorityService appAuthorityService;

	@Autowired
	private IAppGroupService appGroupService;

	@Autowired
	private IAppGroupMemberService appGroupMemberService;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private ISerialService serialService;

	@Autowired
	private IAppGroupMemberDao appGroupMemberDao;

	@Override
	@Transactional
	public Application save(Application o) {
		return applicationDao.save(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppGroupDto> getApplicationsByAccount(String aid) {
		Account account = accountService.getById(aid);
		if (account != null) {
			List<Application> apps = applicationDao.getApplicationsByAccount(account);
			if (apps.size() > 0) {
				Map<String, Object> parm = new HashMap<String, Object>();
				parm.put("count", 999);

				List<String> appids = new ArrayList<String>();
				for (Application app : apps) {
					appids.add(app.getAid());
				}
				List<AppGroupMember> aGroupMembers = appGroupMemberService.getAppGroupMemberByAppids(appids);
				if (aGroupMembers.size() > 0) {
					for (AppGroupMember gm : aGroupMembers) {
						for (Application app : apps) {
							if (app.getAid().equals(gm.getAppid())) {
								gm.setApplication(app);
								break;
							}
						}
					}

					List<AppGroup> aGroups = (List<AppGroup>) appGroupService.query(parm).getItems();
					List<AppGroupDto> results = new ArrayList<AppGroupDto>();
					setGroups(results, aGroups, "", aGroupMembers);

					return results;
				}
			}
		}
		return null;
	}

	public void setGroups(List<AppGroupDto> rs, List<AppGroup> ls, String agid, List<AppGroupMember> gms) {
		for (int i = 0; i < ls.size(); i++) {
			AppGroup lg = ls.get(i);
			if (lg.getPid().equals(agid)) {
				AppGroupDto agd = new AppGroupDto();
				agd.setChildrens(new ArrayList<AppGroupDto>());
				agd.setApplications(new ArrayList<Application>());
				agd.setAgid(lg.getAgid());
				agd.setName(lg.getName());
				agd.setSeq(lg.getSeq());
				for (AppGroupMember gm : gms) {
					if (agd.getAgid().equals(gm.getAgid())) {
						agd.getApplications().add(gm.getApplication());
					}
				}
				rs.add(agd);
				setGroups(agd.getChildrens(), ls, agd.getAgid(), gms);
			}
		}
	}

	@Transactional
	@Override
	public Application save(Application o, Application old, HttpServletRequest request) {
		boolean isNew = false;
		String[] agidss = o.getGroups();
		List<String> agids = java.util.Arrays.asList(agidss);
		if (o.checkNew()) {
			isNew = true;
			String tempNo = serialService.getSerialString("base", "Application", "", 8, 99999999l);
			o.setAid(tempNo);
			if (YESUtil.isEmpty(o.getEid()))
				o.setEid(YESUtil.toString(BaseUtil.getUser().getEid()));
		}
		o = applicationDao.save(o);
		String eid = o.getEid();
		String appid = o.getAid();

		List<String> oldAgids = appGroupMemberDao.listAgidByAppidAndEid(appid, eid);
		if (YESUtil.isEmpty(oldAgids))
			oldAgids = new ArrayList<String>();

		// 已经存在不动
		// 不存在的加入,并计算最大seq值
		for (String agid : agids) {
			if (!oldAgids.contains(agid)) {
				AppGroupMember agm = new AppGroupMember();
				agm.setAgid(agid);
				agm.setAppid(appid);
				agm.setEid(eid);
				Integer seq = appGroupMemberDao.getMaxSeq(agid, eid);
				agm.setSeq(++seq);
				agm.setShow(o.getName());
				appGroupMemberDao.save(agm);
			}
		}
		// 已经存在的消失-删除
		if (!isNew) {
			for (String oldAgid : oldAgids) {
				if (!agids.contains(oldAgid)) {
					appGroupMemberDao.deleteByAppidAndEidAndAgid(appid, oldAgid, eid);
				}
			}
		}
		return o;
	}

	@Override
	public Application getByUid(String uid) {
		return applicationDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(Application o) {
		appGroupMemberService.deleteByAid(o.getAid());
		appAuthorityService.deleteByAid(o.getAid());
		return applicationDao.delete(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public QueryResult query(Map<String, Object> parm) {
		if (YESUtil.isEmpty(parm.get("eid"))) {
			parm.put("eid", BaseUtil.getUser().getEid());
		}

		QueryResult qr = applicationDao.query(parm);
		List<Application> apps = (List<Application>) qr.getItems();
		if (YESUtil.isNotEmpty(apps)) {
			for (Application app : apps) {
				List<String> agids = appGroupMemberDao.listAgidByAppidAndEid(app.getAid(), app.getEid());
				agids.remove(app.getAid());
				if (YESUtil.isNotEmpty(agids)) {
					int size = agids.size();
					app.setGroups((String[]) agids.toArray(new String[size]));
				}
			}
			qr.setItems(apps);
		}
		return qr;
	}

	@Override
	public Application getByIDandPW(String id, String password) {
		if (BaseUtil.isEmpty(id) || BaseUtil.isEmpty(password))
			return null;
		return applicationDao.getByIDandPW(id, password);
	}

	@Override
	public String getEidByAid(String aid) {
		if (YESUtil.isEmpty(aid))
			return null;
		return applicationDao.getEidByAid(aid);
	}

}
