package com.ipower.framework.common.core.text;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

import java.util.List;

/**
 * Class description goes here.
 *
 * @author kris
 */
public final class JsonUtil {

    private JsonUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * json序列转换规则
     */
    private static final JSONWriter.Feature[] SERIALIZER_FEATURES = {
            // 格式化
            JSONWriter.Feature.PrettyFormat,
            // 输出空置字段
            JSONWriter.Feature.WriteMapNullValue,
            // list字段如果为null，输出为[]，而不是null
            JSONWriter.Feature.WriteNullListAsEmpty,
            // 字符类型字段如果为null，输出为""，而不是null
            JSONWriter.Feature.WriteNullStringAsEmpty
    };

    public static String toJson(Object object) {
        return JSON.toJSONString(object, "yyyy-MM-dd HH:mm:ss", SERIALIZER_FEATURES);
    }

    public static String toJsonSimple(Object object) {
        return JSON.toJSONString(object);
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static <T> List<T> toArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
}
