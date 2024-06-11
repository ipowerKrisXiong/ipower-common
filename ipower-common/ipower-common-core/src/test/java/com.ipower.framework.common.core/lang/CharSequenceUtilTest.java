package com.ipower.framework.common.core.lang;

import com.ipower.framework.common.core.collection.Lists;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CharSequenceUtil Tester.
 *
 * @author diablo
 */
public class CharSequenceUtilTest {

    /**
     * Method: isEmpty(CharSequence param)
     */
    @Test
    public void testIsEmpty() {
        assertTrue(CharSequenceUtil.isEmpty(null));
        assertTrue(CharSequenceUtil.isEmpty(""));
        assertFalse(CharSequenceUtil.isEmpty(" "));
        assertFalse(CharSequenceUtil.isEmpty(" \t\n"));
        assertFalse(CharSequenceUtil.isEmpty("abc"));
        assertTrue(CharSequenceUtil.isEmpty(new StringBuilder()));
        assertFalse(CharSequenceUtil.isEmpty(new StringBuilder(" ")));
        assertTrue(CharSequenceUtil.isEmpty(new StringBuffer()));
        assertFalse(CharSequenceUtil.isEmpty(new StringBuffer("abc")));
    }

    /**
     * Method: isNotEmpty(CharSequence param)
     */
    @Test
    public void testIsNotEmpty() {
        assertFalse(CharSequenceUtil.isNotEmpty(null));
        assertFalse(CharSequenceUtil.isNotEmpty(""));
        assertTrue(CharSequenceUtil.isNotEmpty(" "));
        assertTrue(CharSequenceUtil.isNotEmpty(" \t\n"));
        assertTrue(CharSequenceUtil.isNotEmpty("abc"));
        assertFalse(CharSequenceUtil.isNotEmpty(new StringBuilder()));
        assertTrue(CharSequenceUtil.isNotEmpty(new StringBuilder(" ")));
        assertFalse(CharSequenceUtil.isNotEmpty(new StringBuffer()));
        assertTrue(CharSequenceUtil.isNotEmpty(new StringBuffer("abc")));
    }

    /**
     * Method: isAllEmpty(CharSequence... params)
     */
    @Test
    public void testIsAllEmpty() {
        assertTrue(CharSequenceUtil.isAllEmpty());
        //noinspection
        assertTrue(CharSequenceUtil.isAllEmpty((CharSequence) null));
        assertTrue(CharSequenceUtil.isAllEmpty("", null));
        assertFalse(CharSequenceUtil.isAllEmpty("123", ""));
        assertFalse(CharSequenceUtil.isAllEmpty("123", "abc"));
        assertFalse(CharSequenceUtil.isAllEmpty(" ", "\t", "\n", "\t\n"));
        assertTrue(CharSequenceUtil.isAllEmpty(new StringBuilder(), new StringBuffer()));
        assertFalse(CharSequenceUtil.isAllEmpty(new StringBuilder("123"), new StringBuffer()));
        assertFalse(CharSequenceUtil.isAllEmpty(new StringBuilder("123"), new StringBuffer("abc")));
    }

