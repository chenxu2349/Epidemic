package com.example.epidemic.controller;

import com.example.epidemic.dto.RelevanceChainPairDTO;
import com.example.epidemic.mapper.UtilsMapper;
import com.example.epidemic.pojo.*;
import com.example.epidemic.service.InferenceService;
import com.example.epidemic.service.RelevanceService;
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

//        relevance_service.clearChainInfo();
        ThreadPoolExecutor threadPool = ThreadPoolFactory.getThreadPool();

        for (String areaCode : areaCodePool) {
            for (String date : datePool) {
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            relevance_service.relevanceByDateAndAreaCode(date, areaCode);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Thread.sleep(200);
            }
        }
        System.out.println("All computation complete...");
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

    // 从认定的潜在患者中(>=60%)筛选接触了多个感染者的重点对象
    @GetMapping("/keyPersonFilter")
    @ResponseBody
    public List<Contact> findKeyPerson(@PathParam("date") String date,
                                       @PathParam("areaCode") String areaCode) throws ParseException {

        long start = System.currentTimeMillis();

        // 经推理认定的潜在患者
        List<Contact> potentialPatients = relevance_service.getPotentialPatient(date, areaCode);
        Map<Integer, List<ContactTrack>> ctMap = new HashMap<>();
        for (Contact c : potentialPatients) {
            int cId = c.getContactId();
            List<ContactTrack> contactTracks = relevance_service.getContactTrackById(cId);
            ctMap.put(cId, contactTracks);
        }

        // 全部区域的患者
        List<Patient> patients = new ArrayList<>();
        Map<Integer, List<PatientTrack>> ptMap = new HashMap<>();   // 这个人去过哪些地方
        // String[] areaPool = new String[]{"10001","10002","10003","10004"};
        for (Patient p : inference_service.getPatientsByDate(date, areaCode)) patients.add(p);
        for (Patient p : patients) {
            int pId = p.getPatientId();
            List<PatientTrack> patientTracks = relevance_service.getPatientTrackById(pId);
            ptMap.put(pId, patientTracks);
        }

        // 筛查重点对象
        List<Contact> keyPersons = new LinkedList<>();
        // 潜在患者分片
        List<List<Contact>> lists = ListUtils.partition(potentialPatients, 10);
        // 创建线程池
        ThreadPoolExecutor threadPool = ThreadPoolFactory.getThreadPool();
        for (List<Contact> list : lists) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    for (Contact c : list) {
                        int count = 0;
                        for (Patient p : patients) {
                            try {
                                if (relevance_service.checkTwoPerson(p, c)) {
                                    count++;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (count >= 2) {
                                keyPersons.add(c);
                                break;
                            }
                        }
                    }
                }
            });
        }

        threadPool.shutdown();

        long end = System.currentTimeMillis();
        System.out.println("keyPersonFilter执行用时：" + (end - start) + "ms");
        return keyPersons;
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
