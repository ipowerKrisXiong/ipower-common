package com.ipower.framework.common.core.collection;

import com.ipower.framework.common.core.constant.StringPool;
import com.ipower.framework.common.core.exception.UtilException;
import com.ipower.framework.common.core.exception.unchecked.ValidateException;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.lang.StringUtil;
import com.ipower.framework.common.core.lang.Validate;
import com.ipower.framework.common.core.stream.Streams;
import org.springframework.lang.NonNull;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

/**
 * Class description goes here.
 *
 * @author kris
 */
public final class ArrayUtil extends ArrayValidator {

    private ArrayUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * <p>获取数组长度，如果数组参数为{@code null}，返回0</p>
     *
     * @param array 数组对象
     * @return 数组长度
     */
    public static int length(final Object[] array) {
        return array == null ? 0 : array.length;
    }

    /**
     * <p>获取数组对象的元素类型，如果数组参数为{@code null}，返回{@code null}</p>
     *
     * @param array 数组对象
     * @param <T>   数组元素类型
     * @return 元素类型
     */
    public static <T> Class<T> getComponentType(final T[] array) {
        //noinspection unchecked
        return null == array ? null : (Class<T>) array.getClass().getComponentType();
    }

    /**
     * <p>新建一个空数组，会创建一个长度为{@code 0}的数组</p>
     *
     * @param componentType 元素类型
     * @param <T>           数组元素类型
     * @return 空数组
     */
    public static <T> T[] newArray(final Class<T> componentType) {
        return newArray(componentType, 0);
    }

    /**
     * <p>新建一个指定长度且数组元素都为{@code null}的数组。</p>
     *
     * <p>注意：该方法与 {@link Array#newInstance(Class, int)} 的区别在于，该方法有泛型作为约束，省去了强制类型转换</p>
     *
     * @param componentType 元素类型
     * @param size          大小
     * @param <T>           数组元素类型
     * @return 数组
     */
    public static <T> T[] newArray(@NonNull final Class<T> componentType, int size) {
        Validate.notNull(componentType, "componentType must not null");
        //noinspection unchecked
        return (T[]) Array.newInstance(componentType, size);
    }

    // -------------------------------------------------------------------- get

    /**
     * <p>获取数组对象中指定index的值。</p>
     * <p>注意：如果数组下标越界（下标值为负数或是下标值大于等于数组长度），返回{@code null}</p>
     *
     * @param array 数组对象
     * @param index 下标
     * @param <T>   数组元素类型
     * @return 获取下标对应的值
     */
    public static <T> T get(final T[] array, final int index) {
        return (isEmpty(array) || index < 0 || index >= array.length) ? null : array[index];
    }

    /**
     * <p>获取数组对象中指定多个下标元素值，组成新的数组，数组长度为下标列表的长度。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>如果数组对象为{@code null}，会返回{@code null}</li>
     *     <li>如果下标列表为{@code null}或是空，会返回一个空数组</li>
     *     <li>如果下标列表的某个值越界，将会用{@code null}填充</li>
     * </ul>
     *
     * @param array   数组对象
     * @param indexes 下标列表
     * @param <T>     数组元素类型
     * @return 获取下标对应的值集
     */
    public static <T> T[] get(final T[] array, final int... indexes) {
        if (array == null) {
            return null;
        }
        final int length = indexes == null ? 0 : indexes.length;
        final T[] result = newArray(getComponentType(array), length);
        for (int i = 0; i < length; i++) {
            result[i] = get(array, indexes[i]);
        }
        return result;
    }

    // -------------------------------------------------------------------- reverse

