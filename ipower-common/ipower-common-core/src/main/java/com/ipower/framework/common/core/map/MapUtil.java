package com.ipower.framework.common.core.map;

import com.ipower.framework.common.core.collection.CollectionUtil;
import com.ipower.framework.common.core.collection.Lists;
import com.ipower.framework.common.core.convert.Convert;
import com.ipower.framework.common.core.lang.StringUtil;
import com.ipower.framework.common.core.reflect.ReflectUtil;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Map相关工具类
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @author kris
 * @since 1.0.0
 */
public class MapUtil {

    private MapUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * Map是否为空
     *
     * @param map 集合
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Map是否为非空
     *
     * @param map 集合
     * @return 是否为非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    // ----------------------------------------------------------------------------------------------- new HashMap

    /**
     * 新建一个HashMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return newHashMap(MapWrapper.DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 新建一个HashMap
     *
     * @param <K>  Key类型
     * @param <V>  Value类型
     * @param size 初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75 + 1
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap(int size) {
        return newHashMap(size, false);
    }

    /**
     * 新建一个HashMap
     *
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @param isLinked Map的Key是否有序，有序返回 {@link LinkedHashMap}，否则返回 {@link HashMap}
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap(boolean isLinked) {
        return newHashMap(MapWrapper.DEFAULT_INITIAL_CAPACITY, isLinked);
    }

    /**
     * 新建一个HashMap
     *
     * @param <K>      Key类型
     * @param <V>      Value类型
     * @param size     初始大小，由于默认负载因子0.75，传入的size会实际初始大小为size / 0.75 + 1
     * @param isLinked Map的Key是否有序，有序返回 {@link LinkedHashMap}，否则返回 {@link HashMap}
     * @return HashMap对象
     */
    public static <K, V> HashMap<K, V> newHashMap(int size, boolean isLinked) {
        int initialCapacity = (int) (size / MapWrapper.DEFAULT_LOAD_FACTOR) + 1;
        return isLinked ? new LinkedHashMap<>(initialCapacity) : new HashMap<>(initialCapacity);
    }

    /**
     * 新建一个ConcurrentMap
     *
     * @param <K> Key类型
     * @param <V> Value类型
     * @return HashMap对象
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentMap() {
        return new ConcurrentHashMap<>(MapWrapper.DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param comparator Key比较器
     * @return TreeMap
     */
    public static <K, V> TreeMap<K, V> newTreeMap(Comparator<? super K> comparator) {
        return new TreeMap<>(comparator);
    }

    /**
     * 新建TreeMap，Key有序的Map
     *
     * @param map        Map
     * @param comparator Key比较器
     * @return TreeMap
     */
    public static <K, V> TreeMap<K, V> newTreeMap(Map<K, V> map, Comparator<? super K> comparator) {
        final TreeMap<K, V> treeMap = new TreeMap<>(comparator);
        if (!isEmpty(map)) {
            treeMap.putAll(map);
        }
        return treeMap;
    }

    /**
     * 创建键不重复Map
     *
     * @return {@link IdentityHashMap}
     */
    public static <K, V> Map<K, V> newIdentityMap(int size) {
        return new IdentityHashMap<>(size);
    }

    /**
     * 创建Map<br>
     * 传入抽象Map{@link AbstractMap}和{@link Map}类将默认创建{@link HashMap}
     *
     * @param <K>     map键类型
     * @param <V>     map值类型
     * @param mapType map类型
     * @return {@link Map}实例
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> create(Class<?> mapType) {
        if (mapType.isAssignableFrom(AbstractMap.class)) {
            return new HashMap<>();
        } else {
            return (Map<K, V>) ReflectUtil.newInstance(mapType);
        }
    }

    // ----------------------------------------------------------------------------------------------- value of

    /**
     * 将单一键值对转换为Map
     *
     * @param <K>   键类型
     * @param <V>   值类型
     * @param key   键
     * @param value 值
     * @return {@link HashMap}
     */
    public static <K, V> HashMap<K, V> create(K key, V value) {
        return create(key, value, false);
    }

    /**
     * 将单一键值对转换为Map
     *
     * @param <K>      键类型
     * @param <V>      值类型
     * @param key      键
     * @param value    值
     * @param isLinked 是否有序
     * @return {@link HashMap}
     */
    public static <K, V> HashMap<K, V> create(K key, V value, boolean isLinked) {
        final HashMap<K, V> map = newHashMap(isLinked);
        map.put(key, value);
        return map;
    }

