package com.laodai.network.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 09:19 2020-03-10
 * @ Description：Json转换工具类
 * @ Modified By：
 * @Version: ：1.0
 */
public class GsonUtils {

    /**
     * 将json数据转换为对象
     *
     * @param jsonStr json
     * @param tClass 转换后的类对象
     * @param <T> 泛型
     * @return T
     */
    public static <T> T getObject(String jsonStr, Class<T> tClass) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonStr, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将Json数据转换为List<Object>集合
     *
     * @param jsonStr json数据
     * @param tClass 转换后的类对象
     * @param <T> 泛型
     * @return List<T>
     */
    public static <T> List<T> getArray(String jsonStr, Class<T> tClass) {
        List<T> tList = new ArrayList<>();
        try {
            Gson gson = new Gson();
            tList = gson.fromJson(jsonStr, new TypeToken<List<T>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tList;
    }

    /**
     * 将json数据转换成List<Map<String, Object>>
     *
     * @param jsonStr json数据
     * @return List<Map<String, Object>>
     */
    public static List<Map<String, Object>> listKeyMaps(String jsonStr) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        try {
            Gson gson = new Gson();
            mapList = gson.fromJson(jsonStr, new TypeToken<List<Map<String, Object>>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapList;
    }

    /**
     * 将json数据转换为Map<String, Object>
     *
     * @param jsonStr json数据
     * @return Map<String, Object>
     */
    public static Map<String, Object> objKeyMaps(String jsonStr) {
        Map<String, Object> objMap = new HashMap<>();
        try {
            Gson gson = new Gson();
            objMap = gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objMap;
    }
}
