package com.ipower.framework.common.core.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * do something in here.
 *
 * @author diablo
 */
class BooleanUtilTest {

    @Test
    void isBoolean() {
        Assertions.assertTrue(BooleanUtil.isBoolean(Boolean.class));
        Assertions.assertTrue(BooleanUtil.isBoolean(boolean.class));
        Assertions.assertFalse(BooleanUtil.isBoolean(String.class));
    }

    @Test
    void isTrue() {
        Assertions.assertTrue(BooleanUtil.isTrue(Boolean.TRUE));
        Assertions.assertFalse(BooleanUtil.isTrue(Boolean.FALSE));
        Assertions.assertFalse(BooleanUtil.isTrue((Boolean) null));
    }

    @Test
    void testIsTrue() {
        Assertions.assertTrue(BooleanUtil.isTrue("yes"));
        Assertions.assertFalse(BooleanUtil.isTrue("no"));
        Assertions.assertTrue(BooleanUtil.isTrue("是"));
        Assertions.assertFalse(BooleanUtil.isTrue((String) null));
    }

    @Test
    void negate() {
        //noinspection ConstantValue
        Assertions.assertTrue(BooleanUtil.negate(Boolean.FALSE.booleanValue()));
        //noinspection ConstantValue
        Assertions.assertFalse(BooleanUtil.negate(Boolean.TRUE.booleanValue()));

    }

    @Test
    void testNegate() {
        Assertions.assertNull(BooleanUtil.negate(null));
        Assertions.assertTrue(BooleanUtil.isTrue(BooleanUtil.negate(Boolean.FALSE)));
        Assertions.assertFalse(BooleanUtil.isTrue(BooleanUtil.negate(Boolean.TRUE)));
    }

    @Test
    void toBoolean() {
        Assertions.assertTrue(BooleanUtil.toBoolean("y"));
        Assertions.assertTrue(BooleanUtil.toBoolean("Y"));
        Assertions.assertTrue(BooleanUtil.toBoolean("yes"));
        Assertions.assertTrue(BooleanUtil.toBoolean("YES"));
        Assertions.assertTrue(BooleanUtil.toBoolean("true"));
        Assertions.assertTrue(BooleanUtil.toBoolean("TRUE"));
        Assertions.assertTrue(BooleanUtil.toBoolean("t"));
        Assertions.assertTrue(BooleanUtil.toBoolean("T"));
        Assertions.assertTrue(BooleanUtil.toBoolean("on"));
        Assertions.assertTrue(BooleanUtil.toBoolean("ON"));
        Assertions.assertTrue(BooleanUtil.toBoolean("1"));
        Assertions.assertTrue(BooleanUtil.toBoolean("真"));
        Assertions.assertTrue(BooleanUtil.toBoolean("对"));
        Assertions.assertTrue(BooleanUtil.toBoolean("是"));
    }

    @Test
    void testToBoolean() {
        Integer a = 1;
        Assertions.assertTrue(BooleanUtil.toBoolean(a));
        Double b = 1D;
        Assertions.assertFalse(BooleanUtil.toBoolean(b));
        Long c = 1L;
        Assertions.assertTrue(BooleanUtil.toBoolean(c));
        Integer d = 0;
        Assertions.assertFalse(BooleanUtil.toBoolean(d));
        Object f = 3.14;
        Assertions.assertFalse(BooleanUtil.toBoolean(f));
    }

    @Test
    void toInt() {
        Assertions.assertEquals(1, BooleanUtil.toInt(true));
        Assertions.assertEquals(0, BooleanUtil.toInt(false));
    }

    @Test
    void toInteger() {
        Integer actual = BooleanUtil.toInteger(true);
        Assertions.assertEquals(1, actual);
        Integer actual2 = BooleanUtil.toInteger(false);
        Assertions.assertEquals(0, actual2);
    }

    @Test
    void toChar() {
        char actual = BooleanUtil.toChar(true);
        char actual2 = BooleanUtil.toChar(true);
        char actual3 = BooleanUtil.toChar(false);
        char actual4 = BooleanUtil.toChar(false);
        Assertions.assertEquals(actual, actual2);
        Assertions.assertEquals(actual3, actual4);
        Assertions.assertNotEquals(actual, actual4);
    }

    @Test
    void toCharacter() {
        Character actual = BooleanUtil.toCharacter(true);
        Character actual2 = BooleanUtil.toCharacter(true);
        Character actual3 = BooleanUtil.toCharacter(false);
        Character actual4 = BooleanUtil.toCharacter(false);
        Assertions.assertEquals(actual, actual2);
        Assertions.assertEquals(actual3, actual4);
        Assertions.assertNotEquals(actual, actual4);
    }

    @Test
    void toByte() {
        byte actual = BooleanUtil.toByte(true);
        Assertions.assertEquals(1, actual);
        byte actual2 = BooleanUtil.toByte(false);
        Assertions.assertEquals(0, actual2);
    }

    @Test
    void toByteObj() {
        Byte actual = BooleanUtil.toByte(true);
        Byte actual1 = 1;
        Assertions.assertEquals(actual1, actual);
        Byte actual2 = BooleanUtil.toByte(false);
        byte actual21 = 0;
        Assertions.assertEquals(actual21, actual2);
    }

    @Test
    void toLong() {
        Assertions.assertEquals(1, BooleanUtil.toLong(true));
        Assertions.assertEquals(0, BooleanUtil.toLong(false));
    }

