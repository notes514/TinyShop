package com.laodai.network.interfaces;

import io.reactivex.disposables.Disposable;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 23:32 2020-01-07
 * @ Description：自定义网络请求处理接口
 * @ Modified By：
 * @Version: ：1.0
 */
public interface ISubscriber<T> {

    /**
     * doOnSubscribe 回调
     *
     * @param d Disposable
     */
    void doOnSubscribe(Disposable d);

    /**
     * 网络请求失败回调
     *
     * @param errorMsg 错误描述
     */
    void doOnError(String errorMsg);

    /**
     * 请求成功回调
     *
     * @param t 泛型
     */
    void doOnNext(T t);

    /**
     * 请求完成回调
     */
    void doOncompleted();

}
