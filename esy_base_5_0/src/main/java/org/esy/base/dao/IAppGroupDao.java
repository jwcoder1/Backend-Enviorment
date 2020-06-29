package org.esy.base.dao;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.AppGroup;

/**
 * 
 * Dao for 应用
 *
 */
public interface IAppGroupDao extends IBaseDao<AppGroup> {

	public Long getChildrenCount(String agid);

	/**
	 * @Description 取最大的seq
	 * @param pid父id
	 * @param eid
	 * @return
	 */
	public Integer getMaxSeq(String eid, String pid);

}
