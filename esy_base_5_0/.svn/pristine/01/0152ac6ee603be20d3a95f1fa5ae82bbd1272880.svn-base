package org.esy.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.LogBody;
import org.esy.base.core.Response;
import org.esy.base.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/base/remotelog")
public class LogPublicController {

	@Autowired
	private ILogService logService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> saveRemote(@RequestBody LogBody logBody, HttpServletRequest request) {

		Response resp = logService.saveRemote(logBody.getModuleId(), logBody.getEventId(), logBody.getMessage(),
				logBody.getLocalIp(), logBody.getUser(), logBody.getDate(), request.getRemoteAddr());
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

}
