package org.example.mybatis.domain;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import org.junit.Test;

import com.google.common.reflect.ClassPath;

public class DomainTest {

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
        map.put(Byte.class, Byte.valueOf("0"));
        map.put(Character.class, '\0');
        map.put(Short.class, 0);
        map.put(Integer.class, 0);
        map.put(Long.class, 0L);
        map.put(Float.class, 0);
        map.put(Double.class, 0);

        map.put(String.class, "");
        map.put(Date.class, new Date(0L));
        map.put(List.class, new ArrayList(0));
        DEFAULTS = Collections.unmodifiableMap(map);
    }

    private Object getDefaultValue(Class<?> clazz) {
        return DEFAULTS.get(clazz);
    }

    @Test
    public void simpleTest() throws Exception {
        String packageName = getClass().getPackage().getName();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
            if (info.getName().startsWith(packageName)) {
                final Class<?> clazz = info.load();
                if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                    continue;
                }
                Object obj1 = clazz.newInstance();
                Object obj2 = clazz.newInstance();
                Method[] methods = clazz.getMethods();
                for (Method m : methods) {
                    if (!Modifier.isPublic(m.getModifiers())) {
                        continue;
                    }
                    if (m.getName().startsWith("get") && m.getParameterCount() == 0) {
                        m.invoke(obj1);
                    } else if (m.getName().startsWith("set") && m.getParameterCount() == 1) {
                        Class<?>[] paramTypes = m.getParameterTypes();
                        m.invoke(obj1, getDefaultValue(paramTypes[0]));
                        m.invoke(obj2, getDefaultValue(paramTypes[0]));
                    }
                }
                obj1.hashCode();
                obj1.equals(obj2);
                obj1.toString();
            }
        }
    }

}
