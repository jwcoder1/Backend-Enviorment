package org.esy.base.core;

/**
 * web_client被委托类需要继承此接口
 * 
 * @author WYF 2016-12-29
 *
 */
public abstract class WebClientNotifier {

	private WebClientEventHandler eventHandler = new WebClientEventHandler();

	public WebClientEventHandler getEventHandler() {
		return eventHandler;
	}

	public void setEventHandler(WebClientEventHandler eventHandler) {
		this.eventHandler = eventHandler;
	}

	// 增加委托类
	public abstract void addListener(Object object, Object... args);

	// 执行
	public abstract void notifyX();

}