    /**
     * <p>反转数组，会将数组内的元素反转位置</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>如果数组为{@code null}，将会返回{@code null}</li>
     *     <li>如果数组为空，将会返回空数组</li>
     * </ul>
     *
     * @param array 数组，会变更
     * @param <T>   数组元素类型
     * @return 反转后的原数组
     */
    public static <T> T[] reverse(final T[] array) {
        if (isEmpty(array)) {
            return array;
        }
        int i = 0, j = array.length - 1;
        T tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    // -------------------------------------------------------------------- min and max

    /**
     * <p>获取数组中的最小值</p>
     * <p>注意：如果要取值的数组为{@code null}，会抛出{@link ValidateException}异常</p>
     *
     * @param array 要取值的数组，不允许为{@code null}
     * @param <T>   用于约束元素类型，必须是实现了{@link Comparable}接口的类型元素
     * @return 数组中的最小值
     */
    public static <T extends Comparable<? super T>> T min(@NonNull final T[] array) {
        return min(array, null);
    }

    /**
     * <p>使用比较器来获取数组中的最小值</p>
     * <p>注意：如果要取值的数组为{@code null}，会抛出{@link ValidateException}异常</p>
     *
     * @param array      数字数组
     * @param comparator 比较器，若值{@code null}，会按照默认比较
     * @param <T>        用于约束元素类型，必须是实现了{@link Comparable}接口的类型元素
     * @return 数组中的最小值
     */
    public static <T extends Comparable<? super T>> T min(@NonNull final T[] array, final Comparator<T> comparator) {
        Validate.notEmpty(array, "array must not empty");
        T min = array[0];
        for (T t : array) {
            if (ObjectUtil.compare(min, t, comparator) > 0) {
                min = t;
            }
        }
        return min;
    }

    /**
     * <p>获取数组中的最大值</p>
     * <p>注意：如果要取值的数组为{@code null}，会抛出{@link ValidateException}异常</p>
     *
     * @param array 要取值的数组，不允许为{@code null}
     * @param <T>   用于约束元素类型，必须是实现了{@link Comparable}接口的类型元素
     * @return 数组中的最大值
     */
    public static <T extends Comparable<? super T>> T max(@NonNull final T[] array) {
        return max(array, null);
    }

    /**
     * <p>使用比较器来获取数组中的最大值</p>
     * <p>注意：如果要取值的数组为{@code null}，会抛出{@link ValidateException}异常</p>
     *
     * @param array      数字数组
     * @param comparator 比较器，若值{@code null}，会按照默认比较
     * @param <T>        用于约束元素类型，必须是实现了{@link Comparable}接口的类型元素
     * @return 数组中的最大值
     */
    public static <T extends Comparable<? super T>> T max(@NonNull final T[] array, final Comparator<T> comparator) {
        Validate.notEmpty(array, "array must not empty");
        T max = array[0];
        for (T t : array) {
            if (ObjectUtil.compare(max, t, comparator) < 0) {
                max = t;
            }
        }
        return max;
    }

    // -------------------------------------------------------------------- distinct

    /**
     * <p>去除数组中的重复元素，去重后生成新的数组，原数组不变。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>此方法通过{@link LinkedHashSet} 去重，所以数组元素的顺序不会发生改变</li>
     *     <li>如果要去重的数组为{@code null}或是空，直接返回原数组对象</li>
     * </ul>
     *
     * @param array 需要去重的数组
     * @param <T>   数组元素类型
     * @return 去重后的数组
     */
    public static <T> T[] distinct(final T[] array) {
        return isEmpty(array) ? array : toArray(Lists.linkedHashSet(array), getComponentType(array));
    }

    // -------------------------------------------------------------------- add or insert

    /**
     * <p>将新元素数组添加到已有数组中，从已有数组的末尾添加，添加新元素会生成一个新的数组，不影响原数组</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>会优先判断新元素数组是否为{@code null}或空，如果是，则直接返回原数组</li>
     *     <li>再判断原数组是否为{@code null}或空，如果是，则直接返回新元素数组</li>
     * </ul>
     *
     * @param array    已有数组
     * @param elements 新元素数组
     * @param <T>      数组元素类型
     * @return 添加新元素数组之后的新数组
     */
    @SafeVarargs
    public static <T> T[] add(final T[] array, final T... elements) {
        return insert(array, length(array), elements);
    }

    /**
     * <p>将新元素数组插入到到已有数组中的某个位置，插入新元素数组后会生成一个新的数组，不影响原数组</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>会优先判断新元素数组是否为{@code null}或空，如果是，则直接返回原数组</li>
     *     <li>再判断原数组是否为{@code null}或空，如果是，则直接返回新元素数组</li>
     *     <li>如果插入位置为为负数，会将插入位置赋值为{@code null}</li>
     *     <li>如果插入位置大于原数组长度，则会用{@code null}空挡位</li>
     * </ul>
     *
     * @param array    已有数组
     * @param index    插入位置，此位置为对应此位置元素之前的空档
     * @param elements 新元素数组
     * @param <T>      数组元素类型
     * @return 插入新元素数组之后的新数组
     */
    @SafeVarargs
    public static <T> T[] insert(final T[] array, final int index, final T... elements) {
        if (isEmpty(elements)) {
            return array;
        }
        if (isEmpty(array)) {
            return elements;
        }
        // 索引位置，如果为负数，会给默认值0
        final int i = Math.max(index, 0);
        // 计算新数组的长度，并生产新的数组
        final int newLength = Math.max(array.length, i) + elements.length;
        final T[] result = newArray(getComponentType(array), newLength);
        // 拷贝起始位置值、结束位置值
        System.arraycopy(array, 0, result, 0, Math.min(array.length, i));
        System.arraycopy(elements, 0, result, i, elements.length);
        // 拷贝插入位置值
        if (i < array.length) {
            System.arraycopy(array, i, result, Math.addExact(i, elements.length), Math.subtractExact(array.length, i));
        }
        return result;
    }

    // -------------------------------------------------------------------- filter

    /**
     * <p>数组内容过滤，通过传入的Predicate实现来过滤返回需要的元素内容。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>如果需要过滤的数组为{@code null}或空，或者断言器为{@code null}，会直接返回原数组对象</li>
     *     <li>过滤出需要的对象，是将{@link Predicate#test(Object)}方法返回{@code true}的对象将被加入结果数组中</li>
     * </ul>
     *
     * @param array     需要过滤的数组
     * @param predicate 断言器，用于定义过滤规则，若{@code null}表示不过滤，返回原数组
     * @param <T>       数组元素类型
     * @return 过滤后的数组
     */
    public static <T> T[] filter(final T[] array, final Predicate<? super T> predicate) {
        if (isEmpty(array) || predicate == null) {
            return array;
        }
        // 过滤
        List<T> list = Streams.of(array).filter(predicate).toList();
        return toArray(list, getComponentType(array));
    }

    // -------------------------------------------------------------------- join

    /**
     * <p>使用指定的连接符，将数组元素连接成一个字符串</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>如果连接符为{@code null}，会将其转换为 ""</li>
     *     <li>会把数组中为{@code null}的元素转换位字符的"null"</li>
     * </ul>
     *
     * @param array     需要连接的数组
     * @param delimiter 连接符
     * @param <T>       数组元素类型
     * @return 连接后的字符串
     */
    public static <T> String join(final T[] array, final CharSequence delimiter) {
        StringJoiner joiner = new StringJoiner(ObjectUtil.nullToDefault(delimiter, StringPool.EMPTY));
        Streams.of(array).map(it -> StringUtil.toString(it, false)).forEach(joiner::add);
        return joiner.toString();
    }

    // -------------------------------------------------------------------- wrap

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Integer[] wrap(int... values) {
        if (null == values) {
            return null;
        }
        final int length = values.length;
        if (0 == length) {
            return new Integer[0];
        }

        final Integer[] array = new Integer[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Long[] wrap(long... values) {
        if (null == values) {
            return null;
        }
        final int length = values.length;
        if (0 == length) {
            return new Long[0];
        }

        final Long[] array = new Long[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Character[] wrap(char... values) {
        if (null == values) {
            return null;
        }
        final int length = values.length;
        if (0 == length) {
            return new Character[0];
        }

        final Character[] array = new Character[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Byte[] wrap(byte... values) {
        if (null == values) {
            return null;
        }
        final int length = values.length;
        if (0 == length) {
            return new Byte[0];
        }

        final Byte[] array = new Byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Short[] wrap(short... values) {
        if (null == values) {
            return null;
        }
        final int length = values.length;
        if (0 == length) {
            return new Short[0];
        }

        final Short[] array = new Short[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Float[] wrap(float... values) {
        if (null == values) {
            return null;
        }
        final int length = values.length;
        if (0 == length) {
            return new Float[0];
        }

        final Float[] array = new Float[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Double[] wrap(double... values) {
        if (null == values) {
            return null;
        }
        final int length = values.length;
        if (0 == length) {
            return new Double[0];
        }

        final Double[] array = new Double[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 将原始类型数组包装为包装类型
     *
     * @param values 原始类型数组
     * @return 包装类型数组
     */
    public static Boolean[] wrap(boolean... values) {
        if (null == values) {
            return null;
        }
        final int length = values.length;
        if (0 == length) {
            return new Boolean[0];
        }

        final Boolean[] array = new Boolean[length];
        for (int i = 0; i < length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * 包装数组对象
     *
     * @param obj 对象，可以是对象数组或者基本类型数组
     * @return 包装类型数组或对象数组
     * @throws UtilException 对象为非数组
     */
    public static Object[] wrap(Object obj) {
        if (null == obj) {
            return null;
        }
        if (isArray(obj)) {
            try {
                return (Object[]) obj;
            } catch (Exception e) {
                final String className = obj.getClass().getComponentType().getName();
                return switch (className) {
                    case "long" -> wrap((long[]) obj);
                    case "int" -> wrap((int[]) obj);
                    case "short" -> wrap((short[]) obj);
                    case "char" -> wrap((char[]) obj);
                    case "byte" -> wrap((byte[]) obj);
                    case "boolean" -> wrap((boolean[]) obj);
                    case "float" -> wrap((float[]) obj);
                    case "double" -> wrap((double[]) obj);
                    default -> throw new UtilException(e);
                };
            }
        }
        throw new UtilException(StringUtil.format("[{}] is not Array!", obj.getClass()));
    }

    // -------------------------------------------------------------------- contains

    /**
     * <p>判断数组中是否包含指定的元素</p>
     *
     * <p>注意：方法内部使用{@link ObjectUtil#equals(Object, Object)}对数组元素做比较，在判断是否包含精度数字{@link BigDecimal}时需要慎用，如：</p>
     * <ul>
     *     <li>{@code BigDecimal[] decimals = {new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0")}}</li>
     *     <li>{@code ArrayUtil.contains(decimals, new BigDecimal("1.0")      // true}</li>
     *     <li>{@code ArrayUtil.contains(decimals, new BigDecimal("1.00")     // false}</li>
     *     <li>{@code ArrayUtil.contains(decimals, new BigDecimal(1)          // false}</li>
     * </ul>
     *
     * @param array 数组
     * @param value 被检查的元素
     * @param <T>   数组元素类型
     * @return 是否包含指定的元素
     */
    public static <T> boolean contains(final T[] array, final T value) {
        return Streams.of(array).anyMatch(t -> ObjectUtil.equals(t, value));
    }

    /**
     * <p>判断数组中是否包含指定元素中的任意一个</p>
     *
     * <p>注意：方法内部使用{@link ObjectUtil#equals(Object, Object)}对数组元素做比较，在判断是否包含精度数字{@link BigDecimal}时需要慎用</p>
     *
     * @param array  数组
     * @param values 被检查的多个元素
     * @param <T>    数组元素类型
     * @return 是否包含指定元素中的任意一个
     */
    @SafeVarargs
    public static <T> boolean containsAny(final T[] array, final T... values) {
        return Streams.of(values).anyMatch(it -> contains(array, it));
    }

    /**
     * <p>判断数组中是否包含全部的指定元素</p>
     *
     * <p>注意：方法内部使用{@link ObjectUtil#equals(Object, Object)}对数组元素做比较，在判断是否包含精度数字{@link BigDecimal}时需要慎用</p>
     *
     * @param array  数组
     * @param values 被检查的多个元素
     * @param <T>    数组元素类型
     * @return 是否包含全部的指定元素
     */
    @SafeVarargs
    public static <T> boolean containsAll(final T[] array, final T... values) {
        return isNotEmpty(values) && Streams.of(values).allMatch(it -> contains(array, it));
    }

    // -------------------------------------------------------------------- remove

    /**
     * <p>移除数组中指定的元素，移除成功，会返回移除元素之后的新数组</p>
     *
     * <p>注意：会移除匹配到的所有元素</p>
     *
     * @param array   数组对象
     * @param element 要移除的元素
     * @param <T>     数组元素类型
     * @return 移除指定元素后的新数组或原数组
     */
    public static <T> T[] remove(final T[] array, final T element) {
        return remove(array, it -> ObjectUtil.equals(it, element));
    }

    /**
     * <p>移除数组中符合断言条件的元素，移除成功，会返回移除元素之后的新数组</p>
     *
     * <p>注意：会移除匹配到的所有元素</p>
     *
     * @param array     数组对象
     * @param predicate 断言条件
     * @param <T>       数组元素类型
     * @return 移除指定元素后的新数组或原数组
     */
    public static <T> T[] remove(final T[] array, final Predicate<? super T> predicate) {
        return predicate == null ? array : filter(array, it -> !predicate.test(it));
    }

    /**
     * <p>移除数组中指定的多个元素，移除成功，会返回移除元素之后的新数组</p>
     *
     * <p>注意：会移除匹配到的所有元素</p>
     *
     * @param array    数组对象
     * @param elements 要移除的多个元素
     * @param <T>      数组元素类型
     * @return 移除指定元素后的新数组或原数组
     */
    @SafeVarargs
    public static <T> T[] removeAll(final T[] array, final T... elements) {
        return remove(array, it -> contains(elements, it));
    }

    /**
     * <p>移除数组中指定位置的元素，移除成功，会返回移除元素之后的新数组</p>
     *
     * @param array 数组对象
     * @param index 元素位置，如果位置小于0或者大于长度，返回原数组
     * @param <T>   数组元素类型
     * @return 移除指定元素后的新数组或原数组
     */
    public static <T> T[] removeIndex(final T[] array, final int index) {
        // 获取数组的长度
        final int length = length(array);
        if (index < 0 || index >= length) {
            return array;
        }
        // 计算新数组的长度，并构建新的数组
        final int newLength = Math.subtractExact(length, 1);
        final T[] result = newArray(getComponentType(array), newLength);
        // 拷贝索引前半部分
        System.arraycopy(array, 0, result, 0, index);
        if (index < newLength) {
            // 拷贝索引后半部分
            System.arraycopy(array, Math.addExact(index, 1), result, index, Math.subtractExact(newLength, index));
        }
        return result;
    }

    /**
     * <p>移除数组中所有为{@code null}的元素，移除成功，会返回移除元素之后的新数组</p>
     *
     * <p>注意：此方法使用{@link ObjectUtil#isNull(Object)}来过滤元素</p>
     *
     * @param array 数组对象
     * @param <T>   数组元素类型
     * @return 移除为null元素后的新数组或原数组
     */
    public static <T> T[] removeNull(final T[] array) {
        return remove(array, ObjectUtil::isNull);
    }

    /**
     * <p>移除数组中所有为{@code null}或空的元素，移除成功，会返回移除元素之后的新数组</p>
     *
     * <p>注意：此方法使用{@link StringUtil#isEmpty(CharSequence)}来过滤元素，其元素类型限定为{@link CharSequence}类型及其子类</p>
     *
     * @param array 数组对象
     * @param <T>   数组元素类型
     * @return 移除为null或空元素后的新数组或原数组
     */
    public static <T extends CharSequence> T[] removeEmpty(final T[] array) {
        return remove(array, StringUtil::isEmpty);
    }

    /**
     * <p>移除数组中所有为{@code null}或空白的元素，移除成功，会返回移除元素之后的新数组</p>
     *
     * <p>注意：此方法使用{@link StringUtil#isBlank(CharSequence)}来过滤元素，其元素类型限定为{@link CharSequence}类型及其子类</p>
     *
     * @param array 数组对象
     * @param <T>   数组元素类型
     * @return 移除为null或空白元素后的新数组或原数组
     */
    public static <T extends CharSequence> T[] removeBlank(final T[] array) {
        return remove(array, StringUtil::isBlank);
    }

    // -------------------------------------------------------------------- toArray

    /**
     * <p>将迭代器转为数组</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>如果迭代器元素的类型为{@code null}，会抛出{@link ValidateException}异常</li>
     *     <li>如果迭代器{@link Iterator}为{@code null}，会返回一个长度为{@code 0}的空数组</li>
     * </ul>
     *
     * @param iterator      迭代器
     * @param componentType 迭代器元素的类型
     * @param <T>           数组元素的类型
     * @return 转换后的数组
     */
    public static <T> T[] toArray(final Iterator<T> iterator, @NonNull final Class<T> componentType) {
        return toArray(Lists.arrayList(iterator), componentType);
    }

    /**
     * <p>将迭代器转为数组</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>如果迭代器元素的类型为{@code null}，会抛出{@link ValidateException}异常</li>
     *     <li>如果迭代器{@link Iterable}为{@code null}，会返回一个长度为{@code 0}的空数组</li>
     * </ul>
     *
     * @param iterable      迭代器
     * @param componentType 迭代器元素的类型
     * @param <T>           数组元素的类型
     * @return 转换后的数组
     */
    public static <T> T[] toArray(final Iterable<T> iterable, @NonNull final Class<T> componentType) {
        return toArray(Lists.arrayList(iterable), componentType);
    }

    /**
     * <p>将集合转为数组</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>如果集合元素的类型为{@code null}，会抛出{@link ValidateException}异常</li>
     *     <li>如果集合{@link Collection}为{@code null}，会返回一个长度为{@code 0}的空数组</li>
     * </ul>
     *
     * @param collection    集合
     * @param componentType 集合元素的类型
     * @param <T>           数组元素的类型
     * @return 转换后的数组
     */
    public static <T> T[] toArray(final Collection<T> collection, @NonNull final Class<T> componentType) {
        return ObjectUtil.nullToDefault(collection, Lists.arrayList()).toArray(newArray(componentType, 0));
    }

    /**
     * <p>将数组转为数组，实际是对数组做类型转换，内部使用{@link ObjectUtil#convert(Object, Class)}对数组元素做类型转换</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>如果集合元素的类型为{@code null}，会抛出{@link ValidateException}异常</li>
     *     <li>如果集合{@link Array}为{@code null}，会返回一个长度为{@code 0}的空数组</li>
     * </ul>
     *
     * @param array         数组
     * @param componentType 数组元素的类型
     * @param <T>           数组元素的类型
     * @return 转换后的数组
     */
    public static <T> T[] toArray(final Object[] array, @NonNull final Class<T> componentType) {
        List<T> list = Streams.of(array).map(o -> ObjectUtil.convert(o, componentType)).toList();
        return toArray(list, componentType);
    }

    // -------------------------------------------------------------------- clone

    /**
     * <p>克隆数组，如果被克隆的数组为{@code null}，会返回{@code null}</p>
     *
     * @param array 被克隆的数组
     * @param <T>   数组元素类型
     * @return 克隆后的新数组
     */
    public static <T> T[] clone(final T[] array) {
        return array == null ? null : array.clone();
    }

    /**
     * <p>克隆数组，如果被克隆的对象为非数组，会返回{@code null}</p>
     *
     * @param obj 数组对象
     * @param <T> 数组元素类型
     * @return 克隆后的新数组
     */
    public static <T> T clone(final T obj) {
        // 非数组
        if (!isArray(obj)) {
            return null;
        }
        // 获取数组元素类型
        final Class<?> componentType = obj.getClass().getComponentType();
        // 非原生类型
        if (!componentType.isPrimitive()) {
            //noinspection unchecked
            return (T) ((Object[]) obj).clone();
        }
        // 原生类型
        int length = Array.getLength(obj);
        final Object result = Array.newInstance(componentType, length);
        while (length-- > 0) {
            Array.set(result, length, Array.get(obj, length));
        }
        //noinspection unchecked
        return (T) result;
    }

    // -------------------------------------------------------------------- toString

    /**
     * <p>数组对象转String，请参考{@link Arrays#deepToString(Object[])}<p>
     *
     * @param array 数组对象
     * @return 数组字符串，与集合转字符串格式相同
     */
    public static String toString(final Object[] array) {
        return array == null ? StringPool.NULL : Arrays.deepToString(array);
    }
}
