package com.laodai.tinyshop.ui.login;

import com.laodai.mvp.base.BaseModel;
import com.laodai.mvp.base.BasePresenter;
import com.laodai.mvp.base.BaseView;
import com.laodai.mvp.base.MyListener;
import com.laodai.tinyshop.entity.User;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface LoginContract {

    interface LoginModel extends BaseModel {

        /**
         * 用户登录
         * @param name name
         * @param password password
         */
        void login(String name, String password, MyListener<User> listener);
    }

    interface LoginView extends BaseView {

        /**
         * 登录成功
         */
        void loginSuccess();
    }

    abstract class LoginPresenter extends BasePresenter<LoginModel, LoginView> {

        protected abstract void login(String name, String password);
    }

}
