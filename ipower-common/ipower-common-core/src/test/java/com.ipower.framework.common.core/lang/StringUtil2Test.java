package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.collection.Lists;
import com.ipower.framework.common.core.constant.CharPool;
import com.ipower.framework.common.core.constant.StringPool;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * StringUtil Tester.
 *
 * @author huangad@coracle.com
 */
public class StringUtil2Test {

    /**
     * Method: upperCase(final String param)
     */
    @Test
    public void testUpperCase() {
        String param = "test";
        assertEquals("TEST", StringUtil.upperCase(param));
        param = "";
        assertEquals(StringPool.EMPTY, StringUtil.upperCase(param));
    }

    /**
     * Method: upperCase(final Collection<String> params)
     */
    @Test
    public void testUpperCaseList() {
        List<String> params = Lists.arrayList("test", "ADMIN", "userName", "Upper Case", "", null);
        List<String> results = StringUtil.upperCase(params);
        assertEquals("TEST", results.get(0));
        assertEquals("ADMIN", results.get(1));
        assertEquals("USERNAME", results.get(2));
        assertEquals("UPPER CASE", results.get(3));
        assertEquals("", results.get(4));
        assertEquals("", results.get(5));
    }

    /**
     * Method: upperCase(final String... params)
     */
    @Test
    public void testUpperCaseArray() {
        String[] array = new String[]{"test", "ADMIN", "userName", "Upper Case", "", null};
        String[] results = StringUtil.upperCase(array);
        assertEquals("TEST", results[0]);
        assertEquals("ADMIN", results[1]);
        assertEquals("USERNAME", results[2]);
        assertEquals("UPPER CASE", results[3]);
        assertEquals("", results[4]);
        assertEquals("", results[5]);
    }

    /**
     * Method: upperFirst(final String param)
     */
    @Test
    public void testUpperFirst() {
        String param = "test";
        assertEquals("Test", StringUtil.upperFirst(param));
        param = "";
        assertEquals(StringPool.EMPTY, StringUtil.upperFirst(param));
    }

    /**
     * Method: upperFirst(final Collection<String> params)
     */
    @Test
    public void testUpperFirstList() {
        List<String> params = Lists.arrayList("test", "ADMIN", "userName", "Upper Case", "", null);
        List<String> results = StringUtil.upperFirst(params);
        assertEquals("Test", results.get(0));
        assertEquals("ADMIN", results.get(1));
        assertEquals("UserName", results.get(2));
        assertEquals("Upper Case", results.get(3));
        assertEquals("", results.get(4));
        assertEquals("", results.get(5));
    }

    /**
     * Method: upperFirst(final String... params)
     */
    @Test
    public void testUpperFirstArray() {
        String[] array = new String[]{"test", "ADMIN", "userName", "Upper Case", "", null};
        String[] results = StringUtil.upperFirst(array);
        assertEquals("Test", results[0]);
        assertEquals("ADMIN", results[1]);
        assertEquals("UserName", results[2]);
        assertEquals("Upper Case", results[3]);
        assertEquals("", results[4]);
        assertEquals("", results[5]);
    }

    /**
     * Method: lowerCase(final String param)
     */
    @Test
    public void testLowerCase() {
        String param = "Test";
        assertEquals("test", StringUtil.lowerCase(param));
        param = "";
        assertEquals(param, StringUtil.lowerFirst(param));
    }

    /**
     * Method: lowerCase(final Collection<String> params)
     */
    @Test
    public void testLowerCaseList() {
        List<String> params = Lists.arrayList("test", "ADMIN", "userName", "Upper Case", "", null);
        List<String> results = StringUtil.lowerCase(params);
        assertEquals("test", results.get(0));
        assertEquals("admin", results.get(1));
        assertEquals("username", results.get(2));
        assertEquals("upper case", results.get(3));
        assertEquals("", results.get(4));
        assertEquals("", results.get(5));
    }

    /**
     * Method: lowerCase(final String... params)
     */
    @Test
    public void testLowerCaseArray() {
        String[] array = new String[]{"test", "ADMIN", "userName", "Upper Case", "", null};
        String[] results = StringUtil.lowerCase(array);
        assertEquals("test", results[0]);
        assertEquals("admin", results[1]);
        assertEquals("username", results[2]);
        assertEquals("upper case", results[3]);
        assertEquals("", results[4]);
        assertEquals("", results[5]);
    }

