package org.esy.base.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.Response;
import org.esy.base.entity.dto.AppGroupDto;
import org.esy.base.service.IApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/public/base")
public class PublicBaseController {

	@Autowired
	private IApplicationService applicationService;

	@RequestMapping(value = "/getApplication/{aid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> get(@PathVariable String aid, HttpServletRequest request) {
		Response resp;
		List<AppGroupDto> obj = applicationService.getApplicationsByAccount(aid);
		if(obj==null){
			resp = new Response(500, "未找到任何匹配的数据", null);
		}else{
			resp = new Response(0, "success", obj);
		}
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}
	
}
