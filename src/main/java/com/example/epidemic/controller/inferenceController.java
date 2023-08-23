package com.example.epidemic.controller;

import com.example.epidemic.pojo.contact;
import com.example.epidemic.pojo.patient;
import com.example.epidemic.pojo.statistics;
import com.example.epidemic.service.inferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

@Controller
public class inferenceController {

    @Autowired
    private inferenceService service1;

    // 查看某一天的患者
    @GetMapping("/getPatientsByDate")
    @ResponseBody
    public List<patient> getPatients(@PathParam("date") String date, @PathParam("areaCode") String areaCode) {
        List<patient> patients = service1.getPatients(date, areaCode);
        if (patients.size() == 0) return null;
        else return patients;
    }

    // 查看某个患者的接触者
    @GetMapping("/findContacts")
    @ResponseBody
    public List<contact> getContacts(@PathParam("patient_id") int patient_id,
                                     @PathParam("area_code")String area_code,
                                     @PathParam("batch")int batch) {
        List<contact> contacts = service1.getContacts(patient_id, area_code, batch);
        if (contacts.size() == 0) return null;
        else return contacts;
    }

    // 推理运算
    @GetMapping("/infer")
    @ResponseBody
    public List<contact> inference(@PathParam("patient_id") int patient_id,
                          @PathParam("area_code")String area_code,
                          @PathParam("batch")int batch) throws ParseException {
        patient p = service1.getPatient(patient_id);
        List<contact> contacts = service1.getContacts(patient_id, area_code, batch);
        if (contacts.size() == 0) return null;
        else return service1.inference(p, contacts);
    }

    // 统计各区域患者和潜在患者数量
    @GetMapping("/countPatientAndPotential")
    @ResponseBody
    public List<statistics> areaCount() {
        String[] areaPool = new String[]{"10001", "10002", "10003", "10004"};
        List<statistics> ans = new LinkedList<>();
        for (String areaCode : areaPool) {
            statistics s = new statistics();
            s.setAreaCode(areaCode);
            s.setPatient(service1.countPatient(areaCode));
            s.setPotential_patient(service1.countPotentialPatient(areaCode));
            ans.add(s);
        }
        return ans;
    }
}