    /**
     * Method: lowerFirst(final String param)
     */
    @Test
    public void testLowerFirst() {
        String param = "Test";
        assertEquals("test", StringUtil.lowerFirst(param));
        param = "";
        assertEquals(param, StringUtil.lowerFirst(param));
    }

    /**
     * Method: lowerFirst(final Collection<String> params)
     */
    @Test
    public void testLowerFirstLit() {
        List<String> params = Lists.arrayList("test", "ADMIN", "userName", "Upper Case", "", null);
        List<String> results = StringUtil.lowerFirst(params);
        assertEquals("test", results.get(0));
        //noinspection SpellCheckingInspection
        assertEquals("aDMIN", results.get(1));
        assertEquals("userName", results.get(2));
        assertEquals("upper Case", results.get(3));
        assertEquals("", results.get(4));
        assertEquals("", results.get(5));
    }

    /**
     * Method: lowerFirst(final String... params)
     */
    @Test
    public void testLowerFirstArray() {
        String[] array = new String[]{"test", "ADMIN", "userName", "Upper Case", "", null};
        String[] results = StringUtil.lowerFirst(array);
        assertEquals("test", results[0]);
        //noinspection SpellCheckingInspection
        assertEquals("aDMIN", results[1]);
        assertEquals("userName", results[2]);
        assertEquals("upper Case", results[3]);
        assertEquals("", results[4]);
        assertEquals("", results[5]);
    }

    /**
     * 测试驼峰字符转下划线
     */
    @Test
    public void testCamelToUnderline() {
        String str = "camelCharacter";
        assertEquals("camel_character", StringUtil.camelToUnderline(str));
        str = "ADMIN";
        assertEquals("a_d_m_i_n", StringUtil.camelToUnderline(str));
    }

    /**
     * 测试驼峰字符转下划线
     */
    @Test
    public void testCamelListToUnderline() {
        List<String> params = Lists.arrayList("camelCharacter", "testCamelListToUnderline", "ADMIN", "", null);
        List<String> results = StringUtil.camelToUnderline(params);
        assertEquals("camel_character", results.get(0));
        assertEquals("test_camel_list_to_underline", results.get(1));
        assertEquals("a_d_m_i_n", results.get(2));
        assertEquals("", results.get(3));
        assertEquals("", results.get(4));
    }

    /**
     * 测试驼峰字符数组转下划线
     */
    @Test
    public void testCamelArrayToUnderline() {
        String[] str = new String[]{"camelCharacter", "testCamelListToUnderline", "ADMIN", "", null};
        String[] results = StringUtil.camelToUnderline(str);
        assertEquals("camel_character", results[0]);
        assertEquals("test_camel_list_to_underline", results[1]);
        assertEquals("a_d_m_i_n", results[2]);
        assertEquals("", results[3]);
        assertEquals("", results[4]);
    }


    /**
     * Method: camelToCharacter(final String param, final char ch)
     */
    @Test
    public void testCamelToCharacter() {
        String str = "CamelCharacter";
        assertEquals("camel:character", StringUtil.camelToCharacter(str, CharPool.COLON));
        str = "ADMIN";
        assertEquals("a&d&m&i&n", StringUtil.camelToCharacter(str, CharPool.AMP));
    }

    /**
     * Method: underlineToCamel(final String param)
     */
    @Test
    public void testUnderlineToCamel() {
        String str = "camel_character";
        assertEquals("camelCharacter", StringUtil.underlineToCamel(str));
        str = "a_d_m_i_n";
        //noinspection SpellCheckingInspection
        assertEquals("aDMIN", StringUtil.underlineToCamel(str));
    }

    /**
     * Method: underlineToCamel(final Collection<String> params)
     */
    @Test
    public void testUnderlineListToCamel() {
        List<String> params = Lists.arrayList("camel_character", "test_camel_list_to_underline", "a_d_m_i_n", "", null);
        List<String> results = StringUtil.underlineToCamel(params);
        assertEquals("camelCharacter", results.get(0));
        assertEquals("testCamelListToUnderline", results.get(1));
        //noinspection SpellCheckingInspection
        assertEquals("aDMIN", results.get(2));
        assertEquals("", results.get(3));
        assertEquals("", results.get(4));
    }

