package org.example.common.utils;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

public class JPathUtilsTest {

    @Test
    public void testCase1() throws Exception {

        Map addr = Maps.newHashMap();
        addr.put("postcode", "100086");

        Map user = Maps.newHashMap();
        user.put("name", "tom");
        user.put("age", 30);
        user.put("addr", addr);

        Map root = Maps.newHashMap();
        root.put("user", user);

        String name = JPathUtils.getString(root, "/user/name");
        System.out.println(name);

        Integer age = JPathUtils.getInteger(root, "/user/age");
        System.out.println(age);

        String postcode = JPathUtils.getString(root, "/user/addr/postcode");
        System.out.println(postcode);

        Long postcode2 = JPathUtils.getLong(root, "/user/addr/postcode");
        System.out.println(postcode2);
    }

}
