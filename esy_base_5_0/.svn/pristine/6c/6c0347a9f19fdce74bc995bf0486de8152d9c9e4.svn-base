package org.esy.base.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.IBaseService;
import org.esy.base.entity.Application;
import org.esy.base.entity.dto.AppGroupDto;

/**
 * 
 * service
 *
 */
public interface IApplicationService extends IBaseService<Application> {

	public Application save(Application o, Application old, HttpServletRequest request);

	/**
	 * 通过Account.uid 得到 分组过后的applications
	 * 
	 * @param uid
	 * @return
	 */
	public List<AppGroupDto> getApplicationsByAccount(String uid);

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
}
