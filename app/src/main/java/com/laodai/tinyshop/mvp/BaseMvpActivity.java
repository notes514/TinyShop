package com.laodai.tinyshop.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.laodai.tinyshop.util.ReflectUtil;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseMvpActivity
        <P extends BasePresenter, M extends BaseModel> extends BaseActivity {
    //持有表示层
    protected P mPresenter;
    //持有model层
    protected M mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
    protected void onStart() {
        super.onStart();
        initValidada();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //弱引用(解联)
        mPresenter.onDetach();
    }
}
