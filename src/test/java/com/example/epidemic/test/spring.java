package com.example.epidemic.test;


import com.example.epidemic.service.InferenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class spring {

    @Autowired
    private InferenceService service1;

    @Test
    public void print() {
        System.out.println("Hello SpringTest...");
    }
}
