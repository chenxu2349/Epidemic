package com.example.epidemic.pojo;

// 各个区域的患者和潜在患者的统计信息
public class Statistics {
    private String areaCode;

    private int patient;

    private int potential_patient;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public int getPatient() {
        return patient;
    }

    public void setPatient(int patient) {
        this.patient = patient;
    }

    public int getPotential_patient() {
        return potential_patient;
    }

    public void setPotential_patient(int potential_patient) {
        this.potential_patient = potential_patient;
    }

    @Override
    public String toString() {
        return "statistics{" +
                "areaCode='" + areaCode + '\'' +
                ", patient=" + patient +
                ", potential_patient=" + potential_patient +
                '}';
    }
}
