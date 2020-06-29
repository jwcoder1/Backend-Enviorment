package org.esy.base.dao;

import java.util.List;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.Organization;
import org.esy.base.entity.OrganizationRelation;
import org.esy.base.entity.Person;
import org.esy.base.entity.dto.NameValue;

/**
 * 
 * Dao for 组织信息
 *
 */
public interface IOrganizationDao extends IBaseDao<Organization> {

	/**
	 * @Description 返回企业对应的可用组织
	 * @param eid
	 *            企业路径
	 * @return Organization列表
	 */
	public List<Organization> listByEid(String eid);

	/**
	 * @Description 删除本节点以下节点（包含本节点）
	 * @param uid
	 *            本节点的uid
	 * @return
	 */
	public boolean deleteTreeByUid(List<String> uids);

	/**
	 * @Description 查找本节点以下节点（包含本节点）
	 * @param 本节点的uid
	 * @return
	 */
	public List<String> listTreeByUid(String uid);

	/**
	 * @Description 返回父节点对应的子节点
	 * @param eid
	 *            企业路径
	 * @return Organization列表
	 */
	public List<Organization> listByPid(String uid);

	/**
	 * @Description 返回父节点对应的子节点
	 * @param eid
	 *            企业路径
	 * @return Organization ids列表
	 */
	public List<String> listUidsByPid(String uid);

	/**
	 * @Description 根据oid查找
	 * @param oid
	 * @return Organization
	 */
	public Organization getByOid(String oid);

	/**
	 * @Description 返回父节点对应的子节点(包含自己)
	 * @param path
	 *            企业路径
	 * @return Organization列表
	 */
	public List<Organization> listByPath(String path);

	/**
	 * @Description 返回父节点对应的子节点(包含自己) 的oids
	 * @param path
	 *            企业路径
	 * @return oids
	 */
	public List<String> listOidsByPath(String path);

	/**
	 * 
	 * @Description
	 * @param
	 * @return
	 */
	public boolean deleteByOids(List<String> oids);

	/**
	 * @Description 节点下有孩子
	 * @param
	 * @return
	 */
	public boolean hadChild(String path, String eid, String oid);

	/**
	 * @Description 本节点有职务
	 * @param
	 * @return
	 */
	public boolean hadPosition(String oid);

	/**
	 * @Description 本节点有岗位
	 * @param
	 * @return
	 */
	public boolean hadPost(String oid);

	/**
	 * @Description 本节点有身份
	 * @param
	 * @return
	 */
	public boolean hadIdentity(String oid);

	/**
	 * @Description 本节点的path
	 * @param
	 * @return
	 */
	public String getPathFromOid(String oid);

	/**
	 * @Description 获取企业下的所有根节点的组织
	 * @param
	 * @return
	 */
	public List<Organization> getRootByEid(String eid);

	/**
	 * 
	 * @Description 从组织找到企业路径eid
	 * @param oid组织id
	 * @return
	 */
	public String getEidFromOid(String oid);

	/**
	 * 
	 * @Description 检查保存
	 * @param uid存在为修改，不存在为新增
	 * @return
	 */
	public Boolean checkOrgForSave(String eid, String oid, String name, String uid);

	/**
	 * @Description 取得最大的seq值
	 * @param eid企业路径
	 * @param pid上级编号
	 * @return
	 */
	public Integer getMaxSeq(String eid, String pid);

	/**
	 * @Description 取得path
	 * @param eid企业路径
	 * @param pid上级编号
	 * @return
	 */
	public String getPath(String eid, String oid);

	/**
	 * @Description 从person 抓出他身份所属的组织的path列表(包括子组织)
	 * @param Person
	 * @return
	 */
	public List<String> listPathByPerson(Person p);

	/**
	 * @Description 从path列表抓出所有的组织
	 * @param paths
	 * @param eid
	 * @return oids
	 */
	public List<String> listOidsByPaths(List<String> paths, String eid);

	/**
	 * @Description 查找本节点以下节点pid（包含本节点）
	 * @param 本节点的path
	 * @return
	 */
	public List<String> listChildNodesOidByPath(String path, String eid);

	/**
	 * 企业的所有树的根节点
	 * 
	 * @param eid-企业eid
	 * @return
	 */
	public List<NameValue> listRootNames(String eid);

	/**
	 * 企业的点的根节点
	 * 
	 * @param eid-企业eid
	 * @return
	 */
	public OrganizationRelation getRootName(String eid, String oid);

	/**
	 * 所有的父节点 和根节点
	 * 
	 * @param eid
	 * @param oid
	 * @return
	 */
	public List<OrganizationRelation> listParentNodes(String eid, String oid);

	/**
	 * 
	 * @return
	 */
	public boolean exsitsInEid(String eid);

	/**
	 * 用oid查找
	 * 
	 * @param oid
	 * @param eid
	 * @return
	 */
	public Organization getByOidAndEid(String oid, String eid);

	/**
	 * 鑫叶查找tmpid
	 * 
	 * @param tmpid
	 * @param eid
	 * @return
	 */
	public Organization getByTmpid(String tmpid, String eid);

	/**
	 * 鑫叶只查找出一个组织树节点,type为鑫叶数据迁移类型
	 * 
	 * @param oid
	 * @param eid
	 * @return
	 */
	public OrganizationRelation getSingleRelationByOid(String oid, String eid);

}
