package com.laodai.tinyshop.ui.login;

import com.laodai.tinyshop.R;
import com.laodai.tinyshop.mvp.BaseMvpActivity;

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
