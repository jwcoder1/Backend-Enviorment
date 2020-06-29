package org.esy.base.dao;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.Serial;

public interface ISerialDao extends IBaseDao<Serial> {

	public Serial getSerial(String moduleName, String entityName, String serialKey);

}
