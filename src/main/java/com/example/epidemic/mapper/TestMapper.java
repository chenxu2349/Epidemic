package com.example.epidemic.mapper;

import com.example.epidemic.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestMapper {

    List<Patient> getAllPatients();

    Patient queryPatientById(int patient_id);

    int queryPatientByName(String patientName);

    List<Patient> queryPatientsByDate(String date, String areaCode);

    List<Patient> queryPatientsByBatch(int batch, String areaCode);

    List<Contact> queryContacts(int patient_id, String area_code, String date);

    List<PatientTrack> queryPatientTrackById(int patient_id);

    List<ContactTrack> queryContactTrackById(int contact_id);

    Area queryAreaPeopleDensity(int area_id);

    int countPatient(String areaCode, String date);

    int countPotentialPatient(String areaCode, String date);

    void setPossibility(int contactId, double possibility);

    void setPotentialPatient(int contactId, int isPotential);

    List<Contact> queryPotentialPatient(String date, String areaCode);

    void clearAllPossibility();
}
