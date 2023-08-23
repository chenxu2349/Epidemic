package com.example.epidemic.controller;

import com.example.epidemic.pojo.chain;
import com.example.epidemic.pojo.patient;
import com.example.epidemic.pojo.patientTrack;
import com.example.epidemic.service.inferenceService;
import com.example.epidemic.service.relevanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.*;

@Controller
public class relevanceController {

    @Autowired
    private inferenceService inference_service;

    @Autowired
    private relevanceService relevance_service;

    @GetMapping("/relevance")
    @ResponseBody
    public Map<Integer, List<patient>> relevanceAnalyse(@PathParam("date") String date, @PathParam("areaCode") String areaCode) throws ParseException {

        // 该区域关联的全部传播链，map存某个id对应的感染者列表
        Map<Integer, List<patient>> chainList = new HashMap<>();

        List<patient> patients = new ArrayList<>();
        for (patient p : inference_service.getPatients(date, areaCode)) patients.add(p);

        Map<Integer, List<patientTrack>> ptMap = new HashMap<>();   // 这个人去过哪些地方
        Map<Integer, Integer> chainIdMap = new HashMap<>(); // 某个患者在哪个传播链id上
        Set<Integer> hasInChain = new HashSet<>();  // 已经在传播链中的人

        for (patient p : patients) {
            int pId = p.getPatientId();
            List<patientTrack> pTrack = relevance_service.getPatientTrackById(pId);
            ptMap.put(pId, pTrack);
        }

        int chainId = 0;
        for (int i = 0; i < patients.size(); i++) {
            int iPid = patients.get(i).getPatientId();
            List<patientTrack> iTracks = ptMap.get(iPid);
            if (!hasInChain.contains(iPid)) {
                // 先向后找，有没有已经上链的且和i有时空交集的
                boolean basicCheck = false;
                for (int j = i + 1; j < patients.size(); j++) {
                    patient pi = patients.get(i);
                    patient pj = patients.get(j);
                    if (relevance_service.checkTwoPerson(pi, pj) && hasInChain.contains(pj.getPatientId())) {
                        basicCheck = true;
                        List<patient> list = chainList.get(chainIdMap.get(pj.getPatientId()));
                        list.add(pi);
                        chainIdMap.put(pi.getPatientId(), chainIdMap.get(pj.getPatientId()));
                        hasInChain.add(pi.getPatientId());
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
                        chainIdMap.put(pi.getPatientId(), chainId);
                        hasInChain.add(pj.getPatientId());
                        break;
                    }
                }
                c.setChainPatients(list);
            } else {
                int cId = chainIdMap.get(iPid);
                for (int j = i + 1; j < patients.size(); j++) {
                    patient pi = patients.get(i);
                    patient pj = patients.get(j);
                    if (relevance_service.checkTwoPerson(pi, pj) && !hasInChain.contains(pj.getPatientId())) {
                        List<patient> list = chainList.get(cId);
                        list.add(pj);
                        chainIdMap.put(pj.getPatientId(), chainIdMap.get(cId));
                        hasInChain.add(pj.getPatientId());
                        chainList.put(cId, list);
                    }
                }
            }
        }

        return chainList;
    }
}
