package org.esy.base.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 中文表名注解
 * @author duiqing.huang
 *
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface EntityInfo {
	String remark() default ""; //备注

	String value() default ""; //表中文名
}
