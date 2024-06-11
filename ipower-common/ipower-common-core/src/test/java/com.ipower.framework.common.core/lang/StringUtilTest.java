package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.collection.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * StringUtil Tester.
 *
 * @author huangad@coracle.com
 */
public class StringUtilTest {

    /**
     * Method: isEmptyOrUndefined(String param)
     */
    @Test
    public void testIsEmptyOrUndefined() {
        assertTrue(StringUtil.isEmptyOrUndefined(null));
        assertTrue(StringUtil.isEmptyOrUndefined(""));
        assertFalse(StringUtil.isEmptyOrUndefined(" "));
        assertFalse(StringUtil.isEmptyOrUndefined("\t\n"));
        assertTrue(StringUtil.isEmptyOrUndefined("null"));
        assertTrue(StringUtil.isEmptyOrUndefined("undefined"));
        assertFalse(StringUtil.isEmptyOrUndefined("abc"));
    }

    /**
     * Method: isBlankOrUndefined(String param)
     */
    @Test
    public void testIsBlankOrUndefined() {
        assertTrue(StringUtil.isBlankOrUndefined(null));
        assertTrue(StringUtil.isBlankOrUndefined(""));
        assertTrue(StringUtil.isBlankOrUndefined(" "));
        assertTrue(StringUtil.isBlankOrUndefined("\t\n"));
        assertTrue(StringUtil.isBlankOrUndefined("null"));
        assertTrue(StringUtil.isBlankOrUndefined("undefined"));
        assertFalse(StringUtil.isBlankOrUndefined("abc"));
    }

    /**
     * Method: nullToEmpty(String param)
     */
    @Test
    public void testNullToEmpty() {
        assertEquals("", StringUtil.nullToEmpty(null));
        assertEquals("", StringUtil.nullToEmpty(""));
        assertEquals(" ", StringUtil.nullToEmpty(" "));
        assertEquals("abc", StringUtil.nullToEmpty("abc"));
    }

    /**
     * Method: nullToDefault(String param, String defaultString)
     */
    @Test
    public void testNullToDefault() {
        String defaultString = "defaultString";
        assertEquals("defaultString", StringUtil.nullToDefault(null, defaultString));
        assertEquals("", StringUtil.nullToDefault("", defaultString));
        assertEquals(" ", StringUtil.nullToDefault(" ", defaultString));
        assertEquals("\t\n", StringUtil.nullToDefault("\t\n", defaultString));
        assertEquals("abc", StringUtil.nullToDefault("abc", defaultString));
    }

    /**
     * Method: emptyToDefault(String param, String defaultString)
     */
    @Test
    public void testEmptyToDefault() {
        String defaultString = "defaultString";
        assertEquals("defaultString", StringUtil.emptyToDefault(null, defaultString));
        assertEquals("defaultString", StringUtil.emptyToDefault("", defaultString));
        assertEquals(" ", StringUtil.emptyToDefault(" ", defaultString));
        assertEquals("\t\n", StringUtil.emptyToDefault("\t\n", defaultString));
        assertEquals("abc", StringUtil.emptyToDefault("abc", defaultString));
    }

    /**
     * Method: blankToDefault(String param, String defaultString)
     */
    @Test
    public void testBlankToDefault() {
        String defaultString = "defaultString";
        assertEquals("defaultString", StringUtil.blankToDefault(null, defaultString));
        assertEquals("defaultString", StringUtil.blankToDefault("", defaultString));
        assertEquals("defaultString", StringUtil.blankToDefault(" ", defaultString));
        assertEquals("defaultString", StringUtil.blankToDefault("\t\n", defaultString));
        assertEquals("abc", StringUtil.blankToDefault("abc", defaultString));
    }

    /**
     * Method: equal(final String param, final String another)
     */
    @Test
    public void testEqual() {
        assertTrue(StringUtil.equals(null, null));
        assertTrue(StringUtil.equals("", ""));
        assertFalse(StringUtil.equals("abc", null));
        assertFalse(StringUtil.equals(null, "abc"));
        assertFalse(StringUtil.equals("", "abc"));
        assertFalse(StringUtil.equals("abc", ""));
        assertTrue(StringUtil.equals("abc", "abc"));
        assertFalse(StringUtil.equals("abc", "ABC"));
    }

    /**
     * Method: equalIgnoreCase(final String param, final String another)
     */
    @Test
    public void testEqualIgnoreCase() {
        assertTrue(StringUtil.equalsIgnoreCase(null, null));
        assertTrue(StringUtil.equalsIgnoreCase("", ""));
        assertFalse(StringUtil.equalsIgnoreCase("abc", null));
        assertFalse(StringUtil.equalsIgnoreCase(null, "abc"));
        assertFalse(StringUtil.equalsIgnoreCase("", "abc"));
        assertFalse(StringUtil.equalsIgnoreCase("abc", ""));
        assertTrue(StringUtil.equalsIgnoreCase("abc", "abc"));
        assertTrue(StringUtil.equalsIgnoreCase("abc", "ABC"));
    }

    /**
     * Method: equalAny(final String param, final String... others)
     */
    @Test
    public void testEqualAny() {
        assertTrue(StringUtil.equalsAny("", ""));
        assertFalse(StringUtil.equalsAny("abc", (String[]) null));
        assertFalse(StringUtil.equalsAny(null, "abc"));
        assertFalse(StringUtil.equalsAny("", "abc", "admin", "test"));
        assertTrue(StringUtil.equalsAny("", "abc", "admin", "test", ""));
        assertTrue(StringUtil.equalsAny("abc", "abc", "admin", "test"));
        assertFalse(StringUtil.equalsAny("abc", "ABC", "admin", "test"));
        assertFalse(StringUtil.equalsAny("abc", "ABC", "admin", "test", ""));
    }

    /**
     * Method: equalAnyIgnoreCase(final String param, final String... others)
     */
    @Test
    public void testEqualAnyIgnoreCase() {
        assertTrue(StringUtil.equalsAnyIgnoreCase("", ""));
        assertFalse(StringUtil.equalsAnyIgnoreCase("abc", (String[]) null));
        assertFalse(StringUtil.equalsAnyIgnoreCase(null, "abc"));
        assertFalse(StringUtil.equalsAnyIgnoreCase("", "abc", "admin", "test"));
        assertTrue(StringUtil.equalsAnyIgnoreCase("", "abc", "admin", "test", ""));
        assertTrue(StringUtil.equalsAnyIgnoreCase("abc", "abc", "admin", "test"));
        assertTrue(StringUtil.equalsAnyIgnoreCase("abc", "ABC", "admin", "test"));
        assertTrue(StringUtil.equalsAnyIgnoreCase("abc", "ABC", "admin", "test", ""));
    }

    /**
     * Method: trim(final String param)
     */
    @Test
    public void testTrim() {
        String param = null;
        //noinspection ConstantValue
        assertEquals("", StringUtil.trim(param));
        assertEquals("", StringUtil.trim(""));
        assertEquals("", StringUtil.trim("     "));
        assertEquals("abc", StringUtil.trim(" abc "));
        assertEquals("abc", StringUtil.trim("     abc     "));
        assertEquals("abc", StringUtil.trim("\t\n abc \t\n"));
    }

    /**
     * Method: trim(final String param)
     */
    @Test
    public void testTrimArray() {
        String[] params = new String[]{"abc", "abc ", " abc   ", " a  b c", " ", "   ", null};
        String[] results = StringUtil.trim(params);
        assertEquals(7, results.length);
        assertEquals("abc", results[0]);
        assertEquals("abc", results[1]);
        assertEquals("abc", results[2]);
        assertEquals("a  b c", results[3]);
        assertEquals("", results[4]);
        assertEquals("", results[5]);
        assertEquals("", results[6]);

        String[] results2 = StringUtil.trimIgnoreEmpty(params);
        assertEquals(4, results2.length);
        assertEquals("abc", results[0]);
        assertEquals("abc", results[1]);
        assertEquals("abc", results[2]);
        assertEquals("a  b c", results[3]);
    }

    /**
     * Method: trim(final String param)
     */
    @Test
    public void testTrimList() {
        List<String> params = Lists.arrayList("abc", "abc ", " abc   ", " a  b c", " ", "   ", null);
        List<String> results = StringUtil.trim(params);
        assertEquals(7, results.size());
        assertEquals("abc", results.get(0));
        assertEquals("abc", results.get(1));
        assertEquals("abc", results.get(2));
        assertEquals("a  b c", results.get(3));
        assertEquals("", results.get(4));
        assertEquals("", results.get(5));
        assertEquals("", results.get(6));

        List<String> results2 = StringUtil.trimIgnoreEmpty(params);
        assertEquals(4, results2.size());
        assertEquals("abc", results2.get(0));
        assertEquals("abc", results2.get(1));
        assertEquals("abc", results2.get(2));
        assertEquals("a  b c", results2.get(3));
    }

    /**
     * Method: trimBefore(final String param)
     */
    @Test
    public void testTrimBefore() {
        String param = null;
        //noinspection ConstantValue
        assertEquals("", StringUtil.trimBefore(param));
        assertEquals("", StringUtil.trimBefore(""));
        assertEquals("", StringUtil.trimBefore("     "));
        assertEquals("abc ", StringUtil.trimBefore(" abc "));
        assertEquals("abc     ", StringUtil.trimBefore("     abc     "));
        assertEquals("abc \t\n", StringUtil.trimBefore("\t\n abc \t\n"));
    }

    /**
     * Method: trimAfter(final String param)
     */
    @Test
    public void testTrimAfter() {
        String param = null;
        //noinspection ConstantValue
        assertEquals("", StringUtil.trimAfter(param));
        assertEquals("", StringUtil.trimAfter(""));
        assertEquals("", StringUtil.trimAfter("     "));
        assertEquals(" abc", StringUtil.trimAfter(" abc "));
        assertEquals("     abc", StringUtil.trimAfter("     abc     "));
        assertEquals("\t\n abc", StringUtil.trimAfter("\t\n abc \t\n"));
    }

