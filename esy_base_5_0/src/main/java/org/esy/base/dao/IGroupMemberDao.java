package org.esy.base.dao;

import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseDao;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.GroupMember;

/**
 * 
 * Dao for 群组成员
 *
 */
public interface IGroupMemberDao extends IBaseDao<GroupMember> {

	/**
	 * 通过gid 删除所有groupmember
	 * 
	 * @param gid
	 * @return
	 */
	public boolean deleteByGid(String gid);

	/**
	 * 根据类型和值删除
	 * 
	 * @param type
	 * @param values
	 * @return 受影响行数
	 */
	public int deleteByValuesAndType(String[] values, String type);

	/**
	 * 根据values 和gid 和type 取得groupmember列表
	 * 
	 * @param values
	 * @param gid
	 * @param type
	 * @return groupmember列表
	 */
	public List<GroupMember> getGroupMemberByValuesAndTypeAndGid(String[] values, String gid, String type);

	/**
	 * 根据类型和值修改 显示的名称
	 * 
	 * @param type
	 * @param value
	 * @param newShow
	 * @return 受影响行数
	 */
	public int updateShowByValueAndType(String type, String value, String newShow);

	public List<GroupMember> searchByGname(String gname, String eid);

	public List<GroupMember> getMembersByUserId();

	/**
	 * 
	 * @Description list by gid
	 * @param gid
	 * @return
	 */
	public List<GroupMember> listByGid(String gid);

	/**
	 *
	 * @Description 群组对应的人员
	 * @param parm
	 * @return
	 */
	public QueryResult listGroupPeople(Map<String, Object> parm, String hql);
}
