package com.laodai.library.download;

import io.reactivex.Observable;
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
        String DEFAULT_BASE_URL = "https://api.github.com/";
        return null;
//        String DEFAULT_DOWNLOAD_KEY = "defaultDownloadUrlKey";
//        String DEFAULT_BASE_URL = "https://api.github.com/";
//        return ApiFactory.getInstance()
//                .setOkClient(new OkHttpClient.Builder().addInterceptor(new DownloadInterceptor()).build())
//                .createApi(DEFAULT_DOWNLOAD_KEY, DEFAULT_BASE_URL, DownloadApi.class)
//                .downloadFile(fileUrl)
//                .compose(Transformer.<ResponseBody>switchSchedulers());
    }

}
