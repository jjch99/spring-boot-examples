package org.example.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonTest {

    @Test
    public void testCase1() throws Exception {
        Map map = new HashMap();
        map.put("a", 1);
        map.put("b", "2");
        map.put("c", ImmutableMap.builder().put("c1", 3).build());
        map.put("d", Lists.newArrayList("d1", "d2"));

        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        System.out.println(jsonStr);

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonStr);
        System.out.println(element);

        if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            System.out.println("a: " + object.get("a").getAsInt());
            System.out.println("b: " + object.get("b").getAsString());

            JsonArray d = object.getAsJsonArray("d");
            for (int i = 0; i < d.size(); i++) {
                String hobby = d.get(i).getAsString();
                System.out.println("d: " + hobby);
            }

            JsonObject c = object.getAsJsonObject("c");
            System.out.println("c1: " + c.get("c1").getAsString());
        }
    }

}
