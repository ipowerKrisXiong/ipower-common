package com.ipower.framework.common.core.entity;

/**
 * 单元测试用类
 *
 * @author wutao
 */
public class Person implements Comparable<Person> {

    private Integer age;
    private String name;

    public Person(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {

        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }


    @Override
    public int compareTo(Person person) {
        //根据age大小来比较
        return age.compareTo(person.age);
        //return this.name - person.name;
    }
}
