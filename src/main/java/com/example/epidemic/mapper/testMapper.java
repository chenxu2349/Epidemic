package com.example.epidemic.mapper;

import com.example.epidemic.pojo.contact;
import com.example.epidemic.pojo.patient;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface testMapper {
    patient queryById(int id);

    List<patient> queryPatientsByDate(String date);

    List<contact> queryContacts(int patient_id, String area_code);
}
