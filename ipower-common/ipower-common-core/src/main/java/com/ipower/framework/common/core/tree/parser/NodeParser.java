package com.ipower.framework.common.core.tree.parser;

import com.ipower.framework.common.core.tree.Tree;

/**
 * 树节点解析器
 *
 * @param <T> ID类型
 * @param <E> 转换的实体 为数据源里的对象类型
 * @author ldz
 */
@FunctionalInterface
public interface NodeParser<T, E> {

    /**
     * 源数据对象转树节点对象
     *
     * @param source 源数据实体
     * @param tree   树节点实体
     */
    void parse(E source, Tree<T> tree);

}

