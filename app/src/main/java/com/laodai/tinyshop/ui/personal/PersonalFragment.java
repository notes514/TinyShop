package com.laodai.tinyshop.ui.personal;

import android.content.Intent;
import android.widget.TextView;

import com.laodai.mvp.base.BaseMvpFragment;
import com.laodai.tinyshop.R;
import com.laodai.tinyshop.ui.login.LoginActivity;

import butterknife.BindView;

public class PersonalFragment extends BaseMvpFragment<PersonalPresenterImpl, PersonalModelImpl>
        implements PersonalContract.PersonalView {
    @BindView(R.id.text_notifications)
    TextView textNotifications;

    @Override
    protected int getLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initValidada() {

    }

    @Override
    protected void initListener() {
        textNotifications.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });
    }
}