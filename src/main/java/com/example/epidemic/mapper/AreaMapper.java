package com.example.epidemic.mapper;

import com.example.epidemic.pojo.Area;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @InterfaceName AreaMapper
 * @Description
 * @Author chenxu
 * @Date 2023/11/29 15:56
 **/
@Mapper
public interface AreaMapper {

    List<Area> getAllAreas();

    List<Area> getAreas(int from, int to);
}
