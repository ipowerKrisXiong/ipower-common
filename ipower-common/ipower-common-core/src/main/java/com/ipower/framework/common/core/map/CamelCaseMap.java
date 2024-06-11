package com.ipower.framework.common.core.map;

import com.ipower.framework.common.core.lang.StringUtil;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * 驼峰Key风格的Map<br>
 * 对KEY转换为驼峰，get("int_value")和get("intValue")获得的值相同，put进入的值也会被覆盖
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author kris
 * @since 1.0.0
 */
public class CamelCaseMap<K, V> extends AbstractCustomKeyMap<K, V> {

    @Serial
    private static final long serialVersionUID = -4269611781858781635L;

    // ------------------------------------------------------------------------- Constructor start

    /**
     * 构造
     */
    public CamelCaseMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     */
    public CamelCaseMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 构造
     *
     * @param m Map
     */
    public CamelCaseMap(Map<? extends K, ? extends V> m) {
        this(DEFAULT_LOAD_FACTOR, m);
    }

    /**
     * 构造
     *
     * @param loadFactor 加载因子
     * @param m          Map
     */
    public CamelCaseMap(float loadFactor, Map<? extends K, ? extends V> m) {
        this(m.size(), loadFactor);
        this.putAll(m);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     * @param loadFactor      加载因子
     */
    public CamelCaseMap(int initialCapacity, float loadFactor) {
        super(new HashMap<>(initialCapacity, loadFactor));
    }
    // ------------------------------------------------------------------------- Constructor end

    /**
     * 将Key转为驼峰风格，如果key为字符串的话
     *
     * @param key KEY
     * @return 驼峰Key
     */
    @Override
    protected Object customKey(Object key) {
        return (key instanceof CharSequence) ? StringUtil.underlineToCamel(key.toString()) : key;
    }
}
