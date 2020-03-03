package com.laodai.library.base;

import com.laodai.library.exception.ApiException;
import com.laodai.library.interfaces.ISubscriber;
import com.laodai.library.manger.RxHttpManager;

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

    /**
     * 是否隐藏Toast
     *
     * @return true为真，false为否
     */
    protected boolean isHideToast() {
        return false;
    }

    /**
     * 标记网络请求的tag
     * tag下的一组或一个请求，用来处理一个页面的所有请求或者某个请求
     * 设置tag就可以取消当前页面所有请求或者某个请求了
     *
     * @return string
     */
    protected String setTag() {
        return null;
    }

    @Override
    public void onSubscribe(Disposable d) {
        //添加请求并设置Tag
        RxHttpManager.getInstance().add(setTag(), d);
        doOnSubscribe(d);
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
