package com.ipower.framework.common.core.bean;

import com.ipower.framework.common.core.Editor;
import com.ipower.framework.common.core.annotation.TestAnnotation;
import com.ipower.framework.common.core.collection.Lists;
import com.ipower.framework.common.core.entity.Demo;
import com.ipower.framework.common.core.entity.IntList;
import com.ipower.framework.common.core.entity.TestUser;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.text.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * BeanUtil Tester.
 *
 * @author huangad@coracle.com
 */
@Slf4j
public class BeanUtilTest {

    @Before
    public void before() {
        //TODO: Test before goes here...
    }

    @After
    public void after() {
        //TODO: Test after goes here...
    }

    /**
     * 实体类数据拷贝
     * Method: copy(Object orig, Object dest)
     */
    @Test
    public void testCopyForOrigDest() {
        TestUser testUser1 = new TestUser();
        testUser1.setAge(54);
        testUser1.setName("忘忧草涛");
        TestUser testUser2 = new TestUser();
        BeanUtil.copy(testUser1, testUser2);

        Integer integer = 54;

        assertEquals(integer, testUser2.getAge());
        assertEquals("忘忧草涛", testUser2.getName());

    }

    /**
     * 实体类数据拷贝
     * Method: copy(Object orig, Object dest)
     */
    @Test
    public void testCopyForNewOrigDest() {
        TestUser testUser1 = new TestUser();
        testUser1.setAge(54);
        testUser1.setName("忘忧草涛");
        TestUser testUser2 = BeanUtil.copyNew(testUser1, TestUser.class);
        Integer integer = 54;

        assertEquals(integer, testUser2.getAge());
        assertEquals("忘忧草涛", testUser2.getName());

        List<TestUser> testUsers = BeanUtil.copyNew(Lists.arrayList(testUser1, testUser2), TestUser.class);
        assertEquals(integer, testUsers.get(0).getAge());
        assertEquals(integer, testUsers.get(1).getAge());
        assertEquals("忘忧草涛", testUsers.get(0).getName());
        assertEquals("忘忧草涛", testUsers.get(1).getName());
    }

    /**
     * 测试批量拷贝效率
     */
    @Test
    public void testCopyListEfficiency() {
        long time = System.currentTimeMillis();
        int size = 1000000;
        List<TestUser> testUsers = buildUser(size);
        long time2 = System.currentTimeMillis();
        log.warn("===============>> build, size=" + testUsers.size() + ", time=" + (time2 - time));
        List<Demo> demos = BeanUtil.copyNew(testUsers, Demo.class);
        long time3 = System.currentTimeMillis();
        log.warn("===============>> copy, size=" + demos.size() + ", time=" + (time3 - time2));
    }

    private List<TestUser> buildUser(int size) {
        List<TestUser> list = Lists.arrayList();
        for (int i = 0; i < size; i++) {
            TestUser testUser = new TestUser();
            testUser.setStudentId(i + 1);
            testUser.setName("学生" + testUser.getStudentId());
            testUser.setAge(18);
            list.add(testUser);
        }
        return list;
    }

    /**
     * 测试去空格
     * Method: trimProperty(Class<?> clazz)
     */
    @Test
    public void testTrimProperty() {
        Demo demo = new Demo();
        demo.setStudentId(1);
        demo.setCode(" a, b ");
        demo.setName(" 张三");
        demo.setAge(18);
        BeanUtil.trimProperty(demo);
        log.warn("test trim property 1, user={}", demo);
        assertEquals("a, b", demo.getCode());
        assertEquals("张三", demo.getName());

        demo.setCode(" a, b ");
        demo.setName(" 张三");
        BeanUtil.trimProperty(demo, "code");
        log.warn("test trim property 2, user={}", demo);
        assertEquals(" a, b ", demo.getCode());
        assertEquals("张三", demo.getName());

        demo.setCode(" a, b ");
        demo.setName(" 张三");
        BeanUtil.trimProperty(demo, "code", "name");
        log.warn("test trim property 3, user={}", demo);
        assertEquals(" a, b ", demo.getCode());
        assertEquals(" 张三", demo.getName());

    }

