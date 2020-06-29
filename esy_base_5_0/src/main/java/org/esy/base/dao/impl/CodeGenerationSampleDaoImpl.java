package org.esy.base.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.apache.commons.lang.StringEscapeUtils;
import org.esy.base.core.Condition;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.ICodeGenerationSampleDao;
import org.esy.base.entity.CodeGenerationSample;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;

/**
 * 
 * Dao implement for 自动代码生成范例
 *
 */
@Repository
public class CodeGenerationSampleDaoImpl implements ICodeGenerationSampleDao {

	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public CodeGenerationSample getEntityById(String id) { 
		return null;
			}
	
	@Override
	public int getEntityByNumber(int parm1, int parm2) { 
		return 0;
			}
	
	
	@Override
	public CodeGenerationSample save(CodeGenerationSample o) {
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			this.em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		return o;
			}

	@Override
	public CodeGenerationSample getByUid(String uid) {
		return this.em.find(CodeGenerationSample.class, uid);
	}

	@Override
	public boolean delete(CodeGenerationSample o) {
		String hql = "delete CodeGenerationSample where uid='" + o.getUid() + "'";
		Query q = em.createQuery(hql);
		int updateCount = q.executeUpdate();
		if (updateCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public QueryResult query(Map<String, Object> parm) {

		QueryResult qr = new QueryResult();

		Map<String, Condition> conditions = QueryUtils.getQueryData(parm);

		try {
			int count = YESUtil.objtoint(parm.get("count"));
			int start = YESUtil.objtoint(parm.get("start"));
			String order = YESUtil.toString(parm.get("order"));

			String hql = "from CodeGenerationSample a where 1=1";

			String whereStr = "";

			
			Condition stringValue = conditions.get("stringValue"); //字符型变量
			if (stringValue != null) {
				if (stringValue.getConditions().get("eq,match") != null) {
				}
			}
			
			Condition pictureValue = conditions.get("pictureValue"); //图片变量
			if (pictureValue != null) {
				if (pictureValue.getConditions().get("eq,match") != null) {
				}
			}
			
			Condition integerValue = conditions.get("integerValue"); //整型变量
			if (integerValue != null) {
				if (integerValue.getConditions().get("gt,gte,lt,lte,eq") != null) {
				}
			}
			
			Condition booleanValue = conditions.get("booleanValue"); //逻辑型变量
			if (booleanValue != null) {
				if (booleanValue.getConditions().get("eq") != null) {
							whereStr += " and a.booleanValue = " + StringEscapeUtils.escapeSql(booleanValue.getConditions().get("eq"));
				}
			}
			
			Condition datetimeValue = conditions.get("datetimeValue"); //日期时间型变量
			if (datetimeValue != null) {
				if (datetimeValue.getConditions().get("gte,lte") != null) {
				}
			}

			if (!"".equals(whereStr)) {
				hql += whereStr;
			}

			String orderStr = "";
				orderStr = " order by a.integerValue asc, a.doubleValue desc";
			
					if (order.equals("stringValue")) {
						orderStr = " order by a.stringValue asc, a.textValue desc";
					}
					
					if (order.equals("textValue")) {
						orderStr = " order by a.stringValue desc";
					}
					
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
			qr.setHeaders(QueryUtils.getClassFieldInfo(CodeGenerationSample.class));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return qr;
	}
}
