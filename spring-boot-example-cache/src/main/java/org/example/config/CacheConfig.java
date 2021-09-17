package org.example.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.example.constants.CacheNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CacheConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Value("${spring.cache.ehcache.config:classpath:/ehcache.xml}")
    private String ehcacheConfig;

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(120));

        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(cacheWriter)
                .cacheDefaults(redisCacheConfiguration)
                .build();

        return redisCacheManager;
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(ehcacheConfig);
        factoryBean.setConfigLocation(resource);
        return factoryBean;
    }

    @Bean
    public EhCacheCacheManager ehCacheCacheManager() {
        net.sf.ehcache.CacheManager cacheManager = ehCacheManagerFactoryBean().getObject();
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(cacheManager);
        return ehCacheCacheManager;
    }

    @Bean
    public CaffeineCacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .initialCapacity(1000)
                .maximumSize(10000));

        List<String> cacheNames = Lists.newArrayList();
        cacheNames.add(CacheNames.LOCAL);
        // cacheNames.add("local-a");
        cacheManager.setCacheNames(cacheNames);
        return cacheManager;
    }

    @Bean
    @Primary
    public CacheManager cacheManager() {
        List<CacheManager> cacheManagers = Lists.newArrayList();
        cacheManagers.add(redisCacheManager());
        cacheManagers.add(ehCacheCacheManager());
        cacheManagers.add(caffeineCacheManager());

        CompositeCacheManager cacheManager = new CompositeCacheManager();
        cacheManager.setCacheManagers(cacheManagers);
        cacheManager.setFallbackToNoOpCache(true);
        return cacheManager;
    }

    // @Bean
    public CacheManager simpleCacheManager() {

        Cache<Object, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .initialCapacity(1000)
                .maximumSize(10000)
                .build();

        ArrayList<CaffeineCache> caches = Lists.newArrayList();

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);
        return cacheManager;

    }

}
