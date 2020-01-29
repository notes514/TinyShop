package com.laodai.library.observer;

import android.text.TextUtils;

import com.laodai.library.base.BaseObserver;
import com.laodai.library.utils.ToastUtils;

import io.reactivex.disposables.Disposable;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:52 2020-01-29
 * @ Description：处理string字符串类型的回调
 * @ Modified By：
 * @Version: ：1.0
 */
public abstract class StringObserver extends BaseObserver<String> {

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
    protected abstract void onSuccess(String result);

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
    public void doOnNext(String s) {
        onSuccess(s);
    }

    @Override
    public void doOncompleted() {

    }
}
