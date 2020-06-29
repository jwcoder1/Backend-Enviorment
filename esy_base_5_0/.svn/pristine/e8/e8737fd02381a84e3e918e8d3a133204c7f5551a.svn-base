package org.esy.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.core.Response;
import org.esy.base.dao.IAccountDao;
import org.esy.base.dao.YSDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Menu;
import org.esy.base.entity.Person;
import org.esy.base.entity.dto.IdentityDto;
import org.esy.base.entity.dto.MenuDto;
import org.esy.base.entity.dto.NewAccountDto;
import org.esy.base.entity.dto.PersonAccountDto;
import org.esy.base.entity.pojo.IdentityPojo;
import org.esy.base.entity.view.Accountv;
import org.esy.base.enums.MemberType;
import org.esy.base.security.util.BCrypt;
import org.esy.base.service.IAccountService;
import org.esy.base.service.IAppAuthorityService;
import org.esy.base.service.IAttachmentService;
import org.esy.base.service.IAuthorityService;
import org.esy.base.service.IGroupMemberService;
import org.esy.base.service.ILogService;
import org.esy.base.service.IOrganizationService;
import org.esy.base.service.IPersonService;
import org.esy.base.service.ISerialService;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private YSDao dao;

	@Autowired
	private IAccountDao accountDao;

	@Autowired
	private ILogService logService;

	@Autowired
	private IAttachmentService attachmentService;

	@Autowired
	private IGroupMemberService groupMemberService;

	@Autowired
	private IAuthorityService authorityService;

	@Autowired
	private IAppAuthorityService appAuthorityService;

	@Autowired
	private ISerialService serialService;

	@Autowired
	private IOrganizationService organizationService;

	@Autowired
	private IPersonService personService;

	@Transactional
	@Override
	public Account save(Account o, Account old, HttpServletRequest request) {
		if (o.checkNew()) {
			o.setPassword(BCrypt.hashpw(o.getPassword(), BCrypt.gensalt()));
			if (YESUtil.isEmpty(o.getEid())) {
				o.setEid(YESUtil.toString(BaseUtil.getUser().getEid()));
			}

			String tempNo = serialService.getSerialString("base", "Account", "", 8, 99999999l);
			o.setAid(YESUtil.isEmpty(o.getAid()) ? tempNo : o.getAid());
			if (YESUtil.isNotEmpty(accountDao.getById(o.getAid()))) {
				return null;
			}
			logService.save("base", "AccountCreate",
					"[" + o.getAid() + "]" + o.getName() + " : 创建成功，状态：" + o.isEnable(), request.getRemoteAddr());
		} else {
			logService.save("base", "AccountModify",
					"[" + o.getAid() + "]" + o.getName() + " : 修改成功，状态：" + o.isEnable(), request.getRemoteAddr());
		}
		/*
		 * // 不允许更改账号编号 Account account = accountDao.getByUid(o.getUid()); if
		 * (!o.getAid().equals(account.getAid())) { o.setAid(account.getAid());
		 * } // 不允许修改登陆时间 if (o.getLastLogin() != null) { o.setLastLogin(null);
		 * }
		 */
		Account acc = save(o);
		if (YESUtil.isNotEmpty(acc.getPicture())) {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("entityUid", acc.getUid());
			parm.put("entityName", "Account");
			parm.put("fieldName", "picture");
			parm.put("attachmentId", acc.getPicture());
			attachmentService.updateBinds(parm);
		}
		if (YESUtil.isNotEmpty(old)) {
			if (!old.getName().equals(o.getName())) {
				appAuthorityService.updateShowByValueAndType(MemberType.Account.toString(), o.getAid(), o.getName());
				groupMemberService.updateShowByValueAndType(MemberType.Account.toString(), o.getAid(), o.getName());
				authorityService.updateShowByValueAndType(MemberType.Account.toString(), o.getAid(), o.getName());
			}
		}
		return acc;
	}

	@Transactional
	@Override
	public boolean delete(Account o, HttpServletRequest request) {
		String userid = o.getAid();
		String username = o.getName();
		boolean result = accountDao.delete(o);
		if (result) {
			appAuthorityService.deleteByValuesAndType(o.getAid(), MemberType.Account.toString());
			groupMemberService.deleteByValuesAndType(o.getAid(), MemberType.Account.toString());
			authorityService.deleteByValuesAndType(o.getAid(), MemberType.Account.toString());
			logService.save("base", "AccountDelete", "[" + userid + "]" + username + " : 删除成功",
					request.getRemoteAddr());
		}
		return result;
	}

	@Override
	public List<MenuDto> getAllByAccount(String accountId, String pid) {
		List<MenuDto> result = new ArrayList<MenuDto>();
		List<Menu> menus = accountDao.getAllByAccount(accountId, pid);
		for (Menu menu : menus) {
			result.add(new MenuDto(menu));
		}
		return result;
	}

	@Override
	public List<MenuDto> getMenuByAccount(String accountId, String pid) {
		List<MenuDto> result = new ArrayList<MenuDto>();
		List<Menu> menus = accountDao.getMenuByAccount(accountId, pid);
		for (Menu menu : menus) {
			result.add(new MenuDto(menu));
		}
		return result;
	}

	@Override
	public List<MenuDto> getSubAllByAccount(Account account) {
		List<MenuDto> result = new ArrayList<MenuDto>();
		List<Menu> menus = accountDao.getSubAllByAccount(account);
		if (YESUtil.isNotEmpty(menus)) {
			for (Menu menu : menus) {
				result.add(new MenuDto(menu));
			}
		}
		return result;
	}

	@Override
	public List<MenuDto> getSubMenuByAccount(String accountId, String pid) {
		List<MenuDto> result = new ArrayList<MenuDto>();
		List<Menu> menus = accountDao.getSubMenuByAccount(accountId, pid);
		for (Menu menu : menus) {
			result.add(new MenuDto(menu));
		}
		return result;
	}

	@Override
	public Account getById(String id) {
		return accountDao.getById(id);
	}

	@Override
	public Account getEnterPriseManger(String eid) {
		return accountDao.getEnterPriseManger(eid);
	}

	@Override
	@Transactional
	public Response newUserAccount(NewAccountDto newAccountDto) {
		Account account = new Account();
		account.setAid(getNewAid());
		account.setName(newAccountDto.getUsername());
		account.setPassword(newAccountDto.getPassword());
		account.setType("user");

		Response response;
		try {
			response = new Response(0, "success.", accountDao.save(account));
		} catch (Exception e) {
			response = new Response(500, e.getMessage(), null);
		}
		return response;

	}

	@Override
	@Transactional
	public Response newAdminAccount(NewAccountDto newAccountDto) {

		Account account = new Account();
		account.setAid(getNewAid());
		account.setName(newAccountDto.getUsername());
		account.setPassword(newAccountDto.getPassword());
		account.setType("admin");

		Response response;
		try {
			response = new Response(0, "success.", accountDao.save(account));
		} catch (Exception e) {
			response = new Response(500, e.getMessage(), null);
		}
		return response;

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
	@Transactional
	public Account save(Account o) {
		return accountDao.save(o);
	}

	@Override
	public Account getByUid(String uid) {
		return accountDao.getByUid(uid);
	}

	@Override
	@Transactional
	public boolean delete(Account o) {
		return accountDao.delete(o);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public QueryResult query(Map<String, Object> parm) {
		Account a = BaseUtil.getUser();

		if (!"admin".equals(a.getType())) {
			parm.put("eid", YESUtil.toString(a.getEid()));
		}

		QueryResult queryResult = accountDao.query(parm);
		List<Account> accounts = (List<Account>) queryResult.getItems();
		for (Account account : accounts) {
			account.setPassword("");
		}

		return queryResult;
	}

	@Override
	public HashSet<String> getAllPerdition(String accountId) {
		return accountDao.getAllPerdition(accountId);
	}

	@Override
	@Transactional
	public Response newUserAccountFromPerson(IdentityPojo identityPojo) {
		Response resp = new Response();
		resp.setError(HttpStatus.INTERNAL_SERVER_ERROR.value());

		PersonAccountDto pad = identityPojo.getPersonAccountDto();
		if (BaseUtil.isEmpty(pad)) {
			resp.setError(HttpStatus.OK.value());
			return resp;
		}

		Account account = new Account();
		account.setAid(getNewAid());
		account.setName(identityPojo.getPerson().getCname());

		// 随机密码
		String password = "";
		if (pad.getRandomPassword()) {
			password = YESUtil.getRandmoString(8);
		} else { // 使用传入的密码
			password = pad.getPassword();
		}
		// 发送邮件TODO
		account.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
		account.setType("user");
		account.setRemind(pad.getRemind()); // 下次登录提醒
		account.setMail(identityPojo.getPerson().getMail());
		account.setMobile(identityPojo.getPerson().getMobilePhone());
		account.setMatrixNo(identityPojo.getPerson().getPid()); // 关联帐号
		// 主职的eid
		String oid = "";
		for (IdentityDto ide : identityPojo.getIdentitys()) {
			if (ide.getType().equals("1"))
				oid = ide.getOid();
		}
		String eid = organizationService.getEidFromOid(oid);
		account.setEid(eid);
		Response response;
		try {
			response = new Response(0, "success.", accountDao.save(account));
		} catch (Exception e) {
			response = new Response(500, e.getMessage(), null);
		}
		return response;
	}

	@Override
	public List<Account> getDoctors(Map<String, Object> parm) {
		return accountDao.getDoctor(parm);
	}

	@Override
	public List<Account> listByEid() {
		String eid = BaseUtil.getUser().getEid();
		return accountDao.listByEid(eid);
	}

	@Override
	public String getLoginEid() {
		String eid = "";
		try {
			Account account = BaseUtil.getUser();
			if (YESUtil.isNotEmpty(account))
				eid = account.getEid();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eid;
	}

	@Override
	public Account getByPerson(Person p) {
		return accountDao.getByPerson(p);
	}

	@Override
	public Account getByMno(String eid) {

		return accountDao.getByMno(eid);
	}

	@Override
	public Boolean checkMenuValue(String value) {
		if (YESUtil.isEmpty(value))
			return false;
		Boolean flag = false;
		List<MenuDto> menus = BaseUtil.getUser().getMenus();
		if (YESUtil.isEmpty(menus))
			return flag;
		for (MenuDto menu : menus) {
			if (menu.getUid().equals(value)) {
				flag = true;
				return flag;
			}
		}
		return flag;
	}

	@Override
	public Object lovquery(Accountv account, Pageable pageable) {
		return dao.query(Accountv.class, account, pageable);
	}

}
