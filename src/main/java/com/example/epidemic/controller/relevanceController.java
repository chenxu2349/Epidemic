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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class relevanceController {

    @Autowired
    private inferenceService inference_service;

    @Autowired
    private relevanceService relevance_service;

    @GetMapping("/relevance")
    @ResponseBody
    public chain relevanceAnalyse(@PathParam("date") String date, @PathParam("areaCode") String areaCode) {

        chain infectChain = new chain();

        List<patient> patients = new ArrayList<>();
        for (patient p : inference_service.getPatients(date, areaCode)) patients.add(p);

        Map<Integer, List<patientTrack>> ptMap = new HashMap<>();   // 这个人去过哪些地方
        Map<Integer, List<patient>> areaMap = new HashMap<>();  // 这个地方哪些人来过
        for (patient p : patients) {
            int pId = p.getPatientId();
            List<patientTrack> pTrack = relevance_service.getPatientTrackById(pId);
            ptMap.put(pId, pTrack);
        }


        return infectChain;
    }
}
