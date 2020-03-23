package com.laodai.network.observer;

import android.text.TextUtils;

import com.laodai.network.base.BaseObserver;
import com.laodai.network.entity.BaseData;
import com.laodai.network.utils.ToastUtils;

import io.reactivex.disposables.Disposable;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:43 2020-01-29
 * @ Description：针对特定格式的时候设置的通用的Observer
 *                适用于：
 *                {
 *                  "code":200,
 *                  "msg":"成功",
 *                  "data":{
 *                      "userName":"test"
 *                      "token":"abcdefg123456789"
 *                      "userId":"1"
 *                  }
 *                }
 * @ Modified By：
 * @Version: ：1.0
 */
public abstract class DataObserver<T> extends BaseObserver<BaseData<T>> {

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
    public void doOnNext(BaseData<T> data) {
        onSuccess(data.getData());
        //根据需要对code进行统一处理
//        switch (data.getCode()) {
//            case 200:
//                onSuccess(data.getData());
//                break;
//            case 300:
//                break;
//            case 500:
//                onError(data.getMsg());
//                break;
//            default:
//        }
    }

    @Override
    public void doOncompleted() {

    }
}
