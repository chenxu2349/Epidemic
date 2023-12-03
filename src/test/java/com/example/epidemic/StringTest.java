package com.example.epidemic;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StringTest {

    @Test
    public void stringTest() {
        String s1 = "1010";
        String s2 = "101010";
        System.out.println(s2.startsWith(s1));
    }
}
