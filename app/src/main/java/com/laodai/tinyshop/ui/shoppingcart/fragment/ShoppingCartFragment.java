package com.laodai.tinyshop.ui.shoppingcart.fragment;

import android.widget.TextView;

import com.laodai.tinyshop.R;
import com.laodai.tinyshop.mvp.BaseMvpFragment;
import com.laodai.tinyshop.ui.shoppingcart.ShoppingCartContract;
import com.laodai.tinyshop.ui.shoppingcart.ShoppingCartModelImpl;
import com.laodai.tinyshop.ui.shoppingcart.ShoppingCartPresenterImpl;

import butterknife.BindView;

public class ShoppingCartFragment
        extends BaseMvpFragment<ShoppingCartPresenterImpl, ShoppingCartModelImpl>
        implements ShoppingCartContract.ShoppingCartView {

    @BindView(R.id.text_dashboard)
    TextView textDashboard;

    @Override
    protected int getLayout() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected void initValidada() {
        textDashboard.setText("购物车");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void bindData() {

    }

}