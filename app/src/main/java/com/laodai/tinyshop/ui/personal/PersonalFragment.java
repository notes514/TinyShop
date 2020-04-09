package com.laodai.tinyshop.ui.personal;

import com.laodai.mvp.base.BaseMvpFragment;
import com.laodai.tinyshop.R;

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
}