package com.ipower.framework.common.core.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Class description goes here.
 *
 * @author kris
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BeanMethod implements Serializable {

    @Serial
    private static final long serialVersionUID = -3881296775359176374L;

    /**
     * 类名
     */
    private Class<?> clazz;

    /**
     * 字段名称
     */
    private String field;

    /**
     * 标准取值方法
     */
    private Method getterMethod;

    /**
     * 标准设置方法
     */
    private Method setterMethod;

}
