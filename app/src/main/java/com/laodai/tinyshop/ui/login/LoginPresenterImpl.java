package com.laodai.tinyshop.ui.login;

import com.laodai.mvp.base.MyListener;
import com.laodai.tinyshop.entity.User;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/06
 *     desc   : 登录Presenter层
 *     version: 1.0
 * </pre>
 */
public class LoginPresenterImpl extends LoginContract.LoginPresenter {

    @Override
    protected void login(String name, String password) {
        mModel.login(name, password, new MyListener<User>() {
            @Override
            public void onSuccess(User result) {

            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }
}
