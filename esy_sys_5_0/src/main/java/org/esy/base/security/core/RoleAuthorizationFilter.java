package org.esy.base.security.core;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.esy.base.core.Response;
import org.esy.base.util.JsonUtil;

public class RoleAuthorizationFilter extends AuthorizationFilter {

	private final String NOLOGIN = "401 Unauthorized Error : user no login.";

	private final String NOAUTHORITY = "401 Unauthorized Error : user no authority.";

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

		Subject subject = getSubject(request, response);
		HttpServletResponse httpResponse = WebUtils.toHttp(response);
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		String accept = httpRequest.getHeader("Accept");
		if (ContentType.APPLICATION_JSON.getMimeType().equals(accept)) {
			httpResponse.setContentType(ContentType.APPLICATION_JSON.toString());
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			httpResponse.getWriter().append(JsonUtil
					.beanToJson(new Response(401, subject.getPrincipal() == null ? NOLOGIN : NOAUTHORITY, null)));
			return false;
		}

		if (subject.getPrincipal() == null) {
			if (SSOAuth.isSSORequest()) {//中烟门户单点集成
				UsernamePasswordToken token = new UsernamePasswordToken(SSOAuth.getSSOUid(), "");
				subject.login(token);
				redirect(request, response, httpResponse);
				return false;
			}
			saveRequestAndRedirectToLogin(request, response);
		} else {
			redirect(request, response, httpResponse);
		}
		return false;
	}

	private void redirect(ServletRequest request, ServletResponse response, HttpServletResponse httpResponse)
			throws IOException {
		String unauthorizedUrl = getUnauthorizedUrl();
		if (StringUtils.hasText(unauthorizedUrl)) {
			WebUtils.issueRedirect(request, response, unauthorizedUrl);
		} else {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		String[] rolesArray = (String[]) mappedValue;
		if (rolesArray == null || rolesArray.length == 0) {
			return true;
		}
		Set<String> roles = CollectionUtils.asSet(rolesArray);
		return subject.hasAllRoles(roles);
	}

}
