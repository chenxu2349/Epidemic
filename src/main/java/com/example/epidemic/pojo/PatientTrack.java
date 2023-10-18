package com.example.epidemic.pojo;

public class PatientTrack {
    private int patientTrackId;

    private int patientId;

    private int areaId;

    private String startTime;

    private String endTime;

    private int mask;

    private double temperature;

    private double humidness;

    public int getPatientTrackId() {
        return patientTrackId;
    }

    public void setPatientTrackId(int patientTrackId) {
        this.patientTrackId = patientTrackId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidness() {
        return humidness;
    }

    public void setHumidness(double humidness) {
        this.humidness = humidness;
    }

    @Override
    public String toString() {
        return "patientTrack{" +
                "patientTrackId=" + patientTrackId +
                ", patientId=" + patientId +
                ", areaId=" + areaId +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", mask=" + mask +
                ", temperature=" + temperature +
                ", humidness=" + humidness +
                '}';
    }
}
