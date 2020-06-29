package org.esy.base.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.core.Response;
import org.esy.base.dao.IDictionaryDao;
import org.esy.base.entity.DicDetail;
import org.esy.base.entity.Dictionary;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.service.IDicDetailService;
import org.esy.base.service.ILoginService;
import org.esy.base.util.RestUtils;
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
 * 数据字典子表
 * 
 */
@Controller
@RequestMapping("/api/base/dicdetail")
public class DicDetailController {

	public static final String AUTHORITY = "base_dictionary";

	@Autowired
	private ILoginService loginService;

	@Autowired
	private IDicDetailService dicDetailService;

	@Autowired
	IDictionaryDao dictionaryDao;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> insert(@RequestBody DicDetail o, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		if (!"ajitai_product_attr_config".equals(o.getModel())) {
			MsgResult mr = dicDetailService.checkForSave(o);
			if (!mr.getSuccess()) {
				resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		try {
			o.setUid(null);
			resp = new Response(0, "Save success.", dicDetailService.save(o, request));
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

		DicDetail o = dicDetailService.getByUid(uid);
		if (o == null) {
			resp = new Response(HttpStatus.GONE.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else {
			Dictionary d = dictionaryDao.getByID(o.getDid());
			if (!"3".equals(d.getStatus())) {
				resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "没有可操作权限", null);
				return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			try {
				dicDetailService.delete(o, request);
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
	public ResponseEntity<Response> update(@PathVariable String uid, @RequestBody DicDetail o, HttpServletRequest request) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		MsgResult mr = dicDetailService.checkForSave(o);
		if (!mr.getSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), mr.getMsg(), null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		DicDetail obj = dicDetailService.getByUid(uid);
		if (obj != null) {
			if (obj.getUid().equals(o.getUid())) {
				resp = new Response(0, "Update success.", dicDetailService.save(o, request));
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

		DicDetail o = dicDetailService.getByUid(uid);
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
	public ResponseEntity<Response> query(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
//		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
//		if (result.getBody().getError() != 0) {
//			return result;
//		}

		Response resp;
		resp = new Response(0, "success.", dicDetailService.query(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	// 查询数据字典
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> search(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}

		// id , eid
		Response resp;
		resp = new Response(0, "success.", dicDetailService.listByCondition(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 通过did,id获取字典表数据
	 * 
	 * @author huiqiang.yan 2016-7-20 9:00:00 am
	 * @param did
	 *            主类编号
	 * @param id
	 *            编号
	 * @return
	 */
	@RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> getByCondition(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		resp = new Response(0, "success.", dicDetailService.getByCondition(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 通过did,id删除字典数据
	 * 
	 * @author huiqiang.yan 2016-7-20 9:30:00 am
	 * @param did
	 *            主类编号
	 * @param id
	 *            编号
	 * @return
	 */
	@RequestMapping(value = "/deleteByCondition", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> deleteByCondition(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<Response> result = RestUtils.checkAuthorization(request, loginService, AUTHORITY);
		if (result.getBody().getError() != 0) {
			return result;
		}
		Response resp;
		resp = new Response(0, "success.", dicDetailService.deleteByCondition(parm));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

}
