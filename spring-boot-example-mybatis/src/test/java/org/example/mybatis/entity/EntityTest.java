package org.example.mybatis.entity;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.reflect.ClassPath;

public class EntityTest {

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

    private boolean isIgnoredMethod(Method m) {
        if (m.getAnnotation(Test.class) != null) {
            return true;
        }
        Method[] methods = Object.class.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(m.getName())) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void entityTest() throws Exception {
        String packageName = getClass().getPackage().getName();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
            if (info.getName().startsWith(packageName)) {
                if (info.getName().endsWith("Test") || info.getName().endsWith("Tests")) {
                    continue;
                }
                final Class<?> clazz = info.load();
                if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                    continue;
                }
                Object obj = clazz.newInstance();
                Method[] methods = clazz.getMethods();
                for (Method m : methods) {
                    if (!Modifier.isPublic(m.getModifiers()) || isIgnoredMethod(m)) {
                        continue;
                    }
                    if (m.getParameterCount() == 0) {
                        m.invoke(obj);
                    } else if (m.getName().startsWith("set") && m.getParameterCount() == 1) {
                        Class<?>[] paramTypes = m.getParameterTypes();
                        m.invoke(obj, getDefaultValue(paramTypes[0]));
                    }
                }
            }
        }
    }

    @Test
    public void exampleTest() throws Exception {
        String packageName = getClass().getPackage().getName();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
            if (info.getName().startsWith(packageName) && info.getName().endsWith("Example")) {
                final Class<?> clazz = info.load();
                if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                    continue;
                }
                Object obj = clazz.newInstance();
                Method createCriteria = clazz.getMethod("createCriteria");
                Object criteria = createCriteria.invoke(obj);

                Method[] methods = criteria.getClass().getMethods();
                for (Method m : methods) {
                    if (!Modifier.isPublic(m.getModifiers()) || isIgnoredMethod(m)) {
                        continue;
                    }
                    if (m.getParameterCount() == 0) {
                        m.invoke(criteria);
                    } else {
                        Class<?>[] paramTypes = m.getParameterTypes();
                        Object[] args = new Object[paramTypes.length];
                        try {
                            m.invoke(obj, args);
                        } catch (Exception e) {
                            // ignore
                        }
                        for (int i = 0; i < paramTypes.length; i++) {
                            Class<?> paramType = paramTypes[i];
                            args[i] = getDefaultValue(paramType);
                        }
                        m.invoke(criteria, args);
                    }
                }
            }
        }
    }

}
