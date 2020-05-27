package org.example.common.utils;

import java.util.List;

import org.example.common.domain.Person;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtilsTest {

    @Test
    public void createInstance() {
        Person person = TestUtils.mock(Person.class);
        log.info(JsonUtils.toJson(person));

        List<Person> list = TestUtils.newArrayList(Person.class);
        log.info(JsonUtils.toJson(list));

        person.setName("foo");
        list.add(person);
        log.info(JsonUtils.toJson(list));
    }

}
