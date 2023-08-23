package com.example.epidemic.service;

import com.example.epidemic.mapper.testMapper;
import com.example.epidemic.pojo.contactTrack;
import com.example.epidemic.pojo.patientTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class relevanceService {

    @Autowired
    private testMapper mp1;

    public List<patientTrack> getPatientTrackById(int patient_id) {
        return mp1.queryPatientTrackById(patient_id);
    }

    public List<contactTrack> getContactTrackById(int contact_id) {
        return mp1.queryContactTrackById(contact_id);
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
}
