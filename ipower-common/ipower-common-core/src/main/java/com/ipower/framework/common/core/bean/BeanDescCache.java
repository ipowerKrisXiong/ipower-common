package com.ipower.framework.common.core.bean;

import com.ipower.framework.common.core.cache.SimpleCache;

/**
 * Bean属性缓存<br>
 * 缓存用于防止多次反射造成的性能问题
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @author kris
 * @since 1.0.0
 */
public enum BeanDescCache {
    /**
     * 厍变量
     */
    INSTANCE;

    private final SimpleCache<Class<?>, BeanDesc> bdCache = new SimpleCache<>();

    /**
     * 获得属性名和{@link BeanDesc}Map映射
     *
     * @param beanClass Bean的类
     * @return 属性名和{@link BeanDesc}映射
     */
    public BeanDesc getBeanDesc(Class<?> beanClass) {
        return bdCache.get(beanClass);
    }

    /**
     * 加入缓存
     *
     * @param beanClass Bean的类
     * @param BeanDesc  属性名和{@link BeanDesc}映射
     */
    public void putBeanDesc(Class<?> beanClass, BeanDesc BeanDesc) {
        bdCache.put(beanClass, BeanDesc);
    }
}