    /**
     * 判断是否为Bean
     * Method: isBean(Class<?> clazz)
     */
    @Test
    public void testIsBean() {
        assertTrue(BeanUtil.isBean(TestUser.class));
        assertFalse(BeanUtil.isBean(String.class));

    }

    /**
     * 判断是否有Setter方法
     * Method: hasSetter(Class<?> clazz)
     */
    @Test
    public void testHasSetter() {
        assertTrue(BeanUtil.isBean(TestUser.class));
        assertFalse(BeanUtil.isBean(String.class));
    }

    /**
     * 判断是否为Bean对象<br>
     * 判定方法是是否存在只有一个参数的getXXX方法
     * Method: hasGetter(Class<?> clazz)
     */
    @Test
    public void testHasGetter() {
        assertTrue(BeanUtil.hasGetter(TestUser.class));
        assertFalse(BeanUtil.hasGetter(Number.class));
    }

    /**
     * 对象转Map，不进行驼峰转下划线，不忽略值为空的字段
     * Method: beanToMap(Object bean)
     */
    @Test
    public void testBeanToMapBean() {
        TestUser bean = new TestUser();
        Integer integer = 18;
        Integer id = 13231;

        bean.setAge(integer);
        bean.setStudentId(id);

        Map<String, Object> map = BeanUtil.toMap(bean);
        assertEquals(integer, map.get("age"));
        assertTrue(map.containsKey("name"));
        assertEquals(id, map.get("studentId"));
    }

    /**
     * 对象转Map
     * Method: beanToMap(Object bean, boolean isToUnderlineCase, boolean ignoreNullValue)
     */
    @Test
    public void testBeanToMapForBeanIsToUnderlineCaseIgnoreNullValue() {
        TestUser bean = new TestUser();
        Integer integer = 18;
        Integer id = 13231;

        bean.setAge(integer);
        bean.setStudentId(id);

        Map<String, Object> map = BeanUtil.toMap(bean, true, true);
        assertEquals(integer, map.get("age"));
        assertFalse(map.containsKey("name"));
        assertEquals(id, map.get("student_id"));
    }

    /**
     * 对象转Map
     * Method: beanToMap(Object bean, Map<String, Object> targetMap, final boolean isToUnderlineCase, boolean ignoreNullValue)
     */
    @Test
    public void testBeanToMapForBeanTargetMapIsToUnderlineCaseIgnoreNullValue() {
        TestUser bean = new TestUser();
        Integer integer = 18;
        Integer id = 13231;

        bean.setAge(integer);
        bean.setStudentId(id);
        Map<String, Object> targetMap = new HashMap<>();

        Map<String, Object> map = BeanUtil.toMap(bean, targetMap, true, true);
        assertEquals(integer, map.get("age"));
        assertFalse(map.containsKey("name"));
        assertEquals(id, map.get("student_id"));

    }

    /**
     * 对象转Map
     * Method: beanToMap(Object bean, Map<String, Object> targetMap, boolean ignoreNullValue, Editor<String> keyEditor)
     */
    @Test
    public void testBeanToMapForBeanTargetMapIgnoreNullValueKeyEditor() {
        TestUser bean = new TestUser();
        Integer integer = 18;
        Integer id = 13231;

        bean.setAge(integer);
        bean.setStudentId(id);

        Map<String, Object> targetMap = new HashMap<>();
        Editor<String> keyEditor = s -> {
            if ("studentId".equals(s)) {
                return null;
            } else {
                return s + "--";
            }
        };

        Map<String, Object> map = BeanUtil.toMap(bean, targetMap, true, keyEditor);
        assertEquals(integer, map.get("age--"));
        assertFalse(map.containsKey("name"));
        assertNull(map.get("studentId"));
    }

    /**
     * 获取Bean描述信息
     * Method: getBeanDesc(Class<?> clazz)
     */
    @Test
    public void testGetBeanDesc() {

        BeanDesc beanDesc = BeanUtil.getBeanDesc(TestUser.class);
        assertEquals("com.ipower.framework.common.core.entity.TestUser", beanDesc.getName());
        assertEquals("TestUser", beanDesc.getSimpleName());
    }

    /**
     * 获取Bean类的 getxxxx列表
     * Method: getReadMethod(Object bean)
     */
    @Test
    public void testGetReadMethodBean() {
        TestUser bean = new TestUser();

        List<Method> readMethod = BeanUtil.getReadMethod(bean);

        assertEquals("getStudentId", readMethod.get(0).getName());
        assertEquals("getName", readMethod.get(1).getName());
    }

