package com.example.epidemic.pojo;

import lombok.Data;

@Data
public class RelevanceChainPair {

    private int correlationChainId;
    private String correlationChainCode;
    private int patientId1;
    private int patientId2;

}
