package org.esy.base.dao;

import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseDao;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.Person;

/**
 * 
 * Dao for 人员信息
 *
 */
public interface IPersonDao extends IBaseDao<Person> {

	/**
	 * 
	 * @Description 职工号是否存在，防止职工号重复
	 * @param
	 * @return
	 */
	public boolean hadInEmployeeNo(String employeeNo, String uid);

	/**
	 * @Description pid+eid查找person
	 * @param pid
	 * @param eid
	 * @return
	 */
	public Person getByPidAndEid(String pid, String eid);

	/**
	 * 
	 * @Description 人员选择器查询
	 * @param
	 * @return
	 */
	public Map<String, Object> search(Map<String, Object> parm);

	/**
	 * 
	 * @Description 人员选择器，部门人数统计
	 * @param
	 * @return
	 */
	public Map<String, Object> getOrgCount(Map<String, Object> parm);

	/**
	 * 
	 * @Description 用oids+eid找出人员,过滤重复
	 * @param
	 * @return
	 */
	public QueryResult listByOidsAndEid(List<String> oids, String eid);

	/**
	 * @Description pid查找person
	 * @param pid
	 * @param eid
	 * @return
	 */
	public Person getByPid(String pid);

	/**
	 * 所有人的uid
	 * 
	 * @return
	 */
	public List<String> listAllUid();

	/**
	 * temp 临时用
	 */
	public QueryResult query2(Map<String, Object> parm);

	/**
	 * 从职工号找
	 */
	public Person getByStaffno(String staffno, String eid);

	/**
	 * 查找人员是否存在
	 * 
	 * @param pid
	 * @param employeeNo
	 * @param eid
	 * @return
	 */
	public boolean findByEmployeeNo(String pid, String employeeNo, String eid);

}
