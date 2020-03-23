package com.laodai.tinyshop.url;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:32 2020-03-23
 * @ Description：url配置类
 * @ Modified By：
 * @Version: ：1.0
 */
public class AppUrlConfig {
    public static String DOUBAN_KEY = "douban_api";
    public static String OTHER_OPEN_KEY = "other_open_api";
    public static String WANANDROID_KET = "wanandroid_api";

    public static String DOUBAN_URL = "https://api.douban.com/";
    public static String OTHER_OPEN_URL = "http://www.mxnzp.com/api/";
    public static String WANANDROID_URL = "https://www.wanandroid.com/";

    public static Map<String, String> getAllUrl() {
        Map<String, String> map = new HashMap<>();
        map.put(DOUBAN_KEY, DOUBAN_URL);
        map.put(OTHER_OPEN_KEY, OTHER_OPEN_URL);
        map.put(WANANDROID_KET, WANANDROID_URL);
        return map;
    }
}
