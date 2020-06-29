package org.esy.base.dao;

import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseDao;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.DicDetail;

public interface IDicDetailDao extends IBaseDao<DicDetail> {

	public Integer getMaxSeqByModelAndId(String model, String id);

	/**
	 * @Description 用id找出资料
	 * @param
	 * @return
	 */
	public DicDetail findById(String id);

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
	 * @Description 用did和eid查找seq最大值
	 * @param did
	 * @param eid
	 * @return
	 */
	public Integer getMaxSeqByDidAndEid(String did, String eid);

	/**
	 * @Description 检查保存前的条件
	 * @param did
	 * @param eid
	 * @return
	 */
	public boolean checkForSave(String did, String eid, String id, String name, String uid);

	/**
	 * @Description 根据条件查找
	 * @param pid父ID
	 * @param classify分类编号(数据字典)
	 * @return
	 */
	public QueryResult listByCondition(Map<String, Object> parm);

	public String getValueByDid(String did, String id, String eid);

	public String getIdByDid(String did, String eid);

	public List<String> listIdByDid(String did, String eid);

	public String getValueByDid(String did, String eid);

	public List<String> listValueByDid(String did, String eid);

	/**
	 * 通过did,id获取字典表数据
	 * @author huiqiang.yan 2016-7-19 5:48 pm
	 * @param did	主类编号
	 * @param id	编号
	 * @return
	 */
	public List<DicDetail> getByCondition(String did, String id);

	/**
	 * 通过did,id删除字典数据
	 * @author huiqiang.yan 2016-7-20 9:30:00 am
	 * @param did	主类编号
	 * @param id	编号
	 * @return
	 */
	public boolean deleteByCondition(String did, String id);

}
