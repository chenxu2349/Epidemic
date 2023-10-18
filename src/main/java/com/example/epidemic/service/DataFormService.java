package com.example.epidemic.service;

import com.example.epidemic.mapper.InsertMapper;
import com.example.epidemic.mapper.TestMapper;
import com.example.epidemic.pojo.Contact;
import com.example.epidemic.pojo.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName DataFormService
 * @Description
 * @Author chenxu
 * @Date 2023/10/13 11:46
 **/

@Service
public class DataFormService {

    @Autowired
    private InsertMapper insertMapper;

    @Autowired
    private TestMapper testMapper;

    public void insertPatient(Patient p) {
        insertMapper.insertPatient(p.getPatientName(), p.getPatientAddress(), p.getPatientTel(), p.getPatientAge(),
                p.getBatch(), p.getAreaCode(), p.getPatientSymptom(), p.getPatientSex());
    }

    public void insertContact(Contact c, String patientName) {
        int patientId = testMapper.queryPatientByName(patientName);
        insertMapper.insertContact(c.getContactName(), c.getContactAddress(), patientId, c.getContactAge(),
                c.getBatch(), c.getAreaCode(), c.getContactTel(), c.getContactSex());
    }
}
