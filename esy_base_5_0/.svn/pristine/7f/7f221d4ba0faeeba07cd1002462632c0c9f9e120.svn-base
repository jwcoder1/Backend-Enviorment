package org.esy.base.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.ChangePasswordBody;
import org.esy.base.core.LoginBody;
import org.esy.base.core.Response;
import org.esy.base.entity.dto.MenuDto;
import org.esy.base.security.service.ShiroRealm.ShiroUser;
import org.esy.base.service.IAccountService;
import org.esy.base.service.IDicDetailService;
import org.esy.base.service.ILoginService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.RestUtils;
import org.esy.base.util.YESPdfUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.exceptions.ClientException;

/**
 * 登录Controller
 * 
 * @author huiqiang.yan 2014-10-28
 *
 */
@Controller
@RequestMapping("/api")
public class MainController {

	public static final String AUTHORITY = null;

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IDicDetailService dicDetailService;

	public static final String ACCESSKEYID = "LTAII90oEwFGXsen";

	public static final String ACCESSKEYSECRET = "9w3QW9wFngQi6xGUxKbig8PUcJgw7U";

	@CrossOrigin
	@RequestMapping(value = "/setpassword", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Response> setPassword(@RequestBody ChangePasswordBody changePasswordBody,
			HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp = loginService.setPassword(changePasswordBody, request);

		if (resp.getError() == 0) {
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/setpassword/{id}", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Response> adminSetPassword(@RequestBody ChangePasswordBody changePasswordBody,
			@PathVariable String id, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp = loginService.setPassword(id, changePasswordBody, request);

		if (resp.getError() == 0) {
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Response> login(@RequestBody LoginBody loginbody, HttpServletRequest request) {
		Response resp = loginService.login(loginbody.getUsername(), loginbody.getPassword(), loginbody.isRememberMe(),
				request);
		// request.getSession().getId()
		if (resp.getError() == 0) {
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/logout")
	public @ResponseBody ResponseEntity<Response> logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		Response resp = new Response(0, "注销成功", null);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/status")
	public @ResponseBody ResponseEntity<Response> status() {

		Map<String, Object> status = new HashMap<String, Object>();
		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.getPrincipal() == null) {
			status.put("userid", null);
		} else {
			ShiroUser user = (ShiroUser) currentUser.getPrincipal();
			status.put("userid", user.getUserLoginID());
			status.put("username", user.toString());
			status.put("isAuthenticated", currentUser.isAuthenticated());
			status.put("isRemembered", currentUser.isRemembered());
		}

		Response resp = new Response(0, "状态查询成功", status);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/allmenus")
	public @ResponseBody ResponseEntity<Response> allmenus(String parent, String type, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp = null;
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		resp = new Response(0, "success",
				accountService.getAllByAccount(user.getUserLoginID(), YESUtil.toString(parent)));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@CrossOrigin
	@RequestMapping(value = "/menus")
	public @ResponseBody ResponseEntity<Response> menus(String parent, String type, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		List<MenuDto> mts = (List<MenuDto>) BaseUtil.getMumList(parent);
		if (YESUtil.isNotEmpty(mts) && mts.size() > 0) {
			for (MenuDto mt : mts) {
				if (YESUtil.isEmpty(YESUtil.toString(mt.getParent()).trim()))
					mt.setParent("");
			}
		}

		Response resp = new Response(0, "success", mts);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * wwc 2016-11-4
	 * 
	 * @param parm
	 * @param request
	 * @param response
	 * @return
	 */
	@CrossOrigin
	@RequestMapping(value = "/getindusry", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> queryforNoRight(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		Response resp;
		resp = new Response(0, "success.", dicDetailService.query(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/printpdf", method = RequestMethod.GET)
	public ResponseEntity<Response> exportPdf(HttpServletRequest request, @RequestParam Map<String, Object> parm,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		YESPdfUtil.exportReportPdf(request, response, parm);
		return null;
	}

	/**
	 * 给登录者发送短信验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/seedcode", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> seedCode(@RequestBody LoginBody loginbody, HttpServletRequest request,
			HttpServletResponse response) {
		Response resp;

		String mobile = loginbody.getMobile();
		if (YESUtil.isEmpty(mobile)) {
			resp = new Response(403, "请输入手机号码", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String locatCode = YESUtil.randomNum(6);
		Map<String, Object> map = new HashMap<>();
		map.put("locatCode", locatCode);
		map.put("time", new Date());
		YESUtil.putSession("phoneCode", map);
		try {
			String retmsg = BASEUtil.sendMsg(mobile, "SMS_130800115", "{\"code\":\"" + locatCode + "\"}");
			if (BaseUtil.isEmpty(retmsg)) {
				resp = new Response(0, "成功", "");
				return new ResponseEntity<Response>(resp, HttpStatus.OK);
			} else {
				resp = new Response(507, retmsg, null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ClientException e) {
			e.printStackTrace();
			resp = new Response(507, "短信发送失败,请稍后再试", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/seedcodeb", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> seedCodeb(@RequestParam String mobile, HttpServletRequest request,
			HttpServletResponse response) {
		Response resp;

		String locatCode = YESUtil.randomNum(6);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", mobile);
		map.put("date", new Date());
		map.put("locatCode", locatCode);
		YESUtil.putSession("mobileCode", map);
		try {
			String retmsg = BASEUtil.sendMsg(mobile, "SMS_135041101", "{\"code\":\"" + locatCode + "\"}");
			if (BaseUtil.isEmpty(retmsg)) {
				resp = new Response(0, "成功", "");
				return new ResponseEntity<Response>(resp, HttpStatus.OK);
			} else {
				resp = new Response(507, retmsg, null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ClientException e) {
			e.printStackTrace();
			resp = new Response(507, "短信发送失败,请稍后再试", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
