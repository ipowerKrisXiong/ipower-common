package com.ipower.framework.common.core.convert;

import com.ipower.framework.common.core.entity.TestEnum;
import com.ipower.framework.common.core.entity.TestUser;
import com.ipower.framework.common.core.lang.Validate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Convert Tester.
 *
 * @author huangad@coracle.com
 */
public class ConvertTest {

    /**
     * 转换为字符串
     * Method: toString(Object value)
     */
    @Test
    public void testToStringValue() {
        Long l = 9L;
        assertEquals("9", Convert.toString(l));

        assertNull(Convert.toString(null));


    }

    /**
     * 转换为字符串
     * Method: toString(Object value, String defaultValue)
     */
    @Test
    public void testToStringForValueDefaultValue() {
        Long l = 9L;
        assertEquals("9", Convert.toString(l, "0"));

        assertEquals("0", Convert.toString(null, "0"));


    }

    /**
     * 转换为String数组
     * Method: toStringArray(Object value)
     */
    @Test
    public void testToStringArray() {
        Integer[] integers = new Integer[]{1, 2, 3};
        String[] strings = Convert.toStringArray(integers);
        assertEquals("[1, 2, 3]", Arrays.toString(strings));
    }

    /**
     * 转换成字符
     * Method: toChar(Object value, Character defaultValue)
     */
    @Test
    public void testToCharForValueDefaultValue() {
        Integer value = 99;
        Character character = 'a';
        Character character1 = Convert.toChar(value, character);
        Validate.isTrue(character1 == '9');

        assertNull(Convert.toChar(null));
    }

    /**
     * 转换成字符
     * Method: toChar(Object value)
     */
    @Test
    public void testToCharValue() {
        String value = "bab";
        Character character = Convert.toChar(value);
        Validate.isTrue(character == 'b');

    }

    /**
     * 转成Character数组
     * Method: toCharArray(Object value)
     */
    @Test
    public void testToCharArray() {
        String s = "hello";
        Character[] characters = Convert.toCharArray(s);
        assertEquals("[h, e, l, l, o]", Arrays.toString(characters));
    }

    /**
     * Method: toByte(Object value, Byte defaultValue)
     */
    @Test
    public void testToByteForValueDefaultValue() {
        Byte defaultValue = 0;
        assertEquals("0", Convert.toByte(null, defaultValue).toString());
        Double d = 55d;
        assertEquals("55", Convert.toByte(d, defaultValue).toString());
    }

    /**
     * 转换为byte
     * Method: toByte(Object value)
     */
    @Test
    public void testToByteValue() {
        assertNull(Convert.toByte(null));
        Double d = 55d;
        assertEquals("55", Convert.toByte(d).toString());
    }

    /**
     * 转为Byte数组
     * Method: toByteArray(Object value)
     */
    @Test
    public void testToByteArray() {
        Integer[] integers = new Integer[]{5, 7, 9};
        assertEquals("[5, 7, 9]", Arrays.toString(Convert.toByteArray(integers)));
    }

    /**
     * 转成short
     * Method: toShort(Object value, Short defaultValue)
     */
    @Test
    public void testToShortForValueDefaultValue() {
        Short defaultValue = 9;
        assertEquals("9", Convert.toShort(null, defaultValue).toString());

        Short s = 100;
        assertEquals("100", Convert.toShort(s, defaultValue).toString());
    }

    /**
     * 转成short
     * Method: toShort(Object value)
     */
    @Test
    public void testToShortValue() {
        assertNull(Convert.toShort(null));

        Short s = 100;
        assertEquals("100", Convert.toShort(s).toString());
    }

    /**
     * 转成short数组
     * Method: toShortArray(Object value)
     */
    @Test
    public void testToShortArray() {
        Double[] doubles = new Double[]{4d, 6d};
        assertEquals("[4, 6]", Arrays.toString(Convert.toShortArray(doubles)));
    }

    /**
     * 转成Number
     * Method: toNumber(Object value, Number defaultValue)
     */
    @Test
    public void testToNumberForValueDefaultValue() {
        Number defaultValue = 0;
        assertEquals(defaultValue, Convert.toNumber(null, defaultValue));

        char a = 'b', b = '7';
        assertEquals(defaultValue, Convert.toNumber(a, defaultValue));

        Number number = Convert.toNumber(b, defaultValue);
        Number number1 = 7;
        assertEquals(number.intValue(), number1);
    }

