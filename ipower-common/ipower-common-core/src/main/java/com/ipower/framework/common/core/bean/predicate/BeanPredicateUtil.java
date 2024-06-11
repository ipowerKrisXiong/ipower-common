package com.ipower.framework.common.core.bean.predicate;

import com.ipower.framework.common.core.collection.ArrayUtil;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.lang.Validate;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;

import java.util.Collection;

/**
 * @author kris
 */
public final class BeanPredicateUtil {

    private BeanPredicateUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    public static <T, V> Predicate<T> equalPredicate(String property, V value) {
        Validate.notEmpty(property, "property can't be blank!");
        return new BeanPredicate<>(property, PredicateUtils.equalPredicate(value));
    }

    @SafeVarargs
    public static <T, V> Predicate<T> containsPredicate(final String property, final V... values) {
        Validate.notEmpty(property, "property can't be blank!");
        return new BeanPredicate<>(property, (Predicate<V>) value -> ArrayUtil.contains(values, value));
    }

    public static <T, V> Predicate<T> containsPredicate(final String property, final Collection<V> collection) {
        Validate.notEmpty(property, "property can't be blank!");
        return new BeanPredicate<>(property, (Predicate<V>) value -> !ObjectUtil.isEmpty(collection) && collection.contains(value));
    }
}
