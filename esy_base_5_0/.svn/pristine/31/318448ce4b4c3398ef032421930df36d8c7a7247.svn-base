package org.esy.base.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.Response;
import org.esy.base.entity.Identity;
import org.esy.base.entity.dto.IdentityDto;
import org.esy.base.entity.dto.PersonandIdentityDto;
import org.esy.base.service.IIdentityService;
import org.esy.base.service.ILoginService;
import org.esy.base.util.BASEUtil;
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
 * Controller for 身份信息
 * 
 */
@Controller
@RequestMapping("/api/base/identity")
public class IdentityController {

	public static final String AUTHORITY = "base_person";

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IIdentityService identityService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> add(@RequestBody Identity o, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		try {
			o.setUid(null);
			resp = new Response(0, "Save success.", identityService.save(o));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Save failed.", null);
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

		Identity o = identityService.getByUid(uid);
		if (o == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			try {
				identityService.delete(o);
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
	public ResponseEntity<Response> update(@PathVariable String uid, @RequestBody Identity o,
			HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;

		Identity obj = identityService.getByUid(uid);
		if (obj != null) {
			if (obj.getUid().equals(o.getUid())) {
				resp = new Response(0, "Update success.", identityService.save(o));
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

	@RequestMapping(value = "/{personId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> get(@PathVariable String personId, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		List<Identity> os = identityService.getByPid(personId);
		List<IdentityDto> dtos = identityService.listByIdents(os);
		if (dtos == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			resp = new Response(0, "success", dtos);
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
		resp = new Response(0, "success.", identityService.query(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

	/*
	 * @RequestMapping(value = "/test", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public ResponseEntity<Response> test(@RequestParam
	 * Map<String, Object> parm, HttpServletRequest request, HttpServletResponse
	 * response) { System.out.print("开始查询"); List<Identity> ls =
	 * identityService.listtest(); System.out.print("结束查询");
	 * System.out.print(ls); return null; }
	 */

	/**
	 * 获取登入用户的身份列表
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/getidentitybylogin", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getbylogin(HttpServletRequest request) {
		// ResponseEntity<Response> result =
		// RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		// if (result.getBody().getError() != 0) {
		// return result;
		// }
		String personId = BaseUtil.getUser().getMatrixNo();
		PersonandIdentityDto personandIdentityDto = identityService.getidentitybylogin(personId);

		// }
		Response resp;
		if (personandIdentityDto.getIdentitys() == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			// personandIdentityDto.setIdentitys(null);
			resp = new Response(0, "success", personandIdentityDto);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		}
	}

}
