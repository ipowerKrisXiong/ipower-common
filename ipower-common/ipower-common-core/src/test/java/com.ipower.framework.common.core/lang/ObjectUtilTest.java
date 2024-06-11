package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.collection.ArrayUtil;
import com.ipower.framework.common.core.collection.Lists;
import com.ipower.framework.common.core.entity.TestUser;
import com.ipower.framework.common.core.map.Maps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;


/**
 * ObjectUtil Tester.
 *
 * @author diablo
 */
public class ObjectUtilTest {

    @SuppressWarnings("ConstantValue")
    @Test
    public void testIsNull() {
        String str = null;
        assertTrue(ObjectUtil.isNull(null));
        assertTrue(ObjectUtil.isNull(str));
        assertFalse(ObjectUtil.isNull(""));
        assertFalse(ObjectUtil.isNull(new ArrayList<>()));
        assertFalse(ObjectUtil.isNull(new HashMap<>()));
    }

    @SuppressWarnings("ConstantValue")
    @Test
    public void testIsNotNull() {
        String str = null;
        assertFalse(ObjectUtil.isNotNull(null));
        assertFalse(ObjectUtil.isNotNull(str));
        assertTrue(ObjectUtil.isNotNull(""));
        assertTrue(ObjectUtil.isNotNull(new ArrayList<>()));
        assertTrue(ObjectUtil.isNotNull(new HashMap<>()));
    }

    /**
     * Method: isEmpty(Object obj)
     */
    @Test
    public void testIsEmpty() {
        //---------------------------字符串测试------------------------------------
        Object obj = null;
        //noinspection ConstantValue
        assertTrue(ObjectUtil.isEmpty(obj));
        //空字符串
        String str = "";
        assertTrue(ObjectUtil.isEmpty(str));
        //空格
        str = " ";
        assertFalse(ObjectUtil.isEmpty(str));
        //多个tab
        str = "         ";
        assertFalse(ObjectUtil.isEmpty(str));
        str = "abc";
        assertFalse(ObjectUtil.isEmpty(str));

        StringBuilder sb = new StringBuilder();
        assertTrue(ObjectUtil.isEmpty(sb));
        sb.append("  ");
        assertFalse(ObjectUtil.isEmpty(sb));
        sb.append("aa");
        assertFalse(ObjectUtil.isEmpty(sb));

        //---------------------------集合测试------------------------------------
        List<Integer> list = new ArrayList<>();
        assertTrue(ObjectUtil.isEmpty(list));
        list.add(1);
        assertFalse(ObjectUtil.isEmpty(list));

        //---------------------------map测试------------------------------------
        Map<String, String> map = new HashMap<>();
        assertTrue(ObjectUtil.isEmpty(map));
        map.put("key", "value");
        assertFalse(ObjectUtil.isEmpty(map));

        //---------------------------数组测试------------------------------------
        String[] arr = {};
        assertTrue(ObjectUtil.isEmpty(arr));
        arr = new String[1];
        assertFalse(ObjectUtil.isEmpty(arr));
        assertTrue(ObjectUtil.isEmpty(arr[0]));

        //---------------------------迭代器测试------------------------------------
        Set<String> set = new HashSet<>();
        @SuppressWarnings("RedundantOperationOnEmptyContainer") Iterator<String> it = set.iterator();
        assertTrue(ObjectUtil.isEmpty(it));
        //增加元素
        set.add("aa");
        Iterator<String> it2 = set.iterator();
        assertFalse(ObjectUtil.isEmpty(it2));

        //---------------------------其他测试------------------------------------
        TestUser testUser = new TestUser();
        assertFalse(ObjectUtil.isEmpty(testUser));
        assertTrue(ObjectUtil.isEmpty(testUser.getName()));
    }

    /**
     * Method: isNotEmpty(Object obj)
     */
    @SuppressWarnings("ConstantValue")
    @Test
    public void testIsNotEmpty() {
        //---------------------------字符串测试------------------------------------
        String str = null;
        assertFalse(ObjectUtil.isNotEmpty(str));
        //空字符串
        str = "";
        assertFalse(ObjectUtil.isNotEmpty(str));
        //空格
        str = " ";
        assertTrue(ObjectUtil.isNotEmpty(str));
        //多个tab
        str = "         ";
        assertTrue(ObjectUtil.isNotEmpty(str));
        StringBuilder sb = null;
        assertFalse(ObjectUtil.isNotEmpty(sb));
        sb = new StringBuilder();
        assertFalse(ObjectUtil.isNotEmpty(sb));

        //---------------------------集合测试------------------------------------
        List<Integer> list = null;
        assertFalse(ObjectUtil.isNotEmpty(list));
        list = new ArrayList<>();
        assertFalse(ObjectUtil.isNotEmpty(list));
        list.add(1);
        assertTrue(ObjectUtil.isNotEmpty(list));

        //---------------------------map测试------------------------------------
        Map<String, String> map = null;
        assertFalse(ObjectUtil.isNotEmpty(map));
        map = new HashMap<>();
        assertFalse(ObjectUtil.isNotEmpty(map));
        map.put("key", "value");
        assertTrue(ObjectUtil.isNotEmpty(map));


        //---------------------------数组测试------------------------------------
        String[] arr = {};
        assertFalse(ObjectUtil.isNotEmpty(arr));
        arr = new String[1];
        assertTrue(ObjectUtil.isNotEmpty(arr));
        assertFalse(ObjectUtil.isNotEmpty(arr[0]));

        //---------------------------迭代器测试------------------------------------
        Set<String> set = new HashSet<>();
        @SuppressWarnings("RedundantOperationOnEmptyContainer") Iterator<String> it = set.iterator();
        assertFalse(ObjectUtil.isNotEmpty(it));
        //增加元素
        set.add("aa");
        Iterator<String> it2 = set.iterator();
        assertTrue(ObjectUtil.isNotEmpty(it2));

        //---------------------------其他测试------------------------------------
        TestUser testUser = new TestUser();
        assertTrue(ObjectUtil.isNotEmpty(testUser));
        assertFalse(ObjectUtil.isNotEmpty(testUser.getName()));
    }

