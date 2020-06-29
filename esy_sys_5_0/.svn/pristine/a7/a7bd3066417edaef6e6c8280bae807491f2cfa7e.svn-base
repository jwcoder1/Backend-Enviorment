/*
 * Copyright (c) , JianFa Ltd. and/or its affiliates, and/or Yes-soft Ltd. All rights reserved.
 * Use is subject to license terms.
 */
package org.esy.base.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dozer.DozerBeanMapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.sf.json.JSONObject;

/**
 * <pre>
 * ClssName: BeanMapper 
 * Description: 简单封装Dozer,实现深度转换Bean<->Bean的Mapper.实现：
 * 	1.持有Mapper的单例。
 *  2.返回值类型转换。
 *  3.批量转换Collection中的所有对象。
 *  4.区分创建新的B对象与将对象A值复制到已存在的B对象两种函数。
 * </pre>
 *
 * @author chenxin
 * @since 1.7
 * @version $ Id:BeanMapper.java 1.0 2015年11月10日下午5:31:07 $
 */
public class BeanMapper {

	/*
	 * private static final String A_TO_B = "AB";
	 * 
	 * private static final String B_TO_A = "BA";
	 */

	/*
	 * private static IBeanFieldConverter fieldConverterImpl;
	 * 
	 * public static void setFieldConverterImpl(IBeanFieldConverter
	 * fieldConverterImpl) { BeanMapper.fieldConverterImpl = fieldConverterImpl;
	 * }
	 */
	/**
	 * 持有Dozer单例，避免重复合建DozerMapper消耗资源。
	 */
	private static DozerBeanMapper dozer = new DozerBeanMapper();

	/**
	 * Description:基于Dozer转换对象的类型
	 * 
	 * @param 需要转换的对象实例
	 * @param 目标对象的类类型
	 * @return
	 */
	public static <T> T map(Object source, Class<T> destinationClass) {
		if (source == null) {
			return null;
		}
		return dozer.map(source, destinationClass);
	}

	/**
	 * Description:基于Dozer转换对象的类型
	 * 
	 * @param 类型转换配置Key
	 * @param 需要转换的对象实例
	 * @return
	 * @throws Exception
	 */
	/*
	 * @SuppressWarnings("unchecked") public static <T> T map(String key, Object
	 * source) throws Exception { ConvertConfig cfg =
	 * fieldConverterImpl.findByConfigKey(key); if (source != null &&
	 * cfg.getBeanFrom() != null && cfg.getBeanTo() != null) { if
	 * (source.getClass().getName().equals(cfg.getBeanFrom().getName())) {
	 * return (T) mapByCfg(source, A_TO_B, cfg); } else if
	 * (source.getClass().getName().equals(cfg.getBeanTo().getName())) { return
	 * (T) mapByCfg(source, B_TO_A, cfg); } } return null; }
	 */

	/*
	 * @SuppressWarnings({ "unchecked", "rawtypes" }) private static <T> T
	 * mapByCfg(final Object source, final String direction, ConvertConfig cfg)
	 * throws Exception { if (source == null) { return null; } String fieldMap =
	 * cfg.getAttrMapConfig(); final JSONArray array =
	 * JSONArray.fromObject(fieldMap); Class _desBean = null; if
	 * (A_TO_B.equals(direction)) { _desBean = cfg.getBeanTo(); } if
	 * (B_TO_A.equals(direction)) { _desBean = cfg.getBeanFrom(); } final Class
	 * desBean = _desBean; BeanMappingBuilder mappingBuilder = new
	 * BeanMappingBuilder() {
	 * 
	 * @Override protected void configure() { TypeMappingBuilder tmp =
	 * mapping(source.getClass(), desBean); for (Object eachFild : array) {
	 * JSONObject o = JSONObject.fromObject(eachFild); if
	 * (A_TO_B.equals(direction)) { tmp.fields(o.getString("a"),
	 * o.getString("b")); } if (B_TO_A.equals(direction)) {
	 * tmp.fields(o.getString("b"), o.getString("a")); } } } };
	 * dozer.addMapping(mappingBuilder); return (T) dozer.map(source, desBean);
	 * }
	 */
	/**
	 * Description:基于Dozer转换Collection中对象的类型
	 * 
	 * @param 需要转换的对象实例集合
	 * @param 目标对象的类类型
	 * @return
	 */
	public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
		if (sourceList == null) {
			return null;
		}
		List<T> destinationList = Lists.newArrayList();
		for (Object sourceObject : sourceList) {
			T destinationObject = dozer.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}

	/**
	 * Description:基于Dozer转换Set中对象的类型
	 * 
	 * @param 需要转换的对象实例集合
	 * @param 目标对象的类类型
	 * @return
	 */
	public static <T> Set<T> mapSet(Collection<?> sourceList, Class<T> destinationClass) {
		if (sourceList == null) {
			return null;
		}
		Set<T> destinationList = Sets.newHashSet();
		for (Object sourceObject : sourceList) {
			T destinationObject = dozer.map(sourceObject, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}

	/**
	 * Description:基于Dozer将对象source的值拷贝到对象destinationObject中。
	 * 
	 * @param 需要转换的对象实例
	 * @param 目标对象的类类型
	 */
	public static void copy(Object source, Object destinationObject) {
		dozer.map(source, destinationObject);
	}

	/**
	 * <pre>
	 * ClssName: 类对象配置 
	 * Description: XML映射用
	 * </pre>
	 *
	 * @author ZHANGYU
	 * @since 1.7
	 * @version $ Id:BeanMapper.ConvertConfig.java 1.0 2015年12月8日下午1:58:05 $
	 */
	public static class ConvertConfig {
		private String key;

		private String attrMapConfig;

		@SuppressWarnings("rawtypes")
		private Class beanFrom;

		@SuppressWarnings("rawtypes")
		private Class beanTo;

		@SuppressWarnings("rawtypes")
		public ConvertConfig(String key, String attrMapConfig, Class beanFrom, Class beanTo) {
			this.key = key;
			this.attrMapConfig = attrMapConfig;
			this.beanFrom = beanFrom;
			this.beanTo = beanTo;
		}

		public String getKey() {
			return key;
		}

		public String getAttrMapConfig() {
			return attrMapConfig;
		}

		@SuppressWarnings("rawtypes")
		public Class getBeanFrom() {
			return beanFrom;
		}

		@SuppressWarnings("rawtypes")
		public Class getBeanTo() {
			return beanTo;
		}

	}

	/**
	 * json string 转换为 map 对象
	 * 
	 * @param jsonObj
	 * @return
	 */
	public static Map<Object, Object> jsonToMap(Object jsonObj) {
		JSONObject jsonObject = JSONObject.fromObject(jsonObj);
		Map<Object, Object> map = (Map) jsonObject;
		return map;
	}

	/*
	 * public static IBeanFieldConverter getFieldConverterImpl() { return
	 * fieldConverterImpl; }
	 */

}
