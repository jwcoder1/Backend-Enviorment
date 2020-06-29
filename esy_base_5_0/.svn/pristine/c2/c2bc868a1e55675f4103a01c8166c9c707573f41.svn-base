package org.esy.base.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.core.Response;
import org.esy.base.entity.Uid;
import org.esy.base.service.ILoginService;
import org.esy.base.service.IPersonService;
import org.esy.base.service.IUidService;
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

@Controller
@RequestMapping("/api/base/uid")
public class UidManagerController {

	public static final String AUTHORITY = "base_uid";

	@Autowired
	private IUidService uidService;

	@Autowired
	private ILoginService loginService;

	@SuppressWarnings("unused")
	@Autowired
	private IPersonService personService;

	// 查询功能
	@RequestMapping(value = "/{uid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> get(@PathVariable String uid, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		resp = new Response(0, "success", uidService.getById(uid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// 新增
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> add(@RequestBody Uid uid, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;

		// 检查是否重复
		resp = uidService.checkForAdd(uid);
		if (resp.getError() > 0) {
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// 保存uid
		try {
			resp = new Response(0, "Save success", uidService.save(uid));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Save failed.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/{uid}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Response> update(@PathVariable String uid, @RequestBody Uid u, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0)
			return result;

		// 检查是否可以修改
		Response resp = uidService.checkForPut(u);
		if (resp.getError() > 0) {
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// 保存uid
		try {
			resp = new Response(0, "Save success", uidService.save(u));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Save failed.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 删除
	@RequestMapping(value = "/{uid}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Response> delet(@PathVariable String uid, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		Uid u = uidService.getById(uid);
		if (u == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			try {
				uidService.delete(u);
				resp = new Response(0, "Delete success.", null);
				return new ResponseEntity<Response>(resp, HttpStatus.OK);
			} catch (Exception e) {
				resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Object unable delete.", null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	// 返回临时职工号
	@RequestMapping(value = "gettempno", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> gettempno(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		String eid = YESUtil.toString(parm.get("eid"));
		String tempno = uidService.getTempNo(eid);
		if (YESUtil.isEmpty(tempno)) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "企业定位不准", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		resp = new Response(0, "success.", tempno);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// 模糊查询
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> query(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		resp = new Response(0, "success.", uidService.query(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

}
