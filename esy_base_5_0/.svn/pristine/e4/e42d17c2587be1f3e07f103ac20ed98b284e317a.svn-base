package org.esy.base.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.Response;
import org.esy.base.entity.Identity;
import org.esy.base.entity.InterfaceAuthorized;
import org.esy.base.entity.InterfaceLog;
import org.esy.base.entity.Person;
import org.esy.base.entity.dto.PersonIdentityDto;
import org.esy.base.service.IApplicationService;
import org.esy.base.service.IIdentityService;
import org.esy.base.service.IInterfaceAuthorizedService;
import org.esy.base.service.IInterfaceLogService;
import org.esy.base.service.IPersonService;
import org.esy.base.util.JsonUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/uim/api/person")
public class UimPersonContronller {

	@Autowired
	private IInterfaceLogService interfaceLogService;

	@Autowired
	private IInterfaceAuthorizedService interfaceAuthorizedService;

	@Autowired
	private IApplicationService applicationService;

	@Autowired
	private IPersonService personService;

	@Autowired
	private IIdentityService identityService;

	private static final String RECV = "RECV";

	@RequestMapping(value = "/findperson/{pid}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> findperson(@PathVariable String pid, HttpServletRequest request) {
		// 检查传入参数
		if (YESUtil.isEmpty(pid)) {
			Response resp = new Response();
			resp.setError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			resp.setMessage("没有查询值pid");
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// 检查帐号密码
		String iid = "findperson";
		Response resp = interfaceAuthorizedService.checkAuthorization(request, iid, null);
		if (resp.getError() > 0)
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		ResponseEntity<Response> respEntity = null;

		// 检查eid
		InterfaceAuthorized ia = (InterfaceAuthorized) resp.getBody();
		String eid = applicationService.getEidByAid(ia.getAid());
		if (YESUtil.isEmpty(eid)) {
			resp.setError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			resp.setMessage("账户关联的应用没有eid");
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// 检查通过后开启日志
		String recv = "pid:" + pid;
		InterfaceLog log = interfaceLogService.StartLog(iid, ia.getAid(), "/uim/api/person/findperson", null, recv, RECV, YESUtil.getIpAddr(request));

		Person person = personService.getByPidAndEid(pid, eid);
		// 找person
		if (YESUtil.isEmpty(person)) {
			resp.setError(HttpStatus.OK.value());
			resp.setMessage("没有找到相关人员!");
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		}
		// 找身份
		List<Identity> identitys = identityService.getByPid(person.getPid());
		PersonIdentityDto pd = new PersonIdentityDto();
		pd.setPerson(person);
		pd.setIdentitys(identitys);

		// 返回
		resp.setBody(pd);
		respEntity = new ResponseEntity<Response>(resp, HttpStatus.OK);
		log.setSend(BaseUtil.objToJson(respEntity));
		interfaceLogService.saveLog(log);
		return respEntity;
	}

	@RequestMapping(value = "/findpeople", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Response> findpeople(@RequestParam Map<String, Object> parm, HttpServletRequest request, HttpServletResponse response) {
		// 检查帐号密码
		String iid = "findpeople";
		Response resp = interfaceAuthorizedService.checkAuthorization(request, iid, null);
		if (resp.getError() > 0)
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		ResponseEntity<Response> respEntity = null;

		// 检查eid
		InterfaceAuthorized ia = (InterfaceAuthorized) resp.getBody();
		String eid = applicationService.getEidByAid(ia.getAid());
		if (YESUtil.isEmpty(eid)) {
			resp.setError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			resp.setMessage("账户关联的应用没有eid");
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// 检查通过后开启日志
		String recv = JsonUtil.mapToJson(parm);
		InterfaceLog log = interfaceLogService.StartLog(iid, ia.getAid(), "/uim/api/person/findpeople", null, recv, RECV, YESUtil.getIpAddr(request));

		// ps.是否添加eid，添加后只能查到本公司的
		// if (!parm.containsKey("eid")) {
		// parm.put("eid", eid);
		// }

		return null;

	}

	// public static void main(String[] args) {
	// String buff = "";
	// String log = "";
	// for (int i = 0; i < 10; i++) {
	// // 2014-06-19 19:11:15 廖建兰
	// // 办结归档(1:归档办结) 办理意见：无
	// log = log + buff + String.format("%s %s\r\n%s 办理意见:%s", "2014-06-19
	// 19:11:15", "廖建兰", "办结归档(1:归档办结)", "无");
	// buff = "\r\n";
	// }
	// System.out.println(log);
	// }

}
