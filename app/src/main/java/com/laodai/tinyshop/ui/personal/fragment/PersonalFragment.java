package com.laodai.tinyshop.ui.personal.fragment;

import com.laodai.tinyshop.R;
import com.laodai.tinyshop.mvp.BaseMvpFragment;
import com.laodai.tinyshop.ui.personal.PersonalContract;
import com.laodai.tinyshop.ui.personal.PersonalModelImpl;
import com.laodai.tinyshop.ui.personal.PersonalPresenterImpl;

public class PersonalFragment extends BaseMvpFragment<PersonalPresenterImpl, PersonalModelImpl>
        implements PersonalContract.PersonalView {

    @Override
    protected int getLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initValidada() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void bindData() {

    }

}