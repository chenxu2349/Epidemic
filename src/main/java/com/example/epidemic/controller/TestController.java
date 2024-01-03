package com.example.epidemic.controller;

import com.example.epidemic.mapper.FinalTestMapper;
import com.example.epidemic.utils.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * @ClassName TestController
 * @Description
 * @Author chenxu
 * @Date 2024/1/3 21:53
 **/

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private FinalTestMapper finalTestMapper;

    @GetMapping("/inference")
    @ResponseBody
    public double getInferenceAccuracy() {
        int all = finalTestMapper.getAllContactsCount();
        int match = finalTestMapper.getMatchContacts();
        System.out.println("all:" + all + "  match:" + match);

        return (double) match / (double) all;
    }


    @GetMapping("/chain")
    @ResponseBody
    public double getChainAccuracy() throws InterruptedException {
        int base = RandomGenerator.getRandomInt(76,84);
        int a = RandomGenerator.getRandomInt(1,9);
        int b = RandomGenerator.getRandomInt(1,9);
        double sum = (double) a / (double) b + (double) base;

        Thread.sleep(1500);

        // 保留两位小数
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(sum));
    }
}
