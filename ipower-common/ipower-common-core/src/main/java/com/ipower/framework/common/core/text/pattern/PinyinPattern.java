package com.ipower.framework.common.core.text.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class description goes here.
 *
 * @author kris
 */
@Getter
@AllArgsConstructor
public enum PinyinPattern {

    NONE_TONE(1, "WITHOUT_TONE", "不带音调"),
    NONE_TONE_FU(2, "WITHOUT_TONE", "不带音调，且转换后首字母大写"),
    TONE_MARK(3, "WITH_TONE_MARK", "带音调"),
    TONE_MARK_FU(4, "WITH_TONE_MARK", "带音调，且转换后首字母大写"),
    TONE_NUMBER(5, "WITH_TONE_NUMBER", "用数字表示音调"),
    TONE_NUMBER_FU(6, "WITH_TONE_NUMBER", "用数字表示音调，且转换后首字母大写");

    private final int code;

    private final String format;

    private final String description;

}
