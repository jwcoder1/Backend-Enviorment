package org.esy.base.core;

import java.util.ArrayList;
import java.util.List;

public class WebClientEventHandler {

	// 是用一个List
	private List<WebClientEvent> objects;

	public WebClientEventHandler() {
		objects = new ArrayList<WebClientEvent>();
	}

	// 添加某个对象要执行的事件，及需要的参数
	public void addEvent(Object object, String methodName, Object... args) {
		objects.add(new WebClientEvent(object, methodName, args));
	}

	// 通知所有的对象执行指定的事件
	public void notifyX() throws Exception {
		for (WebClientEvent e : objects) {
			e.invoke();
		}
	}

}
