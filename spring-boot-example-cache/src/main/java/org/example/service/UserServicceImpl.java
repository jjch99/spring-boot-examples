package org.example.service;

import java.util.HashMap;
import java.util.Map;

import org.example.constants.CacheNames;
import org.example.domain.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Cache注解示例
 * <p>
 * Spring Cache抽象详解<br>
 * http://jinnianshilongnian.iteye.com/blog/2001040
 */
@Slf4j
@Service
public class UserServicceImpl implements UserServicce, InitializingBean {

    private Map<Long, User> userMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        User u1 = new User();
        u1.setId(1L);
        u1.setName("USER1");
        userMap.put(u1.getId(), u1);
    }

    @Override
    @Cacheable(cacheNames = CacheNames.REDIS, key = "T(org.example.constants.CacheKeys).USER_INFO_PREFIX + #id")
    public User get(Long id) {
        log.info("get user-" + id);
        return userMap.get(id);
    }

    @Override
    public User add(User user) {
        return userMap.put(user.getId(), user);
    }

    @Override
    @CachePut(cacheNames = CacheNames.REDIS, key = "T(org.example.constants.CacheKeys).USER_INFO_PREFIX + #user.id")
    public void update(User user) {
        log.info("update user-" + user.getId());
        userMap.put(user.getId(), user);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheNames.REDIS, key = "T(org.example.constants.CacheKeys).USER_INFO_PREFIX + #id"),
            @CacheEvict(value = CacheNames.LOCAL, key = "T(org.example.constants.CacheKeys).USER_INFO_PREFIX + #id")
    })
    // @CacheEvict(cacheNames = CacheNames.LOCAL, allEntries = true)
    public void delete(Long id) {
        log.info("delete user-" + id);
        userMap.remove(id);
    }

    @Cacheable(cacheNames = CacheNames.REDIS, key = "'hello-' + #name")
    public String hello(String name) {
        log.info("hello " + name);
        return "hello " + name;
    }

}
