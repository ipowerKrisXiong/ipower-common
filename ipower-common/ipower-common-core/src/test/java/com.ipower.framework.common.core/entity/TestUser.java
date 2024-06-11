package com.ipower.framework.common.core.entity;

import com.ipower.framework.common.core.annotation.TestAnnotation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单元测试用类
 *
 * @author diablo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestUser {

    @TestAnnotation(value = "b", name = "李-parent", enable = true)
    private String name;

    @TestAnnotation(value = "c", name = "王-parent", enable = true)
    private Integer age;

    private Integer studentId;

    private Long id;

    public TestUser(String name) {
        this.name = name;
    }

    public TestUser(String name, Integer age, Integer studentId) {
        this.name = name;
        this.age = age;
        this.studentId = studentId;
    }
}
