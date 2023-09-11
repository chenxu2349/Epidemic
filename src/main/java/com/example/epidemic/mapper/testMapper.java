package com.example.epidemic.mapper;

import com.example.epidemic.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface testMapper {
    patient queryPatientById(int patient_id);

    List<patient> queryPatientsByDate(String date, String areaCode);

    List<contact> queryContacts(int patient_id, String area_code, int batch);

    List<patientTrack> queryPatientTrackById(int patient_id);

    List<contactTrack> queryContactTrackById(int contact_id);

    area queryAreaPeopleDensity(int area_id);

    int countPatient(String areaCode, int batch);

    int countPotentialPatient(String areaCode, int batch);

    void setPossibility(int contactId, double possibility);

    void setPotentialPatient(int contactId, int isPotential);

    List<contact> queryPotentialPatient(int batch, String areaCode);
}
