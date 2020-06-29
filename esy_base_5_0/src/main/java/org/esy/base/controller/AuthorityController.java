package org.esy.base.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.core.Response;
import org.esy.base.entity.Authority;
import org.esy.base.entity.AuthorityMenu;
import org.esy.base.entity.Uid;
import org.esy.base.service.IAuthorityService;
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
@RequestMapping("/api/base/authority")
public class AuthorityController {

	public static final String AUTHORITY = "base_authority";

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IAuthorityService authorityService;

	@Autowired
	private IUidService uidService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> add(@RequestBody Authority o, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		try {
			resp = new Response(0, "Save success.", authorityService.save(o));
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

		Authority o = authorityService.getByUid(uid);
		if (o == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			try {
				authorityService.delete(o);
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
	public ResponseEntity<Response> update(@PathVariable String uid, @RequestBody Authority o,
			HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;

		Authority obj = authorityService.getByUid(uid);
		if (obj != null) {
			if (obj.getUid().equals(o.getUid())) {
				resp = new Response(0, "Update success.", authorityService.save(o));
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

		Authority o = authorityService.getByUid(uid);
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

		resp = new Response(0, "success.", authorityService.query(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

	@RequestMapping(value = "detail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> addDetail(@RequestBody AuthorityMenu o, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		try {
			resp = new Response(0, "Save success.", authorityService.save(o));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Save failed.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/detail/{aid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> query(@PathVariable String aid, HttpServletRequest request,
			HttpServletResponse response) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		resp = new Response(0, "success.", authorityService.getDetail(aid));
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
		resp = new Response(0, "success.", authorityService.findByPerson(pid, eid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 查询人员对应的权限的菜单 菜单menus
	 */
	@RequestMapping(value = "/uidmenus", method = RequestMethod.GET)
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
		QueryResult qr = authorityService.findAuthorityMenuByPerson(pid, eid);
		resp = new Response(0, "success.", qr.getItems());
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 查询Aid对应的权限的菜单 菜单menus
	 */
	@RequestMapping(value = "/aidmenus", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> aidmenus(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		String aid = YESUtil.toString(parm.get("aid"));
		Response resp;

		QueryResult qr = authorityService.listMenuByAid(aid);
		resp = new Response(0, "success.", qr.getItems());
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

}
