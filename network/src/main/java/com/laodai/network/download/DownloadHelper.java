package com.laodai.network.download;

import com.laodai.network.factory.ApiFactory;
import com.laodai.network.helper.RxTransformer;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 15:37 2020-02-02
 * @ Description：文件下载辅助类
 * @ Modified By：
 * @Version: ：1.0
 */
public class DownloadHelper {

    public static Observable<ResponseBody> downloadFile(String fileUrl) {
        //默认文件下载的key
        String DEFAULT_DOWNLOAD_KEY = "defaultDownloadUrlKey";
        //默认文件下载的baseUrl
        String DEFAULT_BASE_URL = "https://api.github.com/";
        return ApiFactory.getInstance().setOkClient(new OkHttpClient.Builder()
                .addInterceptor(new DownLoadInterceptor())
                .build())
                .createApi(DEFAULT_DOWNLOAD_KEY, DEFAULT_BASE_URL, DownloadApi.class)
                .downloadFiles(fileUrl)
                .compose(RxTransformer.<ResponseBody>switchSchedulers());
    }

}
