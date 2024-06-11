package com.ipower.framework.common.core.map;

import lombok.Getter;
import org.springframework.lang.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Map包装类，通过包装一个已有Map实现特定功能。例如自定义Key的规则或Value规则
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author kris
 * @since 1.0.0
 */
@Getter
public class MapWrapper<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>>, Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 8178440686170232664L;

    /**
     * 默认初始大小 aka 16
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    /**
     * 默认增长因子
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 被包装的原始Map
     */
    private final Map<K, V> raw;

    /**
     * 构造
     *
     * @param raw 被包装的Map
     */
    public MapWrapper(Map<K, V> raw) {
        this.raw = raw;
    }

    @Override
    public int size() {
        return raw.size();
    }

    @Override
    public boolean isEmpty() {
        return raw.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return raw.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return raw.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return raw.get(key);
    }

    @Override
    public V put(K key, V value) {
        return raw.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return raw.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        raw.putAll(m);
    }

    @Override
    public void clear() {
        raw.clear();
    }

    @NonNull
    @Override
    public Set<K> keySet() {
        return raw.keySet();
    }

    @NonNull
    @Override
    public Collection<V> values() {
        return raw.values();
    }

    @NonNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return raw.entrySet();
    }

    @NonNull
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return this.entrySet().iterator();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public MapWrapper<K, V> clone() {
        try {
            //noinspection unchecked
            return (MapWrapper) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
