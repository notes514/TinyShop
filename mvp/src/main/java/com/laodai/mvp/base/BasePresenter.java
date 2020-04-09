package com.laodai.mvp.base;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/06
 *     desc   : BasePresenter类
 *              实现LifecycleObserver接口，就有能力监听Activity和Fragment的生命周期
 *     version: 1.0
 * </pre>
 */
public abstract class BasePresenter<M, V> implements LifecycleObserver {
    protected M mModel;
    protected WeakReference<V> mViewRef;

    /**
     * 负责V与P的关联
     * @param model 数据层
     * @param view 视图层
     */
    protected void onAttach(M model, V view) {
        mModel = model;
        mViewRef = new WeakReference<>(view);
    }

    /**
     * true：getView() 返回对应的View，false: null
     * @return view
     */
    protected V getView() {
        return isViewAttached() ? mViewRef.get() : null;
    }

    /**
     * 用于检测 V 是否已关联 P
     * @return true：已关联 false：未关联
     */
    protected boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 负责V与P的解联
     */
    protected void onDetach() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected void onCreate(LifecycleOwner owner) {
        Log.i("laodai", "onCreate: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected void onStart(LifecycleOwner owner) {
        Log.i("laodai", "onStart: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected void onResume(LifecycleOwner owner) {
        Log.i("laodai", "onResume: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected void onPause(LifecycleOwner owner) {
        Log.i("laodai", "onPause: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected void onStop(LifecycleOwner owner) {
        Log.i("laodai", "onStop: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy(LifecycleOwner owner) {
        Log.i("laodai", "onDestroy: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    protected void onAny(LifecycleOwner owner) {
        Log.i("laodai", "onAny: ");
    }
}
