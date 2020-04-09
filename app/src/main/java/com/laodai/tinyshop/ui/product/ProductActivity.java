package com.laodai.tinyshop.ui.product;

import com.laodai.mvp.base.BaseMvpActivity;
import com.laodai.tinyshop.R;

public class ProductActivity extends BaseMvpActivity<ProductPresenterImpl, ProductModelImpl>
        implements ProductContract.LoginView {

    @Override
    protected int getLayout() {
        return R.layout.activity_product;
    }

    @Override
    protected void initValidada() {
    }

    @Override
    protected void initListener() {

    }
}