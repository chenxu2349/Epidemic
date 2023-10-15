package com.example.epidemic.controller;

import com.example.epidemic.pojo.patient;
import com.example.epidemic.service.inferenceService;
import com.example.epidemic.service.relevanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ClassName DataFormController
 * @Description
 * @Author chenxu
 * @Date 2023/10/13 10:46
 **/

@Controller
public class DataFormController {

    @Autowired
    private inferenceService iService;

    @Autowired
    private relevanceService rService;

    @PostMapping("/patient")
    public void insertPatient(@RequestBody patient p) {}
}
