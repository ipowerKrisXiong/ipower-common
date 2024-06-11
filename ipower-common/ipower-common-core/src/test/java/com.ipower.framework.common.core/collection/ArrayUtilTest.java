package com.ipower.framework.common.core.collection;

import com.ipower.framework.common.core.entity.TestPerson;
import com.ipower.framework.common.core.entity.TestUser;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.lang.StringUtil;
import com.ipower.framework.common.core.lang.Validate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ArrayUtil Tester.
 *
 * @author huangad@coracle.com
 */
public class ArrayUtilTest {

    /**
     * 对象是否为数组对象
     * Method: isArray(Object obj)
     */
    @Test
    public void testIsArray() {
        Object object = new Object();
        assertFalse(ArrayUtil.isArray(object));
        Integer[] a = new Integer[0];
        assertTrue(ArrayUtil.isArray(a));
        int[][] b = new int[][]{{1, 3}, {2}};
        assertTrue(ArrayUtil.isArray(b));
        Object c = new String[0];
        assertTrue(ArrayUtil.isArray(c));
    }

    /**
     * 判断数组是否为null或是数组长度为0
     * Method: isEmpty(final T[] array)
     */
    @Test
    public void testIsEmpty() {
        //noinspection ConstantValue
        assertTrue(ArrayUtil.isEmpty(null));
        Integer[] a = new Integer[0];
        assertTrue(ArrayUtil.isEmpty(a));
        String[] b = new String[]{"1"};
        assertFalse(ArrayUtil.isEmpty(b));
        int[][] c = new int[][]{{1, 3}, {2}};
        assertFalse(ArrayUtil.isEmpty(c));
    }

    /**
     * 判断数组元素不少于1个
     * Method: isNotEmpty(final T[] array)
     */
    @Test
    public void testIsNotEmpty() {
        //noinspection ConstantValue
        assertFalse(ArrayUtil.isNotEmpty(null));
        Integer[] a = new Integer[0];
        assertFalse(ArrayUtil.isNotEmpty(a));
        String[] b = new String[]{"1"};
        assertTrue(ArrayUtil.isNotEmpty(b));
        Validate.isTrue(ArrayUtil.isNotEmpty(b));
        int[][] c = new int[][]{{1, 3}, {2}};
        assertTrue(ArrayUtil.isNotEmpty(c));
    }

    @Test
    public void testIsAllNull() {
        assertTrue(ArrayUtil.isAllNull((Object[]) null));
        assertTrue(ArrayUtil.isAllNull());
        assertTrue(ArrayUtil.isAllNull(null, null));
        assertFalse(ArrayUtil.isAllNull(null, ""));
        assertFalse(ArrayUtil.isAllNull("", new ArrayList<>(), new HashMap<>()));
        assertFalse(ArrayUtil.isAllNull("", new ArrayList<>(), new HashMap<>(), null));
        assertFalse(ArrayUtil.isAllNull("", "123", Lists.arrayList("abc")));
    }

    @Test
    public void testIsAllNotNull() {
        assertFalse(ArrayUtil.isAllNotNull());
        assertFalse(ArrayUtil.isAllNotNull(null, null));
        assertFalse(ArrayUtil.isAllNotNull(null, ""));
        assertTrue(ArrayUtil.isAllNotNull("", new ArrayList<>(), new HashMap<>()));
        assertFalse(ArrayUtil.isAllNotNull("", new ArrayList<>(), new HashMap<>(), null));
        assertTrue(ArrayUtil.isAllNotNull("", "123", Lists.arrayList("abc")));
    }

    @Test
    public void testHasNull() {
        assertTrue(ArrayUtil.hasNull());
        assertTrue(ArrayUtil.hasNull(null, null));
        assertTrue(ArrayUtil.hasNull(null, ""));
        assertFalse(ArrayUtil.hasNull("", new ArrayList<>(), new HashMap<>()));
        assertTrue(ArrayUtil.hasNull("", new ArrayList<>(), new HashMap<>(), null));
        assertFalse(ArrayUtil.hasNull("", "123", Lists.arrayList("abc")));
    }

    @Test
    public void testIsAllEmpty() {
        assertTrue(ArrayUtil.isAllEmpty((Object[]) null));
        assertTrue(ArrayUtil.isAllEmpty());
        assertTrue(ArrayUtil.isAllEmpty(null, null));
        assertTrue(ArrayUtil.isAllEmpty(null, ""));
        assertTrue(ArrayUtil.isAllEmpty("", new ArrayList<>(), new HashMap<>()));
        assertTrue(ArrayUtil.isAllEmpty("", new ArrayList<>(), new HashMap<>(), null));
        assertFalse(ArrayUtil.isAllEmpty("", "123", Lists.arrayList("abc"), null));
        assertFalse(ArrayUtil.isAllEmpty("", "123", Lists.arrayList("abc"), 123));
        assertFalse(ArrayUtil.isAllEmpty(3.14, "123", Lists.arrayList("abc"), 123));
    }

