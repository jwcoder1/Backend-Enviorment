package org.esy.base.dao;

import java.util.Map;

import org.esy.base.core.IBaseDao;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.InterfaceAuthorized;

public interface IInterfaceAuthorizedDao extends IBaseDao<InterfaceAuthorized> {

	public InterfaceAuthorized getByIdPassword(String iid, String aid, String password);

	/**
	 * @Description 从应用aid，找出对应的企业路径
	 * @param 应用aid
	 * @return
	 */
	public String getEidFromAid(String aid);

	/**
	 * 找接口对应的应用
	 * 
	 * @param parm
	 *            iid
	 * @return
	 */
	public QueryResult listApplications(Map<String, Object> parm);

}
