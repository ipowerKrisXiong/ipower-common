package com.ipower.framework.common.core.tree;

import com.ipower.framework.common.core.collection.Lists;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.tree.parser.NodeParser;

import java.util.Collections;
import java.util.List;

/**
 * 树工具类
 *
 * @author ldz
 */
public class TreeUtil {

    /**
     * 树构建
     *
     * @param <T>        转换的实体 为数据源里的对象类型
     * @param <E>        ID类型
     * @param list       源数据集合
     * @param nodeParser 转换器
     * @return List
     */
    public static <T, E> List<Tree<T>> build(List<E> list, NodeParser<T, E> nodeParser) {
        return build(list, TreeConfig.DEFAULT_CONFIG, nodeParser);
    }

    /**
     * 树构建
     *
     * @param <T>        转换的实体 为数据源里的对象类型
     * @param <E>        ID类型
     * @param list       源数据集合
     * @param treeConfig 配置
     * @param nodeParser 转换器
     * @return List
     */
    public static <T, E> List<Tree<T>> build(List<E> list, TreeConfig treeConfig, NodeParser<T, E> nodeParser) {
        //构建树节点集合
        List<Tree<T>> treeList = Lists.arrayList();
        Tree<T> tree;
        for (E obj : list) {
            tree = new Tree<>(treeConfig);
            nodeParser.parse(obj, tree);
            treeList.add(tree);
        }
        treeList = build(treeList);
        //排序
        Collections.sort(treeList);
        return treeList;
    }

    /**
     * 将节点数组归并为一个森林（多棵树）（填充节点的children域）
     * 时间复杂度为O(n^2)
     *
     * @param items 节点域
     * @return 多棵树的根节点集合
     */
    public static <T, C extends Node<T, C>> List<C> build(List<C> items) {
        NodeManager<T, C> nodeManager = new NodeManager<>(items);
        items.forEach(forestNode -> {
            if (ObjectUtil.notEquals(forestNode.getParentId(), 0)) {
                Node<T, C> node = nodeManager.getTreeNodeAt(forestNode.getParentId());
                if (node != null) {
                    node.getChildren().add(forestNode);
                } else {
                    nodeManager.addParentId(forestNode.getId());
                }
            }
        });
        return nodeManager.getRoot();
    }

}
