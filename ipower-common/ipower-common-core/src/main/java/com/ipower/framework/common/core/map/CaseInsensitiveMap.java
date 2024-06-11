package com.ipower.framework.common.core.map;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * 忽略大小写的Map<br>
 * 对KEY忽略大小写，get("Value")和get("value")获得的值相同，put进入的值也会被覆盖
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author kris
 * @since 1.0.0
 */
public class CaseInsensitiveMap<K, V> extends AbstractCustomKeyMap<K, V> {

    @Serial
    private static final long serialVersionUID = -5252854076641735127L;

    //------------------------------------------------------------------------- Constructor start

    /**
     * 构造
     */
    public CaseInsensitiveMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     */
    public CaseInsensitiveMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     * @param loadFactor      加载因子
     */
    public CaseInsensitiveMap(int initialCapacity, float loadFactor) {
        super(new HashMap<>(initialCapacity, loadFactor));
    }

    /**
     * 构造
     *
     * @param m Map
     */
    public CaseInsensitiveMap(Map<? extends K, ? extends V> m) {
        this(DEFAULT_LOAD_FACTOR, m);
    }

    /**
     * 构造
     *
     * @param loadFactor 加载因子
     * @param m          Map
     */
    public CaseInsensitiveMap(float loadFactor, Map<? extends K, ? extends V> m) {
        this(m.size(), loadFactor);
        this.putAll(m);
    }
    //------------------------------------------------------------------------- Constructor end

    /**
     * 将Key转为小写
     *
     * @param key KEY
     * @return 小写KEY
     */
    @Override
    protected Object customKey(Object key) {
        return (key instanceof CharSequence) ? key.toString().toLowerCase() : key;
    }
}
