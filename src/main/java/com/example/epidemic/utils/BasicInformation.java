package com.example.epidemic.utils;

import com.example.epidemic.controller.InferenceController;
import com.example.epidemic.mapper.UtilsMapper;
import com.example.epidemic.service.InferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BasicInfomation
 * @Description
 * @Author chenxu
 * @Date 2023/11/28 17:08
 **/
public class BasicInformation {
    @Autowired
    private UtilsMapper utilsMapper;
    // 全部区域
    private List<String> areaCodePool;
    // 全部日期
    private List<String> datePool;
    private static Map<String, Integer> areaCodeStart;
    private static Map<String, Integer> areaCodeEnd;

    static {
        // 初始化区域编码映射关系
        areaCodeStart = new HashMap<>();
        areaCodeStart.put("101010", 1);
        areaCodeStart.put("101011", 31);
        areaCodeStart.put("101012", 61);
        areaCodeStart.put("101013", 91);
        areaCodeStart.put("101014", 121);
        areaCodeStart.put("111010", 151);
        areaCodeStart.put("121010", 181);
        areaCodeStart.put("121011", 187);
        areaCodeStart.put("121012", 190);
        areaCodeStart.put("121013", 195);
        areaCodeEnd = new HashMap<>();
        areaCodeEnd.put("101010", 30);
        areaCodeEnd.put("101011", 60);
        areaCodeEnd.put("101012", 90);
        areaCodeEnd.put("101013", 120);
        areaCodeEnd.put("101014", 150);
        areaCodeEnd.put("111010", 180);
        areaCodeEnd.put("121010", 186);
        areaCodeEnd.put("121011", 189);
        areaCodeEnd.put("121012", 194);
        areaCodeEnd.put("121013", 201);
    }

    public BasicInformation() {
        areaCodePool = utilsMapper.getAllAreaCodes();
        datePool = utilsMapper.getAllDates();

    }

    public List<String> getAreaCodePool() {
        return areaCodePool;
    }

    public List<String> getDatePool() {
        return datePool;
    }

    public static int getAreaCodeStart(String areaCode) {
        return areaCodeStart.get(areaCode);
    }

    public static int getAreaCodeEnd(String areaCode) {
        return areaCodeEnd.get(areaCode);
    }
}
