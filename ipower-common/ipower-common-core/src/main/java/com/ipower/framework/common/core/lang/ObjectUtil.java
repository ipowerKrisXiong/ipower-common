package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.bean.BeanUtil;
import com.ipower.framework.common.core.collection.ArrayUtil;
import com.ipower.framework.common.core.collection.CollectionUtil;
import com.ipower.framework.common.core.collection.Lists;
import com.ipower.framework.common.core.constant.StringPool;
import com.ipower.framework.common.core.convert.Convert;
import com.ipower.framework.common.core.map.MapUtil;
import com.ipower.framework.common.core.map.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 通用的Object工具类。
 * 该类提供了对java对象的通用非空判断、java对象的通用比较等基本方法，便捷开发。
 *
 * @author kris
 * @since 1.0.0
 */
@Slf4j
public final class ObjectUtil {

    public static final int DEFAULT_BATCH_SIZE = 1000;

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private ObjectUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //------------------------------- Object isNull ---------------------------------------------

    /**
     * <p>判断对象是否为{@code null}。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.isNull(null)                   // true}</li>
     *     <li>{@code ObjectUtil.isNull("")                     // false}</li>
     *     <li>{@code StringUtil.isNull(new ArrayList<>())      // false}</li>
     *     <li>{@code StringUtil.isNull(new HashMap<>())        // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isEmpty(Object)} 的区别是：该方法只校验对象是否为{@code null}。</p>
     * <p>建议：</p>
     * <ul>
     *     <li>该方法建议用于工具类或任何可以预期的方法参数的校验中。</li>
     * </ul>
     *
     * @param obj 被检测的对象
     * @return 是否为null
     */
    public static boolean isNull(final Object obj) {
        return obj == null;
    }

    /**
     * <p>判断对象是否不为{@code null}。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.isNotNull(null)                   // false}</li>
     *     <li>{@code ObjectUtil.isNotNull("")                     // true}</li>
     *     <li>{@code StringUtil.isNotNull(new ArrayList<>())      // true}</li>
     *     <li>{@code StringUtil.isNotNull(new HashMap<>())        // true}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isNotEmpty(Object)} 的区别是：该方法只校验对象是否不为{@code null}。</p>
     * <p>建议：</p>
     * <ul>
     *     <li>该方法建议用于工具类或任何可以预期的方法参数的校验中。</li>
     * </ul>
     *
     * @param obj 被检测的对象
     * @return 是否不为null
     */
    public static boolean isNotNull(final Object obj) {
        return obj != null;
    }

    //------------------------------- Object isEmpty ---------------------------------------------

