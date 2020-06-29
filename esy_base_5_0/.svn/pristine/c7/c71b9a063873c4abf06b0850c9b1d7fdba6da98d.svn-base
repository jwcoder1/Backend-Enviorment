package org.esy.base.service;

import java.util.Map;

import org.esy.base.core.QueryResult;
import org.esy.base.core.Response;
import org.esy.base.entity.Account;
import org.esy.base.entity.Uid;

public interface IUidService {

	/**
	 * 
	 * @Description 获取新uid
	 * @param type临时人员,正式人员
	 * @param perfix-前缀
	 * @return
	 */
	public String getNewUid(String type, String perfix);

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
	 * @Description 新增一个uid, uid算法在里面实现，职工保存到staffNo
	 * @param
	 * @return 新uid
	 */
	public Uid newUid(String tempStaffNo, String birthday, String name, String identifyNo);

	/**
	 * @Description 检查Regularuid的保存
	 * @param Uid实体
	 * @return Response
	 */
	public Response checkForRegularuid(Uid o);

	/**
	 * @Description Regularuid的保存
	 * @param Uid实体
	 * @return Uid
	 */
	public Uid saveForRegularuid(Uid o);

	/**
	 * @Description 检查TEMP的保存
	 * @param Uid实体
	 * @return Response
	 */
	public Response checkForTempuid(Uid o);

	/**
	 * @Description TEMP的保存
	 * @param Uid实体
	 * @param aid应用id
	 * @return Uid
	 */
	public Uid saveForTempuid(Uid o, String aid);

	/**
	 * 检查是否有重复或者为空的关键字段
	 * 
	 * @param uid
	 * @return
	 */
	public Response checkForAdd(Uid uid);

	/**
	 * 判断是否为有相同职工号
	 * 
	 * @param staffNo
	 * @return
	 */
	public boolean isByStaffNo(String staffNo);

	/**
	 * 判断是否为有相同uid
	 * 
	 * @param staffNo
	 * @return
	 */
	public boolean isByUid(String uid);

	/**
	 * 在修改时，检查是否有重复或者空的关键字段
	 * 
	 * @param uid
	 * @return
	 */
	public Response checkForPut(Uid uid);

	/**
	 * 删除
	 * 
	 * @param uid
	 * @return
	 */
	public boolean delete(Uid uid);

	/**
	 * 模糊查询
	 * 
	 * @param parm
	 * @return
	 */
	public QueryResult query(Map<String, Object> parm);

	/**
	 * 获取一个临时职工号
	 * 
	 * @param eid
	 * @return
	 */
	public String getTempNo(String eid);

	/**
	 * 获取状态值，正式or临时
	 * 
	 * @param uid
	 * @return
	 */
	public String getStatus(Uid uid);

	/**
	 * 从uid 获取帐号
	 * 
	 * @param uid
	 * @return
	 */
	public Account getByUid(String uid);

}
