package com.ipower.framework.common.core.bean;

import com.ipower.framework.common.core.lang.ObjectUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.ipower.framework.common.core.bean.PropertyUtil.getPropertyDescriptors;
import static com.ipower.framework.common.core.lang.ObjectUtil.isNull;


/**
 * bean属性方法工具
 *
 * @author kris
 */
@Slf4j
public class BeanMethodUtil {

    /**
     * 用来缓存对象复制时的转换对象
     */
    private static final Map<String, List<BeanMethod>> CACHE_BEAN_METHOD = new ConcurrentHashMap<>();

    /**
     * 私有化构造函数
     */
    private BeanMethodUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 缓存获取beanMethod属性集合
     */
    public static List<BeanMethod> beanMethodCache(@NonNull Object bean) {
        String key = bean.getClass().getName();
        if (isNull(CACHE_BEAN_METHOD.get(key))) {
            CACHE_BEAN_METHOD.put(key, buildBeanMethod(bean.getClass(), p -> ObjectUtil.equals(p.getPropertyType(), String.class)));
        }
        return CACHE_BEAN_METHOD.get(key);
    }

    /**
     * 根据bean类型、断言条件构造bean的属性方法集合
     *
     * @param clazz     bean类型
     * @param predicate 断言条件
     * @return List<BeanMethod>
     */
    public static List<BeanMethod> buildBeanMethod(Class<?> clazz, Predicate<PropertyDescriptor> predicate) {
        return Arrays.stream(getPropertyDescriptors(clazz)).filter(predicate).map(d -> buildBeanMethod(clazz, d))
                .collect(Collectors.toList());
    }

    /**
     * 根据bean类型、属性信息构造bean的属性方法对象
     */
    private static BeanMethod buildBeanMethod(Class<?> clazz, PropertyDescriptor descriptor) {
        return new BeanMethod(clazz, descriptor.getName(), descriptor.getReadMethod(), descriptor.getWriteMethod());
    }
}
