package com.laodai.library.retrofit;

import com.laodai.library.gson.GsonAdapter;
import com.laodai.library.http.SSLUtils;
import com.laodai.library.interceptor.RxHttpLogger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 16:01 2020-02-02
 * @ Description：自定义RetrofitBuilder对象
 * @ Modified By：
 * @Version: ：1.0
 */
public class RetrofitBuilder {
    //基本URL
    private String baseUrl;
    //CallAdapter工厂
    private CallAdapter.Factory[] callAdapterFactory;
    //Converter工厂
    private Converter.Factory[] converterFactory;
    //OkHttpClient
    private OkHttpClient mOkHttpClient;

    public RetrofitBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public RetrofitBuilder setCallAdapterFactory(CallAdapter.Factory... callAdapterFactory) {
        this.callAdapterFactory = callAdapterFactory;
        return this;
    }

    public RetrofitBuilder setConverterFactory(Converter.Factory... converterFactory) {
        this.converterFactory = converterFactory;
        return this;
    }

    public RetrofitBuilder setOkHttpClient(OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient;
        return this;
    }

    public Retrofit build() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        if (callAdapterFactory == null || callAdapterFactory.length <= 0) {
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        } else {
            for (CallAdapter.Factory factory : callAdapterFactory) {
                builder.addCallAdapterFactory(factory);
            }
        }
        if (converterFactory == null || converterFactory.length <= 0) {
            builder.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(GsonAdapter.buildGson()));
        } else {
            for (Converter.Factory factory : converterFactory) {
                builder.addConverterFactory(factory);
            }
        }
        if (mOkHttpClient == null) {
            builder.client(createOkHttpClient());
        } else {
            builder.client(mOkHttpClient);
        }
        return builder.build();
    }

    /**
     * 创建OkHttpClient
     *
     * @return OkHttpClient
     */
    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置读取超时
        builder.readTimeout(10, TimeUnit.SECONDS);
        //设置注销超时
        builder.writeTimeout(10, TimeUnit.SECONDS);
        //设置连接超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        //获取并设置SSL证书进行客户端验证
        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sslSocketFactory, sslParams.trustManager);
        //获取实例化日志拦截器并添加拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new RxHttpLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        return builder.build();
    }

}
