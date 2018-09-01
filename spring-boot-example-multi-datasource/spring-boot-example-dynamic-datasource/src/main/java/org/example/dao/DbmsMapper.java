package org.example.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DbmsMapper {

    Map getDbInfo();

}
