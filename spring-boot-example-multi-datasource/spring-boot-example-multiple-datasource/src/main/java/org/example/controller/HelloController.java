package org.example.controller;

import org.example.dao.ds1.Ds1DbmsMapper;
import org.example.dao.ds2.Ds2DbmsMapper;
import org.example.dao.ds3.Ds3DbmsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private Ds1DbmsMapper ds1DbmsMapper;

    @Autowired
    private Ds2DbmsMapper ds2DbmsMapper;

    @Autowired
    private Ds3DbmsMapper ds3DbmsMapper;

    @RequestMapping("/ds1")
    public String ds1() {
        return ds1DbmsMapper.getDbInfo().toString();
    }

    @RequestMapping("/ds2")
    public String ds2() {
        return ds2DbmsMapper.getDbInfo().toString();
    }

    @RequestMapping("/ds3")
    public String ds3() {
        return ds3DbmsMapper.getDbInfo().toString();
    }

}
