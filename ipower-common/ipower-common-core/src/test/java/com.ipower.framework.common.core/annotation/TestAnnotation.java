package com.ipower.framework.common.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 字典字段标识
 *
 * @author anding.huang@u-need.cn
 */
@Documented
@Target({TYPE, FIELD, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {

    String value() default "";

    String name() default "";

    boolean enable() default false;

}
