package com.laodai.mvp.base;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/05
 *     desc   : 接口：用于数据从 M(Model) 到 V(View) 的层间传递
 *     version: 1.0
 * </pre>
 */
public interface MyListener<T> {

    /**
     * 请求成功时回调
     * @param result 结果
     */
    void onSuccess(T result);

    /**
     * 失败时的回调接口
     * @param errorMsg 回调的错误信息
     */
    void onError(String errorMsg);

}
