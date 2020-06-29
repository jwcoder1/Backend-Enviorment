package org.esy.base.service;

import java.util.Map;

import org.esy.base.core.IBaseService;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.InterfaceTransmit;

public interface IInterfaceTransmitService extends IBaseService<InterfaceTransmit> {

	public QueryResult listApplications(Map<String, Object> parm);
}
