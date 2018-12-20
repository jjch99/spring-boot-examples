package org.example.utils;

import java.lang.reflect.Field;
import java.util.*;

import org.mockito.Mockito;

public class TestUtils {

    private static final Map<Class<?>, Object> DEFAULTS;

    static {
        Map<Class<?>, Object> map = new HashMap<>();
        map.put(boolean.class, false);
        map.put(byte.class, (byte) 0);
        map.put(char.class, '\0');
        map.put(short.class, 0);
        map.put(int.class, 0);
        map.put(long.class, 0L);
        map.put(float.class, 0);
        map.put(double.class, 0);

        map.put(Boolean.class, false);
        map.put(Byte.class, (byte) 0);
        map.put(Character.class, '\0');
        map.put(Short.class, 0);
        map.put(Integer.class, 0);
        map.put(Long.class, 0L);
        map.put(Float.class, 0);
        map.put(Double.class, 0);

        map.put(String.class, "");
        map.put(Date.class, new Date(0L));
        map.put(List.class, new ArrayList(0));
        map.put(Map.class, new HashMap<>(0));
        DEFAULTS = Collections.unmodifiableMap(map);
    }

    private static Object getDefaultValue(Class<?> clazz) {
        return DEFAULTS.get(clazz);
    }

    public static <T> T newInstance(Class<T> clazz) {
        Object obj;
        try {
            obj = clazz.newInstance();
        } catch (Exception e) {
            return null;
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                Class fieldType = field.getType();
                Object fieldValue = getDefaultValue(fieldType);
                if (fieldValue == null) {
                    fieldValue = Mockito.mock(fieldType);
                }
                field.setAccessible(true);
                field.set(obj, fieldValue);
            } catch (Exception e) {
                // ignore
            }
        }
        return (T) obj;
    }

}
