package com.ipower.framework.common.core.collection;

import com.ipower.framework.common.core.entity.TestUser;
import com.ipower.framework.common.core.lang.Validate;
import org.apache.commons.collections.iterators.IteratorEnumeration;
import org.apache.commons.collections4.Predicate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.*;

import static org.junit.Assert.*;

/**
 * CollectionUtil Tester.
 *
 * @author huangad@coracle.com
 */
public class CollectionUtilTest {

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    /**
     * 集合是否为空
     * Method: isEmpty(Collection<T> collection)
     */
    @Test
    public void testIsEmptyCollection() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        assertFalse(CollectionUtil.isEmpty(list));
    }

    /**
     * Iterable是否为空
     * Method: isEmpty(Iterable<?> iterable)
     */
    @Test
    public void testIsEmptyIterable() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        assertFalse(CollectionUtil.isEmpty((Iterable<Integer>) list));
    }

    /**
     * Iterator是否为空
     * Method: isEmpty(Iterator<?> iterator)
     */
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "RedundantOperationOnEmptyContainer"})
    @Test
    public void testIsEmptyIterator() {
        List<Integer> list = new ArrayList<>();
        Iterator<Integer> iterator = list.iterator();
        assertTrue(CollectionUtil.isEmpty(iterator));
    }

    /**
     * 集合是否不为空
     * Method: isNotEmpty(Collection<T> collection)
     */
    @Test
    public void testIsNotEmptyCollection() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        assertTrue(CollectionUtil.isNotEmpty(list));
    }

    /**
     * Iterable是否不为空
     * Method: isNotEmpty(Iterable<?> iterable)
     */
    @Test
    public void testIsNotEmptyIterable() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        assertTrue(CollectionUtil.isNotEmpty((Iterable<Integer>) list));
    }

    /**
     * Iterator是否不为空
     * Method: isNotEmpty(Iterator<?> iterator)
     */
    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "RedundantOperationOnEmptyContainer"})
    @Test
    public void testIsNotEmptyIterator() {
        List<Integer> list = new ArrayList<>();
        Iterator<Integer> iterator = list.iterator();
        assertFalse(CollectionUtil.isNotEmpty(iterator));
    }

    /**
     * 创建新的集合对象
     * Method: create(Class<?> collectionType)
     */
    @Test
    public void testCreate() {
        //抽象集合
        Collection<Object> objects = CollectionUtil.create(AbstractCollection.class);
        Validate.isInstanceOf(ArrayList.class, objects);
        //Set
        Collection<Object> objects1 = CollectionUtil.create(HashSet.class);
        Validate.isInstanceOf(HashSet.class, objects1);

        Collection<Object> objects2 = CollectionUtil.create(LinkedHashSet.class);
        Validate.isInstanceOf(LinkedHashSet.class, objects2);

        Collection<Object> objects3 = CollectionUtil.create(TreeSet.class);
        Validate.isInstanceOf(TreeSet.class, objects3);

        // Collection<Object> objects4 = CollectionUtil.create(EnumSet.class);
        //Validate.isInstanceOf(EnumSet.class, objects4);报空指针异常

        //List
        Collection<Object> objects5 = CollectionUtil.create(ArrayList.class);
        Validate.isInstanceOf(ArrayList.class, objects5);

        Collection<Object> objects6 = CollectionUtil.create(LinkedList.class);
        Validate.isInstanceOf(LinkedList.class, objects6);


    }

    /**
     * 将指定对象全部加入到集合中
     * Method: addAll(Collection<T> collection, Object value)
     */
    @Test
    public void testAddAllForCollectionValue() {

        List<Integer> list = new ArrayList<>();
        Integer integer = 11;
        Collection<Integer> collection = CollectionUtil.addAll(list, integer);
        Validate.isTrue(collection.contains(integer));

    }

    /**
     * 将指定对象全部加入到集合中
     * Method: addAll(Collection<T> collection, Object value, Type elementType)
     */
    @Test
    public void testAddAllForCollectionValueElementType() {
        List<Integer> list = new ArrayList<>();
        Integer integer = 11;
        Type type = integer.getClass();
        Collection<Integer> collection = CollectionUtil.addAll(list, integer, type);
        Validate.isTrue(collection.contains(integer));
    }

    /**
     * 加入全部到集合
     * Method: addAll(Collection<T> collection, Iterator<T> iterator)
     */
    @Test
    public void testAddAllForCollectionIterator() {
        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        list.add(11);
        list1.add(22);
        Iterator<Integer> iterator = list1.iterator();
        Collection<Integer> collection = CollectionUtil.addAll(list, iterator);
        Validate.isTrue(collection.contains(11) && collection.contains(22));
    }

    /**
     * 加入全部到集合
     * Method: addAll(Collection<T> collection, Iterable<T> iterable)
     */
    @Test
    public void testAddAllForCollectionIterable() {
        List<Integer> list = new ArrayList<>();
        Integer integer = 11;
        list.add(11);

        Set<Integer> set = new HashSet<>();
        Integer integer1 = 122;
        set.add(integer1);

        Collection<Integer> collection = CollectionUtil.addAll(list, set);
        Validate.isTrue(collection.contains(integer) && collection.contains(integer1));

    }

    /**
     * 加入全部到集合
     * Method: addAll(Collection<T> collection, Enumeration<T> enumeration)
     */
    @SuppressWarnings({"unchecked", "CastCanBeRemovedNarrowingVariableType"})
    @Test
    public void testAddAllForCollectionEnumeration() {
        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        list.add(99);
        list1.add(1);
        Iterator<Integer> iterator = list1.iterator();
        Enumeration<Integer> enumeration = new IteratorEnumeration();
        ((IteratorEnumeration) enumeration).setIterator(iterator);
        Collection<Integer> collection = CollectionUtil.addAll(list, enumeration);
        Validate.isTrue(collection.contains(99) && collection.contains(1));


    }

    /**
     * 加入全部数组元素到集合
     * Method: addAll(Collection<T> collection, T[] values)
     */
    @Test
    public void testAddAllForCollectionValues() {
        List<Integer> list = new ArrayList<>();
        Integer[] integers = new Integer[]{5, 3, 22, 8};
        Collection<Integer> collection = CollectionUtil.addAll(list, integers);
        Validate.isTrue(collection.contains(5) && collection.contains(22));
    }

    /**
     * Iterable}转为{@link Collection
     * Method: toCollection(Iterable<E> iterable)
     */
    @Test
    public void testToCollection() {
        List<Integer> list = new LinkedList<>();
        list.add(11);
        Collection<Integer> collection = CollectionUtil.toCollection(list);
        Validate.isTrue(collection.contains(11));

    }

    /**
     * 新建一个HashSet
     * Method: newHashSet(T... ts)
     */
    @Test
    public void testNewHashSetTs() {
        Float[] floats = new Float[]{5f, 44f};
        HashSet<Float> floats1 = CollectionUtil.newHashSet(floats);
        Validate.isTrue(floats1.contains(5f) && floats1.contains(44f));
    }

    /**
     * 新建一个HashSet
     * Method: newLinkedHashSet(T... ts)
     */
    @Test
    public void testNewLinkedHashSet() {
        Double[] doubles = new Double[]{4D, 5D};
        LinkedHashSet<Double> doubles1 = CollectionUtil.newLinkedHashSet(doubles);
        Validate.isTrue(doubles1.contains(4D) && doubles1.contains(5D));
    }

    /**
     * 新建一个HashSet
     * Method: newHashSet(boolean isSorted, T... ts)
     */
    @Test
    public void testNewHashSetForIsSortedTs() {
        Integer[] integers = new Integer[]{4, 1};
        HashSet<Integer> hashSet = CollectionUtil.newHashSet(true, integers);

        Validate.isTrue(!CollectionUtil.isEmpty(hashSet));

    }

    /**
     * 新建一个HashSet
     * Method: newHashSet(Collection<T> collection)
     */
    @Test
    public void testNewHashSetCollection() {
        List<Integer> list = new LinkedList<>();
        HashSet<Integer> integers = CollectionUtil.newHashSet(list);
        Validate.isTrue(CollectionUtil.isEmpty(integers));
    }

    /**
     * 新建一个HashSet
     * Method: newHashSet(boolean isSorted, Collection<T> collection)
     */
    @Test
    public void testNewHashSetForIsSortedCollection() {
        List<Integer> list = new LinkedList<>();
        HashSet<Integer> integers = CollectionUtil.newHashSet(true, list);
        Validate.isTrue(CollectionUtil.isEmpty(integers));
    }

    /**
     * 新建一个HashSet
     * Method: newHashSet(boolean isSorted, Iterator<T> iter)
     */
    @Test
    public void testNewHashSetForIsSortedIter() {
        List<Integer> list = new LinkedList<>();
        list.add(12);
        Iterator<Integer> iterator = list.iterator();
        HashSet<Integer> integers = CollectionUtil.newHashSet(false, iterator);
        Validate.isTrue(integers.contains(12));
    }

    /**
     * 新建一个HashSet
     * Method: newHashSet(boolean isSorted, Enumeration<T> enumeration)
     */
    @SuppressWarnings({"unchecked", "CastCanBeRemovedNarrowingVariableType"})
    @Test
    public void testNewHashSetForIsSortedEnumeration() {
        List<Integer> list = new LinkedList<>();
        list.add(555);
        Iterator<Integer> iterator = list.iterator();
        Enumeration<Integer> enumeration = new IteratorEnumeration();
        ((IteratorEnumeration) enumeration).setIterator(iterator);

        HashSet<Integer> integers = CollectionUtil.newHashSet(false, enumeration);
        Validate.isTrue(integers.contains(555));


    }

    /**
     * 循环为迭代对象的每个元素的指定属性赋值
     * Method: forEach(final Iterable<E> iterable, String property, Object value)
     */
    @Test
    public void testForEach() {
        List<TestUser> list = new LinkedList<>();
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        list.add(testUser1);
        list.add(testUser2);
        String property = "name";
        String value = "hello";

        CollectionUtil.forEach(list, property, value);
        assertEquals("hello", testUser1.getName());
        assertEquals("hello", testUser2.getName());

    }

    /**
     * 从集合中获取第一个指定属性与指定属性值所对应的对象的索引位置
     * Method: indexOf(List<T> list, String property, V value)
     */
    @Test
    public void testIndexOf() {
        List<TestUser> list = new LinkedList<>();
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        testUser1.setName("hello");
        testUser2.setName("world");

        list.add(testUser1);
        list.add(testUser2);
        String property = "name";
        String value = "hello";
        String value1 = "world";

        assertEquals(0, CollectionUtil.indexOf(list, property, value));
        assertEquals(1, CollectionUtil.indexOf(list, property, value1));

    }

    /**
     * Method: removeAll(Collection<T> collection, Collection<T> elements)
     */
    @Test
    public void testRemoveAll() {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> list1 = new LinkedList<>();
        list1.add(1);
        list1.add(2);
        List<Integer> list2 = CollectionUtil.removeAll(list, list1);
        assertEquals("[3]", list2.toString());
    }

    /**
     * 从指定迭代对象中，获取指定属性的值集合，返回为List
     * Method: getPropertyList(Iterable<E> iterable, String property)
     */
    @Test
    public void testGetPropertyList() {
        List<TestUser> list = new LinkedList<>();
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        TestUser testUser3 = new TestUser();
        testUser1.setName("hello");
        testUser2.setName("world");
        list.add(testUser1);
        list.add(testUser2);
        list.add(testUser3);

        String property = "name";
        List<Object> propertyList = CollectionUtil.getPropertyList(list, property);
        assertEquals("[hello, world, null]", propertyList.toString());

        List<String> names = CollectionUtil.getPropertyList(list, TestUser::getName);
        assertEquals("[hello, world, null]", names.toString());

        List<Object> propertyList2 = CollectionUtil.getPropertyList(list, property, true);
        assertEquals("[hello, world]", propertyList2.toString());

        List<String> names2 = CollectionUtil.getPropertyList(list, TestUser::getName, true);
        assertEquals("[hello, world]", names2.toString());
    }

    /**
     * 从指定迭代对象中，获取指定属性的值集合，返回为Set
     * Method: getPropertySet(Iterable<E> iterable, String property)
     */
    @Test
    public void testGetPropertySet() {
        List<TestUser> list = new LinkedList<>();
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        TestUser testUser3 = new TestUser();
        testUser1.setName("hello");
        testUser2.setName("world");
        list.add(testUser1);
        list.add(testUser2);
        list.add(testUser3);

        String property = "name";
        Set<Object> propertySet = CollectionUtil.getPropertySet(list, property);
        assertEquals("[hello, world, null]", propertySet.toString());

        Set<String> names = CollectionUtil.getPropertySet(list, TestUser::getName);
        assertEquals("[hello, world, null]", names.toString());

        Set<Object> propertySet2 = CollectionUtil.getPropertySet(list, property, true);
        assertEquals("[hello, world]", propertySet2.toString());

        Set<String> names2 = CollectionUtil.getPropertySet(list, TestUser::getName, true);
        assertEquals("[hello, world]", names2.toString());
    }

    /**
     * 循环指定迭代对象，以迭代对象中的keyProperty属性值当key，迭代对象中的valueProperty属性值当value，封装成Map对象返回
     * Method: getPropertyMap(Iterable<E> iterable, String keyProperty, String valueProperty)
     */
    @Test
    public void testGetPropertyMap() {
        List<TestUser> list = new LinkedList<>();
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        testUser1.setName("李三");
        testUser2.setName("李四");
        testUser1.setAge(18);
        testUser2.setAge(22);
        list.add(testUser1);
        list.add(testUser2);

        String keyProperty = "name";
        String valueProperty = "age";

        Map<Object, Object> propertyMap = CollectionUtil.getPropertyMap(list, keyProperty, valueProperty);
        assertEquals("{李三=18, 李四=22}", propertyMap.toString());

        Map<String, Integer> propertyMap2 = CollectionUtil.getPropertyMap(list, TestUser::getName, TestUser::getAge);
        assertEquals("{李三=18, 李四=22}", propertyMap2.toString());
    }

    /**
     * 循环指定集合中的对象，得到指定属性的值后，填充到返回集合中
     * Method: get(Iterable<E> iterable, String property, V value)
     */
    @Test
    public void testGetForIterablePropertyValue() {
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        testUser1.setName("李三");

        List<TestUser> list = new LinkedList<>();
        list.add(testUser1);
        list.add(testUser2);

        String property = "name";
        String value = "李三";
        TestUser testUser = CollectionUtil.get(list, property, value);
        assertEquals(testUser1, testUser);

        TestUser testUser3 = CollectionUtil.get(list, TestUser::getName, value);
        assertEquals(testUser1, testUser3);

        TestUser testUser4 = CollectionUtil.get(list, TestUser::getName, "value");
        assertNotEquals(testUser1, testUser4);
    }

    /**
     * 从指定迭代对象中,获取第一个与匹配规则对象对应的元素
     * Method: get(Iterable<E> iterable, Predicate<E> predicate)
     */
    @Test
    public void testGetForIterablePredicate() {
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        testUser1.setName("李三");

        List<TestUser> list = new LinkedList<>();
        list.add(testUser1);
        list.add(testUser2);

        Predicate<TestUser> predicate = user -> user.getName() != null;
        TestUser testUser3 = CollectionUtil.get(list, predicate);

        //User user4 = CollectionUtil.get(list, user -> user.getName() != null);
        assertEquals(testUser3, testUser1);

    }

    /**
     * 循环迭代对象,根据指定属性名，获取迭代对象中的元素,判断值是否在指定的数组值集中，如果在,将该对象存入list中并返回
     * Method: find(Iterable<E> iterable, String property, V... values)
     */
    @Test
    public void testFindForIterablePropertyValues() {
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        testUser1.setName("李三");
        testUser2.setName("李四");

        List<TestUser> list = new LinkedList<>();
        list.add(testUser1);
        list.add(testUser2);

        String property = "name";
        String[] strings = new String[]{"李三", "李四", "卡特"};

        List<TestUser> testUsers = CollectionUtil.find(list, property, strings);
        assertEquals("[TestUser(name=李三, age=null, studentId=null, id=null), TestUser(name=李四, age=null, studentId=null, id=null)]", testUsers.toString());

        List<TestUser> users2 = CollectionUtil.find(list, TestUser::getName, strings);
        assertEquals("[TestUser(name=李三, age=null, studentId=null, id=null), TestUser(name=李四, age=null, studentId=null, id=null)]", users2.toString());
    }

    /**
     * 循环迭代对象,根据指定属性名，获取迭代对象中的元素,判断值是否在指定的列表值集中，如果在,将该对象存入list中并返回
     * Method: find(Iterable<E> iterable, String property, Collection<V> collection)
     */
    @Test
    public void testFindForIterablePropertyCollection() {
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        testUser1.setName("李三");
        testUser2.setName("李四");

        List<TestUser> list = new LinkedList<>();
        list.add(testUser1);
        list.add(testUser2);

        String property = "name";

        Set<String> strings = new HashSet<>();
        strings.add("李三");
        strings.add("李四");
        strings.add("李五");
        List<TestUser> testUsers = CollectionUtil.find(list, property, strings);
        assertEquals("[TestUser(name=李三, age=null, studentId=null, id=null), TestUser(name=李四, age=null, studentId=null, id=null)]", testUsers.toString());

        List<TestUser> users2 = CollectionUtil.find(list, TestUser::getName, strings);
        assertEquals("[TestUser(name=李三, age=null, studentId=null, id=null), TestUser(name=李四, age=null, studentId=null, id=null)]", users2.toString());

    }

    /**
     * 循环迭代对象,获取与匹配规则相符的数据对象，存入list中并返回
     * Method: find(Iterable<E> iterable, Predicate<E> predicate)
     */
    @Test
    public void testFindForIterablePredicate() {
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        testUser1.setName("李三");

        List<TestUser> list = new LinkedList<>();
        list.add(testUser1);
        list.add(testUser2);

        Predicate<TestUser> predicate = user -> user.getName() != null;

        List<TestUser> testUsers = CollectionUtil.find(list, predicate);
        assertEquals("[TestUser(name=李三, age=null, studentId=null, id=null)]", testUsers.toString());
    }

    /**
     * 循环迭代对象,根据指定属性名，获取迭代对象中的元素,判断值是否在指定的数组值集中，如果不在,将该对象存入list中并返回
     * Method: findRejected(Iterable<E> iterable, String property, V... values)
     */
    @Test
    public void testFindRejectedForIterablePropertyValues() {
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        testUser1.setName("李三");
        testUser2.setName("卡特");

        List<TestUser> list = new LinkedList<>();
        list.add(testUser1);
        list.add(testUser2);

        String property = "name";
        String[] strings = new String[]{"李三", "李四",};

        List<TestUser> testUsers = CollectionUtil.findRejected(list, property, strings);
        assertEquals("[TestUser(name=卡特, age=null, studentId=null, id=null)]", testUsers.toString());

        List<TestUser> users2 = CollectionUtil.findRejected(list, TestUser::getName, strings);
        assertEquals("[TestUser(name=卡特, age=null, studentId=null, id=null)]", users2.toString());
    }

    /**
     * 循环迭代对象,根据指定属性名，获取迭代对象中的元素,判断值是否在指定的列表值集中，如果不在,将该对象存入list中并返回
     * Method: findRejected(Iterable<E> iterable, String property, Collection<V> collection)
     */
    @Test
    public void testFindRejectedForIterablePropertyCollection() {
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        testUser1.setName("李三");
        testUser2.setName("李四");

        List<TestUser> list = new LinkedList<>();
        list.add(testUser1);
        list.add(testUser2);

        String property = "name";

        Set<String> strings = new HashSet<>();
        strings.add("李三");
        strings.add("李五");
        List<TestUser> testUsers = CollectionUtil.findRejected(list, property, strings);
        assertEquals("[TestUser(name=李四, age=null, studentId=null, id=null)]", testUsers.toString());

        List<TestUser> users2 = CollectionUtil.findRejected(list, TestUser::getName, strings);
        assertEquals("[TestUser(name=李四, age=null, studentId=null, id=null)]", users2.toString());
    }

    /**
     * 循环迭代对象,获取与匹配规则不相符的数据对象，存入list中并返回
     * Method: findRejected(Iterable<E> iterable, Predicate<E> predicate)
     */
    @Test
    public void testFindRejectedForIterablePredicate() {
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        testUser1.setName("李三");

        List<TestUser> list = new LinkedList<>();
        list.add(testUser1);
        list.add(testUser2);

        Predicate<TestUser> predicate = user -> user.getName() == null;

        List<TestUser> testUsers = CollectionUtil.findRejected(list, predicate);
        assertEquals("[TestUser(name=李三, age=null, studentId=null, id=null)]", testUsers.toString());
    }

    /**
     * Method: contact(Collection<E> c1, Collection<E> c2)
     */
    @Test
    public void testContact() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);

        Set<Integer> set = new LinkedHashSet<>();
        set.add(3);
        set.add(4);
        Collection<Integer> contact = CollectionUtil.contact(list, set);
        assertEquals("[1, 2, 3, 4]", contact.toString());
    }

}
