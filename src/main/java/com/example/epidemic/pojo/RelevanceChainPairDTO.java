package com.example.epidemic.pojo;

import lombok.Data;

// TODO 新增展示字段
@Data
public class RelevanceChainPairDTO extends RelevanceChainPair {
    private String patientName1;
    private String patientName2;
    private String tel1;
    private String tel2;
    private String address1;
    private String address2;
}
