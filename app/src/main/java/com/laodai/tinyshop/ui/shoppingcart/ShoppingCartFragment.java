package com.laodai.tinyshop.ui.shoppingcart;

import android.widget.TextView;

import com.laodai.mvp.base.BaseMvpFragment;
import com.laodai.tinyshop.R;

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
}