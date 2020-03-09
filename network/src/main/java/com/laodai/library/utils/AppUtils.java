package com.laodai.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.laodai.library.RxHttpUtils;

import java.util.UUID;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 21:07 2020-03-09
 * @ Description：APP工具类
 * @ Modified By：
 * @Version: ：1.0
 */
public class AppUtils {

    /**
     * 获取手机版本号
     *
     * @return 版本号
     */
    public static String getAppVersion() {
        PackageInfo pi;
        String versionNum;
        try {
            PackageManager pm = RxHttpUtils.getContext().getPackageManager();
            pi = pm.getPackageInfo(RxHttpUtils.getContext().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            versionNum = pi.versionName;
        } catch (Exception e) {
            versionNum = "0";
        }
        return versionNum;
    }

    /**
     * 获取手机唯一标识码UUID
     * 需要在清单文件中添加相应权限（android.permission.READ_PHONE_STATE）
     *
     * @return 返回UUID
     */
    public static String getUUID() {
        //获取全局上下文对象
        Context context = RxHttpUtils.getContext();
        String uuid = (String) SPUtils.get(context, "PHONE_UUID", "");
        if (TextUtils.isEmpty(uuid)) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null) {
                    @SuppressLint({"MissingPermission", "HardwareIds"}) String tmDevice = tm.getDeviceId();
                    @SuppressLint({"MissingPermission", "HardwareIds"}) String tmSerial = tm.getSimSerialNumber();
                    @SuppressLint("HardwareIds") String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    UUID deviceUuid = new UUID(androidId.hashCode(), tmDevice.hashCode() << 32 | tmSerial.hashCode());
                    uuid = deviceUuid.toString();
                    SPUtils.put(context, "PHONE_UUID", uuid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return uuid;
    }

}