    /**
     * Method: underlineToCamel(final String... params)
     */
    @Test
    public void testUnderlineArrayToCamel() {
        String[] array = new String[]{"Camel___Character__", "Test___camel_list_to_underline", "____A_d_m_i_nNn", "", null};
        String[] results = StringUtil.underlineToCamel(array);
        assertEquals("camelCharacter", results[0]);
        assertEquals("testCamelListToUnderline", results[1]);
        //noinspection SpellCheckingInspection
        assertEquals("aDMINNn", results[2]);
        assertEquals("", results[3]);
        assertEquals("", results[4]);
    }

    /**
     * Method: characterToCamel(final String param, final char ch)
     */
    @Test
    public void testCharacterToCamel() {
        String str = "camel:character";
        assertEquals("camelCharacter", StringUtil.characterToCamel(str, CharPool.COLON));
        str = "a:d:m:i:n";
        //noinspection SpellCheckingInspection
        assertEquals("aDMIN", StringUtil.characterToCamel(str, CharPool.COLON));
    }

    /**
     * Method: split(final String param, final char separator)
     */
    @Test
    public void testSplit() {
        String param = "a,b,c, d, ";
        assertArrayEquals(StringUtil.split(param, ",").toArray(String[]::new), new String[]{"a", "b", "c", "d"});
        assertArrayEquals(StringUtil.split(param, "").toArray(String[]::new), new String[]{"a,b,c, d,"});
        param = "3.14.15.   ..";
        assertArrayEquals(StringUtil.split(param, "\\.").toArray(String[]::new), new String[]{"3", "14", "15"});
        assertArrayEquals(StringUtil.split(param, ".").toArray(String[]::new), new String[]{});
        assertArrayEquals(StringUtil.split("", "\\.").toArray(String[]::new), new String[]{});
        assertArrayEquals(StringUtil.split(null, "\\.").toArray(String[]::new), new String[]{});
    }

    /**
     * Method: split(final String param, final char separator)
     */
    @Test
    public void testSplitForParamSeparator() {
        String param = "a,b,c, d, ";
        assertArrayEquals(StringUtil.split(param, ",").toArray(String[]::new), new String[]{"a", "b", "c", "d"});
        assertArrayEquals(StringUtil.split(param, "").toArray(String[]::new), new String[]{"a,b,c, d,"});

        assertArrayEquals(StringUtil.split(param, ",", false, false).toArray(String[]::new), new String[]{"a", "b", "c", " d", " "});
        assertArrayEquals(StringUtil.split(param, "", false, false).toArray(String[]::new), new String[]{"a,b,c, d, "});
        // 过滤空白
        assertArrayEquals(StringUtil.split(param, ",", false, true).toArray(String[]::new), new String[]{"a", "b", "c", " d"});
        assertArrayEquals(StringUtil.split(param, "", false, true).toArray(String[]::new), new String[]{"a,b,c, d, "});
        // 左右清除空白
        assertArrayEquals(StringUtil.split(param, ",", true, false).toArray(String[]::new), new String[]{"a", "b", "c", "d", ""});
        assertArrayEquals(StringUtil.split(param, "", true, false).toArray(String[]::new), new String[]{"a,b,c, d,"});
        // 左右清除空白，过滤空白
        assertArrayEquals(StringUtil.split(param, ",", true, true).toArray(String[]::new), new String[]{"a", "b", "c", "d"});
        assertArrayEquals(StringUtil.split(param, "", true, true).toArray(String[]::new), new String[]{"a,b,c, d,"});

        String param2 = "3.14.15.   ..";
        assertArrayEquals(StringUtil.split(param2, "\\.").toArray(String[]::new), new String[]{"3", "14", "15"});
        assertArrayEquals(StringUtil.split(param2, ".").toArray(String[]::new), new String[]{});
        assertArrayEquals(StringUtil.split("", "\\.").toArray(String[]::new), new String[]{});
        assertArrayEquals(StringUtil.split(null, "\\.").toArray(String[]::new), new String[]{});
        List<Integer> integers = StringUtil.split(param2, "\\.", Integer::parseInt, true, true);
        assertArrayEquals(integers.toArray(Integer[]::new), new Integer[]{3, 14, 15});
        List<Float> floats = StringUtil.split(param2, "\\.", Float::valueOf);
        assertArrayEquals(floats.toArray(Float[]::new), new Float[]{3.0f, 14.0f, 15.0f});
        List<BigDecimal> bigDecimals = StringUtil.split(param2, "\\.", BigDecimal::new);
        assertArrayEquals(bigDecimals.toArray(BigDecimal[]::new), new BigDecimal[]{new BigDecimal("3"), new BigDecimal("14"), new BigDecimal("15")});
    }

