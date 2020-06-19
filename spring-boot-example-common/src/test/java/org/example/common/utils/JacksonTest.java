package org.example.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * https://www.cnblogs.com/three-fighter/p/12945142.html
 */
@Slf4j
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
        log.info(jsonStr);

        Map map1 = mapper.readValue(jsonStr, HashMap.class);
        log.info(map1.toString());

        JsonNode jsonNode = mapper.readTree(jsonStr);
        log.info(jsonNode.toPrettyString());
        log.info("a=" + jsonNode.get("a").toString());
        log.info("c1=" + jsonNode.findValue("c1"));
        log.info("/c/c1=" + jsonNode.at("/c/c1").toString());

        updateValue(jsonNode, "/c/c1", "Hello");
        log.info(jsonNode.toPrettyString());
    }

    /**
     * 修改指定节点的值
     * 
     * @param root
     * @param path
     * @param value
     * @return
     */
    public static boolean updateValue(JsonNode root, String path, String value) {
        String[] elements = StringUtils.split(path, '/');
        JsonNode parent = root;
        JsonNode node = root;
        String nodeName = null;
        for (String element : elements) {
            if (!node.has(element)) {
                return false;
            }
            nodeName = element;
            parent = node;
            node = parent.get(nodeName);
        }

        ObjectNode parentNode = (ObjectNode) parent;
        parentNode.put(nodeName, value);
        return true;

    }

}
