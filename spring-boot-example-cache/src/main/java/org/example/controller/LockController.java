package org.example.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
public class LockController {

    private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/lock")
    public String lock() {

        String lockKey = "1234567890";
        String uuid = UUID.randomUUID().toString();
        boolean lockSuccess = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid);
        if (!lockSuccess) {
            return "fail";
        }

        String script = RELEASE_LOCK_LUA_SCRIPT;
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);

        Long result = redisTemplate.execute(redisScript, Lists.newArrayList(lockKey), uuid);
        log.info("result {}", result);

        return "OK";
    }

}
