package com.example.epidemic.pojo;

import java.util.List;

public class chain {
    private int chainId;

    private List<patient> chainPatients;

    public int getChainId() {
        return chainId;
    }

    public void setChainId(int chainId) {
        this.chainId = chainId;
    }

    public List<patient> getChainPatients() {
        return chainPatients;
    }

    public void setChainPatients(List<patient> chainPatients) {
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
