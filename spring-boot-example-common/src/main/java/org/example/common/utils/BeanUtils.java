package org.example.common.utils;

import net.sf.cglib.beans.BeanCopier;

public class BeanUtils {

    public static final void copy(Object source, Object target) {
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        copier.copy(source, target, null);
    }

    public static final <T> T clone(T obj) {
        try {
            T inst = (T) obj.getClass().newInstance();
            copy(obj, inst);
            return inst;
        } catch (Exception e) {
            return null;
        }
    }

}