    /**
     * Method: isAllEmpty(Collection<? extends CharSequence> params)
     */
    @Test
    public void testIsAllEmptyCollection() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CharSequenceUtil.isAllEmpty(list1));
        assertTrue(CharSequenceUtil.isAllEmpty(list2));
        assertTrue(CharSequenceUtil.isAllEmpty(Lists.arrayList("", null)));
        assertFalse(CharSequenceUtil.isAllEmpty(Lists.arrayList("123", "")));
        assertFalse(CharSequenceUtil.isAllEmpty(Lists.arrayList("123", "abc")));
        assertFalse(CharSequenceUtil.isAllEmpty(Lists.arrayList(" ", "\t", "\n", "\t\n")));
        assertTrue(CharSequenceUtil.isAllEmpty(Lists.arrayList(new StringBuilder(), new StringBuilder())));
        assertFalse(CharSequenceUtil.isAllEmpty(Lists.arrayList(new StringBuffer("123"), new StringBuffer())));
        assertFalse(CharSequenceUtil.isAllEmpty(Lists.arrayList(new StringBuilder("123"), new StringBuilder("abc"))));
    }

    /**
     * Method: isAllEmpty(Iterable<? extends CharSequence> params)
     */
    @Test
    public void testIsAllEmptyIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.hashSet();
        //noinspection ConstantValue
        assertTrue(CharSequenceUtil.isAllEmpty(iterable1));
        assertTrue(CharSequenceUtil.isAllEmpty(iterable2));
        Iterable<String> iterable3 = Lists.hashSet("", null);
        assertTrue(CharSequenceUtil.isAllEmpty(iterable3));
        Iterable<String> iterable4 = Lists.hashSet("123", "");
        assertFalse(CharSequenceUtil.isAllEmpty(iterable4));
        Iterable<String> iterable5 = Lists.hashSet("123", "abc");
        assertFalse(CharSequenceUtil.isAllEmpty(iterable5));
        Iterable<String> iterable6 = Lists.hashSet(" ", "\t", "\n", "\t\n");
        assertFalse(CharSequenceUtil.isAllEmpty(iterable6));
        Iterable<StringBuilder> iterable7 = Lists.hashSet(new StringBuilder(), new StringBuilder());
        assertTrue(CharSequenceUtil.isAllEmpty(iterable7));
        Iterable<StringBuffer> iterable8 = Lists.hashSet(new StringBuffer("123"), new StringBuffer());
        assertFalse(CharSequenceUtil.isAllEmpty(iterable8));
        Iterable<StringBuilder> iterable9 = Lists.hashSet(new StringBuilder("123"), new StringBuilder("abc"));
        assertFalse(CharSequenceUtil.isAllEmpty(iterable9));
    }

    /**
     * Method: isAllEmpty(Iterable<? extends CharSequence> params)
     */
    @Test
    public void testIsAllEmptyIterator() {
        Iterator<String> iterator1 = null;
        List<String> list = Lists.arrayList();
        Iterator<String> iterator2 = list.iterator();
        //noinspection ConstantValue
        assertTrue(CharSequenceUtil.isAllEmpty(iterator1));
        assertTrue(CharSequenceUtil.isAllEmpty(iterator2));
        Iterable<String> iterable3 = Lists.hashSet("", null);
        assertTrue(CharSequenceUtil.isAllEmpty(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.hashSet("123", "");
        assertFalse(CharSequenceUtil.isAllEmpty(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.hashSet("123", "abc");
        assertFalse(CharSequenceUtil.isAllEmpty(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.hashSet(" ", "\t", "\n", "\t\n");
        assertFalse(CharSequenceUtil.isAllEmpty(iterable6.iterator()));
        Iterable<StringBuilder> iterable7 = Lists.hashSet(new StringBuilder(), new StringBuilder());
        assertTrue(CharSequenceUtil.isAllEmpty(iterable7.iterator()));
        Iterable<StringBuffer> iterable8 = Lists.hashSet(new StringBuffer("123"), new StringBuffer());
        assertFalse(CharSequenceUtil.isAllEmpty(iterable8.iterator()));
        Iterable<StringBuilder> iterable9 = Lists.hashSet(new StringBuilder("123"), new StringBuilder("abc"));
        assertFalse(CharSequenceUtil.isAllEmpty(iterable9.iterator()));
    }

    /**
     * Method: isAllNotEmpty(CharSequence... params)
     */
    @Test
    public void testIsAllNotEmpty() {
        assertFalse(CharSequenceUtil.isAllNotEmpty());
        //noinspection
        assertFalse(CharSequenceUtil.isAllNotEmpty((CharSequence) null));
        assertFalse(CharSequenceUtil.isAllNotEmpty("", null));
        assertFalse(CharSequenceUtil.isAllNotEmpty("123", ""));
        assertTrue(CharSequenceUtil.isAllNotEmpty("123", "abc"));
        assertTrue(CharSequenceUtil.isAllNotEmpty(" ", "\t", "\n", "\t\n"));
        assertFalse(CharSequenceUtil.isAllNotEmpty(new StringBuilder(), new StringBuffer()));
        assertFalse(CharSequenceUtil.isAllNotEmpty(new StringBuilder("123"), new StringBuffer()));
        assertTrue(CharSequenceUtil.isAllNotEmpty(new StringBuilder("123"), new StringBuffer("abc")));
    }

    /**
     * Method: isAllNotEmpty(Collection<CharSequence> params)
     */
    @Test
    public void testIsAllNotEmptyCollection() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertFalse(CharSequenceUtil.isAllNotEmpty(list1));
        assertFalse(CharSequenceUtil.isAllNotEmpty(list2));
        assertFalse(CharSequenceUtil.isAllNotEmpty(Lists.arrayList("", null)));
        assertFalse(CharSequenceUtil.isAllNotEmpty(Lists.arrayList("123", "")));
        assertTrue(CharSequenceUtil.isAllNotEmpty(Lists.arrayList("123", "abc")));
        assertTrue(CharSequenceUtil.isAllNotEmpty(Lists.arrayList(" ", "\t", "\n", "\t\n")));
        assertFalse(CharSequenceUtil.isAllNotEmpty(Lists.arrayList(new StringBuilder(), new StringBuilder())));
        assertFalse(CharSequenceUtil.isAllNotEmpty(Lists.arrayList(new StringBuffer("123"), new StringBuffer())));
        assertTrue(CharSequenceUtil.isAllNotEmpty(Lists.arrayList(new StringBuilder("123"), new StringBuilder("abc"))));
    }

    /**
     * Method: isAllNotEmpty(Iterable<CharSequence> params)
     */
    @Test
    public void testIsAllNotEmptyIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.hashSet();
        //noinspection ConstantValue
        assertFalse(CharSequenceUtil.isAllNotEmpty(iterable1));
        assertFalse(CharSequenceUtil.isAllNotEmpty(iterable2));
        Iterable<String> iterable3 = Lists.hashSet("", null);
        assertFalse(CharSequenceUtil.isAllNotEmpty(iterable3));
        Iterable<String> iterable4 = Lists.hashSet("123", "");
        assertFalse(CharSequenceUtil.isAllNotEmpty(iterable4));
        Iterable<String> iterable5 = Lists.hashSet("123", "abc");
        assertTrue(CharSequenceUtil.isAllNotEmpty(iterable5));
        Iterable<String> iterable6 = Lists.hashSet(" ", "\t", "\n", "\t\n");
        assertTrue(CharSequenceUtil.isAllNotEmpty(iterable6));
        Iterable<StringBuilder> iterable7 = Lists.hashSet(new StringBuilder(), new StringBuilder());
        assertFalse(CharSequenceUtil.isAllNotEmpty(iterable7));
        Iterable<StringBuffer> iterable8 = Lists.hashSet(new StringBuffer("123"), new StringBuffer());
        assertFalse(CharSequenceUtil.isAllNotEmpty(iterable8));
        Iterable<StringBuilder> iterable9 = Lists.hashSet(new StringBuilder("123"), new StringBuilder("abc"));
        assertTrue(CharSequenceUtil.isAllNotEmpty(iterable9));
    }

    /**
     * Method: isAllNotEmpty(Iterator<CharSequence> params)
     */
    @Test
    public void testIsAllNotEmptyIterator() {
        Iterator<String> iterator1 = null;
        List<String> list = Lists.arrayList();
        Iterator<String> iterator2 = list.iterator();
        //noinspection ConstantValue
        assertFalse(CharSequenceUtil.isAllNotEmpty(iterator1));
        assertFalse(CharSequenceUtil.isAllNotEmpty(iterator2));
        Iterable<String> iterable3 = Lists.hashSet("", null);
        assertFalse(CharSequenceUtil.isAllNotEmpty(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.hashSet("123", "");
        assertFalse(CharSequenceUtil.isAllNotEmpty(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.hashSet("123", "abc");
        assertTrue(CharSequenceUtil.isAllNotEmpty(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.hashSet(" ", "\t", "\n", "\t\n");
        assertTrue(CharSequenceUtil.isAllNotEmpty(iterable6.iterator()));
        Iterable<StringBuilder> iterable7 = Lists.hashSet(new StringBuilder(), new StringBuilder());
        assertFalse(CharSequenceUtil.isAllNotEmpty(iterable7.iterator()));
        Iterable<StringBuffer> iterable8 = Lists.hashSet(new StringBuffer("123"), new StringBuffer());
        assertFalse(CharSequenceUtil.isAllNotEmpty(iterable8.iterator()));
        Iterable<StringBuilder> iterable9 = Lists.hashSet(new StringBuilder("123"), new StringBuilder("abc"));
        assertTrue(CharSequenceUtil.isAllNotEmpty(iterable9.iterator()));
    }

    /**
     * Method: isBlank(CharSequence param)
     */
    @Test
    public void testIsBlank() {
        assertTrue(CharSequenceUtil.isBlank(null));
        assertTrue(CharSequenceUtil.isBlank(""));
        assertTrue(CharSequenceUtil.isBlank(" "));
        assertTrue(CharSequenceUtil.isBlank(" \t\n"));
        assertFalse(CharSequenceUtil.isBlank("abc"));
        assertTrue(CharSequenceUtil.isBlank(new StringBuilder()));
        assertTrue(CharSequenceUtil.isBlank(new StringBuilder(" ")));
        assertTrue(CharSequenceUtil.isBlank(new StringBuffer()));
        assertFalse(CharSequenceUtil.isBlank(new StringBuffer("abc")));
    }

    /**
     * Method: isNotBlank(CharSequence param)
     */
    @Test
    public void testIsNotBlank() {
        assertFalse(CharSequenceUtil.isNotBlank(null));
        assertFalse(CharSequenceUtil.isNotBlank(""));
        assertFalse(CharSequenceUtil.isNotBlank(" "));
        assertFalse(CharSequenceUtil.isNotBlank(" \t\n"));
        assertTrue(CharSequenceUtil.isNotBlank("abc"));
        assertFalse(CharSequenceUtil.isNotBlank(new StringBuilder()));
        assertFalse(CharSequenceUtil.isNotBlank(new StringBuilder(" ")));
        assertFalse(CharSequenceUtil.isNotBlank(new StringBuffer()));
        assertTrue(CharSequenceUtil.isNotBlank(new StringBuffer("abc")));
    }

    /**
     * Method: isAllBlank(CharSequence... params)
     */
    @Test
    public void testIsAllBlank() {
        assertTrue(CharSequenceUtil.isAllBlank());
        //noinspection
        assertTrue(CharSequenceUtil.isAllBlank((CharSequence) null));
        assertTrue(CharSequenceUtil.isAllBlank("", null, " "));
        assertFalse(CharSequenceUtil.isAllBlank("123", ""));
        assertFalse(CharSequenceUtil.isAllBlank("123", "abc"));
        assertTrue(CharSequenceUtil.isAllBlank(" ", "\t", "\n", "\t\n"));
        assertTrue(CharSequenceUtil.isAllBlank(new StringBuilder(), new StringBuffer()));
        assertFalse(CharSequenceUtil.isAllBlank(new StringBuilder("123"), new StringBuffer()));
        assertFalse(CharSequenceUtil.isAllBlank(new StringBuilder("123"), new StringBuffer("abc")));
    }

    /**
     * Method: isAllBlank(Collection<CharSequence> params)
     */
    @Test
    public void testIsAllBlankCollection() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CharSequenceUtil.isAllBlank(list1));
        assertTrue(CharSequenceUtil.isAllBlank(list2));
        assertTrue(CharSequenceUtil.isAllBlank(Lists.arrayList("", null, " ")));
        assertFalse(CharSequenceUtil.isAllBlank(Lists.arrayList("123", "")));
        assertFalse(CharSequenceUtil.isAllBlank(Lists.arrayList("123", "abc")));
        assertTrue(CharSequenceUtil.isAllBlank(Lists.arrayList(" ", "\t", "\n", "\t\n")));
        assertTrue(CharSequenceUtil.isAllBlank(Lists.arrayList(new StringBuilder(), new StringBuilder())));
        assertFalse(CharSequenceUtil.isAllBlank(Lists.arrayList(new StringBuffer("123"), new StringBuffer())));
        assertFalse(CharSequenceUtil.isAllBlank(Lists.arrayList(new StringBuilder("123"), new StringBuilder("abc"))));
    }

    /**
     * Method: isAllBlank(Iterable<CharSequence> params)
     */
    @Test
    public void testIsAllBlankIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.hashSet();
        //noinspection ConstantValue
        assertTrue(CharSequenceUtil.isAllBlank(iterable1));
        assertTrue(CharSequenceUtil.isAllBlank(iterable2));
        Iterable<String> iterable3 = Lists.hashSet("", null, " ");
        assertTrue(CharSequenceUtil.isAllBlank(iterable3));
        Iterable<String> iterable4 = Lists.hashSet("123", "");
        assertFalse(CharSequenceUtil.isAllBlank(iterable4));
        Iterable<String> iterable5 = Lists.hashSet("123", "abc");
        assertFalse(CharSequenceUtil.isAllBlank(iterable5));
        Iterable<String> iterable6 = Lists.hashSet(" ", "\t", "\n", "\t\n");
        assertTrue(CharSequenceUtil.isAllBlank(iterable6));
        Iterable<StringBuilder> iterable7 = Lists.hashSet(new StringBuilder(), new StringBuilder());
        assertTrue(CharSequenceUtil.isAllBlank(iterable7));
        Iterable<StringBuffer> iterable8 = Lists.hashSet(new StringBuffer("123"), new StringBuffer());
        assertFalse(CharSequenceUtil.isAllBlank(iterable8));
        Iterable<StringBuilder> iterable9 = Lists.hashSet(new StringBuilder("123"), new StringBuilder("abc"));
        assertFalse(CharSequenceUtil.isAllBlank(iterable9));
    }

    /**
     * Method: isAllBlank(Iterator<CharSequence> params)
     */
    @Test
    public void testIsAllBlankIterator() {
        Iterator<String> iterator1 = null;
        List<String> list = Lists.arrayList();
        Iterator<String> iterator2 = list.iterator();
        //noinspection ConstantValue
        assertTrue(CharSequenceUtil.isAllBlank(iterator1));
        assertTrue(CharSequenceUtil.isAllBlank(iterator2));
        Iterable<String> iterable3 = Lists.hashSet("", null, " ");
        assertTrue(CharSequenceUtil.isAllBlank(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.hashSet("123", "");
        assertFalse(CharSequenceUtil.isAllBlank(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.hashSet("123", "abc");
        assertFalse(CharSequenceUtil.isAllBlank(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.hashSet(" ", "\t", "\n", "\t\n");
        assertTrue(CharSequenceUtil.isAllBlank(iterable6.iterator()));
        Iterable<StringBuilder> iterable7 = Lists.hashSet(new StringBuilder(), new StringBuilder());
        assertTrue(CharSequenceUtil.isAllBlank(iterable7.iterator()));
        Iterable<StringBuffer> iterable8 = Lists.hashSet(new StringBuffer("123"), new StringBuffer());
        assertFalse(CharSequenceUtil.isAllBlank(iterable8.iterator()));
        Iterable<StringBuilder> iterable9 = Lists.hashSet(new StringBuilder("123"), new StringBuilder("abc"));
        assertFalse(CharSequenceUtil.isAllBlank(iterable9.iterator()));
    }

    /**
     * Method: isAllNotBlank(CharSequence... params)
     */
    @Test
    public void testIsAllNotBlank() {
        assertFalse(CharSequenceUtil.isAllNotBlank());
        //noinspection
        assertFalse(CharSequenceUtil.isAllNotBlank((CharSequence) null));
        assertFalse(CharSequenceUtil.isAllNotBlank("", null, " "));
        assertFalse(CharSequenceUtil.isAllNotBlank("123", ""));
        assertTrue(CharSequenceUtil.isAllNotBlank("123", "abc"));
        assertFalse(CharSequenceUtil.isAllNotBlank(" ", "\t", "\n", "\t\n"));
        assertFalse(CharSequenceUtil.isAllNotBlank(new StringBuilder(), new StringBuffer()));
        assertFalse(CharSequenceUtil.isAllNotBlank(new StringBuilder("123"), new StringBuffer()));
        assertTrue(CharSequenceUtil.isAllNotBlank(new StringBuilder("123"), new StringBuffer("abc")));
    }

    /**
     * Method: isAllNotBlank(Collection<CharSequence> params)
     */
    @Test
    public void testIsAllNotBlankCollection() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertFalse(CharSequenceUtil.isAllNotBlank(list1));
        assertFalse(CharSequenceUtil.isAllNotBlank(list2));
        assertFalse(CharSequenceUtil.isAllNotBlank(Lists.arrayList("", null, " ")));
        assertFalse(CharSequenceUtil.isAllNotBlank(Lists.arrayList("123", "")));
        assertTrue(CharSequenceUtil.isAllNotBlank(Lists.arrayList("123", "abc")));
        assertFalse(CharSequenceUtil.isAllNotBlank(Lists.arrayList(" ", "\t", "\n", "\t\n")));
        assertFalse(CharSequenceUtil.isAllNotBlank(Lists.arrayList(new StringBuilder(), new StringBuilder())));
        assertFalse(CharSequenceUtil.isAllNotBlank(Lists.arrayList(new StringBuffer("123"), new StringBuffer())));
        assertTrue(CharSequenceUtil.isAllNotBlank(Lists.arrayList(new StringBuilder("123"), new StringBuilder("abc"))));
    }

    /**
     * Method: isAllNotBlank(Iterable<CharSequence> params)
     */
    @Test
    public void testIsAllNotBlankIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.hashSet();
        //noinspection ConstantValue
        assertFalse(CharSequenceUtil.isAllNotBlank(iterable1));
        assertFalse(CharSequenceUtil.isAllNotBlank(iterable2));
        Iterable<String> iterable3 = Lists.hashSet("", null, " ");
        assertFalse(CharSequenceUtil.isAllNotBlank(iterable3));
        Iterable<String> iterable4 = Lists.hashSet("123", "");
        assertFalse(CharSequenceUtil.isAllNotBlank(iterable4));
        Iterable<String> iterable5 = Lists.hashSet("123", "abc");
        assertTrue(CharSequenceUtil.isAllNotBlank(iterable5));
        Iterable<String> iterable6 = Lists.hashSet(" ", "\t", "\n", "\t\n");
        assertFalse(CharSequenceUtil.isAllNotBlank(iterable6));
        Iterable<StringBuilder> iterable7 = Lists.hashSet(new StringBuilder(), new StringBuilder());
        assertFalse(CharSequenceUtil.isAllNotBlank(iterable7));
        Iterable<StringBuffer> iterable8 = Lists.hashSet(new StringBuffer("123"), new StringBuffer());
        assertFalse(CharSequenceUtil.isAllNotBlank(iterable8));
        Iterable<StringBuilder> iterable9 = Lists.hashSet(new StringBuilder("123"), new StringBuilder("abc"));
        assertTrue(CharSequenceUtil.isAllNotBlank(iterable9));
    }

    /**
     * Method: isAllNotBlank(Iterator<CharSequence> params)
     */
    @Test
    public void testIsAllNotBlankIterator() {
        Iterator<String> iterator1 = null;
        List<String> list = Lists.arrayList();
        Iterator<String> iterator2 = list.iterator();
        //noinspection ConstantValue
        assertFalse(CharSequenceUtil.isAllNotBlank(iterator1));
        assertFalse(CharSequenceUtil.isAllNotBlank(iterator2));
        Iterable<String> iterable3 = Lists.hashSet("", null, " ");
        assertFalse(CharSequenceUtil.isAllNotBlank(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.hashSet("123", "");
        assertFalse(CharSequenceUtil.isAllNotBlank(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.hashSet("123", "abc");
        assertTrue(CharSequenceUtil.isAllNotBlank(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.hashSet(" ", "\t", "\n", "\t\n");
        assertFalse(CharSequenceUtil.isAllNotBlank(iterable6.iterator()));
        Iterable<StringBuilder> iterable7 = Lists.hashSet(new StringBuilder(), new StringBuilder());
        assertFalse(CharSequenceUtil.isAllNotBlank(iterable7.iterator()));
        Iterable<StringBuffer> iterable8 = Lists.hashSet(new StringBuffer("123"), new StringBuffer());
        assertFalse(CharSequenceUtil.isAllNotBlank(iterable8.iterator()));
        Iterable<StringBuilder> iterable9 = Lists.hashSet(new StringBuilder("123"), new StringBuilder("abc"));
        assertTrue(CharSequenceUtil.isAllNotBlank(iterable9.iterator()));
    }

    /**
     * Method: hasEmpty(CharSequence... params)
     */
    @Test
    public void testHasEmpty() {
        assertTrue(CharSequenceUtil.hasEmpty());
        //noinspection
        assertTrue(CharSequenceUtil.hasEmpty((CharSequence) null));
        assertTrue(CharSequenceUtil.hasEmpty("", null));
        assertTrue(CharSequenceUtil.hasEmpty("123", ""));
        assertFalse(CharSequenceUtil.hasEmpty("123", "abc"));
        assertFalse(CharSequenceUtil.hasEmpty(" ", "\t", "\n"));
        assertTrue(CharSequenceUtil.hasEmpty(new StringBuilder(), new StringBuffer()));
        assertTrue(CharSequenceUtil.hasEmpty(new StringBuilder("123"), new StringBuffer()));
        assertFalse(CharSequenceUtil.hasEmpty(new StringBuilder("123"), new StringBuffer("abc")));
    }

    /**
     * Method: hasEmpty(Collection<CharSequence> params)
     */
    @Test
    public void testHasEmptyCollection() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CharSequenceUtil.hasEmpty(list1));
        assertTrue(CharSequenceUtil.hasEmpty(list2));
        assertTrue(CharSequenceUtil.hasEmpty(Lists.arrayList("", null)));
        assertTrue(CharSequenceUtil.hasEmpty(Lists.arrayList("123", "")));
        assertFalse(CharSequenceUtil.hasEmpty(Lists.arrayList("123", "abc")));
        assertFalse(CharSequenceUtil.hasEmpty(Lists.arrayList(" ", "\t", "\n")));
        assertTrue(CharSequenceUtil.hasEmpty(Lists.arrayList(new StringBuilder(), new StringBuilder())));
        assertTrue(CharSequenceUtil.hasEmpty(Lists.arrayList(new StringBuffer("123"), new StringBuffer())));
        assertFalse(CharSequenceUtil.hasEmpty(Lists.arrayList(new StringBuilder("123"), new StringBuilder("abc"))));
    }

    /**
     * Method: isAllEmpty(Iterable<? extends CharSequence> params)
     */
    @Test
    public void testHasEmptyIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.hashSet();
        //noinspection ConstantValue
        assertTrue(CharSequenceUtil.hasEmpty(iterable1));
        assertTrue(CharSequenceUtil.hasEmpty(iterable2));
        Iterable<String> iterable3 = Lists.hashSet("", null);
        assertTrue(CharSequenceUtil.hasEmpty(iterable3));
        Iterable<String> iterable4 = Lists.hashSet("123", "");
        assertTrue(CharSequenceUtil.hasEmpty(iterable4));
        Iterable<String> iterable5 = Lists.hashSet("123", "abc");
        assertFalse(CharSequenceUtil.hasEmpty(iterable5));
        Iterable<String> iterable6 = Lists.hashSet(" ", "\t", "\n", "\t\n");
        assertFalse(CharSequenceUtil.hasEmpty(iterable6));
        Iterable<StringBuilder> iterable7 = Lists.hashSet(new StringBuilder(), new StringBuilder());
        assertTrue(CharSequenceUtil.hasEmpty(iterable7));
        Iterable<StringBuffer> iterable8 = Lists.hashSet(new StringBuffer("123"), new StringBuffer());
        assertTrue(CharSequenceUtil.hasEmpty(iterable8));
        Iterable<StringBuilder> iterable9 = Lists.hashSet(new StringBuilder("123"), new StringBuilder("abc"));
        assertFalse(CharSequenceUtil.hasEmpty(iterable9));
    }

    /**
     * Method: isAllEmpty(Iterable<? extends CharSequence> params)
     */
    @Test
    public void testHasEmptyIterator() {
        Iterator<String> iterator1 = null;
        List<String> list = Lists.arrayList();
        Iterator<String> iterator2 = list.iterator();
        //noinspection ConstantValue
        assertTrue(CharSequenceUtil.hasEmpty(iterator1));
        assertTrue(CharSequenceUtil.hasEmpty(iterator2));
        Iterable<String> iterable3 = Lists.hashSet("", null);
        assertTrue(CharSequenceUtil.hasEmpty(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.hashSet("123", "");
        assertTrue(CharSequenceUtil.hasEmpty(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.hashSet("123", "abc");
        assertFalse(CharSequenceUtil.hasEmpty(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.hashSet(" ", "\t", "\n", "\t\n");
        assertFalse(CharSequenceUtil.hasEmpty(iterable6.iterator()));
        Iterable<StringBuilder> iterable7 = Lists.hashSet(new StringBuilder(), new StringBuilder());
        assertTrue(CharSequenceUtil.hasEmpty(iterable7.iterator()));
        Iterable<StringBuffer> iterable8 = Lists.hashSet(new StringBuffer("123"), new StringBuffer());
        assertTrue(CharSequenceUtil.hasEmpty(iterable8.iterator()));
        Iterable<StringBuilder> iterable9 = Lists.hashSet(new StringBuilder("123"), new StringBuilder("abc"));
        assertFalse(CharSequenceUtil.hasEmpty(iterable9.iterator()));
    }

    /**
     * Method: hasBlank(CharSequence... params)
     */
    @Test
    public void testHasBlank() {
        assertTrue(CharSequenceUtil.hasBlank());
        //noinspection
        assertTrue(CharSequenceUtil.hasBlank((CharSequence) null));
        assertTrue(CharSequenceUtil.hasBlank("", null, " "));
        assertTrue(CharSequenceUtil.hasBlank("123", ""));
        assertFalse(CharSequenceUtil.hasBlank("123", "abc"));
        assertTrue(CharSequenceUtil.hasBlank(" ", "\t", "\n", "\t\n"));
        assertTrue(CharSequenceUtil.hasBlank(new StringBuilder(), new StringBuffer()));
        assertTrue(CharSequenceUtil.hasBlank(new StringBuilder("123"), new StringBuffer()));
        assertFalse(CharSequenceUtil.hasBlank(new StringBuilder("123"), new StringBuffer("abc")));
    }

    /**
     * Method: hasBlank(Collection<CharSequence> params)
     */
    @Test
    public void testHasBlankCollection() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CharSequenceUtil.hasBlank(list1));
        assertTrue(CharSequenceUtil.hasBlank(list2));
        assertTrue(CharSequenceUtil.hasBlank(Lists.arrayList("", null, " ")));
        assertTrue(CharSequenceUtil.hasBlank(Lists.arrayList("123", "")));
        assertFalse(CharSequenceUtil.hasBlank(Lists.arrayList("123", "abc")));
        assertTrue(CharSequenceUtil.hasBlank(Lists.arrayList(" ", "\t", "\n", "\t\n")));
        assertTrue(CharSequenceUtil.hasBlank(Lists.arrayList(new StringBuilder(), new StringBuilder())));
        assertTrue(CharSequenceUtil.hasBlank(Lists.arrayList(new StringBuffer("123"), new StringBuffer())));
        assertFalse(CharSequenceUtil.hasBlank(Lists.arrayList(new StringBuilder("123"), new StringBuilder("abc"))));
    }

    /**
     * Method: hasBlank(Iterable<CharSequence> params)
     */
    @Test
    public void testHasBlankIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.hashSet();
        //noinspection ConstantValue
        assertTrue(CharSequenceUtil.hasBlank(iterable1));
        assertTrue(CharSequenceUtil.hasBlank(iterable2));
        Iterable<String> iterable3 = Lists.hashSet("", null, " ");
        assertTrue(CharSequenceUtil.hasBlank(iterable3));
        Iterable<String> iterable4 = Lists.hashSet("123", "");
        assertTrue(CharSequenceUtil.hasBlank(iterable4));
        Iterable<String> iterable5 = Lists.hashSet("123", "abc");
        assertFalse(CharSequenceUtil.hasBlank(iterable5));
        Iterable<String> iterable6 = Lists.hashSet(" ", "\t", "\n", "\t\n");
        assertTrue(CharSequenceUtil.hasBlank(iterable6));
        Iterable<StringBuilder> iterable7 = Lists.hashSet(new StringBuilder(), new StringBuilder());
        assertTrue(CharSequenceUtil.hasBlank(iterable7));
        Iterable<StringBuffer> iterable8 = Lists.hashSet(new StringBuffer("123"), new StringBuffer());
        assertTrue(CharSequenceUtil.hasBlank(iterable8));
        Iterable<StringBuilder> iterable9 = Lists.hashSet(new StringBuilder("123"), new StringBuilder("abc"));
        assertFalse(CharSequenceUtil.hasBlank(iterable9));
    }

    /**
     * Method: hasBlank(Iterator<CharSequence> params)
     */
    @Test
    public void testHasBlankIterator() {
        Iterator<String> iterator1 = null;
        List<String> list = Lists.arrayList();
        Iterator<String> iterator2 = list.iterator();
        //noinspection ConstantValue
        assertTrue(CharSequenceUtil.hasBlank(iterator1));
        assertTrue(CharSequenceUtil.hasBlank(iterator2));
        Iterable<String> iterable3 = Lists.hashSet("", null, " ");
        assertTrue(CharSequenceUtil.hasBlank(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.hashSet("123", "");
        assertTrue(CharSequenceUtil.hasBlank(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.hashSet("123", "abc");
        assertFalse(CharSequenceUtil.hasBlank(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.hashSet(" ", "\t", "\n", "\t\n");
        assertTrue(CharSequenceUtil.hasBlank(iterable6.iterator()));
        Iterable<StringBuilder> iterable7 = Lists.hashSet(new StringBuilder(), new StringBuilder());
        assertTrue(CharSequenceUtil.hasBlank(iterable7.iterator()));
        Iterable<StringBuffer> iterable8 = Lists.hashSet(new StringBuffer("123"), new StringBuffer());
        assertTrue(CharSequenceUtil.hasBlank(iterable8.iterator()));
        Iterable<StringBuilder> iterable9 = Lists.hashSet(new StringBuilder("123"), new StringBuilder("abc"));
        assertFalse(CharSequenceUtil.hasBlank(iterable9.iterator()));
    }

    /**
     * Method: length(final CharSequence param)
     */
    @Test
    public void testLength() {
        assertEquals(0, CharSequenceUtil.length(null));
        assertEquals(0, CharSequenceUtil.length(""));
        assertEquals(7, CharSequenceUtil.length("alibaba"));
        assertEquals(7, CharSequenceUtil.length(new StringBuffer("alibaba")));
        assertEquals(7, CharSequenceUtil.length(new StringBuilder("alibaba")));
    }

    /**
     * Method: count(final CharSequence param, final char search)
     */
    @Test
    public void testCount() {
        assertEquals(0, CharSequenceUtil.count(null, '*'));
        assertEquals(0, CharSequenceUtil.count("", '*'));
        assertEquals(0, CharSequenceUtil.count("alibaba", '\n'));
        assertEquals(3, CharSequenceUtil.count("alibaba", 'a'));
        assertEquals(3, CharSequenceUtil.count("alibaba", (char) 97));
        assertEquals(2, CharSequenceUtil.count("alibaba", 'b'));
        assertEquals(2, CharSequenceUtil.count("alibaba", (char) 98));
        assertEquals(0, CharSequenceUtil.count("alibaba", 'A'));
        assertEquals(0, CharSequenceUtil.count("alibaba", (char) 65));
    }

    /**
     * Method: startWith(final CharSequence param, final char prefix)
     */
    @Test
    public void testStartWith() {
        assertFalse(CharSequenceUtil.startWith("", 'a'));
        assertTrue(CharSequenceUtil.startWith("alibaba", 'a'));
        assertTrue(CharSequenceUtil.startWith("alibaba", (char) 97));
        assertFalse(CharSequenceUtil.startWith("alibaba", 'b'));
        assertFalse(CharSequenceUtil.startWith("alibaba", (char) 98));
        assertFalse(CharSequenceUtil.startWith("alibaba", 'A'));
        assertFalse(CharSequenceUtil.startWith("alibaba", (char) 65));
    }

    /**
     * Method: endWith(final CharSequence param, final char prefix)
     */
    @Test
    public void testEndWith() {
        assertFalse(CharSequenceUtil.endWith("", 'a'));
        assertTrue(CharSequenceUtil.endWith("alibaba", 'a'));
        assertTrue(CharSequenceUtil.endWith("alibaba", (char) 97));
        assertFalse(CharSequenceUtil.endWith("alibaba", 'b'));
        assertFalse(CharSequenceUtil.endWith("alibaba", (char) 98));
        assertFalse(CharSequenceUtil.endWith("alibaba", 'A'));
        assertFalse(CharSequenceUtil.endWith("alibaba", (char) 65));
    }

    /**
     * Method: contains(final CharSequence param, final char search)
     */
    @Test
    public void testContainsChar() {
        assertFalse(CharSequenceUtil.contains(null, 'a'));
        assertFalse(CharSequenceUtil.contains("", 'a'));
        assertTrue(CharSequenceUtil.contains("alibaba", 'a'));
        assertTrue(CharSequenceUtil.contains("alibaba", (char) 97));
        assertTrue(CharSequenceUtil.contains("alibaba", 'b'));
        assertTrue(CharSequenceUtil.contains("alibaba", (char) 98));
        assertFalse(CharSequenceUtil.contains("alibaba", 'c'));
        assertFalse(CharSequenceUtil.contains("alibaba", (char) 99));
        assertFalse(CharSequenceUtil.contains("alibaba", 'A'));
        assertFalse(CharSequenceUtil.contains("alibaba", (char) 65));
    }

    /**
     * Method: containsAny(final CharSequence param, final char... searches)
     */
    @Test
    public void testContainsAnyChar() {
        assertFalse(CharSequenceUtil.containsAny(null, (char[]) null));
        assertFalse(CharSequenceUtil.containsAny("", (char[]) null));
        assertFalse(CharSequenceUtil.containsAny(null, 'a'));
        assertFalse(CharSequenceUtil.containsAny("alibaba", (char[]) null));
        assertFalse(CharSequenceUtil.containsAny("alibaba", 'c', 'd', 'e'));
        assertTrue(CharSequenceUtil.containsAny("alibaba", 'a', 'b', 'c'));
        assertTrue(CharSequenceUtil.containsAny("alibaba", (char) 97, (char) 98, (char) 99));
        assertFalse(CharSequenceUtil.containsAny("alibaba", 'A', 'B', 'C'));
        assertFalse(CharSequenceUtil.containsAny("alibaba", (char) 65, (char) 66, (char) 67));
    }

    /**
     * Method: containsAll(final CharSequence param, final char... searches)
     */
    @Test
    public void testContainsAllChar() {
        assertFalse(CharSequenceUtil.containsAll(null, (char[]) null));
        assertFalse(CharSequenceUtil.containsAll("", (char[]) null));
        assertFalse(CharSequenceUtil.containsAll(null, 'a'));
        assertFalse(CharSequenceUtil.containsAll("alibaba", 'c', 'd', 'e'));
        assertTrue(CharSequenceUtil.containsAll("alibaba", 'a', 'b', 'l'));
        assertTrue(CharSequenceUtil.containsAll("alibaba", (char) 97, (char) 98, (char) 108));
        assertFalse(CharSequenceUtil.containsAll("alibaba", 'A', 'B', 'C'));
        assertFalse(CharSequenceUtil.containsAll("alibaba", (char) 65, (char) 66, (char) 67));
    }

    /**
     * Method: containsBlank(final CharSequence param)
     */
    @Test
    public void testContainsBlank() {
        assertFalse(CharSequenceUtil.containsBlank(null));
        assertFalse(CharSequenceUtil.containsBlank(""));
        assertTrue(CharSequenceUtil.containsBlank(" "));
        assertFalse(CharSequenceUtil.containsBlank("alibaba"));
        assertTrue(CharSequenceUtil.containsBlank("alibaba "));
        assertTrue(CharSequenceUtil.containsBlank("alibaba\n\t"));
        assertTrue(CharSequenceUtil.containsBlank("  alibaba"));
        assertTrue(CharSequenceUtil.containsBlank("ali ba  ba"));
        assertFalse(CharSequenceUtil.containsBlank(new StringBuffer("alibaba")));
        assertTrue(CharSequenceUtil.containsBlank(new StringBuffer("alibaba ")));
        assertTrue(CharSequenceUtil.containsBlank(new StringBuilder("alibaba\n\t")));
        assertTrue(CharSequenceUtil.containsBlank(new StringBuilder("ali ba  ba")));
    }

    /**
     * Method: indexOf(final CharSequence param, final char search)
     */
    @Test
    public void testIndexOf() {
        assertEquals(-1, CharSequenceUtil.indexOf(null, 'a'));
        assertEquals(-1, CharSequenceUtil.indexOf("", 'a'));
        assertEquals(0, CharSequenceUtil.indexOf("alibaba", 'a'));
        assertEquals(3, CharSequenceUtil.indexOf("alibaba", 'b'));
        assertEquals(-1, CharSequenceUtil.indexOf("alibaba", 'c'));
        assertEquals(0, CharSequenceUtil.indexOf("alibaba", (char) 97));
        assertEquals(3, CharSequenceUtil.indexOf("alibaba", (char) 98));
        assertEquals(-1, CharSequenceUtil.indexOf("alibaba", (char) 99));
        assertEquals(-1, CharSequenceUtil.indexOf("alibaba", 'A'));
        assertEquals(-1, CharSequenceUtil.indexOf("alibaba", 'B'));
        assertEquals(-1, CharSequenceUtil.indexOf("alibaba", (char) 65));
        assertEquals(-1, CharSequenceUtil.indexOf("alibaba", (char) 66));
        assertEquals(0, CharSequenceUtil.indexOf(new StringBuffer("alibaba"), 'a'));
        assertEquals(3, CharSequenceUtil.indexOf(new StringBuilder("alibaba\n\t"), 'b'));
    }

    /**
     * Method: lastIndexOf(final CharSequence param, final char search)
     */
    @Test
    public void testLastIndexOf() {
        assertEquals(-1, CharSequenceUtil.lastIndexOf(null, 'a'));
        assertEquals(-1, CharSequenceUtil.lastIndexOf("", 'a'));
        assertEquals(6, CharSequenceUtil.lastIndexOf("alibaba", 'a'));
        assertEquals(5, CharSequenceUtil.lastIndexOf("alibaba", 'b'));
        assertEquals(-1, CharSequenceUtil.lastIndexOf("alibaba", 'c'));
        assertEquals(6, CharSequenceUtil.lastIndexOf("alibaba", (char) 97));
        assertEquals(5, CharSequenceUtil.lastIndexOf("alibaba", (char) 98));
        assertEquals(-1, CharSequenceUtil.lastIndexOf("alibaba", (char) 99));
        assertEquals(-1, CharSequenceUtil.lastIndexOf("alibaba", 'A'));
        assertEquals(-1, CharSequenceUtil.lastIndexOf("alibaba", 'B'));
        assertEquals(-1, CharSequenceUtil.lastIndexOf("alibaba", (char) 65));
        assertEquals(-1, CharSequenceUtil.lastIndexOf("alibaba", (char) 66));
        assertEquals(6, CharSequenceUtil.lastIndexOf(new StringBuffer("alibaba"), 'a'));
        assertEquals(5, CharSequenceUtil.lastIndexOf(new StringBuilder("alibaba\n\t"), 'b'));
    }

    /**
     * Method: remove(final CharSequence param, final char search)
     */
    @Test
    public void testRemove() {
        assertEquals("", CharSequenceUtil.remove(null, 'a'));
        assertEquals("", CharSequenceUtil.remove("", 'a'));
        //noinspection SpellCheckingInspection
        assertEquals("libb", CharSequenceUtil.remove("alibaba", 'a'));
        //noinspection SpellCheckingInspection
        assertEquals("aliaa", CharSequenceUtil.remove("alibaba", 'b'));
        assertEquals("alibaba", CharSequenceUtil.remove("alibaba", 'c'));
        //noinspection SpellCheckingInspection
        assertEquals("libb", CharSequenceUtil.remove("alibaba", (char) 97));
        //noinspection SpellCheckingInspection
        assertEquals("aliaa", CharSequenceUtil.remove("alibaba", (char) 98));
        assertEquals("alibaba", CharSequenceUtil.remove("alibaba", (char) 99));
        assertEquals("alibaba", CharSequenceUtil.remove("alibaba", 'A'));
        assertEquals("alibaba", CharSequenceUtil.remove("alibaba", 'B'));
        assertEquals("alibaba", CharSequenceUtil.remove("alibaba", (char) 65));
        assertEquals("alibaba", CharSequenceUtil.remove("alibaba", (char) 66));
        //noinspection SpellCheckingInspection
        assertEquals("libb", CharSequenceUtil.remove(new StringBuffer("alibaba"), 'a'));
        //noinspection SpellCheckingInspection
        assertEquals("aliaa\n\t", CharSequenceUtil.remove(new StringBuilder("alibaba\n\t"), 'b'));

        assertEquals("", CharSequenceUtil.remove(null, 'a', 'b'));
        assertEquals("", CharSequenceUtil.remove("", 'a', 'b'));
        assertEquals("alibaba", CharSequenceUtil.remove("alibaba"));
        assertEquals("li", CharSequenceUtil.remove("alibaba", 'a', 'b'));
        //noinspection SpellCheckingInspection
        assertEquals("libb", CharSequenceUtil.remove("alibaba", 'a', 'c'));
        assertEquals("li", CharSequenceUtil.remove("alibaba", 'a', 'b', 'c'));
        assertEquals("li", CharSequenceUtil.remove("alibaba", (char) 97, (char) 98));
        //noinspection SpellCheckingInspection
        assertEquals("libb", CharSequenceUtil.remove("alibaba", (char) 97, (char) 99));
        assertEquals("li", CharSequenceUtil.remove("alibaba", (char) 97, (char) 98, (char) 99));
        assertEquals("li", CharSequenceUtil.remove(new StringBuffer("alibaba"), 'a', 'b'));
        //noinspection SpellCheckingInspection
        assertEquals("libb\n\t", CharSequenceUtil.remove(new StringBuilder("alibaba\n\t"), 'a', 'c'));
    }

    /**
     * Method: removeFirst(final CharSequence param, final char search)
     */
    @Test
    public void testRemoveFirst() {
        assertEquals("", CharSequenceUtil.removeFirst(null));
        assertEquals("", CharSequenceUtil.removeFirst(""));
        //noinspection SpellCheckingInspection
        assertEquals("libaba", CharSequenceUtil.removeFirst("alibaba"));
        //noinspection SpellCheckingInspection
        assertEquals("libaba", CharSequenceUtil.removeFirst(new StringBuffer("alibaba")));
        //noinspection SpellCheckingInspection
        assertEquals("libaba\n\t", CharSequenceUtil.removeFirst(new StringBuilder("alibaba\n\t")));

        assertEquals("", CharSequenceUtil.removeFirst(null, 'a'));
        assertEquals("", CharSequenceUtil.removeFirst("", 'a'));
        //noinspection SpellCheckingInspection
        assertEquals("libaba", CharSequenceUtil.removeFirst("alibaba", 'a'));
        //noinspection SpellCheckingInspection
        assertEquals("aliaba", CharSequenceUtil.removeFirst("alibaba", 'b'));
        assertEquals("alibaba", CharSequenceUtil.removeFirst("alibaba", 'c'));
        //noinspection SpellCheckingInspection
        assertEquals("libaba", CharSequenceUtil.removeFirst("alibaba", (char) 97));
        //noinspection SpellCheckingInspection
        assertEquals("aliaba", CharSequenceUtil.removeFirst("alibaba", (char) 98));
        assertEquals("alibaba", CharSequenceUtil.removeFirst("alibaba", (char) 99));

        //noinspection SpellCheckingInspection
        assertEquals("libaba", CharSequenceUtil.removeFirst(new StringBuffer("alibaba"), 'a'));
        //noinspection SpellCheckingInspection
        assertEquals("aliaba\n\t", CharSequenceUtil.removeFirst(new StringBuilder("alibaba\n\t"), 'b'));
    }

    /**
     * Method: removeLast(final CharSequence param, final char search)
     */
    @Test
    public void testRemoveLast() {
        assertEquals("", CharSequenceUtil.removeLast(null));
        assertEquals("", CharSequenceUtil.removeLast(""));
        //noinspection SpellCheckingInspection
        assertEquals("alibab", CharSequenceUtil.removeLast("alibaba"));
        //noinspection SpellCheckingInspection
        assertEquals("alibab", CharSequenceUtil.removeLast(new StringBuffer("alibaba")));
        assertEquals("alibaba\n", CharSequenceUtil.removeLast(new StringBuilder("alibaba\n\t")));

        assertEquals("", CharSequenceUtil.removeLast(null, 'a'));
        assertEquals("", CharSequenceUtil.removeLast("", 'a'));
        //noinspection SpellCheckingInspection
        assertEquals("alibab", CharSequenceUtil.removeLast("alibaba", 'a'));
        //noinspection SpellCheckingInspection
        assertEquals("alibaa", CharSequenceUtil.removeLast("alibaba", 'b'));
        assertEquals("alibaba", CharSequenceUtil.removeLast("alibaba", 'c'));
        //noinspection SpellCheckingInspection
        assertEquals("alibab", CharSequenceUtil.removeLast("alibaba", (char) 97));
        //noinspection SpellCheckingInspection
        assertEquals("alibaa", CharSequenceUtil.removeLast("alibaba", (char) 98));
        assertEquals("alibaba", CharSequenceUtil.removeLast("alibaba", (char) 99));

        //noinspection SpellCheckingInspection
        assertEquals("alibab", CharSequenceUtil.removeLast(new StringBuffer("alibaba"), 'a'));
        //noinspection SpellCheckingInspection
        assertEquals("alibaa\n\t", CharSequenceUtil.removeLast(new StringBuilder("alibaba\n\t"), 'b'));
    }

    /**
     * Method: removeIndex(final CharSequence param, final char search)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testRemoveIndex() {
        assertEquals("", CharSequenceUtil.removeIndex(null, 1));
        assertEquals("", CharSequenceUtil.removeIndex("", 1));
        assertEquals("alibaba", CharSequenceUtil.removeIndex("alibaba", -2));
        assertEquals("alibaba", CharSequenceUtil.removeIndex("alibaba", -1));
        assertEquals("libaba", CharSequenceUtil.removeIndex("alibaba", 0));
        assertEquals("aibaba", CharSequenceUtil.removeIndex("alibaba", 1));
        assertEquals("albaba", CharSequenceUtil.removeIndex("alibaba", 2));
        assertEquals("aliaba", CharSequenceUtil.removeIndex("alibaba", 3));
        assertEquals("alibba", CharSequenceUtil.removeIndex("alibaba", 4));
        assertEquals("alibaa", CharSequenceUtil.removeIndex("alibaba", 5));
        assertEquals("alibab", CharSequenceUtil.removeIndex("alibaba", 6));
        assertEquals("alibaba", CharSequenceUtil.removeIndex("alibaba", 7));
        assertEquals("alibaba", CharSequenceUtil.removeIndex("alibaba", 8));

        assertEquals("aibaba", CharSequenceUtil.removeIndex(new StringBuffer("alibaba"), 1));
        assertEquals("aibaba\n\t", CharSequenceUtil.removeIndex(new StringBuilder("alibaba\n\t"), 1));
    }

    /**
     * Method: removeBlank(final CharSequence param)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testRemoveBlank() {
        assertEquals("", CharSequenceUtil.removeBlank(null));
        assertEquals("", CharSequenceUtil.removeBlank(""));
        assertEquals("alibaba", CharSequenceUtil.removeBlank("alibaba"));
        assertEquals("alibaba", CharSequenceUtil.removeBlank("   alibaba  "));
        assertEquals("alibaba", CharSequenceUtil.removeBlank("alib   ab a"));
        assertEquals("alibaba", CharSequenceUtil.removeBlank("alibaba\n\t"));
        assertEquals("alibaba", CharSequenceUtil.removeBlank("   \talibaba  "));
        assertEquals("alibaba", CharSequenceUtil.removeBlank("   \n\ralibaba\r\r\t\n  "));

        assertEquals("alibaba", CharSequenceUtil.removeBlank(new StringBuffer("alibaba")));
        assertEquals("alibaba", CharSequenceUtil.removeBlank(new StringBuffer("alib   ab a")));
        assertEquals("alibaba", CharSequenceUtil.removeBlank(new StringBuilder("alibaba\n\t")));
        assertEquals("alibaba", CharSequenceUtil.removeBlank(new StringBuilder("   \talibaba  ")));
        assertEquals("alibaba", CharSequenceUtil.removeBlank(new StringBuilder("   \n\ralibaba\r\r\t\n  ")));
    }

    /**
     * Method: removeLineBreaks(final CharSequence param)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testRemoveLineBreaks() {
        assertEquals("", CharSequenceUtil.removeLineBreaks(null));
        assertEquals("", CharSequenceUtil.removeLineBreaks(""));
        assertEquals("alibaba", CharSequenceUtil.removeLineBreaks("alibaba"));
        assertEquals("   alibaba  ", CharSequenceUtil.removeLineBreaks("   alibaba  "));
        assertEquals("alib   ab a", CharSequenceUtil.removeLineBreaks("alib   ab a"));
        assertEquals("alibaba\t", CharSequenceUtil.removeLineBreaks("alibaba\n\t"));
        assertEquals("   \talibaba  ", CharSequenceUtil.removeLineBreaks("   \talibaba  "));
        assertEquals("   alibaba\t  ", CharSequenceUtil.removeLineBreaks("   \n\ralibaba\r\r\t\n  "));

        assertEquals("alibaba", CharSequenceUtil.removeLineBreaks(new StringBuffer("alibaba")));
        assertEquals("alib   ab a", CharSequenceUtil.removeLineBreaks(new StringBuffer("alib   ab a")));
        assertEquals("alibaba\t", CharSequenceUtil.removeLineBreaks(new StringBuilder("alibaba\n\t")));
        assertEquals("   \talibaba  ", CharSequenceUtil.removeLineBreaks(new StringBuilder("   \talibaba  ")));
        assertEquals("   alibaba\t  ", CharSequenceUtil.removeLineBreaks(new StringBuilder("   \n\ralibaba\r\r\t\n  ")));
    }

    /**
     * Method: replace(final CharSequence param, final char search, final char replacement)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testReplace() {
        assertEquals("", CharSequenceUtil.replace(null, 'a', 'b'));
        assertEquals("", CharSequenceUtil.replace("", 'a', 'b'));
        assertEquals("alibaba", CharSequenceUtil.replace("alibaba", 'a', 'a'));
        assertEquals("blibbbb", CharSequenceUtil.replace("alibaba", 'a', 'b'));
        assertEquals("clibcbc", CharSequenceUtil.replace("alibaba", 'a', 'c'));
        assertEquals("alibaba", CharSequenceUtil.replace("alibaba", (char) 97, (char) 97));
        assertEquals("blibbbb", CharSequenceUtil.replace("alibaba", (char) 97, (char) 98));
        assertEquals("clibcbc", CharSequenceUtil.replace("alibaba", (char) 97, (char) 99));
        assertEquals("alibaba", CharSequenceUtil.replace("alibaba", 'A', 'B'));
        assertEquals("alibaba", CharSequenceUtil.replace("alibaba", 'A', 'C'));
        assertEquals("alibaba", CharSequenceUtil.replace("alibaba", (char) 65, (char) 66));
        assertEquals("alibaba", CharSequenceUtil.replace("alibaba", (char) 65, (char) 67));
        assertEquals("blibbbb", CharSequenceUtil.replace(new StringBuffer("alibaba"), 'a', 'b'));
        assertEquals("blibbbb\n\t", CharSequenceUtil.replace(new StringBuilder("alibaba\n\t"), 'a', 'b'));
    }

    /**
     * Method: replaceFirst(final CharSequence param, final char search, final char replacement)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testReplaceFirst() {
        assertEquals("", CharSequenceUtil.replaceFirst(null, 'a', 'b'));
        assertEquals("", CharSequenceUtil.replaceFirst("", 'a', 'b'));
        assertEquals("alibaba", CharSequenceUtil.replaceFirst("alibaba", 'a', 'a'));
        assertEquals("blibaba", CharSequenceUtil.replaceFirst("alibaba", 'a', 'b'));
        assertEquals("clibaba", CharSequenceUtil.replaceFirst("alibaba", 'a', 'c'));
        assertEquals("alibaba", CharSequenceUtil.replaceFirst("alibaba", (char) 97, (char) 97));
        assertEquals("blibaba", CharSequenceUtil.replaceFirst("alibaba", (char) 97, (char) 98));
        assertEquals("clibaba", CharSequenceUtil.replaceFirst("alibaba", (char) 97, (char) 99));
        assertEquals("alibaba", CharSequenceUtil.replaceFirst("alibaba", 'A', 'B'));
        assertEquals("alibaba", CharSequenceUtil.replaceFirst("alibaba", 'A', 'C'));
        assertEquals("alibaba", CharSequenceUtil.replaceFirst("alibaba", (char) 65, (char) 66));
        assertEquals("alibaba", CharSequenceUtil.replaceFirst("alibaba", (char) 65, (char) 67));
        assertEquals("blibaba", CharSequenceUtil.replaceFirst(new StringBuffer("alibaba"), 'a', 'b'));
        assertEquals("blibaba\n\t", CharSequenceUtil.replaceFirst(new StringBuilder("alibaba\n\t"), 'a', 'b'));
    }

    /**
     * Method: replaceLast(final CharSequence param, final char search, final char replacement)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testReplaceLast() {
        assertEquals("", CharSequenceUtil.replaceLast(null, 'a', 'b'));
        assertEquals("", CharSequenceUtil.replaceLast("", 'a', 'b'));
        assertEquals("alibaba", CharSequenceUtil.replaceLast("alibaba", 'a', 'a'));
        assertEquals("alibabb", CharSequenceUtil.replaceLast("alibaba", 'a', 'b'));
        assertEquals("alibabc", CharSequenceUtil.replaceLast("alibaba", 'a', 'c'));
        assertEquals("alibaba", CharSequenceUtil.replaceLast("alibaba", (char) 97, (char) 97));
        assertEquals("alibabb", CharSequenceUtil.replaceLast("alibaba", (char) 97, (char) 98));
        assertEquals("alibabc", CharSequenceUtil.replaceLast("alibaba", (char) 97, (char) 99));
        assertEquals("alibaba", CharSequenceUtil.replaceLast("alibaba", 'A', 'B'));
        assertEquals("alibaba", CharSequenceUtil.replaceLast("alibaba", 'A', 'C'));
        assertEquals("alibaba", CharSequenceUtil.replaceLast("alibaba", (char) 65, (char) 66));
        assertEquals("alibaba", CharSequenceUtil.replaceLast("alibaba", (char) 65, (char) 67));
        assertEquals("alibabb", CharSequenceUtil.replaceLast(new StringBuffer("alibaba"), 'a', 'b'));
        assertEquals("alibabb\n\t", CharSequenceUtil.replaceLast(new StringBuilder("alibaba\n\t"), 'a', 'b'));
    }

    /**
     * Method: replaceIndex(final CharSequence param, final char search, final char replacement)
     */
    @SuppressWarnings("SpellCheckingInspection")
    @Test
    public void testReplaceIndex() {
        assertEquals("", CharSequenceUtil.replaceIndex(null, 'a', 'b'));
        assertEquals("", CharSequenceUtil.replaceIndex("", 'a', 'b'));
        assertEquals("aaibaba", CharSequenceUtil.replaceIndex("alibaba", 1, 'a'));
        assertEquals("abibaba", CharSequenceUtil.replaceIndex("alibaba", 1, 'b'));
        assertEquals("acibaba", CharSequenceUtil.replaceIndex("alibaba", 1, 'c'));
        assertEquals("aaibaba", CharSequenceUtil.replaceIndex("alibaba", 1, (char) 97));
        assertEquals("abibaba", CharSequenceUtil.replaceIndex("alibaba", 1, (char) 98));
        assertEquals("acibaba", CharSequenceUtil.replaceIndex("alibaba", 1, (char) 99));
        assertEquals("alBbaba", CharSequenceUtil.replaceIndex("alibaba", 2, 'B'));
        assertEquals("alCbaba", CharSequenceUtil.replaceIndex("alibaba", 2, 'C'));
        assertEquals("alBbaba", CharSequenceUtil.replaceIndex("alibaba", 2, (char) 66));
        assertEquals("alCbaba", CharSequenceUtil.replaceIndex("alibaba", 2, (char) 67));
        assertEquals("blibaba", CharSequenceUtil.replaceIndex(new StringBuffer("alibaba"), 0, 'b'));
        assertEquals("blibaba\n\t", CharSequenceUtil.replaceIndex(new StringBuilder("alibaba\n\t"), 0, 'b'));
    }
}
