package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/hello")
    public Object hello() {
        String sql = "select count(*) from system_param";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

}
