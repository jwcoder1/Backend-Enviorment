package org.esy.base.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.ChangePasswordBody;
import org.esy.base.core.Response;
import org.esy.base.entity.Account;
import org.esy.base.entity.Enterprise;
import org.esy.base.entity.dto.MenuDto;
import org.esy.base.security.service.ShiroRealm.ShiroUser;
import org.esy.base.security.util.BCrypt;
import org.esy.base.service.IAccountService;
import org.esy.base.service.IEnterpriseService;
import org.esy.base.service.ILogService;
import org.esy.base.service.ILoginService;
import org.esy.base.service.IUidService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private IAccountService accountService;

	@Autowired
	private IEnterpriseService enterPriseService;

	@Autowired
	private ILogService logService;

	@Autowired
	private IUidService uidService;

	@Override
	public Response setPassword(ChangePasswordBody changePasswordBody, HttpServletRequest request) {

		if (YESUtil.isEmpty(changePasswordBody.getNewPassword())) {
			return new Response(500, "新密码为必填项", null);
		}
		if (YESUtil.isEmpty(changePasswordBody.getConfirmPassword())) {
			return new Response(500, "确认密码为必填项", null);
		}
		if (!changePasswordBody.getNewPassword().equals(changePasswordBody.getConfirmPassword())) {
			return new Response(500, "新密码与确认密码不一致", null);
		}

		Account account = BaseUtil.getUser();

		if (BCrypt.checkpw(changePasswordBody.getOldPassword(), account.getPassword())) {
			account.setPassword(BCrypt.hashpw(changePasswordBody.getNewPassword(), BCrypt.gensalt()));
			accountService.save(account);
			logService.save("base", "AccountChangePassword",
					"[" + account.getAid() + "]" + account.getName() + " : 修改密码成功", request.getRemoteAddr());
			return new Response(0, "密码修改成功", null);
		} else {
			return new Response(500, "旧密码输入错误", null);
		}
	}

	@SuppressWarnings("unused")
	@Override
	public Response login(String username, String password, boolean rememberMe, HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();

		Account account = null;

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(rememberMe);
		try {
			account = accountService.getById(username);
			YESUtil.putSession("lastLogin", account.getLastLogin());
			if (account != null) {
				if (account.isEnable() == false) {
					logService.save("base", "LoginFailed",
							"[" + account.getAid() + "]" + account.getName() + " : 账号已被禁用", request.getRemoteAddr());
					return new Response(403, "账号已被禁用", null);
				}
			} else {
				logService.save("base", "LoginFailed", "[" + username + "] : 账号不存在", request.getRemoteAddr());
				return new Response(404, "账号不存在", null);
			}
			currentUser.login(token);
			account.setLastLogin(new Date());
			account = accountService.save(account);
			List<MenuDto> menus = accountService.getSubAllByAccount(account);
			account.setMenus(menus);
			YESUtil.putSession("user", account);

		} catch (UnknownAccountException ex) {
			logService.save("base", "LoginFailed", "[" + username + "] : 账号不存在", request.getRemoteAddr());
			return new Response(404, "账号不存在", null);
		} catch (IncorrectCredentialsException ex) {
			logService.save("base", "LoginFailed", "[" + username + "] : 密码错误", request.getRemoteAddr());
			return new Response(403, "密码错误", null);
		} catch (LockedAccountException ex) {
			logService.save("base", "LoginFailed", "[" + username + "] : 账号已被禁用", request.getRemoteAddr());
			return new Response(403, "账号已被禁用", null);
		} catch (AuthenticationException ex) {
			logService.save("base", "LoginFailed", "[" + username + "] : 账号没有授权", request.getRemoteAddr());
			return new Response(403, "账号没有授权", null);
		} catch (Exception ex) {
			ex.printStackTrace();
			logService.save("base", "LoginFailed", "[" + username + "] : 帐号或密码错误", request.getRemoteAddr());
			return new Response(500, "帐号或密码错误", null);
		}

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		logService.save("base", "LoginSuccess", "[" + account.getAid() + "]" + account.getName() + " : 登入成功", ip);

		Map<String, String> userinfo = new HashMap<String, String>();

		if (account == null) {
			userinfo.put("id", ((ShiroUser) currentUser.getPrincipal()).getUserLoginID());
			userinfo.put("name", ((ShiroUser) currentUser.getPrincipal()).toString());
		} else {
			userinfo.put("id", account.getAid());
			userinfo.put("name", account.getName());
		}
		userinfo.put("sessonid", request.getSession().getId());
		return new Response(0, "登入成功", userinfo);
	}

	@Override
	public Response setPassword(String id, ChangePasswordBody changePasswordBody, HttpServletRequest request) {

		if (YESUtil.isEmpty(changePasswordBody.getNewPassword())) {
			return new Response(500, "新密码为必填项", null);
		}
		if (YESUtil.isEmpty(changePasswordBody.getConfirmPassword())) {
			return new Response(500, "确认密码为必填项", null);
		}
		if (!changePasswordBody.getNewPassword().equals(changePasswordBody.getConfirmPassword())) {
			return new Response(500, "新密码与确认密码不一致", null);
		}

		Account account = accountService.getById(id);
		if (account == null) {
			return new Response(500, "用户没有找到", null);
		}

		account.setPassword(changePasswordBody.getNewPassword());
		accountService.save(account);
		logService.save("base", "AccountChangePassword", "[" + account.getAid() + "]" + account.getName() + " : 修改密码成功",
				request.getRemoteAddr());
		return new Response(0, "密码修改成功", null);
	}

	/**
	 * 
	 */
	@Override
	public String getLoginByUid(String uid) {
		Account account = uidService.getByUid(uid);
		return account == null ? "" : account.getAid();
	}

}