    /**
     * 将数组转换为Map（HashMap），支持数组元素类型为：
     *
     * <pre>
     * Map.Entry
     * 长度大于1的数组（取前两个值），如果不满足跳过此元素
     * Iterable 长度也必须大于1（取前两个值），如果不满足跳过此元素
     * Iterator 长度也必须大于1（取前两个值），如果不满足跳过此元素
     * </pre>
     *
     * <pre>
     * Map&lt;Object, Object&gt; colorMap = MapUtil.of(new String[][] { { "RED", "#FF0000" }, { "GREEN", "#00FF00" }, { "BLUE", "#0000FF" } });
     * </pre>
     * <p>
     * 参考：commons-lang
     *
     * @param array 数组。元素类型为Map.Entry、数组、Iterable、Iterator
     * @return {@link HashMap}
     */
    public static HashMap<Object, Object> create(Object[] array) {
        if (array == null) {
            return null;
        }
        final HashMap<Object, Object> map = new HashMap<>((int) (array.length * 1.5));
        for (int i = 0; i < array.length; i++) {
            Object object = array[i];
            //noinspection rawtypes
            if (object instanceof Entry entry) {
                map.put(entry.getKey(), entry.getValue());
            } else if (object instanceof Object[] entry) {
                if (entry.length > 1) {
                    map.put(entry[0], entry[1]);
                }
            } else if (object instanceof Iterable) {
                Iterator iter = ((Iterable) object).iterator();
                if (iter.hasNext()) {
                    final Object key = iter.next();
                    if (iter.hasNext()) {
                        final Object value = iter.next();
                        map.put(key, value);
                    }
                }
            } else if (object instanceof Iterator) {
                Iterator iter = ((Iterator) object);
                if (iter.hasNext()) {
                    final Object key = iter.next();
                    if (iter.hasNext()) {
                        final Object value = iter.next();
                        map.put(key, value);
                    }
                }
            } else {
                throw new IllegalArgumentException(StringUtil
                        .format("Array element {}, '{}', is not type of Map.Entry or Array or Iterable or Iterator", i, object));
            }
        }
        return map;
    }

    /**
     * 行转列，合并相同的键，值合并为列表<br>
     * 将Map列表中相同key的值组成列表做为Map的value<br>
     * 是{@link #toMapList(Map)}的逆方法<br>
     * 比如传入数据：
     *
     * <pre>
     * [
     *  {a: 1, b: 1, c: 1}
     *  {a: 2, b: 2}
     *  {a: 3, b: 3}
     *  {a: 4}
     * ]
     * </pre>
     * <p>
     * 结果是：
     *
     * <pre>
     * {
     *   a: [1,2,3,4]
     *   b: [1,2,3,]
     *   c: [1]
     * }
     * </pre>
     *
     * @param <K>     键类型
     * @param <V>     值类型
     * @param mapList Map列表
     * @return Map
     */
    public static <K, V> Map<K, List<V>> toListMap(Iterable<? extends Map<K, V>> mapList) {
        final HashMap<K, List<V>> resultMap = new HashMap<>();
        if (CollectionUtil.isEmpty(mapList)) {
            return resultMap;
        }

        Set<Entry<K, V>> entrySet;
        for (Map<K, V> map : mapList) {
            entrySet = map.entrySet();
            K key;
            List<V> valueList;
            for (Entry<K, V> entry : entrySet) {
                key = entry.getKey();
                valueList = resultMap.get(key);
                if (null == valueList) {
                    valueList = Lists.arrayList(entry.getValue());
                    resultMap.put(key, valueList);
                } else {
                    valueList.add(entry.getValue());
                }
            }
        }

        return resultMap;
    }

    /**
     * 列转行。将Map中值列表分别按照其位置与key组成新的map。<br>
     * 是{@link #toListMap(Iterable)}的逆方法<br>
     * 比如传入数据：
     *
     * <pre>
     * {
     *   a: [1,2,3,4]
     *   b: [1,2,3,]
     *   c: [1]
     * }
     * </pre>
     * <p>
     * 结果是：
     *
     * <pre>
     * [
     *  {a: 1, b: 1, c: 1}
     *  {a: 2, b: 2}
     *  {a: 3, b: 3}
     *  {a: 4}
     * ]
     * </pre>
     *
     * @param <K>     键类型
     * @param <V>     值类型
     * @param listMap 列表Map
     * @return Map列表
     */
    public static <K, V> List<Map<K, V>> toMapList(Map<K, ? extends Iterable<V>> listMap) {
        final List<Map<K, V>> resultList = new ArrayList<>();
        if (isEmpty(listMap)) {
            return resultList;
        }

        // 是否结束。标准是元素列表已耗尽
        boolean isEnd;
        // 值索引
        int index = 0;
        Map<K, V> map;
        do {
            isEnd = true;
            map = new HashMap<>();
            List<V> vList;
            int vListSize;
            for (Entry<K, ? extends Iterable<V>> entry : listMap.entrySet()) {
                vList = Lists.arrayList(entry.getValue());
                vListSize = vList.size();
                if (index < vListSize) {
                    map.put(entry.getKey(), vList.get(index));
                    if (index != vListSize - 1) {
                        // 当值列表中还有更多值（非最后一个），继续循环
                        isEnd = false;
                    }
                }
            }
            if (!map.isEmpty()) {
                resultList.add(map);
            }
            index++;
        } while (!isEnd);

        return resultList;
    }

    /**
     * 将键值对转换为二维数组，第一维是key，第二纬是value
     *
     * @param map Map<?, ?> map
     * @return 数组
     */
    public static Object[][] toObjectArray(Map<?, ?> map) {
        if (map == null) {
            return null;
        }
        final Object[][] result = new Object[map.size()][2];
        if (map.isEmpty()) {
            return result;
        }
        int index = 0;
        for (Entry<?, ?> entry : map.entrySet()) {
            result[index][0] = entry.getKey();
            result[index][1] = entry.getValue();
            index++;
        }
        return result;
    }

