package com.laodai.tinyshop.ui.home;

import android.widget.TextView;

import com.laodai.tinyshop.R;
import com.laodai.tinyshop.mvp.BaseMvpFragment;

import butterknife.BindView;

public class HomeFragment extends BaseMvpFragment<HomePresenterImpl, HomeModelImpl>
        implements HomeContract.HomeView {

    @BindView(R.id.text_home)
    TextView textHome;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initValidada() {
        textHome.setText("首页");
    }

    @Override
    protected void initListener() {

    }
}