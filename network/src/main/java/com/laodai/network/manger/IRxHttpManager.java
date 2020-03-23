package com.laodai.network.manger;

import io.reactivex.disposables.Disposable;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 23:43 2020-01-07
 * @ Description：请求管理接口
 * @ Modified By：
 * @Version: ：1.0
 */
public interface IRxHttpManager<T> {

    /**
     * 添加请求
     *
     * @param tag tag
     * @param disposable disposable
     */
    void add(T tag, Disposable disposable);

    /**
     * 移除请求
     *
     * @param tag tag
     */
    void remove(T tag);

    /**
     * 取消某个tag请求
     *
     * @param tag tag
     */
    void cancel(T tag);

    /**
     * 取消某些tag请求
     *
     * @param tags
     */
    void cancel(T... tags);

    /**
     * 取消所有请求
     */
    void cancelAll();
}
