package com.ipower.framework.common.core.collection;

import com.ipower.framework.common.core.bean.PropertyUtil;
import com.ipower.framework.common.core.bean.closure.BeanPropertyChangeClosure;
import com.ipower.framework.common.core.bean.predicate.BeanPredicateUtil;
import com.ipower.framework.common.core.constant.StringPool;
import com.ipower.framework.common.core.convert.ConverterRegistry;
import com.ipower.framework.common.core.exception.UtilException;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.lang.StringUtil;
import com.ipower.framework.common.core.lang.Validate;
import com.ipower.framework.common.core.map.MapUtil;
import com.ipower.framework.common.core.reflect.ClassUtil;
import com.ipower.framework.common.core.reflect.ReflectUtil;
import com.ipower.framework.common.core.reflect.TypeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyMap;

/**
 * 集合工具类
 *
 * @author kris
 */
public final class CollectionUtil extends CollectionValidator {

    private CollectionUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * <p>检测{@link List}集合如果是{@code null}，会返回一个默认空的{@link ArrayList}集合，否则将返回集合本身。</p>
     *
     * <p>建议：该方法适用于在程序中需要使用获取到的{@code ArrayList}集合时，省去非空判断来规避空指针异常</p>
     * <br/>
     *
     * @param list 被检测的{@code list}集合
     * @return 集合本身或默认空的ArrayList集合
     */
    public static <T> List<T> nullToEmpty(final List<T> list) {
        return ObjectUtil.nullToDefault(list, Collections.emptyList());
    }

    /**
     * <p>检测{@link Set}集合如果是{@code null}，会返回一个默认空的{@link HashSet}集合，否则将返回集合本身。</p>
     *
     * <p>建议：该方法适用于在程序中需要使用获取到的{@code HashSet}集合时，省去非空判断来规避空指针异常</p>
     * <br/>
     *
     * @param set 被检测的{@code set}集合
     * @return 集合本身或默认空的HashSet集合
     */
    public static <T> Set<T> nullToEmpty(final Set<T> set) {
        return ObjectUtil.nullToDefault(set, newHashSet());
    }

    /**
     * 创建新的集合对象
     *
     * @param <T>            集合类型
     * @param collectionType 集合类型
     * @return 集合类型对应的实例
     */
    @SuppressWarnings({"unchecked", "rawtypes", "SortedCollectionWithNonComparableKeys"})
    public static <T> Collection<T> create(Class<?> collectionType) {
        Collection<T> list;
        if (collectionType.isAssignableFrom(AbstractCollection.class)) {
            // 抽象集合默认使用ArrayList
            list = new ArrayList<>();
        }

        // Set
        else if (collectionType.isAssignableFrom(HashSet.class)) {
            list = new HashSet<>();
        } else if (collectionType.isAssignableFrom(LinkedHashSet.class)) {
            list = new LinkedHashSet<>();
        } else if (collectionType.isAssignableFrom(TreeSet.class)) {
            list = new TreeSet<>();
        } else if (collectionType.isAssignableFrom(EnumSet.class)) {
            list = (Collection<T>) EnumSet.noneOf((Class<Enum>) ClassUtil.getTypeArgument(collectionType));
        }

        // List
        else if (collectionType.isAssignableFrom(ArrayList.class)) {
            list = new ArrayList<>();
        } else if (collectionType.isAssignableFrom(LinkedList.class)) {
            list = new LinkedList<>();
        }

        // Others，直接实例化
        else {
            try {
                list = (Collection<T>) ReflectUtil.newInstance(collectionType);
            } catch (Exception e) {
                throw new UtilException(e);
            }
        }
        return list;
    }

    /**
     * 将指定对象全部加入到集合中<br>
     * 提供的对象如果为集合类型，会自动转换为目标元素类型<br>
     *
     * @param <T>        元素类型
     * @param collection 被加入的集合
     * @param value      对象，可能为Iterator、Iterable、Enumeration、Array
     * @return 被加入集合
     */
    public static <T> Collection<T> addAll(Collection<T> collection, Object value) {
        return addAll(collection, value, TypeUtil.getTypeArgument(collection.getClass()));
    }

