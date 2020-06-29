package org.esy.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.Response;
import org.esy.base.service.ILoginService;
import org.esy.base.service.IOrganizationRelationService;
import org.esy.base.util.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Controller for 组织信息
 * 
 */
@Controller
@RequestMapping("/api/base/organizationrelation")
public class OrganizationRelationController {

	public static final String AUTHORITY = "base_organization";

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IOrganizationRelationService organizationRelationService;

	// 删除
	@RequestMapping(value = "/{uid}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Response> delet(@PathVariable String uid, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0)
			return result;
		Response resp;

		try {
			organizationRelationService.deleteByUid(uid);
			resp = new Response(0, "Delete success.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Object unable delete.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 删除
	@RequestMapping(value = "/deallevel", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> deallevel(HttpServletRequest request) {
		return null;
	}

}
