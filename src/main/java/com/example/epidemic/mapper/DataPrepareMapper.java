package com.example.epidemic.mapper;

import com.example.epidemic.pojo.Contact;
import com.example.epidemic.pojo.ContactTrack;
import com.example.epidemic.pojo.Patient;
import com.example.epidemic.pojo.PatientTrack;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName DataPrepareMapper
 * @Description
 * @Author chenxu
 * @Date 2023/12/2 22:51
 **/
@Mapper
public interface DataPrepareMapper {
    List<PatientTrack> getAllPti();
    List<ContactTrack> getAllCti();
    List<PatientTrack> getPtiByRange(int start, int end);
    List<ContactTrack> getCtiByRange(int start, int end);
    void setPtiTimeById(int id, String start, String end);
    void setCtiTimeById(int id, String start, String end);
    void setPatient(int id, int contactId, int patientId);
    void setRelation0(int contactId, int patientId, int contactTime, int contactArea);
    void clearRelation0();
    String getAreaCodeById(int area_id);
    void setPatientIdentity(int patient_id, String ID);
    void setContactIdentity(int contact_id, String ID);
    List<Patient> getAllPatient();
    List<Contact> getAllContact();
}