    /**
     * Method: split(final String param, final char separator)
     */
    @Test
    public void testSplitToNumberForParamSeparator() {
        String param = "3.14.15.   ..";
        assertArrayEquals(StringUtil.splitToLong(param, "\\.").toArray(Long[]::new), new Long[]{3L, 14L, 15L});
        assertArrayEquals(StringUtil.splitToLong("", "\\.").toArray(Long[]::new), new Long[]{});
        assertArrayEquals(StringUtil.splitToLong(null, "\\.").toArray(Long[]::new), new Long[]{});

        assertArrayEquals(StringUtil.splitToInteger(param, "\\.").toArray(Integer[]::new), new Integer[]{3, 14, 15});
        assertArrayEquals(StringUtil.splitToInteger("", "\\.").toArray(new Integer[0]), new Integer[]{});
        assertArrayEquals(StringUtil.splitToInteger(null, "\\.").toArray(new Integer[0]), new Integer[]{});

        BigDecimal[] array = new BigDecimal[]{new BigDecimal("3"), new BigDecimal("14"), new BigDecimal("15")};
        assertArrayEquals(StringUtil.splitToBigDecimal(param, "\\.").toArray(BigDecimal[]::new), array);
        assertArrayEquals(StringUtil.splitToBigDecimal("", "\\.").toArray(BigDecimal[]::new), new BigDecimal[]{});
        assertArrayEquals(StringUtil.splitToBigDecimal(null, "\\.").toArray(BigDecimal[]::new), new BigDecimal[]{});
    }

    /**
     * Method: concat(final Object... params)
     */
    @Test
    public void testConcat() {
        assertEquals("redis:key:admin", StringUtil.concat("redis", ":", "", "key", ":", "admin"));
        assertEquals("redis:key:admin:null", StringUtil.concat("redis", ":", "", "key", ":", "admin", ":", null));
        assertEquals("1:key:2:null", StringUtil.concat(1, ":", "", "key", ":", "2", ":", null));
        assertEquals("4.0 5d 6.0 7 8", StringUtil.concat(4d, " ", "", "5d", " ", 6f, " ", 7L, " ", 8));
        assertEquals("", StringUtil.concat());
        assertEquals("null", StringUtil.concat("", "", null));
        assertEquals("null", StringUtil.concat((Object) null));
        assertEquals("", StringUtil.concat((Object[]) null));

        assertEquals("redis:key:admin", StringUtil.concat(Lists.arrayList("redis", ":", "", "key", ":", "admin")));
        assertEquals("redis:key:admin:null", StringUtil.concat(Lists.arrayList("redis", ":", "", "key", ":", "admin", ":", null)));
        assertEquals("1:key:2:null", StringUtil.concat(Lists.arrayList(1, ":", "", "key", ":", "2", ":", null)));
        assertEquals("4.0 5d 6.0 7 8", StringUtil.concat(Lists.arrayList(4d, " ", "", "5d", " ", 6f, " ", 7L, " ", 8)));
        assertEquals("", StringUtil.concat((Collection<Object>) null));
    }

