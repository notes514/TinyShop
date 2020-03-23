package com.laodai.network.manger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 23:47 2020-01-07
 * @ Description：管理请求，专门用于取消请求
 * @ Modified By：
 * @Version: ：1.0
 */
public class RxHttpManager implements IRxHttpManager<Object> {
    //单例
    private static RxHttpManager mInstance = null;
    //Map对象
    private HashMap<Object, CompositeDisposable> mMaps;

    /**
     * 请求单例
     *
     * @return RxHttpManager
     */
    public static RxHttpManager getInstance() {
        if (mInstance == null) {
            synchronized (RxHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new RxHttpManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 无参构造
     */
    private RxHttpManager() {
        mMaps = new HashMap<>();
    }

    @Override
    public void add(Object tag, Disposable disposable) {
        if (tag == null) return;
        //tag的下一组或者一个请求，用来处理一个页面的所有请求或者某个请求
        //设置相同的tag就可以取消当前页面所有请求或者某个请求了
        CompositeDisposable compositeDisposable = mMaps.get(tag);
        if (compositeDisposable == null) {
            CompositeDisposable compositeDisposableNew = new CompositeDisposable();
            compositeDisposableNew.add(disposable);
            mMaps.put(tag, compositeDisposableNew);
        } else {
            compositeDisposable.add(disposable);
        }
    }

    @Override
    public void remove(Object tag) {
        if (tag == null) return;
        if (!mMaps.isEmpty()) mMaps.remove(tag);
    }

    @Override
    public void cancel(Object tag) {
        if (tag == null) return;
        if (mMaps.isEmpty()) return;
        if (mMaps.get(tag) == null) return;
        if (!mMaps.get(tag).isDisposed()) {
            mMaps.get(tag).dispose();
            mMaps.remove(tag);
        }

    }

    @Override
    public void cancel(Object... tags) {
        if (tags == null) return;
        for (Object tag : tags) {
            cancel(tag);
        }
    }

    @Override
    public void cancelAll() {
        if (mMaps.isEmpty()) return;
        Iterator<Map.Entry<Object, CompositeDisposable>> iterator = mMaps.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Object, CompositeDisposable> entry = iterator.next();
            CompositeDisposable disposable = entry.getValue();
            //如果直接使用map的remove方法会报这个错误java.util.ConcurrentModificationException
            //所以要使用迭代器的方法remove
            if (disposable != null) {
                if (!disposable.isDisposed()) {
                    disposable.dispose();
                    iterator.remove();
                }
            }
        }
    }
}
