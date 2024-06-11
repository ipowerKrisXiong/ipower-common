package com.ipower.framework.common.core.collection;

import java.util.*;

/**
 * collection集合对象构造器，用来便捷的初始化新collection对象
 *
 * @author kris
 * @since 1.0.0
 */
public class Lists {

    private Lists() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // ---------------new ArrayList<>()

    /**
     * 新建一个ArrayList
     *
     * @param <T> 集合元素类型
     * @return ArrayList对象
     */
    public static <T> ArrayList<T> arrayList() {
        return new ArrayList<>();
    }

    /**
     * <p>接收一个数组数据对象，新建一个ArrayList。若提供的参数为null，会返回空的{@link ArrayList}<p/>
     *
     * @param values 数组
     * @param <T>    元素类型
     * @return ArrayList对象
     */
    @SafeVarargs
    public static <T> ArrayList<T> arrayList(T... values) {
        if (ArrayUtil.isEmpty(values)) {
            return arrayList();
        }
        final ArrayList<T> list = new ArrayList<>(values.length);
        Collections.addAll(list, values);
        return list;
    }

    /**
     * <p>接收一个集合数据对象，新建一个ArrayList。若提供的参数为null，会返回空的{@link ArrayList}<p/>
     *
     * @param collection {@link Collection}集合
     * @param <T>        元素类型
     * @return ArrayList对象
     */
    public static <T> ArrayList<T> arrayList(Collection<T> collection) {
        return CollectionUtil.isEmpty(collection) ? arrayList() : new ArrayList<>(collection);
    }

    /**
     * <p>接收一个Iterable迭代器数据对象，新建一个ArrayList。若提供的参数为null，会返回空的{@link ArrayList}<p/>
     *
     * @param iterable {@link Iterable}迭代器
     * @param <T>      元素类型
     * @return ArrayList对象
     */
    public static <T> ArrayList<T> arrayList(Iterable<T> iterable) {
        return iterable == null ? arrayList() : arrayList(iterable.iterator());
    }

    /**
     * <p>接收一个Iterator迭代器数据对象，新建一个ArrayList。若提供的参数为null，会返回空的{@link ArrayList}<p/>
     *
     * @param iterator {@link Iterator}迭代器
     * @param <T>      元素类型
     * @return ArrayList对象
     */
    public static <T> ArrayList<T> arrayList(Iterator<T> iterator) {
        final ArrayList<T> list = arrayList();
        if (iterator != null) {
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
        }
        return list;
    }

    /**
     * <p>接收一个枚举数据对象，新建一个ArrayList。若提供的参数为null，会返回空的{@link ArrayList}<p/>
     *
     * @param enumeration {@link Enumeration}枚举
     * @param <T>         元素类型
     * @return ArrayList对象
     */
    public static <T> ArrayList<T> arrayList(Enumeration<T> enumeration) {
        final ArrayList<T> list = arrayList();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                list.add(enumeration.nextElement());
            }
        }
        return list;
    }

    // ---------------new LinkedList<>()

    /**
     * 新建一个LinkedList
     *
     * @param <T> 元素类型
     * @return LinkedList对象
     */
    public static <T> LinkedList<T> linkedList() {
        return new LinkedList<>();
    }

    /**
     * <p>接收一个数组数据对象，新建一个LinkedList。若提供的参数为null，会返回空的{@link LinkedList}<p/>
     *
     * @param values 数组
     * @param <T>    元素类型
     * @return LinkedList对象
     */
    @SafeVarargs
    public static <T> LinkedList<T> linkedList(T... values) {
        final LinkedList<T> list = linkedList();
        if (ArrayUtil.isNotEmpty(values)) {
            Collections.addAll(list, values);
        }
        return list;
    }

    /**
     * <p>接收一个集合数据对象，新建一个LinkedList。若提供的参数为null，会返回空的{@link LinkedList}<p/>
     *
     * @param collection {@link Collection}集合
     * @param <T>        元素类型
     * @return LinkedList对象
     */
    public static <T> LinkedList<T> linkedList(Collection<T> collection) {
        return CollectionUtil.isEmpty(collection) ? linkedList() : new LinkedList<>(collection);
    }

    /**
     * <p>接收一个Iterable迭代器数据对象，新建一个LinkedList。若提供的参数为null，会返回空的{@link LinkedList}<p/>
     *
     * @param iterable {@link Iterable}迭代器
     * @param <T>      元素类型
     * @return LinkedList对象
     */
    public static <T> LinkedList<T> linkedList(Iterable<T> iterable) {
        return iterable == null ? linkedList() : linkedList(iterable.iterator());
    }

    /**
     * <p>接收一个Iterator迭代器，新建一个LinkedList。若提供的参数为null，会返回空的{@link LinkedList}<p/>
     *
     * @param iterator {@link Iterator}迭代器
     * @param <T>      元素类型
     * @return LinkedList对象
     */
    public static <T> LinkedList<T> linkedList(Iterator<T> iterator) {
        final LinkedList<T> list = linkedList();
        if (iterator != null) {
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
        }
        return list;
    }

    /**
     * <p>接收一个枚举数据对象，新建一个LinkedList。若提供的参数为null，会返回空的{@link LinkedList}<p/>
     *
     * @param enumeration {@link Enumeration}枚举
     * @param <T>         元素类型
     * @return LinkedList对象
     */
    public static <T> LinkedList<T> linkedList(Enumeration<T> enumeration) {
        final LinkedList<T> list = linkedList();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                list.add(enumeration.nextElement());
            }
        }
        return list;
    }

    /**
     * 新建一个HashSet
     *
     * @param <T> 集合元素类型
     * @param ts  元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> HashSet<T> hashSet(T... ts) {
        return CollectionUtil.newHashSet(ts);
    }

    /**
     * 新建一个LinkedHashSet
     *
     * @param <T> 集合元素类型
     * @param ts  元素数组
     * @return HashSet对象
     */
    @SafeVarargs
    public static <T> LinkedHashSet<T> linkedHashSet(T... ts) {
        return CollectionUtil.newLinkedHashSet(ts);
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
    public static <T> HashSet<T> hashSet(boolean isSorted, T... ts) {
        return CollectionUtil.newHashSet(isSorted, ts);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>        集合元素类型
     * @param collection 集合
     * @return HashSet对象
     */
    public static <T> HashSet<T> hashSet(Collection<T> collection) {
        return CollectionUtil.newHashSet(collection);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>        集合元素类型
     * @param isSorted   是否有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param collection 集合，用于初始化Set
     * @return HashSet对象
     */
    public static <T> HashSet<T> hashSet(boolean isSorted, Collection<T> collection) {
        return CollectionUtil.newHashSet(isSorted, collection);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>      集合元素类型
     * @param isSorted 是否有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param iter     {@link Iterator}
     * @return HashSet对象
     */
    public static <T> HashSet<T> hashSet(boolean isSorted, Iterator<T> iter) {
        return CollectionUtil.newHashSet(isSorted, iter);
    }

    /**
     * 新建一个HashSet
     *
     * @param <T>         集合元素类型
     * @param isSorted    是否有序，有序返回 {@link LinkedHashSet}，否则返回{@link HashSet}
     * @param enumeration {@link Enumeration}
     * @return HashSet对象
     */
    public static <T> HashSet<T> hashSet(boolean isSorted, Enumeration<T> enumeration) {
        return CollectionUtil.newHashSet(isSorted, enumeration);
    }

}
