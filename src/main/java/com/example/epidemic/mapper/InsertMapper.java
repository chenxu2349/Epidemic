package com.example.epidemic.mapper;

import com.example.epidemic.pojo.Contact;
import com.example.epidemic.pojo.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ConcurrentModificationException;

/**
 * @InterfaceName InsertMapper
 * @Description
 * @Author chenxu
 * @Date 2023/10/13 11:47
 **/

@Mapper
public interface InsertMapper {

    void insertPatient(String patientName, String patientAddress, String patientTel, int patientAge,
                       int batch, String areaCode, int patientSymptom, int patientSex);

    void insertContact(String contactName, String contactAddress, int patientId, int contactAge,
                       int batch, String areaCode, String contactTel, int contactSex);
}
