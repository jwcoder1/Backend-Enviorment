package org.esy.base.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.Response;
import org.esy.base.entity.Identity;
import org.esy.base.entity.Person;
import org.esy.base.entity.dto.IdentityDto;
import org.esy.base.entity.pojo.IdentityPojo;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.service.IIdentityService;
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

/**
 *
 * Controller for 人员信息
 * 
 */
@Controller
@RequestMapping("/api/base/person")
public class PersonController {

	public static final String AUTHORITY = "base_person";

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IPersonService personService;

	@Autowired
	private IIdentityService identityService;

	@Autowired
	private IUidService uidService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> add(@RequestBody IdentityPojo identityPojo, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		// 检查是否可以保存
		Person o = identityPojo.getPerson();
		MsgResult mr = personService.checkForSave(o);
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (BaseUtil.isEmpty(identityPojo)) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "身份不能为空", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<IdentityDto> dtos = identityPojo.getIdentitys();
		List<Identity> idents = identityService.listByIdentdtos(dtos);
		mr = identityService.checkForSave(dtos);
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			o.setUid(null);
			resp = new Response(0, "Save success.", personService.save(o, null, idents, request));
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

		Person o = personService.getByUid(uid);
		if (o == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			try {
				personService.delete(o);
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
	public ResponseEntity<Response> update(@PathVariable String uid, @RequestBody IdentityPojo identityPojo,
			HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0)
			return result;

		Response resp;
		// 检查是否可以保存
		Person o = identityPojo.getPerson();
		MsgResult mr = personService.checkForSave(o);
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (BaseUtil.isEmpty(identityPojo)) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "身份不能为空", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<IdentityDto> dtos = identityPojo.getIdentitys();
		List<Identity> idents = identityService.listByIdentdtos(dtos);
		mr = identityService.checkForSave(dtos);
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Person obj = personService.getByUid(uid);
		if (obj != null) {
			if (obj.getUid().equals(o.getUid())) {
				resp = new Response(0, "Update success.", personService.save(o, obj, idents, request));
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

		Person o = personService.getByUid(uid);
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
		//		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		//		if (result.getBody().getError() != 0) {
		//			return result;
		//		}
		Response resp;
		resp = new Response(0, "success.", personService.query(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// 人员选择器选择部门，展示人员
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> search(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		resp = new Response(0, "success.", personService.search(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// orgcount
	@RequestMapping(value = "/orgcount", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> orgcount(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		resp = new Response(0, "success.", personService.getOrgCount(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// 获得person所有的可用组织, 找出这些组织(包括子节点)下的所有人员(都是enable=true)
	@RequestMapping(value = "/orgpeople", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> orgpeople(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		if (!parm.containsKey("pid")) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "pid参数缺失", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		String pid = YESUtil.toString(parm.get("pid"));
		Person p = personService.getByPid(pid);

		if (YESUtil.isEmpty(p)) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "没有找到pid相关人员", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		resp = new Response(0, "success.", personService.listByPersonOfOrganization(p));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// @RequestMapping(value = "/fixperson", method = RequestMethod.GET)
	// @ResponseBody
	// public boolean fixperson() {
	// return personService.fixPersonPy();
	// }

	@RequestMapping(value = "/gettempno", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> gettempno(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		String eid = YESUtil.toString(parm.get("eid"));
		if (YESUtil.isEmpty(eid)) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "eid参数缺失", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		resp = new Response(0, "success.", uidService.getTempNo(eid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 查看人员所属组织
	 */
	@RequestMapping(value = "/getorgs", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getorgs(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;

		String pid = YESUtil.toString(parm.get("pid"));
		if (YESUtil.isEmpty(pid)) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "pid参数缺失", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		resp = new Response(0, "success.", identityService.listIdentOrgByPid(pid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

}
