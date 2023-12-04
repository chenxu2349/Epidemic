package com.example.epidemic.pojo;

import lombok.Data;

@Data
public class Patient {
    private int patientId;
    private int epidemicId;
    private String areaCode;
    private String patientName;
    private String patientAddress;
    private String patientIdentity;
    private String patientTel;
    private int patientSymptom;
    private int patientAge;
    private int patientSex;
    private double heartRate;
    private double breath;
    private String patientDate;

}
