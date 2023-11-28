package com.example.epidemic.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Chain {
    private int chainId;
    private List<Patient> chainPatients;

}
