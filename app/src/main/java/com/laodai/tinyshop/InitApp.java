package com.laodai.tinyshop;

import android.app.Application;

import com.laodai.network.RxHttpUtils;
import com.laodai.network.config.OkHttpConfig;
import com.laodai.network.cookie.store.SPCookieStore;
import com.laodai.network.gson.GsonAdapter;
import com.laodai.network.manger.RxUrlManger;
import com.laodai.tinyshop.url.AppUrlConfig;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/04
 *     desc   : 应用入口
 *     version: 1.0
 * </pre>
 */
public class InitApp extends Application {
    //入口日志常量
    public static final String TAG = "InitApp";
    //单例
    private static InitApp instance = null;

    public static InitApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //一个项目配置多个URL
        RxUrlManger.getInstance().setMultipleUrl(AppUrlConfig.getAllUrl());

        RxHttpUtils
                .getInstance()
                .init(this)
                .config()
                //自定义factory
                .setCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .setConverterFactory(ScalarsConverterFactory.create(),
                        GsonConverterFactory.create(GsonAdapter.buildGson()))
                //卑职全局baseUrl
                .setBaseUrl("")
                //开启全局配置
                .setOkClient(createOkHttp());
    }

    /**
     * 配置网络
     * @return OkHttpClient
     */
    private OkHttpClient createOkHttp() {
        OkHttpClient okHttpClient = new OkHttpConfig
                .Builder(this)
                .setHeaders(() -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("token", "token");
                    map.put("other_header", URLEncoder.encode("中文需要转码"));
                    return map;
                })
                //添加自定义拦截器
                .setAddInterceptor()
                //开启缓存策略，默认为false
                .setCache(true)
                .setHasNetCacheTime(10)
                //全局持久化cookie，保存到内存（new MemoryCookieStore()）或者保存到本地（new SPCookieStore(this)）
                //不设置的话默认不对cookie做处理
                .setCookieType(new SPCookieStore(this))
                //全局超时配置
                .setReadTimeout(10)
                //全局超时配置
                .setWriteTimeout(10)
                //全局超时配置
                .setConnectTimeout(10)
                //全局是否打开请求日志
                .setDebug(BuildConfig.DEBUG)
                .build();
        return okHttpClient;
    }
}
