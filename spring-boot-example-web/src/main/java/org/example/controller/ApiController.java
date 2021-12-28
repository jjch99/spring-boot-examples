package org.example.controller;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.example.utils.JsonUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
public class ApiController {

    @RequestMapping(value = "/api/hello", method = { RequestMethod.GET, RequestMethod.POST })
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

        Map resp = new LinkedHashMap();
        resp.put("code", "200");
        resp.put("msg", "成功");
        resp.put("data", ImmutableMap.of("status", "OK"));
        return JsonUtils.toJson(resp);

    }

}
