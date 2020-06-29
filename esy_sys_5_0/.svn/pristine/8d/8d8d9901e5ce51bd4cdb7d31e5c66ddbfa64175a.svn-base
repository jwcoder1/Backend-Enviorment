package org.esy.base.dao.core;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.poi.ss.formula.functions.T;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.common.BaseUtil;
import org.esy.base.util.QueryUtils;
import org.esy.base.util.YESUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * 
 * 分页查询封装实现类
 *
 * @author cx
 * @date 2016年5月12日 下午6:35:12
 * @version v1.0
 */
@Scope("prototype")
@Component
public class PageQuery {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	private ThreadLocal<StringBuilder> hqlBuilder = new ThreadLocal<StringBuilder>();

	private ThreadLocal<StringBuilder> group = new ThreadLocal<StringBuilder>();

	private ThreadLocal<StringBuilder> select = new ThreadLocal<StringBuilder>();

	private ThreadLocal<StringBuilder> where = new ThreadLocal<StringBuilder>();

	private ThreadLocal<List<String>> params = new ThreadLocal<List<String>>();

	private ThreadLocal<List<Object>> values = new ThreadLocal<List<Object>>();

	private ThreadLocal<List<String>> paramsList = new ThreadLocal<List<String>>();

	private ThreadLocal<ArrayList<Collection<Object>>> valuesList = new ThreadLocal<ArrayList<Collection<Object>>>();

	private ThreadLocal<List<String>> paramsArray = new ThreadLocal<List<String>>();

	private ThreadLocal<List<Object[]>> valuesArray = new ThreadLocal<List<Object[]>>();

	private final static String FS_SPACE = " ";

	private final static String FS_ORDERBY = " ORDER BY ";

	/**
	 * 
	 * 添加hql
	 * 
	 * @author cx 2016年5月12日 下午4:28:06
	 * @param hql
	 * @return
	 */
	public PageQuery append(String hql) {
		getHqlBuilder().append(hql);
		if (hql.endsWith(",") || hql.endsWith("(")) {
			return this;
		}
		getHqlBuilder().append(FS_SPACE);
		return this;
	}

	/**
	 * 
	 * 添加group by
	 * 
	 * @author 王一凡 2017年7月12日 下午4:28:06
	 * @param hql
	 * @return
	 */
	public PageQuery appendGroup(String hql) {

		if (BaseUtil.isEmpty(getGroup().toString())) {
			if (hql.trim().toLowerCase().indexOf("group by") == -1) {
				hql = "group by " + hql;
			}
		}

		getGroup().append(FS_SPACE);
		getGroup().append(hql);
		getGroup().append(FS_SPACE);
		return this;
	}

	/**
	 * 
	 * 添加group by
	 * 
	 * @author 王一凡 2017年7月12日 下午4:28:06
	 * @param hql
	 * @return
	 */
	public PageQuery appendSelect(String hql) {
		if (BaseUtil.isEmpty(getSelect().toString())) {
			if (hql.trim().toLowerCase().indexOf("select") == -1) {
				getSelect().append("select");
				getSelect().append(FS_SPACE);
			}
		}
		getSelect().append(FS_SPACE);
		getSelect().append(hql);
		getSelect().append(FS_SPACE);
		return this;
	}

	/**
	 * 
	 * 添加where条件
	 * 
	 * @author 黄对清 2017年7月12日 下午4:28:06
	 * @param hql
	 * @return
	 */
	public PageQuery appendWhere(String hql) {
		if (BaseUtil.isEmpty(getWhere().toString())) {
			if (!hql.trim().toLowerCase().substring(0, 5).equals("where")) {
				getWhere().append(FS_SPACE);
				getWhere().append("where");
				getWhere().append(FS_SPACE);
				if (hql.trim().substring(0, 4).equals("and ") || hql.trim().substring(0, 4).equals("or ")) {
					hql = hql.substring(4);
				}
			}
		}
		getWhere().append(FS_SPACE);
		getWhere().append(hql);
		getWhere().append(FS_SPACE);
		return this;
	}

