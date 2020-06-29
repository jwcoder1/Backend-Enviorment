package org.esy.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.Response;
import org.esy.base.entity.Account;
import org.esy.base.entity.Enterprise;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.http.HttpResult;
import org.esy.base.service.IEnterpriseService;
import org.esy.base.service.ILoginService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.RestUtils;
import org.esy.base.util.YESUtil;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * Controller for 企业信息
 * 
 */
@Controller
@RequestMapping("/api/base/enterprise")
public class EnterpriseController {

	public static final String AUTHORITY = null;// "base_enterprise";

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IEnterpriseService enterpriseService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> add(@RequestBody Enterprise o, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Account user = BaseUtil.getUser();
		if (!((user.getType().equals("enterprise") && YESUtil.isNotEmpty(user.getEid()))
				|| user.getType().equals("admin"))) {
			return new ResponseEntity<Response>(new Response(405, "您不是企业管理员", null), HttpStatus.NOT_IMPLEMENTED);
		}

		Response resp;

		try {
			o.setUid(null);
			resp = new Response(0, "Save success.", enterpriseService.save(o, null, request));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Save failed.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 通过页面数据保存实体
	 * 
	 * @author <a href="mailto:ardui@163.com">ardui</a>
	 * @param Baswar
	 *            o
	 * @param BindingResult
	 *            request
	 * @return ResponseEntity<Response>
	 * @date Sun Nov 26 00:32:53 CST 2017
	 */
	@RequestMapping(value = "savedata", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> save(@RequestBody Enterprise o, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		try {
			// o.setUid(null);
			resp = new Response(0, "Save success.", enterpriseService.save(o));
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (YesException e) {
			// TODO: handle exception
			resp = new Response(e.getErrorcode().value(), e.getMessage(), null);
			return new ResponseEntity<Response>(resp, e.getErrorcode());
		}

	}

	@RequestMapping(value = "/{uid}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Response> delet(@PathVariable String uid, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Account user = BaseUtil.getUser();
		if (!((user.getType().equals("enterprise") && YESUtil.isNotEmpty(user.getEid()))
				|| user.getType().equals("admin"))) {
			return new ResponseEntity<Response>(new Response(405, "您不是企业管理员", null), HttpStatus.NOT_IMPLEMENTED);
		}
		Response resp;
		Enterprise o = enterpriseService.getByUid(uid);
		MsgResult mr = enterpriseService.checkForDelele(o);
		if (!mr.getSuccess()) {
			return new ResponseEntity<Response>(
					new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			enterpriseService.delete(o);
			resp = new Response(0, "Delete success.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Object unable delete.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/{uid}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Response> update(@PathVariable String uid, @RequestBody Enterprise o,
			HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Account user = BaseUtil.getUser();
		if (!((user.getType().equals("enterprise") && YESUtil.isNotEmpty(user.getEid()))
				|| user.getType().equals("admin"))) {
			return new ResponseEntity<Response>(new Response(405, "您不是企业管理员", null), HttpStatus.NOT_IMPLEMENTED);
		}

		Response resp;

		Enterprise obj = enterpriseService.getByUid(uid);
		if (obj != null) {
			if (obj.getUid().equals(o.getUid())) {
				resp = new Response(0, "Update success.", enterpriseService.save(o, obj, request));
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

		Account user = BaseUtil.getUser();
		if (!((user.getType().equals("enterprise") && YESUtil.isNotEmpty(user.getEid()))
				|| user.getType().equals("admin"))) {
			return new ResponseEntity<Response>(new Response(405, "您不是企业管理员", null), HttpStatus.NOT_IMPLEMENTED);
		}

		Response resp;

		Enterprise o = enterpriseService.getByUid(uid);
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
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		resp = new Response(0, "success.", enterpriseService.query(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 通过条件查询实体
	 * 
	 * @author <a href="mailto:ardui@163.com">ardui</a>
	 * @param Baswar,
	 *            pageable
	 * @return HttpResult
	 * @date Sun Nov 26 00:32:53 CST 2017
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	public HttpResult query(@Valid @RequestBody(required = false) Enterprise enterprise, Pageable pageable) {
		if (enterprise == null) {
			enterprise = new Enterprise();
		}
		return new HttpResult(enterpriseService.query(enterprise, pageable));
	}

	// 查询当前登录用户的企业列表

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getenterprises", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getenterprises(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Map map = new HashMap();
		Account user = BaseUtil.getUser();
		String eid = BaseUtil.toString(user.getEid());
		List<Enterprise> es = enterpriseService.listByEid(eid);
		for (Enterprise ep : es) {
			if (eid.equals(ep.getEid())) {
				ep.setPid("");
				break;
			}
		}
		map.put("Enterprises", es);
		map.put("eid", eid);
		Response resp;
		if (BaseUtil.isEmpty(es)) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "没查到相关企业.", map);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		resp = new Response(0, "success.", map);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// 传入企业id,找到这个企业的子企业列表
	@RequestMapping(value = "/getchildren", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> search(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		resp = new Response(0, "success.", enterpriseService.listByPidAndClassify(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "/getbyperson", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getbyperson(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		String pid = YESUtil.toString(parm.get("pid"));

		resp = new Response(0, "success.", enterpriseService.listByPid(pid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

}
