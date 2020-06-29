/*
 * Copyright (c) , Yes-soft Ltd. All rights reserved.
 * Use is subject to license terms.
 */
package org.esy.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.esy.base.core.Base;
import org.esy.base.dao.core.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <pre>
 * ClssName: AbstractDao 
 * Description: 抽象DAO层基类 提供一些简便方法
 * </pre>
 *
 * @author hdq
 * @since 1.8
 * @version $ Id:AbstractDao.java 1.0 2017年2月16日下午2:33:35 $
 */
public abstract class AbstractDao<M, UID extends Serializable> {

	/**
	 * Description:新增Or修改
	 * 
	 * @author hdq 2017年2月16日下午3:12:50
	 * @param entity
	 * @return
	 */
	public abstract <T extends M> T save(T entity);

	/**
	 * Description:新增Or修改
	 * 
	 * @author hdq 2017年2月16日下午3:12:50
	 * @param entity
	 * @return
	 */
	public abstract <T extends M> T save(Class<?> cls, T entity);

	/**
	 * Description:根据实体删除
	 * 
	 * @author hdq 2017年2月16日下午3:18:49
	 * @param entity
	 */
	public abstract boolean delete(M entity);

	/**
	 * Description:根据实体删除
	 * 
	 * @author hdq 2017年2月16日下午3:18:49
	 * @param entity
	 */
	public abstract boolean delete(Class<?> cls, M entity);

	public abstract <T extends M> int deleteList(Class<T> cls, T entity);

	public abstract <T extends M> int update(Class<T> cls, String setstr, T entity);

	/**
	 * Description:根据Uid获取实体
	 * 
	 * @author hdq 2017年2月16日下午3:11:51
	 * @param id
	 * @return
	 */
	public abstract <T extends M> T getByUid(Class<?> cls, UID id);

	public abstract <T> Page<T> query(Class<T> cls, T entity, Pageable pageable);

	public abstract <T> Page<T> query(Class<T> cls, T entity, Pageable pageable, String select, String where,
			String group);

	public abstract <T> Page<T> query(Class<T> cls, T entity, Pageable pageable, String hql, String select,
			String where, String group);

	/**
	 * Description:TODO 简单描述下方法功能和调用注意事项
	 * 
	 * @author hdq 2017年3月2日下午2:23:17
	 * @param cls
	 * @param cnd
	 * @return
	 */
	public abstract <T> List<T> getlist(Class<T> cls, T entity);

	public abstract <T> List<T> getlist(Class<T> cls, T entity, String select, String where, String group);

	public abstract <T> List<T> getlist(Class<T> cls, T entity, Pageable pageable);

	public abstract <T extends M> T getEntity(Class<T> cls, T entity);

	public abstract <T extends M> T getEntity(Class<T> cls, T entity, String select, String where, String group);

	public abstract <T extends M> T getEntity(Class<T> cls, T entity, Pageable pageable);

	public abstract <T extends M> Map<String, Object> getEntityMap(T entity, String hql);

	public abstract <T extends M> Map<String, Object> countEntity(Class<T> cls, String select, T entity);

	public abstract <T extends M> List<Map<String, Object>> queryforMap(Class<T> cls, T entity, String select,
			String where, String group);

	public abstract <T extends Base> T getNativeEntity(T entity, String sql);

	public abstract <T extends Base> T getNativeEntity(T entity, String select, String from, String join, String where);

	public abstract <T extends M> List<T> getNativeResultList(T entity, String sql);

	public abstract <T> PageResult<T> getNativePage(T entity, String select, String from, Pageable pageable);

	public abstract <T> PageResult<T> getNativePage(T entity, String select, String from, String join,
			Pageable pageable);

	public abstract <T> PageResult<T> getNativePage(T entity, String select, String from, String join, String where,
			Pageable pageable);

	public abstract <T> List<T> getNativeList(T entity, String select, String from, String join, String where);

	public abstract Object getSqlforObject(String sqlstr, Map<String, Object> para);

	public abstract Map<String, Object> getSqlformap(String sqlstr, Map<String, Object> para);

	public abstract List<Map<String, Object>> getSqlforListmap(String sqlstr, Map<String, Object> para);

	public abstract Page<Map<String, Object>> getSqlforLispage(String sqlstr, Map<String, Object> para,
			Pageable pageable);

	public abstract List<Map<String, Object>> getSqlforListmap(String sqlstr, Map<String, Object> para,
			Pageable pageable);

}
