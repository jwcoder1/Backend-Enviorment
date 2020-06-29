package org.esy.base.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.core.Response;
import org.esy.base.entity.Area;
import org.esy.base.service.IAreaService;
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

@Controller
@RequestMapping("/api/base/area")
public class AreaController {

	public static final String AUTHORITY = null;

	@Autowired
	private IAreaService areaService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> query(@RequestParam Map<String, Object> parm, HttpServletRequest request,
			HttpServletResponse response) {
		Response resp;
		if (!parm.containsKey("pid")) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "参数pid缺失", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		String addnext = "N";
		if (parm.containsKey("addnext")) {
			addnext = YESUtil.toString(parm.get("addnext"));
		}
		String pid = YESUtil.toString(parm.get("pid"));
		resp = new Response(0, "success.", areaService.listByPid(pid, addnext));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@RequestMapping(value = "query", method = RequestMethod.POST)
	public ResponseEntity<Response> area(@RequestBody Area area, Pageable pageable) {
		if (area == null) {
			area = new Area();
		}
		Response resp;

		resp = new Response(0, "success.", areaService.query(area));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 通过页面数据保存实体
	 * 
	 * @author <a href="mailto:chenshj@esunyun.com">chen.shaoji</a> 
	 * @param ICustPreService  o
	 * @param BindingResult request
	 * @return ResponseEntity<Response>
	 * @date 2017-08-21	
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> save(@RequestBody Area o, HttpServletRequest request) {

		Response resp;
		try {
			//o.setUid(null);
			resp = new Response(0, "Save success.", areaService.save(o));
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
	 * @author <a href="mailto:chenshj@esunyun.com">chen.shaoji</a>
	 * @param uid
	 * @return ResponseEntity<Response> 
	 * @date 2017-08-21	
	 */
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Response> delet(@PathVariable String ids, HttpServletRequest request) {

		Response resp;
		try {

			areaService.deletes(ids);
			resp = new Response(0, "Delete success.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} catch (YesException e) {
			// TODO: handle exception
			resp = new Response(e.getErrorcode().value(), e.getMessage(), null);
			return new ResponseEntity<Response>(resp, e.getErrorcode());
		}
	}

	// get
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> get(@PathVariable String id, HttpServletRequest request) {

		Response resp;
		Area o = areaService.getById(id);
		if (o == null) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Object not found", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			resp = new Response(0, "success", o);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		}

	}

}
