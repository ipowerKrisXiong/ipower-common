package com.ipower.framework.common.core.entity;

import lombok.Data;

/**
 * 单元测试用类
 *
 * @author wutao
 */
@Data
public class TestPerson implements Comparable<TestPerson> {

    private Integer age;
    private String name;

    public TestPerson(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public int compareTo(TestPerson testPerson) {
        //根据age大小来比较
        return age.compareTo(testPerson.age);
        //return this.name - person.name;
    }
}
