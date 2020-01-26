package com.laodai.library.base;

import com.laodai.library.exception.ApiException;
import com.laodai.library.interfaces.ISubscriber;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 23:37 2020-01-07
 * @ Description：基类 BaseObserver
 * @ Modified By：
 * @Version: ：1.0
 */
public abstract class BaseObserver<T> implements Observer<T>, ISubscriber<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        doOnNext(t);
    }

    @Override
    public void onError(Throwable e) {
        //获取错误信息描述
        String errorMsg = ApiException.handleException(e).getMessage();
        doOnError(errorMsg);
    }

    @Override
    public void onComplete() {
        doOncompleted();
    }
}
