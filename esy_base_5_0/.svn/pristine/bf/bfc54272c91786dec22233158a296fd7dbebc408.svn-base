package org.esy.base.dao;

import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseDao;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.Enterprise;

/**
 * 
 * Dao for 企业信息
 *
 */
public interface IEnterpriseDao extends IBaseDao<Enterprise> {

	/**
	 * 根据 id 取得实体
	 * 
	 * @param id
	 * @return
	 */
	public Enterprise getById(String eid);

	public Enterprise getByCname(String cname);

	/**
	 * @Description 根据当前登录的eid取得企业列表
	 * @param eid
	 * @return List<Enterprise>
	 */
	public List<Enterprise> listByEid(String eid);

	/**
	 * 
	 * @Description 根据条件查找
	 * @param pid父ID
	 * @param classify分类编号(数据字典)
	 * @return
	 */
	public QueryResult listByPidAndClassify(Map<String, Object> parm);

	/**
	 * 
	 * @Description 企业类型找出企业eid
	 * @param classify企业类型
	 * @return
	 */
	public List<String> listEidByClassify(List<String> classifys);

	/**
	 * 
	 * @Description 企业no找出企业eid
	 * @param 企业no
	 * @return
	 */
	public String getEidByNo(String no);

	/**
	 * 通过编号找到企业
	 * 
	 * @param no
	 * @return
	 */
	public Enterprise getByNo(String no);

	/**
	 * 从父节点eid找出子企业eid
	 * 
	 * @param eids
	 * @return
	 */
	public List<String> listEidsByParentEids(List<String> eids);

	/**
	 * 用企业编号查找
	 * 
	 * @param nos
	 * @return
	 */
	public List<Enterprise> listByNos(List<String> nos);

	/**
	 * 是否有子企业
	 * 
	 * @param no
	 * @return
	 */
	public boolean existsChildEnterprise(String pid);
}