    /**
     * Method: count(final String param, final String search)
     */
    @Test
    public void testCount() {
        assertEquals(0, StringUtil.count(null, "*"));
        assertEquals(0, StringUtil.count("", "*"));
        assertEquals(0, StringUtil.count("alibaba", null));
        assertEquals(0, StringUtil.count("alibaba", ""));
        assertEquals(3, StringUtil.count("alibaba", "a"));
        assertEquals(0, StringUtil.count("alibaba", "A"));
        assertEquals(2, StringUtil.count("alibaba", "ba"));
        assertEquals(0, StringUtil.count("alibaba", "BA"));
        assertEquals(1, StringUtil.count("alibaba", "alibaba"));
        assertEquals(0, StringUtil.count("alibaba", "_alibaba"));
    }

    /**
     * Method: startWith(final String param, final String prefix)
     */
    @Test
    public void testStartWith() {
        assertFalse(StringUtil.startWith(null, null));
        assertFalse(StringUtil.startWith(null, ""));
        assertFalse(StringUtil.startWith("", null));
        assertTrue(StringUtil.startWith("", ""));
        assertFalse(StringUtil.startWith("alibaba", null));
        assertTrue(StringUtil.startWith("alibaba", ""));
        assertTrue(StringUtil.startWith("alibaba", "a"));
        assertFalse(StringUtil.startWith("alibaba", " a"));
        assertFalse(StringUtil.startWith("alibaba", "A"));
        assertTrue(StringUtil.startWith("alibaba", "ali"));
        assertFalse(StringUtil.startWith("alibaba", "AL"));
        assertTrue(StringUtil.startWith("alibaba", "alibaba"));
        assertFalse(StringUtil.startWith("alibaba", "Alibaba"));
        assertTrue(StringUtil.startWith("alibaba company", "alibaba "));
    }

    /**
     * Method: startWithIgnoreCase(final String param, final String prefix)
     */
    @Test
    public void testStartWithIgnoreCase() {
        assertFalse(StringUtil.startWithIgnoreCase(null, null));
        assertFalse(StringUtil.startWithIgnoreCase(null, ""));
        assertFalse(StringUtil.startWithIgnoreCase("", null));
        assertTrue(StringUtil.startWithIgnoreCase("", ""));
        assertFalse(StringUtil.startWithIgnoreCase("alibaba", null));
        assertTrue(StringUtil.startWithIgnoreCase("alibaba", ""));
        assertTrue(StringUtil.startWithIgnoreCase("alibaba", "a"));
        assertFalse(StringUtil.startWithIgnoreCase("alibaba", " a"));
        assertTrue(StringUtil.startWithIgnoreCase("alibaba", "A"));
        assertTrue(StringUtil.startWithIgnoreCase("alibaba", "ali"));
        assertTrue(StringUtil.startWithIgnoreCase("alibaba", "AL"));
        assertTrue(StringUtil.startWithIgnoreCase("alibaba", "alibaba"));
        assertTrue(StringUtil.startWithIgnoreCase("alibaba", "Alibaba"));
        assertTrue(StringUtil.startWithIgnoreCase("alibaba company", "alibaba "));
    }

    /**
     * Method: startWithAny(final String param, final String... prefixes)
     */
    @Test
    public void testStartWithAny() {
        assertFalse(StringUtil.startWithAny(null, (String[]) null));
        assertFalse(StringUtil.startWithAny(null, ""));
        assertFalse(StringUtil.startWithAny("", (String[]) null));
        assertTrue(StringUtil.startWithAny("", ""));
        assertFalse(StringUtil.startWithAny("alibaba", (String[]) null));
        assertTrue(StringUtil.startWithAny("alibaba", "", "b"));
        assertTrue(StringUtil.startWithAny("alibaba", "a", "b"));
        assertFalse(StringUtil.startWithAny("alibaba", "a ", "b"));
        assertFalse(StringUtil.startWithAny("alibaba", " a", "A"));
        assertFalse(StringUtil.startWithAny("alibaba", "A", "AL"));
        assertTrue(StringUtil.startWithAny("alibaba", "A", "AL", "ali"));
        assertFalse(StringUtil.startWithAny("alibaba", "_alibaba", "Alibaba"));
    }

    /**
     * Method: startWithAnyIgnoreCase(final String param, final String... prefixes)
     */
    @Test
    public void testStartWithAnyIgnoreCase() {
        assertFalse(StringUtil.startWithAnyIgnoreCase(null, (String[]) null));
        assertFalse(StringUtil.startWithAnyIgnoreCase(null, ""));
        assertFalse(StringUtil.startWithAnyIgnoreCase("", (String[]) null));
        assertTrue(StringUtil.startWithAnyIgnoreCase("", ""));
        assertFalse(StringUtil.startWithAnyIgnoreCase("alibaba", (String[]) null));
        assertTrue(StringUtil.startWithAnyIgnoreCase("alibaba", "", "b"));
        assertTrue(StringUtil.startWithAnyIgnoreCase("alibaba", "a", "b"));
        assertFalse(StringUtil.startWithAnyIgnoreCase("alibaba", "a ", "b"));
        assertTrue(StringUtil.startWithAnyIgnoreCase("alibaba", " a", "A"));
        assertTrue(StringUtil.startWithAnyIgnoreCase("alibaba", "A", "AL"));
        assertTrue(StringUtil.startWithAnyIgnoreCase("alibaba", "A", "AL", "ali"));
        assertTrue(StringUtil.startWithAnyIgnoreCase("alibaba", "_alibaba", "Alibaba"));
    }

    /**
     * Method: endWith(final String param, final String suffix)
     */
    @Test
    public void testEndWith() {
        assertFalse(StringUtil.endWith(null, null));
        assertFalse(StringUtil.endWith(null, ""));
        assertFalse(StringUtil.endWith("", null));
        assertTrue(StringUtil.endWith("", ""));
        assertFalse(StringUtil.endWith("alibaba", null));
        assertTrue(StringUtil.endWith("alibaba", ""));
        assertTrue(StringUtil.endWith("alibaba", "a"));
        assertFalse(StringUtil.endWith("alibaba", " a"));
        assertFalse(StringUtil.endWith("alibaba", "A"));
        assertTrue(StringUtil.endWith("alibaba", "aba"));
        assertFalse(StringUtil.endWith("alibaba", "BA"));
        assertTrue(StringUtil.endWith("alibaba", "alibaba"));
        assertFalse(StringUtil.endWith("alibaba", "Alibaba"));
        assertTrue(StringUtil.endWith("alibaba company", " company"));
    }

    /**
     * Method: endWithIgnoreCase(final String param, final String suffix)
     */
    @Test
    public void testEndWithIgnoreCase() {
        assertFalse(StringUtil.endWithIgnoreCase(null, null));
        assertFalse(StringUtil.endWithIgnoreCase(null, ""));
        assertFalse(StringUtil.endWithIgnoreCase("", null));
        assertTrue(StringUtil.endWithIgnoreCase("", ""));
        assertFalse(StringUtil.endWithIgnoreCase("alibaba", null));
        assertTrue(StringUtil.endWithIgnoreCase("alibaba", ""));
        assertTrue(StringUtil.endWithIgnoreCase("alibaba", "a"));
        assertFalse(StringUtil.endWithIgnoreCase("alibaba", " a"));
        assertTrue(StringUtil.endWithIgnoreCase("alibaba", "A"));
        assertTrue(StringUtil.endWithIgnoreCase("alibaba", "aba"));
        assertTrue(StringUtil.endWithIgnoreCase("alibaba", "BA"));
        assertTrue(StringUtil.endWithIgnoreCase("alibaba", "alibaba"));
        assertTrue(StringUtil.endWithIgnoreCase("alibaba", "Alibaba"));
        assertTrue(StringUtil.endWithIgnoreCase("alibaba company", " company"));
    }

    /**
     * Method: endWithAny(final String param, final String... suffixes)
     */
    @Test
    public void testEndWithAny() {
        assertFalse(StringUtil.endWithAny(null, (String[]) null));
        assertFalse(StringUtil.endWithAny(null, ""));
        assertFalse(StringUtil.endWithAny("", (String[]) null));
        assertTrue(StringUtil.endWithAny("", ""));
        assertFalse(StringUtil.endWithAny("alibaba", (String[]) null));
        assertTrue(StringUtil.endWithAny("alibaba", "", "b"));
        assertTrue(StringUtil.endWithAny("alibaba", "a", "b"));
        assertFalse(StringUtil.endWithAny("alibaba", "a ", "b"));
        assertFalse(StringUtil.endWithAny("alibaba", " a", "A"));
        assertFalse(StringUtil.endWithAny("alibaba", "A", "BA"));
        assertTrue(StringUtil.endWithAny("alibaba", "A", "BA", "aba"));
        assertFalse(StringUtil.endWithAny("alibaba", "_alibaba", "Alibaba"));
    }

    /**
     * Method: endWithAnyIgnoreCase(final String param, final String... suffixes)
     */
    @Test
    public void testEndWithAnyIgnoreCase() {
        assertFalse(StringUtil.endWithAnyIgnoreCase(null, (String[]) null));
        assertFalse(StringUtil.endWithAnyIgnoreCase(null, ""));
        assertFalse(StringUtil.endWithAnyIgnoreCase("", (String[]) null));
        assertTrue(StringUtil.endWithAnyIgnoreCase("", ""));
        assertFalse(StringUtil.endWithAnyIgnoreCase("alibaba", (String[]) null));
        assertTrue(StringUtil.endWithAnyIgnoreCase("alibaba", "", "b"));
        assertTrue(StringUtil.endWithAnyIgnoreCase("alibaba", "a", "b"));
        assertFalse(StringUtil.endWithAnyIgnoreCase("alibaba", "a ", "b"));
        assertTrue(StringUtil.endWithAnyIgnoreCase("alibaba", " a", "A"));
        assertTrue(StringUtil.endWithAnyIgnoreCase("alibaba", "A", "BA"));
        assertTrue(StringUtil.endWithAnyIgnoreCase("alibaba", "A", "BA", "aba"));
        assertTrue(StringUtil.endWithAnyIgnoreCase("alibaba", "_alibaba", "Alibaba"));
    }

