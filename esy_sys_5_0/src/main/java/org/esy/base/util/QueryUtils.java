package org.esy.base.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.Condition;
import org.springframework.cglib.beans.BeanMap;

public class QueryUtils {

	/**
	 * 根据 $ 符号拆分搜索条件
	 * 
	 * @param parm
	 * @return
	 */
	public static Map<String, Condition> getQueryData(Map<String, Object> parm) {
		Map<String, Condition> result = new HashMap<String, Condition>();
		String[] parms;
		Condition condition;
		Map<String, String> conditions;
		for (String key : parm.keySet()) {
			parms = key.split("\\$");
			if (parms.length > 1) {
				condition = result.get(parms[0]);
				if (condition == null) {
					conditions = new HashMap<String, String>();
					String value = YESUtil.toString(parm.get(key));
					if (YESUtil.isEmpty(value)) {
						conditions.put(parms[1], null);
					} else {
						conditions.put(parms[1], value);
					}
					result.put(parms[0], new Condition(parms[0], conditions));
				} else {
					condition.getConditions().put(parms[1], YESUtil.toString(parm.get(key)));
				}
			}
		}

		return result;

	}

	/**
	 * 取得类 @FieldInfo 信息
	 * 
	 * @param tagClass
	 *            类名
	 * @return 字段名,注解值
	 */
	public static Map<String, String> getClassFieldInfo(Class<?> tagClass) {

		Map<String, String> result = new HashMap<String, String>();

		for (Field field : tagClass.getDeclaredFields()) {
			FieldInfo annotation = (FieldInfo) field.getAnnotation(FieldInfo.class);
			if (annotation != null) {
				result.put(field.getName(), annotation.value());
			}
		}

		return result;

	}

	/**
	 * 取得类 @FilterInfo 信息
	 * 
	 * @param tagClass
	 *            类名
	 * @return 字段名,注解值
	 */
	public static String getEntityFilterString(String alias, Object entity) {

		Class<?> tagClass = entity.getClass();
		Map<String, String> result = new HashMap<String, String>();

		String wherestr = "";
		Map<String, Object> datamap = maprep(entity);
		String cdstr = " and ";

		for (Field field : tagClass.getDeclaredFields()) {
			FilterInfo annotation = (FilterInfo) field.getAnnotation(FilterInfo.class);

			if (annotation != null) {
				String[] filtertype = annotation.ListValue().split(",");
				for (String type : filtertype) {
					String typestr;
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

					default:
						typestr = "=";
						break;
					}

					if ("eq".equals(type)) {
						wherestr += cdstr + alias + "." + field.getName() + typestr + datamap.get(field.getName());
					}

				}

			}
		}

		return wherestr;

	}

	// 将map类型中的data类型转换，然后返回一个map类型的值
	public static Map<String, Object> maprep(Object base) {
		// Map<String, Object> map = Maps.newHashMap();
		//
		// map.put("created", "2017-03-17");
		// map.put("uid", "11111");
		BeanMap m = BeanMap.create(base);
		Set<?> keys = m.keySet();

		Map<String, Object> map = new HashMap<String, Object>();

		for (Object key : keys) {

			String _key = key.toString();

			//			if ("enable".equals(_key)) {
			//				continue;
			//			}
			Object v = null;
			try {
				v = m.get(_key);
			} catch (Exception e) {

				//				e.printStackTrace();
			}

			if (v == null) {
				continue;
			}

			Class<?> cls = m.getPropertyType(_key);

			// if (Date.class.isAssignableFrom(cls)) {
			// v = BaseUtil.dateToStr((Date) v);
			// }

			// if (!Long.class.isAssignableFrom(cls)) {
			// v = BaseUtil.toString(v);
			// }

			map.put(_key, v);

			// m.put(_key, v);

		}

		// account = (Account) m.getBean();

		return map;

	}

}
