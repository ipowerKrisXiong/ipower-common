package com.ipower.framework.common.core.constant;

/**
 * 数据状态常量
 *
 * @author diablo
 */
@SuppressWarnings("unused")
public class DataState {

    /**
     * 私有化构造函数，禁止实例化该类
     */
    private DataState() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 有效的
     */
    public static final int ENABLED = 1;

    /**
     * 无效的
     */
    public static final int DISABLED = 0;

}
