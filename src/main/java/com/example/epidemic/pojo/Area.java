package com.example.epidemic.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("area_info")
public class Area {

    @TableId
    private int areaId;
    private String areaCode;
    private String areaName;
    private String regionalLocation;
    private double populationDensity;
    private double populationMobility;

    public Area(int areaId, String areaCode, String areaName, String regionalLocation, double populationDensity, double populationMobility) {
        this.areaId = areaId;
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.regionalLocation = regionalLocation;
        this.populationDensity = populationDensity;
        this.populationMobility = populationMobility;
    }
}
