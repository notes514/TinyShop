package com.laodai.tinyshop.ui.home;

import com.laodai.mvp.base.BaseModel;
import com.laodai.mvp.base.BasePresenter;
import com.laodai.mvp.base.BaseView;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface HomeContract {

    interface HomeModel extends BaseModel {
    }

    interface HomeView extends BaseView {
    }

    abstract class HomePresenter extends BasePresenter<HomeModel, HomeView> {
    }

}
