package org.example.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "custom-redis-config", havingValue = "true")
public class RedisConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig redisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(RedisProperties redisProperties) {
        JedisPoolConfig config = redisPoolConfig();
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setDatabase(redisProperties.getDatabase());
        factory.setHostName(redisProperties.getHost());
        factory.setPassword(redisProperties.getPassword());
        factory.setPort(redisProperties.getPort());
        factory.setPoolConfig(config);
        log.info("JedisConnectionFactory init success.");
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisProperties redisProperties) {
        JedisConnectionFactory connectionFactory = jedisConnectionFactory(redisProperties);
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisTemplate redisTemplate) {
        return new StringRedisTemplate(redisTemplate.getConnectionFactory());
    }

}
