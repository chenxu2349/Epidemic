package com.example.epidemic.pojo;

import lombok.Data;

@Data
public class PatientTrack {
    private int patientTrackId;
    private int patientId;
    private int areaId;
    private String startTime;
    private String endTime;
    private int mask;
    private double temperature;
    private double humidness;

}
