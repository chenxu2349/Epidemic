package com.example.epidemic.service;

import com.example.epidemic.mapper.testMapper;
import com.example.epidemic.pojo.contact;
import com.example.epidemic.pojo.contactTrack;
import com.example.epidemic.pojo.patient;
import com.example.epidemic.pojo.patientTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class inferenceService {
    @Autowired
    private testMapper mp1;

    public List<patient> getPatients(String date) {
        List<patient> list = new LinkedList<>();
        for (patient p : mp1.queryPatientsByDate(date)) list.add(p);
        return list;
    }

    public patient getPatients(int patient_id) {
        patient p = mp1.queryPatientById(patient_id);
        return p;
    }

    public List<contact> getContacts(int patient_id, String area_code, int batch) {
        List<contact> list = new LinkedList<>();
        for (contact c : mp1.queryContacts(patient_id, area_code, batch)) list.add(c);
        return list;
    }

    public List<contact> inference(patient p, List<contact> contacts) throws ParseException {
        // 病人：接触地点人流量（密度、流动性），地点的温湿度
        // 推理因子：口罩（两个人），接触时长，接触者是否打疫苗，接触者传染病史，心率，血压
        List<patientTrack> patientTracks = mp1.queryPatientTrackById(p.getPatientId());
        // 按id记录每个接触者的累计分数和概率
        Map<Integer, Integer> contactPotentialPoints = new HashMap<>();
        Map<Integer, Double> contactPotentialPossibility = new HashMap<>();
        for (contact c : contacts) {
            contactPotentialPoints.put(c.getContactId(), 0);
            contactPotentialPossibility.put(c.getContactId(), 0.0);
        }
        for (patientTrack pt : patientTracks) {
            for (contact c : contacts) {
                int countPoint = 0;
                List<contactTrack> contactTracks = mp1.queryContactTrackById(c.getContactId());
                for (contactTrack ct : contactTracks) {
                    // 患者和接触者出现在同一个区域（时空交集）才会推理概率
                    if (pt.getAreaId() == ct.getAreaId()) {
                        int contactTime = contactTime(pt.getStartTime(), pt.getEndTime(), ct.getStartTime(), ct.getEndTime());
                        int maskSituation = maskSituation(pt.getMask(), ct.getMask());
                        double peopleDensity = mp1.queryAreaPeopleDensity(pt.getAreaId()).getPopulationDensity();
                        countPoint = 10 * contactTime + 20 * maskSituation + (int)(30 * peopleDensity);
                    }
                    else continue;
                }
                contactPotentialPoints.put(c.getContactId(), contactPotentialPoints.get(c.getContactId()) + countPoint);
            }
        }
        // 得到每个接触者患病概率，并且更新数据
        for (contact c : contacts) {
            int cId = c.getContactId();
            double potentialP = normalization(contactPotentialPoints.get(c.getContactId()));
            contactPotentialPossibility.put(cId, potentialP);
            // 回写数据库
            c.setPotentialPatientProbability(potentialP);
        }
        return contacts;
    }

    // 接触时间
    public int contactTime(String p_start, String p_end, String c_start, String c_end) throws ParseException {

        // String类型转为日期类型比较
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 时间戳，毫秒
        long ps = format.parse(p_start).getTime();
        long pe = format.parse(p_start).getTime();
        long cs = format.parse(p_start).getTime();
        long ce = format.parse(p_start).getTime();

        long count = 0;
        if (cs >= ps && cs <= pe) {
            if (ce >= pe) count = pe - cs;
            else count = ce - cs;
        }
        if (ps >= cs && ps <= ce) {
            if (pe >= ce) count = ce - ps;
            else count = pe - ps;
        }

        // ms -> min 两个时间段交集是多少分钟
        return (int)(count/60000);
    }

    // 佩戴口罩情况, 0-10代表感染风险
    public int maskSituation(int a, int b) {
        int seed = (int)(0 + Math.random()*(16 - 0 + 1));
        if (a == 1 && b == 1) return 1;
        else if (a == 1 || b == 1) return 3;
        else return 9;
    }

    // 数据归一化为规定区间概率
    public double normalization(int point) {
        return 1.1;
    }
}
