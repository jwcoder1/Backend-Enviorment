package org.esy.base.service;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.ChangePasswordBody;
import org.esy.base.core.Response;

public interface ILoginService {

	public Response setPassword(ChangePasswordBody changePasswordBody, HttpServletRequest request);

	public Response setPassword(String id, ChangePasswordBody changePasswordBody, HttpServletRequest request);

	public Response login(String username, String password, boolean rememberMe, HttpServletRequest request);

	String getLoginByUid(String uid);

}
