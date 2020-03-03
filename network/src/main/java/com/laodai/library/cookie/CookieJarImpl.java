package com.laodai.library.cookie;

import com.laodai.library.cookie.store.CookieStore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 21:59 2020-01-30
 * @ Description：CookieJar的实现类，默认管理了用户自己维护的cookie
 * @ Modified By：
 * @Version: ：1.0
 */
public class CookieJarImpl implements CookieJar {

    private CookieStore cookieStore;

    /**
     * 参数构造
     *
     * @param cookieStore
     */
    public CookieJarImpl(CookieStore cookieStore) {
        if (cookieStore == null) {
            throw new IllegalArgumentException("cookieStore can not be null!");
        }
        this.cookieStore = cookieStore;
    }

    @NotNull
    @Override
    public synchronized List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
        return cookieStore.loadCookie(httpUrl);
    }

    @Override
    public synchronized void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
        cookieStore.saveCookie(httpUrl, list);
    }

    /**
     * 获取cookieStore
     *
     * @return CookieStore
     */
    public CookieStore getCookieStore() {
        return cookieStore;
    }

}
