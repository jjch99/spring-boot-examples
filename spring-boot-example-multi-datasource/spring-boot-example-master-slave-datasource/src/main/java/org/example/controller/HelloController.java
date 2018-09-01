package org.example.controller;

import org.example.dao.master.MasterDbmsMapper;
import org.example.dao.slave.SlaveDbmsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private MasterDbmsMapper masterDbmsMapper;

    @Autowired
    private SlaveDbmsMapper slaveDbmsMapper;

    @RequestMapping("/master")
    public String master() {
        return masterDbmsMapper.getDbInfo().toString();
    }

    @RequestMapping("/slave")
    public String slave() {
        return slaveDbmsMapper.getDbInfo().toString();
    }

}
