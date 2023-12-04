package com.example.epidemic;

import com.example.epidemic.utils.IdCardUtil;
import com.example.epidemic.utils.TimeUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class EasyTest {

    @Test
    public void idCardTest() {
        System.out.println("一组随机身份证号:");
        System.out.println(IdCardUtil.generateID());
        System.out.println(IdCardUtil.generateID());
        System.out.println(IdCardUtil.generateID());
        System.out.println(IdCardUtil.generateID());
        System.out.println(IdCardUtil.generateID());
        System.out.println(IdCardUtil.generateID());
        System.out.println(IdCardUtil.generateID());
        System.out.println(IdCardUtil.generateID());
    }

    @Test
    public void enlargeTimeSpanTest() throws ParseException {
        String start = "2023-07-01 02:43:00";
        String end = "2023-07-01 14:28:00";

        start = TimeUtil.enlargeStart(start);
        end = TimeUtil.enlargeEnd(end);

        System.out.println(start);
        System.out.println(end);
    }
}
