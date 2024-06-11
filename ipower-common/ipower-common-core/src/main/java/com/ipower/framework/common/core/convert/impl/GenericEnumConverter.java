package com.ipower.framework.common.core.convert.impl;

import com.ipower.framework.common.core.convert.AbstractConverter;

import java.io.Serial;

/**
 * 泛型枚举转换器
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @param <E> 枚举类类型
 * @author kris
 * @since 1.0.0
 */
public class GenericEnumConverter<E extends Enum<E>> extends AbstractConverter<E> {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Class<E> enumClass;

    /**
     * 构造
     *
     * @param enumClass 转换成的目标Enum类
     */
    public GenericEnumConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    protected E convertInternal(Object value) {
        return Enum.valueOf(enumClass, convertToStr(value));
    }

    @Override
    public Class<E> getTargetType() {
        return this.enumClass;
    }
}
