package com.example.epidemic.clone;

import java.io.Serializable;

/**
 * @ClassName Person
 * @Description
 * @Author chenxu
 * @Date 2023/11/13 21:46
 **/
public class Person implements Cloneable, Serializable {
    private String name;
    private int age;
    private Address address;

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Address getAddress() {
        return address;
    }
}
