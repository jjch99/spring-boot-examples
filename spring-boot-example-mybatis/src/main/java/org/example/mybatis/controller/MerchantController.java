package org.example.mybatis.controller;

import java.util.List;

import org.example.mybatis.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    @RequestMapping("/info")
    public Object info(Long id) {
        return merchantService.info(id);
    }

    @RequestMapping("/list")
    public List list() {
        return merchantService.list();
    }

}
