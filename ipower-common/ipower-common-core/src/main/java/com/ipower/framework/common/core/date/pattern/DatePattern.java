package com.ipower.framework.common.core.date.pattern;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

/**
 * 日期格式化类，提供常用的日期格式化对象<br>
 * 参考：<a href="https://www.ietf.org/rfc/rfc3339.txt">rfc3339</a>
 *
 * <p>所有的jdk日期格式模式字符串
 * <a href="https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/time/format/DateTimeFormatter.html">
 * <i>jdk date format pattern （Pattern Letters and Symbols） 日期格式模式字符串</i>
 * </a>
 * </p>
 *
 * <p>工具类，提供格式化字符串很多，但是对于具体什么含义，不够清晰，这里进行说明：</p>
 * <b>常见日期格式模式字符串：</b>
 * <ul>
 *    <li>yyyy-MM-dd                   示例：2022-08-05</li>
 *    <li>yyyy年MM月dd日                示例：2022年08月05日</li>
 *    <li>yyyy-MM-dd HH:mm:ss          示例：2022-08-05 12:59:59</li>
 *    <li>yyyy-MM-dd HH:mm:ss.SSS      示例：2022-08-05 12:59:59.559</li>
 * </ul>
 * <p>
 * 系统提供的，请查看，有大量定义好的格式化对象，可以直接使用，如：
 * {@link DateTimeFormatter#ISO_DATE}
 * {@link DateTimeFormatter#ISO_DATE_TIME}
 * 查看更多，请参阅上述官方文档
 * </p>
 *
 * @author Looly
 */
public class DatePattern {

    // region Normal
    //================================================== Normal ==================================================

    /**
     * 年格式：yyyy
     */
    public static final String NORM_YEAR_PATTERN = "yyyy";

    /**
     * 年月格式 {@link DateTimeFormatter}：yyyy
     */
    public static final DateTimeFormatter NORM_YEAR_FORMATTER = formatter(NORM_YEAR_PATTERN);

    /**
     * 年月格式：yyyy-MM
     */
    public static final String NORM_MONTH_PATTERN = "yyyy-MM";

    /**
     * 年月格式 {@link DateTimeFormatter}：yyyy-MM
     */
    public static final DateTimeFormatter NORM_MONTH_FORMATTER = formatter(NORM_MONTH_PATTERN);

    /**
     * 简单年月格式：yyyyMM
     */
    public static final String SIMPLE_MONTH_PATTERN = "yyyyMM";

    /**
     * 简单年月格式 {@link DateTimeFormatter}：yyyyMM
     */
    public static final DateTimeFormatter SIMPLE_MONTH_FORMATTER = formatter(SIMPLE_MONTH_PATTERN);

    /**
     * 标准日期格式：yyyy-MM-dd
     */
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：yyyy-MM-dd
     */
    public static final DateTimeFormatter NORM_DATE_FORMATTER = formatter(NORM_DATE_PATTERN);

    /**
     * 标准时间格式：HH:mm:ss
     */
    public static final String NORM_TIME_PATTERN = "HH:mm:ss";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：HH:mm:ss
     */
    public static final DateTimeFormatter NORM_TIME_FORMATTER = formatter(NORM_TIME_PATTERN);

    /**
     * 标准日期时间格式，精确到分：yyyy-MM-dd HH:mm
     */
    public static final String NORM_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：yyyy-MM-dd HH:mm
     */
    public static final DateTimeFormatter NORM_DATETIME_MINUTE_FORMATTER = formatter(NORM_DATETIME_MINUTE_PATTERN);

    /**
     * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准日期时间格式，精确到秒 {@link DateTimeFormatter}：yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter NORM_DATETIME_FORMATTER = formatter(NORM_DATETIME_PATTERN);

    /**
     * 标准日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 标准日期时间格式，精确到毫秒 {@link DateTimeFormatter}：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final DateTimeFormatter NORM_DATETIME_MS_FORMATTER = formatter(NORM_DATETIME_MS_PATTERN);

    /**
     * ISO8601日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss,SSS
     */
    public static final String NORM_DATETIME_COMMA_MS_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：yyyy-MM-dd HH:mm:ss,SSS
     */
    public static final DateTimeFormatter NORM_DATETIME_COMMA_MS_FORMATTER = formatter(NORM_DATETIME_COMMA_MS_PATTERN);

    /**
     * 标准日期格式：yyyy年MM月dd日
     */
    public static final String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：yyyy年MM月dd日
     */
    public static final DateTimeFormatter CHINESE_DATE_FORMATTER = formatter(CHINESE_DATE_PATTERN);

    /**
     * 标准日期格式：yyyy年MM月dd日HH时mm分ss秒
     */
    public static final String CHINESE_DATE_TIME_PATTERN = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：yyyy年MM月dd日HH时mm分ss秒
     */
    public static final DateTimeFormatter CHINESE_DATE_TIME_FORMATTER = formatter(CHINESE_DATE_TIME_PATTERN);
    // endregion

    // region Pure
    //================================================== Pure ==================================================

    /**
     * 标准日期格式：yyyyMMdd
     */
    public static final String PURE_DATE_PATTERN = "yyyyMMdd";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：yyyyMMdd
     */
    public static final DateTimeFormatter PURE_DATE_FORMATTER = formatter(PURE_DATE_PATTERN);

    /**
     * 标准日期格式：HHmmss
     */
    public static final String PURE_TIME_PATTERN = "HHmmss";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：HHmmss
     */
    public static final DateTimeFormatter PURE_TIME_FORMATTER = formatter(PURE_TIME_PATTERN);

    /**
     * 标准日期格式：yyyyMMddHHmmss
     */
    public static final String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：yyyyMMddHHmmss
     */
    public static final DateTimeFormatter PURE_DATETIME_FORMATTER = formatter(PURE_DATETIME_PATTERN);

    /**
     * 标准日期格式：yyyyMMddHHmmssSSS
     */
    public static final String PURE_DATETIME_MS_PATTERN = "yyyyMMddHHmmssSSS";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：yyyyMMddHHmmssSSS<br>
     * see https://stackoverflow.com/questions/22588051/is-java-time-failing-to-parse-fraction-of-second
     * jdk8 bug at: https://bugs.openjdk.java.net/browse/JDK-8031085
     */
    public static final DateTimeFormatter PURE_DATETIME_MS_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern(DatePattern.PURE_DATETIME_PATTERN).appendValue(ChronoField.MILLI_OF_SECOND, 3).toFormatter();
    // endregion

    /**
     * 创建并为 {@link DateTimeFormatter} 赋予默认时区和位置信息，默认值为系统默认值。
     *
     * @param pattern 日期格式
     * @return {@link DateTimeFormatter}
     */
    public static DateTimeFormatter formatter(final String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.getDefault()).withZone(ZoneId.systemDefault());
    }
}
