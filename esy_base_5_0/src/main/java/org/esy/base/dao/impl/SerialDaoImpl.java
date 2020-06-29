package org.esy.base.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.ISerialDao;
import org.esy.base.entity.Serial;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

@Repository
public class SerialDaoImpl implements ISerialDao {

	@PersistenceContext
	private EntityManager em;

	public Serial save(Serial o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			this.em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
	}

	public Serial getByUid(String uid) {
		return this.em.find(Serial.class, uid);
	}

	public boolean delete(Serial o) {
		String hql = "delete Serial where uid='" + o.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	public QueryResult query(Map<String, Object> parm) {

		QueryResult qr = new QueryResult();

		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);

		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));

			String hql = "from Serial a where 1=1";

			String whereStr = "";

			Condition tableName = conditions.get("tableName"); //模块名
			if (tableName != null) {
				if (tableName.getConditions().get("eq") != null) {
					whereStr += " and a.tableName = '"
							+ StringEscapeUtils.escapeSql(tableName.getConditions().get("eq")) + "'";
				}
			}

			Condition fieldName = conditions.get("fieldName"); //实体类
			if (fieldName != null) {
				if (fieldName.getConditions().get("eq") != null) {
					whereStr += " and a.fieldName = '"
							+ StringEscapeUtils.escapeSql(fieldName.getConditions().get("eq")) + "'";
				}
			}

			Condition serialKey = conditions.get("serialKey"); //业务号
			if (serialKey != null) {
				if (serialKey.getConditions().get("eq") != null) {
					whereStr += " and a.serialKey = '"
							+ StringEscapeUtils.escapeSql(serialKey.getConditions().get("eq")) + "'";
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = " order by a.tableName, a.fieldName, a.serialKey";

			Query q = em.createQuery("select count(uid) " + hql);
			Long records = (Long) q.getSingleResult();
			qr.setCount(records);
			
			if (!"".equals(orderStr)) {
				hql += orderStr;
			}

			q = em.createQuery(hql);
			if (start > 0) {
				q.setFirstResult(start);
			}
			if (count > 0) {
				q.setMaxResults(count);
			} else {
				q.setMaxResults(20);
			}
			qr.setItems(q.getResultList());
			qr.setHeaders(QueryUtils.getClassFieldInfo(Serial.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}

	@Override
	public Serial getSerial(String moduleName, String entityName, String serialKey) {

		Serial serial = null;
		if ("".equals(moduleName) || "".equals(entityName)) {
			return null;
		}

		String hql = "from Serial a where a.moduleName = '" + StringEscapeUtils.escapeSql(moduleName) + "'";
		hql += " and a.entityName = '" + StringEscapeUtils.escapeSql(entityName) + "'";
		hql += " and a.serialKey = '" + StringEscapeUtils.escapeSql(serialKey) + "'";
		Query q = em.createQuery(hql);
		try {
			serial = (Serial) q.getSingleResult();
		} catch (Exception e) {

		}

		if (serial == null) {
			serial = new Serial(moduleName, entityName, serialKey, 0l);
		}

		return serial;
	}

}
