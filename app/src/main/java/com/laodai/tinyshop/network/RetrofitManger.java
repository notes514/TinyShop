package com.laodai.tinyshop.network;

import com.laodai.tinyshop.api.RequestURL;
import com.laodai.tinyshop.mvp.MvpListener;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RetrofitManger {
    //时间5s
    private static final int DEFAULT_TIMEOUT = 5;
    private static volatile RetrofitManger instance;
    //Retrofit(网络请求)
    private Retrofit mRetrofit;
    //网络请求接口
    private AppServer mAppServer;

    /**
     * 构造方法（私有）
     */
    private RetrofitManger() {
        //手动创建一个OKHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(RequestURL.IP_PORT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(builder.build())
                .build();
        mAppServer = mRetrofit.create(AppServer.class);
    }

    /**
     * 请求单例
     * @return
     */
    public static RetrofitManger getInstance() {
        if (instance == null) {
            synchronized (RetrofitManger.class) {
                if (instance == null) {
                    instance = new RetrofitManger();
                }
            }
        }
        return instance;
    }

    public void getUserLogin(Subscriber<ResponseBody> subscriber, String url, Map<String, Object> map) {
        mAppServer.sendGet(url, map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public <T> void sendGetUserLogin(String url, Map<String, Object> map, MvpListener<T> listener){
        Subscriber<T> subscriber = new Subscriber<T>() {
            @Override
            public void onCompleted() {
                listener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e.getMessage());
            }

            @Override
            public void onNext(T t) {
                listener.onNext(t);
            }
        };

        Observable observable = mAppServer.sendGet(url, map);
        toSubscribe(observable, subscriber);
    }

    private <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
