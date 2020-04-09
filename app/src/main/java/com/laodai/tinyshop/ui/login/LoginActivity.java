package com.laodai.tinyshop.ui.login;

import androidx.appcompat.widget.Toolbar;

import com.laodai.mvp.base.BaseMvpActivity;
import com.laodai.tinyshop.R;

import butterknife.BindView;

public class LoginActivity extends BaseMvpActivity<LoginPresenterImpl, LoginModelImpl>
        implements LoginContract.LoginView {

    @BindView(R.id.bar_login)
    Toolbar barLogin;

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