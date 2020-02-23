package com.laodai.library.config;

import android.content.Context;

import com.laodai.library.cookie.store.CookieStore;
import com.laodai.library.interfaces.HeadersListener;

import java.io.InputStream;

import javax.net.ssl.HostnameVerifier;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:52 2020-02-23
 * @ Description：统一OkHttp配置信息
 * @ Modified By：
 * @Version: ：1.0
 */
public class OkHttpConfig {
    //默认缓存大小
    private static final long defaultCacheSize = 1024 * 1024 * 100;
    //默认超时时间
    private static final long defaultTimeout = 10;
    //默认缓存路径
    private static String defaultCachePath;
    //单例
    private static OkHttpConfig instance;
    //OkHttpClient.Builder对象
    private static OkHttpClient.Builder mOkHttpClientBuilder;
    //OkHttpClient对象
    private static OkHttpClient mOkHttpClient;

    public OkHttpConfig() {
        mOkHttpClientBuilder = new OkHttpClient.Builder();
    }

    /**
     * 单例模式
     *
     * @return OkHttpConfig
     */
    public static OkHttpConfig getInstance() {
        if (instance == null) {
            synchronized (OkHttpConfig.class) {
                if (instance == null) {
                    instance = new OkHttpConfig();
                }
            }
        }
        return instance;
    }

    /**
     * 获取 getOkHttpClient
     *
     * @return OkHttpClient
     */
    public OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            return mOkHttpClientBuilder.build();
        } else {
            return mOkHttpClient;
        }
    }

    /**
     * 静态内部类
     */
    public static class Builder {
        //上下文
        public Context mContext;
        private boolean isDebug;
        private boolean isCache;
        //缓存时间
        private int cacheTime = 60;
        //无网络获取缓存时间
        private int noNetCacheTime = 10;
        //缓存路径
        private String cachePath;
        //缓存最大数量
        private long cacheMaxSize;
        //Cookie的公共接口
        private CookieStore mCookieStore;
        //读取超时时间
        private long readTimeout;
        //写入超时时间
        private long writeTimeout;
        //连接超时时间
        private long connectTimeout;
        private InputStream bksFile;
        //密码
        private String password;
        private InputStream[] certificates;
        //拦截器数组
        private Interceptor[] mInterceptors;
        //请求头接口
        private HeadersListener mHeadersListener;
        //主机名验证基接口
        private HostnameVerifier mHostnameVerifier;
    }
}
