package com.ipower.service.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.ipower.framework.common.core.util.JacksonJsonUtil;
import com.ipower.service.core.locale.MarsLocaleProperties;
import com.ipower.service.core.reqformat.*;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimeZone;

/**
 * @author hk
 * @date 2021/7/22
 * 参考文章：https://blog.csdn.net/Heron22/article/details/109512976
 * 这个地方的转换器目前只能用于springmvc出参和入参解析，但是无法用于thymeleaf模板，thymeleaf模板是单独的
 *
 *  如果想达到根据用户当前区域locale来自动返回该区域的timeZone序列化后的时间文本
 *  可以参考下面这篇文章 https://blog.csdn.net/tutuxfsh/article/details/127915291
 *
 */
@Configuration
@AutoConfigureBefore(value = WebMvcAutoConfiguration.class)
public class JsonAutoConfiguration {

    @Bean
    public ObjectMapper objectMapper(){
        return JacksonJsonUtil.getObjectMapper();
    }

    //如果配置了按locale自动转换时区，则
//    @ConditionalOnProperty(prefix = "mars.locale", name = "autoLocalTimeZoneTrans",havingValue="true", matchIfMissing = false)
    //设置转换器所用ObjectMapper为自己定制的OjbMapper
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(MarsLocaleProperties marsLocaleProperties) {
        MappingJackson2HttpMessageConverter mappingJsonpHttpMessageConverter = new MappingJackson2HttpMessageConverter(
                buildObjectMapper(marsLocaleProperties));
        return mappingJsonpHttpMessageConverter;
    }


    /**
     * @return 建立时间格式化为字符串的objMapper
     */
    private ObjectMapper buildObjectMapper( MarsLocaleProperties marsLocaleProperties){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        //Decimal采用PlainStr方式(原字符串)。默认是科学计数法
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN,true);

        //设置默认日期格式化方式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        //开启时区自动转换功能
        if(marsLocaleProperties.getSupportTimeZoneTrans()){

            //序列化（toStr）格式
            TimeFormatTypeEnum serialTimeFormatType = TimeFormatTypeEnum.valueOfCode(marsLocaleProperties.getSerialTimeFormatType());
            //返序列化(str to time)格式
            TimeFormatTypeEnum deserialTimeFormatType = TimeFormatTypeEnum.valueOfCode(marsLocaleProperties.getDeserialTimeFormatType());

            //序列化日期时间
            javaTimeModule.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer(serialTimeFormatType,CustomLocalDateTimeTransUtil.DATE_TIME_FORMATTER));
            javaTimeModule.addSerializer(LocalDate.class, new CustomLocalDateSerializer(serialTimeFormatType,CustomLocalDateTimeTransUtil.DATE_FORMATTER));
            javaTimeModule.addSerializer(LocalTime.class, new CustomLocalTimeSerializer(serialTimeFormatType,CustomLocalDateTimeTransUtil.TIME_FORMATTER));

            //反序列化日期时间
            javaTimeModule.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer(deserialTimeFormatType,CustomLocalDateTimeTransUtil.DATE_TIME_FORMATTER));
            javaTimeModule.addDeserializer(LocalDate.class, new CustomLocalDateDeserializer(deserialTimeFormatType,CustomLocalDateTimeTransUtil.DATE_FORMATTER));
            javaTimeModule.addDeserializer(LocalTime.class, new CustomLocalTimeDeserializer(deserialTimeFormatType,CustomLocalDateTimeTransUtil.TIME_FORMATTER));

        //默认,按以下格式进行字符串->localDateTime之间转换
        }else{
            //序列化日期时间 序列化为字符串
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(CustomLocalDateTimeTransUtil.DATE_TIME_FORMATTER));
            javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(CustomLocalDateTimeTransUtil.DATE_FORMATTER));
            javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(CustomLocalDateTimeTransUtil.TIME_FORMATTER));
            //反序列化日期时间  序列化为字符串
            javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(CustomLocalDateTimeTransUtil.DATE_TIME_FORMATTER));
            javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(CustomLocalDateTimeTransUtil.DATE_FORMATTER));
            javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(CustomLocalDateTimeTransUtil.TIME_FORMATTER));
        }

        objectMapper.registerModule(javaTimeModule);
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());
        //设置默认时区
        objectMapper.setTimeZone(TimeZone.getDefault());

        /**
         * 注册转换器
         * 序列换成json时,将所有的long变成string
         * 因为js中得数字类型不能包含所有的java long值
         */
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addDeserializer(BigDecimal.class, new CustomBigDecimalDeserializer());
        simpleModule.addSerializer(BigDecimal.class, new CustomBigDecimalSerializer());
        objectMapper.registerModule(simpleModule);

        return objectMapper;
    }

}

//另一种写法：
//public class XXXConverterConfig extends WebMvcConfigurerAdapter {
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//        jackson2HttpMessageConverter.setObjectMapper(JsonUtil.getObjectMapper());
//        converters.add(jackson2HttpMessageConverter);
//    }
//}

