package org.esy.base.dao;

import java.util.List;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.Authority;
import org.esy.base.entity.AuthorityMenu;
import org.esy.base.entity.Menu;

/**
 * 
 * Dao for 群组成员
 *
 */
public interface IAuthorityDao extends IBaseDao<Authority> {

	/**
	 * 保存AuthorityMenu
	 * 
	 * @param am
	 * @return
	 */
	public boolean save(AuthorityMenu am);

	/**
	 * 通过aid 删除 菜单权限
	 * 
	 * @param aid
	 * @return
	 */
	public boolean delDetail(String aid);

	/**
	 * 通过aid得到所有的菜单权限
	 * 
	 * @param aid
	 * @return
	 */
	public List<AuthorityMenu> getDetail(String aid);

	/**
	 * 根据values 和type 取得authority列表
	 * 
	 * @param values
	 * @param type
	 * @return authority列表
	 */
	public List<Authority> getAuthorityByValuesAndType(String[] values, String type);

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
	 * 从人员找出人员对应的权限列表
	 * 
	 * @param sql
	 * @return
	 */
	public List<Authority> listByPeron(String sql);

	/**
	 * 从人员找出人员对应的权限菜单
	 * 
	 * @param sql
	 * @return
	 */
	public List<Menu> listMenuByPeron(String sql);

	/**
	 * 从人员找出人员对应的权限菜单-->登录用的menu结构
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
	public List<Menu> listMenuByAid(String aid);

	/**
	 * 
	 * @param aid-用户帐号
	 * @param menuValue-菜单值
	 * @return 检查是否有此菜单值
	 */
	public Boolean checkMenuValue(String aid, String menuValue);
}
