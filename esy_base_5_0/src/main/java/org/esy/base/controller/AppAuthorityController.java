package org.esy.base.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.Response;
import org.esy.base.entity.AppAuthority;
import org.esy.base.entity.Uid;
import org.esy.base.service.IAppAuthorityService;
import org.esy.base.service.ILoginService;
import org.esy.base.service.IUidService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.RestUtils;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Controller for 群组成员
 * 
 */
@Controller
@RequestMapping("/api/base/appAuthority")
public class AppAuthorityController {

	public static final String AUTHORITY = "base_appauthority";

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IAppAuthorityService appAuthorityService;

	@Autowired
	private IUidService uidService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> add(@RequestBody AppAuthority o, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		try {
			resp = new Response(0, "Save success.", appAuthorityService.save(o));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Save failed.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{uid}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Response> delet(@PathVariable String uid, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;

		AppAuthority o = appAuthorityService.getByUid(uid);
		if (o == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			try {
				appAuthorityService.delete(o);
				resp = new Response(0, "Delete success.", null);
				return new ResponseEntity<Response>(resp, HttpStatus.OK);
			} catch (Exception e) {
				resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Object unable delete.", null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	@RequestMapping(value = "/{uid}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Response> update(@PathVariable String uid, @RequestBody AppAuthority o,
			HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;

		AppAuthority obj = appAuthorityService.getByUid(uid);
		if (obj != null) {
			if (obj.getUid().equals(o.getUid())) {
				resp = new Response(0, "Update success.", appAuthorityService.save(o));
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

	@RequestMapping(value = "/{uid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> get(@PathVariable String uid, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;

		AppAuthority o = appAuthorityService.getByUid(uid);
		if (o == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			resp = new Response(0, "success", o);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
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

		resp = new Response(0, "success.", appAuthorityService.query(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

	/**
	 * 查询群组对应的人员
	 */
	@RequestMapping(value = "/people", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> groupmemberPeople(@RequestParam Map<String, Object> parm,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		resp = new Response(0, "success.", appAuthorityService.listGroupPeople(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 查询人员对应的权限
	 */
	@RequestMapping(value = "/uidappauth", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> uidappauth(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		String uid = YESUtil.toString(parm.get("uid"));
		Uid u = uidService.getById(uid);
		String pid = u.getPid();
		String eid = "";
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);
		Condition veid = conditions.get("eid");
		if (YESUtil.isNotEmpty(veid)) {
			if (YESUtil.isNotEmpty(veid.getConditions().get("eq"))) {
				eid = veid.getConditions().get("eq");
			}
		}
		if (YESUtil.isEmpty(eid)) {
			if (parm.containsKey("eid")) {
				eid = YESUtil.toString(parm.get("eid"));
			}
		}
		if (YESUtil.isEmpty(eid)) {
			eid = BaseUtil.getUser().getEid();
		}

		Response resp;
		resp = new Response(0, "success.", appAuthorityService.findByPerson(pid, eid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 查询人员对应的权限的应用
	 */

	@RequestMapping(value = "/uidapps", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> uidapps(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		String uid = YESUtil.toString(parm.get("uid"));
		Uid u = uidService.getById(uid);
		String pid = u.getPid();
		String eid = "";
		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);
		Condition veid = conditions.get("eid");
		if (YESUtil.isNotEmpty(veid)) {
			if (YESUtil.isNotEmpty(veid.getConditions().get("eq"))) {
				eid = veid.getConditions().get("eq");
			}
		}
		if (YESUtil.isEmpty(eid)) {
			if (parm.containsKey("eid")) {
				eid = YESUtil.toString(parm.get("eid"));
			}
		}
		if (YESUtil.isEmpty(eid)) {
			eid = BaseUtil.getUser().getEid();
		}

		Response resp;
		resp = new Response(0, "success.", appAuthorityService.findApplicationByPerson(pid, eid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * appauthority 条件找出应用
	 * 
	 * @param parm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/auapps", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> auapps(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		resp = new Response(0, "success.", appAuthorityService.findByAppAuthority(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

}
