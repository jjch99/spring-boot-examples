package org.example.config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.example.constants.CacheNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CacheConfig {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${spring.cache.ehcache.config:classpath:/ehcache.xml}")
    private String ehcacheConfig;

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        redisCacheManager.setDefaultExpiration(300); // 单位：秒
        List<String> cacheNames = Lists.newArrayList();
        cacheNames.add(CacheNames.REDIS);
        // cacheNames.add("reids-a");
        // cacheNames.add("reids-b");
        redisCacheManager.setCacheNames(cacheNames);
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
    public GuavaCacheManager guavaCacheManager() {
        GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
        CacheBuilder cacheBuilder = CacheBuilder.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(10, TimeUnit.SECONDS);
        guavaCacheManager.setCacheBuilder(cacheBuilder);

        List<String> cacheNames = Lists.newArrayList();
        cacheNames.add(CacheNames.LOCAL);
        // cacheNames.add("local-a");
        guavaCacheManager.setCacheNames(cacheNames);
        return guavaCacheManager;
    }

    @Bean
    @Primary
    public CacheManager cacheManager() {
        List<CacheManager> cacheManagers = Lists.newArrayList();
        cacheManagers.add(redisCacheManager());
        cacheManagers.add(ehCacheCacheManager());
        cacheManagers.add(guavaCacheManager());

        CompositeCacheManager cacheManager = new CompositeCacheManager();
        cacheManager.setCacheManagers(cacheManagers);
        cacheManager.setFallbackToNoOpCache(true);
        return cacheManager;
    }

    // @Bean
    public CacheManager simpleCacheManager() {

        // RedisCache redisCache = new RedisCache(CacheNames.REDIS, null, redisTemplate, 0L);

        com.google.common.cache.Cache gCache = CacheBuilder.newBuilder()
                .maximumSize(2000)
                .expireAfterWrite(20, TimeUnit.SECONDS)
                .build();
        GuavaCache guavaCache = new GuavaCache(CacheNames.LOCAL, gCache);

        List<org.springframework.cache.Cache> cacheList = Lists.newArrayList();
        cacheList.add(guavaCache);
        // cacheList.add(redisCache);

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(cacheList);
        return cacheManager;

    }

}