    /**
     * 转成Number
     * Method: toNumber(Object value)
     */
    @Test
    public void testToNumberValue() {
        assertNull(Convert.toNumber(null));

        char a = 'b', b = '7';
        assertNull(Convert.toNumber(a));

        Number number = Convert.toNumber(b);
        Number number1 = 7;
        assertEquals(number.intValue(), number1);
    }

    /**
     * 转成Number数组
     * Method: toNumberArray(Object value)
     */
    @Test
    public void testToNumberArray() {
        Integer[] integers = new Integer[]{1, 2, 3};
        Number[] numbers = Convert.toNumberArray(integers);

        assertEquals("[1, 2, 3]", Arrays.toString(numbers));
    }

    /**
     * 转换为int
     * Method: toInteger(Object value, Integer defaultValue)
     */
    @Test
    public void testToIntegerForValueDefaultValue() {
        Double dd = 89d;
        Integer defaultValue = 0;
        Integer integer = Convert.toInteger(dd, defaultValue);

        Validate.isTrue(integer == 89);
        Validate.isTrue(0 == Convert.toInteger(null, defaultValue));
    }

    /**
     * 转换为int
     * Method: toInteger(Object value)
     */
    @Test
    public void testToIntegerValue() {
        float f = 44f;
        Validate.isTrue(44 == Convert.toInteger(f));

        assertNull(Convert.toInteger(null));
    }

    /**
     * 转换为int数组
     * Method: toIntegerArray(Object value)
     */
    @Test
    public void testToIntegerArray() {
        Double[] doubles = new Double[]{1d, 2d, 3d};
        Number[] numbers = Convert.toIntegerArray(doubles);

        assertEquals("[1, 2, 3]", Arrays.toString(numbers));
    }

    /**
     * 转换为long
     * Method: toLong(Object value, Long defaultValue)
     */
    @Test
    public void testToLongForValueDefaultValue() {
        Double dd = 89d;
        Long defaultValue = 0L;
        Long aLong = Convert.toLong(dd, defaultValue);

        Validate.isTrue(aLong == 89L);
        Validate.isTrue(0L == Convert.toLong(null, defaultValue));
    }

    /**
     * 转换为long
     * Method: toLong(Object value)
     */
    @Test
    public void testToLongValue() {
        float f = 44f;
        Validate.isTrue(44L == Convert.toLong(f));

        assertNull(Convert.toLong(null));
    }

    /**
     * 转换为long数组
     * Method: toLongArray(Object value)
     */
    @Test
    public void testToLongArray() {
        Double[] doubles = new Double[]{1d, 2d, 3d};
        Number[] numbers = Convert.toIntegerArray(doubles);

        assertEquals("[1, 2, 3]", Arrays.toString(numbers));
    }

    /**
     * 转换为double
     * Method: toDouble(Object value, Double defaultValue)
     */
    @Test
    public void testToDoubleForValueDefaultValue() {
        Integer dd = 89;
        Double defaultValue = 0d;
        Double aDouble = Convert.toDouble(dd, defaultValue);

        Validate.isTrue(aDouble == 89d);
        Validate.isTrue(0d == Convert.toDouble(null, defaultValue));
    }

    /**
     * 转换为double
     * Method: toDouble(Object value)
     */
    @Test
    public void testToDoubleValue() {
        float f = 44f;
        Validate.isTrue(44d == Convert.toDouble(f));

        assertNull(Convert.toDouble(null));
    }

    /**
     * 转成double数组
     * Method: toDoubleArray(Object value)
     */
    @Test
    public void testToDoubleArray() {
        Integer dd = 89;
        Double defaultValue = 0d;
        Double aDouble = Convert.toDouble(dd, defaultValue);

        Validate.isTrue(aDouble == 89f);
        Validate.isTrue(0f == Convert.toDouble(null, defaultValue));
    }

    /**
     * 转出float
     * Method: toFloat(Object value, Float defaultValue)
     */
    @Test
    public void testToFloatForValueDefaultValue() {

    }

