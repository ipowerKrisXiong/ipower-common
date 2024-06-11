package com.ipower.framework.common.core.convert.impl;

import com.ipower.framework.common.core.convert.AbstractConverter;
import com.ipower.framework.common.core.exception.ConvertException;

import java.io.Serial;

/**
 * 强转转换器
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @param <T> 强制转换到的类型
 * @author kris
 * @since 1.0.0
 */
public class CastConverter<T> extends AbstractConverter<T> {
    @Serial
    private static final long serialVersionUID = 1L;

    private Class<T> targetType;

    @Override
    protected T convertInternal(Object value) {
        // 由于在AbstractConverter中已经有类型判断并强制转换，因此当在上一步强制转换失败时直接抛出异常
        throw new ConvertException("Can not cast value to [{}]", this.targetType);
    }

    @Override
    public Class<T> getTargetType() {
        return this.targetType;
    }
}
