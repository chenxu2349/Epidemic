package com.example.epidemic.utils;

import com.example.epidemic.mapper.UtilsMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GetAllDates {

    @Autowired
    static UtilsMapper utilsMapper;

    public static List<String> getAllDates() {
        return utilsMapper.getAllDates();
    }

}
