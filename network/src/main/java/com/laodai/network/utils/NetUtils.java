package com.laodai.network.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.laodai.network.RxHttpUtils;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:27 2020-02-23
 * @ Description：网络工具类
 * @ Modified By：
 * @Version: ：1.0
 */
public class NetUtils {

    /**
     * 判断是否有网络
     *
     * @return 返回值
     */
    @SuppressLint("MissingPermission")
    public static boolean isNetworkConnected() {
        Context context = RxHttpUtils.getContext();
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo networkInfo = null;
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}
