package com.ipower.framework.common.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

/**
 * @author kris
 * @date 2021/7/22
 */
public class JacksonJsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JacksonJsonUtil.class);

    private static final ObjectMapper objectMapper;

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");


    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        //序列化日期时间
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(TIME_FORMATTER));
        //反序列化日期时间
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TIME_FORMATTER));
        objectMapper.registerModule(javaTimeModule);
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());
//        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        //设置默认时区
        objectMapper.setTimeZone(TimeZone.getDefault());
//        objectMapper.registerModule(new JtsModule());

        /**
         * 序列换成json时,将所有的long变成string
         * 因为js中得数字类型不能包含所有的java long值
         */
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String toJSONString(Object obj) {
        String result = null;
        try {
            result =  objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("转换json错误", e);
        }
        return result;
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        T t = null;
        try {
            t = objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("解析json错误", e);
        }
        return t;
    }

    public static <T> T parseObject(byte[] json, Class<T> clazz) {
        T t = null;
        try {
            t = objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("解析json错误", e);
        }
        return t;
    }

    public static <T> T parseObject(String json, JavaType valueType) {
        T t = null;
        try {
            t = objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            logger.error("解析json错误", e);
        }
        return t;
    }

    public static <T> T parseObject(String json, TypeReference<T> valueTypeRef) {
        T t = null;
        try {
            t = objectMapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            logger.error("解析json错误", e);
        }
        return t;
    }

    public static <T> T parseObject(InputStream src, Class<T> clazz) {
        T t = null;
        try {
            t = objectMapper.readValue(src, clazz);
        } catch (Exception e) {
            logger.error("解析json错误", e);
        }
        return t;
    }

    public static <T> List<T> parseList(String json, Class<T> clazz) {
        JavaType javaType = JacksonJsonUtil.getObjectMapper().getTypeFactory().constructParametricType(List.class, clazz);
        List<T> t = null;
        try {
            t = objectMapper.readValue(json, javaType);
        } catch (Exception e) {
            logger.error("解析json错误", e);
        }
        return t;
    }

    /**
     * 手动解析json字符串
     * @param content
     * @return
     */
    public static JsonNode readTree(String content) {
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(content);
        } catch (JsonProcessingException e) {
            logger.error("解析json错误", e);
        }
        return jsonNode;
    }

    /**
     * 创建ObjectNode
     * @return
     */
    public static ObjectNode createObjectNode() {
        return objectMapper.createObjectNode();
    }

    public static byte[] toJsonByte(Object obj){
        byte[] result = null;
        try {
            result =  objectMapper.writeValueAsBytes(obj);
        } catch (Exception e) {
            logger.error("转换json错误", e);
        }
        return result;
    }


//    @Data
//    static class A{
//        String name;
//        String body;
//        Map<String,String> bMap;
//        public A(String name,String body,Map<String,String> map){
//            this.name=name;
//            this.body=body;
//            this.bMap=map;
//        };
//
//    }
//
//    public static void main(String[] args) {
//        A a = new A("a","b",new HashMap<String,String>());
//        String aStr = JsonUtil.toJSONString(a);
//        System.out.println(aStr);
//        JsonNode jsonNode = JsonUtil.readTree(aStr);
//        String aaStr = JsonUtil.toJSONString(jsonNode);
//        System.out.println(aaStr);
//        System.out.println(Arrays.toString(JsonUtil.toJsonByte(a)));
//        System.out.println(Arrays.toString(JsonUtil.toJsonByte(jsonNode)));
//        System.out.println(Arrays.toString(JsonUtil.toJsonByte(aaStr)));
//    }

}
