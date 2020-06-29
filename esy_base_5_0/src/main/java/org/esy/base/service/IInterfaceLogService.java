package org.esy.base.service;

import org.esy.base.core.IBaseService;
import org.esy.base.entity.InterfaceLog;

public interface IInterfaceLogService extends IBaseService<InterfaceLog> {

	// 开始记录
	public InterfaceLog StartLog(String iid, String aid, String url, String send, String recv, String type, String ip);

	// 保存log
	public InterfaceLog saveLog(InterfaceLog log);
}
