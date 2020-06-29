package org.esy.base.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.core.Response;
import org.esy.base.entity.GroupMember;
import org.esy.base.service.IGroupMemberService;
import org.esy.base.service.ILoginService;
import org.esy.base.util.RestUtils;
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
@RequestMapping("/api/base/groupmember")
public class GroupMemberController {

	public static final List<String> AUTHORITY = Arrays.asList("base_group", "base_group2");
	// public static final String AUTHORITY = "base_group";

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IGroupMemberService groupMemberService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> add(@RequestBody GroupMember o, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		try {
			resp = new Response(0, "Save success.", groupMemberService.save(o));
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

		GroupMember o = groupMemberService.getByUid(uid);
		if (o == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			try {
				groupMemberService.delete(o);
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
	public ResponseEntity<Response> update(@PathVariable String uid, @RequestBody GroupMember o, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;

		GroupMember obj = groupMemberService.getByUid(uid);
		if (obj != null) {
			if (obj.getUid().equals(o.getUid())) {
				resp = new Response(0, "Update success.", groupMemberService.save(o));
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

		GroupMember o = groupMemberService.getByUid(uid);
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
	public ResponseEntity<Response> query(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		resp = new Response(0, "success.", groupMemberService.query(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/ByGname", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> searchByPid(@RequestParam String gname, @RequestParam String eid, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, (String) null);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;

		resp = new Response(0, "success", groupMemberService.searchByGname(gname, eid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

	/**
	 * 通过当前登录用户获取所在群组信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMembersByUserId", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getMembersByUserId(HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, (String) null);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp = new Response(0, "success", groupMemberService.getMembersByUserId());
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 查询群组对应的人员
	 */
	@RequestMapping(value = "/people", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> groupmemberPeople(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		resp = new Response(0, "success.", groupMemberService.listGroupPeople(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

}
