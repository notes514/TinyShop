package com.laodai.network.interfaces;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 23:25 2020-01-08
 * @ Description：弹框
 * @ Modified By：接口化处理loadingView(解决只能使用dialog的局限)
 * @Version: ：1.0
 */
public interface ILoadingView {

    /**
     * 显示loadingView
     */
    void showLoadingView();

    /**
     * 隐藏loadingView
     */
    void hideLoadingView();

}
