package com.laodai.network.download;

import android.util.Log;

import com.laodai.network.RxHttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 21:15 2020-02-23
 * @ Description：保存文件下载管理类
 * @ Modified By：
 * @Version: ：1.0
 */
public class DownloadManager {
    private final String TAG = "DownloadManager";

    /**
     * 保存文件
     *
     * @param responseBody     响应体
     * @param destFileName     文件名（包括文件后缀）
     * @param destFileDir      文件下载路径
     * @param progressListener 下载进度监听
     * @return                 返回的文件路径
     * @throws Exception       抛出异常
     */
    public File saveFile(ResponseBody responseBody, String destFileName, String destFileDir,
                         ProgressListener progressListener) throws Exception {
        //获取默认目标文件目录
        String defaultDestFileDir = RxHttpUtils.getContext().getExternalFilesDir(null) + File.separator;
        //获取内容长度（字节数）
        long contentLength = responseBody.contentLength();
        //开始写入文件
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        //捕获异常
        try {
            //InputStream :1）抽象类，2）面向字节形式的I/O操作（8 位字节流）
            is = responseBody.byteStream();
            long sum = 0;
            File dir = new File(destFileDir == null ? defaultDestFileDir : destFileDir);
            if (!dir.exists()) dir.mkdirs();
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                //从指定的字节数组写入len字节
                fos.write(buf, 0, len);
                //获取已经下载的文件大小
                final long finalSum = sum;
                //设置下载监听
                progressListener.onResponseProgress(finalSum, contentLength, (int) ((finalSum * 0.1f / contentLength) * 100),
                        finalSum == contentLength, file.getAbsolutePath());
            }
            //刷新此输出流并强制任何缓冲输出字节写出来
            fos.flush();
            return file;
        } finally {
            try {
                //关闭响应体
                responseBody.close();
                //关闭流字节
                if (is != null) is.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException异常", e.fillInStackTrace());
            }
            try {
                if (fos != null) fos.close();

            } catch (IOException e) {
                Log.e(TAG, "IOException异常", e.fillInStackTrace());
            }
        }
    }
}
