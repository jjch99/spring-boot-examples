package org.example.redis;

import redis.embedded.RedisServer;

public class RedisServerMock {

    public static void main(String[] args) {
        try {
            RedisServer redisServer = RedisServer.builder()
                    .port(6379)
                    .setting("bind 127.0.0.1")
                    .setting("tcp-keepalive 60")
                    .setting("loglevel notice")
                    .setting("logfile \"\"")
                    .setting("databases 1")
                    .setting("save \"\"") // 不做持久化
                    .setting("maxmemory 2147483648") // 最大内存2G
                    .setting("maxmemory-policy volatile-lru") // 使用LRU算法移除包含过期设置的key
                    .build();
            redisServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