    /**
     * 获取Bean类的 setxxx方法列表
     * Method: getWriteMethod(Object bean)
     */
    @Test
    public void testGetWriteMethodBean() {
        TestUser bean = new TestUser();

        List<Method> writeMethod = BeanUtil.getWriteMethod(bean);

        assertEquals("setStudentId", writeMethod.get(0).getName());
        assertEquals("setName", writeMethod.get(1).getName());
    }

    /**
     * 获取Bean类的 getxxxx列表
     * Method: getReadMethod(Class<?> clazz)
     */
    @Test
    public void testGetReadMethodClazz() {
        List<Method> readMethod = BeanUtil.getReadMethod(TestUser.class);

        assertEquals("getStudentId", readMethod.get(0).getName());
        assertEquals("getName", readMethod.get(1).getName());
    }

    /**
     * 获取Bean类的 setxxxx列表
     * Method: getWriteMethod(Class<?> clazz)
     */
    @Test
    public void testGetWriteMethodClazz() {
        List<Method> writeMethod = BeanUtil.getWriteMethod(TestUser.class);

        assertEquals("setStudentId", writeMethod.get(0).getName());
        assertEquals("setName", writeMethod.get(1).getName());
    }

    /**
     * Method: getReadAndWriteMethod(Class<?> clazz)
     */
    @Test
    public void testGetReadAndWriteMethodClazz() {
        List<Method> readAndWriteMethod = BeanUtil.getReadAndWriteMethod(TestUser.class);

        assertEquals("getStudentId", readAndWriteMethod.get(0).getName());
        assertEquals("setStudentId", readAndWriteMethod.get(1).getName());
        assertEquals("getName", readAndWriteMethod.get(2).getName());
        assertEquals("setName", readAndWriteMethod.get(3).getName());

    }

    /**
     * Method: getReadAndWriteMethod(Object bean)
     */
    @Test
    public void testGetReadAndWriteMethodBean() {
        TestUser testUser = new TestUser();
        List<Method> readAndWriteMethod = BeanUtil.getReadAndWriteMethod(testUser);

        assertEquals("getStudentId", readAndWriteMethod.get(0).getName());
        assertEquals("setStudentId", readAndWriteMethod.get(1).getName());
        assertEquals("getName", readAndWriteMethod.get(2).getName());
        assertEquals("setName", readAndWriteMethod.get(3).getName());
    }

    /**
     * 获取类属性对象
     * Method: getField(Object bean, String fieldName)
     */
    @Test
    public void testGetFieldForBeanFieldName() {
        TestUser testUser = new TestUser();
        String fieldName = "name";

        assertEquals(fieldName, BeanUtil.getField(testUser, fieldName).getName());
    }

    /**
     * 获取类属性对象
     * Method: getField(Class<?> clazz, String fieldName)
     */
    @Test
    public void testGetFieldForClazzFieldName() {
        String fieldName = "name";
        //noinspection DataFlowIssue
        assertEquals(fieldName, BeanUtil.getField(TestUser.class, fieldName).getName());
    }

    /**
     * 获取类属性对象列表
     * Method: getFields(Object bean)
     */
    @Test
    public void testGetFieldsBean() {
        TestUser testUser = new TestUser();
        List<Field> fields = BeanUtil.getFields(testUser);
        assertEquals("name", fields.get(0).getName());
        assertEquals("age", fields.get(1).getName());
        assertEquals("studentId", fields.get(2).getName());
    }

    /**
     * 获取类属性对象列表
     * Method: getFields(Object bean)
     */
    @Test
    public void testGetFieldsBean2() {
        Demo demo = new Demo();
        List<Field> fields = BeanUtil.getFields(demo);
        assertEquals("code", fields.get(0).getName());
        assertEquals("studentId", fields.get(3).getName());
        assertEquals("id", fields.get(4).getName());

        List<Field> fields2 = BeanUtil.getFields(demo);
        assertEquals("code", fields2.get(0).getName());
        assertEquals("studentId", fields2.get(3).getName());
    }

