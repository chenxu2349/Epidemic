package com.example.epidemic.pojo;

import java.util.List;

public class Chain {
    private int chainId;

    private List<Patient> chainPatients;

    public int getChainId() {
        return chainId;
    }

    public void setChainId(int chainId) {
        this.chainId = chainId;
    }

    public List<Patient> getChainPatients() {
        return chainPatients;
    }

    public void setChainPatients(List<Patient> chainPatients) {
        this.chainPatients = chainPatients;
    }

    @Override
    public String toString() {
        return "chain{" +
                "chainId=" + chainId +
                ", chainPatients=" + chainPatients +
                '}';
    }
}
