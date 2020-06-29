package org.esy.base.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.esy.base.core.Response;
import org.esy.base.entity.Sysvar;
import org.esy.base.http.HttpResult;
import org.esy.base.service.ILoginService;
import org.esy.base.service.ISysvarService;
import org.esy.base.util.RestUtils;
import org.esy.base.util.YesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 实体控制器
 *  @author <a href="mailto:ardui@163.com">ardui</a>
 *  @version v2.0
 * @date Thu Nov 30 22:38:54 CST 2017			
 */
@Controller
@RequestMapping("/api/base/sysvar")
public class SysvarController {

	public static final String AUTHORITY = "base_sysvar";

	@Autowired
	private ILoginService loginService;

	@Autowired
	private ISysvarService sysvarService;

	/**
	 * 通过页面数据保存实体
	 * 
	 * @author <a href="mailto:ardui@163.com">ardui</a> 
	 * @param Sysvar  o
	 * @param BindingResult request
	 * @return ResponseEntity<Response>
	 * @date Thu Nov 30 22:38:54 CST 2017	
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> save(@RequestBody Sysvar o, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		try {
			//o.setUid(null);
			resp = new Response(0, "Save success.", sysvarService.save(o));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (YesException e) {
			// TODO: handle exception
			resp = new Response(e.getErrorcode().value(), e.getMessage(), null);
			return new ResponseEntity<Response>(resp, e.getErrorcode());
		}

	}

	/**
	 * 获取出货单价
	 * 
	 * @author <a href="mailto:duiqing.huang@yes-soft.cn">huangdq</a>
	 * @param uid
	 * @return ResponseEntity<Response> 
	 * @date 2016-04-21	
	 */
	@RequestMapping(value = "getsysvar", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> getsysvar(@RequestBody Sysvar sysvar, HttpServletRequest request) {
		Response resp;
		String o = sysvarService.getsysvar(sysvar.getVar_type(), sysvar.getVar_name());
		if (o == null) {
			resp = new Response(HttpStatus.NOT_FOUND.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.NOT_FOUND);
		} else {
			resp = new Response(0, "success", o);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		}
	}

	/**
	 * 通过页面数据保存实体
	 * 
	 * @author <a href="mailto:ardui@163.com">ardui</a> 
	 * @param Sysvar  o
	 * @param BindingResult request
	 * @return ResponseEntity<Response>
	 * @date Thu Nov 30 22:38:54 CST 2017	
	 */
	@RequestMapping(value = "savelist", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> savelist(@RequestBody List<Sysvar> o, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		try {
			//o.setUid(null);
			resp = new Response(0, "Save success.", sysvarService.savelist(o));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (YesException e) {
			// TODO: handle exception
			resp = new Response(e.getErrorcode().value(), e.getMessage(), null);
			return new ResponseEntity<Response>(resp, e.getErrorcode());
		}

	}

	/**
	 * 通过UID删除实体
	 * 
	 * @author <a href="mailto:ardui@163.com">ardui</a>
	 * @param uid
	 * @return ResponseEntity<Response> 
	 * @date Thu Nov 30 22:38:54 CST 2017	
	 */
	@RequestMapping(value = "/{uids}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Response> delet(@PathVariable String uids, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		try {

			sysvarService.deletes(uids);
			resp = new Response(0, "Delete success.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (YesException e) {
			// TODO: handle exception
			resp = new Response(e.getErrorcode().value(), e.getMessage(), null);
			return new ResponseEntity<Response>(resp, e.getErrorcode());
		}
	}

	/**
	 * 通过UID更新实体
	 * 
	 * @author <a href="mailto:ardui@163.com">ardui</a>
	 * @param uid
	 * @return ResponseEntity<Response> 
	 * @date Thu Nov 30 22:38:54 CST 2017	
	 */
	@RequestMapping(value = "/{uid}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Response> update(@PathVariable String uid, @RequestBody Sysvar o,
			HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		try {
			resp = new Response(0, "Update success.", sysvarService.save(o));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (YesException e) {
			// TODO: handle exception

			resp = new Response(e.getErrorcode().value(), e.getMessage(), null);
			return new ResponseEntity<Response>(resp, e.getErrorcode());
		}

	}

	/**
	 * 通过UID删除实体
	 * 
	 * @author <a href="mailto:ardui@163.com">ardui</a>
	 * @param uid
	 * @return ResponseEntity<Response> 
	 * @date Thu Nov 30 22:38:54 CST 2017	
	 */
	@RequestMapping(value = "/{uid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> get(@PathVariable String uid, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;

		Sysvar o = sysvarService.getByUid(uid);
		if (o == null) {
			resp = new Response(HttpStatus.NOT_FOUND.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.NOT_FOUND);
		} else {
			resp = new Response(0, "success", o);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		}
	}

	/**
	* 通过条件查询实体
	* 
	* @author <a href="mailto:ardui@163.com">ardui</a>
	* @param Sysvar, pageable
	* @return HttpResult
	* @date Thu Nov 30 22:38:54 CST 2017	
	*/
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public HttpResult query(@Valid @RequestBody(required = false) Sysvar sysvar, Pageable pageable) {
		if (sysvar == null) {
			sysvar = new Sysvar();
		}
		return new HttpResult(sysvarService.query(sysvar, pageable));
	}

	/**
	 * 通过UID删除实体
	 * 
	 * @author <a href="mailto:ardui@163.com">ardui</a>
	 * @param uid
	 * @return ResponseEntity<Response> 
	 * @date Thu Nov 30 22:38:54 CST 2017	
	 */
	@RequestMapping(value = "getgroptype", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getgroptype(HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;

		Object o = sysvarService.getgroptype();
		if (o == null) {
			resp = new Response(HttpStatus.NOT_FOUND.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.NOT_FOUND);
		} else {
			resp = new Response(0, "success", o);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		}
	}

}
