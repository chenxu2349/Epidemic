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
import java.util.List;

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
}
