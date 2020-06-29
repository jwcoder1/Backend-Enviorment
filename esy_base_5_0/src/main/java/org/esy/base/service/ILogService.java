package org.esy.base.service;

import java.util.Map;

import org.esy.base.core.QueryResult;
import org.esy.base.core.Response;
import org.esy.base.dao.core.PageResult;
import org.esy.base.entity.Log;
import org.springframework.data.domain.Pageable;

public interface ILogService {

	public void save(String moduleId, String eventId, String message, String localIp);

	public Response saveRemote(String moduleId, String eventId, String message, String localIp, String user,
			String date, String remoteIp);

	public QueryResult query(Map<String, Object> parm);

	public PageResult<Log> query(Log log, Pageable pageable);

}
