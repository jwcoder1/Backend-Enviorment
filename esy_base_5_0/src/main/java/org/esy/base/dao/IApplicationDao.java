package org.esy.base.dao;

import java.util.List;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Application;

/**
 * 
 * Dao for 应用
 *
 */
public interface IApplicationDao extends IBaseDao<Application> {

	/**
	 * 通过Account 得到 有权限的applications
	 * 
	 * @param uid
	 * @return
	 */
	public List<Application> getApplicationsByAccount(Account account);

	/**
	 * @Description 根据id,密码找出应用
	 * @param id
	 * @param password
	 * @return
	 */
	public Application getByIDandPW(String id, String password);

	/**
	 * @Description 从aid找到eid
	 * @param aid
	 * @return
	 */
	public String getEidByAid(String aid);

	/**
	 * @Description 从aid找到name
	 * @param aid
	 * @return
	 */
	public String getNameByAid(String aid);

}
