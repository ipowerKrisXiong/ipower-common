package com.ipower.framework.common.core.collection;

import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.stream.Streams;

import java.util.Collection;
import java.util.Iterator;

/**
 * 集合工具验证器，提供集合的各种检查验证<br>
 *
 * @author kris
 * @since 3.0.0
 */
public class CollectionValidator {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    protected CollectionValidator() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //region >>>> collection empty validate

    /**
     * 判断集合是否为{@code null}或是空
     *
     * @param collection 被检查的集合{@link Collection}对象
     * @return 是否为null或是空
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断集合是否不为{@code null}或是不为空
     *
     * @param collection 被检查的集合{@link Collection}对象
     * @return 是否不为null或是不为空
     */
    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * <p>判断集合中的元素，是否全部为{@code null}。</p>
     * <p>如果指定的集合为{@code null}或是空，或者所有元素都是{@code null}，则返回 true。</p>
     *
     * @param collection 被检测的集合{@link Collection}对象
     * @return 所有元素是否全部为null
     */
    public static boolean isAllNull(final Collection<?> collection) {
        return Streams.of(collection).allMatch(ObjectUtil::isNull);
    }

    /**
     * <p>判断集合中的元素，是否全部不为{@code null}。</p>
     * <p>如果指定的集合不为{@code null}或是空，或者所有元素都不是{@code null}，则返回 true。</p>
     *
     * @param collection 被检测的集合{@link Collection}对象
     * @return 所有元素是否全部不为null
     */
    public static boolean isAllNotNull(final Collection<?> collection) {
        return !hasNull(collection);
    }

    /**
     * <p>判断集合中的元素，是否全部为{@code null}或空。</p>
     * <p>如果指定的集合为{@code null}或是空，或者所有元素都是{@code null}或空，则返回 {@code true}。</p>
     *
     * @param collection 被检测的集合{@link Collection}对象
     * @return 所有元素是否全部为null或空
     */
    public static boolean isAllEmpty(final Collection<?> collection) {
        return Streams.of(collection).allMatch(ObjectUtil::isEmpty);
    }

    /**
     * <p>判断集合中的元素，是否全部为{@code null}或空。</p>
     * <p>如果指定的集合为{@code null}或是空，或者所有元素都是{@code null}或空，则返回 {@code true}。</p>
     *
     * @param collection 被检测的集合{@link Collection}对象
     * @return 所有元素是否全部为null或空
     */
    public static boolean isAllNotEmpty(final Collection<?> collection) {
        return !hasEmpty(collection);
    }

    /**
     * 检查集合中是否包含{@code null}元素
     *
     * @param collection 被检查的{@link Collection}对象，如果为{@code null}，返回true
     * @return 是否包含{@code null}元素
     */
    public static boolean hasNull(final Collection<?> collection) {
        return isEmpty(collection) || collection.stream().anyMatch(ObjectUtil::isNull);
    }

    /**
     * 检查集合中是否包含{@code null}或空的元素
     *
     * @param collection 被检查的{@link Collection}对象，如果为{@code null}或是空，返回true
     * @return 是否包含{@code null}或空元素
     */
    public static boolean hasEmpty(final Collection<?> collection) {
        return isEmpty(collection) || collection.stream().anyMatch(ObjectUtil::isEmpty);
    }

    //endregion

    //region >>>> iterable empty validate

    /**
     * 判断迭代器Iterable是否为{@code null}或是空
     *
     * @param iterable 被检查的迭代器{@link Iterable}对象
     * @return 是否为null或是空
     */
    public static boolean isEmpty(final Iterable<?> iterable) {
        return iterable == null || !iterable.iterator().hasNext();
    }

    /**
     * 判断迭代器iterable是否不为{@code null}或是不为空
     *
     * @param iterable 被检查的迭代器{@link Iterable}对象
     * @return 是否不为null或是不为空
     */
    public static boolean isNotEmpty(final Iterable<?> iterable) {
        return !isEmpty(iterable);
    }

    /**
     * <p>判断迭代器中的元素，是否全部为{@code null}。</p>
     * <p>如果指定的迭代器为{@code null}或是空，或者所有元素都是{@code null}，则返回 true。</p>
     *
     * @param iterable 被检测的迭代器{@link Iterable}对象
     * @return 所有元素是否全部为null
     */
    public static boolean isAllNull(final Iterable<?> iterable) {
        return Streams.of(iterable).allMatch(ObjectUtil::isNull);
    }

    /**
     * <p>判断迭代器中的元素，是否全部不为{@code null}。</p>
     * <p>如果指定的迭代器不为{@code null}或是空，或者所有元素都不是{@code null}，则返回 true。</p>
     *
     * @param iterable 被检测的迭代器{@link Iterable}对象
     * @return 所有元素是否全部不为null
     */
    public static boolean isAllNotNull(final Iterable<?> iterable) {
        return !hasNull(iterable);
    }

