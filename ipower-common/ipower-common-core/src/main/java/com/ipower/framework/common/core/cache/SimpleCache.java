package com.ipower.framework.common.core.cache;


import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

/**
 * 简单缓存，无超时实现，使用{@link WeakHashMap}实现缓存自动清理
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">hutool</a>
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author kris
 * @since 1.0.0
 */
public class SimpleCache<K, V> implements Serializable {

    @Serial
    private static final long serialVersionUID = -1909783554554630498L;

    /**
     * 缓存池
     */
    private final Map<K, V> cache;

    /**
     * 读写锁
     */
    private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();

    /**
     * 构造，默认使用{@link WeakHashMap}实现缓存自动清理
     */
    public SimpleCache() {
        this.cache = new WeakHashMap<>();
    }

    /**
     * 从缓存池中查找值
     *
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        cacheLock.readLock().lock();
        try {
            return cache.get(key);
        } finally {
            cacheLock.readLock().unlock();
        }
    }

    /**
     * 从缓存池中查找值
     *
     * @param key 键
     * @return 值
     */
    public V get(K key, V defaultValue) {
        cacheLock.readLock().lock();
        try {
            return cache.getOrDefault(key, defaultValue);
        } finally {
            cacheLock.readLock().unlock();
        }
    }

    /**
     * 从缓存中获得对象，当对象不在缓存中或已经过期返回function回调产生的对象
     *
     * @param key      键
     * @param function 如果不存在回调方法，用于生产值对象
     * @return 值对象
     */
    public V computeIfAbsent(K key, Function<? super K, ? extends V> function) {
        V v = get(key);
        if (v == null && function != null) {
            // 双重检查锁
            cacheLock.writeLock().lock();
            try {
                v = cache.get(key);
                if (v == null) {
                    try {
                        v = function.apply(key);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    cache.put(key, v);
                }
            } finally {
                cacheLock.writeLock().unlock();
            }
        }
        return v;
    }

    /**
     * 放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public V put(K key, V value) {
        cacheLock.writeLock().lock();
        try {
            cache.put(key, value);
        } finally {
            cacheLock.writeLock().unlock();
        }
        return value;
    }

    /**
     * 移除缓存
     *
     * @param key 键
     */
    public V remove(K key) {
        cacheLock.writeLock().lock();
        try {
            return cache.remove(key);
        } finally {
            cacheLock.writeLock().unlock();
        }
    }

    /**
     * 清空缓存池
     */
    public void clear() {
        cacheLock.writeLock().lock();
        try {
            this.cache.clear();
        } finally {
            cacheLock.writeLock().unlock();
        }
    }
}