    @Test
    void toLongObj() {
        Long actual = 1L;
        Assertions.assertEquals(actual, BooleanUtil.toLongObj(true));
        Long actual2 = 0L;
        Assertions.assertEquals(actual2, BooleanUtil.toLongObj(false));
    }

    @Test
    void toShort() {
        short actual = 1;
        Assertions.assertEquals(actual, BooleanUtil.toShort(true));
        short actual2 = 0;
        Assertions.assertEquals(actual2, BooleanUtil.toShort(false));
    }

    @Test
    void toShortObj() {
        Short actual = 1;
        Assertions.assertEquals(actual, BooleanUtil.toShortObj(true));
        Short actual2 = 0;
        Assertions.assertEquals(actual2, BooleanUtil.toShortObj(false));
    }

    @Test
    void toFloat() {
        float actual = 1.0F;
        Assertions.assertEquals(actual, BooleanUtil.toFloat(true));
        float actual2 = 0.0F;
        Assertions.assertEquals(actual2, BooleanUtil.toFloat(false));
    }

    @Test
    void toFloatObj() {
        Float actual = 1.0F;
        Assertions.assertEquals(actual, BooleanUtil.toFloatObj(true));
        Float actual2 = 0.0F;
        Assertions.assertEquals(actual2, BooleanUtil.toFloatObj(false));
    }

    @Test
    void toDouble() {
        double actual = 1.0;
        Assertions.assertEquals(actual, BooleanUtil.toDouble(true));
        double actual2 = 0.0;
        Assertions.assertEquals(actual2, BooleanUtil.toDouble(false));
    }

    @Test
    void toDoubleObj() {
        Double actual = 1.0D;
        Assertions.assertEquals(actual, BooleanUtil.toDoubleObj(true));
        Double actual2 = 0.0D;
        Assertions.assertEquals(actual2, BooleanUtil.toDoubleObj(false));
    }

    @Test
    void toTrueOrFalse() {
        Assertions.assertEquals("true", BooleanUtil.toTrueOrFalse(true));
        Assertions.assertEquals("false", BooleanUtil.toTrueOrFalse(false));

        Assertions.assertNotEquals("TRUE", BooleanUtil.toTrueOrFalse(true));
        Assertions.assertNotEquals("FALSE", BooleanUtil.toTrueOrFalse(false));
    }

    @Test
    void testToTrueOrFalse() {
        Assertions.assertEquals("true", BooleanUtil.toTrueOrFalse(Boolean.TRUE));
        Assertions.assertEquals("false", BooleanUtil.toTrueOrFalse(Boolean.FALSE));

        Assertions.assertNotEquals("TRUE", BooleanUtil.toTrueOrFalse(Boolean.TRUE));
        Assertions.assertNotEquals("FALSE", BooleanUtil.toTrueOrFalse(Boolean.FALSE));
    }

    @Test
    void toOnOrOff() {
        Assertions.assertEquals("on", BooleanUtil.toOnOrOff(true));
        Assertions.assertEquals("off", BooleanUtil.toOnOrOff(false));

        Assertions.assertNotEquals("ON", BooleanUtil.toOnOrOff(true));
        Assertions.assertNotEquals("OF", BooleanUtil.toOnOrOff(false));
    }

    @Test
    void testToOnOrOff() {
        Assertions.assertEquals("on", BooleanUtil.toOnOrOff(Boolean.TRUE));
        Assertions.assertEquals("off", BooleanUtil.toOnOrOff(Boolean.FALSE));

        Assertions.assertNotEquals("ON", BooleanUtil.toOnOrOff(Boolean.TRUE));
        Assertions.assertNotEquals("OF", BooleanUtil.toOnOrOff(Boolean.FALSE));
    }

    @Test
    void toYesOrNo() {
        Assertions.assertEquals("yes", BooleanUtil.toYesOrNo(true));
        Assertions.assertEquals("no", BooleanUtil.toYesOrNo(false));

        Assertions.assertNotEquals("YES", BooleanUtil.toYesOrNo(true));
        Assertions.assertNotEquals("NO", BooleanUtil.toYesOrNo(false));
    }

    @Test
    void testToYesOrNo() {
        Assertions.assertEquals("yes", BooleanUtil.toYesOrNo(Boolean.TRUE));
        Assertions.assertEquals("no", BooleanUtil.toYesOrNo(Boolean.FALSE));

        Assertions.assertNotEquals("YES", BooleanUtil.toYesOrNo(Boolean.TRUE));
        Assertions.assertNotEquals("NO", BooleanUtil.toYesOrNo(Boolean.FALSE));
    }

    @Test
    void testToString() {
        Assertions.assertEquals("是", BooleanUtil.toString(true, "是", "否"));
        Assertions.assertEquals("否", BooleanUtil.toString(false, "是", "否"));

        Assertions.assertEquals("真", BooleanUtil.toString(true, "真", "假"));
        Assertions.assertEquals("假", BooleanUtil.toString(false, "真", "假"));
    }

    @Test
    void testToString1() {
        Assertions.assertEquals("是", BooleanUtil.toString(Boolean.TRUE, "是", "否"));
        Assertions.assertEquals("否", BooleanUtil.toString(Boolean.FALSE, "是", "否"));

        Assertions.assertEquals("真", BooleanUtil.toString(Boolean.TRUE, "真", "假"));
        Assertions.assertEquals("假", BooleanUtil.toString(Boolean.FALSE, "真", "假"));

        Assertions.assertEquals("1", BooleanUtil.toString(Boolean.TRUE, "1", "0"));
        Assertions.assertEquals("0", BooleanUtil.toString(Boolean.FALSE, "1", "0"));
    }
}