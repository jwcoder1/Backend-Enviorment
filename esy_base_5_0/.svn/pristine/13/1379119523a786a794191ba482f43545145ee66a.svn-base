package org.esy.base.service;

import java.util.List;

import org.esy.base.core.IBaseService;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.Authority;
import org.esy.base.entity.AuthorityMenu;
import org.esy.base.entity.Menu;

/**
 * 
 * Dao for 群组成员
 *
 */
public interface IAuthorityService extends IBaseService<Authority> {

	/**
	 * 保存AuthorityMenu
	 * 
	 * @param am
	 * @return
	 */
	public boolean save(AuthorityMenu am);

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
	 * 通过aid得到所有的菜单权限
	 * 
	 * @param aid
	 * @return
	 */
	public List<AuthorityMenu> getDetail(String aid);

	/**
	 * 通过类型和值删除对应的权限
	 * 
	 * @param values
	 *            多个以","隔开
	 * @param type
	 * @return
	 */
	public boolean deleteByValuesAndType(String values, String type);

	/**
	 * 说明： 从职工号，找出person,找出这个人所对应的权限分组
	 * 
	 * @param staffno
	 * @return
	 */
	public QueryResult findByPerson(String pid, String eid);

	/**
	 * 说明： 从职工号，找出person,找出这个人所对应的权限
	 * 
	 * @param staffno
	 * @return
	 */
	public QueryResult findAuthorityMenuByPerson(String pid, String eid);

	/**
	 * 找出人员对应的表结构的数据,authority和appauthority
	 * 
	 * @param staffno
	 * @return
	 */
	public String getPersonSql(String pid, String eid);

	/**
	 * 
	 * @param sql
	 * @return
	 */
	public List<Menu> listMenuByPeronForLogin(String sql);

	/**
	 * 从aid对应的权限菜单
	 * 
	 * @param sql
	 * @return
	 */
	public QueryResult listMenuByAid(String aid);

	/**
	 * 旭盈专用
	 * 
	 * @param b
	 */
	public void saveEntity(Authority b);
}
