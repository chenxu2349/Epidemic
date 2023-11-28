package com.example.epidemic.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UtilsMapper {

    List<String> getAllDates();

    List<String> getAllAreaCodes();
}
