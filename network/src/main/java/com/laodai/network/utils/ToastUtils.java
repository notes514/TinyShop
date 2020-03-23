package com.laodai.network.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.laodai.network.RxHttpUtils;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 22:28 2020-01-29
 * @ Description：Toast工具类
 * @ Modified By：
 * @Version: ：1.0
 */
public class ToastUtils {
    private static Toast mToast;

    /**
     * Toast提示
     *
     * @param msg 提示内容
     */
    @SuppressLint("ShowToast")
    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(RxHttpUtils.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
