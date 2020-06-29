package org.esy.base.dao;

import java.util.List;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.OrganizationRelation;
import org.esy.base.entity.dto.IdentOrg;

public interface IOrganizationRelationDao extends IBaseDao<OrganizationRelation> {

	public List<OrganizationRelation> listByEid(String eid);

	public List<OrganizationRelation> listByOidAndType(String oid, String type);

	public Boolean deleteByOid(String oid);

	/**
	 * 找出上级组织的路径
	 *
	 * @param oid
	 * @param type
	 * @return
	 */
	public String getPathFromOidAndType(String oid, String type);

	/**
	 * Description:根据oid和eid找出上级组织路径
	 * 
	 * @param oid
	 * @param eid
	 * @return
	 */
	public String getPathFromOid(String oid, String eid);

	/**
	 * 找出同级组织的最大seq
	 *
	 * @param oid
	 * @param type
	 * @return
	 */
	public Integer getMaxSeqFromPidAndType(String pid, String type);

	public Integer getMaxSeqFromPidAndType(String pid, String type, String ppath);

	public boolean isExsitsOid(String path, String pid, String oid);

	/**
	 * 
	 * @param oid
	 * @param eid
	 * @return
	 */
	public List<OrganizationRelation> listByOid(String oid, String eid);

	/**
	 * 
	 * @param oid
	 * @param path
	 * @param eid
	 * @return
	 */
	public boolean deleteByOidPathEid(String oid, String path, String eid);

	/**
	 * 获取单个seq
	 * 
	 * @param eid
	 * @param pid
	 * @param path
	 * @param type
	 * @return
	 */
	public Integer getSeq(String eid, String pid, String path, String type);

	/**
	 * 删除uid
	 * 
	 * @param uid
	 * @return
	 */
	public boolean deleteByUid(String uid);

	/**
	 * 
	 * @param path
	 * @param eid
	 * @return
	 */
	public List<String> listUidByPathEid(List<String> paths, String eid);

	public List<IdentOrg> listOrgsByOids(List<String> oids);

}