    /**
     * Method: contains(final String param, final String search)
     */
    @Test
    public void testContains() {
        //noinspection ConstantValue
        assertFalse(StringUtil.contains(null, null));
        assertTrue(StringUtil.contains("", ""));
        //noinspection ConstantValue
        assertFalse(StringUtil.contains(null, ""));
        //noinspection ConstantValue
        assertFalse(StringUtil.contains("", null));
        //noinspection ConstantValue
        assertFalse(StringUtil.contains(null, "a"));
        //noinspection ConstantValue
        assertFalse(StringUtil.contains("alibaba", null));
        assertTrue(StringUtil.contains("alibaba", "a"));
        assertFalse(StringUtil.contains("alibaba", "A"));
        assertTrue(StringUtil.contains("alibaba", "ali"));
        assertFalse(StringUtil.contains("alibaba", "ALI"));
        assertFalse(StringUtil.contains("alibaba", "c"));
        assertFalse(StringUtil.contains("alibaba", "bc"));
    }

    /**
     * Method: containsIgnoreCase(final String param, final String search)
     */
    @Test
    public void testContainsIgnoreCase() {
        assertFalse(StringUtil.containsIgnoreCase(null, null));
        assertTrue(StringUtil.containsIgnoreCase("", ""));
        assertFalse(StringUtil.containsIgnoreCase(null, ""));
        assertFalse(StringUtil.containsIgnoreCase("", null));
        assertFalse(StringUtil.containsIgnoreCase(null, "a"));
        assertFalse(StringUtil.containsIgnoreCase("alibaba", null));
        assertTrue(StringUtil.containsIgnoreCase("alibaba", "a"));
        assertTrue(StringUtil.containsIgnoreCase("alibaba", "A"));
        assertTrue(StringUtil.containsIgnoreCase("alibaba", "ali"));
        assertTrue(StringUtil.containsIgnoreCase("alibaba", "ALI"));
        assertFalse(StringUtil.containsIgnoreCase("alibaba", "c"));
        assertFalse(StringUtil.containsIgnoreCase("alibaba", "bc"));
    }

    /**
     * Method: containsAny(final String param, final String... searches)
     */
    @Test
    public void testContainsAny() {
        assertFalse(StringUtil.containsAny(null, (String[]) null));
        assertFalse(StringUtil.containsAny(null, new String[]{}));
        assertFalse(StringUtil.containsAny(null, (String) null));
        assertTrue(StringUtil.containsAny("", ""));
        assertFalse(StringUtil.containsAny("", (String) null));
        assertFalse(StringUtil.containsAny(null, ""));
        assertFalse(StringUtil.containsAny(null, "a"));
        assertFalse(StringUtil.containsAny("alibaba", (String) null));
        assertFalse(StringUtil.containsAny("alibaba", "c", "d", "e"));
        assertTrue(StringUtil.containsAny("alibaba", "a", "b", "li"));
        assertTrue(StringUtil.containsAny("alibaba", "c", "de", "li"));
        assertFalse(StringUtil.containsAny("alibaba", "A", "B", "LI"));
        assertFalse(StringUtil.containsAny("alibaba", "C", "DE", "LO"));
    }

    /**
     * Method: containsAnyIgnoreCase(final String param, final String... searches)
     */
    @Test
    public void testContainsAnyIgnoreCase() {
        assertFalse(StringUtil.containsAnyIgnoreCase(null, (String) null));
        assertTrue(StringUtil.containsAnyIgnoreCase("", ""));
        assertFalse(StringUtil.containsAnyIgnoreCase("", (String) null));
        assertFalse(StringUtil.containsAnyIgnoreCase(null, ""));
        assertFalse(StringUtil.containsAnyIgnoreCase(null, "a"));
        assertFalse(StringUtil.containsAnyIgnoreCase("alibaba", (String) null));
        assertFalse(StringUtil.containsAnyIgnoreCase("alibaba", "c", "d", "e"));
        assertTrue(StringUtil.containsAnyIgnoreCase("alibaba", "a", "b", "li"));
        assertTrue(StringUtil.containsAnyIgnoreCase("alibaba", "c", "de", "li"));
        assertTrue(StringUtil.containsAnyIgnoreCase("alibaba", "A", "B", "LI"));
        assertFalse(StringUtil.containsAnyIgnoreCase("alibaba", "C", "DE", "LO"));
    }

    /**
     * Method: containsAll(final String param, final String... searches)
     */
    @Test
    public void testContainsAll() {
        assertFalse(StringUtil.containsAll(null, (String[]) null));
        assertFalse(StringUtil.containsAll(null, new String[]{}));
        assertFalse(StringUtil.containsAll(null, (String) null));
        assertTrue(StringUtil.containsAll("", ""));
        assertFalse(StringUtil.containsAll("", (String) null));
        assertFalse(StringUtil.containsAll(null, ""));
        assertFalse(StringUtil.containsAll(null, "a"));
        assertFalse(StringUtil.containsAll("alibaba", (String) null));
        assertFalse(StringUtil.containsAll("alibaba", "c", "d", "e"));
        assertTrue(StringUtil.containsAll("alibaba", "a", "b", "li"));
        assertFalse(StringUtil.containsAll("alibaba", "c", "de", "li"));
        assertFalse(StringUtil.containsAll("alibaba", "A", "B", "LI"));
        assertFalse(StringUtil.containsAll("alibaba", "C", "DE", "LO"));
    }

    /**
     * Method: containsAllIgnoreCase(final String param, final String... searches)
     */
    @Test
    public void testContainsAllIgnoreCase() {
        assertFalse(StringUtil.containsAllIgnoreCase(null, (String[]) null));
        assertFalse(StringUtil.containsAllIgnoreCase(null));
        assertFalse(StringUtil.containsAllIgnoreCase(null, (String) null));
        assertTrue(StringUtil.containsAllIgnoreCase("", ""));
        assertFalse(StringUtil.containsAllIgnoreCase("", (String) null));
        assertFalse(StringUtil.containsAllIgnoreCase(null, ""));
        assertFalse(StringUtil.containsAllIgnoreCase(null, "a"));
        assertFalse(StringUtil.containsAllIgnoreCase("alibaba", (String) null));
        assertFalse(StringUtil.containsAllIgnoreCase("alibaba", "c", "d", "e"));
        assertTrue(StringUtil.containsAllIgnoreCase("alibaba", "a", "b", "li"));
        assertFalse(StringUtil.containsAllIgnoreCase("alibaba", "c", "de", "li"));
        assertTrue(StringUtil.containsAllIgnoreCase("alibaba", "A", "B", "LI"));
        assertFalse(StringUtil.containsAllIgnoreCase("alibaba", "C", "DE", "LO"));
    }

    /**
     * Method: indexOf(final String param, final String search)
     */
    @Test
    public void testIndexOf() {
        assertEquals(-1, StringUtil.indexOf(null, null));
        assertEquals(-1, StringUtil.indexOf(null, ""));
        assertEquals(-1, StringUtil.indexOf("", ""));
        assertEquals(-1, StringUtil.indexOf("", null));
        assertEquals(-1, StringUtil.indexOf("alibaba", ""));
        assertEquals(0, StringUtil.indexOf("alibaba", "a"));
        assertEquals(4, StringUtil.indexOf("alibaba", "ab"));
        assertEquals(3, StringUtil.indexOf("alibaba", "ba"));
        assertEquals(-1, StringUtil.indexOf("alibaba", "ca"));
        assertEquals(-1, StringUtil.indexOf("alibaba", "AB"));
        assertEquals(-1, StringUtil.indexOf("alibaba", "BA"));
    }

    /**
     * Method: indexOfIgnoreCase(final String param, final String search)
     */
    @Test
    public void testIndexOfIgnoreCase() {
        assertEquals(-1, StringUtil.indexOfIgnoreCase(null, null));
        assertEquals(-1, StringUtil.indexOfIgnoreCase(null, ""));
        assertEquals(-1, StringUtil.indexOfIgnoreCase("", ""));
        assertEquals(-1, StringUtil.indexOfIgnoreCase("", null));
        assertEquals(-1, StringUtil.indexOfIgnoreCase("alibaba", ""));
        assertEquals(0, StringUtil.indexOfIgnoreCase("alibaba", "a"));
        assertEquals(4, StringUtil.indexOfIgnoreCase("alibaba", "ab"));
        assertEquals(3, StringUtil.indexOfIgnoreCase("alibaba", "ba"));
        assertEquals(-1, StringUtil.indexOfIgnoreCase("alibaba", "ca"));
        assertEquals(4, StringUtil.indexOfIgnoreCase("alibaba", "AB"));
        assertEquals(3, StringUtil.indexOfIgnoreCase("alibaba", "BA"));
    }

    /**
     * Method: lastIndexOf(final String param, final String search)
     */
    @Test
    public void testLastIndexOf() {
        assertEquals(-1, StringUtil.lastIndexOf(null, null));
        assertEquals(-1, StringUtil.lastIndexOf(null, ""));
        assertEquals(-1, StringUtil.lastIndexOf("", ""));
        assertEquals(-1, StringUtil.lastIndexOf("", null));
        assertEquals(-1, StringUtil.lastIndexOf("alibaba", ""));
        assertEquals(6, StringUtil.lastIndexOf("alibaba", "a"));
        assertEquals(4, StringUtil.lastIndexOf("alibaba", "ab"));
        assertEquals(5, StringUtil.lastIndexOf("alibaba", "ba"));
        assertEquals(-1, StringUtil.lastIndexOf("alibaba", "ca"));
        assertEquals(-1, StringUtil.lastIndexOf("alibaba", "AB"));
        assertEquals(-1, StringUtil.lastIndexOf("alibaba", "BA"));
    }

    /**
     * Method: lastIndexOfIgnoreCase(final String param, final String search)
     */
    @Test
    public void testLastIndexOfIgnoreCase() {
        assertEquals(-1, StringUtil.lastIndexOfIgnoreCase(null, null));
        assertEquals(-1, StringUtil.lastIndexOfIgnoreCase(null, ""));
        assertEquals(-1, StringUtil.lastIndexOfIgnoreCase("", ""));
        assertEquals(-1, StringUtil.lastIndexOfIgnoreCase("", null));
        assertEquals(-1, StringUtil.lastIndexOfIgnoreCase("alibaba", ""));
        assertEquals(6, StringUtil.lastIndexOfIgnoreCase("alibaba", "a"));
        assertEquals(4, StringUtil.lastIndexOfIgnoreCase("alibaba", "ab"));
        assertEquals(5, StringUtil.lastIndexOfIgnoreCase("alibaba", "ba"));
        assertEquals(-1, StringUtil.lastIndexOfIgnoreCase("alibaba", "ca"));
        assertEquals(4, StringUtil.lastIndexOfIgnoreCase("alibaba", "AB"));
        assertEquals(5, StringUtil.lastIndexOfIgnoreCase("alibaba", "BA"));
    }