    /**
     * 将指定对象全部加入到集合中<br>
     * 提供的对象如果为集合类型，会自动转换为目标元素类型<br>
     *
     * @param <T>         元素类型
     * @param collection  被加入的集合
     * @param value       对象，可能为Iterator、Iterable、Enumeration、Array，或者与集合元素类型一致
     * @param elementType 元素类型，为空时，使用Object类型来接纳所有类型
     * @return 被加入集合
     */
    @SuppressWarnings({"rawtypes"})
    public static <T> Collection<T> addAll(Collection<T> collection, Object value, Type elementType) {
        if (null == collection || null == value) {
            return collection;
        }
        if (TypeUtil.isUnknown(elementType)) {
            // 元素类型为空时，使用Object类型来接纳所有类型
            elementType = Object.class;
        }

        Iterator iter;
        if (value instanceof Iterator) {
            iter = (Iterator) value;
        } else if (value instanceof Iterable) {
            iter = ((Iterable) value).iterator();
        } else if (ArrayUtil.isArray(value)) {
            iter = new ArrayIter<>(value);
        } else if (value instanceof CharSequence) {
            // String按照逗号分隔的列表对待
            iter = StringUtil.split(value.toString(), StringPool.COMMA).iterator();
        } else {
            // 其它类型按照单一元素处理
            iter = Lists.arrayList(value).iterator();
        }

        final ConverterRegistry convert = ConverterRegistry.getInstance();
        while (iter.hasNext()) {
            collection.add(convert.convert(elementType, iter.next()));
        }

        return collection;
    }

    /**
     * 加入全部
     *
     * @param <T>        集合元素类型
     * @param collection 被加入的集合 {@link Collection}
     * @param iterator   要加入的{@link Iterator}
     * @return 原集合
     */
    public static <T> Collection<T> addAll(Collection<T> collection, Iterator<T> iterator) {
        if (null != collection && null != iterator) {
            while (iterator.hasNext()) {
                collection.add(iterator.next());
            }
        }
        return collection;
    }

    /**
     * 加入全部
     *
     * @param <T>        集合元素类型
     * @param collection 被加入的集合 {@link Collection}
     * @param iterable   要加入的内容{@link Iterable}
     * @return 原集合
     */
    public static <T> Collection<T> addAll(Collection<T> collection, Iterable<T> iterable) {
        return addAll(collection, iterable.iterator());
    }

    /**
     * 加入全部
     *
     * @param <T>         集合元素类型
     * @param collection  被加入的集合 {@link Collection}
     * @param enumeration 要加入的内容{@link Enumeration}
     * @return 原集合
     */
    public static <T> Collection<T> addAll(Collection<T> collection, Enumeration<T> enumeration) {
        if (null != collection && null != enumeration) {
            while (enumeration.hasMoreElements()) {
                collection.add(enumeration.nextElement());
            }
        }
        return collection;
    }

    /**
     * 加入全部
     *
     * @param <T>        集合元素类型
     * @param collection 被加入的集合 {@link Collection}
     * @param values     要加入的内容数组
     * @return 原集合
     */
    public static <T> Collection<T> addAll(Collection<T> collection, T[] values) {
        if (null != collection && null != values) {
            Collections.addAll(collection, values);
        }
        return collection;
    }

    /**
     * {@link Iterable}转为{@link Collection}<br>
     * 首先尝试强转，强转失败则构建一个新的{@link ArrayList}
     *
     * @param <E>      集合元素类型
     * @param iterable {@link Iterable}
     * @return {@link Collection} 或者 {@link ArrayList}
     */
    public static <E> Collection<E> toCollection(Iterable<E> iterable) {
        return (iterable instanceof Collection) ? (Collection<E>) iterable : Lists.arrayList(iterable.iterator());
    }

    // ----------------------------------------------------------------------------------------------- new HashSet

