package org.esy.base.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Menu;
import org.esy.base.entity.Person;

public interface IAccountDao extends IBaseDao<Account> {

	/**
	 * 返回权限列表
	 * 
	 * @param accountId
	 * @return
	 */
	public HashSet<String> getAllPerdition(String accountId);

	/**
	 * 根据 id 取得账号
	 * 
	 * @param id
	 * @return
	 */
	public Account getById(String id);

	/**
	 * 根据 eid 取得企业管理员账号
	 * 
	 * @param eid
	 * @return
	 */
	public Account getEnterPriseManger(String eid);

	/**
	 * 返回下一级菜单(包含Function)
	 * 
	 * @param accountId
	 * @param pid
	 * @return
	 */
	public List<Menu> getAllByAccount(String accountId, String pid);

	/**
	 * 返回下一级菜单(仅 Menu)
	 * 
	 * @param accountId
	 * @param pid
	 * @return
	 */
	public List<Menu> getMenuByAccount(String accountId, String pid);

	/**
	 * 返回下所有子集级菜单(包含Function)
	 * 
	 * @param accountId
	 * @param pid
	 * @return
	 */
	public List<Menu> getSubAllByAccount(Account account);

	/**
	 * 返回下所有子集级菜单(仅 Menu)
	 * 
	 * @param accountId
	 * @param pid
	 * @return
	 */
	public List<Menu> getSubMenuByAccount(String accountId, String pid);

	/**
	 * 用person找出对应的帐号
	 * 
	 * @param p
	 * @return
	 */
	public Account getByPerson(Person p);

	/**
	 * 
	 * @param eid
	 * @param mobile
	 * @return
	 */
	public Account getByEidAndMobile(String eid, String mobile);

	/**
	 * 
	 * @param parm
	 * @return
	 */
	public List<Account> getDoctor(Map<String, Object> parm);

	/**
	 * 
	 * @param mobile
	 * @return
	 */
	public List<Account> getByMobile(String mobile);

	/**
	 * 通过用户类型获取该类型下的所有用户成员
	 * 
	 * @author huiqiang.yan 2016-01-12
	 * @param type->用户类型
	 * @return
	 */
	public List<Account> getAllCountByType(String type);

	/**
	 * 通过帐号/编号获取该帐号是否存在
	 * 
	 * @param alias
	 *            帐号
	 * @param aid
	 *            编号
	 * @return
	 */
	public Account getByAlias(String alias, String aid);

	/**
	 * 查找eid下的帐号（旭应使用）
	 * 
	 * @author wwc 2016-08-19
	 * @param eid
	 * @return
	 */
	public List<Account> listByEid(String eid);

	/**
	 * 通过编号查找账号
	 * 
	 * @author yangkuiping 2016-10-31
	 * @param eid
	 * @return
	 */
	public Account getByMno(String eid);

}
