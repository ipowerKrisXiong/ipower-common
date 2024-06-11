package com.ipower.framework.common.core;

/**
 * 过滤器接口
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">...</a>
 *
 * @param <T> 被过滤对象类型
 * @author kris
 * @since 1.0.0
 */
public interface Filter<T> {
    /**
     * 是否接受对象
     *
     * @param t 检查的对象
     * @return 是否接受对象
     */
    boolean accept(T t);
}