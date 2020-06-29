package org.esy.base.service;

import java.util.Map;

import org.esy.base.core.IBaseService;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.AppAuthority;

/**
 * 
 * Dao for 群组成员
 *
 */
public interface IAppAuthorityService extends IBaseService<AppAuthority> {

	/**
	 * 通过aid 删除所有AppAuthority
	 * 
	 * @param gid
	 * @return
	 */
	public boolean deleteByAid(String aid);

	/**
	 * 根据类型和值修改 显示的名称
	 * 
	 * @param type
	 * @param value
	 * @param newShow
	 * @return 受影响行数
	 */
	public int updateShowByValueAndType(String type, String value, String newShow);

	/**
	 * 通过类型和值删除对应的权限(AppAuthority)
	 * 
	 * @param values多个以","隔开
	 * @param type
	 * @return
	 */
	public int deleteByValuesAndType(String values, String type);

	/**
	 * 查找应用对应的人员
	 * 
	 * @param parm
	 * @return
	 */
	public QueryResult listGroupPeople(Map<String, Object> parm);

	/**
	 * 说明： 从职工号，找出person,找出这个人所对应的权限分组
	 * 
	 * @param staffno
	 * @return
	 */
	public QueryResult findByPerson(String pid, String eid);

	public QueryResult findApplicationByPerson(String pid, String eid);

	/**
	 * 
	 */
	public QueryResult findByAppAuthority(Map<String, Object> parm);

}
