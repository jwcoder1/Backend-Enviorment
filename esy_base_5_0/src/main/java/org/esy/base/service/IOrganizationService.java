package org.esy.base.service;

import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseService;
import org.esy.base.entity.Organization;
import org.esy.base.entity.OrganizationRelation;
import org.esy.base.entity.Person;
import org.esy.base.entity.Position;
import org.esy.base.entity.Post;
import org.esy.base.entity.dto.IdentOrg;
import org.esy.base.entity.dto.NameValue;
import org.esy.base.entity.pojo.MsgResult;

/**
 * 
 * Dao for 组织信息
 *
 */
public interface IOrganizationService extends IBaseService<Organization> {

	/**
	 * @Description 返回企业对应的可用组织
	 * @param eid
	 *            企业路径
	 * @return Organization列表
	 */
	public List<Organization> listByEid(String eid);

	/**
	 * 根据oid查找
	 * 
	 * @param oid
	 * @return
	 */
	public Organization getByOid(String oid);

	/**
	 * @Description 删除本节点以下节点（包含本节点）
	 * @param uid
	 *            本节点的uid
	 * @return
	 */
	public boolean deleteTreeByUid(String uid);

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
	 * @Description 检查是否可删
	 * @param uid节点
	 * @return
	 */
	public MsgResult checkDeleteByUid(Map<String, Object> parm);

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
	 * @Description 检查参数是否符合保存要求
	 * @param
	 * @return
	 */
	public MsgResult checkSave(Organization o);

	/**
	 * 
	 * @Description 从组织找到企业路径eid
	 * @param oid组织id
	 * @return
	 */
	public String getEidFromOid(String oid);

	/**
	 * @Description 改变拖动的位置
	 * @param
	 * @return
	 */
	public MsgResult changeLocation(Map<String, Object> parm, String classname);

	/**
	 * @Description 从person 抓出他身份所属的组织的oid列表(包括子组织)
	 * @param Person
	 * @return
	 */
	public List<String> listOidsByPerson(Person p);

	/**
	 * @Description 查找本节点以下节点pid（包含本节点）
	 * @param 本节点的path
	 * @return
	 */
	public List<String> listChildNodesOidByPath(String path, String eid);

	// 该组织下所有的可用职位
	public List<Position> listPositionsByOid(String oid, String path, Boolean enable, String rootpid, String eid);

	// 该组织下所有的可用岗位
	public List<Post> listPostsByOid(String oid, String path, Boolean enable, String rootpid, String eid);

	/**
	 * 从父组织，找出所有子组织，包括他自己
	 * 
	 * @return
	 */
	public List<String> listOidsByParentOid(String oid);

	/**
	 * 企业的所有树的根节点
	 * 
	 * @param eid-企业eid
	 * @return
	 */
	public List<NameValue> listRootNames(String eid);

	/**
	 * 所有的父节点 和根节点
	 * 
	 * @param eid
	 * @param oid
	 * @return
	 */
	public List<OrganizationRelation> listParentNodes(String eid, String oid);

	/**
	 * 检查组织关系的保存
	 * 
	 * @param o
	 * @return
	 */
	public MsgResult checkforsaverelation(Organization o);

	/**
	 * 保存关系
	 * 
	 * @param o
	 * @return
	 */
	public Organization saveForRelation(Organization o);

	/**
	 * 更新单表
	 * 
	 * @param o
	 * @return
	 */
	public Organization update(Organization o);

	/**
	 * 删除关系，只有一条关系时，删除组织
	 * 
	 * @param parm
	 * @return
	 */
	public MsgResult deletelByRelation(Map<String, Object> parm);

	/**
	 * 从组织关系表orangizationrelation的uid中找出他的下级组织
	 * 
	 * @param uid
	 * @return
	 */
	public String listChildrenOidsByRelationUid(String uid);

}
