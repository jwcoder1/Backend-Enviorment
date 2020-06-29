package org.esy.base.security.core;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;

/**
 * 
 * @Title: FilterChainSource.java
 * @Description: 
 * @author: duiqing.huang
 * @date: 2014年6月17日 上午10:49:42
 * @version: V1.0
 * 
 */
public class FilterChainSource implements FactoryBean<Ini.Section> {

	private String filterChainDefinitions;

	@Override
	public Section getObject() throws Exception {
		Ini ini = new Ini();
		// 加载默认的url
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		// 循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,
		// 里面的键就是链接URL,值就是存在什么条件才能访问该链接
		addPlatform(section);
		return section;
	}

	private void addPlatform(Ini.Section section) {
		// section.put("/**", "user,perms[Platform_Admin_Entry]");
	}

	@Override
	public Class<?> getObjectType() {
		return this.getClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}
}
