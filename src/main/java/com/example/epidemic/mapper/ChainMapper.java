package com.example.epidemic.mapper;

import com.example.epidemic.pojo.RelevanceChainPair;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChainMapper {

    List<RelevanceChainPair> queryRelevancePairs(String date, String areaCode);

    void setRelevancePair(int id, String code, int pid1, int pid2);

    void clearChainInfo();
}
