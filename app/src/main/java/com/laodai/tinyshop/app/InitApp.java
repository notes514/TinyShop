package com.laodai.tinyshop.app;

import android.app.Application;

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
    public static final String TAG = "InitApp";
    private static InitApp instance = null;

    public static InitApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
