package com.ipower.framework.common.core.entity;

import com.ipower.framework.common.core.annotation.TestAnnotation;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单元测试用类
 *
 * @author diablo
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Demo extends TestUser {

    @TestAnnotation(value = "a", name = "张", enable = true)
    private String code;

    @TestAnnotation(value = "b", name = "李", enable = true)
    private String name;

    private Integer age;

    private Integer studentId;
}
