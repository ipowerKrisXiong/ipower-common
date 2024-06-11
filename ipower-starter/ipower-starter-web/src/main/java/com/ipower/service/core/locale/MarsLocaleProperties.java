package com.ipower.service.core.locale;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;
import java.util.TimeZone;

/**
 * locale代表本地方言，主要适配的是语言信息，可以通过设置强制固定语言类型
 * timeZone代表的是时区，不进行配置强制指定，默认跟随系统
 */
@ConfigurationProperties(prefix = "mars.locale")
@Data
public class MarsLocaleProperties {

    /**
     * 打开时区自动转换，打开后会根header=timeZone的传入情况构造SimpleTimeZoneAwareLocaleContext，设置请求本地时区
     */
    private Boolean supportTimeZoneTrans =false;

    /**
     * springmvc在请求的时候，请求来源的字符串格式
     *     "Timestr", "格式化为日期字符串"),
     *     "TimestampSec", "格式化为时间戳秒"),
     *     "TimestampMilli", "格式化为时间戳毫秒");
     *     只对转LocalDateTime的时候生效
     */
    private String deserialTimeFormatType ="TimestampSec";


    /**
     * springmvc在返回的时候，序列化成字符串的格式
     *     "Timestr", "格式化为日期字符串"),
     *     "TimestampSec", "格式化为时间戳秒"),
     *     "TimestampMilli", "格式化为时间戳毫秒");
     *     只对转LocalDateTime的时候生效
     */
    private String serialTimeFormatType ="TimestampSec";

    /**
     * 是否固定本地方言和时区信息
     */
    private Boolean fixedLocalZone=false;
    /**
     * 本地方言进行确定,根据配置文件指定语言区域信息
     * zh_CN
     */
    private String fixedLocale = Locale.getDefault().toString();
    /**
     * 本地方言进行确定,根据配置文件指定语言区域信息
     * Asia/Shanghai
     */
    private String fixedTimeZone = TimeZone.getDefault().toString();


}
