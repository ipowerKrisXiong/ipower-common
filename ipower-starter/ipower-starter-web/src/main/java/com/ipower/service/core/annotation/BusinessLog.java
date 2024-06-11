package com.ipower.service.core.annotation;


/**
 * 业务日志，用于注解在application层的service方法前
 * 然后自定义切面来处理这个日志，进行业务日志记录
 * 暂时没有实现
 */

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessLog {

    /**
     * 日志类型，e.g. addUser editUser removeUser 用于业务方自定义，方便区分
     * @return
     */
    String logType() default "default";


}
