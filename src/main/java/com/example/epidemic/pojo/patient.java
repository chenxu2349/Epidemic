package com.example.epidemic.pojo;

public class patient {
    private int patientId;

    private int epidemicId;

    private String areaCode;

    private String patientName;

    private String patientAddress;

    private String patientTel;

    private int patientSymptom;

    private int patientAge;

    private int patientSex;

    private String heartRate;

    private String bloodPressure;

    private String bloodGlucose;

    private String bloodFat;

    private String bodyTemperature;

    private String hemoglobin;

    private String vitalCapacity;

    private int batch;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getEpidemicId() {
        return epidemicId;
    }

    public void setEpidemicId(int epidemicId) {
        this.epidemicId = epidemicId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public int getPatientSymptom() {
        return patientSymptom;
    }

    public void setPatientSymptom(int patientSymptom) {
        this.patientSymptom = patientSymptom;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public int getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(int patientSex) {
        this.patientSex = patientSex;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getBloodGlucose() {
        return bloodGlucose;
    }

    public void setBloodGlucose(String bloodGlucose) {
        this.bloodGlucose = bloodGlucose;
    }

    public String getBloodFat() {
        return bloodFat;
    }

    public void setBloodFat(String bloodFat) {
        this.bloodFat = bloodFat;
    }

    public String getBloodTemperature() {
        return bodyTemperature;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(String bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public void setBloodTemperature(String bloodTemperature) {
        this.bodyTemperature = bloodTemperature;
    }

    public String getHemoglobin() {
        return hemoglobin;
    }

    public void setHemoglobin(String hemoglobin) {
        this.hemoglobin = hemoglobin;
    }

    public String getVitalCapacity() {
        return vitalCapacity;
    }

    public void setVitalCapacity(String vitalCapacity) {
        this.vitalCapacity = vitalCapacity;
    }

    @Override
    public String toString() {
        return "patient{" +
                "patientId=" + patientId +
                ", epidemicId=" + epidemicId +
                ", areaCode='" + areaCode + '\'' +
                ", patientName='" + patientName + '\'' +
                ", patientAddress='" + patientAddress + '\'' +
                ", patientTel='" + patientTel + '\'' +
                ", patientSymptom=" + patientSymptom +
                ", patientAge=" + patientAge +
                ", patientSex=" + patientSex +
                ", heartRate='" + heartRate + '\'' +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", bloodGlucose='" + bloodGlucose + '\'' +
                ", bloodFat='" + bloodFat + '\'' +
                ", bodyTemperature='" + bodyTemperature + '\'' +
                ", hemoglobin='" + hemoglobin + '\'' +
                ", vitalCapacity='" + vitalCapacity + '\'' +
                ", batch=" + batch +
                '}';
    }
}
