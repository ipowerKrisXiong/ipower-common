package com.ipower.framework.common.core.tree;

import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * 节点接口，提供节点相关的的方法定义
 *
 * @param <T> ID类型
 * @param <C> 节点类型
 * @author ldz
 */
public interface Node<T, C> extends Comparable<Node<T, C>>, Serializable {

    /**
     * 获取ID
     *
     * @return ID
     */
    T getId();

    /**
     * 获取父节点ID
     *
     * @return 父节点ID
     */
    T getParentId();

    /**
     * 获取节点标签名称
     *
     * @return 节点标签名称
     */
    CharSequence getName();

    /**
     * 获取权重
     *
     * @return 权重
     */
    Comparable<?> getWeight();

    /**
     * 获取子节点集合
     *
     * @return 子节点集合
     */
    List<C> getChildren();

    /**
     * 是否有子孙节点
     *
     * @return Boolean
     */
    default Boolean getHasChildren() {
        return false;
    }

    /**
     * 对比算法
     *
     * @param node 待比较节点对象
     * @return 比重因子
     */
    @SuppressWarnings("rawtypes")
    @Override
    default int compareTo(@NonNull Node node) {
        final Comparable weight = this.getWeight();
        if (null != weight) {
            final Comparable weightOther = node.getWeight();
            //noinspection unchecked
            return weight.compareTo(weightOther);
        }
        return 0;
    }

}
