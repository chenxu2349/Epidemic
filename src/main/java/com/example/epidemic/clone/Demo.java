package com.example.epidemic.clone;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;

/**
 * @ClassName Demo
 * @Description
 * @Author chenxu
 * @Date 2023/11/13 21:47
 **/
public class Demo {
    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        Address address = new Address("Beijing");
        Person person = new Person("Tom", 23, address);

        // shallow copy
        Person personClone1 = (Person) person.clone();
        System.out.println(person == personClone1); // false
        System.out.println(person.getAddress() == personClone1.getAddress()); // true;

        // deep copy
        Person personClone2 = (Person) deepCopy(person);
        System.out.println(person == personClone2); // false
        System.out.println(person.getAddress() == personClone2.getAddress()); // false
    }

    public static Object deepCopy(Object obj) throws IOException, ClassNotFoundException {
        // 序列化再反序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
}
