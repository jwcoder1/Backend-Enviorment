package org.esy.base.security.core;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Title:
 * @Description: 
 * @author: duiqing.huang
 * @date: 2014年6月4日 下午5:44:39
 * @version: V1.0
 *
 */
public class BaseFormAuthenticationFilter extends FormAuthenticationFilter {
	private static final Logger log = LoggerFactory.getLogger(BaseFormAuthenticationFilter.class);

	/**
	 * 
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return
	 * @see org.apache.shiro.web.filter.authc.AuthenticatingFilter#isAccessAllowed(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, java.lang.Object)
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		try {
			// 先判断是否是登录操作
			if (isLoginSubmission(request, response)) {
				if (log.isTraceEnabled()) {
					log.trace("Login submission detected.  Attempting to execute login.");
				}
				return false;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return super.isAccessAllowed(request, response, mappedValue);
	}
}
