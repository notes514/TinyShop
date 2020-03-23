package com.laodai.network.utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 11:04 2020-03-10
 * @ Description：1.根据不同后台要求对post请求进行加工处理
 *                2.根据编写需求编写自己的map操作类
 * @ Modified By：
 * @Version: ：1.0
 */
public class ParamsUtils {
    //map操作
    private Map<String, Object> params;

    /**
     * 添加键值对
     * TreeMap：该映射根据其键的自然顺序进行排序
     *
     * @param key 键
     * @param value 值
     * @return 当前对象
     */
    public ParamsUtils addParams(String key, Object value) {
        if (params == null) {
            params = new TreeMap<>();
        }
        params.put(key, value);
        return this;
    }

    /**
     * 获取map
     *
     * @return Map
     */
    public Map getParams() {
        if (params == null) return null;
        return params;
    }

    /**
     * 清空map
     */
    public void clearParams() {
        if (params != null) params.clear();
    }
}
