package org.esy.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.ChangePasswordBody;
import org.esy.base.core.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/error")
public class ErrorController {

	@RequestMapping(value = "/404")
	public @ResponseBody ResponseEntity<Response> page404(@RequestBody ChangePasswordBody changePasswordBody,
			HttpServletRequest request) {

		Response resp = new Response(HttpStatus.GONE.value(), "404 Not Found.", null);
		return new ResponseEntity<Response>(resp, HttpStatus.GONE);

	}

	@RequestMapping(value = "/500")
	public @ResponseBody ResponseEntity<Response> page500(@RequestBody ChangePasswordBody changePasswordBody,
			HttpServletRequest request) {

		Response resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "500 Internal Server Error.", null);
		return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
