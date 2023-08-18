package com.example.epidemic.controller;

import com.example.epidemic.mapper.testMapper;
import com.example.epidemic.pojo.contact;
import com.example.epidemic.pojo.patient;
import com.example.epidemic.service.inferenceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

@Controller
public class inferenceController {

    @Autowired
    private inferenceService service1;

    @GetMapping("/getPatientsByDate")
    @ResponseBody
    public List<patient> getPatients(@PathParam("date") String date) {
        List<patient> patients = service1.getPatients(date);
        if (patients.size() == 0) return null;
        else return patients;
    }

    @GetMapping("/findContacts")
    @ResponseBody
    public List<contact> getContacts(@PathParam("patient_id") int patient_id,
                                     @PathParam("area_code")String area_code,
                                     @PathParam("batch")int batch) {
        List<contact> contacts = service1.getContacts(patient_id, area_code, batch);
        if (contacts.size() == 0) return null;
        else return contacts;
    }

    @GetMapping("/infer")
    @ResponseBody
    public List<contact> inference(@PathParam("patient_id") int patient_id,
                          @PathParam("area_code")String area_code,
                          @PathParam("batch")int batch) throws ParseException {
        patient p = service1.getPatients(patient_id);
        List<contact> contacts = service1.getContacts(patient_id, area_code, batch);
        if (contacts.size() == 0) return null;
        else return service1.inference(p, contacts);
    }

    // 统计各区域患者和潜在患者数量
}
