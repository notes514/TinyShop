package com.laodai.network.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @ Author     ：laodai
 * @ Date       ：Created in 21:48 2020-01-30
 * @ Description：文件下载
 * @ Modified By：
 * @Version: ：1.0
 */
public interface DownloadApi {

    /**
     * 大文件官方建议用'@Streaming'来进行注解，不然会出现IO异常，小文件可以忽略不注入
     *
     * @param fileUrl fileUrl地址（动态传入）
     * @return ResponseBody
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFiles(@Url String fileUrl);

}
