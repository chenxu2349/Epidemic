package com.example.epidemic.controller;

import com.example.epidemic.mapper.testMapper;
import com.example.epidemic.pojo.contact;
import com.example.epidemic.pojo.patient;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.LinkedList;
import java.util.List;

@Controller
public class inferenceController {

    @Autowired
    private testMapper mp1;

    @GetMapping("/getPatientsByDate")
    @ResponseBody
    public List<patient> getPatients(@PathParam("date") String date) {
        List<patient> list = new LinkedList<>();
        for (patient p : mp1.queryPatientsByDate(date)) list.add(p);
        return list;
    }

    @GetMapping("/findContacts")
    @ResponseBody
    public List<contact> getContacts(@PathParam("patient_id") int patient_id,
                                     @PathParam("area_code")String area_code,
                                     @PathParam("batch")int batch) {
        List<contact> list = new LinkedList<>();
        for (contact c : mp1.queryContacts(patient_id, area_code, batch)) list.add(c);
        return list;
    }

    public void inference(int batch) {}
}
