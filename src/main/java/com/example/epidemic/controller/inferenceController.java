package com.example.epidemic.controller;

import com.example.epidemic.mapper.testMapper;
import com.example.epidemic.pojo.contact;
import com.example.epidemic.pojo.patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedList;
import java.util.List;

@Controller
public class inferenceController {

    @Autowired
    private testMapper mp1;

    @GetMapping("/getPatientsByDate")
    public List<patient> getPatients(String date) {
        List<patient> list = new LinkedList<>();
        for (patient p : mp1.queryPatientsByDate(date)) list.add(p);
        return list;
    }

    @GetMapping("/findContacts")
    public List<contact> getContacts(int patient_id, String area_code) {
        List<contact> list = new LinkedList<>();
        for (contact c : mp1.queryContacts(patient_id, area_code)) list.add(c);
        return list;
    }

    public void inference(int batch) {}
}
