package com.ipower.framework.common.core.collection;

import com.ipower.framework.common.core.lang.ObjectUtil;
import org.apache.commons.collections4.ComparatorUtils;

import java.util.Comparator;
import java.util.List;

import static java.util.Collections.emptyList;

/**
 * Class description goes here.
 *
 * @author kris
 */
public final class SortUtil {

    private SortUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    @SafeVarargs
    public static <T> List<T> sortList(List<T> list, Comparator<T>... comparators) {
        if (list == null) {
            return emptyList();
        }
        if (ObjectUtil.isEmpty(comparators)) {
            return list;
        }
        list.sort(toComparator(comparators));
        return list;
    }

    @SafeVarargs
    private static <T> Comparator<T> toComparator(Comparator<T>... comparators) {
        return comparators.length == 1 ? comparators[0] : ComparatorUtils.chainedComparator(comparators);
    }
}