	/**
	 * 
	 * 去左右空格
	 * 
	 * @author cx 2016年5月15日 下午5:35:09
	 * @return
	 */
	public PageQuery trim() {
		String str = getHqlBuilder().toString().trim();
		getHqlBuilder().setLength(0);
		getHqlBuilder().append(str);
		return this;
	}

	/**
	 * 
	 * 设置参数
	 * 
	 * @author cx 2016年5月12日 下午4:29:14
	 * @param name
	 * @param value
	 * @return
	 * @see Query#setParameter(String, Object)
	 */
	public PageQuery setParam(String name, Object value) {
		getParams().add(name);
		getValues().add(value);
		return this;
	}

	/**
	 * 设置参数。与Query接口一致。
	 * 
	 * @author cx 2016年5月12日 下午4:29:14
	 * @param paramMap
	 * @return
	 * @see Query#setProperties(Map)
	 */
	public PageQuery setParams(Map<String, Object> paramMap) {
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			setParam(entry.getKey(), entry.getValue());
		}
		return this;
	}

	/**
	 * 设置参数。与Query接口一致。
	 * 
	 * @author cx 2016年5月12日 下午4:29:14
	 * @param name
	 * @param vals
	 * @return
	 * @see Query#setParameterList(String, Collection)
	 */
	public PageQuery setParamList(String name, Collection<Object> vals) {
		getParamsList().add(name);
		getValuesList().add(vals);
		return this;
	}

	/**
	 * 设置参数。与Query接口一致。
	 * 
	 * @author cx 2016年5月12日 下午4:29:14
	 * @param name
	 * @param vals
	 * @return
	 * @see Query#setParameterList(String, Object[])
	 */
	public PageQuery setParamList(String name, Object[] vals) {
		getParamsArray().add(name);
		getValuesArray().add(vals);
		return this;
	}

	/**
	 * 
	 * @Title: 分页查询
	 * @Description: 分面查询
	 * @author chen xin
	 * @param <T>
	 * @param page
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @param records
	 *            总记录数
	 * 
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> Page<T> getPage(Pageable pageable, Class<T> destinationClass, Object entity) {

		analysisQuery(entity);
		return getPage(pageable, destinationClass);

	}

	/**
	 * 
	 * @Title: 分页查询
	 * @Description: 分面查询
	 * @author chen xin
	 * @param <T>
	 * @param page
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @param records
	 *            总记录数
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public <T> Page<T> getPage(Pageable pageable, Class<T> destinationClass) {

		Object content = null;
		long total = 0l;
		ScrollableResults scrollableResults = null;
		StatelessSession session = getSession();
		try {
			String hql = getOrigHql();
			// 增加where 黄对清
			if (YESUtil.isNotEmpty(getWhere().toString())) {
				hql += getWhere().toString();
			}
			// 增加group by 王一凡
			if (YESUtil.isNotEmpty(getGroup().toString())) {
				hql += getGroup().toString();
			}

			Query tquery = session.createQuery("select count(1) " + hql);
			setParamsToQuery(tquery);
			if (YESUtil.isEmpty(getGroup().toString())) {
				total = (Long) tquery.setFirstResult(0).setMaxResults(1).list().get(0);
			} else {
				total = ((Integer) tquery.list().size()).longValue();
				//				if (destinationClass.getName().equals("org.esy.ord.entity.Chipgroupmoq")) {
				//					total = 1;
				//				} else {
				//					total = tquery.list().size();
				//				}
			}

			// 增加select 王一凡
			if (YESUtil.isNotEmpty(getSelect().toString())) {
				hql = getSelect().toString() + hql;
			}

			hql += getOrderStr(pageable);

			Query query = session.createQuery(hql);
			setParamsToQuery(query);

			if (total > 0) {
				content = query.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
						.setMaxResults(pageable.getPageSize()).list();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (scrollableResults != null) {
				scrollableResults.close();
			}
			session.close();
			clean();
		}

		return new PageResult<T>(BaseUtil.isEmpty(content) ? new ArrayList<T>() : (ArrayList<T>) content, pageable,
				total);
	}

	public <T> Object getSingleResult(Class<T> destinationClass, Object entity) {
		analysisQuery(entity);
		return getSingleResult(destinationClass);
	}

	public <T> Object getSingleResult(Class<T> destinationClass) {
		Object content = null;
		StatelessSession session = getSession();
		try {
			String hql = getOrigHql();
			// 增加where 黄对清
			if (YESUtil.isNotEmpty(getWhere().toString())) {
				hql += getWhere().toString();
			}
			// 增加group by 王一凡
			if (YESUtil.isNotEmpty(getGroup().toString())) {
				hql += getGroup().toString();
			}

			// 增加select 王一凡
			if (YESUtil.isNotEmpty(getSelect().toString())) {
				hql = getSelect().toString() + hql;
			}

			Query query = session.createQuery(hql);
			setParamsToQuery(query);
			content = query.uniqueResult();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {

			session.close();
			clean();
		}

		return content;
	}

	public <T> List<T> getResultList(Class<T> destinationClass, Object entity, Pageable pageable) {

		analysisQuery(entity);

		return getResultList(pageable, destinationClass);

	}

	@SuppressWarnings({ "unchecked", "unused" })
	public <T> List<T> getResultList(Pageable pageable, Class<T> destinationClass) {
		Object content = null;
		StatelessSession session = getSession();
		try {
			String hql = getOrigHql();
			// 增加where 黄对清
			if (YESUtil.isNotEmpty(getWhere().toString())) {
				hql += getWhere().toString();
			}

			// 增加group by 王一凡
			if (YESUtil.isNotEmpty(getGroup().toString())) {
				hql += getGroup().toString();
			}

			hql += getOrderStr(pageable);
			// 增加select 王一凡
			if (YESUtil.isNotEmpty(getSelect().toString())) {
				hql = getSelect().toString() + hql;
			}

			Query query = session.createQuery(hql);

			setParamsToQuery(query);
			content = query.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
					.setMaxResults(pageable.getPageSize()).list();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {

			session.close();
			clean();
		}

		return BaseUtil.isEmpty(content) ? new ArrayList<T>() : (ArrayList<T>) content;
	}

	public <T> List<T> getResultList(Class<T> destinationClass, Object entity) {
		analysisQuery(entity);
		return getResultList(destinationClass);

	}

	public <T> List<T> getResultList(Class<T> destinationClass) {
		Object content = null;
		StatelessSession session = getSession();
		try {
			String hql = getOrigHql();
			// 增加where 黄对清
			if (YESUtil.isNotEmpty(getWhere().toString())) {
				hql += getWhere().toString();
			}

			// 增加group by 王一凡
			if (YESUtil.isNotEmpty(getGroup().toString())) {
				hql += getGroup().toString();
			}

			// 增加select 王一凡
			if (YESUtil.isNotEmpty(getSelect().toString())) {
				hql = getSelect().toString() + hql;
			}

			Query query = session.createQuery(hql);

			setParamsToQuery(query);
			content = query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
			clean();
		}

		return BaseUtil.isEmpty(content) ? new ArrayList<T>() : (ArrayList<T>) content;
	}

	public <T> Object getNativeSingleResult(Class<T> destinationClass, Object entity) {
		analysisQuery(entity);
		return getNativeSingleResult(destinationClass);
	}

	public <T> Object getNativeSingleResult(Class<T> destinationClass) {
		Object content = null;
		StatelessSession session = getSession();
		try {
			String hql = getOrigHql();
			// 增加where 黄对清
			if (YESUtil.isNotEmpty(getWhere().toString())) {
				hql += getWhere().toString();
			}
			// 增加group by 王一凡
			if (YESUtil.isNotEmpty(getGroup().toString())) {
				hql += getGroup().toString();
			}

			// 增加select 王一凡
			if (YESUtil.isNotEmpty(getSelect().toString())) {
				hql = getSelect().toString() + hql;
			}

			Query query = session.createSQLQuery(hql);
			setParamsToQuery(query);
			content = query.uniqueResult();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {

			session.close();
			clean();
		}

		return content;
	}

	/**
	 * 
	 * @Title: 分页查询
	 * @Description: 分面查询
	 * @author chen xin
	 * @param <T>
	 * @param page
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @param records
	 *            总记录数
	 * 
	 * @return
	 */

	@SuppressWarnings("hiding")
	public <T> Page<T> getNativePage(Pageable pageable, Class<T> destinationClass, Object entity) {

		analysisQuery(entity);

		return getNativePage(pageable, destinationClass);

	}

	/**
	 * 
	 * @Title: 分页查询
	 * @Description: 分面查询
	 * @author duiqing.huang
	 * @param <T>
	 * @param page
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @param records
	 *            总记录数
	 * 
	 * @return
	 */
	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> Page<T> getNativePage(Pageable pageable, Class<T> destinationClass) {

		Object content = null;
		long total = 0l;
		ScrollableResults scrollableResults = null;
		StatelessSession session = getSession();
		try {
			String hql = getOrigHql();
			// 增加where 黄对清
			if (YESUtil.isNotEmpty(getWhere().toString())) {
				hql += getWhere().toString();
			}

			// 增加group by 王一凡
			if (YESUtil.isNotEmpty(getGroup().toString())) {
				hql += getGroup().toString();
			}
			if (YESUtil.isEmpty(getGroup().toString())) {
				Query tquery = session.createSQLQuery("select count(1) " + hql);
				setParamsToQuery(tquery);
				BigDecimal totala = (BigDecimal) tquery.setFirstResult(0).setMaxResults(1).list().get(0);
				total = totala.longValue();
			} else {
				Query tquery = session.createSQLQuery("select count(count(1)) " + hql);
				setParamsToQuery(tquery);
				total = ((Integer) tquery.list().size()).longValue();
				//				BigDecimal totala = (BigDecimal) tquery.setFirstResult(0).setMaxResults(1).list().get(0);
				//				total = totala.longValue();
			}

			hql += getOrderStr(pageable);
			// 增加select 王一凡
			if (YESUtil.isNotEmpty(getSelect().toString())) {
				hql = getSelect().toString() + hql;
			}

			Query query = session.createSQLQuery(hql).addEntity(destinationClass);

			setParamsToQuery(query);
			if (total > 0) {
				content = query.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
						.setMaxResults(pageable.getPageSize()).list();

			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (scrollableResults != null) {
				scrollableResults.close();
			}
			session.close();
			clean();
		}

		return new PageResult<T>(BaseUtil.isEmpty(content) ? new ArrayList<T>() : (ArrayList<T>) content, pageable,
				total);
	}

	public <T> List<T> getNativeResultList(Pageable pageable, Class<T> destinationClass, Object entity) {

		analysisQuery(entity);

		return getNativeResultList(pageable, destinationClass);

	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getNativeResultList(Pageable pageable, Class<T> destinationClass) {
		Object content = null;
		StatelessSession session = getSession();
		try {
			String hql = getOrigHql();
			// 增加where 黄对清
			if (YESUtil.isNotEmpty(getWhere().toString())) {
				hql += getWhere().toString();
			}

			// 增加group by 王一凡
			if (YESUtil.isNotEmpty(getGroup().toString())) {
				hql += getGroup().toString();
			}

			hql += getOrderStr(pageable);
			// 增加select 王一凡
			if (YESUtil.isNotEmpty(getSelect().toString())) {
				hql = getSelect().toString() + hql;
			}

			Query query = session.createSQLQuery(hql).addEntity(destinationClass);

			setParamsToQuery(query);
			content = query.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize())
					.setMaxResults(pageable.getPageSize()).list();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {

			session.close();
			clean();
		}

		return BaseUtil.isEmpty(content) ? new ArrayList<T>() : (ArrayList<T>) content;
	}

	public <T> List<T> getNativeResultList(Class<T> destinationClass, Object entity) {
		analysisQuery(entity);
		return getNativeResultList(destinationClass);

	}

	private void analysisQuery(Object entity) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		Class<T> fieldClass = (Class<T>) entity.getClass();

		Map<String, Object> entitymap = QueryUtils.maprep(entity);
		Object lovVal = entitymap.get("lovsearchstr");// Lov速查
		if (BaseUtil.isNotEmpty(lovVal)) {// 如果有速查值
			setLovWhere(entity);
		}
		setObjectWhere(entity);
	}

	private <T> List<T> getNativeResultList(Class<T> destinationClass) {
		Object content = null;
		StatelessSession session = getSession();
		try {
			String hql = getOrigHql();
			// 增加where 黄对清
			if (YESUtil.isNotEmpty(getWhere().toString())) {
				hql += getWhere().toString();
			}

			// 增加group by 王一凡
			if (YESUtil.isNotEmpty(getGroup().toString())) {
				hql += getGroup().toString();
			}

			// 增加select 王一凡
			if (YESUtil.isNotEmpty(getSelect().toString())) {
				hql = getSelect().toString() + hql;
			}

			Query query = session.createSQLQuery(hql).addEntity(destinationClass);

			setParamsToQuery(query);
			content = query.list();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {

			session.close();
			clean();
		}

		return BaseUtil.isEmpty(content) ? new ArrayList<T>() : (ArrayList<T>) content;
	}

	public void setLovWhere(Object entity) {

		Map<String, Object> entitymap = QueryUtils.maprep(entity);
		Object lovVal = entitymap.get("lovsearchstr");// Lov速查
		@SuppressWarnings("unchecked")
		Class<T> fieldClass = (Class<T>) entity.getClass();
		String wherelov = "";
		for (Field field : fieldClass.getDeclaredFields()) {
			FilterInfo annotation = (FilterInfo) field.getAnnotation(FilterInfo.class);
			if (annotation != null) {
				String filterField = field.getName();
				if (BaseUtil.isNotEmpty(annotation.DbfField())) {
					filterField = annotation.DbfField();
				}
				String filterstr = annotation.LovValue();
				String alias = annotation.alias();
				if (BaseUtil.isNotEmpty(alias)) {
					alias += ".";
				}
				if (BaseUtil.isNotEmpty(filterstr)) {
					String[] filtertype = filterstr.split(",");
					for (String type : filtertype) {
						String typestr = gettypestr(type);
						if (BaseUtil.isNotEmpty(wherelov)) {
							wherelov += " or ";
						}
						typestr = " " + typestr + " ";
						String val = "'" + gettypeval(typestr, lovVal) + "'";
						wherelov += alias + filterField + typestr + val;
					}
				}

			}
		}
		setWhere("and");
		setWhere("(" + wherelov + ")");

	}

	public void setObjectWhere(Object entity) {
		// String where = "";
		// String cdstr = FS_SPACE + "and" + FS_SPACE;

		Class<T> fieldClass = (Class<T>) entity.getClass();
		Map<String, Object> entitymap = QueryUtils.maprep(entity);
		String cdstr = "and";
		for (Field field : fieldClass.getDeclaredFields()) {
			FilterInfo annotation = (FilterInfo) field.getAnnotation(FilterInfo.class);

			// Object val = entitymap.get(field.getName());
			if (annotation != null) {
				String filterField = field.getName();
				if (BaseUtil.isNotEmpty(annotation.DbfField())) {
					filterField = annotation.DbfField();
				}

				String filterstr = annotation.ListValue();

				String[] filtertype = filterstr.split(",");
				String alias = annotation.alias();
				if (BaseUtil.isNotEmpty(alias)) {
					alias += ".";
				}
				String[] valueFields = (BaseUtil.isNotEmpty(annotation.FieldsValue()) ? annotation.FieldsValue()
						: field.getName()).split(",");

				int tag = 0;
				String valField = "";
				for (String type : filtertype) {
					valField = "";
					if (valueFields.length >= tag) {
						valField = valueFields[tag];
					}
					if (BaseUtil.isEmpty(valField)) {
						break;
					}
					tag += 1;
					Object val = entitymap.get(valField);
					if (BaseUtil.isNotEmpty(val)) {

						String typestr = gettypestr(type);
						typestr = " " + typestr + " ";
						if (BaseUtil.isNotEmpty(val)) {
							if ("in".equals(typestr.trim()) || "not in".equals(typestr.trim())) {
								setWhere(cdstr);
								setWhere(alias + filterField + typestr + gettypeval(typestr, val));
							} else {
								setWhere(cdstr);
								setWhere(alias + filterField + typestr + ":" + valField);
								getParams().add(valField);
								getValues().add(gettypeval(typestr, val));
							}

							if (BaseUtil.isNotEmpty(entitymap.get("conditionRelatiion"))) {
								cdstr = YESUtil.toString(entitymap.get("conditionRelatiion"));
							}

						}
					}

				}

			}
		}

	}

	private Object gettypeval(String typestr, Object lovVal) {
		// TODO Auto-generated method stub
		typestr = typestr.trim();
		if ("like".equals(typestr)) {
			lovVal = "%" + lovVal + "%";
		}
		if ("in".equals(typestr) || "not in".equals(typestr)) {
			String[] arr = BaseUtil.toString(lovVal).split(",");
			String hstr = "";
			String buff = "";
			for (String ar : arr) {
				hstr += buff + "'" + ar + "'";
				buff = ",";
			}
			lovVal = "(" + hstr + ")";
		}

		return lovVal;

	}

	private String gettypestr(String type) {
		String typestr = "=";
		switch (type) {
		case "eq":
			typestr = "=";
			break;
		case "gt":
			typestr = ">";
			break;
		case "lt":
			typestr = "<";
			break;
		case "gte":
			typestr = ">=";
			break;
		case "lte":
			typestr = "<=";
			break;
		case "match":
			typestr = "like";
			break;

		case "in":
			typestr = "in";
			break;
		case "notin":
			typestr = "not in";
			break;

		default:
			typestr = "=";
			break;
		}

		return typestr;
	}

	/**
	 * 
	 * @Title: 查询所有
	 * @Description: 数据查询
	 * @author chen xin
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> getPage(Class<T> destinationClass) {
		StatelessSession session = getSession();
		Query query = session.createQuery(getOrigHql());
		setParamsToQuery(query);
		Object content = query.list();
		session.close();
		clean();
		return new PageResult<T>(BaseUtil.isEmpty(content) ? new ArrayList<T>() : (ArrayList<T>) content);
	}

	/**
	 * 获得原始hql语句
	 * 
	 * @return
	 */

	public String getOrigHql() {
		String hql = getHqlBuilder().toString().trim();
		return hql.replaceAll("(?i)where (and|or)", "where").replaceAll("(?i)where$", "");
	}

	private String getOrderStr(Pageable pageable) {
		Sort sort = pageable.getSort();
		if (sort == null) {
			return "";
		}
		return FS_ORDERBY + sort.toString().replaceAll(":", "");
	}

	/**
	 * 将HqlUtil中的参数设置到query中。
	 * 
	 * @param query
	 */
	public Query setParamsToQuery(Query query) {

		List<String> _params = getParams(), _paramsList = getParamsList(), _paramsArray = getParamsArray();
		List<Object> _values = getValues();
		List<Collection<Object>> _valuesList = getValuesList();
		List<Object[]> _valuesArray = getValuesArray();

		if (_params != null) {
			for (int i = 0; i < _params.size(); i++) {
				query.setParameter(_params.get(i), _values.get(i));
			}
		}

		if (_paramsList != null) {
			for (int i = 0; i < _paramsList.size(); i++) {
				query.setParameter(_paramsList.get(i), _valuesList.get(i));
			}
		}

		if (_paramsArray != null) {
			for (int i = 0; i < _paramsArray.size(); i++) {
				query.setParameter(_paramsArray.get(i), _valuesArray.get(i));
			}
		}

		return query;
	}
	//
	// /**
	// * 将HqlUtil中的参数设置到query中。
	// *
	// * @param query
	// */
	// private javax.persistence.Query
	// setNativeParamsToQuery(javax.persistence.Query query) {
	//
	// List<String> _params = getParams(), _paramsList = getParamsList(),
	// _paramsArray = getParamsArray();
	// List<Object> _values = getValues();
	// List<Collection<Object>> _valuesList = getValuesList();
	// List<Object[]> _valuesArray = getValuesArray();
	//
	// if (_params != null) {
	// for (int i = 0; i < _params.size(); i++) {
	// query.setParameter(_params.get(i), _values.get(i));
	// }
	// }
	//
	// if (_paramsList != null) {
	// for (int i = 0; i < _paramsList.size(); i++) {
	// query.setParameter(_paramsList.get(i), _valuesList.get(i));
	// }
	// }
	//
	// if (_paramsArray != null) {
	// for (int i = 0; i < _paramsArray.size(); i++) {
	// query.setParameter(_paramsArray.get(i), _valuesArray.get(i));
	// }
	// }
	//
	// return query;
	// }

	/**
	 * 
	 * 取hibernate session
	 * 
	 * @author cx 2016年5月12日 下午6:51:17
	 * @return
	 */
	private StatelessSession getSession() {
		// Session session = (Session) em.getDelegate();
		Session session = em.unwrap(Session.class);
		/*
		 * if (!session.isOpen()) { session =
		 * session.getSessionFactory().openSession(); }
		 */
		return session.getSessionFactory().openStatelessSession();
	}

	public StringBuilder getHqlBuilder() {
		if (hqlBuilder.get() == null) {
			hqlBuilder.set(new StringBuilder());
		}
		return hqlBuilder.get();
	}

	public StringBuilder getGroup() {
		if (group.get() == null) {
			group.set(new StringBuilder());
		}
		return group.get();
	}

	public StringBuilder getSelect() {
		if (select.get() == null) {
			select.set(new StringBuilder());
		}
		return select.get();
	}

	public StringBuilder getWhere() {
		if (where.get() == null) {
			where.set(new StringBuilder());
		}
		return where.get();
	}

	public void setWhere(String hql) {
		if (where.get() == null) {
			where.set(new StringBuilder());
			where.get().append(FS_SPACE + "where" + FS_SPACE);
			if (hql.trim().equals("and") || hql.trim().equals("or")) {
				hql = "";
			}

		}
		where.get().append(FS_SPACE + hql + FS_SPACE);

	}

	public List<String> getParams() {
		if (params.get() == null) {
			params.set(new ArrayList<String>());
		}
		return params.get();
	}

	public List<Object> getValues() {
		if (values.get() == null) {
			values.set(new ArrayList<Object>());
		}
		return values.get();
	}

	public List<String> getParamsList() {
		if (paramsList.get() == null) {
			paramsList.set(new ArrayList<String>());
		}
		return paramsList.get();
	}

	public ArrayList<Collection<Object>> getValuesList() {
		if (valuesList.get() == null) {
			valuesList.set(new ArrayList<Collection<Object>>());
		}
		return valuesList.get();
	}

	public List<String> getParamsArray() {
		if (paramsArray.get() == null) {
			paramsArray.set(new ArrayList<String>());
		}
		return paramsArray.get();
	}

	public synchronized List<Object[]> getValuesArray() {
		if (valuesArray.get() == null) {
			valuesArray.set(new ArrayList<Object[]>());
		}
		return valuesArray.get();
	}

	private void clean() {
		hqlBuilder.remove();
		select.remove();
		group.remove();
		where.remove();
		params.remove();
		values.remove();
		paramsList.remove();
		valuesList.remove();
		paramsArray.remove();
		valuesArray.remove();
	}

	public String gethqlstr(Object entity) {
		analysisQuery(entity);

		String hql = getOrigHql();
		// 增加where 黄对清
		if (YESUtil.isNotEmpty(getWhere().toString())) {
			hql += getWhere().toString();
		}

		// 增加group by 王一凡
		if (YESUtil.isNotEmpty(getGroup().toString())) {
			hql += getGroup().toString();
		}

		// 增加select 王一凡
		if (YESUtil.isNotEmpty(getSelect().toString())) {
			hql = getSelect().toString() + hql;
		}
		return hql;
	}

}
