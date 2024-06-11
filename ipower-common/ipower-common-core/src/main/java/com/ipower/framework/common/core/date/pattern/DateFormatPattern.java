package com.ipower.framework.common.core.date.pattern;

import lombok.Getter;

/**
 * Class description goes here.
 *
 * @author kris
 */
@Getter
public enum DateFormatPattern {
    COMMON_DATE("yyyy-MM-dd", "^[1-9]\\d{3}-([1-9]|0[1-9]|1[0-2])-([1-9]|0[1-9]|[1-2][0-9]|3[0-1])$", "匹配yyyy-MM-dd，y-M-d"),
    COMMON_DATE_TIME("yyyy-MM-dd HH:mm:ss", "^[1-9]\\d{3}-([1-9]|0[1-9]|1[0-2])-([1-9]|0[1-9]|[1-2][0-9]|3[0-1])\\s(20|21|22|23|[0-1]\\d|\\d):[0-5]\\d:[0-5]\\d$", "匹配yyyy-MM-dd HH:mm:ss，y-M-d HH:mm:ss"),
    COMMON_DATE_TIME_MSEC("yyyy-MM-dd HH:mm:ss.SSS", "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d\\.\\d{3}$", "匹配yyyy-MM-dd HH:mm:ss.SSS，y-M-d HH:mm:ss.SSS"),
    COMMON_DATE_SHORT_TIME("yyyy-MM-dd HH:mm", "^[1-9]\\d{3}-([1-9]|0[1-9]|1[0-2])-([1-9]|0[1-9]|[1-2][0-9]|3[0-1])\\s(20|21|22|23|[0-1]\\d|\\d):[0-5]\\d$", "匹配yyyy-MM-dd HH:mm，y-M-d HH:mm"),
    COMMON_SHORT_DATE("yy-MM-dd", "^\\d{2}-([1-9]|0[1-9]|1[0-2])-([1-9]|0[1-9]|[1-2][0-9]|3[0-1])$", "匹配yy-MM-dd，yy-M-d"),
    COMMON_SHORT_TIME("HH:mm", "^(20|21|22|23|[0-1]\\d|\\d):[0-5]\\d$", "匹配HH:mm"),
    COMMON_TIME("HH:mm:ss", "^(20|21|22|23|[0-1]\\d|\\d):[0-5]\\d:[0-5]\\d$", "匹配HH:mm:ss"),
    SLANT_DATE("yyyy/MM/dd", "^[1-9]\\d{3}/([1-9]|0[1-9]|1[0-2])/([1-9]|0[1-9]|[1-2][0-9]|3[0-1])$", "匹配yyyy/MM/dd，y/M/d"),
    SLANT_DATE_TIME("yyyy/MM/dd HH:mm:ss", "^[1-9]\\d{3}/([1-9]|0[1-9]|1[0-2])/([1-9]|0[1-9]|[1-2][0-9]|3[0-1])\\s(20|21|22|23|[0-1]\\d|\\d):[0-5]\\d:[0-5]\\d$", "匹配yyyy/MM/dd HH:mm:ss，y/M/d HH:mm:ss"),
    SLANT_DATE_TIME_MSEC("yyyy/MM/dd HH:mm:ss.SSS", "^[1-9]\\d{3}/([1-9]|0[1-9]|1[0-2])/([1-9]|0[1-9]|[1-2][0-9]|3[0-1])\\s(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d\\.\\d{3}$", "匹配yyyy/MM/dd HH:mm:ss.SSS，y/M/d HH:mm:ss.SSS"),
    SLANT_DATE_SHORT_TIME("yyyy/MM/dd HH:mm", "^[1-9]\\d{3}/([1-9]|0[1-9]|1[0-2])/([1-9]|0[1-9]|[1-2][0-9]|3[0-1])\\s(20|21|22|23|[0-1]\\d|\\d):[0-5]\\d$", "匹配yyyy/MM/dd HH:mm，y/M/d HH:mm"),
    SLANT_SHORT_DATE("yy/MM/dd", "^\\d{2}/([1-9]|0[1-9]|1[0-2])/([1-9]|0[1-9]|[1-2][0-9]|3[0-1])$", "匹配yy/MM/dd，yy/M/d");

    private final String pattern;
    private final String matched;
    private final String description;

    DateFormatPattern(String pattern, String matched, String description) {
        this.pattern = pattern;
        this.matched = matched;
        this.description = description;
    }

}
