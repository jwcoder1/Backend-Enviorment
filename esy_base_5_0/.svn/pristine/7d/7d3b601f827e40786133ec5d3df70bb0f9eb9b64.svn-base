package org.esy.base.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.IBaseService;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.Enterprise;
import org.esy.base.entity.pojo.MsgResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 
 * Dao for 企业信息
 *
 */
public interface IEnterpriseService extends IBaseService<Enterprise> {

	public Enterprise save(Enterprise o, Enterprise old, HttpServletRequest request);

	/**
	 * 根据编号取得对应实体
	 * 
	 * @param eid
	 * @return
	 */
	public Enterprise getById(String eid);

	/**
	 * @Description 根据当前登录的eid取得企业列表
	 * @param eid
	 * @return List<Enterprise>
	 */
	public List<Enterprise> listByEid(String eid);

	/**
	 * @Description 根据条件查找
	 * @param pid父ID
	 * @param classify分类编号(数据字典)
	 * @return
	 */
	public QueryResult listByPidAndClassify(Map<String, Object> parm);

	/**
	 * 从父节点eid找出子企业eid
	 * 
	 * @param eids
	 * @return
	 */
	public List<String> listEidsByParentEids(List<String> eids);

	/**
	 * 从人员找到所属企业，包括上级企业
	 * 
	 * @param pid
	 * @return
	 */
	public List<Enterprise> listByPid(String pid);

	/**
	 * 检查是否可删除
	 * 
	 * @param enterprise
	 * @return
	 */
	public MsgResult checkForDelele(Enterprise enterprise);

	public Page<Enterprise> query(Enterprise enterprise, Pageable pageable);

}
