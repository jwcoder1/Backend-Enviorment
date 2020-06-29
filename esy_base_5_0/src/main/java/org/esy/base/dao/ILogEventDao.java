package org.esy.base.dao;

import java.util.List;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.LogEvent;

public interface ILogEventDao extends IBaseDao<LogEvent> {

	public LogEvent getById(String moduleId, String eventId);

	public List<LogEvent> getByMid(String moduleId);

}
