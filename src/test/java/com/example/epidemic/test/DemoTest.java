package com.example.epidemic.test;


import com.example.epidemic.controller.InferenceController;
import com.example.epidemic.mapper.UtilsMapper;
import com.example.epidemic.utils.BasicInformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest {
    @Autowired
    private UtilsMapper utilsMapper;

    @Autowired
    private InferenceController inferenceController;

    @Test
    public void print() {
        System.out.println("Hello SpringTest...");
    }

    @Test
    public void testUtilsMapper() {
        List<String> allDates = utilsMapper.getAllDates();
        allDates.forEach(System.out::println);
    }

    @Test
    public void testStaticFunc(){
        System.out.println(inferenceController);
    }
}
