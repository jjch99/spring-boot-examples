package org.example.common.utils;

import java.util.List;

import org.example.common.domain.Person;
import org.junit.Assert;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class FastJsonTest {

    @Test
    public void testParseEmptyArray() {

        List<Person> list = JSON.parseArray("", Person.class);
        Assert.assertNull(list);

        list = JSON.parseArray("[]", Person.class);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());

    }

}
