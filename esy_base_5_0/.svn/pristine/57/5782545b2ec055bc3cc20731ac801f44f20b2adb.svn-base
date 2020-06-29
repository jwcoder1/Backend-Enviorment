package org.esy.base.service;

import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseService;
import org.esy.base.entity.AppGroupMember;
import org.esy.base.entity.pojo.MsgResult;

/**
 * 
 * Dao for 群组成员
 *
 */
public interface IAppGroupMemberService extends IBaseService<AppGroupMember> {

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
	 * 根据类型和值修改 显示的名称
	 * 
	 * @param type
	 * @param value
	 * @param newShow
	 * @return 受影响行数
	 */
	public int updateShowByValue(String value, String newShow);

	/**
	 * 根据类型和值删除
	 * 
	 * @param type
	 * @param values
	 *            多个用","隔开
	 * @return 受影响行数
	 */
	public int deleteByValuesAndType(String values, String type);

	/**
	 * 通过appids 取得 appgroupmember列表
	 * 
	 * @param appids
	 * @return
	 */
	public List<AppGroupMember> getAppGroupMemberByAppids(List<String> appids);

	/**
	 * @Description 改变拖动的位置
	 * @param
	 * @return
	 */
	public MsgResult changeLocation(Map<String, Object> parm, String classname);

}
