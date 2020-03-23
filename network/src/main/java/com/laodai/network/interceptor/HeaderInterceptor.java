package com.laodai.network.interceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 21:58 2020-02-23
 * @ Description：请求头拦截器（统一添加请求头）
 * @ Modified By：
 * @Version: ：1.0
 */
public abstract class HeaderInterceptor implements Interceptor {


    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Map<String, String> headers = buildHeaders();
        if (headers == null || headers.isEmpty()) {
            return chain.proceed(request);
        } else {
            Response response = chain.proceed(request.newBuilder()
                    .headers(buildHeaders(request, headers))
                    .build());
            return response;
        }
    }

    private Headers buildHeaders(Request request, Map<String, String> headMap) {
        Headers headers = request.headers();
        if (headers != null) {
            Headers.Builder builder = headers.newBuilder();
            for (String key : headMap.keySet()) {
                builder.add(key, headMap.get(key));
            }
            return builder.build();
        } else {
            return headers;
        }
    }

    protected abstract Map<String, String> buildHeaders();
}
