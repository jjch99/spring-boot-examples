package org.example.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

/**
 * https://www.cnblogs.com/three-fighter/p/12945142.html
 */
public class JacksonTest {

    @Test
    public void testCase1() throws Exception {

        Map map = new HashMap();
        map.put("a", 1);
        map.put("b", "2");
        map.put("c", ImmutableMap.builder().put("c1", 3).build());
        map.put("d", Lists.newArrayList("d1", "d2"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(map);
        System.out.println(jsonStr);

        Map map1 = mapper.readValue(jsonStr, HashMap.class);
        System.out.println(map1);

        JsonNode jsonNode = mapper.readTree(jsonStr);
        System.out.println(jsonNode);
        System.out.println(jsonNode.get("a").toString());
        System.out.println(jsonNode.findValue("b"));
    }

}
