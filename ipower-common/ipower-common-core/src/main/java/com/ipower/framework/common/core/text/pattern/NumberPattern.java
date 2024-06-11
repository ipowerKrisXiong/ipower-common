package com.ipower.framework.common.core.text.pattern;

import lombok.Getter;

/**
 * Class description goes here.
 *
 * @author kris
 */
@Getter
public enum NumberPattern {

    DECIMAL_NON_POINT("#", "整数,不含小数"),
    DECIMAL_ONE_POINT("#0.00", "小数,含1位小数点"),
    DECIMAL_TWO_POINT("#0.00", "小数,含2位小数点"),
    PERCENT_NON_POINT("##%", "百分数的表达式,不带小数点"),
    PERCENT_ONE_POINT("#0.0%", "百分数的表达式,含1位小数点,"),
    PERCENT_TWO_POINT("#0.00%", "百分数的表达式,含2位小数点");

    private final String pattern;
    private final String description;

    NumberPattern(String pattern, String description) {
        this.pattern = pattern;
        this.description = description;
    }

}