    /**
     * Method: equal(Object orig, Object dest)
     */
    @Test
    public void testEquals() {
        assertTrue(ObjectUtil.equals(null, null));
        //noinspection ConstantValue
        assertFalse(ObjectUtil.equals("", null));
        assertTrue(ObjectUtil.equals("", ""));
        assertFalse(ObjectUtil.equals("", " "));
        assertTrue(ObjectUtil.equals(123, 123));
        //noinspection ConstantValue
        assertFalse(ObjectUtil.equals(123, null));
        assertFalse(ObjectUtil.equals(123, 123L));
        assertTrue(ObjectUtil.equals("abc", "abc"));
        assertTrue(ObjectUtil.equals(1.0d, 1.00d));
        assertTrue(ObjectUtil.equals(1d, 1.0d));
        assertTrue(ObjectUtil.equals(1.0f, 1.00f));
        assertFalse(ObjectUtil.equals(1.0d, 1.0f));
        assertTrue(ObjectUtil.equals(Lists.arrayList("abc"), Lists.arrayList("abc")));
        assertFalse(ObjectUtil.equals(new int[]{1, 2}, new int[]{1, 2}));
        assertFalse(ObjectUtil.equals(new String[]{"1", "2"}, new String[]{"1", "2"}));

        assertTrue(ObjectUtil.equal(null, null));
        //noinspection ConstantValue
        assertFalse(ObjectUtil.equal("", null));
        assertTrue(ObjectUtil.equal("", ""));
        assertFalse(ObjectUtil.equal("", " "));
        assertTrue(ObjectUtil.equal(123, 123));
        //noinspection ConstantValue
        assertFalse(ObjectUtil.equal(123, null));
        assertFalse(ObjectUtil.equal(123, 123L));
        assertTrue(ObjectUtil.equal("abc", "abc"));
        assertTrue(ObjectUtil.equal(1.0d, 1.00d));
        assertTrue(ObjectUtil.equal(1d, 1.0d));
        assertTrue(ObjectUtil.equal(1.0f, 1.00f));
        assertFalse(ObjectUtil.equal(1.0d, 1.0f));
        assertTrue(ObjectUtil.equal(Lists.arrayList("abc"), Lists.arrayList("abc")));
        assertFalse(ObjectUtil.equal(new int[]{1, 2}, new int[]{1, 2}));
        assertFalse(ObjectUtil.equal(new String[]{"1", "2"}, new String[]{"1", "2"}));

        assertFalse(ObjectUtil.notEquals(null, null));
        //noinspection ConstantValue
        assertTrue(ObjectUtil.notEquals("", null));
        assertFalse(ObjectUtil.notEquals("", ""));
        assertTrue(ObjectUtil.notEquals("", " "));
        assertFalse(ObjectUtil.notEquals(123, 123));
        //noinspection ConstantValue
        assertTrue(ObjectUtil.notEquals(123, null));
        assertTrue(ObjectUtil.notEquals(123, 123L));
        assertFalse(ObjectUtil.notEquals("abc", "abc"));
        assertFalse(ObjectUtil.notEquals(1.0d, 1.00d));
        assertFalse(ObjectUtil.notEquals(1d, 1.0d));
        assertFalse(ObjectUtil.notEquals(1.0f, 1.00f));
        assertTrue(ObjectUtil.notEquals(1.0d, 1.0f));
        assertFalse(ObjectUtil.notEquals(Lists.arrayList("abc"), Lists.arrayList("abc")));
        assertTrue(ObjectUtil.notEquals(new int[]{1, 2}, new int[]{1, 2}));
        assertTrue(ObjectUtil.notEquals(new String[]{"1", "2"}, new String[]{"1", "2"}));

        assertFalse(ObjectUtil.notEqual(null, null));
        //noinspection ConstantValue
        assertTrue(ObjectUtil.notEqual("", null));
        assertFalse(ObjectUtil.notEqual("", ""));
        assertTrue(ObjectUtil.notEqual("", " "));
        assertFalse(ObjectUtil.notEqual(123, 123));
        //noinspection ConstantValue
        assertTrue(ObjectUtil.notEqual(123, null));
        assertTrue(ObjectUtil.notEqual(123, 123L));
        assertFalse(ObjectUtil.notEqual("abc", "abc"));
        assertFalse(ObjectUtil.notEqual(1.0d, 1.00d));
        assertFalse(ObjectUtil.notEqual(1d, 1.0d));
        assertFalse(ObjectUtil.notEqual(1.0f, 1.00f));
        assertTrue(ObjectUtil.notEqual(1.0d, 1.0f));
        assertFalse(ObjectUtil.notEqual(Lists.arrayList("abc"), Lists.arrayList("abc")));
        assertTrue(ObjectUtil.notEqual(new int[]{1, 2}, new int[]{1, 2}));
        assertTrue(ObjectUtil.notEqual(new String[]{"1", "2"}, new String[]{"1", "2"}));

        BigDecimal decimal = new BigDecimal("123.00");
        BigDecimal decimal1 = new BigDecimal("123.00");
        //noinspection NewObjectEquality,NumberEquality
        System.out.println(decimal == decimal1);
        System.out.println(decimal.equals(decimal1));

        List<String> list = Lists.arrayList("abc");
        List<String> list2 = Lists.arrayList("abc");
        System.out.println(list == list2);
        System.out.println(list.equals(list2));

        assertTrue(ObjectUtil.equals(new BigDecimal("1"), new BigDecimal("1")));
        assertTrue(ObjectUtil.equals(new BigDecimal("1"), new BigDecimal("1.0")));
        assertFalse(ObjectUtil.equals(new BigDecimal("1"), new BigDecimal("2.0")));

        assertTrue(ObjectUtil.equal(new BigDecimal("1"), new BigDecimal("1")));
        assertTrue(ObjectUtil.equal(new BigDecimal("1"), new BigDecimal("1.0")));
        assertFalse(ObjectUtil.equal(new BigDecimal("1"), new BigDecimal("2.0")));

        assertFalse(ObjectUtil.notEquals(new BigDecimal("1"), new BigDecimal("1")));
        assertFalse(ObjectUtil.notEquals(new BigDecimal("1"), new BigDecimal("1.0")));
        assertTrue(ObjectUtil.notEquals(new BigDecimal("1"), new BigDecimal("2.0")));

        assertFalse(ObjectUtil.notEqual(new BigDecimal("1"), new BigDecimal("1")));
        assertFalse(ObjectUtil.notEqual(new BigDecimal("1"), new BigDecimal("1.0")));
        assertTrue(ObjectUtil.notEqual(new BigDecimal("1"), new BigDecimal("2.0")));

        String s1 = null;
        String s2 = "";
        String s3 = "   ";
        String s4 = "a";
        String s5 = "a";
        //noinspection ConstantValue
        assertFalse(ObjectUtil.equals(s1, s3));
        assertFalse(ObjectUtil.equals(s2, s3));
        assertTrue(ObjectUtil.equals(s4, s5));

        //noinspection ConstantValue
        assertFalse(ObjectUtil.equal(s1, s3));
        assertFalse(ObjectUtil.equal(s2, s3));
        assertTrue(ObjectUtil.equal(s4, s5));

        TestUser testUser1 = new TestUser();
        testUser1.setName("张三");
        testUser1.setAge(18);
        @SuppressWarnings("UnnecessaryLocalVariable") TestUser testUser2 = testUser1;
        assertTrue(ObjectUtil.equals(testUser1, testUser2));
        assertTrue(ObjectUtil.equal(testUser1, testUser2));
        TestUser testUser3 = new TestUser();
        testUser3.setName("张三");
        testUser3.setAge(18);
        assertFalse(ObjectUtil.notEquals(testUser1, testUser3));
        assertFalse(ObjectUtil.notEqual(testUser1, testUser3));
    }

