package com.laodai.library.download;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 21:54 2020-01-30
 * @ Description：取消Gzip压缩，Content-Length便是正常数据，
 *                否则有的接口通过Gzip压缩Content-Length返回为-1
 * @ Modified By：
 * @Version: ：1.0
 */
public class DownLoadInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request.newBuilder()
                .addHeader("Accept-Encoding", "identity")
                .build());
        return response;
    }

}
