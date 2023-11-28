package com.example.epidemic.pojo;

import lombok.Data;

@Data
// 各个区域的患者和潜在患者的统计信息
public class Statistics {
    private String areaCode;
    private int patient;
    private int potential_patient;

}
