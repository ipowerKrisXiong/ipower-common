package com.ipower.framework.common.core.convert.impl;

import com.ipower.framework.common.core.convert.AbstractConverter;
import com.ipower.framework.common.core.lang.BooleanUtil;

import java.io.Serial;

/**
 * 波尔转换器
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @author kris
 * @since 1.0.0
 */
public class BooleanConverter extends AbstractConverter<Boolean> {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected Boolean convertInternal(Object value) {
        String valueStr = convertToStr(value);
        return BooleanUtil.toBoolean(valueStr);
    }

}
