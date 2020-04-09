package com.laodai.mvp.utils;

import java.lang.reflect.ParameterizedType;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/05
 *     desc   : 反射获取指定泛型
 *     version: 1.0
 * </pre>
 */
public class ReflectUtil {

    public static <T> T getT(Object obj, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (obj.getClass().getGenericSuperclass()))
                    .getActualTypeArguments()[i]).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
