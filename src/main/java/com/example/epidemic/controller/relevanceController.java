package com.example.epidemic.controller;

import com.example.epidemic.pojo.*;
import com.example.epidemic.service.inferenceService;
import com.example.epidemic.service.relevanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Stream;

@Controller
public class relevanceController {

    @Autowired
    private inferenceService inference_service;

    @Autowired
    private relevanceService relevance_service;

    int count = 0;

    @GetMapping("/relevanceAll")
    @ResponseBody
    public void relevanceAnalyse() throws ParseException {

        if (count >= 1) return;

        // 该区域关联的全部传播链，map存某个id对应的感染者列表
        Map<Integer, List<patient>> chainList = new HashMap<>();

        // 全部区域的患者
        String[] datePool = new String[]{"2023-07-28", "2023-07-29", "2023-07-30"};
        String[] areaPool = new String[]{"10001", "10002", "10003", "10004", "10005"};
        List<patient> patients = new ArrayList<>();
        for (String date : datePool) {
            for (String areaCode : areaPool) {
                for (patient p : inference_service.getPatients(date, areaCode)) patients.add(p);
            }
        }

        Map<Integer, List<patientTrack>> ptMap = new HashMap<>();   // 这个人去过哪些地方
        Map<Integer, Integer> chainIdMap = new HashMap<>(); // 某个患者在哪个传播链id上
        Set<Integer> hasInChain = new HashSet<>();  // 已经在传播链中的人

        // 填充每个人的行动轨迹
        for (patient p : patients) {
            int pId = p.getPatientId();
            List<patientTrack> pTrack = relevance_service.getPatientTrackById(pId);
            ptMap.put(pId, pTrack);
        }

        int chainId = 0;
        for (int i = 0; i < patients.size(); i++) {
            int iPid = patients.get(i).getPatientId();
            if (!hasInChain.contains(iPid)) {
                // 先向后找，有没有已经上链的且和i有时空交集的
                boolean basicCheck = false;
                for (int j = i + 1; j < patients.size(); j++) {
                    patient pi = patients.get(i);
                    patient pj = patients.get(j);
                    if (relevance_service.checkTwoPerson(pi, pj) && hasInChain.contains(pj.getPatientId())) {
                        basicCheck = true;
                        int cId = chainIdMap.get(pj.getPatientId());
                        List<patient> list = chainList.get(cId);
                        list.add(pi);
                        chainIdMap.put(pi.getPatientId(), cId);
                        hasInChain.add(pi.getPatientId());

                        // 记录pair
                        String areaCode1 = pi.getAreaCode();
                        relevance_service.setChainPair(cId, areaCode1, pi.getPatientId(), pj.getPatientId());
                        relevance_service.setChainPair(cId, areaCode1, pj.getPatientId(), pi.getPatientId());
                        break;
                    }
                }
                if (basicCheck) continue;

                chain c = new chain();
                c.setChainId(chainId);
                List<patient> list = new LinkedList<>();
                list.add(patients.get(i));
                chainIdMap.put(iPid, chainId);
                hasInChain.add(iPid);
                // 向后匹配
                for (int j = i + 1; j < patients.size(); j++) {
                    patient pi = patients.get(i);
                    patient pj = patients.get(j);
                    if (relevance_service.checkTwoPerson(pi, pj)) {
                        // 存在时空交集，患者j上链
                        list.add(pi);
                        chainIdMap.put(pj.getPatientId(), chainId);
                        hasInChain.add(pj.getPatientId());

                        // 记录pair
                        String areaCode1 = pi.getAreaCode();
                        relevance_service.setChainPair(chainId, areaCode1, pi.getPatientId(), pj.getPatientId());
                        relevance_service.setChainPair(chainId, areaCode1, pj.getPatientId(), pi.getPatientId());
                        break;
                    }
                }
                c.setChainPatients(list);
                chainList.put(chainId, list);
                chainId++;
            } else {
                int cId = chainIdMap.get(iPid);
                for (int j = i + 1; j < patients.size(); j++) {
                    patient pi = patients.get(i);
                    patient pj = patients.get(j);
                    if (relevance_service.checkTwoPerson(pi, pj) && !hasInChain.contains(pj.getPatientId())) {
                        // 左边的i已经在链上，吸纳新成员j上链
                        List<patient> list = chainList.get(cId);
                        list.add(pj);
                        chainIdMap.put(pj.getPatientId(), chainIdMap.get(cId));
                        hasInChain.add(pj.getPatientId());

                        // 记录pair
                        String areaCode1 = pi.getAreaCode();
                        relevance_service.setChainPair(cId, areaCode1, pi.getPatientId(), pj.getPatientId());
                        relevance_service.setChainPair(cId, areaCode1, pj.getPatientId(), pi.getPatientId());

                        chainList.put(cId, list);
                    }
                }
            }
        }

        count++;
    }

