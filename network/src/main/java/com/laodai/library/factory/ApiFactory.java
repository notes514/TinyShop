package com.laodai.library.factory;

import com.laodai.library.manger.RxUrlManger;
import com.laodai.library.retrofit.RetrofitBuilder;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 15:40 2020-02-02
 * @ Description：网络请求工厂类
 * @ Modified By：
 * @Version: ：1.0
 */
public class ApiFactory {
    //单例
    private volatile static ApiFactory instance;
    //缓存retrofit针对同一个域名下相同的ApiService不会重复创建retrofit对象
    private static HashMap<String, Object> apiServiceCache;
    //callAdapterFactory工厂
    private CallAdapter.Factory[] callAdapterFactory;
    //converterFactory工厂
    private Converter.Factory[] converterFactory;
    //OkHttpClient
    private OkHttpClient mOkHttpClient;

    public ApiFactory() {
        apiServiceCache = new HashMap<>();
    }

    /**
     * 单例模式
     *
     * @return ApiFactory
     */
    public static ApiFactory getInstance() {
        if (instance == null) {
            synchronized (ApiFactory.class) {
                if (instance == null) {
                    instance = new ApiFactory();
                }
            }
        }
        return instance;
    }

    /**
     * 清除所有缓存
     */
    public void claerAllApi() {
        apiServiceCache.clear();
    }

    public ApiFactory setCallAdapterFactory(CallAdapter.Factory... callAdapterFactory) {
        this.callAdapterFactory = callAdapterFactory;
        return this;
    }

    public ApiFactory setConverterFactory(Converter.Factory... converterFactory) {
        this.converterFactory = converterFactory;
        return this;
    }

    public ApiFactory setOkClient(OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient;
        return this;
    }

    public ApiFactory setBaseUrl(String baseUrl) {
        RxUrlManger.getInstance().setBaseUrl(baseUrl);
        return this;
    }

    public <A> A createApi(Class<A> apiClass) {
        String urlKey = RxUrlManger.DEFAULT_URL_KEY;
        String urlValue = RxUrlManger.getInstance().getBaseUrl();
        return createApi(urlKey, urlValue, apiClass);
    }

    private static <A> String getApiKey(String baseUrlKey, Class<A> apiClass) {
        return String.format("%s_%s", baseUrlKey, apiClass);
    }

    public <A> A createApi(String baseUrlKey, String baseUrlValue, Class<A> apiClass) {
        String key = getApiKey(baseUrlKey, apiClass);
        A api = (A) apiServiceCache.get(key);
        if (api == null) {
            Retrofit retrofit = new RetrofitBuilder()
                    .setBaseUrl(baseUrlValue)
                    .setCallAdapterFactory(callAdapterFactory)
                    .setConverterFactory(converterFactory)
                    .setOkHttpClient(mOkHttpClient)
                    .build();
            api = retrofit.create(apiClass);
            apiServiceCache.put(key, api);
        }
        return api;
    }

}
