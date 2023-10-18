package com.example.epidemic.controller;

import com.example.epidemic.pojo.*;
import com.example.epidemic.service.InferenceService;
import com.example.epidemic.service.RelevanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.*;

@Controller
public class RelevanceController {

    @Autowired
    private InferenceService inference_service;

    @Autowired
    private RelevanceService relevance_service;

    int count = 0;

    @GetMapping("/relevanceAll")
    @ResponseBody
    public void relevanceAnalyse() throws ParseException {

        if (count >= 1) return;

        // 该区域关联的全部传播链，map存某个id对应的感染者列表
        Map<Integer, List<Patient>> chainList = new HashMap<>();

        // 全部区域的患者
        String[] datePool = new String[]{"2023-07-28", "2023-07-29", "2023-07-30"};
        String[] areaPool = new String[]{"10001", "10002", "10003", "10004"};
        List<Patient> patients = new ArrayList<>();
        for (String date : datePool) {
            for (String areaCode : areaPool) {
                for (Patient p : inference_service.getPatients(date, areaCode)) patients.add(p);
            }
        }

        Map<Integer, List<PatientTrack>> ptMap = new HashMap<>();   // 这个人去过哪些地方
        Map<Integer, Integer> chainIdMap = new HashMap<>(); // 某个患者在哪个传播链id上
        Set<Integer> hasInChain = new HashSet<>();  // 已经在传播链中的人

        // 填充每个人的行动轨迹
        for (Patient p : patients) {
            int pId = p.getPatientId();
            List<PatientTrack> pTrack = relevance_service.getPatientTrackById(pId);
            ptMap.put(pId, pTrack);
        }

        int chainId = 0;
        for (int i = 0; i < patients.size(); i++) {
            int iPid = patients.get(i).getPatientId();
            if (!hasInChain.contains(iPid)) {
                // 先向后找，有没有已经上链的且和i有时空交集的
                boolean basicCheck = false;
                for (int j = i + 1; j < patients.size(); j++) {
                    Patient pi = patients.get(i);
                    Patient pj = patients.get(j);
                    if (relevance_service.checkTwoPerson(pi, pj) && hasInChain.contains(pj.getPatientId())) {
                        basicCheck = true;
                        int cId = chainIdMap.get(pj.getPatientId());
                        List<Patient> list = chainList.get(cId);
                        list.add(pi);
                        chainList.put(cId, list);
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

                Chain c = new Chain();
                c.setChainId(chainId);
                List<Patient> list = new LinkedList<>();
                list.add(patients.get(i));
                chainIdMap.put(iPid, chainId);
                hasInChain.add(iPid);
                // 向后匹配
                for (int j = i + 1; j < patients.size(); j++) {
                    Patient pi = patients.get(i);
                    Patient pj = patients.get(j);
                    if (relevance_service.checkTwoPerson(pi, pj)) {
                        // 存在时空交集，患者j上链
                        list.add(pj);
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
                    Patient pi = patients.get(i);
                    Patient pj = patients.get(j);
                    if (relevance_service.checkTwoPerson(pi, pj) && !hasInChain.contains(pj.getPatientId())) {
                        // 左边的i已经在链上，吸纳新成员j上链
                        List<Patient> list = chainList.get(cId);
                        list.add(pj);
                        chainIdMap.put(pj.getPatientId(), cId);
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
    public List<RelevanceChainPairWithName> getRelevanceChain(@PathParam("batch") int batch, @PathParam("areaCode") String areaCode) {
        List<RelevanceChainPairWithName> ans = new LinkedList<>();
        for (RelevanceChainPair p : relevance_service.getRelevanceChainPairs(batch, areaCode)) {
            RelevanceChainPairWithName pN = new RelevanceChainPairWithName();
            pN.setCorrelationChainId(p.getCorrelationChainId());
            pN.setCorrelationChainCode(p.getCorrelationChainCode());
            pN.setPatientId1(p.getPatientId1());
            pN.setPatientId2(p.getPatientId2());
            Patient p1 = inference_service.getPatient(p.getPatientId1());
            Patient p2 = inference_service.getPatient(p.getPatientId2());
            pN.setPatientName1(p1.getPatientName());
            pN.setPatientName2(p2.getPatientName());
            ans.add(pN);
        }
        return ans;
    }

    // 从认定的潜在患者中(>=60%)筛选接触了多个感染者的重点对象
    @GetMapping("/keyPersonFilter")
    @ResponseBody
    public List<Contact> findKeyPerson(@PathParam("date") String date,
                                       @PathParam("batch") int batch,
                                       @PathParam("areaCode") String areaCode) throws ParseException {

        // 经推理认定的潜在患者
        List<Contact> potentialPatients = relevance_service.getPotentialPatient(batch, areaCode);
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
        for (Patient p : inference_service.getPatients(date, areaCode)) patients.add(p);
        for (Patient p : patients) {
            int pId = p.getPatientId();
            List<PatientTrack> patientTracks = relevance_service.getPatientTrackById(pId);
            ptMap.put(pId, patientTracks);
        }

        // 筛查重点对象
        List<Contact> keyPersons = new LinkedList<>();
        for (Contact c : potentialPatients) {
            int count = 0;
            for (Patient p : patients) {
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
    public List<Contact> getPotentialPatients(@PathParam("patient_id") int patient_id, @PathParam("batch")int batch) {

        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        List<Contact> contacts = new LinkedList<>();
        for (String areaCode : areaPool) {
            for (Contact c : inference_service.getContacts(patient_id, areaCode, batch)) contacts.add(c);
        }
        List<Contact> ans = new LinkedList<>();

        for (Contact c : contacts) {
            if (c.getPotentialPatient() == 1) ans.add(c);
        }

        return ans;
    }
}
