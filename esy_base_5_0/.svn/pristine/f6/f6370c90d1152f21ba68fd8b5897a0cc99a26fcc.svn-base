package org.esy.base.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.QueryResult;
import org.esy.base.entity.DicDetail;
import org.esy.base.entity.pojo.MsgResult;

public interface IDicDetailService {

	public DicDetail save(DicDetail o, HttpServletRequest request);

	public boolean delete(DicDetail o, HttpServletRequest request);

	public DicDetail getByUid(String uid);

	public QueryResult query(Map<String, Object> parm);

	/**
	 * @Description 用父类ID，企业路径查找
	 * @param
	 * @return
	 */
	public String getValueByDdidandEid(String did, String eid);

	/**
	 * @Description 用父类ID，企业路径查找
	 * @param
	 * @return
	 */
	public List<DicDetail> listValueByDdidandEid(String did, String eid);

	/**
	 * @Description 检查是否可以保存
	 * @param
	 * @return
	 */
	public MsgResult checkForSave(DicDetail o);

	/**
	 * @Description 根据条件查找
	 * @param
	 * @return
	 */
	public QueryResult listByCondition(Map<String, Object> parm);

	/**
	 * 找出具体一项值
	 * 
	 * @param didd
	 * @param did
	 * @param eid
	 * @return
	 */
	public String getValueByDid(String did, String id, String eid);

	public String getIdByDid(String did, String eid);

	public List<String> listIdByDid(String did, String eid);

	public String getValueByDid(String did, String eid);

	public List<String> listValueByDid(String did, String eid);

	/**
	 * 通过did,id获取字典表数据
	 * @author huiqiang.yan 2016-7-20 9:00:00 am
	 * @param did	主类编号
	 * @param id	编号
	 * @return
	 */
	public List<DicDetail> getByCondition(Map<String, Object> parm);

	/**
	 * 通过did,id删除字典数据
	 * @author huiqiang.yan 2016-7-20 9:30:00 am
	 * @param did	主类编号
	 * @param id	编号
	 * @return
	 */
	public boolean deleteByCondition(Map<String, Object> parm);

}
