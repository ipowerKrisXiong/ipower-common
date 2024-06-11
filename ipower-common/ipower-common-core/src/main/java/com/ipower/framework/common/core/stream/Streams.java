package com.ipower.framework.common.core.stream;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.ipower.framework.common.core.lang.ObjectUtil.isNull;

/**
 * {@link Stream} 工具类
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">hutool</a>
 *
 * @author kris
 * @since 3.0.0
 */
public class Streams {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private Streams() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 创建空的{@link Stream}
     *
     * @return {@link Stream}
     */
    public static <T> Stream<T> of() {
        return Stream.empty();
    }

    /**
     * {@link Array}转换为{@link Stream}
     *
     * @param array 数组元素
     * @param <T>   数组元素类型
     * @return {@link Stream}
     */
    @SafeVarargs
    public static <T> Stream<T> of(final T... array) {
        return isNull(array) ? of() : Stream.of(array);
    }

    /**
     * {@link Collection}转换为{@link Stream}，默认非并行
     *
     * @param collection 集合
     * @param <T>        集合元素类型
     * @return {@link Stream}
     */
    public static <T> Stream<T> of(final Collection<T> collection) {
        return of(collection, false);
    }

    /**
     * {@link Iterable}转换为{@link Stream}，默认非并行
     *
     * @param iterable 集合
     * @param <T>      集合元素类型
     * @return {@link Stream}
     */
    public static <T> Stream<T> of(final Iterable<T> iterable) {
        return of(iterable, false);
    }

    /**
     * {@link Iterator} 转换为 {@link Stream}
     *
     * @param iterator 迭代器
     * @param <T>      集合元素类型
     * @return {@link Stream}
     */
    public static <T> Stream<T> of(final Iterator<T> iterator) {
        return of(iterator, false);
    }

    /**
     * {@link Collection}转换为{@link Stream}，默认非并行
     *
     * @param collection 集合
     * @param parallel   是否并行
     * @param <T>        集合元素类型
     * @return {@link Stream}
     */
    public static <T> Stream<T> of(final Collection<T> collection, final boolean parallel) {
        return isNull(collection) ? of() : parallel ? collection.parallelStream() : collection.stream();
    }

    /**
     * {@link Iterable}转换为{@link Stream}
     *
     * @param iterable 集合
     * @param parallel 是否并行
     * @param <T>      集合元素类型
     * @return {@link Stream}
     */
    public static <T> Stream<T> of(final Iterable<T> iterable, final boolean parallel) {
        return isNull(iterable) ? of() : StreamSupport.stream(iterable.spliterator(), parallel);
    }

    /**
     * {@link Iterator} 转换为 {@link Stream}
     *
     * @param iterator 迭代器
     * @param parallel 是否并行
     * @param <T>      集合元素类型
     * @return {@link Stream}
     * @throws IllegalArgumentException 如果iterator为null，抛出该异常
     */
    public static <T> Stream<T> of(final Iterator<T> iterator, final boolean parallel) {
        return isNull(iterator) ? of() : StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), parallel);
    }
}
