package com.ipower.service.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ipower.framework.common.core.util.JacksonJsonUtil;
import com.ipower.service.core.locale.MarsLocaleProperties;
import com.ipower.service.core.reqformat.BigDecimalTransUtil;
import com.ipower.service.core.reqformat.CustomLocalDateTimeTransUtil;
import com.ipower.service.core.reqformat.TimeFormatTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

/**
 * 这儿的转换器是用来转化springmvc中 @RequestParam和@PathVariable的入参转换
 * 如果是RequestBody，那么通过配置ObjectMapper（这个玩意儿会注入到Jackson的HttpMessagConverter里面，即MappingJackson2HttpMessageConverter中）来实现Json格式数据的序列化和反序列化；(JsonAutoConfiguration中的配置)
 * 如果是RequestParam或者PathVariable类型的参数，通过配置Converter实现参数转换（这些Converter会注入到ConversionService中）。
 *
 *
 * 参考链接
 * 链接：https://www.jianshu.com/p/b52db905f020
 */
@Configuration
public class SpringMvcGetConvertConfiguration {



    //下面的时区自动转换暂时只支持LocalDateTime,LocalDate,LocalTime

    /**
     * LocalDate转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDate> localDateConverter(MarsLocaleProperties marsLocaleProperties) {
        return new Converter<String,LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                //自动转时区使用CustomLocalDateTimeTransUtil
                if(marsLocaleProperties!=null&&marsLocaleProperties.getSupportTimeZoneTrans()){
                    TimeFormatTypeEnum timeFormatType = TimeFormatTypeEnum.valueOfCode(marsLocaleProperties.getDeserialTimeFormatType());
                    return CustomLocalDateTimeTransUtil.timeStampOrStrTolocalDate(timeFormatType,source,CustomLocalDateTimeTransUtil.DATE_FORMATTER);
                //否则用普通转换
                }else{
                    return LocalDate.parse(source, CustomLocalDateTimeTransUtil.DATE_FORMATTER);
                }
            }
        };
    }

    /**
     * LocalDateTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalDateTime> localDateTimeConverter(MarsLocaleProperties marsLocaleProperties) {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                //自动转时区使用CustomLocalDateTimeTransUtil
                if(marsLocaleProperties!=null&&marsLocaleProperties.getSupportTimeZoneTrans()){
                    TimeFormatTypeEnum timeFormatType = TimeFormatTypeEnum.valueOfCode(marsLocaleProperties.getDeserialTimeFormatType());
                    return CustomLocalDateTimeTransUtil.timeStampOrStrToLocalDateTime(timeFormatType,source,CustomLocalDateTimeTransUtil.DATE_TIME_FORMATTER);
                }else{
                    //这儿异常抛出最终会变成MethodArgumentTypeMismatchException在统一异常中做处理
                    //返回:{
                    //    "code": 406,
                    //    "msg": "helloDateTime类型错误"
                    //}
                    return LocalDateTime.parse(source, CustomLocalDateTimeTransUtil.DATE_TIME_FORMATTER);
                }
            }
        };
    }

    /**
     * LocalTime转换器，用于转换RequestParam和PathVariable参数
     */
    @Bean
    public Converter<String, LocalTime> localTimeConverter(MarsLocaleProperties marsLocaleProperties) {
        return new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                //自动转时区使用CustomLocalDateTimeTransUtil
                if(marsLocaleProperties!=null&&marsLocaleProperties.getSupportTimeZoneTrans()){
                    TimeFormatTypeEnum timeFormatType = TimeFormatTypeEnum.valueOfCode(marsLocaleProperties.getDeserialTimeFormatType());
                    return CustomLocalDateTimeTransUtil.strToLocalTime(timeFormatType,source,CustomLocalDateTimeTransUtil.TIME_FORMATTER);
                }else{
                    return LocalTime.parse(source, CustomLocalDateTimeTransUtil.TIME_FORMATTER);
                }
            }
        };
    }

//    /**
     //不再支持Date转换器，去除了
//     * Date转换器，用于转换RequestParam和PathVariable参数
//     */
//    @Bean
//    public Converter<String, Date> dateConverter() {
//        return new Converter<String, Date>() {
//            @Override
//            public Date convert(String source) {
//                if(StringUtils.isNotBlank(source)){
//                    SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
//                    try {
//                        return format.parse(source);
//                    } catch (ParseException e) {
//                        throw new RuntimeException(e);
//                    }
//                }else{
//                    return null;
//                }
//            }
//        };
//    }

    //支持url中传数组的转换,比如a=[1,2,3]，这个不和springmvc默认的转数组方法冲突，默认的转发是a=1&a=2
    @Bean
    public Converter<String, List<?>> listConverter() {
        return new Converter<String, List<?>>() {
            @Override
            public List<?> convert(String source) {
                if(StringUtils.isNotBlank(source)){
                    return JacksonJsonUtil.parseObject(source,new TypeReference<List<?>>(){});
                }else{
                    return null;
                }
            }
        };
    }

    @Bean
    public Converter<String, YearMonth> localYearMonthConverter() {
        return new Converter<String, YearMonth>() {
            @Override
            public YearMonth convert(String source) {
                if(StringUtils.isNotBlank(source))
                    return YearMonth.parse(source, CustomLocalDateTimeTransUtil.YEARMONTH_FORMATTER);
                else
                    return null;
            }
        };
    }

    @Bean
    public Converter<String, BigDecimal> bigDecimalConverter() {
        return new Converter<String, BigDecimal>() {
            @Override
            public BigDecimal convert(String source) {
                if(StringUtils.isNotBlank(source))
                    return BigDecimalTransUtil.deserializer(source);
                else
                    return null;
            }
        };
    }



}
