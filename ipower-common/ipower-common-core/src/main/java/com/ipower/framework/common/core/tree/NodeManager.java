package com.ipower.framework.common.core.tree;

import com.ipower.framework.common.core.constant.StringPool;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.map.MapUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 节点管理类
 *
 * @param <T> ID类型
 * @param <C> 节点类型
 * @author ldz
 */
public class NodeManager<T, C extends Node<T, C>> {

    /**
     * 节点的所有节点
     */
    private final ConcurrentMap<T, C> nodeMap = MapUtil.newConcurrentMap();

    /**
     * 节点的父节点ID
     */
    private final Map<T, Object> parentIdMap = MapUtil.newHashMap();

    public NodeManager(List<C> nodes) {
        nodes.forEach(c -> nodeMap.put(c.getId(), c));
    }

    /**
     * 根据节点ID获取一个节点
     *
     * @param id 节点ID
     * @return 对应的节点对象
     */
    public Node<T, C> getTreeNodeAt(T id) {
        if (nodeMap.containsKey(id)) {
            return nodeMap.get(id);
        }
        return null;
    }

    /**
     * 增加父节点ID
     *
     * @param parentId 父节点ID
     */
    public void addParentId(T parentId) {
        parentIdMap.put(parentId, StringPool.EMPTY);
    }

    /**
     * 获取树的根节点(一个节点对应多颗树)
     *
     * @return 树的根节点集合
     */
    public List<C> getRoot() {
        List<C> roots = new ArrayList<>();
        nodeMap.forEach((key, node) -> {
            if (ObjectUtil.equals(node.getParentId(), 0) || parentIdMap.containsKey(node.getId())) {
                roots.add(node);
            }
        });
        return roots.stream().sorted().collect(Collectors.toList());
    }

}
