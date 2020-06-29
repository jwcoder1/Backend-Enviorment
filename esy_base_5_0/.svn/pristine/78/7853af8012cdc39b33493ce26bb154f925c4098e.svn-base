package org.esy.base.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.IBaseService;
import org.esy.base.entity.AppGroup;
import org.esy.base.entity.pojo.MsgResult;

/**
 * 
 * service
 *
 */
public interface IAppGroupService extends IBaseService<AppGroup> {

	public AppGroup save(AppGroup o, AppGroup old, HttpServletRequest request);

	public Long getChildrenCount(String agid);

	/**
	 * @Description 改变拖动的位置
	 * @param
	 * @return
	 */
	public MsgResult changeLocation(Map<String, Object> parm, String classname);

}
