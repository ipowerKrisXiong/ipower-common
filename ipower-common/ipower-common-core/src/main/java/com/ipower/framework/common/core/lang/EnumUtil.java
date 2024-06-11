package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.bean.BeanUtil;

import java.util.stream.Stream;

import static com.ipower.framework.common.core.lang.ObjectUtil.isNotNull;
import static com.ipower.framework.common.core.lang.ObjectUtil.nullToDefault;


/**
 * 枚举处理的工具类。
 * 该类提供了根据name、ordinal、自定义属性来获取枚举元素的常用方法。
 *
 * @author kris
 * @since 1.0.0
 */
public final class EnumUtil {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private EnumUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 根据枚举的name值获取枚举元素，并且会忽略name的大小写，若枚举中不存在name对应的元素，返回null。
     * <p>
     * 同样，使用{@link Enum#valueOf(Class, String)}也可以获取到枚举元素。
     * 区别就是，{@link Enum#valueOf(Class, String)}不会忽略大小写，并且在枚举中找不到name对应的元素，会抛{@link IllegalArgumentException}异常。
     *
     * @param clazz 枚举的class类型
     * @param name  枚举name值
     * @param <E>   用来约束class类型必须为枚举的泛型
     * @return name对应的枚举元素
     */
    public static <E extends Enum<E>> E getEnum(Class<E> clazz, String name) {
        return getEnumStream(clazz).filter(e -> e.name().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    /**
     * 根据枚举的ordinal值获取枚举元素，若枚举中不存在ordinal对应的元素，返回null。
     *
     * @param clazz   枚举的class类型
     * @param ordinal 枚举ordinal值
     * @param <E>     用来约束class类型必须为枚举的泛型
     * @return ordinal对应的枚举元素
     */
    public static <E extends Enum<E>> E getEnum(Class<E> clazz, int ordinal) {
        return getEnumStream(clazz).filter(e -> e.ordinal() == ordinal).findAny().orElse(null);
    }

    /**
     * 根据枚举的定制属性与定制值获取枚举元素。若对应的属性不存在，会抛出{@link NoSuchMethodException}异常，若对应的值不存在，会返回null。
     *
     * @param clazz    枚举的class类型
     * @param property 枚举的定制属性
     * @param value    枚举定制的值
     * @param <E>      用来约束class类型必须为枚举的泛型
     * @return 定制属性与定制值对应的枚举元素
     */
    public static <E extends Enum<E>> E getEnum(Class<E> clazz, String property, Object value) {
        return getEnumStream(clazz).filter(e -> equalEnumValue(e, property, value)).findAny().orElse(null);
    }

    /**
     * 根据枚举的定制属性与定制值获取枚举元素。若对应的属性不存在，会抛出{@link NoSuchMethodException}异常，若对应的值不存在，会返回默认值。
     *
     * @param clazz        枚举的class类型
     * @param property     枚举的定制属性
     * @param value        枚举定制的值
     * @param defaultValue 枚举默认值
     * @param <E>          用来约束class类型必须为枚举的泛型
     * @return 定制属性与定制值对应的枚举元素
     */
    public static <E extends Enum<E>> E getEnum(Class<E> clazz, String property, Object value, E defaultValue) {
        return nullToDefault(getEnum(clazz, property, value), defaultValue);
    }

    /**
     * 根据枚举类类型，获取枚举的stream流
     */
    private static <E extends Enum<E>> Stream<E> getEnumStream(Class<E> clazz) {
        Validate.notNull(clazz, "The enum clazz must not be null");
        return Stream.of(clazz.getEnumConstants());
    }

    /**
     * 比较枚举的数据值，是否与参数的值一致
     */
    private static <E extends Enum<E>> boolean equalEnumValue(E e, String property, Object value) {
        Object obj = BeanUtil.getProperty(e, property);
        return isNotNull(obj) && ObjectUtil.equals(obj, value);
    }
}
