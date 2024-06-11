package com.ipower.framework.common.core.collection;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * CollectionValidator Tester.
 *
 * @author diablo
 */
class CollectionValidatorTest {

    @Test
    void testIsEmpty() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CollectionUtil.isEmpty(list1));
        assertTrue(CollectionUtil.isEmpty(list2));
        assertTrue(CollectionUtil.isEmpty(Lists.hashSet()));
        assertFalse(CollectionUtil.isEmpty(Lists.arrayList(null, null, null)));
        assertFalse(CollectionUtil.isEmpty(Lists.arrayList("", "", "")));
        assertFalse(CollectionUtil.isEmpty(Lists.arrayList("", "", " ", "\t\n")));
        assertFalse(CollectionUtil.isEmpty(Lists.arrayList("a", "b", "c", "d")));
        assertFalse(CollectionUtil.isEmpty(Lists.arrayList("a", "b", " ", null, null)));
        assertFalse(CollectionUtil.isEmpty(Lists.arrayList("a", "b", " ", "", "")));
        assertFalse(CollectionUtil.isEmpty(Lists.arrayList("a", "b", " ", null, "")));
        assertFalse(CollectionUtil.isEmpty(Lists.arrayList("a", "b", null, "", " ", "\t\n")));
        assertFalse(CollectionUtil.isEmpty(Lists.hashSet("a", "b", null, "", " ", "\t\n")));
    }

    @Test
    void testIsNotEmpty() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertFalse(CollectionUtil.isNotEmpty(list1));
        assertFalse(CollectionUtil.isNotEmpty(list2));
        assertFalse(CollectionUtil.isNotEmpty(Lists.hashSet()));
        assertTrue(CollectionUtil.isNotEmpty(Lists.arrayList(null, null, null)));
        assertTrue(CollectionUtil.isNotEmpty(Lists.arrayList("", "", "")));
        assertTrue(CollectionUtil.isNotEmpty(Lists.arrayList("", "", " ", "\t\n")));
        assertTrue(CollectionUtil.isNotEmpty(Lists.arrayList("a", "b", "c", "d")));
        assertTrue(CollectionUtil.isNotEmpty(Lists.arrayList("a", "b", " ", null, null)));
        assertTrue(CollectionUtil.isNotEmpty(Lists.arrayList("a", "b", " ", "", "")));
        assertTrue(CollectionUtil.isNotEmpty(Lists.arrayList("a", "b", " ", null, "")));
        assertTrue(CollectionUtil.isNotEmpty(Lists.arrayList("a", "b", null, "", " ", "\t\n")));
        assertTrue(CollectionUtil.isNotEmpty(Lists.hashSet("a", "b", null, "", " ", "\t\n")));
    }

    @Test
    void testIsAllNull() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CollectionUtil.isAllNull(list1));
        assertTrue(CollectionUtil.isAllNull(list2));
        assertTrue(CollectionUtil.isAllNull(Lists.hashSet()));
        assertTrue(CollectionUtil.isAllNull(Lists.arrayList(null, null, null)));
        assertFalse(CollectionUtil.isAllNull(Lists.arrayList("", "", "")));
        assertFalse(CollectionUtil.isAllNull(Lists.arrayList("", "", " ", "\t\n")));
        assertFalse(CollectionUtil.isAllNull(Lists.arrayList("a", "b", "c", "d")));
        assertFalse(CollectionUtil.isAllNull(Lists.arrayList("a", "b", " ", null, null)));
        assertFalse(CollectionUtil.isAllNull(Lists.arrayList("a", "b", " ", "", "")));
        assertFalse(CollectionUtil.isAllNull(Lists.arrayList("a", "b", " ", null, "")));
        assertFalse(CollectionUtil.isAllNull(Lists.arrayList("a", "b", null, "", " ", "\t\n")));
        assertFalse(CollectionUtil.isAllNull(Lists.hashSet("a", "b", null, "", " ", "\t\n")));
    }

    @Test
    void testIsAllNotNull() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertFalse(CollectionUtil.isAllNotNull(list1));
        assertFalse(CollectionUtil.isAllNotNull(list2));
        assertFalse(CollectionUtil.isAllNotNull(Lists.hashSet()));
        assertFalse(CollectionUtil.isAllNotNull(Lists.arrayList(null, null, null)));
        assertTrue(CollectionUtil.isAllNotNull(Lists.arrayList("", "", "")));
        assertTrue(CollectionUtil.isAllNotNull(Lists.arrayList("", "", " ", "\t\n")));
        assertTrue(CollectionUtil.isAllNotNull(Lists.arrayList("a", "b", "c", "d")));
        assertFalse(CollectionUtil.isAllNotNull(Lists.arrayList("a", "b", " ", null, null)));
        assertTrue(CollectionUtil.isAllNotNull(Lists.arrayList("a", "b", " ", "", "")));
        assertFalse(CollectionUtil.isAllNotNull(Lists.arrayList("a", "b", " ", null, "")));
        assertFalse(CollectionUtil.isAllNotNull(Lists.arrayList("a", "b", null, "", " ", "\t\n")));
        assertFalse(CollectionUtil.isAllNotNull(Lists.hashSet("a", "b", null, "", " ", "\t\n")));
    }

    @Test
    void testIsAllEmpty() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CollectionUtil.isAllEmpty(list1));
        assertTrue(CollectionUtil.isAllEmpty(list2));
        assertTrue(CollectionUtil.isAllEmpty(Lists.hashSet()));
        assertTrue(CollectionUtil.isAllEmpty(Lists.arrayList(null, null, null)));
        assertTrue(CollectionUtil.isAllEmpty(Lists.arrayList("", "", "")));
        assertFalse(CollectionUtil.isAllEmpty(Lists.arrayList("", "", " ", "\t\n")));
        assertFalse(CollectionUtil.isAllEmpty(Lists.arrayList("a", "b", "c", "d")));
        assertFalse(CollectionUtil.isAllEmpty(Lists.arrayList("a", "b", " ", null, null)));
        assertFalse(CollectionUtil.isAllEmpty(Lists.arrayList("a", "b", " ", "", "")));
        assertFalse(CollectionUtil.isAllEmpty(Lists.arrayList("a", "b", " ", null, "")));
        assertFalse(CollectionUtil.isAllEmpty(Lists.arrayList("a", "b", null, "", " ", "\t\n")));
        assertFalse(CollectionUtil.isAllEmpty(Lists.hashSet("a", "b", null, "", " ", "\t\n")));
    }

    @Test
    void testIsAllNotEmpty() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertFalse(CollectionUtil.isAllNotEmpty(list1));
        assertFalse(CollectionUtil.isAllNotEmpty(list2));
        assertFalse(CollectionUtil.isAllNotEmpty(Lists.hashSet()));
        assertFalse(CollectionUtil.isAllNotEmpty(Lists.arrayList(null, null, null)));
        assertFalse(CollectionUtil.isAllNotEmpty(Lists.arrayList("", "", "")));
        assertFalse(CollectionUtil.isAllNotEmpty(Lists.arrayList("", "", " ", "\t\n")));
        assertTrue(CollectionUtil.isAllNotEmpty(Lists.arrayList("a", "b", "c", "d")));
        assertFalse(CollectionUtil.isAllNotEmpty(Lists.arrayList("a", "b", " ", null, null)));
        assertFalse(CollectionUtil.isAllNotEmpty(Lists.arrayList("a", "b", " ", "", "")));
        assertFalse(CollectionUtil.isAllNotEmpty(Lists.arrayList("a", "b", " ", null, "")));
        assertFalse(CollectionUtil.isAllNotEmpty(Lists.arrayList("a", "b", null, "", " ", "\t\n")));
        assertFalse(CollectionUtil.isAllNotEmpty(Lists.hashSet("a", "b", null, "", " ", "\t\n")));
    }

    @Test
    void testHasNull() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CollectionUtil.hasNull(list1));
        assertTrue(CollectionUtil.hasNull(list2));
        assertTrue(CollectionUtil.hasNull(Lists.hashSet()));
        assertTrue(CollectionUtil.hasNull(Lists.arrayList(null, null, null)));
        assertFalse(CollectionUtil.hasNull(Lists.arrayList("", "", "")));
        assertFalse(CollectionUtil.hasNull(Lists.arrayList("", "", " ", "\t\n")));
        assertFalse(CollectionUtil.hasNull(Lists.arrayList("a", "b", "c", "d")));
        assertTrue(CollectionUtil.hasNull(Lists.arrayList("a", "b", " ", null, null)));
        assertFalse(CollectionUtil.hasNull(Lists.arrayList("a", "b", " ", "", "")));
        assertTrue(CollectionUtil.hasNull(Lists.arrayList("a", "b", " ", null, "")));
        assertTrue(CollectionUtil.hasNull(Lists.arrayList("a", "b", null, "", " ", "\t\n")));
        assertTrue(CollectionUtil.hasNull(Lists.hashSet("a", "b", null, "", " ", "\t\n")));
    }

    @Test
    void testHasEmpty() {
        List<String> list1 = null, list2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CollectionUtil.hasEmpty(list1));
        assertTrue(CollectionUtil.hasEmpty(list2));
        assertTrue(CollectionUtil.hasEmpty(Lists.hashSet()));
        assertTrue(CollectionUtil.hasEmpty(Lists.arrayList(null, null, null)));
        assertTrue(CollectionUtil.hasEmpty(Lists.arrayList("", "", "")));
        assertTrue(CollectionUtil.hasEmpty(Lists.arrayList("", "", " ", "\t\n")));
        assertFalse(CollectionUtil.hasEmpty(Lists.arrayList("a", "b", "c", "d")));
        assertTrue(CollectionUtil.hasEmpty(Lists.arrayList("a", "b", " ", null, null)));
        assertTrue(CollectionUtil.hasEmpty(Lists.arrayList("a", "b", " ", "", "")));
        assertTrue(CollectionUtil.hasEmpty(Lists.arrayList("a", "b", " ", null, "")));
        assertTrue(CollectionUtil.hasEmpty(Lists.arrayList("a", "b", null, "", " ", "\t\n")));
        assertTrue(CollectionUtil.hasEmpty(Lists.hashSet("a", "b", null, "", " ", "\t\n")));
    }

    @Test
    void testIsEmptyIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CollectionUtil.isEmpty(iterable1));
        assertTrue(CollectionUtil.isEmpty(iterable2));
        Iterable<String> iterable3 = Lists.hashSet();
        assertTrue(CollectionUtil.isEmpty(iterable3));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertFalse(CollectionUtil.isEmpty(iterable4));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertFalse(CollectionUtil.isEmpty(iterable5));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertFalse(CollectionUtil.isEmpty(iterable6));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertFalse(CollectionUtil.isEmpty(iterable7));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertFalse(CollectionUtil.isEmpty(iterable8));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertFalse(CollectionUtil.isEmpty(iterable9));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertFalse(CollectionUtil.isEmpty(iterable10));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isEmpty(iterable11));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isEmpty(iterable12));
    }

    @Test
    void testIsNotIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.arrayList();
        //noinspection ConstantValue
        assertFalse(CollectionUtil.isNotEmpty(iterable1));
        assertFalse(CollectionUtil.isNotEmpty(iterable2));
        Iterable<String> iterable3 = Lists.hashSet();
        assertFalse(CollectionUtil.isNotEmpty(iterable3));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertTrue(CollectionUtil.isNotEmpty(iterable4));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertTrue(CollectionUtil.isNotEmpty(iterable5));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertTrue(CollectionUtil.isNotEmpty(iterable6));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertTrue(CollectionUtil.isNotEmpty(iterable7));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertTrue(CollectionUtil.isNotEmpty(iterable8));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertTrue(CollectionUtil.isNotEmpty(iterable9));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertTrue(CollectionUtil.isNotEmpty(iterable10));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertTrue(CollectionUtil.isNotEmpty(iterable11));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertTrue(CollectionUtil.isNotEmpty(iterable12));
    }

    @Test
    void testIsAllNullIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CollectionUtil.isAllNull(iterable1));
        assertTrue(CollectionUtil.isAllNull(iterable2));
        Iterable<String> iterable3 = Lists.hashSet();
        assertTrue(CollectionUtil.isAllNull(iterable3));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertTrue(CollectionUtil.isAllNull(iterable4));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertFalse(CollectionUtil.isAllNull(iterable5));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNull(iterable6));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertFalse(CollectionUtil.isAllNull(iterable7));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertFalse(CollectionUtil.isAllNull(iterable8));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertFalse(CollectionUtil.isAllNull(iterable9));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertFalse(CollectionUtil.isAllNull(iterable10));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNull(iterable11));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNull(iterable12));
    }

    @Test
    void testIsAllNotNullIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.arrayList();
        //noinspection ConstantValue
        assertFalse(CollectionUtil.isAllNotNull(iterable1));
        assertFalse(CollectionUtil.isAllNotNull(iterable2));
        Iterable<String> iterable3 = Lists.hashSet();
        assertFalse(CollectionUtil.isAllNotNull(iterable3));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertFalse(CollectionUtil.isAllNotNull(iterable4));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertTrue(CollectionUtil.isAllNotNull(iterable5));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertTrue(CollectionUtil.isAllNotNull(iterable6));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertTrue(CollectionUtil.isAllNotNull(iterable7));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertFalse(CollectionUtil.isAllNotNull(iterable8));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertTrue(CollectionUtil.isAllNotNull(iterable9));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertFalse(CollectionUtil.isAllNotNull(iterable10));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNotNull(iterable11));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNotNull(iterable12));
    }

    @Test
    void testIsAllEmptyIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CollectionUtil.isAllEmpty(iterable1));
        assertTrue(CollectionUtil.isAllEmpty(iterable2));
        Iterable<String> iterable3 = Lists.hashSet();
        assertTrue(CollectionUtil.isAllEmpty(iterable3));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertTrue(CollectionUtil.isAllEmpty(iterable4));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertTrue(CollectionUtil.isAllEmpty(iterable5));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllEmpty(iterable6));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertFalse(CollectionUtil.isAllEmpty(iterable7));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertFalse(CollectionUtil.isAllEmpty(iterable8));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertFalse(CollectionUtil.isAllEmpty(iterable9));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertFalse(CollectionUtil.isAllEmpty(iterable10));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllEmpty(iterable11));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllEmpty(iterable12));
    }

    @Test
    void testIsAllNotEmptyIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.arrayList();
        //noinspection ConstantValue
        assertFalse(CollectionUtil.isAllNotEmpty(iterable1));
        assertFalse(CollectionUtil.isAllNotEmpty(iterable2));
        Iterable<String> iterable3 = Lists.hashSet();
        assertFalse(CollectionUtil.isAllNotEmpty(iterable3));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertFalse(CollectionUtil.isAllNotEmpty(iterable4));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertFalse(CollectionUtil.isAllNotEmpty(iterable5));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNotEmpty(iterable6));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertTrue(CollectionUtil.isAllNotEmpty(iterable7));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertFalse(CollectionUtil.isAllNotEmpty(iterable8));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertFalse(CollectionUtil.isAllNotEmpty(iterable9));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertFalse(CollectionUtil.isAllNotEmpty(iterable10));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNotEmpty(iterable11));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNotEmpty(iterable12));
    }

    @Test
    void testHasNullIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CollectionUtil.hasNull(iterable1));
        assertTrue(CollectionUtil.hasNull(iterable2));
        Iterable<String> iterable3 = Lists.hashSet();
        assertTrue(CollectionUtil.hasNull(iterable3));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertTrue(CollectionUtil.hasNull(iterable4));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertFalse(CollectionUtil.hasNull(iterable5));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertFalse(CollectionUtil.hasNull(iterable6));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertFalse(CollectionUtil.hasNull(iterable7));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertTrue(CollectionUtil.hasNull(iterable8));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertFalse(CollectionUtil.hasNull(iterable9));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertTrue(CollectionUtil.hasNull(iterable10));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertTrue(CollectionUtil.hasNull(iterable11));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertTrue(CollectionUtil.hasNull(iterable12));
    }

    @Test
    void testHasEmptyIterable() {
        Iterable<String> iterable1 = null, iterable2 = Lists.arrayList();
        //noinspection ConstantValue
        assertTrue(CollectionUtil.hasEmpty(iterable1));
        assertTrue(CollectionUtil.hasEmpty(iterable2));
        Iterable<String> iterable3 = Lists.hashSet();
        assertTrue(CollectionUtil.hasEmpty(iterable3));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertTrue(CollectionUtil.hasEmpty(iterable4));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertTrue(CollectionUtil.hasEmpty(iterable5));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertTrue(CollectionUtil.hasEmpty(iterable6));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertFalse(CollectionUtil.hasEmpty(iterable7));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertTrue(CollectionUtil.hasEmpty(iterable8));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertTrue(CollectionUtil.hasEmpty(iterable9));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertTrue(CollectionUtil.hasEmpty(iterable10));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertTrue(CollectionUtil.hasEmpty(iterable11));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertTrue(CollectionUtil.hasEmpty(iterable12));
    }

    @Test
    void testIsEmptyIterator() {
        Iterator<String> iterator = null;
        //noinspection ConstantValue
        assertTrue(CollectionUtil.isEmpty(iterator));
        Iterable<String> iterable2 = Lists.arrayList();
        assertTrue(CollectionUtil.isEmpty(iterable2.iterator()));
        Iterable<String> iterable3 = Lists.hashSet();
        assertTrue(CollectionUtil.isEmpty(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertFalse(CollectionUtil.isEmpty(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertFalse(CollectionUtil.isEmpty(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertFalse(CollectionUtil.isEmpty(iterable6.iterator()));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertFalse(CollectionUtil.isEmpty(iterable7.iterator()));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertFalse(CollectionUtil.isEmpty(iterable8.iterator()));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertFalse(CollectionUtil.isEmpty(iterable9.iterator()));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertFalse(CollectionUtil.isEmpty(iterable10.iterator()));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isEmpty(iterable11.iterator()));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isEmpty(iterable12.iterator()));
    }

    @Test
    void testIsNotEmptyIterator() {
        Iterator<String> iterator = null;
        //noinspection ConstantValue
        assertFalse(CollectionUtil.isNotEmpty(iterator));
        Iterable<String> iterable2 = Lists.arrayList();
        assertFalse(CollectionUtil.isNotEmpty(iterable2.iterator()));
        Iterable<String> iterable3 = Lists.hashSet();
        assertFalse(CollectionUtil.isNotEmpty(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertTrue(CollectionUtil.isNotEmpty(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertTrue(CollectionUtil.isNotEmpty(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertTrue(CollectionUtil.isNotEmpty(iterable6.iterator()));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertTrue(CollectionUtil.isNotEmpty(iterable7.iterator()));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertTrue(CollectionUtil.isNotEmpty(iterable8.iterator()));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertTrue(CollectionUtil.isNotEmpty(iterable9.iterator()));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertTrue(CollectionUtil.isNotEmpty(iterable10.iterator()));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertTrue(CollectionUtil.isNotEmpty(iterable11.iterator()));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertTrue(CollectionUtil.isNotEmpty(iterable12.iterator()));
    }

    @Test
    void testIsAllNullIterator() {
        Iterator<String> iterator = null;
        //noinspection ConstantValue
        assertTrue(CollectionUtil.isAllNull(iterator));
        Iterable<String> iterable2 = Lists.arrayList();
        assertTrue(CollectionUtil.isAllNull(iterable2.iterator()));
        Iterable<String> iterable3 = Lists.hashSet();
        assertTrue(CollectionUtil.isAllNull(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertTrue(CollectionUtil.isAllNull(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertFalse(CollectionUtil.isAllNull(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNull(iterable6.iterator()));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertFalse(CollectionUtil.isAllNull(iterable7.iterator()));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertFalse(CollectionUtil.isAllNull(iterable8.iterator()));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertFalse(CollectionUtil.isAllNull(iterable9.iterator()));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertFalse(CollectionUtil.isAllNull(iterable10.iterator()));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNull(iterable11.iterator()));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNull(iterable12.iterator()));
    }

    @Test
    void testIsAllNotNullIterator() {
        Iterator<String> iterator = null;
        //noinspection ConstantValue
        assertFalse(CollectionUtil.isAllNotNull(iterator));
        Iterable<String> iterable2 = Lists.arrayList();
        assertFalse(CollectionUtil.isAllNotNull(iterable2.iterator()));
        Iterable<String> iterable3 = Lists.hashSet();
        assertFalse(CollectionUtil.isAllNotNull(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertFalse(CollectionUtil.isAllNotNull(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertTrue(CollectionUtil.isAllNotNull(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertTrue(CollectionUtil.isAllNotNull(iterable6.iterator()));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertTrue(CollectionUtil.isAllNotNull(iterable7.iterator()));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertFalse(CollectionUtil.isAllNotNull(iterable8.iterator()));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertTrue(CollectionUtil.isAllNotNull(iterable9.iterator()));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertFalse(CollectionUtil.isAllNotNull(iterable10.iterator()));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNotNull(iterable11.iterator()));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNotNull(iterable12.iterator()));
    }

    @Test
    void testIsAllEmptyIterator() {
        Iterator<String> iterator = null;
        //noinspection ConstantValue
        assertTrue(CollectionUtil.isAllEmpty(iterator));
        Iterable<String> iterable2 = Lists.arrayList();
        assertTrue(CollectionUtil.isAllEmpty(iterable2.iterator()));
        Iterable<String> iterable3 = Lists.hashSet();
        assertTrue(CollectionUtil.isAllEmpty(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertTrue(CollectionUtil.isAllEmpty(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertTrue(CollectionUtil.isAllEmpty(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllEmpty(iterable6.iterator()));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertFalse(CollectionUtil.isAllEmpty(iterable7.iterator()));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertFalse(CollectionUtil.isAllEmpty(iterable8.iterator()));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertFalse(CollectionUtil.isAllEmpty(iterable9.iterator()));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertFalse(CollectionUtil.isAllEmpty(iterable10.iterator()));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllEmpty(iterable11.iterator()));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllEmpty(iterable12.iterator()));
    }

    @Test
    void testIsAllNotEmptyIterator() {
        Iterator<String> iterator = null;
        //noinspection ConstantValue
        assertFalse(CollectionUtil.isAllNotEmpty(iterator));
        Iterable<String> iterable2 = Lists.arrayList();
        assertFalse(CollectionUtil.isAllNotEmpty(iterable2.iterator()));
        Iterable<String> iterable3 = Lists.hashSet();
        assertFalse(CollectionUtil.isAllNotEmpty(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertFalse(CollectionUtil.isAllNotEmpty(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertFalse(CollectionUtil.isAllNotEmpty(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNotEmpty(iterable6.iterator()));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertTrue(CollectionUtil.isAllNotEmpty(iterable7.iterator()));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertFalse(CollectionUtil.isAllNotEmpty(iterable8.iterator()));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertFalse(CollectionUtil.isAllNotEmpty(iterable9.iterator()));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertFalse(CollectionUtil.isAllNotEmpty(iterable10.iterator()));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNotEmpty(iterable11.iterator()));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertFalse(CollectionUtil.isAllNotEmpty(iterable12.iterator()));
    }

    @Test
    void testHasNullIterator() {
        Iterator<String> iterator = null;
        //noinspection ConstantValue
        assertTrue(CollectionUtil.hasNull(iterator));
        Iterable<String> iterable2 = Lists.arrayList();
        assertTrue(CollectionUtil.hasNull(iterable2.iterator()));
        Iterable<String> iterable3 = Lists.hashSet();
        assertTrue(CollectionUtil.hasNull(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertTrue(CollectionUtil.hasNull(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertFalse(CollectionUtil.hasNull(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertFalse(CollectionUtil.hasNull(iterable6.iterator()));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertFalse(CollectionUtil.hasNull(iterable7.iterator()));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertTrue(CollectionUtil.hasNull(iterable8.iterator()));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertFalse(CollectionUtil.hasNull(iterable9.iterator()));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertTrue(CollectionUtil.hasNull(iterable10.iterator()));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertTrue(CollectionUtil.hasNull(iterable11.iterator()));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertTrue(CollectionUtil.hasNull(iterable12.iterator()));
    }

    @Test
    void testHasEmptyIterator() {
        Iterator<String> iterator = null;
        //noinspection ConstantValue
        assertTrue(CollectionUtil.hasEmpty(iterator));
        Iterable<String> iterable2 = Lists.arrayList();
        assertTrue(CollectionUtil.hasEmpty(iterable2.iterator()));
        Iterable<String> iterable3 = Lists.hashSet();
        assertTrue(CollectionUtil.hasEmpty(iterable3.iterator()));
        Iterable<String> iterable4 = Lists.arrayList(null, null, null);
        assertTrue(CollectionUtil.hasEmpty(iterable4.iterator()));
        Iterable<String> iterable5 = Lists.arrayList("", "", "");
        assertTrue(CollectionUtil.hasEmpty(iterable5.iterator()));
        Iterable<String> iterable6 = Lists.arrayList("", "", " ", "\t\n");
        assertTrue(CollectionUtil.hasEmpty(iterable6.iterator()));
        Iterable<String> iterable7 = Lists.arrayList("a", "b", "c", "d");
        assertFalse(CollectionUtil.hasEmpty(iterable7.iterator()));
        Iterable<String> iterable8 = Lists.arrayList("a", "b", " ", null, null);
        assertTrue(CollectionUtil.hasEmpty(iterable8.iterator()));
        Iterable<String> iterable9 = Lists.arrayList("a", "b", " ", "", "");
        assertTrue(CollectionUtil.hasEmpty(iterable9.iterator()));
        Iterable<String> iterable10 = Lists.arrayList("a", "b", " ", null, "");
        assertTrue(CollectionUtil.hasEmpty(iterable10.iterator()));
        Iterable<String> iterable11 = Lists.arrayList("a", "b", null, "", " ", "\t\n");
        assertTrue(CollectionUtil.hasEmpty(iterable11.iterator()));
        Iterable<String> iterable12 = Lists.hashSet("a", "b", null, "", " ", "\t\n");
        assertTrue(CollectionUtil.hasEmpty(iterable12.iterator()));
    }
}