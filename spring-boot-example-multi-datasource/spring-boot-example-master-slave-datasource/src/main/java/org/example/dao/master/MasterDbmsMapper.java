package org.example.dao.master;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterDbmsMapper {

    Map getDbInfo();

}
