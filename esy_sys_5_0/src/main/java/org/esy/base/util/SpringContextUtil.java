package org.esy.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext appContexts;

	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.appContexts = applicationContext;
	}

	public static Object getBean(String beanId) {
		return appContexts.getBean(beanId);
	}

	public static <T> T getBean(String beanId, Class<T> cls) {
		return appContexts.getBean(beanId, cls);
	}

}