    /**
     * Method: substring(final String param, int start)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testSubstring() {
        String param = "admin";
        assertEquals("", StringUtil.substring(null, 1));
        assertEquals("admin", StringUtil.substring(param, -1));
        assertEquals("admin", StringUtil.substring(param, 0));
        assertEquals("dmin", StringUtil.substring(param, 1));
        assertEquals("min", StringUtil.substring(param, 2));
        assertEquals("in", StringUtil.substring(param, 3));
        assertEquals("n", StringUtil.substring(param, 4));
        assertEquals("", StringUtil.substring(param, 5));
        assertEquals("", StringUtil.substring(param, 6));
        // -------------------------------------------------
        assertEquals("", StringUtil.substring(null, 1, 4));
        assertEquals("", StringUtil.substring(param, -1, -1));
        assertEquals("", StringUtil.substring(param, -1, 0));
        assertEquals("a", StringUtil.substring(param, -1, 1));
        assertEquals("ad", StringUtil.substring(param, -1, 2));
        assertEquals("adm", StringUtil.substring(param, -1, 3));
        assertEquals("admi", StringUtil.substring(param, -1, 4));
        assertEquals("admin", StringUtil.substring(param, -1, 5));
        assertEquals("admin", StringUtil.substring(param, -1, 6));

        assertEquals("", StringUtil.substring(param, 0, -1));
        assertEquals("", StringUtil.substring(param, 0, 0));
        assertEquals("a", StringUtil.substring(param, 0, 1));
        assertEquals("ad", StringUtil.substring(param, 0, 2));
        assertEquals("adm", StringUtil.substring(param, 0, 3));
        assertEquals("admi", StringUtil.substring(param, 0, 4));
        assertEquals("admin", StringUtil.substring(param, 0, 5));
        assertEquals("admin", StringUtil.substring(param, 0, 6));

        assertEquals("", StringUtil.substring(param, 1, -1));
        assertEquals("", StringUtil.substring(param, 1, 0));
        assertEquals("", StringUtil.substring(param, 1, 1));
        assertEquals("d", StringUtil.substring(param, 1, 2));
        assertEquals("dm", StringUtil.substring(param, 1, 3));
        assertEquals("dmi", StringUtil.substring(param, 1, 4));
        assertEquals("dmin", StringUtil.substring(param, 1, 5));
        assertEquals("dmin", StringUtil.substring(param, 1, 6));

        assertEquals("", StringUtil.substring(param, 2, -1));
        assertEquals("", StringUtil.substring(param, 2, 0));
        assertEquals("", StringUtil.substring(param, 2, 1));
        assertEquals("", StringUtil.substring(param, 2, 2));
        assertEquals("m", StringUtil.substring(param, 2, 3));
        assertEquals("mi", StringUtil.substring(param, 2, 4));
        assertEquals("min", StringUtil.substring(param, 2, 5));
        assertEquals("min", StringUtil.substring(param, 2, 6));

        assertEquals("", StringUtil.substring(param, 3, -1));
        assertEquals("", StringUtil.substring(param, 3, 0));
        assertEquals("", StringUtil.substring(param, 3, 1));
        assertEquals("", StringUtil.substring(param, 3, 2));
        assertEquals("", StringUtil.substring(param, 3, 3));
        assertEquals("i", StringUtil.substring(param, 3, 4));
        assertEquals("in", StringUtil.substring(param, 3, 5));
        assertEquals("in", StringUtil.substring(param, 3, 6));

        assertEquals("", StringUtil.substring(param, 4, -1));
        assertEquals("", StringUtil.substring(param, 4, 0));
        assertEquals("", StringUtil.substring(param, 4, 1));
        assertEquals("", StringUtil.substring(param, 4, 2));
        assertEquals("", StringUtil.substring(param, 4, 3));
        assertEquals("", StringUtil.substring(param, 4, 4));
        assertEquals("n", StringUtil.substring(param, 4, 5));
        assertEquals("n", StringUtil.substring(param, 4, 6));

        assertEquals("", StringUtil.substring(param, 5, -1));
        assertEquals("", StringUtil.substring(param, 5, 0));
        assertEquals("", StringUtil.substring(param, 5, 1));
        assertEquals("", StringUtil.substring(param, 5, 2));
        assertEquals("", StringUtil.substring(param, 5, 3));
        assertEquals("", StringUtil.substring(param, 5, 4));
        assertEquals("", StringUtil.substring(param, 5, 5));
        assertEquals("", StringUtil.substring(param, 5, 6));

        assertEquals("", StringUtil.substring(param, 6, -1));
        assertEquals("", StringUtil.substring(param, 6, 0));
        assertEquals("", StringUtil.substring(param, 6, 1));
        assertEquals("", StringUtil.substring(param, 6, 2));
        assertEquals("", StringUtil.substring(param, 6, 3));
        assertEquals("", StringUtil.substring(param, 6, 4));
        assertEquals("", StringUtil.substring(param, 6, 5));
        assertEquals("", StringUtil.substring(param, 6, 6));
    }

    /**
     * Method: substringAfter(final String param, final String separator)
     */
    @Test
    public void testSubstringAfter() {
        String param = "redis:key:admin", separator = ":";
        assertEquals("", StringUtil.substringAfter(null, null));
        assertEquals("", StringUtil.substringAfter(null, ""));
        assertEquals("", StringUtil.substringAfter("", ""));
        assertEquals("", StringUtil.substringAfter("", null));
        assertEquals("", StringUtil.substringAfter(null, separator));
        assertEquals("", StringUtil.substringAfter("", separator));
        assertEquals("redis:key:admin", StringUtil.substringAfter(param, null));
        assertEquals("redis:key:admin", StringUtil.substringAfter(param, ""));
        assertEquals("key:admin", StringUtil.substringAfter(param, separator));
        assertEquals("redis:key:admin", StringUtil.substringAfter(param, "&"));
        assertEquals(":admin", StringUtil.substringAfter(param, "key"));
        assertEquals("admin", StringUtil.substringAfter(param, "key:"));
        assertEquals("redis:key:admin", StringUtil.substringAfter(param, "KEY"));
        assertEquals("redis:key:admin", StringUtil.substringAfter(param, "KEY:"));

        assertEquals("key:admin", StringUtil.substringAfter(param, separator, false));
        assertEquals("redis:key:admin", StringUtil.substringAfter(param, "&", false));
        assertEquals(":admin", StringUtil.substringAfter(param, "key", false));
        assertEquals("admin", StringUtil.substringAfter(param, "key:", false));
        assertEquals("redis:key:admin", StringUtil.substringAfter(param, "KEY", false));
        assertEquals("redis:key:admin", StringUtil.substringAfter(param, "KEY:", false));

        assertEquals("admin", StringUtil.substringAfter(param, separator, true));
        assertEquals("redis:key:admin", StringUtil.substringAfter(param, "&", true));
        assertEquals(":admin", StringUtil.substringAfter(param, "key", true));
        assertEquals("admin", StringUtil.substringAfter(param, "key:", true));
        assertEquals("redis:key:admin", StringUtil.substringAfter(param, "KEY", true));
        assertEquals("redis:key:admin", StringUtil.substringAfter(param, "KEY:", true));
    }

    /**
     * Method: substringAfterIgnoreCase(final String param, final String separator)
     */
    @Test
    public void testSubstringAfterIgnoreCase() {
        String param = "redis:key:admin", separator = ":";
        assertEquals("", StringUtil.substringAfterIgnoreCase(null, null));
        assertEquals("", StringUtil.substringAfterIgnoreCase(null, ""));
        assertEquals("", StringUtil.substringAfterIgnoreCase("", ""));
        assertEquals("", StringUtil.substringAfterIgnoreCase("", null));
        assertEquals("", StringUtil.substringAfterIgnoreCase(null, separator));
        assertEquals("", StringUtil.substringAfterIgnoreCase("", separator));
        assertEquals("redis:key:admin", StringUtil.substringAfterIgnoreCase(param, null));
        assertEquals("redis:key:admin", StringUtil.substringAfterIgnoreCase(param, ""));
        assertEquals("key:admin", StringUtil.substringAfterIgnoreCase(param, separator));
        assertEquals("redis:key:admin", StringUtil.substringAfterIgnoreCase(param, "&"));
        assertEquals(":admin", StringUtil.substringAfterIgnoreCase(param, "key"));
        assertEquals("admin", StringUtil.substringAfterIgnoreCase(param, "key:"));
        assertEquals(":admin", StringUtil.substringAfterIgnoreCase(param, "KEY"));
        assertEquals("admin", StringUtil.substringAfterIgnoreCase(param, "KEY:"));

        assertEquals("key:admin", StringUtil.substringAfterIgnoreCase(param, separator, false));
        assertEquals("redis:key:admin", StringUtil.substringAfterIgnoreCase(param, "&", false));
        assertEquals(":admin", StringUtil.substringAfterIgnoreCase(param, "key", false));
        assertEquals("admin", StringUtil.substringAfterIgnoreCase(param, "key:", false));
        assertEquals(":admin", StringUtil.substringAfterIgnoreCase(param, "KEY", false));
        assertEquals("admin", StringUtil.substringAfterIgnoreCase(param, "KEY:", false));

        assertEquals("admin", StringUtil.substringAfterIgnoreCase(param, separator, true));
        assertEquals("redis:key:admin", StringUtil.substringAfterIgnoreCase(param, "&", true));
        assertEquals(":admin", StringUtil.substringAfterIgnoreCase(param, "key", true));
        assertEquals("admin", StringUtil.substringAfterIgnoreCase(param, "key:", true));
        assertEquals(":admin", StringUtil.substringAfterIgnoreCase(param, "KEY", true));
        assertEquals("admin", StringUtil.substringAfterIgnoreCase(param, "KEY:", true));
    }

