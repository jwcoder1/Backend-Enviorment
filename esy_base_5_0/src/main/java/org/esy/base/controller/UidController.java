package org.esy.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.Response;
import org.esy.base.entity.Account;
import org.esy.base.entity.InterfaceAuthorized;
import org.esy.base.entity.InterfaceLog;
import org.esy.base.entity.Uid;
import org.esy.base.entity.jsonObject.UidResult;
import org.esy.base.service.IInterfaceAuthorizedService;
import org.esy.base.service.IInterfaceLogService;
import org.esy.base.service.IUidService;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/uim/api")
public class UidController {

	// private static final int SUCESS=0;

	@Autowired
	private IInterfaceLogService interfaceLogService;

	@Autowired
	private IInterfaceAuthorizedService interfaceAuthorizedService;

	@Autowired
	private IUidService uidService;

	private static final String RECV = "RECV";

	// new 正式职工的创建UID
	@RequestMapping(value = "/regularuid", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> regularuid(@RequestBody Uid o, HttpServletRequest request) {
		// 检查帐号密码
		String iid = "addregularuid";
		Response resp = interfaceAuthorizedService.checkAuthorization(request, iid, null);
		if (resp.getError() > 0)
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		ResponseEntity<Response> respEntity = null;

		// 检查通过,日志开启
		InterfaceAuthorized ia = (InterfaceAuthorized) resp.getBody();
		String recv = BaseUtil.objToJson(o);
		InterfaceLog log = interfaceLogService.StartLog(iid, ia.getAid(), "/uim/api/tempuid", null, recv, RECV, YESUtil.getIpAddr(request));

		// 业务检查
		resp = uidService.checkForRegularuid(o);
		if (resp.getError() > 0) {
			respEntity = new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			log.setSend(BaseUtil.objToJson(respEntity));
			interfaceLogService.saveLog(log);
			return respEntity;
		} else if (BaseUtil.isNotEmpty(resp.getBody())) {
			respEntity = new ResponseEntity<Response>(resp, HttpStatus.OK);
			log.setSend(BaseUtil.objToJson(respEntity));
			interfaceLogService.saveLog(log);
			return respEntity;
		}

		// 保存并返回uid
		try {
			Uid u = uidService.saveForRegularuid(o);
			UidResult ur = new UidResult(u.getUid());
			resp.setBody(ur);
			resp.setMessage("创建uid成功:系统不存在该人员，生成一条新资料");
		} catch (Exception e) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "保存失败，请检查", null);
			respEntity = new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			log.setSend(BaseUtil.objToJson(respEntity));
			interfaceLogService.saveLog(log);
			return respEntity;
		}

		respEntity = new ResponseEntity<Response>(resp, HttpStatus.OK);
		log.setSend(BaseUtil.objToJson(respEntity));
		interfaceLogService.saveLog(log);
		return respEntity;
	}

	// new 临时职工的创建UID
	@RequestMapping(value = "/tempuid", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> tempuid(@RequestBody Uid o, HttpServletRequest request) {
		// 检查帐号密码
		String iid = "addtempuid";
		Response resp = interfaceAuthorizedService.checkAuthorization(request, iid, null);
		if (resp.getError() > 0)
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		ResponseEntity<Response> respEntity = null;

		// 检查通过,日志开启
		InterfaceAuthorized ia = (InterfaceAuthorized) resp.getBody();
		String aid = ia.getAid();
		String recv = BaseUtil.objToJson(o);
		InterfaceLog log = interfaceLogService.StartLog(iid, ia.getAid(), "/uim/api/tempuid", null, recv, RECV, YESUtil.getIpAddr(request));

		// 检查是否可以保存
		o.setTempStaffNo(o.getStaffNo());
		resp = uidService.checkForTempuid(o);
		if (resp.getError() > 0) {
			respEntity = new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			log.setSend(BaseUtil.objToJson(respEntity));
			interfaceLogService.saveLog(log);
			return respEntity;
		} else if (BaseUtil.isNotEmpty(resp.getBody())) {
			respEntity = new ResponseEntity<Response>(resp, HttpStatus.OK);
			log.setSend(BaseUtil.objToJson(respEntity));
			interfaceLogService.saveLog(log);
			return respEntity;
		}

		// 保存并返回uid
		try {
			Uid u = uidService.saveForTempuid(o, aid);
			UidResult ur = new UidResult(u.getUid());
			resp.setBody(ur);
			resp.setMessage("创建uid成功:系统不存在该人员，生成一条新资料");
		} catch (Exception e) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "保存失败，请检查", null);
			respEntity = new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			log.setSend(BaseUtil.objToJson(respEntity));
			interfaceLogService.saveLog(log);
			return respEntity;
		}

		respEntity = new ResponseEntity<Response>(resp, HttpStatus.OK);
		log.setSend(BaseUtil.objToJson(respEntity));
		interfaceLogService.saveLog(log);
		return respEntity;

	}

	// new 临时职工的创建UID
	@RequestMapping(value = "/tempuid2", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Response> tempuid2(@RequestBody Uid o, HttpServletRequest request) {
		Account a = uidService.getByUid(YESUtil.toString(o.getUid()));
		Response resp;
		resp = new Response(0, "Save success.", a);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

}
