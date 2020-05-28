package org.example.common.utils;

import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

public class JPathUtils {

    public static Object set(Map root, String path, Object val) {
        throw new NotImplementedException("set");
    }

    public static Object remove(Map root, String path) {
        throw new NotImplementedException("remove");
    }

    public static Object get(Map root, String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }

        path = StringUtils.removeStart(path, "/");
        path = StringUtils.removeEnd(path, "/");
        if (!StringUtils.contains(path, "/")) {
            return root.get(path);
        }

        Map<String, Object> map = root;
        String[] subPaths = StringUtils.splitByWholeSeparator(path, "/");
        for (int i = 0; i < subPaths.length; i++) {
            Object o = map.get(subPaths[i]);
            if (i == subPaths.length - 1) {
                return o;
            }
            if (o instanceof Map) {
                map = (Map) o;
            } else {
                return null;
            }
        }
        return null;
    }

    public static String getString(Map root, String path) {
        Object val = get(root, path);
        if (val instanceof String) {
            return (String) val;
        }
        return null;
    }

    public static Integer getInteger(Map root, String path) {
        Object val = get(root, path);
        if (val instanceof Integer) {
            return (Integer) val;
        }
        if (val instanceof String) {
            String s = (String) val;
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static Long getLong(Map root, String path) {
        Object val = get(root, path);
        if (val instanceof Long) {
            return (Long) val;
        }
        if (val instanceof String) {
            String s = (String) val;
            try {
                return Long.parseLong(s);
            } catch (Exception e) {
            }
        }
        return null;
    }

}