    /**
     * <p>判断迭代器中的元素，是否全部为{@code null}或空。</p>
     * <p>如果指定的迭代器为{@code null}或是空，或者所有元素都是{@code null}或空，则返回 {@code true}。</p>
     *
     * @param iterable 被检测的迭代器{@link Iterable}对象
     * @return 所有元素是否全部为null或空
     */
    public static boolean isAllEmpty(final Iterable<?> iterable) {
        return Streams.of(iterable).allMatch(ObjectUtil::isEmpty);
    }

    /**
     * <p>判断迭代器中的元素，是否全部为{@code null}或空。</p>
     * <p>如果指定的迭代器为{@code null}或是空，或者所有元素都是{@code null}或空，则返回 {@code true}。</p>
     *
     * @param iterable 被检测的迭代器{@link Iterable}对象
     * @return 所有元素是否全部为null或空
     */
    public static boolean isAllNotEmpty(final Iterable<?> iterable) {
        return !hasEmpty(iterable);
    }

    /**
     * 检查迭代器中是否包含{@code null}元素
     *
     * @param iterable 被检查的{@link Iterable}对象，如果为{@code null}，返回true
     * @return 是否包含{@code null}元素
     */
    public static boolean hasNull(final Iterable<?> iterable) {
        return iterable == null || hasNull(iterable.iterator());
    }

    /**
     * 检查迭代器中是否包含{@code null}或空的元素
     *
     * @param iterable 被检查的{@link Iterable}对象，如果为{@code null}或是空，返回true
     * @return 是否包含{@code null}或空元素
     */
    public static boolean hasEmpty(final Iterable<?> iterable) {
        return iterable == null || hasEmpty(iterable.iterator());
    }

    //endregion

    //region >>>> iterator empty validate

    /**
     * 判断迭代器Iterator是否为{@code null}或是空
     *
     * @param iterator 被检查的迭代器{@link Iterator}对象
     * @return 是否为null或是空
     */
    public static boolean isEmpty(final Iterator<?> iterator) {
        return iterator == null || !iterator.hasNext();
    }

    /**
     * 判断迭代器Iterator是否不为{@code null}或是不为空
     *
     * @param iterator 被检查的迭代器{@link Iterator}对象
     * @return 是否不为null或是不为空
     */
    public static boolean isNotEmpty(final Iterator<?> iterator) {
        return !isEmpty(iterator);
    }

    /**
     * <p>判断迭代器中的元素，是否全部为{@code null}。</p>
     * <p>如果指定的迭代器为{@code null}或是空，或者所有元素都是{@code null}，则返回 true。</p>
     *
     * @param iterator 被检测的迭代器{@link Iterator}对象
     * @return 所有元素是否全部为null
     */
    public static boolean isAllNull(final Iterator<?> iterator) {
        return Streams.of(iterator).allMatch(ObjectUtil::isNull);
    }

    /**
     * <p>判断迭代器中的元素，是否全部不为{@code null}。</p>
     * <p>如果指定的迭代器不为{@code null}或是空，或者所有元素都不是{@code null}，则返回 true。</p>
     *
     * @param iterator 被检测的迭代器{@link Iterator}对象
     * @return 所有元素是否全部不为null
     */
    public static boolean isAllNotNull(final Iterator<?> iterator) {
        return !hasNull(iterator);
    }

    /**
     * <p>判断迭代器中的元素，是否全部为{@code null}或空。</p>
     * <p>如果指定的迭代器为{@code null}或是空，或者所有元素都是{@code null}或空，则返回 {@code true}。</p>
     *
     * @param iterator 被检测的迭代器{@link Iterator}对象
     * @return 所有元素是否全部为null或空
     */
    public static boolean isAllEmpty(final Iterator<?> iterator) {
        return Streams.of(iterator).allMatch(ObjectUtil::isEmpty);
    }

    /**
     * <p>判断迭代器中的元素，是否全部为{@code null}或空。</p>
     * <p>如果指定的迭代器为{@code null}或是空，或者所有元素都是{@code null}或空，则返回 {@code true}。</p>
     *
     * @param iterator 被检测的迭代器{@link Iterator}对象
     * @return 所有元素是否全部为null或空
     */
    public static boolean isAllNotEmpty(final Iterator<?> iterator) {
        return !hasEmpty(iterator);
    }

    /**
     * 检查迭代器中是否包含{@code null}元素
     *
     * @param iterator 被检查的{@link Iterator}对象，如果为{@code null}，返回true
     * @return 是否包含{@code null}元素
     */
    public static boolean hasNull(final Iterator<?> iterator) {
        return isEmpty(iterator) || Streams.of(iterator).anyMatch(ObjectUtil::isNull);
    }

    /**
     * 检查迭代器中是否包含{@code null}或空的元素
     *
     * @param iterator 被检查的{@link Iterator}对象，如果为{@code null}或是空，返回true
     * @return 是否包含{@code null}或空元素
     */
    public static boolean hasEmpty(final Iterator<?> iterator) {
        return isEmpty(iterator) || Streams.of(iterator).anyMatch(ObjectUtil::isEmpty);
    }

    //endregion
}
