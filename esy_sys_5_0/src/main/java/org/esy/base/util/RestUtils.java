package org.esy.base.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.Response;
import org.esy.base.entity.dto.MenuDto;
import org.esy.base.security.core.SSOAuth;
import org.esy.base.service.ILoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestUtils {

	public static ResponseEntity<Response> checkAuthorizationByWeiXin(HttpServletRequest request,
			ILoginService loginService) {
		Response resp;
		if (YESUtil.getSession("wx_userId") != null) {
			resp = new Response(0, "isAuthenticated", null);
			return new ResponseEntity<Response>(resp, HttpStatus.OK); // 已经登入
		} else {
			resp = new Response(401, "401 Unauthorized Error : user no login.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.UNAUTHORIZED); // 没有登入且没有进行基本验证
		}
	}

	/**
	 * 
	 * @param request
	 * @param loginService
	 * @param authority
	 *            该权限需要的菜单(或菜单功能)的mid
	 * @return
	 */
	public static ResponseEntity<Response> checkAuthorization(HttpServletRequest request, ILoginService loginService,
			List<String> authoritys) {
		Response resp;
		String authorizationInfo = YESUtil.toString(request.getHeader("Authorization"));
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.getPrincipal() != null) {
			if (currentUser.isAuthenticated()) {
				// 已经登录
				resp = new Response(0, "isAuthenticated", null);
				if (YESUtil.isEmpty(authoritys)) {
					return new ResponseEntity<Response>(resp, HttpStatus.OK); // 不需要特定菜单即可
				} else {
					for (MenuDto a : BaseUtil.getUser().getMenus()) {
						for (String authority : authoritys) {
							if (a.getUid().equals(authority)) {
								return new ResponseEntity<Response>(resp, HttpStatus.OK); // 有该菜单的权限
							}
						}
					}
					resp = new Response(HttpStatus.FORBIDDEN.value(), "您没有权限!", null);
					return new ResponseEntity<Response>(resp, HttpStatus.FORBIDDEN); // 已经登录但没有该操作的权限
				}
			} else if ("".equals(authorizationInfo)
					|| !"BASIC".equals(authorizationInfo.substring(0, 5).toUpperCase())) {
				resp = new Response(0, "isRemembered", null);
				return new ResponseEntity<Response>(resp, HttpStatus.OK); // 保存密码且没有进行基本验证
			}
		} else if (SSOAuth.isSSORequest()) {// 门户集成单点
			return login(request, loginService, loginService.getLoginByUid(SSOAuth.getSSOUid()), "");
		} else if ("".equals(authorizationInfo) || !"BASIC".equals(authorizationInfo.substring(0, 5).toUpperCase())) {
			resp = new Response(401, "401 Unauthorized Error : user no login.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.UNAUTHORIZED); // 没有登入且没有进行基本验证
		}

		// 基本验证登入处理
		authorizationInfo = authorizationInfo.split(" ")[1];
		authorizationInfo = Base64.decodeToString(authorizationInfo);
		String user = authorizationInfo.substring(0, authorizationInfo.indexOf(":"));
		String password = authorizationInfo.substring(authorizationInfo.indexOf(":") + 1);
		return login(request, loginService, user, password);

	}

	private static ResponseEntity<Response> login(HttpServletRequest request, ILoginService loginService, String user,
			String password) {
		Response resp;
		resp = loginService.login(user, password, false, request);
		if (resp.getError() == 0) {
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else if (resp.getError() == 404) {
			return new ResponseEntity<Response>(resp, HttpStatus.GONE);
		} else if (resp.getError() == 403) {
			return new ResponseEntity<Response>(resp, HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<Response>(resp, HttpStatus.UNAUTHORIZED);
		}
	}

	public static ResponseEntity<Response> checkAuthorization(HttpServletRequest request, ILoginService loginService,
			String authority) {
		List<String> authoritys = null;
		if (YESUtil.isNotEmpty(authority)) {
			authoritys = new ArrayList<String>();
			authoritys.add(authority);
		}
		return checkAuthorization(request, loginService, authoritys);
	}

	/*
	 * public static ResponseEntity<Response>
	 * checkAuthorization(HttpServletRequest request, ILoginService
	 * loginService) { return checkAuthorization(request, loginService, null); }
	 */

}
