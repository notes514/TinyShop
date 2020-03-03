package com.laodai.library.interceptor;

import com.laodai.library.utils.NetUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:03 2020-02-23
 * @ Description：网络缓存拦截器（参考 https://www.jianshu.com/p/cf59500990c7）
 * @ Modified By：
 * @Version: ：1.0
 */
public class NetCacheInterceptor implements Interceptor {
    //默认缓存60秒
    private int cacheTime = 60;

    public NetCacheInterceptor(int cacheTime) {
        this.cacheTime = cacheTime;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        boolean connected = NetUtils.isNetworkConnected();
        //判断是否有网络
        if (connected) {
            //如果有网络
            Response response = chain.proceed(request);
            CacheControl.Builder builder = new CacheControl.Builder()
                    .maxAge(cacheTime, TimeUnit.SECONDS);

            return response.newBuilder()
                    .header("Cache-Control", builder.build().toString())
                    .removeHeader("Pragma")
                    .build();
        }
        //如果没有网络，则不作处理，直接返回
        return chain.proceed(request);
    }

}