    /**
     * Method: substringBefore(final String param, final String separator)
     */
    @Test
    public void testSubstringBefore() {
        String param = "redis:key:admin", separator = ":";
        assertEquals("", StringUtil.substringBefore(null, null));
        assertEquals("", StringUtil.substringBefore(null, ""));
        assertEquals("", StringUtil.substringBefore("", ""));
        assertEquals("", StringUtil.substringBefore("", null));
        assertEquals("", StringUtil.substringBefore(null, separator));
        assertEquals("", StringUtil.substringBefore("", separator));
        assertEquals("redis:key:admin", StringUtil.substringBefore(param, null));
        assertEquals("redis:key:admin", StringUtil.substringBefore(param, ""));
        assertEquals("redis", StringUtil.substringBefore(param, separator));
        assertEquals("redis:key:admin", StringUtil.substringBefore(param, "&"));
        assertEquals("redis:", StringUtil.substringBefore(param, "key"));
        assertEquals("redis:", StringUtil.substringBefore(param, "key:"));
        assertEquals("redis:key:admin", StringUtil.substringBefore(param, "KEY"));
        assertEquals("redis:key:admin", StringUtil.substringBefore(param, "KEY:"));

        assertEquals("redis", StringUtil.substringBefore(param, separator, false));
        assertEquals("redis:key:admin", StringUtil.substringBefore(param, "&", false));
        assertEquals("redis:", StringUtil.substringBefore(param, "key", false));
        assertEquals("redis:", StringUtil.substringBefore(param, "key:", false));
        assertEquals("redis:key:admin", StringUtil.substringBefore(param, "KEY", false));
        assertEquals("redis:key:admin", StringUtil.substringBefore(param, "KEY:", false));

        assertEquals("redis:key", StringUtil.substringBefore(param, separator, true));
        assertEquals("redis:key:admin", StringUtil.substringBefore(param, "&", true));
        assertEquals("redis:", StringUtil.substringBefore(param, "key", true));
        assertEquals("redis:", StringUtil.substringBefore(param, "key:", true));
        assertEquals("redis:key:admin", StringUtil.substringBefore(param, "KEY", true));
        assertEquals("redis:key:admin", StringUtil.substringBefore(param, "KEY:", true));
    }

    /**
     * Method: substringBeforeIgnoreCase(final String param, final String separator)
     */
    @Test
    public void testSubstringBeforeIgnoreCase() {
        String param = "redis:key:admin", separator = ":";
        assertEquals("", StringUtil.substringBeforeIgnoreCase(null, null));
        assertEquals("", StringUtil.substringBeforeIgnoreCase(null, ""));
        assertEquals("", StringUtil.substringBeforeIgnoreCase("", ""));
        assertEquals("", StringUtil.substringBeforeIgnoreCase("", null));
        assertEquals("", StringUtil.substringBeforeIgnoreCase(null, separator));
        assertEquals("", StringUtil.substringBeforeIgnoreCase("", separator));
        assertEquals("redis:key:admin", StringUtil.substringBeforeIgnoreCase(param, null));
        assertEquals("redis:key:admin", StringUtil.substringBeforeIgnoreCase(param, ""));
        assertEquals("redis", StringUtil.substringBeforeIgnoreCase(param, separator));
        assertEquals("redis:key:admin", StringUtil.substringBeforeIgnoreCase(param, "&"));
        assertEquals("redis:", StringUtil.substringBeforeIgnoreCase(param, "key"));
        assertEquals("redis:", StringUtil.substringBeforeIgnoreCase(param, "key:"));
        assertEquals("redis:", StringUtil.substringBeforeIgnoreCase(param, "KEY"));
        assertEquals("redis:", StringUtil.substringBeforeIgnoreCase(param, "KEY:"));

        assertEquals("redis", StringUtil.substringBeforeIgnoreCase(param, separator, false));
        assertEquals("redis:key:admin", StringUtil.substringBeforeIgnoreCase(param, "&", false));
        assertEquals("redis:", StringUtil.substringBeforeIgnoreCase(param, "key", false));
        assertEquals("redis:", StringUtil.substringBeforeIgnoreCase(param, "key:", false));
        assertEquals("redis:", StringUtil.substringBeforeIgnoreCase(param, "KEY", false));
        assertEquals("redis:", StringUtil.substringBeforeIgnoreCase(param, "KEY:", false));

        assertEquals("redis:key", StringUtil.substringBeforeIgnoreCase(param, separator, true));
        assertEquals("redis:key:admin", StringUtil.substringBeforeIgnoreCase(param, "&", true));
        assertEquals("redis:", StringUtil.substringBeforeIgnoreCase(param, "key", true));
        assertEquals("redis:", StringUtil.substringBeforeIgnoreCase(param, "key:", true));
        assertEquals("redis:", StringUtil.substringBeforeIgnoreCase(param, "KEY", true));
        assertEquals("redis:", StringUtil.substringBeforeIgnoreCase(param, "KEY:", true));
    }

    /**
     * Method: substringBetween(final String param, final String prefix, final String suffix)
     */
    @Test
    public void testSubstringBetween() {
        assertEquals("", StringUtil.substringBetween(null, null, null));
        assertEquals("", StringUtil.substringBetween(null, null, ""));
        assertEquals("", StringUtil.substringBetween(null, "", null));
        assertEquals("", StringUtil.substringBetween("", "", ""));
        assertEquals("", StringUtil.substringBetween("", null, ""));
        assertEquals("", StringUtil.substringBetween("", "", null));
        assertEquals("", StringUtil.substringBetween("", "", null));
        assertEquals("", StringUtil.substringBetween("redis:key:admin", null, null));
        assertEquals("", StringUtil.substringBetween("redis:key:admin", null, ""));
        assertEquals("", StringUtil.substringBetween("redis:key:admin", "", null));
        assertEquals("", StringUtil.substringBetween("redis:key:admin", "", ""));
        assertEquals(":", StringUtil.substringBetween("redis:key:admin", "redis", "key"));
        assertEquals("", StringUtil.substringBetween("redis:key:admin", "key", "redis"));
        assertEquals("key", StringUtil.substringBetween("redis:key:admin", ":", ":"));
        assertEquals("s:key:adm", StringUtil.substringBetween("redis:key:admin", "i", "i"));
        assertEquals("", StringUtil.substringBetween("redis:key:admin", "I", "I"));
        assertEquals("", StringUtil.substringBetween("redis:key:admin", "REDIS", "KEY"));
        assertEquals("", StringUtil.substringBetween("redis:key:admin", "key:", "admin"));
        assertEquals("", StringUtil.substringBetween("redis:key:admin", "KEY:", "ADMIN"));
        assertEquals("i", StringUtil.substringBetween("redis:key:admin", "m", "n"));
        assertEquals("", StringUtil.substringBetween("redis:key:admin", "M", "N"));
        assertEquals(":key:", StringUtil.substringBetween("redis:key:admin", "redis", "admin"));
        assertEquals("", StringUtil.substringBetween("redis:key:admin", "REDIS", "ADMIN"));
    }

