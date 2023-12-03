package com.example.epidemic.pojo;

import lombok.Data;

// 联系表实体，标识接触者和患者之间的联系属性
@Data
public class Relation0 {

    private int contactId;
    private int patientId;
    // 接触时长
    private int contactTime;
    // 接触地点
    private int contactArea;
}
