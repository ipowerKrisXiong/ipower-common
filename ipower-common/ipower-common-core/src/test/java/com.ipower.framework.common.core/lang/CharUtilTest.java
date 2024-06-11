package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.constant.CharPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * do something in here.
 *
 * @author diablo
 */
class CharUtilTest {

    @Test
    void isAscii() {
        Assertions.assertTrue(CharUtil.isAscii('a'));
        Assertions.assertTrue(CharUtil.isAscii('A'));
        Assertions.assertTrue(CharUtil.isAscii('3'));
        Assertions.assertTrue(CharUtil.isAscii('-'));
        Assertions.assertTrue(CharUtil.isAscii('\n'));
        Assertions.assertFalse(CharUtil.isAscii('©'));
    }

    @Test
    void isChar() {
        Assertions.assertTrue(CharUtil.isChar('a'));
        Assertions.assertFalse(CharUtil.isChar("a"));
        char ch = 1;
        Assertions.assertTrue(CharUtil.isChar(ch));
        int i = 1;
        Assertions.assertFalse(CharUtil.isChar(i));
    }

    @Test
    void toChar() {
        Assertions.assertEquals('A', CharUtil.toChar("A"));
        Assertions.assertEquals('B', CharUtil.toChar("BA"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> CharUtil.toChar(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> CharUtil.toChar(""));
    }

    @Test
    void testToChar() {
        Assertions.assertEquals('A', CharUtil.toChar("A", 'X'));
        Assertions.assertEquals('B', CharUtil.toChar("BA", 'X'));
        Assertions.assertEquals('X', CharUtil.toChar(null, 'X'));
        Assertions.assertEquals('X', CharUtil.toChar("", 'X'));
    }

    @Test
    void toInt() {
        Assertions.assertEquals(3, CharUtil.toInt('3'));
        Assertions.assertEquals(9, CharUtil.toInt('9'));
        Assertions.assertEquals(0, CharUtil.toInt('0'));
        Assertions.assertThrows(IllegalArgumentException.class, () -> CharUtil.toInt(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> CharUtil.toInt('A'));
    }

    @Test
    void testToInt() {
        Assertions.assertEquals(3, CharUtil.toInt('3', -1));
        Assertions.assertEquals(9, CharUtil.toInt('9', -1));
        Assertions.assertEquals(0, CharUtil.toInt('0', -1));
        Assertions.assertEquals(-1, CharUtil.toInt(null, -1));
        Assertions.assertEquals(-1, CharUtil.toInt('A', -1));
    }

    @Test
    void isBlank() {
        Assertions.assertTrue(CharUtil.isBlank(' '));
        Assertions.assertFalse(CharUtil.isBlank('A'));
        Assertions.assertTrue(CharUtil.isBlank(CharPool.TAB));
        Assertions.assertTrue(CharUtil.isBlank(CharPool.NUL));
    }

    @Test
    void testIsBlank() {
        int a = 32;
        Assertions.assertTrue(CharUtil.isBlank(a));
        int b = 9;
        Assertions.assertTrue(CharUtil.isBlank(b));
        int c = 128;
        Assertions.assertFalse(CharUtil.isBlank(c));
    }

    @Test
    void unicodeEscaped() {
        Assertions.assertNull(CharUtil.unicodeEscaped(null));
        Assertions.assertEquals("\\u0020", CharUtil.unicodeEscaped(' '));
        Assertions.assertEquals("\\u0041", CharUtil.unicodeEscaped('A'));
    }

    @Test
    void testToString() {
        Assertions.assertNull(CharUtil.toString(null));
        Assertions.assertEquals(" ", CharUtil.toString(' '));
        Assertions.assertEquals("A", CharUtil.toString('A'));

        System.out.println(UUIDUtil.gen32UUID().toLowerCase());
    }
}