    /**
     * 获取类属性对象列表
     * Method: getFields(Object bean)
     */
    @Test
    public void testIsDifferent() {
        Demo demo = new Demo();
        demo.setId(1L);
        demo.setCode("a");
        demo.setName("zs");
        demo.setAge(18);
        demo.setStudentId(1);

        Demo demo2 = new Demo();
        demo2.setId(2L);
        demo2.setCode("a");
        demo2.setName("ls");
        demo2.setAge(18);
        demo2.setStudentId(2);

        assertTrue(BeanUtil.isDifferent(demo, demo2));
        assertTrue(BeanUtil.isDifferentSingle(demo, demo2));

        assertTrue(BeanUtil.isDifferentSelected(demo, demo2, "id", "code", "age"));
        assertFalse(BeanUtil.isDifferentSingleSelected(demo, demo2, "id", "code", "age"));

        List<Different> list = BeanUtil.compare(demo, demo2);
        assertEquals(JsonUtil.toJson(list), "[\n" +
                "\t{\n" +
                "\t\t\"fieldName\":\"name\",\n" +
                "\t\t\"fieldType\":\"java.lang.String\",\n" +
                "\t\t\"originValue\":\"zs\",\n" +
                "\t\t\"targetValue\":\"ls\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"fieldName\":\"studentId\",\n" +
                "\t\t\"fieldType\":\"java.lang.Integer\",\n" +
                "\t\t\"originValue\":1,\n" +
                "\t\t\"targetValue\":2\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"fieldName\":\"id\",\n" +
                "\t\t\"fieldType\":\"java.lang.Long\",\n" +
                "\t\t\"originValue\":1,\n" +
                "\t\t\"targetValue\":2\n" +
                "\t}\n" +
                "]");
        List<Different> list2 = BeanUtil.compareSingle(demo, demo2);
        assertEquals(JsonUtil.toJson(list2), "[\n" +
                "\t{\n" +
                "\t\t\"fieldName\":\"name\",\n" +
                "\t\t\"fieldType\":\"java.lang.String\",\n" +
                "\t\t\"originValue\":\"zs\",\n" +
                "\t\t\"targetValue\":\"ls\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"fieldName\":\"studentId\",\n" +
                "\t\t\"fieldType\":\"java.lang.Integer\",\n" +
                "\t\t\"originValue\":1,\n" +
                "\t\t\"targetValue\":2\n" +
                "\t}\n" +
                "]");

        List<Different> list3 = BeanUtil.compareSelected(demo, demo2, "id", "code", "name");
        assertEquals(JsonUtil.toJson(list3), "[\n" +
                "\t{\n" +
                "\t\t\"fieldName\":\"name\",\n" +
                "\t\t\"fieldType\":\"java.lang.String\",\n" +
                "\t\t\"originValue\":\"zs\",\n" +
                "\t\t\"targetValue\":\"ls\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"fieldName\":\"id\",\n" +
                "\t\t\"fieldType\":\"java.lang.Long\",\n" +
                "\t\t\"originValue\":1,\n" +
                "\t\t\"targetValue\":2\n" +
                "\t}\n" +
                "]");
        List<Different> list4 = BeanUtil.compareSingleSelected(demo, demo2, "id", "code", "name");
        assertEquals(JsonUtil.toJson(list4), "[\n" +
                "\t{\n" +
                "\t\t\"fieldName\":\"name\",\n" +
                "\t\t\"fieldType\":\"java.lang.String\",\n" +
                "\t\t\"originValue\":\"zs\",\n" +
                "\t\t\"targetValue\":\"ls\"\n" +
                "\t}\n" +
                "]");
    }

    /**
     * 获取类属性对象列表 是否忽略父类的属性对象
     * Method: getFields(Object bean, boolean superclass)
     */
    @Test
    public void testGetFieldsForBeanSuperclass() {
        @SuppressWarnings("rawtypes") ArrayList arrayList = new ArrayList();
        List<Field> fields = BeanUtil.getFields(arrayList, true);
        //忽略父类的属性对象
        assertEquals("DEFAULT_CAPACITY", fields.get(0).getName());
        assertEquals("DEFAULTCAPACITY_EMPTY_ELEMENTDATA", fields.get(2).getName());

        //不忽略父类的属性对象
        List<Field> fieldsFalse = BeanUtil.getFields(ArrayList.class, false);

        assertEquals("DEFAULT_CAPACITY", fieldsFalse.get(0).getName());
        assertEquals("DEFAULTCAPACITY_EMPTY_ELEMENTDATA", fieldsFalse.get(2).getName());

        assertEquals("size", fieldsFalse.get(4).getName());
    }

