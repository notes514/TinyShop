package com.laodai.tinyshop.ui.shoppingcart;


import com.laodai.tinyshop.mvp.BaseModel;
import com.laodai.tinyshop.mvp.BasePresenter;
import com.laodai.tinyshop.mvp.BaseView;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/05
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface ShoppingCartContract {

    interface ShoppingCartModel extends BaseModel {
    }

    interface ShoppingCartView extends BaseView {
    }

    abstract class ShoppingCartPresenter
            extends BasePresenter<ShoppingCartModel, ShoppingCartView> {
    }

}
