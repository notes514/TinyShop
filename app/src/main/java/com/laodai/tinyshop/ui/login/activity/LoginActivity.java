package com.laodai.tinyshop.ui.login.activity;

import com.laodai.tinyshop.R;
import com.laodai.tinyshop.mvp.BaseMvpActivity;
import com.laodai.tinyshop.ui.login.LoginContract;
import com.laodai.tinyshop.ui.login.LoginModelImpl;
import com.laodai.tinyshop.ui.login.LoginPresenterImpl;


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
    protected void bindData() {

    }

}
