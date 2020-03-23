package com.laodai.network.observer;

import android.text.TextUtils;

import com.laodai.network.base.BaseObserver;
import com.laodai.network.utils.ToastUtils;

import io.reactivex.disposables.Disposable;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:14 2020-01-29
 * @ Description：通用的通用的Observer
 * @ Modified By：
 * @Version: ：1.0
 */
public abstract class CommonObserver<T> extends BaseObserver<T> {

    /**
     * 请求失败回调
     *
     * @param errorMsg 错误描述信息
     */
    protected abstract void onError(String errorMsg);

    /**
     * 请求成功时回调
     *
     * @param result 结果
     */
    protected abstract void onSuccess(T result);

    @Override
    public void doOnSubscribe(Disposable d) {
    }

    @Override
    public void doOnError(String errorMsg) {
        if (!isHideToast() && !TextUtils.isEmpty(errorMsg)) {
            ToastUtils.showToast(errorMsg);
        }
        onError(errorMsg);
    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

    @Override
    public void doOncompleted() {

    }
}