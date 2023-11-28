package com.example.epidemic.pojo;

import lombok.Data;

@Data
public class ContactTrack {

    private int contactsTrackId;
    private int contactId;
    private int areaId;
    private String startTime;
    private String endTime;
    private int mask;
    private double temperature;
    private double humidness;

}
