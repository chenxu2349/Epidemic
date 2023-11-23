package com.example.epidemic.test;


import com.example.epidemic.mapper.UtilsMapper;
import com.example.epidemic.service.InferenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest {

    @Autowired
    private InferenceService service1;

    @Autowired
    private UtilsMapper utilsMapper;

    @Test
    public void print() {
        System.out.println("Hello SpringTest...");
    }

    @Test
    public void testUtilsMapper() {
        List<String> allDates = utilsMapper.getAllDates();
        allDates.forEach(System.out::println);
    }
}