    /**
     * Method: join(final String delimiter, final Object... params)
     */
    @Test
    public void testJoin() {
        assertEquals("", StringUtil.join(null, (Collection<Object>) null));
        assertEquals("", StringUtil.join("", (Collection<Object>) null));
        assertEquals("", StringUtil.join(null, (Object[]) null));
        assertEquals("", StringUtil.join("", (Object[]) null));
        assertEquals("null", StringUtil.join(null, (Object) null));
        assertEquals("null", StringUtil.join("", (Object) null));
        String[] array = new String[]{"a", "b", "c", null, "e", ""};
        assertEquals("a,b,c,null,e,", StringUtil.join(",", (Object[]) array));
        assertEquals("a,b,c,null,e,", StringUtil.join(",", "a", "b", "c", null, "e", ""));
        Object[] objects = new Object[]{"a", 1, 2, null, "c", ""};
        assertEquals("a,1,2,null,c,", StringUtil.join(",", objects));

        Integer[] integerArray = new Integer[]{1, 2, 3, null, 5};
        assertEquals("1,2,3,null,5", StringUtil.join(",", (Object[]) integerArray));
        assertEquals("1,2,3,null,5", StringUtil.join(",", 1, 2, 3, null, 5));

        Long[] longArray = new Long[]{1L, 2L, 3L, null, 5L};
        assertEquals("1,2,3,null,5", StringUtil.join(",", (Object[]) longArray));
        assertEquals("1,2,3,null,5", StringUtil.join(",", 1L, 2L, 3L, null, 5L));
        assertEquals("123null5", StringUtil.join("", 1L, 2L, 3L, null, 5L));

        List<String> stringList = Lists.arrayList("a", "b", null, "d", "");
        assertEquals("a,b,null,d,", StringUtil.join(",", stringList));
        List<Object> objectList = Lists.arrayList("a", 1, "", 2L, 3d, 4f, null);
        assertEquals("a,1,,2,3.0,4.0,null", StringUtil.join(",", objectList));
        assertEquals("a^1^^2^3.0^4.0^null", StringUtil.join("^", objectList));


        Object[] array2 = new String[]{"a", "b", "c", null, "e"};
        assertEquals("a,b,c,null,e", StringUtil.join(",", array2));


        List<Object> list2 = Lists.arrayList(1, 2f, 3d, 4L, null, 6);
        assertEquals("1,2.0,3.0,4,null,6", StringUtil.join(",", list2));
        assertEquals("12.03.04null6", StringUtil.join(null, list2));
    }

    /**
     * Method: wrap(CharSequence str, CharSequence prefix, CharSequence suffix)
     */
    @Test
    public void testWrapForStrPrefixSuffix() {
        String st = "hahahhaha";
        String pre = "@@";
        String suf = "~~";

        assertEquals("@@hahahhaha~~", StringUtil.wrap(st, pre, suf));
    }

    /**
     * Method: propertyName(final String methodName)
     */
    @Test
    public void testPropertyName() {
        assertEquals("name", StringUtil.propertyName("getName"));
        assertEquals("name", StringUtil.propertyName("setName"));
        assertEquals("name", StringUtil.propertyName("isName"));
        assertEquals("name", StringUtil.propertyName("name"));
        assertEquals("", StringUtil.propertyName(""));
        assertEquals("a", StringUtil.propertyName("a"));
        assertEquals("", StringUtil.propertyName(null));
    }

    /**
     * Method: getterName(final String property)
     */
    @Test
    public void testGetterNameProperty() {
        assertEquals("getName", StringUtil.getterName("name"));
        assertEquals("isName", StringUtil.getterName("name", boolean.class));
        assertEquals("getName", StringUtil.getterName("name", Boolean.class));
        assertEquals("getName", StringUtil.getterName("name", Integer.class));
        assertEquals("getName", StringUtil.getterName("name", null));

        assertEquals("", StringUtil.getterName(""));
        assertEquals("", StringUtil.getterName("", boolean.class));
        assertEquals("", StringUtil.getterName("", Boolean.class));
        assertEquals("", StringUtil.getterName("", Integer.class));
        assertEquals("", StringUtil.getterName("", null));

        assertEquals("", StringUtil.getterName(null));
        assertEquals("", StringUtil.getterName(null, boolean.class));
        assertEquals("", StringUtil.getterName(null, Boolean.class));
        assertEquals("", StringUtil.getterName(null, Integer.class));
        assertEquals("", StringUtil.getterName(null, null));
    }


    /**
     * Method: setterName(final String property)
     */
    @Test
    public void testSetterName() {
        assertEquals("setName", StringUtil.setterName("name"));
        assertEquals("", StringUtil.setterName(""));
        assertEquals("", StringUtil.setterName(null));
    }

    /**
     * Method: isGetterOrSetter(String methodName)
     */
    @Test
    public void testIsGetterOrSetter() {
        String methodName1 = "getId";
        String methodName2 = "setId";
        String methodName3 = "SetId";

        assertTrue(StringUtil.isGetterOrSetter(methodName1));
        assertTrue(StringUtil.isGetterOrSetter(methodName2));
        assertFalse(StringUtil.isGetterOrSetter(methodName3));

    }

