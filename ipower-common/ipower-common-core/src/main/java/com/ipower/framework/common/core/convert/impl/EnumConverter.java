package com.ipower.framework.common.core.convert.impl;

import com.ipower.framework.common.core.convert.AbstractConverter;

import java.io.Serial;

/**
 * 无泛型检查的枚举转换器
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @author kris
 * @since 1.0.0
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EnumConverter extends AbstractConverter<Object> {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Class enumClass;

    /**
     * 构造
     *
     * @param enumClass 转换成的目标Enum类
     */
    public EnumConverter(Class enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    protected Object convertInternal(Object value) {
        return Enum.valueOf(enumClass, convertToStr(value));
    }

    @Override
    public Class getTargetType() {
        return this.enumClass;
    }
}
