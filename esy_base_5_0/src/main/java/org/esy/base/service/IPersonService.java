package org.esy.base.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.IBaseService;
import org.esy.base.core.QueryResult;
import org.esy.base.entity.Identity;
import org.esy.base.entity.Person;
import org.esy.base.entity.dto.IdentOrg;
import org.esy.base.entity.pojo.MsgResult;

/**
 * 
 * Dao for 人员信息
 *
 */
public interface IPersonService extends IBaseService<Person> {

	/**
	 * @Description 同时保存Person o和identitys ,old 暂不用
	 * @param Person
	 *            o 人员
	 * @param Person
	 *            old 人员旧有
	 * @param List<Identity>
	 *            identitys 身份
	 * @return
	 */
	public Person save(Person o, Person old, List<Identity> identitys, HttpServletRequest request);

	/**
	 * @Description 检查是否可以保存
	 * @param Person
	 * @return
	 */
	public MsgResult checkForSave(Person p);

	/**
	 * @Description pid+eid查找person
	 * @param pid
	 * @param eid
	 * @return
	 */
	public Person getByPidAndEid(String pid, String eid);

	/**
	 * @Description pid查找person
	 * @param pid
	 * @param eid
	 * @return
	 */
	public Person getByPid(String pid);

	/**
	 * 
	 * @Description uim.api接口的findpeople
	 * @param map.parm
	 * @return
	 */
	public QueryResult findpeople(Map<String, Object> parm);

	public Map<String, Object> search(Map<String, Object> parm);

	public Map<String, Object> getOrgCount(Map<String, Object> parm);

	/**
	 * 
	 * @Description 找出人员的所属组织列表(包括子组织)下的所有人
	 * @param Person
	 * @return
	 */
	public QueryResult listByPersonOfOrganization(Person p);

	/**
	 * 修正所有人员的拼音码
	 * 
	 * @return
	 */
	public boolean fixPersonPy();

	/**
	 * 
	 * @param pid
	 * @param employeeNo
	 * @param eid
	 * @return
	 */
	public boolean checkForEmployeeNo(String pid, String employeeNo, String eid);

}
