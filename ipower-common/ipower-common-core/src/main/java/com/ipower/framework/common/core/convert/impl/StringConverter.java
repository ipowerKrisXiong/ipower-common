package com.ipower.framework.common.core.convert.impl;

import com.ipower.framework.common.core.convert.AbstractConverter;

import java.io.Serial;

/**
 * 字符串转换器
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @author kris
 * @since 1.0.0
 */
public class StringConverter extends AbstractConverter<String> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected String convertInternal(Object value) {
        return convertToStr(value);
    }

}
