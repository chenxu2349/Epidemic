package com.example.epidemic.pojo;

import lombok.Data;

@Data
public class Contact {
    private int contactId;
    private String contactName;
    private String areaCode;
    private int contactAge;
    private int contactSex;
    private String contactTel;
    private String contactAddress;
    private int contactOfVaccinations;
    private int contactHistoryOfIllness;
    private double potentialPatientProbability;
    private int potentialPatient;
    private double heartRate;
    private double breath;
    private String contactDate;

}
