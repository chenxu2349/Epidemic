package com.example.epidemic.mapper;

import com.example.epidemic.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestMapper {
    Patient queryPatientById(int patient_id);

    int queryPatientByName(String patientName);

    List<Patient> queryPatientsByDate(int batch, String areaCode);

    List<Contact> queryContacts(int patient_id, String area_code, int batch);

    List<PatientTrack> queryPatientTrackById(int patient_id);

    List<ContactTrack> queryContactTrackById(int contact_id);

    Area queryAreaPeopleDensity(int area_id);

    int countPatient(String areaCode, int batch);

    int countPotentialPatient(String areaCode, int batch);

    void setPossibility(int contactId, double possibility);

    void setPotentialPatient(int contactId, int isPotential);

    List<Contact> queryPotentialPatient(int batch, String areaCode);

    void clearAllPossibility();
}