    /**
     * 新建一个HashSet
     *
     * @param <T> 集合元素类型
     * @param ts  元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... ts) {
        return newHashSet(false, ts);
    }

    /**
     * 新建一个LinkedHashSet
     *
     * @param <T> 集合元素类型
     * @param ts  元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> LinkedHashSet<T> newLinkedHashSet(T... ts) {
        return (LinkedHashSet<T>) newHashSet(true, ts);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>      集合元素类型
     * @param isSorted 是否有序，有序返回 {@link LinkedHashSet}，否则返回 {@link HashSet}
     * @param ts       元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> HashSet<T> newHashSet(boolean isSorted, T... ts) {
        if (null == ts) {
            return isSorted ? new LinkedHashSet<>() : new HashSet<>();
        }
        int initialCapacity = Math.max((int) (ts.length / .75f) + 1, 16);
        HashSet<T> set = isSorted ? new LinkedHashSet<>(initialCapacity) : new HashSet<>(initialCapacity);
        Collections.addAll(set, ts);
        return set;
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @return HashSet对象
     */
    public static <T> HashSet<T> newHashSet(Collection<T> collection) {
        return newHashSet(false, collection);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>        集合元素类型
     * @param isSorted   是否有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param collection 集合，用于初始化Set
     * @return HashSet对象
     */
    public static <T> HashSet<T> newHashSet(boolean isSorted, Collection<T> collection) {
        return isSorted ? new LinkedHashSet<>(collection) : new HashSet<>(collection);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>      集合元素类型
     * @param isSorted 是否有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param iter     {@link Iterator}
     * @return HashSet对象
     */
    public static <T> HashSet<T> newHashSet(boolean isSorted, Iterator<T> iter) {
        if (null == iter) {
            return newHashSet(isSorted, (T[]) null);
        }
        final HashSet<T> set = isSorted ? new LinkedHashSet<>() : new HashSet<>();
        while (iter.hasNext()) {
            set.add(iter.next());
        }
        return set;
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>         集合元素类型
     * @param isSorted    是否有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param enumeration {@link Enumeration}
     * @return HashSet对象
     */
    public static <T> HashSet<T> newHashSet(boolean isSorted, Enumeration<T> enumeration) {
        if (null == enumeration) {
            return newHashSet(isSorted, (T[]) null);
        }
        final HashSet<T> set = isSorted ? new LinkedHashSet<>() : new HashSet<>();
        while (enumeration.hasMoreElements()) {
            set.add(enumeration.nextElement());
        }
        return set;
    }

    /**
     * 循环为迭代对象的每个元素的指定属性赋值
     *
     * @param iterable 需要做赋值的迭代对象
     * @param property 指定属性名称
     * @param value    指定属性值
     * @param <E>      迭代对象中的泛型对象
     */
    public static <E> void forEach(final Iterable<E> iterable, String property, Object value) {
        if (IterableUtils.isEmpty(iterable)) {
            return;
        }
        IterableUtils.forEach(iterable, new BeanPropertyChangeClosure<>(property, value));
    }

    /**
     * 从集合中获取第一个指定属性与指定属性值所对应的对象的索引位置
     *
     * @param list     需要查找的集合
     * @param property 指定属性名
     * @param value    指定属性值
     * @param <T>      集合中的泛型对象
     * @param <V>      泛型值对象
     * @return int 匹配对象的在集合索引位置
     */
    public static <T, V> int indexOf(List<T> list, String property, V value) {
        return ListUtils.indexOf(list, BeanPredicateUtil.equalPredicate(property, value));
    }


    /**
     * 指定集合中删除某个对象集合
     *
     * @param collection 指定集合
     * @param elements   需要删除的对象集合
     * @param <T>        集合中的泛型对象
     * @return List<T> 删除后的集合
     */
    public static <T> List<T> removeAll(Collection<T> collection, Collection<T> elements) {
        if (isEmpty(collection)) {
            return Lists.arrayList();
        }
        if (isEmpty(elements)) {
            return Lists.arrayList(collection);
        }
        return ListUtils.removeAll(collection, elements);
    }

    /**
     * 从指定迭代对象中，获取指定属性的值集合，返回为List
     *
     * @param iterable 迭代对象
     * @param property 指定属性名称
     * @param <T>      泛型值对象
     * @param <E>      迭代对象中的泛型对象
     * @return List<T> 指定属性值集合
     */
    public static <T, E> List<T> getPropertyList(Iterable<E> iterable, String property) {
        return getPropertyValueCollection(iterable, property, new ArrayList<>(), false);
    }

    /**
     * 从指定迭代对象中，获取指定属性的值集合，返回为List
     *
     * @param iterable 迭代对象
     * @param function 转换函数
     * @param <T>      泛型值对象
     * @param <E>      迭代对象中的泛型对象
     * @return List<T> 指定属性值集合
     */
    public static <T, E> List<T> getPropertyList(Iterable<E> iterable, Function<E, T> function) {
        return getPropertyList(iterable, function, false);
    }

    /**
     * 从指定迭代对象中，获取指定属性的值集合，返回为List
     *
     * @param iterable   迭代对象
     * @param property   指定属性名称
     * @param filterNull 是否过滤空
     * @param <T>        泛型值对象
     * @param <E>        迭代对象中的泛型对象
     * @return List<T> 指定属性值集合
     */
    public static <T, E> List<T> getPropertyList(Iterable<E> iterable, String property, boolean filterNull) {
        return getPropertyValueCollection(iterable, property, new ArrayList<>(), filterNull);
    }

    /**
     * 从指定迭代对象中，获取指定属性的值集合，返回为List
     *
     * @param iterable   迭代对象
     * @param function   转换函数
     * @param filterNull 是否过滤空
     * @param <T>        泛型值对象
     * @param <E>        迭代对象中的泛型对象
     * @return List<T> 指定属性值集合
     */
    public static <T, E> List<T> getPropertyList(Iterable<E> iterable, Function<E, T> function, boolean filterNull) {
        if (isEmpty(iterable)) {
            return Lists.arrayList();
        }
        Validate.notNull(function, "function can't be null!");
        Stream<T> stream = StreamSupport.stream(iterable.spliterator(), false).map(function);
        return filterNull ? stream.filter(ObjectUtil::isNotNull).collect(Collectors.toList()) : stream.collect(Collectors.toList());
    }

    /**
     * 从指定迭代对象中，获取指定属性的值集合，返回为Set
     *
     * @param iterable 迭代对象
     * @param property 指定属性名称
     * @param <T>      泛型值对象
     * @param <E>      迭代对象中的泛型对象
     * @return Set<T> 指定属性值集合
     */
    public static <T, E> Set<T> getPropertySet(Iterable<E> iterable, String property) {
        return getPropertyValueCollection(iterable, property, new LinkedHashSet<>(), false);
    }

    /**
     * 从指定迭代对象中，获取指定属性的值集合，返回为Set
     *
     * @param iterable 迭代对象
     * @param function 转换函数
     * @param <T>      泛型值对象
     * @param <E>      迭代对象中的泛型对象
     * @return Set<T> 指定属性值集合
     */
    public static <T, E> Set<T> getPropertySet(Iterable<E> iterable, Function<E, T> function) {
        return getPropertySet(iterable, function, false);
    }

    /**
     * 从指定迭代对象中，获取指定属性的值集合，返回为Set
     *
     * @param iterable   迭代对象
     * @param property   指定属性名称
     * @param filterNull 是否过滤空
     * @param <T>        泛型值对象
     * @param <E>        迭代对象中的泛型对象
     * @return Set<T> 指定属性值集合
     */
    public static <T, E> Set<T> getPropertySet(Iterable<E> iterable, String property, boolean filterNull) {
        return getPropertyValueCollection(iterable, property, new LinkedHashSet<>(), filterNull);
    }

    /**
     * 从指定迭代对象中，获取指定属性的值集合，返回为Set
     *
     * @param iterable   迭代对象
     * @param function   转换函数
     * @param filterNull 是否过滤空
     * @param <T>        泛型值对象
     * @param <E>        迭代对象中的泛型对象
     * @return Set<T> 指定属性值集合
     */
    public static <T, E> Set<T> getPropertySet(Iterable<E> iterable, Function<E, T> function, boolean filterNull) {
        if (isEmpty(iterable)) {
            return Lists.linkedHashSet();
        }
        Validate.notNull(function, "function can't be null!");
        Stream<T> stream = StreamSupport.stream(iterable.spliterator(), false).map(function);
        if (filterNull) {
            stream = stream.filter(ObjectUtil::isNotNull);
        }
        return stream.collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * 循环指定迭代对象，以迭代对象中的keyProperty属性值当key，迭代对象中的valueProperty属性值当value，封装成Map对象返回
     *
     * @param iterable      迭代对象
     * @param keyProperty   需要当做key的属性名
     * @param valueProperty 需要当做value的属性名
     * @param <K>           Map对象中key的泛型对象
     * @param <V>           Map对象中value的泛型对象
     * @param <E>           迭代对象中的泛型对象
     * @return Map 封装好的Map对象
     */
    public static <K, V, E> Map<K, V> getPropertyMap(Iterable<E> iterable, String keyProperty, String valueProperty) {
        if (ObjectUtil.isEmpty(iterable)) {
            return emptyMap();
        }
        Validate.notEmpty(keyProperty, "keyProperty can't be null/empty!");
        Validate.notEmpty(valueProperty, "valueProperty can't be null/empty!");
        Map<K, V> map = MapUtil.newHashMap(true);
        for (E bean : iterable) {
            map.put(PropertyUtil.getProperty(bean, keyProperty), PropertyUtil.getProperty(bean, valueProperty));
        }
        return map;
    }

    /**
     * 循环指定迭代对象，以迭代对象中的keyProperty属性值当key，迭代对象中的valueProperty属性值当value，封装成Map对象返回
     *
     * @param iterable      迭代对象
     * @param keyFunction   map的key转换函数
     * @param valueFunction map的value转换函数
     * @param <K>           Map对象中key的泛型对象
     * @param <V>           Map对象中value的泛型对象
     * @param <E>           迭代对象中的泛型对象
     * @return Map 封装好的Map对象
     */
    public static <K, V, E> Map<K, V> getPropertyMap(Iterable<E> iterable, Function<E, K> keyFunction, Function<E, V> valueFunction) {
        if (isEmpty(iterable)) {
            return emptyMap();
        }
        Validate.notNull(keyFunction, "keyFunction can't be null!");
        Validate.notNull(valueFunction, "valueFunction can't be null!");
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toMap(keyFunction, valueFunction, (v, v2) -> v2, LinkedHashMap::new));
    }

    /**
     * 从指定迭代对象中,获取第一个指定属性的值对应的元素
     *
     * @param iterable 迭代对象
     * @param property 指定属性名
     * @param value    指定属性对应的值
     * @param <E>      迭代对象中的泛型对象
     * @param <V>      值的泛型对象
     * @return E 返回值对应的对象
     */
    public static <E, V> E get(Iterable<E> iterable, String property, V value) {
        return null == iterable ? null : get(iterable, BeanPredicateUtil.equalPredicate(property, value));
    }

    /**
     * 从指定迭代对象中,获取第一个指定属性的值对应的元素
     *
     * @param iterable 迭代对象
     * @param function 转换函数
     * @param value    指定属性对应的值
     * @param <E>      迭代对象中的泛型对象
     * @param <V>      值的泛型对象
     * @return E 返回值对应的对象
     */
    public static <E, V> E get(Iterable<E> iterable, Function<E, V> function, V value) {
        if (isEmpty(iterable)) {
            return null;
        }
        Validate.notNull(function, "function can't be null!");
        return StreamSupport.stream(iterable.spliterator(), false).filter(e -> ObjectUtil.equals(function.apply(e), value)).findFirst().orElse(null);
    }

    /**
     * 从指定迭代对象中,获取第一个与匹配规则对象对应的元素
     *
     * @param iterable  迭代对象
     * @param predicate 匹配规则对象
     * @param <E>       迭代对象中的泛型对象
     * @return E 匹配规则对应的对象
     */
    public static <E> E get(Iterable<E> iterable, Predicate<E> predicate) {
        return IterableUtils.find(iterable, predicate);
    }

    /**
     * 循环迭代对象,根据指定属性名，获取迭代对象中的元素,判断值是否在指定的数组值集中，如果在,将该对象存入list中并返回
     *
     * @param iterable 迭代对象
     * @param property 指定属性名
     * @param values   指定的数组值集
     * @param <E>      迭代对象中的泛型对象
     * @param <V>      值的泛型对象
     * @return List<E> 返回与条件匹配的数据集合
     */
    @SafeVarargs
    public static <E, V> List<E> find(Iterable<E> iterable, String property, V... values) {
        return find(iterable, BeanPredicateUtil.containsPredicate(property, values));
    }

    /**
     * 循环迭代对象,根据指定属性名，获取迭代对象中的元素,判断值是否在指定的数组值集中，如果在,将该对象存入list中并返回
     *
     * @param iterable 迭代对象
     * @param function 转换函数
     * @param values   指定的数组值集
     * @param <E>      迭代对象中的泛型对象
     * @param <V>      值的泛型对象
     * @return List<E> 返回与条件匹配的数据集合
     */
    @SafeVarargs
    public static <E, V> List<E> find(Iterable<E> iterable, Function<E, V> function, V... values) {
        if (isEmpty(iterable)) {
            return Lists.arrayList();
        }
        Validate.notNull(function, "function can't be null!");
        return StreamSupport.stream(iterable.spliterator(), false).filter(e -> ArrayUtil.contains(values, function.apply(e))).collect(Collectors.toList());
    }


    /**
     * 循环迭代对象,根据指定属性名，获取迭代对象中的元素,判断值是否在指定的列表值集中，如果在,将该对象存入list中并返回
     *
     * @param iterable   迭代对象
     * @param property   指定属性名
     * @param collection 指定的列表值集
     * @param <E>        迭代对象中的泛型对象
     * @param <V>        值的泛型对象
     * @return List<E> 返回与条件匹配的数据集合
     */
    public static <E, V> List<E> find(Iterable<E> iterable, String property, Collection<V> collection) {
        return find(iterable, BeanPredicateUtil.containsPredicate(property, collection));
    }

    /**
     * 循环迭代对象,根据指定属性名，获取迭代对象中的元素,判断值是否在指定的列表值集中，如果在,将该对象存入list中并返回
     *
     * @param iterable   迭代对象
     * @param function   转换函数
     * @param collection 指定的列表值集
     * @param <E>        迭代对象中的泛型对象
     * @param <V>        值的泛型对象
     * @return List<E> 返回与条件匹配的数据集合
     */
    public static <E, V> List<E> find(Iterable<E> iterable, Function<E, V> function, Collection<V> collection) {
        if (isEmpty(iterable)) {
            return Lists.arrayList();
        }
        Validate.notNull(function, "function can't be null!");
        Validate.notNull(collection, "collection can't be null!");
        return StreamSupport.stream(iterable.spliterator(), false).filter(e -> collection.contains(function.apply(e))).collect(Collectors.toList());
    }

    /**
     * 循环迭代对象,获取与匹配规则相符的数据对象，存入list中并返回
     *
     * @param iterable  迭代对象
     * @param predicate 匹配规则对象
     * @param <E>       迭代对象中的泛型对象
     * @return List<E> 返回与匹配规则相符的数据集合
     */
    public static <E> List<E> find(Iterable<E> iterable, Predicate<E> predicate) {
        if (isEmpty(iterable)) {
            return Lists.arrayList();
        }
        return (List<E>) CollectionUtils.select(iterable, predicate);
    }

    /**
     * 循环迭代对象,根据指定属性名，获取迭代对象中的元素,判断值是否在指定的数组值集中，如果不在,将该对象存入list中并返回
     *
     * @param iterable 迭代对象
     * @param property 指定属性名
     * @param values   指定的数组值集
     * @param <E>      迭代对象中的泛型对象
     * @param <V>      值的泛型对象
     * @return List<E> 返回与条件不相符的数据集合
     */
    @SafeVarargs
    public static <E, V> List<E> findRejected(Iterable<E> iterable, String property, V... values) {
        return ObjectUtil.isEmpty(iterable) ? Collections.emptyList() : findRejected(iterable, BeanPredicateUtil.containsPredicate(property, values));
    }

    /**
     * 循环迭代对象,根据指定属性名，获取迭代对象中的元素,判断值是否在指定的数组值集中，如果不在,将该对象存入list中并返回
     *
     * @param iterable 迭代对象
     * @param function 转换函数
     * @param values   指定的数组值集
     * @param <E>      迭代对象中的泛型对象
     * @param <V>      值的泛型对象
     * @return List<E> 返回与条件不相符的数据集合
     */
    @SafeVarargs
    public static <E, V> List<E> findRejected(Iterable<E> iterable, Function<E, V> function, V... values) {
        if (isEmpty(iterable)) {
            return Lists.arrayList();
        }
        Validate.notNull(function, "function can't be null!");
        return StreamSupport.stream(iterable.spliterator(), false).filter(e -> !ArrayUtil.contains(values, function.apply(e))).collect(Collectors.toList());
    }

    /**
     * 循环迭代对象,根据指定属性名，获取迭代对象中的元素,判断值是否在指定的列表值集中，如果不在,将该对象存入list中并返回
     *
     * @param iterable   迭代对象
     * @param property   指定属性名
     * @param collection 指定的列表值集
     * @param <E>        迭代对象中的泛型对象
     * @param <V>        值的泛型对象
     * @return List<E> 返回与条件不相符的数据集合
     */
    public static <E, V> List<E> findRejected(Iterable<E> iterable, String property, Collection<V> collection) {
        return ObjectUtil.isEmpty(iterable) ? Collections.emptyList() : findRejected(iterable, BeanPredicateUtil
                .containsPredicate(property, collection));
    }

    /**
     * 循环迭代对象,根据指定属性名，获取迭代对象中的元素,判断值是否在指定的列表值集中，如果不在,将该对象存入list中并返回
     *
     * @param iterable   迭代对象
     * @param function   转换函数
     * @param collection 指定的列表值集
     * @param <E>        迭代对象中的泛型对象
     * @param <V>        值的泛型对象
     * @return List<E> 返回与条件不相符的数据集合
     */
    public static <E, V> List<E> findRejected(Iterable<E> iterable, Function<E, V> function, Collection<V> collection) {
        if (isEmpty(iterable)) {
            return Lists.arrayList();
        }
        Validate.notNull(function, "function can't be null!");
        Validate.notNull(collection, "collection can't be null!");
        return StreamSupport.stream(iterable.spliterator(), false).filter(e -> !collection.contains(function.apply(e))).collect(Collectors.toList());
    }

    /**
     * 循环迭代对象,获取与匹配规则不相符的数据对象，存入list中并返回
     *
     * @param iterable  迭代对象
     * @param predicate 匹配规则对象
     * @param <E>       迭代对象中的泛型对象
     * @return List<E> 返回与匹配规则不相符的数据集合
     */
    public static <E> List<E> findRejected(Iterable<E> iterable, Predicate<E> predicate) {
        return ObjectUtil.isEmpty(iterable) ? Collections.emptyList() : (List<E>) CollectionUtils.selectRejected(iterable, predicate);
    }

    /**
     * 两个集合的并集
     *
     * @param c1 集合1
     * @param c2 集合2
     * @return 取并集以后的集合
     */
    public static <E> List<E> contact(Collection<E> c1, Collection<E> c2) {
        Set<E> set = new HashSet<>();
        if (isNotEmpty(c1)) {
            set.addAll(c1);
        }
        if (isNotEmpty(c2)) {
            set.addAll(c2);
        }
        return new ArrayList<>(set);
    }

    /**
     * 接收一个嵌套集合，取集合的并集
     *
     * @param collections 嵌套集合
     * @return 取并集以后的集合
     */
    public static <E, K extends Collection<E>> List<E> contact(Collection<K> collections) {
        Set<E> set = new HashSet<>();
        if (isNotEmpty(collections)) {
            for (K collection : collections) {
                set.addAll(collection);
            }
        }
        return new ArrayList<>(set);
    }

    public static <T> List<T> filterNull(Collection<T> collection) {
        if (isNotEmpty(collection)) {
            return collection.stream().filter(ObjectUtil::isNotNull).collect(Collectors.toList());
        }
        return Lists.arrayList();
    }

    /**
     * 循环指定集合中的对象，得到指定属性的值后，填充到返回集合中
     *
     * @param iterable   指定集合
     * @param property   指定属性名称
     * @param collection 返回集合
     * @param filterNull 是否过滤空
     * @param <T>        返回集合中的泛型对象
     * @param <E>        循环集合中的泛型对象
     * @param <K>        返回集合泛型对象
     * @return K 返回集合
     */
    private static <T, E, K extends Collection<T>> K getPropertyValueCollection(Iterable<E> iterable, String property, K collection,
                                                                                boolean filterNull) {
        Validate.notNull(collection, "collection can't be null!");
        if (ObjectUtil.isEmpty(iterable)) {
            return collection;
        }
        Validate.notEmpty(property, "property can't be null/empty!");
        for (E bean : iterable) {
            T value = PropertyUtil.getProperty(bean, property);
            //如果需要过滤空，并且获取的值为空，直接过滤
            if (filterNull && ObjectUtil.isNull(value)) {
                continue;
            }
            collection.add(value);
        }
        return collection;
    }
}
