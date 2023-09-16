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
    @GetMapping("/getPatients")
    @ResponseBody
    public List<patient> getPatients(@PathParam("date") String date, @PathParam("areaCode") String areaCode) {
        List<patient> patients = new LinkedList<>();
        for (patient p : service1.getPatients(date, areaCode)) patients.add(p);
        if (patients.size() == 0) return null;
        else return patients;
    }

    // 查看某一天全部区域的患者
    @GetMapping("/getPatientsByDate")
    @ResponseBody
    public List<patient> getPatients(@PathParam("date") String date) {
        List<patient> patients = new LinkedList<>();
        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        for (String areaCode : areaPool) {
            for (patient p : service1.getPatients(date, areaCode)) patients.add(p);
        }
        if (patients.size() == 0) return null;
        else return patients;
    }

    // 查看某个患者的接触者
    @GetMapping("/findContacts")
    @ResponseBody
    public List<contact> getContacts(@PathParam("patient_id") int patient_id, @PathParam("batch")int batch) {
        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        List<contact> contacts = new LinkedList<>();
        for (String areaCode : areaPool) {
            for (contact c : service1.getContacts(patient_id, areaCode, batch)) contacts.add(c);
        }
        if (contacts.size() == 0) return null;
        else return contacts;
    }

    // 推理运算
    @GetMapping("/infer")
    @ResponseBody
    public List<contact> inference(@PathParam("patient_id") int patient_id, @PathParam("batch")int batch) throws ParseException {
        patient p = service1.getPatient(patient_id);
        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        List<contact> contacts = new LinkedList<>();
        for (String areaCode : areaPool) {
            for (contact c : service1.getContacts(patient_id, areaCode, batch)) contacts.add(c);
        }
        if (contacts.size() == 0) return null;
        else return service1.inference(p, contacts);
    }

    // 推理全部患者的接触者概率，刷新数据库所有接触者患病概率
    @GetMapping("/inferAll")
    public void inferAll() throws ParseException {
        int[] patientsIdPool = new int[]{10001, 10002, 10003, 10004, 10005};
        String[] areaPool = new String[]{"10001","10002","10003","10004"};
        for (int pId : patientsIdPool) {
            patient p = service1.getPatient(pId);
            List<contact> contacts = new LinkedList<>();
            for (String areaCode : areaPool) {
                for (int i = 1; i <= 3; i++) {
                    for (contact c : service1.getContacts(pId, areaCode, i)) contacts.add(c);
                }
            }
            service1.inference(p, contacts);
        }
    }

    // 所有接触者概率清零
    @GetMapping("/clearAllPossibility")
    public void clearAll() {
        service1.clearAllPossibility();
    }

    // 统计各区域患者和潜在患者数量
    @GetMapping("/countPatientAndPotential")
    @ResponseBody
    public List<statistics> areaCount(@PathParam("batch") int batch) {
        String[] areaPool = new String[]{"10001", "10002", "10003", "10004"};
        List<statistics> ans = new LinkedList<>();
        for (String areaCode : areaPool) {
            statistics s = new statistics();
            s.setAreaCode(areaCode);
            s.setPatient(service1.countPatient(areaCode, batch));
            s.setPotential_patient(service1.countPotentialPatient(areaCode, batch));
            ans.add(s);
        }
        return ans;
    }
}
