package com.example.epidemic.controller;

import com.example.epidemic.mapper.TestMapper;
import com.example.epidemic.pojo.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @Autowired
    private TestMapper mp1;

    @GetMapping("/testMybatis")
    @ResponseBody
    public String mybatisDemo(@RequestParam(value = "id", required = true) int id) {
        Patient p1 = mp1.queryPatientById(id);
        System.out.println(p1.toString());
        return p1.toString();
    }
}