    /**
     * 获取类属性对象列表
     * Method: getFields(Class<?> clazz)
     */
    @Test
    public void testGetFieldsClazz() {
        List<Field> fields = BeanUtil.getFields(TestUser.class);
        assertEquals("name", fields.get(0).getName());
        assertEquals("age", fields.get(1).getName());
        assertEquals("studentId", fields.get(2).getName());
    }

    /**
     * 获取类属性对象列表 是否忽略父类属性
     * Method: getFields(Class<?> clazz, boolean superclass)
     */
    @Test
    public void testGetFieldsForClazzSuperclass() {
        List<Field> fields = BeanUtil.getFields(ArrayList.class, true);
        //忽略父类的属性对象
        assertEquals("DEFAULT_CAPACITY", fields.get(0).getName());
        assertEquals("DEFAULTCAPACITY_EMPTY_ELEMENTDATA", fields.get(2).getName());

        //不忽略父类的属性对象
        List<Field> fieldsFalse = BeanUtil.getFields(ArrayList.class, false);

        assertEquals("DEFAULT_CAPACITY", fieldsFalse.get(0).getName());
        assertEquals("DEFAULTCAPACITY_EMPTY_ELEMENTDATA", fieldsFalse.get(2).getName());

        assertEquals("size", fieldsFalse.get(4).getName());
    }

    /**
     * 往bean对象的属性注入对应的值
     * Method: setProperty(Object bean, String property, Object value)
     */
    @Test
    public void testSetProperty() {
        TestUser testUser = new TestUser();
        testUser.setAge(18);
        BeanUtil.setProperty(testUser, "name", "忘忧草涛");
        assertEquals("忘忧草涛", testUser.getName());
        assertTrue(ObjectUtil.equals(18, testUser.getAge()));
        BeanUtil.setProperty(testUser, "age", null);
        assertNull(testUser.getAge());
    }

    /**
     * Method: getSuperClassActualType(Class clazz, int index)
     */
    @SuppressWarnings("rawtypes")
    @Test
    public void testGetSuperClassActualType() {
        Class clazz1 = ArrayList.class;
        Class clazz5 = IntList.class;
        System.out.println("TEST-----------------WARN-----------------------------------------");
        assertNull(BeanUtil.getSuperClassActualType(clazz1, 0));

        //noinspection DataFlowIssue
        assertEquals("java.lang.Integer", BeanUtil.getSuperClassActualType(clazz5, 0).getName());


    }

    /**
     * Method: getProperty(Object bean, String property)
     */
    @Test
    public void testGetProperty() {
        TestUser testUser = new TestUser();
        BeanUtil.setProperty(testUser, "name", "忘忧草涛");
        assertEquals("忘忧草涛", BeanUtil.getProperty(testUser, "name"));
        assertEquals("忘忧草涛", BeanUtil.getProperty(testUser, TestUser::getName));
        assertEquals("忘忧草涛", BeanUtil.getProperty(null, TestUser::getName, "忘忧草涛"));
        assertNull(BeanUtil.getProperty(null, TestUser::getName));
    }

    @Test
    public void testGetPropertyAnnotation() {
        TestAnnotation annotation = BeanUtil.getPropertyAnnotation(Demo.class, "code", TestAnnotation.class);
        assert annotation != null;
        assertEquals("张", annotation.name());

        TestAnnotation annotation2 = BeanUtil.getPropertyAnnotation(Demo.class, "name", TestAnnotation.class);
        assert annotation2 != null;
        assertEquals("李", annotation2.name());

        TestAnnotation annotation3 = BeanUtil.getPropertyAnnotation(Demo.class, "age", TestAnnotation.class);
        assertNull(annotation3);

        TestAnnotation annotation4 = BeanUtil.getPropertyAnnotation(Demo.class, "studentId", TestAnnotation.class);
        assertNull(annotation4);
    }

}
