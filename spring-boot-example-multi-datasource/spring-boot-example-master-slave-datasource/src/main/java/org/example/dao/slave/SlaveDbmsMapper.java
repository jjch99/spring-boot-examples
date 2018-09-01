package org.example.dao.slave;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SlaveDbmsMapper {

    Map getDbInfo();

}
