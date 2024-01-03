package com.example.epidemic.service;

import com.example.epidemic.mapper.ChainMapper;
import com.example.epidemic.mapper.TestMapper;
import com.example.epidemic.pojo.*;
import com.example.epidemic.utils.ThreadPoolFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class RelevanceService {

    @Autowired
    private TestMapper testMapper;
    @Autowired
    private ChainMapper chainMapper;
    @Autowired
    private InferenceService inferenceService;

    public List<PatientTrack> getPatientTrackById(int patient_id) {
        return testMapper.queryPatientTrackById(patient_id);
    }

    public List<ContactTrack> getContactTrackById(int contact_id) {
        return testMapper.queryContactTrackById(contact_id);
    }

    // 关联分析一个区域的一天
    public void relevanceByDateAndAreaCode(String date, String areaCode) throws ParseException {

        // 该区域关联的全部传播链，map存某个id对应的感染者列表
        Map<Integer, List<Patient>> chainList = new HashMap<>();
        // 该区域这一天的全部患者
        List<Patient> patients = new ArrayList<>();
        for (Patient p : inferenceService.getPatientsByDate(date, areaCode)) patients.add(p);

        Map<Integer, List<PatientTrack>> ptMap = new HashMap<>();   // 这个人去过哪些地方
        Map<Integer, Integer> chainIdMap = new HashMap<>(); // 某个患者在哪个传播链id上
        Set<Integer> hasInChain = new HashSet<>();  // 已经在传播链中的人

        // 填充每个人的行动轨迹
        for (Patient p : patients) {
            int pId = p.getPatientId();
            List<PatientTrack> pTrack = getPatientTrackById(pId);
            ptMap.put(pId, pTrack);
        }

        // relevanceAll时用到的变量
        int chainId = 0, i = 0;
        for (; i < patients.size(); i++) {
            int iPid = patients.get(i).getPatientId();
            if (!hasInChain.contains(iPid)) {
                // 先向后找，有没有已经上链的且和i有时空交集的
                boolean basicCheck = false;
                for (int j = i + 1; j < patients.size(); j++) {
                    Patient pi = patients.get(i);
                    Patient pj = patients.get(j);
                    try {
                        if (checkTwoPerson(pi, pj) && hasInChain.contains(pj.getPatientId())) {
                            basicCheck = true;
                            int cId = chainIdMap.get(pj.getPatientId());
                            List<Patient> list = chainList.get(cId);
                            list.add(pi);
                            chainList.put(cId, list);
                            chainIdMap.put(pi.getPatientId(), cId);
                            hasInChain.add(pi.getPatientId());

                            // 记录pair
                            String areaCode1 = pi.getAreaCode();
                            setChainPair(cId, areaCode1, pi.getPatientId(), pj.getPatientId());
                            setChainPair(cId, areaCode1, pj.getPatientId(), pi.getPatientId());
                            break;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (basicCheck) return;

                Chain c = new Chain();
                c.setChainId(chainId);
                List<Patient> list = new LinkedList<>();
                list.add(patients.get(i));
                chainIdMap.put(iPid, chainId);
                hasInChain.add(iPid);
                // 向后匹配
                for (int j = i + 1; j < patients.size(); j++) {
                    Patient pi = patients.get(i);
                    Patient pj = patients.get(j);
                    try {
                        if (checkTwoPerson(pi, pj)) {
                            // 存在时空交集，患者j上链
                            list.add(pj);
                            chainIdMap.put(pj.getPatientId(), chainId);
                            hasInChain.add(pj.getPatientId());

                            // 记录pair
                            String areaCode1 = pi.getAreaCode();
                            setChainPair(chainId, areaCode1, pi.getPatientId(), pj.getPatientId());
                            setChainPair(chainId, areaCode1, pj.getPatientId(), pi.getPatientId());
                            break;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                c.setChainPatients(list);
                chainList.put(chainId, list);
                chainId++;
            } else {
                int cId = chainIdMap.get(iPid);
                for (int j = i + 1; j < patients.size(); j++) {
                    Patient pi = patients.get(i);
                    Patient pj = patients.get(j);
                    try {
                        if (checkTwoPerson(pi, pj) && !hasInChain.contains(pj.getPatientId())) {
                            // 左边的i已经在链上，吸纳新成员j上链
                            List<Patient> list = chainList.get(cId);
                            list.add(pj);
                            chainIdMap.put(pj.getPatientId(), cId);
                            hasInChain.add(pj.getPatientId());

                            // 记录pair
                            String areaCode1 = pi.getAreaCode();
                            setChainPair(cId, areaCode1, pi.getPatientId(), pj.getPatientId());
                            setChainPair(cId, areaCode1, pj.getPatientId(), pi.getPatientId());

                            chainList.put(cId, list);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 检查患者之间的交集
    public boolean checkTwoPerson(Patient p1, Patient p2) throws ParseException {
        List<PatientTrack> p1Tracks = getPatientTrackById(p1.getPatientId());
        List<PatientTrack> p2Tracks = getPatientTrackById(p2.getPatientId());
        for (PatientTrack pt1 : p1Tracks) {
            for (PatientTrack pt2 : p2Tracks) {
                if (checkTimeAndZoneContact(pt1, pt2)) return true;
            }
        }
        return false;
    }

    // 检查患者和接触者之间的交集
    public boolean checkTwoPerson(Patient p, Contact c) throws ParseException {
        List<PatientTrack> pTracks = getPatientTrackById(p.getPatientId());
        List<ContactTrack> cTracks = getContactTrackById(c.getContactId());
        for (PatientTrack pt : pTracks) {
            for (ContactTrack ct : cTracks) {
                if (checkTimeAndZoneContact(pt, ct)) return true;
            }
        }
        return false;
    }

    public boolean checkTimeAndZoneContact(PatientTrack pt, ContactTrack ct) throws ParseException {

        if (pt.getAreaId() != ct.getAreaId()) return false;

        // String类型转为日期类型比较
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 时间戳，毫秒
        long ps = format.parse(pt.getStartTime()).getTime();
        long pe = format.parse(pt.getEndTime()).getTime();
        long cs = format.parse(ct.getStartTime()).getTime();
        long ce = format.parse(ct.getEndTime()).getTime();

        if (cs >= ps && cs <= pe) return true;
        if (ce >= ps && ce <= pe) return true;
        if (ps >= cs && ps <= ce) return true;
        if (pe >= cs && pe <= ce) return true;

        return false;
    }

    public boolean checkTimeAndZoneContact(PatientTrack pt1, PatientTrack pt2) throws ParseException {

        if (pt1.getAreaId() != pt2.getAreaId()) return false;

        // String类型转为日期类型比较
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 时间戳，毫秒
        long ps = format.parse(pt1.getStartTime()).getTime();
        long pe = format.parse(pt1.getEndTime()).getTime();
        long cs = format.parse(pt2.getStartTime()).getTime();
        long ce = format.parse(pt2.getEndTime()).getTime();

        if (cs >= ps && cs <= pe) return true;
        if (ce >= ps && ce <= pe) return true;
        if (ps >= cs && ps <= ce) return true;
        if (pe >= cs && pe <= ce) return true;

        return false;
    }

    public List<Contact> getPotentialPatient(String date, String areaCode) {
        return testMapper.queryPotentialPatient(date, areaCode);
    }

    public List<RelevanceChainPair> getRelevanceChainPairs(String date, String areaCode) {
        return chainMapper.queryRelevancePairs(date, areaCode);
    }

    public void setChainPair(int id, String code, int pid1, int pid2) {
        chainMapper.setRelevancePair(id, code, pid1, pid2);
    }

    public String clearChainInfo() {
        try {
            chainMapper.clearChainInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return "clear failed!";
        }
        return "clear success";
    }

    public void checkTwoChain() {
    }
}
