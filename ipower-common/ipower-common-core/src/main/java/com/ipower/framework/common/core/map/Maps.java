package com.ipower.framework.common.core.map;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * map对象构造器，用来便捷的初始化新map对象
 *
 * @author kris
 * @since 1.0.0
 */
public class Maps {

    private Maps() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 新建一个HashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> hashMap() {
        return MapUtil.newHashMap();
    }

    /**
     * 新建一个HashMap
     *
     * @param <K>  Key类型
     * @param <V>  Value类型
     * @param size 初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75 + 1
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> hashMap(int size) {
        return MapUtil.newHashMap(size);
    }

    /**
     * 新建一个HashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> hashMap(K k, V v) {
        return MapUtil.create(k, v);
    }

    /**
     * 新建一个LinkedHashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @return LinkedHashMap对象
     */
    public static <K, V> LinkedHashMap<K, V> linkedHashMap() {
        //noinspection unchecked
        return (LinkedHashMap<K, V>) MapUtil.newHashMap(true);
    }

    /**
     * 新建一个LinkedHashMap
     *
     * @param <K>  Key类型
     * @param <V>  Value类型
     * @param size 初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75 + 1
     * @return LinkedHashMap对象
     */
    public static <K, V> LinkedHashMap<K, V> linkedHashMap(int size) {
        //noinspection unchecked
        return (LinkedHashMap<K, V>) MapUtil.newHashMap(size, true);
    }

    /**
     * 新建ConcurrentHashMap，Key有序的Map
     *
     * @return ConcurrentHashMap
     */
    public static <K, V> ConcurrentHashMap<K, V> concurrentMap() {
        return MapUtil.newConcurrentMap();
    }

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @return TreeMap
     */
    public static <K, V> TreeMap<K, V> treeMap() {
        //noinspection SortedCollectionWithNonComparableKeys
        return new TreeMap<>();
    }

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param comparator Key比较器
     * @return TreeMap
     */
    public static <K, V> TreeMap<K, V> treeMap(Comparator<? super K> comparator) {
        return new TreeMap<>(comparator);
    }

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param map        Map
     * @param comparator Key比较器
     * @return TreeMap
     */
    public static <K, V> TreeMap<K, V> treeMap(Map<K, V> map, Comparator<? super K> comparator) {
        final TreeMap<K, V> treeMap = new TreeMap<>(comparator);
        if (map != null && !map.isEmpty()) {
            treeMap.putAll(map);
        }
        return treeMap;
    }

    /**
     * 创建键不重复Map
     *
     * @return {@link IdentityHashMap}
     */
    public static <K, V> IdentityHashMap<K, V> identityMap() {
        return new IdentityHashMap<>();
    }

    /**
     * 创建键不重复Map
     *
     * @return {@link IdentityHashMap}
     */
    public static <K, V> IdentityHashMap<K, V> identityMap(int size) {
        return new IdentityHashMap<>(size);
    }

}
