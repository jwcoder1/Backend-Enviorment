package org.esy.base.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 中文字段注解
 * @author duiqing.huang
 *
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface FieldInfo {
	/**
	 * 备注默值为""
	 * @return
	 */
	String remark() default "";

	/**
	 * 字段中文名默值为""
	 * @return
	 */
	String value() default "";
}
