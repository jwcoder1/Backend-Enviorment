package org.esy.base.dao;

import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.AppAuthority;
import org.esy.base.entity.AppGroupMember;

/**
 * 
 * Dao for 群组成员
 *
 */
public interface IAppAuthorityDao extends IBaseDao<AppAuthority> {

	/**
	 * 通过aid 删除所有AppAuthority
	 * 
	 * @param gid
	 * @return
	 */
	public boolean deleteByAid(String aid);

	/**
	 * 通过类型和values删除数据
	 * 
	 * @param values
	 * @param type
	 * @return
	 */
	public int deleteByValuesAndType(String[] values, String type);

	/**
	 * 根据values 和type 取得appAuthority列表
	 * 
	 * @param values
	 * @param type
	 * @return appAuthority列表
	 */
	public List<AppAuthority> getAppAuthorityByValuesAndTypeAndAid(String[] values, String type, String aid);

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
	 * 根据aid 找出应用群组
	 * 
	 * @param aid
	 * @return
	 */
	public List<AppAuthority> listByAid(String aid);

	/**
	 * 找出人员对应的权限分组列表
	 * 
	 * @param staffno
	 * @return
	 */
	public List<AppAuthority> findByPerson(String sql);

	/**
	 * 找出人员对应的应用
	 * 
	 * @param staffno
	 * @return
	 */
	public List<AppGroupMember> findApplicationByPerson(String sql);

	/**
	 * 
	 */
	public List<AppGroupMember> findByAppAuthority(Map<String, Object> parm);

}
