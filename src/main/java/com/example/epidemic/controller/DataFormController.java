package com.example.epidemic.controller;

import com.example.epidemic.pojo.Contact;
import com.example.epidemic.pojo.Patient;
import com.example.epidemic.service.DataFormService;
import com.example.epidemic.service.InferenceService;
import com.example.epidemic.service.RelevanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName DataFormController
 * @Description
 * @Author chenxu
 * @Date 2023/10/13 10:46
 **/

@Controller
public class DataFormController {

    @Autowired
    private InferenceService inferenceService;

    @Autowired
    private RelevanceService relevanceService;

    @Autowired
    private DataFormService dataFormService;

    @PostMapping("/patient")
    @ResponseBody
    public String insertPatient(@RequestParam("patientName") String patientName,
                              @RequestParam("patientAddress") String patientAddress,
                              @RequestParam("patientTel") String patientTel,
                              @RequestParam("patientAge") int patientAge,
                              @RequestParam("batch") int batch,
                              @RequestParam("areaCode") String areaCode,
                              @RequestParam("patientSymptom") int patientSymptom,
                              @RequestParam("patientSex") int patientSex) {
        Patient p = new Patient();
        p.setPatientName(patientName);
        p.setPatientAddress(patientAddress);
        p.setPatientTel(patientTel);
        p.setPatientAge(patientAge);
        p.setBatch(batch);
        p.setAreaCode(areaCode);
        p.setPatientSymptom(patientSymptom);
        p.setPatientSex(patientSex);
        try {
            dataFormService.insertPatient(p);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @PostMapping("/contact")
    @ResponseBody
    public String insertContact(@RequestParam("contactName") String contactName,
                              @RequestParam("contactAddress") String contactAddress,
                              @RequestParam("patientName") String patientName,
                              @RequestParam("contactAge") int contactAge,
                              @RequestParam("batch") int batch,
                              @RequestParam("areaCode") String areaCode,
                              @RequestParam("contactTel") String contactTel,
                              @RequestParam("contactSex") int contactSex) {
        Contact c = new Contact();
        c.setContactName(contactName);
        c.setContactAddress(contactAddress);
        c.setContactAge(contactAge);
        c.setBatch(batch);
        c.setAreaCode(areaCode);
        c.setContactTel(contactTel);
        c.setContactSex(contactSex);
        try {
            dataFormService.insertContact(c, patientName);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @PostMapping("/csv")
    public void csvImportData() {}
}
