package com.laodai.library.manger;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:56 2020-01-29
 * @ Description：多域名管理类
 * @ Modified By：
 * @Version: ：1.0
 */
public class RxUrlManger {
    //单例
    private volatile static RxUrlManger instance;
    //默认URL的key
    public static String DEFAULT_URL_KEY = "default_url_key";
    //mapURL
    private Map<String, String> urlMap;

    /**
     * 无参构造，初始化map管理URL
     */
    private RxUrlManger() {
        urlMap = new HashMap<>();
    }

    /**
     * 单例模式
     *
     * @return instance
     */
    public static RxUrlManger getInstance() {
        if (instance == null) {
            synchronized (RxUrlManger.class) {
                if (instance == null) {
                    instance = new RxUrlManger();
                }
            }
        }
        return instance;
    }

    /**
     * 一次性传入urlMap
     *
     * @param urlMap urlMap
     * @return this
     */
    public RxUrlManger setMultipleUrl(Map<String, String> urlMap) {
        this.urlMap = urlMap;
        return this;
    }

    /**
     * 向map中添加URL
     *
     * @param urlKey 键
     * @param urlValue 值
     * @return this
     */
    public RxUrlManger addUrl(String urlKey, String urlValue) {
        urlMap.put(urlKey, urlValue);
        return this;
    }

    /**
     * 从map中删除某个URL
     *
     * @param urlKey 建
     * @return this
     */
    public RxUrlManger removeUrlByKey(String urlKey) {
        urlMap.remove(urlKey);
        return this;
    }

    /**
     * 获取全局唯一的BaseUrl
     *
     * @return string
     */
    public String getBaseUrl() {
        return urlMap.get(DEFAULT_URL_KEY);
    }

    /**
     * 针对单个baseUrl切换的时候清空老的baseUrl的所有信息
     *
     * @param urlValue 新传入的baseUrl
     * @return this
     */
    public RxUrlManger setBaseUrl(String urlValue) {
        urlMap.put(DEFAULT_URL_KEY, urlValue);
        return this;
    }

    /**
     * 根据keyUrl获取对应的valueUrl
     *
     * @param urlKey 键
     * @return this
     */
    public String getUrlByKey(String urlKey) {
        return urlMap.get(urlKey);
    }

    /**
     * 清空设置的URL相关的所有信息(相当于重用URL)
     * 动态切换生产测试环境的时候调用
     *
     * @return RxUrlManger
     */
    public RxUrlManger clear() {
        urlMap.clear();
        return this;
    }
}
