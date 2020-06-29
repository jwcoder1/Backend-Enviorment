package org.esy.base.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IAttachmentDao;
import org.esy.base.entity.AppGroupMember;
import org.esy.base.entity.Attachment;
import org.esy.base.entity.dto.NameValue;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

/**
 * @author 颜惠强 2015-08-14
 */
@Repository
public class AttachmentDaoImpl implements IAttachmentDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Attachment save(Attachment o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			em.persist(o);
		} else {
			em.merge(o);
		}
		return o;
	}

	public Attachment getByUid(String uid) {
		return this.em.find(Attachment.class, uid);
	}

	public boolean delete(Attachment o) {
		String hql = "delete Attachment where uid='" + o.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	public QueryResult query(Map<String, Object> parm) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> searchByAttId(String attachmentId) {
		String hql = "from Attachment a where a.attachmentId='" + StringEscapeUtils.escapeSql(attachmentId)
				+ "' and a.status='2' order by a.created";
		return (List<Attachment>) em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Attachment searchOneByAttId(String attachmentId) {
		String hql = "from Attachment a where a.attachmentId='" + StringEscapeUtils.escapeSql(attachmentId)
				+ "' and a.status='2' order by a.created  ";
		Query q = em.createQuery(hql);
		q.setFirstResult(0);
		q.setMaxResults(1);
		List<Attachment> ret = q.getResultList();
		return YESUtil.isEmpty(ret) ? null : ret.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Attachment> search(String entityUid) {
		String hql = "from Attachment a where a.entityUid='" + StringEscapeUtils.escapeSql(entityUid) + "'";
		return (List<Attachment>) em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Attachment> getByEntityUid(String entityUid) {
		String hql = "from Attachment a where a.entityUid='" + StringEscapeUtils.escapeSql(entityUid) + "'";
		return (List<Attachment>) em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Attachment> getAttachmentsByDate(String now) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(now);
			String hql = "from Attachment a where a.expired <=:expired and a.status!='2'";
			Query q = em.createQuery(hql).setParameter("expired", date);
			return (List<Attachment>) q.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Attachment> getByAttachmentId(String attachmentId) {
		String hql = "from Attachment a where a.attachmentId='" + StringEscapeUtils.escapeSql(attachmentId)
				+ "' order by created";
		return (List<Attachment>) em.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Attachment> getAttachments() {
		String hql = "from Attachment a where a.entityUid is null and a.entityName is null and a.fieldName is null";
		Query q = em.createQuery(hql);
		return (List<Attachment>) q.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Attachment> getByAttIdByStatus(String attId, String status) {
		String hql = "from Attachment a where a.attachmentId='" + StringEscapeUtils.escapeSql(attId) + "'";
		if (!status.equals("99")) {
			hql += " and a.status='" + status + "'";
		}
		return (List<Attachment>) em.createQuery(hql).getResultList();
	}

	@Override
	public boolean deleteByAttId(String attId) {
		String hql = "delete Attachment where attachmentId='" + attId + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getUrlFromAttId(String attId) {
		String hql = "select  CONCAT(a.filePath, '/', a.fileName)  from  Attachment a  where a.attachmentId=:attachmentId ";
		List<String> ls = em.createQuery(hql).setParameter("attachmentId", attId).getResultList();
		return YESUtil.isEmpty(ls) ? null : ls.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NameValue> listUrlFromAttId(List<String> attIds) {
		if (YESUtil.isEmpty(attIds))
			return null;
		String hql = "select  new   org.esy.base.entity.dto.NameValue(a.attachmentId ,CONCAT(a.filePath, '/', a.fileName))   from  Attachment a  where a.attachmentId in ("
				+ YESUtil.lisToSqlStr(attIds) + " )";
		List<NameValue> ls = em.createQuery(hql).getResultList();
		return ls;
	}

}
