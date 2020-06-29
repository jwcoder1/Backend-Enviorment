package org.esy.base.dao;

import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.Identity;

/**
 * 
 * Dao for 身份信息
 *
 */
public interface IIdentityDao extends IBaseDao<Identity> {

	/**
	 * @Description 传入组织oids，判断是否有人员属于这些组织
	 * @param 组织oids
	 * @return boolean
	 */
	public boolean exsitsInOids(List<String> oids);

	/**
	 * @Description 是否存在相关联的资料
	 * @param oid
	 *            组织id
	 * @param positionId
	 *            职务id
	 * @param postId
	 *            岗位id
	 * @return
	 */
	public boolean hadInConditions(Object... obj);

	/**
	 * 根据pid取到改用户所有的身份
	 * 
	 * @param pid
	 * @return
	 */
	public List<Identity> getByPid(String pid);

	/**
	 * @Description 根据人员编号删除
	 * @param pid
	 * @return
	 */
	public boolean deleteByPid(String pid);

	/**
	 * @Description 根据人员编号删除
	 * @param pid
	 * @return
	 */
	public boolean deleteByPidAndEid(String pid, String eid);

	/**
	 * @Description 用用户pid查找身份的岗位名称
	 * @param
	 * @return
	 */
	public List<String> listPostNameByPid(String pid);

	/**
	 * @Description 用用户pid查找身份的职位名称
	 * @param
	 * @return
	 */
	public List<String> listPositionNameByPid(String pid);

	/**
	 * @Description 用用户pid查找身份的组织名称
	 * @param
	 * @return
	 */
	public List<String> listOrgNameByPid(String pid);

	/**
	 * 根据pid取到改用户所有的身份，且enable=true
	 * 
	 * @param pid
	 * @return
	 */
	public List<Identity> getByPidAndEnable(String pid);

	/**
	 * 用户的所有组织
	 * 
	 * @param pid
	 * @return
	 */
	public List<String> listOidByPid(String pid);

	/**
	 * 企业中是否有人员
	 * 
	 * @param eid
	 */
	public boolean exsitsInEid(String eid);

	public List<Identity> listtest();

	public List<Map<String, String>> listPositionIdNameByPid(String pid);

	/**
	 * Description:根据pid和oid获取唯一身份
	 * 
	 * @param pid
	 * @param oid
	 * @return
	 */
	public Identity getByPidAndOid(String pid, String oid);

	/**
	 * Description:根据pid修改身份的enable和isMain
	 * 
	 * @param pid
	 * @return
	 */
	public boolean moveMainBypid(String pid);

}
