package com.example.epidemic.controller;

import com.example.epidemic.dto.RelevanceChainPairDTO;
import com.example.epidemic.mapper.UtilsMapper;
import com.example.epidemic.pojo.*;
import com.example.epidemic.service.InferenceService;
import com.example.epidemic.service.RelevanceService;
import com.example.epidemic.utils.BasicInformation;
import com.example.epidemic.utils.ThreadPoolFactory;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

@Controller
public class RelevanceController {

    @Autowired
    private InferenceService inference_service;
    @Autowired
    private RelevanceService relevance_service;
    @Autowired
    private UtilsMapper utilsMapper;
    private static List<String> areaCodePool = null;
    private static List<String> datePool = null;


//    int count = 0;


    // TODO 耗时过长
    // 关联分析
    @GetMapping("/relevanceAll")
    @ResponseBody
    public String relevanceAll() throws InterruptedException {
        if (areaCodePool == null) areaCodePool = utilsMapper.getAllAreaCodes();
        if (datePool == null) datePool = utilsMapper.getAllDates();

        relevance_service.clearChainInfo();
        ThreadPoolExecutor threadPool = ThreadPoolFactory.getThreadPool();

        for (String areaCode : areaCodePool) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(areaCode + "sub task started...");
                    for (String date : datePool) {
                        try {
                            relevance_service.relevanceByDateAndAreaCode(date, areaCode);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(areaCode + "sub task end...");
                }
            });
        }

        threadPool.shutdown();
        return "success";
    }

    // 查询传播链
    @GetMapping("getRelevanceChain")
    @ResponseBody
    public List<RelevanceChainPairDTO> getRelevanceChain(@PathParam("date") String date, @PathParam("areaCode") String areaCode) {

        long start = System.currentTimeMillis();

        List<RelevanceChainPairDTO> ans = new LinkedList<>();
        for (RelevanceChainPair p : relevance_service.getRelevanceChainPairs(date, areaCode)) {
            RelevanceChainPairDTO pN = new RelevanceChainPairDTO();
            pN.setCorrelationChainId(p.getCorrelationChainId());
            pN.setCorrelationChainCode(p.getCorrelationChainCode());
            pN.setPatientId1(p.getPatientId1());
            pN.setPatientId2(p.getPatientId2());
            Patient p1 = inference_service.getPatientById(p.getPatientId1());
            Patient p2 = inference_service.getPatientById(p.getPatientId2());
            pN.setPatientName1(p1.getPatientName());
            pN.setPatientName2(p2.getPatientName());
            pN.setTel1(p1.getPatientTel());
            pN.setTel2(p2.getPatientTel());
            pN.setAddress1(p1.getPatientAddress());
            pN.setAddress2(p2.getPatientAddress());
            ans.add(pN);
        }

        long end = System.currentTimeMillis();
        System.out.println("getRelevanceChain执行用时：" + (end - start) + "ms");
        return ans;
    }

    @GetMapping("/getCityRelevanceChain")
    @ResponseBody
    public List<RelevanceChainPairDTO> getCityRelevanceChain(@PathParam("date") String date, @PathParam("cityCode") String cityCode) {
        Set<String> areas = BasicInformation.getCityAreas(cityCode);
        List<RelevanceChainPairDTO> ans = new LinkedList<>();
        for (String areaCode : areas) {
            for (RelevanceChainPair p : relevance_service.getRelevanceChainPairs(date, areaCode)) {
                RelevanceChainPairDTO pN = new RelevanceChainPairDTO();
                pN.setCorrelationChainId(p.getCorrelationChainId());
                pN.setCorrelationChainCode(p.getCorrelationChainCode());
                pN.setPatientId1(p.getPatientId1());
                pN.setPatientId2(p.getPatientId2());
                Patient p1 = inference_service.getPatientById(p.getPatientId1());
                Patient p2 = inference_service.getPatientById(p.getPatientId2());
                pN.setPatientName1(p1.getPatientName());
                pN.setPatientName2(p2.getPatientName());
                pN.setTel1(p1.getPatientTel());
                pN.setTel2(p2.getPatientTel());
                pN.setAddress1(p1.getPatientAddress());
                pN.setAddress2(p2.getPatientAddress());
                ans.add(pN);
            }
        }
        return ans;
    }


    // 从认定的潜在患者中(>=60%)筛选接触了多个感染者的重点对象
    @GetMapping("/keyPersonFilter")
    @ResponseBody
    public List<Contact> findKeyPerson(@PathParam("date") String date,
                                       @PathParam("areaCode") String areaCode) throws ParseException, InterruptedException {

        long start = System.currentTimeMillis();

        // 经推理认定的潜在患者
        List<Contact> potentialPatients = relevance_service.getPotentialPatient(date, areaCode);
        if (potentialPatients == null || potentialPatients.size() == 0) return null;
        // 该天该区域的全部患者
        List<Patient> patients = inference_service.getPatientsByDate(date, areaCode);

        List<Contact> keyPersons = relevance_service.getKeyPersons(potentialPatients, patients);

        long end = System.currentTimeMillis();
        System.out.println("keyPersonFilter执行用时：" + (end - start) + "ms");
        return keyPersons;
    }

    @GetMapping("/keyPersonFilterByCity")
    @ResponseBody
    public List<Contact> findKeyPersonByCity(@PathParam("date") String date,
                                             @PathParam("cityCode") String cityCode) throws ParseException, InterruptedException {

        long start = System.currentTimeMillis();
        Set<String> areas = BasicInformation.getCityAreas(cityCode);
        List<Contact> cityKeyPersons = new ArrayList<>();

        for (String areaCode : areas) {
            // 经推理认定的潜在患者
            List<Contact> potentialPatients = relevance_service.getPotentialPatient(date, areaCode);
            if (potentialPatients == null || potentialPatients.size() == 0) continue;
            // 该天该区域的全部患者
            List<Patient> patients = inference_service.getPatientsByDate(date, areaCode);
            List<Contact> keyPersons = relevance_service.getKeyPersons(potentialPatients, patients);
            for (Contact c : keyPersons) cityKeyPersons.add(c);
        }

        long end = System.currentTimeMillis();
        System.out.println("keyPersonFilterByCity执行用时：" + (end - start) + "ms");
        return cityKeyPersons;
    }

    // 查看某个患者的潜在患者
    @GetMapping("/getPotentialPatients")
    @ResponseBody
    public List<Contact> getPotentialPatients(@PathParam("patient_id") int patient_id, @PathParam("date") String date) {

        long start = System.currentTimeMillis();

//        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        Patient p = inference_service.getPatientById(patient_id);
        List<Contact> contacts = new LinkedList<>();
        if (areaCodePool == null) areaCodePool = utilsMapper.getAllAreaCodes();
        for (String areaCode : areaCodePool) {
            List<Contact> list = inference_service.getContacts(p, areaCode, date);
            if (list != null) {
                for (Contact c : list) contacts.add(c);
            }
        }
        List<Contact> ans = new LinkedList<>();

        for (Contact c : contacts) {
            if (c.getPotentialPatient() == 1) ans.add(c);
        }

        long end = System.currentTimeMillis();
        System.out.println("getPotentialPatients执行用时：" + (end - start) + "ms");
        return ans;
    }
}
