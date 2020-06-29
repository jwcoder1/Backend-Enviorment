package org.esy.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring biz
 * 
 * @author duiqing.huang
 * 
 */
@Component
public class BizUtils implements ApplicationContextAware {

	private static ApplicationContext appContexts;

	public BizUtils() {
	}

	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContexts = applicationContext;
	}

	/**
	 * 根据biz名称取业务对象
	 * 
	 * @param beanId
	 * @return
	 */
	public static Object getBean(String beanId) {
		return appContexts.getBean(beanId);
	}

	public static ApplicationContext getApplicationContext() {
		return appContexts;
	}
}
