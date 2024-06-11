package com.ipower.framework.common.core.bean.closure;

import com.ipower.framework.common.core.bean.PropertyUtil;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.lang.Validate;
import org.apache.commons.collections4.Closure;

/**
 * @author kris
 */
public class BeanPropertyChangeClosure<T> implements Closure<T> {

    /**
     * 属性名
     */
    private final String property;

    /**
     * 属性值
     */
    private final Object value;

    public BeanPropertyChangeClosure(String property, Object value) {
        Validate.notEmpty(property, "property can't be blank!");
        this.property = property;
        this.value = value;
    }

    @Override
    public void execute(T bean) {
        if (ObjectUtil.isEmpty(bean)) {
            return;
        }
        PropertyUtil.setProperty(bean, property, value);
    }
}