    /**
     * Method: isNumeric(CharSequence param)
     */
    @Test
    public void testIsNumeric() {
        assertTrue(StringUtil.isNumeric("123"));
        assertTrue(StringUtil.isNumeric("0000"));
        assertTrue(StringUtil.isNumeric("0"));
        assertFalse(StringUtil.isNumeric("-123"));
        assertFalse(StringUtil.isNumeric(""));
        assertFalse(StringUtil.isNumeric(" 1"));
        assertFalse(StringUtil.isNumeric(" 1 "));
        assertFalse(StringUtil.isNumeric(null));
    }

    /**
     * Method: reverse(String str)
     */
    @Test
    public void testReverse() {
        String str = "abcd";
        assertEquals("dcba", StringUtil.reverse(str));
    }

    /**
     * Method: format(final CharSequence template, final Object... params)
     */
    @Test
    public void testFormat() {
        String a = "this is {} for {}";
        String paramsA = "a";
        String paramsB = "b";
        String format = StringUtil.format(a, paramsA, paramsB);

        String reString = "this is a for b";
        assertEquals(reString, format);
    }

    /**
     * Method: toString(final Object obj)
     */
    @Test
    public void testToStringObj() {
        Object obj = 333;
        Object objChar = 'h';


        assertEquals("", StringUtil.toString(null));
        assertEquals("333", StringUtil.toString(obj));
        assertEquals("h", StringUtil.toString(objChar));
    }

    /**
     * Method: toString(final Object obj, final String charsetName)
     */
    @Test
    public void testToStringForObjCharsetName() {
        String objStr = "abc话";

        String charsetUTF16 = "utf-16";
        String charsetUTF8 = "utf-8";

        byte[] bytes = new byte[]{1, 5, 8};
        Byte[] byteBig = new Byte[]{22, 22, 127};
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);

        Object[] obj = new Object[3];
        int integer = 55;
        double param = 59d;
        obj[1] = integer;
        obj[2] = param;


        assertEquals(StringUtil.toString(objStr, charsetUTF16), StringUtil.toString(objStr, charsetUTF8));
        assertNotEquals(StringUtil.toString(bytes, charsetUTF16), StringUtil.toString(bytes, charsetUTF8));
        assertNotEquals(StringUtil.toString(byteBig, charsetUTF16), StringUtil.toString(byteBig, charsetUTF8));
        assertNotEquals(StringUtil.toString(byteBuffer, charsetUTF16), StringUtil.toString(byteBuffer, charsetUTF8));
        assertEquals(StringUtil.toString(obj, charsetUTF16), StringUtil.toString(obj, charsetUTF8));


    }

    /**
     * Method: toString(Object obj, Charset charset)
     */
    @Test
    public void testToStringForObjCharset() {
        String objStr = "abc话";

        Charset charsetUTF16 = StandardCharsets.UTF_16;
        Charset charsetUTF8 = StandardCharsets.UTF_8;


        byte[] bytes = new byte[]{1, 5, 8};
        Byte[] byteBig = new Byte[]{22, 22, 127};
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);

        Object[] obj = new Object[3];
        int integer = 55;
        double param = 59d;
        obj[1] = integer;
        obj[2] = param;


        assertEquals(StringUtil.toString(objStr, charsetUTF16), StringUtil.toString(objStr, charsetUTF8));
        assertNotEquals(StringUtil.toString(bytes, charsetUTF16), StringUtil.toString(bytes, charsetUTF8));
        assertNotEquals(StringUtil.toString(byteBig, charsetUTF16), StringUtil.toString(byteBig, charsetUTF8));
        assertNotEquals(StringUtil.toString(byteBuffer, charsetUTF16), StringUtil.toString(byteBuffer, charsetUTF8));
        assertEquals(StringUtil.toString(obj, charsetUTF16), StringUtil.toString(obj, charsetUTF8));

    }

    /**
     * Method: toUtf8String(Object obj)
     */
    @Test
    public void testToUtf8String() {
        Object object = 'h';
        Object objectInt = 56;

        assertEquals("h", StringUtil.toUtf8String(object));
        assertEquals("56", StringUtil.toUtf8String(objectInt));

    }
}
