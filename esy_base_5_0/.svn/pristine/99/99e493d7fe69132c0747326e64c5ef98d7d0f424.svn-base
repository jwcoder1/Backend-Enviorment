package org.esy.base.controller;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.core.Response;
import org.esy.base.entity.Post;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.service.ILoginService;
import org.esy.base.service.IPostService;
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
 * Controller for 岗位信息
 * 
 */
@Controller
@RequestMapping("/api/base/post")
public class PostController {

	public static final String AUTHORITY = "base_organization";

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IPostService postService;

	// 新增
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> add(@RequestBody Post o, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;
		// 检查同级是否重复
		MsgResult mr = postService.checkForSave(o);
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			o.setUid(null);
			resp = new Response(0, "Save success.", postService.save(o));
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

		Post o = postService.getByUid(uid);
		if (o == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		}

		// 检查是否可以删除
		MsgResult mr = postService.checkDeleteByUid(uid);
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			postService.delete(o);
			resp = new Response(0, "Delete success.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (Exception e) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Object unable delete.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// 修改
	@RequestMapping(value = "/{uid}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Response> update(@PathVariable String uid, @RequestBody Post o, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;

		// 检查同级是否重复
		MsgResult mr = postService.checkForSave(o);
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Post obj = postService.getByUid(uid);
		if (obj != null) {
			if (obj.getUid().equals(o.getUid())) {
				resp = new Response(0, "Update success.", postService.save(o));
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

	// 查询
	@RequestMapping(value = "/{uid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> get(@PathVariable String uid, HttpServletRequest request) {

		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;

		Post o = postService.getByUid(uid);
		if (o == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			resp = new Response(0, "success", o);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		}
	}

	// 查询
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> query(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		Response resp;

		resp = new Response(0, "success.", postService.query(parm));
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
		MsgResult mr = postService.changeLocation(parm, "Post");
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		resp = new Response(0, "success.", null);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 本身和父节点所拥有的职位
	 * 
	 * @param parm
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/parentposts", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> parentpositions(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		String path = YESUtil.toString(parm.get("path"));
		String rootpid = YESUtil.toString(parm.get("rootpid"));
		String[] oids = path.split("\\.");

		resp = new Response(0, "success.", postService.listByOids(Arrays.asList(oids), rootpid, true));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);

	}

}
