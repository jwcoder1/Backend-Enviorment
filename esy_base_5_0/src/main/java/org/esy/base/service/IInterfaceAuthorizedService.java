package org.esy.base.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.IBaseService;
import org.esy.base.core.QueryResult;
import org.esy.base.core.Response;
import org.esy.base.entity.InterfaceAuthorized;

public interface IInterfaceAuthorizedService extends IBaseService<InterfaceAuthorized> {

	/**
	 * @Description 对外接口验证帐号密码 wwc 2015-12-7
	 * @param authoritys权限列表
	 * @param iid
	 *            接口id
	 * @return
	 */
	public Response checkAuthorization(HttpServletRequest request, String iid, List<String> authoritys);

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