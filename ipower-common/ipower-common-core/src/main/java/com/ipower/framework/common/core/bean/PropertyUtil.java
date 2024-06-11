package com.ipower.framework.common.core.bean;

import com.ipower.framework.common.core.collection.ArrayUtil;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.lang.Validate;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;

import static com.ipower.framework.common.core.lang.ObjectUtil.isNotNull;


/**
 * @author kris
 */
public final class PropertyUtil {

    private PropertyUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProperty(Object bean, String property) {
        Validate.notEmpty(property, "property can't be blank!");
        try {
            return (T) PropertyUtils.getProperty(bean, property);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setProperty(Object bean, String property, Object value) {
        Validate.notNull(bean, "bean can't be null!");
        Validate.notEmpty(property, "property can't be null!");
        try {
            PropertyUtils.setProperty(bean, property, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
        return isNotNull(clazz) ? PropertyUtils.getPropertyDescriptors(clazz) : new PropertyDescriptor[]{};
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Object bean) {
        Validate.notNull(bean, "bean can't be null!");
        return getPropertyDescriptors(bean.getClass());
    }

    public static void trimProperty(Object bean, String... excludeProperty) {
        PropertyDescriptor[] descriptors = getPropertyDescriptors(bean);
        try {
            for (PropertyDescriptor descriptor : descriptors) {
                if (ObjectUtil.equals(descriptor.getPropertyType(), String.class) && !ArrayUtil
                        .contains(excludeProperty, descriptor.getName())) {
                    String value = (String) descriptor.getReadMethod().invoke(bean);
                    if (ObjectUtil.isNotEmpty(value)) {
                        descriptor.getWriteMethod().invoke(bean, value.trim());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
