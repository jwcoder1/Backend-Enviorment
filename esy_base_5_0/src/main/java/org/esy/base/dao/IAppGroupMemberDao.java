package org.esy.base.dao;

import java.util.List;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.AppGroupMember;

/**
 * 
 * Dao for 群组成员
 *
 */
public interface IAppGroupMemberDao extends IBaseDao<AppGroupMember> {

	/**
	 * 通过agid 删除所有AppGroupMember
	 * 
	 * @param gid
	 * @return
	 */
	public boolean deleteByAgid(String agid);

	/**
	 * 通过aid 删除所有AppGroupMember
	 * 
	 * @param gid
	 * @return
	 */
	public boolean deleteByAid(String aid);

	/**
	 * 根据类型和值删除
	 * 
	 * @param type
	 * @param values
	 * @return 受影响行数
	 */
	public int deleteByValuesAndType(String[] values, String type);

	/**
	 * 根据appids 和agid 取得groupmember列表
	 * 
	 * @param appids
	 * @param agid
	 * @return AppGroupMember列表
	 */
	public List<AppGroupMember> getAppGroupMemberByAppidsAndAgid(String[] appids, String agid);

	/**
	 * 根据appids取得groupmember列表
	 * 
	 * @param appids
	 * @param agid
	 * @return AppGroupMember列表
	 */
	public List<AppGroupMember> getAppGroupMemberByAppids(List<String> appids);

	/**
	 * 根据类型和值修改 显示的名称
	 * 
	 * @param type
	 * @param value
	 * @param newShow
	 * @return 受影响行数
	 */
	public int updateShowByValue(String value, String newShow);

	/**
	 * @Description 用appid,eid删除AppGroupMember
	 * @param appid
	 * @param eid
	 * @return
	 */
	public boolean deleteByAppidAndEid(String appid, String eid);

	/**
	 * @Description 用appid,eid,agid删除AppGroupMember
	 * @param appid
	 * @param eid
	 * @param agid
	 * @return
	 */
	public boolean deleteByAppidAndEidAndAgid(String appid, String agid, String eid);

	/**
	 * @Description 用appid,eid 查找AppGroupMember
	 * @param appid
	 * @param eid
	 * @return
	 */
	public List<AppGroupMember> listByAppidAndEid(String appid, String eid);

	/**
	 * @Description 用appid,eid 查找AppGroupMember的Agid（应用分组id）
	 * @param appid
	 * @param eid
	 * @return
	 */
	public List<String> listAgidByAppidAndEid(String appid, String eid);

	/**
	 * @Description 找出最大的seq
	 * @param agid
	 * @param eid
	 * @return
	 */
	public Integer getMaxSeq(String agid, String eid);

	/**
	 * @Description 获取seq
	 * @param appid
	 * @param eid
	 * @param agid
	 * @return
	 */
	public Integer getSeqByAppidAndEidAndAgid(String appid, String agid, String eid);

	/**
	 * @Description 获取AppGroupMember
	 * @param appid
	 * @param eid
	 * @param agid
	 * @return
	 */
	public AppGroupMember getByAppidAndEidAndAgid(String appid, String agid, String eid);

}
