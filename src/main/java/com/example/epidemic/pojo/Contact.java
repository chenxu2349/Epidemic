package com.example.epidemic.pojo;

public class Contact {
    private int contactId;

    private String contactName;

    private int patientId;

    private String areaCode;

    private int contactAge;

    private int contactSex;

    private String contactTel;

    private String contactAddress;

    private int contactOfVaccinations;

    private int contactHistoryOfIllness;

    private double potentialPatientProbability;

    private int potentialPatient;

    private String heartRate;

    private String bloodPressure;

    private String bloodGlucose;

    private String bloodFat;

    private String bodyTemperature;

    private String hemoglobin;

    private String vitalCapacity;

    private int batch;

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public int getContactAge() {
        return contactAge;
    }

    public void setContactAge(int contactAge) {
        this.contactAge = contactAge;
    }

    public int getContactSex() {
        return contactSex;
    }

    public void setContactSex(int contactSex) {
        this.contactSex = contactSex;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public int getContactOfVaccinations() {
        return contactOfVaccinations;
    }

    public void setContactOfVaccinations(int contactOfVaccinations) {
        this.contactOfVaccinations = contactOfVaccinations;
    }

    public int getContact_historyOfIllness() {
        return contactHistoryOfIllness;
    }

    public void setContact_historyOfIllness(int contact_historyOfIllness) {
        this.contactHistoryOfIllness = contact_historyOfIllness;
    }

    public double getPotentialPatientProbability() {
        return potentialPatientProbability;
    }

    public void setPotentialPatientProbability(double potentialPatientProbability) {
        this.potentialPatientProbability = potentialPatientProbability;
    }

    public int getPotentialPatient() {
        return potentialPatient;
    }

    public void setPotentialPatient(int potentialPatient) {
        this.potentialPatient = potentialPatient;
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

    public String getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(String bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
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

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "contact{" +
                "contactId=" + contactId +
                ", contactName='" + contactName + '\'' +
                ", patientId=" + patientId +
                ", areaCode='" + areaCode + '\'' +
                ", contactAge=" + contactAge +
                ", contactSex=" + contactSex +
                ", contactTel='" + contactTel + '\'' +
                ", contactAddress='" + contactAddress + '\'' +
                ", contactOfVaccinations=" + contactOfVaccinations +
                ", contact_historyOfIllness=" + contactHistoryOfIllness +
                ", potentialPatientProbability=" + potentialPatientProbability +
                ", potentialPatient=" + potentialPatient +
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
