package com.ipower.framework.common.core.map;

import java.io.Serial;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 忽略大小写的LinkedHashMap<br>
 * 对KEY忽略大小写，get("Value")和get("value")获得的值相同，put进入的值也会被覆盖
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author kris
 * @since 1.0.0
 */
public class CaseInsensitiveLinkedMap<K, V> extends CaseInsensitiveMap<K, V> {

    @Serial
    private static final long serialVersionUID = 224091593664978349L;

    // ------------------------------------------------------------------------- Constructor start

    /**
     * 构造
     */
    public CaseInsensitiveLinkedMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     */
    public CaseInsensitiveLinkedMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     * @param loadFactor      加载因子
     */
    public CaseInsensitiveLinkedMap(int initialCapacity, float loadFactor) {
        super(new LinkedHashMap<>(initialCapacity, loadFactor));
    }

    /**
     * 构造
     *
     * @param map Map
     */
    public CaseInsensitiveLinkedMap(Map<? extends K, ? extends V> map) {
        this(DEFAULT_LOAD_FACTOR, map);
    }

    /**
     * 构造
     *
     * @param loadFactor 加载因子
     * @param map        Map
     */
    public CaseInsensitiveLinkedMap(float loadFactor, Map<? extends K, ? extends V> map) {
        this(map.size(), loadFactor);
        this.putAll(map);
    }
    // ------------------------------------------------------------------------- Constructor end
}
