package org.example.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
public class LimitController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/limit")
    public String limit() {

        String key = "user_limit_20210831_183000";
        Long limit = 100L;
        Long expire = 60L;

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/RateLimit.lua")));
        redisScript.setResultType(Long.class);

        Long result = redisTemplate.execute(redisScript, Lists.newArrayList(key), limit, expire);
        log.info("result {}", result);

        if (result > 0) {
            return "OK";
        }

        return "limit";
    }

}
