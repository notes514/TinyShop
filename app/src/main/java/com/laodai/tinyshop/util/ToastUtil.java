package com.laodai.tinyshop.util;

import android.widget.Toast;

import com.laodai.tinyshop.app.InitApp;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/06
 *     desc   : 进行点击吐司操作，解决连续点击连续吐司
 *     version: 1.0
 * </pre>
 */
public class ToastUtil {
    private static Toast mToast;

    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(InitApp.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }
}
