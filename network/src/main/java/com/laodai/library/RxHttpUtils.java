package com.laodai.library;

import android.app.Application;
import android.content.Context;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 23:32 2020-01-07
 * @ Description：Rx网络请求工具类
 * @ Modified By：
 * @Version: ：1.0
 */
public class RxHttpUtils {
    //单例
    private static RxHttpUtils instance;
    //全局上下文
    private static Application context;

    /**
     * 请求单例(双重校验锁的方式)
     *
     * @return
     */
    public static RxHttpUtils getInstance() {
        if (instance == null) {
            synchronized (RxHttpUtils.class) {
                if (instance == null) {
                    instance = new RxHttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 获取全局上下文
     *
     * @return Context
     */
    public static Context getContext() {
        checkInitialize();
        return context;
    }

    /**
     * 检测是否调用初始化方法
     */
    private static void checkInitialize() {
        if (context == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用RxHttpUtils.getInstance().init(this) 初始化！");
        }
    }

    /**
     * 必须在全局Application先调用，获取contxet上下文，否则缓存无法使用
     *
     * @param application 全局Application
     * @return 当前对象
     */
    public RxHttpUtils init(Application application) {
        context = application;
        return this;
    }

}
