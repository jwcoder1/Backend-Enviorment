package org.esy.base.dao;

import java.util.Map;

import org.esy.base.core.IBaseDao;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.InterfaceTransmit;

public interface IInterfaceTransmitDao extends IBaseDao<InterfaceTransmit> {
	public QueryResult listApplications(Map<String, Object> parm);

}
