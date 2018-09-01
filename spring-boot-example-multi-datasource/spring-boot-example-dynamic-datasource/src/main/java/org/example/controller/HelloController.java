package org.example.controller;

import org.example.aop.TargetDataSource;
import org.example.dao.DbmsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private DbmsMapper dbmsMapper;

    @TargetDataSource("db1")
    @RequestMapping("/db1")
    public String db1() {
        return dbmsMapper.getDbInfo().toString();
    }

    @TargetDataSource("db2")
    @RequestMapping("/db2")
    public String db2() {
        return dbmsMapper.getDbInfo().toString();
    }

}
