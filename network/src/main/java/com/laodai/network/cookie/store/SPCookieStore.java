package com.laodai.network.cookie.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.laodai.network.cookie.SerializableCookie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 21:45 2020-01-31
 * @ Description：使用 SharedPreferences 持久化存储 cookie
 * @ Modified By：
 * @Version: ：1.0
 */
public class SPCookieStore implements CookieStore {
    //cookie使用prefs保存
    private static final String COOKIE_PREFS = "rx_http_utils_cookie";
    //cookie持久化的统一前缀
    private static final String COOKIE_NAME_PREFIX = "cookie_";

    /**
     * 数据结构如下
     * Url.host -> cookieToken1,cookieToken2,cookieToken3
     * cookie_cookieToken1 - cookie1
     * cookie_cookieToken2 - cookie2
     * cookie_cookieToken3 - cookie3
     */
    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences cookiePrefs;

    public SPCookieStore(Context context) {
        //初始化对象
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE);
        cookies = new HashMap<>();
        //将持久化的cookies缓存到内存中，数据结构为 Map<Url.host, Map<d>
        Map<String, ?> prefsMap = cookiePrefs.getAll();
        //startsWith() 方法用于检测字符串是否以指定的前缀开始。
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()){
            if (entry.getValue() != null && !entry.getKey().startsWith(COOKIE_NAME_PREFIX)) {
                //获取URL对应的所有的cookie的key，用","分割
                String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
                for (String name : cookieNames) {
                    //根据对应的cookie的key，从xml中获取cookie的真实值
                    String encodedCookie = cookiePrefs.getString(COOKIE_NAME_PREFIX + name, null);
                    if (encodedCookie != null) {
                        Cookie decodedCookie = SerializableCookie.decodeCookie(encodedCookie);
                        if (decodedCookie != null) {
                            if (!cookies.containsKey(entry.getKey())) {
                                cookies.put(entry.getKey(), new ConcurrentHashMap<String, Cookie>());
                            }
                            cookies.get(entry.getKey()).put(name, decodedCookie);
                        }
                    }
                }
            }
        }
    }

    /**
     * 检测当前cookie是否过期
     *
     * @param cookie cookie
     * @return boolean
     */
    private static boolean isCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }

    private String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    /**
     * 将URL的所有cookie保存在本地
     *
     * @param url URI
     * @param cookies cookies
     */
    @Override
    public synchronized void saveCookie(HttpUrl url, List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            saveCookie(url, cookie);
        }
    }

    @Override
    public void saveCookie(HttpUrl url, Cookie cookie) {
        if (!cookies.containsKey(url.host())) {
            cookies.put(url.host(), new ConcurrentHashMap<String, Cookie>());
        }
        //当前cookie是否过期
        if (isCookieExpired(cookie)) {
            removeCookie(url, cookie);
        } else {
            saveCookie(url, cookie, getCookieToken(cookie));
        }
    }

    /**
     * 保存cookie，并将cookies持久化到本地
     *
     * @param url url
     * @param cookie cookie
     * @param cookieToken cookieToken
     */
    private void saveCookie(HttpUrl url, Cookie cookie, String cookieToken) {
        //内存缓存
        cookies.get(url.host()).put(cookieToken, cookie);
        //文件缓存
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
        prefsWriter.putString(COOKIE_NAME_PREFIX + cookieToken, SerializableCookie.encodeCookie(url.host(), cookie));
        prefsWriter.apply();
    }

    /**
     * 根据当前URL获取所有需要的cookie，只返回没有过期的cookie
     *
     * @param url URL
     * @return Cookie集合
     */
    @Override
    public List<Cookie> loadCookie(HttpUrl url) {
        List<Cookie> ret = new ArrayList<>();
        //通过containsKey()方法判断HashMap中是否包含此键值
        if (!cookies.containsKey(url.host())) return ret;
        //通过Collection集合获取Cookie集合对象的value
        Collection<Cookie> urlCookies = cookies.get(url.host()).values();
        //循环遍历cookie集合的value
        for (Cookie cookie : urlCookies) {
            //检测每一个cookie是否过期，true为过期，false为不过期
            if (isCookieExpired(cookie)) {
                removeCookie(url, cookie);
            } else {
                ret.add(cookie);
            }
        }
        return ret;
    }

    /**
     * 获取所有的cookie
     *
     * @return Cookie
     */
    @Override
    public List<Cookie> getAllCookie() {
        List<Cookie> ret = new ArrayList<>();
        for (String key : cookies.keySet()) {
            ret.addAll(cookies.get(key).values());
        }
        return ret;
    }

    /**
     * 根据URL获取当前cookie
     *
     * @param url URL
     * @return Cookie集合
     */
    @Override
    public List<Cookie> getCookie(HttpUrl url) {
        List<Cookie> ret = new ArrayList<>();
        Map<String, Cookie> mapCookie = cookies.get(url.host());
        if (mapCookie != null) ret.addAll(mapCookie.values());
        return ret;
    }

    /**
     * 根据URL移除当前的cookie
     *
     * @param url url
     * @param cookie cookie
     * @return boolean
     */
    @Override
    public boolean removeCookie(HttpUrl url, Cookie cookie) {
        //不含值也为真
        if (!cookies.containsKey(url.host())) return false;
        String cookieToken = getCookieToken(cookie);
        if (!cookies.get(url.host()).containsKey(cookieToken)) return false;
        //内存移除
        cookies.get(url.host()).remove(cookieToken);
        //文件移除
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        if (cookiePrefs.contains(COOKIE_NAME_PREFIX + cookieToken)) {
            prefsWriter.remove(COOKIE_NAME_PREFIX + cookieToken);
        }
        prefsWriter.putString(url.host(), TextUtils.join(",", cookies.get(url.host()).keySet()));
        prefsWriter.apply();
        return true;
    }

    @Override
    public boolean removeCookie(HttpUrl url) {
        if (!cookies.containsKey(url.host())) return false;
        //内存移除
        ConcurrentHashMap<String, Cookie> urlCookie = cookies.remove(url.host());
        //文件移除
        Set<String> cookieTokens = urlCookie.keySet();
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        for (String cookieToken : cookieTokens) {
            if (cookiePrefs.contains(COOKIE_NAME_PREFIX + cookieToken)) {
                prefsWriter.remove(COOKIE_NAME_PREFIX + cookieToken);
            }
        }
        prefsWriter.remove(url.host());
        prefsWriter.apply();
        return true;
    }

    @Override
    public boolean removeAllCookie() {
        //内存移除
        cookies.clear();
        //文件移除
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.clear();
        prefsWriter.apply();
        return true;
    }
}
