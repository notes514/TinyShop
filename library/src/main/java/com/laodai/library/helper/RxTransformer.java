package com.laodai.library.helper;

import android.content.Context;

import com.laodai.library.interfaces.ILoadingView;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.components.RxActivity;
import com.trello.rxlifecycle3.components.RxFragment;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle3.components.support.RxFragmentActivity;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:45 2020-01-26
 * @ Description：控制操作线程以及网络请求生命周期的辅助类
 * @ Modified By：
 * @Version: ：1.0
 */
public class RxTransformer {

    /**
     * 无参数(无生命周期)
     *
     * @param <T> 泛型
     * @return 返回Observable
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers() {
        return switchSchedulers(null);
    }

    /**
     * 带参数(无生命周期)  显示loading对话框
     *
     * @param loadingView loading
     * @param <T>         泛型
     * @return 返回Observable
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers(final ILoadingView loadingView) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                if (loadingView != null) {
                                    loadingView.showLoadingView();
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (loadingView != null) {
                                    loadingView.hideLoadingView();
                                }
                            }
                        });
            }
        };
    }

    /**
     * 在Activity中使用(含网络请求生命周期)
     * 无加载框
     *
     * @param context 上下文
     * @param <T> 泛型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> observableAIOMain(final Context context) {
        return observableAIOMain(context, null);
    }

    /**
     * 在Activity中使用(含网络请求生命周期)
     *
     * @param context 上下文
     * @param loadingView 加载框
     * @param <T> 泛型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> observableAIOMain(final Context context,
                                                                   final ILoadingView loadingView) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                if (loadingView != null) {
                                    loadingView.showLoadingView();
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (loadingView != null) {
                                    loadingView.hideLoadingView();
                                }
                            }
                        });
                return composeContext(context, observable);
            }
        };
    }

    /**
     * 在Fragment中使用(含网络请求生命周期)
     *
     * @param fragment 当前fragment
     * @param <T> 泛型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> observableFIOMain(final RxFragment fragment) {
        return observableFIOMain(fragment, null);
    }

    /**
     * 在Fragment中使用(含网络请求生命周期)
     *
     * @param fragment 当前fragment
     * @param loadingView 加载框
     * @param <T> 泛型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> observableFIOMain(final RxFragment fragment,
                                                                   final ILoadingView loadingView) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (loadingView != null) {
                                    loadingView.showLoadingView();
                                }
                            }
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (loadingView != null) {
                                    loadingView.hideLoadingView();
                                }
                            }
                        })
                        .compose(fragment.<T>bindToLifecycle());
            }
        };
    }

    /**
     * 使用发射器的方式请求网络(未完成)
     *
     * @param loadingView 加载框
     * @param <T> 泛型
     * @return FlowableTransformer
     */
    public static <T> FlowableTransformer<T, T> flowableIOMain(final ILoadingView loadingView) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io());
            }
        };
    }

    /**
     * 复合上下文绑定Activity的生命周期
     *
     * @param context    上下文
     * @param observable Observable(被观察者)
     * @param <T> 泛型
     * @return ObservableSource<T>
     */
    private static <T> ObservableSource<T> composeContext(Context context, Observable<T> observable) {
        if (context instanceof RxActivity) {
            return observable.compose(((RxActivity) context).<T>bindUntilEvent(ActivityEvent.DESTROY));
        } else if (context instanceof RxFragmentActivity) {
            return observable.compose(((RxFragmentActivity) context).<T>bindUntilEvent(ActivityEvent.DESTROY));
        } else if (context instanceof RxAppCompatActivity) {
            return observable.compose(((RxAppCompatActivity) context).<T>bindUntilEvent(ActivityEvent.DESTROY));
        } else {
            return observable;
        }
    }
}
