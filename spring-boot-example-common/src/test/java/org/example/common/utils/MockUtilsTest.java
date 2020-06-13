package org.example.common.utils;

import java.util.List;

import org.example.common.domain.Person;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockUtilsTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createInstance() throws Exception {
        Person person = MockUtils.mock(Person.class);
        log.info(mapper.writeValueAsString(person));

        List<Person> list = MockUtils.newArrayList(Person.class);
        log.info(mapper.writeValueAsString(list));

        person.setName("foo");
        list.add(person);
        log.info(mapper.writeValueAsString(list));
    }

}
