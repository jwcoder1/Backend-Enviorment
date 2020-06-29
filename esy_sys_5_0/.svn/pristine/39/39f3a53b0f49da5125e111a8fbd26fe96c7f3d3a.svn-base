package org.esy.base.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.IBaseService;
import org.esy.base.core.Response;
import org.esy.base.entity.Account;
import org.esy.base.entity.Person;
import org.esy.base.entity.dto.MenuDto;
import org.esy.base.entity.dto.NewAccountDto;
import org.esy.base.entity.pojo.IdentityPojo;
import org.esy.base.entity.view.Accountv;
import org.springframework.data.domain.Pageable;

public interface IAccountService extends IBaseService<Account> {

	public Account save(Account o, Account old, HttpServletRequest request);

	public boolean delete(Account o, HttpServletRequest request);

	/**
	 * 返回权限列表
	 * 
	 * @param accountId
	 * @return
	 */
	public HashSet<String> getAllPerdition(String accountId);

	/**
	 * 返回下一级菜单(包含Function)
	 * 
	 * @param accountId
	 * @param pid
	 * @return List<MenuDto>
	 */
	public List<MenuDto> getAllByAccount(String accountId, String pid);

	/**
	 * 返回下一级菜单(仅 Menu)
	 * 
	 * @param accountId
	 * @param pid
	 * @return List<MenuDto>
	 */
	public List<MenuDto> getMenuByAccount(String accountId, String pid);

	/**
	 * 返回下所有子集级菜单(包含Function)
	 * 
	 * @param accountId
	 * @param pid
	 * @return List<MenuDto>
	 */
	public List<MenuDto> getSubAllByAccount(Account account);

	/**
	 * 返回下所有子集级菜单(仅 Menu)
	 * 
	 * @param accountId
	 * @param pid
	 * @return List<MenuDto>
	 */
	public List<MenuDto> getSubMenuByAccount(String accountId, String pid);

	/**
	 * 根据编号取得用户账号
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
	 * 创建一个普通用户账号
	 * 
	 * @param newAccountDto
	 * @return
	 */
	public Response newUserAccount(NewAccountDto newAccountDto);

	/**
	 * 创建一个管理员账号
	 * 
	 * @param newAccountDto
	 * @return
	 */
	public Response newAdminAccount(NewAccountDto newAccountDto);

	/**
	 * @Description 新增人员后，创建一个普通用户账号
	 * @param Person
	 * @return
	 */
	public Response newUserAccountFromPerson(IdentityPojo identityPojo);

	/**
	 * 查找所有类型为doctor的帐号(阿吉泰项目使用)
	 * 
	 * @author huiqiang.yan 2016-06-01
	 * @param parm
	 * @return
	 */
	public List<Account> getDoctors(Map<String, Object> parm);

	/**
	 * 查找eid下的帐号（旭盈使用）
	 * 
	 * @author wwc 2016-08-19
	 * @param eid
	 * @return
	 */
	public List<Account> listByEid();

	/**
	 * 获取当前登录者的eid
	 * 
	 * @return
	 */
	public String getLoginEid();

	/**
	 * person的pid找到帐号
	 * 
	 * @param p
	 * @return
	 */
	public Account getByPerson(Person p);

	/**
	 * 通过编号查找账号
	 * 
	 * @author yangkuiping 2016-10-31
	 * @param eid
	 * @return
	 */
	public Account getByMno(String eid);

	/**
	 * 检查用户权限是否有这个菜单值
	 * 
	 * @param value
	 * @return
	 */
	public Boolean checkMenuValue(String value);

	public Object lovquery(Accountv account, Pageable pageable);

}
