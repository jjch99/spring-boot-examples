package org.example.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.HashMap;
import java.util.Map;

public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        // 在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 日期类型序列化时默认转成时间戳
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        // 在序列化时忽略值为 null 的属性
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 忽略值为默认值的属性
        // OBJECT_MAPPER.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
        // 缩进/格式化
        // OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(text, clazz);
        } catch (Exception e) {
            try {
                return clazz.newInstance();
            } catch (Exception ex) {
                return null;
            }
        }
    }

    public static <T> T toObject(String text, Class<T> clazz) {
        return parseObject(text, clazz);
    }

    public static Map toMap(String text) {
        return parseObject(text, HashMap.class);
    }

    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static String toJsonPretty(Object obj) {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            return null;
        }
    }

}
