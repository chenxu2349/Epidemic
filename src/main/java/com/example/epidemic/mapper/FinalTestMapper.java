package com.example.epidemic.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @InterfaceName FinalTestMapper
 * @Description
 * @Author chenxu
 * @Date 2024/1/3 21:55
 **/

@Mapper
public interface FinalTestMapper {

    int getAllContactsCount();

    int getMatchContacts();

    void deleteChainInfoById(int id);
}