    @Test
    public void testIsAllNotEmpty() {
        assertFalse(ArrayUtil.isAllNotEmpty());
        assertFalse(ArrayUtil.isAllNotEmpty(null, null));
        assertFalse(ArrayUtil.isAllNotEmpty(null, ""));
        assertFalse(ArrayUtil.isAllNotEmpty("", new ArrayList<>(), new HashMap<>()));
        assertFalse(ArrayUtil.isAllNotEmpty("", new ArrayList<>(), new HashMap<>(), null));
        assertFalse(ArrayUtil.isAllNotEmpty("", "123", Lists.arrayList("abc"), null));
        assertFalse(ArrayUtil.isAllNotEmpty("", "123", Lists.arrayList("abc"), 123));
        assertTrue(ArrayUtil.isAllNotEmpty(3.14, "123", Lists.arrayList("abc"), 123));
    }

    @Test
    public void testHasEmpty() {
        assertTrue(ArrayUtil.hasEmpty());
        assertTrue(ArrayUtil.hasEmpty(null, null));
        assertTrue(ArrayUtil.hasEmpty(null, ""));
        assertTrue(ArrayUtil.hasEmpty("", new ArrayList<>(), new HashMap<>()));
        assertTrue(ArrayUtil.hasEmpty("", new ArrayList<>(), new HashMap<>(), null));
        assertTrue(ArrayUtil.hasEmpty("", "123", Lists.arrayList("abc"), null));
        assertTrue(ArrayUtil.hasEmpty("", "123", Lists.arrayList("abc"), 123));
        assertFalse(ArrayUtil.hasEmpty(3.14, "123", Lists.arrayList("abc"), 123));
    }

    /**
     * 数组的长度
     * Method: length(Object array)
     */
    @Test
    public void testLength() {
        Long[] a = new Long[0];
        Integer[] b = null;
        String[] c = new String[2];
        Double[] d = new Double[]{1d, 2d};
        Integer[] e = new Integer[]{1, 2, 3};
        assertEquals(0, ArrayUtil.length(a));
        //noinspection ConstantValue
        assertEquals(0, ArrayUtil.length(b));
        assertEquals(2, ArrayUtil.length(c));
        assertEquals(2, ArrayUtil.length(d));
        assertEquals(3, ArrayUtil.length(e));
    }

    @Test
    public void testGetComponentType() {
        Long[] a = new Long[0];
        assertEquals(Long.class, ArrayUtil.getComponentType(a));
        Integer[] b = null;
        //noinspection ConstantValue
        assertNull(ArrayUtil.getComponentType(b));
        String[] c = new String[2];
        assertEquals(String.class, ArrayUtil.getComponentType(c));
        Double[] d = new Double[]{1d, 2d};
        assertEquals(Double.class, ArrayUtil.getComponentType(d));
        Integer[] e = new Integer[]{1, 2, 3};
        assertEquals(Integer.class, ArrayUtil.getComponentType(e));
        Object[] f = new Object[]{1, null, "a"};
        assertEquals(Object.class, ArrayUtil.getComponentType(f));
        Object[] g = new String[]{"1", null, "a"};
        assertEquals(String.class, ArrayUtil.getComponentType(g));
    }