    /**
     * 转成float
     * Method: toFloat(Object value)
     */
    @Test
    public void testToFloatValue() {
        double f = 44d;
        Validate.isTrue(44f == Convert.toFloat(f));

        assertNull(Convert.toFloat(null));
    }

    /**
     * 转成float数组
     * Method: toFloatArray(Object value)
     */
    @Test
    public void testToFloatArray() {
        Integer dd = 89;
        Float defaultValue = 0f;
        Float aFloat = Convert.toFloat(dd, defaultValue);

        Validate.isTrue(aFloat == 89f);
        Validate.isTrue(0f == Convert.toFloat(null, defaultValue));
    }

    /**
     * 转成boolean
     * Method: toBool(Object value, Boolean defaultValue)
     */
    @Test
    public void testToBoolForValueDefaultValue() {
        Integer integer = 0;
        Boolean aBoolean = Convert.toBool(integer, true);
        Boolean aBoolean1 = Convert.toBool(null, true);
        assertEquals(false, aBoolean);
        assertEquals(true, aBoolean1);
    }

    /**
     * 转成boolean
     * Method: toBool(Object value)
     */
    @Test
    public void testToBoolValue() {
        Integer integer = 0;
        Boolean aBoolean = Convert.toBool(integer);
        assertEquals(false, aBoolean);

        Integer integer1 = 1;
        Boolean aBoolean1 = Convert.toBool(integer1);
        assertEquals(true, aBoolean1);

        assertNull(Convert.toBool(null));
    }

    /**
     * 转成boolean数组
     * Method: toBooleanArray(Object value)
     */
    @Test
    public void testToBooleanArray() {
        Integer[] integers = new Integer[]{1, 2, 3};
        Boolean[] booleans = Convert.toBooleanArray(integers);
        assertEquals("[true, false, false]", Arrays.toString(booleans));
    }

    /**
     * 转成BigInteger
     * Method: toBigInteger(Object value, BigInteger defaultValue)
     */
    @Test
    public void testToBigIntegerForValueDefaultValue() {
        Integer integer = 6;
        BigInteger defaultValue = new BigInteger("0");
        BigInteger bigInteger = Convert.toBigInteger(integer, defaultValue);
        BigInteger bigInteger1 = Convert.toBigInteger(null, defaultValue);

        assertEquals(6, bigInteger.intValue());
        assertEquals(0, bigInteger1.intValue());
    }

    /**
     * 转成BigInteger
     * Method: toBigInteger(Object value)
     */
    @Test
    public void testToBigIntegerValue() {
        Integer integer = 6;
        BigInteger bigInteger = Convert.toBigInteger(integer);
        BigInteger bigInteger1 = Convert.toBigInteger(null);

        assertEquals(6, bigInteger.intValue());
        assertNull(bigInteger1);
    }

    /**
     * 转成BigDecimal
     * Method: toBigDecimal(Object value, BigDecimal defaultValue)
     */
    @Test
    public void testToBigDecimalForValueDefaultValue() {
        Integer integer = 6;
        BigDecimal defaultValue = new BigDecimal("0");
        BigDecimal bigDecimal = Convert.toBigDecimal(integer, defaultValue);
        BigDecimal bigDecimal1 = Convert.toBigDecimal(null, defaultValue);

        assertEquals(6, bigDecimal.intValue());
        assertEquals(0, bigDecimal1.intValue());
    }

    /**
     * 转成BigDecimal
     * Method: toBigDecimal(Object value)
     */
    @Test
    public void testToBigDecimalValue() {
        Integer integer = 6;
        BigDecimal bigDecimal = Convert.toBigDecimal(integer);
        BigDecimal bigDecimal1 = Convert.toBigDecimal(null);

        assertEquals(6, bigDecimal.intValue());
        assertNull(bigDecimal1);
    }

