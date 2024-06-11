package com.ipower.framework.common.core.convert.impl;

import com.ipower.framework.common.core.convert.AbstractConverter;
import com.ipower.framework.common.core.lang.CharsetUtil;

import java.io.Serial;
import java.nio.charset.Charset;

/**
 * 编码对象转换器
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @author kris
 * @since 1.0.0
 */
public class CharsetConverter extends AbstractConverter<Charset> {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected Charset convertInternal(Object value) {
        return CharsetUtil.charset(convertToStr(value));
    }

}
