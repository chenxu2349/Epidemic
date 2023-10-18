package com.example.epidemic.service;

import com.example.epidemic.mapper.ChainMapper;
import com.example.epidemic.mapper.TestMapper;
import com.example.epidemic.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class RelevanceService {

    @Autowired
    private TestMapper mp1;

    @Autowired
    private ChainMapper mp2;

    public List<PatientTrack> getPatientTrackById(int patient_id) {
        return mp1.queryPatientTrackById(patient_id);
    }

    public List<ContactTrack> getContactTrackById(int contact_id) {
        return mp1.queryContactTrackById(contact_id);
    }

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

    public boolean checkTwoPerson(Patient p1, Contact p2) throws ParseException {
        List<PatientTrack> p1Tracks = getPatientTrackById(p1.getPatientId());
        List<PatientTrack> p2Tracks = getPatientTrackById(p2.getPatientId());
        for (PatientTrack pt1 : p1Tracks) {
            for (PatientTrack pt2 : p2Tracks) {
                if (checkTimeAndZoneContact(pt1, pt2)) return true;
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

    public boolean checkTimeAndZoneContact(PatientTrack pt, PatientTrack ct) throws ParseException {
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

    public List<Contact> getPotentialPatient(int batch, String areaCode) {
        return mp1.queryPotentialPatient(batch, areaCode);
    }

    public List<RelevanceChainPair> getRelevanceChainPairs(int batch, String areaCode) {
        return mp2.queryRelevancePairs(batch, areaCode);
    }

    public void setChainPair(int id, String code, int pid1, int pid2) {
        mp2.setRelevancePair(id, code, pid1, pid2);
    }
}
