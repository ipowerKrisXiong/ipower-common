package com.ipower.framework.common.core.collection;


import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.stream.Streams;

/**
 * 数组工具验证器，提供数组的各种检查验证
 *
 * @author kris
 * @since 3.0.0
 */
public class ArrayValidator {

    protected ArrayValidator() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // -------------------------------------------------------------------- isArray

    /**
     * <p>判断Object对象是否为数组对象</p>
     *
     * @param obj 需要判断的Object对象
     * @return 是否为数组对象，如果为{@code null} 返回false
     */
    public static boolean isArray(final Object obj) {
        return obj != null && obj.getClass().isArray();
    }

    // -------------------------------------------------------------------- isEmpty

    /**
     * <p>接收一个数组对象，判断数组是否为空</p>
     *
     * @param array 要判断的数组
     * @return 数组是否为空
     */
    public static boolean isEmpty(final Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * <p>接收一个数组对象，判断数组是否不为空，需要该数组中的元素个数 > 0 </p>
     *
     * @param array 需要判断的数组
     * @return 判断数组是否不为空
     */
    public static boolean isNotEmpty(final Object[] array) {
        return !isEmpty(array);
    }

    /**
     * <p>判断对象数组中的元素，是否全部为{@code null}。</p>
     * <p>如果指定的对象数组的长度为 0，或者所有元素都是{@code null}，则返回 true。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.isAllNull()                                               // true}</li>
     *     <li>{@code ObjectUtil.isAllNull(null, null)                                     // true}</li>
     *     <li>{@code ObjectUtil.isAllNull(null, "")                                       // false}</li>
     *     <li>{@code ObjectUtil.isAllNull("", new ArrayList<>(), new HashMap<>())         // false}</li>
     *     <li>{@code ObjectUtil.isAllNull("", new ArrayList<>(), new HashMap<>(), null)   // false}</li>
     *     <li>{@code ObjectUtil.isAllNull("", "123", Lists.newArrayList("abc"))           // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #hasNull(Object...)} 的区别在于：</p>
     * <ul>
     *     <li>{@link #hasNull(Object...)}   等价于 {@code isNull(...) || isNull(...) || ...}</li>
     *     <li>isAllNull(Object...)   等价于 {@code isNull(...) && isNull(...) && ...}</li>
     * </ul>
     *
     * @param objs 被检测的对象数组
     * @return 所有对象是否全部为null
     */
    public static boolean isAllNull(final Object... objs) {
        return Streams.of(objs).allMatch(ObjectUtil::isNull);
    }

    /**
     * <p>判断对象数组中的元素，是否全部为{@code null}或空。</p>
     * <p>如果指定的对象数组的长度为 0，或者所有元素都是{@code null}或空，则返回 {@code true}。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.isAllEmpty()                                                // true}</li>
     *     <li>{@code ObjectUtil.isAllEmpty(null, null)                                      // true}</li>
     *     <li>{@code ObjectUtil.isAllEmpty("", null)                                        // true}</li>
     *     <li>{@code ObjectUtil.isAllEmpty("", new ArrayList<>(), new HashMap<>()))         // true}</li>
     *     <li>{@code ObjectUtil.isAllEmpty("", new ArrayList<>(), new HashMap<>(), null)    // true}</li>
     *     <li>{@code ObjectUtil.isAllEmpty("", "123", Lists.arrayList("abc"), null)         // false}</li>
     *     <li>{@code ObjectUtil.isAllEmpty("", "123", Lists.arrayList("abc"), 123)          // false}</li>
     *     <li>{@code ObjectUtil.isAllEmpty(3.14, "123", Lists.arrayList("abc"), 123)        // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #hasEmpty(Object...)} 的区别在于：</p>
     * <ul>
     *     <li>{@link #hasEmpty(Object...)}   等价于 {@code isEmpty(...) || isEmpty(...) || ...}</li>
     *     <li>isAllEmpty(Object...)          等价于 {@code isEmpty(...) && isEmpty(...) && ...}</li>
     * </ul>
     *
     * @param objs 被检测的对象数组
     * @return 所有对象是否全部为null或空
     */
    public static boolean isAllEmpty(final Object... objs) {
        return Streams.of(objs).allMatch(ObjectUtil::isEmpty);
    }

    /**
     * <p>判断对象数组中的元素，是否全部不为{@code null}。</p>
     * <p>如果指定的对象数组的长度不为 0，或者所有元素都不是{@code null}，则返回 true。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.isAllNotNull()                                               // false}</li>
     *     <li>{@code ObjectUtil.isAllNotNull(null, null)                                     // false}</li>
     *     <li>{@code ObjectUtil.isAllNotNull(null, "")                                       // false}</li>
     *     <li>{@code ObjectUtil.isAllNotNull("", new ArrayList<>() ,new HashMap<>())         // true}</li>
     *     <li>{@code ObjectUtil.isAllNotNull("", new ArrayList<>() ,new HashMap<>(), null)   // false}</li>
     *     <li>{@code ObjectUtil.isAllNotNull("", "123", new ArrayList<>("abc"))              // true}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isAllNull(Object...)} 的区别在于：</p>
     * <ul>
     *     <li>{@link #isAllNull(Object...)}    等价于 {@code isNull(...) && isNull(...) && ...}</li>
     *     <li>isAllNotNull(Object...)          等价于 {@code !isNull(...) && !isNull(...) && ...}</li>
     * </ul>
     *
     * @param objs 被检测的对象数组
     * @return 所有对象是否全部不为null
     */
    public static boolean isAllNotNull(final Object... objs) {
        return !hasNull(objs);
    }

    /**
     * <p>判断对象数组中的元素，是否全部不为{@code null}或空。</p>
     * <p>如果指定的对象数组的长度不为 0，或者所有元素都不是{@code null}或空，则返回 {@code true}。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.isAllNotEmpty()                                                // false}</li>
     *     <li>{@code ObjectUtil.isAllNotEmpty(null, null)                                      // false}</li>
     *     <li>{@code ObjectUtil.isAllNotEmpty("", null)                                        // false}</li>
     *     <li>{@code ObjectUtil.isAllNotEmpty("", new ArrayList<>(), new HashMap<>()))         // false}</li>
     *     <li>{@code ObjectUtil.isAllNotEmpty("", new ArrayList<>(), new HashMap<>(), null)    // false}</li>
     *     <li>{@code ObjectUtil.isAllNotEmpty("", "123", Lists.arrayList("abc"), null)         // false}</li>
     *     <li>{@code ObjectUtil.isAllNotEmpty("", "123", Lists.arrayList("abc"), 123)          // false}</li>
     *     <li>{@code ObjectUtil.isAllNotEmpty(3.14, "123", Lists.arrayList("abc"), 123)        // true}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isAllEmpty(Object...)} 的区别在于：</p>
     * <ul>
     *     <li>{@link #isAllEmpty(Object...)}    等价于 {@code isEmpty(...) && isEmpty(...) && ...}</li>
     *     <li>isAllNotEmpty(Object...)          等价于 {@code !isEmpty(...) && !isEmpty(...) && ...}</li>
     * </ul>
     *
     * @param objs 被检测的对象数组
     * @return 所有对象是否全部不为{@code null}或空
     */
    public static boolean isAllNotEmpty(final Object... objs) {
        return !hasEmpty(objs);
    }

    /**
     * <p>判断对象数组中的元素，是否包含{@code null}的元素。</p>
     * <p>如果指定的对象数组的长度为 0，或者其中的任意一个元素是{@code null}，则返回 true。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.hasNull()                                               // true}</li>
     *     <li>{@code ObjectUtil.hasNull(null, null)                                     // true}</li>
     *     <li>{@code ObjectUtil.hasNull(null, "")                                       // true}</li>
     *     <li>{@code ObjectUtil.hasNull("", new ArrayList<>() ,new HashMap<>())         // false}</li>
     *     <li>{@code ObjectUtil.hasNull("", new ArrayList<>() ,new HashMap<>(), null)   // true}</li>
     *     <li>{@code ObjectUtil.hasNull("", "123", Lists.arrayList("abc"))              // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isAllNull(Object...)} 的区别在于：</p>
     * <ul>
     *     <li>hasNull(Object...)            等价于 {@code isNull(...) || isNull(...) || ...}</li>
     *     <li>{@link #isAllNull(Object...)} 等价于 {@code isNull(...) && isNull(...) && ...}</li>
     * </ul>
     *
     * @param objs 被检测的对象数组
     * @return 是否包含为null的对象
     */
    public static boolean hasNull(final Object... objs) {
        return isEmpty(objs) || Streams.of(objs).anyMatch(ObjectUtil::isNull);
    }

    /**
     * <p>判断对象数组中的元素，是否包含{@code null}或空的元素。</p>
     * <p>如果指定的对象数组的长度为 0，或者其中的任意一个元素是{@code null}或空，则返回 {@code true}。</p>
     *
     * <p>例：</p>
     * <ul>
     *     <li>{@code ObjectUtil.hasEmpty()                                                // true}</li>
     *     <li>{@code ObjectUtil.hasEmpty(null, null)                                      // true}</li>
     *     <li>{@code ObjectUtil.hasEmpty("", null)                                        // true}</li>
     *     <li>{@code ObjectUtil.hasEmpty("", new ArrayList<>(), new HashMap<>()))         // true}</li>
     *     <li>{@code ObjectUtil.hasEmpty("", new ArrayList<>(), new HashMap<>(), null)    // true}</li>
     *     <li>{@code ObjectUtil.hasEmpty("", "123", Lists.arrayList("abc"), null)         // true}</li>
     *     <li>{@code ObjectUtil.hasEmpty("", "123", Lists.arrayList("abc"), 123)          // true}</li>
     *     <li>{@code ObjectUtil.hasEmpty(3.14, "123", Lists.arrayList("abc"), 123)        // false}</li>
     * </ul>
     *
     * <p>注意：该方法与 {@link #isAllEmpty(Object...)} 的区别在于：</p>
     * <ul>
     *     <li>hasEmpty(Object...)            等价于 {@code isEmpty(...) || isEmpty(...) || ...}</li>
     *     <li>{@link #isAllEmpty(Object...)} 等价于 {@code isEmpty(...) && isEmpty(...) && ...}</li>
     * </ul>
     *
     * @param objs 被检测的对象数组
     * @return 是否包含{@code null}或空
     */
    public static boolean hasEmpty(final Object... objs) {
        return isEmpty(objs) || Streams.of(objs).anyMatch(ObjectUtil::isEmpty);
    }
}
