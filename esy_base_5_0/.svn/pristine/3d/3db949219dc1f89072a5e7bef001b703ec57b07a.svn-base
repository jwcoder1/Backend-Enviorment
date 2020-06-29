package org.esy.base.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.Response;
import org.esy.base.entity.Account;
import org.esy.base.entity.Authority;
import org.esy.base.entity.AuthorityMenu;
import org.esy.base.entity.dto.NewAccountDto;
import org.esy.base.entity.pojo.AccountPojo;
import org.esy.base.entity.view.Accountv;
import org.esy.base.http.HttpResult;
import org.esy.base.security.util.BCrypt;
import org.esy.base.service.IAccountService;
import org.esy.base.service.IAuthorityService;
import org.esy.base.service.ILoginService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.PropertiesUtils;
import org.esy.base.util.RestUtils;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Controller
@RequestMapping("/api/base/account")
public class AccountController {

	public static final String AUTHORITY = "base_account";

	public static List<String> types = new ArrayList<String>();

	public static String projectName = "";

	public static LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(10000)
			.expireAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<String, String>() {
				public String load(String key) throws Exception {
					return cache.get(key);
				};
			});;

	static {
		AccountController.types.add("user");
		AccountController.types.add("enterprise");
		AccountController.types.add("doctor");
		PropertiesUtils pro;
		try {
			pro = new PropertiesUtils();
			String accountTypes = YESUtil.toString(pro.getProperty("accountTypes"));
			AccountController.projectName = YESUtil.toString(pro.getProperty("projectName"));
			if (YESUtil.isNotEmpty(accountTypes))

			{
				String[] acctypes = accountTypes.split(",");
				for (String type : acctypes) {
					AccountController.types.add(type);
				}
			}
		} catch (

		IOException e)

		{
			e.printStackTrace();
		}
	}

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IAuthorityService authorityService;

	@RequestMapping(value = "identifyCode/{code}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> identifyCode(@PathVariable String code, @RequestParam String account,
			HttpServletRequest request) {
		Response resp;
		String identify = null;
		try {
			identify = cache.get(account);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if (identify == null) {
			resp = new Response(HttpStatus.GONE.value(), "验证码过期,请重新获取", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			if (identify.equals(code)) {
				resp = new Response(0, "success", account);
				return new ResponseEntity<Response>(resp, HttpStatus.OK);
			} else {
				resp = new Response(HttpStatus.GONE.value(), "验证码错误", null);
				return new ResponseEntity<Response>(resp, HttpStatus.GONE);
			}
		}
	}

	@RequestMapping(value = "mailUpdatePassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> sendIdentifyCode(@RequestBody Map<String, Object> parm,
			HttpServletRequest request) {
		Response resp;
		String account = YESUtil.toString(parm.get("account"));
		String identify = null;
		try {
			identify = cache.get(account);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		if (identify == null) {
			resp = new Response(HttpStatus.GONE.value(), "验证码过期,请重新获取", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			if (identify.equals(YESUtil.toString(parm.get("identify")))) {
				String newpwd = YESUtil.toString(parm.get("newpwd"));
				Account o = accountService.getById(account);
				if (o == null || YESUtil.isEmpty(newpwd)) {
					resp = new Response(HttpStatus.GONE.value(), "非法数据!", null);
					return new ResponseEntity<Response>(resp, HttpStatus.GONE);
				} else {
					o.setPassword(BCrypt.hashpw(newpwd, BCrypt.gensalt()));
					accountService.save(o);
					resp = new Response(HttpStatus.OK.value(), "success", null);
					return new ResponseEntity<Response>(resp, HttpStatus.OK);
				}
			} else {
				resp = new Response(HttpStatus.GONE.value(), "验证码错误", null);
				return new ResponseEntity<Response>(resp, HttpStatus.GONE);
			}
		}

	}

	@RequestMapping(value = "sendIdentifyCode/{account}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> sendIdentifyCode(@PathVariable String account, HttpServletRequest request) {
		Response resp;
		// Account a = accountService.getById(YESUtil.getUserId());
		Account o = accountService.getById(account);
		if (o == null || YESUtil.isEmpty(o.getMail()) || o.getMail().length() < 4) {
			resp = new Response(HttpStatus.GONE.value(), "该用户不存在或未绑定合法邮箱,请联系管理员!", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			int yzm = new Random().nextInt(900000) + 100000;
			if (BASEUtil.sendEmail(o.getMail(), "修改密码", "您本次忘记密码操作的验证码为[" + yzm + "],10分钟后过期,请及时操作")) {
				cache.put(account, YESUtil.toString(yzm));
				/*
				 * Map<String, String> identify = new HashMap<String, String>();
				 * identify.put("yzm", YESUtil.toString(yzm));
				 * identify.put("account", account);
				 * YESUtil.putSession("identify", identify);
				 */

				String encodemail = "***" + o.getMail().substring(3);

				resp = new Response(0, "success", encodemail);
				return new ResponseEntity<Response>(resp, HttpStatus.OK);
			} else {
				resp = new Response(HttpStatus.GONE.value(), "邮件验证码发送失败,请重试", null);
				return new ResponseEntity<Response>(resp, HttpStatus.GONE);
			}
		}
	}

	@RequestMapping(value = "/{uid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> get(@PathVariable String uid, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;

		Account o = accountService.getByUid(uid);
		if (o == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			resp = new Response(0, "success", o);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "getTypes", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getTypes(HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		List<String> rs = new ArrayList<String>();
		if ("admin".equals(BaseUtil.getUser().getType())) {
			rs.add("admin");
		}
		for (String s : types) {
			rs.add(s);
		}
		return new ResponseEntity<Response>(new Response(0, "success", rs), HttpStatus.OK);
	}

	@RequestMapping(value = "getEnterpriseManger/{eid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getEnterPriseManger(@PathVariable String eid, HttpServletRequest request) {
		eid = eid.replace("_", ".");
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;

		Account o = accountService.getEnterPriseManger(eid);
		if (o == null) {
			resp = new Response(0, "success", null);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "该企业的管理员账号已存在", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{uid}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Response> delete(@PathVariable String uid, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		Account o = accountService.getByUid(uid);
		if (o == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			try {
				accountService.delete(o, request);
				resp = new Response(0, "Delete success.", null);
				return new ResponseEntity<Response>(resp, HttpStatus.OK);
			} catch (Exception e) {
				resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Object unable delete.", null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> insert(@RequestBody Account o, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Account a = BaseUtil.getUser();
		if ("admin".equals(o.getType()) && (!"admin".equals(a.getType()))) {
			return new ResponseEntity<Response>(new Response(501, "非admin无法创建admin", null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Response resp;

		try {
			o.setUid(null);
			resp = new Response(0, "Save success.", accountService.save(o, null, request));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Save failed.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 创建新的普通用户账号
	 * 
	 * @param o
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user/new", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> newUserAccount(@RequestBody NewAccountDto o, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp = accountService.newUserAccount(o);

		if (resp.getError() == 0) {
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * 创建新的管理员账号
	 * 
	 * @param o
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/user/newadmin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> newAdminAccount(@RequestBody NewAccountDto o, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp = accountService.newUserAccount(o);

		if (resp.getError() == 0) {
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/{uid}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Response> update(@PathVariable String uid, @RequestBody Account o,
			HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Account a = BaseUtil.getUser();
		if ("admin".equals(o.getType()) && (!"admin".equals(a.getType()))) {
			return new ResponseEntity<Response>(new Response(501, "非admin无法创建admin", null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Response resp;

		Account obj = accountService.getByUid(uid);
		if (o.getPassword().equals("******")) {
			o.setPassword(obj.getPassword());
		} else {
			o.setPassword(BCrypt.hashpw(o.getPassword(), BCrypt.gensalt()));
		}
		if (obj != null) {
			if (obj.getUid().equals(o.getUid())) {
				resp = new Response(0, "Save success.", accountService.save(o, obj, request));
				return new ResponseEntity<Response>(resp, HttpStatus.OK);
			} else {
				resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Uid does not match.", null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			resp = new Response(HttpStatus.GONE.value(), "Object not found.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		}

	}

	@RequestMapping(value = "/savePayInfo", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Response> savePayInfo(@RequestBody Account o) {
		Account a = BaseUtil.getUser();
		Response resp;
		if (o != null) {
			if (o.getPayID() != null)
				a.setPayID(o.getPayID());
			if (o.getPayName() != null)
				a.setPayName(o.getPayName());
			if (o.getPayPassword() != null)
				a.setPayPassword(BCrypt.hashpw(o.getPayPassword(), BCrypt.gensalt()));
			resp = new Response(0, "Save success.", accountService.save(a));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {
			resp = new Response(HttpStatus.GONE.value(), "Object not found.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		}

	}

	@RequestMapping(value = "/checkPayInfo", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Response> checkPayInfo(@RequestBody Account o) {
		// Account a = YESUtil.getUser();
		Response resp;
		if (YESUtil.isEmpty(o.getAid())) {
			resp = new Response(HttpStatus.GONE.value(), "aid not found.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		}
		Account a = accountService.getById(o.getAid());
		if (YESUtil.isNotEmpty(o)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("checkPayID", a.getPayID().equals(o.getPayID()));
			map.put("checkPayName", a.getPayName().equals(o.getPayName()));
			map.put("checkPayPassword",
					(YESUtil.isNotEmpty(a.getPayPassword()) && YESUtil.isNotEmpty(o.getPayPassword()))
							? BCrypt.checkpw(o.getPayPassword(), a.getPayPassword()) : false);
			map.put("setPayPassword", YESUtil.isNotEmpty(a.getPayPassword()));
			resp = new Response(0, "success", map);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {
			resp = new Response(HttpStatus.GONE.value(), "Object not found.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		}

	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> query(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		resp = new Response(0, "success.", accountService.query(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> queryv(@RequestParam Map<String, Object> parm, @RequestBody AccountPojo a,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		parm.put("aid$match", a.getAid$match());
		parm.put("name$match", a.getName$match());
		parm.put("aid$notin", a.getAid$notin());
		Response resp;
		resp = new Response(0, "success.", accountService.query(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 通过条件查询实体
	 * 
	 * @author <a href="mailto:ardui@163.com">ardui</a>
	 * @param Hz,
	 *            pageable
	 * @return HttpResult
	 * @date Tue Apr 17 22:31:00 CST 2018
	 */
	@RequestMapping(value = "lovquery", method = RequestMethod.POST)
	public HttpResult lovquery(@Valid @RequestBody(required = false) Accountv account, Pageable pageable) {
		if (account == null) {
			account = new Accountv();
		}
		return new HttpResult(accountService.lovquery(account, pageable));
	}

	/**
	 * 获取所有帐号类型为doctor的帐号(阿吉泰项目使用)
	 * 
	 * @author huiqiang.yan 2016-06-01
	 * @param parm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getDoctors", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getDoctors(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		/*
		 * ResponseEntity<Response> result =
		 * RestUtils.checkAuthorization(request, loginService, AUTHORITY); if
		 * (result.getBody().getError() != 0) { return result; }
		 */
		Response resp = new Response(0, "success.", accountService.getDoctors(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

	/**
	 * 用户注册 旭盈专用
	 * 
	 * @author huiqiang.yan 2016-01-15
	 * @param o
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> register(@RequestBody Account o, HttpServletRequest request) {
		Account a = accountService.getById(o.getAid());
		Response resp;
		if (a == null) {
			try {
				// 给注册用户默认菜单
				String mid = "xuying_notice,xuying_helpcenter,dashboard,xuying_sys_help,xuying_sysnotice,xuying_notice_detail";
				AuthorityMenu am = new AuthorityMenu();
				am.setAid(o.getAid());
				am.setMid(mid);
				authorityService.save(am);
				Authority b = new Authority(o.getAid(), "A", o.getAid(), o.getName());
				authorityService.saveEntity(b);
				o.setUid(null);
				o.setEnable(true);
				o.setPassword(BCrypt.hashpw(o.getPassword(), BCrypt.gensalt()));
				resp = new Response(0, "Save success.", accountService.save(o));
				return new ResponseEntity<Response>(resp, HttpStatus.OK);
			} catch (Exception e) {
				resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "注册失败", null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			resp = new Response(HttpStatus.FOUND.value(), "登录账号已经存在", null);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		}
	}

	/**
	 * 通过Aid获取用户信息(阿吉泰项目使用),对比密码是否相等
	 * 
	 * @author huiqiang.yan 2016-06-15
	 * @param parm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getByAid", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getByAid(@RequestParam String aid, @RequestParam String loginPwd,
			HttpServletRequest request, HttpServletResponse response) {
		/*
		 * ResponseEntity<Response> result =
		 * RestUtils.checkAuthorization(request, loginService, AUTHORITY); if
		 * (result.getBody().getError() != 0) { return result; }
		 */Account account = accountService.getById(aid);
		boolean result = BCrypt.checkpw(loginPwd, account.getPassword());
		Response resp = new Response(0, "success.", result);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 通过Aid获取用户信息
	 * 
	 * @author huiqiang.yan 2016-11-30
	 * @param parm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getAccountByAid/{aid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getAccountByAid(@PathVariable String aid, HttpServletRequest request,
			HttpServletResponse response) {
		Account account = accountService.getById(aid);
		Response resp = new Response(0, "success.", account);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 
	 * @param aid
	 * @param loginPwd
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/geteid", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getEid(HttpServletRequest request, HttpServletResponse response) {
		Response resp = new Response(0, "success.", accountService.getLoginEid());
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 检查是否有权限值
	 * 
	 * @param parm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "checkright", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> checkRight(@RequestBody Map<String, String> parm, HttpServletRequest request) {
		// ResponseEntity<Response> result =
		// RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		// if (result.getBody().getError() != 0) {
		// return result;
		// }

		Response resp;
		String value = YESUtil.toString(parm.get("value")); // 要检查的值
		resp = new Response(0, "success.", accountService.checkMenuValue(value));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

}
