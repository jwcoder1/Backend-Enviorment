package org.esy.base.service.impl;

import java.util.List;
import java.util.Map;

import org.esy.base.core.QueryResult;
import org.esy.base.dao.IOrganizationRelationDao;
import org.esy.base.entity.OrganizationRelation;
import org.esy.base.service.IOrganizationRelationService;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrganizationRelationServiceImpl implements IOrganizationRelationService {

	@Autowired
	private IOrganizationRelationDao organizationrelationDao;

	@Override
	@Transactional
	public OrganizationRelation save(OrganizationRelation o) {
		return organizationrelationDao.save(o);
	}

	@Override
	public OrganizationRelation getByUid(String uid) {
		return organizationrelationDao.getByUid(uid);
	}

	@Override
	@Transactional
	public boolean delete(OrganizationRelation o) {
		return organizationrelationDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return organizationrelationDao.query(parm);
	}

	@Override
	public List<OrganizationRelation> listByEid(String eid) {
		return organizationrelationDao.listByEid(eid);
	}

	@Override
	public List<OrganizationRelation> listByOidAndType(String oid, String type) {
		return organizationrelationDao.listByOidAndType(oid, type);
	}

	@Override
	public List<OrganizationRelation> listByOid(String oid, String eid) {
		return organizationrelationDao.listByOid(oid, eid);
	}

	@Override
	public boolean deleteByOidPathEid(String oid, String path, String eid) {
		return organizationrelationDao.deleteByOidPathEid(oid, path, eid);
	}

	@Override
	@Transactional
	public boolean deleteByUid(String uid) {
		OrganizationRelation o = new OrganizationRelation();
		o.setUid(uid);
		return this.delete(o);
	}

	@Override
	public String getOidFromUid(String uid) {
		OrganizationRelation o = this.getByUid(uid);
		if (YESUtil.isEmpty(o))
			return null;
		return o.getOid();
	}

}
