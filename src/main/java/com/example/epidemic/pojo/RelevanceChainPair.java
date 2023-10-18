package com.example.epidemic.pojo;

public class RelevanceChainPair {

    private int correlationChainId;

    private String correlationChainCode;

    private int patientId1;

    private int patientId2;

    public int getCorrelationChainId() {
        return correlationChainId;
    }

    public void setCorrelationChainId(int correlationChainId) {
        this.correlationChainId = correlationChainId;
    }

    public String getCorrelationChainCode() {
        return correlationChainCode;
    }

    public void setCorrelationChainCode(String correlationChainCode) {
        this.correlationChainCode = correlationChainCode;
    }

    public int getPatientId1() {
        return patientId1;
    }

    public void setPatientId1(int patientId1) {
        this.patientId1 = patientId1;
    }

    public int getPatientId2() {
        return patientId2;
    }

    public void setPatientId2(int patientId2) {
        this.patientId2 = patientId2;
    }

    @Override
    public String toString() {
        return "relevanceChain{" +
                "correlationChainId=" + correlationChainId +
                ", correlationChainCode='" + correlationChainCode + '\'' +
                ", patientId1=" + patientId1 +
                ", patientId2=" + patientId2 +
                '}';
    }
}
