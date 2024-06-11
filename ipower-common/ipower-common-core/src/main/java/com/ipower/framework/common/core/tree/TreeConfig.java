package com.ipower.framework.common.core.tree;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 树配置属性相关
 *
 * @author ldz
 */
@Getter
public class TreeConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 默认属性配置对象
     */
    public static TreeConfig DEFAULT_CONFIG = new TreeConfig();

    /**
     * 获取ID对应的名称
     */
    private String idKey = "id";

    /**
     * 获取父节点ID对应的名称
     */
    private String parentIdKey = "parentId";

    /**
     * 获取权重对应的名称
     */
    private String weightKey = "weight";

    /**
     * 获取节点名对应的名称
     */
    private String nameKey = "name";

    /**
     * 获取子点对应的名称
     */
    private String childrenKey = "children";

    /**
     * 设置ID对应的名称
     *
     * @param idKey ID对应的名称
     * @return this
     */
    public TreeConfig setIdKey(String idKey) {
        this.idKey = idKey;
        return this;
    }

    /**
     * 设置权重对应的名称
     *
     * @param weightKey 权重对应的名称
     * @return this
     */
    public TreeConfig setWeightKey(String weightKey) {
        this.weightKey = weightKey;
        return this;
    }

    /**
     * 设置节点名对应的名称
     *
     * @param nameKey 节点名对应的名称
     * @return this
     */
    public TreeConfig setNameKey(String nameKey) {
        this.nameKey = nameKey;
        return this;
    }

    /**
     * 设置子点对应的名称
     *
     * @param childrenKey 子点对应的名称
     * @return this
     */
    public TreeConfig setChildrenKey(String childrenKey) {
        this.childrenKey = childrenKey;
        return this;
    }


    /**
     * 设置父点对应的名称
     *
     * @param parentIdKey 父点对应的名称
     * @return this
     */
    public TreeConfig setParentIdKey(String parentIdKey) {
        this.parentIdKey = parentIdKey;
        return this;
    }

}