    // ----------------------------------------------------------------------------------------------- join

    /**
     * 将map转成字符串
     *
     * @param <K>               键类型
     * @param <V>               值类型
     * @param map               Map
     * @param separator         entry之间的连接符
     * @param keyValueSeparator kv之间的连接符
     * @return 连接字符串
     */
    public static <K, V> String join(Map<K, V> map, String separator, String keyValueSeparator) {
        return join(map, separator, keyValueSeparator, false);
    }

    /**
     * 将map转成字符串，忽略null的键和值
     *
     * @param <K>               键类型
     * @param <V>               值类型
     * @param map               Map
     * @param separator         entry之间的连接符
     * @param keyValueSeparator kv之间的连接符
     * @return 连接后的字符串
     */
    public static <K, V> String joinIgnoreNull(Map<K, V> map, String separator, String keyValueSeparator) {
        return join(map, separator, keyValueSeparator, true);
    }

    /**
     * 将map转成字符串
     *
     * @param <K>               键类型
     * @param <V>               值类型
     * @param map               Map
     * @param separator         entry之间的连接符
     * @param keyValueSeparator kv之间的连接符
     * @param isIgnoreNull      是否忽略null的键和值
     * @return 连接后的字符串
     */
    public static <K, V> String join(Map<K, V> map, String separator, String keyValueSeparator, boolean isIgnoreNull) {
        final StringBuilder strBuilder = new StringBuilder();
        boolean isFirst = true;
        for (Entry<K, V> entry : map.entrySet()) {
            if (!isIgnoreNull || entry.getKey() != null && entry.getValue() != null) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    strBuilder.append(separator);
                }
                strBuilder.append(Convert.toString(entry.getKey())).append(keyValueSeparator)
                        .append(Convert.toString(entry.getValue()));
            }
        }
        return strBuilder.toString();
    }

    /**
     * 获取Map指定key的值，并转换为字符串
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static String getString(Map<?, ?> map, Object key) {
        return get(map, key, String.class);
    }

    /**
     * 获取Map指定key的值，并转换为Integer
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Integer getInteger(Map<?, ?> map, Object key) {
        return get(map, key, Integer.class);
    }

    /**
     * 获取Map指定key的值，并转换为Double
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Double getDouble(Map<?, ?> map, Object key) {
        return get(map, key, Double.class);
    }

    /**
     * 获取Map指定key的值，并转换为Float
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Float getFloat(Map<?, ?> map, Object key) {
        return get(map, key, Float.class);
    }

    /**
     * 获取Map指定key的值，并转换为Short
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Short getShort(Map<?, ?> map, Object key) {
        return get(map, key, Short.class);
    }

    /**
     * 获取Map指定key的值，并转换为Bool
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Boolean getBool(Map<?, ?> map, Object key) {
        return get(map, key, Boolean.class);
    }

    /**
     * 获取Map指定key的值，并转换为Character
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Character getChar(Map<?, ?> map, Object key) {
        return get(map, key, Character.class);
    }

    /**
     * 获取Map指定key的值，并转换为Long
     *
     * @param map Map
     * @param key 键
     * @return 值
     */
    public static Long getLong(Map<?, ?> map, Object key) {
        return get(map, key, Long.class);
    }

    /**
     * 获取Map指定key的值，并转换为指定类型
     *
     * @param <T>  目标值类型
     * @param map  Map
     * @param key  键
     * @param type 值类型
     * @return 值
     */
    public static <T> T get(Map<?, ?> map, Object key, Class<T> type) {
        return null == map ? null : Convert.convert(type, map.get(key));
    }

    /**
     * 重命名键<br>
     * 实现方式为一处然后重新put，当旧的key不存在直接返回<br>
     * 当新的key存在，抛出{@link IllegalArgumentException} 异常
     *
     * @param map    Map
     * @param oldKey 原键
     * @param newKey 新键
     * @return map
     * @throws IllegalArgumentException 新key存在抛出此异常
     */
    public static <K, V> Map<K, V> renameKey(Map<K, V> map, K oldKey, K newKey) {
        if (isNotEmpty(map) && map.containsKey(oldKey)) {
            if (map.containsKey(newKey)) {
                throw new IllegalArgumentException(StringUtil.format("The key '{}' exist !", newKey));
            }
            map.put(newKey, map.remove(oldKey));
        }
        return map;
    }

    /**
     * 去除Map中值为{@code null}的键值对<br>
     * 注意：此方法在传入的Map上直接修改。
     *
     * @param map Map
     * @return map
     */
    public static <K, V> Map<K, V> removeNullValue(Map<K, V> map) {
        if (isEmpty(map)) {
            return map;
        }

        final Iterator<Entry<K, V>> iter = map.entrySet().iterator();
        Entry<K, V> entry;
        while (iter.hasNext()) {
            entry = iter.next();
            if (null == entry.getValue()) {
                iter.remove();
            }
        }

        return map;
    }
}
