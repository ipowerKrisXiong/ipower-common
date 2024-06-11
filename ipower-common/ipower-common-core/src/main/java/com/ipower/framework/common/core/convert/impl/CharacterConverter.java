package com.ipower.framework.common.core.convert.impl;

import com.ipower.framework.common.core.convert.AbstractConverter;
import com.ipower.framework.common.core.lang.BooleanUtil;
import com.ipower.framework.common.core.lang.StringUtil;

import java.io.Serial;

/**
 * 字符转换器
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @author kris
 * @since 1.0.0
 */
public class CharacterConverter extends AbstractConverter<Character> {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected Character convertInternal(Object value) {
        if (value instanceof Boolean) {
            return BooleanUtil.toCharacter((Boolean) value);
        } else {
            final String valueStr = convertToStr(value);
            if (StringUtil.isNotEmpty(valueStr)) {
                return valueStr.charAt(0);
            }
        }
        return null;
    }

}
