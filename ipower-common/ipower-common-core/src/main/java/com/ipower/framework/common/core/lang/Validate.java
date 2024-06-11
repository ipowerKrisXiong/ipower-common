package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.collection.ArrayUtil;
import com.ipower.framework.common.core.collection.CollectionUtil;
import com.ipower.framework.common.core.exception.unchecked.ValidateException;
import com.ipower.framework.common.core.map.MapUtil;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 校验某些对象或值是否符合规定，否则抛出异常。经常用于做变量检查
 *
 * @author kris
 * @since 1.0.0
 */
public class Validate {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private Validate() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // --------------------- Check is true

    /**
     * 断言是否为真，如果为 {@code false} 抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.isTrue(i &gt; 0, "The value must be greater than zero");
     * </pre>
     *
     * @param expression 布尔值
     * @throws IllegalArgumentException if expression is {@code false}
     */
    public static void isTrue(final boolean expression) throws IllegalArgumentException {
        isTrue(expression, "[Validate failed] - this expression must be true");
    }

    /**
     * 断言是否为真，如果为 {@code false} 抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.isTrue(i &gt; 0, "The value must be greater than zero");
     * </pre>
     *
     * @param expression 布尔值
     * @param message    错误抛出异常附带的消息模板，变量用{}代替
     * @param params     参数列表
     * @throws IllegalArgumentException if expression is {@code false}
     */
    public static void isTrue(final boolean expression, final String message, final Object... params) throws IllegalArgumentException {
        isTrue(expression, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 断言是否为真，如果为 {@code false}，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.isTrue(i &gt; 0, IllegalArgumentException::new);
     * </pre>
     *
     * @param expression 波尔值
     * @param supplier   指定断言不通过时抛出的异常
     * @throws X if expression is {@code false}
     */
    public static <X extends Throwable> void isTrue(final boolean expression, @NonNull final Supplier<? extends X> supplier) throws X {
        if (!expression) {
            throw supplier.get();
        }
    }

    // --------------------- Check not equals

    /**
     * 断言两个对象是否不相等，如果两个对象相等，抛出{@link IllegalArgumentException}异常。
     * <pre class="code">
     *   Validate.notEquals(obj, another);
     * </pre>
     *
     * @param obj     被检测的对象
     * @param another 被检测的另一个对象
     * @throws IllegalArgumentException obj must be not equals another
     */
    public static void notEquals(final Object obj, final Object another) throws IllegalArgumentException {
        notEquals(obj, another, "({}) must be not equals ({})", obj, another);
    }

    /**
     * 断言两个对象是否不相等，如果两个对象相等，抛出{@link IllegalArgumentException}异常。
     * <pre class="code">
     *   Validate.notEquals(obj, another, "obj must be not equals another");
     * </pre>
     *
     * @param obj     被检测的对象
     * @param another 被检测的另一个对象
     * @param message 异常信息模板，类似于"aa{}bb{}cc"
     * @param params  异常信息参数，用于替换"{}"占位符
     * @throws IllegalArgumentException obj must be not equals another
     */
    public static void notEquals(final Object obj, final Object another, final String message, final Object... params) throws IllegalArgumentException {
        notEquals(obj, another, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 断言两个对象是否不相等，如果两个对象相等，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.notEquals(obj, another, ()-&gt;{
     *      return new IllegalArgumentException("obj must be not equals another");
     *  });
     * </pre>
     *
     * @param obj      被检测的对象
     * @param another  被检测的另一个对象
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <X>      异常类型
     * @throws X obj must be not equals another
     */
    public static <X extends Throwable> void notEquals(final Object obj, final Object another, @NonNull final Supplier<X> supplier) throws X {
        if (ObjectUtil.notEquals(obj, another)) {
            throw supplier.get();
        }
    }

    // --------------------- Check not null

    /**
     * 断言对象是否不为{@code null} ，如果为{@code null}，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notNull(clazz);
     * </pre>
     *
     * @param obj 被检查对象
     * @param <T> 被检查对象类型
     * @return 被检查的对象
     * @throws IllegalArgumentException if the obj is {@code null}
     */
    public static <T> T notNull(final T obj) throws IllegalArgumentException {
        return notNull(obj, "[Validate failed] - this argument is required; it must not be null");
    }

    /**
     * 断言对象是否不为{@code null} ，如果为{@code null}，抛出{@link IllegalArgumentException} 异常。
     *
     * <pre class="code">
     * Validate.notNull(clazz, "The class must not be null");
     * </pre>
     *
     * @param obj     被检查对象
     * @param message 错误消息模板，变量使用{}表示
     * @param params  参数
     * @param <T>     被检查对象泛型类型
     * @return 被检查的对象
     * @throws IllegalArgumentException if the obj is {@code null}
     */
    public static <T> T notNull(final T obj, final String message, final Object... params) throws IllegalArgumentException {
        return notNull(obj, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 断言对象是否不为{@code null} ，如果为{@code null}，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.notNull(clazz, ()-&gt;{
     *      return new IllegalArgumentException("The class must not be null");
     *  });
     * </pre>
     *
     * @param obj      被检查对象
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <T>      被检查对象泛型类型
     * @param <X>      异常类型
     * @return 被检查的对象
     * @throws X if the obj is {@code null}
     */
    public static <T, X extends Throwable> T notNull(final T obj, @NonNull final Supplier<? extends X> supplier) throws X {
        if (obj == null) {
            throw supplier.get();
        }
        return obj;
    }

    // --------------------- Check String not blank

    /**
     * 检查给定字符串是否为{@code null}或空白字符串，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notBlank(name);
     * </pre>
     *
     * @param text 被检查字符串
     * @return 被检查的字符串
     * @throws IllegalArgumentException 被检查字符串为为{@code null}或空白字符串
     * @see StringUtil#isNotBlank(CharSequence)
     */
    public static <T extends CharSequence> T notBlank(final T text) throws IllegalArgumentException {
        return notBlank(text, "[Validate failed] - this String argument must have length; it must not be null or blank");
    }

    /**
     * 检查给定字符串是否为{@code null}或空白字符串，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notBlank(name, "Name must not be blank");
     * </pre>
     *
     * @param text    被检查字符串
     * @param message 错误消息模板，变量使用{}表示
     * @param params  参数
     * @return 非空字符串
     * @throws IllegalArgumentException 被检查字符串为{@code null}或空白字符串
     * @see StringUtil#isNotBlank(CharSequence)
     */
    public static <T extends CharSequence> T notBlank(final T text, final String message, final Object... params) throws IllegalArgumentException {
        return notBlank(text, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查给定字符串是否为{@code null}或空白字符串，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.notBlank(name, ()-&gt;{
     *      return new IllegalArgumentException("Name must not be blank");
     *  });
     * </pre>
     *
     * @param text     被检查字符串
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <T>      被检查字符串泛型类型
     * @param <X>      异常类型
     * @return 被检查字符串
     * @throws X 被检查字符串是否为{@code null}或空白字符串
     * @see StringUtil#isNotBlank(CharSequence)
     */
    public static <T extends CharSequence, X extends Throwable> T notBlank(final T text, @NonNull final Supplier<? extends X> supplier) throws X {
        if (StringUtil.isBlank(text)) {
            throw supplier.get();
        }
        return text;
    }

    // --------------------- Check String not empty

    /**
     * 检查给定字符串是否为{@code null}或{@code ""}，抛出 {@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notEmpty(name);
     * </pre>
     *
     * @param text 被检查字符串
     * @return 被检查的字符串
     * @throws IllegalArgumentException 被检查字符串是否为{@code null}或{@code ""}
     * @see StringUtil#isNotEmpty(CharSequence)
     */
    public static <T extends CharSequence> T notEmpty(final T text) throws IllegalArgumentException {
        return notEmpty(text, "[Validate failed] - this String argument must have length; it must not be null or empty");
    }

    /**
     * 检查给定字符串是否为{@code null}或{@code ""}，抛出 {@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notEmpty(name, "Name must not be empty");
     * </pre>
     *
     * @param text    被检查字符串
     * @param message 错误消息模板，变量使用{}表示
     * @param params  参数
     * @return 被检查字符串
     * @throws IllegalArgumentException 被检查字符串是否为{@code null}或{@code ""}
     * @see StringUtil#isNotEmpty(CharSequence)
     */
    public static <T extends CharSequence> T notEmpty(final T text, final String message, final Object... params) throws IllegalArgumentException {
        return notEmpty(text, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查给定字符串是否为{@code null}或{@code ""}，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.notEmpty(name, ()-&gt;{
     *      return new IllegalArgumentException("Name must not be empty");
     *  });
     * </pre>
     *
     * @param text     被检查字符串
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <T>      被检查字符串泛型类型
     * @param <X>      指定异常类型
     * @return 被检查字符串
     * @throws X 被检查字符串是否为{@code null}或{@code ""}
     * @see StringUtil#isNotEmpty(CharSequence)
     */
    public static <T extends CharSequence, X extends Throwable> T notEmpty(final T text, @NonNull final Supplier<? extends X> supplier) throws X {
        if (StringUtil.isEmpty(text)) {
            throw supplier.get();
        }
        return text;
    }

    // --------------------- Check array not empty

    /**
     * 检查给定数组是否包含元素，数组必须不为{@code null}且至少包含一个元素，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notEmpty(array, "The array must have elements");
     * </pre>
     *
     * @param array 被检查的数组
     * @param <T>   被检查数组泛型类型
     * @return 被检查的数组
     * @throws ValidateException if the obj array is {@code null} or has no elements
     * @see ArrayUtil#isNotEmpty(Object[])
     */
    public static <T> T[] notEmpty(final T[] array) throws IllegalArgumentException {
        return notEmpty(array, "[Validate failed] - this array must not be empty; it must contain at least one element");
    }

    /**
     * 检查给定数组是否包含元素，数组必须不为{@code null}且至少包含一个元素，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notEmpty(array, "The array must have elements");
     * </pre>
     *
     * @param array   被检查的数组
     * @param message 异常时的消息模板
     * @param params  参数列表
     * @param <T>     被检查数组泛型类型
     * @return 被检查的数组
     * @throws ValidateException if the obj array is {@code null} or has no elements
     * @see ArrayUtil#isNotEmpty(Object[])
     */
    public static <T> T[] notEmpty(final T[] array, final String message, final Object... params) throws IllegalArgumentException {
        return notEmpty(array, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查给定数组是否包含元素，数组必须不为{@code null}且至少包含一个元素，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.notEmpty(array, ()-&gt;{
     *      return new IllegalArgumentException("The array must have elements");
     *  });
     * </pre>
     *
     * @param array    被检查的数组
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <T>      被检查数组泛型类型
     * @param <X>      异常类型
     * @return 被检查的数组
     * @throws X if the obj array is {@code null} or has no elements
     * @see ArrayUtil#isNotEmpty(Object[])
     */
    public static <T, X extends Throwable> T[] notEmpty(final T[] array, @NonNull final Supplier<? extends X> supplier) throws X {
        if (ArrayUtil.isEmpty(array)) {
            throw supplier.get();
        }
        return array;
    }

    // --------------------- Check iterable not empty

    /**
     * 检查给定集合是否包含元素，集合必须不为{@code null}且至少包含一个元素，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notEmpty(iterable);
     * </pre>
     *
     * @param iterable 被检查的集合
     * @param <E>      集合元素类型
     * @param <T>      集合类型
     * @return 被检查的集合
     * @throws IllegalArgumentException if the iterable is {@code null} or has no elements
     * @see CollectionUtil#isNotEmpty(Iterable)
     */
    public static <E, T extends Iterable<E>> T notEmpty(final T iterable) throws IllegalArgumentException {
        return notEmpty(iterable, "[Validate failed] - this iterable must not be empty; it must contain at least one element");
    }

    /**
     * 检查给定集合是否包含元素，集合必须不为{@code null}且至少包含一个元素，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notEmpty(iterable, "The iterable must have elements");
     * </pre>
     *
     * @param iterable 被检查的集合
     * @param message  异常时的消息模板
     * @param params   参数列表
     * @param <E>      集合元素类型
     * @param <T>      集合类型
     * @return 被检查的集合
     * @throws IllegalArgumentException if the iterable is {@code null} or has no elements
     * @see CollectionUtil#isNotEmpty(Iterable)
     */
    public static <E, T extends Iterable<E>> T notEmpty(final T iterable, final String message, final Object... params) throws IllegalArgumentException {
        return notEmpty(iterable, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查给定集合是否包含元素，集合必须不为{@code null}且至少包含一个元素，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.notEmpty(iterable, ()-&gt;{
     *      return new IllegalArgumentException("The iterable must have elements");
     *  });
     * </pre>
     *
     * @param iterable 被检查集合
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <E>      集合元素类型
     * @param <T>      集合类型
     * @param <X>      异常类型
     * @return 被检查的集合
     * @throws X if the iterable is {@code null} or has no elements
     * @see CollectionUtil#isNotEmpty(Iterable)
     */
    public static <E, T extends Iterable<E>, X extends Throwable> T notEmpty(final T iterable, @NonNull final Supplier<? extends X> supplier) throws X {
        if (CollectionUtil.isEmpty(iterable)) {
            throw supplier.get();
        }
        return iterable;
    }

    // --------------------- Check iterator not empty

    /**
     * 检查给迭代器是否包含元素，迭代器必须不为{@code null}且至少包含一个元素，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notEmpty(iterator);
     * </pre>
     *
     * @param iterator 被检查的迭代器
     * @param <E>      迭代器元素类型
     * @param <T>      迭代器类型
     * @return 被检查的迭代器
     * @throws IllegalArgumentException if the iterator is {@code null} or has no elements
     * @see CollectionUtil#isNotEmpty(Iterator)
     */
    public static <E, T extends Iterator<E>> T notEmpty(final T iterator) throws IllegalArgumentException {
        return notEmpty(iterator, "[Validate failed] - this iterator must not be empty; it must contain at least one element");
    }

    /**
     * 检查给定迭代器是否包含元素，迭代器必须不为{@code null}且至少包含一个元素，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notEmpty(iterator, "The iterator must have elements");
     * </pre>
     *
     * @param iterator 被检查的迭代器
     * @param message  异常时的消息模板
     * @param params   参数列表
     * @param <E>      迭代器元素类型
     * @param <T>      迭代器类型
     * @return 被检查的迭代器
     * @throws IllegalArgumentException if the iterator is {@code null} or has no elements
     * @see CollectionUtil#isNotEmpty(Iterator)
     */
    public static <E, T extends Iterator<E>> T notEmpty(final T iterator, final String message, final Object... params) throws IllegalArgumentException {
        return notEmpty(iterator, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查给定迭代器是否包含元素，迭代器必须不为{@code null}且至少包含一个元素，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.notEmpty(iterator, ()-&gt;{
     *      return new IllegalArgumentException("The iterator must have elements");
     *  });
     * </pre>
     *
     * @param iterator 被检查的迭代器
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <E>      迭代器元素类型
     * @param <T>      迭代器类型
     * @param <X>      异常类型
     * @return 被检查的迭代器
     * @throws X if the iterator is {@code null} or has no elements
     * @see CollectionUtil#isNotEmpty(Iterator)
     */
    public static <E, T extends Iterator<E>, X extends Throwable> T notEmpty(final T iterator, @NonNull final Supplier<? extends X> supplier) throws X {
        if (CollectionUtil.isEmpty(iterator)) {
            throw supplier.get();
        }
        return iterator;
    }

    // --------------------- Check map not empty

    /**
     * 检查给定Map集合是否包含元素，Map集合必须不为{@code null}且至少包含一个元素，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notEmpty(map, "The map must have entries");
     * </pre>
     *
     * @param map 被检查的Map
     * @param <K> Key类型
     * @param <V> Value类型
     * @param <T> Map类型
     * @return 被检查的Map
     * @throws IllegalArgumentException if the map is {@code null} or has no entries
     * @see MapUtil#isNotEmpty(Map)
     */
    public static <K, V, T extends Map<K, V>> T notEmpty(final T map) throws IllegalArgumentException {
        return notEmpty(map, "[Validate failed] - this map must not be empty; it must contain at least one entry");
    }

    /**
     * 检查给定Map集合是否包含元素，Map集合必须不为{@code null}且至少包含一个元素，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.notEmpty(map, "The map must have entries");
     * </pre>
     *
     * @param map     被检查的Map
     * @param message 异常时的消息模板
     * @param params  参数列表
     * @param <K>     Key类型
     * @param <V>     Value类型
     * @param <T>     Map类型
     * @return 被检查的Map
     * @throws IllegalArgumentException if the map is {@code null} or has no entries
     * @see MapUtil#isNotEmpty(Map)
     */
    public static <K, V, T extends Map<K, V>> T notEmpty(final T map, final String message, final Object... params) throws IllegalArgumentException {
        return notEmpty(map, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查给定Map集合是否包含元素，Map集合必须不为{@code null}且至少包含一个元素，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.notEmpty(map, ()-&gt;{
     *      return new IllegalArgumentException("The map must have entries");
     *  });
     * </pre>
     *
     * @param map      被检查的Map
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @param <T>      Map类型
     * @param <X>      异常类型
     * @return 被检查的Map
     * @throws X if the map is {@code null} or has no entries
     * @see MapUtil#isNotEmpty(Map)
     */
    public static <K, V, T extends Map<K, V>, X extends Throwable> T notEmpty(final T map, @NonNull final Supplier<X> supplier) throws X {
        if (MapUtil.isEmpty(map)) {
            throw supplier.get();
        }
        return map;
    }

    // --------------------- Check array no null elements

    /**
     * 检查给定数组是否不包含{@code null}元素，如果数组为空或 {@code null}将被认为不包含，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.noNullElements(array);
     * </pre>
     *
     * @param array 被检查的数组
     * @param <T>   数组元素类型
     * @return 被检查的数组
     * @throws IllegalArgumentException if the object array contains a {@code null} element
     */
    public static <T> T[] noNullElements(final T[] array) throws IllegalArgumentException {
        return noNullElements(array, "[Validate failed] - this array must not contain any null elements");
    }

    /**
     * 断言给定数组是否不包含{@code null}元素，如果数组为空或 {@code null}将被认为不包含，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.noNullElements(array, "The array must have non-null elements");
     * </pre>
     *
     * @param array   被检查的数组
     * @param message 异常时的消息模板
     * @param params  参数列表
     * @param <T>     数组元素类型
     * @return 被检查的数组
     * @throws IllegalArgumentException if the object array contains a {@code null} element
     * @see ArrayUtil#hasNull(Object...)
     */
    public static <T> T[] noNullElements(final T[] array, final String message, final Object... params) throws IllegalArgumentException {
        return noNullElements(array, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 断言给定数组是否不包含{@code null}元素，如果数组为空或 {@code null}将被认为不包含，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.noNullElements(array, ()-&gt;{
     *      return new IllegalArgumentException("The array must have non-null elements");
     *  });
     * </pre>
     *
     * @param array    被检查的数组
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <T>      数组元素类型
     * @param <X>      异常类型
     * @return 被检查的数组
     * @throws X if the object array contains a {@code null} element
     * @see ArrayUtil#hasNull(Object...)
     */
    public static <T, X extends Throwable> T[] noNullElements(final T[] array, final Supplier<X> supplier) throws X {
        if (ArrayUtil.hasNull((Object[]) array)) {
            throw supplier.get();
        }
        return array;
    }

    // --------------------- Check iterable no null elements

    /**
     * 检查给定集合是否不包含{@code null}元素，如果集合为空或 {@code null}将被认为不包含，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.noNullElements(collection);
     * </pre>
     *
     * @param collection 被检查的集合
     * @param <E>        集合元素类型
     * @param <T>        集合类型
     * @return 被检查的集合
     * @throws IllegalArgumentException if the object iterable contains a {@code null} element
     * @see CollectionUtil#hasNull(Collection)
     */
    public static <E, T extends Collection<E>> T noNullElements(final T collection) throws IllegalArgumentException {
        return noNullElements(collection, "[Validate failed] - this collection must not contain any null elements");
    }

    /**
     * 检查给定集合是否不包含{@code null}元素，如果集合为空或 {@code null}将被认为不包含，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.noNullElements(array, "The collection must have non-null elements");
     * </pre>
     *
     * @param collection 被检查的集合
     * @param message    异常时的消息模板
     * @param params     参数列表
     * @param <E>        集合元素类型
     * @param <T>        集合类型
     * @return 被检查的集合
     * @throws IllegalArgumentException if the object collection contains a {@code null} element
     * @see CollectionUtil#hasNull(Collection)
     */
    public static <E, T extends Collection<E>> T noNullElements(final T collection, final String message, final Object... params) throws IllegalArgumentException {
        return noNullElements(collection, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查给定集合是否不包含{@code null}元素，如果集合为空或 {@code null}将被认为不包含，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.noNullElements(iterable, ()-&gt;{
     *      return new IllegalArgumentException("The collection must have non-null elements");
     *  });
     * </pre>
     *
     * @param collection 被检查的集合
     * @param supplier   错误抛出异常附带的消息生产接口
     * @param <E>        集合元素类型
     * @param <T>        集合类型
     * @param <X>        异常类型
     * @return 被检查的集合
     * @throws X if the object collection contains a {@code null} element
     * @see CollectionUtil#hasNull(Collection)
     */
    public static <E, T extends Collection<E>, X extends Throwable> T noNullElements(final T collection, @NonNull final Supplier<? extends X> supplier) throws X {
        if (CollectionUtil.hasNull(collection)) {
            throw supplier.get();
        }
        return collection;
    }

    // --------------------- Check type instanceOf

    /**
     * 检查给定对象是否是指定类的实例，如果不是，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.instanceOf(Foo.class, foo);
     * </pre>
     *
     * @param type 被检查对象匹配的类型
     * @param obj  被检查对象
     * @param <T>  被检查对象泛型类型
     * @return 被检查的对象
     * @throws IllegalArgumentException if the obj is not an instance of clazz
     * @see Class#isInstance(Object)
     */
    public static <T> T isInstanceOf(final Class<?> type, final T obj) throws IllegalArgumentException {
        return isInstanceOf(type, obj, "Object [{}] is not instanceof [{}]", obj, type);
    }

    /**
     * 检查给定对象是否是指定类的实例，如果不是，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.instanceOf(Foo.class, foo, "foo must be an instance of class Foo");
     * </pre>
     *
     * @param type    被检查对象匹配的类型
     * @param obj     被检查对象
     * @param message 异常时的消息模板
     * @param params  参数列表
     * @param <T>     被检查对象泛型类型
     * @return 被检查对象
     * @throws IllegalArgumentException if the obj is not an instance of clazz
     * @see Class#isInstance(Object)
     */
    public static <T> T isInstanceOf(final Class<?> type, final T obj, final String message, final Object... params) throws IllegalArgumentException {
        return isInstanceOf(type, obj, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查给定对象是否是指定类的实例，如果不是，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.isInstanceOf(Foo.class, foo, ()-&gt;{
     *      return new IllegalArgumentException("foo must be an instance of class Foo");
     *  });
     * </pre>
     *
     * @param type     被检查对象匹配的类型
     * @param obj      被检查对象
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <T>      被检查对象泛型类型
     * @param <X>      异常类型
     * @return 被检查对象
     * @throws X if the obj is not an instance of clazz
     * @see Class#isInstance(Object)
     */
    public static <T, X extends Throwable> T isInstanceOf(final Class<?> type, final T obj, @NonNull final Supplier<? extends X> supplier) throws X {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw supplier.get();
        }
        return obj;
    }

    // --------------------- Check superType isAssignable subType

    /**
     * 检查{@code superType.isAssignableFrom(subType)} 是否为{@code true}，如果不是，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.isAssignable(Number.class, myClass);
     * </pre>
     *
     * @param superType 需要检查的父类或接口
     * @param subType   需要检查的子类
     * @throws IllegalArgumentException 如果子类非继承父类，抛出此异常
     */
    public static void isAssignable(final Class<?> superType, final Class<?> subType) throws IllegalArgumentException {
        isAssignable(superType, subType, "[{}] is not assignable to [{}]", subType, superType);
    }

    /**
     * 检查{@code superType.isAssignableFrom(subType)} 是否为{@code true}，如果不是，抛出{@link IllegalArgumentException}异常。
     *
     * <pre class="code">
     * Validate.isAssignable(Number.class, myClass, "myClass must can be assignable to class Number");
     * </pre>
     *
     * @param superType 需要检查的父类或接口
     * @param subType   需要检查的子类
     * @param message   异常时的消息模板
     * @param params    参数列表
     * @throws IllegalArgumentException 如果子类非继承父类，抛出此异常
     */
    public static void isAssignable(final Class<?> superType, final Class<?> subType, final String message, final Object... params) throws IllegalArgumentException {
        isAssignable(superType, subType, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查{@code superType.isAssignableFrom(subType)} 是否为{@code true}，如果不是，抛出指定类型的异常。
     *
     * <pre class="code">
     * Validate.isInstanceOf(Number.class, myClass, ()-&gt;{
     *      return new IllegalArgumentException("myClass must can be assignable to class Number");
     *  });
     * </pre>
     *
     * @param superType 需要检查的父类或接口
     * @param subType   需要检查的子类
     * @param supplier  错误抛出异常附带的消息生产接口
     * @param <X>       异常类型
     * @throws X 如果子类非继承父类，抛出此异常
     */
    public static <X extends Throwable> void isAssignable(final Class<?> superType, final Class<?> subType, @NonNull final Supplier<? extends X> supplier) throws X {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw supplier.get();
        }
    }

    // --------------------- Check char sequence index

    /**
     * 检查字符串下标是否越界，如果越界，抛出{@link IllegalArgumentException}异常。
     *
     * @param chars 需要检查的字符串对象
     * @param index 长度
     * @param <T>   字符串泛型
     * @return 被检查的字符串对象
     * @throws IllegalArgumentException 如果chars is null，index < 0，index >= size 抛出此异常
     */
    public static <T extends CharSequence> T checkIndex(final T chars, final int index) throws IllegalArgumentException {
        return checkIndex(chars, index, "[Validate failed] - The validated character sequence index is invalid: {}", index);
    }

    /**
     * 检查字符串下标是否越界，如果越界，抛出{@link IllegalArgumentException}异常。
     *
     * @param chars   需要检查的字符串对象
     * @param index   长度
     * @param message 异常时的消息模板
     * @param params  参数列表
     * @param <T>     字符串泛型
     * @return 被检查的字符串对象
     * @throws IllegalArgumentException 如果chars is null，index < 0，index >= size 抛出此异常
     */
    public static <T extends CharSequence> T checkIndex(final T chars, final int index, final String message, final Object... params) throws IllegalArgumentException {
        return checkIndex(chars, index, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查字符串下标是否越界，如果越界，抛出指定类型的异常。
     *
     * @param chars    需要检查的字符串对象
     * @param index    长度
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <T>      字符串泛型
     * @param <X>      异常类型
     * @return 被检查的字符串对象
     * @throws X 如果chars is null，index < 0，index >= size 抛出此异常
     */
    public static <T extends CharSequence, X extends Throwable> T checkIndex(final T chars, final int index, @NonNull final Supplier<? extends X> supplier) throws X {
        notNull(chars);
        if (index < 0 || index >= chars.length()) {
            throw supplier.get();
        }
        return chars;
    }

    // --------------------- Check array index

    /**
     * 检查数组下标是否越界，如果越界，抛出{@link IllegalArgumentException}异常。
     *
     * @param array 需要检查的数组对象
     * @param index 长度
     * @param <T>   数组泛型
     * @return 被检查的数组对象
     * @throws IllegalArgumentException 如果array is null，index < 0，index >= size 抛出此异常
     */
    public static <T> T[] checkIndex(final T[] array, final int index) throws IllegalArgumentException {
        return checkIndex(array, index, "[Validate failed] - The validated array index is invalid: {}", index);
    }

    /**
     * 检查数组下标是否越界，如果越界，抛出{@link IllegalArgumentException}异常。
     *
     * @param array   需要检查的数组对象
     * @param index   长度
     * @param message 异常时的消息模板
     * @param params  参数列表
     * @param <T>     数组泛型
     * @return 被检查的数组对象
     * @throws IllegalArgumentException 如果array is null，index < 0，index >= size 抛出此异常
     */
    public static <T> T[] checkIndex(final T[] array, final int index, final String message, final Object... params) throws IllegalArgumentException {
        return checkIndex(array, index, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查数组下标是否越界，如果越界，抛出指定类型的异常。
     *
     * @param array    需要检查的数组对象
     * @param index    长度
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <T>      数组泛型
     * @param <X>      异常类型
     * @return 被检查的数组对象
     * @throws X 如果array is null，index < 0，index >= size 抛出此异常
     */
    public static <T, X extends Throwable> T[] checkIndex(final T[] array, final int index, @NonNull final Supplier<? extends X> supplier) throws X {
        notNull(array);
        if (index < 0 || index >= array.length) {
            throw supplier.get();
        }
        return array;
    }

    // --------------------- Check collection index

    /**
     * 检查集合下标是否越界，如果越界，抛出{@link IllegalArgumentException}异常。
     *
     * @param collection 需要检查的集合对象
     * @param index      长度
     * @param <E>        集合元素类型
     * @param <T>        集合泛型
     * @return 被检查的集合对象
     * @throws IllegalArgumentException 如果collection is null，index < 0，index >= size 抛出此异常
     */
    public static <E, T extends Collection<E>> T checkIndex(final T collection, final int index) throws IllegalArgumentException {
        return checkIndex(collection, index, "[Validate failed] - The validated collection index is invalid: {}", index);
    }

    /**
     * 检查集合下标是否越界，如果越界，抛出{@link IllegalArgumentException}异常。
     *
     * @param collection 需要检查的集合对象
     * @param index      长度
     * @param message    异常时的消息模板
     * @param params     参数列表
     * @param <E>        集合元素类型
     * @param <T>        集合泛型
     * @return 被检查的集合对象
     * @throws IllegalArgumentException 如果collection is null，index < 0，index >= size 抛出此异常
     */
    public static <E, T extends Collection<E>> T checkIndex(final T collection, final int index, final String message, final Object... params) throws IllegalArgumentException {
        return checkIndex(collection, index, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查集合下标是否越界，如果越界，抛出指定类型的异常。
     *
     * @param collection 需要检查的集合对象
     * @param index      长度
     * @param supplier   错误抛出异常附带的消息生产接口
     * @param <E>        集合元素类型
     * @param <T>        集合泛型
     * @param <X>        异常类型
     * @return 被检查的集合对象
     * @throws X 如果collection is null，index < 0，index >= size 抛出此异常
     */
    public static <E, T extends Collection<E>, X extends Throwable> T checkIndex(final T collection, final int index, @NonNull final Supplier<? extends X> supplier) throws X {
        notNull(collection);
        if (index < 0 || index >= collection.size()) {
            throw supplier.get();
        }
        return collection;
    }

    // --------------------- Check int value between

    /**
     * 检查值是否在指定范围内，若不在，抛出{@link IllegalArgumentException}异常。
     *
     * @param value 值
     * @param min   最小值（包含）
     * @param max   最大值（包含）
     * @return 检查后的长度值
     * @throws IllegalArgumentException 如果value < min 或者 value > max 抛出此异常
     */
    public static int checkBetween(final int value, final int min, final int max) throws IllegalArgumentException {
        return checkBetween(value, min, max, "The value must be between {} and {}.", min, max);
    }

    /**
     * 检查值是否在指定范围内，若不在，抛出{@link IllegalArgumentException}异常。
     *
     * @param value   值
     * @param min     最小值（包含）
     * @param max     最大值（包含）
     * @param message 异常时的消息模板
     * @param params  参数列表
     * @return 检查后的长度值
     * @throws IllegalArgumentException 如果value < min 或者 value > max 抛出此异常
     */
    public static int checkBetween(final int value, final int min, final int max, final String message, final Object... params) throws IllegalArgumentException {
        return checkBetween(value, min, max, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查值是否在指定范围内，若不在，抛出指定类型的异常。
     *
     * @param value    值
     * @param min      最小值（包含）
     * @param max      最大值（包含）
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <X>      异常类型
     * @return 检查后的长度值
     * @throws X 如果value < min 或者 value > max 抛出此异常
     */
    public static <X extends Throwable> int checkBetween(final int value, final int min, final int max, @NonNull final Supplier<? extends X> supplier) throws X {
        if (value < min || value > max) {
            throw supplier.get();
        }
        return value;
    }

    // --------------------- Check long value between

    /**
     * 检查值是否在指定范围内，若不在，抛出{@link IllegalArgumentException}异常。
     *
     * @param value 值
     * @param min   最小值（包含）
     * @param max   最大值（包含）
     * @return 检查后的长度值
     * @throws IllegalArgumentException 如果value < min 或者 value > max 抛出此异常
     */
    public static long checkBetween(final long value, final long min, final long max) throws IllegalArgumentException {
        return checkBetween(value, min, max, "The value must be between {} and {}.", min, max);
    }

    /**
     * 检查值是否在指定范围内，若不在，抛出{@link IllegalArgumentException}异常。
     *
     * @param value   值
     * @param min     最小值（包含）
     * @param max     最大值（包含）
     * @param message 异常时的消息模板
     * @param params  参数列表
     * @return 检查后的长度值
     * @throws IllegalArgumentException 如果value < min 或者 value > max 抛出此异常
     */
    public static long checkBetween(final long value, final long min, final long max, final String message, final Object... params) throws IllegalArgumentException {
        return checkBetween(value, min, max, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查值是否在指定范围内，若不在，抛出指定类型的异常。
     *
     * @param value    值
     * @param min      最小值（包含）
     * @param max      最大值（包含）
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <X>      异常类型
     * @return 检查后的长度值
     * @throws X 如果value < min 或者 value > max 抛出此异常
     */
    public static <X extends Throwable> long checkBetween(final long value, final long min, final long max, @NonNull final Supplier<? extends X> supplier) throws X {
        if (value < min || value > max) {
            throw supplier.get();
        }
        return value;
    }

    // --------------------- Check float value between

    /**
     * 检查值是否在指定范围内，若不在，抛出{@link IllegalArgumentException}异常。
     *
     * @param value 值
     * @param min   最小值（包含）
     * @param max   最大值（包含）
     * @return 检查后的长度值
     * @throws IllegalArgumentException 如果value < min 或者 value > max 抛出此异常
     */
    public static float checkBetween(final float value, final float min, final float max) throws IllegalArgumentException {
        return checkBetween(value, min, max, "The value must be between {} and {}.", min, max);
    }

    /**
     * 检查值是否在指定范围内，若不在，抛出{@link IllegalArgumentException}异常。
     *
     * @param value   值
     * @param min     最小值（包含）
     * @param max     最大值（包含）
     * @param message 异常时的消息模板
     * @param params  参数列表
     * @return 检查后的长度值
     * @throws IllegalArgumentException 如果value < min 或者 value > max 抛出此异常
     */
    public static float checkBetween(final float value, final float min, final float max, final String message, final Object... params) throws IllegalArgumentException {
        return checkBetween(value, min, max, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查值是否在指定范围内，若不在，抛出指定类型的异常。
     *
     * @param value    值
     * @param min      最小值（包含）
     * @param max      最大值（包含）
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <X>      异常类型
     * @return 检查后的长度值
     * @throws X 如果value < min 或者 value > max 抛出此异常
     */
    public static <X extends Throwable> float checkBetween(final float value, final float min, final float max, @NonNull final Supplier<? extends X> supplier) throws X {
        if (value < min || value > max) {
            throw supplier.get();
        }
        return value;
    }

    // --------------------- Check double value between

    /**
     * 检查值是否在指定范围内，若不在，抛出{@link IllegalArgumentException}异常。
     *
     * @param value 值
     * @param min   最小值（包含）
     * @param max   最大值（包含）
     * @return 检查后的长度值
     * @throws IllegalArgumentException 如果value < min 或者 value > max 抛出此异常
     */
    public static double checkBetween(final double value, final double min, final double max) throws IllegalArgumentException {
        return checkBetween(value, min, max, "The value must be between {} and {}.", min, max);
    }

    /**
     * 检查值是否在指定范围内，若不在，抛出{@link IllegalArgumentException}异常。
     *
     * @param value   值
     * @param min     最小值（包含）
     * @param max     最大值（包含）
     * @param message 异常时的消息模板
     * @param params  参数列表
     * @return 检查后的长度值
     * @throws IllegalArgumentException 如果value < min 或者 value > max 抛出此异常
     */
    public static double checkBetween(final double value, final double min, final double max, final String message, final Object... params) throws IllegalArgumentException {
        return checkBetween(value, min, max, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查值是否在指定范围内，若不在，抛出指定类型的异常。
     *
     * @param value    值
     * @param min      最小值（包含）
     * @param max      最大值（包含）
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <X>      异常类型
     * @return 检查后的长度值
     * @throws X 如果value < min 或者 value > max 抛出此异常
     */
    public static <X extends Throwable> double checkBetween(final double value, final double min, final double max, @NonNull final Supplier<? extends X> supplier) throws X {
        if (value < min || value > max) {
            throw supplier.get();
        }
        return value;
    }

    // --------------------- Check number value between

    /**
     * 检查值是否在指定范围内，若不在，抛出{@link IllegalArgumentException}异常。
     *
     * @param value 值
     * @param min   最小值（包含）
     * @param max   最大值（包含）
     * @return 检查后的长度值
     * @throws IllegalArgumentException 如果value < min 或者 value > max 抛出此异常
     */
    public static BigDecimal checkBetween(final BigDecimal value, final BigDecimal min, final BigDecimal max) throws IllegalArgumentException {
        return checkBetween(value, min, max, "The value must be between {} and {}.", min, max);
    }

    /**
     * 检查值是否在指定范围内，若不在，抛出{@link IllegalArgumentException}异常。
     *
     * @param value   值
     * @param min     最小值（包含）
     * @param max     最大值（包含）
     * @param message 异常时的消息模板
     * @param params  参数列表
     * @return 检查后的长度值
     * @throws IllegalArgumentException 如果value < min 或者 value > max 抛出此异常
     */
    public static BigDecimal checkBetween(final BigDecimal value, final BigDecimal min, final BigDecimal max, final String message, final Object... params) throws IllegalArgumentException {
        return checkBetween(value, min, max, () -> new IllegalArgumentException(StringUtil.format(message, params)));
    }

    /**
     * 检查值是否在指定范围内，若不在，抛出指定类型的异常。
     *
     * @param value    值
     * @param min      最小值（包含）
     * @param max      最大值（包含）
     * @param supplier 错误抛出异常附带的消息生产接口
     * @param <X>      异常类型
     * @return 检查后的长度值
     * @throws X 如果value < min 或者 value > max 抛出此异常
     */
    public static <X extends Throwable> BigDecimal checkBetween(final BigDecimal value, final BigDecimal min, final BigDecimal max, @NonNull final Supplier<? extends X> supplier) throws X {
        notNull(value);
        notNull(min);
        notNull(max);
        if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
            throw supplier.get();
        }
        return value;
    }
}
