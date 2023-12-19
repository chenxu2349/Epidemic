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

    public List<Contact> getContacts(Patient p, String area_code, String date) {
        if (!p.getAreaCode().equals(area_code) || !p.getPatientDate().equals(date)) return null;
        List<Contact> list = new LinkedList<>();
        for (Contact c : mp1.queryContacts(p.getPatientId(), area_code, date)) list.add(c);
        return list;
    }

    public List<Contact> inference(Patient p, List<Contact> allContacts) throws ParseException {

        if (allContacts.size() == 0 || allContacts == null) return null;

        // 病人：接触地点人流量（密度、流动性），地点的温湿度
        // 推理因子：口罩（两个人），年龄，接触时长，接触者是否打疫苗，接触者传染病史，心率，血压

        List<Contact> contacts = new ArrayList<>();
        List<Contact> contactsExist = new ArrayList<>();
        for (Contact c : allContacts) {
            if (c.getPotentialPatientProbability() <= 0.01) contacts.add(c);
            else contactsExist.add(c);
        }

        List<PatientTrack> patientTracks = mp1.queryPatientTrackById(p.getPatientId());
        // 按id记录每个接触者的累计分数和概率
        Map<Integer, Integer> contactPotentialPoints = new HashMap<>();
        Map<Integer, Double> contactPotentialPossibility = new HashMap<>();
        // 初始得分和概率置0
        for (Contact c : contacts) {
            contactPotentialPoints.put(c.getContactId(), 0);
            contactPotentialPossibility.put(c.getContactId(), 0.0);
        }
        // 影响因子个数
        int factors = 6;
        for (PatientTrack pt : patientTracks) {
            for (Contact c : contacts) {
                if (contactPotentialPoints.get(c.getContactId()) > 0) continue;
                int countPoint = 0;
                List<ContactTrack> contactTracks = mp1.queryContactTrackById(c.getContactId());
                for (ContactTrack ct : contactTracks) {
                    // 患者和接触者出现在同一个区域（时空交集）才会推理概率
                    if (pt.getAreaId() == ct.getAreaId()) {
                        // 影响因子
                        int contactTime = Normalization.contactTime(pt.getStartTime(), pt.getEndTime(), ct.getStartTime(), ct.getEndTime());
                        int maskSituation = Normalization.maskSituation(pt.getMask(), ct.getMask());
                        double peopleDensity = RandomGenerator.getRandomInt(0, 50);
                        int contactAge = c.getContactAge();
                        int contactSex = c.getContactSex();
                        int contactOfVaccinations = c.getContactOfVaccinations();
                        // 影响因子权重系数
                        double x1 = 0.0, x2 = 0.0, x3 = 0.0, x4 = 0.0, x5 = 0.0, x6 = 0.0;

                        // x1
                        if (contactTime == 0) x1 = c.hashCode() % 2 == 0 ? 0.1 : 0.15;
                        else if (contactTime > 0 && contactTime <= 10) x1 = c.hashCode() % 2 == 0 ? 0.3 : 0.35;

                        else if (contactTime > 10 && contactTime <= 20) x1 = c.hashCode() % 2 == 0 ? 0.5 : 0.55;
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
                        if (System.currentTimeMillis() % 3 == 0) countPoint = (int)(130 * x1 + 145 * x2 + 110 * x3 + 95 * x4 + 115 * x5 + 100 * x6);
                        else if (System.currentTimeMillis() % 3 == 1) countPoint = (int)(120 * x1 + 135 * x2 + 115 * x3 + 100 * x4 + 110 * x5 + 105 * x6);
                        else countPoint = (int)(125 * x1 + 142 * x2 + 118 * x3 + 97 * x4 + 135 * x5 + 97 * x6);

                        countPoint += 50;
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

        for (Contact c : contactsExist) contacts.add(c);
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
