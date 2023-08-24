package com.example.epidemic.service;

import com.example.epidemic.mapper.chainMapper;
import com.example.epidemic.mapper.testMapper;
import com.example.epidemic.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class relevanceService {

    @Autowired
    private testMapper mp1;

    @Autowired
    private chainMapper mp2;

    public List<patientTrack> getPatientTrackById(int patient_id) {
        return mp1.queryPatientTrackById(patient_id);
    }

    public List<contactTrack> getContactTrackById(int contact_id) {
        return mp1.queryContactTrackById(contact_id);
    }

    public boolean checkTwoPerson(patient p1, patient p2) throws ParseException {
        List<patientTrack> p1Tracks = getPatientTrackById(p1.getPatientId());
        List<patientTrack> p2Tracks = getPatientTrackById(p2.getPatientId());
        for (patientTrack pt1 : p1Tracks) {
            for (patientTrack pt2 : p2Tracks) {
                if (checkTimeAndZoneContact(pt1, pt2)) return true;
            }
        }
        return false;
    }

    public boolean checkTwoPerson(patient p1, contact p2) throws ParseException {
        List<patientTrack> p1Tracks = getPatientTrackById(p1.getPatientId());
        List<patientTrack> p2Tracks = getPatientTrackById(p2.getPatientId());
        for (patientTrack pt1 : p1Tracks) {
            for (patientTrack pt2 : p2Tracks) {
                if (checkTimeAndZoneContact(pt1, pt2)) return true;
            }
        }
        return false;
    }

    public boolean checkTimeAndZoneContact(patientTrack pt, contactTrack ct) throws ParseException {
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

    public boolean checkTimeAndZoneContact(patientTrack pt, patientTrack ct) throws ParseException {
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

    public List<contact> getPotentialPatient(int batch) {
        return mp1.queryPotentialPatient(batch);
    }

    public List<relevanceChainPair> getRelevanceChainPairs() {
        return mp2.queryRelevancePairs();
    }

    public void setChainPair(int id, String code, int pid1, int pid2) {
        mp2.setRelevancePair(id, code, pid1, pid2);
    }
}
