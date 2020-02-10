package com.laodai.tinyshop.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/05
 *     desc   : Fragment基类
 *     version: 1.0
 * </pre>
 */
public abstract class BaseFragment extends Fragment implements BaseView {
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(getLayout(), container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    /**
     * 获取布局文件
     * @return 返回id
     */
    protected abstract int getLayout();

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        unbinder.unbind();
    }
}
