package com.neteasenews.common.util;

import java.lang.reflect.ParameterizedType;

/**
 * @author Yuan
 * @time 2016/7/26  21:10
 * @desc ${TODD}
 */
public class TUtil {

    public static <T> T getT(Object obj, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (obj.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
