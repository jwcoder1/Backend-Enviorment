/*
 * Copyright (c) , Yes-soft Ltd. All rights reserved.
 * Use is subject to license terms.
 */
package org.esy.base.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.Base;
import org.esy.base.dao.core.PQuery;
import org.esy.base.dao.core.PageResult;
import org.esy.base.util.BeanMapper;
import org.esy.base.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * ClssName: YSDao 
 * Description: 数据简单操作类
 * </pre>
 *
 * @author hdq
 * @since 1.8
 * @version $ Id:YSDao.java 1.0 2017年2月16日下午2:30:11 $
 */
@Component
public class YSDao extends AbstractDao<Base, String> {

	@PersistenceContext
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Autowired
	private PQuery pageQuery;

	/**
	 * Description:新增Or修改
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public <T extends Base> T save(T o) {
		Map<String, Object> filedmap = this.getJsonPropertyfields(o);
		if (o.checkNew()) {
			o.setUid(UuidUtils.getUUID());
			o.createEntity();
			this.em.persist(o);
		} else {
			o.updateEntity();
			o = this.em.merge(o);
		}
		o = this.setJsonPropertyfields(filedmap, o);
		return o;

	}

	/**
	 * Description:新增Or修改
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public <T extends Base> T save(Class<?> cls, T o) {
		Map<String, Object> filedmap = this.getJsonPropertyfields(o);
		Base entity = null;
		try {
			entity = (Base) cls.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		BeanMapper.copy(o, entity);
		if (entity.checkNew()) {
			String uid = UuidUtils.getUUID();
			entity.setUid(uid);
			entity.createEntity();
			this.em.persist(entity);
			o.setUid(entity.getUid());
		} else {
			entity.updateEntity();
			entity = this.em.merge(entity);
		}
		// BeanMapper.copy(entity, o);
		o = this.setJsonPropertyfields(filedmap, o);
		return o;

	}

	/**
	 * Description:根据实体删除
	 * 
	 * @author hdq 2017年2月16日下午3:18:49
	 * @param entity
	 */
	@Override
	public boolean delete(Base o) {
		try {
			this.em.remove(getEm().merge(o));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Description:根据实体删除
	 * 
	 * @author hdq 2017年2月16日下午3:18:49
	 * @param entity
	 */
	@Override
	public boolean delete(Class<?> cls, Base o) {
		Base entity = null;
		try {
			entity = (Base) cls.newInstance();
			BeanMapper.copy(o, entity);
			this.em.remove(getEm().merge(entity));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Description:获取所有数据
	 * 
	 * @param cls
	 *            must not be
	 * @return Collection
	 */
	@Override
	public <T extends Base> int deleteList(Class<T> cls, T entity) {
		pageQuery.append("delete from  " + cls.getSimpleName());
		return pageQuery.deleteList(cls, entity);
	}

	/**
	 * Description:获取所有数据
	 * 
	 * @param cls
	 *            must not be
	 * @return Collection
	 */
	@Override
	public <T extends Base> int update(Class<T> cls, String setstr, T entity) {
		pageQuery.append("update from  " + cls.getSimpleName());
		pageQuery.append("set " + setstr);
		return pageQuery.update(cls, entity);
	}

	/**
	 * Description:根据Uid获取实体
	 * 
	 * @author hdq 2017年2月16日下午3:11:51
	 * @param id
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Base> T getByUid(Class<?> cls, String id) {
		Object entity = em.find(cls, id);
		return entity == null ? null : (T) entity;
	}

	/**
	 * Description:根据条件查询,不查count
	 * 
	 * @param cls
	 * @param cnd
	 * @param pageable
	 * @return Page
	 */
	@Override
	public <T> Page<T> query(Class<T> cls, T entity, Pageable pageable) {
		pageQuery.append("from " + cls.getSimpleName() + " a");
		return pageQuery.getPage(pageable, cls, entity);

	}

	/**
	 * Description:根据条件查询,不查count
	 * 
	 * @param cls
	 * @param cnd
	 * @param pageable
	 * @return Page
	 */
	@Override
	public <T> Page<T> query(Class<T> cls, T entity, Pageable pageable, String select, String where, String group) {
		pageQuery.append("from " + cls.getSimpleName() + " a");
		if (BaseUtil.isNotEmpty(select)) {
			pageQuery.appendSelect(select);
		}
		if (BaseUtil.isNotEmpty(where)) {
			pageQuery.appendWhere(where);
		}
		if (BaseUtil.isNotEmpty(group)) {
			pageQuery.appendGroup(group);
		}
		return pageQuery.getPage(pageable, cls, entity);

	}

	/**
	 * Description:自定义hql
	 * 
	 * @param cls
	 * @param cnd
	 * @param pageable
	 * @return Page
	 */
	@Override
	public <T> Page<T> query(Class<T> cls, T entity, Pageable pageable, String hql, String select, String where,
			String group) {
		pageQuery.append(hql);
		if (BaseUtil.isNotEmpty(select)) {
			pageQuery.appendSelect(select);
		}
		if (BaseUtil.isNotEmpty(where)) {
			pageQuery.appendWhere(where);
		}
		if (BaseUtil.isNotEmpty(group)) {
			pageQuery.appendGroup(group);
		}
		return pageQuery.getPage(pageable, cls, entity);
	}

	/**
	 * Description:获取所有数据
	 * 
	 * @param cls
	 *            must not be
	 * @return Collection
	 */
	@Override
	public <T> List<T> getlist(Class<T> cls, T entity, String select, String where, String group) {
		pageQuery.append("from " + cls.getSimpleName() + " a");
		if (BaseUtil.isNotEmpty(select)) {
			pageQuery.appendSelect(select);
		}
		if (BaseUtil.isNotEmpty(where)) {
			pageQuery.appendWhere(where);
		}
		if (BaseUtil.isNotEmpty(group)) {
			pageQuery.appendGroup(group);
		}
		return pageQuery.getResultList(cls, entity);
	}

	/**
	 * Description:获取所有数据
	 * 
	 * @param cls
	 *            must not be
	 * @return Collection
	 */
	@Override
	public <T> List<T> getlist(Class<T> cls, T entity) {
		pageQuery.append("from " + cls.getSimpleName() + " a");
		return pageQuery.getResultList(cls, entity);
	}

	/**
	 * Description:获取所有数据
	 * 
	 * @param cls
	 *            must not be
	 * @return Collection
	 */
	@Override
	public <T> List<T> getlist(Class<T> cls, T entity, Pageable pageable) {
		pageQuery.append("from " + cls.getSimpleName() + " a");
		return pageQuery.getResultList(cls, entity, pageable);
	}

	/**
	 * Description:根据Uid获取实体
	 * 
	 * @author hdq 2017年2月16日下午3:11:51
	 * @param id
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Base> T getEntity(Class<T> cls, T entity, Pageable pageable) {
		pageQuery.append("from " + cls.getSimpleName() + " a");
		Object ret = pageQuery.getSingleResult(cls, entity, pageable);
		return ret == null ? null : (T) ret;
	}

	/**
	 * Description:根据Uid获取实体
	 * 
	 * @author hdq 2017年2月16日下午3:11:51
	 * @param id
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Base> T getEntity(Class<T> cls, T entity) {
		pageQuery.append("from " + cls.getSimpleName() + " a");
		Object ret = pageQuery.getSingleResult(cls, entity);
		return ret == null ? null : (T) ret;
	}

	/**
	 * Description:根据Uid获取实体
	 * 
	 * @author hdq 2017年2月16日下午3:11:51
	 * @param id
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Base> T getEntity(Class<T> cls, T entity, String select, String where, String group) {
		pageQuery.append("from " + cls.getSimpleName() + " a");
		if (BaseUtil.isNotEmpty(select)) {
			pageQuery.appendSelect(select);
		}
		if (BaseUtil.isNotEmpty(where)) {
			pageQuery.appendWhere(where);
		}
		if (BaseUtil.isNotEmpty(group)) {
			pageQuery.appendGroup(group);
		}
		Object ret = pageQuery.getSingleResult(cls, entity);
		return ret == null ? null : (T) ret;
	}

	/**
	 * Description:根据Uid获取实体
	 * 
	 * @author hdq 2017年2月16日下午3:11:51
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Base> Map<String, Object> countEntity(Class<T> cls, String countFields, T entity) {
		String[] fields = countFields.split(",");
		String hqlselect = "", tag = "";
		for (String field : fields) {
			String fieldname = "1".equals(field) ? "count" : field;
			hqlselect += tag + "sum(" + field + ") as " + fieldname;
			tag = ",";
		}
		pageQuery.append("select new map(" + hqlselect + ") from " + cls.getSimpleName() + " a");
		Object ret = pageQuery.getSingleResult(cls, entity);

		Map<String, Object> retmap = (Map<String, Object>) ret;
		return retmap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.dao.AbstractDao#getEntityMap(java.lang.Class,
	 * java.lang.Object, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public <T extends Base> Map<String, Object> getEntityMap(T entity, String hql) {

		@SuppressWarnings("unused")
		Class<T> fieldClass = (Class<T>) entity.getClass();
		pageQuery.append(hql);
		Object ret = pageQuery.getSingleResult(fieldClass, entity);
		Map<String, Object> retmap = (Map<String, Object>) ret;
		return retmap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.dao.AbstractDao#getEntityMap(java.lang.Class,
	 * java.lang.Object, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public <T extends Base> T getNativeEntity(T entity, String sql) {
		@SuppressWarnings("unused")
		Class<T> destinationClass = (Class<T>) entity.getClass();
		pageQuery.append(sql);
		Object ret = pageQuery.getNativeSingleResult(destinationClass, entity);
		return ret == null ? null : (T) ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.dao.AbstractDao#getEntityMap(java.lang.Class,
	 * java.lang.Object, java.lang.String, java.lang.String, java.lang.String)
	 */
	public <T extends Base> Map<String, Object> getNativeformap(T entity, String sqlstr, String where) {
		@SuppressWarnings("unused")
		Class<T> destinationClass = (Class<T>) entity.getClass();
		pageQuery.append(sqlstr);
		if (BaseUtil.isNotEmpty(where)) {
			pageQuery.appendWhere(where);
		}
		return pageQuery.getNativeformap(destinationClass, entity);
	}

	@Override
	public <T extends Base> T getNativeEntity(T entity, String select, String from, String join, String where) {
		@SuppressWarnings("unused")
		Class<T> destinationClass = (Class<T>) entity.getClass();
		if (BaseUtil.isNotEmpty(select)) {
			pageQuery.appendSelect(select);
		}
		if (BaseUtil.isNotEmpty(join)) {
			pageQuery.appendJoin(join);
		}

		if (BaseUtil.isNotEmpty(where)) {
			pageQuery.appendWhere(where);
		}
		pageQuery.append(from);
		Object ret = pageQuery.getNativeSingleResult(destinationClass, entity);
		return ret == null ? null : (T) ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.dao.AbstractDao#getEntityMap(java.lang.Class,
	 * java.lang.Object, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public <T extends Base> List<T> getNativeResultList(T entity, String sql) {
		@SuppressWarnings("unused")
		Class<T> destinationClass = (Class<T>) entity.getClass();
		pageQuery.append(sql);
		List<T> ret = pageQuery.getNativeResultList(destinationClass, entity);
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.dao.AbstractDao#getEntityMap(java.lang.Class,
	 * java.lang.Object, java.lang.String, java.lang.String, java.lang.String)
	 */
	public <T extends Base> List<T> getNativeResultList(Class<T> cls, Map<String, Object> para, String sql) {
		pageQuery.append(sql);

		for (String key : para.keySet()) {
			pageQuery.getParams().add(key);
			pageQuery.getValues().add(para.get(key));
		}
		List<T> ret = pageQuery.getNativeResultList(cls);
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.dao.AbstractDao#getEntityMap(java.lang.Class,
	 * java.lang.Object, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public <T> PageResult<T> getNativePage(T entity, String select, String from, Pageable pageable) {
		@SuppressWarnings("unused")
		Class<T> destinationClass = (Class<T>) entity.getClass();
		if (BaseUtil.isNotEmpty(select)) {
			pageQuery.appendSelect(select);
		}
		pageQuery.append(from);
		PageResult<T> ret = pageQuery.getNativePage(pageable, destinationClass, entity);
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.dao.AbstractDao#getEntityMap(java.lang.Class,
	 * java.lang.Object, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public <T> PageResult<T> getNativePage(T entity, String select, String from, String join, Pageable pageable) {
		@SuppressWarnings("unused")
		Class<T> destinationClass = (Class<T>) entity.getClass();
		if (BaseUtil.isNotEmpty(select)) {
			pageQuery.appendSelect(select);
		}
		if (BaseUtil.isNotEmpty(join)) {
			pageQuery.appendJoin(join);
		}
		pageQuery.append(from);
		PageResult<T> ret = pageQuery.getNativePage(pageable, destinationClass, entity);
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.dao.AbstractDao#getEntityMap(java.lang.Class,
	 * java.lang.Object, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public <T> PageResult<T> getNativePage(T entity, String select, String from, String join, String where,
			Pageable pageable) {
		@SuppressWarnings("unused")
		Class<T> destinationClass = (Class<T>) entity.getClass();
		if (BaseUtil.isNotEmpty(select)) {
			pageQuery.appendSelect(select);
		}
		if (BaseUtil.isNotEmpty(join)) {
			pageQuery.appendJoin(join);
		}

		if (BaseUtil.isNotEmpty(where)) {
			pageQuery.appendWhere(where);
		}
		pageQuery.append(from);
		PageResult<T> ret = pageQuery.getNativePage(pageable, destinationClass, entity);
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.dao.AbstractDao#getEntityMap(java.lang.Class,
	 * java.lang.Object, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public <T> List<T> getNativeList(T entity, String select, String from, String join, String where) {
		@SuppressWarnings("unused")
		Class<T> destinationClass = (Class<T>) entity.getClass();
		if (BaseUtil.isNotEmpty(select)) {
			pageQuery.appendSelect(select);
		}
		if (BaseUtil.isNotEmpty(join)) {
			pageQuery.appendJoin(join);
		}

		if (BaseUtil.isNotEmpty(where)) {
			pageQuery.appendWhere(where);
		}
		pageQuery.append(from);
		List<T> ret = pageQuery.getNativeResultList(destinationClass, entity);
		return ret;
	}

	/**
	 * Description:根据Uid获取实体
	 * 
	 * @author hdq 2017年2月16日下午3:11:51
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Base> List<Map<String, Object>> queryforMap(Class<T> cls, T entity, String select, String where,
			String group) {
		pageQuery.append(" from " + cls.getSimpleName() + " a");
		if (BaseUtil.isNotEmpty(select)) {
			pageQuery.appendSelect(select);
		}
		if (BaseUtil.isNotEmpty(where)) {
			pageQuery.appendWhere(where);
		}

		if (BaseUtil.isNotEmpty(group)) {
			pageQuery.appendGroup(group);
		}

		Object ret = pageQuery.getResultList(cls, entity);

		List<Map<String, Object>> retmap = (List<Map<String, Object>>) ret;
		return retmap;
	}

	private <T extends Base> T setJsonPropertyfields(Map<String, Object> map, T entity) {
		List<Field> fieldList = new ArrayList<>();
		Class tempClass = entity.getClass();
		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
		}
		try {
			for (Field field : fieldList) {
				Column column = (Column) field.getAnnotation(Column.class);
				if (!"serialVersionUID".equals(field.getName()) && BaseUtil.isEmpty(column)) {
					if (map.containsKey(field.getName())) {
						boolean flag = field.isAccessible();
						field.setAccessible(true);
						Object object = map.get(field.getName());
						if (object != null && field.getType().isAssignableFrom(object.getClass())) {
							field.set(entity, object);
						}
						field.setAccessible(flag);
					}
				}

			}
			return entity;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;

	}

	private <T extends Base> Map<String, Object> getJsonPropertyfields(T object) {

		Map<String, Object> map = new HashMap();
		List<Field> fieldList = new ArrayList<>();
		Class tempClass = object.getClass();
		while (tempClass != null) {// 当父类为null的时候说明到达了最上层的父类(Object类).
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			tempClass = tempClass.getSuperclass(); // 得到父类,然后赋给自己
		}
		for (Field field : fieldList) {
			try {
				Column column = (Column) field.getAnnotation(Column.class);
				if (!"serialVersionUID".equals(field.getName()) && BaseUtil.isEmpty(column)) {
					boolean flag = field.isAccessible();
					field.setAccessible(true);
					Object o = field.get(object);
					map.put(field.getName(), o);
					field.setAccessible(flag);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;

	}

	@Override
	public Object getSqlforObject(String sqlstr, Map<String, Object> para) {
		return pageQuery.getSqlforObject(sqlstr, para);
	}

	@Override
	public Map<String, Object> getSqlformap(String sqlstr, Map<String, Object> para) {
		Map<String, Object> ret = pageQuery.getSqlformap(sqlstr, para);
		return ret;
	}

	@Override
	public List<Map<String, Object>> getSqlforListmap(String sqlstr, Map<String, Object> para) {
		return pageQuery.getSqlforListmap(sqlstr, para);
	}

	@Override
	public Page<Map<String, Object>> getSqlforLispage(String sqlstr, Map<String, Object> para, Pageable pageable) {
		return pageQuery.getSqlforLispage(sqlstr, para, pageable);
	}

	@Override
	public List<Map<String, Object>> getSqlforListmap(String sqlstr, Map<String, Object> para, Pageable pageable) {
		return pageQuery.getSqlforList(sqlstr, para, pageable);
	}

	public int sqlCommand(String sqlstr, Map<String, Object> para) {
		// TODO Auto-generated method stub
		return pageQuery.sqlCommand(sqlstr, para);
	}

	public void clear() {
		pageQuery.getEm().clear();

	}

}
