package org.example.common.utils;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.jxpath.JXPathContext;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JXPathTest {

    @Test
    public void testCase1() throws Exception {
        Map map = new HashMap();
        map.put("a", 1);
        map.put("b", ImmutableMap.builder().put("b1", "b1-value").build());
        System.out.println(map);

        JXPathContext context = JXPathContext.newContext(map);
        String b1 = (String) context.getValue("//b/b1");
        System.out.println(b1);
    }

}
