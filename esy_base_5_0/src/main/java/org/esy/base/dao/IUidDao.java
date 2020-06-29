package org.esy.base.dao;

import java.util.Map;

import org.esy.base.core.QueryResult;
import org.esy.base.entity.Account;
import org.esy.base.entity.Uid;

public interface IUidDao {

	/**
	 * @Description 主键查找
	 * @param uid
	 * @return
	 */
	public Uid getById(String uid);

	/**
	 * @Description 保存UID
	 * @param Uid
	 *            bean
	 * @return
	 */
	public Uid save(Uid uid);

	/**
	 * @Description 根据条件判断是否存在
	 * @param
	 * @return
	 */
	public Uid getByUid(String uid, String staffNo, String tempStaffNo, String birthday, String name, String identifyNo, String status);

	/**
	 * @Description 通过职工号查找
	 * @param
	 * @return
	 */
	public Uid getByStaffNo(String staffNo, String uid);

	/**
	 * 根据正式职工号判断是否存在
	 * 
	 * @param staffNo
	 * @return
	 */
	public boolean isByStaffNo(String staffNo);

	/**
	 * 根据临时职工号判断是否存在
	 * 
	 * @param tempStaffNo
	 * @return
	 */
	public boolean isByTempStaffNo(String tempStaffNo);

	/**
	 * 根据uid判断是否存在
	 * 
	 * @param uid
	 * @return
	 */
	public boolean isByUid(String uid);

	/**
	 * 根据uid删除数据
	 * 
	 * @param uid
	 * @return
	 */
	public boolean delete(Uid uid);

	/**
	 * 正式员工查找职工号+uid
	 * 
	 * @param StaffNo
	 * @param uid
	 * @param status
	 * @return
	 */
	public Uid getByStaffNoAndUidForEffected(String staffNo, String uid, String status);

	/**
	 * 临时员工查找临时职工号+uid
	 * 
	 * @param tempstaffNo
	 * @param uid
	 * @param status
	 * @return
	 */
	public Uid getBytempStaffNoAndUidForEffected(String tempstaffNo, String uid, String status);

	/**
	 * 模糊查询
	 * 
	 * @param parm
	 * @return
	 */
	public QueryResult query(Map<String, Object> parm);

	/**
	 * 根据uid 找出Account
	 * 
	 * @param uid
	 * @return
	 */
	public Account getByUid(String uid);
}
