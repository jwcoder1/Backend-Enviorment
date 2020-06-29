package org.esy.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.codec.Base64;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.core.Response;
import org.esy.base.dao.IInterfaceAuthorizedDao;
import org.esy.base.entity.InterfaceAuthorized;
import org.esy.base.service.IInterfaceAuthorizedService;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InterfaceAuthorizedServiceImpl implements IInterfaceAuthorizedService {

	private static final Integer SUCCESS = 0;

	@Autowired
	private IInterfaceAuthorizedDao interfaceAuthorizedDao;

	@Override
	@Transactional
	public InterfaceAuthorized save(InterfaceAuthorized o) {
		return interfaceAuthorizedDao.save(o);
	}

	@Override
	public InterfaceAuthorized getByUid(String uid) {
		return interfaceAuthorizedDao.getByUid(uid);
	}

	@Override
	@Transactional
	public boolean delete(InterfaceAuthorized o) {
		return interfaceAuthorizedDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return interfaceAuthorizedDao.query(parm);
	}

	@Override
	public Response checkAuthorization(HttpServletRequest request, String iid, List<String> authoritys) {
		Response resp = new Response();
		String authorizationInfo = YESUtil.toString(request.getHeader("Authorization"));

		// 基本验证登入处理
		authorizationInfo = authorizationInfo.split(" ")[1];
		authorizationInfo = Base64.decodeToString(authorizationInfo);
		String user = authorizationInfo.substring(0, authorizationInfo.indexOf(":"));
		String password = authorizationInfo.substring(authorizationInfo.indexOf(":") + 1);
		InterfaceAuthorized ia = interfaceAuthorizedDao.getByIdPassword(iid, user, password);

		// 帐号密码不符
		if (BaseUtil.isEmpty(ia)) {
			resp.setError(HttpStatus.INTERNAL_SERVER_ERROR.value());
			resp.setMessage("帐号密码不符");
			return resp;
		}

		// 权限TODO
		resp.setError(SUCCESS);
		resp.setBody(ia);
		return resp;
	}

	@Override
	public InterfaceAuthorized getByIdPassword(String iid, String aid, String password) {
		if (BaseUtil.isEmpty(aid) || BaseUtil.isEmpty(iid) || BaseUtil.isEmpty(password))
			return null;
		return interfaceAuthorizedDao.getByIdPassword(iid, aid, password);
	}

	@Override
	public String getEidFromAid(String aid) {
		return interfaceAuthorizedDao.getEidFromAid(aid);
	}

	@Override
	public QueryResult listApplications(Map<String, Object> parm) {
		return interfaceAuthorizedDao.listApplications(parm);
	}

}