    /**
     * Method: substringBetweenIgnoreCase(final String param, final String prefix, final String suffix)
     */
    @Test
    public void testSubstringBetweenIgnoreCase() {
        assertEquals("", StringUtil.substringBetweenIgnoreCase(null, null, null));
        assertEquals("", StringUtil.substringBetweenIgnoreCase(null, null, ""));
        assertEquals("", StringUtil.substringBetweenIgnoreCase(null, "", null));
        assertEquals("", StringUtil.substringBetweenIgnoreCase("", "", ""));
        assertEquals("", StringUtil.substringBetweenIgnoreCase("", null, ""));
        assertEquals("", StringUtil.substringBetweenIgnoreCase("", "", null));
        assertEquals("", StringUtil.substringBetweenIgnoreCase("", "", null));
        assertEquals("", StringUtil.substringBetweenIgnoreCase("redis:key:admin", null, null));
        assertEquals("", StringUtil.substringBetweenIgnoreCase("redis:key:admin", null, ""));
        assertEquals("", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "", null));
        assertEquals("", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "", ""));
        assertEquals(":", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "redis", "key"));
        assertEquals("", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "key", "redis"));
        assertEquals("key", StringUtil.substringBetweenIgnoreCase("redis:key:admin", ":", ":"));
        assertEquals("s:key:adm", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "i", "i"));
        assertEquals("s:key:adm", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "I", "I"));
        assertEquals(":", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "REDIS", "KEY"));
        assertEquals("", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "key:", "admin"));
        assertEquals("", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "KEY:", "ADMIN"));
        assertEquals("i", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "m", "n"));
        assertEquals("i", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "M", "N"));
        assertEquals(":key:", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "redis", "admin"));
        assertEquals(":key:", StringUtil.substringBetweenIgnoreCase("redis:key:admin", "REDIS", "ADMIN"));
    }

    /**
     * Method: replace(final String param, final String search, final String replacement)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testReplace() {
        assertEquals("", StringUtil.replace(null, null, null));
        assertEquals("", StringUtil.replace(null, null, ""));
        assertEquals("", StringUtil.replace(null, "", null));
        assertEquals("", StringUtil.replace("", "", ""));
        assertEquals("", StringUtil.replace("", null, ""));
        assertEquals("", StringUtil.replace("", "", null));
        assertEquals("", StringUtil.replace("", "", null));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", null, null));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", null, ""));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", "", null));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", "", ""));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", null, "$"));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", "", "$"));
        assertEquals("rediskeyadmin", StringUtil.replace("redis:key:admin", ":", null));
        assertEquals("rediskeyadmin", StringUtil.replace("redis:key:admin", ":", ""));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", ":", ":"));
        assertEquals("redis$key$admin", StringUtil.replace("redis:key:admin", ":", "$"));
        assertEquals("redis:code:admin", StringUtil.replace("redis:key:admin", "key", "code"));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", "KEY", "code"));
        assertEquals("code:key:admin", StringUtil.replace("redis:key:admin", "redis", "code"));
        assertEquals(":key:admin", StringUtil.replace("redis:key:admin", "redis", ""));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", "REDIS", "code"));
        assertEquals("redis:key:code", StringUtil.replace("redis:key:admin", "admin", "code"));
        assertEquals("redis:key:", StringUtil.replace("redis:key:admin", "admin", null));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", "ADMIN", "code"));
        assertEquals("red*s:key:adm*n", StringUtil.replace("redis:key:admin", "i", "*"));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", "I", "*"));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", "c", "z"));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", "C", "z"));
        assertEquals("zedis:key:admin", StringUtil.replace("redis:key:admin", "r", "z"));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", "R", "z"));
        assertEquals("redis:key:admiz", StringUtil.replace("redis:key:admin", "n", "z"));
        assertEquals("redis:key:admin", StringUtil.replace("redis:key:admin", "N", "z"));
    }

    /**
     * Method: replaceIgnoreCase(final String param, final String search, final String replacement)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testReplaceIgnoreCase() {
        assertEquals("", StringUtil.replaceIgnoreCase(null, null, null));
        assertEquals("", StringUtil.replaceIgnoreCase(null, null, ""));
        assertEquals("", StringUtil.replaceIgnoreCase(null, "", null));
        assertEquals("", StringUtil.replaceIgnoreCase("", "", ""));
        assertEquals("", StringUtil.replaceIgnoreCase("", null, ""));
        assertEquals("", StringUtil.replaceIgnoreCase("", "", null));
        assertEquals("", StringUtil.replaceIgnoreCase("", "", null));
        assertEquals("redis:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", null, null));
        assertEquals("redis:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", null, ""));
        assertEquals("redis:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", "", null));
        assertEquals("redis:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", "", ""));
        assertEquals("redis:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", null, "$"));
        assertEquals("redis:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", "", "$"));
        assertEquals("rediskeyadmin", StringUtil.replaceIgnoreCase("redis:key:admin", ":", null));
        assertEquals("rediskeyadmin", StringUtil.replaceIgnoreCase("redis:key:admin", ":", ""));
        assertEquals("redis:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", ":", ":"));
        assertEquals("redis$key$admin", StringUtil.replaceIgnoreCase("redis:key:admin", ":", "$"));
        assertEquals("redis:code:admin", StringUtil.replaceIgnoreCase("redis:key:admin", "key", "code"));
        assertEquals("redis:code:admin", StringUtil.replaceIgnoreCase("redis:key:admin", "KEY", "code"));
        assertEquals("code:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", "redis", "code"));
        assertEquals(":key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", "redis", ""));
        assertEquals("code:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", "REDIS", "code"));
        assertEquals("redis:key:code", StringUtil.replaceIgnoreCase("redis:key:admin", "admin", "code"));
        assertEquals("redis:key:", StringUtil.replaceIgnoreCase("redis:key:admin", "admin", null));
        assertEquals("redis:key:code", StringUtil.replaceIgnoreCase("redis:key:admin", "ADMIN", "code"));
        assertEquals("red*s:key:adm*n", StringUtil.replaceIgnoreCase("redis:key:admin", "i", "*"));
        assertEquals("red*s:key:adm*n", StringUtil.replaceIgnoreCase("redis:key:admin", "I", "*"));
        assertEquals("redis:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", "c", "z"));
        assertEquals("redis:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", "C", "z"));
        assertEquals("zedis:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", "r", "z"));
        assertEquals("zedis:key:admin", StringUtil.replaceIgnoreCase("redis:key:admin", "R", "z"));
        assertEquals("redis:key:admiz", StringUtil.replaceIgnoreCase("redis:key:admin", "n", "z"));
        assertEquals("redis:key:admiz", StringUtil.replaceIgnoreCase("redis:key:admin", "N", "z"));
    }

    /**
     * Method: replaceRegex(final String param, final String regex, final String replacement)
     */
    @Test
    public void testReplaceRegex() {
        assertEquals("", StringUtil.replaceRegex(null, null, null));
        assertEquals("", StringUtil.replaceRegex(null, null, ""));
        assertEquals("", StringUtil.replaceRegex(null, "", null));
        assertEquals("", StringUtil.replaceRegex("", "", ""));
        assertEquals("", StringUtil.replaceRegex("", null, ""));
        assertEquals("", StringUtil.replaceRegex("", "", null));
        assertEquals("", StringUtil.replaceRegex("", "", null));
        assertEquals("redis1key2admin", StringUtil.replaceRegex("redis1key2admin", null, null));
        assertEquals("redis1key2admin", StringUtil.replaceRegex("redis1key2admin", null, ""));
        assertEquals("redis1key2admin", StringUtil.replaceRegex("redis1key2admin", "", null));
        assertEquals("redis1key2admin", StringUtil.replaceRegex("redis1key2admin", "", ""));
        assertEquals("redis1key2admin", StringUtil.replaceRegex("redis1key2admin", null, "$"));
        assertEquals("redis1key2admin", StringUtil.replaceRegex("redis1key2admin", "", "$"));
        assertEquals("redis1key2admin", StringUtil.replaceRegex("redis1key2admin", ":", null));
        assertEquals("redis1key2admin", StringUtil.replaceRegex("redis1key2admin", ":", ""));
        assertEquals("redis:key2admin", StringUtil.replaceRegex("redis1key2admin", "1", ":"));
        assertEquals("redis1key:admin", StringUtil.replaceRegex("redis1key2admin", "2", ":"));
        assertEquals("redis1key%admin", StringUtil.replaceRegex("redis1key2admin", "2", "%"));
        assertEquals("redis:key:admin", StringUtil.replaceRegex("redis1key2admin", "\\d", ":"));
        assertEquals("red*s:key:*dm*n", StringUtil.replaceRegex("redis:key:admin", "[a,i]", "*"));
        assertEquals("****s:**y:*****", StringUtil.replaceRegex("redis:key:admin", "[a-r]", "*"));
        assertEquals("redis:key:admin", StringUtil.replaceRegex("redis:key:admin", ":", ":"));
        assertEquals("redis*key*admin", StringUtil.replaceRegex("redis:key:admin", ":", "*"));
    }

    /**
     * Method: replaceFirstRegex(final String param, final String regex, final String replacement)
     */
    @Test
    public void testReplaceFirstRegex() {
        assertEquals("", StringUtil.replaceFirstRegex(null, null, null));
        assertEquals("", StringUtil.replaceFirstRegex(null, null, ""));
        assertEquals("", StringUtil.replaceFirstRegex(null, "", null));
        assertEquals("", StringUtil.replaceFirstRegex("", "", ""));
        assertEquals("", StringUtil.replaceFirstRegex("", null, ""));
        assertEquals("", StringUtil.replaceFirstRegex("", "", null));
        assertEquals("", StringUtil.replaceFirstRegex("", "", null));
        assertEquals("redis1key2admin", StringUtil.replaceFirstRegex("redis1key2admin", null, null));
        assertEquals("redis1key2admin", StringUtil.replaceFirstRegex("redis1key2admin", null, ""));
        assertEquals("redis1key2admin", StringUtil.replaceFirstRegex("redis1key2admin", "", null));
        assertEquals("redis1key2admin", StringUtil.replaceFirstRegex("redis1key2admin", "", ""));
        assertEquals("redis1key2admin", StringUtil.replaceFirstRegex("redis1key2admin", null, "$"));
        assertEquals("redis1key2admin", StringUtil.replaceFirstRegex("redis1key2admin", "", "$"));
        assertEquals("redis1key2admin", StringUtil.replaceFirstRegex("redis1key2admin", ":", null));
        assertEquals("redis1key2admin", StringUtil.replaceFirstRegex("redis1key2admin", ":", ""));
        assertEquals("redis:key2admin", StringUtil.replaceFirstRegex("redis1key2admin", "1", ":"));
        assertEquals("redis1key:admin", StringUtil.replaceFirstRegex("redis1key2admin", "2", ":"));
        assertEquals("redis1key%admin", StringUtil.replaceFirstRegex("redis1key2admin", "2", "%"));
        assertEquals("redis:key2admin", StringUtil.replaceFirstRegex("redis1key2admin", "\\d", ":"));
        assertEquals("red*s:key:admin", StringUtil.replaceFirstRegex("redis:key:admin", "[a,i]", "*"));
        assertEquals("*edis:key:admin", StringUtil.replaceFirstRegex("redis:key:admin", "[a-r]", "*"));
        assertEquals("redis:key:admin", StringUtil.replaceFirstRegex("redis:key:admin", ":", ":"));
        assertEquals("redis*key:admin", StringUtil.replaceFirstRegex("redis:key:admin", ":", "*"));
    }

    /**
     * Method: replaceFirst(final String param, final String search, final String replacement)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testReplaceFirst() {
        assertEquals("", StringUtil.replaceFirst(null, null, null));
        assertEquals("", StringUtil.replaceFirst(null, null, ""));
        assertEquals("", StringUtil.replaceFirst(null, "", null));
        assertEquals("", StringUtil.replaceFirst("", "", ""));
        assertEquals("", StringUtil.replaceFirst("", null, ""));
        assertEquals("", StringUtil.replaceFirst("", "", null));
        assertEquals("", StringUtil.replaceFirst("", "", null));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", null, null));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", null, ""));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", "", null));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", "", ""));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", null, "$"));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", "", "$"));
        assertEquals("rediskey:admin", StringUtil.replaceFirst("redis:key:admin", ":", null));
        assertEquals("rediskey:admin", StringUtil.replaceFirst("redis:key:admin", ":", ""));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", ":", ":"));
        assertEquals("redis$key:admin", StringUtil.replaceFirst("redis:key:admin", ":", "$"));
        assertEquals("redis:code:admin", StringUtil.replaceFirst("redis:key:admin", "key", "code"));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", "KEY", "code"));
        assertEquals("code:key:admin", StringUtil.replaceFirst("redis:key:admin", "redis", "code"));
        assertEquals(":key:admin", StringUtil.replaceFirst("redis:key:admin", "redis", ""));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", "REDIS", "code"));
        assertEquals("redis:key:code", StringUtil.replaceFirst("redis:key:admin", "admin", "code"));
        assertEquals("redis:key:", StringUtil.replaceFirst("redis:key:admin", "admin", null));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", "ADMIN", "code"));
        assertEquals("red*s:key:admin", StringUtil.replaceFirst("redis:key:admin", "i", "*"));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", "I", "*"));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", "c", "z"));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", "C", "z"));
        assertEquals("zedis:key:admin", StringUtil.replaceFirst("redis:key:admin", "r", "z"));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", "R", "z"));
        assertEquals("redis:key:admiz", StringUtil.replaceFirst("redis:key:admin", "n", "z"));
        assertEquals("redis:key:admin", StringUtil.replaceFirst("redis:key:admin", "N", "z"));
    }

    /**
     * Method: replaceFirstIgnoreCase(final String param, final String search, final String replacement)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testReplaceFirstIgnoreCase() {
        assertEquals("", StringUtil.replaceFirstIgnoreCase(null, null, null));
        assertEquals("", StringUtil.replaceFirstIgnoreCase(null, null, ""));
        assertEquals("", StringUtil.replaceFirstIgnoreCase(null, "", null));
        assertEquals("", StringUtil.replaceFirstIgnoreCase("", "", ""));
        assertEquals("", StringUtil.replaceFirstIgnoreCase("", null, ""));
        assertEquals("", StringUtil.replaceFirstIgnoreCase("", "", null));
        assertEquals("", StringUtil.replaceFirstIgnoreCase("", "", null));
        assertEquals("redis:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", null, null));
        assertEquals("redis:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", null, ""));
        assertEquals("redis:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "", null));
        assertEquals("redis:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "", ""));
        assertEquals("redis:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", null, "$"));
        assertEquals("redis:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "", "$"));
        assertEquals("rediskey:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", ":", null));
        assertEquals("rediskey:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", ":", ""));
        assertEquals("redis:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", ":", ":"));
        assertEquals("redis$key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", ":", "$"));
        assertEquals("redis:code:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "key", "code"));
        assertEquals("redis:code:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "KEY", "code"));
        assertEquals("code:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "redis", "code"));
        assertEquals(":key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "redis", ""));
        assertEquals("code:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "REDIS", "code"));
        assertEquals("redis:key:code", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "admin", "code"));
        assertEquals("redis:key:", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "admin", null));
        assertEquals("redis:key:code", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "ADMIN", "code"));
        assertEquals("red*s:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "i", "*"));
        assertEquals("red*s:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "I", "*"));
        assertEquals("redis:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "c", "z"));
        assertEquals("redis:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "C", "z"));
        assertEquals("zedis:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "r", "z"));
        assertEquals("zedis:key:admin", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "R", "z"));
        assertEquals("redis:key:admiz", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "n", "z"));
        assertEquals("redis:key:admiz", StringUtil.replaceFirstIgnoreCase("redis:key:admin", "N", "z"));
    }

    /**
     * Method: replaceLast(final String param, final String search, final String replacement)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testReplaceLast() {
        assertEquals("", StringUtil.replaceLast(null, null, null));
        assertEquals("", StringUtil.replaceLast(null, null, ""));
        assertEquals("", StringUtil.replaceLast(null, "", null));
        assertEquals("", StringUtil.replaceLast("", "", ""));
        assertEquals("", StringUtil.replaceLast("", null, ""));
        assertEquals("", StringUtil.replaceLast("", "", null));
        assertEquals("", StringUtil.replaceLast("", "", null));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", null, null));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", null, ""));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", "", null));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", "", ""));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", null, "$"));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", "", "$"));
        assertEquals("redis:keyadmin", StringUtil.replaceLast("redis:key:admin", ":", null));
        assertEquals("redis:keyadmin", StringUtil.replaceLast("redis:key:admin", ":", ""));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", ":", ":"));
        assertEquals("redis:key$admin", StringUtil.replaceLast("redis:key:admin", ":", "$"));
        assertEquals("redis:code:admin", StringUtil.replaceLast("redis:key:admin", "key", "code"));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", "KEY", "code"));
        assertEquals("code:key:admin", StringUtil.replaceLast("redis:key:admin", "redis", "code"));
        assertEquals(":key:admin", StringUtil.replaceLast("redis:key:admin", "redis", ""));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", "REDIS", "code"));
        assertEquals("redis:key:code", StringUtil.replaceLast("redis:key:admin", "admin", "code"));
        assertEquals("redis:key:", StringUtil.replaceLast("redis:key:admin", "admin", null));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", "ADMIN", "code"));
        assertEquals("redis:key:adm*n", StringUtil.replaceLast("redis:key:admin", "i", "*"));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", "I", "*"));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", "c", "z"));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", "C", "z"));
        assertEquals("zedis:key:admin", StringUtil.replaceLast("redis:key:admin", "r", "z"));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", "R", "z"));
        assertEquals("redis:key:admiz", StringUtil.replaceLast("redis:key:admin", "n", "z"));
        assertEquals("redis:key:admin", StringUtil.replaceLast("redis:key:admin", "N", "z"));
    }

    /**
     * Method: replaceLastIgnoreCase(final String param, final String search, final String replacement)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testReplaceLastIgnoreCase() {
        assertEquals("", StringUtil.replaceLastIgnoreCase(null, null, null));
        assertEquals("", StringUtil.replaceLastIgnoreCase(null, null, ""));
        assertEquals("", StringUtil.replaceLastIgnoreCase(null, "", null));
        assertEquals("", StringUtil.replaceLastIgnoreCase("", "", ""));
        assertEquals("", StringUtil.replaceLastIgnoreCase("", null, ""));
        assertEquals("", StringUtil.replaceLastIgnoreCase("", "", null));
        assertEquals("", StringUtil.replaceLastIgnoreCase("", "", null));
        assertEquals("redis:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", null, null));
        assertEquals("redis:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", null, ""));
        assertEquals("redis:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", "", null));
        assertEquals("redis:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", "", ""));
        assertEquals("redis:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", null, "$"));
        assertEquals("redis:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", "", "$"));
        assertEquals("redis:keyadmin", StringUtil.replaceLastIgnoreCase("redis:key:admin", ":", null));
        assertEquals("redis:keyadmin", StringUtil.replaceLastIgnoreCase("redis:key:admin", ":", ""));
        assertEquals("redis:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", ":", ":"));
        assertEquals("redis:key$admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", ":", "$"));
        assertEquals("redis:code:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", "key", "code"));
        assertEquals("redis:code:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", "KEY", "code"));
        assertEquals("code:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", "redis", "code"));
        assertEquals(":key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", "redis", ""));
        assertEquals("code:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", "REDIS", "code"));
        assertEquals("redis:key:code", StringUtil.replaceLastIgnoreCase("redis:key:admin", "admin", "code"));
        assertEquals("redis:key:", StringUtil.replaceLastIgnoreCase("redis:key:admin", "admin", null));
        assertEquals("redis:key:code", StringUtil.replaceLastIgnoreCase("redis:key:admin", "ADMIN", "code"));
        assertEquals("redis:key:adm*n", StringUtil.replaceLastIgnoreCase("redis:key:admin", "i", "*"));
        assertEquals("redis:key:adm*n", StringUtil.replaceLastIgnoreCase("redis:key:admin", "I", "*"));
        assertEquals("redis:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", "c", "z"));
        assertEquals("redis:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", "C", "z"));
        assertEquals("zedis:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", "r", "z"));
        assertEquals("zedis:key:admin", StringUtil.replaceLastIgnoreCase("redis:key:admin", "R", "z"));
        assertEquals("redis:key:admiz", StringUtil.replaceLastIgnoreCase("redis:key:admin", "n", "z"));
        assertEquals("redis:key:admiz", StringUtil.replaceLastIgnoreCase("redis:key:admin", "N", "z"));
    }

    /**
     * Method: remove(final String param, final String search)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testRemove() {
        assertEquals("", StringUtil.remove(null, (String) null));
        assertEquals("", StringUtil.remove(null, ""));
        assertEquals("", StringUtil.remove("", ""));
        assertEquals("", StringUtil.remove("", (String) null));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", (String) null));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", ""));
        assertEquals("rediskeyadmin", StringUtil.remove("redis:key:admin", ":"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "$"));
        assertEquals("edis:key:admin", StringUtil.remove("redis:key:admin", "r"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "R"));
        assertEquals(":key:admin", StringUtil.remove("redis:key:admin", "redis"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "REDIS"));
        assertEquals("redis::admin", StringUtil.remove("redis:key:admin", "key"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "KEY"));
        assertEquals("redis:key:", StringUtil.remove("redis:key:admin", "admin"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "ADMIN"));
        assertEquals("redis:key:admi", StringUtil.remove("redis:key:admin", "n"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "N"));
        assertEquals("reds:key:admn", StringUtil.remove("redis:key:admin", "i"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "I"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "c"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "C"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "code"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "CODE"));

        assertEquals("", StringUtil.remove(null, null, null));
        assertEquals("", StringUtil.remove(null, null, ""));
        assertEquals("", StringUtil.remove(null, "", null));
        assertEquals("", StringUtil.remove("", "", ""));
        assertEquals("", StringUtil.remove("", null, ""));
        assertEquals("", StringUtil.remove("", "", null));
        assertEquals("", StringUtil.remove("", "", null));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", null, null));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", null, ""));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "", null));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "", ""));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", null, "$"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "", "$"));
        assertEquals("rediskeyadmin", StringUtil.remove("redis:key:admin", ":", null));
        assertEquals("rediskeyadmin", StringUtil.remove("redis:key:admin", ":", ""));
        assertEquals("rediskeyadmin", StringUtil.remove("redis:key:admin", ":", ":"));
        assertEquals("rediskeyadmin", StringUtil.remove("redis:key:admin", ":", "$"));
        assertEquals("::admin", StringUtil.remove("redis:key:admin", "redis", "key"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "REDIS", "KEY"));
        assertEquals("redisadmin", StringUtil.remove("redis:key:admin", ":", "key"));
        assertEquals("rediskeyadmin", StringUtil.remove("redis:key:admin", ":", "KEY"));
        assertEquals("key", StringUtil.remove("redis:key:admin", "redis", "admin", ":"));
        assertEquals("rediskeyadmin", StringUtil.remove("redis:key:admin", "REDIS", "ADMIN", ":"));
        assertEquals("ris:ky:min", StringUtil.remove("redis:key:admin", "a", "b", "c", "d", "e", "f"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "A", "B", "C", "D", "E", "F"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "$", "code", "z", "x"));
        assertEquals("redis:key:admin", StringUtil.remove("redis:key:admin", "$", "CODE", "Z", "X"));
    }

    /**
     * Method: removeIgnoreCase(final String param, final String search)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testRemoveIgnoreCase() {
        assertEquals("", StringUtil.removeIgnoreCase(null, (String) null));
        assertEquals("", StringUtil.removeIgnoreCase(null, ""));
        assertEquals("", StringUtil.removeIgnoreCase("", ""));
        assertEquals("", StringUtil.removeIgnoreCase("", (String) null));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", (String) null));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", ""));
        assertEquals("rediskeyadmin", StringUtil.removeIgnoreCase("redis:key:admin", ":"));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "$"));
        assertEquals("edis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "r"));
        assertEquals("edis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "R"));
        assertEquals(":key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "redis"));
        assertEquals(":key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "REDIS"));
        assertEquals("redis::admin", StringUtil.removeIgnoreCase("redis:key:admin", "key"));
        assertEquals("redis::admin", StringUtil.removeIgnoreCase("redis:key:admin", "KEY"));
        assertEquals("redis:key:", StringUtil.removeIgnoreCase("redis:key:admin", "admin"));
        assertEquals("redis:key:", StringUtil.removeIgnoreCase("redis:key:admin", "ADMIN"));
        assertEquals("redis:key:admi", StringUtil.removeIgnoreCase("redis:key:admin", "n"));
        assertEquals("redis:key:admi", StringUtil.removeIgnoreCase("redis:key:admin", "N"));
        assertEquals("reds:key:admn", StringUtil.removeIgnoreCase("redis:key:admin", "i"));
        assertEquals("reds:key:admn", StringUtil.removeIgnoreCase("redis:key:admin", "I"));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "c"));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "C"));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "code"));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "CODE"));

        assertEquals("", StringUtil.removeIgnoreCase(null, null, null));
        assertEquals("", StringUtil.removeIgnoreCase(null, null, ""));
        assertEquals("", StringUtil.removeIgnoreCase(null, "", null));
        assertEquals("", StringUtil.removeIgnoreCase("", "", ""));
        assertEquals("", StringUtil.removeIgnoreCase("", null, ""));
        assertEquals("", StringUtil.removeIgnoreCase("", "", null));
        assertEquals("", StringUtil.removeIgnoreCase("", "", null));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", null, null));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", null, ""));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "", null));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "", ""));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", null, "$"));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "", "$"));
        assertEquals("rediskeyadmin", StringUtil.removeIgnoreCase("redis:key:admin", ":", null));
        assertEquals("rediskeyadmin", StringUtil.removeIgnoreCase("redis:key:admin", ":", ""));
        assertEquals("rediskeyadmin", StringUtil.removeIgnoreCase("redis:key:admin", ":", ":"));
        assertEquals("rediskeyadmin", StringUtil.removeIgnoreCase("redis:key:admin", ":", "$"));
        assertEquals("::admin", StringUtil.removeIgnoreCase("redis:key:admin", "redis", "key"));
        assertEquals("::admin", StringUtil.removeIgnoreCase("redis:key:admin", "REDIS", "KEY"));
        assertEquals("redisadmin", StringUtil.removeIgnoreCase("redis:key:admin", ":", "key"));
        assertEquals("redisadmin", StringUtil.removeIgnoreCase("redis:key:admin", ":", "KEY"));
        assertEquals("key", StringUtil.removeIgnoreCase("redis:key:admin", "redis", "admin", ":"));
        assertEquals("key", StringUtil.removeIgnoreCase("redis:key:admin", "REDIS", "ADMIN", ":"));
        assertEquals("ris:ky:min", StringUtil.removeIgnoreCase("redis:key:admin", "a", "b", "c", "d", "e", "f"));
        assertEquals("ris:ky:min", StringUtil.removeIgnoreCase("redis:key:admin", "A", "B", "C", "D", "E", "F"));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "$", "code", "z", "x"));
        assertEquals("redis:key:admin", StringUtil.removeIgnoreCase("redis:key:admin", "$", "CODE", "Z", "X"));
    }

    /**
     * Method: removeFirst(final String param, final String search)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testRemoveFirst() {
        assertEquals("", StringUtil.removeFirst(null, null));
        assertEquals("", StringUtil.removeFirst(null, ""));
        assertEquals("", StringUtil.removeFirst("", ""));
        assertEquals("", StringUtil.removeFirst("", null));
        assertEquals("redis:key:admin", StringUtil.removeFirst("redis:key:admin", null));
        assertEquals("redis:key:admin", StringUtil.removeFirst("redis:key:admin", ""));
        assertEquals("rediskey:admin", StringUtil.removeFirst("redis:key:admin", ":"));
        assertEquals("redis:key:admin", StringUtil.removeFirst("redis:key:admin", "$"));
        assertEquals("edis:key:admin", StringUtil.removeFirst("redis:key:admin", "r"));
        assertEquals("redis:key:admin", StringUtil.removeFirst("redis:key:admin", "R"));
        assertEquals(":key:admin", StringUtil.removeFirst("redis:key:admin", "redis"));
        assertEquals("redis:key:admin", StringUtil.removeFirst("redis:key:admin", "REDIS"));
        assertEquals("reds:key:admin", StringUtil.removeFirst("redis:key:admin", "i"));
        assertEquals("redis:key:admin", StringUtil.removeFirst("redis:key:admin", "I"));
        assertEquals("redis:key:admin", StringUtil.removeFirst("redis:key:admin", "c"));
        assertEquals("redis:key:admin", StringUtil.removeFirst("redis:key:admin", "C"));
        assertEquals("redis:key:admin", StringUtil.removeFirst("redis:key:admin", "code"));
        assertEquals("redis:key:admin", StringUtil.removeFirst("redis:key:admin", "CODE"));

        assertEquals("", StringUtil.removeFirstIgnoreCase(null, null));
        assertEquals("", StringUtil.removeFirstIgnoreCase(null, ""));
        assertEquals("", StringUtil.removeFirstIgnoreCase("", ""));
        assertEquals("", StringUtil.removeFirstIgnoreCase("", null));
        assertEquals("redis:key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", null));
        assertEquals("redis:key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", ""));
        assertEquals("rediskey:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", ":"));
        assertEquals("redis:key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", "$"));
        assertEquals("edis:key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", "r"));
        assertEquals("edis:key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", "R"));
        assertEquals(":key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", "redis"));
        assertEquals(":key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", "REDIS"));
        assertEquals("reds:key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", "i"));
        assertEquals("reds:key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", "I"));
        assertEquals("redis:key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", "c"));
        assertEquals("redis:key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", "C"));
        assertEquals("redis:key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", "code"));
        assertEquals("redis:key:admin", StringUtil.removeFirstIgnoreCase("redis:key:admin", "CODE"));
    }

    /**
     * Method: removeLast(final String param, final String search)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testRemoveLast() {
        assertEquals("", StringUtil.removeLast(null, null));
        assertEquals("", StringUtil.removeLast(null, ""));
        assertEquals("", StringUtil.removeLast("", ""));
        assertEquals("", StringUtil.removeLast("", null));
        assertEquals("redis:key:admin", StringUtil.removeLast("redis:key:admin", null));
        assertEquals("redis:key:admin", StringUtil.removeLast("redis:key:admin", ""));
        assertEquals("redis:keyadmin", StringUtil.removeLast("redis:key:admin", ":"));
        assertEquals("redis:key:admin", StringUtil.removeLast("redis:key:admin", "$"));
        assertEquals("edis:key:admin", StringUtil.removeLast("redis:key:admin", "r"));
        assertEquals("redis:key:admin", StringUtil.removeLast("redis:key:admin", "R"));
        assertEquals(":key:admin", StringUtil.removeLast("redis:key:admin", "redis"));
        assertEquals("redis:key:admin", StringUtil.removeLast("redis:key:admin", "REDIS"));
        assertEquals("redis:key:admn", StringUtil.removeLast("redis:key:admin", "i"));
        assertEquals("redis:key:admin", StringUtil.removeLast("redis:key:admin", "I"));
        assertEquals("redis:key:admin", StringUtil.removeLast("redis:key:admin", "c"));
        assertEquals("redis:key:admin", StringUtil.removeLast("redis:key:admin", "C"));
        assertEquals("redis:key:admin", StringUtil.removeLast("redis:key:admin", "code"));
        assertEquals("redis:key:admin", StringUtil.removeLast("redis:key:admin", "CODE"));

        assertEquals("", StringUtil.removeLastIgnoreCase(null, null));
        assertEquals("", StringUtil.removeLastIgnoreCase(null, ""));
        assertEquals("", StringUtil.removeLastIgnoreCase("", ""));
        assertEquals("", StringUtil.removeLastIgnoreCase("", null));
        assertEquals("redis:key:admin", StringUtil.removeLastIgnoreCase("redis:key:admin", null));
        assertEquals("redis:key:admin", StringUtil.removeLastIgnoreCase("redis:key:admin", ""));
        assertEquals("redis:keyadmin", StringUtil.removeLastIgnoreCase("redis:key:admin", ":"));
        assertEquals("redis:key:admin", StringUtil.removeLastIgnoreCase("redis:key:admin", "$"));
        assertEquals("edis:key:admin", StringUtil.removeLastIgnoreCase("redis:key:admin", "r"));
        assertEquals("edis:key:admin", StringUtil.removeLastIgnoreCase("redis:key:admin", "R"));
        assertEquals(":key:admin", StringUtil.removeLastIgnoreCase("redis:key:admin", "redis"));
        assertEquals(":key:admin", StringUtil.removeLastIgnoreCase("redis:key:admin", "REDIS"));
        assertEquals("redis:key:admn", StringUtil.removeLastIgnoreCase("redis:key:admin", "i"));
        assertEquals("redis:key:admn", StringUtil.removeLastIgnoreCase("redis:key:admin", "I"));
        assertEquals("redis:key:admin", StringUtil.removeLastIgnoreCase("redis:key:admin", "c"));
        assertEquals("redis:key:admin", StringUtil.removeLastIgnoreCase("redis:key:admin", "C"));
        assertEquals("redis:key:admin", StringUtil.removeLastIgnoreCase("redis:key:admin", "code"));
        assertEquals("redis:key:admin", StringUtil.removeLastIgnoreCase("redis:key:admin", "CODE"));
    }
}