    /**
     * <p>通用判断对象是否为{@code null}或空的通用方法，支持字符串、集合、数组、迭代器等数据类型。</p>
     * <ol>
     *     <li>Object类型对象，如果为{@code null}，返回{@code true}，同{@link Objects#isNull(Object)}。</li>
     *     <li>字符串类型，如果为{@code null}或是空白字符，返回true。同{@link StringUtil#isEmpty(CharSequence)}。</li>
     *     <li>集合类型，如果为{@code null}或是集合的元素对象数量为{@code 0}，返回{@code true}。集合类型可以是List,Set,Map。</li>
     *     <li>数组类型，如果为{@code null}或是数组的长度为{@code 0}，返回{@code true}。</li>
     *     <li>迭代器，如果为{@code null}或是迭代器对象中不存在可迭代的元素，返回{@code true}。</li>
     *     <li>其他类型，如果为{@code null}，返回{@code true}。</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.isEmpty(null)                       // true}</li>
     *     <li>{@code ObjectUtil.isEmpty("")                         // true}</li>
     *     <li>{@code ObjectUtil.isEmpty("abc")                      // false}</li>
     *     <li>{@code ObjectUtil.isEmpty(new ArrayList<>())          // true}</li>
     *     <li>{@code ObjectUtil.isEmpty(Lists.arrayList("abc"))     // false}</li>
     *     <li>{@code ObjectUtil.isEmpty(new HashMap<>())            // true}</li>
     *     <li>{@code ObjectUtil.isEmpty(Maps.hashMap("a", "123"))   // false}</li>
     *     <li>{@code ObjectUtil.isEmpty(new Object[]{})             // true}</li>
     *     <li>{@code ObjectUtil.isEmpty(new Object[]{123})          // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isNull(Object)} 的区别是：该方法不仅校验对象是否为{@code null}，对于字符串、集合之类的数据还会判断是否空。</p>
     * <p>建议：</p>
     * <ul>
     *     <li>该方法建议用于工具类或任何可以预期的方法参数的校验中。</li>
     *     <li>需要同时校验多个对象时，建议采用 {@link ArrayUtil#hasEmpty(Object...)} 或 {@link ArrayUtil#isAllEmpty(Object...)}</li>
     * </ul>
     *
     * @param obj 被检测的对象
     * @return 是否为null或空
     */
    public static boolean isEmpty(final Object obj) {
        // 判断对象是否为null
        if (obj == null) {
            return true;
        }
        // 判断字符串
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).isEmpty();
        }
        // 判断map
        else if (obj instanceof Map<?, ?>) {
            return ((Map<?, ?>) obj).isEmpty();
        }
        // 判断集合
        else if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        } else if (obj instanceof Iterable<?>) {
            return !((Iterable<?>) obj).iterator().hasNext();
        }
        // 迭代器
        else if (obj instanceof Iterator<?>) {
            return !((Iterator<?>) obj).hasNext();
        }
        // 数组
        else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        // 其他情况
        else {
            return false;
        }
    }

    /**
     * <p>判断是否为{@code null}或空字符串</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>该方法是{@link #isEmpty(Object)}的一个重载方法，方便在JVM在运行时可以根据入参类型优先选择要执行的方法，省去了通用方法的类型判断。</li>
     *     <li>执行逻辑及测试用例参考{@link StringUtil#isEmpty(CharSequence)}</li>
     * </ul>
     *
     * @param cs 需要判断的字符串
     * @return 是否为null或空
     */
    public static boolean isEmpty(final CharSequence cs) {
        return StringUtil.isEmpty(cs);
    }

    /**
     * <p>判断是否为{@code null}或空元素的集合</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>该方法是{@link #isEmpty(Object)}的一个重载方法，方便在JVM在运行时可以根据入参类型优先选择要执行的方法，省去了通用方法的类型判断。</li>
     *     <li>执行逻辑及测试用例参考{@link CollectionUtil#isEmpty(Collection)}</li>
     * </ul>
     *
     * @param coll 需要判断的集合
     * @return 是否为null或空
     */
    public static boolean isEmpty(final Collection<?> coll) {
        return CollectionUtil.isEmpty(coll);
    }

    /**
     * <p>判断是否为{@code null}或空元素的Map</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>该方法是{@link #isEmpty(Object)}的一个重载方法，方便在JVM在运行时可以根据入参类型优先选择要执行的方法，省去了通用方法的类型判断。</li>
     *     <li>执行逻辑及测试用例参考{@link MapUtil#isEmpty(Map)}</li>
     * </ul>
     *
     * @param map 需要判断的Map
     * @return 是否为null或空
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return MapUtil.isEmpty(map);
    }

    /**
     * <p>判断是否为{@code null}或空元素的数组</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>该方法是{@link #isEmpty(Object)}的一个重载方法，方便在JVM在运行时可以根据入参类型优先选择要执行的方法，省去了通用方法的类型判断。</li>
     *     <li>执行逻辑及测试用例参考{@link ArrayUtil#isEmpty(Object[])}</li>
     * </ul>
     *
     * @param array 需要判断的数组
     * @return 是否为null或空
     */
    public static boolean isEmpty(final Object[] array) {
        return ArrayUtil.isEmpty(array);
    }

    /**
     * <p>判断对象是否不为{@code null}或不为空的通用方法，支持字符串、集合、数组、迭代器等数据类型。</p>
     * <p>说明：是{@link #isEmpty(Object)}方法的取反。</p>
     *
     * @param obj 需要判断的对象
     * @return 是否不为null或不为空
     */
    public static boolean isNotEmpty(final Object obj) {
        return !isEmpty(obj);
    }

    /**
     * <p>判断字符串是否不为{@code null}或不为空。它是{@link #isNotEmpty(Object)}的一个重载方法，方便在JVM在运行时根据入参类型优先选择。</p>
     * <p>说明：是{@link #isEmpty(CharSequence)}方法的取反。</p>
     *
     * @param cs 需要判断的字符串
     * @return 是否不为null或不为空
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * <p>判断集合是否不为{@code null}或不为空。它是{@link #isNotEmpty(Object)}的一个重载方法，方便在JVM在运行时根据入参类型优先选择。</p>
     * <p>说明：是{@link #isEmpty(Collection)}方法的取反。</p>
     *
     * @param coll 需要判断的集合
     * @return 是否不为null或不为空
     */
    public static boolean isNotEmpty(final Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * <p>判断Map是否不为{@code null}或不为空。它是{@link #isNotEmpty(Object)}的一个重载方法，方便在JVM在运行时根据入参类型优先选择。</p>
     * <p>说明：是{@link #isEmpty(Map)}方法的取反。</p>
     *
     * @param map 需要判断的Map
     * @return 是否不为null或不为空
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * <p>判断数组是否不为{@code null}或不为空。它是{@link #isNotEmpty(Object)}的一个重载方法，方便在JVM在运行时根据入参类型优先选择。</p>
     * <p>说明：是{@link #isEmpty(Map)}方法的取反。</p>
     *
     * @param array 需要判断的数组
     * @return 是否不为null或不为空
     */
    public static boolean isNotEmpty(final Object[] array) {
        return !isEmpty(array);
    }

    //------------------------------- Object nullToDefault ---------------------------------------------

    /**
     * <p>接收一个Object类型对象，如果被检测的对象为{@code null}，会返回设置的默认值，否则，将返回被检测的对象。</p>
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.nullToDefault(null, "admin")                               // admin}</li>
     *     <li>{@code ObjectUtil.nullToDefault("redis", "admin")                            // redis}</li>
     *     <li>{@code ObjectUtil.nullToDefault(null, new BigDecimal("1.0"))                 // new BigDecimal("1.0")}</li>
     *     <li>{@code ObjectUtil.nullToDefault(new BigDecimal("0"), new BigDecimal("1.0"))  // new BigDecimal("0")}</li>
     * </ul>
     *
     * @param obj          被检测的对象
     * @param defaultValue 被检测对象为{@code null}时返回的默认值，可以为{@code null}
     * @param <T>          被检测对象的类型
     * @return 被检查对象为null返回默认值，否则返回原值
     */
    public static <T> T nullToDefault(final T obj, final T defaultValue) {
        return isNull(obj) ? defaultValue : obj;
    }

    /**
     * <p>接收一个Object类型对象，如果被检测的对象为{@code null}，会返回值由{@code defaultValueSupplier}提供，否则，将返回被检测的对象。</p>
     *
     * <p>注意：{@code defaultValueSupplier}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj                  被检测的对象
     * @param defaultValueSupplier 被检测对象为{@code null}时返回{@code defaultValueSupplier}提供的值，{@code defaultValueSupplier}不允许为{@code null}
     * @param <T>                  被检测对象的类型
     * @return 被检查对象为null返回defaultValueSupplier提供的值，否则返回原值
     */
    public static <T> T nullToDefault(final T obj, @NonNull final Supplier<? extends T> defaultValueSupplier) {
        Validate.notNull(defaultValueSupplier, "The defaultValueSupplier must not be null.");
        return isNull(obj) ? defaultValueSupplier.get() : obj;
    }

    /**
     * <p>接收一个Object类型对象，如果被检测的对象为{@code null}，会返回值由{@code defaultValueFunction}提供，否则，将返回被检测的对象。</p>
     *
     * <p>注意：{@code defaultValueFunction}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj                  被检测的对象
     * @param defaultValueFunction 被检测对象为{@code null}时返回{@code defaultValueSupplier}提供的值，{@code defaultValueFunction}不允许为{@code null}
     * @param <T>                  被检测对象的类型
     * @return 被检查对象为null返回defaultValueFunction提供的值，否则返回原值
     */
    public static <T> T nullToDefault(final T obj, @NonNull final Function<T, ? extends T> defaultValueFunction) {
        Validate.notNull(defaultValueFunction, "The defaultValueFunction must not be null.");
        return isNull(obj) ? defaultValueFunction.apply(null) : obj;
    }

    /**
     * <p>接收一个Object类型对象，如果被检测的对象为{@code null}，会返回默认值，否则，将执行{@code handleSupplier}函数获取值。</p>
     *
     * <p>注意：{@code handleSupplier}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj            被检测的对象
     * @param handleSupplier 用于被检测对象不为{@code null}时，执行该处理方法来获取值，{@code handleSupplier}不允许为{@code null}
     * @param defaultValue   被检测对象为{@code null}时返回的默认值，可以为{@code null}
     * @param <R>            被检测对象的类型
     * @param <T>            返回值的类型
     * @return 被检查对象为null返回默认值，否则返回handleSupplier提供的值
     */
    public static <T, R> T nullToDefault(final R obj, @NonNull final Supplier<? extends T> handleSupplier,
                                         final T defaultValue) {
        Validate.notNull(handleSupplier, "The handleSupplier must not be null.");
        return isNull(obj) ? defaultValue : handleSupplier.get();
    }

    /**
     * <p>接收一个Object类型对象，如果被检测的对象为{@code null}，会返回默认值，否则，将执行{@code handleFunction}函数获取值。</p>
     *
     * <p>注意：{@code handleFunction}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj            被检测的对象
     * @param handleFunction 用于被检测对象不为{@code null}时，执行该处理方法来获取值，{@code handleFunction}不允许为{@code null}
     * @param defaultValue   被检测对象为{@code null}时返回的默认值，可以为{@code null}
     * @param <R>            被检测对象的类型
     * @param <T>            返回值的类型
     * @return 被检查对象为null返回默认值，否则返回handleFunction提供的值
     */
    public static <T, R> T nullToDefault(final R obj, @NonNull final Function<R, ? extends T> handleFunction,
                                         final T defaultValue) {
        Validate.notNull(handleFunction, "The handleFunction must not be null.");
        return isNull(obj) ? defaultValue : handleFunction.apply(obj);
    }

    //------------------------------- Object emptyToDefault ---------------------------------------------

    /**
     * <p>接收一个Object类型对象，如果被检测的对象为{@code null}或空，会返回设置的默认值，否则，将返回被检测的对象。</p>
     * <p>支持字符串、集合、数组、迭代器等数据类型，由{@link ObjectUtil#isEmpty(Object)}提供检测</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.emptyToDefault(null, "admin")                                     // admin}</li>
     *     <li>{@code ObjectUtil.emptyToDefault("", "admin")                                       // admin}</li>
     *     <li>{@code ObjectUtil.emptyToDefault("redis", "admin")                                  // redis}</li>
     *     <li>{@code ObjectUtil.emptyToDefault(null, new BigDecimal("1.0"))                       // new BigDecimal("1.0")}</li>
     *     <li>{@code ObjectUtil.emptyToDefault(new BigDecimal("0"), new BigDecimal("1.0"))        // new BigDecimal("0")}</li>
     *     <li>{@code ObjectUtil.emptyToDefault(null, Lists.arrayList("a", "b"))                   // Lists.arrayList("a", "b")}</li>
     *     <li>{@code ObjectUtil.emptyToDefault(Lists.arrayList("a"), Lists.arrayList("a", "b"))   // Lists.arrayList("a")}</li>
     * </ul>
     *
     * @param obj          被检测的对象
     * @param defaultValue 被检测对象为{@code null}或空时返回的默认值，可以为{@code null}
     * @param <T>          被检测对象的类型
     * @return 被检查对象为null或空返回默认值，否则返回原值
     */
    public static <T> T emptyToDefault(final T obj, final T defaultValue) {
        return isEmpty(obj) ? defaultValue : obj;
    }

    /**
     * <p>接收一个Object类型对象，如果被检测的对象为{@code null}或空，会返回值由{@code defaultValueSupplier}提供，否则，将返回被检测的对象。</p>
     * <p>支持字符串、集合、数组、迭代器等数据类型，由{@link ObjectUtil#isEmpty(Object)}提供检测</p>
     *
     * <p>注意：{@code defaultValueSupplier}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj                  被检测的对象
     * @param defaultValueSupplier 被检测对象为{@code null}或空时返回{@code defaultValueSupplier}提供的值，{@code defaultValueSupplier}不允许为{@code null}
     * @param <T>                  被检测对象的类型
     * @return 被检查对象为null或空返回defaultValueSupplier提供的值，否则返回原值
     */
    public static <T> T emptyToDefault(final T obj, @NonNull final Supplier<? extends T> defaultValueSupplier) {
        Validate.notNull(defaultValueSupplier, "The defaultValueSupplier must not be null.");
        return isNull(obj) ? defaultValueSupplier.get() : obj;
    }

    /**
     * <p>接收一个Object类型对象，如果被检测的对象为{@code null}或空，会返回值由{@code defaultValueFunction}提供，否则，将返回被检测的对象。</p>
     * <p>支持字符串、集合、数组、迭代器等数据类型，由{@link ObjectUtil#isEmpty(Object)}提供检测</p>
     *
     * <p>注意：{@code defaultValueSupplier}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj                  被检测的对象
     * @param defaultValueFunction 被检测对象为{@code null}或空时返回{@code defaultValueFunction}提供的值，{@code defaultValueFunction}不允许为{@code null}
     * @param <T>                  被检测对象的类型
     * @return 被检查对象为null或空返回defaultValueFunction提供的值，否则返回原值
     */
    public static <T> T emptyToDefault(final T obj, @NonNull final Function<T, ? extends T> defaultValueFunction) {
        Validate.notNull(defaultValueFunction, "The defaultValueFunction must not be null.");
        return isNull(obj) ? defaultValueFunction.apply(null) : obj;
    }

    /**
     * <p>接收一个Object类型对象，如果被检测的对象为{@code null}或空，会返回默认值，否则，将执行{@code handleSupplier}函数获取值。</p>
     * <p>支持字符串、集合、数组、迭代器等数据类型，由{@link ObjectUtil#isEmpty(Object)}提供检测</p>
     *
     * <p>注意：{@code handleSupplier}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj            被检测的对象
     * @param handleSupplier 用于被检测对象不为{@code null}或空时，执行该处理方法来获取值，{@code handleSupplier}不允许为{@code null}
     * @param defaultValue   被检测对象为{@code null}或空时返回的默认值，可以为{@code null}
     * @param <R>            被检测对象的类型
     * @param <T>            返回值的类型
     * @return 被检查对象为null或空返回默认值，否则返回handleSupplier提供的值
     */
    public static <T, R> T emptyToDefault(final R obj, @NonNull final Supplier<? extends T> handleSupplier, final T defaultValue) {
        Validate.notNull(handleSupplier, "The handleSupplier must not be null.");
        return isNull(obj) ? defaultValue : handleSupplier.get();
    }

    /**
     * <p>接收一个Object类型对象，如果被检测的对象为{@code null}或空，会返回默认值，否则，将执行{@code handleFunction}函数获取值。</p>
     * <p>支持字符串、集合、数组、迭代器等数据类型，由{@link ObjectUtil#isEmpty(Object)}提供检测</p>
     *
     * <p>注意：{@code handleFunction}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj            被检测的对象
     * @param handleFunction 用于被检测对象不为{@code null}或空时，执行该处理方法来获取值，{@code handleFunction}不允许为{@code null}
     * @param defaultValue   被检测对象为{@code null}或空时返回的默认值，可以为{@code null}
     * @param <R>            被检测对象的类型
     * @param <T>            返回值的类型
     * @return 被检查对象为null或空返回默认值，否则返回handleFunction提供的值
     */
    public static <T, R> T emptyToDefault(final R obj, @NonNull final Function<R, ? extends T> handleFunction, final T defaultValue) {
        Validate.notNull(handleFunction, "The handleFunction must not be null.");
        return isNull(obj) ? defaultValue : handleFunction.apply(obj);
    }

    //------------------------------- notNullToConsumer notEmptyToConsumer ---------------------------------------------

    /**
     * <p>接收一个Object类型对象，如果被检测的对象不为{@code null}，将会执行{@code consumer}函数。</p>
     *
     * <p>注意：{@code consumer}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj      被检测的对象
     * @param consumer 用于被检测对象不为{@code null}时，需要执行的消费函数
     * @param <T>      被检测对象的类型
     */
    public static <T> void notNullToConsumer(final T obj, @NonNull final Consumer<T> consumer) {
        Validate.notNull(consumer, "The consumer must not be null.");
        if (isNotNull(obj)) {
            consumer.accept(obj);
        }
    }

    /**
     * <p>接收一个Object类型对象，如果被检测的对象不为{@code null}或空，将会执行{@code consumer}函数。</p>
     * <p>支持字符串、集合、数组、迭代器等数据类型，由{@link ObjectUtil#isNotEmpty(Object)}提供检测</p>
     *
     * <p>注意：{@code consumer}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj      被检测的对象
     * @param consumer 用于被检测对象不为{@code null}或空时，需要执行的消费函数
     * @param <T>      被检测对象的类型
     */
    public static <T> void notEmptyToConsumer(final T obj, @NonNull final Consumer<T> consumer) {
        Validate.notNull(consumer, "The consumer must not be null.");
        if (isNotEmpty(obj)) {
            consumer.accept(obj);
        }
    }

    /**
     * <p>接收一个字符串类型对象，如果被检测的对象不为{@code null}或空，将会执行{@code consumer}函数。</p>
     *
     * <p>注意：{@code consumer}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj      被检测的字符串类型对象
     * @param consumer 用于被检测对象不为{@code null}或空时，需要执行的消费函数
     * @param <T>      被检测对象的类型
     */
    public static <T extends CharSequence> void notEmptyToConsumer(final T obj, @NonNull final Consumer<T> consumer) {
        Validate.notNull(consumer, "The consumer must not be null.");
        if (isNotEmpty(obj)) {
            consumer.accept(obj);
        }
    }

    /**
     * <p>接收一个集合类型对象，如果被检测的对象不为{@code null}或空，将会执行{@code consumer}函数。</p>
     *
     * <p>注意：{@code consumer}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj      被检测的集合类型对象
     * @param consumer 用于被检测对象不为{@code null}或空时，需要执行的消费函数
     * @param <T>      被检测对象的类型
     */
    public static <E, T extends Collection<E>> void notEmptyToConsumer(final T obj, @NonNull final Consumer<T> consumer) {
        Validate.notNull(consumer, "The consumer must not be null.");
        if (isNotEmpty(obj)) {
            consumer.accept(obj);
        }
    }

    /**
     * <p>接收一个Map类型对象，如果被检测的对象不为{@code null}或空，将会执行{@code consumer}函数。</p>
     *
     * <p>注意：{@code consumer}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj      被检测的Map类型对象
     * @param consumer 用于被检测对象不为{@code null}或空时，需要执行的消费函数
     * @param <T>      被检测对象的类型
     */
    public static <K, V, T extends Map<K, V>> void notEmptyToConsumer(final T obj, @NonNull final Consumer<T> consumer) {
        Validate.notNull(consumer, "The consumer must not be null.");
        if (isNotEmpty(obj)) {
            consumer.accept(obj);
        }
    }

    /**
     * <p>接收一个数组对象，如果被检测的对象不为{@code null}或空，将会执行{@code consumer}函数。</p>
     *
     * <p>注意：{@code consumer}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param obj      被检测的数组对象
     * @param consumer 用于被检测对象不为{@code null}或空时，需要执行的消费函数
     * @param <T>      被检测对象的类型
     */
    public static <T> void notEmptyToConsumer(final T[] obj, @NonNull final Consumer<T[]> consumer) {
        Validate.notNull(consumer, "The consumer must not be null.");
        if (isNotEmpty(obj)) {
            consumer.accept(obj);
        }
    }

    //------------------------------- isTrueToConsumer notEmptyToConsumer ---------------------------------------------

    /**
     * <p>接收一个条件值，如果被检测的条件为{@code true}或空，将会执行{@code consumer}函数。</p>
     *
     * <p>注意：{@code consumer}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param consumer 用于被检测条件为{@code true}时，需要执行的消费函数
     */
    public static void isTrueToConsumer(final boolean condition, @NonNull final Consumer<Void> consumer) {
        Validate.notNull(consumer, "The consumer must not be null.");
        if (condition) {
            consumer.accept(null);
        }
    }

    /**
     * <p>接收一个条件值，如果被检测的条件为{@code true}，将会执行{@code trueConsumer}函数，否则会执行{@code falseConsumer}函数。</p>
     *
     * <p>注意：{@code trueConsumer}、{@code falseConsumer}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param trueConsumer  用于被检测条件为{@code true}时，需要执行的消费函数
     * @param falseConsumer 用于被检测条件为{@code false}时，需要执行的消费函数
     */
    public static void isTrueToConsumer(final boolean condition, @NonNull final Consumer<Void> trueConsumer,
                                        @NonNull final Consumer<Void> falseConsumer) {
        Validate.notNull(trueConsumer, "The trueConsumer must not be null.");
        Validate.notNull(falseConsumer, "The falseConsumer must not be null.");
        if (condition) {
            trueConsumer.accept(null);
        } else {
            falseConsumer.accept(null);
        }
    }

    /**
     * <p>接收一个条件值，如果被检测的条件为{@code false}，将会执行{@code consumer}函数。</p>
     *
     * <p>注意：{@code consumer}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param consumer 用于被检测条件为{@code false}时，需要执行的消费函数
     */
    public static void isFalseToConsumer(final boolean condition, @NonNull final Consumer<Void> consumer) {
        Validate.notNull(consumer, "The consumer must not be null.");
        if (!condition) {
            consumer.accept(null);
        }
    }

    /**
     * <p>接收一个条件值，如果被检测的条件为{@code false}，将会执行{@code falseConsumer}函数，否则会执行{@code trueConsumer}函数。</p>
     *
     * <p>注意：{@code falseConsumer}、{@code trueConsumer}不允许为空，否则会抛出{@link IllegalArgumentException}异常</p>
     *
     * @param falseConsumer 用于被检测条件为{@code false}时，需要执行的消费函数
     * @param trueConsumer  用于被检测条件为{@code true}时，需要执行的消费函数
     */
    public static void isFalseToConsumer(final boolean condition, @NonNull final Consumer<Void> falseConsumer,
                                         @NonNull final Consumer<Void> trueConsumer) {
        Validate.notNull(falseConsumer, "The falseConsumer must not be null.");
        Validate.notNull(trueConsumer, "The trueConsumer must not be null.");
        if (!condition) {
            falseConsumer.accept(null);
        } else {
            trueConsumer.accept(null);
        }
    }

    //------------------------------- isTrueToConsumer notEmptyToConsumer ---------------------------------------------

    /**
     * <p>接收一个对象值，优先执行检测函数，如果检测的结果为{@code true}，将会执行{@code consumer}函数。</p>
     *
     * @param obj       对象值
     * @param predicate 检测函数
     * @param consumer  如果检测的结果为{@code true}时，需要执行的消费函数
     * @param <T>       对象值类型
     */
    public static <T> void predicateToConsumer(final T obj, @NonNull final Predicate<T> predicate, @NonNull final Consumer<T> consumer) {
        Validate.notNull(predicate, "The predicate must not be null.");
        Validate.notNull(consumer, "The consumer must not be null.");
        if (predicate.test(obj)) {
            consumer.accept(obj);
        }
    }

    /**
     * <p>接收一个对象值，优先执行检测函数，如果检测的结果为{@code true}，将会执行{@code trueConsumer}函数，否则会执行{@code falseConsumer}函数。</p>
     *
     * @param obj           对象值
     * @param predicate     检测函数
     * @param trueConsumer  如果检测的结果为{@code true}时，需要执行的消费函数
     * @param falseConsumer 如果检测的结果为{@code false}时，需要执行的消费函数
     * @param <T>           对象值类型
     */
    public static <T> void predicateToConsumer(final T obj, @NonNull final Predicate<T> predicate, @NonNull final Consumer<T> trueConsumer,
                                               @NonNull final Consumer<T> falseConsumer) {
        Validate.notNull(predicate, "The predicate must not be null.");
        Validate.notNull(trueConsumer, "The trueConsumer must not be null.");
        Validate.notNull(falseConsumer, "The falseConsumer must not be null.");
        if (predicate.test(obj)) {
            trueConsumer.accept(obj);
        } else {
            falseConsumer.accept(obj);
        }
    }

    //------------------------------- Object equals ---------------------------------------------

    /**
     * <p>比较俩个对象是否相等，同{@link Objects#equals(Object, Object)}。</p>
     * <ol>
     *     <li>该方法会先用"=="比较俩个对象是否相等，若不相等的话，再调用对象的equals方法进行比较。</li>
     *     <li>如果传入的对象为原生类型，则会先将原生类型对象转换为对应的包装类型对象，再进行比较。</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.equals(null, null)                                        // true}</li>
     *     <li>{@code ObjectUtil.equals("", null)                                          // false}</li>
     *     <li>{@code ObjectUtil.equals("", "")                                            // true}</li>
     *     <li>{@code ObjectUtil.equals("", " ")                                           // false}</li>
     *     <li>{@code ObjectUtil.equals(123, 123)                                          // true}</li>
     *     <li>{@code ObjectUtil.equals(123, 123L)                                         // false}</li>
     *     <li>{@code ObjectUtil.equals("abc", "abc")                                      // true}</li>
     *     <li>{@code ObjectUtil.equals(1.0d, 1.00d)                                       // true}</li>
     *     <li>{@code ObjectUtil.equals(1.0f, 1.00f)                                       // true}</li>
     *     <li>{@code ObjectUtil.equals(1.0d, 1.0f)                                        // false}</li>
     *     <li>{@code ObjectUtil.equals(Lists.arrayList("abc"), Lists.arrayList("abc"))    // true}</li>
     *     <li>{@code ObjectUtil.equals(new int[]{1, 2}, new int[]{1, 2})                  // false}</li>
     * </ul>
     *
     * @param obj     要比较的对象
     * @param another 要比较的另一个对象
     * @return 俩个对象是否相等
     */
    public static boolean equals(final Object obj, final Object another) {
        return Objects.equals(obj, another);
    }

    /**
     * <p>比较俩个对象是否相等，同{@link Objects#equals(Object, Object)}。</p>
     *
     * @param obj     要比较的对象
     * @param another 要比较的另一个对象
     * @return 俩个对象是否相等
     * @see ObjectUtil#equals(Object, Object)
     */
    public static boolean equal(final Object obj, final Object another) {
        return equals(obj, another);
    }

    /**
     * <p>比较俩个精度数字对象是否相等，此方法通过调用{@link BigDecimal#compareTo(BigDecimal)}方法来判断是否相等。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.equals(null, null)                                        // true}</li>
     *     <li>{@code ObjectUtil.equals(new BigDecimal("1"), new BigDecimal("1"))          // true}</li>
     *     <li>{@code ObjectUtil.equals(new BigDecimal("1"), new BigDecimal("1.0"))        // true}</li>
     *     <li>{@code ObjectUtil.equals(new BigDecimal("1"), new BigDecimal("2.0"))        // false}</li>
     * </ul>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>该方法是{@link #equals(Object, Object)}的一个重载方法，方便在JVM在运行时可以根据入参类型优先选择要执行的方法，省去了通用方法的类型判断。</li>
     *     <li>执行逻辑及测试用例参考{@link NumberUtil#equals(BigDecimal, BigDecimal)}</li>
     * </ul>
     *
     * @param obj     要比较的精度数字对象
     * @param another 要比较的另一个精度数字对象
     * @return 俩个精度数字对象是否相等
     */
    public static boolean equals(final BigDecimal obj, final BigDecimal another) {
        return NumberUtil.equals(obj, another);
    }

    /**
     * <p>比较俩个精度数字对象是否相等，此方法通过调用{@link BigDecimal#compareTo(BigDecimal)}方法来判断是否相等。</p>
     *
     * @param obj     要比较的精度数字对象
     * @param another 要比较的另一个精度数字对象
     * @return 俩个精度数字对象是否相等
     * @see ObjectUtil#equals(BigDecimal, BigDecimal)
     */
    public static boolean equal(final BigDecimal obj, final BigDecimal another) {
        return equals(obj, another);
    }

    /**
     * <p>比较俩个对象是否不相等，是{@link #equals(Object, Object)}方法的取反。</p>
     *
     * @param obj     要比较的对象
     * @param another 要比较的另一个对象
     * @return 俩个对象是否不相等
     */
    public static boolean notEquals(final Object obj, final Object another) {
        return !equals(obj, another);
    }

    /**
     * <p>比较俩个对象是否不相等，是{@link #equals(Object, Object)}方法的取反。</p>
     *
     * @param obj     要比较的对象
     * @param another 要比较的另一个对象
     * @return 俩个对象是否不相等
     * @see ObjectUtil#notEquals(Object, Object)
     */
    public static boolean notEqual(final Object obj, final Object another) {
        return notEquals(obj, another);
    }

    /**
     * <p>比较俩个精度数字对象是否不相等，是{@link #equals(BigDecimal, BigDecimal)}方法的取反。</p>
     *
     * @param obj     要比较的精度数字对象
     * @param another 要比较的另一个精度数字对象
     * @return 俩个精度数字对象是否不相等
     */
    public static boolean notEquals(final BigDecimal obj, final BigDecimal another) {
        return !equals(obj, another);
    }

    /**
     * <p>比较俩个精度数字对象是否不相等，是{@link #equals(BigDecimal, BigDecimal)}方法的取反。</p>
     *
     * @param obj     要比较的精度数字对象
     * @param another 要比较的另一个精度数字对象
     * @return 俩个精度数字对象是否不相等
     * @see ObjectUtil#notEquals(BigDecimal, BigDecimal)
     */
    public static boolean notEqual(final BigDecimal obj, final BigDecimal another) {
        return notEquals(obj, another);
    }

    //------------------------------- Object compare ---------------------------------------------

    /**
     * <p>安全的比较俩个对象的大小，{@code null}小于任何非{@code null}对象。</p>
     * <ol>
     *     <li>{@code obj == another}，返回 {@code 0}</li>
     *     <li>{@code obj < another}，返回 {@code -1}</li>
     *     <li>{@code obj > another}，返回 {@code 1}</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.compare(null, null)               // 0}</li>
     *     <li>{@code ObjectUtil.compare(1, null)                  // 1}</li>
     *     <li>{@code ObjectUtil.compare(null, 13))                // -1}</li>
     *     <li>{@code ObjectUtil.compare(1, 1)                     // 0}</li>
     *     <li>{@code ObjectUtil.compare(1, 13)                    // -1}</li>
     *     <li>{@code ObjectUtil.compare(13, 1)                    // 1}</li>
     *     <li>{@code ObjectUtil.compare("a", "a")                 // 0}</li>
     *     <li>{@code ObjectUtil.compare("a", "aa")                // -1}</li>
     *     <li>{@code ObjectUtil.compare("aa", "a")                // 1}</li>
     * </ul>
     *
     * <p>注意：要比较的俩个对象的类型，必须是实现了{@link Comparable}接口。</p>
     *
     * @param obj     要比较的对象，可以为{@code null}
     * @param another 要比较的另一个对象，可以为{@code null}
     * @param <T>     用来限制比较对象的类型，必须是实现了{@link Comparable}接口
     * @return 比较的结果，obj == another 返回 0；obj &lt another 返回 -1；obj &gt another 返回 1
     */
    public static <T extends Comparable<? super T>> int compare(final T obj, final T another) {
        if (obj == another) {
            return 0;
        } else if (obj == null) {
            return -1;
        } else if (another == null) {
            return 1;
        }
        return obj.compareTo(another);
    }

    /**
     * <p>比较俩个对象的大小，比较的结果取决于comparator比较器。如果比较的对象为{@code null}，将由comparator对象来处理此类情况。</p>
     * <p>如果comparator比较器为{@code null}，则默认调用{@link ObjectUtil#compare(Comparable, Comparable)}来执行比较。</p>
     * <ol>
     *     <li>{@code obj == another}，返回 {@code 0}</li>
     *     <li>{@code obj < another}，返回 {@code -1}</li>
     *     <li>{@code obj > another}，返回 {@code 1}</li>
     * </ol>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.compare(1, 1, Integer::compareTo)                     // 0}</li>
     *     <li>{@code ObjectUtil.compare(1, 13, Integer::compareTo)                    // -1}</li>
     *     <li>{@code ObjectUtil.compare(13, 1, Integer::compareTo)                    // 1}</li>
     *     <li>{@code ObjectUtil.compare("a", "a", String::compareTo)                  // 0}</li>
     *     <li>{@code ObjectUtil.compare("a", "aa", String::compareTo)                 // -1}</li>
     *     <li>{@code ObjectUtil.compare("aa", "a", String::compareTo)                 // 1}</li>
     *     <li>{@code ObjectUtil.compare(1, 13, null)                                  // -1}</li>
     *     <li>{@code ObjectUtil.compare("aa", "a", null)                              // 1}</li>
     * </ul>
     *
     * <p>注意：在comparator比较器为{@code null}的情况下，要比较的对象必须是已实现了{@link Comparable}接口，否则会抛出{@link ClassCastException}异常。</p>
     *
     * @param obj        要比较的对象，可以为{@code null}
     * @param another    要比较的另一个对象，可以为{@code null}
     * @param comparator 比较器
     * @param <T>        比较对象的类型
     * @return 比较的结果，obj == another 返回 0；obj &lt another 返回 -1；obj &gt another 返回 1
     * @see Comparator#compare(Object, Object)
     */
    public static <T> int compare(final T obj, final T another, final Comparator<? super T> comparator) {
        if (comparator != null) {
            return comparator.compare(obj, another);
        }
        //noinspection rawtypes,unchecked
        return compare((Comparable) obj, (Comparable) another);
    }

    //------------------------------- Object toString ---------------------------------------------

    /**
     * <p>将Object对象转String</p>
     * <ol>
     *     <li>如果对象为{@code null}，转换为{@code "null"}</li>
     *     <li>其他情况，调用对象的toString()方法</li>
     * </ol>
     *
     * @param obj 需要转String的对象
     * @return 转String之后后的值
     */
    public static String toString(final Object obj) {
        return obj == null ? StringPool.NULL : obj.toString();
    }

    //------------------------------- Object convert ---------------------------------------------

    /**
     * 通用的类型转换
     *
     * @param obj  需要转换的数据
     * @param type 需要转换的类型
     * @param <T>  转换后的类型泛型
     * @return T 转换后的数据对象
     */
    public static <T> T convert(Object obj, Class<T> type) {
        return Convert.convert(type, obj);
    }

    /**
     * 通用的类型转换，若转换不成功，则设置为默认值
     *
     * @param obj          需要转换的数据
     * @param defaultValue 默认值
     * @param <T>          转换后的类型泛型
     * @return T 转换后的数据对象
     */
    public static <T> T convert(Object obj, T defaultValue) {
        try {
            return Convert.convert(null, obj, defaultValue);
        } catch (Exception e) {
            log.warn("数据类型转换异常，返回默认值：{}，异常信息：{}", defaultValue, e.getMessage());
            return defaultValue;
        }
    }

    /**
     * 判断数字是否大于0
     *
     * @param value 要判断的数字
     * @return boolean 是否大于0
     */
    public static boolean gtZero(Number value) {
        return isNotNull(value) && new BigDecimal(value.toString()).compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 判断数字是否大于等于0
     *
     * @param value 要判断的数字
     * @return boolean 是否大于等于0
     */
    public static boolean geZero(Number value) {
        return isNotNull(value) && new BigDecimal(value.toString()).compareTo(BigDecimal.ZERO) > -1;
    }

    /**
     * 判断数字是否小于0
     *
     * @param value 要判断的数字
     * @return boolean 是否小于0
     */
    public static boolean ltZero(Number value) {
        return !geZero(value);
    }

    /**
     * 判断数字是否小于等于0
     *
     * @param value 要判断的数字
     * @return boolean 是否小于等于0
     */
    public static boolean leZero(Number value) {
        return !gtZero(value);
    }

    /**
     * 对数据进行分组，返回分组后的集合，默认按数量为1000进行分组
     *
     * @param dataList 需要分组的数据
     * @param <T>      数据泛型
     * @return List<List < T>> 分组后的数据
     */
    public static <T> List<List<T>> group(List<T> dataList) {
        return group(dataList, DEFAULT_BATCH_SIZE);
    }

    /**
     * 对数据进行分组，返回分组后的集合
     *
     * @param dataList 需要分组的数据
     * @param size     分组数据的大小
     * @param <T>      数据泛型
     * @return List<List < T>> 分组后的数据
     */
    public static <T> List<List<T>> group(List<T> dataList, int size) {
        List<List<T>> lists = Lists.arrayList();
        if (isEmpty(dataList)) {
            return lists;
        }
        size = Math.max(size, 1);
        int len = dataList.size();
        if (len <= size) {
            lists.add(dataList);
            return lists;
        }
        //分批数
        int part = len / size;
        for (int i = 0; i < part; i++) {
            int form = i * size;
            lists.add(dataList.subList(i * size, form + size));
        }
        //是否有余数
        int remainder = len % size;
        if (remainder > 0) {
            lists.add(dataList.subList(part * size, len));
        }
        return lists;
    }

    /**
     * 对数据进行分页，会默认最小值为1
     *
     * @param dataList 要分页的数据
     * @param page     页码
     * @param size     每页大小
     * @param <T>      数据泛型
     * @return 分页后的数据
     */
    public static <T> List<T> dataPaging(List<T> dataList, int page, int size) {
        List<List<T>> group = group(dataList, size);
        page = Math.max(page, 1);
        return page <= group.size() ? group.get(page - 1) : Lists.arrayList();
    }

    /**
     * 将集合转换成map对象，value为集合元素
     *
     * @param collection 需要转换的集合
     * @param keyField   key的字段名称
     * @param <K>        key的泛型类型
     * @param <T>        值的泛型类型
     * @return 转换后的map对象
     */
    public static <K, T> Map<K, T> toMap(Collection<T> collection, String keyField) {
        return toMap(collection, it -> BeanUtil.getProperty(it, keyField));
    }

    /**
     * 将集合转换成map对象，value为集合元素
     *
     * @param collection  需要转换的集合
     * @param keyFunction key转换函数
     * @param <K>         key的泛型类型
     * @param <T>         值的泛型类型
     * @return 转换后的map对象
     */
    public static <K, T> Map<K, T> toMap(Collection<T> collection, Function<T, K> keyFunction) {
        return toMap(collection, keyFunction, it -> it);
    }

    /**
     * 将集合转换成map对象，value为指定字段集合元素
     *
     * @param collection 需要转换的集合
     * @param keyField   key的字段名称
     * @param valueField value的字段名称
     * @param <K>        key的泛型类型
     * @param <V>        值的泛型类型
     * @param <T>        集合对象的泛型类型
     * @return 转换后的map对象
     */
    public static <K, V, T> Map<K, V> toMap(Collection<T> collection, String keyField, String valueField) {
        return toMap(collection, keyField, valueField, false);
    }

    /**
     * 将集合转换成map对象，value为指定字段集合元素
     *
     * @param collection    需要转换的集合
     * @param keyFunction   key转换函数
     * @param valueFunction value转换函数
     * @param <K>           key的泛型类型
     * @param <V>           值的泛型类型
     * @param <T>           集合对象的泛型类型
     * @return 转换后的map对象
     */
    public static <K, V, T> Map<K, V> toMap(Collection<T> collection, Function<T, K> keyFunction, Function<T, V> valueFunction) {
        return toMap(collection, keyFunction, valueFunction, false);
    }

    /**
     * 将集合转换成map对象，value为指定字段集合元素
     *
     * @param collection 需要转换的集合
     * @param keyField   key的字段名称
     * @param valueField value的字段名称
     * @param <K>        key的泛型类型
     * @param <V>        值的泛型类型
     * @param <T>        集合对象的泛型类型
     * @return 转换后的map对象
     */
    public static <K, V, T> Map<K, V> toMap(Collection<T> collection, String keyField, String valueField, boolean filterNull) {
        return toMap(collection, it -> BeanUtil.getProperty(it, keyField), it -> BeanUtil.getProperty(it, valueField), filterNull);
    }

    /**
     * 将集合转换成map对象，value为指定字段集合元素
     *
     * @param collection    需要转换的集合
     * @param keyFunction   key转换函数
     * @param valueFunction value转换函数
     * @param <K>           key的泛型类型
     * @param <V>           值的泛型类型
     * @param <T>           集合对象的泛型类型
     * @return 转换后的map对象
     */
    public static <K, V, T> Map<K, V> toMap(Collection<T> collection, Function<T, K> keyFunction, Function<T, V> valueFunction, boolean filterNull) {
        Map<K, V> map = Maps.hashMap();
        if (isEmpty(collection)) {
            return map;
        }
        Validate.notNull(keyFunction, "keyFunction can't be null!");
        Validate.notNull(valueFunction, "valueFunction can't be null!");
        collection.stream().filter(ObjectUtil::isNotNull).forEach(it -> {
            K k = keyFunction.apply(it);
            if (isEmpty(k)) {
                return;
            }
            V v = valueFunction.apply(it);
            if (filterNull && isNull(v)) {
                return;
            }
            map.put(k, v);
        });
        return map;
    }

    /**
     * 将集合转换成map对象，value为相同key值的集合元素列表
     *
     * @param collection 需要转换的集合
     * @param keyField   key的字段名称
     * @param <K>        key的泛型类型
     * @param <T>        值的泛型类型
     * @return 转换后的map对象
     */
    public static <K, T> Map<K, List<T>> toMapList(Collection<T> collection, String keyField) {
        return toMapList(collection, keyField, false);
    }

    /**
     * 将集合转换成map对象，value为相同key值的集合元素列表
     *
     * @param collection  需要转换的集合
     * @param keyFunction key转换函数
     * @param <K>         key的泛型类型
     * @param <T>         值的泛型类型
     * @return 转换后的map对象
     */
    public static <K, T> Map<K, List<T>> toMapList(Collection<T> collection, Function<T, K> keyFunction) {
        return toMapList(collection, keyFunction, false);
    }

    /**
     * 将集合转换成map对象，value为相同key值的集合元素列表
     *
     * @param collection 需要转换的集合
     * @param keyField   key的字段名称
     * @param <K>        key的泛型类型
     * @param <T>        值的泛型类型
     * @return 转换后的map对象
     */
    public static <K, T> Map<K, List<T>> toMapList(Collection<T> collection, String keyField, boolean filterNull) {
        return toMapList(collection, it -> BeanUtil.getProperty(it, keyField), filterNull);
    }

    /**
     * 将集合转换成map对象，value为相同key值的集合元素列表
     *
     * @param collection  需要转换的集合
     * @param keyFunction key转换函数
     * @param filterNull  是否过滤null
     * @param <K>         key的泛型类型
     * @param <T>         值的泛型类型
     * @return 转换后的map对象
     */
    public static <K, T> Map<K, List<T>> toMapList(Collection<T> collection, Function<T, K> keyFunction, boolean filterNull) {
        return toMapList(collection, keyFunction, it -> it, filterNull);
    }

    /**
     * 将集合转换成map对象，value为相同key值的指定字段集合元素
     *
     * @param collection 需要转换的集合
     * @param keyField   key的字段名称
     * @param valueField value的字段名称
     * @param <K>        key的泛型类型
     * @param <V>        值的泛型类型
     * @param <T>        集合对象的泛型类型
     * @return 转换后的map对象
     */
    public static <K, V, T> Map<K, List<V>> toMapList(Collection<T> collection, String keyField, String valueField) {
        return toMapList(collection, keyField, valueField, false);
    }

    /**
     * 将集合转换成map对象，value为相同key值的指定字段集合元素
     *
     * @param collection    需要转换的集合
     * @param keyFunction   key转换函数
     * @param valueFunction value转换函数
     * @param <K>           key的泛型类型
     * @param <V>           值的泛型类型
     * @param <T>           集合对象的泛型类型
     * @return 转换后的map对象
     */
    public static <K, V, T> Map<K, List<V>> toMapList(Collection<T> collection, Function<T, K> keyFunction, Function<T, V> valueFunction) {
        return toMapList(collection, keyFunction, valueFunction, false);
    }

    /**
     * 将集合转换成map对象，value为相同key值的指定字段集合元素
     *
     * @param collection 需要转换的集合
     * @param keyField   key的字段名称
     * @param valueField value的字段名称
     * @param filterNull 是否过滤null
     * @param <K>        key的泛型类型
     * @param <V>        值的泛型类型
     * @param <T>        集合对象的泛型类型
     * @return 转换后的map对象
     */
    public static <K, V, T> Map<K, List<V>> toMapList(Collection<T> collection, String keyField, String valueField, boolean filterNull) {
        return toMapList(collection, it -> BeanUtil.getProperty(it, keyField), it -> BeanUtil.getProperty(it, valueField), filterNull);
    }

    /**
     * 将集合转换成map对象，value为相同key值的指定字段集合元素
     *
     * @param collection    需要转换的集合
     * @param keyFunction   key转换
     * @param valueFunction value转换函数
     * @param filterNull    是否过滤null
     * @param <K>           key的泛型类型
     * @param <V>           值的泛型类型
     * @param <T>           集合对象的泛型类型
     * @return 转换后的map对象
     */
    public static <K, V, T> Map<K, List<V>> toMapList(Collection<T> collection, Function<T, K> keyFunction, Function<T, V> valueFunction, boolean filterNull) {
        Map<K, List<V>> map = Maps.hashMap();
        if (isEmpty(collection)) {
            return map;
        }
        Validate.notNull(keyFunction, "keyFunction can't be null!");
        Validate.notNull(valueFunction, "valueFunction can't be null!");
        collection.stream().filter(ObjectUtil::isNotNull).forEach(it -> {
            K k = keyFunction.apply(it);
            if (isEmpty(k)) {
                return;
            }
            addMapValue(map, k, valueFunction.apply(it), filterNull);
        });
        return map;
    }

    private static <K, V> void addMapValue(Map<K, List<V>> map, K k, V v, boolean filterNull) {
        List<V> list = map.get(k);
        if (isNull(list)) {
            list = Lists.arrayList();
            map.put(k, list);
        }
        if (filterNull && isNull(v)) {
            return;
        }
        list.add(v);
    }
}
