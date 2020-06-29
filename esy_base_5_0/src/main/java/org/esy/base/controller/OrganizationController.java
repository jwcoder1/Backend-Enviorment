package org.esy.base.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.core.Response;
import org.esy.base.entity.Organization;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.service.ILoginService;
import org.esy.base.service.IOrganizationService;
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
 * Controller for 组织信息
 * 
 */
@Controller
@RequestMapping("/api/base/organization")
public class OrganizationController {

	public static final String AUTHORITY = "base_organization";

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IOrganizationService organizationService;

	// 新增
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> add(@RequestBody Organization o, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		// 检查是否可以保存
		MsgResult mr = organizationService.checkSave(o);
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			o.setUid(null);
			resp = new Response(0, "Save success.", organizationService.save(o));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Save failed.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 修改
	@RequestMapping(value = "/{uid}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Response> update(@PathVariable String uid, @RequestBody Organization o, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;

		// 检查是否可以保存
		MsgResult mr = organizationService.checkSave(o);
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Organization obj = organizationService.getByUid(uid);
		if (obj != null) {
			if (obj.getUid().equals(o.getUid())) {
				resp = new Response(0, "Update success.", organizationService.update(o));
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

	// 删除
	@RequestMapping(value = "/{uid}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Response> delete(@PathVariable String uid, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0)
			return result;
		Response resp;

		// // 检查是否可以删除
		// MsgResult mr = organizationService.checkDeleteByUid(uid);
		// if (!mr.getSuccess()) {
		// resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(),
		// mr.getMsg(), null);
		// return new ResponseEntity<Response>(resp,
		// HttpStatus.INTERNAL_SERVER_ERROR);
		// }

		try {
			organizationService.deleteTreeByUid(uid);
			resp = new Response(0, "Delete success.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Object unable delete.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 删除
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Response> deletRelation(@RequestParam Map<String, Object> parm, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0)
			return result;
		Response resp;

		// 检查是否可以删除
		MsgResult mr = organizationService.checkDeleteByUid(parm);
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			mr = organizationService.deletelByRelation(parm);
			resp = new Response(0, "Delete success.", mr);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Object unable delete.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
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

		Organization o = organizationService.getByUid(uid);
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
//		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
//		if (result.getBody().getError() != 0) {
//			return result;
//		}

		Response resp;
		resp = new Response(0, "success.", organizationService.query(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/changeLocation", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Response> changeLocation(@RequestBody Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;

		MsgResult mr = organizationService.changeLocation(parm, "OrganizationRelation");
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		resp = new Response(0, "success.", null);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// 查询职位
	@RequestMapping(value = "/positions", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> positions(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		String oid = "";
		String path = "";
		String eid = "";
		String rootpid = "";
		Response resp;

		if (parm.containsKey("oid")) {
			oid = YESUtil.toString(parm.get("oid"));
		} else {
			resp = new Response(0, "error.", "没找到oid");
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (parm.containsKey("path")) {
			path = YESUtil.toString(parm.get("path"));
		} else {
			resp = new Response(0, "error.", "没找到path");
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (parm.containsKey("eid")) {
			eid = YESUtil.toString(parm.get("eid"));
		} else {
			resp = new Response(0, "error.", "没找到eid");
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (parm.containsKey("rootpid")) {
			rootpid = YESUtil.toString(parm.get("rootpid"));
		}

		Boolean enable = null;
		if (parm.containsKey(enable)) {
			enable = YESUtil.objToBoolean(parm.get("enable"));
		}

		resp = new Response(0, "success.", organizationService.listPositionsByOid(oid, path, enable, rootpid, eid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// 查询岗位
	@RequestMapping(value = "/posts", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> posts(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		String oid = "";
		String path = "";
		String eid = "";
		String rootpid = "";
		Response resp;

		if (parm.containsKey("oid")) {
			oid = YESUtil.toString(parm.get("oid"));
		} else {
			resp = new Response(0, "error.", "没找到oid");
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (parm.containsKey("path")) {
			path = YESUtil.toString(parm.get("path"));
		} else {
			resp = new Response(0, "error.", "没找到path");
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (parm.containsKey("eid")) {
			eid = YESUtil.toString(parm.get("eid"));
		} else {
			resp = new Response(0, "error.", "没找到eid");
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Boolean enable = null;
		if (parm.containsKey(enable)) {
			enable = YESUtil.objToBoolean(parm.get("enable"));
		}

		if (parm.containsKey("rootpid")) {
			rootpid = YESUtil.toString(parm.get("rootpid"));
		}

		resp = new Response(0, "success.", organizationService.listPostsByOid(oid, path, enable, rootpid, eid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// 查询岗位
	@RequestMapping(value = "/getroots", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getroots(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		String eid = "";
		Response resp;

		if (parm.containsKey("eid")) {
			eid = YESUtil.toString(parm.get("eid"));
		}
		resp = new Response(0, "success.", organizationService.listRootNames(eid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// 查询组织的所有上级
	@RequestMapping(value = "/parentinfo", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getparentinfo(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		String eid = "";
		String oid = "";
		Response resp;

		if (parm.containsKey("eid")) {
			eid = YESUtil.toString(parm.get("eid"));
		}
		oid = YESUtil.toString(parm.get("oid"));

		resp = new Response(0, "success.", organizationService.listParentNodes(eid, oid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// 新增子关系
	@RequestMapping(value = "/relation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> addrelation(@RequestBody Organization o, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		// 检查是否可以保存
		MsgResult mr = organizationService.checkforsaverelation(o);
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			o.setUid(null);
			resp = new Response(0, "Save success.", organizationService.saveForRelation(o));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Save failed.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
