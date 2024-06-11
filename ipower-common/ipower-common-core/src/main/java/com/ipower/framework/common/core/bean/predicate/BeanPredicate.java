package com.ipower.framework.common.core.bean.predicate;

import com.ipower.framework.common.core.bean.PropertyUtil;
import com.ipower.framework.common.core.lang.Validate;
import org.apache.commons.collections4.Predicate;

/**
 * @author kris
 */
public class BeanPredicate<T> implements Predicate<T> {

    private final String property;

    private final Predicate predicate;


    public BeanPredicate(String property, Predicate predicate) {
        Validate.notEmpty(property, "property can't be blank!");
        Validate.notNull(predicate, "predicate can't be null!");
        this.property = property;
        this.predicate = predicate;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean evaluate(T bean) {
        Object value = PropertyUtil.getProperty(bean, property);
        return predicate.evaluate(value);
    }
}