    /**
     * Method: nullToDefault(T obj, @Nonnull T value)
     */
    @SuppressWarnings({"ConstantValue", "DataFlowIssue"})
    @Test
    public void testNullToDefault() {
        assertEquals("admin", ObjectUtil.nullToDefault(null, "admin"));
        assertEquals("redis", ObjectUtil.nullToDefault("redis", "admin"));

        assertEquals(new BigDecimal("1.0"), ObjectUtil.nullToDefault(null, new BigDecimal("1.0")));
        assertEquals(new BigDecimal("0"), ObjectUtil.nullToDefault(new BigDecimal("0"), new BigDecimal("1.0")));


        TestUser testUser = null;
        TestUser testUser1 = ObjectUtil.nullToDefault(testUser, new TestUser("admin"));
        assertEquals("admin", testUser1.getName());
        TestUser testUser2 = ObjectUtil.nullToDefault(testUser, new TestUser("redis"));
        assertEquals("redis", testUser2.getName());
        TestUser testUser0 = ObjectUtil.nullToDefault(testUser1, new TestUser("redis"));
        assertEquals("admin", testUser0.getName());

        TestUser testUser3 = ObjectUtil.nullToDefault(testUser, () -> new TestUser("zhangSan"));
        assertEquals("zhangSan", testUser3.getName());
        TestUser testUser4 = ObjectUtil.nullToDefault(testUser, () -> testUser2);
        assertEquals("redis", testUser4.getName());
        TestUser testUser5 = ObjectUtil.nullToDefault(testUser1, () -> testUser2);
        assertEquals("admin", testUser5.getName());

        TestUser testUser6 = ObjectUtil.nullToDefault(testUser, it -> new TestUser("liSi"));
        assertEquals("liSi", testUser6.getName());
        TestUser testUser7 = ObjectUtil.nullToDefault(testUser, it -> testUser2);
        assertEquals("redis", testUser7.getName());
        TestUser testUser8 = ObjectUtil.nullToDefault(testUser1, it -> testUser2);
        assertEquals("admin", testUser8.getName());
        Assertions.assertThrows(IllegalArgumentException.class, () -> ObjectUtil.nullToDefault(testUser, (Supplier<? extends TestUser>) null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ObjectUtil.nullToDefault(testUser1, (Function<TestUser, ? extends TestUser>) null));
        assertNull(ObjectUtil.nullToDefault(testUser, (TestUser) null));

        String name = ObjectUtil.nullToDefault(testUser, () -> "zhangSan", "liSi");
        assertEquals("liSi", name);
        String name1 = ObjectUtil.nullToDefault(testUser1, () -> "zhangSan", "liSi");
        assertEquals("zhangSan", name1);
        String name2 = ObjectUtil.nullToDefault(testUser, TestUser::getName, "liSi");
        assertEquals("liSi", name2);
        String name3 = ObjectUtil.nullToDefault(testUser1, TestUser::getName, "liSi");
        assertEquals("admin", name3);
        Assertions.assertThrows(IllegalArgumentException.class, () -> ObjectUtil.nullToDefault(testUser, (Supplier<? extends TestUser>) null, "liSi"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ObjectUtil.nullToDefault(testUser1, (Function<TestUser, ? extends TestUser>) null, "liSi"));
    }

    /**
     * Method: emptyToDefault(T obj, @Nonnull T value)
     */
    @SuppressWarnings({"ConstantValue", "DataFlowIssue"})
    @Test
    public void testEmptyToDefault() {
        assertEquals("admin", ObjectUtil.emptyToDefault(null, "admin"));
        assertEquals("admin", ObjectUtil.emptyToDefault("", "admin"));
        assertEquals("redis", ObjectUtil.emptyToDefault("redis", "admin"));
        assertEquals(Lists.arrayList("a", "b"), ObjectUtil.emptyToDefault(null, Lists.arrayList("a", "b")));
        assertEquals(Lists.arrayList("a"), ObjectUtil.emptyToDefault(Lists.arrayList("a"), Lists.arrayList("a", "b")));

        assertEquals(new BigDecimal("1.0"), ObjectUtil.emptyToDefault(null, new BigDecimal("1.0")));
        assertEquals(new BigDecimal("0"), ObjectUtil.emptyToDefault(new BigDecimal("0"), new BigDecimal("1.0")));

        List<String> list = null;
        assertNotNull(ObjectUtil.emptyToDefault(list, Lists.arrayList()));
        Set<String> set = null;
        assertNotNull(ObjectUtil.emptyToDefault(set, Lists.hashSet()));
        Map<String, Object> map = null;
        assertNotNull(ObjectUtil.emptyToDefault(map, Maps.hashMap()));
        String[] array = null;
        assertNotNull(ObjectUtil.emptyToDefault(array, ArrayUtil.newArray(String.class, 1)));


        TestUser testUser = null;
        TestUser testUser1 = ObjectUtil.emptyToDefault(testUser, new TestUser("admin"));
        assertEquals("admin", testUser1.getName());
        TestUser testUser2 = ObjectUtil.emptyToDefault(testUser, new TestUser("redis"));
        assertEquals("redis", testUser2.getName());
        TestUser testUser0 = ObjectUtil.emptyToDefault(testUser1, new TestUser("redis"));
        assertEquals("admin", testUser0.getName());

        TestUser testUser3 = ObjectUtil.emptyToDefault(testUser, () -> new TestUser("zhangSan"));
        assertEquals("zhangSan", testUser3.getName());
        TestUser testUser4 = ObjectUtil.emptyToDefault(testUser, () -> testUser2);
        assertEquals("redis", testUser4.getName());
        TestUser testUser5 = ObjectUtil.emptyToDefault(testUser1, () -> testUser2);
        assertEquals("admin", testUser5.getName());

        TestUser testUser6 = ObjectUtil.emptyToDefault(testUser, it -> new TestUser("liSi"));
        assertEquals("liSi", testUser6.getName());
        TestUser testUser7 = ObjectUtil.emptyToDefault(testUser, it -> testUser2);
        assertEquals("redis", testUser7.getName());
        TestUser testUser8 = ObjectUtil.emptyToDefault(testUser1, it -> testUser2);
        assertEquals("admin", testUser8.getName());
        Assertions.assertThrows(IllegalArgumentException.class, () -> ObjectUtil.emptyToDefault(testUser, (Supplier<? extends TestUser>) null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ObjectUtil.emptyToDefault(testUser1, (Function<TestUser, ? extends TestUser>) null));
        assertNull(ObjectUtil.emptyToDefault(testUser, (TestUser) null));

        String name = ObjectUtil.emptyToDefault(testUser, () -> "zhangSan", "liSi");
        assertEquals("liSi", name);
        String name1 = ObjectUtil.emptyToDefault(testUser1, () -> "zhangSan", "liSi");
        assertEquals("zhangSan", name1);
        String name2 = ObjectUtil.emptyToDefault(testUser, TestUser::getName, "liSi");
        assertEquals("liSi", name2);
        String name3 = ObjectUtil.emptyToDefault(testUser1, TestUser::getName, "liSi");
        assertEquals("admin", name3);
        Assertions.assertThrows(IllegalArgumentException.class, () -> ObjectUtil.emptyToDefault(testUser, (Supplier<? extends TestUser>) null, "liSi"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> ObjectUtil.emptyToDefault(testUser1, (Function<TestUser, ? extends TestUser>) null, "liSi"));
    }

    @SuppressWarnings("ConstantValue")
    @Test
    public void testNullToConsumer() {
        TestUser testUser = new TestUser();
        testUser.setName("zs");
        ObjectUtil.notNullToConsumer(testUser, it -> it.setAge(18));
        assertEquals(18, testUser.getAge());

        TestUser testUser2 = null;
        ObjectUtil.notNullToConsumer(testUser2, it -> it.setAge(18));
        assertNull(testUser2);
    }

    @SuppressWarnings("ConstantValue")
    @Test
    public void testEmptyToConsumer() {
        TestUser testUser = new TestUser();
        testUser.setName("zs");
        ObjectUtil.notEmptyToConsumer(testUser, it -> it.setAge(18));
        assertEquals(18, testUser.getAge());

        TestUser testUser2 = null;
        ObjectUtil.notEmptyToConsumer(testUser2, it -> it.setAge(18));
        assertNull(testUser2);

        String str = "abc";
        ObjectUtil.notEmptyToConsumer(str, s -> System.out.println("---->> testEmptyToConsumer: s=" + s));
        String str2 = "";
        ObjectUtil.notEmptyToConsumer(str2, s -> System.out.println("---->> testEmptyToConsumer: s2=" + s));

        List<String> list = Lists.arrayList("a", "b");
        ObjectUtil.notEmptyToConsumer(list, it -> it.add("c"));
        assertLinesMatch(Lists.arrayList("a", "b", "c"), list);

        List<String> list2 = Lists.arrayList();
        ObjectUtil.notEmptyToConsumer(list2, it -> it.add("c"));
        assertLinesMatch(Lists.arrayList(), list2);

        Map<String, Integer> map = Maps.hashMap("a", 1);
        ObjectUtil.notEmptyToConsumer(map, it -> it.put("b", 2));
        assertEquals(2, map.size());

        Map<String, Integer> map2 = Maps.hashMap();
        ObjectUtil.notEmptyToConsumer(map2, it -> it.put("b", 2));
        assertEquals(0, map2.size());

        Long[] array = new Long[]{1L, 2L};
        ObjectUtil.notEmptyToConsumer(array, it -> {
            Long[] longs = ArrayUtil.remove(it, 2L);
            assertArrayEquals(new Long[]{1L}, longs);
        });
        assertArrayEquals(new Long[]{1L, 2L}, array);

        Long[] array2 = new Long[]{};
        ObjectUtil.notEmptyToConsumer(array, it -> {
            Long[] longs = ArrayUtil.remove(it, 2L);
            assertArrayEquals(new Long[]{1L}, longs);
        });
        assertArrayEquals(new Long[]{}, array2);
    }

    @Test
    public void testIsTrueToConsumer() {
        TestUser testUser = new TestUser();
        testUser.setName("zs");
        testUser.setAge(10);
        ObjectUtil.isTrueToConsumer(ObjectUtil.isNotNull(testUser.getAge()), it -> testUser.setAge(20));
        assertEquals(20, testUser.getAge());
        ObjectUtil.isTrueToConsumer(ObjectUtil.isNotNull(testUser.getId()), it -> testUser.setAge(30));
        assertEquals(20, testUser.getAge());
        ObjectUtil.isTrueToConsumer(ObjectUtil.isNotNull(testUser.getId()), it -> testUser.setAge(40), it -> testUser.setAge(50));
        assertEquals(50, testUser.getAge());
    }

    @Test
    public void testIsFalseToConsumer() {
        TestUser testUser = new TestUser();
        testUser.setName("zs");
        testUser.setAge(10);
        ObjectUtil.isFalseToConsumer(ObjectUtil.isNotNull(testUser.getAge()), it -> testUser.setAge(20));
        assertEquals(10, testUser.getAge());
        ObjectUtil.isFalseToConsumer(ObjectUtil.isNotNull(testUser.getId()), it -> testUser.setAge(30));
        assertEquals(30, testUser.getAge());
        ObjectUtil.isFalseToConsumer(ObjectUtil.isNotNull(testUser.getId()), it -> testUser.setAge(40), it -> testUser.setAge(50));
        assertEquals(40, testUser.getAge());
        ObjectUtil.isFalseToConsumer(ObjectUtil.isNull(testUser.getId()), it -> testUser.setAge(60), it -> testUser.setAge(70));
        assertEquals(70, testUser.getAge());
    }

    @Test
    public void testPredicateToConsumer() {
        TestUser testUser = new TestUser();
        testUser.setName("zs");
        testUser.setAge(10);
        ObjectUtil.predicateToConsumer(testUser, it -> ObjectUtil.isNotNull(it.getAge()), it -> it.setAge(20));
        assertEquals(20, testUser.getAge());
        ObjectUtil.predicateToConsumer(testUser, it -> ObjectUtil.isNotNull(it.getId()), it -> it.setAge(30));
        assertEquals(20, testUser.getAge());
        ObjectUtil.predicateToConsumer(testUser, it -> ObjectUtil.isNotNull(it.getAge()), it -> it.setAge(40), it -> it.setAge(50));
        assertEquals(40, testUser.getAge());
        ObjectUtil.predicateToConsumer(testUser, it -> ObjectUtil.isNotNull(it.getId()), it -> it.setAge(60), it -> it.setAge(70));
        assertEquals(70, testUser.getAge());
    }

    @Test
    public void testConvert() {
        String obj = "12";
        long a = ObjectUtil.convert(obj, long.class);
        assertEquals(12, a);
        Integer b = 12;
        assertEquals("12", ObjectUtil.convert(b, String.class));


        //测试待默认值的转换
        assertEquals("12", ObjectUtil.convert(b, "5"));
        b = null;
        assertEquals("5", ObjectUtil.convert(b, "5"));
    }

    @Test
    public void testGtZero() {
        Integer a = 1;
        assertTrue(ObjectUtil.geZero(a));
        assertTrue(ObjectUtil.gtZero(a));
        Integer b = 0;
        assertTrue(ObjectUtil.geZero(b));
        assertFalse(ObjectUtil.gtZero(b));
        Integer c = -1;
        assertFalse(ObjectUtil.geZero(c));
        assertFalse(ObjectUtil.gtZero(c));
    }

    @Test
    public void testLtZero() {
        Integer a = 1;
        assertFalse(ObjectUtil.leZero(a));
        assertFalse(ObjectUtil.ltZero(a));
        Integer b = 0;
        assertTrue(ObjectUtil.leZero(b));
        assertFalse(ObjectUtil.ltZero(b));
        Integer c = -1;
        assertTrue(ObjectUtil.leZero(c));
        assertTrue(ObjectUtil.ltZero(c));
    }

    @Test
    public void testGroup() {
        List<TestUser> testUsers = getUsers(2000);
        List<List<TestUser>> group = ObjectUtil.group(testUsers);
        assertEquals(2, group.size());
        assertEquals(1000, group.get(0).size());
        assertEquals(1000, group.get(1).size());
        List<TestUser> subList = ObjectUtil.dataPaging(testUsers, 4, 600);
        assertEquals(200, subList.size());
        List<TestUser> subList2 = ObjectUtil.dataPaging(testUsers, 5, 600);
        assertEquals(0, subList2.size());
        List<TestUser> subList3 = ObjectUtil.dataPaging(testUsers, 2, 600);
        assertEquals(600, subList3.size());


        List<TestUser> users2 = getUsers(2001);
        List<List<TestUser>> group2 = ObjectUtil.group(users2);
        assertEquals(3, group2.size());
        assertEquals(1000, group2.get(0).size());
        assertEquals(1000, group2.get(1).size());
        assertEquals(1, group2.get(2).size());

        List<TestUser> users3 = getUsers(1000);
        List<List<TestUser>> group3 = ObjectUtil.group(users3);
        assertEquals(1, group3.size());
        assertEquals(1000, group3.get(0).size());

        List<TestUser> users4 = getUsers(1999);
        List<List<TestUser>> group4 = ObjectUtil.group(users4);
        assertEquals(2, group4.size());
        assertEquals(1000, group4.get(0).size());
        assertEquals(999, group4.get(1).size());
    }

    private List<TestUser> getUsers(int size) {
        List<TestUser> testUsers = Lists.arrayList();
        for (int i = 0; i < size; i++) {
            TestUser testUser = new TestUser();
            testUser.setStudentId(i + 1);
            testUser.setAge(testUser.getStudentId() % 2 == 0 ? 17 : 18);
            testUser.setName("student" + testUser.getStudentId());
            testUsers.add(testUser);
        }
        return testUsers;
    }

    /**
     * Method: compare(T orig, T dest)
     */
    @Test
    public void testCompare() {
        assertEquals(0, ObjectUtil.compare(null, null));
        assertEquals(1, ObjectUtil.compare(1, null));
        assertEquals(-1, ObjectUtil.compare(null, 13));
        assertEquals(0, ObjectUtil.compare(1, 1));
        assertEquals(-1, ObjectUtil.compare(1, 13));
        assertEquals(1, ObjectUtil.compare(13, 1));
        assertEquals(0, ObjectUtil.compare("a", "a"));
        assertEquals(-1, ObjectUtil.compare("a", "aa"));
        assertEquals(1, ObjectUtil.compare("aa", "a"));

        assertEquals(0, ObjectUtil.compare(1, 1, Integer::compareTo));
        assertEquals(-1, ObjectUtil.compare(1, 13, Integer::compareTo));
        assertEquals(1, ObjectUtil.compare(13, 1, Integer::compareTo));
        assertEquals(0, ObjectUtil.compare("a", "a", String::compareTo));
        assertEquals(-1, ObjectUtil.compare("a", "aa", String::compareTo));
        assertEquals(1, ObjectUtil.compare("aa", "a", String::compareTo));

        assertEquals(0, ObjectUtil.compare(null, null, null));
        assertEquals(1, ObjectUtil.compare(1, null, null));
        assertEquals(-1, ObjectUtil.compare(null, 13, null));
        assertEquals(0, ObjectUtil.compare(1, 1, null));
        assertEquals(-1, ObjectUtil.compare(1, 13, null));
        assertEquals(0, ObjectUtil.compare("a", "a", null));
        assertEquals(-1, ObjectUtil.compare("a", "aa", null));
        assertEquals(1, ObjectUtil.compare("aa", "a", null));
        assertThrowsExactly(NullPointerException.class, () -> ObjectUtil.compare(null, 1, Integer::compareTo));
        assertThrowsExactly(NullPointerException.class, () -> ObjectUtil.compare("aa", null, String::compareTo));
        assertThrowsExactly(ClassCastException.class, () -> ObjectUtil.compare(new TestUser("zs"), new TestUser("ls"), null));
        assertThrowsExactly(ClassCastException.class, () -> ObjectUtil.compare(new TestUser("zs"), null, null));
        assertThrowsExactly(ClassCastException.class, () -> ObjectUtil.compare(null, new TestUser("ls"), null));
    }

    @Test
    public void testToString() {
        assertEquals("0", ObjectUtil.toString(0));
        assertEquals("null", ObjectUtil.toString(null));
        Map<String, Object> map = Maps.linkedHashMap();
        map.put("a", 1);
        map.put("b", 2);
        assertEquals("{a=1, b=2}", ObjectUtil.toString(map));
        List<String> list = List.of("a", "b", "c");
        assertEquals("[a, b, c]", ObjectUtil.toString(list));
        TestUser testUser = new TestUser("zs");
        assertEquals("TestUser(name=zs, age=null, studentId=null, id=null)", ObjectUtil.toString(testUser));
    }

    /**
     * Method: compare(T orig, T dest)
     */
    @Test
    public void testToMap() {

        List<TestUser> list = Lists.arrayList(
                new TestUser("张三", 18, 1),
                new TestUser("李四", 19, 2),
                new TestUser("王五", 17, 3),
                new TestUser("马六", 18, 4),
                new TestUser("钱七", 16, 5),
                new TestUser("赵一", null, 6),
                new TestUser(null, null, 7),
                null
        );

        Map<Integer, TestUser> idMap = ObjectUtil.toMap(list, "studentId");
        assertEquals(7, idMap.size());
        Map<Integer, TestUser> idMap2 = ObjectUtil.toMap(list, TestUser::getStudentId);
        assertEquals(7, idMap2.size());

        Map<String, TestUser> nameMap = ObjectUtil.toMap(list, "name");
        assertEquals(6, nameMap.size());
        Map<String, TestUser> nameMap2 = ObjectUtil.toMap(list, TestUser::getName);
        assertEquals(6, nameMap2.size());

        Map<Integer, TestUser> ageMap = ObjectUtil.toMap(list, "age");
        assertEquals(4, ageMap.size());
        Map<Integer, TestUser> ageMap2 = ObjectUtil.toMap(list, TestUser::getAge);
        assertEquals(4, ageMap2.size());

        Map<Integer, String> idNameMap = ObjectUtil.toMap(list, "studentId", "name");
        assertEquals(7, idNameMap.size());
        Map<Integer, String> idNameMap2 = ObjectUtil.toMap(list, TestUser::getStudentId, TestUser::getName);
        assertEquals(7, idNameMap2.size());

        Map<Integer, String> idNameMap3 = ObjectUtil.toMap(list, "studentId", "name", true);
        assertEquals(6, idNameMap3.size());
        Map<Integer, String> idNameMap4 = ObjectUtil.toMap(list, TestUser::getStudentId, TestUser::getName, true);
        assertEquals(6, idNameMap4.size());

        Map<Integer, Integer> idAgeMap = ObjectUtil.toMap(list, "studentId", "age");
        assertEquals(7, idAgeMap.size());
        Map<Integer, Integer> idAgeMap2 = ObjectUtil.toMap(list, TestUser::getStudentId, TestUser::getAge);
        assertEquals(7, idAgeMap2.size());

        Map<Integer, Integer> idAgeMap3 = ObjectUtil.toMap(list, "studentId", "age", true);
        assertEquals(5, idAgeMap3.size());
        Map<Integer, Integer> idAgeMap4 = ObjectUtil.toMap(list, TestUser::getStudentId, TestUser::getAge, true);
        assertEquals(5, idAgeMap4.size());
    }

    /**
     * Method: compare(T orig, T dest)
     */
    @Test
    public void testToMapList() {

        List<TestUser> list = Lists.arrayList(
                new TestUser("张三", 18, 1),
                new TestUser("李四", 17, 2),
                new TestUser("王五", 17, 3),
                new TestUser("马六", 18, 4),
                new TestUser("钱七", 17, 5),
                new TestUser("赵一", 18, 6),
                new TestUser(null, 17, 7),
                null
        );

        Map<Integer, List<TestUser>> idMap = ObjectUtil.toMapList(list, "studentId");
        System.out.println(idMap);
        assertEquals(7, idMap.size());
        Map<Integer, List<TestUser>> idMap2 = ObjectUtil.toMapList(list, TestUser::getStudentId);
        System.out.println(idMap2);
        assertEquals(7, idMap2.size());

        Map<Integer, List<TestUser>> idMap3 = ObjectUtil.toMapList(list, "studentId", true);
        System.out.println(idMap3);
        assertEquals(7, idMap3.size());
        Map<Integer, List<TestUser>> idMap4 = ObjectUtil.toMapList(list, TestUser::getStudentId, true);
        System.out.println(idMap4);
        assertEquals(7, idMap4.size());

        Map<Integer, List<TestUser>> ageMap = ObjectUtil.toMapList(list, "age");
        System.out.println(ageMap);
        assertEquals(2, ageMap.size());
        assertEquals(4, ageMap.get(17).size());
        assertEquals(3, ageMap.get(18).size());
        Map<Integer, List<TestUser>> ageMap2 = ObjectUtil.toMapList(list, TestUser::getAge);
        System.out.println(ageMap2);
        assertEquals(2, ageMap2.size());
        assertEquals(4, ageMap.get(17).size());
        assertEquals(3, ageMap.get(18).size());

        Map<Integer, List<String>> ageMap3 = ObjectUtil.toMapList(list, "age", "name");
        System.out.println(ageMap3);
        assertEquals(2, ageMap3.size());
        assertEquals(4, ageMap3.get(17).size());
        assertEquals(3, ageMap3.get(18).size());
        Map<Integer, List<String>> ageMap4 = ObjectUtil.toMapList(list, TestUser::getAge, TestUser::getName);
        System.out.println(ageMap4);
        assertEquals(2, ageMap4.size());
        assertEquals(4, ageMap4.get(17).size());
        assertEquals(3, ageMap4.get(18).size());

        Map<Integer, List<String>> ageMap5 = ObjectUtil.toMapList(list, "age", "name", true);
        System.out.println(ageMap5);
        assertEquals(2, ageMap5.size());
        assertEquals(3, ageMap5.get(17).size());
        assertEquals(3, ageMap5.get(18).size());
        Map<Integer, List<String>> ageMap6 = ObjectUtil.toMapList(list, TestUser::getAge, TestUser::getName, true);
        System.out.println(ageMap6);
        assertEquals(2, ageMap6.size());
        assertEquals(3, ageMap6.get(17).size());
        assertEquals(3, ageMap6.get(18).size());

        Map<Integer, List<Integer>> ageMap7 = ObjectUtil.toMapList(list, "age", "studentId", true);
        System.out.println(ageMap7);
        assertEquals(2, ageMap7.size());
        assertEquals(4, ageMap7.get(17).size());
        assertEquals(3, ageMap7.get(18).size());
        Map<Integer, List<Integer>> ageMap8 = ObjectUtil.toMapList(list, TestUser::getAge, TestUser::getStudentId, true);
        System.out.println(ageMap8);
        assertEquals(2, ageMap8.size());
        assertEquals(4, ageMap8.get(17).size());
        assertEquals(3, ageMap8.get(18).size());
    }
}
