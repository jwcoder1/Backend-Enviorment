package org.esy.base.dao;

import java.util.Map;

import org.esy.base.core.IBaseDao;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.Log;

public interface ILogDao extends IBaseDao<Log> {

	public QueryResult query(Map<String, Object> parm);
}
