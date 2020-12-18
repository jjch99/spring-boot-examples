package org.example.common.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * https://github.com/json-path/JsonPath
 */
public class JsonPathTest {

    @Test
    public void testCase1() {
        Map book1 = ImmutableMap.builder()
                .put("category", "reference")
                .put("author", "张三")
                .put("title", "葵花宝典")
                .put("price", 9.9)
                .build();

        Map book2 = ImmutableMap.builder()
                .put("category", "reference")
                .put("author", "李四")
                .put("title", "九阳神功")
                .put("price", 8.8)
                .build();

        Map store = ImmutableMap.builder()
                .put("book", Lists.newArrayList(book1, book2))
                .put("bicycle", ImmutableMap.of("color", "red"))
                .build();

        Map map = ImmutableMap.of("store", store);
        String json = new Gson().toJson(map);
        System.out.println(json);

        List<String> authors = JsonPath.read(json, "$.store.book[*].author");
        System.out.println(authors);

        Object obj = JsonPath.read(json, "$.store.book");
        System.out.println(obj.getClass().getCanonicalName());
        System.out.println(obj);

    }

}
