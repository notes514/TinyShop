package com.laodai.library.config;

import android.content.Context;
import android.text.TextUtils;

import com.laodai.library.cookie.CookieJarImpl;
import com.laodai.library.cookie.store.CookieStore;
import com.laodai.library.http.SSLUtils;
import com.laodai.library.interceptor.HeaderInterceptor;
import com.laodai.library.interceptor.NetCacheInterceptor;
import com.laodai.library.interceptor.NoNetCacheInterceptor;
import com.laodai.library.interceptor.RxHttpLogger;
import com.laodai.library.interfaces.HeadersListener;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:52 2020-02-23
 * @ Description：统一OkHttp配置信息
 * @ Modified By：
 * @Version: ：1.0
 */
public class OkHttpConfig {
    //默认缓存大小（100MB）
    private static final long defaultCacheSize = 1024 * 1024 * 100;
    //默认超时时间（10秒）
    private static final long defaultTimeout = 10;
    //单例
    private static OkHttpConfig instance;
    //OkHttpClient.Builder对象
    private static OkHttpClient.Builder mOkHttpClientBuilder;
    //OkHttpClient对象
    private static OkHttpClient mOkHttpClient;

    private OkHttpConfig() {
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
        Context mContext;
        private boolean isDebug;
        private boolean isCache;
        //缓存时间（60秒）
        private int cacheTime = 60;
        //无网络获取缓存时间（10秒）
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

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setDebug(boolean isDebug) {
            this.isDebug = isDebug;
            return this;
        }

        public Builder setCache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        public Builder setHasNetCacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public Builder setNoNetCacheTime(int noNetCacheTime) {
            this.noNetCacheTime = noNetCacheTime;
            return this;
        }

        public Builder setCachePath(String cachePath) {
            this.cachePath = cachePath;
            return this;
        }

        public Builder setCacheMaxSize(long cacheMaxSize) {
            this.cacheMaxSize = cacheMaxSize;
            return this;
        }

        public Builder setCookieType(CookieStore cookieStore) {
            this.mCookieStore = cookieStore;
            return this;
        }

        public Builder setReadTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setAddInterceptor(Interceptor... interceptors) {
            this.mInterceptors = interceptors;
            return this;
        }

        public Builder setSslSocketFactory(InputStream... certificates) {
            this.certificates = certificates;
            return this;
        }

        public Builder setSslSocketFactory(InputStream bksFile, String password,
                                           InputStream... certificates) {
            this.bksFile = bksFile;
            this.password = password;
            this.certificates = certificates;
            return this;
        }

        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.mHostnameVerifier = hostnameVerifier;
            return this;
        }

        public Builder setHeaders(HeadersListener headersListener) {
            this.mHeadersListener = headersListener;
            return this;
        }

        public OkHttpClient build() {
            //初始化OkHttpConfig单例
            OkHttpConfig.getInstance();
            //设置相关配置
            setCookieConfig();
            setCacheConfig();
            setHeadersConfig();
            setSslConfig();
            setHostnameVerifier();
            addInterceptors();
            setTimeout();
            setDebugConfig();
            //初始化 OkHttpClient
            mOkHttpClient = mOkHttpClientBuilder.build();
            return mOkHttpClient;
        }

        /**
         * 添加拦截器
         */
        private void addInterceptors() {
            if (mInterceptors != null) {
                for (Interceptor interceptor : mInterceptors) {
                    mOkHttpClientBuilder.addInterceptor(interceptor);
                }
            }
        }

        /**
         * 配置开发环境
         */
        private void setDebugConfig() {
            if (isDebug) {
                //创建请求日志拦截器对象
                HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new RxHttpLogger());
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //添加拦截器
                mOkHttpClientBuilder.addInterceptor(logInterceptor);
            }
        }

        /**
         * 配置headers
         */
        private void setHeadersConfig() {
            if (mHeadersListener != null) {
                mOkHttpClientBuilder.addInterceptor(new HeaderInterceptor() {
                    @Override
                    protected Map<String, String> buildHeaders() {
                        return mHeadersListener.buildHeaders();
                    }
                });
            }
        }

        /**
         * 配置cookie保存到sp文件中
         */
        private void setCookieConfig() {
            if (mCookieStore != null) {
                mOkHttpClientBuilder.cookieJar(new CookieJarImpl(mCookieStore));
            }
        }

        /**
         * 配置缓存
         */
        private void setCacheConfig() {
            //创建file获取手机外部存储的默认路径
            File externalCacheDir = mContext.getExternalCacheDir();
            if (externalCacheDir == null) return;
            //默认缓存路径
            String defaultCachePath = externalCacheDir + "RxHttpCacheData";
            if (isCache) {
                Cache cache;
                if (!TextUtils.isEmpty(cachePath) && cacheMaxSize > 0) {
                    cache = new Cache(new File(cachePath), cacheMaxSize);
                } else {
                    cache = new Cache(new File(defaultCachePath), defaultCacheSize);
                }
                //设置缓存
                mOkHttpClientBuilder
                        .cache(cache)
                        //将无网络的拦截器当做应用拦截器添加
                        .addInterceptor(new NoNetCacheInterceptor(noNetCacheTime))
                        //将有网络的拦截器当做网络拦截器添加
                        .addNetworkInterceptor(new NetCacheInterceptor(cacheTime));
            }

        }

        /**
         * 配置超时信息
         */
        private void setTimeout() {
            mOkHttpClientBuilder.readTimeout(readTimeout == 0 ? defaultTimeout : readTimeout, TimeUnit.SECONDS);
            mOkHttpClientBuilder.writeTimeout(writeTimeout == 0 ? defaultTimeout : writeTimeout, TimeUnit.SECONDS);
            mOkHttpClientBuilder.connectTimeout(connectTimeout == 0 ? defaultTimeout : connectTimeout, TimeUnit.SECONDS);
            mOkHttpClientBuilder.retryOnConnectionFailure(true);
        }

        /**
         * 配置SSL证书
         */
        private void setSslConfig() {
            SSLUtils.SSLParams sslParams = null;
            if (certificates != null) {
                //信任所有证书，不安全有风险
                sslParams = SSLUtils.getSslSocketFactory();
            } else {
                if (bksFile != null && !TextUtils.isEmpty(password)) {
                    //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                    sslParams = SSLUtils.getSslSocketFactory(bksFile, password, certificates);
                } else {
                    //使用预埋证书，校验服务端证书（自签名证书）
                    sslParams = SSLUtils.getSslSocketFactory(certificates);
                }
            }
            //设置信任管理器（不设置则使用默认值）
            mOkHttpClientBuilder.sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager);
        }

        /**
         * 配置主机名验证
         */
        private void setHostnameVerifier() {
            if (mHostnameVerifier == null) {
                mOkHttpClientBuilder.hostnameVerifier(SSLUtils.UnSafeHostnameVerifier);
            } else {
                mOkHttpClientBuilder.hostnameVerifier(mHostnameVerifier);
            }
        }
    }
}
