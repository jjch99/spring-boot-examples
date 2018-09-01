package org.example.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ApiController {

    @RequestMapping("/api/hello")
    public Object hello(HttpServletRequest request) {

        try {
            log.info("----Parameters----");
            Enumeration params = request.getParameterNames();
            while (params.hasMoreElements()) {
                String name = (String) params.nextElement();
                log.info(name + ": " + request.getParameter(name));
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        JSONObject resp = new JSONObject();
        resp.put("code", "200");
        resp.put("msg", "成功");
        JSONObject data = new JSONObject();
        data.put("status", "OK");
        resp.put("data", data);
        return resp;

    }

}
