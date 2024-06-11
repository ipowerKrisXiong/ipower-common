package com.ipower.framework.common.core.convert.impl;

import com.ipower.framework.common.core.convert.AbstractConverter;
import com.ipower.framework.common.core.lang.BooleanUtil;

import java.io.Serial;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@link AtomicBoolean}转换器
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @author kris
 * @since 1.0.0
 */
public class AtomicBooleanConverter extends AbstractConverter<AtomicBoolean> {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected AtomicBoolean convertInternal(Object value) {
        if (value instanceof Boolean) {
            return new AtomicBoolean((Boolean) value);
        }
        final String valueStr = convertToStr(value);
        return new AtomicBoolean(BooleanUtil.toBoolean(valueStr));
    }

}
