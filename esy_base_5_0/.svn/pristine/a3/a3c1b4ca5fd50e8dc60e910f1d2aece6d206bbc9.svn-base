package org.esy.base.service;

import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseService;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.GroupMember;

/**
 * 
 * Dao for 群组成员
 *
 */
public interface IGroupMemberService extends IBaseService<GroupMember> {

	/**
	 * 通过gid 删除所有groupmember
	 * 
	 * @param gid
	 * @return
	 */
	public boolean deleteByGid(String gid);

	/**
	 * 根据类型和值修改 显示的名称
	 * 
	 * @param type
	 * @param value
	 * @param newShow
	 * @return 受影响行数
	 */
	public int updateShowByValueAndType(String type, String value, String newShow);

	/**
	 * 根据类型和值删除
	 * 
	 * @param type
	 * @param values
	 *            多个用","隔开
	 * @return 受影响行数
	 */
	public int deleteByValuesAndType(String values, String type);

	public List<GroupMember> searchByGname(String gname, String eid);

	public List<GroupMember> getMembersByUserId();

	/**
	 *
	 * @Description 群组对应的人员
	 * @param parm
	 * @return
	 */
	public QueryResult listGroupPeople(Map<String, Object> parm);

}
