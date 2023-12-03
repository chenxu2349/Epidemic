package com.example.epidemic.service;

import com.example.epidemic.mapper.TestMapper;
import com.example.epidemic.pojo.Contact;
import com.example.epidemic.pojo.ContactTrack;
import com.example.epidemic.pojo.Patient;
import com.example.epidemic.pojo.PatientTrack;
import com.example.epidemic.utils.Normalization;
import com.example.epidemic.utils.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InferenceService {
    @Autowired
    private TestMapper mp1;

    public List<Patient> getAllPatients() {
        return mp1.getAllPatients();
    }

    public List<Patient> getPatientsByDate(String date, String areaCode) {
        List<Patient> list = new LinkedList<>();
        for (Patient p : mp1.queryPatientsByDate(date, areaCode)) list.add(p);
        return list;
    }

    public Patient getPatientById(int patient_id) {
        Patient p = mp1.queryPatientById(patient_id);
        return p;
    }

    public List<Contact> getContacts(int patient_id, String area_code, String date) {
        List<Contact> list = new LinkedList<>();
        for (Contact c : mp1.queryContacts(patient_id, area_code, date)) list.add(c);
        return list;
    }

    public List<Contact> inference(Patient p, List<Contact> contacts) throws ParseException {
        // 病人：接触地点人流量（密度、流动性），地点的温湿度
        // 推理因子：口罩（两个人），年龄，接触时长，接触者是否打疫苗，接触者传染病史，心率，血压
        List<PatientTrack> patientTracks = mp1.queryPatientTrackById(p.getPatientId());
        // 按id记录每个接触者的累计分数和概率
        Map<Integer, Integer> contactPotentialPoints = new HashMap<>();
        Map<Integer, Double> contactPotentialPossibility = new HashMap<>();
        for (Contact c : contacts) {
            contactPotentialPoints.put(c.getContactId(), 0);
            contactPotentialPossibility.put(c.getContactId(), 0.0);
        }
        // 影响因子个数
        int factors = 6;
        for (PatientTrack pt : patientTracks) {
            for (Contact c : contacts) {
                int countPoint = 0;
                List<ContactTrack> contactTracks = mp1.queryContactTrackById(c.getContactId());
                for (ContactTrack ct : contactTracks) {
                    // 患者和接触者出现在同一个区域（时空交集）才会推理概率
                    if (pt.getAreaId() == ct.getAreaId()) {
                        // 影响因子
                        int contactTime = Normalization.contactTime(pt.getStartTime(), pt.getEndTime(), ct.getStartTime(), ct.getEndTime());
                        int maskSituation = Normalization.maskSituation(pt.getMask(), ct.getMask());
//                        double peopleDensity = mp1.queryAreaPeopleDensity(pt.getAreaId()).getPopulationDensity();
                        double peopleDensity = RandomGenerator.getRandomInt(0, 50);
                        int contactAge = c.getContactAge();
                        int contactSex = c.getContactSex();
                        int contactOfVaccinations = c.getContactOfVaccinations();
                        // 影响因子权重系数
                        double x1 = 0.0, x2 = 0.0, x3 = 0.0, x4 = 0.0, x5 = 0.0, x6 = 0.0;

                        // x1
                        if (contactTime == 0) x1 = c.hashCode() % 2 == 0 ? 0.1 : 0.15;
                        else if (contactTime > 0 && contactTime <= 30) x1 = c.hashCode() % 2 == 0 ? 0.3 : 0.35;

                        else if (contactTime > 30 && contactTime <= 60) x1 = c.hashCode() % 2 == 0 ? 0.5 : 0.55;
                        else x1 = System.currentTimeMillis() % 2 == 0 ? 0.7 : 0.75;

                        // x2
                        switch (maskSituation) {
                            case 1 : x2 = c.hashCode() % 2 == 0 ? 0.1 : 0.15; break;
                            case 3 : x2 = c.hashCode() % 2 == 0 ? 0.4 : 0.45; break;
                            case 9 : x2 = c.hashCode() % 2 == 0 ? 0.8 : 0.85; break;
                        }

                        // x3
                        if (peopleDensity <= 10) x3 = 0.3;
                        else if (peopleDensity > 10 && peopleDensity <= 30) x3 = 0.5;
                        else x3 = 0.7;

                        // x4
                        if (contactAge < 10) x4 = c.hashCode() % 2 == 0 ? 0.6 : 0.65;
                        else if (contactAge >= 10 && contactAge < 25) x4 = c.hashCode() % 2 == 0 ? 0.3 : 0.35;
                        else if (contactAge >= 25 && contactAge < 55) x4 = c.hashCode() % 2 == 0 ? 0.28 : 0.32;
                        else if (contactAge >= 55 && contactAge < 75) x4 = c.hashCode() % 2 == 0 ? 0.57 : 0.63;
                        else x4 = c.hashCode() % 2 == 0 ? 0.73 : 0.8;

                        // x5
                        if (contactSex == 1) x5 = c.hashCode() % 2 == 0 ? 0.4 : 0.45;
                        else x5 = c.hashCode() % 2 == 0 ? 0.6 : 0.65;

                        // x6
                        if (contactOfVaccinations == 1) x6 = c.hashCode() % 2 == 0 ? 0.23 : 0.3;
                        else x6 = c.hashCode() % 2 == 0 ? 0.5 : 0.55;

                        // 分数汇总
                        if (System.currentTimeMillis() % 3 == 0) countPoint = (int)(120 * x1 + 135 * x2 + 100 * x3 + 85 * x4 + 105 * x5 + 90 * x6);
                        else if (System.currentTimeMillis() % 3 == 1) countPoint = (int)(110 * x1 + 125 * x2 + 105 * x3 + 90 * x4 + 100 * x5 + 95 * x6);
                        else countPoint = (int)(115 * x1 + 132 * x2 + 108 * x3 + 87 * x4 + 125 * x5 + 87 * x6);
                    }
                }
                contactPotentialPoints.put(c.getContactId(), contactPotentialPoints.get(c.getContactId()) + countPoint);
            }
        }
        // 得到每个接触者患病概率，并且更新数据
        for (Contact c : contacts) {
            int cId = c.getContactId();
            double potentialP = Normalization.normalization(contactPotentialPoints.get(c.getContactId()), factors);
            DecimalFormat df = new DecimalFormat("#.00");
            potentialP = Double.valueOf(df.format(potentialP));
            contactPotentialPossibility.put(cId, potentialP);
            // 回写数据库
            c.setPotentialPatientProbability(potentialP);
            // setDatabase(cId, potentialP);
            if (potentialP >= 0.6) {
                mp1.setPotentialPatient(cId, 1);
                c.setPotentialPatient(1);
            } else {
                mp1.setPotentialPatient(cId, 0);
                c.setPotentialPatient(0);
            }
            mp1.setPossibility(cId, potentialP);
        }
        return contacts;
    }

    public int countPatient(String areaCode, String date) {
        return mp1.countPatient(areaCode, date);
    }

    public int countPotentialPatient(String areaCode, String date) {
        return mp1.countPotentialPatient(areaCode, date);
    }

//    public void setDatabase(int cId, double potentialP) {
//        if (potentialP >= 0.6) mp1.setPotentialPatient(cId, 1);
//        mp1.setPossibility(cId, potentialP);
//    }

    public void clearAllPossibility() {
        mp1.clearAllPossibility();
    }
}
