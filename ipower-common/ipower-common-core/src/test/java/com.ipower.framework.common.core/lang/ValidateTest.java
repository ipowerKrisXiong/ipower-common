package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.collection.Lists;
import com.ipower.framework.common.core.exception.unchecked.UncheckedException;
import com.ipower.framework.common.core.exception.unchecked.ValidateException;
import com.ipower.framework.common.core.map.Maps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Validate Tester.
 *
 * @author huangad@coracle.com
 */
public class ValidateTest {

    /**
     * Method: isTrue(final boolean expression)
     */
    @SuppressWarnings("DataFlowIssue")
    @Test
    public void testIsTrueExpression() {
        Validate.isTrue(true);
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.isTrue(false));
        assertEquals("[Validate failed] - this expression must be true", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.isTrue(false, "the value[{}] mast be true", "param"));
        assertEquals("the value[param] mast be true", e1.getMessage());
        Exception e2 = assertThrows(RuntimeException.class, () -> Validate.isTrue(false, ValidateException::new));
        assertNull(e2.getMessage());
        //noinspection DataFlowIssue
        Exception e3 = Assertions.assertThrowsExactly(ValidateException.class, () -> Validate.isTrue(false, ValidateException::new));
        assertNull(e3.getMessage());
        Exception e4 = Assertions.assertThrowsExactly(NullPointerException.class, () -> Validate.isTrue(false, null));
        assertEquals("Cannot invoke \"java.util.function.Supplier.get()\" because \"supplier\" is null", e4.getMessage());
    }

    /**
     * Method: isTrue(final boolean expression, final String message, final Object... params)
     */
    @Test
    public void testIsTrueForExpressionMessageParams() {
        String a = "Validate failed,params is not true but {}";
        String paramsA = "false";
        Validate.isTrue(true, a, paramsA);

        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.isTrue(false, a, paramsA));
        assertEquals("Validate failed,params is not true but false", e.getMessage());
        final int size = 0;
        //noinspection ConstantValue
        Validate.isTrue(size < 1, "size must not be less than one");

        final int batchSize = 2;
        //noinspection ConstantValue
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.isTrue(batchSize < 1, "batchSize must not be less than one"));
        assertEquals("batchSize must not be less than one", e1.getMessage());
    }

    /**
     * Method: isTrue(final boolean expression, final String message, final Object... params)
     */
    @Test
    public void testIsNotEquals() {
        final String a = "a", b = "a", c = "c";
        Validate.notEquals(a, b);

        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.notEquals(a, c));
        assertEquals("(a) must be not equals (c)", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.notEquals(a, c, "the param[{}] mast be not equals param[{}]", a, c));
        assertEquals("the param[a] mast be not equals param[c]", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.notEquals(a, c, () -> new ValidateException(400, StringUtil.format("the param[{}] mast be not equals param[{}]", a, c))));
        assertEquals("the param[a] mast be not equals param[c]", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.notEquals(a, c, () -> new ValidateException(400, StringUtil.format("the param[{}] mast be not equals param[{}]", a, c))));
        assertEquals("the param[a] mast be not equals param[c]", e3.getMessage());
        assertEquals(400, e3.getCode());
    }

    /**
     * Method: notNull(final T obj)
     */
    @SuppressWarnings({"DataFlowIssue", "ConstantValue"})
    @Test
    public void testNotNullObj() {
        Object obj = 1, obj1 = null;
        assertEquals(1, Validate.notNull(obj));

        //noinspection ConstantValue
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.notNull(obj1));
        assertEquals("[Validate failed] - this argument is required; it must not be null", e.getMessage());
        //noinspection ConstantValue
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.notNull(obj1, "obj is not null."));
        assertEquals("obj is not null.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.notNull(obj1, () -> new ValidateException("obj is not null.")));
        assertEquals("obj is not null.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.notNull(obj1, () -> new ValidateException(400, "obj is not null.")));
        assertEquals(400, e3.getCode());
        assertEquals("obj is not null.", e3.getMessage());
    }

    @Test
    public void testNotBlank() {
        String a = "abc", b = "", c = " ", d = "\n\t";
        assertEquals("abc", Validate.notBlank(a));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.notBlank(b));
        assertEquals("[Validate failed] - this String argument must have length; it must not be null or blank", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.notBlank(c, "the c is not blank."));
        assertEquals("the c is not blank.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.notBlank(c, () -> new ValidateException("the c is not blank.")));
        assertEquals("the c is not blank.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.notBlank(d, () -> new ValidateException(400, "the d is not blank.")));
        assertEquals(400, e3.getCode());
        assertEquals("the d is not blank.", e3.getMessage());
    }

    @Test
    public void testNotEmpty() {
        String a = "abc", b = "";
        assertEquals("abc", Validate.notEmpty(a));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.notEmpty(b));
        assertEquals("[Validate failed] - this String argument must have length; it must not be null or empty", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.notEmpty(b, "the b is not empty."));
        assertEquals("the b is not empty.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.notEmpty(b, () -> new ValidateException("the b is not empty.")));
        assertEquals("the b is not empty.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.notEmpty(b, () -> new ValidateException(400, "the b is not empty.")));
        assertEquals(400, e3.getCode());
        assertEquals("the b is not empty.", e3.getMessage());
    }

    /**
     * Method: notEmpty(final T[] array)
     */
    @SuppressWarnings("ConstantValue")
    @Test
    public void testNotEmptyArray() {
        Long[] l = new Long[5];
        assertEquals(5, Validate.notEmpty(l).length);
        Integer[] i = new Integer[5];
        assertEquals(5, Validate.notEmpty(i).length);
        Object[] array = null;
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.notEmpty(array));
        assertEquals("[Validate failed] - this array must not be empty; it must contain at least one element", e.getMessage());
        Object[] array2 = new Object[0];
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.notEmpty(array2, "array is not empty."));
        assertEquals("array is not empty.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.notEmpty(array2, () -> new ValidateException("array is not empty.")));
        assertEquals("array is not empty.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.notEmpty(array2, () -> new ValidateException(400, "array is not empty.")));
        assertEquals(400, e3.getCode());
        assertEquals("array is not empty.", e3.getMessage());
    }

    @Test
    public void testNotEmptyIterable() {
        List<String> strings = Lists.arrayList("a", "b", "c"), list = Lists.arrayList();
        assertEquals("[a, b, c]", StringUtil.toString(Validate.notEmpty(strings)));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.notEmpty(list));
        assertEquals("[Validate failed] - this iterable must not be empty; it must contain at least one element", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.notEmpty(list, "list is not empty."));
        assertEquals("list is not empty.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.notEmpty(list, () -> new ValidateException("list is not empty.")));
        assertEquals("list is not empty.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.notEmpty(list, () -> new ValidateException(400, "list is not empty.")));
        assertEquals(400, e3.getCode());
        assertEquals("list is not empty.", e3.getMessage());
    }

    @Test
    public void testNotEmptyIterator() {
        List<String> strings = Lists.arrayList("a", "b", "c"), list = Lists.arrayList();
        assertEquals("a", StringUtil.toString(Validate.notEmpty(strings.iterator()).next()));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.notEmpty(list.iterator()));
        assertEquals("[Validate failed] - this iterator must not be empty; it must contain at least one element", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.notEmpty(list.iterator(), "list is not empty."));
        assertEquals("list is not empty.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.notEmpty(list.iterator(), () -> new ValidateException("list is not empty.")));
        assertEquals("list is not empty.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.notEmpty(list.iterator(), () -> new ValidateException(400, "list is not empty.")));
        assertEquals(400, e3.getCode());
        assertEquals("list is not empty.", e3.getMessage());
    }

    @Test
    public void testNotEmptyMap() {
        Map<String, Object> map = Maps.hashMap("a", 1), map1 = Maps.concurrentMap();
        assertEquals(1, Validate.notEmpty(map).get("a"));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.notEmpty(map1));
        assertEquals("[Validate failed] - this map must not be empty; it must contain at least one entry", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.notEmpty(map1, "map is not empty."));
        assertEquals("map is not empty.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.notEmpty(map1, () -> new ValidateException("map is not empty.")));
        assertEquals("map is not empty.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.notEmpty(map1, () -> new ValidateException(400, "map is not empty.")));
        assertEquals(400, e3.getCode());
        assertEquals("map is not empty.", e3.getMessage());
    }

    @Test
    public void testNoNullElementsArray() {
        String[] strings = {"a", "b", "c"}, array = new String[]{"a", "b", "c", null};
        assertEquals("b", Validate.noNullElements(strings)[1]);
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.noNullElements(array));
        assertEquals("[Validate failed] - this array must not contain any null elements", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.noNullElements(array, "the array has null element."));
        assertEquals("the array has null element.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.noNullElements(array, () -> new ValidateException("the array has null element.")));
        assertEquals("the array has null element.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.noNullElements(array, () -> new ValidateException(400, "the array has null element.")));
        assertEquals(400, e3.getCode());
        assertEquals("the array has null element.", e3.getMessage());
    }

    @Test
    public void testNoNullElementsIterable() {
        Set<String> strings = Lists.hashSet("a", "b", "c"), sets = Lists.hashSet("a", "b", "c", null);
        Set<String> result = Validate.noNullElements(strings);
        assertTrue(result.contains("c"));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.noNullElements(sets));
        assertEquals("[Validate failed] - this collection must not contain any null elements", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.noNullElements(sets, "the set has null element."));
        assertEquals("the set has null element.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.noNullElements(sets, () -> new ValidateException("the set has null element.")));
        assertEquals("the set has null element.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.noNullElements(sets, () -> new ValidateException(400, "the set has null element.")));
        assertEquals(400, e3.getCode());
        assertEquals("the set has null element.", e3.getMessage());
    }

    @Test
    public void testIsInstanceOfForTypeObj() {
        String string = "5";
        Integer integer = 5;
        assertEquals("5", Validate.isInstanceOf(String.class, string));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.isInstanceOf(String.class, integer));
        assertEquals("Object [5] is not instanceof [class java.lang.String]", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.isInstanceOf(String.class, integer, "the value is not instanceof String."));
        assertEquals("the value is not instanceof String.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.isInstanceOf(String.class, integer, () -> new ValidateException("the value is not instanceof String.")));
        assertEquals("the value is not instanceof String.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.isInstanceOf(String.class, integer, () -> new ValidateException(400, "the value is not instanceof String.")));
        assertEquals(400, e3.getCode());
        assertEquals("the value is not instanceof String.", e3.getMessage());
    }

    @Test
    public void testIsAssignableForSuperTypeSubType() {
        Class<?> clazz = List.class, clazz1 = ArrayList.class, clazz2 = HashMap.class;
        Validate.isAssignable(clazz, clazz1);
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.isAssignable(clazz, clazz2));
        assertEquals("[class java.util.HashMap] is not assignable to [interface java.util.List]", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.isAssignable(clazz, clazz2, "the class HashMap is not assignable to interface List."));
        assertEquals("the class HashMap is not assignable to interface List.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.isAssignable(clazz, clazz2, () -> new ValidateException("the class HashMap is not assignable to interface List.")));
        assertEquals("the class HashMap is not assignable to interface List.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.isAssignable(clazz, clazz2, () -> new ValidateException(400, "the class HashMap is not assignable to interface List.")));
        assertEquals(400, e3.getCode());
        assertEquals("the class HashMap is not assignable to interface List.", e3.getMessage());
    }

    @Test
    public void testCheckIndexForCharsIndex() {
        String s = " ", s1 = "abc";
        assertEquals(" ", Validate.checkIndex(s, 0));
        assertEquals("abc", Validate.checkIndex(s1, 2));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.checkIndex(s1, 3));
        assertEquals("[Validate failed] - The validated character sequence index is invalid: 3", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.checkIndex(s1, 3, "validated the index is invalid."));
        assertEquals("validated the index is invalid.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.checkIndex(s1, 3, () -> new ValidateException("validated the index is invalid.")));
        assertEquals("validated the index is invalid.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.checkIndex(s1, 3, () -> new ValidateException(400, "validated the index is invalid.")));
        assertEquals(400, e3.getCode());
        assertEquals("validated the index is invalid.", e3.getMessage());
    }

    @Test
    public void testCheckIndexForArrayIndex() {
        String[] s = {" ", ""}, s1 = {"abc", "123", "admin"};
        assertEquals("", Validate.checkIndex(s, 0)[1]);
        assertEquals("admin", Validate.checkIndex(s1, 2)[2]);
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.checkIndex(s1, 3));
        assertEquals("[Validate failed] - The validated array index is invalid: 3", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.checkIndex(s1, 3, "validated the index is invalid."));
        assertEquals("validated the index is invalid.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.checkIndex(s1, 3, () -> new ValidateException("validated the index is invalid.")));
        assertEquals("validated the index is invalid.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.checkIndex(s1, 3, () -> new ValidateException(400, "validated the index is invalid.")));
        assertEquals(400, e3.getCode());
        assertEquals("validated the index is invalid.", e3.getMessage());
    }

    @Test
    public void testCheckIndexForCollectionIndex() {
        List<Integer> s = Lists.arrayList(1, 2), s1 = Lists.arrayList(4, 5, 6);
        assertEquals(1, Validate.checkIndex(s, 0).get(0));
        assertEquals(6, Validate.checkIndex(s1, 2).get(2));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.checkIndex(s1, 3));
        assertEquals("[Validate failed] - The validated collection index is invalid: 3", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.checkIndex(s1, 3, "validated the index is invalid."));
        assertEquals("validated the index is invalid.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.checkIndex(s1, 3, () -> new ValidateException("validated the index is invalid.")));
        assertEquals("validated the index is invalid.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.checkIndex(s1, 3, () -> new ValidateException(400, "validated the index is invalid.")));
        assertEquals(400, e3.getCode());
        assertEquals("validated the index is invalid.", e3.getMessage());
    }

    @Test
    public void testCheckBetweenForValueIntMinMax() {
        int value = 55, max = 60, min = 40;
        assertEquals(55, Validate.checkBetween(value, min, max));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.checkBetween(value, 60, 70));
        assertEquals("The value must be between 60 and 70.", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.checkBetween(value, 60, 70, "validated the value between is invalid."));
        assertEquals("validated the value between is invalid.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.checkBetween(value, 60, 70, () -> new ValidateException("validated the value between is invalid.")));
        assertEquals("validated the value between is invalid.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.checkBetween(value, 60, 70, () -> new ValidateException(400, "validated the value between is invalid.")));
        assertEquals(400, e3.getCode());
        assertEquals("validated the value between is invalid.", e3.getMessage());
    }

    @Test
    public void testCheckBetweenForValueLongMinMax() {
        long value = 55L, max = 60L, min = 40L;
        assertEquals(55L, Validate.checkBetween(value, min, max));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.checkBetween(value, 60L, 70L));
        assertEquals("The value must be between 60 and 70.", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.checkBetween(value, 60L, 70L, "validated the value between is invalid."));
        assertEquals("validated the value between is invalid.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.checkBetween(value, 60L, 70L, () -> new ValidateException("validated the value between is invalid.")));
        assertEquals("validated the value between is invalid.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.checkBetween(value, 60L, 70L, () -> new ValidateException(400, "validated the value between is invalid.")));
        assertEquals(400, e3.getCode());
        assertEquals("validated the value between is invalid.", e3.getMessage());
    }

    @Test
    public void testCheckBetweenForValueFloatMinMax() {
        float value = 55.0F, max = 60.0F, min = 40.0F;
        assertEquals(55.0F, Validate.checkBetween(value, min, max));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.checkBetween(value, 60F, 70F));
        assertEquals("The value must be between 60.0 and 70.0.", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.checkBetween(value, 60F, 70F, "validated the value between is invalid."));
        assertEquals("validated the value between is invalid.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.checkBetween(value, 60F, 70F, () -> new ValidateException("validated the value between is invalid.")));
        assertEquals("validated the value between is invalid.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.checkBetween(value, 60F, 70F, () -> new ValidateException(400, "validated the value between is invalid.")));
        assertEquals(400, e3.getCode());
        assertEquals("validated the value between is invalid.", e3.getMessage());
    }

    @Test
    public void testCheckBetweenForValueDoubleMinMax() {
        double value = 55.0D, max = 60.0D, min = 40.0D;
        assertEquals(55.0D, Validate.checkBetween(value, min, max));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.checkBetween(value, 60D, 70D));
        assertEquals("The value must be between 60.0 and 70.0.", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.checkBetween(value, 60D, 70D, "validated the value between is invalid."));
        assertEquals("validated the value between is invalid.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.checkBetween(value, 60D, 70D, () -> new ValidateException("validated the value between is invalid.")));
        assertEquals("validated the value between is invalid.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.checkBetween(value, 60D, 70D, () -> new ValidateException(400, "validated the value between is invalid.")));
        assertEquals(400, e3.getCode());
        assertEquals("validated the value between is invalid.", e3.getMessage());
    }

    @Test
    public void testCheckBetweenForValueBigDecimalMinMax() {
        BigDecimal value = new BigDecimal("55.0"), max = new BigDecimal("60.0"), min = new BigDecimal("40.0");
        assertEquals(new BigDecimal("55.0"), Validate.checkBetween(value, min, max));
        Exception e = assertThrows(IllegalArgumentException.class, () -> Validate.checkBetween(value, new BigDecimal("60.0"), new BigDecimal("70.0")));
        assertEquals("The value must be between 60.0 and 70.0.", e.getMessage());
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> Validate.checkBetween(value, new BigDecimal("60.0"), new BigDecimal("70.0"), "validated the value between is invalid."));
        assertEquals("validated the value between is invalid.", e1.getMessage());
        Exception e2 = assertThrowsExactly(ValidateException.class, () -> Validate.checkBetween(value, new BigDecimal("60.0"), new BigDecimal("70.0"), () -> new ValidateException("validated the value between is invalid.")));
        assertEquals("validated the value between is invalid.", e2.getMessage());
        UncheckedException e3 = assertThrows(UncheckedException.class, () -> Validate.checkBetween(value, new BigDecimal("60.0"), new BigDecimal("70.0"), () -> new ValidateException(400, "validated the value between is invalid.")));
        assertEquals(400, e3.getCode());
        assertEquals("validated the value between is invalid.", e3.getMessage());
    }

}
