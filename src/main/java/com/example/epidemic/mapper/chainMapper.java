package com.example.epidemic.mapper;

import com.example.epidemic.pojo.relevanceChainPair;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface chainMapper {

    List<relevanceChainPair> queryRelevancePairs(int batch, String areaCode);

    void setRelevancePair(int id, String code, int pid1, int pid2);
}
