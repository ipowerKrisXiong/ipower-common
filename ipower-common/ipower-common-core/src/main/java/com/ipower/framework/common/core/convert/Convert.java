package com.ipower.framework.common.core.convert;

import com.alibaba.fastjson2.TypeReference;
import com.ipower.framework.common.core.convert.impl.CollectionConverter;
import com.ipower.framework.common.core.convert.impl.GenericEnumConverter;
import com.ipower.framework.common.core.exception.ConvertException;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 类型转换器
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @author kris
 * @since 1.0.0
 */
public class Convert {
    /**
     * 私有化构造函数，禁止实例化该类
     */
    private Convert() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 转换为字符串<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static String toString(Object value) {
        return toString(value, null);
    }

    /**
     * 转换为字符串<br>
     * 如果给定的值为null，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static String toString(Object value, String defaultValue) {
        return convertQuietly(String.class, value, defaultValue);
    }

    /**
     * 转换为String数组
     *
     * @param value 被转换的值
     * @return String数组
     */
    public static String[] toStringArray(Object value) {
        return convert(String[].class, value);
    }

    /**
     * 转换为字符<br>
     * 如果给定的值为null，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Character toChar(Object value, Character defaultValue) {
        return convertQuietly(Character.class, value, defaultValue);
    }

    /**
     * 转换为字符<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Character toChar(Object value) {
        return toChar(value, null);
    }

    /**
     * 转换为Character数组
     *
     * @param value 被转换的值
     * @return Character数组
     */
    public static Character[] toCharArray(Object value) {
        return convert(Character[].class, value);
    }

    /**
     * 转换为byte<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Byte toByte(Object value, Byte defaultValue) {
        return convertQuietly(Byte.class, value, defaultValue);
    }

    /**
     * 转换为byte<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Byte toByte(Object value) {
        return toByte(value, null);
    }

    /**
     * 转换为Byte数组
     *
     * @param value 被转换的值
     * @return Byte数组
     */
    public static Byte[] toByteArray(Object value) {
        return convert(Byte[].class, value);
    }

    /**
     * 转换为Short<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Short toShort(Object value, Short defaultValue) {
        return convertQuietly(Short.class, value, defaultValue);
    }

    /**
     * 转换为Short<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Short toShort(Object value) {
        return toShort(value, null);
    }

    /**
     * 转换为Short数组
     *
     * @param value 被转换的值
     * @return Short数组
     */
    public static Short[] toShortArray(Object value) {
        return convert(Short[].class, value);
    }

    /**
     * 转换为Number<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Number toNumber(Object value, Number defaultValue) {
        return convertQuietly(Number.class, value, defaultValue);
    }

    /**
     * 转换为Number<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Number toNumber(Object value) {
        return toNumber(value, null);
    }

    /**
     * 转换为Number数组
     *
     * @param value 被转换的值
     * @return Number数组
     */
    public static Number[] toNumberArray(Object value) {
        return convert(Number[].class, value);
    }

