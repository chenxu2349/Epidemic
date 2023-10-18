package com.example.epidemic.pojo;

public class ContactTrack {
    private int contactsTrackId;

    private int contactId;

    private int areaId;

    private String startTime;

    private String endTime;

    private int mask;

    private double temperature;

    private double humidness;

    public int getContactsTrackId() {
        return contactsTrackId;
    }

    public void setContactsTrackId(int contactsTrackId) {
        this.contactsTrackId = contactsTrackId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
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
        return "contactTrack{" +
                "contactsTrackId=" + contactsTrackId +
                ", contactId=" + contactId +
                ", areaId=" + areaId +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", mask=" + mask +
                ", temperature=" + temperature +
                ", humidness=" + humidness +
                '}';
    }
}
