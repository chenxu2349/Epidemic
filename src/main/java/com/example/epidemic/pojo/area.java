package com.example.epidemic.pojo;

public class area {
    private int areaId;

    private String areaCode;

    private String areaName;

    private String regionalLocation;

    private double populationDensity;

    private double populationMobility;

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRegionalLocation() {
        return regionalLocation;
    }

    public void setRegionalLocation(String regionalLocation) {
        this.regionalLocation = regionalLocation;
    }

    public double getPopulationDensity() {
        return populationDensity;
    }

    public void setPopulationDensity(double populationDensity) {
        this.populationDensity = populationDensity;
    }

    public double getPopulationMobility() {
        return populationMobility;
    }

    public void setPopulationMobility(double populationMobility) {
        this.populationMobility = populationMobility;
    }

    @Override
    public String toString() {
        return "area{" +
                "areaId=" + areaId +
                ", areaCode='" + areaCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", regionalLocation='" + regionalLocation + '\'' +
                ", populationDensity=" + populationDensity +
                ", populationMobility=" + populationMobility +
                '}';
    }
}
