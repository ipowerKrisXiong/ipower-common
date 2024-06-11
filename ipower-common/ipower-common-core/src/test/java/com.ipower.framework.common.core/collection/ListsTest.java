package com.ipower.framework.common.core.collection;

import org.apache.commons.collections.iterators.IteratorEnumeration;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ListsTest Tester.
 *
 * @author diablo
 */
public class ListsTest {

    @Test
    public void testListIsLinked() {
        List<Integer> arrayList = Lists.arrayList();
        assertTrue(CollectionUtil.isEmpty(arrayList));
        assertEquals(ArrayList.class, arrayList.getClass());

        List<Integer> linkedList = Lists.linkedList();
        assertEquals(0, linkedList.size());
        assertEquals(LinkedList.class, linkedList.getClass());
    }

    @Test
    public void testListForIsLinkedValues() {
        Integer integer = 5;
        Integer integer1 = 222;
        List<Integer> arrayList = Lists.arrayList(integer, integer1);
        assertTrue(arrayList.contains(5) && arrayList.contains(222));
        assertEquals(ArrayList.class, arrayList.getClass());

        List<Integer> linkedList = Lists.linkedList(integer, integer1);
        assertTrue(linkedList.contains(5) && linkedList.contains(222));
        assertEquals(LinkedList.class, linkedList.getClass());
    }

    /**
     * 新建一个ArrayList
     * Method: newArrayList(T... values)
     */
    @Test
    public void testNewArrayListValues() {
        Integer[] integers = new Integer[]{5, 6};

        List<Integer> arrayList = Lists.arrayList(integers);
        assertEquals(5, arrayList.get(0));
        assertEquals(ArrayList.class, arrayList.getClass());

        List<Integer> linkedList = Lists.linkedList(integers);
        assertEquals(5, linkedList.get(0));
        assertEquals(LinkedList.class, linkedList.getClass());

        integers = null;
        //noinspection ConstantValue
        assertEquals("[]", Lists.arrayList(integers).toString());
        //noinspection ConstantValue
        assertEquals("[]", Lists.linkedList(integers).toString());
    }

    @Test
    public void testListForIsLinkedCollection() {
        Set<Integer> set = new HashSet<>();
        set.add(1);
        List<Integer> arrayList = Lists.arrayList(set);
        assertEquals(1, arrayList.get(0));
        assertEquals(ArrayList.class, arrayList.getClass());

        List<Integer> linkedList = Lists.linkedList(set);
        assertEquals(1, linkedList.get(0));
        assertEquals(LinkedList.class, linkedList.getClass());

        set = null;
        //noinspection ConstantValue
        assertEquals("[]", Lists.arrayList(set).toString());
        //noinspection ConstantValue
        assertEquals("[]", Lists.linkedList(set).toString());
    }

    @Test
    public void testListForIsLinkedIterable() {
        Iterable<Integer> iterable = Arrays.asList(1, 5);
        List<Integer> arrayList = Lists.arrayList(iterable);
        assertEquals(1, arrayList.get(0));
        assertEquals(ArrayList.class, arrayList.getClass());

        List<Integer> linkedList = Lists.linkedList(iterable);
        assertEquals(1, linkedList.get(0));
        assertEquals(LinkedList.class, linkedList.getClass());

        iterable = null;
        //noinspection ConstantValue
        assertEquals("[]", Lists.arrayList(iterable).toString());
        //noinspection ConstantValue
        assertEquals("[]", Lists.linkedList(iterable).toString());
    }

    @Test
    public void testListForIsLinkedIter() {
        Iterable<Integer> iterable = Arrays.asList(1, 5);
        Iterator<Integer> iterator1 = iterable.iterator();
        List<Integer> arrayList = Lists.arrayList(iterator1);
        assertEquals(1, arrayList.get(0));
        assertEquals(ArrayList.class, arrayList.getClass());
        Iterator<Integer> iterator2 = iterable.iterator();
        List<Integer> linkedList = Lists.linkedList(iterator2);
        assertEquals(1, linkedList.get(0));
        assertEquals(LinkedList.class, linkedList.getClass());

        iterator1 = null;
        //noinspection ConstantValue
        assertEquals("[]", Lists.arrayList(iterator1).toString());
        iterator2 = null;
        //noinspection ConstantValue
        assertEquals("[]", Lists.linkedList(iterator2).toString());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testListForIsLinkedEnumeration() {
        List<Integer> list = new ArrayList<>();
        list.add(500);
        list.add(1000);
        Iterator<Integer> iterator1 = list.iterator();
        Enumeration<Integer> enumeration1 = new IteratorEnumeration();
        //noinspection CastCanBeRemovedNarrowingVariableType
        ((IteratorEnumeration) enumeration1).setIterator(iterator1);

        List<Integer> arrayList = Lists.arrayList(enumeration1);
        assertEquals(500, arrayList.get(0));
        assertEquals(ArrayList.class, arrayList.getClass());

        Iterator<Integer> iterator2 = list.iterator();
        Enumeration<Integer> enumeration2 = new IteratorEnumeration();
        //noinspection CastCanBeRemovedNarrowingVariableType
        ((IteratorEnumeration) enumeration2).setIterator(iterator2);

        List<Integer> linkedList = Lists.linkedList(enumeration2);
        assertEquals(500, linkedList.get(0));
        assertEquals(LinkedList.class, linkedList.getClass());

        enumeration1 = null;
        //noinspection ConstantValue
        assertEquals("[]", Lists.arrayList(enumeration1).toString());
        enumeration2 = null;
        //noinspection ConstantValue
        assertEquals("[]", Lists.linkedList(enumeration2).toString());
    }
}