    /**
     * 新建一个空数组
     * Method: newArray(Class<?> componentType, int newSize)
     */
    @Test
    public void testNewArrayForComponentTypeNewSize() {
        //noinspection DataFlowIssue
        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtil.newArray(null, 5));
        Integer[] integers = ArrayUtil.newArray(Integer.class, 5);
        assertEquals(5, integers.length);
        String[] strings = ArrayUtil.newArray(String.class, 2);
        assertEquals(2, ArrayUtil.length(strings));
        Long[] longs = ArrayUtil.newArray(Long.class, 0);
        assertTrue(ArrayUtil.isEmpty(longs));
        Double[] doubles = ArrayUtil.newArray(Double.class);
        assertTrue(ArrayUtil.isEmpty(doubles));
    }

    /**
     * 获取数组对象中指定index的值，支持负数，例如-1表示倒数第一个值
     * Method: get(Object array, int index)
     */
    @Test
    public void testGet() {
        assertNull(ArrayUtil.get(null, 1));
        assertNull(ArrayUtil.get(new Object[]{}, 1));
        Integer[] array = new Integer[]{4, 5, 8, 9};
        assertEquals(5, ArrayUtil.get(array, 1));
        assertNull(ArrayUtil.get(array, 4));
        assertNull(ArrayUtil.get(array, 5));
        assertNull(ArrayUtil.get(array, -1));
        assertNull(ArrayUtil.get(array, -4));

        assertEquals("[]", ArrayUtil.toString(ArrayUtil.get(new Integer[]{1, 2, 3, 4})));
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.get(new Integer[]{1, 2, 3, 4}, (int[]) null)));
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.get(new Integer[]{})));
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.get(new Integer[]{}, (int[]) null)));
        assertEquals("[null, null, null]", ArrayUtil.toString(ArrayUtil.get(new Integer[]{}, 0, 1, -1)));
        assertNull(ArrayUtil.get(null));
        assertNull(ArrayUtil.get(null, (int[]) null));
        assertNull(ArrayUtil.get(null, 0, 1, -1));
        assertEquals("[1, 2, null]", ArrayUtil.toString(ArrayUtil.get(new Integer[]{1, 2, 3, 4}, 0, 1, -1)));
        assertEquals("[null, null, null, 1, 2, null, null]", ArrayUtil.toString(ArrayUtil.get(new Integer[]{1, 2, 3, 4}, -5, -4, -1, 0, 1, 4, 5)));
        assertEquals("[false, true]", ArrayUtil.toString(ArrayUtil.get(new Boolean[]{false, true, false}, 0, 1)));
    }

    /**
     * 反转数组，会变更原数组
     * Method: reverse(final T[] array)
     */
    @Test
    public void testReverseArray() {
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        TestUser testUser3 = new TestUser();
        testUser1.setAge(1);
        testUser2.setAge(2);
        testUser3.setAge(3);
        TestUser[] reverseTestUser = ArrayUtil.reverse(new TestUser[]{testUser1, testUser2, testUser3});
        String str = "[TestUser(name=null, age=3, studentId=null, id=null), " +
                "TestUser(name=null, age=2, studentId=null, id=null), " +
                "TestUser(name=null, age=1, studentId=null, id=null)]";
        assertEquals(str, ArrayUtil.toString(reverseTestUser));

        String[] array = {"a", "b", "c", "d"};
        assertEquals("[d, c, b, a]", ArrayUtil.toString(ArrayUtil.reverse(array)));

        Integer[] integers = {9, 5, 2, 7};
        assertEquals("[7, 2, 5, 9]", ArrayUtil.toString(ArrayUtil.reverse(integers)));
        assertNull(ArrayUtil.reverse(null));
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.reverse(new Object[]{})));
    }

    /**
     * 取最小值
     * Method: min(T[] numberArray)
     */
    @Test
    public void testMinNumberArray() {
        TestPerson testPerson1 = new TestPerson(23, "aaa");
        TestPerson testPerson2 = new TestPerson(22, "bbb");
        TestPerson testPerson3 = new TestPerson(33, "ccc");
        TestPerson[] testPeople = new TestPerson[]{testPerson1, testPerson2, testPerson3};
        assertEquals(testPerson2, ArrayUtil.min(testPeople));
        assertEquals(testPerson1, ArrayUtil.min(testPeople, Comparator.comparing(TestPerson::getName)));

        String[] array = {"a", "b", "c", "d"};
        assertEquals("a", ArrayUtil.min(array));

        Integer[] integers = {9, 5, 2, 7};
        assertEquals(2, ArrayUtil.min(integers));

        //noinspection DataFlowIssue
        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtil.min(null));
        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtil.min(new Long[]{}));

    }

    /**
     * Method: max(T[] numberArray)
     */
    @Test
    public void testMaxNumberArray() {
        TestPerson testPerson1 = new TestPerson(23, "aaa");
        TestPerson testPerson2 = new TestPerson(22, "bbb");
        TestPerson testPerson3 = new TestPerson(33, "ccc");
        TestPerson[] testPeople = new TestPerson[]{testPerson1, testPerson2, testPerson3};
        assertEquals(testPerson3, ArrayUtil.max(testPeople));
        assertEquals(testPerson3, ArrayUtil.max(testPeople, Comparator.comparing(TestPerson::getName)));

        String[] array = {"a", "b", "c", "d"};
        assertEquals("d", ArrayUtil.max(array));

        Integer[] integers = {9, 5, 2, 7};
        assertEquals(9, ArrayUtil.max(integers));

        //noinspection DataFlowIssue
        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtil.max(null));
        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtil.max(new Long[]{}));
    }

    /**
     * 去重数组中的元素，去重后生成新的数组，原数组不变
     * Method: distinct(T[] array)
     */
    @Test
    public void testDistinct() {
        Integer[] arr = new Integer[]{5, 6, 1, 2, 3, 3, 2, 5};
        assertEquals("[5, 6, 1, 2, 3]", ArrayUtil.toString(ArrayUtil.distinct(arr)));

        String[] array = {"a", "b", null, "d", null, "c", "c", "b", null};
        assertEquals("[a, b, null, d, c]", ArrayUtil.toString(ArrayUtil.distinct(array)));

        assertEquals("[]", ArrayUtil.toString(ArrayUtil.distinct(new Object[]{})));
        assertNull(ArrayUtil.distinct(null));
    }

    /**
     * 将新元素添加到已有数组中
     * 添加新元素会生成一个新的数组，不影响原数组
     * Method: add(T[] buffer, T... newElements)
     */
    @Test
    public void testAddForBufferNewElements() {
        Integer[] array = new Integer[]{1, 3, 5, 7};
        Integer[] elements = new Integer[]{9, 11};
        assertNull(ArrayUtil.add(null, (Integer[]) null));
        assertNull(ArrayUtil.add(null));
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.add(new Integer[]{})));
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.add(new Integer[]{}, (Integer[]) null)));
        assertEquals("[1, 3, 5, 7]", ArrayUtil.toString(ArrayUtil.add(array)));
        assertEquals("[1, 3, 5, 7]", ArrayUtil.toString(ArrayUtil.add(array, (Integer[]) null)));
        assertEquals("[9, 11]", ArrayUtil.toString(ArrayUtil.add(null, elements)));
        assertEquals("[9, 11]", ArrayUtil.toString(ArrayUtil.add(new Integer[]{}, elements)));
        assertEquals("[1, 3, 5, 7, 9, 11]", ArrayUtil.toString(ArrayUtil.add(array, elements)));

        String[] strings = {"a", "b", "c"};
        assertEquals("[a, b, c, d, e, f]", ArrayUtil.toString(ArrayUtil.add(strings, "d", "e", "f")));
        assertEquals("[a, b, c, null, d, e, , null, f]", ArrayUtil.toString(ArrayUtil.add(strings, null, "d", "e", "", null, "f")));

    }

    /**
     * 将新元素插入到到已有数组中的某个位置
     * Method: insert(T[] buffer, int index, T... newElements)
     */
    @Test
    public void testInsertForBufferIndexNewElements() {
        Integer[] array = new Integer[]{1, 3, 5, 7};
        Integer[] elements = new Integer[]{9, 11};
        assertEquals("[9, 11, 1, 3, 5, 7]", ArrayUtil.toString(ArrayUtil.insert(array, -2, elements)));
        assertEquals("[9, 11, 1, 3, 5, 7]", ArrayUtil.toString(ArrayUtil.insert(array, -1, elements)));
        assertEquals("[9, 11, 1, 3, 5, 7]", ArrayUtil.toString(ArrayUtil.insert(array, 0, elements)));
        assertEquals("[1, 9, 11, 3, 5, 7]", ArrayUtil.toString(ArrayUtil.insert(array, 1, elements)));
        assertEquals("[1, 3, 9, 11, 5, 7]", ArrayUtil.toString(ArrayUtil.insert(array, 2, elements)));
        assertEquals("[1, 3, 5, 9, 11, 7]", ArrayUtil.toString(ArrayUtil.insert(array, 3, elements)));
        assertEquals("[1, 3, 5, 7, 9, 11]", ArrayUtil.toString(ArrayUtil.insert(array, 4, elements)));
        assertEquals("[1, 3, 5, 7, null, 9, 11]", ArrayUtil.toString(ArrayUtil.insert(array, 5, elements)));
        assertEquals("[1, 3, 5, 7, null, null, 9, 11]", ArrayUtil.toString(ArrayUtil.insert(array, 6, elements)));

        assertNull(ArrayUtil.insert(null, 6, (Integer[]) null));
        assertNull(ArrayUtil.insert(null, 6));
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.insert(new Integer[]{}, 6)));
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.insert(new Integer[]{}, 6, (Integer[]) null)));
        assertEquals("[1, 3, 5, 7]", ArrayUtil.toString(ArrayUtil.insert(array, 6)));
        assertEquals("[1, 3, 5, 7]", ArrayUtil.toString(ArrayUtil.insert(array, 6, (Integer[]) null)));
        assertEquals("[9, 11]", ArrayUtil.toString(ArrayUtil.insert(null, 6, elements)));
        assertEquals("[9, 11]", ArrayUtil.toString(ArrayUtil.insert(new Integer[]{}, 6, elements)));
    }

    /**
     * 过滤
     * Method: filter(T[] array, Filter<T> filter)
     */
    @Test
    public void testFilterForArrayFilter() {
        Float[] floats = new Float[]{4f, 5f, 7f, 10f};
        assertEquals("[4.0, 5.0, 7.0]", ArrayUtil.toString(ArrayUtil.filter(floats, f -> f < 10f)));

        String[] a = {"a", "b", "c", null, "d", "c", "cc"};
        assertArrayEquals(new String[]{"a", "c", "c",}, ArrayUtil.filter(a, s -> StringUtil.equals(s, "a") || StringUtil.equals(s, "c")));
        assertArrayEquals(new String[]{"a", "b", "c", null, "d", "c", "cc"}, ArrayUtil.filter(a, null));
        assertArrayEquals(new String[]{"cc"}, ArrayUtil.filter(a, s -> StringUtil.length(s) > 1));
        assertArrayEquals(new String[]{"a", "b", "c", "d", "c", "cc"}, ArrayUtil.filter(a, ObjectUtil::isNotNull));
        assertNull(ArrayUtil.filter(null, ObjectUtil::isNotNull));
        assertNull(ArrayUtil.filter(null, null));
    }

    /**
     * 以 conjunction 为分隔符将数组转换为字符串
     * Method: join(T[] array, CharSequence conjunction)
     */
    @Test
    public void testJoinForArrayConjunction() {
        CharSequence charSequence = ".";
        Object[] objects = new Object[]{1, 2, 5};
        assertEquals("1.2.5", ArrayUtil.join(objects, charSequence));

        Long[] longArray = new Long[]{1L, 2L, 3L, null, 5L};
        assertEquals("1,2,3,null,5", ArrayUtil.join(longArray, ","));

        assertEquals("123null5", ArrayUtil.join(longArray, ""));
        assertEquals("123null5", ArrayUtil.join(longArray, null));
        assertEquals("", ArrayUtil.join(null, null));
        assertEquals("", ArrayUtil.join(null, ","));
    }

    /**
     * 判断对象数组是否存在某个对象
     * Method: contains(Object[] array, Object obj)
     */
    @Test
    public void testContains() {
        assertFalse(ArrayUtil.contains(null, null));
        assertFalse(ArrayUtil.contains(null, "a"));
        assertFalse(ArrayUtil.contains(new String[]{"a", "b", "c"}, null));

        assertFalse(ArrayUtil.contains(new String[]{}, ""));
        assertFalse(ArrayUtil.contains(new String[]{}, "a"));
        assertFalse(ArrayUtil.contains(new String[]{"a", "b", "c"}, ""));

        assertTrue(ArrayUtil.contains(new String[]{"a", "b", "c"}, "a"));
        assertTrue(ArrayUtil.contains(new String[]{"a", "b", "c"}, "c"));
        assertFalse(ArrayUtil.contains(new String[]{"a", "b", "c"}, "d"));

        Integer[] integers = {1, 2, 3};
        assertTrue(ArrayUtil.contains(integers, 1));
        assertTrue(ArrayUtil.contains(integers, 2));
        assertFalse(ArrayUtil.contains(integers, 3L));
        assertFalse(ArrayUtil.contains(integers, 4));

        BigDecimal[] decimals = {new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0")};
        assertFalse(ArrayUtil.contains(decimals, new BigDecimal(1)));
        assertFalse(ArrayUtil.contains(decimals, new BigDecimal("1")));
        //noinspection UnpredictableBigDecimalConstructorCall
        assertFalse(ArrayUtil.contains(decimals, new BigDecimal(1.0f)));
        //noinspection UnpredictableBigDecimalConstructorCall
        assertFalse(ArrayUtil.contains(decimals, new BigDecimal(1.0d)));
        assertFalse(ArrayUtil.contains(decimals, new BigDecimal("1.00")));

        assertTrue(ArrayUtil.contains(decimals, new BigDecimal("1.0")));
        assertTrue(ArrayUtil.contains(decimals, new BigDecimal("2.0")));
        assertTrue(ArrayUtil.contains(decimals, new BigDecimal("3.0")));
        assertFalse(ArrayUtil.contains(decimals, new BigDecimal("4.0")));

        TestUser testUser1 = new TestUser("张三");
        TestUser testUser2 = new TestUser("李四");
        TestUser testUser3 = new TestUser("王五");
        TestUser[] testUsers = new TestUser[]{testUser1, testUser2};
        assertTrue(ArrayUtil.contains(testUsers, testUser1));
        assertFalse(ArrayUtil.contains(testUsers, testUser3));
    }

    /**
     * 判断对象数组是否存在某个对象
     * Method: containsAny(Object[] array, Object... obj)
     */
    @Test
    public void testContainsAny() {
        assertFalse(ArrayUtil.containsAny(null, (Object[]) null));
        assertFalse(ArrayUtil.containsAny(null, "a", "d"));
        assertFalse(ArrayUtil.containsAny(new String[]{"a", "b", "c"}, (String[]) null));

        assertFalse(ArrayUtil.containsAny(new String[]{}, ""));
        assertFalse(ArrayUtil.containsAny(new String[]{}, "a", "d"));
        assertFalse(ArrayUtil.containsAny(new String[]{"a", "b", "c"}, ""));

        assertTrue(ArrayUtil.containsAny(new String[]{"a", "b", "c"}, "a", "b"));
        assertTrue(ArrayUtil.containsAny(new String[]{"a", "b", "c"}, "c", "d"));
        assertFalse(ArrayUtil.containsAny(new String[]{"a", "b", "c"}, "d", "e"));

        Integer[] integers = {1, 2, 3};
        assertTrue(ArrayUtil.containsAny(integers, 1, 2));
        assertTrue(ArrayUtil.containsAny(integers, 2, 3));
        assertTrue(ArrayUtil.containsAny(integers, 3L, 2));
        assertFalse(ArrayUtil.containsAny(integers, 3L, 2L));
        assertFalse(ArrayUtil.containsAny(integers, 4, 5));

        BigDecimal[] decimals = {new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0")};
        assertFalse(ArrayUtil.containsAny(decimals, new BigDecimal(1), new BigDecimal(2)));
        assertFalse(ArrayUtil.containsAny(decimals, new BigDecimal("1"), new BigDecimal("2")));
        //noinspection UnpredictableBigDecimalConstructorCall
        assertFalse(ArrayUtil.containsAny(decimals, new BigDecimal(1.0f), new BigDecimal(2.0f)));
        //noinspection UnpredictableBigDecimalConstructorCall
        assertFalse(ArrayUtil.containsAny(decimals, new BigDecimal(1.0d), new BigDecimal(2.0d)));
        assertFalse(ArrayUtil.containsAny(decimals, new BigDecimal("1.00"), new BigDecimal("2.00")));

        assertTrue(ArrayUtil.containsAny(decimals, new BigDecimal("1.0"), new BigDecimal("2.0")));
        assertTrue(ArrayUtil.containsAny(decimals, new BigDecimal("2.0"), new BigDecimal("3.0")));
        assertTrue(ArrayUtil.containsAny(decimals, new BigDecimal("3.0"), new BigDecimal("4.0")));
        assertFalse(ArrayUtil.containsAny(decimals, new BigDecimal("4.0"), new BigDecimal("5.0")));

        TestUser testUser1 = new TestUser("张三");
        TestUser testUser2 = new TestUser("李四");
        TestUser testUser3 = new TestUser("王五");
        TestUser testUser4 = new TestUser("马六");
        TestUser[] testUsers = new TestUser[]{testUser1, testUser2};
        assertTrue(ArrayUtil.containsAny(testUsers, testUser1, testUser2));
        assertTrue(ArrayUtil.containsAny(testUsers, testUser2, testUser3));
        assertFalse(ArrayUtil.containsAny(testUsers, testUser3, testUser4));
    }

    /**
     * 判断对象数组是否存在某个对象
     * Method: containsAll(Object[] array, Object... obj)
     */
    @Test
    public void testContainsAll() {
        assertFalse(ArrayUtil.containsAll(null, (Object[]) null));
        assertFalse(ArrayUtil.containsAll(null));
        assertFalse(ArrayUtil.containsAll(null, "a", "d"));
        assertFalse(ArrayUtil.containsAll(new String[]{"a", "b", "c"}, (String[]) null));

        assertFalse(ArrayUtil.containsAll(new String[]{}, ""));
        assertFalse(ArrayUtil.containsAll(new String[]{}, "a", "d"));
        assertFalse(ArrayUtil.containsAll(new String[]{"a", "b", "c"}, ""));

        assertTrue(ArrayUtil.containsAll(new String[]{"a", "b", "c"}, "a", "b"));
        assertFalse(ArrayUtil.containsAll(new String[]{"a", "b", "c"}, "c", "d"));
        assertFalse(ArrayUtil.containsAll(new String[]{"a", "b", "c"}, "d", "e"));

        Integer[] integers = {1, 2, 3};
        assertTrue(ArrayUtil.containsAll(integers, 1, 2));
        assertTrue(ArrayUtil.containsAll(integers, 2, 3));
        assertFalse(ArrayUtil.containsAll(integers, 3L, 2));
        assertFalse(ArrayUtil.containsAll(integers, 3L, 2L));
        assertFalse(ArrayUtil.containsAll(integers, 4, 5));

        BigDecimal[] decimals = {new BigDecimal("1.0"), new BigDecimal("2.0"), new BigDecimal("3.0")};
        assertFalse(ArrayUtil.containsAll(decimals, new BigDecimal(1), new BigDecimal(2)));
        assertFalse(ArrayUtil.containsAll(decimals, new BigDecimal("1"), new BigDecimal("2")));
        //noinspection UnpredictableBigDecimalConstructorCall
        assertFalse(ArrayUtil.containsAll(decimals, new BigDecimal(1.0f), new BigDecimal(2.0f)));
        //noinspection UnpredictableBigDecimalConstructorCall
        assertFalse(ArrayUtil.containsAll(decimals, new BigDecimal(1.0d), new BigDecimal(2.0d)));
        assertFalse(ArrayUtil.containsAll(decimals, new BigDecimal("1.00"), new BigDecimal("2.00")));

        assertTrue(ArrayUtil.containsAll(decimals, new BigDecimal("1.0"), new BigDecimal("2.0")));
        assertTrue(ArrayUtil.containsAll(decimals, new BigDecimal("2.0"), new BigDecimal("3.0")));
        assertFalse(ArrayUtil.containsAll(decimals, new BigDecimal("3.0"), new BigDecimal("4.0")));
        assertFalse(ArrayUtil.containsAll(decimals, new BigDecimal("4.0"), new BigDecimal("5.0")));

        TestUser testUser1 = new TestUser("张三");
        TestUser testUser2 = new TestUser("李四");
        TestUser testUser3 = new TestUser("王五");
        TestUser testUser4 = new TestUser("马六");
        TestUser[] testUsers = new TestUser[]{testUser1, testUser2};
        assertTrue(ArrayUtil.containsAll(testUsers, testUser1, testUser2));
        assertFalse(ArrayUtil.containsAll(testUsers, testUser2, testUser3));
        assertFalse(ArrayUtil.containsAll(testUsers, testUser3, testUser4));
    }

    @Test
    public void remove() {
        String[] a = {"a", "b", "c", null, "d", "c", "cc"};
        assertArrayEquals(new String[]{"a", "b", null, "d", "cc"}, ArrayUtil.remove(a, "c"));
        assertArrayEquals(new String[]{"a", "b", "c", "d", "c", "cc"}, ArrayUtil.remove(a, (String) null));
        assertNull(ArrayUtil.remove(null, "c"));
        assertNull(ArrayUtil.remove(null, (String) null));

        Integer[] b = {5, 1, 2, 3, 1, 2, 1, 4, 7};
        assertArrayEquals(new Integer[]{5, 2, 3, 2, 4, 7}, ArrayUtil.remove(b, 1));
    }

    @Test
    public void removePredicate() {
        String[] a = {"a", "b", "c", null, "d", "c", "cc"};
        assertArrayEquals(new String[]{"b", null, "d", "cc"}, ArrayUtil.remove(a, s -> StringUtil.equals(s, "a") || StringUtil.equals(s, "c")));
        assertArrayEquals(new String[]{"a", "b", "c", null, "d", "c", "cc"}, ArrayUtil.remove(a, null));
        assertArrayEquals(new String[]{"a", "b", "c", null, "d", "c"}, ArrayUtil.remove(a, s -> StringUtil.length(s) > 1));
        assertArrayEquals(new String[]{"a", "b", "c", "d", "c", "cc"}, ArrayUtil.remove(a, ObjectUtil::isNull));
        assertNull(ArrayUtil.remove(null, ObjectUtil::isNull));
        assertNull(ArrayUtil.remove(null, null));

        Integer[] b = {5, 1, 2, 3, 1, 2, 1, 4, 7};
        assertArrayEquals(new Integer[]{1, 2, 1, 2, 1}, ArrayUtil.remove(b, i -> i > 2));
    }

    @Test
    public void removeAllTest() {
        String[] a = {"a", "b", "c", null, "d", "c"};
        assertArrayEquals(new String[]{"b", null, "d"}, ArrayUtil.removeAll(a, "a", "c"));
        assertArrayEquals(new String[]{"a", "b", "c", null, "d", "c"}, ArrayUtil.removeAll(a));
        assertArrayEquals(new String[]{"a", "b", "c", null, "d", "c"}, ArrayUtil.removeAll(a, (String[]) null));
        assertArrayEquals(new String[]{"a", "b", "c", "d", "c"}, ArrayUtil.removeAll(a, (String) null));
        assertNull(ArrayUtil.removeAll(null));
        assertNull(ArrayUtil.removeAll(null, (String[]) null));
        assertNull(ArrayUtil.removeAll(null, (String) null));
        assertNull(ArrayUtil.removeAll(null, "a", "c"));

        Integer[] b = {5, 1, 2, 3, null, 2, 1, 4, null};
        assertArrayEquals(new Integer[]{5, 2, null, 2, 4, null}, ArrayUtil.removeAll(b, 1, 3));
    }

    @Test
    public void removeIndex() {
        String[] a = {"a", "b", "c", null, "d", "c", "cc"};
        assertArrayEquals(new String[]{"a", "b", "c", null, "d", "c", "cc"}, ArrayUtil.removeIndex(a, -2));
        assertArrayEquals(new String[]{"a", "b", "c", null, "d", "c", "cc"}, ArrayUtil.removeIndex(a, -1));
        assertArrayEquals(new String[]{"b", "c", null, "d", "c", "cc"}, ArrayUtil.removeIndex(a, 0));
        assertArrayEquals(new String[]{"a", "c", null, "d", "c", "cc"}, ArrayUtil.removeIndex(a, 1));
        assertArrayEquals(new String[]{"a", "b", null, "d", "c", "cc"}, ArrayUtil.removeIndex(a, 2));
        assertArrayEquals(new String[]{"a", "b", "c", "d", "c", "cc"}, ArrayUtil.removeIndex(a, 3));
        assertArrayEquals(new String[]{"a", "b", "c", null, "c", "cc"}, ArrayUtil.removeIndex(a, 4));
        assertArrayEquals(new String[]{"a", "b", "c", null, "d", "cc"}, ArrayUtil.removeIndex(a, 5));
        assertArrayEquals(new String[]{"a", "b", "c", null, "d", "c"}, ArrayUtil.removeIndex(a, 6));
        assertArrayEquals(new String[]{"a", "b", "c", null, "d", "c", "cc"}, ArrayUtil.removeIndex(a, 7));
        assertArrayEquals(new String[]{"a", "b", "c", null, "d", "c", "cc"}, ArrayUtil.removeIndex(a, 8));
        assertNull(ArrayUtil.remove(null, -1));
        assertNull(ArrayUtil.remove(null, 1));

        Integer[] b = {5, 1, 2, 3, 1, 2, 1, 4, 7};
        assertArrayEquals(new Integer[]{5, 2, 3, 1, 2, 1, 4, 7}, ArrayUtil.removeIndex(b, 1));
    }

    @Test
    public void removeNullTest() {
        String[] a = {"a", "b", "", null, " ", "c"};
        assertArrayEquals(new String[]{"a", "b", "", " ", "c"}, ArrayUtil.removeNull(a));
        assertNull(ArrayUtil.removeNull(null));

        Integer[] b = {null, 1, 2, 3, null, 2, 1, 4, null};
        assertArrayEquals(new Integer[]{1, 2, 3, 2, 1, 4}, ArrayUtil.removeNull(b));
    }

    @Test
    public void removeEmptyTest() {
        String[] a = {"a", "b", "", null, " ", "c"};
        assertArrayEquals(new String[]{"a", "b", " ", "c"}, ArrayUtil.removeEmpty(a));
        assertNull(ArrayUtil.removeEmpty(null));
    }

    @Test
    public void removeBlankTest() {
        String[] a = {"a", "b", "", null, " ", "c"};
        assertArrayEquals(new String[]{"a", "b", "c"}, ArrayUtil.removeBlank(a));
        assertNull(ArrayUtil.removeBlank(null));
    }

    @Test
    public void toArrayTest() {
        List<String> list = Lists.arrayList("A", "B", "C", "D");
        assertEquals("[A, B, C, D]", ArrayUtil.toString(ArrayUtil.toArray(list, String.class)));
        assertEquals("[A, B, C, D]", ArrayUtil.toString(ArrayUtil.toArray(list.iterator(), String.class)));
        Iterable<Integer> integers = Lists.linkedHashSet(2, 3, 1, 9, 1, 3, 5);
        assertEquals("[2, 3, 1, 9, 5]", ArrayUtil.toString(ArrayUtil.toArray(integers, Integer.class)));
        //noinspection DataFlowIssue
        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtil.toArray(list, null));

        Iterator<String> iterator = null;
        //noinspection ConstantValue
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.toArray(iterator, String.class)));
        Iterable<Long> iterable = null;
        //noinspection ConstantValue
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.toArray(iterable, Long.class)));
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.toArray((Collection<Long>) null, Long.class)));

        List<String> stringList = Lists.arrayList();
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.toArray(stringList, String.class)));
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.toArray(stringList.iterator(), String.class)));
        Iterable<Integer> integerIterable = Lists.hashSet();
        assertEquals("[]", ArrayUtil.toString(ArrayUtil.toArray(integerIterable, Integer.class)));


        String[] array = {"1", "2", "3"};
        //noinspection DataFlowIssue
        assertThrowsExactly(IllegalArgumentException.class, () -> ArrayUtil.toArray(array, null));
        Integer[] castArray = ArrayUtil.toArray(array, Integer.class);
        assertEquals(1, castArray[0]);
        assertEquals(2, castArray[1]);
        assertEquals(3, castArray[2]);

        Integer[] integerArray = {1, 2, 3};
        String[] castIntegerArray = ArrayUtil.toArray(integerArray, String.class);
        assertEquals("1", castIntegerArray[0]);
        assertEquals("2", castIntegerArray[1]);
        assertEquals("3", castIntegerArray[2]);
    }

    @Test
    public void cloneTest() {
        Integer[] b = {1, 2, 3};
        assertArrayEquals(b, ArrayUtil.clone(b));
        String[] c = {"a", "b", "c"};
        assertArrayEquals(c, ArrayUtil.clone(c));
        assertNull(ArrayUtil.clone(null));
        assertNull(ArrayUtil.clone("a"));
        List<TestUser> list = Lists.arrayList(new TestUser("zs"), new TestUser("ls"));
        assertNull(ArrayUtil.clone(list));
        TestUser[] cloneList = ArrayUtil.clone(ArrayUtil.toArray(list, TestUser.class));
        assertArrayEquals(list.toArray(), cloneList);
        int[] a = {1, 2, 3};
        assertArrayEquals(a, ArrayUtil.clone(a));
        Object obj = new String[]{"a", "b", "c"};
        String[] d = (String[]) ArrayUtil.clone(obj);
        assertArrayEquals(new String[]{"a", "b", "c"}, d);
    }

    /**
     * 数组或集合转String
     * Method: toString(Object obj)
     */
    @Test
    public void testToString() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(333);
        assertEquals("[1, 333]", ArrayUtil.toString(list.toArray(new Integer[0])));
        assertEquals("null", ArrayUtil.toString(null));
        assertEquals("[]", ArrayUtil.toString(new Object[]{}));
        assertEquals("[]", ArrayUtil.toString(new Object[0]));

        Integer[][] integers = {{1, 2}, {3, 4}};
        assertEquals("[[1, 2], [3, 4]]", ArrayUtil.toString(integers));
    }

}
