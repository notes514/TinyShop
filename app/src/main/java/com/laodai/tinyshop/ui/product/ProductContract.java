package com.laodai.tinyshop.ui.product;

import com.laodai.mvp.base.BaseModel;
import com.laodai.mvp.base.BasePresenter;
import com.laodai.mvp.base.BaseView;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface ProductContract {

    interface LoginModel extends BaseModel {
    }

    interface LoginView extends BaseView {
    }

    abstract class LoginPresenter extends BasePresenter<LoginModel, LoginView> {
    }

}
