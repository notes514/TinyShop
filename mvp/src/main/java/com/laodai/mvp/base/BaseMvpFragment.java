package com.laodai.mvp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.laodai.mvp.utils.ReflectUtil;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/06
 *     desc   : mvp基类fragment
 *     version: 1.0
 * </pre>
 */
public abstract class BaseMvpFragment
        <P extends BasePresenter, M extends BaseModel> extends BaseFragment {
    protected P mPresenter;
    protected M mModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = ReflectUtil.getT(this, 0);
        mModel = ReflectUtil.getT(this, 1);
        //弱引用(关联)
        mPresenter.onAttach(mModel, this);
    }

    /**
     * 初始化变量
     */
    protected abstract void initValidada();

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

    @Override
    public void onStart() {
        super.onStart();
        initValidada();
        initListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //弱引用(解联)
        mPresenter.onDetach();
    }

}
