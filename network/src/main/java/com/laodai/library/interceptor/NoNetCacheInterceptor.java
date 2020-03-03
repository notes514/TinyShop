package com.laodai.library.interceptor;

import com.laodai.library.utils.NetUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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
public class NoNetCacheInterceptor implements Interceptor {
    //无网络时缓存3600秒
    private int noNetCacheTime;

    public NoNetCacheInterceptor(int noNetCacheTime) {
        this.noNetCacheTime = noNetCacheTime;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        boolean connected = NetUtils.isNetworkConnected();
        //如果没有网络，则启用 FORCE_CACHE（强制缓存）
        if (!connected) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            //创建Response对象
            Response response = chain.proceed(request);
            //没网的时候并且没有缓存就走网络
            if (response.code() == 504) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
                return chain.proceed(request);
            }
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale" + noNetCacheTime)
                    .removeHeader("Pragma")
                    .build();
        }
        //有网络的时候，这个拦截器不做处理，直接返回
        return chain.proceed(request);
    }
}
