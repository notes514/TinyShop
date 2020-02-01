package com.laodai.library.cookie.store;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:18 2020-01-30
 * @ Description：Cookie的公共接口
 * @ Modified By：
 * @Version: ：1.0
 */
public interface CookieStore {

    /**
     * 保存URL对应所有的cookie
     *
     * @param url URI
     * @param cookies cookies
     */
    void saveCookie(HttpUrl url, List<Cookie> cookies);

    /**
     * 保存URL所对应的cookie
     *
     * @param url URL
     * @param cookie cookie
     */
    void saveCookie(HttpUrl url, Cookie cookie);

    /**
     * 加载URL所有的cookie
     *
     * @param url URL
     * @return
     */
    List<Cookie> loadCookie(HttpUrl url);

    /**
     * 获取当前所有保存的cookie
     *
     * @return Cookie
     */
    List<Cookie> getAllCookie();

    /**
     * 获取当前URL对应的所有cookie
     *
     * @param url URL
     * @return Cookie
     */
    List<Cookie> getCookie(HttpUrl url);

    /**
     * 根据URL和cookie移除对应的cookie
     *
     * @param url url
     * @param cookie cookie
     * @return boolean
     */
    boolean removeCookie(HttpUrl url, Cookie cookie);

    /**
     * 根据URL移除所有的cookie
     *
     * @param url url
     * @return boolean
     */
    boolean removeCookie(HttpUrl url);

    /**
     * 根据URL移除所有的cookie
     *
     * @return boolean
     */
    boolean removeAllCookie();

}