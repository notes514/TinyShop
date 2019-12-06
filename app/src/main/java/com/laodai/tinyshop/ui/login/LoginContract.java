package com.laodai.tinyshop.ui.login;

import com.laodai.tinyshop.mvp.BaseModel;
import com.laodai.tinyshop.mvp.BasePresenter;
import com.laodai.tinyshop.mvp.BaseView;

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
    }

    interface LoginView extends BaseView {
    }

    abstract class LoginPresenter extends BasePresenter<LoginModel, LoginView> {
    }

}