    /**
     * 转成Enum
     * Method: toEnum(Class<E> clazz, Object value, E defaultValue)
     */
    @Test
    public void testToEnumForClazzValueDefaultValue() {
        TestEnum defaultValue = TestEnum.SUN;
        Integer integer = 100;
        TestEnum testEnum = Convert.toEnum(TestEnum.class, TestEnum.MON, defaultValue);
        TestEnum testEnum1 = Convert.toEnum(TestEnum.class, null, defaultValue);
        TestEnum testEnum2 = Convert.toEnum(TestEnum.class, integer, defaultValue);

        assertEquals("MON", testEnum.toString());
        assertEquals("SUN", testEnum1.toString());
        assertEquals("SUN", testEnum2.toString());

    }

    /**
     * 转成Enum
     * Method: toEnum(Class<E> clazz, Object value)
     */
    @Test
    public void testToEnumForClazzValue() {
        TestEnum testEnum = Convert.toEnum(TestEnum.class, TestEnum.MON);
        assertEquals("MON", testEnum.toString());

        assertNull(Convert.toEnum(TestEnum.class, null));

        assertThrowsExactly(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                assertNull(Convert.convert(TestEnum.class, 1000));
            }
        });
    }

    /**
     * 转成集合
     * Method: toCollection(Class<?> collectionType, Class<?> elementType, Object value)
     */
    @Test
    public void testToCollection() {
        Integer[] integers = new Integer[]{7, 9, 1};
        Collection<?> objects = Convert.toCollection(List.class, Integer.class, integers);

        assertEquals("[7, 9, 1]", objects.toString());
    }

    /**
     * 转成List
     * Method: toList(Object value)
     */
    @Test
    public void testToListValue() {
        Integer integer = 100;
        List<?> objects = Convert.toList(integer);

        assertEquals("[100]", objects.toString());
    }

    /**
     * 转成List
     * Method: toList(Class<T> elementType, Object value)
     */
    @Test
    public void testToListForElementTypeValue() {
        TestUser testUser1 = new TestUser();
        TestUser testUser2 = new TestUser();
        TestUser testUser3 = new TestUser();
        testUser1.setAge(18);
        testUser2.setAge(19);
        testUser3.setAge(20);
        TestUser[] testUsers = new TestUser[]{testUser1, testUser2, testUser3};
        List<TestUser> list = Convert.toList(TestUser.class, testUsers);
        assertEquals("[TestUser(name=null, age=18, studentId=null, id=null), TestUser(name=null, age=19, studentId=null, id=null), TestUser(name=null, age=20, studentId=null, id=null)]", list
                .toString());
    }

    /**
     * 转换值为指定类型
     * Method: convert(Class<T> type, Object value)
     */
    @Test
    public void testConvertForTypeValue() {
        //String<--->Integer
        String s = "66";
        String s1 = "66.0";
        Integer integer = 66;
        assertEquals(integer, Convert.convert(Integer.class, s));
        assertEquals(s, Convert.convert(String.class, integer));
        //String<-->Long
        Long along = 66L;
        assertEquals(along, Convert.convert(Long.class, s));
        assertEquals(s, Convert.convert(String.class, along));
        //String<--->Double
        Double aDouble = 66d;
        assertEquals(aDouble, Convert.convert(Double.class, s1));
        assertEquals(s1, Convert.convert(String.class, aDouble));
        //String<--->Float
        Float aFloat = 66f;
        assertEquals(aFloat, Convert.convert(Float.class, s1));
        assertEquals(s1, Convert.convert(String.class, aFloat));


    }

    /**
     * 转换值为指定类型
     * Method: convert(TypeReference<T> reference, Object value)
     */
    @Test
    public void testConvertForReferenceValue() {

    }

    /**
     * 转换值为指定类型
     * Method: convert(Class<T> type, Object value, T defaultValue)
     */
    @Test
    public void testConvertForTypeValueDefaultValue() {
        //String<--->Integer
        String s = "66", s1 = "66.0", defaultStr = "0";
        Integer integer = 66, defaultInt = 0;
        assertEquals(integer, Convert.convert(Integer.class, s, defaultInt));
        assertEquals(s, Convert.convert(String.class, integer));
        //String<-->Long
        Long along = 66L, defaultLong = 0L;
        assertEquals(along, Convert.convert(Long.class, s, defaultLong));
        assertEquals(s, Convert.convert(String.class, along, defaultStr));
        //String<--->Double
        Double aDouble = 66d, defaultDouble = 0d;
        assertEquals(aDouble, Convert.convert(Double.class, s1, defaultDouble));
        assertEquals(s1, Convert.convert(String.class, aDouble, defaultStr));
        //String<--->Float
        Float aFloat = 66f, defaultFloat = 0f;
        assertEquals(aFloat, Convert.convert(Float.class, s1, defaultFloat));
        assertEquals(s1, Convert.convert(String.class, aFloat, defaultStr));


    }

    /**
     * 转换值为指定类型，不抛异常转换
     * 转换失败时返回 null
     * Method: convertQuietly(Type type, Object value)
     */
    @Test
    public void testConvertQuietlyForTypeValue() {
        Object object = Convert.convertQuietly(Map.class, true);
        Object object1 = Convert.convertQuietly(Integer.class, true);
        assertNull(object);
        assertEquals(1, object1);
    }

    /**
     * 转换值为指定类型，不抛异常转换
     * Method: convertQuietly(Type type, Object value, T defaultValue)
     */
    @Test
    public void testConvertQuietlyForTypeValueDefaultValue() {
        @SuppressWarnings("rawtypes") Map defaultMap = new HashMap();
        Integer defaultI = 0;
        Object object = Convert.convertQuietly(Map.class, true, defaultMap);
        Object object1 = Convert.convertQuietly(Integer.class, true, defaultI);

        assertNotNull(object);
        assertEquals(1, object1);
    }

    /**
     * 原始类转为包装类，非原始类返回原类
     * Method: wrap(Class<?> clazz)
     */
    @Test
    public void testWrap() {
        assertEquals(Integer.class, Convert.wrap(int.class));
        assertEquals(Byte.class, Convert.wrap(byte.class));
        assertEquals(Short.class, Convert.wrap(short.class));
        assertEquals(Long.class, Convert.wrap(long.class));
        assertEquals(Float.class, Convert.wrap(float.class));
        assertEquals(Double.class, Convert.wrap(double.class));
        assertEquals(Boolean.class, Convert.wrap(boolean.class));
        assertEquals(Character.class, Convert.wrap(char.class));

        assertEquals(List.class, Convert.wrap(List.class));
    }

    /**
     * 包装类转为原始类，非包装类返回原类
     * Method: unWrap(Class<?> clazz)
     */
    @Test
    public void testUnWrap() {
        assertEquals(int.class, Convert.unWrap(Integer.class));
        assertEquals(byte.class, Convert.unWrap(Byte.class));
        assertEquals(short.class, Convert.unWrap(Short.class));
        assertEquals(long.class, Convert.unWrap(Long.class));
        assertEquals(float.class, Convert.unWrap(Float.class));
        assertEquals(double.class, Convert.unWrap(Double.class));
        assertEquals(boolean.class, Convert.unWrap(Boolean.class));
        assertEquals(char.class, Convert.unWrap(Character.class));

        assertEquals(List.class, Convert.unWrap(List.class));
    }

    /**
     * 将阿拉伯数字转为英文表达方式
     * Method: numberToWord(Number number)
     */
    @Test
    public void testNumberToWord() {
        double d = 88.8888d;
        Number number = 89;
        assertEquals("EIGHTY NINE ONLY", Convert.numberToWord(number));
        assertEquals("EIGHTY EIGHT AND CENTS EIGHTY EIGHT ONLY", Convert.numberToWord(d));
    }

    /**
     * 将阿拉伯数字转为中文表达方式
     * Method: numberToChinese(double number, boolean isUseTraditonal)
     */
    @Test
    public void testNumberToChinese() {
        double d = 88.8888d;
        assertEquals("捌拾捌点捌玖", Convert.numberToChinese(d, true));
        assertEquals("八十八点八九", Convert.numberToChinese(d, false));

    }

    /**
     * 金额转为中文形式
     * Method: digitToChinese(Number n)
     */
    @Test
    public void testDigitToChinese() {
        Number number = 888.8d;
        Integer i = 1000;
        assertEquals("捌佰捌拾捌元捌角", Convert.digitToChinese(number));
        assertEquals("壹仟元整", Convert.digitToChinese(i));
    }

}