    /**
     * 转换为int<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Integer toInteger(Object value, Integer defaultValue) {
        return convertQuietly(Integer.class, value, defaultValue);
    }

    /**
     * 转换为int<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Integer toInteger(Object value) {
        return toInteger(value, null);
    }

    /**
     * 转换为Integer数组<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Integer[] toIntegerArray(Object value) {
        return convert(Integer[].class, value);
    }

    /**
     * 转换为long<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Long toLong(Object value, Long defaultValue) {
        return convertQuietly(Long.class, value, defaultValue);
    }

    /**
     * 转换为long<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Long toLong(Object value) {
        return toLong(value, null);
    }

    /**
     * 转换为Long数组<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Long[] toLongArray(Object value) {
        return convert(Long[].class, value);
    }

    /**
     * 转换为double<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Double toDouble(Object value, Double defaultValue) {
        return convertQuietly(Double.class, value, defaultValue);
    }

    /**
     * 转换为double<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Double toDouble(Object value) {
        return toDouble(value, null);
    }

    /**
     * 转换为Double数组<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Double[] toDoubleArray(Object value) {
        return convert(Double[].class, value);
    }

    /**
     * 转换为Float<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Float toFloat(Object value, Float defaultValue) {
        return convertQuietly(Float.class, value, defaultValue);
    }

    /**
     * 转换为Float<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Float toFloat(Object value) {
        return toFloat(value, null);
    }

    /**
     * 转换为Float数组<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Float[] toFloatArray(Object value) {
        return convert(Float[].class, value);
    }

    /**
     * 转换为boolean<br>
     * String支持的值为：true、false、yes、ok、no，1,0 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Boolean toBool(Object value, Boolean defaultValue) {
        return convertQuietly(Boolean.class, value, defaultValue);
    }

    /**
     * 转换为boolean<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Boolean toBool(Object value) {
        return toBool(value, null);
    }

    /**
     * 转换为Boolean数组<br>
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Boolean[] toBooleanArray(Object value) {
        return convert(Boolean[].class, value);
    }

    /**
     * 转换为BigInteger<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static BigInteger toBigInteger(Object value, BigInteger defaultValue) {
        return convertQuietly(BigInteger.class, value, defaultValue);
    }

    /**
     * 转换为BigInteger<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static BigInteger toBigInteger(Object value) {
        return toBigInteger(value, null);
    }

    /**
     * 转换为BigDecimal<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
        return convertQuietly(BigDecimal.class, value, defaultValue);
    }

    /**
     * 转换为BigDecimal<br>
     * 如果给定的值为空，或者转换失败，返回null<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static BigDecimal toBigDecimal(Object value) {
        return toBigDecimal(value, null);
    }

    /**
     * 转换为Enum对象<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     *
     * @param <E>          枚举类型
     * @param clazz        Enum的Class
     * @param value        值
     * @param defaultValue 默认值
     * @return Enum
     */
    public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value, E defaultValue) {
        return (new GenericEnumConverter<>(clazz)).convertQuietly(value, defaultValue);
    }

    /**
     * 转换为Enum对象<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     *
     * @param <E>   枚举类型
     * @param clazz Enum的Class
     * @param value 值
     * @return Enum
     */
    public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value) {
        return toEnum(clazz, value, null);
    }

    /**
     * 转换为集合类
     *
     * @param collectionType 集合类型
     * @param elementType    集合中元素类型
     * @param value          被转换的值
     * @return {@link Collection}
     */
    public static Collection<?> toCollection(Class<?> collectionType, Class<?> elementType, Object value) {
        return new CollectionConverter(collectionType, elementType).convert(value, null);
    }

    /**
     * 转换为ArrayList，元素类型默认Object
     *
     * @param value 被转换的值
     * @return {@link List}
     */
    public static List<?> toList(Object value) {
        return convert(List.class, value);
    }

    /**
     * 转换为ArrayList
     *
     * @param <T>         元素类型
     * @param elementType 集合中元素类型
     * @param value       被转换的值
     * @return {@link List}
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> toList(Class<T> elementType, Object value) {
        return (List<T>) toCollection(ArrayList.class, elementType, value);
    }

    /**
     * 转换值为指定类型
     *
     * @param <T>   目标类型
     * @param type  类型
     * @param value 值
     * @return 转换后的值
     * @throws ConvertException 转换器不存在
     */
    public static <T> T convert(Class<T> type, Object value) throws ConvertException {
        return convert((Type) type, value);
    }

    /**
     * 转换值为指定类型
     *
     * @param <T>       目标类型
     * @param reference 类型参考，用于持有转换后的泛型类型
     * @param value     值
     * @return 转换后的值
     * @throws ConvertException 转换器不存在
     */
    public static <T> T convert(TypeReference<T> reference, Object value) throws ConvertException {
        return convert(reference.getType(), value, null);
    }

    /**
     * 转换值为指定类型
     *
     * @param <T>   目标类型
     * @param type  类型
     * @param value 值
     * @return 转换后的值
     * @throws ConvertException 转换器不存在
     */
    public static <T> T convert(Type type, Object value) throws ConvertException {
        return convert(type, value, null);
    }

    /**
     * 转换值为指定类型
     *
     * @param <T>          目标类型
     * @param type         类型
     * @param value        值
     * @param defaultValue 默认值
     * @return 转换后的值
     * @throws ConvertException 转换器不存在
     */
    public static <T> T convert(Class<T> type, Object value, T defaultValue) throws ConvertException {
        return convert((Type) type, value, defaultValue);
    }

    /**
     * 转换值为指定类型
     *
     * @param <T>          目标类型
     * @param type         类型
     * @param value        值
     * @param defaultValue 默认值
     * @return 转换后的值
     * @throws ConvertException 转换器不存在
     */
    public static <T> T convert(Type type, Object value, T defaultValue) throws ConvertException {
        return ConverterRegistry.getInstance().convert(type, value, defaultValue);
    }

    /**
     * 转换值为指定类型，不抛异常转换<br>
     * 当转换失败时返回{@code null}
     *
     * @param <T>   目标类型
     * @param type  目标类型
     * @param value 值
     * @return 转换后的值，转换失败返回null
     */
    public static <T> T convertQuietly(Type type, Object value) {
        return convertQuietly(type, value, null);
    }

    /**
     * 转换值为指定类型，不抛异常转换<br>
     * 当转换失败时返回默认值
     *
     * @param <T>          目标类型
     * @param type         目标类型
     * @param value        值
     * @param defaultValue 默认值
     * @return 转换后的值
     */
    public static <T> T convertQuietly(Type type, Object value, T defaultValue) {
        try {
            return convert(type, value, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    // --------------------------------------------------------------- 原始包装类型转换

    /**
     * 原始类转为包装类，非原始类返回原类
     *
     * @param clazz 原始类
     * @return 包装类
     * @see BasicType#wrap(Class)
     * @see BasicType#wrap(Class)
     */
    public static Class<?> wrap(Class<?> clazz) {
        return BasicType.wrap(clazz);
    }

    /**
     * 包装类转为原始类，非包装类返回原类
     *
     * @param clazz 包装类
     * @return 原始类
     * @see BasicType#unWrap(Class)
     * @see BasicType#unWrap(Class)
     */
    public static Class<?> unWrap(Class<?> clazz) {
        return BasicType.unWrap(clazz);
    }

    // -------------------------------------------------------------------------- 数字和英文转换

    /**
     * 将阿拉伯数字转为英文表达方式
     *
     * @param number {@link Number}对象
     * @return 英文表达式
     */
    public static String numberToWord(Number number) {
        return NumberWordFormatter.format(number);
    }

    /**
     * 将阿拉伯数字转为中文表达方式
     *
     * @param number          数字
     * @param isUseTraditonal 是否使用繁体字（金额形式）
     * @return 中文
     */
    public static String numberToChinese(double number, boolean isUseTraditonal) {
        return NumberChineseFormatter.format(number, isUseTraditonal);
    }

    /**
     * 金额转为中文形式
     *
     * @param n 数字
     * @return 中文大写数字
     */
    public static String digitToChinese(Number n) {
        if (null == n) {
            return "零";
        }
        return NumberChineseFormatter.format(n.doubleValue(), true, true);
    }
}