    @GetMapping("getRelevanceChain")
    @ResponseBody
    public List<relevanceChainPairWithName> getRelevanceChain(@PathParam("batch") int batch, @PathParam("areaCode") String areaCode) {
        List<relevanceChainPairWithName> ans = new LinkedList<>();
        for (relevanceChainPair p : relevance_service.getRelevanceChainPairs(batch, areaCode)) {
            relevanceChainPairWithName pN = new relevanceChainPairWithName();
            pN.setCorrelationChainId(p.getCorrelationChainId());
            pN.setCorrelationChainCode(p.getCorrelationChainCode());
            pN.setPatientId1(p.getPatientId1());
            pN.setPatientId2(p.getPatientId2());
            patient p1 = inference_service.getPatient(p.getPatientId1());
            patient p2 = inference_service.getPatient(p.getPatientId2());
            pN.setPatientName1(p1.getPatientName());
            pN.setPatientName2(p2.getPatientName());
            ans.add(pN);
        }
        return ans;
    }

    // 从认定的潜在患者中(>=60%)筛选接触了多个感染者的重点对象
    @GetMapping("/keyPersonFilter")
    @ResponseBody
    public List<contact> findKeyPerson(@PathParam("date") String date,
                                       @PathParam("batch") int batch,
                                       @PathParam("areaCode") String areaCode) throws ParseException {

        // 经推理认定的潜在患者
        List<contact> potentialPatients = relevance_service.getPotentialPatient(batch, areaCode);
        Map<Integer, List<contactTrack>> ctMap = new HashMap<>();
        for (contact c : potentialPatients) {
            int cId = c.getContactId();
            List<contactTrack> contactTracks = relevance_service.getContactTrackById(cId);
            ctMap.put(cId, contactTracks);
        }

        // 全部区域的患者
        List<patient> patients = new ArrayList<>();
        Map<Integer, List<patientTrack>> ptMap = new HashMap<>();   // 这个人去过哪些地方
        // String[] areaPool = new String[]{"10001","10002","10003","10004"};
        for (patient p : inference_service.getPatients(date, areaCode)) patients.add(p);
        for (patient p : patients) {
            int pId = p.getPatientId();
            List<patientTrack> patientTracks = relevance_service.getPatientTrackById(pId);
            ptMap.put(pId, patientTracks);
        }

        // 筛查重点对象
        List<contact> keyPersons = new LinkedList<>();
        for (contact c : potentialPatients) {
            int count = 0;
            for (patient p : patients) {
                if (relevance_service.checkTwoPerson(p, c)) {
                    count++;
                }
                if (count >= 2) {
                    keyPersons.add(c);
                    break;
                }
            }
        }

        return keyPersons;
    }

    // 查看某个患者的潜在患者
    @GetMapping("/getPotentialPatients")
    @ResponseBody
    public List<contact> getPotentialPatients(@PathParam("patient_id") int patient_id, @PathParam("batch")int batch) {

        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        List<contact> contacts = new LinkedList<>();
        for (String areaCode : areaPool) {
            for (contact c : inference_service.getContacts(patient_id, areaCode, batch)) contacts.add(c);
        }
        List<contact> ans = new LinkedList<>();

        for (contact c : contacts) {
            if (c.getPotentialPatient() == 1) ans.add(c);
        }

        return ans;
    }
}
