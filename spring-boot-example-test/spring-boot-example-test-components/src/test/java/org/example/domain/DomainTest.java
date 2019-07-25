package org.example.domain;

import java.util.List;

import org.example.utils.TestUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DomainTest {

    @Test
    public void createInstance() {
        Person person = TestUtils.mock(Person.class);
        log.info(JSON.toJSONString(person));

        List<Person> list = TestUtils.newArrayList(Person.class);
        log.info(JSON.toJSONString(list));

        person.setName("foo");
        list.add(person);
        log.info(JSON.toJSONString(list));
    }

}
