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
import java.util.*;

/**
 * @ClassName BasicInfomation
 * @Description
 * @Author chenxu
 * @Date 2023/11/28 17:08
 **/
public class BasicInformation {
    private static Map<String, Integer> areaCodeStart;
    private static Map<String, Integer> areaCodeEnd;
    private static Map<String, Set<String>> cityMap;

    // 静态代码块，加载类时执行
    static {
        // 初始化区域编码映射关系
        areaCodeStart = new HashMap<>();
        areaCodeStart.put("410105", 1);
        areaCodeStart.put("410102", 31);
        areaCodeStart.put("410103", 61);
        areaCodeStart.put("410106", 91);
        areaCodeStart.put("410108", 121);
        areaCodeStart.put("320281", 151);
        areaCodeStart.put("340104", 181);
        areaCodeStart.put("340111", 187);
        areaCodeStart.put("340103", 190);
        areaCodeStart.put("340181", 195);
        // beijing
        areaCodeStart.put("110105", 202);
        areaCodeStart.put("110106", 232);
        areaCodeStart.put("110108", 262);
        areaCodeStart.put("110112", 292);
        areaCodeStart.put("110114", 322);
        // shanghai
        areaCodeStart.put("310101", 352);
        areaCodeStart.put("310104", 382);
        areaCodeStart.put("310105", 412);
        areaCodeStart.put("310106", 442);
        areaCodeStart.put("310107", 472);

        // *****************************************

        areaCodeEnd = new HashMap<>();
        areaCodeEnd.put("410105", 30);
        areaCodeEnd.put("410102", 60);
        areaCodeEnd.put("410103", 90);
        areaCodeEnd.put("410106", 120);
        areaCodeEnd.put("410108", 150);
        areaCodeEnd.put("320281", 180);
        areaCodeEnd.put("340104", 186);
        areaCodeEnd.put("340111", 189);
        areaCodeEnd.put("340103", 194);
        areaCodeEnd.put("340181", 201);
        // beijing
        areaCodeEnd.put("110105", 231);
        areaCodeEnd.put("110106", 261);
        areaCodeEnd.put("110108", 291);
        areaCodeEnd.put("110112", 321);
        areaCodeEnd.put("110114", 351);
        // shanghai
        areaCodeEnd.put("310101", 381);
        areaCodeEnd.put("310104", 411);
        areaCodeEnd.put("310105", 441);
        areaCodeEnd.put("310106", 471);
        areaCodeEnd.put("310107", 501);
    }

    static {
        cityMap = new HashMap<>();

        Set<String> zhengzhouSet = new HashSet<>();
        zhengzhouSet.add("410102");
        zhengzhouSet.add("410103");
        zhengzhouSet.add("410105");
        zhengzhouSet.add("410106");
        zhengzhouSet.add("410108");
        cityMap.put("410100", zhengzhouSet);

        Set<String> wuxiSet = new HashSet<>();
        wuxiSet.add("320281");
        cityMap.put("320200", wuxiSet);

        Set<String> hefeiSet = new HashSet<>();
        hefeiSet.add("340103");
        hefeiSet.add("340104");
        hefeiSet.add("340111");
        hefeiSet.add("340181");
        cityMap.put("340100", hefeiSet);

        Set<String> beijingSet = new HashSet<>();
        beijingSet.add("110105");
        beijingSet.add("110106");
        beijingSet.add("110108");
        beijingSet.add("110112");
        beijingSet.add("110114");
        cityMap.put("110000", beijingSet);

        Set<String> shanghaiSet = new HashSet<>();
        shanghaiSet.add("310101");
        shanghaiSet.add("310104");
        shanghaiSet.add("310105");
        shanghaiSet.add("310106");
        shanghaiSet.add("310107");
        cityMap.put("310000", shanghaiSet);
    }

    public static int getAreaCodeStart(String areaCode) {
        return areaCodeStart.get(areaCode);
    }

    public static int getAreaCodeEnd(String areaCode) {
        return areaCodeEnd.get(areaCode);
    }

    public static Set<String> getCityAreas(String cityCode) {
        return cityMap.get(cityCode);
    }
}
