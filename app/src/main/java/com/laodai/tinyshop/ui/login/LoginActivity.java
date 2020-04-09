package com.laodai.tinyshop.ui.login;

import com.laodai.mvp.base.BaseMvpActivity;
import com.laodai.tinyshop.R;

public class LoginActivity extends BaseMvpActivity<LoginPresenterImpl, LoginModelImpl>
        implements LoginContract.LoginView {

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initValidada() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void loginSuccess() {

    }
}