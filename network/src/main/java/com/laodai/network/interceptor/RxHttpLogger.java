package com.laodai.network.interceptor;

import android.util.Log;

import com.laodai.network.utils.JsonUtils;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 21:34 2020-02-10
 * @ Description：日志打印拦截器
 * @ Modified By：
 * @Version: ：1.0
 */
public class RxHttpLogger implements HttpLoggingInterceptor.Logger {
    //创建StringBuffer对象存储请求日志
    private StringBuffer mMessage = new StringBuffer();

    @Override
    public void log(String message) {
        //请求或者响应开始
        if (message.startsWith("--> GET")) {
            mMessage.setLength(0);
            mMessage.append(" ");
            mMessage.append("\r\n");
        }
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
            mMessage.append(" ");
            mMessage.append("\r\n");
        }
        //以{}或者[]形式的说明的是响应结果的json数据，需要进行格式化
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonUtils.formatJson(message);
        }
        mMessage.append(message.concat("\n"));
        //请求响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            Log.e("RxHttpUtils", mMessage.toString());
        }
    }
}
