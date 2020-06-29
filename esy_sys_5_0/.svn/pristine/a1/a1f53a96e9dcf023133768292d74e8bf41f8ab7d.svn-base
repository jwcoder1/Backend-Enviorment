package org.esy.base.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 查询字段注解
 * @author duiqing.huang
 *
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface FilterInfo {
	/**
	 * 查询类别默值为""
	 * @return
	 */
	String remark() default "";

	/**
	 * 列表搜索类别
	 * @return
	 */
	String ListValue() default "";

	/**
	 * LOV搜索类别
	 * @return
	 */
	String LovValue() default "";

	/**
	 * 查询栏位
	 * @return
	 */
	String FieldsValue() default "";

	/**
	 * 实表栏位
	 * @return
	 */
	String DbfField() default "";

	/**
	 * 查询别名默值为""
	 * @return
	 */
	String alias() default "";

}
