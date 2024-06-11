package com.ipower.framework.common.core.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 对象的不同信息
 *
 * @author kris
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Different implements Serializable {

    @Serial
    private static final long serialVersionUID = -5142133608051349765L;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private Class<?> fieldType;

    /**
     * 源对象的值
     */
    private Object originValue;

    /**
     * 目标对象的值
     */
    private Object targetValue;
}
