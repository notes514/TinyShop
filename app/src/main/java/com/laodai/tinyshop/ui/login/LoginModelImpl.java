package com.laodai.tinyshop.ui.login;

import com.laodai.tinyshop.entity.User;
import com.laodai.tinyshop.mvp.MyListener;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/06
 *     desc   : 登录model层
 *     version: 1.0
 * </pre>
 */
public class LoginModelImpl implements LoginContract.LoginModel {

    @Override
    public void login(String name, String password, MyListener<User> listener) {
    }
}
