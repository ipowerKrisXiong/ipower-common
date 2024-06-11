package com.ipower.framework.common.core.tree;

import com.ipower.framework.common.core.lang.ObjectUtil;
import org.springframework.util.Assert;

import java.io.Serial;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 通过转换器将你的实体转化为TreeNodeMap节点实体 属性都存在此处,属性有序，可支持排序
 *
 * @param <T> ID类型
 * @author ldz
 */
public class Tree<T> extends LinkedHashMap<String, Object> implements Node<T, Tree<T>> {

    @Serial
    private static final long serialVersionUID = 1L;

    private final TreeConfig treeConfig;

    public Tree() {
        this(null);
    }

    /**
     * 构造
     *
     * @param treeConfig TreeNode配置
     */
    public Tree(TreeConfig treeConfig) {
        super();
        this.treeConfig = ObjectUtil.nullToDefault(treeConfig, TreeConfig.DEFAULT_CONFIG);
        this.put(treeConfig.getChildrenKey(), new ArrayList<>());
    }

    @Override
    public T getId() {
        //noinspection unchecked
        return (T) this.get(treeConfig.getIdKey());
    }

    public Tree<T> setId(T id) {
        this.put(treeConfig.getIdKey(), id);
        return this;
    }

    @Override
    public T getParentId() {
        //noinspection unchecked
        return (T) this.get(treeConfig.getParentIdKey());
    }

    public Tree<T> setParentId(T parentId) {
        this.put(treeConfig.getParentIdKey(), parentId);
        return this;
    }

    @Override
    public CharSequence getName() {
        return (CharSequence) this.get(treeConfig.getNameKey());
    }

    public Tree<T> setName(CharSequence name) {
        this.put(treeConfig.getNameKey(), name);
        return this;
    }

    @Override
    public Comparable<?> getWeight() {
        return (Comparable<?>) this.get(treeConfig.getWeightKey());
    }

    public Tree<T> setWeight(Comparable<?> weight) {
        this.put(treeConfig.getWeightKey(), weight);
        return this;
    }

    @Override
    public List<Tree<T>> getChildren() {
        //noinspection unchecked
        return (List<Tree<T>>) this.get(treeConfig.getChildrenKey());
    }

    public void setChildren(List<Tree<T>> children) {
        this.put(treeConfig.getChildrenKey(), children);
    }

    /**
     * 扩展属性
     *
     * @param key   键
     * @param value 扩展值
     */
    public void putExtra(String key, Object value) {
        Assert.notNull(key, "Key must be not empty !");
        this.put(key, value);
    }

}