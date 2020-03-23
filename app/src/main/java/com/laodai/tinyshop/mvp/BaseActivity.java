package com.laodai.tinyshop.mvp;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.laodai.tinyshop.util.ToastUtil;

import butterknife.ButterKnife;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/06
 *     desc   : 基类Activity
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        //绑定ID
        ButterKnife.bind(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * 获取布局文件
     * @return 返回id
     */
    protected abstract int getLayout();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return checkBackAction() || super.onKeyDown(keyCode, event);
    }

    private boolean mFlag = false;
    private long mTimeout = -1;

    private boolean checkBackAction() {
        //判定时间设置为3秒
        long time = 3000L;
        boolean flag = mFlag;
        mFlag = true;
        boolean timeout = (mTimeout == -1 || (System.currentTimeMillis() - mTimeout) > time);
        if (mFlag && (mFlag != flag || timeout)) {
            mTimeout = System.currentTimeMillis();
            ToastUtil.showToast("再点击一次回到桌面");
            return true;
        }
        return !mFlag;
    }

}
