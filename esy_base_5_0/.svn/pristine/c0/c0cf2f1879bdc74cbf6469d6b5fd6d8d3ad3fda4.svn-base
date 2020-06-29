package org.esy.base.service;

import java.util.List;

import org.esy.base.core.IBaseService;
import org.esy.base.entity.OrganizationRelation;

public interface IOrganizationRelationService extends IBaseService<OrganizationRelation> {

	public List<OrganizationRelation> listByEid(String eid);

	public List<OrganizationRelation> listByOidAndType(String oid, String type);

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
	 * 用relation的主键删除
	 * 
	 * @param uid
	 * @return
	 */
	public boolean deleteByUid(String uid);

	/**
	 * uid找出oid
	 * 
	 * @param uid
	 * @return
	 */
	public String getOidFromUid(String uid);

}
