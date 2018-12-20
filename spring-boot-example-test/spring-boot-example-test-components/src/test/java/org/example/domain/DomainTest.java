package org.example.domain;

import org.example.utils.TestUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainTest {

    @Test
    public void createInstance() {
        Person person = TestUtils.newInstance(Person.class);
        log.info(JSON.toJSONString(person));
    }

}
